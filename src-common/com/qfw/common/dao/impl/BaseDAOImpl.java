package com.qfw.common.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.qfw.common.dao.IBaseDAO;

public class BaseDAOImpl extends HibernateDaoSupport implements IBaseDAO {

	@Resource
	public void setMySessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public Object save(Object entity) {
		Object id = this.getHibernateTemplate().save(entity);
		return id;
	}

	@Override
	public void update(Object entity) {
		this.getHibernateTemplate().update(entity);
	}

	@Override
	public void merge(Object entity) {
		this.getHibernateTemplate().merge(entity);
	}

	@Override
	public Object findById(Class entityClass, Serializable id) {
		return this.getHibernateTemplate().get(entityClass, id);
	}

	@Override
	public void delete(Object entity) {
		this.getHibernateTemplate().delete(entity);
	}

	@Override
	public List findBySQL(String sqlString) {
		final String finalSQLString = sqlString;
		List list = this.getJdbcTemplate().queryForList(finalSQLString);
		return list;
	}

	public List findBySQL(String sqlString, Object[] obj) {
		final String finalSQLString = sqlString;
		List list = this.getJdbcTemplate().queryForList(finalSQLString, obj);
		return list;
	}

	@Override
	public List findByHQL(String hqlString) {
		// final String hqlStr = hqlString;
		return getHibernateTemplate().find(hqlString);
	}

	public List findByHQLByPages(String hqlString, final int first,
			final int pageSize) {
		final String hqlStr = hqlString;
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				List li = new ArrayList();
				Query query = session.createQuery(hqlStr);
				query.setFirstResult(first).setMaxResults(pageSize);
				li = query.list();
				/*
				 * Iterator it = query.iterate(); while(it.hasNext()){
				 * li.add(it.next()); }
				 */
				return li;
			}
		});
	}

	@SuppressWarnings("unchecked")
	public int count(String hqlString) {
		final String hqlStr = hqlString;
		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						int count = 0;
						Query query = session.createQuery(hqlStr);
						return Integer.valueOf(String.valueOf(query
								.uniqueResult()));
					}
				});
	}

	public void getLazyData(Object object) {
		this.getHibernateTemplate().initialize(object);
	}

	public void evic(Object object) {
		this.getHibernateTemplate().evict(object);
	}

	public void flush() {
		this.getHibernateTemplate().flush();
	}

	protected JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<?> findByHQL(String hqlString, Object[] objs) {
		return this.getHibernateTemplate().find(hqlString, objs);
	}

	 

	protected void deleteById(String hql){
		final String hqlStr = hql;
			this.getHibernateTemplate().executeFind(new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					Query query = session.createQuery(hqlStr);
					query.executeUpdate();
					return null;
				}
			});
		 
	}

}
