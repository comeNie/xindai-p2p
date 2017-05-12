package com.qfw.bizservice.loan.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qfw.bizservice.charge.IChargeBS;
import com.qfw.bizservice.credit.ICreditBS;
import com.qfw.bizservice.credit.report.ICreditReportBS;
import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.bizservice.custinfo.account.ITransferAccountsBS;
import com.qfw.bizservice.custinfo.recommendation.IRecommendationBS;
import com.qfw.bizservice.loan.ILoanBS;
import com.qfw.bizservice.repay.IRepayBS;
import com.qfw.common.bizservice.IMsgTemplateBS;
import com.qfw.common.bizservice.IParamBS;
import com.qfw.common.bizservice.ISeqBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.bizservice.permission.IRoleBS;
import com.qfw.common.bizservice.permission.IUserBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.BussConst;
import com.qfw.common.model.permission.SysRole;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.model.vo.MessageVO;
import com.qfw.common.util.CollectionUtil;
import com.qfw.common.util.DateUtils;
import com.qfw.common.util.NumberUtils;
import com.qfw.common.util.web.ViewOper;
import com.qfw.dao.loan.ILoanDAO;
import com.qfw.jbpm.bizservice.IFlowBS;
import com.qfw.jbpm.model.JbpmConst;
import com.qfw.manager.model.MyCreditorTranVO;
import com.qfw.manager.model.MyCreditorVO;
import com.qfw.manager.service.IUserCreditorServcie;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizAccountBO;
import com.qfw.model.bo.BizArrearsDetailBO;
import com.qfw.model.bo.BizCostBO;
import com.qfw.model.bo.BizCreditorRightBO;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.bo.BizLoanApproveBO;
import com.qfw.model.bo.BizLoanBO;
import com.qfw.model.vo.credit.RequestCreditVO;
import com.qfw.model.vo.repay.RepayPlanDetailVO;

@Service("loanBS")
public class LoanBSImpl extends BaseServiceImpl implements ILoanBS{
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	@Autowired
	public IRepayBS repayBS;
	@Autowired
	public ILoanDAO loanDAO; 
	@Autowired
	public ICreditBS creditBS;
	@Autowired
	public ITransferAccountsBS transferAccountsBS; 
	@Autowired
	public ISeqBS seqBS;
	@Autowired
	public IParamBS paramBS;
	@Autowired
	private IFlowBS flowBS;
	@Autowired
	private IRoleBS roleBS;
	@Autowired
	private IUserBS userBS;
	@Autowired
	private IMsgTemplateBS msgTemplateBS;
	@Autowired
	private ICustInfoBS custInfoBS;
	@Autowired
	private ICreditReportBS creditReportBS;
	@Autowired
	private IChargeBS chargeBS;
	
	@Autowired
	private IRecommendationBS recommendationBS;
	
	@Autowired
	private IUserCreditorServcie userCreditorServcie;//
	
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void startLoan(Integer loanApproveId,Integer userId) throws BizException{
		
		BizLoanApproveBO bizLoanApproveBO = (BizLoanApproveBO) loanDAO.findById(BizLoanApproveBO.class, loanApproveId);
		if(bizLoanApproveBO==null) throw new BizException("查询不到[loanApproveId]="+loanApproveId+"的[BizLoanApproveBO]对象");
//		SysUser user = userBS.findUserById(userId);
		SysUser user = userBS.findUserByCustId(bizLoanApproveBO.getCustId());
		Map map = new HashMap();
		map.put(JbpmConst.APPLY_USER, user);
		SysRole auditRole = roleBS.findSysRole(null,AppConst.WORKITEM_ROLE_FINANICAL);
		map.put(JbpmConst.AUDIT_ROLE, auditRole);
		map.put(JbpmConst.FLOW_REMARK, user.getUserName()+"发起一笔放款申请，借款申请金额"+ bizLoanApproveBO.getLoanAmt()+"（元）,已投标金额"+bizLoanApproveBO.getTenderUseAmt()+"（元）");
		String workItemId = flowBS.startProcessInstance("loan", null, map);
		bizLoanApproveBO.setWorkItemId(workItemId);
		loanDAO.update(bizLoanApproveBO);
	}
	
	
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void effectLoan(Integer loanApproveId) throws BizException {
		if(loanApproveId==null) throw new BizException("参数[loanApproveId]不能为空");
		BizLoanApproveBO bizLoanApproveBO = (BizLoanApproveBO) loanDAO.findById(BizLoanApproveBO.class, loanApproveId);
		if(bizLoanApproveBO==null) throw new BizException("查询不到[loanApproveId]="+loanApproveId+"的[BizLoanApproveBO]对象");
		//获取交易编号
		String txNO =  seqBS.getResultNum(AppConst.TXNO);
		if(txNO==null) throw new BizException("获取交易编号[txNO]为空");
		//初始化借据信息表
		BizLoanBO bizLoanBO = assemBizLoanBO(bizLoanApproveBO);
		if(bizLoanBO==null) throw new BizException("组装[bizLoanBO]出错");
		loanDAO.save(bizLoanBO);//先持久化借款BO
		
		//保存还款计划
		RepayPlanDetailVO repayPlanDetailVO = repayBS.saveRepayPlanDetail(new RepayPlanDetailVO(bizLoanBO, null));
		if(repayPlanDetailVO==null || repayPlanDetailVO.getBizRepayPlanDetailBOs().size()<=0) 
			throw new BizException("生成还款计划出错,[loanApproveId]="+loanApproveId);
		bizLoanBO.setTotalPeriod(repayPlanDetailVO.getBizRepayPlanDetailBOs().size());//回填总期数
		bizLoanBO.setSurplusPeriod(bizLoanBO.getTotalPeriod());//回填剩余期数
		bizLoanBO.setTtlPrincipalInterestAmt(repayPlanDetailVO.getTtlPrincipalInterestAmt());//回填总本息
		bizLoanBO.setTtlInterestAmt(repayPlanDetailVO.getTtlInterestAmt());//回填总利息
		
		
		//债权生效处理
		List<?>  bizCreditorRightBOs = this.getHibernateTemplate().find("From BizCreditorRightBO Where loanApproveId = ? ",loanApproveId);
		if(CollectionUtil.isEmpty(bizCreditorRightBOs))
			throw new BizException("未找到该[loanApproveId]="+loanApproveId+"对应的债权");
		BigDecimal ttlCRAmt = BigDecimal.ZERO; //累加总投资金额
		BizCreditorRightBO bizCreditorRightBO = null;
		Map<Integer,BizCustomerBO> custMap = new HashMap<Integer, BizCustomerBO>();
		MessageVO message = null;
		List<MessageVO> messageList = new ArrayList<MessageVO>();
		for(Object obj : bizCreditorRightBOs){
			bizCreditorRightBO = (BizCreditorRightBO)obj;
			if(bizCreditorRightBO.getLoanId()!=null) 
				throw new BizException("可能出现重复放款,请核实数据正确性");
			
			bizCreditorRightBO.setLoanId(bizLoanBO.getId());//借款ID
			bizCreditorRightBO.setLoanAmt(bizLoanBO.getLoanAmt());//借款金额
			bizCreditorRightBO.setTotalPeriod(bizLoanBO.getTotalPeriod());//回填总期数
			bizCreditorRightBO.setSurplusPeriod(bizCreditorRightBO.getTotalPeriod());//回填剩余期数
			bizCreditorRightBO.setCrStatusCd(AppConst.CR_STATUS_CD_REPAYING);//还款中
			bizCreditorRightBO.setNextGatherDate(repayPlanDetailVO.getFirstRepayDate());//下一收款日
			ttlCRAmt = ttlCRAmt.add(bizCreditorRightBO.getCrAmt());
			loanDAO.update(bizCreditorRightBO);	
			loanDAO.flush();
			
			BizCustomerBO cust = custInfoBS.findCustById(bizCreditorRightBO.getCustId());
			custMap.put(bizCreditorRightBO.getCustId(), cust);
			
			//add by yangjj 推荐提成发放 start 
			message = recommendationBS.recommendDeduct(bizCreditorRightBO.getCustId(), bizLoanApproveBO.getRecommendPercent(),
					bizCreditorRightBO.getTotalPeriod(),bizCreditorRightBO.getCrAmt(), txNO);
			if(message != null){
				messageList.add(message);
			}
			//add by yangjj 推荐提成发放 end 
			//add by yangjj 加入vip start
			BigDecimal creditorAmt = userCreditorServcie.calculateMyCreditor(bizCreditorRightBO.getCustId());
			if(new BigDecimal(paramBS.getParam("VIP_LINE")).compareTo(creditorAmt) <= 0){
				cust.setIsVip(BussConst.YES_FLAG);
				loanDAO.update(cust);
			}
			//add by yangjj 加入vip end
			
		}
				
		if(ttlCRAmt.compareTo(bizLoanBO.getLoanAmt())!=0)
			throw new BizException("借款金额和债权总金额不匹配,请核实数据的正确性");
		
		//调用额度占用服务 占用借款金额额度
		RequestCreditVO requestCreditVO = new RequestCreditVO();
		requestCreditVO.setRelId(String.valueOf(bizLoanBO.getCustId()));
		requestCreditVO.setRelTypeCd(AppConst.CREDITLIMIT_RELTYPE_CUST);//关联类型:客户类型
		requestCreditVO.setPassAmt(bizLoanApproveBO.getTenderUseAmt());//占用金额
		requestCreditVO.setUnPassAmt(bizLoanApproveBO.getTenderBalAmt());//释放金额
		requestCreditVO.setEventTypeCd(Integer.valueOf(AppConst.EVENTTYPE_CREDITOR));//交易类型:放款
		requestCreditVO.setTxNO(txNO);//交易编号
		creditBS.tranTransactionCredit(requestCreditVO); //TODO 额度调用
		
		//调用账户管理(转账服务) 【平台投资账户】放款金额转入【借款用户账户】
		transferAccountsBS.transferAccount(txNO,AppConst.TRADE_TYPE_JK, 
				transferAccountsBS.getPMAccountBO(AppConst.ACCOUNT_TYPE_PMTZ), 
				transferAccountsBS.getAccountBO(AppConst.PAYMENT_WAY_CD_SELF.equals(bizLoanBO.getPaymentWayCd())?bizLoanBO.getCustId():bizLoanBO.getTrusteeCustId()), bizLoanBO.getLoanAmt());
		//调用账户管理(转账服务) 【借款用户账户】风险准备金转入【风险准备金账户】
		transferAccountsBS.transferAccount(txNO,AppConst.TRADE_TYPE_TQFXZBJ, 
				transferAccountsBS.getAccountBO(AppConst.PAYMENT_WAY_CD_SELF.equals(bizLoanBO.getPaymentWayCd())?bizLoanBO.getCustId():bizLoanBO.getTrusteeCustId()), 
				transferAccountsBS.getPMAccountBO(AppConst.ACCOUNT_TYPE_PMFXZBJ), calculateFXZBJ(bizLoanBO.getLoanAmt()));
		//add by yangjj 扣收借款管理费 start
		List<?> bizCostBOs = loanDAO.findByHQL("From BizCostBO Where custId =? and relateId=? and relateTypeCd=? and costTypeCd=? and costStatusCd=?",
				new Object[]{bizLoanBO.getCustId(),bizLoanBO.getId(),AppConst.RELATE_TYPE_CD_LOAN,AppConst.COST_TYPE_CD_JKFY,AppConst.COST_STATUS_CD_WKF});
		
		if(CollectionUtil.isNotEmpty(bizCostBOs)){
			BizCostBO bizCostBO = (BizCostBO)bizCostBOs.get(0);
			chargeBS.deductCharge(txNO, bizCostBO);
		}
		//add by yangjj 扣收借款管理费 start
		//add by lsj start
		bizLoanApproveBO.setWorkItemId("0");
		bizLoanApproveBO.setApproveStatusCd(AppConst.APPROVE_STATUS_CD_LOAN);
		loanDAO.update(bizLoanApproveBO);
		//add by lsj end
		
		//add by kangyc start
		//更新信用报告中的成功借款笔数
		this.creditReportBS.updateApplyNum(bizLoanApproveBO.getCustId().toString(), null, 1, bizLoanApproveBO.getTenderUseAmt());
		//add by kangyc end
		//短信发送
		try {
			//放款成功短信
			Map<String,String> dataMap = new HashMap<String, String>();
			BizCustomerBO cust = custInfoBS.findCustById(bizLoanBO.getCustId());
			BizAccountBO account = transferAccountsBS.getAccountBO(bizLoanBO.getCustId());
			dataMap.put("CUST_NAME", cust.getCustName());
			dataMap.put("LOAN_NAME", bizLoanBO.getLoanName());
			dataMap.put("AMT", NumberUtils.format2(bizLoanBO.getLoanAmt()));
			dataMap.put("BALANCE", NumberUtils.format2(account.getUsableBalAmt()));
		
			msgTemplateBS.sendMsg(AppConst.EVENTTYPE_CREDITOR, cust, dataMap);
			//收益提醒短信
			if(!custMap.isEmpty()){
				Set<Entry<Integer, BizCustomerBO>> set = custMap.entrySet();
				Iterator<Entry<Integer, BizCustomerBO>> it  = set.iterator();
				while (it.hasNext()) {
					Entry<Integer, BizCustomerBO> entry = it.next();
					BizCustomerBO crCust = entry.getValue();
					dataMap.put("CUST_NAME", crCust.getCustName());
					dataMap.put("LOAN_NAME", bizLoanBO.getLoanName());
					try {
						msgTemplateBS.sendMsg(AppConst.EVENTTYPE_INCOME_CAL, crCust, dataMap);
					} catch (Exception e) {
						log.error("满标收益提醒异常=========："+e.getMessage());
					}
				}
			}
			//推荐提成短信
			for(MessageVO vo : messageList){
				dataMap.put("CUST_NAME", vo.getCustName());
				dataMap.put("AMT", vo.getTxAmt());
				dataMap.put("BALANCE", vo.getBalance());
				try {
					BizCustomerBO crCust = custInfoBS.findCustById(vo.getCustId());
					msgTemplateBS.sendMsg(AppConst.EVENTTYPE_DEDUCT, crCust, dataMap);
				} catch (Exception e) {
					log.error("推荐提成提醒异常=========："+e.getMessage());
				}
			}
		} catch (Exception e) {
			log.error("放款短信发送异常：============"+e.getMessage());
		}
		
		
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void invalidLoan(Integer loanApproveId) throws BizException {
		
		//更新借款发布表
		BizLoanApproveBO bizLoanApproveBO = (BizLoanApproveBO) loanDAO.findById(BizLoanApproveBO.class, loanApproveId);
		if(bizLoanApproveBO==null) throw new BizException("查询不到[loanApproveId]="+loanApproveId+"的[BizLoanApproveBO]对象");
		if(!AppConst.APPROVE_STATUS_CD_TENDERING.equals(bizLoanApproveBO.getApproveStatusCd())){
			throw new BizException("已满标，不能撤标");
		}
		BizLoanBO bizLoanBO =  bizLoanApproveBO.getBizLoanBO();
		if(bizLoanBO!=null) 
			throw new BizException("该标借款已发放,不能撤标");
		bizLoanApproveBO.setApproveStatusCd(AppConst.APPROVE_STATUS_CD_CANCEL);//更新状态,撤标	
		String txNO =  seqBS.getResultNum(AppConst.TXNO);//获取交易编号
		//调用额度管理,恢复待占用额度
		RequestCreditVO requestCreditVO = new RequestCreditVO();
		requestCreditVO.setRelId(String.valueOf(bizLoanApproveBO.getCustId()));
		requestCreditVO.setPassAmt(BigDecimal.ZERO);//占用金额
		requestCreditVO.setUnPassAmt(bizLoanApproveBO.getLoanAmt());//释放待占用金额
		requestCreditVO.setEventTypeCd(Integer.valueOf(AppConst.EVENTTYPE_LOAN));//交易类型
		requestCreditVO.setTxNO(txNO);//交易编号
		requestCreditVO.setRelTypeCd(AppConst.CREDITLIMIT_RELTYPE_CUST);//关联类型:客户类型
		creditBS.tranTransactionCredit(requestCreditVO); //TODO 额度调用
		
		//更新信用报告中的剩余额度
		this.creditReportBS.updateApplyAmtForRev(String.valueOf(bizLoanApproveBO.getCustId()), bizLoanApproveBO.getLoanAmt());
		
		//处理债权失效
		List<?>  bizCreditorRightBOs = this.getHibernateTemplate().find("From BizCreditorRightBO Where loanApproveId = ? ",loanApproveId);
//		if(CollectionUtil.isEmpty(bizCreditorRightBOs))
//			throw new BizException("未找到该[loanApproveId]="+loanApproveId+"对应的债权");
		
		Map<Integer,List> msgMap = new HashMap<Integer, List>();
		if(CollectionUtil.isNotEmpty(bizCreditorRightBOs)){
			BizCreditorRightBO bizCreditorRightBO = null;
			for(Object obj : bizCreditorRightBOs){
				bizCreditorRightBO = (BizCreditorRightBO)obj;
				bizCreditorRightBO.setLoanId(null);//借款ID
				bizCreditorRightBO.setLoanAmt(BigDecimal.ZERO);//借款金额
				bizCreditorRightBO.setTotalPeriod(0);//回填总期数
				bizCreditorRightBO.setSurplusPeriod(0);//回填剩余期数
				bizCreditorRightBO.setCrStatusCd(AppConst.CR_STATUS_CD_CANCEL);//撤标
				bizCreditorRightBO.setNextGatherDate(null);//下一收款日
				bizCreditorRightBO.setSettleDate(new Date());
				loanDAO.update(bizCreditorRightBO);		
				//调用账户管理(转账服务) 【平台投资账户】投资金额转入【投融资人账户】
				transferAccountsBS.transferAccount(txNO,AppConst.TRADE_TYPE_THTZ, 
						transferAccountsBS.getPMAccountBO(AppConst.ACCOUNT_TYPE_PMTZ), 
						transferAccountsBS.getAccountBO(bizCreditorRightBO.getCustId()),bizCreditorRightBO.getCrAmt());
				try {
					List<Map<String,String>> msgList = new ArrayList<Map<String,String>>();
					Map<String,String> dataMap = new HashMap<String,String>();
					BizAccountBO account = transferAccountsBS.getAccountBO(bizCreditorRightBO.getCustId());
					dataMap.put("BALANCE", NumberUtils.format2(account.getUsableBalAmt()));
					dataMap.put("AMT", NumberUtils.format2(bizCreditorRightBO.getCrAmt()));
					
					if(msgMap.get(bizCreditorRightBO.getCustId()) != null){
						msgMap.get(bizCreditorRightBO.getCustId()).add(dataMap);
					}else{
						msgList.add(dataMap);
						msgMap.put(bizCreditorRightBO.getCustId(),msgList);
					}
				} catch (Exception e) {
					log.error("退回投资短信发送封装异常:==========="+e.getMessage());
				}
			}
		}
		//add by lsj start
		bizLoanApproveBO.setWorkItemId("0");
		bizLoanApproveBO.setApproveStatusCd(AppConst.APPROVE_STATUS_CD_CANCEL);
		loanDAO.update(bizLoanApproveBO);
		//add by lsj end
		//调用短信发送
		try {
			if(!msgMap.isEmpty()){
				Set<Entry<Integer, List>> set = msgMap.entrySet();
				Iterator<Entry<Integer, List>> it = set.iterator();
				while (it.hasNext()) {
					Entry<Integer, List> entry = it.next();
					BizCustomerBO cust = custInfoBS.findCustById(entry.getKey());
					
					List<Map<String,String>> msgList = entry.getValue();
					for(Map dataMap : msgList){
						try {
							dataMap.put("CUST_NAME", cust.getCustName());
	//						dataMap.put("EVENT_TYPE", AppConst.EVENTTYPE_RETURN_TRENDER);
							dataMap.put("LOAN_NAME", bizLoanApproveBO.getLoanName());
						
							msgTemplateBS.sendMsg(AppConst.EVENTTYPE_RETURN_TRENDER, cust, dataMap);
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				}
			}
			//发送借款人短信
			Map<String,String> dataMap = new HashMap<String,String>();
			BizCustomerBO cust = custInfoBS.findCustById(bizLoanApproveBO.getCustId());
			dataMap.put("CUST_NAME", cust.getCustName());
			dataMap.put("LOAN_NAME", bizLoanApproveBO.getLoanName());
			msgTemplateBS.sendMsg(AppConst.EVENTTYPE_CANCEL_LOAN, cust, dataMap);
			
		} catch (Exception e) {
			log.error("退回投资短信发送失败"+e.getMessage());
		}
		
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public BigDecimal delayLoan(BizArrearsDetailBO bizArrearsDetailBO,Date batchDate) throws BizException {
		if(bizArrearsDetailBO == null) 
			throw new BizException("参数[bizArrearsDetailBO]不能为空");
		if(batchDate==null) batchDate = new Date();
		if(((int)DateUtils.minuDay(bizArrearsDetailBO.getRepayPlanDate(), batchDate)+1)>=bizArrearsDetailBO.getGraceDays()){
			bizArrearsDetailBO.setDelayDays(bizArrearsDetailBO.getGraceDays());//延期天数
		}else{
			bizArrearsDetailBO.setDelayDays((int)DateUtils.minuDay(bizArrearsDetailBO.getRepayPlanDate(), batchDate)+1);//延期天数
		}
		//未还本息
		BigDecimal unpaidAmt = bizArrearsDetailBO.getUnpaidInterestAmt().add(bizArrearsDetailBO.getUnpaidPrincipalAmt());
		//罚息 = 未还本金*延期利率*延期天数/360
		BigDecimal penaltyAmt = unpaidAmt.multiply(bizArrearsDetailBO.getDelayRate().multiply(new BigDecimal(bizArrearsDetailBO.getDelayDays())))
				.divide(AppConst.BASEINTDAY, AppConst.DECIMAL_AMT_PRECISION, BigDecimal.ROUND_HALF_UP);
		bizArrearsDetailBO.setUnpaidPenaltyAmt(penaltyAmt);
		bizArrearsDetailBO.setArrearsFlagCd(AppConst.ARREARS_FLAG_CD_ZQ);//展期
		bizArrearsDetailBO.setLastInterestDate(batchDate);//上次计息日期
		loanDAO.update(bizArrearsDetailBO);
		
		//更新借款信息
		BizLoanBO bizLoanBO = bizArrearsDetailBO.getBizLoanBO();
		bizLoanBO.setTtlPenaltyAmt(bizLoanBO.getTtlPenaltyAmt().add(penaltyAmt));
		bizLoanBO.setLoanStatusCd(AppConst.LOAN_STATUS_CD_ZQ);
		loanDAO.update(bizLoanBO);
		return penaltyAmt;
	}

	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public BigDecimal overdueLoan(BizArrearsDetailBO bizArrearsDetailBO,Date batchDate) throws BizException {
		if(bizArrearsDetailBO == null) 
			throw new BizException("参数[bizArrearsDetailBO]不能为空");
		if(batchDate==null) batchDate = new Date();
		
		
		//处理欠款明细信息
		bizArrearsDetailBO = (BizArrearsDetailBO)loanDAO.findById(BizArrearsDetailBO.class, bizArrearsDetailBO.getId());//加载BizArrearsDetailBO对象
		int oldOverdueDays = bizArrearsDetailBO.getOverdueDays();
		int overdueDays = (int)DateUtils.minuDay(DateUtils.addDay(bizArrearsDetailBO.getRepayPlanDate(), bizArrearsDetailBO.getGraceDays()), batchDate)+1;
		//未还本息
		BigDecimal unpaidAmt = bizArrearsDetailBO.getUnpaidInterestAmt().add(bizArrearsDetailBO.getUnpaidPrincipalAmt());
		//获取借据信息
		 BizLoanBO bizLoanBO = bizArrearsDetailBO.getBizLoanBO();
		// 更新信用报告中的逾期信息
		String custId = bizLoanBO.getCustId().toString();
		log.info("欠款明细表id:"+bizArrearsDetailBO.getId()+"信息逾期，逾期天数："+overdueDays+"，逾期本息："+unpaidAmt);
		
		
		if(overdueDays > 90 && oldOverdueDays <= 90){
			// 严重逾期
			this.creditReportBS.addSeriousOverdueNum(custId, 1);
		}
		
		//上次不逾期，
		if(!bizArrearsDetailBO.getArrearsFlagCd().equals(AppConst.ARREARS_FLAG_CD_YQ)){
				this.creditReportBS.addOverdueNum(custId, 1);
				this.creditReportBS.addOverdueAmt(custId, unpaidAmt);
		}
		log.info("overdueDays==="+overdueDays+"还款计划id:"+bizArrearsDetailBO.getBizRepayPlanDetailBO().getId()+"期数："+bizArrearsDetailBO.getBizRepayPlanDetailBO().getPeriod());
		bizArrearsDetailBO.setOverdueDays(overdueDays);//逾期天数
		
		//延期罚息 = 未还本金*延期利率*延期天数/360
		BigDecimal ZQPenaltyAmt = unpaidAmt.multiply(bizArrearsDetailBO.getDelayRate().multiply(new BigDecimal(bizArrearsDetailBO.getDelayDays())));
		//逾期罚息 = 未还本金*逾期利率*逾期天数/360
		BigDecimal YQPenaltyAmt = unpaidAmt.multiply(bizArrearsDetailBO.getOverdueRate().multiply(new BigDecimal(bizArrearsDetailBO.getOverdueDays())));
		bizArrearsDetailBO.setUnpaidPenaltyAmt((ZQPenaltyAmt.add(YQPenaltyAmt)).divide(AppConst.BASEINTDAY, AppConst.DECIMAL_AMT_PRECISION, BigDecimal.ROUND_HALF_UP));
		bizArrearsDetailBO.setArrearsFlagCd(AppConst.ARREARS_FLAG_CD_YQ);//逾期
		bizArrearsDetailBO.setLastInterestDate(batchDate);//上次计息日期
		loanDAO.update(bizArrearsDetailBO);
			
		
		
	    //更新借款信息
		bizLoanBO.setTtlPenaltyAmt(bizLoanBO.getTtlPenaltyAmt().add(ZQPenaltyAmt.add(YQPenaltyAmt)));
		bizLoanBO.setLoanStatusCd(AppConst.LOAN_STATUS_CD_YQ);
		loanDAO.update(bizLoanBO);
		
		//更新债权信息
		List<?> bizCreditorRightBOs = this.loanDAO.findByHQL("From BizCreditorRightBO where loanId = ?", new Object[]{bizLoanBO.getId()});
		for (Object object : bizCreditorRightBOs) {
			BizCreditorRightBO bizCreditorRightBO = (BizCreditorRightBO)object;
			bizCreditorRightBO.setCrStatusCd(AppConst.CR_STATUS_CD_OVERDUE);//更新状态为逾期
			updateOperator(bizCreditorRightBO);
			loanDAO.update(bizCreditorRightBO);
		}
		
		
		
		//短信提醒overdue  平台账号${accno}有一笔${amt}元还款，还款日：${date}已逾期，请及时还款，详咨XXXXXXXXXXXX
		try {
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("accno", transferAccountsBS.getAccountBO(bizLoanBO.getCustId()).getAccount());
			dataMap.put("amt", unpaidAmt);
			dataMap.put("date", bizArrearsDetailBO.getRepayPlanDate());
			BizCustomerBO bizCustomerBO = custInfoBS.findCustById(bizLoanBO.getCustId());
			msgTemplateBS.sendMsg("overdue", dataMap, null, bizCustomerBO.getMobileTelephone());
		} catch (Exception e) {
			 
			log.error("短信信息发送失败:"+e.getMessage());
		}
		
		return ZQPenaltyAmt.add(YQPenaltyAmt);
	}
	
	/**
	 * 计算提取风险准备金
	 * @param loanAmt
	 * @return
	 * @throws BizException
	 */
	private BigDecimal calculateFXZBJ(BigDecimal loanAmt) throws BizException{
		String param =  paramBS.getParam(AppConst.PARAMETER_CODE_FXZBJBL);
		if(param==null || "".equals(param))
			throw new BizException("平台[风险准备金比例]未设置");
		BigDecimal fxzbjbl = new BigDecimal(param);
		return (loanAmt.multiply(fxzbjbl)).setScale(AppConst.DECIMAL_AMT_PRECISION, BigDecimal.ROUND_HALF_UP); //准备金提前公式divide(BigDecimal.ONE,AppConst.DECIMAL_AMT_PRECISION, BigDecimal.ROUND_HALF_UP)
	}
	
	/**
	 * 计算逾期年利率
	 * @param loanRate
	 * @return
	 * @throws BizException
	 */
	private BigDecimal getOverdueRate(BigDecimal loanRate) throws BizException{
		String param =  paramBS.getParam(AppConst.PARAMETER_CODE_YQSFBL);
		if(param==null || "".equals(param))
			throw new BizException("平台[逾期利率上浮比例]未设置");
		BigDecimal overDueRate = new BigDecimal(param);
		return loanRate.multiply(BigDecimal.ONE.add(overDueRate));
	}
	
	/**
	 * 计算延期年利率
	 * @param loanRate
	 * @return
	 * @throws BizException
	 */
	private BigDecimal getDelayRate(BigDecimal loanRate) throws BizException{
		String param =  paramBS.getParam(AppConst.PARAMETER_CODE_ZQSFBL);
		if(param==null || "".equals(param))
			throw new BizException("平台[展期利率上浮比例]未设置");
		BigDecimal delayRate = new BigDecimal(param);
		return loanRate.multiply(BigDecimal.ONE.add(delayRate));
	}
	
	private BizLoanBO assemBizLoanBO(BizLoanApproveBO bizLoanApproveBO) throws BizException {
		BizLoanBO bizLoanBO = new BizLoanBO();
		if(bizLoanApproveBO.getPaymentWayCd()==null){
			throw new BizException("支付方式[PaymentWayCd]不能为空");
		} 
		if (AppConst.PAYMENT_WAY_CD_TRUSTEE.equals(bizLoanApproveBO.getPaymentWayCd())){
			if(bizLoanApproveBO.getTrusteeCustId()==null) throw new BizException("支付方式为受托支付时,[TrusteeCustId]不能为空");
			bizLoanBO.setPaymentWayCd(bizLoanApproveBO.getPaymentWayCd());//支付方式
			bizLoanBO.setTrusteeCustId(bizLoanApproveBO.getTrusteeCustId());//受托人
		}else if(AppConst.PAYMENT_WAY_CD_SELF.equals(bizLoanApproveBO.getPaymentWayCd())){
			bizLoanBO.setPaymentWayCd(bizLoanApproveBO.getPaymentWayCd());//支付方式
			bizLoanBO.setTrusteeCustId(null);//受托人
		}else{
			throw new BizException("支付方式[PaymentWayCd]不能识别");
		}
		String contractNum = AppConst.CONTRACTNUM_NS
				+DateUtils.getDateString("yyyyMMdd",new Date())+RandomStringUtils.randomNumeric(3);
		bizLoanBO.setLoanRate(bizLoanApproveBO.getLoanRate());
		bizLoanBO.setBizLoanApproveBO(bizLoanApproveBO);//借款发布信息
		bizLoanBO.setComment(bizLoanApproveBO.getRemark());//备注
		bizLoanBO.setContractNum(contractNum);//合同编号
		bizLoanBO.setCurrencyCd(AppConst.CURRENCY_CD_CNY);//币别
		bizLoanBO.setCustId(bizLoanApproveBO.getCustId());//会员ID
		bizLoanBO.setCycleUnitCd(AppConst.TERM_UNIT_CD_MONTH);//周期单位,默认月
		bizLoanBO.setDelayRate(getDelayRate(bizLoanApproveBO.getLoanRate()));//延期年利率  取值平台统一设置参数
		bizLoanBO.setEarlyRepayAmt(BigDecimal.ZERO);//提前还款金额
		bizLoanBO.setEarlyRepayDate(null);//提前还款日期
		bizLoanBO.setEarlyRepayReason(null);//提前款原因
		bizLoanBO.setExchangeRate(BigDecimal.ONE);//汇率
		bizLoanBO.setLoanAmt(bizLoanApproveBO.getTenderUseAmt());//贷款金额
		bizLoanBO.setLoanBalAmt(bizLoanBO.getLoanAmt());//贷款余额
		bizLoanBO.setLoanDate(ViewOper.getOperTime());//贷款日期
		if(AppConst.TERM_UNIT_CD_DATE.equals(bizLoanApproveBO.getTermUnitCd())){
			bizLoanBO.setLoanDueDate(DateUtils.addDay(ViewOper.getOperTime(), bizLoanApproveBO.getLoanTerm()));
		}else if(AppConst.TERM_UNIT_CD_MONTH.equals(bizLoanApproveBO.getTermUnitCd())){
			bizLoanBO.setLoanDueDate(DateUtils.addMonth(ViewOper.getOperTime(), bizLoanApproveBO.getLoanTerm()));
		}else if(AppConst.TERM_UNIT_CD_YEAR.equals(bizLoanApproveBO.getTermUnitCd())){
			bizLoanBO.setLoanDueDate(DateUtils.addYear(ViewOper.getOperTime(), bizLoanApproveBO.getLoanTerm()));
		}else{
			throw new BizException("借款期限单位错误[年,月,日]");
		}
		bizLoanBO.setLoanName(bizLoanApproveBO.getLoanName());
		bizLoanBO.setLoanStatusCd(AppConst.LOAN_STATUS_CD_WJQ);//借款状态:未结清
		bizLoanBO.setLoanTerm(bizLoanApproveBO.getLoanTerm());
		bizLoanBO.setOverdueRate(getOverdueRate(bizLoanApproveBO.getLoanRate()));//逾期年利率  取值平台统一设置参数
		bizLoanBO.setRepayCycle(1);//还款周期,默认一个月
		bizLoanBO.setRepayEndWay(AppConst.REPAY_WAY_CD_CS);//初始化状态
		bizLoanBO.setRepayTypeCd(bizLoanApproveBO.getRepayTypeCd());//还款方式
		bizLoanBO.setTotalPeriod(0);//总期数
		bizLoanBO.setSurplusPeriod(0);//剩余期数
		bizLoanBO.setRepayedPeriod(0);//已还期数
		bizLoanBO.setTtlPrincipalInterestAmt(BigDecimal.ZERO);//借款总本息
		bizLoanBO.setTtlInterestAmt(BigDecimal.ZERO);//借款总利息
		bizLoanBO.setTtlPenaltyAmt(BigDecimal.ZERO);//累计产生罚息
		bizLoanBO.setTtlPlatRepayedAmt(BigDecimal.ZERO);//累计平台垫付 
		bizLoanBO.setTtlRepayedAmt(BigDecimal.ZERO);//累计已还款金额
		bizLoanBO.setWorkItemId("0");
		bizLoanBO.setProductId(bizLoanApproveBO.getProductId());
		bizLoanBO.setProductName(bizLoanApproveBO.getProductName());
		bizLoanBO.setLoanTypeCd(bizLoanApproveBO.getLoanTypeCd());
		createOperator(bizLoanBO);
		updateOperator(bizLoanBO);
		return bizLoanBO;
	}
	@Override
	public MyCreditorVO getProtocolInfo(Integer loanApproveId) throws BizException {
		MyCreditorVO myCreditorVO = new MyCreditorVO();
		StringBuffer str = new StringBuffer();
		str.append(" select bl.CONTRACT_NUM,");//合同
		str.append(" bl.ID,");
		str.append(" bl.SYS_CREATE_TIME as CREAT_DATE,");// 日期
		str.append(" c.CUST_NAME as LOAN_CUST_NAME,");// 贷款人姓名
		str.append(" c.CERTIFICATE_NUM,");// 贷款人身份证号
		str.append(" bl.LOAN_AMT,");//借款金额
		str.append(" bl.LOAN_TERM,");//借款期限
		str.append(" bl.LOAN_RATE*100 as LOAN_RATE,");// 年利率
		str.append(" date_format(bl.LOAN_DATE,'%Y-%m-%d') as LOAN_DATE,");//开始日期
		str.append(" date_format(bl.LOAN_DUE_DATE,'%Y-%m-%d') as LOAN_DUE_DATE,");//到期日期
		str.append(" bl.LOAN_DATE as START_LOAN_DATE,");
		str.append(" bl.TTL_PRINCIPAL_INTEREST_AMT,");//总还本息
		str.append(" la.LOAN_PURPOSE,");//贷款用途
		str.append(" bl.REPAY_TYPE_CD");// 还款方式
		str.append(" from biz_loan bl,biz_customer c,biz_loan_approve la");
		str.append(" where  bl.CUST_ID = c.ID and la.ID = bl.LOAN_APPROVE_ID");
		str.append(" and la.ID = ").append(loanApproveId);
		
		List<MyCreditorVO> list = this.getCommonQuery().findObjects(str.toString(), MyCreditorVO.class);
		if (list != null && list.size() > 0) {
			 myCreditorVO = (MyCreditorVO)list.get(0);
		}
		return myCreditorVO;
	}

	@Override
	public Map findLoanApproveByWorkItemId(String workItemId) throws BizException{
		String sql = "SELECT LA.* FROM BIZ_LOAN_APPROVE LA WHERE LA.WORK_ITEM_ID = ?";
		List datas = getCommonQuery().findBySQL(sql, new String[]{workItemId});
		if(CollectionUtil.isEmpty(datas)){
			throw new BizException("查询不到放款申请");
		}else{
			return (Map) datas.get(0);
		}
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void completeLoan(Integer loanApproveId,boolean isAgree,String taskId,SysUser curAuditUser,SysRole auditRole,String comment,String html) throws BizException{
		try {
			Map<String, Object> paraMap = new HashMap();
			paraMap.put(JbpmConst.CUR_AUDIT_USER, curAuditUser);
			paraMap.put(JbpmConst.AUDIT_ROLE,auditRole);
			paraMap.put(JbpmConst.FLOW_HTML, html);
			if(isAgree){
				effectLoan(loanApproveId);
				paraMap.put(JbpmConst.AUDIT_STATUS_CD,JbpmConst.AUDIT_STATUS_CD_YES);
				flowBS.takeAndCompleteTask(taskId, "通过",comment, paraMap);
			}else{
				invalidLoan(loanApproveId);
				paraMap.put(JbpmConst.AUDIT_STATUS_CD,JbpmConst.AUDIT_STATUS_CD_REVOKE);
				flowBS.takeAndCompleteTask(taskId, "不通过",comment, paraMap);
			}
		} catch (Exception e) {
			log.error("放款申请异常：",e);
			throw new BizException("放款申请异常："+e.getMessage());
		}
	}


	public IRecommendationBS getRecommendationBS() {
		return recommendationBS;
	}


	public void setRecommendationBS(IRecommendationBS recommendationBS) {
		this.recommendationBS = recommendationBS;
	}

}
