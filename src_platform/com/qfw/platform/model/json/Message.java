package com.qfw.platform.model.json;

import java.io.Serializable;

/**
 * 返回前台的消息
 * 
 * @author Administrator
 * 
 */
public class Message implements Serializable {

	private static final long serialVersionUID = -4397676259828380767L;

	private int status;

	private String message;

	public Message() {

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