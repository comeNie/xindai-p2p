package com.qfw.platform.model.loan;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 还款计划
 * @author Think
 *
 */
public class RepayPlanDtlVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer period;// 当前期数
	private Date repayplanDate;// 还款日期,
	private BigDecimal principalAmt;// 应还本金
	private BigDecimal interestAmt;// 应还利息
	
	private String repayStatusCd;// 还款状态

 
	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public Date getRepayplanDate() {
		return repayplanDate;
	}

	public void setRepayplanDate(Date repayplanDate) {
		this.repayplanDate = repayplanDate;
	}

	public BigDecimal getPrincipalAmt() {
		return principalAmt;
	}

	public void setPrincipalAmt(BigDecimal principalAmt) {
		this.principalAmt = principalAmt;
	}

	public BigDecimal getInterestAmt() {
		return interestAmt;
	}

	public void setInterestAmt(BigDecimal interestAmt) {
		this.interestAmt = interestAmt;
	}

	public String getRepayStatusCd() {
		return repayStatusCd;
	}

	public void setRepayStatusCd(String repayStatusCd) {
		this.repayStatusCd = repayStatusCd;
	}
	
}