package com.qfw.platform.model.loan;

import java.math.BigDecimal;
import java.util.Date;

public class LoanRepayInfoVo {
	
	private Integer loanApproveId; //发布编号
	private Integer restPeriods; //剩余期数
	private Date nextRepayDate; //下个还款日
	private BigDecimal sumRepayAmt;//待还本息
	public Integer getLoanApproveId() {
		return loanApproveId;
	}
	public void setLoanApproveId(Integer loanApproveId) {
		this.loanApproveId = loanApproveId;
	}
	public Integer getRestPeriods() {
		return restPeriods;
	}
	public void setRestPeriods(Integer restPeriods) {
		this.restPeriods = restPeriods;
	}
	public Date getNextRepayDate() {
		return nextRepayDate;
	}
	public void setNextRepayDate(Date nextRepayDate) {
		this.nextRepayDate = nextRepayDate;
	}
	public BigDecimal getSumRepayAmt() {
		return sumRepayAmt;
	}
	public void setSumRepayAmt(BigDecimal sumRepayAmt) {
		this.sumRepayAmt = sumRepayAmt;
	}
	
	
	
}
