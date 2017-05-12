package com.qfw.common.filter;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.qfw.model.AppConst;
import com.qfw.platform.model.vo.LoginInfo;


public class FrontIsLogin implements HandlerInterceptor {
	
	private String loginHtml ;
	
	
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object obj) throws Exception {
		//请求的路径
        String contextPath=request.getContextPath();
        String  url=request.getServletPath().toString();
        // System.out.println("曹操草草===="+url);
        ////System.out.println("url=="+url);
        ////System.out.println("request.getRequestURL()=="+request.getRequestURL());
        HttpSession session = request.getSession();
        LoginInfo loginInfo  = (LoginInfo) session.getAttribute(AppConst.LOGIN_INFO_SESSION);
        
        if (null == loginInfo) {
            //被拦截，重定向到login界面
        	if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
    			
				String uri = request.getRequestURI();
				String[] strGroup = uri.split("/");
				int i = strGroup.length - 1;
					
				if(uri.endsWith(".do") && strGroup[i].startsWith("ajax")){
						if(!strGroup[i].equals("ajaxQuery.do") && !strGroup[i].equals("ajaxQueryMsgCnt.do") && !strGroup[i].equals("ajaxCaptcha.do")
						   && !strGroup[i].equals("ajaxCheckUserName.do") && !strGroup[i].equals("ajaxCheckTel.do") && !strGroup[i].equals("ajaxCheckEmail.do")
						   && !strGroup[i].equals("ajaxRecommender.do")
						  ){
							//System.out.println("曹操草草======535");
							((HttpServletResponse) response).sendError(535);
						}
					}
			  
			}else{
				response.sendRedirect(contextPath+loginHtml);
			}
        	
        	 return false;
            
        }
        return true;
	}

	public String getLoginHtml() {
		return loginHtml;
	}

	public void setLoginHtml(String loginHtml) {
		this.loginHtml = loginHtml;
	}
 

}
