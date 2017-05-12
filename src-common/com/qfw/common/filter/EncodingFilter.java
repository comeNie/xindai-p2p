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

public class EncodingFilter implements Filter {
	/**
	 * 编码过滤器
	 */

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request1 = (HttpServletRequest) request;
		
			String uri = request1.getRequestURI();
			//System.out.println("uri==="+uri);
			if (request1.getHeader("x-requested-with") != null
				&& request1.getHeader("x-requested-with").equalsIgnoreCase(
						"XMLHttpRequest") && !uri.endsWith(".do") ) {
			HttpSession session = request1.getSession(false);
			if(session == null || session.isNew()){
				//((HttpServletResponse) response).setHeader("sessionstatus",	"sessionOut");
				//response.getWriter().print("sessionOut");
				((HttpServletResponse) response).sendError(403);
				// 在响应头设置session状态
				return;
			}
			
		}

		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		chain.doFilter(request, response);

	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
