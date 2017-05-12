package com.qfw.common.bean.permission;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.primefaces.model.DualListModel;
import org.primefaces.model.TreeNode;

import com.qfw.common.bizservice.IParamBS;
import com.qfw.common.bizservice.permission.IDeptBS;
import com.qfw.common.bizservice.permission.IRoleBS;
import com.qfw.common.bizservice.permission.IUserBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.permission.SysDept;
import com.qfw.common.model.permission.SysRole;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.CollectionUtil;
import com.qfw.common.util.MD5Utils;
import com.qfw.common.util.StringUtils;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;

@ViewScoped
@ManagedBean(name="userAddBean")
public class UserAddBean extends BaseBackingBean {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value="#{userBS}")
	private IUserBS userBS;
	@ManagedProperty(value="#{deptBS}")
	private IDeptBS deptBS;
	@ManagedProperty(value="#{roleBS}")
	private IRoleBS roleBS;
	private SysUser user = new SysUser();	
	private String operateFlag;
	@ManagedProperty(value = "#{paramBS}")
	private IParamBS paramBS;
	private TreeNode selectedDept;//选择部门
	private TreeNode allDept;//所以部门树
	private String deptStrs;//部门名称字符串多个用“,”分开
	private String deptIdStrs;//部门ID字符串多个用“,”分开
	private String roleStrs;//角色名称字符串多个用“,”分开
	private String roleIdStrs;//角色id字符串多个用“,”分开
	private DualListModel<SysRole> roleListModel = new DualListModel<SysRole>();
	@PostConstruct
	public void init() {
		if(allDept == null){
			try {
				allDept = deptBS.createAllDeptTree();
				//allDept = deptBS.findOneDeptTree(allDept, dept);
			} catch (BizException e) {
				this.alert(e.getMessage());
			}
		}
		operateFlag = ViewOper.getRequestParameter("operateFlag");
		Object userid = ViewOper.getSessionTmpAttr("userid");
		if (userid != null) {
			user = userBS.findUserById((Integer) userid);
			if(user != null){
				List<SysDept> sysDepts = deptBS.findUserDepts(user.getUserId());
				StringBuilder deptStr = new StringBuilder();
				StringBuilder deptIdStr = new StringBuilder();
				if(sysDepts != null){
					for (SysDept sysDept : sysDepts) {
						deptStr.append(",").append(sysDept.getDeptName());
						deptIdStr.append(",").append(sysDept.getDeptId());
					}
					try {
						List<SysRole> rolesSour = roleBS.findSysRolesByDepts(sysDepts);
						if(CollectionUtil.isEmpty(rolesSour)){
							rolesSour = new ArrayList<SysRole>();
						}
						List<SysRole> commonRoles = roleBS.findSysCommonRoles();
						if(CollectionUtil.isNotEmpty(commonRoles)){
							rolesSour.addAll(commonRoles);
						}else{
							rolesSour = commonRoles;
						}
						roleListModel.setSource(rolesSour);
						List<SysRole> roles = roleBS.findSysRolesByUser(user.getUserId());
						if(CollectionUtil.isNotEmpty(roles)){
							roleListModel.setTarget(roles);
							selectRole();
							rolesSour.removeAll(roles);
						}
						
					} catch (BizException e) {
						 
					}
				}
				if(deptStr.length()>0){
					deptStrs = deptStr.substring(1);
				}
				if(deptIdStr.length()>0){
					deptIdStrs = deptIdStr.substring(1);
				}
			}
		}
	}

	public void save(){
		try{
			String[] deptIds = null;
			String[] roleIds = null;
			int count = userBS.getIsAdminCount(user.getUserCode());
			if (selectedDept != null || roleStrs != null) {
			if(StringUtils.isNotEmpty(deptIdStrs)){
				deptIds = deptIdStrs.split(",");
			}
			if(StringUtils.isNotEmpty(roleIdStrs)){
				roleIds = roleIdStrs.split(",");
			}
			if(user.getUserId()==null || user.getUserId()==0){
				if(count > 0){
					this.alert("登陆名已经存在");
					return;
				}
				
				user.setPassword(MD5Utils.getMD5Str(paramBS.getParam("defaultPassword")));
				user.setIsAdmin("1");
				List<SysUser> userList = userBS.findExistUser(user.getCardid(), user.getTel());
				if(userList !=null && userList.size() > 0){
					if(userList.size() > 1){
						this.alert("用户数据有误");
						return;
					}else{
//						user.setUserId(userList.get(0).getUserId());
//						userBS.updateUser(user, deptIds,roleIds);
						this.alert("用户已经存在");
						return;
					}
				}else{
					userBS.addUser(user, deptIds,roleIds);
				}
				
			}else{
				userBS.updateUser(user, deptIds,roleIds);
			}
//			MessagesController.addInfo("用户"+user.getUserName()+"保存成功！", "用户"+user.getUserName()+"保存成功！");
			executeJS("alert('用户信息["+user.getUserName()+"]保存成功！');closeParMainDialog();");
			}else{
				this.alert("新增用户必须指定部门和角色");
			}
		}catch(Exception e){
			 
			this.alert("操作失败，请与管理员联系");
		}
		
	}
	
	public void onDeptSelect(){
		if(selectedDept != null){//页面选择的部门不为空
			SysDept detp = (SysDept)selectedDept.getData();
			
			selectedDept.setSelected(false);//取消选择状态
			
			//更新角色列表
			List<SysDept> depts = new ArrayList<SysDept>();
			depts.add(detp);
			try {
				roleListModel.getSource().clear();
				List<SysRole> rolesSour = roleBS.findSysRolesByDepts(depts);
				if(CollectionUtil.isNotEmpty(rolesSour)){
					roleListModel.getSource().addAll(rolesSour);
				}
				List<SysRole> commonRoles = roleBS.findSysCommonRoles();
				if(CollectionUtil.isNotEmpty(commonRoles)){
					roleListModel.getSource().addAll(commonRoles);
				}
				roleListModel.getSource().removeAll(roleListModel.getTarget());
			} catch (BizException e) {
				this.alert("部门查找角色错误！");
			}
			
			if(StringUtils.isNotEmpty(deptIdStrs)){//已经存在选择的部门
				String[] deptArr = deptIdStrs.split(",");
				for (String dept : deptArr) {
					if(dept.equals(String.valueOf(detp.getDeptId()))){
						this.alert("已经选择该部门");
						return;
					}
					TreeNode tmp = selectedDept.getParent();
					while(tmp != null){
						Object data = tmp.getData();
						if(data instanceof SysDept) {
							SysDept tmpDetp = (SysDept)tmp.getData();
							if(dept.equals(String.valueOf(tmpDetp.getDeptId()))){
								this.alert("已经选择上级部门，不能选择该部门");
								return;
							}
							tmp = tmp.getParent();
						}else{
							break;
						}
					}
				}
				deptIdStrs += (","+detp.getDeptId());
			}else{
				deptIdStrs = String.valueOf(detp.getDeptId());
			}
			if(StringUtils.isNotEmpty(deptStrs)){
				deptStrs += (","+detp.getDeptName());
			}else{
				deptStrs = detp.getDeptName();
			}
		}
		
	}
	
	public void selectRole(){
		List<SysRole> selectRoles = roleListModel.getTarget();
		if(CollectionUtil.isNotEmpty(selectRoles)){
			StringBuilder ids = new StringBuilder();
			StringBuilder names = new StringBuilder();
			for (SysRole sysRole : selectRoles) {
				if(sysRole.getRoleId() == null || sysRole.getRoleId() == 0){
					continue;
				}
				ids.append(",").append(sysRole.getRoleId());
				names.append(",").append(sysRole.getRoleName());
			}
			if(names.length() > 0){
				roleStrs = names.substring(1);
			}
			if(ids.length() > 0){
				roleIdStrs = ids.substring(1);
			}
			
		}
	}
	public void clearAll(){
		deptStrs = "";
		deptIdStrs = "";
		roleStrs = "";
		roleIdStrs = "";
		if(selectedDept != null){
			selectedDept.setSelected(false);
		}
		roleListModel = new DualListModel<SysRole>();
	}
	
	public void checkUserCode(FacesContext context, UIComponent componentToValidate,Object value) throws ValidatorException {
		int count = userBS.findUserCount(String.valueOf(value));
		if(count>0){
            FacesMessage message =  new FacesMessage(FacesMessage.SEVERITY_ERROR,"登录名"+String.valueOf(value)+"已经存在！", "登录名"+String.valueOf(value)+"已经存在！");
			throw new ValidatorException(message);
		}
	}

	public IUserBS getUserBS() {
		return userBS;
	}
	public void setUserBS(IUserBS userBS) {
		this.userBS = userBS;
	}
	public SysUser getUser() {
		return user;
	}
	public void setUser(SysUser user) {
		this.user = user;
	}

	public String getOperateFlag() {
		return operateFlag;
	}

	public void setOperateFlag(String operateFlag) {
		this.operateFlag = operateFlag;
	}

	public IParamBS getParamBS() {
		return paramBS;
	}

	public void setParamBS(IParamBS paramBS) {
		this.paramBS = paramBS;
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

	public IDeptBS getDeptBS() {
		return deptBS;
	}

	public void setDeptBS(IDeptBS deptBS) {
		this.deptBS = deptBS;
	}

	public String getDeptStrs() {
		return deptStrs;
	}

	public void setDeptStrs(String deptStrs) {
		this.deptStrs = deptStrs;
	}

	public String getDeptIdStrs() {
		return deptIdStrs;
	}

	public void setDeptIdStrs(String deptIdStrs) {
		this.deptIdStrs = deptIdStrs;
	}

	public DualListModel<SysRole> getRoleListModel() {
		return roleListModel;
	}

	public void setRoleListModel(DualListModel<SysRole> roleListModel) {
		this.roleListModel = roleListModel;
	}

	public IRoleBS getRoleBS() {
		return roleBS;
	}

	public void setRoleBS(IRoleBS roleBS) {
		this.roleBS = roleBS;
	}

	public String getRoleStrs() {
		return roleStrs;
	}

	public void setRoleStrs(String roleStrs) {
		this.roleStrs = roleStrs;
	}

	public String getRoleIdStrs() {
		return roleIdStrs;
	}

	public void setRoleIdStrs(String roleIdStrs) {
		this.roleIdStrs = roleIdStrs;
	}
	
	
}
