package com.qfw.common.bizservice.permission;

import java.util.List;

import org.primefaces.model.TreeNode;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.permission.SysDept;
import com.qfw.common.model.permission.SysFunction;
import com.qfw.common.model.permission.SysRole;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.model.permission.vo.SysRoleVO;

public interface IRoleBS extends IBaseService {
	/**
	 * 获取角色下面的功能点
	 * @param roleID
	 * @return
	 */
	public List<SysFunction> getFuns(String roleID);
	/**
	 * 获取所有的功能菜单
	 * @return
	 */
	public List<SysFunction> getAllFuns();
	/**
	 * 获取角色下面的所有用户
	 * @param roleID
	 * @return
	 */
	public List<SysUser> getUsers(String roleID);
	/**
	 * 通过VO计算角色数量
	 * @param sysRoleVO
	 * @return
	 * @throws BizException
	 */
	public int findRolesCountByVO(SysRoleVO sysRoleVO) throws BizException;
	/**
	 * 组装VO条件语句
	 * @param sysRoleVO
	 * @return
	 * @throws BizException
	 */
	public String VO2Condition(SysRoleVO sysRoleVO) throws BizException;
	/**
	 * 通过VO查找角色列表
	 * @param sysRoleVO
	 * @return
	 * @throws BizException
	 */
	public List<SysRole> findRolesByVO(SysRoleVO sysRoleVO) throws BizException;
	/**
	 * 角色翻页查询
	 * @param sysRoleVO
	 * @return
	 * @throws BizException
	 */
	public List<SysRole> findRolesPagesByVO(SysRoleVO sysRoleVO,int first,int pageSize)throws BizException;
	/**
	 * 通过ID查找角色
	 * @param roleId
	 * @return
	 * @throws BizException
	 */
	public SysRole findRoleById(Integer roleId)throws BizException;
	/**
	 * 组装HQL的VO条件语句
	 * @param sysRoleVO
	 * @return
	 * @throws BizException
	 */
	public String VO2ConditionHQL(SysRoleVO sysRoleVO)throws BizException;
	/**
	 * 根据角色代号查找角色列表
	 * @return
	 * @throws BizException
	 */
	public List<SysRole> findRolesByCode(String roleCode,Integer roleId,Integer deptId)throws BizException;
	/**
	 * 根据角色代号查找角色列表
	 * @param roleName
	 * @return
	 * @throws BizException
	 */
	public List<SysRole> findRolesByName(String roleName,Integer roleId,Integer deptId)throws BizException;
	/**
	 * 查找一个机构的树节点
	 * @param root
	 * @param sysDept
	 * @return
	 * @throws BizException
	 */
	public TreeNode findDeptTreeNode(TreeNode root,SysDept sysDept)throws BizException;
	
	/**
	 * 查找机构下的所有角色
	 * @param sysDepts
	 * @return
	 */
	public List<SysRole> findSysRolesByDepts(List<SysDept> sysDepts)throws BizException;
	
	/**
	 * 查找公共机构角色，无隶属机构
	 * @param sysDepts
	 * @return
	 */
	public List<SysRole> findSysCommonRoles()throws BizException;
	
	/**
	 * 通过用户查找用户角色
	 * @param userId
	 * @return
	 * @throws BizException
	 */
	public List<SysRole> findSysRolesByUser(Integer userId)throws BizException;
	/**
	 * 通过部门Id及角色获取角色信息
	 * @param deptId
	 * @param roleCode
	 * @return
	 */
	public SysRole findSysRole(Integer deptId,String roleCode)throws BizException;
}
