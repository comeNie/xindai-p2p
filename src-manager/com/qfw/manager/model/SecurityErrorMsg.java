package com.qfw.manager.model;

import java.io.Serializable;

/**
 * 安全信息的错误信息
 * 
 * @author Administrator
 * 
 */
public class SecurityErrorMsg implements Serializable {

	private static final long serialVersionUID = 623482502686399252L;

	private int type;

	private String msg;

	public SecurityErrorMsg() {

	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}



	/**
	 * 实名认证
	 */
	public static int SECURITY_TYPE_AUTH = 0;

	/**
	 * 登陆密码
	 */
	public static int SECURITY_TYPE_PASSWORD = 1;

	/**
	 * 绑定邮箱
	 */
	public static int SECURITY_TYPE_EMAIL = 2;

	/**
	 * 绑定手机
	 */
	public static int SECURITY_TYPE_PHONE = 3;

	/**
	 * 设置交易密码
	 */
	public static int SECURITY_TYPE_WITHDRAW = 4;

	
	/**
	 * 修改手机
	 */
	public static int SECURITY_TYPE_PHONE_MODIFY1 = 5;
	

	/**
	 * 修改手机
	 */
	public static int SECURITY_TYPE_PHONE_MODIFY2 = 6;
	
	/**
	 * 修改交易密码
	 */
	public static int SECURITY_TYPE_WITHDRAW_MODIFY = 7;
	
	
	/**
	 * 找回交易密码
	 */
	public static int SECURITY_TYPE_WITHDRAW_FIND = 8;

	
	/**
	 * 解绑邮箱
	 */
	public static int SECURITY_TYPE_EMAIL_UNBING = 9;
}
