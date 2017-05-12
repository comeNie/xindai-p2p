package com.qfw.common.bizservice.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.qfw.common.bizservice.IMsgConfBS;
import com.qfw.common.bizservice.IMsgTemplateBS;
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

@Service("msgConfBS")
public class MsgConfBSImpl implements IMsgConfBS{
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	private Logger log = LogFactory.getInstance().getPlatformLogger();
	
	
	/**
	 * 获取消息配置BO
	 * @param custId
	 * @param eventType
	 * @param msgTypeCd
	 * @return
	 * @throws BizException
	 */
	public BizMessageConfBO getConf(Integer custId,String eventType,String msgTypeCd)throws BizException{
		String findMsgConf = "from BizMessageConfBO where custId = ? and eventType = ? and msgTypeCd=?";
		List<BizMessageConfBO> messageConfBOs = hibernateTemplate.find(findMsgConf,custId,eventType,msgTypeCd);
		if( messageConfBOs.size()>0 ){
			return messageConfBOs.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 获取消息配置BO列表
	 * @param custId
	 * @return
	 * @throws BizException
	 */
	public List<BizMessageConfBO> getConfList(Integer custId)throws BizException{
		String findMsgConf = "from BizMessageConfBO where custId = ? ";
		List<BizMessageConfBO> messageConfBOs = hibernateTemplate.find(findMsgConf,custId);
		return messageConfBOs;
	}

}
