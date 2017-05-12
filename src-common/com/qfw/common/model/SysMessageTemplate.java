package com.qfw.common.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "SYS_MESSAGE_TEMPLATE")
public class SysMessageTemplate implements java.io.Serializable {


	private static final long serialVersionUID = 1L;
	private Integer id;
	private String msgTmpName;
	private String msgTmpCode;
	private String msgTmpSubject;
	private String msgTmpContent;
	private String msgTmpTypeCd;
	private String eventType;
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;

	// Constructors

	/** default constructor */
	public SysMessageTemplate() {
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

	@Column(name = "MSG_TMP_NAME", length = 32)
	public String getMsgTmpName() {
		return this.msgTmpName;
	}

	public void setMsgTmpName(String msgTmpName) {
		this.msgTmpName = msgTmpName;
	}

	@Column(name = "MSG_TMP_CODE", length = 32)
	public String getMsgTmpCode() {
		return this.msgTmpCode;
	}

	public void setMsgTmpCode(String msgTmpCode) {
		this.msgTmpCode = msgTmpCode;
	}
	
	@Column(name = "MSG_TMP_SUBJECT", length = 500)
	public String getMsgTmpSubject() {
		return msgTmpSubject;
	}

	public void setMsgTmpSubject(String msgTmpSubject) {
		this.msgTmpSubject = msgTmpSubject;
	}

	@Column(name = "MSG_TMP_CONTENT")
	public String getMsgTmpContent() {
		return this.msgTmpContent;
	}

	public void setMsgTmpContent(String msgTmpContent) {
		this.msgTmpContent = msgTmpContent;
	}

	@Column(name = "MSG_TMP_TYPE_CD", length = 2)
	public String getMsgTmpTypeCd() {
		return this.msgTmpTypeCd;
	}

	public void setMsgTmpTypeCd(String msgTmpTypeCd) {
		this.msgTmpTypeCd = msgTmpTypeCd;
	}
	
	@Column(name = "EVENT_TYPE", length = 2)
	public String getEventType() {
		return eventType;
	}


	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	@Column(name = "SYS_CREATE_USER")
	public Integer getSysCreateUser() {
		return this.sysCreateUser;
	}

	public void setSysCreateUser(Integer sysCreateUser) {
		this.sysCreateUser = sysCreateUser;
	}

	@Column(name = "SYS_CREATE_TIME", length = 19)
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

	@Column(name = "SYS_UPDATE_TIME")
	@Version
	public Date getSysUpdateTime() {
		return this.sysUpdateTime;
	}

	public void setSysUpdateTime(Date sysUpdateTime) {
		this.sysUpdateTime = sysUpdateTime;
	}

}