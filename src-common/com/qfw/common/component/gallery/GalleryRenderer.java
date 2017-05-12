package com.qfw.common.component.gallery;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import com.qfw.common.model.vo.ImageVO;
import com.qfw.common.util.StringUtils;

public class GalleryRenderer extends Renderer {
	
	
	

	@Override
	public void encodeBegin(FacesContext context, UIComponent component)
			throws IOException {

		ResponseWriter writer = context.getResponseWriter();
		try {
			String id = component.getClientId();
			String styleClass = StringUtils.isEmpty(((Gallery)component).getStyleClass()) ? "ad-gallery" : ((Gallery)component).getStyleClass();
			//writer.write("<div id=\"gallery\" class=\"ad-gallery\">");
			writer.startElement("div", null);			
			writer.writeAttribute("id", id, null);
	        writer.writeAttribute("class", styleClass, null);	
	        writer.writeAttribute("style", "padding : 30px", null);
	        writer.startElement("div", null);
	        writer.writeAttribute("class", "ad-image-wrapper", null);
	        writer.endElement("div");	        
	        writer.startElement("div", null);
	        writer.writeAttribute("class", "ad-controls", null);
	        writer.endElement("div");	        
	        writer.startElement("div", null);
	        writer.writeAttribute("class", "ad-nav", null);	        
	        writer.startElement("div", null);
	        writer.writeAttribute("class", "ad-thumbs", null);
	        writer.startElement("ul", null);
	        writer.writeAttribute("class", "ad-thumb-list", null);
	        
	        encodeImageList(context, component);
	        
	        writer.endElement("ul");
	        writer.endElement("div");	        
	        writer.endElement("div");	        
			writer.endElement("div");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			 
		}
		
	
	}

	@Override
	public void decode(FacesContext context, UIComponent component) {
		
	}

	@Override
	public void encodeEnd(FacesContext context, UIComponent component)
			throws IOException {
		encodeScript(context,component);
	}
	
	protected void encodeScript(FacesContext context, UIComponent component) throws IOException {
		String startAtIndex = ((Gallery)component).getStartAtIndex();
		if(StringUtils.isEmpty(startAtIndex)){
			startAtIndex = "0";
		}
		String widgetVar = ((Gallery)component).getWidgetVar();
		if(StringUtils.isEmpty(widgetVar)){
			widgetVar = "galleries";
		}
		ResponseWriter writer = context.getResponseWriter();		
		String styleClass = StringUtils.isEmpty(((Gallery)component).getStyleClass()) ? "ad-gallery" : ((Gallery)component).getStyleClass();
		writer.startElement("script", null);
		writer.writeAttribute("type", "text/javascript", null);		
		writer.write("$(function() {");
		writer.write(widgetVar+" = $('."+styleClass+"').adGallery({start_at_index: "+startAtIndex+"});");
		writer.write("});");		
		writer.endElement("script");
	}

	protected void encodeImageList(FacesContext context, UIComponent component) throws IOException{
		Object value = ((Gallery)component).getValue();
		if(value instanceof List){
			ResponseWriter writer = context.getResponseWriter();
			for (Iterator iterator = ((List)value).iterator(); iterator.hasNext();) {
				ImageVO img = (ImageVO) iterator.next();
				String normalImage = img.getNormalImageUrl();
				String smallImage = img.getSmallImageUrl();
				String originalImage = img.getOriginalImageUrl();
				writer.startElement("li", null);				
				writer.startElement("a", null);
		        writer.writeAttribute("href", normalImage, null);		        
		        writer.startElement("img", null);
		        writer.writeAttribute("src", smallImage, null);
		        writer.writeAttribute("title", img.getTitle(), null);
		        writer.writeAttribute("alt", img.getAlt(), null);
		        writer.writeAttribute("longdesc", originalImage, null);
		        writer.endElement("img");
		        writer.endElement("a");		        
		        writer.endElement("li");
			}
		}
		
	}
}
