package com.qfw.jbpm.bizservice.impl;

import java.util.List;

import org.jbpm.api.identity.Group;
import org.jbpm.api.identity.User;
import org.jbpm.pvm.internal.identity.impl.GroupImpl;
import org.jbpm.pvm.internal.identity.impl.UserImpl;
import org.jbpm.pvm.internal.identity.spi.IdentitySession;

import com.qfw.common.dao.ICommonQuery;
import com.qfw.common.dao.permission.IUserDAO;
import com.qfw.common.model.permission.SysUser;

public class JbpmSessionEngine implements IdentitySession {

	private ICommonQuery commonQuery;
	@Override
	public String createUser(String userId, String givenName,
			String familyName, String businessEmail) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findUserById(String userId) {
		String sql = "SELECT TO_CHAR(USER_ID) AS ID,USER_NAME AS FAMILY_NAME,'' AS GIVEN_NAME,EMAIL AS BUSINESS_EMAIL FROM SYS_USER WHERE USER_ID = ? ";
		List<User> users = commonQuery.findObjects(sql, new String[]{userId},UserImpl.class);
		if(users!=null && !users.isEmpty()){
			return users.get(0);
		}
		return null;
	}

	private User createUser(SysUser sysUser){
		UserImpl user = new UserImpl();
		if(sysUser!=null){
			user.setId(String.valueOf(sysUser.getUserId()));
			user.setFamilyName(sysUser.getUserName());
			user.setGivenName("");
			user.setPassword(sysUser.getPassword());
		}
		return user;
	}
	@Override
	public List<User> findUsersById(String... userIds) {
		StringBuilder hql = new StringBuilder("SELECT TO_CHAR(USER_ID) AS ID,USER_NAME AS FAMILY_NAME,'' AS GIVEN_NAME,EMAIL AS BUSINESS_EMAIL FROM SYS_USER WHERE 1=2 ");
		for (String userId : userIds) {
			hql.append(" OR USER_ID = ?");
		}
		List<User> users = commonQuery.findObjects(hql.toString(), userIds,UserImpl.class);
		return users;
	}

	@Override
	public List<User> findUsers() {
		String sql = "SELECT TO_CHAR(USER_ID) AS ID,USER_NAME AS FAMILY_NAME,'' AS GIVEN_NAME,EMAIL AS BUSINESS_EMAIL FROM SYS_USER";
		List<User> users = commonQuery.findObjects(sql,UserImpl.class);
		return users;
	}

	@Override
	public void deleteUser(String userId) {
		// TODO Auto-generated method stub

	}

	@Override
	public String createGroup(String groupName, String groupType,
			String parentGroupId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findUsersByGroup(String groupId) {
		String sql = "SELECT DISTINCT TO_CHAR(U.USER_ID) AS ID,USER_NAME AS FAMILY_NAME,'' AS GIVEN_NAME,EMAIL AS BUSINESS_EMAIL FROM SYS_USER U,SYS_USER_ROLE R WHERE U.USER_ID = R.USER_ID AND R.ROLE_ID = ?";
		List<User> users = commonQuery.findObjects(sql,new String[]{groupId},UserImpl.class);
		return users;
	}

	@Override
	public Group findGroupById(String groupId) {
		String sql = "SELECT TO_CHAR(ROLE_ID) AS ID,ROLE_CODE AS TYPE,ROLE_NAME AS NAME FROM SYS_ROLE WHERE ROLE_ID ='"+groupId+"'";
		List<GroupImpl> groups = this.getCommonQuery().findObjects(sql, GroupImpl.class);
		if(groups!=null && !groups.isEmpty()){
			return groups.get(0);
		}
		return null;
	}

	@Override
	public List<Group> findGroupsByUserAndGroupType(String userId,
			String groupType) {
		String sql = "SELECT TO_CHAR(ROLE_ID) AS ID,ROLE_CODE AS TYPE,ROLE_NAME AS NAME FROM SYS_ROLE WHERE ROLE_ID IN (SELECT ROLE_ID FROM SYS_USER_ROLE WHERE USER_ID = '"+userId+"') AND ROLE_CODE ='"+groupType+"'";
		return this.getCommonQuery().findObjects(sql, GroupImpl.class);
	}

	@Override
	public List<Group> findGroupsByUser(String userId) {
		String sql = "SELECT TO_CHAR(ROLE_ID) AS ID,ROLE_CODE AS TYPE,ROLE_NAME AS NAME FROM SYS_ROLE WHERE ROLE_ID IN (SELECT ROLE_ID FROM SYS_USER_ROLE WHERE USER_ID = '"+userId+"')";
		return this.getCommonQuery().findObjects(sql, GroupImpl.class);
	}

	@Override
	public void deleteGroup(String groupId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createMembership(String userId, String groupId, String role) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteMembership(String userId, String groupId, String role) {
		// TODO Auto-generated method stub

	}

	public ICommonQuery getCommonQuery() {
		return commonQuery;
	}

	public void setCommonQuery(ICommonQuery commonQuery) {
		this.commonQuery = commonQuery;
	}
	
	

}
