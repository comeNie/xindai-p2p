package com.qfw.model.bo;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * BizLoan entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "biz_loan")
public class BizLoanBO extends BizBaseBO implements java.io.Serializable {

	// Fields

	private Integer id;
//	private Integer loanApproveId;
	private String loanName;
	private Integer custId;
	private String contractNum;
	private BigDecimal loanAmt;
	private BigDecimal loanBalAmt;
	private String currencyCd;
	private BigDecimal exchangeRate;
	private Integer loanTerm;
	private Date loanDate;
	private Date loanDueDate;
	private String repayTypeCd;
	private Integer repayCycle;
	private String cycleUnitCd;
	private Integer totalPeriod;
	private Integer repayedPeriod;
	private Integer surplusPeriod;
	private BigDecimal loanRate;
	private BigDecimal delayRate;
	private BigDecimal overdueRate;
	private BigDecimal ttlPrincipalInterestAmt;
	private BigDecimal ttlInterestAmt;
	private BigDecimal ttlPenaltyAmt;
	private BigDecimal ttlRepayedAmt;
	private BigDecimal ttlPlatRepayedAmt;
	private Date earlyRepayDate;
	private BigDecimal earlyRepayAmt;
	private String earlyRepayReason;
	private String repayEndWay;
	private String comment;
	private String loanStatusCd;
	private String paymentWayCd;//支付方式
	private Integer trusteeCustId;//受托支付会员ID
	private String workItemId;
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;
	
	private String productId;
	private String productName;
	private String loanTypeCd;
	
	private BizLoanApproveBO bizLoanApproveBO;//借款发布
	private Set<BizRepayPlanDetailBO> bizRepayPlanDetailBOs = new HashSet<BizRepayPlanDetailBO>();//还款计划
	private Set<BizArrearsDetailBO> bizArrearsDetailBOs = new HashSet<BizArrearsDetailBO>();//欠款明细
	private Set<BizRepayDetailBO> bizRepayDetailBOs = new HashSet<BizRepayDetailBO>();//还款明细
	
	@OneToMany(fetch=FetchType.LAZY,targetEntity=BizRepayDetailBO.class,mappedBy="bizLoanBO")
	@OrderBy("period ASC")
	public Set<BizRepayDetailBO> getBizRepayDetailBOs() {
		return bizRepayDetailBOs;
	}
	
	@OneToMany(fetch=FetchType.LAZY,targetEntity=BizArrearsDetailBO.class,mappedBy="bizLoanBO")
	@OrderBy("period ASC")
	public Set<BizArrearsDetailBO> getBizArrearsDetailBOs() {
		return bizArrearsDetailBOs;
	}
	
	@OneToMany(fetch=FetchType.LAZY,targetEntity=BizRepayPlanDetailBO.class,mappedBy="bizLoanBO")
	@OrderBy("period ASC")
	public Set<BizRepayPlanDetailBO> getBizRepayPlanDetailBOs() {
		return bizRepayPlanDetailBOs;
	}
	
	@OneToOne(fetch=FetchType.LAZY,targetEntity=BizLoanApproveBO.class)
	@JoinColumn(name="LOAN_APPROVE_ID")
	public BizLoanApproveBO getBizLoanApproveBO() {
		return bizLoanApproveBO;
	}
	
	
	// Constructors

	/** default constructor */
	public BizLoanBO() {
	}

	/** minimal constructor */
	public BizLoanBO(/*Integer loanApproveId,*/ String loanName, Integer custId,
			String contractNum, BigDecimal loanAmt, BigDecimal loanBalAmt,
			String currencyCd, BigDecimal exchangeRate, Integer loanTerm,
			Date loanDate, Date loanDueDate, String repayTypeCd,
			Integer repayCycle, String cycleUnitCd, Integer totalPeriod,
			Integer repayedPeriod, Integer surplusPeriod, BigDecimal loanRate,
			BigDecimal delayRate, BigDecimal overdueRate,
			BigDecimal ttlPrincipalInterestAmt, BigDecimal ttlInterestAmt,
			BigDecimal ttlPenaltyAmt, BigDecimal ttlRepayedAmt,
			BigDecimal ttlPlatRepayedAmt, Date earlyRepayDate,
			BigDecimal earlyRepayAmt, String repayEndWay, String loanStatusCd,String paymentWayCd,Integer trusteeCustId,
			String workItemId, Integer sysCreateUser, Date sysCreateTime,
			Integer sysUpdateUser, Date sysUpdateTime,
			String productId,String productName,String loanTypeCd) {
//		this.loanApproveId = loanApproveId;
		this.loanName = loanName;
		this.custId = custId;
		this.contractNum = contractNum;
		this.loanAmt = loanAmt;
		this.loanBalAmt = loanBalAmt;
		this.currencyCd = currencyCd;
		this.exchangeRate = exchangeRate;
		this.loanTerm = loanTerm;
		this.loanDate = loanDate;
		this.loanDueDate = loanDueDate;
		this.repayTypeCd = repayTypeCd;
		this.repayCycle = repayCycle;
		this.cycleUnitCd = cycleUnitCd;
		this.totalPeriod = totalPeriod;
		this.repayedPeriod = repayedPeriod;
		this.surplusPeriod = surplusPeriod;
		this.loanRate = loanRate;
		this.delayRate = delayRate;
		this.overdueRate = overdueRate;
		this.ttlPrincipalInterestAmt = ttlPrincipalInterestAmt;
		this.ttlInterestAmt = ttlInterestAmt;
		this.ttlPenaltyAmt = ttlPenaltyAmt;
		this.ttlRepayedAmt = ttlRepayedAmt;
		this.ttlPlatRepayedAmt = ttlPlatRepayedAmt;
		this.earlyRepayDate = earlyRepayDate;
		this.earlyRepayAmt = earlyRepayAmt;
		this.repayEndWay = repayEndWay;
		this.loanStatusCd = loanStatusCd;
		this.workItemId = workItemId;
		this.paymentWayCd = paymentWayCd;
		this.trusteeCustId = trusteeCustId;
		this.sysCreateUser = sysCreateUser;
		this.sysCreateTime = sysCreateTime;
		this.sysUpdateUser = sysUpdateUser;
		this.sysUpdateTime = sysUpdateTime;
		this.productId = productId;
		this.productName = productName;
		this.loanTypeCd = loanTypeCd;
	}

	/** full constructor */
	public BizLoanBO(/*Integer loanApproveId, */String loanName, Integer custId,
			String contractNum, BigDecimal loanAmt, BigDecimal loanBalAmt,
			String currencyCd, BigDecimal exchangeRate, Integer loanTerm,
			Date loanDate, Date loanDueDate, String repayTypeCd,
			Integer repayCycle, String cycleUnitCd, Integer totalPeriod,
			Integer repayedPeriod, Integer surplusPeriod, BigDecimal loanRate,
			BigDecimal delayRate, BigDecimal overdueRate,
			BigDecimal ttlPrincipalInterestAmt, BigDecimal ttlInterestAmt,
			BigDecimal ttlPenaltyAmt, BigDecimal ttlRepayedAmt,
			BigDecimal ttlPlatRepayedAmt, Date earlyRepayDate,
			BigDecimal earlyRepayAmt, String earlyRepayReason,
			String repayEndWay, String comment, String loanStatusCd,String paymentWayCd,Integer trusteeCustId,
			String workItemId, Integer sysCreateUser, Date sysCreateTime,
			Integer sysUpdateUser, Date sysUpdateTime,
			String productId,String productName,String loanTypeCd) {
//		this.loanApproveId = loanApproveId;
		this.loanName = loanName;
		this.custId = custId;
		this.contractNum = contractNum;
		this.loanAmt = loanAmt;
		this.loanBalAmt = loanBalAmt;
		this.currencyCd = currencyCd;
		this.exchangeRate = exchangeRate;
		this.loanTerm = loanTerm;
		this.loanDate = loanDate;
		this.loanDueDate = loanDueDate;
		this.repayTypeCd = repayTypeCd;
		this.repayCycle = repayCycle;
		this.cycleUnitCd = cycleUnitCd;
		this.totalPeriod = totalPeriod;
		this.repayedPeriod = repayedPeriod;
		this.surplusPeriod = surplusPeriod;
		this.loanRate = loanRate;
		this.delayRate = delayRate;
		this.overdueRate = overdueRate;
		this.ttlPrincipalInterestAmt = ttlPrincipalInterestAmt;
		this.ttlInterestAmt = ttlInterestAmt;
		this.ttlPenaltyAmt = ttlPenaltyAmt;
		this.ttlRepayedAmt = ttlRepayedAmt;
		this.ttlPlatRepayedAmt = ttlPlatRepayedAmt;
		this.earlyRepayDate = earlyRepayDate;
		this.earlyRepayAmt = earlyRepayAmt;
		this.earlyRepayReason = earlyRepayReason;
		this.repayEndWay = repayEndWay;
		this.comment = comment;
		this.loanStatusCd = loanStatusCd;
		this.paymentWayCd = paymentWayCd;
		this.trusteeCustId = trusteeCustId;
		this.workItemId = workItemId;
		this.sysCreateUser = sysCreateUser;
		this.sysCreateTime = sysCreateTime;
		this.sysUpdateUser = sysUpdateUser;
		this.sysUpdateTime = sysUpdateTime;
		this.productId = productId;
		this.productName = productName;
		this.loanTypeCd = loanTypeCd;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	//@Column(name = "LOAN_APPROVE_ID", nullable = false)
//	public Integer getLoanApproveId() {
//		return this.loanApproveId;
//	}

//	public void setLoanApproveId(Integer loanApproveId) {
//		this.loanApproveId = loanApproveId;
//	}

	@Column(name = "LOAN_NAME")
	public String getLoanName() {
		return this.loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	@Column(name = "CUST_ID")
	public Integer getCustId() {
		return this.custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	@Column(name = "CONTRACT_NUM")
	public String getContractNum() {
		return this.contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}

	@Column(name = "LOAN_AMT")
	public BigDecimal getLoanAmt() {
		return this.loanAmt;
	}

	public void setLoanAmt(BigDecimal loanAmt) {
		this.loanAmt = loanAmt;
	}

	@Column(name = "LOAN_BAL_AMT")
	public BigDecimal getLoanBalAmt() {
		return this.loanBalAmt;
	}

	public void setLoanBalAmt(BigDecimal loanBalAmt) {
		this.loanBalAmt = loanBalAmt;
	}

	@Column(name = "CURRENCY_CD")
	public String getCurrencyCd() {
		return this.currencyCd;
	}

	public void setCurrencyCd(String currencyCd) {
		this.currencyCd = currencyCd;
	}

	@Column(name = "EXCHANGE_RATE")
	public BigDecimal getExchangeRate() {
		return this.exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	@Column(name = "LOAN_TERM")
	public Integer getLoanTerm() {
		return this.loanTerm;
	}

	public void setLoanTerm(Integer loanTerm) {
		this.loanTerm = loanTerm;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "LOAN_DATE")
	public Date getLoanDate() {
		return this.loanDate;
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "LOAN_DUE_DATE")
	public Date getLoanDueDate() {
		return this.loanDueDate;
	}

	public void setLoanDueDate(Date loanDueDate) {
		this.loanDueDate = loanDueDate;
	}

	@Column(name = "REPAY_TYPE_CD")
	public String getRepayTypeCd() {
		return this.repayTypeCd;
	}

	public void setRepayTypeCd(String repayTypeCd) {
		this.repayTypeCd = repayTypeCd;
	}

	@Column(name = "REPAY_CYCLE")
	public Integer getRepayCycle() {
		return this.repayCycle;
	}

	public void setRepayCycle(Integer repayCycle) {
		this.repayCycle = repayCycle;
	}

	@Column(name = "CYCLE_UNIT_CD")
	public String getCycleUnitCd() {
		return this.cycleUnitCd;
	}

	public void setCycleUnitCd(String cycleUnitCd) {
		this.cycleUnitCd = cycleUnitCd;
	}

	@Column(name = "TOTAL_PERIOD")
	public Integer getTotalPeriod() {
		return this.totalPeriod;
	}

	public void setTotalPeriod(Integer totalPeriod) {
		this.totalPeriod = totalPeriod;
	}

	@Column(name = "REPAYED_PERIOD")
	public Integer getRepayedPeriod() {
		return this.repayedPeriod;
	}

	public void setRepayedPeriod(Integer repayedPeriod) {
		this.repayedPeriod = repayedPeriod;
	}

	@Column(name = "SURPLUS_PERIOD")
	public Integer getSurplusPeriod() {
		return this.surplusPeriod;
	}

	public void setSurplusPeriod(Integer surplusPeriod) {
		this.surplusPeriod = surplusPeriod;
	}

	@Column(name = "LOAN_RATE")
	public BigDecimal getLoanRate() {
		return this.loanRate;
	}

	public void setLoanRate(BigDecimal loanRate) {
		this.loanRate = loanRate;
	}

	@Column(name = "DELAY_RATE")
	public BigDecimal getDelayRate() {
		return this.delayRate;
	}

	public void setDelayRate(BigDecimal delayRate) {
		this.delayRate = delayRate;
	}

	@Column(name = "OVERDUE_RATE")
	public BigDecimal getOverdueRate() {
		return this.overdueRate;
	}

	public void setOverdueRate(BigDecimal overdueRate) {
		this.overdueRate = overdueRate;
	}

	@Column(name = "TTL_PRINCIPAL_INTEREST_AMT")
	public BigDecimal getTtlPrincipalInterestAmt() {
		return this.ttlPrincipalInterestAmt;
	}

	public void setTtlPrincipalInterestAmt(BigDecimal ttlPrincipalInterestAmt) {
		this.ttlPrincipalInterestAmt = ttlPrincipalInterestAmt;
	}

	@Column(name = "TTL_INTEREST_AMT")
	public BigDecimal getTtlInterestAmt() {
		return this.ttlInterestAmt;
	}

	public void setTtlInterestAmt(BigDecimal ttlInterestAmt) {
		this.ttlInterestAmt = ttlInterestAmt;
	}

	@Column(name = "TTL_PENALTY_AMT")
	public BigDecimal getTtlPenaltyAmt() {
		return this.ttlPenaltyAmt;
	}

	public void setTtlPenaltyAmt(BigDecimal ttlPenaltyAmt) {
		this.ttlPenaltyAmt = ttlPenaltyAmt;
	}

	@Column(name = "TTL_REPAYED_AMT")
	public BigDecimal getTtlRepayedAmt() {
		return this.ttlRepayedAmt;
	}

	public void setTtlRepayedAmt(BigDecimal ttlRepayedAmt) {
		this.ttlRepayedAmt = ttlRepayedAmt;
	}

	@Column(name = "TTL_PLAT_REPAYED_AMT")
	public BigDecimal getTtlPlatRepayedAmt() {
		return this.ttlPlatRepayedAmt;
	}

	public void setTtlPlatRepayedAmt(BigDecimal ttlPlatRepayedAmt) {
		this.ttlPlatRepayedAmt = ttlPlatRepayedAmt;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "EARLY_REPAY_DATE")
	public Date getEarlyRepayDate() {
		return this.earlyRepayDate;
	}

	public void setEarlyRepayDate(Date earlyRepayDate) {
		this.earlyRepayDate = earlyRepayDate;
	}

	@Column(name = "EARLY_REPAY_AMT")
	public BigDecimal getEarlyRepayAmt() {
		return this.earlyRepayAmt;
	}

	public void setEarlyRepayAmt(BigDecimal earlyRepayAmt) {
		this.earlyRepayAmt = earlyRepayAmt;
	}

	@Column(name = "EARLY_REPAY_REASON")
	public String getEarlyRepayReason() {
		return this.earlyRepayReason;
	}

	public void setEarlyRepayReason(String earlyRepayReason) {
		this.earlyRepayReason = earlyRepayReason;
	}

	@Column(name = "REPAY_END_WAY")
	public String getRepayEndWay() {
		return this.repayEndWay;
	}

	public void setRepayEndWay(String repayEndWay) {
		this.repayEndWay = repayEndWay;
	}

	@Column(name = "PAYMENT_WAY_CD")
	public String getPaymentWayCd() {
		return paymentWayCd;
	}

	public void setPaymentWayCd(String paymentWayCd) {
		this.paymentWayCd = paymentWayCd;
	}

	@Column(name = "TRUSTEE_CUST_ID")
	public Integer getTrusteeCustId() {
		return trusteeCustId;
	}

	public void setTrusteeCustId(Integer trusteeCustId) {
		this.trusteeCustId = trusteeCustId;
	}
	
	@Column(name = "COMMENT")
	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Column(name = "LOAN_STATUS_CD")
	public String getLoanStatusCd() {
		return this.loanStatusCd;
	}

	public void setLoanStatusCd(String loanStatusCd) {
		this.loanStatusCd = loanStatusCd;
	}

	@Column(name = "WORK_ITEM_ID")
	public String getWorkItemId() {
		return this.workItemId;
	}

	public void setWorkItemId(String workItemId) {
		this.workItemId = workItemId;
	}

	@Column(name = "SYS_CREATE_USER")
	public Integer getSysCreateUser() {
		return this.sysCreateUser;
	}

	public void setSysCreateUser(Integer sysCreateUser) {
		this.sysCreateUser = sysCreateUser;
	}

	@Column(name = "SYS_CREATE_TIME")
	public Date getSysCreateTime() {
		return this.sysCreateTime;
	}

	public void setSysCreateTime(Date sysCreateTime) {
		this.sysCreateTime = sysCreateTime;
	}

	@Column(name = "SYS_UPDATE_USER")
	public Integer getSysUpdateUser() {
		return this.sysUpdateUser;
	}

	public void setSysUpdateUser(Integer sysUpdateUser) {
		this.sysUpdateUser = sysUpdateUser;
	}

	@Version
	@Column(name = "SYS_UPDATE_TIME")
	public Date getSysUpdateTime() {
		return this.sysUpdateTime;
	}

	public void setSysUpdateTime(Date sysUpdateTime) {
		this.sysUpdateTime = sysUpdateTime;
	}

	public void setBizLoanApproveBO(BizLoanApproveBO bizLoanApproveBO) {
		this.bizLoanApproveBO = bizLoanApproveBO;
	}

	public void setBizRepayPlanDetailBOs(
			Set<BizRepayPlanDetailBO> bizRepayPlanDetailBOs) {
		this.bizRepayPlanDetailBOs = bizRepayPlanDetailBOs;
	}

	public void setBizArrearsDetailBOs(Set<BizArrearsDetailBO> bizArrearsDetailBOs) {
		this.bizArrearsDetailBOs = bizArrearsDetailBOs;
	}
	
	public void setBizRepayDetailBOs(Set<BizRepayDetailBO> bizRepayDetailBOs) {
		this.bizRepayDetailBOs = bizRepayDetailBOs;
	}
	@Column(name = "PRODUCT_ID")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	@Column(name = "PRODUCT_NAME")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Column(name = "LOAN_TYPE_CD")
	public String getLoanTypeCd() {
		return loanTypeCd;
	}

	public void setLoanTypeCd(String loanTypeCd) {
		this.loanTypeCd = loanTypeCd;
	}
	
}