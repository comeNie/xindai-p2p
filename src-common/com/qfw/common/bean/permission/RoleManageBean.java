package com.qfw.common.bean.permission;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.TreeNode;

import com.qfw.common.bizservice.permission.IFunBS;
import com.qfw.common.bizservice.permission.IRoleBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.permission.SysRole;
import com.qfw.common.model.permission.vo.LazyRoleDataModel;
import com.qfw.common.model.permission.vo.SysRoleVO;
import com.qfw.common.util.MessagesController;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;

@ViewScoped
@ManagedBean(name="roleManageBean")
public class RoleManageBean extends BaseBackingBean{

	@ManagedProperty(value="#{roleBS}")
	private IRoleBS roleBS;	
	private LazyDataModel<SysRole> lazyModel;
	private SysRole selectRole;
	private SysRoleVO roleVO = new SysRoleVO();
	private TreeNode allFuns;
	@ManagedProperty(value="#{funBS}")
	private IFunBS funBS;
	private TreeNode[] selectedFuns;
	private Logger log = LogFactory.getInstance().getPlatformLogger();
	
	@PostConstruct
	public void init(){
		if(roleVO != null){
			try {
				if(lazyModel == null){
					lazyModel = new LazyRoleDataModel(roleVO, roleBS);
				}
				
			} catch (Exception e) {
				log.error("角色搜索异常：", e);
				alert("角色搜索异常："+ e.getMessage());
			}	
		}
	}
	/**
	 * 角色搜索
	 */
	public void search(){
		super.search();
		init();
	}

	public void operate(){
		String operateFlag = ViewOper.getParameter("operateFlag");
		if("view".equalsIgnoreCase(operateFlag)||"edit".equals(operateFlag)){
			ViewOper.getSession().setAttribute("roleid", selectRole.getRoleId());
		}else if("delete".equalsIgnoreCase(operateFlag)){
			String roleid = String.valueOf(selectRole.getRoleId());
			String sql = "DELETE FROM SYS_ROLE WHERE ROLE_ID = '"+roleid+"'";
			try {
				roleBS.getJdbcTemplate().execute(sql);
				lazyModel.setRowCount(lazyModel.getRowCount()-1);
			} catch (Exception e) {
				log.error("删除角色"+selectRole.getRoleName()+"异常：", e);
				alert("删除角色异常："+ e);
			}
		}else if("selectFuns".equalsIgnoreCase(operateFlag)){
			try{
				allFuns = funBS.createFunTreeByRole(selectRole.getRoleId());
				List funsList = funBS.findHavedFunsByRoleId(selectRole.getRoleId());
				funBS.searchFunTree(allFuns, funsList);
			}catch(Exception e){
				log.error("权限查询错误", e);
				alert("权限查询错误");
			}
		}else{
			ViewOper.getSession().setAttribute("roleid", null);
		}
		
	}

	/**
	 * 全选所有功能
	 */
	public void selectedAllFuns(){
		try {
			funBS.changeFunSelect(allFuns, true);
		} catch (Exception e) {
			log.error("添加全部功能异常：", e);
			alert("添加全部功能异常！");
		}
	}
	/**
	 * 清空所有功能
	 */
	public void removedAllFuns(){
		try {
			funBS.changeFunSelect(allFuns, false);
		} catch (Exception e) {
			log.error("添加全部功能异常：", e);
			alert("添加全部功能异常！");
		}
	}
	
	/**
	 * 保存功能
	 */
	public void addFuns(){
		try {
			funBS.updateRoleFunction(selectRole.getRoleId(), selectedFuns);
			MessagesController.addInfo("保存成功！", "保存成功！");
		} catch (BizException e) {
			log.error("添加未授权功能异常：", e);
			alert("添加未授权功能异常！");
		} catch (Exception e) {
			log.error("添加未授权功能异常：", e);
			alert("添加未授权功能异常！");
		}
	}
	
	public LazyDataModel<SysRole> getLazyModel() {
		return lazyModel;
	}
	public void setLazyModel(LazyDataModel<SysRole> lazyModel) {
		this.lazyModel = lazyModel;
	}
	public IRoleBS getRoleBS() {
		return roleBS;
	}
	public void setRoleBS(IRoleBS roleBS) {
		this.roleBS = roleBS;
	}
	public SysRole getSelectRole() {
		return selectRole;
	}
	public void setSelectRole(SysRole selectRole) {
		this.selectRole = selectRole;
	}

	public SysRoleVO getRoleVO() {
		return roleVO;
	}

	public void setRoleVO(SysRoleVO roleVO) {
		this.roleVO = roleVO;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}
	public TreeNode getAllFuns() {
		return allFuns;
	}
	public void setAllFuns(TreeNode allFuns) {
		this.allFuns = allFuns;
	}
	public IFunBS getFunBS() {
		return funBS;
	}
	public void setFunBS(IFunBS funBS) {
		this.funBS = funBS;
	}
	public TreeNode[] getSelectedFuns() {
		return selectedFuns;
	}
	public void setSelectedFuns(TreeNode[] selectedFuns) {
		this.selectedFuns = selectedFuns;
	}
	
	

}
