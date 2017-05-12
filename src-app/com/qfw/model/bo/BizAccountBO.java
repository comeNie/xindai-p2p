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
 * BizAccount entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "biz_account")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class BizAccountBO extends BizBaseBO implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer custId;
	private String accountTypeCd;
	private String account;
	private BigDecimal accountBalAmt;
	private BigDecimal freezeBalAmt;
	private BigDecimal usableBalAmt;
	private BigDecimal pmAmt;
	private String workItemId;
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;
	
	private BigDecimal rechargeAmt;

	// Constructors

	/** default constructor */
	public BizAccountBO() {
	}

	/** full constructor */
	public BizAccountBO(Integer custId, String account,String accountTypeCd, BigDecimal accountBalAmt,
			BigDecimal freezeBalAmt, BigDecimal usableBalAmt, BigDecimal pmAmt,
			String workItemId, Integer sysCreateUser, Date sysCreateTime,
			Integer sysUpdateUser, Date sysUpdateTime, BigDecimal rechargeAmt) {
		this.custId = custId;
		this.account = account;
		this.accountTypeCd = accountTypeCd;
		this.accountBalAmt = accountBalAmt;
		this.freezeBalAmt = freezeBalAmt;
		this.usableBalAmt = usableBalAmt;
		this.pmAmt = pmAmt;
		this.workItemId = workItemId;
		this.sysCreateUser = sysCreateUser;
		this.sysCreateTime = sysCreateTime;
		this.sysUpdateUser = sysUpdateUser;
		this.sysUpdateTime = sysUpdateTime;
		this.rechargeAmt = rechargeAmt;
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

	@Column(name = "ACCOUNT")
	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	@Column(name = "ACCOUNT_TYPE_CD")
	public String getAccountTypeCd() {
		return accountTypeCd;
	}

	public void setAccountTypeCd(String accountTypeCd) {
		this.accountTypeCd = accountTypeCd;
	}

	@Column(name = "ACCOUNT_BAL_AMT")
	public BigDecimal getAccountBalAmt() {
		return this.accountBalAmt;
	}

	public void setAccountBalAmt(BigDecimal accountBalAmt) {
		this.accountBalAmt = accountBalAmt;
	}

	@Column(name = "FREEZE_BAL_AMT")
	public BigDecimal getFreezeBalAmt() {
		return this.freezeBalAmt;
	}

	public void setFreezeBalAmt(BigDecimal freezeBalAmt) {
		this.freezeBalAmt = freezeBalAmt;
	}

	@Column(name = "USABLE_BAL_AMT")
	public BigDecimal getUsableBalAmt() {
		return this.usableBalAmt;
	}

	public void setUsableBalAmt(BigDecimal usableBalAmt) {
		this.usableBalAmt = usableBalAmt;
	}

	@Column(name = "PM_AMT")
	public BigDecimal getPmAmt() {
		return this.pmAmt;
	}

	public void setPmAmt(BigDecimal pmAmt) {
		this.pmAmt = pmAmt;
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
	@Column(name = "RECHARGE_AMT",nullable = true)
	public BigDecimal getRechargeAmt() {
		return rechargeAmt;
	}

	public void setRechargeAmt(BigDecimal rechargeAmt) {
		this.rechargeAmt = rechargeAmt;
	}

}