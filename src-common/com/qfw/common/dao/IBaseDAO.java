package com.qfw.common.dao;

import java.io.Serializable;
import java.util.List;

public interface IBaseDAO {
	/**
	 * 保存实体类到数据库
	 * @param entity
	 * @return
	 */
	public Object save(Object entity);
	/**
	 * 更新实体类到数据库
	 * @param entity
	 */
	public void update(Object entity);
	/**
	 * 通过id查询数据
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public Object findById(Class entityClass,Serializable id);
	/**
	 * 删除数据
	 * @param entity
	 */
	public void delete(Object entity);
	/**
	 * 通过sql语句查询数据库
	 * @param sqlString
	 * @return
	 */
	public List findBySQL(String sqlString);
	
	/**
	 * 通过sql语句查询数据库,带参数
	 * @param sqlString
	 * @param obj
	 * @return
	 */
	public List findBySQL(String sqlString,Object[] obj);
	/**
	 * 通过hql语句查询数据库
	 * @param hqlString
	 * @return
	 */
	public List findByHQL(String hqlString);
	
	/**
	 * 通过hql语句查询数据库
	 * @param hqlString
	 * @return
	 */
	public List findByHQL(String hqlString,Object[] objs);
	
	/**
	 * hql分页查询
	 * @param hqlString
	 * @param first
	 * @param pageSize
	 * @return
	 */
	public List findByHQLByPages(String hqlString,final int first,final int pageSize);
	public int count(String hqlString);
	public void merge(Object entity);
	public void evic(Object object);
	public void flush();
	public void getLazyData(Object object);

}
