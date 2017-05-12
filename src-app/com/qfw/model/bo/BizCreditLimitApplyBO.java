package com.qfw.model.bo;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 额度申请BO
 */
import javax.persistence.Column;
import javax.persistence.Version;

/**
 * BizCreditLimitApply entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "biz_credit_limit_apply")
public class BizCreditLimitApplyBO implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer custId;
	private BigDecimal limitApply;
	private String tenderType;
	private Date applyStartdate;
	private Date applyEnddate;
	private Integer applyTerm;
	private String applyStatusCd;
	private String workItemId;
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;
	

	// Constructors

	/** default constructor */
	public BizCreditLimitApplyBO() {
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


	@Column(name = "CUST_ID", nullable = true)
	public Integer getCustId() {
		return this.custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	@Column(name = "LIMIT_APPLY")
	public BigDecimal getLimitApply() {
		return this.limitApply;
	}

	public void setLimitApply(BigDecimal limitApply) {
		this.limitApply = limitApply;
	}

	@Column(name = "TENDER_TYPE")
	public String getTenderType() {
		return this.tenderType;
	}

	public void setTenderType(String tenderType) {
		this.tenderType = tenderType;
	}

	@Column(name = "APPLY_STARTDATE")
	public Date getApplyStartdate() {
		return this.applyStartdate;
	}

	public void setApplyStartdate(Date applyStartdate) {
		this.applyStartdate = applyStartdate;
	}

	@Column(name = "APPLY_TERM")
	public Integer getApplyTerm() {
		return applyTerm;
	}

	public void setApplyTerm(Integer applyTerm) {
		this.applyTerm = applyTerm;
	}

	@Column(name = "APPLY_ENDDATE")
	public Date getApplyEnddate() {
		return this.applyEnddate;
	}

	public void setApplyEnddate(Date applyEnddate) {
		this.applyEnddate = applyEnddate;
	}
	
	@Column(name = "APPLY_STATUS_CD")
	public String getApplyStatusCd() {
		return this.applyStatusCd;
	}

	public void setApplyStatusCd(String applyStatusCd) {
		this.applyStatusCd = applyStatusCd;
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

}