package com.qfw.bizservice.repay.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.qfw.bizservice.repay.IRepayBS;
import com.qfw.common.bizservice.IMsgTemplateBS;
import com.qfw.common.bizservice.IParamBS;
import com.qfw.common.bizservice.ISeqBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.CollectionUtil;
import com.qfw.common.util.DateUtils;
import com.qfw.common.util.StringUtils;
import com.qfw.common.util.web.ViewOper;
import com.qfw.dao.repay.IRepayDAO;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizAccountBO;
import com.qfw.model.bo.BizArrearsDetailBO;
import com.qfw.model.bo.BizCostBO;
import com.qfw.model.bo.BizCreditorRightBO;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.bo.BizLoanBO;
import com.qfw.model.bo.BizRepayDetailBO;
import com.qfw.model.bo.BizRepayPlanDetailBO;
import com.qfw.model.vo.charge.ChargeVO;
import com.qfw.model.vo.credit.RequestCreditVO;
import com.qfw.model.vo.repay.RepayInfoVO;
import com.qfw.model.vo.repay.RepayPlanDetailVO;

@Service("repayBS")
public class RepayBSImpl extends BaseServiceImpl implements IRepayBS {
	private Logger log = LogFactory.getInstance().getBusinessLogger();

	@Autowired
	private IRepayDAO repayDAO;
	@Autowired
	private ITransferAccountsBS transferAccountsBS;
	@Autowired
	private ISeqBS seqBS;
	@Autowired
	private IChargeBS chargeBS;
	@Autowired
	private IParamBS paramBS;
	@Autowired
	private ICreditReportBS creditReportBS;
	@Autowired
	private IMsgTemplateBS msgTemplateBS;
	@Autowired
	private ICustInfoBS custInfoBS;
	@Autowired
	private ICreditBS creditBS;
	
	@Override
	public List<BizRepayPlanDetailBO> genRepayPlanDetail(RepayPlanDetailVO repayPlanDetailVO) throws BizException {
		if(repayPlanDetailVO == null) throw new BizException("参数[RepayPlanDetailVO]不能为空");
		RepayPlanService repayPlanService = new  RepayPlanService(repayPlanDetailVO);
		List<BizRepayPlanDetailBO> bizRepayPlanDetailBOs = null;
		if(AppConst.REPAY_TYPE_CD_DEBJ.equals(repayPlanDetailVO.getRepayTypeCD())){//等额本金
			bizRepayPlanDetailBOs =  repayPlanService.debjCalCulate();
		}else if(AppConst.REPAY_TYPE_CD_DEBX.equals(repayPlanDetailVO.getRepayTypeCD())){//等额本息
			bizRepayPlanDetailBOs =  repayPlanService.debxCalCulate();
		}else if(AppConst.REPAY_TYPE_CD_LSBQ.equals(repayPlanDetailVO.getRepayTypeCD())){//利随本清 
			bizRepayPlanDetailBOs =  repayPlanService.lsbqCalCulate();
		}else if(AppConst.REPAY_TYPE_CD_ZQFX.equals(repayPlanDetailVO.getRepayTypeCD())){//周期付息
			bizRepayPlanDetailBOs =  repayPlanService.zqfxCalCulate();
		}else{
			throw new BizException("参数[repayPlanDetailVO.getRepayTypeCD()]无法识别!");
		}
		repayPlanDetailVO.setBizRepayPlanDetailBOs(bizRepayPlanDetailBOs);
		repayPlanDetailVO.setTtlInterestAmt(repayPlanService.getTtlIntAmt());
		repayPlanDetailVO.setTtlPrincipalInterestAmt(repayPlanService.getTtlPalAmt().add(repayPlanService.getTtlIntAmt()));
		repayPlanDetailVO.setFirstRepayDate(repayPlanService.getFirstRepayDate());
		return bizRepayPlanDetailBOs;
	}

	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public RepayPlanDetailVO saveRepayPlanDetail(RepayPlanDetailVO repayPlanDetailVO) throws BizException {
		if(repayPlanDetailVO == null) throw new BizException("参数[RepayPlanDetailVO]不能为空");
		List<BizRepayPlanDetailBO> repayPlanDetails = null;//还款计划明细表
		BizRepayDetailBO repayDetailBO = null;
		BizArrearsDetailBO arrearsDetailBO = null;
		BizLoanBO bizLoanBO = null;
		
		if(repayPlanDetailVO.getBizLoanBO()==null){
			bizLoanBO = (BizLoanBO) repayDAO.findById(BizLoanBO.class, repayPlanDetailVO.getLoanId());
			repayPlanDetailVO.setBizLoanBO(bizLoanBO);
		}else{
			bizLoanBO = repayPlanDetailVO.getBizLoanBO();
		}
		
		if(repayPlanDetailVO.getBizLoanBO() == null)  throw new BizException("获取不到bizLoanBO");
		Set<BizRepayPlanDetailBO> checkData = bizLoanBO.getBizRepayPlanDetailBOs();
		if(checkData!=null&&checkData.size()>0){
			throw new BizException("此还款计划已存在,请与管理员联系!");
		}
		
		repayPlanDetails = genRepayPlanDetail(repayPlanDetailVO);
		
		
		for(BizRepayPlanDetailBO repayPlanDetailBO : repayPlanDetails){
			//保存还款计划
			repayPlanDetailBO.setCustId(repayPlanDetailVO.getCustId());
			repayPlanDetailBO.setBizLoanBO(bizLoanBO);
			repayPlanDetailBO.setBizLoanApproveBO(bizLoanBO.getBizLoanApproveBO());
			repayPlanDetailBO.setWorkItemId("0");
			createOperator(repayPlanDetailBO);
			updateOperator(repayPlanDetailBO);
			repayDAO.save(repayPlanDetailBO);
			
			
			// 初始化欠款明细表
			arrearsDetailBO = new BizArrearsDetailBO();
			arrearsDetailBO.setBizLoanBO(bizLoanBO);
			arrearsDetailBO.setBizRepayPlanDetailBO(repayPlanDetailBO);
			arrearsDetailBO.setCustId(repayPlanDetailVO.getCustId());
			arrearsDetailBO.setBizLoanApproveBO(bizLoanBO.getBizLoanApproveBO());
			arrearsDetailBO.setPeriod(repayPlanDetailBO.getPeriod());
			arrearsDetailBO.setRepayPlanDate(repayPlanDetailBO.getRepayplanDate());
			String kxqts = paramBS.getParam(AppConst.PARAMETER_CODE_KXQTS);
			if(kxqts == null) throw new BizException("平台[宽限期天数]参数未设置");
			arrearsDetailBO.setGraceDays(Integer.valueOf(kxqts));
			arrearsDetailBO.setDelayDays(0);
			arrearsDetailBO.setOverdueDays(0);
			arrearsDetailBO.setRepayDate(null);
			arrearsDetailBO.setDelayRate(bizLoanBO.getDelayRate());
			arrearsDetailBO.setOverdueRate(bizLoanBO.getOverdueRate());
			arrearsDetailBO.setUnpaidPrincipalAmt(repayPlanDetailBO.getPrincipalAmt());//未还本金
			arrearsDetailBO.setUnpaidInterestAmt(repayPlanDetailBO.getInterestAmt());//未还利息
			arrearsDetailBO.setUnpaidPenaltyAmt(BigDecimal.ZERO);
			arrearsDetailBO.setArrearsFlagCd(AppConst.ARREARS_FLAG_CD_ZC);//正常
			arrearsDetailBO.setLastInterestDate(DateUtils.addDay(repayPlanDetailBO.getRepayplanDate(),-1));
			arrearsDetailBO.setArrearsStatusCd(AppConst.ARREARS_STATUS_CD_WH);//正常未还
			arrearsDetailBO.setWorkItemId("0");
			createOperator(arrearsDetailBO);
			updateOperator(arrearsDetailBO);
			repayDAO.save(arrearsDetailBO);
			
			
			// 初始化还款明细表
			repayDetailBO = new BizRepayDetailBO();
			repayDetailBO.setBizLoanBO(bizLoanBO);
			repayDetailBO.setBizRepayPlanDetailBO(repayPlanDetailBO);
			repayDetailBO.setBizArrearsDetailBO(arrearsDetailBO);
			repayDetailBO.setCustId(repayPlanDetailVO.getCustId());
			repayDetailBO.setBizLoanApproveBO(bizLoanBO.getBizLoanApproveBO());
			repayDetailBO.setRepayDate(null);
			repayDetailBO.setPeriod(repayPlanDetailBO.getPeriod());
			repayDetailBO.setRepayedPrincipalAmt(BigDecimal.ZERO);
			repayDetailBO.setRepayedInterestAmt(BigDecimal.ZERO);
			repayDetailBO.setRepayedPenaltyAmt(BigDecimal.ZERO);
			repayDetailBO.setTtlRepayedAmt(BigDecimal.ZERO);
			repayDetailBO.setPlatRepayedPrincipalAmt(BigDecimal.ZERO);
			repayDetailBO.setPlatRepayedInterestAmt(BigDecimal.ZERO);
			repayDetailBO.setPlatRepayedPenaltyAmt(BigDecimal.ZERO);
			repayDetailBO.setPlatRepayedAmt(BigDecimal.ZERO);
			repayDetailBO.setPlatRepayedDate(null);
			repayDetailBO.setPlatRepayedFlagCd(AppConst.PLAT_REPAYED_FLAG_CD_NO);
			repayDetailBO.setRepayStatusCd(AppConst.REPAY_STATUS_CD_WH);
			repayDetailBO.setRepayWayCd(AppConst.REPAY_WAY_CD_CS);
			repayDetailBO.setComment(null);
			repayDetailBO.setWorkItemId("0");
			repayDetailBO.setIsIncomeCd(AppConst.IS_INCOME_CD_NO);
			repayDetailBO.setUnIncomePrincipalAmt(BigDecimal.ZERO);
			repayDetailBO.setUnIncomeInterestAmt(BigDecimal.ZERO);
			repayDetailBO.setUnIncomePenaltyAmt(BigDecimal.ZERO);
			repayDetailBO.setTtlIncomePrincipalAmt(BigDecimal.ZERO);
			repayDetailBO.setTtlIncomeInterestAmt(BigDecimal.ZERO);
			repayDetailBO.setTtlIncomePenaltyAmt(BigDecimal.ZERO);
			createOperator(repayDetailBO);
			updateOperator(repayDetailBO);
			repayDAO.save(repayDetailBO);
			
			
			/*delete by yangjj
			 * //生成借款费用
			//借款管理费收取=借款本金*管理费率
			ChargeVO chargeVO = new ChargeVO();
			chargeVO.setCustId(repayPlanDetailBO.getCustId());//会员ID
			chargeVO.setCostTypeCd(AppConst.COST_TYPE_CD_JKFY);//借款管理费
			chargeVO.setCostBasicAmt(bizLoanBO.getLoanAmt());//借款本金
			chargeVO.setRelateId(repayPlanDetailBO.getId());//关联还款计划信息表ID
			chargeVO.setRelateTypeCd(AppConst.RELATE_TYPE_CD_REPAYPLAN);//还款信息表ID
			chargeBS.genCharge(chargeVO);*/
		}
		// mod by yangjj
		  //生成借款费用
			//借款管理费收取=借款本金*管理费率
			ChargeVO chargeVO = new ChargeVO();
			chargeVO.setCustId(bizLoanBO.getCustId());//会员ID
			chargeVO.setCostTypeCd(AppConst.COST_TYPE_CD_JKFY);//借款管理费
			chargeVO.setCostBasicAmt(bizLoanBO.getLoanAmt());//借款本金
			chargeVO.setRelateId(bizLoanBO.getId());//关联还款计划信息表ID
			chargeVO.setRelateTypeCd(AppConst.RELATE_TYPE_CD_LOAN);//还款信息表ID
			chargeVO.setLoanTerm(bizLoanBO.getLoanTerm());
			chargeBS.genCharge(chargeVO);
		return repayPlanDetailVO;
	}
	
	
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void repayForFullDh(String repayWayCD ,Integer loanId,Integer repayPlanId,Integer custId) throws BizException{
		RepayInfoVO repayInfoVO = repayForTrial(repayWayCD,loanId,repayPlanId);
		BigDecimal repAmt = repayInfoVO.getTtlRepayedAmt();
		//System.out.println("loanId=="+loanId+";repayPlanId=="+repayPlanId +";还款总金额=="+repAmt);
		BizAccountBO bizAccountBO= transferAccountsBS.getAccountBO(custId);
		//System.out.println("还款金额=="+repAmt+"账户余额比较大小=="+repAmt.compareTo(bizAccountBO.getUsableBalAmt()));
		if(repAmt.compareTo(bizAccountBO.getUsableBalAmt()) > 0){
			 throw new BizException("账户余额不足:【"+repAmt+"】，无法还款！");
		}
		String txNO =  seqBS.getResultNum(AppConst.TXNO);//获取交易编号
		
		BizLoanBO bizLoanBO = (BizLoanBO)repayDAO.findById(BizLoanBO.class, loanId);//获取借款信息表
		//System.out.println("bizLoanBO.getCustId()=="+bizLoanBO.getCustId());
		/**
		 * 第三方公司代还时，先将钱转到借款人账户，在从借款人账户直接还款
		 */
		//转账服务(适用于会员之间转账) 
		transferAccountsBS.transferAccount(txNO,AppConst.TRADE_TYPE_ZHBX,custId,bizLoanBO.getCustId(),repAmt);
		
		//还款
		repayForFull(repayWayCD,loanId,repayPlanId);
		
	}
	
	
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void repayForFull(String repayWayCD,Integer loanId,Integer repayPlanId) throws BizException {
		if(StringUtils.isEmpty(repayWayCD))  throw new BizException("参数[repayWayCD]不能为空");
		if(loanId == null) throw new BizException("参数[loanId]不能为空");
		BizLoanBO bizLoanBO = (BizLoanBO)repayDAO.findById(BizLoanBO.class, loanId);//获取借款信息表
		if(bizLoanBO == null) throw new BizException("获取不到借款信息,[loanId]="+loanId);
		
		String txNO =  seqBS.getResultNum(AppConst.TXNO);//获取交易编号
		BigDecimal fullAmt = BigDecimal.ZERO;
		BigDecimal PiAmt = BigDecimal.ZERO;// add by wushanhu
		BigDecimal repayCreditAmt = BigDecimal.ZERO;//额度恢复金额，即还额度金额
		BigDecimal repayAmtForCredit = BigDecimal.ZERO;// 还款金额中，还额度的金额
		
		BigDecimal overDueRepayAmt = BigDecimal.ZERO;// 逾期信息还款金额:包括本金、利息
		
		if(AppConst.REPAY_WAY_CD_ZC.equals(repayWayCD)){ //正常还款,只能还当期
			if(repayPlanId==null) throw new BizException("参数[repayPlanId]不能为空");
			BizRepayPlanDetailBO bizRepayPlanDetailBO = (BizRepayPlanDetailBO) repayDAO.findById(BizRepayPlanDetailBO.class, repayPlanId);//获取当期还款计划表
			
			//控制不能跨期还款,判断上一期是否已经结清,如果还没结清,提示从上一期开始还款
			if(bizRepayPlanDetailBO.getPeriod()!=1){//第一期不用校验
				String chksql = "SELECT SUM(A.UNPAID_INTEREST_AMT+A.UNPAID_PENALTY_AMT+A.UNPAID_PRINCIPAL_AMT) UNPAID FROM BIZ_ARREARS_DETAIL A WHERE A.REPAY_PLAN_DETAIL_ID IN (SELECT ID FROM BIZ_REPAY_PLAN_DETAIL RP WHERE RP.CUST_ID=? AND RP.LOAN_ID=? AND RP.PERIOD < ? )";
				List<?> chkList = this.commonQuery.findBySQL(chksql,new Object[]{bizRepayPlanDetailBO.getCustId(),bizLoanBO.getId(),bizRepayPlanDetailBO.getPeriod()});
				if(chkList!=null && chkList.size()>0){
					BigDecimal unpaidAmt =  (BigDecimal)((Map<?,?>)chkList.get(0)).get("UNPAID");
					if(unpaidAmt!=null && unpaidAmt.compareTo(BigDecimal.ZERO)>0){
						throw new BizException("上一期还存在欠款,请先结清上一期");
					}
				}
			}
			//判断是否当前期数是否允许还款,判断规则为,当前日期+当月自然日 <计划还款日
			if(DateUtils.addDay(ViewOper.getOperTime(), DateUtils.getActualMaximum()).compareTo(bizRepayPlanDetailBO.getRepayplanDate())<0){
				throw new BizException("当前期数:["+bizRepayPlanDetailBO.getPeriod()+"]，还款日["+bizRepayPlanDetailBO.getRepayplanDate()+"]未到不允许进行还款操作");
			}
			
			BizArrearsDetailBO bizArrearsDetailBO = bizRepayPlanDetailBO.getBizArrearsDetailBO();//欠款明细表
			//本金+利息+罚息
			fullAmt = bizArrearsDetailBO.getUnpaidPrincipalAmt().add(bizArrearsDetailBO.getUnpaidInterestAmt()).add(bizArrearsDetailBO.getUnpaidPenaltyAmt());
			//本金+利息  add by wushanhu
			PiAmt=bizArrearsDetailBO.getUnpaidPrincipalAmt().add(bizArrearsDetailBO.getUnpaidInterestAmt());
			
			if(fullAmt.compareTo(BigDecimal.ZERO)<=0) throw new BizException("本期欠款已结清,不能重复还款");
			
			if(log.isInfoEnabled()) log.info("本次正常还款金额为="+fullAmt.toString());
			
			/**扣收借款管理费*/
			//mod by yangjj 借款管理费改成关联借款id 
//			List<?> bizCostBOs = repayDAO.findByHQL("From BizCostBO Where custId =? and relateId=? and relateTypeCd=? and costTypeCd=? and costStatusCd=?",
//					new Object[]{bizRepayPlanDetailBO.getCustId(),bizRepayPlanDetailBO.getId(),AppConst.RELATE_TYPE_CD_REPAYPLAN,AppConst.COST_TYPE_CD_JKFY,AppConst.COST_STATUS_CD_WKF});
			List<?> bizCostBOs = repayDAO.findByHQL("From BizCostBO Where custId =? and relateId=? and relateTypeCd=? and costTypeCd=? and costStatusCd=?",
					new Object[]{bizRepayPlanDetailBO.getCustId(),bizLoanBO.getId(),AppConst.RELATE_TYPE_CD_LOAN,AppConst.COST_TYPE_CD_JKFY,AppConst.COST_STATUS_CD_WKF});
			if(CollectionUtil.isNotEmpty(bizCostBOs)){
				BizCostBO bizCostBO = (BizCostBO)bizCostBOs.get(0);
				chargeBS.deductCharge(txNO, bizCostBO);
			}
			
			/**生成并扣收展期、逾期管理费*/
			ChargeVO chargeVO = new ChargeVO();
			chargeVO.setCustId(bizRepayPlanDetailBO.getCustId());//会员ID
			chargeVO.setRelateId(bizRepayPlanDetailBO.getId());//关联还款计划信息表ID
			chargeVO.setRelateTypeCd(AppConst.RELATE_TYPE_CD_REPAYPLAN);//还款信息表ID
			chargeVO.setTxNO(txNO);//交易编号
			chargeVO.setLoanTerm(bizLoanBO.getLoanTerm());
			String tradeType = null;
			if(AppConst.ARREARS_FLAG_CD_ZC.equals(bizArrearsDetailBO.getArrearsFlagCd())){
				tradeType = AppConst.TRADE_TYPE_HK;
			}else if(AppConst.ARREARS_FLAG_CD_ZQ.equals(bizArrearsDetailBO.getArrearsFlagCd())){//展期,收取展期管理费=当期未还本息*管理费率*天数
				tradeType = AppConst.TRADE_TYPE_ZQHK;
				chargeVO.setCostTypeCd(AppConst.COST_TYPE_CD_ZQFY);//展期管理费
				chargeVO.setCostBasicAmt(PiAmt.multiply(new BigDecimal(bizArrearsDetailBO.getDelayDays())));//未还本息*展期天数
				chargeBS.genAndDeductCharge(chargeVO);
			}else if(AppConst.ARREARS_FLAG_CD_YQ.equals(bizArrearsDetailBO.getArrearsFlagCd())){//逾期,收取逾期管理费=当期未还本息*管理费率*天数
				tradeType = AppConst.TRADE_TYPE_ZQHK;
				chargeVO.setCostTypeCd(AppConst.COST_TYPE_CD_ZQFY);//展期管理费
				chargeVO.setCostBasicAmt(PiAmt.multiply(new BigDecimal(bizArrearsDetailBO.getDelayDays())));//未还本息*展期天数
				chargeBS.genAndDeductCharge(chargeVO);
				
				tradeType = AppConst.TRADE_TYPE_YQHK;
				chargeVO.setCostTypeCd(AppConst.COST_TYPE_CD_YQFY);//逾期管理费
				chargeVO.setCostBasicAmt(PiAmt.multiply(new BigDecimal(bizArrearsDetailBO.getOverdueDays())));//未还本息*逾期天数
				chargeBS.genAndDeductCharge(chargeVO);
			}
			//调用账户转账服务,该服务要提供同步控制,传入【fullAmt】,【借款人账户】转入【平台还款账户】
			transferAccountsBS.transferAccount(txNO,tradeType, 
					transferAccountsBS.getAccountBO(bizRepayPlanDetailBO.getCustId()), 
					transferAccountsBS.getPMAccountBO(AppConst.ACCOUNT_TYPE_PMHK), fullAmt);
			
			//用于判断是否属于平台垫款后还款,以确定资金流向
			if(AppConst.ARREARS_STATUS_CD_PTDF.equals(bizArrearsDetailBO.getArrearsStatusCd())){//如果是平台垫付
				// 调用账户转账服务,该服务要提供同步控制,传入【fullAmt】,【平台还款账户】转入【风险保证金账户】。
				transferAccountsBS.transferAccount(txNO,AppConst.TRADE_TYPE_GHFXZBJ, 
						transferAccountsBS.getPMAccountBO(AppConst.ACCOUNT_TYPE_PMHK), 
						transferAccountsBS.getPMAccountBO(AppConst.ACCOUNT_TYPE_PMFXZBJ), fullAmt);
			}
			
			// 额度恢复金额 
			repayCreditAmt = bizRepayPlanDetailBO.getPrincipalAmt();
			repayAmtForCredit = bizRepayPlanDetailBO.getPrincipalAmt();
			
			if(AppConst.ARREARS_FLAG_CD_YQ.equals(bizArrearsDetailBO.getArrearsFlagCd())){
				overDueRepayAmt = overDueRepayAmt.add(PiAmt);
			}
			
			//处理还款明细表
			BizRepayDetailBO bizRepayDetailBO = bizRepayPlanDetailBO.getBizRepayDetailBO();//还款明细表
			bizRepayDetailBO.setRepayDate(ViewOper.getOperTime());
			bizRepayDetailBO.setRepayedPrincipalAmt(bizRepayPlanDetailBO.getPrincipalAmt());//当期已还本金
			bizRepayDetailBO.setRepayedInterestAmt(bizRepayPlanDetailBO.getInterestAmt());//当期已还利息
			bizRepayDetailBO.setRepayedPenaltyAmt(bizArrearsDetailBO.getUnpaidPenaltyAmt());//当期已还罚息
			bizRepayDetailBO.setTtlRepayedAmt(fullAmt);//当期已还总金额
			bizRepayDetailBO.setRepayStatusCd(AppConst.REPAY_STATUS_CD_YH);//已还
			bizRepayDetailBO.setRepayWayCd(repayWayCD);//还款结清方式
			
			bizRepayDetailBO.setUnIncomePrincipalAmt(bizRepayPlanDetailBO.getPrincipalAmt());//可分配本金
			bizRepayDetailBO.setUnIncomeInterestAmt(bizRepayPlanDetailBO.getInterestAmt());//可分配利息
			bizRepayDetailBO.setUnIncomePenaltyAmt(bizArrearsDetailBO.getUnpaidPenaltyAmt());//可分配罚息
			bizRepayDetailBO.setTtlIncomePrincipalAmt(bizRepayDetailBO.getTtlIncomePrincipalAmt().add(bizRepayPlanDetailBO.getPrincipalAmt()));//总已分配本金
			bizRepayDetailBO.setTtlIncomeInterestAmt(bizRepayDetailBO.getTtlIncomeInterestAmt().add(bizRepayPlanDetailBO.getInterestAmt()));//总已分配利息
			bizRepayDetailBO.setTtlIncomePenaltyAmt(bizRepayDetailBO.getTtlIncomePenaltyAmt().add(bizArrearsDetailBO.getUnpaidPenaltyAmt()));//总已分配罚息
			updateOperator(bizRepayDetailBO);
			repayDAO.update(bizRepayDetailBO);
			
			//处理欠款明细表
			bizArrearsDetailBO.setRepayDate(ViewOper.getOperTime());
			bizArrearsDetailBO.setUnpaidInterestAmt(BigDecimal.ZERO);//未还利息
			bizArrearsDetailBO.setUnpaidPrincipalAmt(BigDecimal.ZERO);//未还本金
			bizArrearsDetailBO.setUnpaidPenaltyAmt(BigDecimal.ZERO);//未还罚息
			bizArrearsDetailBO.setArrearsStatusCd(AppConst.ARREARS_STATUS_CD_YH);//已还
			updateOperator(bizArrearsDetailBO);
			repayDAO.update(bizArrearsDetailBO);
		}else if(AppConst.REPAY_WAY_CD_TQ.equals(repayWayCD)){//提前还款,结清后面所有的钱
			Set<BizArrearsDetailBO>  bizArrearsDetailBOs =bizLoanBO.getBizArrearsDetailBOs();//欠款明细列表
			fullAmt = BigDecimal.ZERO;
			BigDecimal ptdfAmt = BigDecimal.ZERO;//还平台垫付的钱
			BigDecimal zchkAmt = BigDecimal.ZERO;//正常归还的钱
			BigDecimal tqhkAmt = BigDecimal.ZERO; //提前还款本金
			BigDecimal zqGLFAMT = BigDecimal.ZERO;//展期管理费金额
			BigDecimal yqGLFAMT = BigDecimal.ZERO;//逾期期管理费金额
			
			BigDecimal creditAmt = BigDecimal.ZERO;//正常归还的钱中更新额度的值
			
			//上个应还款日期
			Date lastRepayDate = null;
			Date bussDate = new Date();
			//还款频率单位
			String cycleUnitCd = bizLoanBO.getCycleUnitCd();
			//还款频率
			Integer repayCycle= bizLoanBO.getRepayCycle();
			
			
			/**计算还款金额*/
			List<Integer> repayPlanDetailIds = new ArrayList<Integer>();//用于存放需要扣费的还款计划ID
			
			for(BizArrearsDetailBO bizArrearsDetailBO : bizArrearsDetailBOs ){
				if(AppConst.REPAY_STATUS_CD_YH.equals(bizArrearsDetailBO.getArrearsStatusCd())){
					continue;
				}
				/*计算未还本金利息罚息金额*/
				BigDecimal unpaidAmt = bizArrearsDetailBO.getUnpaidInterestAmt().add(bizArrearsDetailBO.getUnpaidPrincipalAmt().add(bizArrearsDetailBO.getUnpaidPenaltyAmt()));
				/*计算未还本息罚金额*/
				BigDecimal unPIAmt = bizArrearsDetailBO.getUnpaidInterestAmt().add(bizArrearsDetailBO.getUnpaidPrincipalAmt());
				
				if(AppConst.REPAY_TYPE_CD_LSBQ.equals(bizArrearsDetailBO.getBizLoanBO().getRepayTypeCd())){
					if(bussDate.getTime() < bizArrearsDetailBO.getRepayPlanDate().getTime()){
						BigDecimal  lsbqPrincipalAmt = bizArrearsDetailBO.getUnpaidPrincipalAmt(); //利随本清本金
						BigDecimal lsbqInterestAmt = lsbqPrincipalAmt //本金
								.multiply(bizArrearsDetailBO.getBizRepayPlanDetailBO().getLoanRate()) //贷款利率
								.multiply(new BigDecimal(DateUtils.minuDay(bizArrearsDetailBO.getBizRepayPlanDetailBO().getStartInterestDate(), ViewOper.getOperTime())).add(new BigDecimal(1))) //贷款天数
								.divide(AppConst.BASEINTDAY, AppConst.DECIMAL_AMT_PRECISION, BigDecimal.ROUND_HALF_UP);//利随本清利息
						zchkAmt = zchkAmt.add(lsbqInterestAmt);
						tqhkAmt = tqhkAmt.add(lsbqPrincipalAmt);
						creditAmt = creditAmt.add(lsbqPrincipalAmt);
						//利随本清照收（借款管理费）
						repayPlanDetailIds.add(bizArrearsDetailBO.getBizRepayPlanDetailBO().getId());
					}else{
						throw new BizException("该笔借款已经到期，您无法提前还款"); 
					}
				}else {
					//用于判断是否属于平台垫款后还款,以确定资金流向
					if(AppConst.ARREARS_STATUS_CD_PTDF.equals(bizArrearsDetailBO.getArrearsStatusCd())){//如果是平台垫付
						ptdfAmt = ptdfAmt.add(unpaidAmt);
						repayPlanDetailIds.add(bizArrearsDetailBO.getBizRepayPlanDetailBO().getId());
						//System.out.println("平台垫付："+bizArrearsDetailBO.getBizRepayPlanDetailBO().getId()+":"+unpaidAmt);
						creditAmt = creditAmt.add(bizArrearsDetailBO.getUnpaidPrincipalAmt());
					}else{
						if(AppConst.ARREARS_FLAG_CD_ZQ.equals(bizArrearsDetailBO.getArrearsFlagCd())){//展期,还本息
							zchkAmt = zchkAmt.add(unpaidAmt);
							repayPlanDetailIds.add(bizArrearsDetailBO.getBizRepayPlanDetailBO().getId());
							//System.out.println("展期还款："+bizArrearsDetailBO.getBizRepayPlanDetailBO().getId()+":"+unpaidAmt);
							creditAmt = creditAmt.add(bizArrearsDetailBO.getUnpaidPrincipalAmt());
						}else if(AppConst.ARREARS_FLAG_CD_YQ.equals(bizArrearsDetailBO.getArrearsFlagCd())){//逾期,还本息
							zchkAmt = zchkAmt.add(unpaidAmt);
							repayPlanDetailIds.add(bizArrearsDetailBO.getBizRepayPlanDetailBO().getId());
							//System.out.println("逾期还款："+bizArrearsDetailBO.getBizRepayPlanDetailBO().getId()+":"+unpaidAmt);
							creditAmt = creditAmt.add(bizArrearsDetailBO.getUnpaidPrincipalAmt());
						}else{ //正常还本金
							/*获取上个还款日期*/
							if(AppConst.TERM_UNIT_CD_DATE.equals(cycleUnitCd)){
								lastRepayDate = DateUtils.addDay(bizArrearsDetailBO.getRepayPlanDate(), 0- repayCycle);
							}else if(AppConst.TERM_UNIT_CD_MONTH.equals(cycleUnitCd)){
								lastRepayDate = DateUtils.addMonth(bizArrearsDetailBO.getRepayPlanDate(),0- repayCycle);
							}else if(AppConst.TERM_UNIT_CD_YEAR.equals(cycleUnitCd)){
								lastRepayDate = DateUtils.addYear(bizArrearsDetailBO.getRepayPlanDate(), 0- repayCycle);
							}else{
								throw new BizException("还款频率不对，请核实数据"); 
							}
							 //所在的当期，不管在哪天，提前还款，当期利息全收
							if(bussDate.getTime() > lastRepayDate.getTime() && bussDate.getTime()  <= bizArrearsDetailBO.getRepayPlanDate().getTime()){
								repayPlanDetailIds.add(bizArrearsDetailBO.getBizRepayPlanDetailBO().getId());
								//System.out.println("正常还款："+bizArrearsDetailBO.getBizRepayPlanDetailBO().getId()+":"+unpaidAmt);
								zchkAmt = zchkAmt.add(unpaidAmt);
								creditAmt = creditAmt.add(bizArrearsDetailBO.getUnpaidPrincipalAmt());
							}else{
								//zchkAmt = zchkAmt.add(bizArrearsDetailBO.getUnpaidPrincipalAmt());
								/*计算提前还款本金*/
								tqhkAmt =tqhkAmt.add(bizArrearsDetailBO.getUnpaidPrincipalAmt());
								creditAmt = creditAmt.add(bizArrearsDetailBO.getUnpaidPrincipalAmt());
							}
						}
				  }
				}
				/*计算计费金额*/
				//展期,收取展期管理费=当期未还本息*管理费率*天数
				if(AppConst.ARREARS_FLAG_CD_ZQ.equals(bizArrearsDetailBO.getArrearsFlagCd())){
					zqGLFAMT =zqGLFAMT.add(unPIAmt.multiply(new BigDecimal(bizArrearsDetailBO.getDelayDays())));
					//System.out.println("展期管理费："+bizArrearsDetailBO.getBizRepayPlanDetailBO().getId()+":"+unPIAmt.multiply(new BigDecimal(bizArrearsDetailBO.getDelayDays())));
				}
				//逾期,收取逾期管理费=当期未还本息*管理费率*天数
				else if(AppConst.ARREARS_FLAG_CD_YQ.equals(bizArrearsDetailBO.getArrearsFlagCd())){
					zqGLFAMT =zqGLFAMT.add(unPIAmt.multiply(new BigDecimal(bizArrearsDetailBO.getDelayDays())));
					//System.out.println("展期管理费："+bizArrearsDetailBO.getBizRepayPlanDetailBO().getId()+":"+unPIAmt.multiply(new BigDecimal(bizArrearsDetailBO.getDelayDays())));
					yqGLFAMT =yqGLFAMT.add(unPIAmt.multiply(new BigDecimal(bizArrearsDetailBO.getOverdueDays())));
					//System.out.println("逾期管理费=："+bizArrearsDetailBO.getBizRepayPlanDetailBO().getId()+":"+unPIAmt.multiply(new BigDecimal(bizArrearsDetailBO.getOverdueDays())));
				}
				
			}
			
			
			
			repayCreditAmt = creditAmt; //恢复额度金额
			
			repayAmtForCredit = creditAmt;
			
			fullAmt = fullAmt.add(ptdfAmt).add(zchkAmt).add(tqhkAmt);
			if(fullAmt.compareTo(BigDecimal.ZERO)<=0) throw new BizException("本次提前还款金额不能小于等于零");
			if(log.isInfoEnabled()) log.info("本次提前还款金额为="+fullAmt.toString()+",其中还平台垫付:"+ptdfAmt.toString());
			
			/**扣收借款管理费*/
			//mod by yangjj 借款管理费改成关联借款id start
//			List<?> bizCostBOs = repayDAO.findByHQL("From BizCostBO Where custId =? and relateTypeCd=? and costTypeCd=? and costStatusCd=? and relateId in (select id from BizRepayPlanDetailBO where bizLoanBO.id = ?)",
//					new Object[]{bizLoanBO.getCustId(),AppConst.RELATE_TYPE_CD_REPAYPLAN,AppConst.COST_TYPE_CD_JKFY,AppConst.COST_STATUS_CD_WKF,bizLoanBO.getId()});
			List<?> bizCostBOs = repayDAO.findByHQL("From BizCostBO Where custId =? and relateTypeCd=? and costTypeCd=? and costStatusCd=? and relateId = ?)",
					new Object[]{bizLoanBO.getCustId(),AppConst.RELATE_TYPE_CD_LOAN,AppConst.COST_TYPE_CD_JKFY,AppConst.COST_STATUS_CD_WKF,bizLoanBO.getId()});
			/*if(CollectionUtil.isNotEmpty(bizCostBOs)){
				BizCostBO bizCostBO = null;
				for(int i=0;i<bizCostBOs.size();i++){
					bizCostBO = (BizCostBO)bizCostBOs.get(i);
					if(repayPlanDetailIds.contains(bizCostBO.getRelateId())){
						chargeBS.deductCharge(txNO, bizCostBO);
					}else{
						bizCostBO.setCostStatusCd(AppConst.COST_STATUS_CD_SX);
						repayDAO.update(bizCostBO);
					}
				}
			}*/
			if(CollectionUtil.isNotEmpty(bizCostBOs)){
				BizCostBO bizCostBO = (BizCostBO)bizCostBOs.get(0);
				chargeBS.deductCharge(txNO, bizCostBO);
			}
			//mod by yangjj end
			
			/**生成并扣收展期、逾期管理费，关联借款信息*/
			ChargeVO chargeVO = new ChargeVO();
			chargeVO.setCustId(bizLoanBO.getCustId());//会员ID
			chargeVO.setRelateId(bizLoanBO.getId());//关联借款信息表ID
			chargeVO.setRelateTypeCd(AppConst.RELATE_TYPE_CD_LOAN);//关联借款信息表ID
			chargeVO.setTxNO(txNO);//交易编号
			//提前还款违约金
			chargeVO.setCostTypeCd(AppConst.COST_TYPE_CD_TQHKWYJ);//提前还款违约金
			chargeVO.setCostBasicAmt(tqhkAmt);//提前还款金额
			chargeBS.genAndDeductCharge(chargeVO);
			//扣收展期管理费
			chargeVO.setCostTypeCd(AppConst.COST_TYPE_CD_ZQFY);//展期管理费
			chargeVO.setCostBasicAmt(zqGLFAMT);//未还本息*展期天数
			chargeBS.genAndDeductCharge(chargeVO);
			//扣收逾期管理费
			chargeVO.setCostTypeCd(AppConst.COST_TYPE_CD_YQFY);//逾期管理费
			chargeVO.setCostBasicAmt(yqGLFAMT);//未还本息*逾期天数
			chargeBS.genAndDeductCharge(chargeVO);
			/**扣收管理费*/
			
			/**转账*/
			//调用账户转账服务,该服务要提供余款同步控制,传入【fullAmt】,【借款人账户】转入【平台还款账户】。
					
			transferAccountsBS.transferAccount(txNO,AppConst.TRADE_TYPE_TQHK, 
					transferAccountsBS.getAccountBO(bizLoanBO.getCustId()), 
					transferAccountsBS.getPMAccountBO(AppConst.ACCOUNT_TYPE_PMHK), fullAmt);
			//调用账户转账服务,该服务要提供余款同步控制,传入【ptdfAmt】,【平台还款账户】转入【风险保证金账户】。
			transferAccountsBS.transferAccount(txNO,AppConst.TRADE_TYPE_GHFXZBJ, 
					transferAccountsBS.getPMAccountBO(AppConst.ACCOUNT_TYPE_PMHK), 
					transferAccountsBS.getPMAccountBO(AppConst.ACCOUNT_TYPE_PMFXZBJ), ptdfAmt);
			/**转账*/
			
			/**处理还款信息、欠款信息表*/
			Set<BizRepayDetailBO>  bizRepayDetailBOs =bizLoanBO.getBizRepayDetailBOs();//还款明细列表
			for(BizRepayDetailBO bizRepayDetailBO : bizRepayDetailBOs ){
				if(AppConst.REPAY_STATUS_CD_YH.equals(bizRepayDetailBO.getRepayStatusCd()))
					continue;
				BizArrearsDetailBO bizArrearsDetailBO = bizRepayDetailBO.getBizArrearsDetailBO();
				
				if(AppConst.REPAY_TYPE_CD_LSBQ.equals(bizArrearsDetailBO.getBizLoanBO().getRepayTypeCd())){
					if(bussDate.getTime() < bizArrearsDetailBO.getRepayPlanDate().getTime()){
						BigDecimal  lsbqPrincipalAmt = bizArrearsDetailBO.getUnpaidPrincipalAmt(); //利随本清本金
						BigDecimal lsbqInterestAmt = lsbqPrincipalAmt //本金
								.multiply(bizArrearsDetailBO.getBizRepayPlanDetailBO().getLoanRate()) //贷款利率
								.multiply(new BigDecimal(DateUtils.minuDay(bizArrearsDetailBO.getBizRepayPlanDetailBO().getStartInterestDate(), ViewOper.getOperTime())).add(new BigDecimal(1))) //贷款天数
								.divide(AppConst.BASEINTDAY, AppConst.DECIMAL_AMT_PRECISION, BigDecimal.ROUND_HALF_UP);//利随本清利息
						
						bizRepayDetailBO.setRepayedPrincipalAmt(lsbqPrincipalAmt);//当期已还本金
						bizRepayDetailBO.setRepayedInterestAmt(lsbqInterestAmt);//当期已还利息
					}else{
						throw new BizException("该笔借款已经到期，您无法提前还款"); 
					}
				}else {
					//处理还款明细表
					if(AppConst.ARREARS_FLAG_CD_ZQ.equals(bizArrearsDetailBO.getArrearsFlagCd())){//展期
						bizRepayDetailBO.setRepayedPrincipalAmt(bizArrearsDetailBO.getUnpaidPrincipalAmt());//当期已还本金
						bizRepayDetailBO.setRepayedInterestAmt(bizArrearsDetailBO.getUnpaidInterestAmt());//当期已还利息
						bizRepayDetailBO.setRepayedPenaltyAmt(bizArrearsDetailBO.getUnpaidPenaltyAmt());//当期已还罚息
					}else if(AppConst.ARREARS_FLAG_CD_YQ.equals(bizArrearsDetailBO.getArrearsFlagCd())){//逾期
						overDueRepayAmt = overDueRepayAmt.add(bizArrearsDetailBO.getUnpaidInterestAmt().add(bizArrearsDetailBO.getUnpaidPrincipalAmt()));
						
						bizRepayDetailBO.setRepayedPrincipalAmt(bizArrearsDetailBO.getUnpaidPrincipalAmt());//当期已还本金
						bizRepayDetailBO.setRepayedInterestAmt(bizArrearsDetailBO.getUnpaidInterestAmt());//当期已还利息
						bizRepayDetailBO.setRepayedPenaltyAmt(bizArrearsDetailBO.getUnpaidPenaltyAmt());//当期已还罚息
					}else{ //正常
						/*获取上个还款日期*/
						if(AppConst.TERM_UNIT_CD_DATE.equals(cycleUnitCd)){
							lastRepayDate = DateUtils.addDay(bizArrearsDetailBO.getRepayPlanDate(), 0- repayCycle);
						}else if(AppConst.TERM_UNIT_CD_MONTH.equals(cycleUnitCd)){
							lastRepayDate = DateUtils.addMonth(bizArrearsDetailBO.getRepayPlanDate(),0- repayCycle);
						}else if(AppConst.TERM_UNIT_CD_YEAR.equals(cycleUnitCd)){
							lastRepayDate = DateUtils.addYear(bizArrearsDetailBO.getRepayPlanDate(), 0- repayCycle);
						}else{
							throw new BizException("还款频率不对，请核实数据"); 
						}
						 //所在的当期，不管在哪天，提前还款，当期利息全收
						if(bussDate.getTime() > lastRepayDate.getTime() && bussDate.getTime()  <= bizArrearsDetailBO.getRepayPlanDate().getTime()){
							bizRepayDetailBO.setRepayedPrincipalAmt(bizArrearsDetailBO.getUnpaidPrincipalAmt());//当期已还本金
							bizRepayDetailBO.setRepayedInterestAmt(bizArrearsDetailBO.getUnpaidInterestAmt());//当期已还利息
							bizRepayDetailBO.setRepayedPenaltyAmt(bizArrearsDetailBO.getUnpaidPenaltyAmt());//当期已还罚息
						}else{
							bizRepayDetailBO.setRepayedPrincipalAmt(bizArrearsDetailBO.getUnpaidPrincipalAmt());//当期已还本金
						}
					}
				}
					 
				
				bizRepayDetailBO.setTtlRepayedAmt(bizRepayDetailBO.getRepayedPenaltyAmt().add(bizRepayDetailBO.getRepayedInterestAmt()).add(bizRepayDetailBO.getRepayedPrincipalAmt()));//当期已还总金额
				bizRepayDetailBO.setRepayStatusCd(AppConst.REPAY_STATUS_CD_YH);//已还
				bizRepayDetailBO.setRepayWayCd(repayWayCD);//还款结清方式
				
				bizRepayDetailBO.setUnIncomePrincipalAmt(bizRepayDetailBO.getRepayedPrincipalAmt());//可分配本金
				bizRepayDetailBO.setUnIncomeInterestAmt(bizRepayDetailBO.getRepayedInterestAmt());//可分配利息
				bizRepayDetailBO.setUnIncomePenaltyAmt(bizRepayDetailBO.getRepayedPenaltyAmt());//可分配罚息
				bizRepayDetailBO.setTtlIncomePrincipalAmt(bizRepayDetailBO.getTtlIncomePrincipalAmt().add(bizRepayDetailBO.getRepayedPrincipalAmt()));//总已分配本金
				bizRepayDetailBO.setTtlIncomeInterestAmt(bizRepayDetailBO.getTtlIncomeInterestAmt().add(bizRepayDetailBO.getRepayedInterestAmt()));//总已分配利息
				bizRepayDetailBO.setTtlIncomePenaltyAmt(bizRepayDetailBO.getTtlIncomePenaltyAmt().add(bizRepayDetailBO.getRepayedPenaltyAmt()));//总已分配罚息
				bizRepayDetailBO.setRepayDate(ViewOper.getOperTime());
				updateOperator(bizRepayDetailBO);
				repayDAO.update(bizRepayDetailBO);
				
				//处理欠款明细表
				bizArrearsDetailBO.setRepayDate(ViewOper.getOperTime());
				bizArrearsDetailBO.setUnpaidInterestAmt(BigDecimal.ZERO);//未还利息
				bizArrearsDetailBO.setUnpaidPrincipalAmt(BigDecimal.ZERO);//未还本金
				bizArrearsDetailBO.setUnpaidPenaltyAmt(BigDecimal.ZERO);//未还罚息
				bizArrearsDetailBO.setArrearsStatusCd(AppConst.ARREARS_STATUS_CD_YH);//已还
				updateOperator(bizArrearsDetailBO);
				repayDAO.update(bizArrearsDetailBO);
			}
			/**处理欠款信息、欠款信息表*/
		}else if(AppConst.REPAY_WAY_CD_DF.equals(repayWayCD)){//平台垫付
			if(repayPlanId==null || "".equals(repayPlanId)) throw new BizException("参数[repayPlanId]不能为空");
			BizRepayPlanDetailBO bizRepayPlanDetailBO = (BizRepayPlanDetailBO) repayDAO.findById(BizRepayPlanDetailBO.class, repayPlanId);//获取当期还款计划表
			BizRepayDetailBO bizRepayDetailBO = bizRepayPlanDetailBO.getBizRepayDetailBO();//还款明细表
			BizArrearsDetailBO bizArrearsDetailBO = bizRepayPlanDetailBO.getBizArrearsDetailBO();//欠款明细表
			fullAmt = bizArrearsDetailBO.getUnpaidPrincipalAmt().add(bizArrearsDetailBO.getUnpaidInterestAmt()).add(bizArrearsDetailBO.getUnpaidPenaltyAmt());
			if(fullAmt.compareTo(BigDecimal.ZERO)<=0) throw new BizException("当前期次不存在欠款,请重新操作");
			if(log.isInfoEnabled()) log.info("本次平台垫款金额为="+fullAmt.toString());
			
			//平台垫付,不扣收管理费
			//调用账户转账服务,该服务要提供余款同步控制,传入【fullAmt】,【风险准备金账户】转入【平台还款账户】。
			transferAccountsBS.transferAccount(txNO,AppConst.TRADE_TYPE_PTDF, 
					transferAccountsBS.getPMAccountBO(AppConst.ACCOUNT_TYPE_PMFXZBJ), 
					transferAccountsBS.getPMAccountBO(AppConst.ACCOUNT_TYPE_PMHK), fullAmt);
			
			//处理还款明细表
			bizRepayDetailBO.setPlatRepayedDate(ViewOper.getOperTime());//平台垫款日期
			bizRepayDetailBO.setPlatRepayedPrincipalAmt(bizArrearsDetailBO.getUnpaidPrincipalAmt());//未还本金
			bizRepayDetailBO.setPlatRepayedInterestAmt(bizArrearsDetailBO.getUnpaidInterestAmt());//未还利息
			bizRepayDetailBO.setPlatRepayedPenaltyAmt(bizArrearsDetailBO.getUnpaidPenaltyAmt());//未还罚息
			bizRepayDetailBO.setPlatRepayedAmt(fullAmt);
			bizRepayDetailBO.setPlatRepayedFlagCd(AppConst.PLAT_REPAYED_FLAG_CD_YES);//置平台垫款标识
			bizRepayDetailBO.setRepayStatusCd(AppConst.REPAY_STATUS_CD_PTDF);//平台垫付
			bizRepayDetailBO.setRepayWayCd(AppConst.REPAY_WAY_CD_DF);//平台垫付
			
			bizRepayDetailBO.setUnIncomePrincipalAmt(bizArrearsDetailBO.getUnpaidPrincipalAmt());//可分配本金
			bizRepayDetailBO.setUnIncomeInterestAmt(bizArrearsDetailBO.getUnpaidInterestAmt());//可分配利息
			bizRepayDetailBO.setUnIncomePenaltyAmt(bizArrearsDetailBO.getUnpaidPenaltyAmt());//可分配罚息
			bizRepayDetailBO.setTtlIncomePrincipalAmt(bizRepayDetailBO.getTtlIncomePrincipalAmt().add(bizArrearsDetailBO.getUnpaidPrincipalAmt()));//总已分配本金
			bizRepayDetailBO.setTtlIncomeInterestAmt(bizRepayDetailBO.getTtlIncomeInterestAmt().add(bizArrearsDetailBO.getUnpaidInterestAmt()));//总已分配利息
			bizRepayDetailBO.setTtlIncomePenaltyAmt(bizRepayDetailBO.getTtlIncomePenaltyAmt().add(bizArrearsDetailBO.getUnpaidPenaltyAmt()));//总已分配罚息
			
			updateOperator(bizRepayDetailBO);
			repayDAO.update(bizRepayDetailBO);
			
			//处理欠款明细表
			bizArrearsDetailBO.setArrearsStatusCd(AppConst.ARREARS_STATUS_CD_PTDF);//平台垫付
			updateOperator(bizArrearsDetailBO);
			repayDAO.update(bizArrearsDetailBO);
		}else{
			throw new BizException("参数[repayWayCD]无法识别!");
		}
		
		//处理借款信息表 
		if(AppConst.REPAY_WAY_CD_ZC.equals(repayWayCD)){  //正常还款 
			bizLoanBO.setRepayedPeriod(bizLoanBO.getRepayedPeriod()+1);//已还期数
			bizLoanBO.setSurplusPeriod(bizLoanBO.getSurplusPeriod()-1);//剩余期数
			bizLoanBO.setTtlRepayedAmt(bizLoanBO.getTtlRepayedAmt().add(fullAmt));//累计已还总额
			bizLoanBO.setLoanBalAmt(bizLoanBO.getLoanBalAmt().subtract(repayCreditAmt));//借款余额
			bizLoanBO.setRepayEndWay(AppConst.REPAY_WAY_CD_ZC);//还款结清方式:正常还款
			if(bizLoanBO.getSurplusPeriod()<=0){
				bizLoanBO.setLoanStatusCd(AppConst.LOAN_STATUS_CD_JQ);//借款状态:结清
			}
		}else if(AppConst.REPAY_WAY_CD_DF.equals(repayWayCD)){//平台垫付
			bizLoanBO.setRepayedPeriod(bizLoanBO.getRepayedPeriod());//已还期数 不变
			bizLoanBO.setSurplusPeriod(bizLoanBO.getSurplusPeriod());//剩余期数 不变
			bizLoanBO.setTtlRepayedAmt(bizLoanBO.getTtlRepayedAmt());//累计已还总额 不变
			bizLoanBO.setTtlPlatRepayedAmt(bizLoanBO.getTtlPlatRepayedAmt().add(fullAmt));//累计平台垫付金额
			bizLoanBO.setRepayEndWay(AppConst.REPAY_WAY_CD_DF);//还款结清方式:平台垫付
		}else if(AppConst.REPAY_WAY_CD_TQ.equals(repayWayCD)){//提前还款
			bizLoanBO.setRepayedPeriod(bizLoanBO.getTotalPeriod());//已还期数=总期数
			bizLoanBO.setSurplusPeriod(0);//剩余期数=0
			bizLoanBO.setTtlRepayedAmt(bizLoanBO.getTtlRepayedAmt().add(fullAmt));//累计已还总额
			bizLoanBO.setEarlyRepayDate(ViewOper.getOperTime());//提前还款日期
			bizLoanBO.setEarlyRepayAmt(fullAmt);//提前还款金额
			bizLoanBO.setRepayEndWay(AppConst.REPAY_WAY_CD_TQ);//还款结清方式:提前还款
			bizLoanBO.setLoanBalAmt(bizLoanBO.getLoanBalAmt().subtract(repayCreditAmt));//借款余额
			bizLoanBO.setLoanStatusCd(AppConst.LOAN_STATUS_CD_JQ);//借款状态:结清
		}
		updateOperator(bizLoanBO);
		repayDAO.update(bizLoanBO);
		//处理借款信息表 
		
		log.info("还款方式："+repayWayCD+" 释放金额为："+repayCreditAmt);
		log.info("客户："+bizLoanBO.getCustId().toString()+" 还款方式："+repayWayCD+" 释放额度金额为："+repayAmtForCredit);
		if(BigDecimal.ZERO.compareTo(repayAmtForCredit)<0){
			// 更新额度信息
			RequestCreditVO requestCreditVO = new RequestCreditVO();
			requestCreditVO.setRelId(String.valueOf(bizLoanBO.getCustId()));
			requestCreditVO.setRelTypeCd(AppConst.CREDITLIMIT_RELTYPE_CUST);//关联类型:客户类型
			requestCreditVO.setPassAmt(BigDecimal.ZERO);//占用金额
			requestCreditVO.setUnPassAmt(repayAmtForCredit);//释放金额
			requestCreditVO.setEventTypeCd(Integer.valueOf(AppConst.EVENTTYPE_REAPY));//交易类型:还款
			requestCreditVO.setTxNO(txNO);//交易编号
			creditBS.tranTransactionCredit(requestCreditVO); //TODO 额度调用
			
			//更新信用报告中的剩余额度,借款余额
			log.info("客户："+bizLoanBO.getCustId().toString()+" 更新信用报告中的剩余额度："+repayAmtForCredit);
			this.creditReportBS.updateApplyAmtForRev(bizLoanBO.getCustId().toString(),repayAmtForCredit);
		}
		
		if(null!=bizLoanBO.getLoanStatusCd()&&AppConst.LOAN_STATUS_CD_JQ.equals(bizLoanBO.getLoanStatusCd())){
			// 累加信息报告中的还款次数
			log.info("客户："+bizLoanBO.getCustId().toString()+"还款次数减一");
			this.creditReportBS.updateTol(bizLoanBO.getCustId().toString(), 1);
		}	
		log.info("客户："+bizLoanBO.getCustId().toString()+"进行了逾期信息的还款操作。");
		this.creditReportBS.subtractOverdueAmt(bizLoanBO.getCustId().toString(), overDueRepayAmt);
		
		//短信发送 "平台账号${accno}于${date}成功还款${amt}元，详咨XXXXXXXXXXXX"
		/*try {
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("date", ViewOper.getOperTime());
			dataMap.put("amt", fullAmt);
			BizCustomerBO cust = custInfoBS.findCustById(bizLoanBO.getCustId());
			msgTemplateBS.sendMsg(AppConst.EVENTTYPE_REAPY, cust, dataMap);
		} catch (Exception e) {
			log.error("还款短信发送失败:"+e.getMessage());
		}*/
		
	}

	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.NOT_SUPPORTED)
	public RepayInfoVO repayForTrial(String repayWayCD, Integer loanId,
			Integer repayPlanId) throws BizException {
		if(StringUtils.isEmpty(repayWayCD))  throw new BizException("参数[repayWayCD]不能为空");
		if(loanId == null) throw new BizException("参数[loanId]不能为空");
		BizLoanBO bizLoanBO = (BizLoanBO)repayDAO.findById(BizLoanBO.class, loanId);//获取借款信息表
		if(bizLoanBO == null) throw new BizException("获取不到借款信息,[loanId]="+loanId);
		String txNO = null;//获取交易编号
		BigDecimal fullAmt = BigDecimal.ZERO;
		BigDecimal PIAmt = BigDecimal.ZERO;
		RepayInfoVO repayInfoVO = new RepayInfoVO();
		repayInfoVO.setCustId(bizLoanBO.getCustId());
		repayInfoVO.setLoanId(loanId);
		repayInfoVO.setRepayWayCD(repayWayCD);
		repayInfoVO.setRepayPlanId(repayPlanId);
		
		if(AppConst.REPAY_WAY_CD_ZC.equals(repayWayCD)){ //正常还款,只能还当期
			if(repayPlanId==null) throw new BizException("参数[repayPlanId]不能为空");
			BizRepayPlanDetailBO bizRepayPlanDetailBO = (BizRepayPlanDetailBO) repayDAO.findById(BizRepayPlanDetailBO.class, repayPlanId);//获取当期还款计划表
			//控制不能跨期还款,判断上一期是否已经结清,如果还没结清,提示从上一期开始还款
			if(bizRepayPlanDetailBO.getPeriod()!=1){//第一期不用校验
				String chksql = "SELECT SUM(A.UNPAID_INTEREST_AMT+A.UNPAID_PENALTY_AMT+A.UNPAID_PRINCIPAL_AMT) UNPAID FROM BIZ_ARREARS_DETAIL A WHERE A.REPAY_PLAN_DETAIL_ID IN (SELECT ID FROM BIZ_REPAY_PLAN_DETAIL RP WHERE RP.CUST_ID=? AND RP.LOAN_ID=? AND RP.PERIOD < ? )";
				List<?> chkList = this.commonQuery.findBySQL(chksql,new Object[]{bizRepayPlanDetailBO.getCustId(),bizLoanBO.getId(),bizRepayPlanDetailBO.getPeriod()});
				if(chkList!=null && chkList.size()>0){
					BigDecimal unpaidAmt =  (BigDecimal)((Map<?,?>)chkList.get(0)).get("UNPAID");
					if(unpaidAmt!=null && unpaidAmt.compareTo(BigDecimal.ZERO)>0){
						throw new BizException("上一期还存在欠款,请先结清上一期");
					}
				}
			}
			//判断是否当前期数是否允许还款,判断规则为,当前日期+当月自然日 <计划还款日
			if(DateUtils.addDay(ViewOper.getOperTime(), DateUtils.getActualMaximum()).compareTo(bizRepayPlanDetailBO.getRepayplanDate())<0){
				throw new BizException("当前期数:["+bizRepayPlanDetailBO.getPeriod()+"]，还款日["+bizRepayPlanDetailBO.getRepayplanDate()+"]未到不允许进行还款操作");
			}
			BizArrearsDetailBO bizArrearsDetailBO = bizRepayPlanDetailBO.getBizArrearsDetailBO();//欠款明细表
			fullAmt = bizArrearsDetailBO.getUnpaidPrincipalAmt().add(bizArrearsDetailBO.getUnpaidInterestAmt()).add(bizArrearsDetailBO.getUnpaidPenaltyAmt());
			PIAmt = bizArrearsDetailBO.getUnpaidPrincipalAmt().add(bizArrearsDetailBO.getUnpaidInterestAmt());
			if(fullAmt.compareTo(BigDecimal.ZERO)<=0) throw new BizException("本期欠款已结清,不能重复还款");
			if(log.isInfoEnabled()) log.info("本次正常还款金额为="+fullAmt.toString());
			repayInfoVO.setRepayedPrincipalAmt(bizArrearsDetailBO.getUnpaidPrincipalAmt());//本金
			repayInfoVO.setRepayedInterestAmt(bizArrearsDetailBO.getUnpaidInterestAmt());//利息
			repayInfoVO.setRepayedPenaltyAmt(bizArrearsDetailBO.getUnpaidPenaltyAmt());//罚息
			/**扣收借款管理费*/
			//mod by yangjj 借款管理费改成关联借款id start
//			List<?> bizCostBOs = repayDAO.findByHQL("From BizCostBO Where custId =? and relateId=? and relateTypeCd=? and costTypeCd=? and costStatusCd=?",
//					new Object[]{bizRepayPlanDetailBO.getCustId(),bizRepayPlanDetailBO.getId(),AppConst.RELATE_TYPE_CD_REPAYPLAN,AppConst.COST_TYPE_CD_JKFY,AppConst.COST_STATUS_CD_WKF});
			List<?> bizCostBOs = repayDAO.findByHQL("From BizCostBO Where custId =? and relateId=? and relateTypeCd=? and costTypeCd=? and costStatusCd=?",
					new Object[]{bizRepayPlanDetailBO.getCustId(),bizLoanBO.getId(),AppConst.RELATE_TYPE_CD_LOAN,AppConst.COST_TYPE_CD_JKFY,AppConst.COST_STATUS_CD_WKF});
			if(CollectionUtil.isNotEmpty(bizCostBOs)){
				BizCostBO bizCostBO = (BizCostBO)bizCostBOs.get(0);
				repayInfoVO.setLoanChargeAmt(bizCostBO.getCostAmt());//借款管理费
			}
			//mod by yangjj end
			/**生成并扣收展期、逾期管理费*/
			ChargeVO chargeVO = new ChargeVO();
			chargeVO.setCustId(bizRepayPlanDetailBO.getCustId());//会员ID
			chargeVO.setRelateId(bizRepayPlanDetailBO.getId());//关联还款计划信息表ID
			chargeVO.setRelateTypeCd(AppConst.RELATE_TYPE_CD_REPAYPLAN);//还款信息表ID
			chargeVO.setTxNO(txNO);//交易编号
			chargeVO.setLoanTerm(bizLoanBO.getLoanTerm());
			if(AppConst.ARREARS_FLAG_CD_ZC.equals(bizArrearsDetailBO.getArrearsFlagCd())){
			}else if(AppConst.ARREARS_FLAG_CD_ZQ.equals(bizArrearsDetailBO.getArrearsFlagCd())){//展期,收取展期管理费=当期未还本息*管理费率*天数
				chargeVO.setCostTypeCd(AppConst.COST_TYPE_CD_ZQFY);//展期管理费
				chargeVO.setCostBasicAmt(PIAmt.multiply(new BigDecimal(bizArrearsDetailBO.getDelayDays())));//未还本息*展期天数
				repayInfoVO.setDelayChargeAmt(chargeBS.trialCharge(chargeVO).getCostAmt());//延期管理费
			}else if(AppConst.ARREARS_FLAG_CD_YQ.equals(bizArrearsDetailBO.getArrearsFlagCd())){//逾期,收取逾期管理费=当期未还本息*管理费率*天数
				chargeVO.setCostTypeCd(AppConst.COST_TYPE_CD_ZQFY);//展期管理费
				chargeVO.setCostBasicAmt(PIAmt.multiply(new BigDecimal(bizArrearsDetailBO.getDelayDays())));//未还本息*展期天数
				repayInfoVO.setDelayChargeAmt(chargeBS.trialCharge(chargeVO).getCostAmt());//延期管理费
				
				chargeVO.setCostTypeCd(AppConst.COST_TYPE_CD_YQFY);//逾期管理费
				chargeVO.setCostBasicAmt(PIAmt.multiply(new BigDecimal(bizArrearsDetailBO.getOverdueDays())));//未还本息*逾期天数
				repayInfoVO.setOverdueChargeAmt(chargeBS.trialCharge(chargeVO).getCostAmt());//延期管理费
			}
		}else if(AppConst.REPAY_WAY_CD_TQ.equals(repayWayCD)){//提前还款,结清后面所有的钱
			Set<BizArrearsDetailBO>  bizArrearsDetailBOs =bizLoanBO.getBizArrearsDetailBOs();//欠款明细列表
			fullAmt = BigDecimal.ZERO;
			PIAmt = BigDecimal.ZERO;
			BigDecimal tqhkAmt = BigDecimal.ZERO; //提前还款本金
			BigDecimal bjAmt = BigDecimal.ZERO; //本金
			BigDecimal lxAmt = BigDecimal.ZERO; //利息
			BigDecimal fxAmt = BigDecimal.ZERO; //罚息
			
			BigDecimal zqGLFAMT = BigDecimal.ZERO;//展期管理费金额
			BigDecimal yqGLFAMT = BigDecimal.ZERO;//逾期期管理费金额
			//上个应还款日期
			Date lastRepayDate = null;
			Date bussDate = new Date();
			//还款频率单位
			String cycleUnitCd = bizLoanBO.getCycleUnitCd();
			//还款频率
			Integer repayCycle= bizLoanBO.getRepayCycle();
			/**计算还款金额*/
			List<Integer> repayPlanDetailIds = new ArrayList<Integer>();//用于存放需要扣费的还款计划ID
			
			
			for(BizArrearsDetailBO bizArrearsDetailBO : bizArrearsDetailBOs ){
				if(AppConst.REPAY_STATUS_CD_YH.equals(bizArrearsDetailBO.getArrearsStatusCd())){
					continue;
				}
				/*计算未还本息金额*/
				BigDecimal unpaidAmt = bizArrearsDetailBO.getUnpaidInterestAmt().add(bizArrearsDetailBO.getUnpaidPrincipalAmt().add(bizArrearsDetailBO.getUnpaidPenaltyAmt()));
				PIAmt = bizArrearsDetailBO.getUnpaidInterestAmt().add(bizArrearsDetailBO.getUnpaidPrincipalAmt());
				
				if(AppConst.REPAY_TYPE_CD_LSBQ.equals(bizArrearsDetailBO.getBizLoanBO().getRepayTypeCd())){
					if(bussDate.getTime() < bizArrearsDetailBO.getRepayPlanDate().getTime()){
						 BigDecimal  lsbqPrincipalAmt = bizArrearsDetailBO.getUnpaidPrincipalAmt(); //利随本清本金
							BigDecimal lsbqInterestAmt = lsbqPrincipalAmt //本金
									.multiply(bizArrearsDetailBO.getBizRepayPlanDetailBO().getLoanRate()) //贷款利率
									.multiply(new BigDecimal(DateUtils.minuDay(bizArrearsDetailBO.getBizRepayPlanDetailBO().getStartInterestDate(), ViewOper.getOperTime())).add(new BigDecimal(1))) //贷款天数
									.divide(AppConst.BASEINTDAY, AppConst.DECIMAL_AMT_PRECISION, BigDecimal.ROUND_HALF_UP);//利随本清利息
							lxAmt = lxAmt.add(lsbqInterestAmt);
							tqhkAmt = tqhkAmt.add(lsbqPrincipalAmt);
							//利随本清照收（借款管理费）
							repayPlanDetailIds.add(bizArrearsDetailBO.getBizRepayPlanDetailBO().getId());
					}else{
						throw new BizException("该笔借款已经到期，您无法提前还款"); 
					}
				}else {
					//用于判断是否属于平台垫款后还款,以确定资金流向
					if(AppConst.ARREARS_STATUS_CD_PTDF.equals(bizArrearsDetailBO.getArrearsStatusCd())){//如果是平台垫付
						bjAmt = bjAmt.add(bizArrearsDetailBO.getUnpaidPrincipalAmt());//本金
						lxAmt = lxAmt.add(bizArrearsDetailBO.getUnpaidInterestAmt());//利息
						fxAmt = fxAmt.add(bizArrearsDetailBO.getUnpaidPenaltyAmt());//罚息
						//System.out.println("bjAmt=="+bjAmt+"lxAmt=="+lxAmt+"fxAmt=="+fxAmt+";期数："+bizArrearsDetailBO.getPeriod());
						repayPlanDetailIds.add(bizArrearsDetailBO.getBizRepayPlanDetailBO().getId());
					}else{
						if(AppConst.ARREARS_FLAG_CD_ZQ.equals(bizArrearsDetailBO.getArrearsFlagCd())){//展期,还本息				
							bjAmt = bjAmt.add(bizArrearsDetailBO.getUnpaidPrincipalAmt());//本金
							lxAmt = lxAmt.add(bizArrearsDetailBO.getUnpaidInterestAmt());//利息
							fxAmt = fxAmt.add(bizArrearsDetailBO.getUnpaidPenaltyAmt());//罚息
							//System.out.println("bjAmt=="+bjAmt+"lxAmt=="+lxAmt+"fxAmt=="+fxAmt+";期数："+bizArrearsDetailBO.getPeriod());
							repayPlanDetailIds.add(bizArrearsDetailBO.getBizRepayPlanDetailBO().getId());
						}else if(AppConst.ARREARS_FLAG_CD_YQ.equals(bizArrearsDetailBO.getArrearsFlagCd())){//逾期,还本息						
							bjAmt = bjAmt.add(bizArrearsDetailBO.getUnpaidPrincipalAmt());//本金
							lxAmt = lxAmt.add(bizArrearsDetailBO.getUnpaidInterestAmt());//利息
							fxAmt = fxAmt.add(bizArrearsDetailBO.getUnpaidPenaltyAmt());//罚息
							//System.out.println("bjAmt=="+bjAmt+"lxAmt=="+lxAmt+"fxAmt=="+fxAmt+";期数："+bizArrearsDetailBO.getPeriod());
							repayPlanDetailIds.add(bizArrearsDetailBO.getBizRepayPlanDetailBO().getId());
						}else{ //正常还本金
							/*获取上个还款日期*/
							if(AppConst.TERM_UNIT_CD_DATE.equals(cycleUnitCd)){
								lastRepayDate = DateUtils.addDay(bizArrearsDetailBO.getRepayPlanDate(), 0- repayCycle);
							}else if(AppConst.TERM_UNIT_CD_MONTH.equals(cycleUnitCd)){
								lastRepayDate = DateUtils.addMonth(bizArrearsDetailBO.getRepayPlanDate(),0- repayCycle);
							}else if(AppConst.TERM_UNIT_CD_YEAR.equals(cycleUnitCd)){
								lastRepayDate = DateUtils.addYear(bizArrearsDetailBO.getRepayPlanDate(), 0- repayCycle);
							}else{
								throw new BizException("还款频率不对，请核实数据"); 
							}
							//System.out.println("lastRepayDate=="+DateUtils.getYmd(lastRepayDate));
							 //所在的当期，不管在哪天，提前还款，当期利息全收
							if(bussDate.getTime() > lastRepayDate.getTime() && bussDate.getTime()  <= bizArrearsDetailBO.getRepayPlanDate().getTime()){
								bjAmt = bjAmt.add(bizArrearsDetailBO.getUnpaidPrincipalAmt());//本金
								lxAmt = lxAmt.add(bizArrearsDetailBO.getUnpaidInterestAmt());//利息
								//System.out.println("bjAmt=="+bjAmt+"lxAmt=="+lxAmt+"fxAmt=="+fxAmt+";期数："+bizArrearsDetailBO.getPeriod());
								repayPlanDetailIds.add(bizArrearsDetailBO.getBizRepayPlanDetailBO().getId());
							}else{
								tqhkAmt = tqhkAmt.add(bizArrearsDetailBO.getUnpaidPrincipalAmt());//本金
								//System.out.println("bjAmt=="+bjAmt+"lxAmt=="+lxAmt+"fxAmt=="+fxAmt+";期数："+bizArrearsDetailBO.getPeriod());
							}
							 
						}
					}
				}
				/*计算计费金额*/
				//展期,收取展期管理费=当期未还本息*管理费率*天数
				if(AppConst.ARREARS_FLAG_CD_ZQ.equals(bizArrearsDetailBO.getArrearsFlagCd())){
					zqGLFAMT =zqGLFAMT.add(PIAmt.multiply(new BigDecimal(bizArrearsDetailBO.getDelayDays())));
				}
				//逾期,收取逾期管理费=当期未还本息*管理费率*天数
				else if(AppConst.ARREARS_FLAG_CD_YQ.equals(bizArrearsDetailBO.getArrearsFlagCd())){
					zqGLFAMT =zqGLFAMT.add(PIAmt.multiply(new BigDecimal(bizArrearsDetailBO.getDelayDays())));
					yqGLFAMT =yqGLFAMT.add(PIAmt.multiply(new BigDecimal(bizArrearsDetailBO.getOverdueDays())));
				}
				
			}
			
			/**扣收借款管理费*/
			//mod by yangjj 借款管理费改成关联借款id
//			List<?> bizCostBOs = repayDAO.findByHQL("From BizCostBO Where custId =? and relateTypeCd=? and costTypeCd=? and costStatusCd=? and relateId in (select id from BizRepayPlanDetailBO where bizLoanBO.id = ?)",
//					new Object[]{bizLoanBO.getCustId(),AppConst.RELATE_TYPE_CD_REPAYPLAN,AppConst.COST_TYPE_CD_JKFY,AppConst.COST_STATUS_CD_WKF,bizLoanBO.getId()});
			List<?> bizCostBOs = repayDAO.findByHQL("From BizCostBO Where custId =? and relateTypeCd=? and costTypeCd=? and costStatusCd=? and relateId = ?)",
					new Object[]{bizLoanBO.getCustId(),AppConst.RELATE_TYPE_CD_LOAN,AppConst.COST_TYPE_CD_JKFY,AppConst.COST_STATUS_CD_WKF,bizLoanBO.getId()});
			//mod by yangjj end
			if(CollectionUtil.isNotEmpty(bizCostBOs)){
				/*BizCostBO bizCostBO = null;
				for(int i=0;i<bizCostBOs.size();i++){
					bizCostBO = (BizCostBO)bizCostBOs.get(i);
					if(repayPlanDetailIds.contains(bizCostBO.getRelateId())){
						repayInfoVO.setLoanChargeAmt(repayInfoVO.getLoanChargeAmt().add(bizCostBO.getCostAmt()));//借款管理费
					}
				}*/
				BizCostBO bizCostBO = (BizCostBO)bizCostBOs.get(0);
				repayInfoVO.setLoanChargeAmt(repayInfoVO.getLoanChargeAmt().add(bizCostBO.getCostAmt()));//借款管理费
			}
			//mod by yangjj end
			
			/**生成并扣收展期、逾期管理费，关联借款信息*/
			ChargeVO chargeVO = new ChargeVO();
			chargeVO.setCustId(bizLoanBO.getCustId());//会员ID
			chargeVO.setRelateId(bizLoanBO.getId());//关联还款信息表ID
			chargeVO.setRelateTypeCd(AppConst.RELATE_TYPE_CD_LOAN);//关联借款信息表ID
			chargeVO.setTxNO(txNO);//交易编号
			//本金总金额
			repayInfoVO.setRepayedPrincipalAmt(bjAmt.add(tqhkAmt));
			//正常本金
			repayInfoVO.setRepayAmtNormal(bjAmt);
			//提前还款本金
			repayInfoVO.setRepayAmtPre(tqhkAmt);
			//正常利息
			repayInfoVO.setRepayedInterestAmt(lxAmt);
			//罚息
			repayInfoVO.setRepayedPenaltyAmt(fxAmt);			
			//提前还款违约金
			chargeVO.setCostTypeCd(AppConst.COST_TYPE_CD_TQHKWYJ);//提前还款违约金
			chargeVO.setCostBasicAmt(tqhkAmt);//提前还款金额
			repayInfoVO.setPrepaymentChargeAmt(chargeBS.trialCharge(chargeVO).getCostAmt());
			//扣收展期管理费
			chargeVO.setCostTypeCd(AppConst.COST_TYPE_CD_ZQFY);//展期管理费
			chargeVO.setCostBasicAmt(zqGLFAMT);//未还本息*展期天数
			repayInfoVO.setDelayChargeAmt(chargeBS.trialCharge(chargeVO).getCostAmt());
			//扣收逾期管理费
			chargeVO.setCostTypeCd(AppConst.COST_TYPE_CD_YQFY);//逾期管理费
			chargeVO.setCostBasicAmt(yqGLFAMT);//未还本息*逾期天数
			repayInfoVO.setOverdueChargeAmt(chargeBS.trialCharge(chargeVO).getCostAmt());
			/**扣收管理费*/
		}else if(AppConst.REPAY_WAY_CD_DF.equals(repayWayCD)){//平台垫付
			if(repayPlanId==null || "".equals(repayPlanId)) throw new BizException("参数[repayPlanId]不能为空");
			BizRepayPlanDetailBO bizRepayPlanDetailBO = (BizRepayPlanDetailBO) repayDAO.findById(BizRepayPlanDetailBO.class, repayPlanId);//获取当期还款计划表
			BizArrearsDetailBO bizArrearsDetailBO = bizRepayPlanDetailBO.getBizArrearsDetailBO();//欠款明细表
			repayInfoVO.setRepayedPrincipalAmt(repayInfoVO.getRepayedPrincipalAmt().add(bizArrearsDetailBO.getUnpaidPrincipalAmt()));//本金
			repayInfoVO.setRepayedInterestAmt(repayInfoVO.getRepayedInterestAmt().add(bizArrearsDetailBO.getUnpaidInterestAmt()));//利息
			repayInfoVO.setRepayedPenaltyAmt(repayInfoVO.getRepayedPenaltyAmt().add(bizArrearsDetailBO.getUnpaidPenaltyAmt()));//罚息
		}else{
			throw new BizException("参数[repayWayCD]无法识别!");
		}
		repayInfoVO.setTtlRepayedAmt(repayInfoVO.getRepayedPrincipalAmt().add(repayInfoVO.getRepayedInterestAmt())
				.add(repayInfoVO.getRepayedPenaltyAmt()).add(repayInfoVO.getLoanChargeAmt())
				.add(repayInfoVO.getDelayChargeAmt()).add(repayInfoVO.getOverdueChargeAmt())
				.add(repayInfoVO.getPrepaymentChargeAmt()));
		return repayInfoVO;
	}
	
	
	public static void main(String[] args) {
		Calendar days = Calendar.getInstance();
		days.setTime(ViewOper.getOperTime());
		int a = days.getActualMaximum(Calendar.DAY_OF_MONTH);
		//System.out.println(DateUtils.addDay(DateUtils.getDateByFull("2014-2-19 00:00:00"), 28));
		//System.out.println(a);
		
		BizException aa = new BizException("1");
		//System.out.println(aa.getDecision());
	}

}
