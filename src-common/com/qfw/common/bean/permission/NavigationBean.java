package com.qfw.common.bean.permission;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.MenuModel;
import org.primefaces.model.TreeNode;

import com.qfw.common.dao.ICommonQuery;
import com.qfw.common.model.BussConst;
import com.qfw.common.model.constant.PermissionsConst;
import com.qfw.common.model.permission.SysFunction;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.CollectionUtil;
import com.qfw.common.util.web.ViewOper;
@RequestScoped
@ManagedBean(name="navigationBean")
public class NavigationBean implements Serializable{


	private final String FUN_SQL = "select distinct f.* from sys_function f, sys_role_function rf where f.fun_id = rf.fun_id and exists (select 1 from (select r.* from sys_role r, sys_user_role ur where r.role_id = ur.role_id and exists (select 1 from sys_user u where u.user_id = ur.user_id and u.user_id = ? )) t where rf.role_id = t.role_id) order by f.fun_level,f.sort ";
	private final String QUERY_USER_FUN ="select distinct fun.* from sys_function fun,sys_role rl,sys_role_function rf,sys_user u,sys_user_role ur where fun.fun_id = rf.fun_id and rl.role_id = rf.role_id and rl.role_id = ur.role_id and u.user_id = ur.user_id and u.user_id = ? order by fun.fun_level,fun.sort";
	public NavigationBean() {
		super();
	}	
	@ManagedProperty(value = "#{commonQuery}")
	private ICommonQuery commonQuery;
	//头部导航菜单
	private List<SysFunction> topMenus = new LinkedList<SysFunction>();
	//左边导航树
	private Map<SysFunction ,List<SysFunction>> leftMenus = new LinkedHashMap<SysFunction,List<SysFunction>> ();//遍历的时候不会出现随机问题
	private List navigationMenu = new ArrayList();
	private SysFunction selectSysFunction;
	
    private void createLeftTree(){
    	SysUser user = (SysUser)ViewOper.getSession().getAttribute("user");
    	List<SysFunction> funs = null;
    	SysFunction topMenu = null;
    	if(user != null){
    		funs = commonQuery.findObjects(FUN_SQL,new String[]{user.getUserId().toString()}, SysFunction.class);
		}else{
			return;//
		}
    	if(CollectionUtil.isNotEmpty(funs)){
    		
    		Map<Integer,SysFunction> funLevel2 = new LinkedHashMap<Integer, SysFunction>();//二级菜单,左边树的一级菜单    		
    		for(SysFunction fun : funs){
    			if(PermissionsConst.FUN_LEVEL1.equals(fun.getFunLevel())){
    				continue;
    			}else if(PermissionsConst.FUN_LEVEL2.equals(fun.getFunLevel())){
    				funLevel2.put(fun.getFunId(), fun);
    				leftMenus.put(fun, new ArrayList<SysFunction>());
    			}else if(PermissionsConst.FUN_LEVEL3.equals(fun.getFunLevel())){  
    				leftMenus.get(funLevel2.get(fun.getParentFunId())).add(fun);
    			}
    		}
    	}
    	
	}
	public List<SysFunction> getTopMenus() {
		return topMenus;
	}
	public void setTopMenus(List<SysFunction> topMenus) {
		this.topMenus = topMenus;
	}
	public Map<SysFunction, List<SysFunction>> getLeftMenus() {
		if(leftMenus == null){
			createLeftTree();
		}
		return leftMenus;
	}
	public void setLeftMenus(Map<SysFunction, List<SysFunction>> leftMenus) {
		this.leftMenus = leftMenus;
	}
	
	public List getNavigationMenu() {
		if(navigationMenu.size() == 0){
			Map<SysFunction ,List<SysFunction>> functionMap = this.getLeftMenus();
			if(functionMap == null || functionMap.size() == 0)
				return null;
			Iterator it = functionMap.entrySet().iterator();
			while(it.hasNext())
				navigationMenu.add(it.next());
		}
		return navigationMenu;
	}
	public void setNavigationMenu(List navigationMenu) {
		this.navigationMenu = navigationMenu;
	}
	public ICommonQuery getCommonQuery() {
		return commonQuery;
	}
	public void setCommonQuery(ICommonQuery commonQuery) {
		this.commonQuery = commonQuery;
	}
	public SysFunction getSelectSysFunction() {
		if(selectSysFunction == null){
			selectSysFunction = new SysFunction();
			selectSysFunction.setFunPath("");
		}
		return selectSysFunction;
	}
	public void setSelectSysFunction(SysFunction selectSysFunction) {
		this.selectSysFunction = selectSysFunction;
	}
	
	private MenuModel createMenuModel(){
		DefaultTreeNode funRoot = getFunRoot();
		MenuModel model = new DefaultMenuModel();
		List<TreeNode> children = funRoot.getChildren();
		for (TreeNode treeNode : children) {
			SysFunction fun = (SysFunction) treeNode.getData();
			if(treeNode.isLeaf()){
				MenuItem item = new MenuItem();  
		        item.setValue(fun.getFunName());  
		        item.setUrl("javascript:changeMenu('food','"+fun.getFunPath()+"','"+fun.getFunName()+"');");
		        model.addMenuItem(item);
			}else{
				Submenu submenu = new Submenu();
				submenu.setLabel(fun.getFunName());
				model.addSubmenu(submenu);
				createSubmenuItem(submenu, treeNode.getChildren());
			}
		}
		return model;
	}
	private void createSubmenuItem(Submenu submenu,List<TreeNode> treeNodes){
		for (TreeNode treeNode : treeNodes) {
			SysFunction fun = (SysFunction) treeNode.getData();
			if(treeNode.isLeaf()){
				MenuItem item = new MenuItem();  
		        item.setValue(fun.getFunName());  
		        item.setUrl("javascript:changeMenu('/food','"+fun.getFunPath()+"','"+fun.getFunName()+"');");
		        submenu.getChildren().add(item);
			}else{
				Submenu submenuChild = new Submenu();
				submenuChild.setLabel(fun.getFunName());
				submenu.getChildren().add(submenuChild);
				createSubmenuItem(submenuChild, treeNode.getChildren());
			}
		}
	}
	
    private DefaultTreeNode funRoot;
	public DefaultTreeNode getFunRoot() {
		if(funRoot == null){
			DefaultTreeNode funs = (DefaultTreeNode) ViewOper.getSession().getAttribute("userFuns");
			if(funs == null){
				return new DefaultTreeNode();
			}
			funRoot = new DefaultTreeNode(funs.getData(),null);
			List<TreeNode> children = funs.getChildren();
			for (TreeNode treeNode : children) {
				funRoot.getChildren().add(createFunTree(treeNode));
			}
		}
		return funRoot;
	}
	public TreeNode createFunTree(TreeNode treeNode){
		DefaultTreeNode newNode = new DefaultTreeNode();
		SysFunction fun = (SysFunction) treeNode.getData();
		newNode.setData(fun);
		if(!treeNode.isLeaf()&&BussConst.NO_FLAG.equals(fun.getIsLast())){
			List<TreeNode> children= treeNode.getChildren();
			for (TreeNode treeNode2 : children) {
				newNode.getChildren().add(createFunTree(treeNode2));
			}
		}
		return newNode;
	}
	public void setFunRoot(DefaultTreeNode funRoot) {
		this.funRoot = funRoot;
	}
	private MenuModel menuModel;
	
	public MenuModel getMenuModel() {
		if(menuModel == null){
			menuModel = createMenuModel();
		}
		return menuModel;
	}
	public void setMenuModel(MenuModel menuModel) {
		this.menuModel = menuModel;
	}
	public void onNodeSelect(NodeSelectEvent event){
		SysFunction selectSysFunction = (SysFunction) event.getTreeNode().getData();
		if("3".equals(selectSysFunction.getFunType())&&selectSysFunction.getFunId() != this.selectSysFunction.getFunId()){
			this.selectSysFunction = selectSysFunction;
		}
	}
}
