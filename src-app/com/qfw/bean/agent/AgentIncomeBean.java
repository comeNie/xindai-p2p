package com.qfw.bean.agent;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.model.vo.agent.AgentIncomeDetailVO;
import com.qfw.model.vo.agent.AgentIncomeVO;
import com.qfw.model.vo.agent.LazyAgentIncomeDetailModel;
import com.qfw.model.vo.agent.LazyAgentIncomeModel;
import com.qfw.model.vo.agent.SearchAgentIncomeVO;

@ViewScoped
@ManagedBean(name = "agentIncomeBean")
public class AgentIncomeBean extends BaseBackingBean {
	@ManagedProperty(value = "#{agentIncomeModel}")
	private LazyAgentIncomeModel model;

	private AgentIncomeVO selected;

	private List<AgentIncomeDetailVO> detailList;

	private LazyAgentIncomeDetailModel detailModel ;

	@PostConstruct
	public void init() {
		if (this.model != null) {
			this.model.setVo(new SearchAgentIncomeVO());
		}
		this.detailModel = new LazyAgentIncomeDetailModel();
		this.detailModel.setAgentIncomeBS(this.model.getAgentIncomeBS());
		
	}

	public LazyAgentIncomeModel getModel() {
		return model;
	}

	public void setModel(LazyAgentIncomeModel model) {
		this.model = model;
	}

	public LazyAgentIncomeDetailModel getDetailModel() {
		return detailModel;
	}

	public void setDetailModel(LazyAgentIncomeDetailModel detailModel) {
		this.detailModel = detailModel;
	}

	public AgentIncomeVO getSelected() {
		return selected;
	}

	public void setSelected(AgentIncomeVO selected) {
		this.selected = selected;
	}

	public List<AgentIncomeDetailVO> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<AgentIncomeDetailVO> detailList) {
		this.detailList = detailList;
	}

	public void findLeaderDetail() {
		this.detailModel.setVo(this.selected);
		this.detailModel.setOper("findLeaderDetail");
	}

	public void findAgentDetailByLeaderId() {
		this.detailModel.setVo(this.selected);
		this.detailModel.setOper("findAgentDetailByLeaderId");
	}

	public void findAgentDetail() {
		this.detailModel.setVo(this.selected);
		this.detailModel.setOper("findAgentDetailByLeaderId");

	}
}
