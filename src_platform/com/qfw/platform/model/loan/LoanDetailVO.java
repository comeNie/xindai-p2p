package com.qfw.platform.model.loan;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 标的详情
 * @author Think
 */
public class LoanDetailVO implements Serializable{

	private static final long serialVersionUID = 8841603758706289438L;
	
	/**
	 * 借款编号
	 */
	private Integer loanApproveId;
	
	/**
	 * 借款标题
	 */
	private String loanName;
	
	/**
	 * 借款金额
	 */
	private BigDecimal loanAmt;
	
	/**
	 * 借款期限
	 */
	private Integer loanTerm;
	
	/**
	 * 还款方式
	 */
	private String repayTypeCd;
	private String loanStatusCd;
	
	/**
	 * 年利率
	 */
	private BigDecimal loanRate;
	
	/**
	 * 发布状态
	 */
	private String approveStatusCd;
	
	/**
	 * 完成进度
	 */
	private BigDecimal completeness;
	
	/**
	 * 信用等级
	 */
	private String creditRate;
	
	/**
	 * 用户id
	 */
	private Integer custId;
	
	/**
	 * id
	 */
	private Integer id;
	
	/**
	 * 产品名称
	 */
	private String productName;
	
	
	/**
	 * 担保方式
	 */
	private String loanTypeCd;
	
	/**
	 * 剩余金额
	 */
	private BigDecimal tenderBalAmt;
	
	/**
	 * 剩余份数
	 */
	private Integer tenderBalCount;
	
	/**
	 * 剩余天数
	 */
	private Integer remainDay;
	
	/**
	 * 登陆号
	 */
	private String userCode;
	
	/**
	 * 每份金额
	 */
	private BigDecimal tenderLimitAmt;
	
	/**
	 * 每份金额
	 */
	private String remark;

	
	
	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public BigDecimal getTenderLimitAmt() {
		return tenderLimitAmt;
	}


	public void setTenderLimitAmt(BigDecimal tenderLimitAmt) {
		this.tenderLimitAmt = tenderLimitAmt;
	}


	public String getUserCode() {
		return userCode;
	}


	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}


	public LoanDetailVO(){
		
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


	public String getApproveStatusCd() {
		return approveStatusCd;
	}


	public void setApproveStatusCd(String approveStatusCd) {
		this.approveStatusCd = approveStatusCd;
	}


	public BigDecimal getCompleteness() {
		return completeness;
	}


	public void setCompleteness(BigDecimal completeness) {
		this.completeness = completeness;
	}


	public String getCreditRate() {
		return creditRate;
	}


	public void setCreditRate(String creditRate) {
		this.creditRate = creditRate;
	}


	public Integer getCustId() {
		return custId;
	}


	public void setCustId(Integer custId) {
		this.custId = custId;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public String getLoanTypeCd() {
		return loanTypeCd;
	}


	public void setLoanTypeCd(String loanTypeCd) {
		this.loanTypeCd = loanTypeCd;
	}


 

	public BigDecimal getTenderBalAmt() {
		return tenderBalAmt;
	}


	public void setTenderBalAmt(BigDecimal tenderBalAmt) {
		this.tenderBalAmt = tenderBalAmt;
	}


	public Integer getTenderBalCount() {
		return tenderBalCount;
	}


	public void setTenderBalCount(Integer tenderBalCount) {
		this.tenderBalCount = tenderBalCount;
	}


	public Integer getRemainDay() {
		return remainDay;
	}


	public void setRemainDay(Integer remainDay) {
		this.remainDay = remainDay;
	}


	public Integer getLoanApproveId() {
		return loanApproveId;
	}


	public void setLoanApproveId(Integer loanApproveId) {
		this.loanApproveId = loanApproveId;
	}


	public String getLoanStatusCd() {
		return loanStatusCd;
	}


	public void setLoanStatusCd(String loanStatusCd) {
		this.loanStatusCd = loanStatusCd;
	}
	
}