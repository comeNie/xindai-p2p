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

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.application.Resource;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultUploadedFile;
import org.primefaces.model.UploadedFile;
import org.primefaces.renderkit.CoreRenderer;
import org.primefaces.util.ComponentUtils;
import org.primefaces.webapp.MultipartRequest;

import com.qfw.common.util.web.ViewOper;

public class FileUploadRenderer extends CoreRenderer {

    @Override
	public void decode(FacesContext facesContext, UIComponent component) {
		FileUpload fileUpload = (FileUpload) component;
		MultipartRequest multipartRequest = getMultiPartRequestInChain(facesContext);
		
		if(multipartRequest != null) {
			FileItem file = multipartRequest.getFileItem(fileUpload.getInputFileId(facesContext));
			
			if(file != null) {
				UploadedFile uploadedFile = new DefaultUploadedFile(file);
				fileUpload.queueEvent(new FileUploadEvent(fileUpload, uploadedFile));
			}
		}
	}
	
	/**
	 * Finds our MultipartRequestServletWrapper in case application contains other RequestWrappers
	 */
	private MultipartRequest getMultiPartRequestInChain(FacesContext facesContext) {
		Object request = facesContext.getExternalContext().getRequest();
		
		while(request instanceof ServletRequestWrapper) {
			if(request instanceof MultipartRequest) {
				return (MultipartRequest) request;
			}
			else {
				request = ((ServletRequestWrapper) request).getRequest();
			}
		}
		
		return null;
	}

    @Override
	public void encodeEnd(FacesContext facesContext, UIComponent component) throws IOException {
		FileUpload fileUpload = (FileUpload) component;
		
		encodeMarkup(facesContext, fileUpload);
		encodeScript(facesContext, fileUpload);
	}

	protected void encodeScript(FacesContext context, FileUpload fileUpload) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		String clientId = fileUpload.getClientId(context);
		String inputFileId = fileUpload.getInputFileId(context);
		String actionURL = getActionURL(context);
		String cancelImg = fileUpload.getCancelImage() == null ? getResourceRequestPath(context, "fileupload/cancel.png") : getResourceURL(context, fileUpload.getCancelImage());
		
		UIComponent parentForm = ComponentUtils.findParentForm(context, fileUpload);
		if(parentForm == null) {
			throw new FacesException("FileUpload component:" + clientId + " needs to be enclosed in a form");
		}
		String formClientId = parentForm.getClientId(context);
		
		writer.startElement("script", null);
		writer.writeAttribute("type", "text/javascript", null);
		
		writer.write("jQuery(function() {");
		writer.write(fileUpload.resolveWidgetVar() + " = new PrimeFaces.widget.Uploader('" + clientId + "', {");
		writer.write("uploader:'" + getResourceRequestPath(context, "fileupload/uploadify.swf") + "'");
		writer.write(",script:'" + actionURL + "'");
		writer.write(",cancelImg:'" + cancelImg + "'");
		writer.write(",formId:'" + formClientId + "'");
		writer.write(",fileDataName:'" + inputFileId + "'");
		writer.write(",multi:" + fileUpload.isMultiple());
		writer.write(",auto:" + fileUpload.isAuto());
		writer.write(",inputFileId:'" + inputFileId + "'");
        writer.write(",jsessionid:'" + ((HttpSession) (context.getExternalContext().getSession(true))).getId() + "'");
		
		if(fileUpload.getUpdate() != null) writer.write(",update:'" + ComponentUtils.findClientIds(context, fileUpload, fileUpload.getUpdate()) + "'");
		if(fileUpload.getImage() != null) writer.write(",buttonImg:'" + getResourceURL(context, fileUpload.getImage()) + "'");
		if(fileUpload.getLabel() != null) writer.write(",buttonText:'" + fileUpload.getLabel() + "'");
		if(fileUpload.getWidth() != null) writer.write(",width:'" + fileUpload.getWidth() + "'");
		if(fileUpload.getHeight() != null) writer.write(",height:'" + fileUpload.getWidth() + "'");
		if(fileUpload.getAllowTypes() != null) writer.write(",fileExt:'" + fileUpload.getAllowTypes() + "'");
		if(fileUpload.getDescription() != null) writer.write(",fileDesc:'" + fileUpload.getDescription() + "'");
		if(fileUpload.getSizeLimit() != Long.MAX_VALUE) writer.write(",sizeLimit:" + fileUpload.getSizeLimit());
		if(fileUpload.getWmode() != null) writer.write(",wmode:'" + fileUpload.getWmode() + "'");
		
		writer.write("});});");						
		
		writer.endElement("script");
	}

	protected void encodeMarkup(FacesContext facesContext, FileUpload fileUpload) throws IOException {
		ResponseWriter writer = facesContext.getResponseWriter();
		String clientId = fileUpload.getClientId(facesContext);
		String inputFileId = fileUpload.getInputFileId(facesContext);
		String widgetVar = fileUpload.resolveWidgetVar();
		
		writer.startElement("span", fileUpload);
		writer.writeAttribute("id", clientId, "id");
		
		if(fileUpload.getStyle() != null) writer.writeAttribute("style", fileUpload.getStyle(), "style");
		if(fileUpload.getStyleClass() != null) writer.writeAttribute("class", fileUpload.getStyleClass(), "styleClass");
		
		writer.startElement("input", null);
		writer.writeAttribute("type", "file", null);
		writer.writeAttribute("id", inputFileId, null);
		writer.writeAttribute("name", inputFileId, null);
		writer.endElement("input");
		
		if(!fileUpload.isCustomUI() && !fileUpload.isAuto()) {
			writer.startElement("a", null);
			writer.writeAttribute("href", "javascript:void(0)", null);
			writer.writeAttribute("onclick", widgetVar + ".upload()", null);
			writer.write("Upload");
			writer.endElement("a");
			
			writer.write(" | ");
			
			writer.startElement("a", null);
			writer.writeAttribute("href", "javascript:void(0)", null);
			writer.writeAttribute("onclick", widgetVar + ".clear()", null);
			writer.write("Clear");
			writer.endElement("a");
		}
		
		writer.endElement("span");
	}

	@Override
	protected String getResourceRequestPath(FacesContext facesContext,
			String resourceName) {
		Resource resource = facesContext.getApplication().getResourceHandler().createResource(resourceName, "qfw");

        return resource.getRequestPath();
		/*Resource resource = facesContext.getApplication().getResourceHandler().createResource(resourceName, "qfw");
		ViewOper.getRequest().getRealPath("META-INF/resources/qfw/");
		ViewOper.getRequest().getContextPath()+"/META-INF/resources/qfw/"+resourceName
		//System.out.println(resource);
        return ViewOper.getRequest().getContextPath()+"/resources/qfw/"+resourceName;*/
	}
	
}