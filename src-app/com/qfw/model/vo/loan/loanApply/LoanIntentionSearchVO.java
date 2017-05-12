package com.qfw.model.vo.loan.loanApply;

import java.util.Date;

/**
 * 借款管理vo
 *
 * @author kyc
 */
public class LoanIntentionSearchVO {

	private Integer loanId; // 借据id
	private Integer loanApplyId;	//借款申请id
	private String account; // 账户
	private String custId;	// 客户id
	
	private String loanTerm; // 期限
	
	private String custName; // 客户名称
	private String applyStatusCd; // 状态
	private String fenPeiStatus;//
	private Date startDate;		// 申请开始时间
	private Date endDate;		// 申请结束时间
	
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
	public Integer getLoanApplyId() {
		return loanApplyId;
	}
	public void setLoanApplyId(Integer loanApplyId) {
		this.loanApplyId = loanApplyId;
	}
	
}
