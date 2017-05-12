package com.qfw.common.bizservice.permission.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.bizservice.permission.IDeptBS;
import com.qfw.common.dao.ICommonQuery;
import com.qfw.common.dao.permission.IDeptDAO;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.permission.SysDept;
import com.qfw.common.model.permission.vo.SysDeptVO;
import com.qfw.common.util.CollectionUtil;
import com.qfw.common.util.StringUtils;

public class DeptBSImpl extends BaseServiceImpl implements IDeptBS {
	private IDeptDAO deptDAO;
	private Logger log = LogFactory.getInstance().getPlatformLogger();

	@Override
	public List<SysDept> getDeptChildren(Integer deptId) throws BizException{
		SysDept dept = new SysDept();
		dept.setDeptId(deptId);
		return deptDAO.findChildren(dept);
	}


	@Override
	public SysDept getSysDeptById(Integer deptId) throws BizException{
		return (SysDept) deptDAO.findById(SysDept.class, deptId);
	}

	@SuppressWarnings("unchecked")
	public TreeNode createAllDeptTree()throws BizException{
		String hql = "from SysDept where status = '1' order by levelNum,deptId,parentDeptId,orderNum ";
		List<SysDept> depts = deptDAO.findByHQL(hql);
		if(CollectionUtil.isNotEmpty(depts)){
			DefaultTreeNode root = new DefaultTreeNode("root", null);
			createDeptLevel1(root, depts);
			List<TreeNode> lev1Tree = root.getChildren();
			for (TreeNode treeNode : lev1Tree) {
				createDeptChildren(treeNode, depts);
			}
			return root;
		}
		return null;
	}
	public TreeNode findDeptTree(TreeNode root,List<SysDept> depts)throws BizException{
		TreeNode newRootTree = new DefaultTreeNode("root",null);
		if(CollectionUtil.isNotEmpty(depts)){
			if(root == null){
				root = createAllDeptTree();
			}
			for (SysDept sysDept : depts) {
				TreeNode node = findOneDeptTree(root, sysDept);
				if(node != null){
					node.setParent(newRootTree);
				}
			}
		}
		return newRootTree;
	}
	public TreeNode findOneDeptTree(TreeNode root,SysDept dept)throws BizException{
		TreeNode selectTreeNode = null;
		Object obj = root.getData();
		if(obj instanceof SysDept){
			SysDept data = (SysDept)obj;
			if(data != null){
				if(dept.getDeptId().equals(data.getDeptId())){
					return root;
				}
			}
		}
		
		List<TreeNode> children = root.getChildren();
		if(CollectionUtil.isNotEmpty(children)){
			for (TreeNode treeNode : children) {
				selectTreeNode = findOneDeptTree(treeNode, dept);
				if(selectTreeNode != null){
					return selectTreeNode;
				}
			}
		}
		return selectTreeNode;
	}
	/**
	 * 对有按level_num排序的数据查询第一级节点
	 * @param deptRoot
	 * @param depts
	 * @return
	 */
	private void createDeptLevel1(DefaultTreeNode deptRoot,List<SysDept> depts)throws BizException{
		List<SysDept> sysDeptLev1List = new ArrayList<SysDept>();
		for (SysDept sysDept : depts) {
			if(1 == sysDept.getLevelNum()){
				sysDeptLev1List.add(sysDept);
				new DefaultTreeNode(sysDept, deptRoot);
			}else{
				break;
			}
		}
		depts.removeAll(sysDeptLev1List);
	}
	/**
	 * 创建非一级部门
	 */
	private void createDeptChildren(TreeNode deptParentNode,List<SysDept> depts)throws BizException{
		if(CollectionUtil.isNotEmpty(depts)){
			List<SysDept> deptChildren = new ArrayList<SysDept>();
			SysDept parentDept = (SysDept) deptParentNode.getData();
			for (SysDept sysDept : depts) {
				if(parentDept.getDeptId().equals(sysDept.getParentDeptId())){
					deptChildren.add(sysDept);
					new DefaultTreeNode(sysDept,deptParentNode);
				}
			}
			depts.removeAll(deptChildren);
			List<TreeNode> children = deptParentNode.getChildren();
			if(CollectionUtil.isNotEmpty(children)){
				for (TreeNode treeNode : children) {
					createDeptChildren(treeNode, depts);
				}
			}
		}
		
	}
	
	public List<SysDept> findDeptsByVO(SysDeptVO deptVO,TreeNode deptTree)throws BizException{
		StringBuilder sb = new StringBuilder("select * from sys_dept");
		sb.append(VO2Condition(deptVO));
		sb.append(VO2Condition2(deptTree));
		return deptDAO.findBySQL(sb.toString());
	}
	
	public int findDeptsCountByVO(SysDeptVO deptVO)throws BizException{
		StringBuilder sb = new StringBuilder("select count(dept_id) from sys_dept");
		sb.append(VO2Condition(deptVO));
		return commonQuery.findCountBySQL(sb, null);
	}
	
	/**
	 * 通过部门id查找下属部门个数
	 * @param detpId
	 * @return
	 */
	public int findChildrenCount(Integer detpId){
		StringBuilder sb = new StringBuilder("select count(dept_id) from sys_dept where parent_dept_id = ?");
		return commonQuery.findCountBySQL(sb, new Integer[]{detpId});
	}
	
	public String VO2Condition(SysDeptVO deptVO)throws BizException{
		StringBuilder sb = new StringBuilder(" where 1=1");
		if(deptVO != null){
			if(StringUtils.isNotEmpty(deptVO.getDeptCode())){
				sb.append(" and dept_code like '%").append(deptVO.getDeptCode()).append("%'");
			}
			if(StringUtils.isNotEmpty(deptVO.getDeptName())){
				sb.append(" and dept_name like '%").append(deptVO.getDeptName()).append("%'");
			}
			if(deptVO.getParentDeptId() != null && deptVO.getParentDeptId() > 0){
				sb.append(wrapParentCondition(deptVO.getParentDeptId()));
			}
		}
		return sb.toString();
	}
	
	public String VO2Condition2(TreeNode deptTree)throws BizException{
		StringBuilder sb = new StringBuilder("");
		if(deptTree != null){
			SysDept sysDept = (SysDept) deptTree.getData();
			if(sysDept != null &&sysDept.getDeptId() != null){
				Integer deptId = sysDept.getDeptId();
				sb.append(" and dept_id in (select dept_id from sys_dept CONNECT BY PRIOR ");
				sb.append(" dept_id = parent_dept_id start with dept_id = ").append(deptId).append(")");
			}
		}
		return sb.toString();
	}
	private String wrapParentCondition(Integer deptParId){
		StringBuilder sb = new StringBuilder();
		//FIND_IN_SET(dept_id,getChildDeptIdList(1))
		sb.append(" and FIND_IN_SET(dept_id,getChildDeptIdList(").append(deptParId).append("))");
		/*sb.append(" and dept_id in (select dept_id from sys_dept CONNECT BY PRIOR ");
		sb.append(" dept_id = parent_dept_id start with dept_id = ").append(deptParId).append(")");*/
		return sb.toString();
	}
	@Override
	public List<SysDept> findDeptsPagesByVO(SysDeptVO deptVO,
			int first,int pageSize) throws BizException{
		StringBuilder sb = new StringBuilder("select * from sys_dept ");
		sb.append(VO2Condition(deptVO));
		return commonQuery.findBySQLByPages(sb.toString(), first, pageSize,SysDept.class);
	}
	
	public List<SysDept> checkDeptName(String deptName,Integer deptId)throws BizException{
		if(deptName != null && !"".equals(deptName)){
			StringBuilder sb = new StringBuilder("from SysDept where ");
			sb.append(" deptName = '").append(deptName).append("'");
			if(deptId!=null && !deptId.equals(Integer.valueOf(0))){
				sb.append(" and deptId != '").append(deptId).append("'");
			}
			List<SysDept> list = baseDAO.findByHQL(sb.toString());
			return list;
		}else{
			return null;
		}
	}
	
	public List<SysDept> checkDeptCode(String deptCode,Integer deptId)throws BizException{
		if(deptCode != null && !"".equals(deptCode)){
			StringBuilder sb = new StringBuilder("from SysDept where");
			sb.append(" deptCode = '").append(deptCode).append("'");
			if(deptId!=null && !deptId.equals(Integer.valueOf(0))){
				sb.append(" and deptId != '").append(deptId).append("'");
			}
			List<SysDept> list = baseDAO.findByHQL(sb.toString());
			return list;
		}else{
			return null;
		}	
	}
	
	public List<SysDept> findUserDepts(Integer userId){
		String deptSql = "SELECT * FROM SYS_DEPT T WHERE EXISTS (SELECT 1 FROM SYS_USER_DEPT UD WHERE T.DEPT_ID = UD.DEPT_ID AND UD.USER_ID = '"+userId+"')";
		List<SysDept> depts = commonQuery.findObjects(deptSql, SysDept.class);
		return depts;
	}
	
	public SysDept findDeptById(Integer id)throws BizException{
		return (SysDept)deptDAO.findById(SysDept.class, id);
	}
	
	public boolean isExistsUserByDeptId(Integer deptId){
		StringBuffer sb = new StringBuffer();
		sb.append("select COUNT(1) from SYS_USER U,SYS_USER_DEPT UD");
		sb.append(" where U.USER_ID = UD.USER_ID AND UD.DEPT_ID IN");
		sb.append(" (SELECT D.DEPT_ID FROM SYS_DEPT D WHERE D.DEPT_ID = UD.DEPT_ID");
		sb.append(" AND D.DEPT_ID = '"+deptId+"')");
		int tmp = (Integer)getJdbcTemplate().queryForInt(sb.toString());
		if (tmp > 0){
			return true;
		} else {
			return false;
		}
	}
	
	public IDeptDAO getDeptDAO() {
		return deptDAO;
	}

	public void setDeptDAO(IDeptDAO deptDAO) {
		this.deptDAO = deptDAO;
	}

	public ICommonQuery getCommonQuery() {
		return commonQuery;
	}
	
	public void setCommonQuery(ICommonQuery commonQuery) {
		this.commonQuery = commonQuery;
	}

	public Logger getLog() {
		return log;
	}
	
	public void setLog(Logger log) {
		this.log = log;
	}

}
