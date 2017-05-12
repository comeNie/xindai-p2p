package com.qfw.common.converter;

import java.math.BigDecimal;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.qfw.common.util.NumberUtils;



public class PercentConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if(arg2 != null && !arg2.isEmpty()){
			BigDecimal bd = new BigDecimal(arg2);
			return bd.divide(new BigDecimal("100"));
		}
		return arg2;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if(arg2!=null){
			if(arg2 instanceof BigDecimal){
				int scale = ((BigDecimal) arg2).scale();
				if(scale>2){
					scale -= 2;
				}
				return ((BigDecimal) arg2).multiply(new BigDecimal("100")).setScale(scale).toPlainString();
			}
		}
		return String.valueOf(arg2);
	}

	

}
