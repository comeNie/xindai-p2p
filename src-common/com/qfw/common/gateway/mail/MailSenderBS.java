package com.qfw.common.gateway.mail;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.qfw.common.exception.BizException;
import com.qfw.common.gateway.ISenderBS;
import com.qfw.common.gateway.SenderInfo;
@Service("mailSenderBS")
public class MailSenderBS implements ISenderBS {

	@Autowired
	@Qualifier("mailSender")
	private JavaMailSenderImpl sender;

	@Override
	public void send(SenderInfo senderInfo) throws BizException {
		// TODO Auto-generated method stub
		
		try {
			MimeMessage msg = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8");
			msg.setRecipients(Message.RecipientType.TO,new InternetAddress().parse(senderInfo.getTo()));
			helper.setFrom(sender.getUsername());
			helper.setSubject(senderInfo.getSubject());
			helper.setText(senderInfo.getContent(), true);
			if(senderInfo instanceof MailSenderInfo){
				List<String> files = ((MailSenderInfo) senderInfo).getAttachments();
				if(files!=null && !files.isEmpty()){
					for (String fileUrl : files) {
						File file = new File(fileUrl);
						helper.addAttachment(MimeUtility.encodeWord(file.getName()), file);
					}
				}
				
			}
			// 这里的方法调用和插入图片是不同的，使用MimeUtility.encodeWord()来解决附件名称的中文问题
			// 如果主题出现乱码，可以使用该函数，也可以使用下面的函数
			// helper.setSubject(MimeUtility.encodeText(String text,String
			// charset,String encoding))
			// 第2个参数表示字符集，第三个为目标编码格式。
			// MimeUtility.encodeWord(String word,String charset,String encoding)
			sender.send(msg);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			 
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			 
		}
		

	}

}
