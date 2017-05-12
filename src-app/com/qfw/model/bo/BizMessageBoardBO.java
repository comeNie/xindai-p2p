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
@Table(name = "biz_message_board")
public class BizMessageBoardBO extends BizBaseBO implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer loanId;
	private Integer remainUser;
	private Integer replyUser;
	private Integer linkId;
	private String content;
	
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;
	

	/** default constructor */
	public BizMessageBoardBO() {
	}

	/** full constructor */
	public BizMessageBoardBO(Integer loanId, Integer remainUser,
			Integer replyUser, Integer linkId,
			String content ,
			Integer sysCreateUser, Timestamp sysCreateTime,
			Integer sysUpdateUser, Timestamp sysUpdateTime) {
		this.loanId = loanId;
		this.remainUser = remainUser;
		this.replyUser = replyUser;
		this.linkId = linkId;
		this.content = content;
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

	@Column(name = "loanId")
	public Integer getLoanId() {
		return loanId;
	}

	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}

	@Column(name = "remainUser")
	public Integer getRemainUser() {
		return remainUser;
	}

	public void setRemainUser(Integer remainUser) {
		this.remainUser = remainUser;
	}

	@Column(name = "replyUser", nullable = true)
	public Integer getReplyUser() {
		return replyUser;
	}

	public void setReplyUser(Integer replyUser) {
		this.replyUser = replyUser;
	}

	@Column(name = "linkId", nullable = true)
	public Integer getLinkId() {
		return linkId;
	}

	public void setLinkId(Integer linkId) {
		this.linkId = linkId;
	}

	@Column(name = "content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	

}