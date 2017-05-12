package com.qfw.platform.model.register;

import java.io.Serializable;

/**
 * 验证消息
 * 
 * @author Administrator
 * 
 */
public class Msg implements Serializable {

	private static final long serialVersionUID = 8333341833188908315L;
	
	public static final int SUCCESS_STATUS = 0;
	public static final int ERROR_STATUS =1;
	public static final Msg SUCCESS_MSG = new Msg(SUCCESS_STATUS,"");
	

	/**
	 * 状态
	 */
	private int status;

	/**
	 * 消息
	 */
	private String message;

	public Msg() {

	}

	public Msg(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}