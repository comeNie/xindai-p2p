package com.qfw.common.bizservice.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.qfw.common.bizservice.IParamBS;
import com.qfw.common.util.StringUtils;

@Service("paramBS")
public class ParamBSImpl extends BaseServiceImpl implements IParamBS {

	private Map<String,String> params = new HashMap<String, String>();
	@Override
	public String getParam(String parameterCode) {
		if(StringUtils.isNotEmpty(parameterCode)){
			String value = params.get(parameterCode);
			if(StringUtils.isNotEmpty(value)){
				return value;
			}else{
				String sql = "select parameter_value as parameterValue  from SYS_PARAMETER where parameter_code = ? ";
				List<Map<String, String>> list = this.getCommonQuery().findBySQL(sql, new String[]{parameterCode});
				for(Map<String, String> map : list){
					value = map.get("parameterValue");
					params.put(parameterCode, value);
					return value;
				}
			}			
		}		
		return null;
	}
	@Override
	public void refreshParam(){
		String sql = "select parameter_value as parameterValue,parameter_code as parameterCode   from SYS_PARAMETER ";
		List<Map<String, String>> list=this.getCommonQuery().findBySQL(sql);
		for(Map<String, String> map : list){
			String code = map.get("parameterCode");
			String value = map.get("parameterValue");
			params.put(code, value);
		}
	}
	
	public Map<String, String> getParams() {
		return params;
	}
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
	
	
}
