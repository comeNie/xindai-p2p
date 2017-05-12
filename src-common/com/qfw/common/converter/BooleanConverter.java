package com.qfw.common.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.qfw.common.model.permission.SysFunction;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.web.SelectItemUtil;



public class BooleanConverter implements Converter {
	
	private String trueValue;
	private String falseValue;
	
	
	public BooleanConverter(){
		
	}
	
	public BooleanConverter(String trueValue, String falseValue) {
		super();
		this.trueValue = trueValue;
		this.falseValue = falseValue;
	}


	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		//System.out.println("objecct"+arg2);
		return arg2;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		//System.out.println("ddd"+arg2);
		return String.valueOf(arg2);
	}
	
	public String getTrueValue() {
		return trueValue;
	}

	public void setTrueValue(String trueValue) {
		this.trueValue = trueValue;
	}

	public String getFalseValue() {
		return falseValue;
	}

	public void setFalseValue(String falseValue) {
		this.falseValue = falseValue;
	}
	

}
