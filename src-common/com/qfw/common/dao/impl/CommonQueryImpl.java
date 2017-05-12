package com.qfw.common.dao.impl;

import java.io.Reader;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.qfw.common.dao.ICommonQuery;
import com.qfw.common.util.DataBaseUtil;
import com.qfw.common.util.StringUtils;

/**
 * 
 * @author lsj
 * 
 */
public class CommonQueryImpl extends JdbcDaoSupport implements ICommonQuery {

	private final Log logger = LogFactory.getLog(this.getClass());
	public static final java.lang.String CHAR_VALUE = "CHAR"; // char

	public static final java.lang.String DATE_VALUE = "DATE"; // timestamp

	public static final java.lang.String ORA_STRING_VALUE = "VARCHAR2"; // String
	
	public static final String STRING_VALUE = "VARCHAR";

	public static final java.lang.String ORA_DECIMAL_VALUE = "NUMBER"; // int,decimal

	public static final java.lang.String ORA_CLOB_VALUE = "CLOB";

	public static final int DEFAULT_DATE_FORMAT = 0; // YYYY-MM-DD HH:MM:SS

	public static final int SHORT_DATE_FORMAT = 1; // YYYY-MM-DD
	private static final ResultSetExtractor RowCallbackHandler = null;

	public int findCountBySQL(StringBuilder sqlString, Object[] object) {
		if (sqlString == null || "".equals(sqlString.toString().trim())) {
			if (logger.isDebugEnabled())
				logger.debug("sqlString is null!!!");
			return 0;
		}
		final String finalString = sqlString.toString();
		if (object == null) {
			return this.getJdbcTemplate().queryForInt(finalString);
		} else {
			return this.getJdbcTemplate().queryForInt(finalString, object);
		}

	}
	
	/**
	 * 组装count语句查询条数
	 * @param sql
	 * @param object
	 * @return
	 */
	public int findCountByWapSQL(String sql,Object[] object){
		StringBuilder sqlString = new StringBuilder("select count(1) from (");
		sqlString.append(sql).append(") as total");
		return findCountBySQL(sqlString, object);
	}

	/**
	 * 组装count语句查询条数
	 * @param sql
	 * @param object
	 * @return
	 */
	public int findCountByWapSQL(String sql){
		StringBuilder sqlString = new StringBuilder("select count(1) from (");
		sqlString.append(sql).append(") as total");
		return findCountBySQL(sqlString, null);
	}
	
	public Map findBySQL(StringBuilder sqlString, final String[] params) {
		if (logger.isDebugEnabled()) {
			logger.debug("---findBySQL--start---");
		}
		if (sqlString == null || "".equals(sqlString.toString().trim())) {
			if (logger.isDebugEnabled())
				logger.debug("sqlString is null!!!");
			return null;
		}
		final Map map = new HashMap();
		final String finalString = sqlString.toString();
		this.getJdbcTemplate().query(finalString, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				if (params.length == 2) {
					if (!map.containsKey(rs.getString(params[0]))) {
						if (rs.getString(params[1]) != null
								&& !"".equals(rs.getString(params[1]))) {
							map.put(rs.getString(params[0]),
									rs.getString(params[1]));
						}
					}

				}
			}
		});
		if (logger.isDebugEnabled()) {
			logger.debug("sqlString===" + sqlString);
			logger.debug("---findBySQL--end---");
		}
		return map;
	}

	public List findBySQL(String sqlString) {
		if (logger.isDebugEnabled()) {
			logger.debug("---findBySQL--start---");
		}
		if (StringUtils.isEmpty(sqlString)) {
			if (logger.isDebugEnabled())
				logger.debug("sqlString is null!!!");
			return null;
		}
		List results = this.getJdbcTemplate().queryForList(sqlString);
		if (logger.isDebugEnabled()) {
			logger.debug("sqlString===" + sqlString);
			logger.debug("---findBySQL--end---");
		}
		return results;
	}

	/**
	 * 带参数的 jdbctemple 查询
	 */
	public List findBySQL(String sqlString, Object[] object) {
		if (logger.isDebugEnabled()) {
			logger.debug("---findBySQL--start---");
		}
		if (sqlString == null || "".equals(sqlString.toString().trim())) {
			if (logger.isDebugEnabled())
				logger.debug("sqlString is null!!!");
			return null;
		}
		//final String finalString = sqlString.toString();
		List results = this.getJdbcTemplate().queryForList(sqlString, object);
		if (logger.isDebugEnabled()) {
			logger.debug("sqlString===" + sqlString);
			logger.debug("---findBySQL--end---");
		}
		return results;
	}

	@Override
	public List findObjects(String sqlString, final Class clazz) {
		return this.getJdbcTemplate().query(sqlString, new RowMapper(){ 
			public Object mapRow(ResultSet rs, int rowNumber) throws SQLException { 
				
			return processRS1(rs,clazz); 
			}}); 
	}
	/**
	 * 分页查询sql语句
	 */
	public List findBySQLByPages(String sqlString,final int first,final int pageSize,final Class clazz) {
		StringBuilder sql2 = new StringBuilder();// 根据翻页标签查询条件返回的所在行的查询语句
		/*sql2.append("select * from ( select row_.*, rownum rownum_ from (");
		sql2.append(sqlString);
		sql2.append(")row_) where rownum_ <");
		sql2.append(first + pageSize + 1); // 查询开始行数
		sql2.append("  and rownum_ >");
		sql2.append(first);*/
		sql2.append(sqlString).append(" limit ").append(first).append(",").append(pageSize);
		
		////System.out.println("sql535353535===="+sql2);
		return this.getJdbcTemplate().query(sql2.toString(), new RowMapper(){ 
			public Object mapRow(ResultSet rs, int rowNumber) throws SQLException { 
			return processRS1(rs,clazz); 
			}}); 
	}
	
	public List findBySQLByPages(String sqlString,final int first,final int pageSize,Object args[],final Class clazz) {
		StringBuilder sql2 = new StringBuilder();// 根据翻页标签查询条件返回的所在行的查询语句
		/*oracle
		sql2.append("select * from ( select row_.*, rownum rownum_ from (");
		sql2.append(sqlString);
		sql2.append(")row_) where rownum_ <");
		sql2.append(first + pageSize + 1); // 查询开始行数
		sql2.append("  and rownum_ >");
		sql2.append(first);
		*/
		sql2.append(sqlString).append(" limit ").append(first).append(",").append(pageSize);
		return this.getJdbcTemplate().query(sql2.toString(),args, new RowMapper(){ 
			public Object mapRow(ResultSet rs, int rowNumber) throws SQLException { 
			return processRS1(rs,clazz); 
			}}); 
	}
	
	
	/**
	 * 分页查询sql语句
	 * @param sqlString
	 * @param first
	 * @param pageSize
	 * @return
	 */
	public List<Map<String,Object>> findBySQLByPages(String sqlString,final int first,final int pageSize) {
		StringBuilder sql2 = new StringBuilder();// 根据翻页标签查询条件返回的所在行的查询语句
		/*sql2.append("select * from ( select row_.*, rownum rownum_ from (");
		sql2.append(sqlString);
		sql2.append(")row_) where rownum_ <");
		sql2.append(first + pageSize + 1); // 查询开始行数
		sql2.append("  and rownum_ >");
		sql2.append(first);*/
		sql2.append(sqlString).append(" limit ").append(first).append(",").append(pageSize);
		return this.getJdbcTemplate().queryForList(sql2.toString()); 
	}
	/**
	 * 分页查询sql语句
	 * @param sqlString
	 * @param first
	 * @param pageSize
	 * @param args
	 * @return
	 */
	public List<Map<String,Object>> findBySQLByPages(String sqlString,final int first,final int pageSize,Object[] args) {
		StringBuilder sql2 = new StringBuilder();// 根据翻页标签查询条件返回的所在行的查询语句
		/*sql2.append("select * from ( select row_.*, rownum rownum_ from (");
		sql2.append(sqlString);
		sql2.append(")row_) where rownum_ <");
		sql2.append(first + pageSize + 1); // 查询开始行数
		sql2.append("  and rownum_ >");
		sql2.append(first);*/
		sql2.append(sqlString).append(" limit ").append(first).append(",").append(pageSize);
		return this.getJdbcTemplate().queryForList(sql2.toString(), args);
	}
	
	public int findSqlCount(String sql){
		
		return 0;
	}
	
	public List findObjects(String sqlString, Object[] args, final Class clazz){
		return this.getJdbcTemplate().query(sqlString, args, new RowMapper(){
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				return processRS1(rs,clazz);
			}
			
		});
	}

	private List processRS(ResultSet rs, Class clazz) {
		Reader stream = null;
		Object obj = null;
		Object value = null;
		List objs = new ArrayList();
		try {
			obj = clazz.newInstance();		
			ResultSetMetaData mData = rs.getMetaData();
			int maxColumn = mData.getColumnCount(); // 最大列
			String colName = null;
			String colType = null;			
			Method method = null;
			while (rs.next()) {					
				for (int i = 0; i < maxColumn; i++) {					
					colName = StringUtils.upperFirst(DataBaseUtil.translateColumnIntoProperty(mData.getColumnName(i + 1)));
					String methName = "set"+colName;
					colType = mData.getColumnTypeName(i + 1).toUpperCase();
					if (colType.equals(this.ORA_CLOB_VALUE)) {// Clob
						Clob clob = rs.getClob(i + 1);
						if (clob != null) {
							char[] c = new char[(int) clob.length()];
							stream = clob.getCharacterStream();
							stream.read(c);
							method = clazz.getMethod(methName, String.class);
							value = String.valueOf(c);
						}
					} else if (colType.equals(this.DATE_VALUE)) { // date from
						method = clazz.getMethod(methName, Date.class);
						value = rs.getDate(i + 1);

					} else {// char/string/number/timestamp type
						method = clazz.getMethod(methName, String.class);
						value = rs.getString(i + 1);
					}
					if(value!=null)
					method.invoke(obj, value);
				}
				objs.add(obj);
			}
			
		} catch (Exception e) {
			 
		} finally {

		}
		return objs;
	}
	/**
	 * 将行记录转化为对象
	 * @param rs
	 * @param clazz
	 * @return
	 */
	private Object processRS1(ResultSet rs, Class clazz) {
		try{
		ResultSetMetaData mData = rs.getMetaData();
		int maxColumn = mData.getColumnCount(); // 最大列
		String colName = null;
		String colType = null;			
		Method method = null;
		Reader stream = null;
		Object obj = clazz.newInstance();	
		Object value = null;
			for (int i = 0; i < maxColumn; i++) {					
				colName = StringUtils.upperFirst(DataBaseUtil.translateColumnIntoProperty(mData.getColumnName(i + 1)));
				if("Rownum_".equals(colName)){
					continue;
				}
			    ////System.out.println(colName+":"+rs.getString(i + 1)+":"+colType);
				String methName = "set"+colName;
				colType = mData.getColumnTypeName(i + 1).toUpperCase();
				////System.out.println(methName+":"+colType);
				if(colType.equals(this.ORA_STRING_VALUE)||colType.equals(STRING_VALUE)||colType.equals(CHAR_VALUE)||colType.equals("")){// char/string/number/timestamp type
					method = clazz.getMethod(methName, String.class);
					value = rs.getString(i + 1);
				}else if (colType.equals(this.ORA_CLOB_VALUE)) {// Clob
					Clob clob = rs.getClob(i + 1);
					if (clob != null) {
						char[] c = new char[(int) clob.length()];
						stream = clob.getCharacterStream();
						stream.read(c);
						method = clazz.getMethod(methName, String.class);
						value = String.valueOf(c);
					}
				} else if (colType.equals(this.DATE_VALUE)) { // date from
					method = clazz.getMethod(methName, Date.class);
					value = rs.getDate(i + 1);
				} else if (colType.equals(this.ORA_DECIMAL_VALUE)){
					int scale = mData.getScale(i + 1);
					if(scale > 0){
						method = clazz.getMethod(methName, Double.class);
						value = rs.getDouble(i+1);
					}else{
						method = clazz.getMethod(methName, Integer.class);
						value = rs.getInt(i+1);
					}
					
				}else if (colType.equals("INTEGER") || colType.equals("INT") ){
					method = clazz.getMethod(methName, Integer.class);
					value = rs.getInt(i+1);
				}else if (colType.equals(this.DATE_VALUE) || colType.equals("TIMESTAMP") || "DATETIME".equals(colType)) { // date from
					method = clazz.getMethod(methName, Date.class);
					value = rs.getTimestamp(i + 1);
				}else if("BIGINT".equals(colType)){
					method = clazz.getMethod(methName, Integer.class);
					value = rs.getInt(i+1);
				}else if("DECIMAL".equals(colType)){
					method = clazz.getMethod(methName, BigDecimal.class);
					value = rs.getBigDecimal(i+1);
					//value = bd.intValue();
				}
				if(value!=null){
					////System.out.println(value);
					method.invoke(obj, value);
				}
				
			}
			return obj;
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return null;
		}
	
}

