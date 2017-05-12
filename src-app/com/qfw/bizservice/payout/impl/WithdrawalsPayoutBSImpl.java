package com.qfw.bizservice.payout.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jbpm.api.ProcessEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aipg.transquery.QTDetail;
import com.qfw.batch.bizservice.util.DateUtil;
import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.bizservice.custinfo.account.ICustAccountBS;
import com.qfw.bizservice.custinfo.account.ITransferAccountsBS;
import com.qfw.bizservice.payout.ITranxService;
import com.qfw.bizservice.payout.IWithdrawalApplyBS;
import com.qfw.bizservice.payout.IWithdrawalsPayoutBS;
import com.qfw.bizservice.transaction.ITradeBS;
import com.qfw.common.bizservice.ICodeDictBS;
import com.qfw.common.bizservice.IMsgTemplateBS;
import com.qfw.common.bizservice.IParamBS;
import com.qfw.common.bizservice.ISeqBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.bizservice.permission.IRoleBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.SysBankCode;
import com.qfw.common.model.permission.SysRole;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.DateUtils;
import com.qfw.common.util.NumberUtils;
import com.qfw.common.util.StringUtils;
import com.qfw.common.util.web.ViewOper;
import com.qfw.dao.payout.IWithdrawalsDAO;
import com.qfw.jbpm.bizservice.IFlowBS;
import com.qfw.jbpm.model.JbpmConst;
import com.qfw.jbpm.model.bo.Jbpm4AuditOpinion;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizAccountBO;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.bo.BizTradeBO;
import com.qfw.model.bo.BizWithdrawalApplyBO;
import com.qfw.model.bo.BizWithdrawalsBO;
import com.qfw.model.vo.custinfo.account.AccountRequestVO;
import com.qfw.model.vo.payout.WithdrawalsResponseVO;
import com.qfw.model.vo.payout.WithdrawalsVO;
import com.qfw.model.vo.transaction.TranxCon;

/**
 * 提现bs
 *
 * @author kyc
 */
@Service("withdrawalsPayoutBS")
public class WithdrawalsPayoutBSImpl extends BaseServiceImpl implements
		IWithdrawalsPayoutBS {
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	@Autowired
	@Qualifier("flowBS")
	private IFlowBS flowBS;
	@Autowired
	private ITradeBS tradeBS;
	@Autowired
	private ICustAccountBS accountBS;
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private IWithdrawalsDAO withdrawalsDAO;
	@Autowired
	@Qualifier("roleBS")
	private IRoleBS roleBS;
	@Autowired
	private ISeqBS seqBS;
	
	@Autowired
	@Qualifier("transferAccountsBS")
	private ITransferAccountsBS transferAccountsBS;
	
	@Autowired
	private IMsgTemplateBS msgTemplateBS;
	
	@Autowired
	private ICustInfoBS custInfoBS;
	
	@Autowired
	private IParamBS paramBS;
	
	@Autowired
	private ITranxService tranxService;
	
	@Autowired
	private ICodeDictBS codeDictBS;
	
	@Autowired
	private IWithdrawalApplyBS withdrawalApplyBS;
	
	@Autowired
	private ICustAccountBS custAccountBS;
	
	@Override
	public List<Jbpm4AuditOpinion> getAuditOpinion(String workItemId) {
		try {
			List<Jbpm4AuditOpinion> result = this.flowBS.getAuditOpinion(workItemId);
			return result;
		} catch (Exception e) {
			log.error("获取审批意见异常：",e);
		}
		return new ArrayList<Jbpm4AuditOpinion>();
	}
	
	@Override
	public void transWithDrawPayou(WithdrawalsVO vo) throws BizException {
		String mess = this.validateVO(vo);
		if(null!=mess&&mess.length()>0){
			throw new BizException("提现信息校验失败："+mess);
		}
		
		// 账户信息接口
		AccountRequestVO requestVO = new AccountRequestVO();
		requestVO.setDealAmt(vo.getDealAmt());;
		requestVO.setDealType(AppConst.ACCOUNT_AMT);
//		requestVO.setAccount(vo.getOutAccount());// TODO
		requestVO.setCustId(vo.getCustId());
		requestVO.setEventTypeCd(AppConst.DETAIL_TYPE_ACCOUNTAMT);
		requestVO.setTxNo(vo.getTradeNum());
		BigDecimal usableAmt =this.accountBS.updateAccountAmt(requestVO);
		
		// 生成交易明细
		BizTradeBO tradeBO = new BizTradeBO();
		tradeBO.setAccountBal(usableAmt);
		tradeBO.setAccountNum(vo.getOutAccount());
		tradeBO.setTradaTime(new Date());
		tradeBO.setTradeAmt(vo.getDealAmt());
		tradeBO.setTradeTypeCd(AppConst.TRADE_TYPE_WITHDRAW);
		tradeBO.setComment(AppConst.TRADE_TYPE_WITHDRAW);
		tradeBO.setSysCreateUser(ViewOper.getUser().getUserId());
		tradeBO.setSysUpdateUser(ViewOper.getUser().getUserId());
		tradeBO.setSysCreateTime(new Date());
		tradeBO.setSysUpdateTime(new Date());
		tradeBO.setTradeNum(vo.getTradeNum());
		this.tradeBS.save(tradeBO);
	}
	
	/**
	 * 检查提现信息是否有效
	 * @param vo
	 * @return
	 */
	private String validateVO(WithdrawalsVO vo) {
		String str = "";
		if(null==vo){
			return "提现信息参数不能为空!";
		}
		if(null == vo.getOutAccount() || vo.getOutAccount().length()<=0){
			return "提现账号不能为空!";
		}
		return str;
	}
	
	private String VO2Condition(WithdrawalsVO withVO)throws BizException{
		StringBuilder sb = new StringBuilder(" where 1=1");
		if(withVO != null){
			if(null!=withVO.getOutAccount()&&withVO.getOutAccount().length()>0){
				sb.append(" and OUT_ACCOUNT = '").append(withVO.getOutAccount()).append("'");
			}
		}
		return sb.toString();
	}
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public String submitDrawal(WithdrawalsVO withdrawalsVO) throws BizException {
			//获取提现费率,并计算提现费用
			String withdrawalCost = paramBS.getParam(AppConst.PARAMETER_CODE_TXGLFL);
//			double costRate = Double.parseDouble(withdrawalCost);
//			BigDecimal feeAmt = withdrawalsVO.getDealAmt().multiply(new BigDecimal(costRate)).setScale(2,BigDecimal.ROUND_HALF_UP);
			BigDecimal feeAmt = new BigDecimal(withdrawalCost);
			BigDecimal exceedAmt = new BigDecimal(0);
			
			String minApprovalCash = paramBS.getParam(AppConst.MIN_APPROVAL_CASH);
			//申请金额大于最小审批提现金额
			if(StringUtils.isNotEmpty(minApprovalCash) 
					&& withdrawalsVO.getDealAmt().compareTo(new BigDecimal(minApprovalCash)) < 0){
				throw new BizException("提现金额要大于"+minApprovalCash+"元。"); 
			}
			BizAccountBO custAcc = transferAccountsBS.getAccountBO(withdrawalsVO.getCustId());//客户账号
			if(custAcc == null){
				throw new BizException("获取不到客户账号信息");
			}
			if(custAcc.getUsableBalAmt().subtract(custAcc.getRechargeAmt())
					.compareTo(withdrawalsVO.getDealAmt()) < 0){//
				BigDecimal useable = custAcc.getUsableBalAmt().subtract(custAcc.getRechargeAmt());
				if(useable.compareTo(BigDecimal.ZERO) < 0){
					exceedAmt =withdrawalsVO.getDealAmt();
				}else{
					exceedAmt =withdrawalsVO.getDealAmt().subtract(useable);
				}
				
				if(exceedAmt.compareTo(BigDecimal.ZERO) > 0){//未投资金额按不同费率收费
					BigDecimal exceedFee = exceedAmt.multiply(new BigDecimal(paramBS.getParam("EXCEED_WITHDRAW_RATE")));
					feeAmt = feeAmt.add(exceedFee);
				}
			}
			
			BigDecimal draAmt = withdrawalsVO.getDealAmt().add(withdrawalsVO.getFeeAmt());
			if(custAcc.getUsableBalAmt().compareTo(draAmt) < 0){
				 throw new BizException("提现金额【"+withdrawalsVO.getDealAmt()+"】 "
				 		+ "+ 提现费用【"+withdrawalsVO.getFeeAmt()+"】要小于 账户余额"+custAcc.getUsableBalAmt()+"元。"); 
			}
			//========================校验完成
			Date tradeDate = new Date();
			String dateStr = DateUtils.getDateString("yyyyMMddHHmmss",tradeDate);
			String tradeNum =  dateStr+seqBS.getResultNum(AppConst.SEQ_PAY_NUM);//交易编号
			
			withdrawalsVO.setTradeNum(tradeNum);
			withdrawalsVO.setFeeAmt(feeAmt);
			withdrawalsVO.setTxDate(tradeDate);
			withdrawalsVO.setTradeDate(DateUtils.getDateString("yyyyMMdd", tradeDate));
			//查询银行代码
			SysBankCode bank = codeDictBS.findBankCodeByType(withdrawalsVO.getBankType());
			if(bank == null){
				throw new BizException("该银行暂不支持提现，请重新选择银行卡");
			}
			withdrawalsVO.setBankName(bank.getBankName());
			boolean isfront=false;
			TranxCon tranxCon = new TranxCon();
			
			tranxCon.setAccountProp(AppConst.WD_ACCOUNT_PROP);
			tranxCon.setAccountType(AppConst.WD_ACCOUNT_TYPE);
			tranxCon.setAcctName(withdrawalsVO.getCustName());
			tranxCon.setAcctNo(withdrawalsVO.getOutAccount());
			//通联金额单位为 分，转换： X100
			tranxCon.setAmount(withdrawalsVO.getDealAmt().multiply(new BigDecimal(100)).toString());
			tranxCon.setBankcode(bank == null ? "" : bank.getBankCode());
			tranxCon.setMerchantId(paramBS.getParam("WD_MERCHANTID"));
			tranxCon.setReqSn(tradeNum);
			tranxCon.setSummary("世纪创投平台账户提现");
//			tranxCon.setTel(tel);
			//平台提现，调用转账服务，事务先提交。
			try {
				withdrawalApplyBS.drawalTransfer(withdrawalsVO, custAcc);
			} catch (Exception e) {
				log.error("平台提现失败=========="+e.getMessage());;
				throw new BizException("操作失败");
			}
			
			String status = AppConst.WD_STATUS_S;
			try {
				//add by yangjj  通联提现
				//通联提现
				status = tranxService.singleTranx(AppConst.WITHDRAWAL_TRX_CODE, AppConst.WITHDRAWAL_BUSICODE, 
						isfront,tranxCon);
			} catch (Exception e) {
				log.error("调用通联提现服务异常："+e.getMessage());
//				throw new BizException("提现失败");
				//保存提现申请信息
				status = AppConst.WD_STATUS_P;
			} 
			if(AppConst.WD_STATUS_S.equals(status)){//提现成功
				if(log.isDebugEnabled()){
					log.debug("============提现成功");
				}
//				sendMsg(withdrawalsVO);
				
			}else if(AppConst.WD_STATUS_F.equals(status)){//失败
				if(log.isDebugEnabled()){
					log.debug("============提现失败");
				}
				status = AppConst.WD_STATUS_F;
				tranxCon.setStatus(status);
				withdrawalApplyBS.saveWithdrawalApply(tranxCon, withdrawalsVO);
//				throw new BizException("提现失败");
			}
			else {
				if(log.isDebugEnabled()){
					log.debug("============提现处理中");
				}
				//保存提现申请信息
				status = AppConst.WD_STATUS_P;
				tranxCon.setStatus(status);
				withdrawalApplyBS.saveWithdrawalApply(tranxCon, withdrawalsVO);
			}
			
			return status;
			
	}
	
	public void withdrawalOrderHandle() throws BizException{
		WithdrawalsVO vo = new WithdrawalsVO();
		vo.setStatus(AppConst.WD_STATUS_P);
		//处理状态为"处理中"交易
		List<BizWithdrawalApplyBO> list = withdrawalsDAO.findApplyByParams(vo);
		if(list != null && list.size() > 0){
			TranxCon tranxCon = new TranxCon();
			tranxCon.setQueryStatus(AppConst.WD_QUERY_STATUS_ALL);
			tranxCon.setQueryType(AppConst.WD_QUERY_TYPE_DONE);
			//开始时间：前一天
//			tranxCon.setStartDate(DateUtils.getDateString("yyyyMMddHHmmss", DateUtils.addDay(DateUtils.getTodayStart(),-1)));
			//查询开始时间：当天
			tranxCon.setStartDate(DateUtils.getDateString("yyyyMMddHHmmss", DateUtils.getTodayStart()));
			tranxCon.setEndDate(DateUtils.getDateString("yyyyMMddHHmmss", DateUtils.getTodayEnd()));
			List<QTDetail> qtDetail = tranxService.queryTradeNew(AppConst.WITHDRAWAL_TRAN_URL, tranxCon, false);
			if(qtDetail != null && qtDetail.size() > 0){
				for(BizWithdrawalApplyBO applyBo : list){
					for(QTDetail detail : qtDetail){
						if(applyBo.getReqSn().equals(detail.getBATCHID())){
							if(AppConst.WD_RETURN_CODE_SUCCESS.equals(detail.getRET_CODE())
									|| AppConst.WD_RETURN_SUCCESS.equals(detail.getRET_CODE())){//成功
								
								withdrawalApplyBS.updateStatusOf(AppConst.WD_STATUS_S, applyBo.getReqSn());
								try {
									WithdrawalsVO withdrawalsVO = new WithdrawalsVO();
									withdrawalsVO.setDealAmt(applyBo.getTxAmt().subtract(applyBo.getFeeAmt()));
									withdrawalsVO.setCustId(applyBo.getCustId());
									sendMsg(withdrawalsVO);
								} catch (Exception e) {
									log.error("提现短信发送失败====="+e.getMessage());
								}
							
							}else{//失败
								withdrawalApplyBS.updateStatusOf(AppConst.WD_STATUS_F, applyBo.getReqSn());
							}
							break;
						}
					}
				}
			}
		}
		//处理状态为"失败"交易
		WithdrawalsVO failQueryVO = new WithdrawalsVO();
		failQueryVO.setStatus(AppConst.WD_STATUS_F);
		List<BizWithdrawalApplyBO> failList = withdrawalsDAO.findApplyByParams(failQueryVO);
		if(failList != null){
			for(BizWithdrawalApplyBO bo : failList){
				withdrawalApplyBS.withdrawalFailHandle(bo);
			}
		}
	}
	
	
	private void sendMsg(WithdrawalsVO withdrawalsVO){
		//发送短信
		try {
			Map<String,String> dataMap = new HashMap<String,String>();
			//您在${date}为账号XXXX充值${amt}元已到账，详咨XXXXXXXXXXXX
			BizAccountBO acc = transferAccountsBS.getAccountBO(withdrawalsVO.getCustId());
			BizCustomerBO cust = custInfoBS.findCustById(withdrawalsVO.getCustId());
			dataMap.put("DATE", DateUtils.getYmdhms(new Date()));//对应模板的${date}
			dataMap.put("AMT", NumberUtils.format2(withdrawalsVO.getDealAmt()));//对应模板的${amt}
			dataMap.put("CUST_NAME", cust.getCustName());
			dataMap.put("BALANCE", NumberUtils.format2(acc.getUsableBalAmt()));
			msgTemplateBS.sendMsg(AppConst.EVENTTYPE_DRAWAL, cust, dataMap);
		} catch (Exception e) {
			log.error("短信发送失败，交易编号："+withdrawalsVO.getTradeNum(),e);
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
	
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void saveWithdrawalsApply(WithdrawalsVO vo)
			throws BizException {
		try {
			BizWithdrawalsBO bo = this.voTransForBO(vo);
			Map map = new HashMap();
			SysUser user = ViewOper.getUser();
			map.put(JbpmConst.APPLY_USER, user);
			SysRole auditRole = roleBS.findSysRole(null,AppConst.WORKITEM_ROLE_FINANICAL);
			map.put(JbpmConst.AUDIT_ROLE, auditRole);
			map.put(JbpmConst.FLOW_REMARK, user.getUserName()+"申请提现金额"+ vo.getDealAmt());
			String workItemId = flowBS.startProcessInstanceAndCompleteTask("withdrawals_apply", "提交", map);
			
			String txNO =  seqBS.getResultNum(AppConst.TXNO);//获取交易编号
			
			bo.setWorkItemId(workItemId);
			bo.setTradeNum(txNO);
			bo.setCtrStateCd(AppConst.WITHDRAWALS_STATUS_PENDING);
			this.withdrawalsDAO.save(bo);
			
		} catch (Exception e) {
			log.warn("提现申请提交工作流失败："+e);
			throw new BizException(e.getMessage());
		}
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void tranTakeAndCompleteTask(String taskId, String to,
			WithdrawalsVO vo, SysUser auditUser, String auditStatus,
			boolean finalApp, String html) throws BizException {
	try {
			// 工作流流转
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put(JbpmConst.CUR_AUDIT_USER, auditUser);
			paraMap.put(JbpmConst.AUDIT_STATUS_CD, auditStatus);
			if(finalApp&&("通过".equals(to)||"撤销".equals(to))){
				// 流程结束，成功或失败
				paraMap.put(JbpmConst.FLOW_HTML, html);
			}
			flowBS.completeTask(taskId,to, vo.getComment(), paraMap);

			// 更新提现申请
			
			List<BizWithdrawalsBO> boList =  this.withdrawalsDAO.findByParams(vo);
			if(null == boList || boList.size()!=1){
				throw new BizException("获取不到具体的提现申请信息");
			}
			
			BizWithdrawalsBO bo = boList.get(0);
			
			String txNo = bo.getTradeNum();
			String workItemID = bo.getWorkItemId();
			
			if(auditStatus.equals(JbpmConst.AUDIT_STATUS_CD_YES)&&finalApp){
				bo.setCtrStateCd(AppConst.WITHDRAWALS_STATUS_SUCCESS);
				workItemID = AppConst.WORKITEMID_NORMAL;
			}else if(auditStatus.equals(JbpmConst.AUDIT_STATUS_CD_REVOKE)){
				bo.setCtrStateCd(AppConst.WITHDRAWALS_STATUS_FAILURE);
				workItemID = AppConst.WORKITEMID_NORMAL;
			}
			bo.setTradeNum(txNo);
			bo.setWorkItemId(workItemID);
			this.withdrawalsDAO.update(bo);
			
			// 提现--银行账户金额变更
			if(auditStatus.equals(JbpmConst.AUDIT_STATUS_CD_YES)&&finalApp){
				vo.setTradeNum(txNo);
				this.transWithDrawPayou(vo);
			}
		} catch (Exception e) {
			log.error("提现申请审批异常：",e);
			throw new BizException(e);
		}
	}
	
	@Override
	public List<BizWithdrawalsBO> findByParams(WithdrawalsVO vo) {
		return this.withdrawalsDAO.findByParams(vo);
	}
	
	/**
	 * vo转bo
	 * @param vo
	 * @return
	 */
	/*private BizWithdrawalsBO voTransForBO(WithdrawalsVO vo){
		BizWithdrawalsBO bo = new BizWithdrawalsBO();
		bo.setCustId(vo.getCustId());
		bo.setOutAccount(vo.getOutAccount());
		if(null == vo.getIntputAccount() || vo.getIntputAccount().length()<=0)
			bo.setInputAccount(vo.getOutAccount());
		else 
			bo.setInputAccount(vo.getIntputAccount());
		bo.setFeeAmt(vo.getFeeAmt());
		bo.setTxAmt(vo.getDealAmt());
		bo.setTradeNum(vo.getTradeNum());
		bo.setTxDate(new Date());
		bo.setCtrStateCd(vo.getStatus());
		this.createOperator(bo);
		bo.setSysUpdateTime(new Date());
		bo.setSysUpdateUser(ViewOper.getUser().getUserId());
		return bo;
	}*/
	
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
//		withdrawalsBO.setTxDate(new Date());
		withdrawalsBO.setTxDate(withdrawalsVO.getTxDate());
		withdrawalsBO.setSysCreateTime(new Date());
		withdrawalsBO.setSysUpdateTime(new Date());
		withdrawalsBO.setSysUpdateUser(withdrawalsVO.getCustId());
		withdrawalsBO.setSysCreateUser(withdrawalsVO.getCustId());
		return withdrawalsBO;
	}

	@Override
	public WithdrawalsVO findVOInfoByVO(WithdrawalsVO vo) {
		WithdrawalsVO result = new WithdrawalsVO();
		try {
			List<BizWithdrawalsBO> boList = this.findByParams(vo);
			AccountRequestVO reqVO = new AccountRequestVO();
			if(null != boList && boList.size() == 1){
				BizWithdrawalsBO bo = boList.get(0);
				reqVO.setAccount(bo.getOutAccount());
				BizAccountBO accountBO = this.accountBS.findInfoByAccount(reqVO);
				result = this.boTOVo(bo, accountBO);
			}
		} catch (Exception e) {
			log.error("提现申请初始化异常：",e);
		}
		return result;
	}
	
	private WithdrawalsVO boTOVo(BizWithdrawalsBO bo, BizAccountBO accountBO){
		WithdrawalsVO vo = new WithdrawalsVO();
		vo.setAccountBalAmt(accountBO.getAccountBalAmt());
		vo.setDealAmt(bo.getTxAmt());
		vo.setFeeAmt(bo.getFeeAmt());
		vo.setTradeNum(bo.getTradeNum());
		vo.setTradeDate(bo.getTxDate().toString());
		vo.setOutAccount(bo.getOutAccount());
		vo.setIntputAccount(bo.getInputAccount());
		vo.setWorkItemId(bo.getWorkItemId());
		vo.setUsableBalAmt(accountBO.getUsableBalAmt());
		return vo;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<WithdrawalsResponseVO> findWithBOPagesByVO(WithdrawalsVO withVO,
			int first, int pageSize) throws BizException {
		return this.commonQuery.findBySQLByPages(this.assembleSQL(withVO), first, pageSize,WithdrawalsResponseVO.class);
	};
	
	public List<WithdrawalsResponseVO> findWithBOByVO(WithdrawalsVO withVO) throws BizException {
		return this.commonQuery.findObjects(this.assembleSQL(withVO),WithdrawalsResponseVO.class);
	};
	
	@SuppressWarnings("unchecked")
	@Override
	public WithdrawalsResponseVO findWithBOById(Integer id) throws BizException {
		WithdrawalsVO vo = new WithdrawalsVO();
		vo.setId(id);
		List<WithdrawalsResponseVO> list = this.commonQuery.findObjects(this.assembleSQL(vo), WithdrawalsResponseVO.class);
		if(null!=list&&list.size()==1){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	@Override
	public int findWithCountByVO(WithdrawalsVO withVO) throws BizException {
		return commonQuery.findCountByWapSQL(this.assembleSQL(withVO), null);
	}
	
	/**
	 * 组装查询sql
	 * @param searchVO
	 * @return
	 */
	private String assembleSQL(WithdrawalsVO searchVO){
		StringBuffer str = new StringBuffer();
		//str.append(" select * from (");
		str.append(" select wals.ID,wals.CTR_STATE_CD,wals.FEE_AMT,wals.INPUT_ACCOUNT,wals.OUT_ACCOUNT,");
		str.append(" wals.TRADE_NUM,wals.TX_AMT,wals.TX_DATE,cust.CUST_NAME,sysuser.USER_CODE,sysuser.USER_NAME,");
		str.append(" cust.MOBILE_TELEPHONE as phone,wals.SYS_CREATE_TIME as apply_date,");
		str.append(" wals.bank_name,wals.INPUT_ACCOUNT as ACCOUNT_NAME");
//		str.append(" card.ACCOUNT_NAME,card.ACCOUNT_BANK as bank,card.bank_name,card.area_province,card.area_city");
		str.append(" from biz_withdrawals wals , biz_account acc ,biz_customer cust,sys_user sysuser");
//		str.append(" ,biz_card card");
		str.append(" where wals.CUST_ID = acc.CUST_ID AND acc.CUST_ID = cust.ID");
		str.append(" AND cust.USER_ID = sysuser.USER_ID");
//		str.append(" AND card.ACCOUNT_NUM = wals.OUT_ACCOUNT AND card.CUST_ID = acc.CUST_ID");
		if(null != searchVO.getId() && searchVO.getId() != 0 ){
			str.append(" and wals.id = '").append(searchVO.getId()).append("'");
		}
		if(null != searchVO.getUserCode() && searchVO.getUserCode().length()>0 ){
			str.append(" and sysuser.USER_CODE like '%").append(searchVO.getUserCode()).append("%'");
		}
		if(null != searchVO.getCustName() && searchVO.getCustName().length()>0 ){
			str.append(" and cust.CUST_NAME like '%").append(searchVO.getCustName()).append("%'");
		}
		if(null != searchVO.getUserName() && searchVO.getUserName().length()>0 ){
			str.append(" and sysuser.USER_NAME like '%").append(searchVO.getUserName()).append("%'");
		}
		if(null != searchVO.getStartDate()  ){
			str.append(" and wals.SYS_CREATE_TIME >= '").append(DateUtil.getYmdhms(searchVO.getStartDate())).append("'");
		}
		if(null != searchVO.getEndDate()  ){
			str.append(" and wals.SYS_CREATE_TIME <= '").append(DateUtil.getYmdhms(searchVO.getEndDate())).append("'");
		}
		if(searchVO.getStatus() != null){
			str.append(" and wals.CTR_STATE_CD = '").append(searchVO.getStatus()).append("'");
		}
		str.append(" order by wals.ID desc");
		//str.append(") temp where 1=1");
		return str.toString();
	}

	@Override
	public void updateWithdrawalsStatus(String status, List<String> idList)
			throws BizException {
		if(null == idList || idList.size()<=0){
			return;
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" update biz_withdrawals set CTR_STATE_CD = '").append(status).append("'");
		sql.append(" where ");
		int num =1;
		for (String id : idList) {
			if(num>1){
				sql.append(" or ");
			}
			sql.append(" id = '").append(id).append("'");
			num=num+1;
		}
		this.getJdbcTemplate().update(sql.toString());
	}

}
