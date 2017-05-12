package com.qfw.platform.model.loan;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

public class LoanDueDtlVo implements Serializable {
 
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 借款标题
	 */
	private String loanName;
	
	/**
	 * 借款金额
	 */
	private BigDecimal loanAmt;
	
	/**
	 * 借款余额
	 */
	private BigDecimal loanBalAmt;
	
	/**
	 * 借款期限
	 */
	private Integer loanTerm;
	
	/**
	 * 还款方式
	 */
	private String repayTypeCd;
	
	/**
	 * 年利率
	 */
	private BigDecimal loanRate;
	
	/**
	 * 担保方式
	 */
	private String loanTypeCd;
	
	/**
	 * 登陆号
	 */
	private String loginNo;

	
	/**
	 * 总期数
	 */
	private Integer totalPeriod;
	
	/**
	 * 剩余期数
	 */
	private Integer surplusPeriod;
	
	
	/**
	 * 放款日期
	 */
	private String loanDate;
		
	
	/**
	 * 放到期日期日期
	 */
	private String loanDueDate;


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


	public BigDecimal getLoanBalAmt() {
		return loanBalAmt;
	}


	public void setLoanBalAmt(BigDecimal loanBalAmt) {
		this.loanBalAmt = loanBalAmt;
	}


	public Integer getLoanTerm() {
		return loanTerm;
	}


	public void setLoanTerm(Integer loanTerm) {
		this.loanTerm = loanTerm;
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


	public String getLoanTypeCd() {
		return loanTypeCd;
	}


	public void setLoanTypeCd(String loanTypeCd) {
		this.loanTypeCd = loanTypeCd;
	}


	public String getLoginNo() {
		return loginNo;
	}


	public void setLoginNo(String loginNo) {
		this.loginNo = loginNo;
	}


	public Integer getTotalPeriod() {
		return totalPeriod;
	}


	public void setTotalPeriod(Integer totalPeriod) {
		this.totalPeriod = totalPeriod;
	}


	public Integer getSurplusPeriod() {
		return surplusPeriod;
	}


	public void setSurplusPeriod(Integer surplusPeriod) {
		this.surplusPeriod = surplusPeriod;
	}


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

	

}
