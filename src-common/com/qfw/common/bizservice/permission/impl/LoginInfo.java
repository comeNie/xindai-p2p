package com.qfw.common.bizservice.permission.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class LoginInfo {
	public static Map<String,HttpSession> loginSession = new HashMap<String, HttpSession>();
}
