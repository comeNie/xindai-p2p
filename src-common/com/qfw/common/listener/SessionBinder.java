
package com.qfw.common.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.qfw.common.bizservice.permission.IUserBS;
import com.qfw.common.bizservice.permission.impl.LoginInfo;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.ApplicationContextUtil;
import com.qfw.common.util.web.ViewOper;

public class SessionBinder implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent arg0) {
		//System.out.println("create Session id = "+arg0.getSession().getId());
	}

	public void sessionDestroyed(HttpSessionEvent arg0) {
		HttpSession session = arg0.getSession();
		SysUser user = (SysUser) session.getAttribute("user");
		if(user != null){
			IUserBS userBS = (IUserBS) ApplicationContextUtil.getContext().getBean("userBS");
			LoginInfo.loginSession.remove(session.getId());
			userBS.updateUserOfLoginOutTime(user);
			session.removeAttribute("user");
		}
		session.invalidate();
	}
}
