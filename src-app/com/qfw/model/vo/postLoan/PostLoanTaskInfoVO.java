package com.qfw.model.vo.postLoan;

import java.util.Date;

public class PostLoanTaskInfoVO {
	private Integer taskId;

	private Integer custId;

	private String custName;

	private Integer postLoanGenerateType;

	private Date startDate;

	private Date finishDate;

	private String manageName;

	private Integer deptId;

	private String deptName;

	private Integer creatorId;

	private String creatorName;

	private Date createDate;

	private Integer postLoanStatus;

	private Integer checkResult;

	private String resultRemark;

	private Integer frozenLimit;

	private Integer frozenBalance;

	private Integer legalMeasure;

	private Boolean frozenLimitB;

	private Boolean frozenBalanceB;

	private Boolean legalMeasureB;
	
	private Integer manageId;

	public Boolean getFrozenBalanceB() {
		return this.frozenBalanceB;
	}

	public void setFrozenBalanceB(Boolean frozenBalanceB) {
		this.frozenBalanceB = frozenBalanceB;
		this.frozenBalance = this.frozenBalanceB ? 1 : 0 ;
	}

	public Boolean getFrozenLimitB() {
		return frozenLimitB;
	}

	public void setFrozenLimitB(Boolean frozenLimitB) {
		this.frozenLimitB = frozenLimitB;
		this.frozenLimit = this.frozenLimitB ? 1 : 0 ;
	}

	public Boolean getLegalMeasureB() {
		return legalMeasureB;
	}

	public void setLegalMeasureB(Boolean legalMeasureB) {
		this.legalMeasureB = legalMeasureB;
		this.legalMeasure = this.legalMeasureB ? 1 : 0 ;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public String getManageName() {
		return manageName;
	}

	public void setManageName(String manageName) {
		this.manageName = manageName;
	}

	public Integer getPostLoanGenerateType() {
		return postLoanGenerateType;
	}

	public void setPostloanGenerateType(Integer postLoanGenerateType) {
		this.postLoanGenerateType = postLoanGenerateType;
	}

	public Integer getPostLoanStatus() {
		return postLoanStatus;
	}

	public void setPostLoanStatus(Integer postLoanStatus) {
		this.postLoanStatus = postLoanStatus;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public Integer getCustId() {
		return custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Integer getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(Integer checkResult) {
		this.checkResult = checkResult;
	}

	public Integer getFrozenBalance() {
		return frozenBalance;
	}

	public void setFrozenBalance(Integer frozenBalance) {
		this.frozenBalance = frozenBalance;
		this.frozenBalanceB = this.frozenBalance == 0 ? false : true;
	}

	public Integer getFrozenLimit() {
		return frozenLimit;
	}

	public void setFrozenLimit(Integer frozenLimit) {
		this.frozenLimit = frozenLimit;
		this.frozenLimitB = this.frozenLimit == 0 ? false : true;
	}

	public Integer getLegalMeasure() {
		return legalMeasure;
	}

	public void setLegalMeasure(Integer legalMeasure) {
		this.legalMeasure = legalMeasure;
		this.legalMeasureB = this.legalMeasure == 0 ? false : true;
	}

	public String getResultRemark() {
		return resultRemark;
	}

	public void setResultRemark(String resultRemark) {
		this.resultRemark = resultRemark;
	}

	public Integer getManageId() {
		return manageId;
	}

	public void setManageId(Integer manageId) {
		this.manageId = manageId;
	}

}
