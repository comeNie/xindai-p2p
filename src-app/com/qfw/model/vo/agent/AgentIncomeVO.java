package com.qfw.model.vo.agent;

import java.math.BigDecimal;
import java.util.Date;

public class AgentIncomeVO {
	private Integer id;

	private Date settlementDate;

	private Integer agentId;

	private String agentName;
	
	private Integer leaderId;
	
	private String leaderName;

	private String cardId;

	private String tel;

	private Integer investorCount;

	private BigDecimal investAmount;

	private BigDecimal commision;

	private Integer agentCount;

	private BigDecimal agentIncome;
	
	private BigDecimal leaderCommision;

	private BigDecimal totalIncome;

	public Integer getAgentCount() {
		return agentCount;
	}

	public void setAgentCount(Integer agentCount) {
		this.agentCount = agentCount;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public BigDecimal getAgentIncome() {
		return agentIncome;
	}

	public void setAgentIncome(BigDecimal agentIncome) {
		this.agentIncome = agentIncome;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public BigDecimal getCommision() {
		return commision;
	}

	public void setCommision(BigDecimal commision) {
		this.commision = commision;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getInvestAmount() {
		return investAmount;
	}

	public void setInvestAmount(BigDecimal investAmount) {
		this.investAmount = investAmount;
	}

	public Integer getInvestorCount() {
		return investorCount;
	}

	public void setInvestorCount(Integer investorCount) {
		this.investorCount = investorCount;
	}

	public BigDecimal getLeaderCommision() {
		return leaderCommision;
	}

	public void setLeaderCommision(BigDecimal leaderCommision) {
		this.leaderCommision = leaderCommision;
	}

	public Integer getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(Integer leaderId) {
		this.leaderId = leaderId;
	}

	public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	public Date getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public BigDecimal getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(BigDecimal totalIncome) {
		this.totalIncome = totalIncome;
	}

	

}
