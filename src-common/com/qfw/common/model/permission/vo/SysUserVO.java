package com.qfw.common.model.permission.vo;

import com.qfw.common.model.permission.SysUser;

public class SysUserVO extends SysUser {
	
	public String deptIds;
	
	public String deptNames;
	
	public String roleIds;

	public String getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}

	public String getDeptNames() {
		return deptNames;
	}

	public void setDeptNames(String deptNames) {
		this.deptNames = deptNames;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	
	
}
