package com.qfw.common.bizservice;

import java.util.List;

import javax.faces.model.SelectItem;

import com.qfw.common.exception.BizException;
import com.qfw.common.model.SysBankCode;
import com.qfw.common.model.SysCodeDict;

public interface ICodeDictBS extends IBaseService {

	/**
	 * 通过下拉框类型获取下拉框
	 * @param codeType
	 * @return
	 */
	public List<SelectItem> getSelectItems(String codeType);

	public List<SysCodeDict> findCodeDictPagesByVO(
			SysCodeDict searchCodeDictVO, int first, int pageSize);

	public int findCodeDictCountByVO(SysCodeDict searchCodeDictVO);

	public SysCodeDict findCodeDictById(Integer valueOf);
	
	public List<SysCodeDict> findCodeDict (SysCodeDict searchCodeDictVO) throws BizException;
	
	public SysCodeDict getSysCodeDictById(Integer id) throws BizException;

	public String getDisPlayNameByCodeTypeAndVal(String codeType,
			String codeValue);
	
	public List<SysCodeDict> getSysCodeDictListByCodeType(String codeType);
	
	public List<SysCodeDict> findAllSysCodeDicts();
	
	public List<SysBankCode> findBankCodes();
	
	public SysBankCode findBankCodeByType(String codeType);
}
