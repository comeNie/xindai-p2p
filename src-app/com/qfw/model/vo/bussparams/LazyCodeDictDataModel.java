package com.qfw.model.vo.bussparams;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.qfw.common.bizservice.ICodeDictBS;
import com.qfw.common.model.SysCodeDict;
import com.qfw.common.util.StringUtils;

public class LazyCodeDictDataModel extends LazyDataModel<SysCodeDict>{
	
	private static final long serialVersionUID = 1L;
	private List<SysCodeDict> codeDictList;	
	private ICodeDictBS codeDictBS;
	private SysCodeDict searchCodeDictVO;
	
	public LazyCodeDictDataModel(SysCodeDict searchCodeDictVO, ICodeDictBS codeDictBS){
		this.searchCodeDictVO = searchCodeDictVO;
		this.codeDictBS = codeDictBS;
	}

	@Override
	public List<SysCodeDict> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		try {
			codeDictList = this.codeDictBS.findCodeDictPagesByVO(searchCodeDictVO, first, pageSize);
			setRowCount(this.codeDictBS.findCodeDictCountByVO(searchCodeDictVO));
		} catch (Exception e) {
			 
		}
		return codeDictList;
	}
	
	@Override
	public SysCodeDict getRowData(String rowKey) {
		if(StringUtils.isEmpty(rowKey)){
			return null;
		}
		for(SysCodeDict sysCodeDictVO : codeDictList){
			if(String.valueOf(sysCodeDictVO.getCodeId()).equals(rowKey))
				return sysCodeDictVO;
		}
		SysCodeDict sysCodeDictVO = new SysCodeDict();
		try {
			sysCodeDictVO = codeDictBS.findCodeDictById(Integer.valueOf(rowKey));
		} catch (Exception e) {
		}
		return sysCodeDictVO;
	}
	
	@Override
	public Object getRowKey(SysCodeDict sysCodeDictVO){
		return sysCodeDictVO.getCodeId();
	}

	public List<SysCodeDict> getCodeDictList() {
		return codeDictList;
	}

	public void setCodeDictList(List<SysCodeDict> codeDictList) {
		this.codeDictList = codeDictList;
	}

	public ICodeDictBS getCodeDictBS() {
		return codeDictBS;
	}

	public void setCodeDictBS(ICodeDictBS codeDictBS) {
		this.codeDictBS = codeDictBS;
	}

	public SysCodeDict getSearchCodeDictVO() {
		return searchCodeDictVO;
	}

	public void setSearchCodeDictVO(SysCodeDict searchCodeDictVO) {
		this.searchCodeDictVO = searchCodeDictVO;
	}

}
