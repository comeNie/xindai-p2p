package com.qfw.manager.model;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

/**
 * 用户安全信息
 * 
 * @author Administrator
 * 
 */
public class UserSecurity implements Serializable {

	private static final long serialVersionUID = -6707381649724785229L;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 真实姓名
	 */
	private String realName;

	/**
	 * 身份证
	 */
	private String idCard;

	/**
	 * 登录密码
	 */
	private String password;

	/**
	 * 邮箱地址
	 */
	private String email;

	/**
	 * 绑定手机
	 */
	private String mobile;

	/**
	 * 提现密码
	 */
	private String cashPassword;
	
	/**
	 * 带*的电话号码
	 */
	private String showTel;
	
	/**
	 * 带*的真实姓名
	 */
	private String showRealName;
	
	/**
	 * 带*的身份证号
	 */
	private String showIdCard;
	
	private String birthDate;

	public UserSecurity() {

	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCashPassword() {
		return cashPassword;
	}

	public void setCashPassword(String cashPassword) {
		this.cashPassword = cashPassword;
	}

	public String getShowTel() {
		
		if (StringUtils.isNotEmpty(mobile)) {
			if(mobile.length() != 11){
				return mobile;
			}
			StringBuffer buffer = new StringBuffer();
			buffer.append(mobile.substring(0, 3));
			buffer.append("****");
			buffer.append(mobile.substring(7));
			return buffer.toString();
		}
		return showTel;
	}

	public void setShowTel(String showTel) {
		this.showTel = showTel;
	}

	public String getShowRealName() {
		
		
		if (StringUtils.isNotEmpty(realName)) {
			int realNameLength = realName.length();
			StringBuffer buffer = new StringBuffer();
			buffer.append(realName.substring(0, 1));
			for (int i = 1; i < realNameLength; i++) {
				buffer.append("*");
			}
			return buffer.toString();
		}else{
			return showRealName;
		}
		
	}
	public void setShowRealName(String showRealName) {
		this.showRealName = showRealName;
	}

	public String getShowIdCard() {
		
	
		if (StringUtils.isNotEmpty(idCard)) {
			int idCardLength = idCard.length();
			if(idCardLength != 18){
				return showIdCard;
			}
			StringBuffer buffer = new StringBuffer();
			buffer.append(idCard.substring(0, 4));
			for (int i = 4; i < idCardLength-4; i++) {
				buffer.append("*");
			}
			buffer.append(idCard.subSequence(idCardLength-4, idCardLength));
			return buffer.toString();
		}
		return showIdCard;
	}

	public void setShowIdCard(String showIdCard) {
		this.showIdCard = showIdCard;
	}
	
	public static void main(String[] args) {
		UserSecurity userSecurity = new UserSecurity();
		userSecurity.setRealName("");
		userSecurity.setIdCard("35");
		userSecurity.setMobile("158");
		System.err.println(userSecurity.getShowIdCard());
		System.err.println(userSecurity.getIdCard());
		System.err.println(userSecurity.getShowTel());
		System.err.println(userSecurity.getShowRealName());
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
}