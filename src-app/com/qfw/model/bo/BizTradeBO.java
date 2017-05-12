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
 * BizTrade entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "biz_trade")
public class BizTradeBO extends BizBaseBO implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 2711074208957633498L;
	
	private Integer id;
	private String tradeNum;
	private Date tradaTime;
	private String accountNum;
	private BigDecimal accountBal;
	private BigDecimal tradeAmt;
	private String tradeTypeCd;
	private String comment;
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;

	// Constructors

	/** default constructor */
	public BizTradeBO() {
	}

	/** full constructor */
	public BizTradeBO(String tradeNum, Date tradaTime, String accountNum,
			BigDecimal accountBal, BigDecimal tradeAmt, String tradeTypeCd,
			String comment, Integer sysCreateUser, Date sysCreateTime,
			Integer sysUpdateUser, Date sysUpdateTime) {
		this.tradeNum = tradeNum;
		this.tradaTime = tradaTime;
		this.accountNum = accountNum;
		this.accountBal = accountBal;
		this.tradeAmt = tradeAmt;
		this.tradeTypeCd = tradeTypeCd;
		this.comment = comment;
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

	@Column(name = "TRADE_NUM")
	public String getTradeNum() {
		return this.tradeNum;
	}

	public void setTradeNum(String tradeNum) {
		this.tradeNum = tradeNum;
	}

	@Column(name = "TRADA_TIME")
	public Date getTradaTime() {
		return this.tradaTime;
	}

	public void setTradaTime(Date tradaTime) {
		this.tradaTime = tradaTime;
	}

	@Column(name = "ACCOUNT_NUM")
	public String getAccountNum() {
		return this.accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	@Column(name = "ACCOUNT_BAL")
	public BigDecimal getAccountBal() {
		return this.accountBal;
	}

	public void setAccountBal(BigDecimal accountBal) {
		this.accountBal = accountBal;
	}

	@Column(name = "TRADE_AMT")
	public BigDecimal getTradeAmt() {
		return this.tradeAmt;
	}

	public void setTradeAmt(BigDecimal tradeAmt) {
		this.tradeAmt = tradeAmt;
	}

	@Column(name = "TRADE_TYPE_CD")
	public String getTradeTypeCd() {
		return this.tradeTypeCd;
	}

	public void setTradeTypeCd(String tradeTypeCd) {
		this.tradeTypeCd = tradeTypeCd;
	}

	@Column(name = "COMMENT")
	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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