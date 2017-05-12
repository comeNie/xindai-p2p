/**
 * Copyright 2015 Software innovation and R & D center. All rights reserved.
 * File Name: AddFunctionBean.java
 * Encoding UTF-8
 * Version: 0.0.1
 * History:	2015年7月28日
 */
package com.qfw.common.bean.permission;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.TreeNode;

import com.qfw.common.bizservice.permission.IFunBS;
import com.qfw.common.bizservice.permission.impl.FunBSImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.permission.SysDept;
import com.qfw.common.model.permission.SysFunction;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.platform.service.SysFunctionService;

/**
 * 
 * @author: yangyang.wu
 * @version Revision: 0.0.1
 * @Date: 2015年7月28日
 */
@ViewScoped
@ManagedBean(name = "addFunctionBean")
public class AddFunctionBean extends BaseBackingBean {
	private static final long serialVersionUID = 1L;
	private String operateFlag;
	private SysFunction sysFunction=new SysFunction();
	private SysFunction function=new SysFunction();
	private TreeNode selectedTree;
	private TreeNode allFun;
	@ManagedProperty(value = "#{funBS}")
	private IFunBS funBS;
	private Logger log = LogFactory.getInstance().getPlatformLogger();
	@ManagedProperty(value = "#{sysFunctionService}")
	private SysFunctionService sysFunctionService;
	@PostConstruct
	public void init() {
		operateFlag = ViewOper.getRequestParameter("operateFlag");
		Object funId = ViewOper.getSessionTmpAttr("id");
		if (funId != null) {
			try {
				function = sysFunctionService
						.sysFunctionById((Integer) funId);
				if(function.getParentFunId()!=null&&function!=null&&function.getParentFunId()>0)
				{
					sysFunction.setFunName(sysFunctionService.sysFunctionById((Integer) function.getParentFunId()).getFunName());
				}
			} catch (Exception e) {
				log.error("通过ID获取菜单信息异常：", e);
			}
		}
		
		try {
			allFun = funBS.createAllFunTree();
			if("view".equals(operateFlag) || "edit".equals(operateFlag)){
				selectedTree = funBS.findOneFunTree(allFun, function);
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
			log.error("新增页面创建菜单树异常：", e);
		}
	}
public String operate(){
		
		SysUser user = (SysUser) ViewOper.getSession().getAttribute("user");
		if("add".equals(operateFlag)){
			if(checkFunCode(function.getFunCode(),null)){
				alert("英文代号已经存在，请重新输入！");
				function.setFunCode("");
				return null;
			}
			if(checkFunName(function.getFunName(),null)){
				alert("菜单名称已经存在，请重新输入！");
				function.setFunName("");
				return null;
			}
			if(sysFunction == null || sysFunction.getFunId() == null || sysFunction.getFunId().equals(Integer.valueOf(0))){
				function.setFunLevel("2");
				function.setIsLast("1");
				function.setFunType("2");
			} else{
				function.setParentFunId(sysFunction.getFunId());
				function.setFunLevel("2");
				function.setIsLast("1");
				function.setFunType("2");
			}
			this.funBS.save(function);
//			MessagesController.addInfo("用户"+dept.getDeptName()+"保存成功！", "用户"+dept.getDeptName()+"保存成功！");
			executeJS("alert('部门信息["+function.getFunName()+"]保存成功！');closeParMainDialog();");
		}else if("edit".equals(operateFlag)){
			if(checkFunCode(function.getFunCode(),function.getFunId())){
				alert("部门代号已经存在，请重新输入！");
				function.setFunCode("");
			return null;
			}
			if(checkFunName(function.getFunName(),null)){
				alert("菜单名称已经存在，请重新输入！");
				function.setFunName("");
				return null;
			}
			function.setParentFunId(sysFunction.getFunId());
			this.funBS.update(function);
//			MessagesController.addInfo("用户"+dept.getDeptName()+"修改成功！", "用户"+dept.getDeptName()+"修改成功！");
			executeJS("alert('部门信息["+function.getFunName()+"]修改成功！');closeParMainDialog();");
		}
		return null;
	}
    public boolean checkFunName(String funName,Integer funId){
	    List<SysFunction> list = new ArrayList<SysFunction>();
	    try {
		      list = funBS.checkFunName(funName,funId);
	        } catch (BizException e) {
	        	log.error("菜单名称异常：", e);
		        alert("菜单名称异常："+e);
	        }
	   if(list != null && list.size() > 0){
		return true;
	       }else{
		    return false;
	     }
       }

    public boolean checkFunCode(String funCode,Integer funId){
	     List<SysFunction> list = new ArrayList<SysFunction>();
	     try {
		      list = funBS.checkFunCode(funCode,funId);
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
	 public void onDeptSelect(){
		 sysFunction =(SysFunction)selectedTree.getData();
	 }
	public String getOperateFlag() {
		return operateFlag;
	}
	public void setOperateFlag(String operateFlag) {
		this.operateFlag = operateFlag;
	}
	public SysFunction getSysFunction() {
		return sysFunction;
	}
	public void setSysFunction(SysFunction sysFunction) {
		this.sysFunction = sysFunction;
	}
	public Logger getLog() {
		return log;
	}
	public void setLog(Logger log) {
		this.log = log;
	}
	public SysFunctionService getSysFunctionService() {
		return sysFunctionService;
	}
	public void setSysFunctionService(SysFunctionService sysFunctionService) {
		this.sysFunctionService = sysFunctionService;
	}
	public TreeNode getSelectedTree() {
		return selectedTree;
	}
	public void setSelectedTree(TreeNode selectedTree) {
		this.selectedTree = selectedTree;
	}
	public TreeNode getAllFun() {
		return allFun;
	}
	public void setAllFun(TreeNode allFun) {
		this.allFun = allFun;
	}
	public IFunBS getFunBS() {
		return funBS;
	}
	public void setFunBS(IFunBS funBS) {
		this.funBS = funBS;
	}
	public SysFunction getFunction() {
		return function;
	}
	public void setFunction(SysFunction function) {
		this.function = function;
	}
	
    
}
