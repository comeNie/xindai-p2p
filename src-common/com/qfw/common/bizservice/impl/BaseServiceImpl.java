package com.qfw.common.bizservice.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.dao.IBaseDAO;
import com.qfw.common.dao.ICommonQuery;
import com.qfw.common.dao.impl.BaseDAOImpl;
import com.qfw.common.dao.impl.CommonQueryImpl;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.bo.BizBaseBO;

public class BaseServiceImpl implements IBaseService{
	protected ICommonQuery commonQuery;
	protected IBaseDAO baseDAO;	
	
	@Resource
	public void setMyCommonQuery(ICommonQuery commonQuery) {
		this.setCommonQuery(commonQuery);
	}
	
	@Resource
	public void setMyBaseDAO(IBaseDAO baseDAO) {
		this.setBaseDAO(baseDAO);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List findObjectsByPages(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters,LazyDataModel lazyDataMode) {
		throw new RuntimeException("子类必须重写findObjectsByPages方法");
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object find(Class entityClass,String id) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().get(entityClass, Integer.valueOf(id));
	}
	@Override
	public Object find(Class entityClass,Long id) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().get(entityClass, id);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object find(Class entityClass,Integer id) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().get(entityClass, id);
	}
	@Override
	public int count(String hql) {		
		return baseDAO.count(hql);
	}

	public HibernateTemplate getHibernateTemplate(){
		return ((BaseDAOImpl)baseDAO).getHibernateTemplate();
	}
	public JdbcTemplate getJdbcTemplate(){
		return ((CommonQueryImpl)commonQuery).getJdbcTemplate();
	}
	public ICommonQuery getCommonQuery() {
		return commonQuery;
	}
	public void setCommonQuery(ICommonQuery commonQuery) {
		this.commonQuery = commonQuery;
	}
	public IBaseDAO getBaseDAO() {
		return baseDAO;
	}
	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void save(Object obj) {
		baseDAO.save(obj);		
	}
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void delete(Object obj) {
		baseDAO.delete(obj);		
	}
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void update(Object obj){
		baseDAO.update(obj);
	}
	@Override
	public List findByHQL(String hqlString) {
		return baseDAO.findByHQL(hqlString);
	}
	/**
	 * 更新操作人
	 * @param baseBO
	 */
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void updateOperator(BizBaseBO baseBO){
		SysUser user = null;
		try{
			user = ViewOper.getUser();
		}catch(Exception e){
			
		}
		if(user !=  null){
			baseBO.setSysUpdateUser(user.getUserId());
		}else{
			baseBO.setSysUpdateUser(0);//自动化操作
		}
		baseBO.setSysUpdateTime(new Date());
	}
	/**
	 * 创建or更新操作人
	 * @param baseBO
	 */
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void createOperator(BizBaseBO baseBO){
		SysUser user = null;
		try{
			user = ViewOper.getUser();
		}catch(Exception e){
			
		}
		if(user !=  null){
			baseBO.setSysCreateUser(user.getUserId());
		}else{
			baseBO.setSysCreateUser(0);//自动化操作
		}
		baseBO.setSysCreateTime(new Date());
	}
	
	public List<Map<String,Object>> getProvinceList(){
		List<Map<String,Object>> list = getJdbcTemplate().queryForList("select * from  XX_AREA WHERE PARENT IS NULL");
		return list;
	}
	
	public List<Map<String,Object>> getCityListByProId(String proId){
		List<Map<String,Object>> list = getJdbcTemplate().queryForList("select * from XX_AREA WHERE PARENT=?",proId);
		return list;
	}
}
