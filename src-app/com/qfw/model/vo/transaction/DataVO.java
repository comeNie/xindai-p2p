package com.qfw.model.vo.transaction;


public class DataVO {
	private ResultMessageVO message = new ResultMessageVO();
	
//	private List<CheckInfoVO> policeCheckInfos = new ArrayList<CheckInfoVO>();

	CheckInfoVO policeCheckInfos = new CheckInfoVO();

	public ResultMessageVO getMessage() {
		return message;
	}

	public void setMessage(ResultMessageVO message) {
		this.message = message;
	}

	public CheckInfoVO getPoliceCheckInfos() {
		return policeCheckInfos;
	}

	public void setPoliceCheckInfos(CheckInfoVO policeCheckInfos) {
		this.policeCheckInfos = policeCheckInfos;
	}
}
