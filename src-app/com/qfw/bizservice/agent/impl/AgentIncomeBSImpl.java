package com.qfw.bizservice.agent.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfw.bizservice.agent.IAgentIncomeBS;
import com.qfw.dao.agent.IAgentIncomeDAO;
import com.qfw.model.vo.agent.AgentIncomeDetailVO;
import com.qfw.model.vo.agent.AgentIncomeVO;
import com.qfw.model.vo.agent.SearchAgentIncomeVO;

@Service("agentIncomeBS")
public class AgentIncomeBSImpl implements IAgentIncomeBS {
	@Autowired
	private IAgentIncomeDAO agentIncomeDAO;

	public List<AgentIncomeVO> getAgentIncomeList(SearchAgentIncomeVO vo,
			int first, int pageSize) {
		// TODO Auto-generated method stub
		return this.agentIncomeDAO.getAgentIncomeList(vo, first, pageSize);
	}

	public int getAgentIncomeCount(SearchAgentIncomeVO vo) {
		// TODO Auto-generated method stub
		return this.agentIncomeDAO.getAgentIncomeCount(vo);
	}

	public List<AgentIncomeDetailVO> findAgentDetail(AgentIncomeVO vo,
			int first, int pageSize) {
		// TODO Auto-generated method stub
		return this.agentIncomeDAO.findAgentDetail(vo, first, pageSize);
	}

	public List<AgentIncomeDetailVO> findAgentDetailByLeaderId(
			AgentIncomeVO vo, int first, int pageSize) {
		// TODO Auto-generated method stub
		return this.agentIncomeDAO.findAgentDetailByLeaderId(vo, first,
				pageSize);
	}

	public List<AgentIncomeDetailVO> findLeaderDetail(AgentIncomeVO vo,
			int first, int pageSize) {
		// TODO Auto-generated method stub
		return this.agentIncomeDAO.findLeaderDetail(vo, first, pageSize);
	}

	public int getDetailCount(String oper, AgentIncomeVO vo) {
		// TODO Auto-generated method stub
		return this.agentIncomeDAO.getDetailCount(oper, vo);
	}

}
