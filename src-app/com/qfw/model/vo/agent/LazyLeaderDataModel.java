package com.qfw.model.vo.agent;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import com.qfw.common.exception.BizException;

public class LazyLeaderDataModel extends LazyAgentDataModel {
	@Override
	public List<AgentInfoVO> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		// TODO Auto-generated method stub
		try {
			List<AgentInfoVO> ls = this.getAgentInfoBS().getTeamLeaders(
					this.getSearchAgentInfoVO(), first, pageSize);
			setRowCount(this.getAgentInfoBS().findAgentInfoCountByVO(
					this.getSearchAgentInfoVO()));
			return ls;
		} catch (BizException e) {
			// TODO Auto-generated catch block
			 
			return null;
		}
	}
}
