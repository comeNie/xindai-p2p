package com.qfw.bizservice.creditor.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qfw.bizservice.charge.IChargeBS;
import com.qfw.bizservice.creditor.ICreditorRightBS;
import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.bizservice.custinfo.account.ITransferAccountsBS;
import com.qfw.common.bizservice.ISeqBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.util.CollectionUtil;
import com.qfw.common.util.DateUtils;
import com.qfw.common.util.NumberUtils;
import com.qfw.common.util.StringUtils;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizCreditorRightBO;
import com.qfw.model.bo.BizCreditorRightTranBO;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.bo.BizLoanApproveBO;
import com.qfw.model.vo.charge.ChargeVO;
import com.qfw.model.vo.creditor.CreditorRightTranVO;
import com.qfw.model.vo.creditor.CreditorRightVO;

@Service("creditorRightBS")
public class CreditorRightBSImpl extends BaseServiceImpl implements ICreditorRightBS {
	
	@Autowired
	public ITransferAccountsBS transferAccountsBS; 
	@Autowired
	public ISeqBS seqBS;
	@Autowired
	public IChargeBS chargeBS;
	
	@Autowired
	private ICustInfoBS custInfoBS;   //客户信息
	
	@Transactional(rollbackFor = BizException.class,propagation = Propagation.REQUIRED)
	public synchronized boolean submitTrender(CreditorRightVO creditorRightVO) throws BizException {
		boolean isFull = false;//是否满标标识
		if (NumberUtils.isBlank(creditorRightVO.getLoanApproveId())) {// 借款发布ID为空时
			throw new BizException("请选择[投资的标]");
		}
		if (NumberUtils.isBlank(creditorRightVO.getCustId())) {// 投资人ID为空时
			throw new BizException("[投资人ID]为空");
		}
		
		BizLoanApproveBO bizLoanApproveBO = (BizLoanApproveBO) baseDAO.findById(BizLoanApproveBO.class,creditorRightVO.getLoanApproveId());
		if (bizLoanApproveBO == null) {
			throw new BizException("[投资的标]不存在");
		}
		if(ViewOper.getOperTime().compareTo(bizLoanApproveBO.getTenderDueTime())>0){
			throw new BizException("[投资的标]已到期,不允许投标!");
		}
		if(AppConst.APPROVE_STATUS_CD_FULL.equals(bizLoanApproveBO.getApproveStatusCd())){
			throw new BizException("[投资的标]已满标,不允许投标!");
		}else if(AppConst.APPROVE_STATUS_CD_LOAN.equals(bizLoanApproveBO.getApproveStatusCd())){
			throw new BizException("[投资的标]已放款,不允许投标!");
		}else if(AppConst.APPROVE_STATUS_CD_CANCEL.equals(bizLoanApproveBO.getApproveStatusCd())){
			throw new BizException("[投资的标]已撤标,不允许投标!");
		}
		
		BigDecimal minAmt = bizLoanApproveBO.getTenderLimitAmt();// 每份最小投资金额；

		BizCreditorRightBO bizCreditorRightBO = new BizCreditorRightBO();
		if (creditorRightVO.getCrAmt() != null && creditorRightVO.getCrAmt().compareTo(BigDecimal.ZERO) > 0) {
			bizCreditorRightBO.setTurnoverCount(creditorRightVO.getCrAmt().divide(minAmt).intValue());
			bizCreditorRightBO.setCrAmt(creditorRightVO.getCrAmt());
			bizCreditorRightBO.setPerTenderAmt(minAmt);
		} else if (creditorRightVO.getTurnoverCount() > 0) {
			bizCreditorRightBO.setCrAmt(minAmt.multiply(new BigDecimal(creditorRightVO.getTurnoverCount())));
			bizCreditorRightBO.setTurnoverCount(creditorRightVO.getTurnoverCount());
			bizCreditorRightBO.setPerTenderAmt(minAmt);
		} else {
			throw new BizException("[投资金额]或[投资份数]必须大于零");
		}
		
		//校验投资金额和投资份数
		if(bizCreditorRightBO.getCrAmt().compareTo(minAmt.multiply(new BigDecimal(creditorRightVO.getTurnoverCount())))!=0){
			throw new BizException("[投资金额]和[投资份数]不匹配,请核实数据");
		}
		
		if (creditorRightVO.getCrAmt().compareTo(bizLoanApproveBO.getTenderBalAmt()) > 0) {// 投标金额大于
			throw new BizException("[投资金额]不能大于[竞标剩余金额]");
		}
		BizCustomerBO cust = custInfoBS.findCustById(creditorRightVO.getCustId());
		String txNO = seqBS.getResultNum(AppConst.TXNO);// 获取交易编号
		// 调用账户管理(转账服务) 【投资人账户】投资金转入【平台投资账户】
		transferAccountsBS.transferAccount(txNO, AppConst.TRADE_TYPE_CREDITOR,
				transferAccountsBS.getAccountBO(creditorRightVO.getCustId()),
				transferAccountsBS.getPMAccountBO(AppConst.ACCOUNT_TYPE_PMTZ),bizCreditorRightBO.getCrAmt());

		bizLoanApproveBO.setTenderUseAmt(bizLoanApproveBO.getTenderUseAmt().add(bizCreditorRightBO.getCrAmt()));// 已投标金额
		bizLoanApproveBO.setTenderBalAmt(bizLoanApproveBO.getTenderBalAmt().subtract(bizCreditorRightBO.getCrAmt()));// 竞标剩余金额
		bizLoanApproveBO.setTenderUseCount(bizLoanApproveBO.getTenderUseCount() + bizCreditorRightBO.getTurnoverCount());// 已投标数
		bizLoanApproveBO.setTenderBalCount(bizLoanApproveBO.getTenderTtlCount() - bizLoanApproveBO.getTenderUseCount());// 剩余投标数
		bizCreditorRightBO.setLoanApproveId(bizLoanApproveBO.getId());//融资发布ID
		bizCreditorRightBO.setCustId(creditorRightVO.getCustId());//投资人客户ID
		bizCreditorRightBO.setLoanCustId(bizLoanApproveBO.getCustId());//借款人会员ID
		bizCreditorRightBO.setCrTypeCd(AppConst.CR_TYPE_CD_TENDER);// CR_TYPE_CD:投标0\转让1
		bizCreditorRightBO.setLoanAmt(bizLoanApproveBO.getLoanAmt());// 借款金额
		bizCreditorRightBO.setLoanName(bizLoanApproveBO.getLoanName());// 借款名称
		bizCreditorRightBO.setLoanRate(bizLoanApproveBO.getLoanRate());// 年利率
		bizCreditorRightBO.setTotalPeriod(0);// 总期数
		bizCreditorRightBO.setSurplusPeriod(0);// 剩余期数
		bizCreditorRightBO.setNextGatherDate(null);// 初始化下个收款日
		bizCreditorRightBO.setCrStatusCd(AppConst.CR_STATUS_CD_TENDERING);// 债权状态
		bizCreditorRightBO.setTotalProfitAmt(BigDecimal.ZERO);// 总收益金额
		bizCreditorRightBO.setSettleDate(null);// 初始化结清日期
		bizCreditorRightBO.setRepayTypeCd(bizLoanApproveBO.getRepayTypeCd());// 还款方式
		bizCreditorRightBO.setSurplusCount(bizCreditorRightBO.getTurnoverCount());// 剩余份数
		bizCreditorRightBO.setRetrieveAmt(BigDecimal.ZERO);// 回收金额
		bizCreditorRightBO.setUnretrieveAmt(bizCreditorRightBO.getCrAmt());// 待收本金
		bizCreditorRightBO.setWorkItemId("0");
		bizCreditorRightBO.setIsVip(cust.getIsVip());
		if (StringUtils.isEmpty(creditorRightVO.getTenderTypeCd())) {
			bizCreditorRightBO.setTenderTypeCd(AppConst.TENDER_TYPE_CD_HAND);
		}else{
			bizCreditorRightBO.setTenderTypeCd(creditorRightVO.getTenderTypeCd());
		}
		createOperator(bizCreditorRightBO);
		updateOperator(bizCreditorRightBO);
		updateOperator(bizLoanApproveBO);
		if (bizLoanApproveBO.getTenderBalAmt().compareTo(BigDecimal.ZERO) == 0) {// 满标的情况
			bizLoanApproveBO.setApproveStatusCd(AppConst.APPROVE_STATUS_CD_FULL);// 更新状态已满标
			isFull = true;
		}
		baseDAO.update(bizLoanApproveBO);
		baseDAO.save(bizCreditorRightBO);
		//更新推荐状态
		if(StringUtils.isEmpty(cust.getRefereeStatus())){
			cust.setRefereeStatus(AppConst.REFEREE_STATUS_INITIAL);
		}
		baseDAO.update(cust);
		return isFull;
	}
	
	@Transactional(rollbackFor = BizException.class,propagation = Propagation.REQUIRED)
	public synchronized void creditorTranApprove(CreditorRightTranVO creditorRightTranVO) throws BizException{
		if(creditorRightTranVO.getCrId()==null) throw new BizException("参数[债权转让ID]不能为空");
		if(creditorRightTranVO.getTranTtlCount()==null) throw new BizException("参数[债权转让份数]不能为空");
		
		BigDecimal minAmt = AppConst.MIN_TENDER_AMT;// 每份最小投资金额；
		if(creditorRightTranVO.getPerTenderAmt()!=null && BigDecimal.ZERO.compareTo(creditorRightTranVO.getPerTenderAmt())<0){
			minAmt = creditorRightTranVO.getPerTenderAmt();
		} 

		BizCreditorRightTranBO bizCreditorRightTranBO = null;
		if(creditorRightTranVO.getCrTranId()!=null){
			bizCreditorRightTranBO = (BizCreditorRightTranBO)baseDAO.findById(BizCreditorRightTranBO.class, creditorRightTranVO.getCrTranId());
		}else{
			List<?> cts = baseDAO.findByHQL("SELECT COUNT(*) FROM BizCreditorRightTranBO WHERE crId = ? AND crtStatusCd != ? "
					, new Object[]{creditorRightTranVO.getCrId(),AppConst.CRT_STATUS_CD_TENDERING});
			if(CollectionUtil.isNotEmpty(cts) && (Long)cts.listIterator().next()>0){
				throw new BizException("该债权转让正在发布中,不允许重复债权转让");
			} 
			bizCreditorRightTranBO = new BizCreditorRightTranBO();
			createOperator(bizCreditorRightTranBO);
			updateOperator(bizCreditorRightTranBO);
		}
		
		if (creditorRightTranVO.getTranTtlAmt() != null && creditorRightTranVO.getTranTtlAmt().compareTo(BigDecimal.ZERO) > 0) {
			bizCreditorRightTranBO.setTranTtlCount(creditorRightTranVO.getTranTtlAmt().divide(minAmt).intValue());
			bizCreditorRightTranBO.setTranTtlAmt(creditorRightTranVO.getTranTtlAmt());
			bizCreditorRightTranBO.setPerTenderAmt(minAmt);
		} else if (creditorRightTranVO.getTranTtlCount() > 0) {
			bizCreditorRightTranBO.setTranTtlAmt(minAmt.multiply(new BigDecimal(creditorRightTranVO.getTranTtlCount())));
			bizCreditorRightTranBO.setTranTtlCount(creditorRightTranVO.getTranTtlCount());
			bizCreditorRightTranBO.setPerTenderAmt(minAmt);
		} else {
			throw new BizException("[债权转让金额]或[债权投资份数]必须大于零");
		}
		
		//校验投资金额和投资份数
		if(bizCreditorRightTranBO.getTranTtlAmt().compareTo(minAmt.multiply(new BigDecimal(bizCreditorRightTranBO.getTranTtlCount())))!=0){
			throw new BizException("[债权转让金额]和[债权投资份数]不匹配,请核实数据");
		}
		
		BizCreditorRightBO bizCreditorRightBO =(BizCreditorRightBO)baseDAO.findById(BizCreditorRightBO.class, creditorRightTranVO.getCrId());
		if(bizCreditorRightBO==null) throw new BizException("获取不到债权,[债权ID]="+creditorRightTranVO.getCrId());
		if(bizCreditorRightBO.getSurplusCount()<bizCreditorRightTranBO.getTranTtlCount()) throw new BizException("剩余债权份数不足,[债权ID]="+bizCreditorRightBO.getId());
		
		bizCreditorRightTranBO.setCrId(creditorRightTranVO.getCrId());//债权ID
		bizCreditorRightTranBO.setSurplusPeriod(bizCreditorRightBO.getSurplusPeriod());//剩余期数
		bizCreditorRightTranBO.setLoanRate(bizCreditorRightBO.getLoanRate());//年利率
		bizCreditorRightTranBO.setTranOutAmt(BigDecimal.ZERO);//转出金额
		bizCreditorRightTranBO.setTranOutCount(0);//转出份数
		bizCreditorRightTranBO.setTakeAmt(creditorRightTranVO.getTakeAmt());//接手奖金
		bizCreditorRightTranBO.setTakeBalAmt(bizCreditorRightTranBO.getTakeAmt());//接手剩余奖金
		bizCreditorRightTranBO.setWorkItemId("0");
		bizCreditorRightTranBO.setCrtStatusCd(AppConst.CRT_STATUS_CD_PENDING);//发布中
		bizCreditorRightTranBO.setLoanId(bizCreditorRightBO.getLoanId());//借款ID
		bizCreditorRightTranBO.setLoanApproveId(bizCreditorRightBO.getLoanApproveId());//借款发布ID
		bizCreditorRightTranBO.setTranTerm(creditorRightTranVO.getTranTerm());//转让期限
		if(null==creditorRightTranVO.getTranDate()){ //转让日期
			bizCreditorRightTranBO.setTranDate(new Date());
		}else{
			bizCreditorRightTranBO.setTranDate(creditorRightTranVO.getTranDate());
		}
		bizCreditorRightTranBO.setTranDueDate(DateUtils.addDay(bizCreditorRightTranBO.getTranDate(), bizCreditorRightTranBO.getTranTerm()));//转让结束时间
		
		if(bizCreditorRightTranBO.getId()!=null){
			baseDAO.update(bizCreditorRightTranBO);
		}else{
			baseDAO.save(bizCreditorRightTranBO);
		}
		
		String txNO = seqBS.getResultNum(AppConst.TXNO);// 获取交易编号
		
		//扣收债权转让管理费
		ChargeVO chargeVO = new ChargeVO();
		chargeVO.setCustId(bizCreditorRightBO.getCustId());
		chargeVO.setRelateId(bizCreditorRightTranBO.getId());
		chargeVO.setRelateTypeCd(AppConst.RELATE_TYPE_CD_CREDITORTRAN);
		chargeVO.setTxNO(txNO);
		chargeVO.setCostBasicAmt(bizCreditorRightTranBO.getTranTtlAmt());//计费金额
		chargeVO.setCostTypeCd(AppConst.COST_TYPE_CD_ZQZRFY);//债权转让费用
		chargeBS.genAndDeductCharge(chargeVO);
	}
	
	@Transactional(rollbackFor = BizException.class,propagation = Propagation.REQUIRED)
	public synchronized boolean submitTrenderTran(CreditorRightVO creditorRightVO) throws BizException {
		boolean isFull = false;//是否满标标识
		if (NumberUtils.isBlank(creditorRightVO.getCreditorRightTranId())) {// 债权转让发布ID为空时
			throw new BizException("请选择[投资的标]");
		}
		if (NumberUtils.isBlank(creditorRightVO.getCustId())) {// 投资人ID为空时
			throw new BizException("[投资人ID]为空");
		}
		
		//获取债权转让发布ID
		BizCreditorRightTranBO bizCreditorRightTranBO = (BizCreditorRightTranBO)baseDAO.findById(BizCreditorRightTranBO.class, creditorRightVO.getCreditorRightTranId());
		if(bizCreditorRightTranBO==null) throw new BizException("获取不到[债权转让发布信息],[债权转让ID]="+creditorRightVO.getCreditorRightTranId());
		if(ViewOper.getOperTime().compareTo(bizCreditorRightTranBO.getTranDueDate())>0){
			throw new BizException("[投资的标]已到期,不允许投标!");
		}
		if(!AppConst.CRT_STATUS_CD_TENDERING.equals(bizCreditorRightTranBO.getCrtStatusCd())){
			throw new BizException("[投资的标]目前不在'筹标中',不允许投标!");
		}
		
		BigDecimal minAmt = bizCreditorRightTranBO.getPerTenderAmt();// 每份最小投资金额；
		
		BizCreditorRightBO inBizCreditorRightBO = new BizCreditorRightBO();//投资人债权信息
		if (creditorRightVO.getCrAmt() != null && creditorRightVO.getCrAmt().compareTo(BigDecimal.ZERO) > 0) {
			inBizCreditorRightBO.setTurnoverCount(creditorRightVO.getCrAmt().divide(minAmt).intValue());
			inBizCreditorRightBO.setCrAmt(creditorRightVO.getCrAmt());
			inBizCreditorRightBO.setPerTenderAmt(minAmt);
		} else if (creditorRightVO.getTurnoverCount() > 0) {
			inBizCreditorRightBO.setCrAmt(minAmt.multiply(new BigDecimal(creditorRightVO.getTurnoverCount())));
			inBizCreditorRightBO.setTurnoverCount(creditorRightVO.getTurnoverCount());
			inBizCreditorRightBO.setPerTenderAmt(minAmt);
		} else {
			throw new BizException("[投资金额]或[投资份数]必须大于零");
		}
		
		//校验投资金额和投资份数
		if(inBizCreditorRightBO.getCrAmt().compareTo(minAmt.multiply(new BigDecimal(inBizCreditorRightBO.getTurnoverCount())))!=0){
			throw new BizException("[投资金额]和[投资份数]不匹配,请核实数据");
		}
		
		
		//校验投资金额和剩余债权转让金额
		if (creditorRightVO.getCrAmt().compareTo(bizCreditorRightTranBO.getTranTtlAmt().subtract(bizCreditorRightTranBO.getTranOutAmt())) > 0) {
			throw new BizException("[投资金额]不能大于[剩余债权转让金额]");
		}
		
		//获取转出债权信息
		BizCreditorRightBO outBizCreditorRightBO =(BizCreditorRightBO)baseDAO.findById(BizCreditorRightBO.class, bizCreditorRightTranBO.getCrId());
		if(outBizCreditorRightBO==null) throw new BizException("获取不到[投资的标],[债权ID]="+bizCreditorRightTranBO.getCrId());
		if(outBizCreditorRightBO.getSurplusCount()<inBizCreditorRightBO.getTurnoverCount()) throw new BizException("剩余债权份数不足,[债权ID]="+outBizCreditorRightBO.getCrId());
		
		String txNO = seqBS.getResultNum(AppConst.TXNO);// 获取交易编号
		// 调用账户管理(转账服务) 【投资人账户】投资金转入【债权转出人账户】
		transferAccountsBS.transferAccount(txNO, AppConst.TRADE_TYPE_ZQZR,
				transferAccountsBS.getAccountBO(creditorRightVO.getCustId()),
				transferAccountsBS.getAccountBO(outBizCreditorRightBO.getCustId()),creditorRightVO.getCrAmt());
		
		//计算转让比例
		BigDecimal tranBL =  new BigDecimal(inBizCreditorRightBO.getTurnoverCount()).divide(new BigDecimal(bizCreditorRightTranBO.getTranTtlCount()),AppConst.DECIMAL_AMT_PRECISION,BigDecimal.ROUND_HALF_UP);
		BigDecimal takeAmt = tranBL.multiply(bizCreditorRightTranBO.getTakeAmt());//计算接手奖金
		// 调用账户管理(转账服务) 【债权转出人账户】投资金转入【投资人账户】
		transferAccountsBS.transferAccount(txNO, AppConst.TRADE_TYPE_JSJJ,
				transferAccountsBS.getAccountBO(outBizCreditorRightBO.getCustId()),
				transferAccountsBS.getAccountBO(creditorRightVO.getCustId()),takeAmt);
		
		//处理投资人债权
		inBizCreditorRightBO.setLoanApproveId(outBizCreditorRightBO.getLoanApproveId());//融资发布ID
		inBizCreditorRightBO.setCustId(creditorRightVO.getCustId());//投资人客户ID
		inBizCreditorRightBO.setLoanCustId(outBizCreditorRightBO.getLoanCustId());//借款人会员ID
		inBizCreditorRightBO.setLoanId(outBizCreditorRightBO.getLoanId());//借据ID
		inBizCreditorRightBO.setCrTypeCd(AppConst.CR_TYPE_CD_TRAN);// CR_TYPE_CD:投标0\转让1
		inBizCreditorRightBO.setLoanAmt(outBizCreditorRightBO.getLoanAmt());// 借款金额
		inBizCreditorRightBO.setLoanName(outBizCreditorRightBO.getLoanName());// 借款名称
		inBizCreditorRightBO.setLoanRate(outBizCreditorRightBO.getLoanRate());// 年利率
		inBizCreditorRightBO.setTotalPeriod(outBizCreditorRightBO.getTotalPeriod());// 总期数
		inBizCreditorRightBO.setSurplusPeriod(outBizCreditorRightBO.getSurplusCount());// 剩余期数
		inBizCreditorRightBO.setNextGatherDate(outBizCreditorRightBO.getNextGatherDate());// 初始化下个收款日
		inBizCreditorRightBO.setCrStatusCd(outBizCreditorRightBO.getCrStatusCd());// 债权状态
		inBizCreditorRightBO.setTotalProfitAmt(outBizCreditorRightBO.getTotalProfitAmt());// 总收益金额
		inBizCreditorRightBO.setSettleDate(outBizCreditorRightBO.getSettleDate());// 初始化结清日期
		inBizCreditorRightBO.setRepayTypeCd(outBizCreditorRightBO.getRepayTypeCd());// 还款方式
		inBizCreditorRightBO.setSurplusCount(inBizCreditorRightBO.getTurnoverCount());// 剩余份数
		inBizCreditorRightBO.setRetrieveAmt(BigDecimal.ZERO);// 回收金额
		inBizCreditorRightBO.setUnretrieveAmt(inBizCreditorRightBO.getCrAmt());// 待收本金
		inBizCreditorRightBO.setCrId(outBizCreditorRightBO.getId());//原债权转让ID
		inBizCreditorRightBO.setCrTranDate(ViewOper.getOperTime());
		inBizCreditorRightBO.setWorkItemId("0");
		if (StringUtils.isEmpty(creditorRightVO.getTenderTypeCd())) {
			inBizCreditorRightBO.setTenderTypeCd(AppConst.TENDER_TYPE_CD_HAND);
		}else{
			inBizCreditorRightBO.setTenderTypeCd(creditorRightVO.getTenderTypeCd());
		}
		createOperator(inBizCreditorRightBO);
		updateOperator(inBizCreditorRightBO);
		baseDAO.save(inBizCreditorRightBO);
		//处理债权转出人的债权信息
		outBizCreditorRightBO.setSurplusCount(outBizCreditorRightBO.getSurplusCount()-inBizCreditorRightBO.getTurnoverCount());// 剩余份数
		outBizCreditorRightBO.setRetrieveAmt(outBizCreditorRightBO.getRetrieveAmt().add(inBizCreditorRightBO.getCrAmt()));// 回收金额
		outBizCreditorRightBO.setUnretrieveAmt(outBizCreditorRightBO.getUnretrieveAmt().subtract(inBizCreditorRightBO.getCrAmt()));// 待收本金
		updateOperator(outBizCreditorRightBO);
		baseDAO.update(outBizCreditorRightBO);
		//处理债权转让发布信息
		bizCreditorRightTranBO.setTakeBalAmt(bizCreditorRightTranBO.getTakeBalAmt().subtract(takeAmt));//剩余接手奖金
		bizCreditorRightTranBO.setTranOutAmt(bizCreditorRightTranBO.getTranOutAmt().add(inBizCreditorRightBO.getCrAmt()));
		bizCreditorRightTranBO.setTranOutCount(bizCreditorRightTranBO.getTranOutCount()+inBizCreditorRightBO.getTurnoverCount());
		if (bizCreditorRightTranBO.getTranTtlAmt().compareTo(bizCreditorRightTranBO.getTranOutAmt()) == 0) {// 满标的情况
			bizCreditorRightTranBO.setCrtStatusCd(AppConst.CRT_STATUS_CD_FULL);// 更新状态已满标
			isFull = true;
		}
		updateOperator(bizCreditorRightTranBO);
		baseDAO.update(bizCreditorRightTranBO);
		return isFull;
	}
	
	@Transactional(rollbackFor = BizException.class,propagation = Propagation.REQUIRED)
	public void invalidCreditorRightTran(Integer crtId) throws BizException{
		if(crtId == null) throw new BizException("参数[债权转让ID]不能为空");
		BizCreditorRightTranBO bizCreditorRightTranBO = (BizCreditorRightTranBO)this.baseDAO.findById(BizCreditorRightTranBO.class, crtId);
		bizCreditorRightTranBO.setCrtStatusCd(AppConst.CRT_STATUS_CD_CANCEL);//状态为撤标
		updateOperator(bizCreditorRightTranBO);
		this.baseDAO.update(bizCreditorRightTranBO);
	}
	
	/**
	 * 通过债权id获取所有债权转让信息
	 * @param crId
	 * @return
	 * @throws BizException
	 */
	@SuppressWarnings("unchecked")
	public List<BizCreditorRightTranBO> findCreditorRightTransByCrId(Integer crId) throws BizException{
		try{
			return getHibernateTemplate().find("from BizCreditorRightTranBO where crId = ?", crId);
		}catch(Exception e){
			throw new BizException("查询债权信息失败");
		}
	}
	
	/**
	 * 通过债权转让id获取债权转让信息
	 * @param crId
	 * @return
	 * @throws BizException
	 */
	@Override
	public BizCreditorRightTranBO findCreditorRightTransById(Integer tranId) throws BizException{
		try{
			String queryString = "from BizCreditorRightTranBO where id = ?";
			List<?> list = getHibernateTemplate().find(queryString, tranId);
			if(list != null &&  !list.isEmpty()){
				return  (BizCreditorRightTranBO) list.get(0);
			} 
			return null;
		}catch(Exception e){
			 
		}
		return null;
	}
	
}
