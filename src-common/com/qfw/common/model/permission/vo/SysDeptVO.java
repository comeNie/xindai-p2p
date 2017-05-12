package com.qfw.common.model.permission.vo;

import com.qfw.common.model.permission.SysDept;

public class SysDeptVO extends SysDept{

	private String parentDeptName;

	public String getParentDeptName() {
		return parentDeptName;
	}

	public void setParentDeptName(String parentDeptName) {
		this.parentDeptName = parentDeptName;
	}
	
	
}
