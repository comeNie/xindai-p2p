package com.qfw.common.bean.permission;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.primefaces.model.TreeNode;

import com.qfw.common.bizservice.permission.IDeptBS;
import com.qfw.common.bizservice.permission.IFunBS;
import com.qfw.common.bizservice.permission.IUserBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.permission.SysDept;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;

@ManagedBean(name="userAddRoleBean")
public class UserAddRoleBean extends BaseBackingBean{

	private TreeNode selectedDept;
	private List<SelectItem> roles = new ArrayList<SelectItem>();
	private String selectedRoleId;
	private TreeNode allDept;
	private SysUser user = new SysUser();
	@ManagedProperty(value="#{deptBS}")
	private IDeptBS deptBS;
	@ManagedProperty(value="#{funBS}")
	private IFunBS funBS;
	@ManagedProperty(value="#{userBS}")
	private IUserBS userBS;
	private Logger log = LogFactory.getInstance().getPlatformLogger();
		
	public TreeNode getSelectedDept() {
		return selectedDept;
	}

	public void setSelectedDept(TreeNode selectedDept) {
		this.selectedDept = selectedDept;
	}

	public List<SelectItem> getRoles() {
		return roles;
	}

	public void setRoles(List<SelectItem> roles) {
		this.roles = roles;
	}

	public IFunBS getFunBS() {
		return funBS;
	}

	public void setFunBS(IFunBS funBS) {
		this.funBS = funBS;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public String getSelectedRoleId() {
		return selectedRoleId;
	}

	public void setSelectedRoleId(String selectedRoleId) {
		this.selectedRoleId = selectedRoleId;
	}

	public TreeNode getAllDept() {
		return allDept;
	}

	public void setAllDept(TreeNode allDept) {
		this.allDept = allDept;
	}

	public IDeptBS getDeptBS() {
		return deptBS;
	}

	public void setDeptBS(IDeptBS deptBS) {
		this.deptBS = deptBS;
	}

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}

	public IUserBS getUserBS() {
		return userBS;
	}

	public void setUserBS(IUserBS userBS) {
		this.userBS = userBS;
	}

	@PostConstruct
	public void init(){
		try {
			allDept = deptBS.createAllDeptTree();
			Object userid = ViewOper.getSessionTmpAttr("userid");
			 if(userid!=null){
				 user = userBS.findUserById((Integer)userid);  
			 }		 
		} catch (BizException e) {
			log.error("创建部门树异常：",e);
			alert("创建部门树异常！");
		}
	}
	/**
	 * 选择部门
	 */
	public void onDeptSelect(){
		SysDept sysDept = (SysDept) selectedDept.getData();
		Integer deptId = sysDept.getDeptId();
		List sysRoleList = new ArrayList();
		roles = new ArrayList<SelectItem>();
		try {
			sysRoleList = funBS.findRolesByDeptId(deptId);
			for (int i = 0; i < sysRoleList.size(); i++) {
				roles.add(new SelectItem(((Map)sysRoleList.get(i)).get("ROLE_ID").toString(),((Map)sysRoleList.get(i)).get("ROLE_NAME").toString()));
			}
		} catch (BizException e) {
			log.error("通过部门ID查找角色信息异常：", e);
			alert("通过部门ID查找角色信息异常！");
		}
	}
	
	public void saveInfo(){
		SysDept sysDept = (SysDept) selectedDept.getData();
		Integer deptId = sysDept.getDeptId();
		Integer roleId = Integer.parseInt(selectedRoleId);
		Integer userId = user.getUserId();
		try {
			this.userBS.saveUserRole(userId, roleId);
		} catch (BizException e) {
			log.error("新增用户的角色信息异常：", e);
		}
		try {
			this.userBS.saveUserDept(userId, deptId);
		} catch (BizException e) {
			log.error("新增用户的部门信息异常：", e);
		}
	}
}
