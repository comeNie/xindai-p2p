package com.qfw.platform.utils;

/**
 * 存放静态常量
 * 
 * @author Administrator
 * 
 */
public class Constants {

	/**
	 * 邮箱验证的主题
	 */
	public static final String MESSAGE_EMAIL_SUBJECT = "平台在线注册激活邮件";

	/**
	 * 发送邮箱地址
	 */
	public static final String MESSAGE_EMAIL_FROM = "service@pmzaixian.net";

	/**
	 * 邮件的编码
	 */
	public static final String MESSAGE_EMAIL_ENCODE = "UTF-8";

	/**
	 * 注册验证的邮件模板
	 */
	public static final String REGISTER_EMAIL_TEMPLATE = "/common/registerEmailAuth.ftl";

	/**
	 * 域名
	 */
	public static final String BASE_DOMAIN = "http://www.pmzaixian.net";

	/**
	 * 注册邮件激活的地址
	 */
	public static final String REGISTER_AUTH_URL = BASE_DOMAIN
			+ CommonUtil.getBasePath() + "/register/authEmail.do";
	
	/**
	 * 解绑与绑定邮箱
	 */
	public static final String BING_AUTH_URL = BASE_DOMAIN
			+ CommonUtil.getBasePath() + "/register/bingingEmail.do";
	/**
	 * 注册短信激活码
	 */
	public static final String REGISTER_AUTH_MESSAGE = "您的验证码：";

	/**
	 * 短信验证码的有效期
	 */
	public static final int REGISTER_AUTH_MESSAGE_TIME = 5 * 60 * 1000;

}
