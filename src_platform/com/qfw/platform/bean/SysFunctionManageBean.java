/**
 * Copyright 2015 Software innovation and R & D center. All rights reserved.
 * File Name: sysFuctionManageBean.java
 * Encoding UTF-8
 * Version: 0.0.1
 * History:	2015年7月26日
 */
package com.qfw.platform.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;

import com.qfw.common.log.LogFactory;
import com.qfw.common.model.permission.SysFunction;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.platform.model.vo.sysFuntionDataModel;
import com.qfw.platform.service.SysFunctionService;

/**
 * 
 * @author: yangyang.wu
 * @version Revision: 0.0.1
 * @Date: 2015年7月26日
 */
@ViewScoped
@ManagedBean(name = "sysFunctionManagerBean")
public class SysFunctionManageBean extends BaseBackingBean{

	private static final long serialVersionUID = 1L;

	private LazyDataModel<SysFunction> fuctionList;
	
	private SysFunction fucionVO=new SysFunction();
	
	private SysFunction selectFunctionlink;
	
	@ManagedProperty(value = "#{sysFunctionService}")
	private SysFunctionService sysFunctionService;
	
	private Logger log = LogFactory.getInstance().getPlatformLogger();
	@PostConstruct
	public void init() {
		if (null == fuctionList) {
			fuctionList = new sysFuntionDataModel(fucionVO,
					sysFunctionService);
			
		}

	}
	public void search() {
		super.search();
		if (null == fuctionList) {
			fuctionList = new sysFuntionDataModel(fucionVO,
					sysFunctionService);
		}
	}
	public void operate() {
		String operateFlag = ViewOper.getParameter("operateFlag");
		System.out.print(operateFlag);
		if ("view".equalsIgnoreCase(operateFlag)
				|| "edit".equalsIgnoreCase(operateFlag)) {
			ViewOper.getSession().setAttribute("id", selectFunctionlink.getFunId());
		} else if ("delete".equalsIgnoreCase(operateFlag)) {
			String id = String.valueOf(selectFunctionlink.getFunId());
			String sql = "delete from sys_function where FUN_ID = " + id;
			try {
				sysFunctionService.getJdbcTemplate().execute(sql);
				fuctionList.setRowCount(fuctionList.getRowCount() - 1);
			} catch (Exception e) {
				log.error("删除菜单" + selectFunctionlink.getFunName() + "异常：", e);
				alert("删除菜单异常：" + e);
			}
		}
	}
	public LazyDataModel<SysFunction> getFuctionList() {
		return fuctionList;
	}
	public void setFuctionList(LazyDataModel<SysFunction> fuctionList) {
		this.fuctionList = fuctionList;
	}
	public SysFunction getFucionVO() {
		return fucionVO;
	}
	public void setFucionVO(SysFunction fucionVO) {
		this.fucionVO = fucionVO;
	}
	public SysFunction getSelectFunctionlink() {
		return selectFunctionlink;
	}
	public void setSelectFunctionlink(SysFunction selectFunctionlink) {
		this.selectFunctionlink = selectFunctionlink;
	}
	public SysFunctionService getSysFunctionService() {
		return sysFunctionService;
	}
	public void setSysFunctionService(SysFunctionService sysFunctionService) {
		this.sysFunctionService = sysFunctionService;
	}
	public Logger getLog() {
		return log;
	}
	public void setLog(Logger log) {
		this.log = log;
	}


	
}
