package com.qfw.platform.service;

import java.util.List;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.model.permission.SysFunction;


/**
 * 
 * @author: yangyang.wu
 * @version Revision: 0.0.1
 * @Date: 2015年7月26日
 */
public interface SysFunctionService extends IBaseService{
	public List<SysFunction> sysFuctionPagesByVO(
			SysFunction sysFuctionVO, int first, int pageSize);
	public int sysFuctionCountByVO(SysFunction sysFuctionVO);
	public SysFunction sysFunctionById(int id);
  
}
