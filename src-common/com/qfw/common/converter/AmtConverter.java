package com.qfw.common.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.qfw.common.util.NumberUtils;



public class AmtConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if(arg2 != null && !arg2.isEmpty()){
			return NumberUtils.parseNum(arg2);
		}
		return arg2;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if(arg2!=null){
			if ("0.00".equals(arg2.toString())) {
				return arg2.toString();
			}
			return NumberUtils.format2(arg2);
		}
		return String.valueOf(arg2);
	}

	

}
