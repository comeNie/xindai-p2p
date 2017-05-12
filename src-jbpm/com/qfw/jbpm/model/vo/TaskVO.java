package com.qfw.jbpm.model.vo;

import java.io.Serializable;
import java.util.Date;

public class TaskVO implements Serializable {
	
	/**
	 * 
	 * 待执行任务id任务id
	 */
	private Integer taskId;
	/**
	 * 当前流程实例id
	 */
	private String executionId;
	/**
	 * 流程中文名称
	 */
	private String txName;
	/**
	 * 处理时间
	 */
	private Date processDate;
	/**
	 * 当前环节名称
	 */
	private String curTaskName;
	/**
	 * 当前处理人
	 */
	private String curProcessUser;
	/**
	 * 链接地址
	 */
	private String urlPath;
	/**
	 * 申请人名称
	 */
	private String applyUserName;
	/**
	 * 申请时间
	 */
	private Date applyTime;
	/**
	 * 申请部门id
	 */
	private Integer deptId;
	/**
	 * 申请部门名称
	 */
	private String deptName;
	/**
	 * 任务描述
	 */
	private String remark;
	
	public TaskVO() {
		super();
	}
	
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public String getExecutionId() {
		return executionId;
	}
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	public String getTxName() {
		return txName;
	}
	public void setTxName(String txName) {
		this.txName = txName;
	}
	public Date getProcessDate() {
		return processDate;
	}
	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}
	public String getCurTaskName() {
		return curTaskName;
	}
	public void setCurTaskName(String curTaskName) {
		this.curTaskName = curTaskName;
	}
	public String getCurProcessUser() {
		return curProcessUser;
	}
	public void setCurProcessUser(String curProcessUser) {
		this.curProcessUser = curProcessUser;
	}
	public String getUrlPath() {
		return urlPath;
	}
	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

	public String getApplyUserName() {
		return applyUserName;
	}

	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
