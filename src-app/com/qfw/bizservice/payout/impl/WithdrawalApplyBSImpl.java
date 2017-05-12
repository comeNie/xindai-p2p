package com.qfw.bizservice.payout.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qfw.bizservice.custinfo.account.ITransferAccountsBS;
import com.qfw.bizservice.payout.ITranxService;
import com.qfw.bizservice.payout.IWithdrawalApplyBS;
import com.qfw.common.bizservice.ISeqBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.DateUtils;
import com.qfw.common.util.StringUtils;
import com.qfw.dao.payout.IWithdrawalsDAO;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizAccountBO;
import com.qfw.model.bo.BizWithdrawalApplyBO;
import com.qfw.model.bo.BizWithdrawalsBO;
import com.qfw.model.vo.payout.WithdrawalsVO;
import com.qfw.model.vo.transaction.TranxCon;

/**
 *
 */
@Service("withdrawalApplyBS")
public class WithdrawalApplyBSImpl extends BaseServiceImpl implements
	IWithdrawalApplyBS {
	
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	
	@Autowired
	private IWithdrawalsDAO withdrawalsDAO;
	
	@Autowired
	private ITranxService tranxService;
	
	@Autowired
	@Qualifier("transferAccountsBS")
	private ITransferAccountsBS transferAccountsBS;
	
	@Autowired
	private ISeqBS seqBS;
	
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
	public void saveWithdrawalApply(TranxCon tranxCon,WithdrawalsVO withdrawalsVO) throws BizException{
		BizWithdrawalApplyBO bizWithdrawalApplyBO = new BizWithdrawalApplyBO();
		try {
			bizWithdrawalApplyBO.setReqSn(tranxCon.getReqSn());
			bizWithdrawalApplyBO.setAccountName(tranxCon.getAcctName());
			bizWithdrawalApplyBO.setAccountNo(tranxCon.getAcctNo());
			bizWithdrawalApplyBO.setCustId(withdrawalsVO.getCustId());
			bizWithdrawalApplyBO.setFeeAmt(withdrawalsVO.getFeeAmt());
//			bizWithdrawalApplyBO.setId(id);
			bizWithdrawalApplyBO.setStatus(tranxCon.getStatus());
			bizWithdrawalApplyBO.setTxAmt(withdrawalsVO.getDealAmt());
			bizWithdrawalApplyBO.setTxDate(withdrawalsVO.getTradeDate());
			bizWithdrawalApplyBO.setBankCode(tranxCon.getBankcode());
			this.createOperator(bizWithdrawalApplyBO);
			withdrawalsDAO.save(bizWithdrawalApplyBO);
		} catch (Exception e) {
			log.error("提现申请保存失败："+e.getMessage());
		}
		
	}
	@Transactional(rollbackFor = BizException.class,propagation = Propagation.REQUIRES_NEW)
	public void drawalTransfer(WithdrawalsVO withdrawalsVO,BizAccountBO custAcc) throws BizException{
		BigDecimal feeAmt = withdrawalsVO.getFeeAmt();
		String tradeNum = withdrawalsVO.getTradeNum();
		BizAccountBO pmtxAccountBO = transferAccountsBS.getPMAccountBO(AppConst.ACCOUNT_TYPE_PMTX);//平台提现账号
		//从客户账号转账到平台提现账号
		transferAccountsBS.transferAccount(tradeNum, AppConst.TRADE_TYPE_WITHDRAW, 
				custAcc, pmtxAccountBO, withdrawalsVO.getDealAmt());
		if(feeAmt != null && feeAmt.compareTo(BigDecimal.ZERO)>0){
			BizAccountBO pmsyAccountBO = transferAccountsBS.getPMAccountBO(AppConst.ACCOUNT_TYPE_PMSY);//平台收益账号
			//从客户账号转账到平台收益账号
			transferAccountsBS.transferAccount(tradeNum, AppConst.TRADE_TYPE_TXGLF, custAcc, pmsyAccountBO, feeAmt);
		}
		//保存提现记录
		BizWithdrawalsBO withdrawalsBO = this.voTransForBO(withdrawalsVO);
		withdrawalsBO.setWorkItemId("0");
		withdrawalsBO.setCtrStateCd(AppConst.WITHDRAWALS_STATUS_SUCCESS);
		withdrawalsDAO.save(withdrawalsBO);
		
	}
	private BizWithdrawalsBO voTransForBO(WithdrawalsVO withdrawalsVO) throws BizException {
		BizWithdrawalsBO withdrawalsBO = new BizWithdrawalsBO();
		if(StringUtils.isEmpty(withdrawalsVO.getTradeNum())){
			String dateStr = DateUtils.getDateString("yyyyMMddHHmmss",new Date());
			String tradeNum =  dateStr+seqBS.getResultNum(AppConst.SEQ_PAY_NUM);
			withdrawalsBO.setTradeNum(tradeNum);
		}else{
			withdrawalsBO.setTradeNum(withdrawalsVO.getTradeNum());
		}
		
		withdrawalsBO.setCtrStateCd(AppConst.WITHDRAWALS_STATUS_SUCCESS);
		withdrawalsBO.setCustId(withdrawalsVO.getCustId());
		withdrawalsBO.setOutAccount(withdrawalsVO.getOutAccount());
		if(null == withdrawalsVO.getIntputAccount() || withdrawalsVO.getIntputAccount().length() <= 0) {
			withdrawalsBO.setInputAccount(withdrawalsVO.getOutAccount());
		} else { 
			withdrawalsBO.setInputAccount(withdrawalsVO.getIntputAccount());
		}
		withdrawalsBO.setFeeAmt(withdrawalsVO.getFeeAmt());
		withdrawalsBO.setTxAmt(withdrawalsVO.getDealAmt());
		withdrawalsBO.setTxDate(withdrawalsVO.getTxDate());
		withdrawalsBO.setSysCreateTime(new Date());
		withdrawalsBO.setSysUpdateTime(new Date());
		withdrawalsBO.setSysUpdateUser(withdrawalsVO.getCustId());
		withdrawalsBO.setSysCreateUser(withdrawalsVO.getCustId());
		withdrawalsBO.setBankName(withdrawalsVO.getBankName());
		return withdrawalsBO;
	}
	
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void withdrawalFailHandle(BizWithdrawalApplyBO bo) throws BizException{
		BigDecimal feeAmt = bo.getFeeAmt();
		Integer applyCustId = bo.getCustId();
		String reqSn = bo.getReqSn();
		
		BizAccountBO custAcc = transferAccountsBS.getAccountBO(applyCustId);//客户账号
		BizAccountBO pmtxAccountBO = transferAccountsBS.getPMAccountBO(AppConst.ACCOUNT_TYPE_PMTX);//平台提现账号
		//从平台提现账号转账到客户账号
		transferAccountsBS.transferAccount(reqSn, AppConst.TRADE_TYPE_CANCEL_WITHDRAW,  pmtxAccountBO,custAcc, bo.getTxAmt());
		if(feeAmt != null && feeAmt.compareTo(BigDecimal.ZERO)>0){
			BizAccountBO pmsyAccountBO = transferAccountsBS.getPMAccountBO(AppConst.ACCOUNT_TYPE_PMSY);//平台收益账号
			//从平台收益账号转账到客户账号
			transferAccountsBS.transferAccount(reqSn, AppConst.TRADE_TYPE_CANCEL_TXGLF, pmsyAccountBO,custAcc, feeAmt);
		}
		WithdrawalsVO vo = new WithdrawalsVO();
		vo.setTradeNum(reqSn);
		List<BizWithdrawalsBO> wdList = withdrawalsDAO.findByParams(vo);
		if(wdList != null && wdList.size() > 0){
			BizWithdrawalsBO wd = wdList.get(0);
			wd.setCtrStateCd(AppConst.WITHDRAWALS_STATUS_FAILURE);
			wd.setSysUpdateTime(new Date());
			withdrawalsDAO.update(bo);
		}
		//状态更新为已处理
		List<BizWithdrawalApplyBO> applyList = withdrawalsDAO.findApplyByParams(vo);
		if(applyList != null && applyList.size() > 0){
			BizWithdrawalApplyBO applyBO = applyList.get(0);
			applyBO.setStatus(AppConst.WD_STATUS_D);
			withdrawalsDAO.update(applyBO);
		}
	
	}
	
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void updateStatusOf(String status,String reqSn) throws BizException{
		StringBuilder sql = new StringBuilder("UPDATE BIZ_WITHDRAW_APPLY SET STATUS = ?  WHERE REQ_SN = ?" );
		this.getJdbcTemplate().update(sql.toString(),new Object[]{status,reqSn});
	}
	
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void updateQueryTime() throws BizException{
		StringBuilder sql = new StringBuilder("UPDATE BIZ_WITHDRAW_APPLY SET QUERY_TIME = QUERY_TIME + 1  WHERE STATUS = '1'" );
		this.getJdbcTemplate().update(sql.toString());
	}
	
}
