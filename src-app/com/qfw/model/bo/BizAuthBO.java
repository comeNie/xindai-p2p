package com.qfw.model.bo;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * BizAuth entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "biz_auth")
public class BizAuthBO implements java.io.Serializable {

	// Fields

	private Integer id;
	private String relId;
	private String relTypeCd;
	//private Integer custId;
	private String authItemCd;
	private String authStatusCd;
	private Date submitDate;
	private Date passDate;
	private String workItemId;
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;

	// Constructors

	/** default constructor */
	public BizAuthBO() {
	}

	
	public BizAuthBO(String authItemCd) {
		super();
		this.authItemCd = authItemCd;
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
	
	@Column(name = "REL_ID")
	public String getRelId() {
		return relId;
	}


	public void setRelId(String relId) {
		this.relId = relId;
	}

	@Column(name = "REL_TYPE_CD")
	public String getRelTypeCd() {
		return relTypeCd;
	}


	public void setRelTypeCd(String relTypeCd) {
		this.relTypeCd = relTypeCd;
	}


	/*@Column(name = "CUST_ID", nullable = false)
	public Integer getCustId() {
		return this.custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}*/

	@Column(name = "AUTH_ITEM_CD")
	public String getAuthItemCd() {
		return this.authItemCd;
	}

	public void setAuthItemCd(String authItemCd) {
		this.authItemCd = authItemCd;
	}

	@Column(name = "AUTH_STATUS_CD")
	public String getAuthStatusCd() {
		return this.authStatusCd;
	}

	public void setAuthStatusCd(String authStatusCd) {
		this.authStatusCd = authStatusCd;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "SUBMIT_DATE")
	public Date getSubmitDate() {
		return this.submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "PASS_DATE")
	public Date getPassDate() {
		return this.passDate;
	}

	public void setPassDate(Date passDate) {
		this.passDate = passDate;
	}

	@Column(name = "WORK_ITEM_ID")
	public String getWorkItemId() {
		return this.workItemId;
	}

	public void setWorkItemId(String workItemId) {
		this.workItemId = workItemId;
	}

	@Column(name = "SYS_CREATE_USER")
	public Integer getSysCreateUser() {
		return this.sysCreateUser;
	}

	public void setSysCreateUser(Integer sysCreateUser) {
		this.sysCreateUser = sysCreateUser;
	}

	@Column(name = "SYS_CREATE_TIME")
	public Date getSysCreateTime() {
		return this.sysCreateTime;
	}

	public void setSysCreateTime(Date sysCreateTime) {
		this.sysCreateTime = sysCreateTime;
	}

	@Column(name = "SYS_UPDATE_USER")
	public Integer getSysUpdateUser() {
		return this.sysUpdateUser;
	}

	public void setSysUpdateUser(Integer sysUpdateUser) {
		this.sysUpdateUser = sysUpdateUser;
	}

	@Version
	@Column(name = "SYS_UPDATE_TIME")
	public Date getSysUpdateTime() {
		return this.sysUpdateTime;
	}

	public void setSysUpdateTime(Date sysUpdateTime) {
		this.sysUpdateTime = sysUpdateTime;
	}

}