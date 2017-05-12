package com.qfw.platform.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;

import com.qfw.platform.model.register.RegisterInfo;
import com.qfw.platform.model.register.SendMessage;
import com.qfw.platform.utils.Constants;

/**
 * 短信验证码缓存管理器
 * 
 * @author Administrator
 * 
 */
public class SmsMessageManager {

	/**
	 * 保存手机号码和验证密码
	 */
	private static Map<String, SendMessage> smsMessageMap = new ConcurrentHashMap<String, SendMessage>();

	/**
	 * 临时保存用户注册未验证手机的信息
	 */
	private static Map<String, RegisterInfo> registerInfoMap = new ConcurrentHashMap<String, RegisterInfo>();

	private static SmsMessageManager instance = new SmsMessageManager();

	private SmsMessageManager() {
		new ClearUnUseCodeThread().start();
	}

	public static SmsMessageManager getInstance() {
		return instance;
	}

	public void addSmsMessage(SendMessage message) {
		if (null == message)
			return;

		String tel = message.getAddress();
		smsMessageMap.put(tel, message);
	}
	
	public void addRegisterInfo(RegisterInfo registerInfo) {
		if (null == registerInfo)
			return;
		String tel = registerInfo.getTel();
		registerInfoMap.put(tel, registerInfo);
	}
	
	public RegisterInfo getRegisterInfoByTel(String tel){
		if(StringUtils.isEmpty(tel))
			return null;
		return registerInfoMap.get(tel);
	}

	public String getAuthCode(String tel) {
		if (StringUtils.isEmpty(tel))
			return null;
		SendMessage message = smsMessageMap.get(tel);
		if (null != message) {
			long createTime = message.getCreateTime();
			long now = System.currentTimeMillis();
			if ((now - createTime) <= Constants.REGISTER_AUTH_MESSAGE_TIME) {
				return message.getContent();
			}
		}
		return null;
	}

	class ClearUnUseCodeThread extends Thread {

		public ClearUnUseCodeThread() {
			super();
			this.setDaemon(true);
			this.setName("ClearUnUseCodeThread");
		}

		@Override
		public void run() {
			for (String tel : smsMessageMap.keySet()) {
				SendMessage message = smsMessageMap.get(tel);
				if (null != message) {
					long createTime = message.getCreateTime();
					long now = System.currentTimeMillis();
					if ((now - createTime) > Constants.REGISTER_AUTH_MESSAGE_TIME) {
						smsMessageMap.remove(tel);
						registerInfoMap.remove(tel);
					}
				}
			}
		}
	}
}
