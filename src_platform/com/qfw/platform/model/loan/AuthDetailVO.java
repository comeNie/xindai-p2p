package com.qfw.platform.model.loan;

import java.io.Serializable;
import java.util.Date;

/**
 * 认证信息
 * @author Think
 *
 */
public class AuthDetailVO implements Serializable{

	private static final long serialVersionUID = 8841603758706289438L;
	
	private String authItemCd;// 认证项目
	private String authStatusCd;// 认证状态
	private Date sysCreateTime;// 认证时间

	 

	public String getAuthItemCd() {
		return authItemCd;
	}

	public void setAuthItemCd(String authItemCd) {
		this.authItemCd = authItemCd;
	}

	public String getAuthStatusCd() {
		return authStatusCd;
	}

	public void setAuthStatusCd(String authStatusCd) {
		this.authStatusCd = authStatusCd;
	}

	public Date getSysCreateTime() {
		return sysCreateTime;
	}

	public void setSysCreateTime(Date sysCreateTime) {
		this.sysCreateTime = sysCreateTime;
	}

}