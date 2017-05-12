package com.qfw.common.dao.impl;

import java.util.List;

import com.qfw.common.dao.ICodeDictDAO;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.BussConst;
import com.qfw.common.model.SysBankCode;
import com.qfw.common.model.SysCodeDict;
import com.qfw.common.util.StringUtils;

public class CodeDictDAOImpl extends BaseDAOImpl implements ICodeDictDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<SysCodeDict> findCodeDicts(String codeType) {
		String sql = "from SysCodeDict where codeType = '" + codeType
				+ "' order by codeSort";
		List<SysCodeDict> findByHQL = this.findByHQL(sql);
		return findByHQL;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysCodeDict> findCodeDict(SysCodeDict searchCodeDictVO)
			throws BizException {
		StringBuffer sb = new StringBuffer();
		sb.append("select s.CODE_ID,s.CODE_TYPE,s.CODE_TYPE_NAME,");
		sb.append(" s.CODE_VALUE,s.DISPLAY_VALUE,s.REMARK,s.CODE_SORT");
		sb.append(" from SYS_CODE_DICT s");
		sb.append(" where 1=1");
		if (StringUtils.isNotEmpty(searchCodeDictVO.getCodeType())) {
			sb.append(" and s.PRODUCT_NUM like '%")
					.append(searchCodeDictVO.getCodeType()).append("%'");
		}
		if (StringUtils.isNotEmpty(searchCodeDictVO.getCodeTypeName())) {
			sb.append(" and s.PRODUCT_NAME like '%")
					.append(searchCodeDictVO.getCodeTypeName()).append("%'");
		}
		sb.append(" order by s.PRODUCT_NUM");
		List<SysCodeDict> codeDictList = this.findByHQL(sb.toString());
		return codeDictList;
	}

	@Override
	public SysCodeDict findCodeDictByCodeTypeAndVal(String codeType,
			String codeValue) {
		String sql = "from SysCodeDict where codeType = '" + codeType
				+ "'and codeValue='" + codeValue + "'";
		List<SysCodeDict> codeDictList = this.findByHQL(sql);
		if (null != codeDictList && !codeDictList.isEmpty()) {
			return codeDictList.get(0);
		}
		return null;
	}

	@Override
	public List<SysCodeDict> findAllCodeDicts() {
		String sql = "from SysCodeDict where 1=1 ";
		List<SysCodeDict> codeDictList = this.findByHQL(sql);
		return codeDictList;
	}
	
	public List<SysBankCode> findBankCode() {
		String sql = "from SysBankCode where 1=1 ";
		List<SysBankCode> bankCodeList = findByHQL(sql);
		return bankCodeList;
	}
	
	public SysBankCode findBankCodeByType(String codeType) {
		String sql = " from SysBankCode where codeValue = '"+codeType+"' and enable = '"+BussConst.YES_FLAG+"' ";
		List<SysBankCode> list = this.findByHQL(sql);
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
}
