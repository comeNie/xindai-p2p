package com.qfw.model.bo;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * BizCreditLimit entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "biz_credit_limit")
public class BizCreditLimitBO implements java.io.Serializable {

	// Fields

	private Integer id;
	private String relId;
	private String relTypeCd;
	private Date startDate;
	private Date endDate;
	private BigDecimal clAmt;
	private BigDecimal freezeAmt;
	private String creditStateCd;
	private String opstateCd;
	private String workItemId;
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;

	// Constructors

	/** default constructor */
	public BizCreditLimitBO() {
	}

	/** full constructor */
	public BizCreditLimitBO(String relId, String relTypeCd, Date startDate,
			Date endDate, BigDecimal clAmt, BigDecimal freezeAmt,
			String creditStateCd, String opstateCd, String workItemId,
			Integer sysCreateUser, Date sysCreateTime,
			Integer sysUpdateUser, Date sysUpdateTime) {
		this.relId = relId;
		this.relTypeCd = relTypeCd;
		this.startDate = startDate;
		this.endDate = endDate;
		this.clAmt = clAmt;
		this.freezeAmt = freezeAmt;
		this.creditStateCd = creditStateCd;
		this.opstateCd = opstateCd;
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

	@Column(name = "REL_ID")
	public String getRelId() {
		return this.relId;
	}

	public void setRelId(String relId) {
		this.relId = relId;
	}

	@Column(name = "REL_TYPE_CD")
	public String getRelTypeCd() {
		return this.relTypeCd;
	}

	public void setRelTypeCd(String relTypeCd) {
		this.relTypeCd = relTypeCd;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "START_DATE")
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "END_DATE")
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "CL_AMT")
	public BigDecimal getClAmt() {
		return this.clAmt;
	}

	public void setClAmt(BigDecimal clAmt) {
		this.clAmt = clAmt;
	}

	@Column(name = "FREEZE_AMT")
	public BigDecimal getFreezeAmt() {
		return this.freezeAmt;
	}

	public void setFreezeAmt(BigDecimal freezeAmt) {
		this.freezeAmt = freezeAmt;
	}

	@Column(name = "CREDIT_STATE_CD")
	public String getCreditStateCd() {
		return this.creditStateCd;
	}

	public void setCreditStateCd(String creditStateCd) {
		this.creditStateCd = creditStateCd;
	}

	@Column(name = "OPSTATE_CD")
	public String getOpstateCd() {
		return this.opstateCd;
	}

	public void setOpstateCd(String opstateCd) {
		this.opstateCd = opstateCd;
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