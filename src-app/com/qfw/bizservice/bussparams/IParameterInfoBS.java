package com.qfw.bizservice.bussparams;

import java.util.List;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.SysParameter;

public interface IParameterInfoBS extends IBaseService {
	
	public List<SysParameter> findParameterInfoPagesByVO (SysParameter searchParameterVO, int first, int pageSize);
	
	public int findParameterInfoCountByVO (SysParameter searchParameterVO);	
	
	public List<SysParameter> checkParameterCode(String parameterCode,Integer id) throws BizException;
	
	public List<SysParameter> checkParameterValue(String parameterValue,Integer id)throws BizException;
	
	public SysParameter getSysParameterById(Integer id) throws BizException;
	
	public SysParameter findParameterInfoById(Integer id) throws BizException;
	
}
