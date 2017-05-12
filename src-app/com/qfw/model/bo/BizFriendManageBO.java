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
@Table(name = "biz_friend_manage")
public class BizFriendManageBO extends BizBaseBO implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userId;
	private Integer friendId;
	
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;
	
	
	// Constructors

	

	/** default constructor */
	public BizFriendManageBO() {
	}

	/** full constructor */
	public BizFriendManageBO(Integer id, 
			Integer friendId, 
			Integer sysCreateUser, Timestamp sysCreateTime,
			Integer sysUpdateUser, Timestamp sysUpdateTime) {
		this.userId = userId;
		this.friendId = friendId;
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

	
	@Column(name = "userId")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "friendId")
	public Integer getFriendId() {
		return friendId;
	}

	public void setFriendId(Integer friendId) {
		this.friendId = friendId;
	}

	

}