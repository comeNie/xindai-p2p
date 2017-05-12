package com.qfw.model.bo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BizRechargeCard entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "biz_recharge_card")
public class BizRechargeCardBO extends BizBaseBO implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1561339789798893258L;
	
	private Integer id;
	private String batchNo;
	private String cardCd;
	private String cardPwd;
	private BigDecimal amt;
	private Date startDate;
	private Date endDate;
	private Date rechargeDate;
	private Integer accountId;
	private String account;
	private String status;
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;

	// Constructors

	/** default constructor */
	public BizRechargeCardBO() {
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

	@Column(name = "BATCH_NO")
	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	@Column(name = "CARD_CD")
	public String getCardCd() {
		return this.cardCd;
	}

	public void setCardCd(String cardCd) {
		this.cardCd = cardCd;
	}

	@Column(name = "CARD_PWD")
	public String getCardPwd() {
		return this.cardPwd;
	}

	public void setCardPwd(String cardPwd) {
		this.cardPwd = cardPwd;
	}

	@Column(name = "AMT")
	public BigDecimal getAmt() {
		return this.amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	@Column(name = "START_DATE")
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "END_DATE")
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "RECHARGE_DATE")
	public Date getRechargeDate() {
		return this.rechargeDate;
	}

	public void setRechargeDate(Date rechargeDate) {
		this.rechargeDate = rechargeDate;
	}

	@Column(name = "ACCOUNT_ID")
	public Integer getAccountId() {
		return this.accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	@Column(name = "ACCOUNT")
	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column(name = "STATUS")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	@Column(name = "SYS_UPDATE_TIME")
	public Date getSysUpdateTime() {
		return this.sysUpdateTime;
	}

	public void setSysUpdateTime(Date sysUpdateTime) {
		this.sysUpdateTime = sysUpdateTime;
	}

}