package com.qfw.bean.agent;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.qfw.bizservice.agent.IAgentInfoBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.StringUtils;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.vo.agent.AgentInfoVO;
import com.qfw.model.vo.agent.LazyAgentDataModel;
import com.qfw.model.vo.agent.LazyLeaderDataModel;
import com.qfw.model.vo.agent.SearchAgentInfoVO;

@ViewScoped
@ManagedBean(name = "agentInfoBean")
public class AgentInfoBean extends BaseBackingBean {

	@ManagedProperty(value = "#{agentInfoBS}")
	private IAgentInfoBS agentInfoBS;

	private LazyAgentDataModel agentDataModel;

	private LazyLeaderDataModel leaderDataModel;

	private SearchAgentInfoVO searchAgentInfoVO = new SearchAgentInfoVO();

	private List<AgentInfoVO> teamLeaderList = new ArrayList();

	private String selectUserStr;

	private Integer selectUserId;

	private String agentName;

	private SysUser selectUser;

	private AgentInfoVO newLeader = new AgentInfoVO();

	private AgentInfoVO selectLeader = new AgentInfoVO();

	private AgentInfoVO selectAgent = new AgentInfoVO();

	private List<AgentInfoVO> agentList = new ArrayList();

	private String operateFlag = "";

	@PostConstruct
	public void init() {
		Object selectLeader = ViewOper.getSessionTmpAttr("selectLeader");
		Object selectAgent = ViewOper.getSessionTmpAttr("selectAgent");
		operateFlag = ViewOper.getRequestParameter("operateFlag");
		if (selectLeader != null) {
			this.setSelectLeader((AgentInfoVO) selectLeader);
			if (operateFlag != null && operateFlag.equals("update")) {
				this.setSelectUserStr(this.selectLeader.getUserName());
				this.setSelectUserId(this.selectLeader.getUserID());
				this.setAgentName(this.getSelectLeader().getAgentName());
			}
		}

		if (selectAgent != null) {
			this.selectAgent = (AgentInfoVO) selectAgent;
		}

		this.agentDataModel = new LazyAgentDataModel();
		this.agentDataModel.setSearchAgentInfoVO(searchAgentInfoVO);
		this.agentDataModel.setAgentInfoBS(agentInfoBS);

		this.leaderDataModel = new LazyLeaderDataModel();
		this.leaderDataModel.setSearchAgentInfoVO(searchAgentInfoVO);
		this.leaderDataModel.setAgentInfoBS(agentInfoBS);
	}

	public String getOperateFlag() {
		return operateFlag;
	}

	public void setOperateFlag(String operateFlag) {
		this.operateFlag = operateFlag;
	}

	public void addLeader() {

	}

	/**
	 * 移交所有经纪人
	 * 
	 */
	public void transferLeader() {
		//System.out.println(this.getSelectAgent().getUserID() + " : " + this.getNewLeader().getUserID());

		if (this.getSelectAgent() != null
				&& this.getSelectAgent().getUserID() != null) {

			try {
				this.agentInfoBS.transferLeaderForAgent(this.getSelectAgent()
						.getUserID(), this.getNewLeader().getUserID());
				this.alert("移交成功!");
			} catch (Exception ex) {
				ex.printStackTrace();
				this.alert("操作失败，请与管理员联系");
			}
		} else {
			try {
				this.agentInfoBS.transferLeader(this.getSelectLeader()
						.getUserID(), this.getNewLeader().getUserID());
				this.alert("移交成功!");
			} catch (Exception ex) {
				ex.printStackTrace();
				this.alert("操作失败，请与管理员联系");
			}
		}

	}

	/**
	 * 修改状态
	 * 
	 */
	public void changeState() {
		String target = ViewOper.getRequestParameter("target");
		if (target.equals("agent")) {
			try {
				this.getAgentInfoBS().changeState(
						this.getSelectAgent().getUserID());
				this.alert("修改成功！");

			} catch (Exception ex) {
				ex.printStackTrace();
				this.alert("操作失败，请与管理员联系");
			}

		} else {
			try {
				int rs = this.getAgentInfoBS().changeState(
						this.getSelectLeader().getUserID());
				if (rs == -1) {
					this.alert("请先禁用该团队长下的所有经纪人！");
				} else {
					this.alert("修改成功！");
				}

			} catch (Exception ex) {
				ex.printStackTrace();
				this.alert("操作失败，请与管理员联系");
			}
		}

	}

	/**
	 * 添加经纪人
	 * 
	 */
	public void addAgent() {
		try {
			this.getAgentInfoBS().addAgent(this.getSelectLeader().getID(),
					this.selectUserId, this.agentName,
					this.getSelectLeader().getUserID());
			this.alert("添加成功");
		} catch (Exception ex) {
			ex.printStackTrace();
			this.alert("操作失败，请与管理员联系");
		}

	}

	public void selectUserAction() {
		this.selectUserStr = selectUser.getUserName();
		this.selectUserId = selectUser.getUserId();
	}

	public LazyLeaderDataModel getLeaderDataModel() {
		return leaderDataModel;
	}

	public void setLeaderDataModel(LazyLeaderDataModel leaderDataModel) {
		this.leaderDataModel = leaderDataModel;
	}

	public AgentInfoVO getSelectLeader() {
		return selectLeader;
	}

	public void setSelectLeader(AgentInfoVO selectLeader) {
		this.selectLeader = selectLeader;
	}

	/**
	 * 添加团队长
	 * 
	 */
	public void addTeamLeader() {
		if (StringUtils.isNotEmpty(this.operateFlag)
				&& this.operateFlag.equals("update")) {
			this.getAgentInfoBS().updateAgent(this.getSelectLeader().getID(),
					this.selectUserId, this.agentName);
			this.alert("修改成功");
		} else {
			try {
				this.getAgentInfoBS().addTeamLeader(
						this.selectUser.getUserId(), this.agentName);
				this.alert("添加成功");
			} catch (Exception ex) {
				ex.printStackTrace();
				this.alert("操作失败，请与管理员联系");
			}
		}
	}

	/**
	 * 选择经济人
	 * 
	 */
	public void selectAgent() {
		ViewOper.getSession().setAttribute("selectAgent", this.selectAgent);
	}

	/**
	 * 选择团队长
	 * 
	 */
	public void modifyLeader() {
		ViewOper.getSession().setAttribute("selectLeader", this.selectLeader);
		// operateFlag = "update";
	}

	public void getAgentListByLeaderId() {
		this.agentDataModel.setSelectLeader(this.selectLeader);
		// this.searchAgentInfoVO.setLeaderId(this.selectLeader.getUserID());
		// this.agentList = this.agentInfoBS.getAgentListByLeaderId();
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public SysUser getSelectUser() {
		return selectUser;
	}

	public void setSelectUser(SysUser selectUser) {
		this.selectUser = selectUser;
	}

	public String reset() {
		searchAgentInfoVO = new SearchAgentInfoVO();
		return null;
	}

	public List<AgentInfoVO> getAgentList() {
		return agentList;
	}

	public void setAgentList(List<AgentInfoVO> agentList) {
		this.agentList = agentList;
	}

	public List<AgentInfoVO> getTeamLeaderList() throws BizException {
		return null;
	}

	public void setTeamLeaderList(List<AgentInfoVO> teamLeaderList) {

		this.teamLeaderList = teamLeaderList;
	}

	public IAgentInfoBS getAgentInfoBS() {
		return agentInfoBS;
	}

	public void setAgentInfoBS(IAgentInfoBS agentInfoBS) {
		this.agentInfoBS = agentInfoBS;
	}

	public LazyAgentDataModel getAgentDataModel() {
		return agentDataModel;
	}

	public void setAgentDataModel(LazyAgentDataModel agentDataModel) {
		this.agentDataModel = agentDataModel;
	}

	public SearchAgentInfoVO getSearchAgentInfoVO() {
		return searchAgentInfoVO;
	}

	public void setSearchAgentInfoVO(SearchAgentInfoVO searchAgentInfoVO) {
		this.searchAgentInfoVO = searchAgentInfoVO;
	}

	public Integer getSelectUserId() {
		return selectUserId;
	}

	public void setSelectUserId(Integer selectUserId) {
		this.selectUserId = selectUserId;
	}

	public String getSelectUserStr() {
		return selectUserStr;
	}

	public void setSelectUserStr(String selectUserStr) {
		this.selectUserStr = selectUserStr;
	}

	public AgentInfoVO getNewLeader() {
		return newLeader;
	}

	public void setNewLeader(AgentInfoVO newLeader) {
		this.newLeader = newLeader;
	}

	public AgentInfoVO getSelectAgent() {
		return selectAgent;
	}

	public void setSelectAgent(AgentInfoVO selectAgent) {
		this.selectAgent = selectAgent;
	}

}
