package com.qfw.common.util;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

public class MessagesController {

	public static void addInfo(String summary,String detail) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,summary,detail));
	}
	
	public static void addWarn(String summary,String detail) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,summary,detail));
	}
	
	public static void addError(String summary,String detail) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,summary,detail));
	}
	
	public static void addFatal(String summary,String detail) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,summary,detail));
	}
	public static void addError(String s,String summary,String detail) {
		FacesContext.getCurrentInstance().addMessage(s, new FacesMessage(FacesMessage.SEVERITY_ERROR,summary,detail));
	}
	public static void clearMessages(){
		List messages = FacesContext.getCurrentInstance().getMessageList();
		if(messages!=null){
			messages.clear();
		}		
	}
}
