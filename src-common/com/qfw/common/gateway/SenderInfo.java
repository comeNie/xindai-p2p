package com.qfw.common.gateway;

import java.io.Serializable;

/**
 * 发送信息类
 * @author Jie
 *
 */
public class SenderInfo implements Serializable {
	
	//信息主题，标题
	private String subject; 
	//信息内容
	private String content; 
	//信息发送方
	private String from;
	//信息接收方
	private String to;
	
	private String msgCode;
	
	private String msService;
	
	
	public SenderInfo() {
		super();
	}
	
	public SenderInfo(String subject, String content, String from, String to) {
		super();
		this.subject = subject;
		this.content = content;
		this.from = from;
		this.to = to;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}

	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	public String getMsService() {
		return msService;
	}

	public void setMsService(String msService) {
		this.msService = msService;
	}
	
	

}
