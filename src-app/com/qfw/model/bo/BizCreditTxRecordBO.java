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
 * BizCreditTxRecord entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "biz_credit_tx_record")
public class BizCreditTxRecordBO implements java.io.Serializable {

	// Fields

	private Integer id;
	private String txNo;
	private Integer eventTypeCd;
	private Integer clId;
	private BigDecimal txAmt;
	private BigDecimal clAmt;
	private Date txDate;
	private String ctrStateCd;
	private String opstateCd;
	private Integer workItemId;
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;

	// Constructors

	/** default constructor */
	public BizCreditTxRecordBO() {
	}

	/** full constructor */
	public BizCreditTxRecordBO(String txNo, Integer eventTypeCd, Integer clId,
			BigDecimal txAmt, BigDecimal clAmt, Date txDate, String ctrStateCd,
			String opstateCd, Integer workItemId, Integer sysCreateUser,
			Date sysCreateTime, Integer sysUpdateUser,
			Date sysUpdateTime) {
		this.txNo = txNo;
		this.eventTypeCd = eventTypeCd;
		this.clId = clId;
		this.txAmt = txAmt;
		this.clAmt = clAmt;
		this.txDate = txDate;
		this.ctrStateCd = ctrStateCd;
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

	@Column(name = "TX_NO")
	public String getTxNo() {
		return this.txNo;
	}

	public void setTxNo(String txNo) {
		this.txNo = txNo;
	}

	@Column(name = "EVENT_TYPE_CD")
	public Integer getEventTypeCd() {
		return this.eventTypeCd;
	}

	public void setEventTypeCd(Integer eventTypeCd) {
		this.eventTypeCd = eventTypeCd;
	}

	@Column(name = "CL_ID")
	public Integer getClId() {
		return this.clId;
	}

	public void setClId(Integer clId) {
		this.clId = clId;
	}

	@Column(name = "TX_AMT")
	public BigDecimal getTxAmt() {
		return this.txAmt;
	}

	public void setTxAmt(BigDecimal txAmt) {
		this.txAmt = txAmt;
	}

	@Column(name = "CL_AMT")
	public BigDecimal getClAmt() {
		return this.clAmt;
	}

	public void setClAmt(BigDecimal clAmt) {
		this.clAmt = clAmt;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "TX_DATE")
	public Date getTxDate() {
		return this.txDate;
	}

	public void setTxDate(Date txDate) {
		this.txDate = txDate;
	}

	@Column(name = "CTR_STATE_CD")
	public String getCtrStateCd() {
		return this.ctrStateCd;
	}

	public void setCtrStateCd(String ctrStateCd) {
		this.ctrStateCd = ctrStateCd;
	}

	@Column(name = "OPSTATE_CD")
	public String getOpstateCd() {
		return this.opstateCd;
	}

	public void setOpstateCd(String opstateCd) {
		this.opstateCd = opstateCd;
	}

	@Column(name = "WORK_ITEM_ID")
	public Integer getWorkItemId() {
		return this.workItemId;
	}

	public void setWorkItemId(Integer workItemId) {
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