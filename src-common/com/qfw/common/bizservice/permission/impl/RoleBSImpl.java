package com.qfw.common.bizservice.permission.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.TreeNode;

import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.bizservice.permission.IRoleBS;
import com.qfw.common.dao.permission.IRoleDAO;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.permission.SysDept;
import com.qfw.common.model.permission.SysFunction;
import com.qfw.common.model.permission.SysRole;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.model.permission.vo.SysRoleVO;
import com.qfw.common.util.CollectionUtil;
import com.qfw.common.util.NumberUtils;
import com.qfw.common.util.StringUtils;

public class RoleBSImpl extends BaseServiceImpl implements IRoleBS {

	private IRoleDAO roleDAO;
	private Logger log = LogFactory.getInstance().getPlatformLogger();
	
	public IRoleDAO getRoleDAO() {
		return roleDAO;
	}
	public void setRoleDAO(IRoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}
	
	public Logger getLog() {
		return log;
	}
	public void setLog(Logger log) {
		this.log = log;
	}
	
	@Override
	public List<SysRole> findObjectsByPages(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters,LazyDataModel lazyDataMode) {
		StringBuilder hqlString = new StringBuilder("from SysRole role where 1=1 ");
		if(filters!=null && !filters.isEmpty()){
			Set<String> keys = filters.keySet();
			for(String key : keys){
				hqlString.append(" and ").append(key).append(" like '%").append(filters.get(key)).append("%'");
			}
		}
		int count = this.count("select count(role) "+hqlString.toString());
		if(StringUtils.isNotEmpty(sortField)){
			if (!SortOrder.UNSORTED.equals(sortOrder)) {
				hqlString.append(" order by ").append(sortField);
				if (SortOrder.DESCENDING.equals(sortOrder)) {
					hqlString.append(" desc");
				}
			}
		}
		lazyDataMode.setRowCount(count);
		
		return roleDAO.findByHQLByPages(hqlString.toString(), first, pageSize);
	}
	
	@Override
	public List<SysFunction> getFuns(String roleID) {
		String sql = "SELECT FUN_ID,FUN_NAME FROM SYS_FUNCTION WHERE FUN_ID IN (SELECT FUN_ID FROM SYS_ROLE_FUNCTION WHERE ROLE_ID = ?)";
		return this.getCommonQuery().findObjects(sql, new String[]{roleID}, SysFunction.class);
		//this.getCommonQuery().findObjects("select fun_id,fun_name from sys_function f where fun_id in (select fun_id from sys_role_function where role_id = '1')", SysFunction.class);
		//return null;
	}
	@Override
	public List<SysFunction> getAllFuns() {
		String sql = "SELECT FUN_ID,FUN_NAME FROM SYS_FUNCTION";
		return this.getCommonQuery().findObjects(sql, SysFunction.class);		
	}
	@Override
	public List<SysUser> getUsers(String roleID) {
		String sql = "SELECT USER_ID,USER_NAME,USER_CODE FROM SYS_USER WHERE USER_ID IN (SELECT USER_ID FROM SYS_USER_ROLE WHERE ROLE_ID = ?)";
		return this.getCommonQuery().findObjects(sql, new String[]{roleID}, SysUser.class);
	}	
	@Override
	public int findRolesCountByVO(SysRoleVO sysRoleVO) throws BizException {
		StringBuilder sb = new StringBuilder(" SELECT COUNT(ROLE_ID) FROM SYS_ROLE ");
		sb.append(VO2Condition(sysRoleVO));
		return commonQuery.findCountBySQL(sb, null);
	}
	@Override
	public String VO2Condition(SysRoleVO sysRoleVO) throws BizException {
		StringBuilder sb = new StringBuilder(" WHERE 1=1 ");
		if(sysRoleVO != null){
			if(StringUtils.isNotEmpty(sysRoleVO.getRoleCode())){
				sb.append(" AND ROLE_CODE LIKE '%").append(sysRoleVO.getRoleCode()).append("%'");
			}
			if(StringUtils.isNotEmpty(sysRoleVO.getRoleName())){
				sb.append(" AND ROLE_NAME LIKE '%").append(sysRoleVO.getRoleName()).append("%'");
			}
		}
		return sb.toString();
	}
	@Override
	public List<SysRole> findRolesByVO(SysRoleVO sysRoleVO) throws BizException {
		StringBuilder sb = new StringBuilder(" SELECT * FROM SYS_ROLE ");
		sb.append(VO2Condition(sysRoleVO));
		return commonQuery.findBySQL(sb.toString());
	}
	@Override
	public List<SysRole> findRolesPagesByVO(SysRoleVO sysRoleVO,int first,int pageSize)
			throws BizException {
		StringBuilder sb = new StringBuilder("SELECT R.*,D.DEPT_NAME FROM SYS_ROLE R LEFT JOIN SYS_DEPT D ON R.DEPT_ID = D.DEPT_ID");
		sb.append(VO2ConditionSQL(sysRoleVO));
		return commonQuery.findBySQLByPages(sb.toString(), first, pageSize, SysRoleVO.class);
		//return baseDAO.findByHQLByPages(sb.toString(), first, pageSize);
	}
	@Override
	public SysRole findRoleById(Integer roleId) throws BizException {
		return (SysRole) roleDAO.findById(SysRole.class, roleId);
	}
	@Override
	public String VO2ConditionHQL(SysRoleVO sysRoleVO) throws BizException {
		StringBuilder sb = new StringBuilder(" WHERE 1=1 ");
		if(sysRoleVO != null){
			if(StringUtils.isNotEmpty(sysRoleVO.getRoleCode())){
				sb.append(" AND roleCode LIKE '%").append(sysRoleVO.getRoleCode()).append("%'");
			}
			if(StringUtils.isNotEmpty(sysRoleVO.getRoleName())){
				sb.append(" AND roleName LIKE '%").append(sysRoleVO.getRoleName()).append("%'");
			}
		}
		return sb.toString();
	}
	
	public String VO2ConditionSQL(SysRoleVO sysRoleVO) throws BizException {
		StringBuilder sb = new StringBuilder(" WHERE 1=1 ");
		if(sysRoleVO != null){
			if(StringUtils.isNotEmpty(sysRoleVO.getRoleCode())){
				sb.append(" AND R.ROLE_CODE LIKE '%").append(sysRoleVO.getRoleCode()).append("%'");
			}
			if(StringUtils.isNotEmpty(sysRoleVO.getRoleName())){
				sb.append(" AND R.ROLE_NAME LIKE '%").append(sysRoleVO.getRoleName()).append("%'");
			}
			if(!NumberUtils.isBlank(sysRoleVO.getDeptId())){
				sb.append(" AND R.DEPT_ID = ").append(sysRoleVO.getDeptId());
			}
		}
		return sb.toString();
	}
	
	@Override
	public List<SysRole> findRolesByCode(String roleCode,Integer roleId,Integer deptId) throws BizException {
		if(roleCode != null && !"".equals(roleCode)){
			StringBuilder sb = new StringBuilder(" FROM SysRole WHERE");
			sb.append(" roleCode = '").append(roleCode).append("'");
			if(roleId!=null && !roleId.equals(Integer.valueOf(0))){
				sb.append(" and roleId != '").append(roleId).append("'");
			}
			if(deptId!=null && !deptId.equals(Integer.valueOf(0))){
				sb.append(" and deptId = '").append(deptId).append("'");
			}else{
				sb.append(" and deptId is null");
			}
			List<SysRole> list = baseDAO.findByHQL(sb.toString());
			return list;
		}else{
			return null;
		}
	}
	@Override
	public List<SysRole> findRolesByName(String roleName,Integer roleId,Integer deptId) throws BizException {
		if(roleName != null && !"".equals(roleName)){
			StringBuilder sb = new StringBuilder(" FROM SysRole WHERE");
			sb.append(" roleName = '").append(roleName).append("'");
			if(roleId!=null && !roleId.equals(Integer.valueOf(0))){
				sb.append(" and roleId != '").append(roleId).append("'");
			}
			if(roleId!=null && !roleId.equals(Integer.valueOf(0))){
				sb.append(" and deptId = '").append(deptId).append("'");
			}else{
				sb.append(" and deptId is null");
			}
			List<SysRole> list = baseDAO.findByHQL(sb.toString());
			return list;
		}else{
			return null;
		}
	}
	@Override
	public TreeNode findDeptTreeNode(TreeNode root,SysDept sysDept) throws BizException {
		TreeNode selectTreeNode = null;
		Object obj = root.getData();
		if(obj instanceof SysDept){
			SysDept data = (SysDept)obj;
			if(data != null){
				if(sysDept.getDeptId().equals(data.getDeptId())){
					return root;
				}
			}
		}
		List<TreeNode> children = root.getChildren();
		if(CollectionUtil.isNotEmpty(children)){
			for (TreeNode treeNode : children) {
				selectTreeNode = findDeptTreeNode(treeNode, sysDept);
				if(selectTreeNode != null){
					return selectTreeNode;
				}
			}
		}
		return selectTreeNode;
	}
	/**
	 * 查找部门下的所有角色
	 * @param sysDepts
	 * @return
	 */
	public List<SysRole> findSysRolesByDepts(List<SysDept> sysDepts)throws BizException{
		try{
			if(CollectionUtil.isNotEmpty(sysDepts)){
				List<SysRole> roles = new ArrayList<SysRole>();
				String hql = "from SysRole r where r.deptId = ?";
				List<SysRole> tmep = null;
				for (SysDept sysDept : sysDepts) {
					tmep = getHibernateTemplate().find(hql, sysDept.getDeptId());
					if(CollectionUtil.isNotEmpty(tmep)){
						roles.addAll(tmep);
					}
				}
				if(CollectionUtil.isNotEmpty(roles)){
					return roles;
				}
			}
			return null;
		}catch(Exception e){
			log.error("部门查询角色失败", e);
			throw new BizException("部门查询角色失败");
		}
		
	}
	
	/**
	 * 查找公共部门角色，无隶属部门
	 * @param sysDepts
	 * @return
	 */
	public List<SysRole> findSysCommonRoles()throws BizException{
		try{
			String hql = "from SysRole r where r.deptId is null or r.deptId = 0 ";
			List<SysRole> roles = getHibernateTemplate().find(hql);
			return roles;
		}catch(Exception e){
			log.error("部门查询角色失败", e);
			throw new BizException("部门查询角色失败");
		}
		
	}
	/**
	 * 通过用户查找用户角色
	 * @param userId
	 * @return
	 * @throws BizException
	 */
	@SuppressWarnings("unchecked")
	public List<SysRole> findSysRolesByUser(Integer userId)throws BizException{
		try{
			String sqlString = "SELECT R.* FROM SYS_ROLE R,SYS_USER_ROLE UR WHERE R.ROLE_ID = UR.ROLE_ID AND UR.USER_ID = ?";
			return getCommonQuery().findObjects(sqlString, new Integer[]{userId}, SysRole.class);
		}catch(Exception e){
			log.error("用户查询角色失败",e);
			throw new BizException("用户查询角色失败");
		}
		
	}
	/**
	 * 通过部门Id及角色获取角色信息
	 * @param depId
	 * @param roleCode
	 * @return
	 */
	public SysRole findSysRole(Integer deptId,String roleCode)throws BizException{
		try{
			if(StringUtils.isEmpty(roleCode)){
				return null;
			}
			List<SysRole> roles = null;
			if(NumberUtils.isBlank(deptId)){
//				roles = getHibernateTemplate().find("from SysRole where (deptId is null or deptId = 0) and roleCode = ?", roleCode);
				roles = getHibernateTemplate().find("from SysRole where roleCode = ?", roleCode);
			}else{
				roles = getHibernateTemplate().find("from SysRole where deptId = ? and roleCode = ?", deptId,roleCode);
			}
			if(roles == null || roles.isEmpty()){
				return null;
			}
			return roles.get(0);
		}catch(Exception e){
			throw new BizException(e.getMessage());
		}
		
	}

}
