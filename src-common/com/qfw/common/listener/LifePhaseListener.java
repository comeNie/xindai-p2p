package com.qfw.common.listener;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

public class LifePhaseListener implements PhaseListener {

	@Override
	public void afterPhase(PhaseEvent event) {
		////System.out.println((new StringBuilder("after phase id :")).append(
		//		event.getPhaseId()).toString());

	}

	@Override
	public void beforePhase(PhaseEvent event) {		
		////System.out.println((new StringBuilder("before phase id :")).append(event.getPhaseId()).toString());
		/*FacesContext facesCtx=event.getFacesContext();
		ExternalContext extCtx = facesCtx.getExternalContext();
        HttpSession session = (HttpSession)extCtx.getSession(false);
        boolean newSession = (session == null)||(session.isNew());  
        //System.out.println("回话"+newSession);
        boolean postback =!extCtx.getRequestParameterMap().isEmpty();    
        //System.out.println("回话postback"+postback);
        boolean timedout = postback && newSession;
        if(timedout){
        	try{
        		extCtx.dispatch("login.jsf");
        		String url = extCtx.getRequestContextPath() + "/login.jsf?"+Math.random();
        		extCtx.redirect(url);        
        		facesCtx.getPartialViewContext().setRenderAll(true);
        		facesCtx.renderResponse();
        	}catch (IOException e) {
        		 
        	}
        }*/
		/*UIViewRoot uv =  FacesContext.getCurrentInstance().getViewRoot();
		if(uv == null) return ;
		List<UIComponent> coms = uv.getChildren();
		int size = coms.size();
		UIComponent uiComponent = null;
		 for (int i = 0; i<size ;i++) {
			 uiComponent = coms.get(i);
			//System.out.println(i+":"+uiComponent.getId()+uiComponent.getClass());
		}*/

	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

}
