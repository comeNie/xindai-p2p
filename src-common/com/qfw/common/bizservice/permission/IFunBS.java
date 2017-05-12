package com.qfw.common.bizservice.permission;

import java.util.List;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.permission.SysDept;
import com.qfw.common.model.permission.SysFunction;
import com.qfw.common.model.permission.SysRole;

public interface IFunBS extends IBaseService{

	/**
	 * 创建功能树
	 * @return
	 * @throws BizException
	 */
	public TreeNode createAllFunTree()throws BizException;
	public List<SysFunction> checkFunName(String funName,Integer funId)throws BizException;
	/**
	 * 创建非一级功能
	 * @param funParentNode
	 * @param funs
	 * @throws BizException
	 */
	public void createFunChildren(TreeNode funParentNode,List<SysFunction> funs)throws BizException;
	/**
	 * 对有按fun_level排序的数据查询第一级节点
	 * @param funRoot
	 * @param funs
	 * @throws BizException
	 */
	public void createFunLevel1(DefaultTreeNode funRoot,List<SysFunction> funs)throws BizException;
	/**
	 * 通过角色创建功能树
	 * @throws BizException
	 */
	public TreeNode createFunTreeByRole(Integer roleId)throws BizException;
	/**
	 * 通过机构ID查找角色信息
	 * @param deptId
	 * @return
	 * @throws BizException
	 */
	public List findRolesByDeptId(Integer deptId)throws BizException;
	/**
	 * 查找某机构下的机构树
	 * @param root
	 * @param dept
	 * @return
	 */
	public TreeNode findOneFunTree(TreeNode root,SysFunction fun)throws BizException;
	/**
	 * 查找角色已经授权的功能
	 * @param roleId
	 * @return
	 * @throws BizException
	 */
	public List findHavedFunsByRoleId(Integer roleId)throws BizException;
	/**
	 * 通过功能ID查找其子功能
	 * @param funId
	 * @return
	 * @throws BizException
	 */
	public List findChildrenByFunId(Integer funId)throws BizException;
	/**
	 * 新增角色功能
	 * @param roleId
	 * @param funId
	 * @throws BizException
	 */
	public void saveRoleFunction(Integer roleId,Integer funId)throws BizException;
	/**
	 * 删除角色功能
	 * @param roleId
	 * @param funId
	 * @throws BizException
	 */
	public void deleteRoleFunction(Integer roleId,Integer funId)throws BizException;
	/**
	 * 遍历功能树
	 * @param root
	 * @param funIdList
	 * @throws BizException
	 */
	public void searchFunTree(TreeNode root,List funIdList)throws BizException;
	/**
	 * 校验角色功能是否存在
	 * @param roleId
	 * @param funId
	 * @return
	 * @throws BizException
	 */
	public Boolean findRoleFunction(Integer roleId,Integer funId)throws BizException;
	/**
	 * 保存树的父节点
	 * @param treeNode
	 * @param roleId
	 * @throws BizException
	 */
	public void saveTreeParents(TreeNode treeNode,Integer roleId)throws BizException;
	/**
	 * 获取所有功能的ID
	 * @return
	 * @throws BizException
	 */
	public List findAllFun()throws BizException;
	
	/**
	 * 修改功能选择
	 * @param node
	 * @param isSelect
	 */
	public void changeFunSelect(TreeNode node,boolean isSelect);
	/**
	 * 更新角色功能信息
	 * @param roleId
	 * @param selectFunction
	 * @throws BizException
	 */
	public void updateRoleFunction(Integer roleId,TreeNode[] selectedFuns) throws BizException;
	public List<SysFunction> checkFunCode(String funCode, Integer funId) throws BizException;
}
