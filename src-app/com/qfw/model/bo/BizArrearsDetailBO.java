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
 * BizArrearsDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "biz_arrears_detail")
public class BizArrearsDetailBO extends BizBaseBO implements java.io.Serializable {

	// Fields

	private Integer id;
//	private Integer loanId;
//	private Integer repayPlanDetailId;
	private Integer custId;
//	private Integer loanApproveId;
	private Integer period;
	private Date repayPlanDate;
	private Integer graceDays;
	private Integer delayDays;
	private Integer overdueDays;
	private Date repayDate;
	private BigDecimal delayRate;
	private BigDecimal overdueRate;
	private BigDecimal unpaidPrincipalAmt;
	private BigDecimal unpaidInterestAmt;
	private BigDecimal unpaidPenaltyAmt;
	private String arrearsFlagCd;
	private Date lastInterestDate;
	private String arrearsStatusCd;
	private String workItemId;
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;
	
	private BizLoanBO bizLoanBO;
	private BizLoanApproveBO bizLoanApproveBO;
	private BizRepayPlanDetailBO bizRepayPlanDetailBO;
	
	@ManyToOne(fetch=FetchType.LAZY ,targetEntity=BizLoanBO.class)
	@JoinColumn(name="LOAN_ID")
	public BizLoanBO getBizLoanBO() {
		return bizLoanBO;
	}
	
	@ManyToOne(fetch=FetchType.LAZY,targetEntity=BizLoanApproveBO.class)
	@JoinColumn(name="LOAN_APPROVE_ID")
	public BizLoanApproveBO getBizLoanApproveBO() {
		return bizLoanApproveBO;
	}

	@OneToOne(fetch=FetchType.LAZY,targetEntity=BizRepayPlanDetailBO.class)
	@JoinColumn(name="REPAY_PLAN_DETAIL_ID")
	public BizRepayPlanDetailBO getBizRepayPlanDetailBO() {
		return bizRepayPlanDetailBO;
	}
	

	// Constructors
	
	/** default constructor */
	public BizArrearsDetailBO() {
	}

	/** full constructor */
	public BizArrearsDetailBO(/*Integer loanId, Integer repayPlanDetailId,*/
			Integer custId, /*Integer loanApproveId, */Integer period,
			Date repayPlanDate, Integer graceDays, Integer delayDays,
			Integer overdueDays, Date repayDate, BigDecimal delayRate,
			BigDecimal overdueRate, BigDecimal unpaidPrincipalAmt,
			BigDecimal unpaidInterestAmt, BigDecimal unpaidPenaltyAmt,
			String arrearsFlagCd, Date lastInterestDate,
			String arrearsStatusCd, String workItemId, Integer sysCreateUser,
			Date sysCreateTime, Integer sysUpdateUser,
			Date sysUpdateTime) {
//		this.loanId = loanId;
//		this.repayPlanDetailId = repayPlanDetailId;
		this.custId = custId;
//		this.loanApproveId = loanApproveId;
		this.period = period;
		this.repayPlanDate = repayPlanDate;
		this.graceDays = graceDays;
		this.delayDays = delayDays;
		this.overdueDays = overdueDays;
		this.repayDate = repayDate;
		this.delayRate = delayRate;
		this.overdueRate = overdueRate;
		this.unpaidPrincipalAmt = unpaidPrincipalAmt;
		this.unpaidInterestAmt = unpaidInterestAmt;
		this.unpaidPenaltyAmt = unpaidPenaltyAmt;
		this.arrearsFlagCd = arrearsFlagCd;
		this.lastInterestDate = lastInterestDate;
		this.arrearsStatusCd = arrearsStatusCd;
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

	//@Column(name = "LOAN_REPAY_INFO_ID", nullable = false)
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

	@Column(name = "CUST_ID", nullable = false)
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

	@Column(name = "PERIOD", nullable = false)
	public Integer getPeriod() {
		return this.period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "REPAY_PLAN_DATE", nullable = false)
	public Date getRepayPlanDate() {
		return this.repayPlanDate;
	}

	public void setRepayPlanDate(Date repayPlanDate) {
		this.repayPlanDate = repayPlanDate;
	}

	@Column(name = "GRACE_DAYS", nullable = false)
	public Integer getGraceDays() {
		return this.graceDays;
	}

	public void setGraceDays(Integer graceDays) {
		this.graceDays = graceDays;
	}

	@Column(name = "DELAY_DAYS", nullable = false)
	public Integer getDelayDays() {
		return this.delayDays;
	}

	public void setDelayDays(Integer delayDays) {
		this.delayDays = delayDays;
	}

	@Column(name = "OVERDUE_DAYS", nullable = false)
	public Integer getOverdueDays() {
		return this.overdueDays;
	}

	public void setOverdueDays(Integer overdueDays) {
		this.overdueDays = overdueDays;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "REPAY_DATE", nullable = true)
	public Date getRepayDate() {
		return this.repayDate;
	}

	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
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

	@Column(name = "UNPAID_PRINCIPAL_AMT")
	public BigDecimal getUnpaidPrincipalAmt() {
		return this.unpaidPrincipalAmt;
	}

	public void setUnpaidPrincipalAmt(BigDecimal unpaidPrincipalAmt) {
		this.unpaidPrincipalAmt = unpaidPrincipalAmt;
	}

	@Column(name = "UNPAID_INTEREST_AMT")
	public BigDecimal getUnpaidInterestAmt() {
		return this.unpaidInterestAmt;
	}

	public void setUnpaidInterestAmt(BigDecimal unpaidInterestAmt) {
		this.unpaidInterestAmt = unpaidInterestAmt;
	}

	@Column(name = "UNPAID_PENALTY_AMT")
	public BigDecimal getUnpaidPenaltyAmt() {
		return this.unpaidPenaltyAmt;
	}

	public void setUnpaidPenaltyAmt(BigDecimal unpaidPenaltyAmt) {
		this.unpaidPenaltyAmt = unpaidPenaltyAmt;
	}

	@Column(name = "ARREARS_FLAG_CD")
	public String getArrearsFlagCd() {
		return this.arrearsFlagCd;
	}

	public void setArrearsFlagCd(String arrearsFlagCd) {
		this.arrearsFlagCd = arrearsFlagCd;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "LAST_INTEREST_DATE")
	public Date getLastInterestDate() {
		return this.lastInterestDate;
	}

	public void setLastInterestDate(Date lastInterestDate) {
		this.lastInterestDate = lastInterestDate;
	}

	@Column(name = "ARREARS_STATUS_CD")
	public String getArrearsStatusCd() {
		return this.arrearsStatusCd;
	}

	public void setArrearsStatusCd(String arrearsStatusCd) {
		this.arrearsStatusCd = arrearsStatusCd;
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
	
	public void setBizLoanBO(BizLoanBO bizLoanBO) {
		this.bizLoanBO = bizLoanBO;
	}
	
	public void setBizRepayPlanDetailBO(BizRepayPlanDetailBO bizRepayPlanDetailBO) {
		this.bizRepayPlanDetailBO = bizRepayPlanDetailBO;
	}
	
}