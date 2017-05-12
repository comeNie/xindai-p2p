package com.qfw.dao.bussparams.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qfw.common.dao.impl.BaseDAOImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.util.StringUtils;
import com.qfw.dao.bussparams.IGrantUserDAO;
import com.qfw.jbpm.model.bo.Jbpm4GrantUser;

@Repository("grantUserDAO")
public class GrantUserDAOImpl extends BaseDAOImpl implements IGrantUserDAO {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Jbpm4GrantUser> findGrantUser(Jbpm4GrantUser searchGrantUserVO) throws BizException {
		StringBuffer sb = new StringBuffer();
		sb.append("select s.ID,s.GRANT_USER_ID,s.GRANTED_USER_ID,");
		sb.append(" s.FLOW_KEY,s.START_DATE,s.END_DATE");
		sb.append(" from JBPM4_GRANT_USER s");
		sb.append(" where 1=1");
			if (searchGrantUserVO.getGrantUserId() != null && 0 != searchGrantUserVO.getGrantUserId() && !"".equals(searchGrantUserVO.getGrantUserId())) {	
			sb.append(" and s.GRANT_USER_ID like '%").append(searchGrantUserVO.getGrantUserId()).append("%'");
		}
			if (searchGrantUserVO.getGrantedUserId() != null && 0 != searchGrantUserVO.getGrantedUserId() && !"".equals(searchGrantUserVO.getGrantedUserId())) {	
			sb.append(" and s.GRANTED_USER_ID like '%").append(searchGrantUserVO.getGrantedUserId()).append("%'");
		}
		sb.append(" order by s.ID");
		List<Jbpm4GrantUser> grantUserList = this.findByHQL(sb.toString());
		return grantUserList;
	}
	
}
