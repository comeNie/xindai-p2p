package com.qfw.bizservice.bussparams;

import java.util.List;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.SysMessageTemplate;

public interface IMessageInfoBS extends IBaseService {

	public List<SysMessageTemplate> findMessageInfo (SysMessageTemplate searchMessageVO) throws BizException;
	
	public List<SysMessageTemplate> findMessageInfoPagesByVO (SysMessageTemplate searchMessageVO, int first, int pageSize);
	
	public int findMessageInfoCountByVO (SysMessageTemplate searchMessageVO);	
	
	public List<SysMessageTemplate> checkMessageCode(String msgTmpCode,Integer id) throws BizException;
	
	public List<SysMessageTemplate> checkMessageName(String msgTmpName,Integer id)throws BizException;
	
	public SysMessageTemplate getSysMessageTemplateById(Integer id) throws BizException;
	
	public SysMessageTemplate findMessageInfoById(Integer id) throws BizException;
}
