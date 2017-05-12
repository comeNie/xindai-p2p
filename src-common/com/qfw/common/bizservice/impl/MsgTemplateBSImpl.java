package com.qfw.common.bizservice.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.qfw.common.bizservice.IMsgTemplateBS;
import com.qfw.common.bizservice.IParamBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.gateway.ISenderBS;
import com.qfw.common.gateway.SenderInfo;
import com.qfw.common.gateway.mail.MailSenderInfo;
import com.qfw.common.gateway.sms.SmsSenderInfo;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.BussConst;
import com.qfw.common.model.SysMessageTemplate;
import com.qfw.common.util.CollectionUtil;
import com.qfw.common.util.StringUtils;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.bo.BizMessageConfBO;

@Service("msgTemplateBS")
public class MsgTemplateBSImpl implements IMsgTemplateBS{
	
	@Autowired
	@Qualifier("mailSenderBS")
	private ISenderBS mailSenderBS;
	
	@Autowired
	@Qualifier("smsSenderBS")
	private ISenderBS smsSenderBS;
	
	@Autowired
	private IParamBS paramBS;
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	private Logger log = LogFactory.getInstance().getPlatformLogger();
	/**
	 * 获取短信模板
	 * @param msgCode
	 * @return
	 * @throws BizException
	 */
	public SysMessageTemplate getMsgTemp(String msgTmpCode) throws BizException{
		String hql = "from SysMessageTemplate where msgTmpCode = ?";
		List<SysMessageTemplate> data = hibernateTemplate.find(hql, msgTmpCode);
		if(data!=null && !data.isEmpty()){
			return data.get(0);
		}
		return null;
	}
	/**
	 * 通过交易形态及通知类型获取短信模板
	 * @param eventType
	 * @param msgTmpTypeCd
	 * @return
	 * @throws BizException
	 */
	public SysMessageTemplate getMsgTemp(String eventType,String msgTmpTypeCd) throws BizException{
		String hql = "from SysMessageTemplate where eventType = ? and msgTmpTypeCd = ?";
		List<SysMessageTemplate> data = hibernateTemplate.find(hql, eventType,msgTmpTypeCd);
		if(data!=null && !data.isEmpty()){
			return data.get(0);
		}
		return null;
	}
	
	/**
	 * 创建发送信息
	 * @param msgCode
	 * @param dataMap
	 * @return
	 * @throws BizException
	 */
	public SenderInfo createSenderInfo(String msgTmpCode,Map dataMap) throws BizException{
		
		SysMessageTemplate template = getMsgTemp(msgTmpCode);
		return createSenderInfo(template, dataMap);
	}
	
	public SenderInfo createSenderInfo(SysMessageTemplate template,Map dataMap) throws BizException{
		SenderInfo senderInfo = null;
		if(BussConst.MSG_MP_TYPE_CD_SMS.equals(template.getMsgTmpTypeCd())){//短信
			senderInfo = new SmsSenderInfo();
		}else if(BussConst.MSG_MP_TYPE_CD_MAIL.equals(template.getMsgTmpTypeCd())){//邮件
			senderInfo = new MailSenderInfo();
		}else if(BussConst.MSG_MP_TYPE_CD_INSTATION.equals(template.getMsgTmpTypeCd())){//站内信
			
		}
		if(senderInfo!=null){
			String content = StringUtils.getTemplateStr(template.getMsgTmpContent(), dataMap);
			String subject = StringUtils.getTemplateStr(template.getMsgTmpSubject(), dataMap);
			senderInfo.setContent(content);
			senderInfo.setSubject(subject);
		}
		
		return senderInfo;
	}
	
	public SenderInfo createSenderInfo(SysMessageTemplate template,Map dataMap,BizCustomerBO cust,String msType) throws BizException{
		SenderInfo senderInfo = null;
		if(BussConst.MSG_MP_TYPE_CD_SMS.equals(template.getMsgTmpTypeCd())){//短信
			if(StringUtils.isEmpty(cust.getMobileTelephone())){
				log.warn(cust.getCustName()+"短信发送失败,手机号码为空");
				return null;
			}
			senderInfo = new SmsSenderInfo();
			senderInfo.setTo(cust.getMobileTelephone());
		}else if(BussConst.MSG_MP_TYPE_CD_MAIL.equals(template.getMsgTmpTypeCd())){//邮件
			if(StringUtils.isEmpty(cust.getEmail())){
				log.warn(cust.getCustName()+"邮件发送失败,邮箱地址为空");
				return null;
			}
			senderInfo = new MailSenderInfo();
			senderInfo.setTo(cust.getEmail());
		}else if(BussConst.MSG_MP_TYPE_CD_INSTATION.equals(template.getMsgTmpTypeCd())){//站内信
			
		}
		if(senderInfo!=null){
			//mod by yangjj start
			String content = "";
			if(AppConst.MS_SERVICE_SUN.equals(msType)){//天元
				content = StringUtils.getTemplateStr(template.getMsgTmpContent(), dataMap);
				String subject = StringUtils.getTemplateStr(template.getMsgTmpSubject(), dataMap);
			}else if(AppConst.MS_SERVICE_YUN.equals(msType)){
				if(StringUtils.isNotEmpty(template.getMsgTmpContent())){
					String[] Contents = template.getMsgTmpContent().split(",");
					content = (String) dataMap.get(Contents[0]);
					if(Contents.length > 1){
						for(int i = 1;i<Contents.length;i++){
							content = content + "|"+dataMap.get(Contents[i]);
						}
					}
				}
			}
			
			senderInfo.setContent(content);
			senderInfo.setMsgCode(template.getMsgTmpCode());
//			senderInfo.setSubject(subject);
		}
		
		return senderInfo;
	}
	/**
	 * 创建发送信息
	 * @param msgTmpCode 模板代码
	 * @param dataMap 模板绑定数据
	 * @param from 系统短信发送，系统站内信，系统邮件等系统信息发送可为空，会员间的站内信需提供发送者
	 * @param to 接收消息者
	 * @return
	 * @throws BizException
	 */
	public SenderInfo createSenderInfo(String msgTmpCode,Map dataMap,String from,String to) throws BizException{
		SenderInfo senderInfo = createSenderInfo(msgTmpCode, dataMap);
		if(StringUtils.isNotEmpty(from)){
			senderInfo.setFrom(from);
		}
		senderInfo.setTo(to);
		return senderInfo;
	}
	/**
	 * 消息发送服务
	 * @param msgTmpCode 模板代码
	 * @param dataMap 模板绑定数据
	 * @param from 系统短信发送，系统站内信，系统邮件等系统信息发送可为空，会员间的站内信需提供发送者
	 * @param to 接收消息者
	 * @return
	 * @throws BizException
	 */
	public void sendMsg(String msgTmpCode,Map dataMap,String from,String to) throws BizException{
		if(StringUtils.isEmpty(to)){
			throw new BizException("接收方不能为空");
		}
		SenderInfo senderInfo = createSenderInfo(msgTmpCode, dataMap, from, to);
		sendMsg(senderInfo);
	}
	/**
	 * 通过交易，会员Id 发送短信信息
	 * @param eventType 交易形态
	 * @param custId 会员id
	 * @param dataMap
	 * @param from 系统短信发送，系统站内信，系统邮件等系统信息发送可为空，会员间的站内信需提供发送者
	 * @param to 接受者
	 * @throws BizException
	 */
	public void sendMsg(String eventType,Integer custId,Map dataMap,String from,String to)throws BizException{
		String findMsgConf = "from BizMessageConfBO where custId = ? and isEnable = ? and eventType = ?";
		List<BizMessageConfBO> messageConfBOs = hibernateTemplate.find(findMsgConf,custId,AppConst.PLAT_REPAYED_FLAG_CD_YES,eventType);
		if(CollectionUtil.isEmpty(messageConfBOs)){
			messageConfBOs = hibernateTemplate.find(findMsgConf,0,AppConst.PLAT_REPAYED_FLAG_CD_YES,eventType);//查询公共配置
		}
		if(CollectionUtil.isNotEmpty(messageConfBOs)){
			for (BizMessageConfBO bizMessageConfBO : messageConfBOs) {
				SysMessageTemplate messTemp = getMsgTemp(bizMessageConfBO.getEventType(),bizMessageConfBO.getMsgTypeCd());
				SenderInfo senderInfo = createSenderInfo(messTemp, dataMap);
				sendMsg(senderInfo);
			}
		}
	}
	
	/**
	 * 通过交易类型给客户发送提示信息
	 * @param eventType 交易形态
	 * @param custId 会员id
	 * @param dataMap 绑定数据
	 * @param cust 发送客户
	 * @throws BizException
	 */
	public void sendMsg(String eventType,BizCustomerBO cust,Map dataMap)throws BizException{
//		String findMsgConf = "from BizMessageConfBO where custId = ? and isEnable = ? and eventType = ?";
//		List<BizMessageConfBO> messageConfBOs = hibernateTemplate.find(findMsgConf,cust.getId(),AppConst.PLAT_REPAYED_FLAG_CD_YES,eventType);
//		if(CollectionUtil.isEmpty(messageConfBOs)){
//			messageConfBOs = hibernateTemplate.find(findMsgConf,0,AppConst.PLAT_REPAYED_FLAG_CD_YES,eventType);//查询公共配置
//		}
//		if(CollectionUtil.isNotEmpty(messageConfBOs)){
//			for (BizMessageConfBO bizMessageConfBO : messageConfBOs) {
		 		String msgTmpTypeCd = "";
		 		String type= "";
				if(AppConst.MS_SERVICE_SUN.equals(paramBS.getParam("MS_SERVICE"))){//天元
					msgTmpTypeCd = BussConst.MSG_MP_TYPE_CD_UD;
					type = AppConst.MS_SERVICE_SUN;
				}else if(AppConst.MS_SERVICE_YUN.equals(paramBS.getParam("MS_SERVICE"))){
					msgTmpTypeCd = BussConst.MSG_MP_TYPE_CD_SMS;
					type = AppConst.MS_SERVICE_YUN;
				}else{
					throw new BizException("其他提醒服务未实现");
				}
				SysMessageTemplate messTemp = getMsgTemp(eventType,msgTmpTypeCd);
				SenderInfo senderInfo = createSenderInfo(messTemp, dataMap,cust,type);
				if(senderInfo!=null){
					senderInfo.setMsService(type);
					sendMsg(senderInfo);
				}
//			}
//		}
	}
	
	/**
	 * 通过发送信息发送
	 * @param senderInfo 发送信息
	 * @throws BizException
	 */
	public void sendMsg(SenderInfo senderInfo) throws BizException{
		if(senderInfo instanceof MailSenderInfo){
			mailSenderBS.send(senderInfo);
		}else if(senderInfo instanceof SmsSenderInfo){
			smsSenderBS.send(senderInfo);
		}else{
			throw new BizException("其他提醒服务未实现");
		}
	}

}
