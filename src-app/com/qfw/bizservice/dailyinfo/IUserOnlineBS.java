package com.qfw.bizservice.dailyinfo;

import java.util.List;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.model.bo.BizUserOnlineBO;

public interface IUserOnlineBS extends IBaseService {
	
	public BizUserOnlineBO findBizUserOnlineBOById(Integer id) throws BizException;
	
	public List<BizUserOnlineBO> findUserOnlinePagesByVO (BizUserOnlineBO searchVO, int first, int pageSize);
	
	public int findUserOnlineCountByVO (BizUserOnlineBO searchVO);
}
