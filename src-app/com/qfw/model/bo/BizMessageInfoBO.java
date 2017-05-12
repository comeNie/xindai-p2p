package com.qfw.model.bo;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BizCost entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "biz_message_info")
public class BizMessageInfoBO extends BizBaseBO implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer sendUserId;
	private Integer recUserId;
	private String contentTxt;
	private String titleTxt;
	private Integer readStatus;
	private Integer messageType;
	
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;
	

	

	// Constructors

	

	

	/** default constructor */
	public BizMessageInfoBO() {
	}

	/** full constructor */
	public BizMessageInfoBO(Integer sendUserId, Integer recUserId,
			String contentTxt, Integer readStatus,
			Integer messageType ,
			Integer sysCreateUser, Timestamp sysCreateTime,
			Integer sysUpdateUser, Timestamp sysUpdateTime) {
		this.sendUserId = sendUserId;
		this.recUserId = recUserId;
		this.contentTxt = contentTxt;
		this.readStatus = readStatus;
		this.messageType = messageType;
		this.sysCreateUser = sysCreateUser;
		this.sysCreateTime = sysCreateTime;
		this.sysUpdateUser = sysUpdateUser;
		this.sysUpdateTime = sysUpdateTime;
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

	@Column(name = "sendUserId")
	public Integer getSendUserId() {
		return sendUserId;
	}

	public void setSendUserId(Integer sendUserId) {
		this.sendUserId = sendUserId;
	}

	@Column(name = "recUserId")
	public Integer getRecUserId() {
		return recUserId;
	}

	public void setRecUserId(Integer recUserId) {
		this.recUserId = recUserId;
	}

	@Column(name = "contentTxt")
	public String getContentTxt() {
		return contentTxt;
	}

	public void setContentTxt(String contentTxt) {
		this.contentTxt = contentTxt;
	}

	@Column(name = "readStatus")
	public Integer getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(Integer readStatus) {
		this.readStatus = readStatus;
	}

	@Column(name = "messageType")
	public Integer getMessageType() {
		return messageType;
	}

	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}
	
	@Column(name = "SYS_CREATE_USER", nullable = true)
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

	@Column(name = "SYS_UPDATE_TIME", nullable = false, length = 19)
	public Date getSysUpdateTime() {
		return this.sysUpdateTime;
	}

	public void setSysUpdateTime(Date sysUpdateTime) {
		this.sysUpdateTime = sysUpdateTime;
	}

	@Column(name = "titleTxt")
	public String getTitleTxt() {
		return titleTxt;
	}

	public void setTitleTxt(String titleTxt) {
		this.titleTxt = titleTxt;
	}

	

}