package com.qfw.common.converter;

import java.io.UnsupportedEncodingException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;



public class EncodingConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if(arg2!=null){
			try {
				return new String(arg2.getBytes("ISO8859-1"),"utf8");
			} catch (UnsupportedEncodingException e) {
			}
		}
		return arg2;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		// TODO Auto-generated method stub
		return String.valueOf(arg2);
	}

	

}
