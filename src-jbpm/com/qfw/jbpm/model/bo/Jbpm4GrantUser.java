package com.qfw.jbpm.model.bo;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * 
 */
@Entity
@Table(name = "jbpm4_grant_user")
public class Jbpm4GrantUser implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer grantUserId;
	private Integer grantedUserId;
	private String flowKey;
	private Date startDate;
	private Date endDate;
	private Integer sysCreateUser;
	private Timestamp sysCreateTime;
	private Integer sysUpdateUser;
	private Timestamp sysUpdateTime;

	// Constructors

	/** default constructor */
	public Jbpm4GrantUser() {
		
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

	@Column(name = "GRANT_USER_ID", nullable = false)
	public Integer getGrantUserId() {
		return this.grantUserId;
	}

	public void setGrantUserId(Integer grantUserId) {
		this.grantUserId = grantUserId;
	}

	@Column(name = "GRANTED_USER_ID", nullable = false)
	public Integer getGrantedUserId() {
		return this.grantedUserId;
	}

	public void setGrantedUserId(Integer grantedUserId) {
		this.grantedUserId = grantedUserId;
	}
	
	@Column(name = "FLOW_KEY")
	public String getFlowKey() {
		return flowKey;
	}


	public void setFlowKey(String flowKey) {
		this.flowKey = flowKey;
	}


	@Temporal(TemporalType.DATE)
	@Column(name = "START_DATE", nullable = false, length = 10)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "END_DATE", nullable = false, length = 10)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "SYS_CREATE_USER", nullable = false)
	public Integer getSysCreateUser() {
		return this.sysCreateUser;
	}

	public void setSysCreateUser(Integer sysCreateUser) {
		this.sysCreateUser = sysCreateUser;
	}

	@Column(name = "SYS_CREATE_TIME", nullable = false, length = 19)
	public Timestamp getSysCreateTime() {
		return this.sysCreateTime;
	}

	public void setSysCreateTime(Timestamp sysCreateTime) {
		this.sysCreateTime = sysCreateTime;
	}

	@Column(name = "SYS_UPDATE_USER", nullable = false)
	public Integer getSysUpdateUser() {
		return this.sysUpdateUser;
	}

	public void setSysUpdateUser(Integer sysUpdateUser) {
		this.sysUpdateUser = sysUpdateUser;
	}

	@Column(name = "SYS_UPDATE_TIME")
	@Version
	public Timestamp getSysUpdateTime() {
		return this.sysUpdateTime;
	}

	public void setSysUpdateTime(Timestamp sysUpdateTime) {
		this.sysUpdateTime = sysUpdateTime;
	}

}