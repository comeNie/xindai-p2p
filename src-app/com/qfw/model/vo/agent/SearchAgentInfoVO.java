package com.qfw.model.vo.agent;

public class SearchAgentInfoVO {

	private String userName; // 团队长用户名

	private String agentName; // 团队长名称

	private Integer leaderId;
	
	private Integer userId;

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(Integer leaderId) {
		this.leaderId = leaderId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
