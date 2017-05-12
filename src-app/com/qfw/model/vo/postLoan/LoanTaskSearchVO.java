package com.qfw.model.vo.postLoan;

import java.util.Date;

import com.qfw.common.model.permission.SysUser;

public class LoanTaskSearchVO {
	private String custName; // 用户真名

	private int postLoanStatus = -1; // 还款状态

	private Date finishDateFrom; // 完成日期从

	private Date finishDateTo; // 完成日期到

	private SysUser manager ; // 客户经理


	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public Date getFinishDateFrom() {
		return finishDateFrom;
	}

	public void setFinishDateFrom(Date finishDateFrom) {
		this.finishDateFrom = finishDateFrom;
	}

	public Date getFinishDateTo() {
		return finishDateTo;
	}

	public void setFinishDateTo(Date finishDateTo) {
		this.finishDateTo = finishDateTo;
	}

	public SysUser getManager() {
		return manager;
	}

	public void setManager(SysUser manager) {
		this.manager = manager;
	}

	public int getPostLoanStatus() {
		return postLoanStatus;
	}

	public void setPostLoanStatus(int postLoanStatus) {
		this.postLoanStatus = postLoanStatus;
	}

}
