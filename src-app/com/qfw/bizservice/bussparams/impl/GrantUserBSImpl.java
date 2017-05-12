package com.qfw.bizservice.bussparams.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qfw.bizservice.bussparams.IGrantUserBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.dao.bussparams.IGrantUserDAO;
import com.qfw.jbpm.model.bo.Jbpm4GrantUser;

@Service("grantUserBS")
public class GrantUserBSImpl extends BaseServiceImpl implements IGrantUserBS {
	
	@Autowired
	private IGrantUserDAO grantUserDAO;
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public List<Jbpm4GrantUser> findGrantUser (Jbpm4GrantUser searchGrantUserVO) throws BizException {
		return this.grantUserDAO.findGrantUser(searchGrantUserVO);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Jbpm4GrantUser> findGrantUserPagesByVO(
			Jbpm4GrantUser searchGrantUserVO, int first, int pageSize) {
		try {
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
			return commonQuery.findBySQLByPages(sb.toString(), first, pageSize, Jbpm4GrantUser.class);
		} catch (Exception e) {
			 
		}
		return null;
	}

	@Override
	public int findGrantUserCountByVO(Jbpm4GrantUser searchGrantUserVO) {
		StringBuilder sb = new StringBuilder();
		sb.append("select count(1)");
		sb.append(" from JBPM4_GRANT_USER s");
		sb.append(" where 1=1");
			if (searchGrantUserVO.getGrantUserId() != null && 0 != searchGrantUserVO.getGrantUserId() && !"".equals(searchGrantUserVO.getGrantUserId())) {
			sb.append(" and s.GRANT_USER_ID like '%").append(searchGrantUserVO.getGrantUserId()).append("%'");
		}
			if (searchGrantUserVO.getGrantedUserId() != null && 0 != searchGrantUserVO.getGrantedUserId() && !"".equals(searchGrantUserVO.getGrantedUserId())) {
			sb.append(" and s.GRANTED_USER_ID like '%").append(searchGrantUserVO.getGrantedUserId()).append("%'");
		}
		return this.commonQuery.findCountBySQL(sb, null);
	}

	@Override
	public Jbpm4GrantUser findGrantUserById(Integer id) throws BizException {
		this.grantUserDAO.findById(Jbpm4GrantUser.class,id);
		return null;
	}
	
//	@SuppressWarnings("unchecked")
//	public List<BizProductBO> checkProductNum(String productName,Integer id) throws BizException{
//		if(productName != null && !"".equals(productName)){
//			StringBuilder sb = new StringBuilder("from BizProductBO where ");
//			sb.append(" productName = '").append(productName).append("'");
//			if(id!=null && !id.equals(Integer.valueOf(0))){
//				sb.append(" and id != '").append(id).append("'");
//			}
//			List<BizProductBO> list = baseDAO.findByHQL(sb.toString());
//			return list;
//		}else{
//			return null;
//		}
//	}
	
//	@SuppressWarnings("unchecked")
//	public List<BizProductBO> checkProductName(String productName,Integer id)throws BizException{
//		if(productName != null && !"".equals(productName)){
//			StringBuilder sb = new StringBuilder("from BizProductBO where ");
//			sb.append(" productName = '").append(productName).append("'");
//			if(id!=null && !id.equals(Integer.valueOf(0))){
//				sb.append(" and id != '").append(id).append("'");
//			}
//			List<BizProductBO> list = baseDAO.findByHQL(sb.toString());
//			return list;
//		}else{
//			return null;
//		}
//	}
	
	@Override
	public Jbpm4GrantUser getJbpm4GrantUserById(Integer id) throws BizException{
		return (Jbpm4GrantUser) grantUserDAO.findById(Jbpm4GrantUser.class, id);
	}

	public IGrantUserDAO getGrantUserDAO() {
		return grantUserDAO;
	}

	public void setGrantUserDAO(IGrantUserDAO grantUserDAO) {
		this.grantUserDAO = grantUserDAO;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

}
