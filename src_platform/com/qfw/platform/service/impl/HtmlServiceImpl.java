package com.qfw.platform.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.qfw.platform.cache.CacheManager;
import com.qfw.platform.service.IHtmlService;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 生成静态文件服务类
 * 
 * @author Administrator
 * 
 */
@Service("htmlService")
public class HtmlServiceImpl implements IHtmlService {

	@Resource(name = "platformFreemakerConfigBean")
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@Resource(name = "cacheManager")
	private CacheManager cacheManager;

	public void buildHtml(String templateFilePath, String htmlFilePath,
			Map<String, Object> data) {
		try {
			WebApplicationContext webApplicationContext = ContextLoader
					.getCurrentWebApplicationContext();
			ServletContext servletContext = webApplicationContext
					.getServletContext();
			Configuration configuration = freeMarkerConfigurer
					.getConfiguration();
			Template template = configuration.getTemplate(templateFilePath);
			File htmlFile = new File(servletContext.getRealPath(htmlFilePath));
			File htmlDirectory = htmlFile.getParentFile();
			if (!htmlDirectory.exists()) {
				htmlDirectory.mkdirs();
			}
			Writer out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(htmlFile), "UTF-8"));
			template.process(data, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			 
		}
	}

	// 获取公共数据
	public Map<String, Object> getCommonData() {
		Map<String, Object> commonData = new HashMap<String, Object>();
		WebApplicationContext webApplicationContext = ContextLoader
				.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext
				.getServletContext();
		commonData.put("base", servletContext.getContextPath());
		commonData
				.put("topNavigationList", cacheManager.getTopNavigationList());
		commonData.put("bottomNavigationList",
				cacheManager.getBottomNavigationList());
		commonData.put("friendlinkList", cacheManager.getFriendLinkList());
		commonData.put("carouselList", cacheManager.getCarouselList());
		return commonData;
	}

	/**
	 * 生成首页
	 */
	public void indexBuildHtml() {
		String templateFilePath = "/platform/index.ftl";
		String htmlFilePath = "/html/index.html";
		buildHtml(templateFilePath, htmlFilePath, getCommonData());
	}

	@Override
	public void loginBuildHtml() {
		// TODO Auto-generated method stub

	}

	@Override
	public void errorPageBuildHtml() {
		// TODO Auto-generated method stub

	}

	@Override
	public void errorPageAccessDeniedBuildHtml() {
		// TODO Auto-generated method stub

	}

	@Override
	public void errorPage500BuildHtml() {
		// TODO Auto-generated method stub

	}

	@Override
	public void errorPage404BuildHtml() {
		// TODO Auto-generated method stub

	}

	@Override
	public void errorPage403BuildHtml() {
		// TODO Auto-generated method stub

	}

	@Override
	public void baseJavascriptBuildHtml() {
		// TODO Auto-generated method stub

	}
}