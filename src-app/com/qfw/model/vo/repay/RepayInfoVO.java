package com.qfw.model.vo.repay;

import java.math.BigDecimal;

/**
 * 还款明细VO
 * 
 * @author kindion
 * 
 */
public class RepayInfoVO {

	private String repayWayCD;// 还款类型
	private Integer loanId;// 借款ID
	private Integer custId;// 会员ID
	private Integer repayPlanId;// 还款计划ID

	private BigDecimal repayedPrincipalAmt; // 还款总本金
	private BigDecimal repayAmtNormal; // 正常本金
	private BigDecimal repayAmtPre; // 提前还款本金
	private BigDecimal repayedInterestAmt; // 本次还款利息
	private BigDecimal repayedPenaltyAmt; // 本次还款罚息
	private BigDecimal loanChargeAmt;//正常管理费
	private BigDecimal delayChargeAmt;//延期管理费
	private BigDecimal overdueChargeAmt;//逾期管理费
	private BigDecimal prepaymentChargeAmt;//提前还款违约金
	private BigDecimal ttlRepayedAmt; // 本次还款总金额

	public String getRepayWayCD() {
		return repayWayCD;
	}

	public void setRepayWayCD(String repayWayCD) {
		this.repayWayCD = repayWayCD;
	}

	public Integer getLoanId() {
		return loanId;
	}

	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}

	public Integer getRepayPlanId() {
		return repayPlanId;
	}

	public void setRepayPlanId(Integer repayPlanId) {
		this.repayPlanId = repayPlanId;
	}

	public BigDecimal getRepayedPrincipalAmt() {
		return repayedPrincipalAmt==null?BigDecimal.ZERO:repayedPrincipalAmt;
	}

	public void setRepayedPrincipalAmt(BigDecimal repayedPrincipalAmt) {
		this.repayedPrincipalAmt = repayedPrincipalAmt;
	}

	public BigDecimal getRepayedInterestAmt() {
		return repayedInterestAmt==null?BigDecimal.ZERO:repayedInterestAmt;
	}

	public void setRepayedInterestAmt(BigDecimal repayedInterestAmt) {
		this.repayedInterestAmt = repayedInterestAmt;
	}
	

	public BigDecimal getPrepaymentChargeAmt() {
		return prepaymentChargeAmt==null?BigDecimal.ZERO:prepaymentChargeAmt;
	}

	public void setPrepaymentChargeAmt(BigDecimal prepaymentChargeAmt) {
		this.prepaymentChargeAmt = prepaymentChargeAmt;
	}

	public BigDecimal getRepayedPenaltyAmt() {
		return repayedPenaltyAmt==null?BigDecimal.ZERO:repayedPenaltyAmt;
	}

	public void setRepayedPenaltyAmt(BigDecimal repayedPenaltyAmt) {
		this.repayedPenaltyAmt = repayedPenaltyAmt;
	}

	public BigDecimal getTtlRepayedAmt() {
		return ttlRepayedAmt==null?BigDecimal.ZERO:ttlRepayedAmt;
	}

	public void setTtlRepayedAmt(BigDecimal ttlRepayedAmt) {
		this.ttlRepayedAmt = ttlRepayedAmt;
	}

	public BigDecimal getLoanChargeAmt() {
		return loanChargeAmt==null?BigDecimal.ZERO:loanChargeAmt;
	}

	public void setLoanChargeAmt(BigDecimal loanChargeAmt) {
		this.loanChargeAmt = loanChargeAmt;
	}

	public BigDecimal getDelayChargeAmt() {
		return delayChargeAmt==null?BigDecimal.ZERO:delayChargeAmt;
	}

	public void setDelayChargeAmt(BigDecimal delayChargeAmt) {
		this.delayChargeAmt = delayChargeAmt;
	}

	public BigDecimal getOverdueChargeAmt() {
		return overdueChargeAmt==null?BigDecimal.ZERO:overdueChargeAmt;
	}

	public void setOverdueChargeAmt(BigDecimal overdueChargeAmt) {
		this.overdueChargeAmt = overdueChargeAmt;
	}

	public Integer getCustId() {
		return custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	public BigDecimal getRepayAmtNormal() {
		return repayAmtNormal;
	}

	public void setRepayAmtNormal(BigDecimal repayAmtNormal) {
		this.repayAmtNormal = repayAmtNormal;
	}

	public BigDecimal getRepayAmtPre() {
		return repayAmtPre;
	}

	public void setRepayAmtPre(BigDecimal repayAmtPre) {
		this.repayAmtPre = repayAmtPre;
	}

}
