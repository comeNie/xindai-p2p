package com.qfw.model.bo;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * BizUserOnline entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "biz_user_online")
public class BizUserOnlineBO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer loginUserId;
	private String loginUserName;
	private String loginTypeCd;
	private String loginIp;
	private Date loginTime;
	private Date logoutTime;


	/** default constructor */
	public BizUserOnlineBO() {
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

	@Column(name = "LOGIN_USER_ID")
	public Integer getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(Integer loginUserId) {
		this.loginUserId = loginUserId;
	}

	@Column(name = "LOGIN_USER_NAME")
	public String getLoginUserName() {
		return loginUserName;
	}

	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}

	@Column(name = "LOGIN_TYPE_CD")
	public String getLoginTypeCd() {
		return loginTypeCd;
	}

	public void setLoginTypeCd(String loginTypeCd) {
		this.loginTypeCd = loginTypeCd;
	}

	@Column(name = "LOGIN_IP")
	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	@Column(name = "LOGIN_TIME")
	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	@Column(name = "LOGOUT_TIME")
	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

}