/**
 * Copyright 2015 Software innovation and R & D center. All rights reserved.
 * File Name: SysFunctionImpl.java
 * Encoding UTF-8
 * Version: 0.0.1
 * History:	2015年7月26日
 */
package com.qfw.platform.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.dao.ICommonQuery;
import com.qfw.common.model.permission.SysFunction;
import com.qfw.platform.dao.FunctionDao;
import com.qfw.platform.service.SysFunctionService;

/**
 * 
 * @author: yangyang.wu(改成自己的名字)
 * @version Revision: 0.0.1
 * @Date: 2015年7月26日
 */
@Service("sysFunctionService")
public class SysFunctionServiceImpl extends BaseServiceImpl implements
SysFunctionService{

	@Autowired
	private ICommonQuery commonQuery;
	@Autowired
    private FunctionDao functionDAO;
	@Override
	public List<SysFunction> sysFuctionPagesByVO(SysFunction sysFuctionVO,
			int first, int pageSize) {
		StringBuilder buffer = new StringBuilder(
				"select * from sys_function sf");
		buffer.append(VO2Condition(sysFuctionVO));
		return commonQuery.findBySQLByPages(buffer.toString(), first, pageSize,
				SysFunction.class);
	}


	@Override
	public int sysFuctionCountByVO(SysFunction sysFuctionVO) {
		StringBuilder buffer = new StringBuilder(
				"SELECT COUNT(FUN_ID) FROM sys_function sf ");
		buffer.append(VO2Condition(sysFuctionVO));
		return commonQuery.findCountBySQL(buffer, null);
	}


	@Override
	public SysFunction sysFunctionById(int id) {
		return (SysFunction) functionDAO.findById(SysFunction.class,
				id);
	}

	
	private String VO2Condition(SysFunction sysFuctionVO) {
		StringBuilder buffer = new StringBuilder(" where 1=1 ");
		if (null != sysFuctionVO) {
			if (StringUtils.isNotEmpty(sysFuctionVO.getFunName())) {
				buffer.append(" and sf.FUN_NAME like '%")
						.append(sysFuctionVO.getFunName()).append("%'");
			}
			if (StringUtils.isNotEmpty(sysFuctionVO.getFunStatus())) {
				buffer.append(" and sf.FUN_STATUS like '%")
						.append(sysFuctionVO.getFunStatus()).append("%'");
			}
		}
		return buffer.toString();
	}

}
