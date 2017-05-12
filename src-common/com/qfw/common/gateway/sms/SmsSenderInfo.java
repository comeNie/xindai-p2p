package com.qfw.common.gateway.sms;

import java.util.Date;

import com.qfw.common.gateway.SenderInfo;

/** 
* 短信需要使用的基本信息 
*/ 
public class SmsSenderInfo extends SenderInfo {
		
	private Date sendTime = null;//发送时间，定时发送是需提供发送时间
	
	

	public SmsSenderInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SmsSenderInfo(String subject, String content, String from, String to) {
		super(subject, content, from, to);
		// TODO Auto-generated constructor stub
	}
	

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
	
} 