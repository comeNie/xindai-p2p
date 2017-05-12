package com.qfw.platform.utils;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.qfw.platform.model.register.SendMessage;

import freemarker.template.Template;

public class SpringSendEmail {
	
	private JavaMailSender sender;
	private FreeMarkerConfigurer freeMarkerConfigurer = null;

	public void setFreeMarkerConfigurer(
			FreeMarkerConfigurer freeMarkerConfigurer) {
		this.freeMarkerConfigurer = freeMarkerConfigurer;
	}

	public void setSender(JavaMailSender sender) {
		this.sender = sender;
	}

	/**
	 * 构造注册邮件内容
	 * 
	 * @param message
	 * @return
	 */
	private String getRegisterEmailContent(SendMessage message) {
		String htmlText = "";
		try {
			Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate(
					Constants.REGISTER_EMAIL_TEMPLATE);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("content", message.getContent());
			htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl,
					map);
		} catch (Exception e) {
			 
		}
		return htmlText;
	}

	/**
	 * 发送注册的验证邮件
	 * 
	 * @param message
	 * @throws MessagingException
	 */
	public void sendRegisterEmail(SendMessage message)
			throws MessagingException {
		MimeMessage msg = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, false,
				Constants.MESSAGE_EMAIL_ENCODE);
		helper.setFrom(Constants.MESSAGE_EMAIL_FROM);
		helper.setSubject(Constants.MESSAGE_EMAIL_SUBJECT);
		String htmlText = getRegisterEmailContent(message);
		helper.setText(htmlText, true);
		try {
			helper.setTo(message.getAddress());
			sender.send(msg);
		} catch (Exception e) {
			 
		}
	}

}