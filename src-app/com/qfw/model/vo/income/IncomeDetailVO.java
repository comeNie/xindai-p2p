package com.qfw.model.vo.income;

import java.math.BigDecimal;
import java.util.Date;

public class IncomeDetailVO {

	private Integer id;
	private Integer incomeRelId;
	private String incomeTypeCd;
	private BigDecimal incomeBaseAmt;
	private BigDecimal incomeRate;
	private Integer incomeDay;
	private BigDecimal incomeAmt;
	private Date incomeDate;
	
	private BigDecimal loanAmt; //标的总额
	private String loanName; // 借款标题
	private BigDecimal crAmt; // 债权金额 
	
	private String userCode;	// 登录编号
	private String userName;	// 登录名
	private String custName;	// 真实姓名
	private Integer incomePeriod;// 收益期数
	private Integer totalPeriod;//总期数
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIncomeRelId() {
		return incomeRelId;
	}
	public void setIncomeRelId(Integer incomeRelId) {
		this.incomeRelId = incomeRelId;
	}
	public String getIncomeTypeCd() {
		return incomeTypeCd;
	}
	public void setIncomeTypeCd(String incomeTypeCd) {
		this.incomeTypeCd = incomeTypeCd;
	}
	public BigDecimal getIncomeBaseAmt() {
		return incomeBaseAmt;
	}
	public void setIncomeBaseAmt(BigDecimal incomeBaseAmt) {
		this.incomeBaseAmt = incomeBaseAmt;
	}
	public BigDecimal getIncomeRate() {
		return incomeRate;
	}
	public void setIncomeRate(BigDecimal incomeRate) {
		this.incomeRate = incomeRate;
	}
	public Integer getIncomeDay() {
		return incomeDay;
	}
	public void setIncomeDay(Integer incomeDay) {
		this.incomeDay = incomeDay;
	}
	public BigDecimal getIncomeAmt() {
		return incomeAmt;
	}
	public void setIncomeAmt(BigDecimal incomeAmt) {
		this.incomeAmt = incomeAmt;
	}
	public Date getIncomeDate() {
		return incomeDate;
	}
	public void setIncomeDate(Date incomeDate) {
		this.incomeDate = incomeDate;
	}
	public BigDecimal getLoanAmt() {
		return loanAmt;
	}
	public void setLoanAmt(BigDecimal loanAmt) {
		this.loanAmt = loanAmt;
	}
	public String getLoanName() {
		return loanName;
	}
	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	public BigDecimal getCrAmt() {
		return crAmt;
	}
	public void setCrAmt(BigDecimal crAmt) {
		this.crAmt = crAmt;
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
	public Integer getIncomePeriod() {
		return incomePeriod;
	}
	public void setIncomePeriod(Integer incomePeriod) {
		this.incomePeriod = incomePeriod;
	}
	public Integer getTotalPeriod() {
		return totalPeriod;
	}
	public void setTotalPeriod(Integer totalPeriod) {
		this.totalPeriod = totalPeriod;
	}
}
