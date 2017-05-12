package com.qfw.model.vo.transaction;

import java.math.BigDecimal;
import java.util.Date;

public class TradeVO{

	private Integer id;
	private String custId; 	//	 会员号
	private String tradeNum;	
	private Date tradaTime;
	private String accountNum;
	private BigDecimal accountBal;
	private BigDecimal tradeAmt;
	private String tradeTypeCd;	//交易类型
	private String comment;
	private Date startDate;		// 开始时间
	private Date endDate;		// 结束时间
	
	private String userCode;  // 登录编号
	private String userName; // 登录名
	private String custName;  // 真实姓名
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getTradeNum() {
		return tradeNum;
	}
	public void setTradeNum(String tradeNum) {
		this.tradeNum = tradeNum;
	}
	public Date getTradaTime() {
		return tradaTime;
	}
	public void setTradaTime(Date tradaTime) {
		this.tradaTime = tradaTime;
	}
	public String getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	public BigDecimal getAccountBal() {
		return accountBal;
	}
	public void setAccountBal(BigDecimal accountBal) {
		this.accountBal = accountBal;
	}
	public BigDecimal getTradeAmt() {
		return tradeAmt;
	}
	public void setTradeAmt(BigDecimal tradeAmt) {
		this.tradeAmt = tradeAmt;
	}
	public String getTradeTypeCd() {
		return tradeTypeCd;
	}
	public void setTradeTypeCd(String tradeTypeCd) {
		this.tradeTypeCd = tradeTypeCd;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
}
