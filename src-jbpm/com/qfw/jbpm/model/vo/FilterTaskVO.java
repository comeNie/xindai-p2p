package com.qfw.jbpm.model.vo;

import java.io.Serializable;
import java.util.Date;

public class FilterTaskVO implements Serializable {
	
	
	/**
	 * 流程定义id
	 */
	private String procdefId;
	
	/**
	 * 流程开始处理时间
	 */
	private Date startDate;
	/**
	 * 流程结束时间
	 */
	private Date endDate;
	
	/**
	 * 申请人
	 */
	private String userId;
	
	/**
	 * 提交人
	 */
	private String submitUserId;

	public String getProcdefId() {
		return procdefId;
	}

	public void setProcdefId(String procdefId) {
		this.procdefId = procdefId;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSubmitUserId() {
		return submitUserId;
	}

	public void setSubmitUserId(String submitUserId) {
		this.submitUserId = submitUserId;
	}
	
}
