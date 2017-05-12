package com.qfw.bizservice.agent;

import java.util.List;

import com.qfw.model.vo.agent.AgentIncomeDetailVO;
import com.qfw.model.vo.agent.AgentIncomeVO;
import com.qfw.model.vo.agent.SearchAgentIncomeVO;

public interface IAgentIncomeBS {
	public List<AgentIncomeVO> getAgentIncomeList(SearchAgentIncomeVO vo,
			int first, int pageSize);

	public int getAgentIncomeCount(SearchAgentIncomeVO vo);

	public List<AgentIncomeDetailVO> findLeaderDetail(AgentIncomeVO vo,
			int first, int pageSize);

	public List<AgentIncomeDetailVO> findAgentDetailByLeaderId(
			AgentIncomeVO vo, int first, int pageSize);

	public List<AgentIncomeDetailVO> findAgentDetail(AgentIncomeVO vo,
			int first, int pageSize);

	public int getDetailCount(String oper, AgentIncomeVO vo);
}
