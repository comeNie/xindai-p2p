package com.qfw.common.listener;

import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.faces.lifecycle.LifecycleImpl;

public class SessionLifecycleImpl extends LifecycleImpl {
	public final static String SESSION_TIMEOUT_PAGES = "/index/main.jsf";

	public SessionLifecycleImpl() {
		super();
	}

	public void execute(FacesContext context) {
		try {
			super.execute(context);
		} catch (ViewExpiredException vee) {
			redirect(context);
			//throw vee;
		} catch (FacesException fe) {
			redirect(context);
			//throw fe;
		}catch (Exception e) {
			redirect(context);
		}
	}

	private void redirect(FacesContext context) {
		try {
			context.responseComplete();
			context.renderResponse();
			HttpServletRequest request = ((HttpServletRequest)context.getExternalContext().getRequest());
			/*//System.out.println(request.getRequestURL());
			//System.out.println(request.getRequestURI());*/
			String path = request.getRequestURI();
			////System.out.println(path);
			HttpServletResponse response = (HttpServletResponse) context
					.getExternalContext().getResponse();
			//String url = context.getExternalContext().getRequestContextPath()+ SESSION_TIMEOUT_PAGES;
			response.sendRedirect(path);
		} catch (Exception e) {
			//System.out.println(" Error: session timeout url redirect ");
		}
	}
}
