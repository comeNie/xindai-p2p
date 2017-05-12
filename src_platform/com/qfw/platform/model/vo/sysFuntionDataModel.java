/**
 * Copyright 2015 Software innovation and R & D center. All rights reserved.
 * File Name: sysFuntionDataModel.java
 * Encoding UTF-8
 * Version: 0.0.1
 * History:	2015年7月26日
 */
package com.qfw.platform.model.vo;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.qfw.common.model.permission.SysFunction;
import com.qfw.platform.service.SysFunctionService;

/**
 * 
 * @author: xiaoming.wang(改成自己的名字)
 * @version Revision: 0.0.1
 * @Date: 2015年7月26日
 */
public class sysFuntionDataModel extends LazyDataModel<SysFunction>{

	private static final long serialVersionUID = 1L;
	private SysFunction sysFuctionVO;
	private SysFunctionService sysFunctionService;
	private List<SysFunction> sysFuctionList;


	public  sysFuntionDataModel(SysFunction sysFuctionVO,
			SysFunctionService sysFunctionService) {
		this.sysFuctionVO = sysFuctionVO;
		this.sysFunctionService = sysFunctionService;
	}
	@Override
	public List<SysFunction> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		if (null != sysFunctionService) {
			sysFuctionList = sysFunctionService.sysFuctionPagesByVO(
					sysFuctionVO, first, pageSize);
			setRowCount(sysFunctionService.sysFuctionCountByVO(sysFuctionVO));
		}
		System.out.print(sysFuctionList.size());
		return sysFuctionList;
	}
	@Override
	public SysFunction getRowData(String rowKey) {
		if (StringUtils.isEmpty(rowKey))
			return null;

		for (SysFunction sysFunction : sysFuctionList) {
			if (String.valueOf(sysFunction.getFunId()).equals(rowKey))
				return sysFunction;
		}

		SysFunction sysFunction = new SysFunction();
		try {
			sysFunction = sysFunctionService.sysFunctionById(Integer
					.valueOf(rowKey));
		} catch (Exception e) {
		}
		return sysFunction;
	}
	@Override
	public Object getRowKey(SysFunction sysFuction) {
		if(null != sysFuction){
			return sysFuction.getFunId();
		}
		return null;
	}

	public SysFunctionService getSysFunctionService() {
		return sysFunctionService;
	}
	public void setSysFunctionService(SysFunctionService sysFunctionService) {
		this.sysFunctionService = sysFunctionService;
	}
	public SysFunction getSysFuctionVO() {
		return sysFuctionVO;
	}
	public void setSysFuctionVO(SysFunction sysFuctionVO) {
		this.sysFuctionVO = sysFuctionVO;
	}
	public List<SysFunction> getSysFuctionList() {
		return sysFuctionList;
	}
	public void setSysFuctionList(List<SysFunction> sysFuctionList) {
		this.sysFuctionList = sysFuctionList;
	}

     
}
