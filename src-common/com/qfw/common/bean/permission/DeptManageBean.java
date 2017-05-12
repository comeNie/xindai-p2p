package com.qfw.common.bean.permission;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.TreeNode;

import com.qfw.common.bizservice.permission.IDeptBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.permission.SysDept;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.model.permission.vo.LazyDeptDataModel;
import com.qfw.common.model.permission.vo.SysDeptVO;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;

@ViewScoped
@ManagedBean(name="deptManageBean")
public class DeptManageBean extends BaseBackingBean{
    //private TreeNodeImpl rootNode;
    
    @ManagedProperty(value = "#{deptBS}")
    private IDeptBS deptBS;
    
    private TreeNode allDept;
    
//    private TreeNode userDept;
    
    private SysDeptVO deptVO = new SysDeptVO();
    
    private List<SysDept> depts;
    
    private LazyDataModel<SysDept> lazyModel;
    
    private SysDept selectDept;
    
    private TreeNode selectedTree;
    
    private SysUser sysUser = new SysUser();
    
    private Logger log = LogFactory.getInstance().getPlatformLogger();
	
    @PostConstruct
    public void init(){
    	try {
    		initParentDeptVO();
    		allDept = deptBS.createAllDeptTree();
    		search();
		} catch (BizException e) {
			log.error("创建部门树异常：", e);
			alert("创建部门树异常："+e.getMessage());
		}
    }
    private void initParentDeptVO(){
    	if(selectedTree != null){
			SysDept selectDep = (SysDept) selectedTree.getData();
			deptVO.setParentDeptId(selectDep.getDeptId());
			deptVO.setParentDeptName(selectDep.getDeptName());
		}
    }
//    
//	public TreeNode getUserDept() {
//		if(userDept == null){
//			List<SysDept> depts = (List<SysDept>) ViewOper.getSession().getAttribute("depts");
//			try {
//				userDept = deptBS.findDeptTree(getAllDept(),depts);
//			} catch (Exception e) {
//				log.error("" , e);
//			}
//		}
//		return userDept;
//	}
	
    public void onDeptSelect(){
    	initParentDeptVO();
    }
	public void search(){
		super.search();
		if(deptVO != null){ 
			try {
				selectDept = new SysDept();
				//depts = deptBS.findDeptsByVO(deptVO,selectedTree);
				if(lazyModel == null){
					lazyModel = new LazyDeptDataModel(deptVO, deptBS);
				}
				
			} catch (Exception e) {
				log.error("部门搜索异常：", e);
				alert("部门搜索异常："+e.getMessage());
			}
		}
	}
	
	public void operate(){
		String operateFlag = ViewOper.getParameter("operateFlag");
		if("view".equalsIgnoreCase(operateFlag) || "edit".equalsIgnoreCase(operateFlag)){
			ViewOper.getSession().setAttribute("deptId", selectDept.getDeptId());
		}else if("delete".equalsIgnoreCase(operateFlag)){
			if (isExistsUserByDeptId(selectDept.getDeptId())){
				alert("部门底下有未删除的用户，无法删除！");
			} else {
				String deptId = String.valueOf(selectDept.getDeptId());
				String sql = "delete from sys_dept where dept_id = '"+deptId+"'";
				try {
					deptBS.getJdbcTemplate().execute(sql);
					lazyModel.setRowCount(lazyModel.getRowCount()-1);
				} catch (Exception e) {
					log.error("删除部门"+selectDept.getDeptName()+"异常：", e);
					alert("删除部门异常："+ e);
				}
			}
		}
	}
	
	public boolean isExistsUserByDeptId(Integer deptId){
		return deptBS.isExistsUserByDeptId(deptId);
	}
	
	public IDeptBS getDeptBS() {
		return deptBS;
	}

	public void setDeptBS(IDeptBS deptBS) {
		this.deptBS = deptBS;
	}

	public TreeNode getAllDept() {
		return allDept;
	}

	public void setAllDept(TreeNode allDept) {
		this.allDept = allDept;
	}

	public SysDeptVO getDeptVO() {
		return deptVO;
	}

	public void setDeptVO(SysDeptVO deptVO) {
		this.deptVO = deptVO;
	}

	public List<SysDept> getDepts() {
		return depts;
	}

	public void setDepts(List<SysDept> depts) {
		this.depts = depts;
	}

	public LazyDataModel<SysDept> getLazyModel() {
		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<SysDept> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public SysDept getSelectDept() {
		return selectDept;
	}

	public void setSelectDept(SysDept selectDept) {
		this.selectDept = selectDept;
	}

	public TreeNode getSelectedTree() {
		return selectedTree;
	}

	public void setSelectedTree(TreeNode selectedTree) {
		this.selectedTree = selectedTree;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}
	public SysUser getSysUser() {
		return sysUser;
	}
	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}
	
}
