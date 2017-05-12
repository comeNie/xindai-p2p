package com.qfw.common.bizservice.impl;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qfw.common.bizservice.ICodeDictBS;
import com.qfw.common.dao.ICodeDictDAO;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.SysBankCode;
import com.qfw.common.model.SysCodeDict;
import com.qfw.common.util.StringUtils;

@Service("codeDictBS")
public class CodeDictBSImpl extends BaseServiceImpl implements ICodeDictBS {
	@Autowired
	private ICodeDictDAO codeDictDAO;
	private Logger log = LogFactory.getInstance().getBusinessLogger();

	@Override
	public List<SelectItem> getSelectItems(String codeType) {
		List<SysCodeDict> codeDicts = codeDictDAO.findCodeDicts(codeType);
		if (codeDicts != null && !codeDicts.isEmpty()) {
			List<SelectItem> items = new ArrayList<SelectItem>();
			for (SysCodeDict dict : codeDicts) {
				items.add(new SelectItem(dict.getCodeValue(), dict
						.getDisplayValue()));
			}
			return items;
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public List<SysCodeDict> findCodeDict(SysCodeDict searchCodeDictVO)
			throws BizException {
		return this.codeDictDAO.findCodeDict(searchCodeDictVO);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysCodeDict> findCodeDictPagesByVO(
			SysCodeDict searchCodeDictVO, int first, int pageSize) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select s.CODE_ID,s.CODE_TYPE,s.CODE_TYPE_NAME,");
			sb.append(" s.CODE_VALUE,s.DISPLAY_VALUE,s.REMARK,s.CODE_SORT");
			sb.append(" from SYS_CODE_DICT s");
			sb.append(" where 1=1");
			if (StringUtils.isNotEmpty(searchCodeDictVO.getCodeType())) {
				sb.append(" and s.CODE_TYPE like '%")
						.append(searchCodeDictVO.getCodeType()).append("%'");
			}
			if (StringUtils.isNotEmpty(searchCodeDictVO.getCodeTypeName())) {
				sb.append(" and s.CODE_TYPE_NAME like '%")
						.append(searchCodeDictVO.getCodeTypeName())
						.append("%'");
			}
			return commonQuery.findBySQLByPages(sb.toString(), first, pageSize,
					SysCodeDict.class);
		} catch (Exception e) {
			 
		}
		return null;
	}

	@Override
	public int findCodeDictCountByVO(SysCodeDict searchCodeDictVO) {
		StringBuilder sb = new StringBuilder();
		sb.append("select count(1)");
		sb.append(" from SYS_CODE_DICT s");
		sb.append(" where 1=1");
		if (StringUtils.isNotEmpty(searchCodeDictVO.getCodeType())) {
			sb.append(" and s.CODE_TYPE like '%")
					.append(searchCodeDictVO.getCodeType()).append("%'");
		}
		if (StringUtils.isNotEmpty(searchCodeDictVO.getCodeTypeName())) {
			sb.append(" and s.CODE_TYPE_NAME like '%")
					.append(searchCodeDictVO.getCodeTypeName()).append("%'");
		}
		return this.commonQuery.findCountBySQL(sb, null);
	}

	@Override
	public String getDisPlayNameByCodeTypeAndVal(String codeType,
			String codeValue) {
		SysCodeDict sysCodeDict = codeDictDAO.findCodeDictByCodeTypeAndVal(
				codeType, codeValue);
		if (null != sysCodeDict) {
			return sysCodeDict.getDisplayValue();
		}
		return null;
	}

	@Override
	public List<SysCodeDict> getSysCodeDictListByCodeType(String codeType) {
		if (StringUtils.isEmpty(codeType))
			return null;
		List<SysCodeDict> sysCodeDictList = codeDictDAO.findCodeDicts(codeType);
		return sysCodeDictList;
	}

	public SysCodeDict getSysCodeDictById(Integer id) throws BizException {
		return (SysCodeDict) this.codeDictDAO.findById(SysCodeDict.class, id);
	}

	@Override
	public SysCodeDict findCodeDictById(Integer id) {
		this.codeDictDAO.findById(SysCodeDict.class, id);
		return null;
	}

	public ICodeDictDAO getCodeDictDAO() {
		return codeDictDAO;
	}

	public void setCodeDictDAO(ICodeDictDAO codeDictDAO) {
		this.codeDictDAO = codeDictDAO;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	@Override
	public List<SysCodeDict> findAllSysCodeDicts() {
		return this.codeDictDAO.findAllCodeDicts();
	}
	
	public List<SysBankCode> findBankCodes() {
		return this.codeDictDAO.findBankCode();
	}
	
	public SysBankCode findBankCodeByType(String codeType){
		return this.codeDictDAO.findBankCodeByType(codeType);
	}
	
	
}
