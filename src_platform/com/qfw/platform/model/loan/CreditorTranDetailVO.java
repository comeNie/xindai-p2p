package com.qfw.platform.model.loan;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 债权转让-标的详情
 * @author Teddy
 *
 */
public class CreditorTranDetailVO implements Serializable{

	private static final long serialVersionUID = 8841603758706289438L;
	
	private String loanMame;// 标题
	private Integer loanCustId;// 客户编号
	private Date settleDate;// 结清日期
	private String repayTypeCd;// 还款方式
	private BigDecimal loanRate;// 年利率
	private String creditRate;// 信用等级
	private Integer surplusPeriod;// 剩余期数
	private Integer tranTtlCount;// 转让份数
	private BigDecimal tranTtlAmt;// 转让总金额
	private Integer tranInCount;// 可接手份数
	private BigDecimal crAmt;// 每份金额
	private BigDecimal completeness;// 进度
	private BigDecimal takeAmt;// 接手奖金

	public String getLoanMame() {
		return loanMame;
	}

	public void setLoanMame(String loanMame) {
		this.loanMame = loanMame;
	}

	public Integer getLoanCustId() {
		return loanCustId;
	}

	public void setLoanCustId(Integer loanCustId) {
		this.loanCustId = loanCustId;
	}

	public Date getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(Date settleDate) {
		this.settleDate = settleDate;
	}

	public String getRepayTypeCd() {
		return repayTypeCd;
	}

	public void setRepayTypeCd(String repayTypeCd) {
		this.repayTypeCd = repayTypeCd;
	}

	public BigDecimal getLoanRate() {
		return loanRate;
	}

	public void setLoanRate(BigDecimal loanRate) {
		this.loanRate = loanRate;
	}

	public String getCreditRate() {
		return creditRate;
	}

	public void setCreditRate(String creditRate) {
		this.creditRate = creditRate;
	}

	public Integer getSurplusPeriod() {
		return surplusPeriod;
	}

	public void setSurplusPeriod(Integer surplusPeriod) {
		this.surplusPeriod = surplusPeriod;
	}

	public Integer getTranTtlCount() {
		return tranTtlCount;
	}

	public void setTranTtlCount(Integer tranTtlCount) {
		this.tranTtlCount = tranTtlCount;
	}

	public BigDecimal getTranTtlAmt() {
		return tranTtlAmt;
	}

	public void setTranTtlAmt(BigDecimal tranTtlAmt) {
		this.tranTtlAmt = tranTtlAmt;
	}

	public Integer getTranInCount() {
		return tranInCount;
	}

	public void setTranInCount(Integer tranInCount) {
		this.tranInCount = tranInCount;
	}

	public BigDecimal getCrAmt() {
		return crAmt;
	}

	public void setCrAmt(BigDecimal crAmt) {
		this.crAmt = crAmt;
	}

	public BigDecimal getCompleteness() {
		return completeness;
	}

	public void setCompleteness(BigDecimal completeness) {
		this.completeness = completeness;
	}

	public BigDecimal getTakeAmt() {
		return takeAmt;
	}

	public void setTakeAmt(BigDecimal takeAmt) {
		this.takeAmt = takeAmt;
	}
  
}