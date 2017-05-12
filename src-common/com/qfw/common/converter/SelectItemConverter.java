package com.qfw.common.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.qfw.common.model.permission.SysFunction;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.web.SelectItemUtil;



public class SelectItemConverter implements Converter {
	private String codeType;

	
	public SelectItemConverter(String codeType) {
		this.codeType = codeType;
	}

	public SelectItemConverter(){
		
	}
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		return SelectItemUtil.getDictValue(codeType, arg2);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return SelectItemUtil.getDictKey(codeType, String.valueOf(arg2));
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	
	

}
