package com.qfw.model;

import java.io.Serializable;

public class HandlerResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String responseCode;
	private String responseMessage;
	private Object responseObj = new Object();

	public HandlerResponse() {
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public Object getResponseObj() {
		return responseObj;
	}

	public void setResponseObj(Object responseObj) {
		this.responseObj = responseObj;
	}
	
}
