package com.qfw.common.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.qfw.common.model.permission.SysFunction;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.web.SelectItemUtil;



public class IntegerValueConverter implements Converter {
	private String value;

	
	public IntegerValueConverter(String value) {
		this.value = value;
	}

	public IntegerValueConverter(){
		
	}
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		return Integer.valueOf(arg2);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if(arg2 == null){
			return value;
		}
		return String.valueOf(arg2);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
