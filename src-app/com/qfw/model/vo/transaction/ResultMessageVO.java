package com.qfw.model.vo.transaction;

public class ResultMessageVO {
	
	private String status;
	
	private String value;
	
	private String querySeq;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getQuerySeq() {
		return querySeq;
	}

	public void setQuerySeq(String querySeq) {
		this.querySeq = querySeq;
	}
}
