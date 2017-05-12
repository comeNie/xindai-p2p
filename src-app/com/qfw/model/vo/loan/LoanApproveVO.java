package com.qfw.model.vo.loan;

import java.math.BigDecimal;

public class LoanApproveVO {

	private Integer loanApproveId;//借款发布id
	private String loanName; // 借款标题
	private BigDecimal loanAmt; 	// 标的总额
	private BigDecimal loanBalAmt; // 剩余借款金额
	private BigDecimal loanRate;		// 利率
	private String repayTypeCd;		// 还款方式
	private String loanTypeCd; //表的类型
	private String approveStatusCd; // 状态
	private Integer loanTerm;  //借款期限
	private String termUnitCd; //借款期限单位
	private String creditRate;		// 信用等级
	private BigDecimal  completeness; // 完成进度
	private BigDecimal  completenessRound; // 完成进度
	private String loanIco; // 完成进度
	private String remark;
    private String loanStatusCd;
    
	/**
	 * 每份金额
	 */
	private BigDecimal tenderLimitAmt;
	private String repayTypeCdStr;
	private String approveStatusCdStr;
	
	public BigDecimal getTenderLimitAmt() {
		return tenderLimitAmt;
	}
	public void setTenderLimitAmt(BigDecimal tenderLimitAmt) {
		this.tenderLimitAmt = tenderLimitAmt;
	}
	public String getLoanIco() {
		return loanIco;
	}
	public void setLoanIco(String loanIco) {
		this.loanIco = loanIco;
	}
	public Integer getLoanApproveId() {
		return loanApproveId;
	}
	public void setLoanApproveId(Integer loanApproveId) {
		this.loanApproveId = loanApproveId;
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
	public BigDecimal getLoanBalAmt() {
		return loanBalAmt;
	}
	public void setLoanBalAmt(BigDecimal loanBalAmt) {
		this.loanBalAmt = loanBalAmt;
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
	public String getLoanTypeCd() {
		return loanTypeCd;
	}
	public void setLoanTypeCd(String loanTypeCd) {
		this.loanTypeCd = loanTypeCd;
	}
	public String getApproveStatusCd() {
		return approveStatusCd;
	}
	public void setApproveStatusCd(String approveStatusCd) {
		this.approveStatusCd = approveStatusCd;
	}
	public Integer getLoanTerm() {
		return loanTerm;
	}
	public void setLoanTerm(Integer loanTerm) {
		this.loanTerm = loanTerm;
	}
	public String getTermUnitCd() {
		return termUnitCd;
	}
	public void setTermUnitCd(String termUnitCd) {
		this.termUnitCd = termUnitCd;
	}
	public String getCreditRate() {
		return creditRate;
	}
	public void setCreditRate(String creditRate) {
		this.creditRate = creditRate;
	}
	public BigDecimal getCompleteness() {
		return completeness;
	}
	public void setCompleteness(BigDecimal completeness) {
		this.completeness = completeness;
	}
	public String getRepayTypeCdStr() {
		return repayTypeCdStr;
	}
	public void setRepayTypeCdStr(String repayTypeCdStr) {
		this.repayTypeCdStr = repayTypeCdStr;
	}
	public String getApproveStatusCdStr() {
		return approveStatusCdStr;
	}
	public void setApproveStatusCdStr(String approveStatusCdStr) {
		this.approveStatusCdStr = approveStatusCdStr;
	}
	public BigDecimal getCompletenessRound() {
		return completenessRound;
	}
	public void setCompletenessRound(BigDecimal completenessRound) {
		this.completenessRound = completenessRound;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getLoanStatusCd() {
		return loanStatusCd;
	}
	public void setLoanStatusCd(String loanStatusCd) {
		this.loanStatusCd = loanStatusCd;
	}
}