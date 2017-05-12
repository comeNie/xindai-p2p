package com.qfw.platform.model.vo;

import com.qfw.manager.model.UserSecurity;

public class LoginInfo {
	
	Integer userId;
	Integer custId;
	private String custName;// 真实姓名
	private String certificateNum;// 身份证号
	private String userCode;// 用户登录名
	private String isAdmin;// 用户登录名
	private UserSecurity userSecurity;
	
	public UserSecurity getUserSecurity() {
		return userSecurity;
	}

	public void setUserSecurity(UserSecurity userSecurity) {
		this.userSecurity = userSecurity;
	}

	public LoginInfo() {
		super();
	}
	
	public LoginInfo(Integer userId, Integer custId) {
		super();
		this.userId = userId;
		this.custId = custId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCustId() {
		return custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCertificateNum() {
		return certificateNum;
	}

	public void setCertificateNum(String certificateNum) {
		this.certificateNum = certificateNum;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

}
