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
 * BizCreditUse entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "biz_credit_use")
public class BizCreditUseBO implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer clId;
	private BigDecimal tieupAmt;
	private BigDecimal preTieupAmt;
	private BigDecimal preRestoreAmt;
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;

	// Constructors

	/** default constructor */
	public BizCreditUseBO() {
	}

	/** full constructor */
	public BizCreditUseBO(Integer clId, BigDecimal tieupAmt,
			BigDecimal preTieupAmt, BigDecimal preRestoreAmt,
			Integer sysCreateUser, Date sysCreateTime,
			Integer sysUpdateUser, Date sysUpdateTime) {
		this.clId = clId;
		this.tieupAmt = tieupAmt;
		this.preTieupAmt = preTieupAmt;
		this.preRestoreAmt = preRestoreAmt;
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

	@Column(name = "CL_ID")
	public Integer getClId() {
		return this.clId;
	}

	public void setClId(Integer clId) {
		this.clId = clId;
	}

	@Column(name = "TIEUP_AMT")
	public BigDecimal getTieupAmt() {
		return this.tieupAmt;
	}

	public void setTieupAmt(BigDecimal tieupAmt) {
		this.tieupAmt = tieupAmt;
	}

	@Column(name = "PRE_TIEUP_AMT")
	public BigDecimal getPreTieupAmt() {
		return this.preTieupAmt;
	}

	public void setPreTieupAmt(BigDecimal preTieupAmt) {
		this.preTieupAmt = preTieupAmt;
	}

	@Column(name = "PRE_RESTORE_AMT")
	public BigDecimal getPreRestoreAmt() {
		return this.preRestoreAmt;
	}

	public void setPreRestoreAmt(BigDecimal preRestoreAmt) {
		this.preRestoreAmt = preRestoreAmt;
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