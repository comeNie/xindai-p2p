package com.qfw.common.bizservice.permission;

import java.util.List;

import org.primefaces.model.TreeNode;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.permission.SysDept;
import com.qfw.common.model.permission.vo.SysDeptVO;


public interface IDeptBS extends IBaseService{
	/**
	 * 通过机构id获取子机构
	 * @param deptId
	 * @return
	 */
	public List<SysDept> getDeptChildren(Integer deptId)throws BizException;
	/**
	 * 通过id获取机构id
	 * @param deptId
	 * @return
	 */
	public SysDept getSysDeptById(Integer deptId)throws BizException;
	/**
	 * 创建所有机构的树
	 * @return
	 */
	public TreeNode createAllDeptTree()throws BizException;
	/**
	 * 查找某机构下的机构树
	 * @param root
	 * @param dept
	 * @return
	 */
	public TreeNode findOneDeptTree(TreeNode root,SysDept dept)throws BizException;
	/**
	 * 查找几个机构下的机构树
	 * @param depts
	 * @return
	 */
	public TreeNode findDeptTree(TreeNode root,List<SysDept> depts)throws BizException;
	/**
	 * 通过VO和树节点查找机构列表
	 * @param deptVO
	 * @return
	 */
	public List<SysDept> findDeptsByVO(SysDeptVO deptVO,TreeNode deptTree)throws BizException;
	/**
	 * 组装VO条件语句
	 * @param deptVO
	 * @return
	 */
	public String VO2Condition(SysDeptVO deptVO)throws BizException;
	/**
	 * 计算机构数量
	 * @param deptVO
	 * @return
	 */
	public int findDeptsCountByVO(SysDeptVO deptVO)throws BizException;
	
	List<SysDept> findDeptsPagesByVO(SysDeptVO deptVO, int first, int pageSize)throws BizException;
	
	/**
	 * 通过机构id查找下属机构个数
	 * @param detpId
	 * @return
	 */
	public int findChildrenCount(Integer detpId);

	public SysDept findDeptById(Integer id)throws BizException;
	
	public String VO2Condition2(TreeNode deptTree)throws BizException;
	
	/**
	 * 验证机构名称是否存在
	 * @param deptName
	 * @return
	 */
	public List<SysDept> checkDeptName(String deptName,Integer deptId)throws BizException;
	
	/**
	 * 验证机构代号是否存在
	 * @param deptCode
	 * @return
	 */
	public List<SysDept> checkDeptCode(String deptCode,Integer deptId)throws BizException;
	/**
	 * 查询用户所在机构
	 * @param userId
	 * @return
	 * @throws BizException
	 */
	public List<SysDept> findUserDepts(Integer userId);
	
	public boolean isExistsUserByDeptId(Integer deptId);
}
