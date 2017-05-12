package com.qfw.common.bean.permission;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.TreeNode;

import com.qfw.common.bizservice.permission.IDeptBS;
import com.qfw.common.bizservice.permission.IRoleBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.permission.SysDept;
import com.qfw.common.model.permission.SysRole;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.NumberUtils;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;

@ViewScoped
@ManagedBean(name="roleAddBean")
public class RoleAddBean extends BaseBackingBean{

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value="#{roleBS}")
	private IRoleBS roleBS;
	@ManagedProperty(value="#{deptBS}")
	private IDeptBS deptBS;
	private SysRole role = new SysRole();	
	private String operateFlag;
	private TreeNode selectedDept;
	private TreeNode allDept;
	private SysDept dept = new SysDept();
	private Logger log = LogFactory.getInstance().getPlatformLogger();
	@PostConstruct
	public void init() {
		operateFlag = ViewOper.getRequestParameter("operateFlag");
		Object roleid = ViewOper.getSessionTmpAttr("roleid");
		if (roleid != null) {
			role = (SysRole) roleBS.find(SysRole.class, String.valueOf(roleid));
			if(!NumberUtils.isBlank(role.getDeptId())){
				dept = (SysDept) deptBS.find(SysDept.class, role.getDeptId());
			}
		}else{
			role = new SysRole();
		}
		try {
			allDept = this.deptBS.createAllDeptTree();
			if(roleid != null){
				if("view".equals(operateFlag) || "edit".equals(operateFlag)){
					if(!NumberUtils.isBlank(dept.getDeptId())){
						selectedDept = roleBS.findDeptTreeNode(allDept,dept);
						if(selectedDept != null){
							TreeNode tempNode = selectedDept;
							tempNode.setSelected(true);
							for(;tempNode.getParent()!=null;){
								tempNode = tempNode.getParent();
								tempNode.setExpanded(true);
							}
						}
					}
					
				}
			}
		} catch (BizException e) {
			log.error("新增角色页面创建部门树异常：", e);
		}
	}
	
	public String save(){
		/*if(dept == null || dept.getDeptId() == null || dept.getDeptId() == 0){
			alert("部门信息不能为空");
			return null;
		}*/
		SysUser user = (SysUser) ViewOper.getSession().getAttribute("user");
		if(role.getRoleId()==null || role.getRoleId()==0){
			if(checkRoleCode(role.getRoleCode(),null)){
				alert("角色代号已经存在，请重新输入！");
				return null;
			}
			/*if(checkRoleName(role.getRoleName())){
				alert("角色名称已经存在，请重新输入！");
				return null;
			}*/
			role.setDeptId(dept.getDeptId());
			role.setSysUpdateUser(user.getUserCode());
			role.setSysCreateTime(new Date());
			roleBS.save(role);
//			MessagesController.addInfo("角色"+role.getRoleName()+"保存成功！", "角色"+role.getRoleName()+"保存成功！");
			executeJS("alert('角色信息["+role.getRoleName()+"]保存成功！');closeParMainDialog();");
		}else{
			if(checkRoleCode(role.getRoleCode(),role.getRoleId())){
				alert("角色代号已经存在，请重新输入！");
				return null;
			}
			/*if(checkRoleName(role.getRoleName())){
				alert("角色名称已经存在，请重新输入！");
				return null;
			}*/
			role.setSysUpdateUser(user.getUserCode());
			roleBS.update(role);
//			MessagesController.addInfo("角色"+role.getRoleName()+"修改成功！", "角色"+role.getRoleName()+"修改成功！");
			executeJS("alert('角色信息["+role.getRoleName()+"]修改成功！');closeParMainDialog();");
		}
		role = new SysRole();
		return null;
	}
	
	 public void onDeptSelect(){
		 dept = (SysDept) selectedDept.getData();
	 }
	 
	
	public boolean checkRoleCode(String roleCode,Integer roleId){
		List<SysRole> list = new ArrayList<SysRole>();
		try {
			if(NumberUtils.isBlank(dept.getDeptId())){
				list = roleBS.findRolesByCode(roleCode,roleId,null);
			}else{
				list = roleBS.findRolesByCode(roleCode,roleId,dept.getDeptId());
			}
			
		} catch (BizException e) {
			log.error("验证角色代号异常：", e);
			alert("验证角色代号异常："+e);
		}
		if(list != null && list.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean checkRoleName(String roleName,Integer roleId){
		List<SysRole> list = new ArrayList<SysRole>();
		try {
			list = roleBS.findRolesByName(roleName,roleId,dept.getDeptId());
		} catch (BizException e) {
			log.error("验证角色名称异常：", e);
			alert("验证角色名称异常："+e);
		}
		if(list != null && list.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	/*public void checkRoleCode(FacesContext context, UIComponent componentToValidate,Object value) throws ValidatorException {
		int count = roleBS.count("select count(role) from SysRole role where roleCode");
		if(count>0){
            FacesMessage message =  new FacesMessage(FacesMessage.SEVERITY_ERROR,"登录名"+String.valueOf(value)+"已经存在！", "登录名"+String.valueOf(value)+"已经存在！");
			throw new ValidatorException(message);
		}
	}*/

	

	public String getOperateFlag() {
		return operateFlag;
	}

	public IRoleBS getRoleBS() {
		return roleBS;
	}

	public void setRoleBS(IRoleBS roleBS) {
		this.roleBS = roleBS;
	}

	public SysRole getRole() {
		return role;
	}

	public void setRole(SysRole role) {
		this.role = role;
	}

	public void setOperateFlag(String operateFlag) {
		this.operateFlag = operateFlag;
	}

	public TreeNode getSelectedDept() {
		return selectedDept;
	}

	public void setSelectedDept(TreeNode selectedDept) {
		this.selectedDept = selectedDept;
	}

	public TreeNode getAllDept() {
		return allDept;
	}

	public void setAllDept(TreeNode allDept) {
		this.allDept = allDept;
	}

	
	public SysDept getDept() {
		return dept;
	}

	public void setDept(SysDept dept) {
		this.dept = dept;
	}

	public IDeptBS getDeptBS() {
		return deptBS;
	}

	public void setDeptBS(IDeptBS deptBS) {
		this.deptBS = deptBS;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

}
