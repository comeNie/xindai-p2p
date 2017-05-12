package com.qfw.model;

public class CustomerResponse extends BaseResponse {
	
	private static final long serialVersionUID = 1L;
	
	private String version;// 软件版本

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
