package com.qfw.common.dao.permission.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.qfw.common.dao.impl.BaseDAOImpl;
import com.qfw.common.dao.permission.IUserDAO;
import com.qfw.common.model.permission.SysFunction;
import com.qfw.common.model.permission.SysRole;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.web.NavigationApp;

public class UserDAOImpl extends BaseDAOImpl implements IUserDAO {

	/* 
	@SuppressWarnings("unchecked")
	@Override
	public Set<SysFunction> getFuns(SysUser user) {
		final String hqlStr = "from SysUser where userId = '"+user.getUserId()+"'";
		return(Set<SysFunction>) getHibernateTemplate().execute(new HibernateCallback() {
		public Object doInHibernate(Session session)
	        throws HibernateException, SQLException {
			    Set<SysFunction> funs = new HashSet<SysFunction>();
				List<SysUser> li = new ArrayList<SysUser>();
				Query query = session.createQuery(hqlStr);
				li = query.list();
				if(li.size()>0){
					SysUser user = li.get(0);
					Set<SysRole> sysRoles = user.getSysRoles();
					for(SysRole role: sysRoles){						
						Set<SysFunction> _funs = NavigationApp.roleMap.get(role.getRoleId());
						if(_funs == null || _funs.isEmpty()){
							_funs = role.getSysFunctions();
							NavigationApp.roleMap.put(role.getRoleId(), _funs);
						}
						//role.getSysFunctions();
						funs.addAll(_funs);
					}
				}
				Iterator it = query.iterate();
				while(it.hasNext()){
					li.add(it.next());
				}
				return funs;
			}
		});  
	}
	*/
	@Override
	public SysUser registerUser(SysUser user) {
		// TODO Auto-generated method stub
		try {
			Integer userid=(Integer) this.save(user);
			return (SysUser) this.findById(SysUser.class, userid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			 
		}
		return null;
	}
	
	

}
