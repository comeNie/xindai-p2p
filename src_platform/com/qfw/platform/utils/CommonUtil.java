package com.qfw.platform.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * 公共工具类
 * 
 * @author Administrator
 * 
 */
public class CommonUtil {

	/**
	 * 获取UUID
	 * 
	 * @return
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		return uuid;
	}

	/**
	 * 获取当天的日期字段串
	 * 
	 * @return
	 */
	public static String getDateStr() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		String dateString = simpleDateFormat.format(new Date());
		return dateString;
	}

	/**
	 * 获取真实的路径
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getRealPath(String filePath) {
		WebApplicationContext webApplicationContext = ContextLoader
				.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext
				.getServletContext();
		return servletContext.getRealPath(filePath);
	}

	/**
	 * 获取网站根路径
	 * 
	 * @return
	 */
	public static String getBasePath() {
		WebApplicationContext webApplicationContext = ContextLoader
				.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext
				.getServletContext();
		return servletContext.getContextPath();
	}

	/**
	 * 设置数据
	 * 
	 * @param request
	 * @param response
	 * @param dataMap
	 */
	public static void parseDataMap(HttpServletRequest request,
			Map<String, Object> dataMap) {
		if (null == dataMap || dataMap.isEmpty())
			return;

		for (final String key : dataMap.keySet()) {
			request.setAttribute(key, dataMap.get(key));
		}
	}
}
