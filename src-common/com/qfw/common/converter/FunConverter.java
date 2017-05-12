package com.qfw.common.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.qfw.common.model.permission.SysFunction;



public class FunConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		// TODO Auto-generated method stub
		SysFunction fun = new SysFunction();
		fun.setFunId(Integer.valueOf(arg2));
		return fun;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		// TODO Auto-generated method stub
		return String.valueOf(arg2);
	}

	

}
