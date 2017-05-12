package com.qfw.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qfw.common.model.permission.SysUser;


/**
 * 
 * @author lsj
 *
 */
public class FileFilter implements Filter {

	@Override
	public void destroy() {
		
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest =(HttpServletRequest)request;
		//System.out.println("--------------图片请求-----------------");
		//System.out.println(httpRequest.getAuthType());
		//System.out.println(httpRequest.getContextPath());
		//System.out.println(httpRequest.getLocalName());
		//System.out.println(httpRequest.getRequestURI());
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
