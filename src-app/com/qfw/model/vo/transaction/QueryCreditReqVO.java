package com.qfw.model.vo.transaction;

public class QueryCreditReqVO {
	
	private String queryType;
	private String name;
	private String documentNo;
	private String subreportIDs;
	private String refID;
	
	
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}
	public String getSubreportIDs() {
		return subreportIDs;
	}
	public void setSubreportIDs(String subreportIDs) {
		this.subreportIDs = subreportIDs;
	}
	public String getRefID() {
		return refID;
	}
	public void setRefID(String refID) {
		this.refID = refID;
	}
	
}
