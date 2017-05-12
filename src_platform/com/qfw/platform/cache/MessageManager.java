package com.qfw.platform.cache;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.jfree.util.Log;

import com.qfw.bizservice.transaction.chanel.impl.AuthenticationBSImpl;
import com.qfw.common.bizservice.IMsgTemplateBS;
import com.qfw.common.bizservice.IParamBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.gateway.SenderInfo;
import com.qfw.common.gateway.sms.MSClient;
import com.qfw.common.gateway.sms.SendSMS;
import com.qfw.common.gateway.sms.SmsSenderBS;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.BussConst;
import com.qfw.common.model.SysMessageTemplate;
import com.qfw.common.util.StringUtils;
import com.qfw.model.AppConst;
import com.qfw.platform.model.register.RegisterInfo;
import com.qfw.platform.model.register.SendMessage;
import com.qfw.platform.utils.AuthUtil;
import com.qfw.platform.utils.Constants;
import com.qfw.platform.utils.SpringSendEmail;

/**
 * 消息管理器
 * 
 * @author Administrator
 * 
 */
public class MessageManager {

	private SpringSendEmail springSendEmail;

	private SmsSenderBS smsSenderBS;
	
	private IMsgTemplateBS msgTemplateBS;
	
	private IParamBS paramBS;
	
	private static Logger log = LogFactory.getInstance().getBusinessLogger();

	/**
	 * 消息队列
	 */
	private Queue<SendMessage> messageQueue = new ConcurrentLinkedQueue<SendMessage>();

	public MessageManager() {
		new SendMessageThread().start();
	}

	public void setSpringSendEmail(SpringSendEmail springSendEmail) {
		this.springSendEmail = springSendEmail;
	}

	public void setSmsSenderBS(SmsSenderBS smsSenderBS) {
		this.smsSenderBS = smsSenderBS;
	}

	public void addMessage(SendMessage message) {
		if (null == message)
			return;
		messageQueue.add(message);
	}

	/**
	 * 添加邮件验证消息
	 * 
	 * @param toAddress
	 * @param userName
	 */
	public void addEmailMessage(String toAddress, String userName,
			String password) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setAddress(toAddress);
		sendMessage.setType(SendMessage.MESSAGE_TYPE_EMAIL);
		String content = Constants.REGISTER_AUTH_URL + "?username=" + userName
				+ "&auth=" + AuthUtil.getAuthByUserName(userName, AppConst.BINGING_EMAIL,toAddress);
		sendMessage.setCreateTime(System.currentTimeMillis());
		sendMessage.setContent(content);
		addMessage(sendMessage);
	}
	
	/**
	 * 解绑与绑定
	 * @param toAddress
	 * @param userName
	 */
	public void addSendEmailMessage(String toAddress, String userName, String mode) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setAddress(toAddress);
		sendMessage.setType(SendMessage.MESSAGE_TYPE_EMAIL);
		String content = Constants.BING_AUTH_URL + "?username=" + userName
				+ "&auth=" + AuthUtil.getAuthByUserName(userName, mode,toAddress);
		sendMessage.setCreateTime(System.currentTimeMillis());
		sendMessage.setContent(content);
		addMessage(sendMessage);
	}
	/**
	 * 添加短信验证
	 * 
	 * @param tel
	 */
	public void addSmsMessage(String tel, String userName, String password,
			String recommender) {
			
		//发送手机验证码
		addSmsMess(tel,AppConst.PHONE_SEND_TYPE_REGI);
		
		// 注册信息
		RegisterInfo registerInfo = new RegisterInfo();
		registerInfo.setUsername(userName);
		registerInfo.setPassword(password);
		registerInfo.setTel(tel);
		registerInfo.setRecommender(recommender);
		 
		// 保存注册信息
		SmsMessageManager.getInstance().addRegisterInfo(registerInfo);
	}
	
	
	/**
	 * 发送手机验证码
	 * @param tel 手机号码
	 * @param operType验证码用途类型：1 手机注册  2、密码修改
	 */
	public void addSmsMess(String tel,String useType) {
		    SendMessage sendMessage = new SendMessage();
			sendMessage.setAddress(tel);
			sendMessage.setType(SendMessage.MESSAGE_TYPE_PHONE);
			String content = AuthUtil.getAuthMessage();
			sendMessage.setCreateTime(System.currentTimeMillis());
			sendMessage.setUseType(useType);
			sendMessage.setContent(content);
			if(AppConst.PHONE_SEND_TYPE_REGI.equals(useType)){
				sendMessage.setEvenType(AppConst.EVENTTYPE_REGISTER);
			}else if(AppConst.PHONE_SEND_TYPE_EDITPWD.equals(useType)){
				sendMessage.setEvenType(AppConst.EVENTTYPE_FINDPWD);
			}
			
			addMessage(sendMessage);
	}
	/**
	 * 发送消息线程
	 * 
	 * @author Administrator
	 * 
	 */
	class SendMessageThread extends Thread {

		public SendMessageThread() {
			super();
			this.setName("SendMessageThread");
			this.setDaemon(true);
		}

		@Override
		public void run() {
			while (true) {
				try {
					sleep(3000);
				} catch (InterruptedException e) {
				}

				while (null != messageQueue.peek()) {
					SendMessage message = messageQueue.poll();
					if (null != message) {
						int type = message.getType();
						switch (type) {
						case SendMessage.MESSAGE_TYPE_PHONE:
							handlePhoneMessage(message);
							break;

						case SendMessage.MESSAGE_TYPE_EMAIL:
							handleEmailMessage(message);

						default:
							break;
						}
					}
				}
			}
		}

		private void handleEmailMessage(SendMessage message) {
			try {
				springSendEmail.sendRegisterEmail(message);
			} catch (MessagingException e) {
				 
			}
		}

		private void handlePhoneMessage(SendMessage message) {
			try {
				SenderInfo senderInfo = new SenderInfo();
				String service = paramBS.getParam("AUTH_CODE_SERVICE");
				if(AppConst.MS_SERVICE_YUN.equals(service)){
					//add by yangjj 20150714 for 云通讯短信
					SysMessageTemplate template = msgTemplateBS.getMsgTemp(message.getEvenType(), BussConst.MSG_MP_TYPE_CD_SMS);
					String content = message.getContent()+","+template.getMsgTmpContent();
					senderInfo.setContent(content);
					senderInfo.setMsgCode(template.getMsgTmpCode());
					senderInfo.setMsService(AppConst.MS_SERVICE_YUN);
				}else if(AppConst.MS_SERVICE_SUN.equals(service)){
					SysMessageTemplate template = msgTemplateBS.getMsgTemp(message.getEvenType(), BussConst.MSG_MP_TYPE_CD_UD);
					senderInfo.setContent(Constants.REGISTER_AUTH_MESSAGE
							+ message.getContent()+template.getMsgTmpContent());
					senderInfo.setMsService(AppConst.MS_SERVICE_SUN);
				}
				
				senderInfo.setTo(message.getAddress());
				/*senderInfo.setContent(Constants.REGISTER_AUTH_MESSAGE
				+ message.getContent()+"，世纪创投感谢您的注册！");*/
			
				if(log.isDebugEnabled()){
					log.debug("调用短信发送开始====================================");
				}
				smsSenderBS.send(senderInfo);
			} catch (BizException e) {
				log.error("短信发送失败");
			}

			SmsMessageManager.getInstance().addSmsMessage(message);
		}
	}
	public IMsgTemplateBS getMsgTemplateBS() {
		return msgTemplateBS;
	}

	public void setMsgTemplateBS(IMsgTemplateBS msgTemplateBS) {
		this.msgTemplateBS = msgTemplateBS;
	}

	public IParamBS getParamBS() {
		return paramBS;
	}

	public void setParamBS(IParamBS paramBS) {
		this.paramBS = paramBS;
	}
}