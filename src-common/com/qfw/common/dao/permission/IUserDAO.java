package com.qfw.common.dao.permission;

import java.util.Set;

import com.qfw.common.dao.IBaseDAO;
import com.qfw.common.model.permission.SysFunction;
import com.qfw.common.model.permission.SysUser;

public interface IUserDAO extends IBaseDAO {
	/**
	 * 获取用户所关联的功能菜单
	 * @param user
	 * @return
	 */
	//public Set<SysFunction> getFuns(SysUser user);
	
	SysUser registerUser(SysUser user);

}
