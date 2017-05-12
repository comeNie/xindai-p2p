package com.qfw.common.model.permission.vo;

import com.qfw.common.model.permission.SysRole;

public class SysRoleVO extends SysRole{
	//部门名称
	public String deptName;

	public SysRoleVO() {
		super();
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
}
