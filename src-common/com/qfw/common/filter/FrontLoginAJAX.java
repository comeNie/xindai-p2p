package com.qfw.common.filter;


import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.qfw.model.AppConst;
import com.qfw.platform.model.vo.LoginInfo;


public class FrontLoginAJAX implements HandlerInterceptor {
	

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		
		////System.out.println("come on here1");
        HttpSession session = request.getSession();
        LoginInfo loginInfo  = (LoginInfo) session.getAttribute(AppConst.LOGIN_INFO_SESSION);
        ////System.out.println();
    
		if (null ==  loginInfo)//判断session里是否有用户信息  
	      {  
			//如果是ajax请求响应头会有，x-requested-with；  
		   //response.setContentType("text/plain" );
		   ////System.out.println("come on here2"+request.getHeader("x-requested-with"));
	       if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest"))
	       { 
	    	 ////System.out.println("come on here  wo love you 3333");
	    	 response.setStatus(600);
	         response.setHeader("sessionstatus", "timeout");//在响应头设置session状态  	         
	         PrintWriter out  = response.getWriter();
	         out.print("sessionOut");
	         out.flush();
	         out.close();
	         
	        return false;  
	      }
	     }
	     return true;  
	   }
	}
 
