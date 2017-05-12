/*
 * Generated, Do Not Modify
 */
/*
 * Copyright 2010 Prime Technology.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qfw.common.component.fileupload;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.component.UINamingContainer;
import javax.el.ValueExpression;
import javax.el.MethodExpression;
import javax.faces.render.Renderer;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.event.AbortProcessingException;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import java.util.List;
import java.util.ArrayList;
import javax.faces.component.UINamingContainer;

@ResourceDependencies({
	@ResourceDependency(library="qfw", name="fileupload/fileupload.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="qfw", name="fileupload/fileupload.js")
})
public class FileUpload extends UIComponentBase implements org.primefaces.component.api.Widget {


	public static final String COMPONENT_TYPE = "com.qfw.common.component.FileUpload";
	public static final String COMPONENT_FAMILY = "com.qfw.common.component";
	private static final String DEFAULT_RENDERER = "com.qfw.common.component.FileUploadRenderer";
	private static final String OPTIMIZED_PACKAGE = "com.qfw.common.component.";

	protected enum PropertyKeys {

		widgetVar
		,update
		,fileUploadListener
		,multiple
		,auto
		,label
		,image
		,cancelImage
		,width
		,height
		,allowTypes
		,description
		,sizeLimit
		,wmode
		,customUI
		,style
		,styleClass;

		String toString;

		PropertyKeys(String toString) {
			this.toString = toString;
		}

		PropertyKeys() {}

		public String toString() {
			return ((this.toString != null) ? this.toString : super.toString());
}
	}

	public FileUpload() {
		setRendererType(DEFAULT_RENDERER);
	}

	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	public java.lang.String getWidgetVar() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.widgetVar, null);
	}
	public void setWidgetVar(java.lang.String _widgetVar) {
		getStateHelper().put(PropertyKeys.widgetVar, _widgetVar);
		handleAttribute("widgetVar", _widgetVar);
	}

	public java.lang.String getUpdate() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.update, null);
	}
	public void setUpdate(java.lang.String _update) {
		getStateHelper().put(PropertyKeys.update, _update);
		handleAttribute("update", _update);
	}

	public javax.el.MethodExpression getFileUploadListener() {
		return (javax.el.MethodExpression) getStateHelper().eval(PropertyKeys.fileUploadListener, null);
	}
	public void setFileUploadListener(javax.el.MethodExpression _fileUploadListener) {
		getStateHelper().put(PropertyKeys.fileUploadListener, _fileUploadListener);
		handleAttribute("fileUploadListener", _fileUploadListener);
	}

	public boolean isMultiple() {
		return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.multiple, false);
	}
	public void setMultiple(boolean _multiple) {
		getStateHelper().put(PropertyKeys.multiple, _multiple);
		handleAttribute("multiple", _multiple);
	}

	public boolean isAuto() {
		return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.auto, false);
	}
	public void setAuto(boolean _auto) {
		getStateHelper().put(PropertyKeys.auto, _auto);
		handleAttribute("auto", _auto);
	}

	public java.lang.String getLabel() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.label, null);
	}
	public void setLabel(java.lang.String _label) {
		getStateHelper().put(PropertyKeys.label, _label);
		handleAttribute("label", _label);
	}

	public java.lang.String getImage() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.image, null);
	}
	public void setImage(java.lang.String _image) {
		getStateHelper().put(PropertyKeys.image, _image);
		handleAttribute("image", _image);
	}

	public java.lang.String getCancelImage() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.cancelImage, null);
	}
	public void setCancelImage(java.lang.String _cancelImage) {
		getStateHelper().put(PropertyKeys.cancelImage, _cancelImage);
		handleAttribute("cancelImage", _cancelImage);
	}

	public java.lang.String getWidth() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.width, null);
	}
	public void setWidth(java.lang.String _width) {
		getStateHelper().put(PropertyKeys.width, _width);
		handleAttribute("width", _width);
	}

	public java.lang.String getHeight() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.height, null);
	}
	public void setHeight(java.lang.String _height) {
		getStateHelper().put(PropertyKeys.height, _height);
		handleAttribute("height", _height);
	}

	public java.lang.String getAllowTypes() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.allowTypes, null);
	}
	public void setAllowTypes(java.lang.String _allowTypes) {
		getStateHelper().put(PropertyKeys.allowTypes, _allowTypes);
		handleAttribute("allowTypes", _allowTypes);
	}

	public java.lang.String getDescription() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.description, null);
	}
	public void setDescription(java.lang.String _description) {
		getStateHelper().put(PropertyKeys.description, _description);
		handleAttribute("description", _description);
	}

	public java.lang.Long getSizeLimit() {
		return (java.lang.Long) getStateHelper().eval(PropertyKeys.sizeLimit, java.lang.Long.MAX_VALUE);
	}
	public void setSizeLimit(java.lang.Long _sizeLimit) {
		getStateHelper().put(PropertyKeys.sizeLimit, _sizeLimit);
		handleAttribute("sizeLimit", _sizeLimit);
	}

	public java.lang.String getWmode() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.wmode, null);
	}
	public void setWmode(java.lang.String _wmode) {
		getStateHelper().put(PropertyKeys.wmode, _wmode);
		handleAttribute("wmode", _wmode);
	}

	public boolean isCustomUI() {
		return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.customUI, false);
	}
	public void setCustomUI(boolean _customUI) {
		getStateHelper().put(PropertyKeys.customUI, _customUI);
		handleAttribute("customUI", _customUI);
	}

	public java.lang.String getStyle() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.style, null);
	}
	public void setStyle(java.lang.String _style) {
		getStateHelper().put(PropertyKeys.style, _style);
		handleAttribute("style", _style);
	}

	public java.lang.String getStyleClass() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.styleClass, null);
	}
	public void setStyleClass(java.lang.String _styleClass) {
		getStateHelper().put(PropertyKeys.styleClass, _styleClass);
		handleAttribute("styleClass", _styleClass);
	}



	private String inputFileId;

	public void broadcast(javax.faces.event.FacesEvent event) throws javax.faces.event.AbortProcessingException {
		super.broadcast(event);
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		MethodExpression me = getFileUploadListener();
		
		if (me != null) {
			me.invoke(facesContext.getELContext(), new Object[] {event});
		}
	}
	
	String getInputFileId(FacesContext facesContext) {
		if(inputFileId == null) {
			inputFileId = this.getClientId(facesContext).replaceAll(String.valueOf(UINamingContainer.getSeparatorChar(facesContext)), "_") + "_file";
		}
		
		return inputFileId;
	}

	protected FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}
	public String resolveWidgetVar() {
		FacesContext context = FacesContext.getCurrentInstance();
		String userWidgetVar = (String) getAttributes().get("widgetVar");

		if(userWidgetVar != null)
			return userWidgetVar;
		 else
			return "widget_" + getClientId(context).replaceAll("-|" + UINamingContainer.getSeparatorChar(context), "_");
	}

	public void handleAttribute(String name, Object value) {
		List<String> setAttributes = (List<String>) this.getAttributes().get("javax.faces.component.UIComponentBase.attributesThatAreSet");
		if(setAttributes == null) {
			String cname = this.getClass().getName();
			if(cname != null && cname.startsWith(OPTIMIZED_PACKAGE)) {
				setAttributes = new ArrayList<String>(6);
				this.getAttributes().put("javax.faces.component.UIComponentBase.attributesThatAreSet", setAttributes);
			}
		}
		if(setAttributes != null) {
			if(value == null) {
				ValueExpression ve = getValueExpression(name);
				if(ve == null) {
					setAttributes.remove(name);
				} else if(!setAttributes.contains(name)) {
					setAttributes.add(name);
				}
			}
		}
	}
}