package com.qfw.platform.model.json;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 投资信息
 * 
 * @author Administrator
 * 
 */
public class Loan implements Serializable {

	private static final long serialVersionUID = -3236152480877658268L;
	
	/**
	 * 借款申请ID
	 */
	private Integer loanApproveId; 

	/**
	 * 借款图标
	 */
	private String loanIco;

	/**
	 * 借款产品
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
	 * 借款金额
	 */
	private BigDecimal applyAmt;

	/**
	 * 期限
	 */
	private Integer tenderTerm;

	/**
	 * 完成进度
	 */
	private BigDecimal completeness;

	/**
	 * 剩余借款金额
	 */
	private BigDecimal loanBalAmt;
	
	/**
	 * 还款方式
	 */
	private String repayTypeCd;
	
	/**
	 * 状态
	 */
	private String loanStatusCd;

	public Loan() {

	}

	public Integer getLoanApproveId() {
		return loanApproveId;
	}

	public void setLoanApproveId(Integer loanApproveId) {
		this.loanApproveId = loanApproveId;
	}

	public String getLoanIco() {
		return loanIco;
	}

	public void setLoanIco(String loanIco) {
		this.loanIco = loanIco;
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

	public BigDecimal getApplyAmt() {
		return applyAmt;
	}

	public void setApplyAmt(BigDecimal applyAmt) {
		this.applyAmt = applyAmt;
	}

	public Integer getTenderTerm() {
		return tenderTerm;
	}

	public void setTenderTerm(Integer tenderTerm) {
		this.tenderTerm = tenderTerm;
	}

	public BigDecimal getCompleteness() {
		return completeness;
	}

	public void setCompleteness(BigDecimal completeness) {
		this.completeness = completeness;
	}

	public BigDecimal getLoanBalAmt() {
		return loanBalAmt;
	}

	public void setLoanBalAmt(BigDecimal loanBalAmt) {
		this.loanBalAmt = loanBalAmt;
	}

	public String getRepayTypeCd() {
		return repayTypeCd;
	}

	public void setRepayTypeCd(String repayTypeCd) {
		this.repayTypeCd = repayTypeCd;
	}

	public String getLoanStatusCd() {
		return loanStatusCd;
	}

	public void setLoanStatusCd(String loanStatusCd) {
		this.loanStatusCd = loanStatusCd;
	}
}