package com.qfw.common.util.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIOutput;
import javax.faces.event.PhaseEvent;

import com.qfw.common.bizservice.impl.ComponseFactory;

@ViewScoped
@ManagedBean(name="commonManageBean")
public class CommonManageBean {

	@ManagedProperty(value="#{componseFactory}")
	ComponseFactory componseFactory;
	public boolean hasint = false;
	public String title = "测试";
	public void init(PhaseEvent phaseEvent) { 
		if(!hasint){
			componseFactory.createUIView();
			hasint = true;
		}
	} 

	private UIOutput body;
	
	private String name = "test";

	public UIOutput getBody() {
		return body;
	}

	public void setBody(UIOutput body) {
		this.body = body;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void changeName(){
		this.name = "测试使用";
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ComponseFactory getComponseFactory() {
		return componseFactory;
	}

	public void setComponseFactory(ComponseFactory componseFactory) {
		this.componseFactory = componseFactory;
	}
	
	
}
