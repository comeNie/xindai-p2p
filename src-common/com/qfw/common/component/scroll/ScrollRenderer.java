package com.qfw.common.component.scroll;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import com.qfw.common.model.vo.ScrollModel;
import com.qfw.common.util.StringUtils;

public class ScrollRenderer extends Renderer {
	
	
	

	@Override
	public void encodeBegin(FacesContext context, UIComponent component)
			throws IOException {		
		ResponseWriter writer = context.getResponseWriter();
		try {
			String id = component.getClientId();
			//String styleClass = StringUtils.isEmpty(((Scroll)component).getStyleClass()) ? "ad-gallery" : ((Scroll)component).getStyleClass();
			//writer.write("<div id=\"gallery\" class=\"ad-gallery\">");
			String width = ((Scroll)component).getWidth();
			if(StringUtils.isEmpty(width)){
				width = "400";
			}
			String[] lis = encodeImageList(component);
			writer.startElement("div", null);			
			writer.writeAttribute("id", id, null);	
	        writer.writeAttribute("style", "width : "+width+"px", null);
	        writer.startElement("div", null);	
	        writer.writeAttribute("class", "lxfscroll", null);
	        
	        writer.startElement("ul", null);
	        writer.write(lis[0]);
	        writer.endElement("ul");	        
	        writer.endElement("div");	        
	        writer.startElement("div", null);
	        writer.writeAttribute("class", "lxfscroll-title", null);
            writer.startElement("ul", null);
            writer.write(lis[1]);
	        writer.endElement("ul");
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
		ResponseWriter writer = context.getResponseWriter();		
		//String styleClass = StringUtils.isEmpty(((Gallery)component).getStyleClass()) ? "ad-gallery" : ((Gallery)component).getStyleClass();
		Object value = ((Scroll)component).getValue();
		int size = 0;
		if(value instanceof List){
			size = ((List)value).size();
		}
		if(size ==0 ){
			return;
		}
		String width = ((Scroll)component).getWidth();
		if(StringUtils.isEmpty(width)){
			width = "400";
		}
		/*String heigth = ((Scroll)component).getWidth();
		if(StringUtils.isEmpty(heigth)){
			heigth = "300";
		}
		*/
		writer.startElement("script", null);
		writer.writeAttribute("type", "text/javascript", null);		
		writer.write("$(function() {");
		writer.write("var ul = $(\".lxfscroll ul\");");
		writer.write("var li = $(\".lxfscroll li\");");
		writer.write("var tli = $(\".lxfscroll-title li\");");
		writer.write("var speed = 350;");
		writer.write("var autospeed = 3000;");
		writer.write("var i=1;");
		writer.write("var index = 0;");
		writer.write("var n = 0;");
		writer.write("function lxfscroll() {");
		writer.write("var index = tli.index($(this));");
		writer.write("tli.removeClass(\"cur\");");
		writer.write("$(this).addClass(\"cur\");");
		writer.write("ul.css({\"left\":\"0px\"});");
		writer.write("li.css({\"left\":\"0px\"}); ");
		writer.write("li.eq(index).css({\"z-index\":i});");
		writer.write("li.eq(index).css({\"left\":\""+width+"px\"});");
		writer.write("ul.animate({left:\"-"+width+"px\"},speed);");
		writer.write("i++;");
		writer.write("};");
		writer.write("function autoroll() {");
		writer.write("if(n >= "+size+") {");
		writer.write("n = 0;}");
		writer.write("tli.removeClass(\"cur\");");
		writer.write("tli.eq(n).addClass(\"cur\");");
		writer.write("ul.css({\"left\":\"0px\"});");
		writer.write("li.css({\"left\":\"0px\"});");
		writer.write("li.eq(n).css({\"z-index\":i});");
		writer.write("li.eq(n).css({\"left\":\""+width+"px\"});");
		writer.write("n++;i++;");
		writer.write("timer = setTimeout(autoroll, autospeed);");
		writer.write("ul.animate({left:\"-"+width+"px\"},speed);};");
		writer.write("function stoproll() {");
		writer.write("li.hover(function() {");
		writer.write("clearTimeout(timer);");
		writer.write("n = $(this).prevAll().length+1;");
		writer.write("}, function() {");
		writer.write("timer = setTimeout(autoroll, autospeed);});");
		writer.write("tli.hover(function() {");
		writer.write("clearTimeout(timer);");
		writer.write("n = $(this).prevAll().length+1;");
		writer.write("}, function() {");
		writer.write("timer = setTimeout(autoroll, autospeed);");
		writer.write("});};");
		writer.write("tli.mouseover(lxfscroll);");
		writer.write("autoroll();");
		writer.write("stoproll();");
		writer.write("});");				
		writer.endElement("script");
	}

	protected String[] encodeImageList(UIComponent component) throws IOException{
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		String width = ((Scroll)component).getWidth();
		if(StringUtils.isEmpty(width)){
			width = "400";
		}
		String heigth = ((Scroll)component).getHeight();
		if(StringUtils.isEmpty(heigth)){
			heigth = "300";
		}
		Object value = ((Scroll)component).getValue();
		int i = 1;
		if(value instanceof List){
			for (Iterator iterator = ((List)value).iterator(); iterator.hasNext();) {
				ScrollModel img = (ScrollModel) iterator.next();
				sb1.append("<li>").append("<a target=\"_blank\" href=\""+img.getHref()+"\">").
				append("<img style = \"border : 0;\" src=\""+img.getImageUrl()+"\" width=\""+width+"\" height=\""+heigth+"\" />").append("</a></li>");
				if(1 == i){
					sb2.append("<li class=\"lxfscroll-title-li cur\">1</li>");
				}else{
					sb2.append("<li class=\"lxfscroll-title-li\">"+i+"</li>");
				}
				i++;				
			}
		}
		return new String[]{sb1.toString(),sb2.toString()};
	}
}
