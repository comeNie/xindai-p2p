package com.qfw.common.component.alert;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

public class Alert extends UIComponentBase {

	protected enum PropertyKeys {

		sticky
		,showSummary
		,showDetail
		,globalOnly
		,life
		,warnIcon
		,infoIcon
		,errorIcon
		,fatalIcon
		,autoUpdate;

		String toString;

		PropertyKeys(String toString) {
			this.toString = toString;
		}

		PropertyKeys() {}

		public String toString() {
			return ((this.toString != null) ? this.toString : super.toString());
		}
	}
	
	@Override
	public String getFamily() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void encodeEnd(FacesContext context) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		startScript(writer, getClientId());
		encodeMessages(context);
		endScript(writer);
	}
	private void encodeMessages(FacesContext context) throws IOException{
		ResponseWriter writer = context.getResponseWriter();
        Iterator<FacesMessage> messages = isGlobalOnly() ? context.getMessages(null) : context.getMessages();
        FacesMessage msg = null;
        StringBuilder errorMsg = new StringBuilder();
        if(messages.hasNext()){
        	while(messages.hasNext()){
            	msg = messages.next();
            	String summary = msg.getSummary().replaceAll("'", "\\\\'");
    			String detail = msg.getDetail().replaceAll("'", "\\\\'");
    			if(isShowSummary()){
    				errorMsg.append(summary).append("\\n");
    			}
    			if(isShowDetail()){
    				errorMsg.append(detail).append("\\n");
    			}
            }
            writer.writeText("alert('"+errorMsg.toString()+"');", null);
        }
	}

	private void startScript(ResponseWriter writer, String id)
			throws IOException {
		writer.startElement("script", null);
		writer.writeAttribute("id", id + "_s", null);
		writer.writeAttribute("type", "text/javascript", null);
	}

	private void endScript(ResponseWriter writer) throws IOException {
		writer.endElement("script");
	}
	
	
	public boolean isSticky() {
		return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.sticky, false);
	}
	public void setSticky(boolean _sticky) {
		getStateHelper().put(PropertyKeys.sticky, _sticky);
	}

	public boolean isShowSummary() {
		return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.showSummary, true);
	}
	public void setShowSummary(boolean _showSummary) {
		getStateHelper().put(PropertyKeys.showSummary, _showSummary);
	}

	public boolean isShowDetail() {
		return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.showDetail, false);
	}
	public void setShowDetail(boolean _showDetail) {
		getStateHelper().put(PropertyKeys.showDetail, _showDetail);
	}

	public boolean isGlobalOnly() {
		return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.globalOnly, false);
	}
	public void setGlobalOnly(boolean _globalOnly) {
		getStateHelper().put(PropertyKeys.globalOnly, _globalOnly);
	}

	public int getLife() {
		return (java.lang.Integer) getStateHelper().eval(PropertyKeys.life, 6000);
	}
	public void setLife(int _life) {
		getStateHelper().put(PropertyKeys.life, _life);
	}

	public java.lang.String getWarnIcon() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.warnIcon, null);
	}
	public void setWarnIcon(java.lang.String _warnIcon) {
		getStateHelper().put(PropertyKeys.warnIcon, _warnIcon);
	}

	public java.lang.String getInfoIcon() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.infoIcon, null);
	}
	public void setInfoIcon(java.lang.String _infoIcon) {
		getStateHelper().put(PropertyKeys.infoIcon, _infoIcon);
	}

	public java.lang.String getErrorIcon() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.errorIcon, null);
	}
	public void setErrorIcon(java.lang.String _errorIcon) {
		getStateHelper().put(PropertyKeys.errorIcon, _errorIcon);
	}

	public java.lang.String getFatalIcon() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.fatalIcon, null);
	}
	public void setFatalIcon(java.lang.String _fatalIcon) {
		getStateHelper().put(PropertyKeys.fatalIcon, _fatalIcon);
	}

	public boolean isAutoUpdate() {
		return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.autoUpdate, false);
	}
	public void setAutoUpdate(boolean _autoUpdate) {
		getStateHelper().put(PropertyKeys.autoUpdate, _autoUpdate);
	}

}
