package com.qfw.dao.bussparams;

import java.util.List;

import com.qfw.common.dao.IBaseDAO;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.SysMessageTemplate;

public interface IMessageInfoDAO extends IBaseDAO{
	
	public List<SysMessageTemplate> findMessageInfo(SysMessageTemplate searchMessageInfoVO) throws BizException;
 
}
