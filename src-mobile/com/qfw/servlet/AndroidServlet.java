package com.qfw.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.qfw.common.util.ApplicationContextUtil;
import com.qfw.handle.IHandler;
import com.qfw.model.AppConst;
import com.qfw.model.HandlerResponse;

/**
 * 移动端调用servlet
 */
public class AndroidServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String END1 = "android";

	private static final String END2 = "android/";

	protected static final Logger logger = Logger.getLogger(AndroidServlet.class);
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/plain;charset=UTF-8");
		String uri = request.getRequestURI();
		if (uri.endsWith(END1) || uri.endsWith(END2)) {
			return;
		}
		PrintWriter writer = response.getWriter();
		HandlerResponse handlerResponse = new HandlerResponse();
		String clazz = uri.substring(uri.lastIndexOf("/") + 1);
		try {
			if (ApplicationContextUtil.getContext().containsBean(clazz)) {
				IHandler handler = (IHandler) ApplicationContextUtil.getContext().getBean(clazz);
				handler.doHandler(request, response, handlerResponse);
			} else {
				logger.error("不存在bean名为[" + clazz + "].");
				handlerResponse.setResponseCode(AppConst.RESP_NOT_FOUND);
				handlerResponse.setResponseObj(null);
			}
		} catch (Exception e) {
			logger.error(e);
			 
			handlerResponse.setResponseCode(AppConst.RESP_FORBIDDEN);
			handlerResponse.setResponseMessage("服务器内部异常.");
			handlerResponse.setResponseObj(null);
		} finally {
			JSONObject json = JSONObject.fromObject(handlerResponse);
			writer.print(json.toString());
			logger.info("响应内容:" + json.toString());
			writer.close();
		}
	}

}
