package com.qfw.model.vo.bussparams;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.qfw.bizservice.bussparams.IParameterInfoBS;
import com.qfw.common.model.SysParameter;
import com.qfw.common.util.StringUtils;

public class LazyParameterDataModel extends LazyDataModel<SysParameter>{
	
	private static final long serialVersionUID = 1L;
	private List<SysParameter> parameterList;	
	private IParameterInfoBS parameterInfoBS;
	private SysParameter searchParameterVO;
	
	public LazyParameterDataModel(SysParameter searchParameterVO, IParameterInfoBS parameterInfoBS){
		this.searchParameterVO = searchParameterVO;
		this.parameterInfoBS = parameterInfoBS;
	}

	@Override
	public List<SysParameter> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		try {
			parameterList = this.parameterInfoBS.findParameterInfoPagesByVO(searchParameterVO, first, pageSize);
			setRowCount(this.parameterInfoBS.findParameterInfoCountByVO(searchParameterVO));
		} catch (Exception e) {
			 
		}
		return parameterList;
	}
	
	@Override
	public SysParameter getRowData(String rowKey) {
		if(StringUtils.isEmpty(rowKey)){
			return null;
		}
		for(SysParameter parameterInfoVO : parameterList){
			if(String.valueOf(parameterInfoVO.getParameterId()).equals(rowKey))
				return parameterInfoVO;
		}
		SysParameter parameterInfoVO = new SysParameter();
		try {
			parameterInfoVO = parameterInfoBS.findParameterInfoById(Integer.valueOf(rowKey));
		} catch (Exception e) {
		}
		return parameterInfoVO;
	}
	
	@Override
	public Object getRowKey(SysParameter parameterInfoVO){
		return parameterInfoVO.getParameterId();
	}

	public List<SysParameter> getParameterList() {
		return parameterList;
	}

	public void setParameterList(List<SysParameter> parameterList) {
		this.parameterList = parameterList;
	}

	public IParameterInfoBS getParameterInfoBS() {
		return parameterInfoBS;
	}

	public void setParameterInfoBS(IParameterInfoBS parameterInfoBS) {
		this.parameterInfoBS = parameterInfoBS;
	}

	public SysParameter getSearchParameterVO() {
		return searchParameterVO;
	}

	public void setSearchParameterVO(SysParameter searchParameterVO) {
		this.searchParameterVO = searchParameterVO;
	}

}
