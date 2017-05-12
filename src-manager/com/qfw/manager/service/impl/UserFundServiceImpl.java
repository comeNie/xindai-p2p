package com.qfw.manager.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.bizservice.custinfo.account.ICustAccountBS;
import com.qfw.bizservice.custinfo.account.ITransferAccountsBS;
import com.qfw.bizservice.payout.IWithdrawalsPayoutBS;
import com.qfw.bizservice.transaction.ITradeBS;
import com.qfw.common.bizservice.IMsgTemplateBS;
import com.qfw.common.bizservice.IParamBS;
import com.qfw.common.bizservice.ISeqBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.bizservice.permission.IRoleBS;
import com.qfw.common.bizservice.permission.IUserBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.permission.SysRole;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.DateUtils;
import com.qfw.common.util.NumberUtils;
import com.qfw.jbpm.bizservice.IFlowBS;
import com.qfw.jbpm.model.JbpmConst;
import com.qfw.manager.service.IUserFundService;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizAccountBO;
import com.qfw.model.bo.BizCardBO;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.bo.BizTradeBO;
import com.qfw.model.bo.BizWithdrawalsBO;
import com.qfw.model.vo.custinfo.account.AccountRequestVO;
import com.qfw.model.vo.payout.WithdrawalsVO;
import com.qfw.platform.model.json.Pager;

@Service("userFundService")
public class UserFundServiceImpl extends BaseServiceImpl implements IUserFundService {
	
	@Autowired
	private ISeqBS seqBS;
	@Autowired
	private IWithdrawalsPayoutBS withdrawalsPayoutBS;
	@Autowired
	private ICustAccountBS custAccountBS;
	@Autowired
	private ITradeBS tradeBS;
	
	@Autowired
	private IParamBS paramBS;
	
	@Autowired
	@Qualifier("roleBS")
	private IRoleBS roleBS;
	
	@Autowired
	@Qualifier("flowBS")
	private IFlowBS flowBS;

	@Autowired
	@Qualifier("userBS")
	private IUserBS userBS;
	
	@Autowired
	@Qualifier("transferAccountsBS")
	private ITransferAccountsBS transferAccountsBS;
	
	@Autowired
	private IMsgTemplateBS msgTemplateBS;
	
	@Autowired
	private ICustInfoBS custInfoBS;
	
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	@Override
	public Pager findTradeList(String tradeType, String startTime, String endTime,int requestPage, int pageSize, String userId) throws BizException {
		StringBuffer str = new StringBuffer();
		str.append(" select tra.ID,tra.ACCOUNT_BAL,tra.ACCOUNT_NUM,tra.`COMMENT`,date_format(tra.TRADA_TIME,'%Y-%m-%d %T') AS TRADA_TIME,tra.TRADE_AMT,");
		str.append(" tra.TRADE_NUM,tra.TRADE_TYPE_CD ");
		str.append(" from biz_trade tra ");
		str.append(" where tra.ACCOUNT_NUM = ? ");
		if(StringUtils.isNotEmpty(tradeType)){
			str.append(" and tra.TRADE_TYPE_CD="+tradeType);
		}
		if(StringUtils.isNotEmpty(startTime)){
			str.append(" and tra.SYS_CREATE_TIME >='"+startTime + "'");
		}
		if(StringUtils.isNotEmpty(endTime)){
			str.append(" and tra.SYS_CREATE_TIME <='"+endTime + "'");
		}
		str.append(" order by tra.ID desc,tra.TRADA_TIME desc");
		
		return findPagerByUserCode(str.toString(), requestPage, pageSize, userId);
	}
	
	private Pager findPagerByUserCode(String sql,int requestPage,int pageSize,String custId){
		Pager pager = new Pager();
		int count = getCommonQuery().findCountByWapSQL(sql,new Object[]{custId});
		pager.setTotalCount(count);
		pager.setPageSize(pageSize);
		if(count > 0){
			List list = getCommonQuery().findBySQLByPages(sql, (requestPage-1)*pageSize, pageSize,new Object[]{custId});
			pager.setList(list);
		}
		return pager;
	}
	
	public Pager findBankCardList(int requestPage, int pageSize, Integer custId) throws BizException {
		StringBuffer str = new StringBuffer();
		str.append(" select * ");
		str.append(" from biz_card card");
		str.append(" where card.CUST_ID = ? ");
		
		return findPagerByCust(str.toString(), requestPage, pageSize, custId);
	}
	
	public List findBankCardList(Integer custId) throws BizException {
		StringBuffer str = new StringBuffer();
		str.append(" select card.id,");
//		str.append(" 		card.cust_id,");
		str.append(" 		card.account_num, ");
		str.append(" 		card.bank_name ");
		str.append("  from biz_card card");
		str.append(" where card.cust_ID = " + custId + "");
		List list = commonQuery.findBySQLByPages(str.toString(), 0, 10, BizCardBO.class);
		
		return list;
	}
	
	private Pager findPagerByCust(String sql,int requestPage,int pageSize,Integer custId){
		Pager pager = new Pager();
		int count = getCommonQuery().findCountByWapSQL(sql,new Object[]{custId});
		pager.setTotalCount(count);
		pager.setPageSize(pageSize);
		if(count > 0){
			List list = getCommonQuery().findBySQLByPages(sql, (requestPage-1)*pageSize, pageSize,new Object[]{custId});
			pager.setList(list);
		}
		return pager;
	}
	
	@Transactional(rollbackFor = BizException.class,propagation = Propagation.REQUIRED)
	public void submitDrawal(WithdrawalsVO withdrawalsVO) throws BizException {
			//获取提现费率,并计算提现费用
			String withdrawalCost = paramBS.getParam(AppConst.PARAMETER_CODE_TXGLFL);
//			double costRate = Double.parseDouble(withdrawalCost);
//			BigDecimal feeAmt = withdrawalsVO.getDealAmt().multiply(new BigDecimal(costRate)).setScale(2,BigDecimal.ROUND_HALF_UP);
			BigDecimal feeAmt = new BigDecimal(withdrawalCost);
//			withdrawalsVO.setFeeAmt(feeAmt);
			
			BizWithdrawalsBO withdrawalsBO = this.voTransForBO(withdrawalsVO);
			
			String minApprovalCash = paramBS.getParam(AppConst.MIN_APPROVAL_CASH);
			//申请金额大于最小审批提现金额
			if(StringUtils.isNotEmpty(minApprovalCash) && withdrawalsVO.getDealAmt().compareTo(new BigDecimal(minApprovalCash)) >= 0){
				Map map = new HashMap();
				SysUser user = userBS.findUser(withdrawalsVO.getUserCode());
				map.put(JbpmConst.APPLY_USER, user);
				SysRole auditRole = roleBS.findSysRole(null,AppConst.WORKITEM_ROLE_FINANICAL);
				map.put(JbpmConst.AUDIT_ROLE, auditRole);
				map.put(JbpmConst.FLOW_REMARK, user.getUserName()+"申请提现金额"+ withdrawalsVO.getDealAmt());
				//if(false){
				//	String workItemId = flowBS.startProcessInstanceAndCompleteTask("withdrawals_apply", "提交", map);
				//	withdrawalsBO.setWorkItemId(workItemId);
				//}
				withdrawalsBO.setWorkItemId("0");
				withdrawalsBO.setCtrStateCd(AppConst.WITHDRAWALS_STATUS_SUCCESS);
			}else{
				throw new BizException("提现金额要大于"+minApprovalCash+"元。"); 
			}
			BizAccountBO custAcc = transferAccountsBS.getAccountBO(withdrawalsVO.getCustId());//客户账号
			if(custAcc.getRechargeAmt().compareTo(withdrawalsVO.getDealAmt()) < 0){//
				BigDecimal exceedAmt = withdrawalsVO.getDealAmt().subtract(custAcc.getRechargeAmt());
				if(exceedAmt.compareTo(BigDecimal.ZERO) > 0){//未投资金额按不同费率收费
					BigDecimal exceedFee = exceedAmt.multiply(new BigDecimal(paramBS.getParam("EXCEED_WITHDRAW_RATE")));
					feeAmt = feeAmt.add(exceedFee);
				}
			}
			withdrawalsBO.setFeeAmt(feeAmt);
			
			 BigDecimal draAmt = withdrawalsBO.getTxAmt().add(withdrawalsBO.getFeeAmt());
			 if(custAcc.getUsableBalAmt().compareTo(draAmt) < 0){
				 throw new BizException("提现金额【"+withdrawalsBO.getTxAmt()+"】 + 提现费用【"+withdrawalsBO.getFeeAmt()+"】要小于 账户余额"+custAcc.getUsableBalAmt()+"元。"); 
			 }
			
			
			withdrawalsVO.setTradeNum(withdrawalsBO.getTradeNum());
			//withdrawalsPayoutBS.transWithDrawPayou(withdrawalsVO);
			
			BizAccountBO pmtxAccountBO = transferAccountsBS.getPMAccountBO(AppConst.ACCOUNT_TYPE_PMTX);//平台提现账号
			
			//从客户账号转账到平台提现账号
			transferAccountsBS.transferAccount(withdrawalsVO.getTradeNum(), AppConst.TRADE_TYPE_WITHDRAW, custAcc, pmtxAccountBO, withdrawalsVO.getDealAmt());
			
			if(feeAmt != null && feeAmt.compareTo(BigDecimal.ZERO)>0){
				BizAccountBO pmsyAccountBO = transferAccountsBS.getPMAccountBO(AppConst.ACCOUNT_TYPE_PMSY);//平台收益账号
				//从客户账号转账到平台收益账号
				transferAccountsBS.transferAccount(withdrawalsVO.getTradeNum(), AppConst.TRADE_TYPE_TXGLF, custAcc, pmsyAccountBO, feeAmt);
			}
			
			withdrawalsPayoutBS.save(withdrawalsBO);
			
			//发送短信
			try {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				//您在${date}为账号XXXX充值${amt}元已到账，详咨XXXXXXXXXXXX
				dataMap.put("date", DateUtils.getYmdhms(new Date()));//对应模板的${date}
				dataMap.put("amt", NumberUtils.format2(withdrawalsBO.getTxAmt()));//对应模板的${amt}
				BizCustomerBO cust = custInfoBS.findCustById(withdrawalsVO.getCustId());
				dataMap.put("custName", cust.getCustName());
				msgTemplateBS.sendMsg(AppConst.EVENTTYPE_DRAWAL, cust, dataMap);
			} catch (Exception e) {
				log.error("短信发送失败",e);
			}
			
			/*
			 * 
			 
			// 账户信息接口
			AccountRequestVO requestVO = new AccountRequestVO();
			requestVO.setDealAmt(new BigDecimal(0).subtract(withdrawalsVO.getDealAmt()));;
			requestVO.setDealType(AppConst.ACCOUNT_AMT);
			requestVO.setCustId(withdrawalsVO.getCustId());
			requestVO.setEventTypeCd(AppConst.DETAIL_TYPE_ACCOUNTAMT);
			requestVO.setTxNo(withdrawalsVO.getTradeNum());
			BigDecimal usableAmt =this.custAccountBS.updateAccountAmt(requestVO);
			
			// 生成交易明细
			BizTradeBO tradeBO = new BizTradeBO();
			tradeBO.setAccountBal(usableAmt);
			tradeBO.setAccountNum(withdrawalsVO.getUserCode());
			tradeBO.setTradaTime(new Date());
			tradeBO.setTradeAmt(withdrawalsVO.getDealAmt());
			tradeBO.setTradeTypeCd(AppConst.TRADE_TYPE_WITHDRAW);
			tradeBO.setComment(AppConst.TRADE_TYPE_WITHDRAW);
			tradeBO.setSysCreateUser(withdrawalsVO.getCustId());
			tradeBO.setSysUpdateUser(withdrawalsVO.getCustId());
			tradeBO.setSysCreateTime(new Date());
			tradeBO.setSysUpdateTime(new Date());
			tradeBO.setTradeNum(withdrawalsVO.getTradeNum());
			this.tradeBS.save(tradeBO);
			*/
	}
	
	private BizWithdrawalsBO voTransForBO(WithdrawalsVO withdrawalsVO) throws BizException {
		BizWithdrawalsBO withdrawalsBO = new BizWithdrawalsBO();
		String txNO =  seqBS.getResultNum(AppConst.TXNO);//获取交易编号
		withdrawalsBO.setTradeNum(txNO);
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
		withdrawalsBO.setTxDate(new Date());
		withdrawalsBO.setSysCreateTime(new Date());
		withdrawalsBO.setSysUpdateTime(new Date());
		withdrawalsBO.setSysUpdateUser(withdrawalsVO.getCustId());
		withdrawalsBO.setSysCreateUser(withdrawalsVO.getCustId());
		return withdrawalsBO;
	}

	@Override
	public Pager findWithdrawalsList(int requestPage, int pageSize,
			Integer custId) throws BizException {
		StringBuffer str = new StringBuffer();
		str.append("select date_format(a.SYS_CREATE_TIME,'%Y-%m-%d %T') as tx_time,a.TX_AMT,a.FEE_AMT,a.OUT_ACCOUNT as tx_bankCard,a.CTR_STATE_CD as tx_status from biz_withdrawals a where a.CUST_ID =? order by a.ID desc ");
		return findPagerByCustId(str.toString(), requestPage, pageSize, custId);
	}
	
	
	private Pager findPagerByCustId(String sql,int requestPage,int pageSize,int custId){
		Pager pager = new Pager();
		int count = getCommonQuery().findCountByWapSQL(sql,new Object[]{custId});
		pager.setTotalCount(count);
		pager.setPageSize(pageSize);
		if(count > 0){
			List list = getCommonQuery().findBySQLByPages(sql, (requestPage-1)*pageSize, pageSize,new Object[]{custId});
			pager.setList(list);
		}
		return pager;
	}
	
}
