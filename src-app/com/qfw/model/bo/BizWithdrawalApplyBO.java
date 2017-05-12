package com.qfw.model.bo;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BizTrade entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BIZ_WITHDRAW_APPLY")
public class BizWithdrawalApplyBO extends BizBaseBO implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 2711074208957633498L;

	private Integer id;
	private Integer custId;
	private String reqSn;
	private String accountNo;
	private String accountName;
	private String status;
	private BigDecimal txAmt;
	private BigDecimal feeAmt;
	private String txDate;
	private String bankCode;
	private int queryTime;
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;

	// Constructors

	/** default constructor */
	public BizWithdrawalApplyBO() {
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

	@Column(name = "CUST_ID")
	public Integer getCustId() {
		return this.custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}
	

	@Column(name = "TX_AMT")
	public BigDecimal getTxAmt() {
		return txAmt;
	}

	public void setTxAmt(BigDecimal txAmt) {
		this.txAmt = txAmt;
	}

	@Column(name = "FEE_AMT")
	public BigDecimal getFeeAmt() {
		return feeAmt;
	}

	public void setFeeAmt(BigDecimal feeAmt) {
		this.feeAmt = feeAmt;
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

	@Column(name = "ACCOUNT_NO")
	public String getAccountNo() {
		return accountNo;
	}


	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	@Column(name = "ACCOUNT_NAME")
	public String getAccountName() {
		return accountName;
	}


	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "TX_DATE")
	public String getTxDate() {
		return txDate;
	}


	public void setTxDate(String txDate) {
		this.txDate = txDate;
	}

	@Column(name = "BANK_CODE")
	public String getBankCode() {
		return bankCode;
	}


	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	@Column(name = "REQ_SN")
	public String getReqSn() {
		return reqSn;
	}


	public void setReqSn(String reqSn) {
		this.reqSn = reqSn;
	}

	@Column(name = "QUERY_TIME")
	public int getQueryTime() {
		return queryTime;
	}


	public void setQueryTime(int queryTime) {
		this.queryTime = queryTime;
	}


}