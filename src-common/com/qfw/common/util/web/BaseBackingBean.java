package com.qfw.common.util.web;

import java.io.Serializable;

import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;

import com.qfw.common.exception.BizException;


public class BaseBackingBean implements Serializable {
	
	public static final String HEADER_INFO_NAME = "headerInfoBean";
	
	private DataTable dataTable;
	
	/**
	 * default constructor
	 *
	 */
	public BaseBackingBean() {
	}

	/**
	 * intialization logic before jsp presentation logic
	 *
	 */
	protected void init() throws BizException {
		
	};
	/**
	 * 搜索功能
	 */
	public void search(){
		if(dataTable!=null){
			dataTable.setFirst(0);
		}
	}
	/**
	 * 页面刷新功能
	 */
	public void refresh(){
		if(dataTable!=null){
			////System.out.println(dataTable.getFirst());
			//dataTable.getc
			//dataTable.setFirst(0);
		}
	}
	
	/**
	 * 向页面输出错误信息
	 *
	 * @param msg
	 */
	public void alert(String msg) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				msg, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	/**
	 * 提示信息
	 * @param msg
	 */
	public void alertInfo(String msg) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				msg, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	/**
	 * 关闭弹出框
	 * @param widgetVar
	 */
	public void closeDailog(String widgetVar){
		executeJS(widgetVar+".hide();");
	}
	/**
	 * 执行js脚本
	 * @param js
	 */
	public void executeJS(String js){
		RequestContext.getCurrentInstance().execute(js);
	}
	public static String escapeUnicode(String s) {
		if (s == null) {
			return s;
		}
		char[] chars = s.toCharArray();
		char c;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			c = chars[i]; // Ignore ascii character
			if (c > 0xff) {
				sb.append("\\u").append(Integer.toHexString(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	@PreDestroy
	public void preDestroy(){
		//System.out.println("Bean "+this.getClass().getName()+" Destroy");
	}

	public DataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}



}
