package com.qfw.model.vo.agent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfw.bizservice.agent.IAgentIncomeBS;

@SuppressWarnings("serial")
@Service("agentIncomeDetailModel")
public class LazyAgentIncomeDetailModel extends LazyDataModel<AgentIncomeVO> {

	@Autowired
	private IAgentIncomeBS agentIncomeBS;

	private AgentIncomeVO vo;

	private String oper = "";

	private Map<String, AgentIncomeVO> agentIncomeMap = new HashMap();

	@Override
	public List<AgentIncomeVO> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		if (this.oper.equals("findLeaderDetail")) {
			List ls = this.agentIncomeBS.findLeaderDetail(vo, first, pageSize);
			this.setRowCount(this.agentIncomeBS.getDetailCount(
					"findLeaderDetail", vo));
			return ls;
		} else if (this.oper.equals("findAgentDetailByLeaderId")) {
			List ls = this.agentIncomeBS.findAgentDetailByLeaderId(vo, first,
					pageSize);
			this.setRowCount(this.agentIncomeBS.getDetailCount(
					"findAgentDetailByLeaderId", vo));
			return ls;
		} else if (this.oper.equals("findAgentDetail")) {
			List ls = this.agentIncomeBS.findAgentDetail(vo, first, pageSize);
			this.setRowCount(this.agentIncomeBS.getDetailCount(
					"findAgentDetail", vo));
			return ls;
		}

		return null;
	}

	@Override
	public Object getRowKey(AgentIncomeVO vo) {
		// TODO Auto-generated method stub
		if (vo != null && vo.getId() != null) {
			this.agentIncomeMap.put(vo.getId().toString(), vo);
			return vo.getId();
		}
		return null;
	}

	@Override
	public AgentIncomeVO getRowData(String rowKey) {
		return this.agentIncomeMap.get(rowKey);
	}

	public IAgentIncomeBS getAgentIncomeBS() {
		return agentIncomeBS;
	}

	public void setAgentIncomeBS(IAgentIncomeBS agentIncomeBS) {
		this.agentIncomeBS = agentIncomeBS;
	}

	public Map<String, AgentIncomeVO> getAgentIncomeMap() {
		return agentIncomeMap;
	}

	public void setAgentIncomeMap(Map<String, AgentIncomeVO> agentIncomeMap) {
		this.agentIncomeMap = agentIncomeMap;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public AgentIncomeVO getVo() {
		return vo;
	}

	public void setVo(AgentIncomeVO vo) {
		this.vo = vo;
	}

}
