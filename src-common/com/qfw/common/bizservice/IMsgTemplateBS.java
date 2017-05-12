package com.qfw.common.bizservice;

import java.util.Map;

import com.qfw.common.exception.BizException;
import com.qfw.common.gateway.SenderInfo;
import com.qfw.common.model.SysMessageTemplate;
import com.qfw.model.bo.BizCustomerBO;

public interface IMsgTemplateBS {
	
	/**
	 * 获取短信模板
	 * @param msgCode
	 * @return
	 * @throws BizException
	 */
	public SysMessageTemplate getMsgTemp(String msgTmpCode) throws BizException;
	
	public SysMessageTemplate getMsgTemp(String eventType,String msgTmpTypeCd) throws BizException;
	
	/**
	 * 创建发送信息
	 * @param msgCode
	 * @param dataMap
	 * @return
	 * @throws BizException
	 */
	public SenderInfo createSenderInfo(String msgTmpCode,Map dataMap) throws BizException;
	
	/**
	 * 创建发送信息
	 * @param msgTmpCode 模板代码
	 * @param dataMap 模板绑定数据
	 * @param from 系统短信发送，系统站内信，系统邮件等系统信息发送可为空，会员间的站内信需提供发送者
	 * @param to 接收消息者
	 * @return
	 * @throws BizException
	 */
	public SenderInfo createSenderInfo(String msgTmpCode,Map dataMap,String from,String to) throws BizException;
	/**
	 * 消息发送服务
	 * @param msgTmpCode 模板代码
	 * @param dataMap 模板绑定数据
	 * @param from 系统短信发送，系统站内信，系统邮件等系统信息发送可为空，会员间的站内信需提供发送者
	 * @param to 接收消息者
	 * @return
	 * @throws BizException
	 */
	public void sendMsg(String msgTmpCode,Map dataMap,String from,String to) throws BizException;
	/**
	 * 通过发送信息发送
	 * @param senderInfo 发送信息
	 * @throws BizException
	 */
	public void sendMsg(SenderInfo senderInfo) throws BizException;
	
	/**
	 * 通过交易，会员Id 发送短信信息
	 * @param eventType 交易形态
	 * @param custId 会员id
	 * @param dataMap
	 * @param from 系统短信发送，系统站内信，系统邮件等系统信息发送可为空，会员间的站内信需提供发送者
	 * @param to 接受者
	 * @throws BizException
	 */
	public void sendMsg(String eventType,Integer custId,Map dataMap,String from,String to)throws BizException;
	
	/**
	 * 通过交易类型给客户发送提示信息
	 * @param eventType 交易形态
	 * @param custId 会员id
	 * @param dataMap 绑定数据
	 * @param cust 发送客户
	 * @throws BizException
	 */
	public void sendMsg(String eventType,BizCustomerBO cust,Map dataMap)throws BizException;

}
