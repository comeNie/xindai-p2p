package com.qfw.model.bo;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * BizAccount entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "biz_account_detail")
public class BizAccountDetailBO extends BizBaseBO implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = -4258213005201032543L;
	
	private Integer id;
	private Integer custId;
	private String account;
	private BigDecimal accountBalAmt;
	private BigDecimal freezeBalAmt;
	private BigDecimal pmAmt;
	
	private String txNo;
	private BigDecimal txAmt;
	private String eventTypeCd;
	private Date txDate;
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;

	// Constructors

	/** default constructor */
	public BizAccountDetailBO() {
	}

	/** full constructor */
	public BizAccountDetailBO(Integer custId, String account, BigDecimal accountBalAmt,
			BigDecimal freezeBalAmt, BigDecimal pmAmt, String txNo,
			Integer sysCreateUser, Date sysCreateTime, BigDecimal txAmt,
			String eventTypeCd,  Date txDate,Integer sysUpdateUser, Date sysUpdateTime) {
		this.custId = custId;
		this.account = account;
		this.accountBalAmt = accountBalAmt;
		this.freezeBalAmt = freezeBalAmt;
		this.pmAmt = pmAmt;
		this.sysCreateUser = sysCreateUser;
		this.sysCreateTime = sysCreateTime;
		this.txNo = txNo;
		this.eventTypeCd = eventTypeCd;
		this.txAmt = txAmt;
		this.txDate = txDate;
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

	@Column(name = "CUST_ID", nullable = false)
	public Integer getCustId() {
		return this.custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	@Column(name = "ACCOUNT", nullable = false)
	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column(name = "ACCOUNT_BAL_AMT", nullable = false)
	public BigDecimal getAccountBalAmt() {
		return this.accountBalAmt;
	}

	public void setAccountBalAmt(BigDecimal accountBalAmt) {
		this.accountBalAmt = accountBalAmt;
	}

	@Column(name = "FREEZE_BAL_AMT", nullable = false)
	public BigDecimal getFreezeBalAmt() {
		return this.freezeBalAmt;
	}

	public void setFreezeBalAmt(BigDecimal freezeBalAmt) {
		this.freezeBalAmt = freezeBalAmt;
	}

	@Column(name = "PM_AMT", nullable = false)
	public BigDecimal getPmAmt() {
		return this.pmAmt;
	}

	public void setPmAmt(BigDecimal pmAmt) {
		this.pmAmt = pmAmt;
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

	@Column(name = "TX_NO", nullable = false)
	public String getTxNo() {
		return this.txNo;
	}

	public void setTxNo(String txNo) {
		this.txNo = txNo;
	}

	@Column(name = "EVENT_TYPE_CD")
	public String getEventTypeCd() {
		return this.eventTypeCd;
	}

	public void setEventTypeCd(String eventTypeCd) {
		this.eventTypeCd = eventTypeCd;
	}
	
	@Column(name = "TX_AMT")
	public BigDecimal getTxAmt() {
		return this.txAmt;
	}

	public void setTxAmt(BigDecimal txAmt) {
		this.txAmt = txAmt;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "TX_DATE")
	public Date getTxDate() {
		return this.txDate;
	}

	public void setTxDate(Date txDate) {
		this.txDate = txDate;
	}
	
	@Column(name = "SYS_UPDATE_USER")
	public Integer getSysUpdateUser() {
		return this.sysUpdateUser;
	}

	public void setSysUpdateUser(Integer sysUpdateUser) {
		this.sysUpdateUser = sysUpdateUser;
	}

	@Column(name = "SYS_UPDATE_TIME")
	public Date getSysUpdateTime() {
		return this.sysUpdateTime;
	}

	public void setSysUpdateTime(Date sysUpdateTime) {
		this.sysUpdateTime = sysUpdateTime;
	}

}