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
@Service("agentIncomeModel")
public class LazyAgentIncomeModel extends LazyDataModel<AgentIncomeVO> {
	private SearchAgentIncomeVO vo = new SearchAgentIncomeVO();

	@Autowired
	private IAgentIncomeBS agentIncomeBS;

	private Map<String, AgentIncomeVO> agentIncomeMap = new HashMap();

	@Override
	public List<AgentIncomeVO> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		List ls = this.agentIncomeBS.getAgentIncomeList(this.vo, first,
				pageSize);
		this.setRowCount(this.agentIncomeBS.getAgentIncomeCount(this.vo));
		return ls;
	}

	public SearchAgentIncomeVO getVo() {
		return vo;
	}

	public void setVo(SearchAgentIncomeVO vo) {
		this.vo = vo;
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

}
