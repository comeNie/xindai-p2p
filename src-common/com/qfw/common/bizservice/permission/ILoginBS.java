package com.qfw.common.bizservice.permission;

import com.qfw.common.exception.BizException;
import com.qfw.common.model.permission.SysUser;

public interface ILoginBS {
	/**
	 * 用户登陆
	 * @param user
	 * @return
	 */
	public boolean login(SysUser user) throws BizException;
	
	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	public SysUser userRegister(SysUser user);
	/**
	 * 判断用户是否登录
	 * @return
	 */
	public boolean isLogin();
	/**
	 * 用户退出
	 */
	public void loginOut();
}
