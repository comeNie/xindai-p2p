package com.qfw.dao.agent;

import java.util.List;

import com.qfw.common.exception.BizException;
import com.qfw.model.vo.agent.AgentInfoVO;
import com.qfw.model.vo.agent.SearchAgentInfoVO;

public interface IAgentInfoDAO {
	public List<AgentInfoVO> getTeamLeaders(SearchAgentInfoVO searchAgentInfoVO, int first, int pageSize) throws BizException;
	public int findAgentInfoCountByVO(SearchAgentInfoVO searchAgentInfoVO);
	public void addTeamLeader(int userId,String agentName);
	public List<AgentInfoVO> getAgentListByLeaderId(Integer leaderId);
	public void updateAgent(int ID,int userId, String agentName);
	public void addAgent(int id,int agentUserId , String agentName,int leaderId);
	public int changeState(int id);
	public void transferLeader(int oldLeaderId,int newLeaderId);
	public void transferLeaderForAgent(int agentId,int newLeaderId);
	
}
