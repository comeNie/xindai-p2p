package com.qfw.common.bizservice.permission.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedProperty;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.bizservice.permission.IFunBS;
import com.qfw.common.dao.ICommonQuery;
import com.qfw.common.dao.permission.IFunDAO;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.permission.SysDept;
import com.qfw.common.model.permission.SysFunction;
import com.qfw.common.model.permission.SysRole;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.CollectionUtil;
import com.qfw.common.util.web.ViewOper;

public class FunBSImpl extends BaseServiceImpl implements IFunBS{

	private Logger log = LogFactory.getInstance().getPlatformLogger();

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	@Override
	@SuppressWarnings("unchecked")
	public TreeNode createAllFunTree() throws BizException {
		String hql = "from SysFunction where funStatus = '1' order by funLevel,funId,parentFunId,sort ";
		List<SysFunction> funs = baseDAO.findByHQL(hql);
		if(CollectionUtil.isNotEmpty(funs)){
			DefaultTreeNode root = new DefaultTreeNode("root", null);
			createFunLevel1(root, funs);
			List<TreeNode> lev1Tree = root.getChildren();
			for (TreeNode treeNode : lev1Tree) {
				createFunChildren(treeNode, funs);
			}
			return root;
		}
		return null;
	}
	public List<SysFunction> checkFunName(String funName,Integer funId)throws BizException{
		if(funName != null && !"".equals(funName)){
			StringBuilder sb = new StringBuilder("SELECT * FROM sys_function SYS where ");
			sb.append(" FUN_NAME = '").append(funName).append("'");
			List<SysFunction> funs = commonQuery.findObjects(sb.toString(), SysFunction.class);
			return funs;
		}else{
			return null;
		}
	}
	public List<SysFunction> checkFunCode(String funCode,Integer funId)throws BizException{
		if(funCode != null && !"".equals(funCode)){
			StringBuilder sb = new StringBuilder("SELECT * FROM sys_function SYS where ");
			sb.append(" FUN_CODE = '").append(funCode).append("'");
			List<SysFunction> funs = commonQuery.findObjects(sb.toString(), SysFunction.class);
			return funs;
		}else{
			return null;
		}
	}
	/**
	 * 修改功能选择
	 * @param node
	 * @param isSelect
	 */
	public void changeFunSelect(TreeNode node,boolean isSelect){
		if(node != null){
			node.setSelected(isSelect);
			if(isSelect){
				node.setExpanded(isSelect);
			}
		}
		List<TreeNode> children = node.getChildren();
		for (TreeNode treeNode : children) {
			changeFunSelect(treeNode, isSelect);
		}
	}
	@Override
	public void createFunChildren(TreeNode funParentNode, List<SysFunction> funs)
			throws BizException {
		if(CollectionUtil.isNotEmpty(funs)){
			List<SysFunction> funChildren = new ArrayList<SysFunction>();
			SysFunction parentFun = (SysFunction) funParentNode.getData();
			for (SysFunction sysFunction : funs) {
				if(parentFun.getFunId().equals(sysFunction.getParentFunId())){
					funChildren.add(sysFunction);
					new DefaultTreeNode(sysFunction,funParentNode);
				}
			}
			funs.removeAll(funChildren);
			List<TreeNode> children = funParentNode.getChildren();
			if(CollectionUtil.isNotEmpty(children)){
				for (TreeNode treeNode : children) {
					createFunChildren(treeNode, funs);
				}
			}
		}
	}

	@Override
	public void createFunLevel1(DefaultTreeNode funRoot, List<SysFunction> funs)
			throws BizException {
		List<SysFunction> sysFunctionLev1List = new ArrayList<SysFunction>();
		for (SysFunction sysFunction : funs) {
			if("1".equals(sysFunction.getFunLevel())){
				sysFunctionLev1List.add(sysFunction);
				new DefaultTreeNode(sysFunction, funRoot);
			}else{
				break;
			}
		}
		funs.removeAll(sysFunctionLev1List);
	}

	@Override
	public TreeNode createFunTreeByRole(Integer roleId) throws BizException {
		StringBuilder sb = new StringBuilder("SELECT * FROM sys_function SYS WHERE SYS.FUN_STATUS = '1' ");
		sb.append(" ORDER BY sys.Fun_Level,sys.Fun_Id,sys.Parent_Fun_Id,sys.Sort ");
		List<SysFunction> funs = commonQuery.findObjects(sb.toString(), SysFunction.class);
		if(CollectionUtil.isNotEmpty(funs)){
			DefaultTreeNode root = new DefaultTreeNode("root", null);
			createFunLevel1(root, funs);
			List<TreeNode> lev1Tree = root.getChildren();
			for (TreeNode treeNode : lev1Tree) {
				createFunChildren(treeNode, funs);
			}
			return root;
		}
		return null;
	}

	@Override
	public List findRolesByDeptId(Integer deptId) throws BizException {
		StringBuilder sb = new StringBuilder("SELECT ROLE_ID,ROLE_NAME FROM SYS_ROLE WHERE DEPT_ID = ");
		sb.append(deptId);
		List roles = commonQuery.findBySQL(sb.toString());
		return roles;
	}

	@Override
	public List findHavedFunsByRoleId(Integer roleId)
			throws BizException {
		StringBuilder sql = new StringBuilder("SELECT SYS.FUN_ID,SYS.FUN_NAME FROM SYS_FUNCTION SYS WHERE SYS.FUN_ID IN ");
		sql.append("(SELECT FUN.FUN_ID FROM SYS_ROLE_FUNCTION FUN WHERE FUN.ROLE_ID = ?)");
		List funs = commonQuery.findBySQL(sql.toString(), new Integer[]{roleId});
		return funs;
	}

	@Override
	public List findChildrenByFunId(Integer funId) throws BizException {
		StringBuilder sb = new StringBuilder("SELECT FUN_ID FROM SYS_FUNCTION");
		sb.append(" start with FUN_ID=? connect by prior FUN_ID=PARENT_FUN_ID");
		List funIdList = commonQuery.findBySQL(sb.toString(), new Integer[]{funId});
		return funIdList;
	}

	@Override
	public void saveRoleFunction(Integer roleId, Integer funId)
			throws BizException {
		SysUser sysUser = (SysUser)ViewOper.getSession().getAttribute("user");
		StringBuilder sb = new StringBuilder("INSERT INTO SYS_ROLE_FUNCTION(ROLE_ID, FUN_ID, SYS_CREATE_TIME, SYS_UPDATE_TIME, SYS_UPDATE_USER)");
		sb.append(" VALUES(").append(roleId).append(",");
		sb.append(funId).append(",");
		sb.append("sysdate(),sysdate(),'");
		sb.append(sysUser.getUserCode()).append("')");
		this.getJdbcTemplate().execute(sb.toString());
	}
	public TreeNode findOneFunTree(TreeNode root,SysFunction fun)throws BizException{
		TreeNode selectTreeNode = null;
		Object obj = root.getData();
		if(obj instanceof SysFunction){
			SysFunction data = (SysFunction)obj;
			if(data != null){
				if(fun.getFunId().equals(data.getFunId())){
					return root;
				}
			}
		}
		
		List<TreeNode> children = root.getChildren();
		if(CollectionUtil.isNotEmpty(children)){
			for (TreeNode treeNode : children) {
				selectTreeNode = findOneFunTree(treeNode, fun);
				if(selectTreeNode != null){
					return selectTreeNode;
				}
			}
		}
		return selectTreeNode;
	}
	@Override
	public void deleteRoleFunction(Integer roleId, Integer funId)
			throws BizException {
		StringBuilder sb = new StringBuilder("DELETE FROM SYS_ROLE_FUNCTION ");
		sb.append(" WHERE ROLE_ID = ").append(roleId);
		sb.append(" AND FUN_ID = ").append(funId);
		this.getJdbcTemplate().execute(sb.toString());
	}
	
	@Transactional(rollbackFor = BizException.class,propagation = Propagation.REQUIRED)
	public void updateRoleFunction(Integer roleId,TreeNode[] selectedFuns) throws BizException{
		//1、删除已授权的功能
		StringBuilder sb = new StringBuilder("DELETE FROM SYS_ROLE_FUNCTION ");
		sb.append(" WHERE ROLE_ID = ").append(roleId);
		this.getJdbcTemplate().execute(sb.toString());
		//2、保存新增功能
		for (TreeNode treeNode : selectedFuns) {
			////System.out.println("父节点"+treeNode.getParent().isSelected());
			saveParentFun(treeNode, roleId);
			SysFunction sysFunction = (SysFunction) treeNode.getData(); 
			saveRoleFunction(roleId, sysFunction.getFunId());
		}
	}

	private void saveParentFun(TreeNode treeNode,Integer roleId) throws BizException{
		TreeNode parentNode = treeNode.getParent();
		if(parentNode != null && !parentNode.isSelected()){
			if(parentNode.getData() instanceof SysFunction){
				SysFunction sysFunction = (SysFunction) parentNode.getData(); 
				boolean b = findRoleFunction(roleId, sysFunction.getFunId());//
				if(!b){
					saveRoleFunction(roleId, sysFunction.getFunId());//角色权限不存在
					saveParentFun(parentNode.getParent(), roleId);
				}
			}
		}
	}
	@Override
	public void searchFunTree(TreeNode root,List funIdList) throws BizException {
		if(root.getParent() != null){
			SysFunction sysFunction = (SysFunction) root.getData();
			for (int i = 0; i < funIdList.size(); i++) {
				Integer funId = Integer.valueOf(((Map)funIdList.get(i)).get("FUN_ID").toString());
				if(funId.equals(sysFunction.getFunId())){
					root.setSelected(true);
					root.setExpanded(true);
					funIdList.remove(i);
					break;
				}
			}
		}
		List<TreeNode> children = root.getChildren();
		if(CollectionUtil.isNotEmpty(children)){
			for(TreeNode treeNode : children){
				searchFunTree(treeNode, funIdList);
			}
		}
	}

	@Override
	public Boolean findRoleFunction(Integer roleId, Integer funId)
			throws BizException {
		StringBuilder sb = new StringBuilder("SELECT * FROM SYS_ROLE_FUNCTION ");
		sb.append(" WHERE ROLE_ID = ").append(roleId);
		sb.append(" AND FUN_ID = ").append(funId);
		List roleFunList = commonQuery.findBySQL(sb.toString());
		if(roleFunList != null && roleFunList.size() > 0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void saveTreeParents(TreeNode treeNode,Integer roleId) throws BizException {
		while (treeNode.getParent() != null
				&& !"root".equals(treeNode.getParent().getData().toString())) {
			TreeNode parentTree = treeNode.getParent();
			SysFunction sysFunction = (SysFunction) parentTree.getData();
			if (!this.findRoleFunction(roleId, sysFunction.getFunId())) {
				this.saveRoleFunction(roleId, sysFunction.getFunId());
			}
			treeNode = treeNode.getParent();
		}
	}

	@Override
	public List findAllFun() throws BizException {
		String sql = "SELECT FUN_ID FROM SYS_FUNCTION WHERE FUN_STATUS = '1'";
		List funs = commonQuery.findBySQL(sql);
		return funs;
	}

}
