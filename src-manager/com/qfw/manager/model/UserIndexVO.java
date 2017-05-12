package com.qfw.manager.model;

import java.math.BigDecimal;

/**
 * 会员中心-首页VO
 * @author Think
 *
 */
public class UserIndexVO {

	// 我的平台币
	// 安全等级
	
	private Integer custId;// 客户编号
	private BigDecimal accountBalAmt;// 账户余额
	private BigDecimal freezeBalAmt;// 冻结余额
	private BigDecimal usableBalAmt;// 可用余额
	private BigDecimal unretrieveAmt;// 理财资产
	private BigDecimal loanBalAmt;// 借款负债
	private BigDecimal pmAmt;//pm币
	private BigDecimal accoutPureAmt;// 账户净资产 = 理财资产 - 借款负债 + 账户余额
	private String referee_code;//我的推荐码
	private String isVip;

	public Integer getCustId() {
		return custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	public BigDecimal getAccountBalAmt() {
		return accountBalAmt;
	}

	public void setAccountBalAmt(BigDecimal accountBalAmt) {
		this.accountBalAmt = accountBalAmt;
	}

	public BigDecimal getFreezeBalAmt() {
		return freezeBalAmt;
	}

	public void setFreezeBalAmt(BigDecimal freezeBalAmt) {
		this.freezeBalAmt = freezeBalAmt;
	}

	public BigDecimal getUsableBalAmt() {
		return usableBalAmt;
	}

	public void setUsableBalAmt(BigDecimal usableBalAmt) {
		this.usableBalAmt = usableBalAmt;
	}

	public BigDecimal getUnretrieveAmt() {
		return unretrieveAmt;
	}

	public void setUnretrieveAmt(BigDecimal unretrieveAmt) {
		this.unretrieveAmt = unretrieveAmt;
	}

	public BigDecimal getLoanBalAmt() {
		return loanBalAmt;
	}

	public void setLoanBalAmt(BigDecimal loanBalAmt) {
		this.loanBalAmt = loanBalAmt;
	}

	public BigDecimal getAccoutPureAmt() {
		return accoutPureAmt;
	}

	public void setAccoutPureAmt(BigDecimal accoutPureAmt) {
		this.accoutPureAmt = accoutPureAmt;
	}

	public BigDecimal getPmAmt() {
		return pmAmt;
	}

	public void setPmAmt(BigDecimal pmAmt) {
		this.pmAmt = pmAmt;
	}

	public String getReferee_code() {
		return referee_code;
	}

	public void setReferee_code(String referee_code) {
		this.referee_code = referee_code;
	}

	public String getIsVip() {
		return isVip;
	}

	public void setIsVip(String isVip) {
		this.isVip = isVip;
	}
	
}
