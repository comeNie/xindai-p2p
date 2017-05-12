package com.qfw.common.gateway.mail;

import java.util.List;

import com.qfw.common.gateway.SenderInfo;

/** 
* 发送邮件需要使用的基本信息 
*/ 
public class MailSenderInfo extends SenderInfo {
	private List<String> attachments;

	public MailSenderInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MailSenderInfo(String subject, String content, String from, String to) {
		super(subject, content, from, to);
		// TODO Auto-generated constructor stub
	}

	public MailSenderInfo(String subject, String content, String from,
			String to, List<String> attachments) {
		super(subject, content, from, to);
		this.attachments = attachments;
	}

	public List<String> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<String> attachments) {
		this.attachments = attachments;
	}
	
	
} 