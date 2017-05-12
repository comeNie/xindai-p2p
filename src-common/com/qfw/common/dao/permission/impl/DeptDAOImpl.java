package com.qfw.common.dao.permission.impl;

import java.util.List;

import com.qfw.common.dao.impl.BaseDAOImpl;
import com.qfw.common.dao.permission.IDeptDAO;
import com.qfw.common.model.permission.SysDept;

public class DeptDAOImpl extends BaseDAOImpl implements IDeptDAO {

	@Override
	public List<SysDept> findChildren(SysDept dept) {
		List<SysDept> deptChildren = this.findByHQL("from SysDept where parentDeptId = '"+dept.getDeptId()+"'");
		return deptChildren;
	}

}
