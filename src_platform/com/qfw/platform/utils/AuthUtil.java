package com.qfw.platform.utils;

import java.util.Date;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

import com.qfw.common.util.DateUtils;

/**
 * 权限验证
 * 
 * @author Administrator
 * 
 */
public class AuthUtil {

	/**
	 * 权限验证的KEY
	 */
	public static String AUTH_KEY = "pmzaixian.net";
	
	public static String AUTH_SPLIT = "||";

	/**
	 * 验证方式(username+mode+email+key)
	 * 
	 * @param userName
	 * @return
	 */
	public static String getAuthByUserName(String userName, String mode,String email) {
		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(mode))
			return null;
		String auth = "";
		String dateStr = DateUtils.getYmdhms(new Date());
		////System.out.println("dateStr=="+dateStr);
		try {
			auth = ApiPass.getInstance().encrypt(userName + AUTH_SPLIT +mode + AUTH_SPLIT+ email+ AUTH_SPLIT+ AUTH_KEY + AUTH_SPLIT +dateStr);
			////System.out.println("auth==="+auth);
		} catch (Exception e) {
			 
		}
		return auth;
	}
	
	/**
	 * 获取短信验证码
	 * 
	 * @return
	 */
	public static String getAuthMessage() {
		String[] digits = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };
		Random rnum = new Random(new Date().getTime());

		for (int i = 0; i < digits.length; i++) {
			int index = Math.abs(rnum.nextInt()) % 10;
			String tmpDigit = digits[index];
			digits[index] = digits[i];
			digits[i] = tmpDigit;
		}

		String returnStr = digits[0];
		for (int i = 1; i < 6; i++) {
			returnStr = digits[i] + returnStr;
		}
		return returnStr;
	}

	/**
	 * 认证中的信息
	 * @param auth
	 * @return
	 */
	public static String getFieldByAuth(String auth,int i) {
		if (StringUtils.isEmpty(auth))
			return null;

		String[] values;
		try {
			values = ApiPass.getInstance().decrypt(auth).split("\\|\\|");
			
			if (i > values.length - 1 || i < 0){
				return null;
			}
				
			
			if (null != values && values.length == 5) {
				////System.out.println("values["+i+"]=="+values[i]);
				return values[i];
			}
		} catch (Exception e) {
			 
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.err.println(getAuthMessage());
	}
}