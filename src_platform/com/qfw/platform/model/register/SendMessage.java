package com.qfw.platform.model.register;

import java.io.Serializable;

/**
 * 发送消息
 * 
 * @author Administrator
 * 
 */
public class SendMessage implements Serializable {

	private static final long serialVersionUID = 8551074395737787194L;

	/**
	 * 消息内容
	 */
	private String content;

	/**
	 * 消息类型(0:手机验证短信,1:邮箱验证)
	 */
	private int type;

	/**
	 * 邮箱或者手机号码
	 */
	private String address;
	
	/**
	 * 创建时间
	 */
	private long createTime;
	
    /**
     * 验证码用途类型
     */
	private String useType;
	
	private String msgCode;
	
	private String evenType;
	
 
	public String getUseType() {
		return useType;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}

	public SendMessage() {

	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
	public static final int MESSAGE_TYPE_PHONE = 0;
	public static final int MESSAGE_TYPE_EMAIL = 1;


	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	public String getEvenType() {
		return evenType;
	}

	public void setEvenType(String evenType) {
		this.evenType = evenType;
	}
}