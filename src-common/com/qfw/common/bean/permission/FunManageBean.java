package com.qfw.common.bean.permission;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.primefaces.model.TreeNode;

import com.qfw.common.bizservice.permission.IDeptBS;
import com.qfw.common.bizservice.permission.IFunBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.permission.SysDept;
import com.qfw.common.model.permission.SysFunction;
import com.qfw.common.util.web.BaseBackingBean;

@ViewScoped
@ManagedBean(name="funManageBean")
public class FunManageBean extends BaseBackingBean{
	private TreeNode allDept;
	private TreeNode allFuns;
	private List<SelectItem> havedFuns = new ArrayList<SelectItem>();
	private List selectedHaveFuns;
	private TreeNode selectedDept;
	private List<SelectItem> roles = new ArrayList<SelectItem>();
	private TreeNode[] selectedFuns;
	private String selectedRoleId;
	@ManagedProperty(value="#{deptBS}")
	private IDeptBS deptBS;
	@ManagedProperty(value="#{funBS}")
	private IFunBS funBS;
	private Logger log = LogFactory.getInstance().getPlatformLogger();
	
	public TreeNode getAllDept() {
		return allDept;
	}
	public void setAllDept(TreeNode allDept) {
		this.allDept = allDept;
	}
	public TreeNode getAllFuns() {
		return allFuns;
	}
	public void setAllFuns(TreeNode allFuns) {
		this.allFuns = allFuns;
	}
	public List<SelectItem> getRoles() {
		return roles;
	}
	public void setRoles(List<SelectItem> roles) {
		this.roles = roles;
	}
	public TreeNode getSelectedDept() {
		return selectedDept;
	}
	public void setSelectedDept(TreeNode selectedDept) {
		this.selectedDept = selectedDept;
	}
	public IDeptBS getDeptBS() {
		return deptBS;
	}
	public void setDeptBS(IDeptBS deptBS) {
		this.deptBS = deptBS;
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
	public TreeNode[] getSelectedFuns() {
		return selectedFuns;
	}
	public void setSelectedFuns(TreeNode[] selectedFuns) {
		this.selectedFuns = selectedFuns;
	}
	public List<SelectItem> getHavedFuns() {
		return havedFuns;
	}
	public void setHavedFuns(List<SelectItem> havedFuns) {
		this.havedFuns = havedFuns;
	}
	public List getSelectedHaveFuns() {
		return selectedHaveFuns;
	}
	public void setSelectedHaveFuns(List selectedHaveFuns) {
		this.selectedHaveFuns = selectedHaveFuns;
	}
	@PostConstruct
	public void init(){
		try {
			allDept = deptBS.createAllDeptTree();
		} catch (BizException e) {
			log.error("创建部门树异常：", e);
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
	/**
	 * 选择角色
	 * @param event
	 */
	public void onRoleSelect(ValueChangeEvent event){
		try {
			selectedRoleId = (String) event.getNewValue();
			allFuns = funBS.createFunTreeByRole(Integer.valueOf(selectedRoleId));
			List funsList = funBS.findHavedFunsByRoleId(Integer.valueOf(selectedRoleId));
			havedFuns = new ArrayList<SelectItem>();
			for(int i=0;i<funsList.size();i++){
				havedFuns.add(new SelectItem(((Map)funsList.get(i)).get("FUN_ID").toString(),((Map)funsList.get(i)).get("FUN_NAME").toString()));
			}
			funBS.searchFunTree(allFuns, funsList);
		} catch (BizException e) {
			log.error("通过角色查找功能信息异常：", e);
			alert("通过角色查找功能信息异常！");
		}
	}
	/**
	 * 保存功能
	 */
	public void addFuns(){
		try {
			//删除已授权的功能
			List haveFuns = funBS.findHavedFunsByRoleId(Integer.valueOf(selectedRoleId));
			for (int i = 0; i < haveFuns.size(); i++) {
				funBS.deleteRoleFunction(Integer.valueOf(selectedRoleId), Integer.valueOf(((Map)haveFuns.get(i)).get("FUN_ID").toString()));
			}
			//授权已经选择的功能，并将它们的父节点都给于权限
			for (int i = 0; i < selectedFuns.length; i++) {
				SysFunction sysFunction = (SysFunction) selectedFuns[i].getData();
				if(!funBS.findRoleFunction(Integer.valueOf(selectedRoleId), sysFunction.getFunId())){
					funBS.saveRoleFunction(Integer.valueOf(selectedRoleId), sysFunction.getFunId());
					funBS.saveTreeParents(selectedFuns[i], Integer.valueOf(selectedRoleId));//授权父节点
				}
			}
			//获取已授权的功能（为了刷新页面）
			List funsList = funBS.findHavedFunsByRoleId(Integer.valueOf(selectedRoleId));
			havedFuns.clear();
			for(int i=0;i<funsList.size();i++){
				havedFuns.add(new SelectItem(((Map)funsList.get(i)).get("FUN_ID").toString(),((Map)funsList.get(i)).get("FUN_NAME").toString()));
			}
			allFuns = funBS.createAllFunTree();//重新获取整棵功能树
			funBS.searchFunTree(allFuns, funsList);//节点的展开和选择处理
			alert("保存成功！");
		} catch (BizException e) {
			log.error("添加未授权功能异常：", e);
			alert("添加未授权功能异常！");
		} catch (Exception e) {
			log.error("添加未授权功能异常：", e);
			alert("添加未授权功能异常！");
		}
	}
	/**
	 * 全选所有功能
	 */
	public void selectedAllFuns(){
		try {
			List funsList = funBS.findAllFun();
			funBS.searchFunTree(allFuns, funsList);//节点的展开和选择处理
		} catch (BizException e) {
			log.error("添加全部功能异常：", e);
			alert("添加全部功能异常！");
		}
	}
	/**
	 * 清空所有功能
	 */
	public void removedAllFuns(){
		try {
			allFuns = funBS.createAllFunTree();//重新获取整棵功能树
		} catch (BizException e) {
			log.error("添加全部功能异常：", e);
			alert("添加全部功能异常！");
		}
	}
}
