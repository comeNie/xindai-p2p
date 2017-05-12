package com.qfw.common.bizservice;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.qfw.common.dao.ICommonQuery;
import com.qfw.common.dao.impl.BaseDAOImpl;
import com.qfw.common.dao.impl.CommonQueryImpl;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.NumberUtils;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.bo.BizBaseBO;
import com.qfw.platform.model.bo.CmsFriendLink;
import com.qfw.platform.model.transfer.TransferSearchVo;

public interface IBaseService {	
	@SuppressWarnings("rawtypes")
	public List findObjectsByPages(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters,LazyDataModel lazyDataMode);
	@SuppressWarnings("rawtypes")
	public Object find(Class entityClass,String id);
	public Object find(Class entityClass,Integer id);
	public int count(String hql);
	public void save(Object obj);
	public void delete(Object obj);
	public void update(Object obj);
	public List findByHQL(String hqlString);
	public HibernateTemplate getHibernateTemplate();
	public JdbcTemplate getJdbcTemplate();
	public ICommonQuery getCommonQuery();
	
	
	public List<Map<String,Object>> getProvinceList();
	public List<Map<String,Object>> getCityListByProId(String proId);
	
	/**
	 * 更新操作人
	 * @param baseBO
	 */
	public void updateOperator(BizBaseBO baseBO);
	/**
	 * 创建or更新操作人
	 * @param baseBO
	 */
	public void createOperator(BizBaseBO baseBO);
	public Object find(Class entityClass, Long id);
	
	

}
