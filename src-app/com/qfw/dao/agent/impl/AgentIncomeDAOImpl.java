package com.qfw.dao.agent.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.qfw.common.dao.ICommonQuery;
import com.qfw.common.util.StringUtils;
import com.qfw.common.util.web.ViewOper;
import com.qfw.dao.agent.IAgentIncomeDAO;
import com.qfw.model.vo.agent.AgentIncomeDetailVO;
import com.qfw.model.vo.agent.AgentIncomeVO;
import com.qfw.model.vo.agent.SearchAgentIncomeVO;

@Repository("agentIncomeDAO")
public class AgentIncomeDAOImpl implements IAgentIncomeDAO {
	@Autowired
	private ICommonQuery commonQuery;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

	public List<AgentIncomeVO> getAgentIncomeList(SearchAgentIncomeVO vo,
			int first, int pageSize) {
		// TODO Auto-generated method stub
		String sql = "select * from biz_agent_income where 1=1 ";
		List args = new ArrayList();
		String role = ViewOper.getRequestParameter("role");
		if (!StringUtils.isEmpty(role)) {
			sql += role.equals("leader") ? " and leader_id is null "
					: " and leader_id is not null";
		}
		if (!StringUtils.isEmpty(vo.getAgentName())) {
			sql += " and AGENT_NAME like ? ";
			args.add(vo.getAgentName());
		}
		if (!StringUtils.isEmpty(vo.getLeaderName())) {
			sql += " and LEADER_NAME like ? ";
			args.add(vo.getLeaderName());
		}
		if (vo.getStartDate() != null) {
			sql += " and SETTLEMENT_DATE >= ?";
			args.add(new SimpleDateFormat("yyyy-MM").format(vo.getStartDate()));
		}
		if (vo.getEndDate() != null) {
			sql += " and SETTLEMENT_DATE <= ?";
			args.add(new SimpleDateFormat("yyyy-MM").format(vo.getEndDate()));
		}

		List<AgentIncomeVO> ls = this.commonQuery.findBySQLByPages(sql
				.toString(), first, pageSize, args.toArray(),
				AgentIncomeVO.class);

		return ls;

	}

	public int getAgentIncomeCount(SearchAgentIncomeVO vo) {
		// TODO Auto-generated method stub

		String sql = "select count(*) from biz_agent_income where 1=1 ";
		List args = new ArrayList();

		String role = ViewOper.getRequestParameter("role");
		if (!StringUtils.isEmpty(role)) {
			sql += role.equals("leader") ? " and leader_id is null "
					: " and leader_id is not null";
		}

		if (!StringUtils.isEmpty(vo.getAgentName())) {
			sql += " and AGENT_NAME like ? ";
			args.add(vo.getAgentName());
		}
		if (!StringUtils.isEmpty(vo.getLeaderName())) {
			sql += " and LEADER_NAME like ? ";
			args.add(vo.getLeaderName());
		}
		if (vo.getStartDate() != null) {
			sql += " and SETTLEMENT_DATE >= ?";
			args.add(new SimpleDateFormat("yyyy-MM").format(vo.getStartDate()));
		}
		if (vo.getEndDate() != null) {
			sql += " and SETTLEMENT_DATE <= ?";
			args.add(new SimpleDateFormat("yyyy-MM").format(vo.getEndDate()));
		}
		return this.commonQuery.findCountBySQL(new StringBuilder(sql), args
				.toArray());
	}

	public List<AgentIncomeDetailVO> findAgentDetail(AgentIncomeVO vo,
			int first, int pageSize) {
		// TODO Auto-generated method stub
		String sql = "select * from biz_agent_income_detail where AGENT_ID = ? and LEADER_ID is null and date_format(SETTLEMENT_DATE,'%Y-%m') = ? ";
		Object[] args = { vo.getAgentId(),
				this.sdf.format(vo.getSettlementDate()) };
		return this.commonQuery.findBySQLByPages(sql, first, pageSize, args,
				AgentIncomeDetailVO.class);
	}

	public List<AgentIncomeDetailVO> findAgentDetailByLeaderId(
			AgentIncomeVO vo, int first, int pageSize) {
		// TODO Auto-generated method stub
		String sql = "select * from biz_agent_income_detail where LEADER_ID = ?  and date_format(SETTLEMENT_DATE,'%Y-%m') = ? ";
		Object[] args = { vo.getAgentId(),
				this.sdf.format(vo.getSettlementDate()) };
		return this.commonQuery.findBySQLByPages(sql, first, pageSize, args,
				AgentIncomeDetailVO.class);
	}

	public List<AgentIncomeDetailVO> findLeaderDetail(AgentIncomeVO vo,
			int first, int pageSize) {
		// TODO Auto-generated method stub
		String sql = "select * from biz_agent_income_detail where AGENT_ID = ? and LEADER_ID is null and date_format(SETTLEMENT_DATE,'%Y-%m') = ?";
		Object[] args = { vo.getAgentId(),
				this.sdf.format(vo.getSettlementDate()) };
		return this.commonQuery.findBySQLByPages(sql, first, pageSize, args,
				AgentIncomeDetailVO.class);
	}

	public int getDetailCount(String oper, AgentIncomeVO vo) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		if (oper.equals("findLeaderDetail")) {
			sb
					.append("select count(*) from biz_agent_income_detail where AGENT_ID = ? and LEADER_ID is null and date_format(SETTLEMENT_DATE,'%Y-%m') = ?");

		} else if (oper.equals("findAgentDetail")) {
			sb
					.append("select count(*) from biz_agent_income_detail where AGENT_ID = ? and LEADER_ID is null and date_format(SETTLEMENT_DATE,'%Y-%m') = ?");
		} else if (oper.equals("findAgentDetailByLeaderId")) {
			sb
					.append("select count(*) from biz_agent_income_detail where LEADER_ID = ?  and date_format(SETTLEMENT_DATE,'%Y-%m') = ?");
		}
		Object[] args = { vo.getAgentId(),
				this.sdf.format(vo.getSettlementDate()) };
		return this.commonQuery.findCountBySQL(sb, args);
	}
}
