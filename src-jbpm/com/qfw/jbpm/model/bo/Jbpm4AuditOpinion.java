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
@Table(name = "jbpm4_audit_opinion")
public class Jbpm4AuditOpinion implements java.io.Serializable {

	// Fields

	private Integer id;
	private String workItemId;
	private Integer approvalUserId;
	private String approvalUserName;
	private String auditStatusCd;
	private String comment;
	private String remark;
	private Integer approvalRoleId;
	private String approvalRoleName;
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;

	// Constructors

	/** default constructor */
	public Jbpm4AuditOpinion() {
		
	}


	// Property accessors
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

	@Column(name = "APPROVAL_USER_ID")
	public Integer getApprovalUserId() {
		return this.approvalUserId;
	}

	public void setApprovalUserId(Integer approvalUserId) {
		this.approvalUserId = approvalUserId;
	}

	@Column(name = "APPROVAL_USER_NAME", length = 32)
	public String getApprovalUserName() {
		return this.approvalUserName;
	}

	public void setApprovalUserName(String approvalUserName) {
		this.approvalUserName = approvalUserName;
	}

	@Column(name = "AUDIT_STATUS_CD", length = 2)
	public String getAuditStatusCd() {
		return this.auditStatusCd;
	}

	public void setAuditStatusCd(String auditStatusCd) {
		this.auditStatusCd = auditStatusCd;
	}

	@Column(name = "COMMENT", length = 1024)
	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Column(name = "REMARK", length = 1024)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "APPROVAL_ROLE_ID")
	public Integer getApprovalRoleId() {
		return approvalRoleId;
	}

	public void setApprovalRoleId(Integer approvalRoleId) {
		this.approvalRoleId = approvalRoleId;
	}

	@Column(name = "APPROVAL_ROLE_NAME")
	public String getApprovalRoleName() {
		return approvalRoleName;
	}

	public void setApprovalRoleName(String approvalRoleName) {
		this.approvalRoleName = approvalRoleName;
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