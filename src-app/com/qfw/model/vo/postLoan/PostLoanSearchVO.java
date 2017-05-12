package com.qfw.model.vo.postLoan;

import java.util.Date;

/**
 * 贷后管理查询vo
 *
 * @author weiqs
 */
public class PostLoanSearchVO {

	private Integer loanId; // 借据id
	private Integer loanApproveId;	//借款发布id
	private String account; // 账户
	
	private String loanTerm; // 期限
	private String loanStatusCd; // 状态
	
	private Integer planId; // 还款计划id
	private String repayStatusCd; // 还款状态
	private String loanName; // 借款标题
	private String custId;	// 客户id
	private String custName; // 客户名称
	private Date startDate;		// 开始时间
	private Date endDate;		// 结束时间
	
	private String seachName;
	
	public String getSeachName() {
		return seachName;
	}
	public void setSeachName(String seachName) {
		this.seachName = seachName;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getLoanTerm() {
		return loanTerm;
	}
	public void setLoanTerm(String loanTerm) {
		this.loanTerm = loanTerm;
	}
	public String getLoanStatusCd() {
		return loanStatusCd;
	}
	public void setLoanStatusCd(String loanStatusCd) {
		this.loanStatusCd = loanStatusCd;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getLoanId() {
		return loanId;
	}
	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public Integer getLoanApproveId() {
		return loanApproveId;
	}
	public void setLoanApproveId(Integer loanApproveId) {
		this.loanApproveId = loanApproveId;
	}
	public Integer getPlanId() {
		return planId;
	}
	public void setPlanId(Integer planId) {
		this.planId = planId;
	}
	public String getRepayStatusCd() {
		return repayStatusCd;
	}
	public void setRepayStatusCd(String repayStatusCd) {
		this.repayStatusCd = repayStatusCd;
	}
	public String getLoanName() {
		return loanName;
	}
	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	
}
