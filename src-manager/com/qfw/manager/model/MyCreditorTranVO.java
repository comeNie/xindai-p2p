package com.qfw.manager.model;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * 会员中心-债权转让VO
 * @author Teddy
 */
public class MyCreditorTranVO {

	private Integer id;// ID
	private String loanName;// 借款标题
	private BigDecimal loanAmt;// 标的总额
	private BigDecimal crAmt;// 债权金额
	private BigDecimal loanRate;// 年利率
	private BigDecimal unretrieveAmt;// 待收本金[可转让金额]
	private Integer turnoverCount;// 成交份数
	private Integer canTranCount;// 可转让份数 = 待收本金/(债权金额/成交份数)
	private Integer tranCount;// 转让份数
	private BigDecimal tranAmt;// 转让金额
	private BigDecimal receiveBonus;// 接手奖金
	private BigDecimal tranFee;// 转让管理费
	private String crStatusCd;//状态
	private Integer surplusPeriod;//剩余期数
	private String loanDate;//债权到期日
	private String loanDueDate;//债权到期日
	private Integer remainDay;//剩余天数
	
	
	
	public String getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}

	public String getLoanDueDate() {
		return loanDueDate;
	}

	public void setLoanDueDate(String loanDueDate) {
		this.loanDueDate = loanDueDate;
	}

	public Integer getRemainDay() {
		return remainDay;
	}

	public void setRemainDay(Integer remainDay) {
		this.remainDay = remainDay;
	}

	public String getCrStatusCd() {
		return crStatusCd;
	}

	public void setCrStatusCd(String crStatusCd) {
		this.crStatusCd = crStatusCd;
	}

	 

	public Integer getSurplusPeriod() {
		return surplusPeriod;
	}

	public void setSurplusPeriod(Integer surplusPeriod) {
		this.surplusPeriod = surplusPeriod;
	}

	 

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLoanName() {
		return loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	public BigDecimal getLoanAmt() {
		return loanAmt;
	}

	public void setLoanAmt(BigDecimal loanAmt) {
		this.loanAmt = loanAmt;
	}

	public BigDecimal getCrAmt() {
		return crAmt;
	}

	public void setCrAmt(BigDecimal crAmt) {
		this.crAmt = crAmt;
	}

	public BigDecimal getLoanRate() {
		return loanRate;
	}

	public void setLoanRate(BigDecimal loanRate) {
		this.loanRate = loanRate;
	}

	public BigDecimal getUnretrieveAmt() {
		return unretrieveAmt;
	}

	public void setUnretrieveAmt(BigDecimal unretrieveAmt) {
		this.unretrieveAmt = unretrieveAmt;
	}

	public Integer getTurnoverCount() {
		return turnoverCount;
	}

	public void setTurnoverCount(Integer turnoverCount) {
		this.turnoverCount = turnoverCount;
	}

	public Integer getCanTranCount() {
		return canTranCount;
	}

	public void setCanTranCount(Integer canTranCount) {
		this.canTranCount = canTranCount;
	}

	public Integer getTranCount() {
		return tranCount;
	}

	public void setTranCount(Integer tranCount) {
		this.tranCount = tranCount;
	}

	public BigDecimal getTranAmt() {
		return tranAmt;
	}

	public void setTranAmt(BigDecimal tranAmt) {
		this.tranAmt = tranAmt;
	}

	public BigDecimal getReceiveBonus() {
		return receiveBonus;
	}

	public void setReceiveBonus(BigDecimal receiveBonus) {
		this.receiveBonus = receiveBonus;
	}

	public BigDecimal getTranFee() {
		return tranFee;
	}

	public void setTranFee(BigDecimal tranFee) {
		this.tranFee = tranFee;
	}
	
}
