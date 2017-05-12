package com.qfw.platform.service;

import java.util.Map;

/**
 * 生成静态页面接口
 * @author Administrator
 *
 */
public interface IHtmlService {
	
	public Map<String, Object> getCommonData();
	
	/**
	 * 根据Freemarker模板文件路径、Map数据生成HTML静态文件
	 * @param templateFilePath 模板文件路径
	 * @param htmlFilePath
	 * @param data
	 */
	public void buildHtml(String templateFilePath, String htmlFilePath, Map<String, Object> data);
	
	/**
	 * 生成baseJavascript文件
	 * 
	 */
	public void baseJavascriptBuildHtml();
	
	/**
	 * 生成首页HTML静态文件
	 * 
	 */
	public void indexBuildHtml();
	
	/**
	 * 生成登录HTML静态文件
	 * 
	 */
	public void loginBuildHtml();
	
	/**
	 * 错误页HTML静态文件
	 */
	public void errorPageBuildHtml();
	
	/**
	 * 权限错误页HTML静态文件
	 */
	public void errorPageAccessDeniedBuildHtml();
	
	/**
	 * 错误页500 HTML静态文件
	 */
	public void errorPage500BuildHtml();
	
	/**
	 * 错误页404 HTML静态文件
	 */
	public void errorPage404BuildHtml();
	
	/**
	 * 错误页403 HTML静态文件
	 */
	public void errorPage403BuildHtml();
	
}