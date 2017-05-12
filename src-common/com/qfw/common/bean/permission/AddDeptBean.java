package com.qfw.common.bean.permission;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.TreeNode;

import com.qfw.common.bizservice.permission.IDeptBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.permission.SysDept;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;

@ViewScoped
@ManagedBean(name = "addDeptBean")
public class AddDeptBean extends BaseBackingBean{
	
	
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{deptBS}")
	private IDeptBS deptBS;
	private String operateFlag;
	private TreeNode allDept;
	private SysDept dept = new SysDept();
	private SysDept parDept = new SysDept();
	private TreeNode selectedTree;
	private Logger log = LogFactory.getInstance().getPlatformLogger();

	@PostConstruct
	public void init() {
		operateFlag = ViewOper.getRequestParameter("operateFlag");
		Object deptId = ViewOper.getSessionTmpAttr("deptId");
		if(deptId!=null){
			try {
				dept = deptBS.getSysDeptById((Integer)deptId);
				if(dept!=null && dept.getParentDeptId() != null && dept.getParentDeptId()>0){
					parDept = deptBS.getSysDeptById(dept.getParentDeptId());
				}
			} catch (BizException e) {
				log.error("通过ID获取部门异常：", e);
			}
		}
		try {
			allDept = deptBS.createAllDeptTree();
			if("view".equals(operateFlag) || "edit".equals(operateFlag)){
				selectedTree = deptBS.findOneDeptTree(allDept, dept);
				if(selectedTree != null){
					TreeNode tempNode = selectedTree;
					tempNode.setSelected(true);
					for(;tempNode.getParent()!=null;){
						tempNode = tempNode.getParent();
						tempNode.setExpanded(true);
					}
				}
			}
		} catch (BizException e) {
			log.error("新增页面创建部门树异常：", e);
		}
	}
	
	 public void onDeptSelect(){
		 parDept = (SysDept) selectedTree.getData();
	 }

	public String operate(){
		
		SysUser user = (SysUser) ViewOper.getSession().getAttribute("user");
		if("add".equals(operateFlag)){
			if(checkDeptCode(dept.getDeptCode(),null)){
				alert("部门代号已经存在，请重新输入！");
				dept.setDeptCode("");
				return null;
			}
			if(checkDeptName(dept.getDeptName(),null)){
				alert("部门名称已经存在，请重新输入！");
				dept.setDeptName("");
				return null;
			}
			if(parDept == null || parDept.getParentDeptId() == null || parDept.getParentDeptId().equals(Integer.valueOf(0))){
				dept.setParentDeptId(parDept.getDeptId());
				dept.setLevelNum(new Integer(1));
				dept.setOrderNum(allDept.getChildCount());
				dept.setStatus("1");	
				dept.setSysUpdateUser(user.getUserCode());
			} else{
				//获取子节点的id
				int count = deptBS.findChildrenCount(parDept.getDeptId());
				dept.setParentDeptId(parDept.getDeptId());
				dept.setLevelNum(parDept.getLevelNum()+1);
				dept.setOrderNum(count+1);
				dept.setStatus(parDept.getStatus());
				dept.setSysUpdateUser(user.getUserCode());
			}
			this.deptBS.save(dept);
//			MessagesController.addInfo("用户"+dept.getDeptName()+"保存成功！", "用户"+dept.getDeptName()+"保存成功！");
			executeJS("alert('部门信息["+dept.getDeptName()+"]保存成功！');closeParMainDialog();");
		}else if("edit".equals(operateFlag)){
			if(checkDeptCode(dept.getDeptCode(),dept.getDeptId())){
				alert("部门代号已经存在，请重新输入！");
				dept.setDeptCode("");
				return null;
			}
			if(checkDeptName(dept.getDeptName(),dept.getDeptId())){
				alert("部门名称已经存在，请重新输入！");
				dept.setDeptName("");
				return null;
			}
			dept.setParentDeptId(parDept.getDeptId());
			dept.setSysUpdateUser(user.getUserCode());
			this.deptBS.update(dept);
//			MessagesController.addInfo("用户"+dept.getDeptName()+"修改成功！", "用户"+dept.getDeptName()+"修改成功！");
			executeJS("alert('部门信息["+dept.getDeptName()+"]修改成功！');closeParMainDialog();");
		}
		dept = new SysDept();
		parDept = new SysDept();
		try {
			allDept = deptBS.createAllDeptTree();
		} catch (BizException e) {
			if("add".equals(operateFlag)){
				log.error("保存部门"+dept.getDeptName()+"的时候更新部门树异常：", e);
				alert("保存部门的时候更新部门树异常："+ e);
			}else if("edit".equals(operateFlag)){
				log.error("修改部门"+dept.getDeptName()+"的时候更新部门树异常：", e);
				alert("修改部门的时候更新部门树异常："+ e);
			}
			
		}
		return null;
	}
	
	public boolean checkDeptName(String deptName,Integer deptId){
		List<SysDept> list = new ArrayList<SysDept>();
		try {
			list = deptBS.checkDeptName(deptName,deptId);
		} catch (BizException e) {
			log.error("验证部门名称异常：", e);
			alert("验证部门名称异常："+e);
		}
		if(list != null && list.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean checkDeptCode(String deptCode,Integer deptId){
		List<SysDept> list = new ArrayList<SysDept>();
		try {
			list = deptBS.checkDeptCode(deptCode,deptId);
		} catch (BizException e) {
			log.error("验证部门代号异常：", e);
			alert("验证部门代号异常："+e);
		}
		if(list != null && list.size() > 0){
			return true;
		}else{
			return false;
		}
	}

	public IDeptBS getDeptBS() {
		return deptBS;
	}

	public void setDeptBS(IDeptBS deptBS) {
		this.deptBS = deptBS;
	}

	public SysDept getDept() {
		return dept;
	}

	public void setDept(SysDept dept) {
		this.dept = dept;
	}

	public String getOperateFlag() {
		return operateFlag;
	}

	public void setOperateFlag(String operateFlag) {
		this.operateFlag = operateFlag;
	}

	public TreeNode getAllDept() {
		return allDept;
	}

	public void setAllDept(TreeNode allDept) {
		this.allDept = allDept;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public SysDept getParDept() {
		return parDept;
	}

	public void setParDept(SysDept parDept) {
		this.parDept = parDept;
	}

	public TreeNode getSelectedTree() {
		return selectedTree;
	}

	public void setSelectedTree(TreeNode selectedTree) {
		this.selectedTree = selectedTree;
	}

}
