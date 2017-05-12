package com.qfw.model.vo.loan.loanApprove;

/**
 * 借款发布查询vo
 *
 * @author kyc
 */
public class LoanApproveSearchVO {
	
	private Integer custId;
	private String loanApplyId;
	private String workItemId;
	public Integer getCustId() {
		return custId;
	}
	public void setCustId(Integer custId) {
		this.custId = custId;
	}
	public String getLoanApplyId() {
		return loanApplyId;
	}
	public void setLoanApplyId(String loanApplyId) {
		this.loanApplyId = loanApplyId;
	}
	public String getWorkItemId() {
		return workItemId;
	}
	public void setWorkItemId(String workItemId) {
		this.workItemId = workItemId;
	}
}
