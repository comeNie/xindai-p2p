package com.qfw.dao.postloan.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.qfw.common.dao.ICommonQuery;
import com.qfw.common.dao.impl.BaseDAOImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.util.StringUtils;
import com.qfw.dao.postloan.IPostLoanTaskDAO;
import com.qfw.model.vo.agent.AgentInfoVO;
import com.qfw.model.vo.postLoan.LoanTaskSearchVO;
import com.qfw.model.vo.postLoan.PostLoanCheckVO;
import com.qfw.model.vo.postLoan.PostLoanTaskInfoVO;

@Repository("postLoanTaskDao")
public class PostLoanTaskDAOImpl extends BaseDAOImpl implements
		IPostLoanTaskDAO {
	@Autowired
	private ICommonQuery commonQuery;

	public List<PostLoanTaskInfoVO> findPostLoanTasks(LoanTaskSearchVO vo,
			int first, int pageSize) throws BizException {
		// TODO Auto-generated method stub
		try {
			String sql = "select t1.task_id,t3.user_id as cust_id,t3.cust_name,t1.POSTLOAN_GENERATE_TYPE,t1.START_DATE,t1.FINISH_DATE,"
					+ " t6.user_id as creator_id,t6.user_name as creator_name, t2.user_name as manage_name,t1.POSTLOAN_STATUS as POST_LOAN_STATUS "
					+ ",t5.dept_id,t5.dept_name, t1.create_date ,t1.CHECK_RESULT,t1.RESULT_REMARK,t1.FROZEN_LIMIT,t1.FROZEN_BALANCE,t1.legal_measure "
					+ " from postloan_task_info t1 , sys_user t2 , biz_customer t3 ,sys_user_dept t4, sys_dept t5 ,sys_user t6 "
					+ " where t1.cust_id = t3.id and t1.manager_user_id = t2.user_id and t2.user_id = t4.user_id and t4.dept_id = t5.dept_id and t1.creator_id = t6.user_id";

			if (StringUtils.isNotEmpty(vo.getCustName())) {
				sql += " and t3.cust_name like '%" + vo.getCustName() + "%'";
			}
			if (vo.getPostLoanStatus() != -1) {
				sql += " and t1.POSTLOAN_STATUS = " + vo.getPostLoanStatus();
			}
			if (vo.getFinishDateFrom() != null) {
				sql += " and t1.FINISH_DATE >= '"
						+ new SimpleDateFormat("yyyy-MM-dd").format(vo
								.getFinishDateFrom()) + "'";
			}
			if (vo.getFinishDateTo() != null) {
				sql += " and t1.FINISH_DATE <= '"
						+ new SimpleDateFormat("yyyy-MM-dd").format(vo
								.getFinishDateTo()) + "'";
			}
			if (vo.getManager() != null
					&& StringUtils.isNotEmpty(vo.getManager().getUserName())
					&& vo.getManager().getUserId() != null) {
				sql += " and t1.MANAGER_USER_ID = "
						+ vo.getManager().getUserId();
			}

			List<PostLoanTaskInfoVO> ls = this.commonQuery.findBySQLByPages(sql
					.toString(), first, pageSize, PostLoanTaskInfoVO.class);

			return ls;
		} catch (Exception ex) {
			throw new BizException(ex);
		}
	}

	public List<PostLoanCheckVO> getCheckByTaskId(Integer taskId) {
		// TODO Auto-generated method stub
		String sql = "select LOG_ID,TASK_ID,CHECK_DATE,CHECK_MODE,CHECK_TARGET,REMARK from postloan_check_log where task_id = ? ";
		Object[] args = { taskId };
		List ls = this.commonQuery
				.findObjects(sql, args, PostLoanCheckVO.class);
		return ls;
	}

	public void addCheck(PostLoanCheckVO vo) {
		// TODO Auto-generated method stub
		String sql = " insert into postloan_check_log (TASK_ID,CHECK_DATE,CHECK_MODE,CHECK_TARGET,REMARK) "
				+ " values (?,?,?,?,?)";
		Object[] args = { vo.getTaskId(),
				new SimpleDateFormat("yyyy-MM-dd").format(vo.getCheckDate()),
				vo.getCheckMode(), vo.getCheckTarget(), vo.getRemark() };
		JdbcDaoSupport jdbc = ((JdbcDaoSupport) this.commonQuery);
		jdbc.getJdbcTemplate().update(sql, args);

	}

	public void updateCheck(PostLoanCheckVO vo) {
		// TODO Auto-generated method stub
		String sql = " update postloan_check_log set CHECK_DATE = ? , CHECK_MODE = ? ,CHECK_TARGET = ? ,REMARK = ? where LOG_ID = ? ";
		Object[] args = {
				new SimpleDateFormat("yyyy-MM-dd").format(vo.getCheckDate()),
				vo.getCheckMode(), vo.getCheckTarget(), vo.getRemark(),
				vo.getLogId() };
		JdbcDaoSupport jdbc = ((JdbcDaoSupport) this.commonQuery);
		jdbc.getJdbcTemplate().update(sql, args);
	}

	public void deleteCheck(Integer logId) {
		// TODO Auto-generated method stub
		String sql = " delete from postloan_check_log where log_id = ? ";
		Object[] args = { logId };
		JdbcDaoSupport jdbc = ((JdbcDaoSupport) this.commonQuery);
		jdbc.getJdbcTemplate().update(sql, args);
	}

	public void updateCheckResult(PostLoanTaskInfoVO vo) {
		// TODO Auto-generated method stub
		String sql = " update postloan_task_info set CHECK_RESULT = ? , RESULT_REMARK = ? ,FROZEN_LIMIT = ? ,FROZEN_BALANCE = ?,legal_measure=? where TASK_ID = ? ";
		Object[] args = { vo.getCheckResult(), vo.getResultRemark(),
				vo.getFrozenLimit(), vo.getFrozenBalance(),
				vo.getLegalMeasure(), vo.getTaskId() };
		JdbcDaoSupport jdbc = ((JdbcDaoSupport) this.commonQuery);
		jdbc.getJdbcTemplate().update(sql, args);
	}

	@Override
	public void addPostLoanTask(PostLoanTaskInfoVO infoVO) throws BizException{
		// TODO Auto-generated method stub
		try {
			String sql = "INSERT INTO postloan_task_info (CUST_ID, POSTLOAN_GENERATE_TYPE, START_DATE, FINISH_DATE, MANAGER_USER_ID, POSTLOAN_STATUS, CREATOR_ID, CREATE_DATE)"
					+ " values (?,?,?,?,?,?,?,?)";
			Object[] args = { infoVO.getCustId(),infoVO.getPostLoanGenerateType(),infoVO.getStartDate(),infoVO.getFinishDate(),
					infoVO.getManageId(),infoVO.getPostLoanStatus(),infoVO.getCreatorId(),infoVO.getCreateDate() };
			JdbcDaoSupport jdbc = ((JdbcDaoSupport) this.commonQuery);
			jdbc.getJdbcTemplate().update(sql, args);
		} catch (Exception e) {
			 
			throw new BizException(e);
		}
		
	}

}
