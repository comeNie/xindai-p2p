package com.qfw.common.dao.permission;

import java.util.List;

import com.qfw.common.dao.IBaseDAO;
import com.qfw.common.model.permission.SysDept;

public interface IDeptDAO extends IBaseDAO {
	/**
	 * 通过当前机构去找子机构
	 * @param detp
	 * @return
	 */
	public List<SysDept> findChildren(SysDept dept);

}
