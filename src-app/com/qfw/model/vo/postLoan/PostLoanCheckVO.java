package com.qfw.model.vo.postLoan;

import java.util.Date;

public class PostLoanCheckVO {
	private Integer logId;

	private Integer taskId;

	private Date checkDate;

	private Integer checkMode;

	private Integer checkTarget;

	private String remark;

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public Integer getCheckMode() {
		return checkMode;
	}

	public void setCheckMode(Integer checkMode) {
		this.checkMode = checkMode;
	}

	public Integer getCheckTarget() {
		return checkTarget;
	}

	public void setCheckTarget(Integer checkTarget) {
		this.checkTarget = checkTarget;
	}

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

}
