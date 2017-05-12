package com.qfw.model.vo.transaction;

public class CheckInfoVO {
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 身份证号
	 */
	
	private String id;
	
	private CheckInfoDetailVO policeCheckInfo = new CheckInfoDetailVO();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public CheckInfoDetailVO getPoliceCheckInfo() {
		return policeCheckInfo;
	}
	public void setPoliceCheckInfo(CheckInfoDetailVO policeCheckInfo) {
		this.policeCheckInfo = policeCheckInfo;
	}
}
