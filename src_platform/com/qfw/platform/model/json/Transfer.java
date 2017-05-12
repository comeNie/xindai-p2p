package com.qfw.platform.model.json;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 债权转让
 * 
 * @author Administrator
 * 
 */
public class Transfer implements Serializable {

	private static final long serialVersionUID = -4814931779922143535L;

	/**
	 * 债权id
	 */
	private Integer creditorId;

	/**
	 * 借款标题
	 */
	private String loanName;

	/**
	 * 信用等级
	 */
	private String creditLevel;

	/**
	 * 借款年利率
	 */
	private BigDecimal loanRate;

	/**
	 * 还款方式
	 */
	private String repayTypeCd;

	/**
	 * 转让总金额
	 */
	private BigDecimal tranBalAmt;

	/**
	 * 剩余期数
	 */
	private Integer surplusPeriod;

	/**
	 * 接手奖金
	 */
	private BigDecimal takeAmt;

	/**
	 * 进度
	 */
	private BigDecimal completeness;

	public Transfer() {

	}

	public Integer getCreditorId() {
		return creditorId;
	}

	public void setCreditorId(Integer creditorId) {
		this.creditorId = creditorId;
	}

	public String getLoanName() {
		return loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	public String getCreditLevel() {
		return creditLevel;
	}

	public void setCreditLevel(String creditLevel) {
		this.creditLevel = creditLevel;
	}

	public BigDecimal getLoanRate() {
		return loanRate;
	}

	public void setLoanRate(BigDecimal loanRate) {
		this.loanRate = loanRate;
	}

	public String getRepayTypeCd() {
		return repayTypeCd;
	}

	public void setRepayTypeCd(String repayTypeCd) {
		this.repayTypeCd = repayTypeCd;
	}

	public BigDecimal getTranBalAmt() {
		return tranBalAmt;
	}

	public void setTranBalAmt(BigDecimal tranBalAmt) {
		this.tranBalAmt = tranBalAmt;
	}

	public Integer getSurplusPeriod() {
		return surplusPeriod;
	}

	public void setSurplusPeriod(Integer surplusPeriod) {
		this.surplusPeriod = surplusPeriod;
	}

	public BigDecimal getTakeAmt() {
		return takeAmt;
	}

	public void setTakeAmt(BigDecimal takeAmt) {
		this.takeAmt = takeAmt;
	}

	public BigDecimal getCompleteness() {
		return completeness;
	}

	public void setCompleteness(BigDecimal completeness) {
		this.completeness = completeness;
	}
}