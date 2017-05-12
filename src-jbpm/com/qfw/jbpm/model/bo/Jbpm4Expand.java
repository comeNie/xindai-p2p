package com.qfw.jbpm.model.bo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Version;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "jbpm4_expand")
public class Jbpm4Expand implements java.io.Serializable {

	// Fields

	private Integer id;
	private String workItemId;
	private Integer applyUserId;
	private String applyUserName;
	
	/**
	 * 申请部门id
	 */
	private Integer deptId;
	/**
	 * 申请部门名称
	 */
	private String deptName;

	private String status;
	private String remark;
	private String html;
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;
	

	/** default constructor */
	public Jbpm4Expand() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "WORK_ITEM_ID")
	public String getWorkItemId() {
		return this.workItemId;
	}

	public void setWorkItemId(String workItemId) {
		this.workItemId = workItemId;
	}

	@Column(name = "APPLY_USER_ID")
	public Integer getApplyUserId() {
		return this.applyUserId;
	}

	public void setApplyUserId(Integer applyUserId) {
		this.applyUserId = applyUserId;
	}

	@Column(name = "APPLY_USER_NAME", length = 32)
	public String getApplyUserName() {
		return this.applyUserName;
	}

	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}
	
	@Column(name = "DEPT_ID")
	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	
	@Column(name = "DEPT_NAME")
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "STATUS", length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "REMARK", length = 1024)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "HTML")
	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	@Column(name = "SYS_CREATE_USER", nullable = false)
	public Integer getSysCreateUser() {
		return this.sysCreateUser;
	}

	public void setSysCreateUser(Integer sysCreateUser) {
		this.sysCreateUser = sysCreateUser;
	}

	@Column(name = "SYS_CREATE_TIME", nullable = false, length = 19)
	public Date getSysCreateTime() {
		return this.sysCreateTime;
	}

	public void setSysCreateTime(Date sysCreateTime) {
		this.sysCreateTime = sysCreateTime;
	}

	@Column(name = "SYS_UPDATE_USER", nullable = false)
	public Integer getSysUpdateUser() {
		return this.sysUpdateUser;
	}

	public void setSysUpdateUser(Integer sysUpdateUser) {
		this.sysUpdateUser = sysUpdateUser;
	}

	@Version
	@Column(name = "SYS_UPDATE_TIME", length = 19)
	public Date getSysUpdateTime() {
		return this.sysUpdateTime;
	}

	public void setSysUpdateTime(Date sysUpdateTime) {
		this.sysUpdateTime = sysUpdateTime;
	}

}