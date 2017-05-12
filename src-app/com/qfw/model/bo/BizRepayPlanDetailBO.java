package com.qfw.model.bo;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
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

import com.qfw.common.model.BaseBO;


/**
 * BizRepayPlanDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "biz_repay_plan_detail")
public class BizRepayPlanDetailBO extends BizBaseBO implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer version;
	//private Integer loanId;
	//private Integer loanApproveId;
	private Integer custId;
	private Integer period;
	private BigDecimal loanRate;
	private Date startInterestDate;
	private Date repayplanDate;
	private BigDecimal principalAmt;
	private BigDecimal interestAmt;
	private BigDecimal principaInterestAmt;
	private BigDecimal ttlPrincipalAmt;
	private BigDecimal ttlInterestAmt;
	private BigDecimal loanBalAmt;
	private String workItemId;
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;
	
	private BizLoanBO bizLoanBO;
	private BizLoanApproveBO bizLoanApproveBO;
	private BizArrearsDetailBO bizArrearsDetailBO;
	private BizRepayDetailBO bizRepayDetailBO;
	
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

	@OneToOne(fetch=FetchType.LAZY,targetEntity=BizArrearsDetailBO.class,mappedBy="bizRepayPlanDetailBO")
	public BizArrearsDetailBO getBizArrearsDetailBO() {
		return bizArrearsDetailBO;
	}
	
	@OneToOne(fetch=FetchType.LAZY,targetEntity=BizRepayDetailBO.class,mappedBy="bizRepayPlanDetailBO")
	public BizRepayDetailBO getBizRepayDetailBO() {
		return bizRepayDetailBO;
	}

	// Constructors

	/** default constructor */
	public BizRepayPlanDetailBO() {
	}

	/** full constructor */
	public BizRepayPlanDetailBO(/*Integer loanId, Integer loanApproveId,*/
			Integer custId, Integer period, BigDecimal loanRate,
			Date startInterestDate, Date repayplanDate,
			BigDecimal principalAmt, BigDecimal interestAmt,
			BigDecimal principaInterestAmt, BigDecimal ttlPrincipalAmt,
			BigDecimal ttlInterestAmt, BigDecimal loanBalAmt,
			String workItemId, Integer sysCreateUser, Date sysCreateTime,
			Integer sysUpdateUser, Date sysUpdateTime) {
		//this.loanId = loanId;
		//this.loanApproveId = loanApproveId;
		this.custId = custId;
		this.period = period;
		this.loanRate = loanRate;
		this.startInterestDate = startInterestDate;
		this.repayplanDate = repayplanDate;
		this.principalAmt = principalAmt;
		this.interestAmt = interestAmt;
		this.principaInterestAmt = principaInterestAmt;
		this.ttlPrincipalAmt = ttlPrincipalAmt;
		this.ttlInterestAmt = ttlInterestAmt;
		this.loanBalAmt = loanBalAmt;
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

	@Version
	@Column(name = "VERSION")
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	//@Column(name = "LOAN_ID", nullable = false)
//	public Integer getLoanId() {
//		return this.loanId;
//	}

//	public void setLoanId(Integer loanId) {
//		this.loanId = loanId;
//	}

	//@Column(name = "LOAN_APPROVE_ID", nullable = false)
//	public Integer getLoanApproveId() {
//		return this.loanApproveId;
//	}

//	public void setLoanApproveId(Integer loanApproveId) {
//		this.loanApproveId = loanApproveId;
//	}

	@Column(name = "CUST_ID")
	public Integer getCustId() {
		return this.custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	@Column(name = "PERIOD")
	public Integer getPeriod() {
		return this.period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	@Column(name = "LOAN_RATE")
	public BigDecimal getLoanRate() {
		return this.loanRate;
	}

	public void setLoanRate(BigDecimal loanRate) {
		this.loanRate = loanRate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "START_INTEREST_DATE")
	public Date getStartInterestDate() {
		return this.startInterestDate;
	}

	public void setStartInterestDate(Date startInterestDate) {
		this.startInterestDate = startInterestDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "REPAYPLAN_DATE")
	public Date getRepayplanDate() {
		return this.repayplanDate;
	}

	public void setRepayplanDate(Date repayplanDate) {
		this.repayplanDate = repayplanDate;
	}

	@Column(name = "PRINCIPAL_AMT")
	public BigDecimal getPrincipalAmt() {
		return this.principalAmt;
	}

	public void setPrincipalAmt(BigDecimal principalAmt) {
		this.principalAmt = principalAmt;
	}

	@Column(name = "INTEREST_AMT")
	public BigDecimal getInterestAmt() {
		return this.interestAmt;
	}

	public void setInterestAmt(BigDecimal interestAmt) {
		this.interestAmt = interestAmt;
	}

	@Column(name = "PRINCIPA_INTEREST_AMT")
	public BigDecimal getPrincipaInterestAmt() {
		return this.principaInterestAmt;
	}

	public void setPrincipaInterestAmt(BigDecimal principaInterestAmt) {
		this.principaInterestAmt = principaInterestAmt;
	}

	@Column(name = "TTL_PRINCIPAL_AMT")
	public BigDecimal getTtlPrincipalAmt() {
		return this.ttlPrincipalAmt;
	}

	public void setTtlPrincipalAmt(BigDecimal ttlPrincipalAmt) {
		this.ttlPrincipalAmt = ttlPrincipalAmt;
	}

	@Column(name = "TTL_INTEREST_AMT")
	public BigDecimal getTtlInterestAmt() {
		return this.ttlInterestAmt;
	}

	public void setTtlInterestAmt(BigDecimal ttlInterestAmt) {
		this.ttlInterestAmt = ttlInterestAmt;
	}

	@Column(name = "LOAN_BAL_AMT")
	public BigDecimal getLoanBalAmt() {
		return this.loanBalAmt;
	}

	public void setLoanBalAmt(BigDecimal loanBalAmt) {
		this.loanBalAmt = loanBalAmt;
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

	@Version@Column(name = "SYS_UPDATE_TIME")
	public Date getSysUpdateTime() {
		return this.sysUpdateTime;
	}

	public void setSysUpdateTime(Date sysUpdateTime) {
		this.sysUpdateTime = sysUpdateTime;
	}

	public void setBizLoanBO(BizLoanBO bizLoanBO) {
		this.bizLoanBO = bizLoanBO;
	}

	public void setBizLoanApproveBO(BizLoanApproveBO bizLoanApproveBO) {
		this.bizLoanApproveBO = bizLoanApproveBO;
	}

	public void setBizArrearsDetailBO(BizArrearsDetailBO bizArrearsDetailBO) {
		this.bizArrearsDetailBO = bizArrearsDetailBO;
	}

	public void setBizRepayDetailBO(BizRepayDetailBO bizRepayDetailBO) {
		this.bizRepayDetailBO = bizRepayDetailBO;
	}
	
	

}