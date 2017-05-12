package com.qfw.model.vo.agent;

import java.math.BigDecimal;
import java.util.Date;

import org.primefaces.model.SelectableDataModel;

public class AgentInfoVO implements SelectableDataModel {
	private Integer ID; // ID

	private Integer userID; // 经纪人用户ID

	private String userName; // 经纪人用户名

	private String tel; // 联系电话

	private String cardId; // 身份证号码

	private String agentName; // 经纪人名称

	private Date enableDate; // 启用日期

	private Integer state; // 状态

	private Integer leaderID;// 团队长ID

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public Date getEnableDate() {
		return enableDate;
	}

	public void setEnableDate(Date enableDate) {
		this.enableDate = enableDate;
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer id) {
		ID = id;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Object getRowData(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getRowKey(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getLeaderID() {
		return leaderID;
	}

	public void setLeaderID(Integer leaderID) {
		this.leaderID = leaderID;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

}
