package com.qfw.common.bizservice.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.servlet.http.HttpServletRequest;

import com.qfw.common.dao.IBaseDAO;
import com.qfw.common.model.vo.SysComponent;
import com.qfw.common.model.vo.SysComponentTree;
import com.qfw.common.model.vo.SysPageTemplate;
import com.qfw.common.util.web.ViewOper;

public class ComponseFactory {
	private IBaseDAO baseDAO;
	/**
	 * 创建jsf组件树
	 */
	@SuppressWarnings("unchecked")
	public void createUIView(){
		
		UIViewRoot viewRoot = ViewOper.getUIViewRoot();
		HttpServletRequest request = ViewOper.getRequest();
		String servletPath = request.getServletPath();
		String funId = request.getParameter("fun_id");
		//查询页面配置模板
		String hqlString = "from SysPageTemplate where page = '"+servletPath+"'";
		List<SysPageTemplate> pageTemList = baseDAO.findByHQL(hqlString);
		
		if(pageTemList == null || pageTemList.size() == 0){
			return;
		}
		//定义页面模板Map
		Map<String, UIComponent> tempMap = new HashMap<String, UIComponent>();
		for (SysPageTemplate sysPageTemplate : pageTemList) {
			tempMap.put(sysPageTemplate.getTemplate(), viewRoot.findComponent(sysPageTemplate.getCompId()));
		}
		
		//查询页面配置组件
		String queryCompHql = "from SysComponent where isValid = '1' and funId = '"+funId+"' order by pageTemplate,compParentId desc,position";
		List<SysComponent> compList = baseDAO.findByHQL(queryCompHql);
				
		List<SysComponentTree> rootCompTreeList = findCompTemplate(compList);
		/*
		 * 循环处理每个模板节点
		 */
		for (SysComponentTree sysComponentTree : rootCompTreeList) {
			
			try{
				createCompTree(sysComponentTree, compList);
				SysComponent compVaule = sysComponentTree.getValue();
				UIComponent uiComp = tempMap.get(compVaule.getPageTemplate());
				createUICompTree(uiComp, sysComponentTree);
			}catch(Exception e){
				 
			}
			
			
		}
	}

	private void createUICompTree(UIComponent uiComp,SysComponentTree sysComponentTree){
		SysComponent sysComponent = sysComponentTree.getValue();
		String compTpye = sysComponent.getCompTpye();
		if(compTpye != null && !compTpye.equals("")){
			List<UIComponent> uiChildren = uiComp.getChildren();
			try {
				UIComponent uiComponent = (UIComponent) Class.forName(compTpye).newInstance();
				if(sysComponent.getCompId() != null && !"".equals(sysComponent.getCompId())){
					uiComponent.setId(sysComponent.getCompId());
				}
				processValueExpression(uiComponent, sysComponent);
				processMethodExpression(uiComponent, sysComponent);
				if(!sysComponentTree.isLeaf()){//不是叶子节点继续创建子节点
					List<SysComponentTree> children = sysComponentTree.getChildren();
					for (SysComponentTree child : children) {
						createUICompTree(uiComponent, child);
					}
				}
				//将节点添加到组件上
				uiChildren.add(uiComponent);
				uiComponent.decode(FacesContext.getCurrentInstance());
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				 
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				 
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				 
			}
		}else{
			processValueExpression(uiComp, sysComponent);
			processMethodExpression(uiComp, sysComponent);
			uiComp.decode(FacesContext.getCurrentInstance());
		}
		
	}
	
	private void processMethodExpression(UIComponent uiComponent,SysComponent sysComponent){
		
		String methodExpression = sysComponent.getMethodExpression();
		if(methodExpression != null && !"".equals(methodExpression)){
			String splitFlag = sysComponent.getSplitFlag();
			String[] methodExpArray = null;
			if(splitFlag != null && !"".equals(splitFlag)){
				methodExpArray = methodExpression.split(splitFlag);
			}else{
				methodExpArray = new String[]{methodExpression};
			}
			for (String methodExp : methodExpArray) {
				String[] methodExp2 = methodExp.split("=");
				String methodName = "set"+methodExp2[0].substring(0, 1).toUpperCase()+methodExp2[0].substring(1);
				try {
					Method method = uiComponent.getClass().getMethod(methodName, String.class);
					method.invoke(uiComponent, methodExp2[1].trim());
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					 
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					 
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					 
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					 
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					 
				}
				
			}
		}
	}
	/**
	 * 处理结息valueExpression配置
	 * @param uiComponent
	 * @param sysComponent
	 */
	private void processValueExpression(UIComponent uiComponent,SysComponent sysComponent){
		String valueExpression = sysComponent.getValueExpression();
		if(valueExpression != null && !"".equals(valueExpression)){
			//System.out.println(valueExpression);
			String splitFlag = sysComponent.getSplitFlag();
			String[] valueExpArray = null;
			if(splitFlag != null && !"".equals(splitFlag)){
				valueExpArray = valueExpression.split(splitFlag);
			}else{
				valueExpArray = new String[]{valueExpression};
			}
			ELContext elContext = FacesContext.getCurrentInstance().getELContext();
			ExpressionFactory elFactory = FacesContext.getCurrentInstance().getApplication().getExpressionFactory();
			for (String valueExp : valueExpArray) {
				String[] valueExp2 = valueExp.split("=");
				if("action".equals(valueExp2[0])){
					MethodBinding  methodBind =FacesContext.getCurrentInstance().getApplication().createMethodBinding(valueExp2[1], null);
					((UICommand)uiComponent).setAction(methodBind);
				}else{
					ValueExpression ve = elFactory.createValueExpression(elContext, valueExp2[1].trim(), Object.class);
					uiComponent.setValueExpression(valueExp2[0].trim(), ve);
				}
			}
		}
	}
	/**
	 * 创建组件树
	 * @param sysComponentTree
	 * @param compList
	 */
	private void createCompTree(SysComponentTree sysComponentTree,List<SysComponent> compList){
		List<SysComponentTree> compTreeChildren = findCompChildren(compList, sysComponentTree.getValue());
		sysComponentTree.setChildren(compTreeChildren);
		if(compTreeChildren != null && compTreeChildren.size() > 0){
			for (SysComponentTree sysComponentTree2 : compTreeChildren) {
				createCompTree(sysComponentTree2, compList);
			}
		}
	}
	/**
	 * 查找子节点
	 * @param compList
	 * @param compParentId
	 * @return
	 */
	private List<SysComponentTree> findCompChildren(List<SysComponent> compList,SysComponent compParent) {
		List<SysComponent> compChildren = new ArrayList<SysComponent>();
		List<SysComponentTree> compTreeChildren = new ArrayList<SysComponentTree>();
		SysComponentTree  compTree = null;
		String compParentId = compParent.getCompId();
		for (SysComponent sysComponent : compList) {
			if(compParentId != null && compParentId.equals(sysComponent.getCompParentId())){
				compTree = new SysComponentTree();
				compTree.setValue(sysComponent);
				compChildren.add(sysComponent);
				compTreeChildren.add(compTree);
			}
		}
		compList.removeAll(compChildren);
		return compTreeChildren;
	}
	/**
	 * 获取模板节点
	 * @param compList
	 * @return
	 */
	private List<SysComponentTree> findCompTemplate(List<SysComponent> compList) {
		List<SysComponentTree> compTreeTemplates = new ArrayList<SysComponentTree>();
		List<SysComponent> compTemplates = new ArrayList<SysComponent>();
		SysComponentTree rootTree = null;
		for (SysComponent sysComponent : compList) {
			if(sysComponent.getPageTemplate() != null){
				compTemplates.add(sysComponent);
				rootTree = new SysComponentTree();
				rootTree.setValue(sysComponent);
				compTreeTemplates.add(rootTree);
			}
		}
		compList.removeAll(compTemplates);
		return compTreeTemplates;
	}

	public IBaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
	
}
