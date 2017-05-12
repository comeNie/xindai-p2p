package com.qfw.common.dao;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author lsj
 *
 */
public interface ICommonQuery {
	
	public int findCountBySQL(StringBuilder sqlString , Object[] object) ;
	
	/**
	 * 组装count语句查询条数
	 * @param sql
	 * @param object
	 * @return
	 */
	public int findCountByWapSQL(String sql,Object[] object);
	
	public int findCountByWapSQL(String sql);
	/**
	   * 根据SQL查询语句查询结果集。用JdbcTemplate方式进行查询。
	   * 对SQL查询语句有以下限制：
	   * 1. SQL查询语句没有特殊字符。
	   * @param sqlString SQL查询语句
	   * @return List(结果集中每行作为一个成员) of Map(每列作为一个成员且使用字段名称作为键)，Map对象列表
	   * @since 
	   */
	public List findBySQL(String sqlString) ;
	
	/**
	 * add 2009-06-18 hongjl
	 * 根据SQL查询语句查询结果集。用JdbcTemplate方式进行查询。
	 * 对SQL查询语句有以下限制：
	 * 1. SQL查询语句没有特殊字符。
	 * @param sqlString SQL查询语句
	 * @param params  Map(key:value)(传入要键值对应的2个字段，一个字段作为key，一个字段作为vlaue) key=params[0] value=params[1]
	 * @return Map(key:value)(键值对应的2个字段，一个字段作为key，一个字段作为vlaue)
	 * @
	 */
	public Map findBySQL(StringBuilder sqlString,final String[] params) ;
	
		
	/**
	 * 带参数的 jdbctemple 查询 
	 * @param sqlString
	 * @param object
	 * @return
	 * @
	 */
	public List findBySQL(String sqlString , Object[] object) ;
	/**
	 * 将查询结果返回实体类
	 * @param sqlString
	 * @param c
	 * @return
	 */
	public List findObjects(String sqlString,Class c);
	/**
	 * 将查询结果返回实体类，带参数的查询方法
	 * @param sqlString
	 * @param args
	 * @param clazz
	 * @return
	 */
	public List findObjects(String sqlString, Object[] args, final Class clazz);
	/**
	 * 分页查sql
	 * @param sqlString
	 * @param first
	 * @param pageSize
	 * @param args
	 * @param clazz
	 * @return
	 */
	public List findBySQLByPages(String sqlString,final int first,final int pageSize,Object args[],final Class clazz);
	/**
	 * 通过sql分页查询出对象
	 * @param sqlString
	 * @param first
	 * @param pageSize
	 * @param clazz
	 * @return
	 */
	public List findBySQLByPages(String sqlString,final int first,final int pageSize,final Class clazz);
	
	/**
	 * 分页查询sql语句
	 * @param sqlString
	 * @param first
	 * @param pageSize
	 * @return
	 */
	public List<Map<String,Object>> findBySQLByPages(String sqlString,final int first,final int pageSize);
	
	/**
	 * 分页查询sql语句
	 * @param sqlString
	 * @param first
	 * @param pageSize
	 * @param args
	 * @return
	 */
	public List<Map<String,Object>> findBySQLByPages(String sqlString,final int first,final int pageSize,Object[] args);

}
