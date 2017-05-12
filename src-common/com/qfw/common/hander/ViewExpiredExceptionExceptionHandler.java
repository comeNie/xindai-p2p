package com.qfw.common.hander;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

public class ViewExpiredExceptionExceptionHandler extends
		ExceptionHandlerWrapper {

	private ExceptionHandler wrapped;

	public ViewExpiredExceptionExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return this.wrapped;
	}

	@Override
	public void handle() throws FacesException {
		for (Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents()
				.iterator(); i.hasNext();) {
			ExceptionQueuedEvent event = i.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event
					.getSource();
			Throwable t = context.getException();
			if (t instanceof ViewExpiredException) {
				/*FacesContext fc = FacesContext.getCurrentInstance();
				try {
					HttpServletResponse response = (HttpServletResponse) fc
							.getExternalContext().getResponse();
					String url = fc.getExternalContext()
							.getRequestContextPath() + "/404.html";
					response.sendRedirect(url);
					fc.renderResponse();*/
				FacesContext facesContext = FacesContext.getCurrentInstance();
				ExternalContext externalContext = facesContext.getExternalContext();
	            try {

	                //facesContext.setViewRoot(facesContext.getApplication().getViewHandler().createView(facesContext, "/login.xhtml"));   
	            	String url = facesContext.getExternalContext()
							.getRequestContextPath() + "/login.jsf?"+Math.random();
	            	externalContext.redirect(url);        
	                facesContext.getPartialViewContext().setRenderAll(true);
	                facesContext.renderResponse();
				} catch (IOException e) {
					 
				} finally {
					i.remove();
				}
			}
		}
		getWrapped().handle();
	}
	
	/*public void handle() throws FacesException {
	    FacesContext facesContext = FacesContext.getCurrentInstance();
	                 for (Iterator<ExceptionQueuedEvent> iter = getUnhandledExceptionQueuedEvents()
	            .iterator(); iter.hasNext();) {
	        Throwable exception = iter.next().getContext().getException();

	        if (exception instanceof ViewExpiredException) {


	            final ExternalContext externalContext = facesContext.getExternalContext();
	            try {


	                facesContext.setViewRoot(facesContext.getApplication().getViewHandler().createView(facesContext, "/Login.xhtml"));   
	                externalContext.redirect("ibm_security_logout?logoutExitPage=/Login.xhtml");        
	                facesContext.getPartialViewContext().setRenderAll(true);
	                facesContext.renderResponse();

	            } catch (IOException e) {
	                // TODO Auto-generated catch block
	                 
	            } finally {
	                iter.remove();
	            }
	        }

	    }

	    getWrapped().handle();
	}*/
}