package com.qfw.common.component.scroll;

import java.util.ArrayList;
import java.util.List;

import javax.el.ValueExpression;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.html.HtmlOutputText;

@ResourceDependencies({
	@ResourceDependency(library="qfw", name="scroll/scroll.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js")
})
public class Scroll extends HtmlOutputText {
	
	protected enum PropertyKeys {
		width,
		height;
		String toString;
		PropertyKeys(String toString) {
			this.toString = toString;
		}

		PropertyKeys() {}

		public String toString() {
			return ((this.toString != null) ? this.toString : super.toString());
}
	}
	
	public static final String COMPONENT_TYPE = "com.qfw.common.component.Scroll";
	public static final String COMPONENT_FAMILY = "com.qfw.common.component";
	private static final String DEFAULT_RENDERER = "com.qfw.common.component.ScrollRenderer";
	private static final String OPTIMIZED_PACKAGE = "com.qfw.common.component.";
	
	public Scroll(){
		setRendererType(DEFAULT_RENDERER);
	}
	public String getFamily() {
		return COMPONENT_FAMILY;
	}
	
	public java.lang.String getWidth() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.width, null);
	}
	public void setWidth(java.lang.String width) {
		getStateHelper().put(PropertyKeys.width, width);
		handleAttribute("width", width);
	}
	public java.lang.String getHeight() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.height, null);
	}
	public void setHeight(java.lang.String height) {
		getStateHelper().put(PropertyKeys.height, height);
		handleAttribute("height", height);
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
