package com.qfw.common.dao;

import java.util.List;

import com.qfw.common.exception.BizException;
import com.qfw.common.model.SysBankCode;
import com.qfw.common.model.SysCodeDict;

public interface ICodeDictDAO extends IBaseDAO {
	public List<SysCodeDict> findCodeDicts(String codeType);
	public List<SysCodeDict> findCodeDict(SysCodeDict searchCodeDictVO) throws BizException;
	public SysCodeDict findCodeDictByCodeTypeAndVal(String codeType,String codeValue);
	public List<SysCodeDict> findAllCodeDicts();
	
	public List<SysBankCode> findBankCode();
	
	public SysBankCode findBankCodeByType(String codeType);
}
