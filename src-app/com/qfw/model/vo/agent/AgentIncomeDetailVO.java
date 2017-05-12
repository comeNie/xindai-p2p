package com.qfw.model.vo.agent;

import java.math.BigDecimal;
import java.util.Date;

public class AgentIncomeDetailVO {
	private Integer id;

	private Date settlementDate;

	private Integer agentId;

	private String agentName;

	private Integer incomeType;

	private Integer leaderId;

	private String leaderName;

	private Integer investorId;

	private String investorName;

	private BigDecimal investorIncome;

	private BigDecimal totalIncome;

	private BigDecimal leaderIncome;

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIncomeType() {
		return incomeType;
	}

	public void setIncomeType(Integer incomeType) {
		this.incomeType = incomeType;
	}

	public Integer getInvestorId() {
		return investorId;
	}

	public void setInvestorId(Integer investorId) {
		this.investorId = investorId;
	}

	public BigDecimal getInvestorIncome() {
		return investorIncome;
	}

	public void setInvestorIncome(BigDecimal investorIncome) {
		this.investorIncome = investorIncome;
	}

	public String getInvestorName() {
		return investorName;
	}

	public void setInvestorName(String investorName) {
		this.investorName = investorName;
	}

	public Integer getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(Integer leaderId) {
		this.leaderId = leaderId;
	}

	public BigDecimal getLeaderIncome() {
		return leaderIncome;
	}

	public void setLeaderIncome(BigDecimal leaderIncome) {
		this.leaderIncome = leaderIncome;
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

	public BigDecimal getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(BigDecimal totalIncome) {
		this.totalIncome = totalIncome;
	}

}
