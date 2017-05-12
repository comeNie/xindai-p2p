package com.qfw.model.bo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * BizTrade entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "biz_withdrawals")
public class BizWithdrawalsBO extends BizBaseBO implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 2711074208957633498L;

	private Integer id;
	private Integer custId;
	private String outAccount;
	private String inputAccount;
	private String tradeNum;
	private BigDecimal txAmt;
	private BigDecimal feeAmt;
	private Date txDate;
	private String ctrStateCd;
	private String workItemId;
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;
	
	private String bankName;

	// Constructors

	/** default constructor */
	public BizWithdrawalsBO() {
	}

	/** full constructor */
	public BizWithdrawalsBO( Integer custId, String outAccount, String inputAccount,
		 String tradeNum,BigDecimal txAmt,BigDecimal feeAmt,Date txDate,
		 String ctrStateCd,String workItemId, Integer sysCreateUser, Date sysCreateTime,
			Integer sysUpdateUser, Date sysUpdateTime) {
		this.custId = custId;
		this.outAccount = outAccount;
		this.inputAccount = inputAccount;
		this.tradeNum = tradeNum;
		this.txAmt =txAmt;
		this.feeAmt = feeAmt;
		this.txDate = txDate;
		this.ctrStateCd = ctrStateCd;
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

	@Column(name = "CUST_ID")
	public Integer getCustId() {
		return this.custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}
	
	@Column(name = "TRADE_NUM")
	public String getTradeNum() {
		return this.tradeNum;
	}

	public void setTradeNum(String tradeNum) {
		this.tradeNum = tradeNum;
	}

	@Column(name = "OUT_ACCOUNT")
	public String getOutAccount() {
		return outAccount;
	}

	public void setOutAccount(String outAccount) {
		this.outAccount = outAccount;
	}
	
	@Column(name = "INPUT_ACCOUNT")
	public String getInputAccount() {
		return inputAccount;
	}

	public void setInputAccount(String inputAccount) {
		this.inputAccount = inputAccount;
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

	@Temporal(TemporalType.DATE)
	@Column(name = "TX_DATE")
	public Date getTxDate() {
		return txDate;
	}

	public void setTxDate(Date txDate) {
		this.txDate = txDate;
	}

	@Column(name = "CTR_STATE_CD")
	public String getCtrStateCd() {
		return ctrStateCd;
	}

	public void setCtrStateCd(String ctrStateCd) {
		this.ctrStateCd = ctrStateCd;
	}

	@Column(name = "WORK_ITEM_ID")
	public String getWorkItemId() {
		return workItemId;
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

	@Column(name = "SYS_UPDATE_TIME")
	public Date getSysUpdateTime() {
		return this.sysUpdateTime;
	}

	public void setSysUpdateTime(Date sysUpdateTime) {
		this.sysUpdateTime = sysUpdateTime;
	}
	@Column(name = "BANK_NAME")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

}