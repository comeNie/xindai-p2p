package com.qfw.model.bo;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * BizRepayDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "biz_repay_detail")
public class BizRepayDetailBO extends BizBaseBO implements java.io.Serializable {

	// Fields

	private Integer id;
//	private Integer loanId;
//	private Integer repayPlanDetailId;
//	private Integer arrearsDetailId;
	private Integer custId;
//	private Integer loanApproveId;
	private Date repayDate;
	private Integer period;
	private BigDecimal repayedPrincipalAmt;
	private BigDecimal repayedInterestAmt;
	private BigDecimal repayedPenaltyAmt;
	private BigDecimal ttlRepayedAmt;
	private BigDecimal platRepayedPrincipalAmt;
	private BigDecimal platRepayedInterestAmt;
	private BigDecimal platRepayedPenaltyAmt;
	private BigDecimal platRepayedAmt;
	private Date platRepayedDate;
	private String platRepayedFlagCd;
	private String repayStatusCd;
	private String repayWayCd;
	private String isIncomeCd;
	
	private BigDecimal unIncomePrincipalAmt;
	private BigDecimal unIncomeInterestAmt;
	private BigDecimal unIncomePenaltyAmt;
	
	private BigDecimal ttlIncomePrincipalAmt;
	private BigDecimal ttlIncomeInterestAmt;
	private BigDecimal ttlIncomePenaltyAmt;
	
	private String comment;
	private String workItemId;
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;
	
	private BizLoanBO bizLoanBO;
	private BizLoanApproveBO bizLoanApproveBO;
	private BizRepayPlanDetailBO bizRepayPlanDetailBO;
	private BizArrearsDetailBO bizArrearsDetailBO;

	
	@ManyToOne(fetch=FetchType.LAZY,targetEntity=BizLoanBO.class,optional=false)
	@JoinColumn(name="LOAN_ID")
	public BizLoanBO getBizLoanBO() {
		return bizLoanBO;
	}
	
	@OneToOne(fetch=FetchType.LAZY,targetEntity=BizRepayPlanDetailBO.class)
	@JoinColumn(name="REPAY_PLAN_DETAIL_ID")
	public BizRepayPlanDetailBO getBizRepayPlanDetailBO() {
		return bizRepayPlanDetailBO;
	}
	
	@OneToOne(fetch=FetchType.LAZY,targetEntity=BizArrearsDetailBO.class)
	@JoinColumn(name="ARREARS_DETAIL_ID")
	public BizArrearsDetailBO getBizArrearsDetailBO() {
		return bizArrearsDetailBO;
	}
	
	@ManyToOne(fetch=FetchType.LAZY,targetEntity=BizLoanApproveBO.class)
	@JoinColumn(name="LOAN_APPROVE_ID")
	public BizLoanApproveBO getBizLoanApproveBO() {
		return bizLoanApproveBO;
	}
	
	// Constructors

	/** default constructor */
	public BizRepayDetailBO() {
	}

	/** minimal constructor */
	public BizRepayDetailBO(/*Integer loanId, Integer repayPlanDetailId,
			Integer arrearsDetailId, */Integer custId, /*Integer loanApproveId,*/
			Date repayDate, Integer period, BigDecimal repayedPrincipalAmt,
			BigDecimal repayedInterestAmt, BigDecimal repayedPenaltyAmt,
			BigDecimal ttlRepayedAmt, BigDecimal platRepayedPrincipalAmt,
			BigDecimal platRepayedInterestAmt,
			BigDecimal platRepayedPenaltyAmt, BigDecimal platRepayedAmt,
			Date platRepayedDate, String platRepayedFlagCd,
			String repayStatusCd, String repayWayCd, String workItemId,
			Integer sysCreateUser, Date sysCreateTime,
			Integer sysUpdateUser, Date sysUpdateTime) {
//		this.loanId = loanId;
//		this.repayPlanDetailId = repayPlanDetailId;
//		this.arrearsDetailId = arrearsDetailId;
		this.custId = custId;
//		this.loanApproveId = loanApproveId;
		this.repayDate = repayDate;
		this.period = period;
		this.repayedPrincipalAmt = repayedPrincipalAmt;
		this.repayedInterestAmt = repayedInterestAmt;
		this.repayedPenaltyAmt = repayedPenaltyAmt;
		this.ttlRepayedAmt = ttlRepayedAmt;
		this.platRepayedPrincipalAmt = platRepayedPrincipalAmt;
		this.platRepayedInterestAmt = platRepayedInterestAmt;
		this.platRepayedPenaltyAmt = platRepayedPenaltyAmt;
		this.platRepayedAmt = platRepayedAmt;
		this.platRepayedDate = platRepayedDate;
		this.platRepayedFlagCd = platRepayedFlagCd;
		this.repayStatusCd = repayStatusCd;
		this.repayWayCd = repayWayCd;
		this.workItemId = workItemId;
		this.sysCreateUser = sysCreateUser;
		this.sysCreateTime = sysCreateTime;
		this.sysUpdateUser = sysUpdateUser;
		this.sysUpdateTime = sysUpdateTime;
	}

	/** full constructor */
	public BizRepayDetailBO(/*Integer loanId, Integer repayPlanDetailId,
			Integer arrearsDetailId, */Integer custId, /*Integer loanApproveId,*/
			Date repayDate, Integer period, BigDecimal repayedPrincipalAmt,
			BigDecimal repayedInterestAmt, BigDecimal repayedPenaltyAmt,
			BigDecimal ttlRepayedAmt, BigDecimal platRepayedPrincipalAmt,
			BigDecimal platRepayedInterestAmt,
			BigDecimal platRepayedPenaltyAmt, BigDecimal platRepayedAmt,
			Date platRepayedDate, String platRepayedFlagCd,
			String repayStatusCd, String repayWayCd, String comment,
			String workItemId, Integer sysCreateUser, Date sysCreateTime,
			Integer sysUpdateUser, Date sysUpdateTime) {
//		this.loanId = loanId;
//		this.repayPlanDetailId = repayPlanDetailId;
//		this.arrearsDetailId = arrearsDetailId;
		this.custId = custId;
//		this.loanApproveId = loanApproveId;
		this.repayDate = repayDate;
		this.period = period;
		this.repayedPrincipalAmt = repayedPrincipalAmt;
		this.repayedInterestAmt = repayedInterestAmt;
		this.repayedPenaltyAmt = repayedPenaltyAmt;
		this.ttlRepayedAmt = ttlRepayedAmt;
		this.platRepayedPrincipalAmt = platRepayedPrincipalAmt;
		this.platRepayedInterestAmt = platRepayedInterestAmt;
		this.platRepayedPenaltyAmt = platRepayedPenaltyAmt;
		this.platRepayedAmt = platRepayedAmt;
		this.platRepayedDate = platRepayedDate;
		this.platRepayedFlagCd = platRepayedFlagCd;
		this.repayStatusCd = repayStatusCd;
		this.repayWayCd = repayWayCd;
		this.comment = comment;
		this.workItemId = workItemId;
		this.sysCreateUser = sysCreateUser;
		this.sysCreateTime = sysCreateTime;
		this.sysUpdateUser = sysUpdateUser;
		this.sysUpdateTime = sysUpdateTime;
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

	//@Column(name = "LOAN_ID", nullable = false)
//	public Integer getLoanId() {
//		return this.loanId;
//	}

//	public void setLoanId(Integer loanId) {
//		this.loanId = loanId;
//	}

	//@Column(name = "REPAY_PLAN_DETAIL_ID", nullable = false)
//	public Integer getRepayPlanDetailId() {
//		return this.repayPlanDetailId;
//	}

//	public void setRepayPlanDetailId(Integer repayPlanDetailId) {
//		this.repayPlanDetailId = repayPlanDetailId;
//	}

	//@Column(name = "ARREARS_DETAIL_ID", nullable = false)
//	public Integer getArrearsDetailId() {
//		return this.arrearsDetailId;
//	}

//	public void setArrearsDetailId(Integer arrearsDetailId) {
//		this.arrearsDetailId = arrearsDetailId;
//	}

	@Column(name = "CUST_ID")
	public Integer getCustId() {
		return this.custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	//@Column(name = "LOAN_APPROVE_ID", nullable = false)
//	public Integer getLoanApproveId() {
//		return this.loanApproveId;
//	}

//	public void setLoanApproveId(Integer loanApproveId) {
//		this.loanApproveId = loanApproveId;
//	}

	@Temporal(TemporalType.DATE)
	@Column(name = "REPAY_DATE")
	public Date getRepayDate() {
		return this.repayDate;
	}

	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}

	@Column(name = "PERIOD")
	public Integer getPeriod() {
		return this.period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	@Column(name = "REPAYED_PRINCIPAL_AMT")
	public BigDecimal getRepayedPrincipalAmt() {
		return this.repayedPrincipalAmt;
	}

	public void setRepayedPrincipalAmt(BigDecimal repayedPrincipalAmt) {
		this.repayedPrincipalAmt = repayedPrincipalAmt;
	}

	@Column(name = "REPAYED_INTEREST_AMT")
	public BigDecimal getRepayedInterestAmt() {
		return this.repayedInterestAmt;
	}

	public void setRepayedInterestAmt(BigDecimal repayedInterestAmt) {
		this.repayedInterestAmt = repayedInterestAmt;
	}

	@Column(name = "REPAYED_PENALTY_AMT")
	public BigDecimal getRepayedPenaltyAmt() {
		return this.repayedPenaltyAmt;
	}

	public void setRepayedPenaltyAmt(BigDecimal repayedPenaltyAmt) {
		this.repayedPenaltyAmt = repayedPenaltyAmt;
	}

	@Column(name = "TTL_REPAYED_AMT")
	public BigDecimal getTtlRepayedAmt() {
		return this.ttlRepayedAmt;
	}

	public void setTtlRepayedAmt(BigDecimal ttlRepayedAmt) {
		this.ttlRepayedAmt = ttlRepayedAmt;
	}

	@Column(name = "PLAT_REPAYED_PRINCIPAL_AMT")
	public BigDecimal getPlatRepayedPrincipalAmt() {
		return this.platRepayedPrincipalAmt;
	}

	public void setPlatRepayedPrincipalAmt(BigDecimal platRepayedPrincipalAmt) {
		this.platRepayedPrincipalAmt = platRepayedPrincipalAmt;
	}

	@Column(name = "PLAT_REPAYED_INTEREST_AMT")
	public BigDecimal getPlatRepayedInterestAmt() {
		return this.platRepayedInterestAmt;
	}

	public void setPlatRepayedInterestAmt(BigDecimal platRepayedInterestAmt) {
		this.platRepayedInterestAmt = platRepayedInterestAmt;
	}

	@Column(name = "PLAT_REPAYED_PENALTY_AMT")
	public BigDecimal getPlatRepayedPenaltyAmt() {
		return this.platRepayedPenaltyAmt;
	}

	public void setPlatRepayedPenaltyAmt(BigDecimal platRepayedPenaltyAmt) {
		this.platRepayedPenaltyAmt = platRepayedPenaltyAmt;
	}

	@Column(name = "PLAT_REPAYED_AMT")
	public BigDecimal getPlatRepayedAmt() {
		return this.platRepayedAmt;
	}

	public void setPlatRepayedAmt(BigDecimal platRepayedAmt) {
		this.platRepayedAmt = platRepayedAmt;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "PLAT_REPAYED_DATE")
	public Date getPlatRepayedDate() {
		return this.platRepayedDate;
	}

	public void setPlatRepayedDate(Date platRepayedDate) {
		this.platRepayedDate = platRepayedDate;
	}

	@Column(name = "PLAT_REPAYED_FLAG_CD")
	public String getPlatRepayedFlagCd() {
		return this.platRepayedFlagCd;
	}

	public void setPlatRepayedFlagCd(String platRepayedFlagCd) {
		this.platRepayedFlagCd = platRepayedFlagCd;
	}

	@Column(name = "REPAY_STATUS_CD")
	public String getRepayStatusCd() {
		return this.repayStatusCd;
	}

	public void setRepayStatusCd(String repayStatusCd) {
		this.repayStatusCd = repayStatusCd;
	}

	@Column(name = "REPAY_WAY_CD")
	public String getRepayWayCd() {
		return this.repayWayCd;
	}

	public void setRepayWayCd(String repayWayCd) {
		this.repayWayCd = repayWayCd;
	}

	@Column(name = "IS_INCOME_CD")
	public String getIsIncomeCd() {
		return isIncomeCd;
	}

	public void setIsIncomeCd(String isIncomeCd) {
		this.isIncomeCd = isIncomeCd;
	}
	
	@Column(name = "UNINCOME__PRINCIPAL_AMT")
	public BigDecimal getUnIncomePrincipalAmt() {
		return unIncomePrincipalAmt;
	}

	public void setUnIncomePrincipalAmt(BigDecimal unIncomePrincipalAmt) {
		this.unIncomePrincipalAmt = unIncomePrincipalAmt;
	}
	
	@Column(name = "UNINCOME_INTEREST_AMT")
	public BigDecimal getUnIncomeInterestAmt() {
		return unIncomeInterestAmt;
	}

	public void setUnIncomeInterestAmt(BigDecimal unIncomeInterestAmt) {
		this.unIncomeInterestAmt = unIncomeInterestAmt;
	}
	
	@Column(name = "UNINCOME_PENALTY_AMT")
	public BigDecimal getUnIncomePenaltyAmt() {
		return unIncomePenaltyAmt;
	}

	public void setUnIncomePenaltyAmt(BigDecimal unIncomePenaltyAmt) {
		this.unIncomePenaltyAmt = unIncomePenaltyAmt;
	}
	
	@Column(name = "TTL_INCOME_PRINCIPAL_AMT")
	public BigDecimal getTtlIncomePrincipalAmt() {
		return ttlIncomePrincipalAmt;
	}

	public void setTtlIncomePrincipalAmt(BigDecimal ttlIncomePrincipalAmt) {
		this.ttlIncomePrincipalAmt = ttlIncomePrincipalAmt;
	}
	
	@Column(name = "TTL_INCOME_INTEREST_AMT")
	public BigDecimal getTtlIncomeInterestAmt() {
		return ttlIncomeInterestAmt;
	}

	public void setTtlIncomeInterestAmt(BigDecimal ttlIncomeInterestAmt) {
		this.ttlIncomeInterestAmt = ttlIncomeInterestAmt;
	}
	
	@Column(name = "TTL_INCOME_PENALTY_AMT")
	public BigDecimal getTtlIncomePenaltyAmt() {
		return ttlIncomePenaltyAmt;
	}

	public void setTtlIncomePenaltyAmt(BigDecimal ttlIncomePenaltyAmt) {
		this.ttlIncomePenaltyAmt = ttlIncomePenaltyAmt;
	}

	@Column(name = "COMMENT")
	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public void setBizRepayPlanDetailBO(BizRepayPlanDetailBO bizRepayPlanDetailBO) {
		this.bizRepayPlanDetailBO = bizRepayPlanDetailBO;
	}
	
	public void setBizLoanBO(BizLoanBO bizLoanBO) {
		this.bizLoanBO = bizLoanBO;
	}

	public void setBizArrearsDetailBO(BizArrearsDetailBO bizArrearsDetailBO) {
		this.bizArrearsDetailBO = bizArrearsDetailBO;
	}
	
	public void setBizLoanApproveBO(BizLoanApproveBO bizLoanApproveBO) {
		this.bizLoanApproveBO = bizLoanApproveBO;
	}
	
}