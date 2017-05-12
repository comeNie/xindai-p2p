package com.qfw.model.bo;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * BizCreditorRightTran entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "biz_creditor_right_tran")
public class BizCreditorRightTranBO extends BizBaseBO implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer crId;
	private Integer loanId;
	private Integer loanApproveId;
	private Integer surplusPeriod;
	private BigDecimal loanRate;
	private BigDecimal perTenderAmt;
	private Integer tranTtlCount;
	private Integer tranOutCount;
	private BigDecimal tranTtlAmt;
	private BigDecimal tranOutAmt;
	private BigDecimal takeAmt;
	private BigDecimal takeBalAmt;
	private String crtStatusCd;
	private String workItemId;
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;
	private Integer tranTerm;
	private Date tranDate;
	private Date tranDueDate;
	
	// Constructors

	/** default constructor */
	public BizCreditorRightTranBO() {
	}

	/** full constructor */
	public BizCreditorRightTranBO(Integer crId, Integer surplusPeriod,BigDecimal perTenderAmt,
			BigDecimal loanRate, Integer tranTtlCount, Integer tranOutCount,
			BigDecimal tranTtlAmt, BigDecimal tranOutAmt, BigDecimal takeAmt,
			String crtStatusCd, String workItemId, Integer sysCreateUser,
			Date sysCreateTime, Integer sysUpdateUser,Date sysUpdateTime,
			Integer tranTerm,Date tranDate,Date tranDueDate ) {
		this.crId = crId;
		this.surplusPeriod = surplusPeriod;
		this.loanRate = loanRate;
		this.perTenderAmt = perTenderAmt;
		this.tranTtlCount = tranTtlCount;
		this.tranOutCount = tranOutCount;
		this.tranTtlAmt = tranTtlAmt;
		this.tranOutAmt = tranOutAmt;
		this.takeAmt = takeAmt;
		this.crtStatusCd = crtStatusCd;
		this.workItemId = workItemId;
		this.sysCreateUser = sysCreateUser;
		this.sysCreateTime = sysCreateTime;
		this.sysUpdateUser = sysUpdateUser;
		this.sysUpdateTime = sysUpdateTime;
		this.tranTerm = tranTerm;
		this.tranDate = tranDate;
		this.tranDueDate = tranDueDate;
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

	@Column(name = "CR_ID")
	public Integer getCrId() {
		return this.crId;
	}

	public void setCrId(Integer crId) {
		this.crId = crId;
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
	
	@Column(name = "PER_TENDER_AMT")
	public BigDecimal getPerTenderAmt() {
		return perTenderAmt;
	}

	public void setPerTenderAmt(BigDecimal perTenderAmt) {
		this.perTenderAmt = perTenderAmt;
	}

	public void setLoanRate(BigDecimal loanRate) {
		this.loanRate = loanRate;
	}

	@Column(name = "TRAN_TTL_COUNT")
	public Integer getTranTtlCount() {
		return this.tranTtlCount;
	}

	public void setTranTtlCount(Integer tranTtlCount) {
		this.tranTtlCount = tranTtlCount;
	}

	@Column(name = "TRAN_OUT_COUNT")
	public Integer getTranOutCount() {
		return this.tranOutCount;
	}

	public void setTranOutCount(Integer tranOutCount) {
		this.tranOutCount = tranOutCount;
	}

	@Column(name = "TRAN_TTL_AMT")
	public BigDecimal getTranTtlAmt() {
		return this.tranTtlAmt;
	}

	public void setTranTtlAmt(BigDecimal tranTtlAmt) {
		this.tranTtlAmt = tranTtlAmt;
	}

	@Column(name = "TRAN_OUT_AMT")
	public BigDecimal getTranOutAmt() {
		return this.tranOutAmt;
	}

	public void setTranOutAmt(BigDecimal tranOutAmt) {
		this.tranOutAmt = tranOutAmt;
	}

	@Column(name = "TAKE_AMT")
	public BigDecimal getTakeAmt() {
		return this.takeAmt;
	}

	public void setTakeAmt(BigDecimal takeAmt) {
		this.takeAmt = takeAmt;
	}
	@Column(name = "TAKE_BAL_AMT")
	public BigDecimal getTakeBalAmt() {
		return takeBalAmt;
	}

	public void setTakeBalAmt(BigDecimal takeBalAmt) {
		this.takeBalAmt = takeBalAmt;
	}

	@Column(name = "CRT_STATUS_CD")
	public String getCrtStatusCd() {
		return this.crtStatusCd;
	}

	public void setCrtStatusCd(String crtStatusCd) {
		this.crtStatusCd = crtStatusCd;
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

	@Column(name = "LOAN_ID")
	public Integer getLoanId() {
		return loanId;
	}

	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}

	@Column(name = "LOAN_APPROVE_ID")
	public Integer getLoanApproveId() {
		return loanApproveId;
	}

	public void setLoanApproveId(Integer loanApproveId) {
		this.loanApproveId = loanApproveId;
	}

	@Column(name = "TRAN_TERM")
	public Integer getTranTerm() {
		return tranTerm;
	}

	public void setTranTerm(Integer tranTerm) {
		this.tranTerm = tranTerm;
	}

	@Column(name = "TRAN_DATE")
	public Date getTranDate() {
		return tranDate;
	}

	public void setTranDate(Date tranDate) {
		this.tranDate = tranDate;
	}

	@Column(name = "TRAN_DUE_DATE")
	public Date getTranDueDate() {
		return tranDueDate;
	}

	public void setTranDueDate(Date tranDueDate) {
		this.tranDueDate = tranDueDate;
	}
	
}