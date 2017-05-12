package com.qfw.platform.model.loan;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 信用报告
 * @author Think
 *
 */
public class CreditReportDetailVO implements Serializable{

	private static final long serialVersionUID = 1L;
	private BigDecimal creditAmt;// 信用额度
	private BigDecimal remainAmt;//	剩余额度
	private Integer applyLoanNum;// 申请借款笔数
	private Integer approveNum;// 成功借款笔数
	private Integer payOffNum;// 还清笔数
	private BigDecimal loanTolAmt;// 借款总金额
	private BigDecimal loanBal;// 借款余额
	private BigDecimal overdueAmt;// 逾期总额
	private Integer overdueNum;// 逾期次数
	private Integer serOverdueNum;// 严重逾期
	public BigDecimal getLoanBal() {
		return loanBal;
	}

	public void setLoanBal(BigDecimal loanBal) {
		this.loanBal = loanBal;
	}

	public BigDecimal getCreditAmt() {
		return creditAmt;
	}

	public void setCreditAmt(BigDecimal creditAmt) {
		this.creditAmt = creditAmt;
	}

	public BigDecimal getRemainAmt() {
		return remainAmt;
	}

	public void setRemainAmt(BigDecimal remainAmt) {
		this.remainAmt = remainAmt;
	}

	public Integer getApplyLoanNum() {
		return applyLoanNum;
	}

	public void setApplyLoanNum(Integer applyLoanNum) {
		this.applyLoanNum = applyLoanNum;
	}

	public Integer getApproveNum() {
		return approveNum;
	}

	public void setApproveNum(Integer approveNum) {
		this.approveNum = approveNum;
	}

	public Integer getPayOffNum() {
		return payOffNum;
	}

	public void setPayOffNum(Integer payOffNum) {
		this.payOffNum = payOffNum;
	}

	public BigDecimal getLoanTolAmt() {
		return loanTolAmt;
	}

	public void setLoanTolAmt(BigDecimal loanTolAmt) {
		this.loanTolAmt = loanTolAmt;
	}

	public BigDecimal getOverdueAmt() {
		return overdueAmt;
	}

	public void setOverdueAmt(BigDecimal overdueAmt) {
		this.overdueAmt = overdueAmt;
	}

	public Integer getOverdueNum() {
		return overdueNum;
	}

	public void setOverdueNum(Integer overdueNum) {
		this.overdueNum = overdueNum;
	}

	public Integer getSerOverdueNum() {
		return serOverdueNum;
	}

	public void setSerOverdueNum(Integer serOverdueNum) {
		this.serOverdueNum = serOverdueNum;
	}
 
	 
}