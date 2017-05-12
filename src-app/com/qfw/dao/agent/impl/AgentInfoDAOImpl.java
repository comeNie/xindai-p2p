package com.qfw.dao.agent.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.qfw.common.dao.ICommonQuery;
import com.qfw.common.dao.impl.BaseDAOImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.util.MessagesController;
import com.qfw.common.util.StringUtils;
import com.qfw.dao.agent.IAgentInfoDAO;
import com.qfw.model.vo.agent.AgentInfoVO;
import com.qfw.model.vo.agent.SearchAgentInfoVO;

@Repository("agentInfoDAO")
public class AgentInfoDAOImpl extends BaseDAOImpl implements IAgentInfoDAO {
	@Autowired
	private ICommonQuery commonQuery;

	/**
	 * 查询团队长
	 */
	public List getTeamLeaders(SearchAgentInfoVO vo, int first, int pageSize)
			throws BizException {
		// TODO Auto-generated method stub
		try {
			String sql = "select t2.id as I_D, t1.user_id as user_I_D,t1.user_name ,t1.CARDID as card_Id,t1.TEL as tel,t2.AGENT_NAME ,t2.ENABLE_DATE ,t2.STATE  from sys_user t1 , biz_agent_info t2 where t1.user_id = t2.user_id and t2.LEADER_ID is null";
			if (StringUtils.isNotEmpty(vo.getUserName())) {
				sql += " and t1.user_name = '" + vo.getUserName() + "'";
			}
			if (StringUtils.isNotEmpty(vo.getAgentName())) {
				sql += " and t2.AGENT_NAME = '" + vo.getAgentName() + "'";
			}
			List<AgentInfoVO> ls = this.commonQuery.findBySQLByPages(sql
					.toString(), first, pageSize, AgentInfoVO.class);

			return ls;
		} catch (Exception ex) {
			throw new BizException(ex);
		}
	}

	public int findAgentInfoCountByVO(SearchAgentInfoVO vo) {
		StringBuilder sql = new StringBuilder();
		sql
				.append("select count(*) from sys_user t1 , biz_agent_info t2 where t1.user_id = t2.user_id and  t2.LEADER_ID is null");
		if (StringUtils.isNotEmpty(vo.getUserName())) {
			sql.append(" and t1.user_name = '" + vo.getUserName() + "'");
		}
		if (StringUtils.isNotEmpty(vo.getAgentName())) {
			sql.append(" and t2.AGENT_NAME = '" + vo.getAgentName() + "'");
		}
		return this.commonQuery.findCountBySQL(sql, null);
	}

	public void addTeamLeader(int userId, String agentName) {
		// TODO Auto-generated method stub
		String sql = "insert into biz_agent_info (user_id,agent_name,enable_date,state) values (?,?,NOW(),1)";
		Object[] params = { userId, agentName == null ? "" : agentName };
		JdbcDaoSupport jdbc = ((JdbcDaoSupport) this.commonQuery);
		jdbc.getJdbcTemplate().update(sql, params);
	}

	public List<AgentInfoVO> getAgentListByLeaderId(Integer leaderId) {
		// TODO Auto-generated method stub
		String sql = "select t2.id as I_D, t1.user_id as user_I_D,t1.user_name ,t1.CARDID as card_Id,t1.TEL as tel,t2.AGENT_NAME ,t2.ENABLE_DATE ,t2.STATE  from sys_user t1 , biz_agent_info t2 where t1.user_id = t2.user_id and t2.LEADER_ID = ?";
		Object[] args = { leaderId };
		return this.commonQuery.findObjects(sql, args, AgentInfoVO.class);
	}

	public void updateAgent(int ID, int userId, String agentName) {
		// TODO Auto-generated method stub
		String sql = "update biz_agent_info set user_id = ? ,agent_name = ? where id = ? ";
		Object[] args = { userId, agentName, ID };
		JdbcDaoSupport jdbc = ((JdbcDaoSupport) this.commonQuery);
		jdbc.getJdbcTemplate().update(sql, args);
	}

	public void addAgent(int id, int agentUserId, String agentName, int leaderId) {
		// TODO Auto-generated method stub
		String sql = "insert into biz_agent_info (user_id,agent_name,enable_date,state,leader_id) values (?,?,NOW(),1,?)";
		Object[] args = { agentUserId, agentName, leaderId };
		JdbcDaoSupport jdbc = ((JdbcDaoSupport) this.commonQuery);
		jdbc.getJdbcTemplate().update(sql, args);
	}

	public int changeState(int id) {
		// TODO Auto-generated method stub
		String sql = "select count(*) from biz_agent_info where leader_id = ? and state = 1 ";
		Object[] args = { id };
		int count = this.commonQuery.findCountBySQL(new StringBuilder(sql),
				args);
		if (count > 0) {
			return -1;
		}
		sql = "update biz_agent_info set state = -(state-1) where user_id = ?";
		JdbcDaoSupport jdbc = ((JdbcDaoSupport) this.commonQuery);
		return jdbc.getJdbcTemplate().update(sql, args);
	}

	public void transferLeader(int oldLeaderId, int newLeaderId) {
		// TODO Auto-generated method stub
		String sql = " update biz_agent_info set leader_id = ? where leader_id = ?";
		Object[] args = { newLeaderId, oldLeaderId };
		JdbcDaoSupport jdbc = ((JdbcDaoSupport) this.commonQuery);
		jdbc.getJdbcTemplate().update(sql, args);
	}

	public void transferLeaderForAgent(int agentId, int newLeaderId) {
		// TODO Auto-generated method stub
		String sql = " update biz_agent_info set leader_id = ? where user_id = ?";
		Object[] args = { newLeaderId, agentId };
		JdbcDaoSupport jdbc = ((JdbcDaoSupport) this.commonQuery);
		jdbc.getJdbcTemplate().update(sql, args);
	}

}
