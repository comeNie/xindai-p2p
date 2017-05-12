package com.qfw.model;

import java.io.Serializable;

public class BaseResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String responseCode;
	private String responseMessage;

	public BaseResponse() {
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

}
