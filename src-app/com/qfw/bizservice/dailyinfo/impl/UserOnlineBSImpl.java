package com.qfw.bizservice.dailyinfo.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.qfw.bizservice.dailyinfo.IUserOnlineBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.StringUtils;
import com.qfw.dao.dailyinfo.IUserOnlineDAO;
import com.qfw.model.bo.BizUserOnlineBO;

@Service("userOnlineBS")
public class UserOnlineBSImpl extends BaseServiceImpl implements IUserOnlineBS {
	
	private IUserOnlineDAO userOnlineDAO;
	private Logger log = LogFactory.getInstance().getBusinessLogger();

	@Override
	public BizUserOnlineBO findBizUserOnlineBOById(Integer id)
			throws BizException {
		return (BizUserOnlineBO)userOnlineDAO.findById(BizUserOnlineBO.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BizUserOnlineBO> findUserOnlinePagesByVO(
			BizUserOnlineBO searchVO, int first, int pageSize) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select * from BIZ_USER_ONLINE s");
			sb.append(" where 1=1");
			if (StringUtils.isNotEmpty(searchVO.getLoginUserName())) {
				sb.append(" and s.LOGIN_USER_NAME like '%").append(searchVO.getLoginUserName()).append("%'");
			}
			sb.append(" order by s.LOGIN_TIME desc");
			return commonQuery.findBySQLByPages(sb.toString(), first, pageSize, BizUserOnlineBO.class);
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.error(e.getMessage());
			}
		}
		return null;
	}

	@Override
	public int findUserOnlineCountByVO(BizUserOnlineBO searchVO) {
		StringBuilder sb = new StringBuilder();
		sb.append("select count(1) from BIZ_USER_ONLINE s");
		sb.append(" where 1=1");
		if (StringUtils.isNotEmpty(searchVO.getLoginUserName())) {
			sb.append(" and s.LOGIN_USER_NAME like '%").append(searchVO.getLoginUserName()).append("%'");
		}
		return this.commonQuery.findCountBySQL(sb, null);
	}

	public IUserOnlineDAO getUserOnlineDAO() {
		return userOnlineDAO;
	}

	public void setUserOnlineDAO(IUserOnlineDAO userOnlineDAO) {
		this.userOnlineDAO = userOnlineDAO;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}
	
}
