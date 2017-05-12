package com.qfw.bizservice.agent.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfw.bizservice.agent.IAgentInfoBS;
import com.qfw.common.exception.BizException;
import com.qfw.dao.agent.IAgentInfoDAO;
import com.qfw.model.vo.agent.AgentInfoVO;
import com.qfw.model.vo.agent.SearchAgentInfoVO;

@Service("agentInfoBS")
public class AgentInfoBSImpl implements IAgentInfoBS {
	@Autowired
	private IAgentInfoDAO agentInfoDAO;
	public List<AgentInfoVO> getTeamLeaders(SearchAgentInfoVO searchAgentInfoVO, int first, int pageSize) throws BizException {
		// TODO Auto-generated method stub
		return this.agentInfoDAO.getTeamLeaders( searchAgentInfoVO,  first,  pageSize);
	}
	public int findAgentInfoCountByVO(SearchAgentInfoVO searchAgentInfoVO) {
		// TODO Auto-generated method stub
		return this.agentInfoDAO.findAgentInfoCountByVO(searchAgentInfoVO);
	}
	public void addTeamLeader(int userId, String agentName) {
		// TODO Auto-generated method stub
		this.agentInfoDAO.addTeamLeader(userId, agentName);
	}
	public List<AgentInfoVO> getAgentListByLeaderId(Integer leaderId) {
		// TODO Auto-generated method stub
		return this.agentInfoDAO.getAgentListByLeaderId(leaderId);
	}
	public void updateAgent(int ID, int userId, String agentName) {
		// TODO Auto-generated method stub
		this.agentInfoDAO.updateAgent(ID, userId, agentName);
	}
	public void addAgent(int id, int agentUserId, String agentName, int leaderId) {
		// TODO Auto-generated method stub
		this.agentInfoDAO.addAgent(id, agentUserId, agentName, leaderId);
	}
	public int changeState(int id) {
		// TODO Auto-generated method stub
		return this.agentInfoDAO.changeState(id);
	}
	public void transferLeader(int oldLeaderId, int newLeaderId) {
		// TODO Auto-generated method stub
		this.agentInfoDAO.transferLeader(oldLeaderId, newLeaderId);
	}
	public void transferLeaderForAgent(int agentId, int newLeaderId) {
		// TODO Auto-generated method stub
		this.agentInfoDAO.transferLeaderForAgent(agentId, newLeaderId);
	}
	
}
