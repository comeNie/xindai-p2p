package com.qfw.common.component.gallery;

import java.util.ArrayList;
import java.util.List;

import javax.el.ValueExpression;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.html.HtmlOutputText;

@ResourceDependencies({
	@ResourceDependency(library="qfw", name="gallery/gallery.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="qfw", name="gallery/gallery.js")
})
public class Gallery extends HtmlOutputText {
	
	public static final String COMPONENT_TYPE = "com.qfw.common.component.Gallery";
	public static final String COMPONENT_FAMILY = "com.qfw.common.component";
	private static final String DEFAULT_RENDERER = "com.qfw.common.component.GalleryRenderer";
	private static final String OPTIMIZED_PACKAGE = "com.qfw.common.component.";
	
	protected enum PropertyKeys {
		startAtIndex,
		afterImageVisible,
		widgetVar;
		String toString;
		PropertyKeys(String toString) {
			this.toString = toString;
		}
		PropertyKeys() {}

		public String toString() {
			return ((this.toString != null) ? this.toString : super.toString());
}
	}
	
	public Gallery(){
		setRendererType(DEFAULT_RENDERER);
	}
	public String getFamily() {
		return COMPONENT_FAMILY;
	}
	public java.lang.String getStartAtIndex() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.startAtIndex, null);
	}
	public void setStartAtIndex(java.lang.String startAtIndex) {
		getStateHelper().put(PropertyKeys.startAtIndex, startAtIndex);
		handleAttribute("startAtIndex", startAtIndex);
	}
	public java.lang.String getWidgetVar() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.widgetVar, null);
	}
	public void setWidgetVar(java.lang.String _widgetVar) {
		getStateHelper().put(PropertyKeys.widgetVar, _widgetVar);
		handleAttribute("widgetVar", _widgetVar);
	}
	public java.lang.String getAfterImageVisible() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.afterImageVisible, null);
	}
	public void setAfterImageVisible(java.lang.String afterImageVisible) {
		getStateHelper().put(PropertyKeys.afterImageVisible, afterImageVisible);
		handleAttribute("afterImageVisible", afterImageVisible);
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
