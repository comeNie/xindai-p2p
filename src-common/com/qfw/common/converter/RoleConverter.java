package com.qfw.common.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.qfw.common.model.permission.SysRole;
import com.qfw.common.util.StringUtils;



public class RoleConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		SysRole role = new SysRole();
		if(StringUtils.isNotEmpty(arg2)){
			// TODO Auto-generated method stub
			String[] strs = arg2.split("_\\$_",3);
			if(strs.length != 3){
				return role;
			}
			role.setRoleId(Integer.valueOf(strs[0]));
			role.setRoleCode(strs[1]);
			role.setRoleName(strs[2]);
		}
		
		return role;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		// TODO Auto-generated method stub
		return String.valueOf(arg2);
	}

	

}
