package com.qfw.model.vo.agent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.qfw.bizservice.agent.IAgentInfoBS;

@SuppressWarnings("serial")
public class LazyAgentDataModel extends LazyDataModel<AgentInfoVO> {

	private IAgentInfoBS agentInfoBS;

	private SearchAgentInfoVO searchAgentInfoVO = new SearchAgentInfoVO();
	
	private AgentInfoVO selectLeader ;

	private Map<String, AgentInfoVO> leaders = new HashMap<String, AgentInfoVO>();

	public IAgentInfoBS getAgentInfoBS() {
		return agentInfoBS;
	}

	public void setAgentInfoBS(IAgentInfoBS agentInfoBS) {
		this.agentInfoBS = agentInfoBS;
	}

	public SearchAgentInfoVO getSearchAgentInfoVO() {
		return searchAgentInfoVO;
	}

	public Map<String, AgentInfoVO> getLeaders() {
		return leaders;
	}

	public void setLeaders(Map<String, AgentInfoVO> leaders) {
		this.leaders = leaders;
	}

	public void setSearchAgentInfoVO(SearchAgentInfoVO searchAgentInfoVO) {
		this.searchAgentInfoVO = searchAgentInfoVO;
	}

	public AgentInfoVO getSelectLeader() {
		return selectLeader;
	}

	public void setSelectLeader(AgentInfoVO selectLeader) {
		this.selectLeader = selectLeader;
	}

	@Override
	public List<AgentInfoVO> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		// TODO Auto-generated method stub
		if(this.selectLeader != null){
			List<AgentInfoVO> ls = this.getAgentInfoBS().getAgentListByLeaderId(
					this.selectLeader.getUserID());
			setRowCount(ls.size());
			return ls;
		}
		return null;
	}

	@Override
	public AgentInfoVO getRowData(String rowKey) {
		return this.leaders.get(rowKey);
	}

	public Object getRowKey(AgentInfoVO vo) {
		if(vo != null && vo.getID()!=null){
			this.leaders.put(vo.getID().toString(), vo);
			return vo.getID();
		}
		return null;
	}

}
