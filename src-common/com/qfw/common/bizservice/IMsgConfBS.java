package com.qfw.common.bizservice;

import java.util.List;
import java.util.Map;

import com.qfw.common.exception.BizException;
import com.qfw.common.gateway.SenderInfo;
import com.qfw.common.model.SysMessageTemplate;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.bo.BizMessageConfBO;

public interface IMsgConfBS {
	
	/**
	 * 获取消息配置BO
	 * @param custId
	 * @param eventType
	 * @param msgTypeCd
	 * @return
	 * @throws BizException
	 */
	public BizMessageConfBO getConf(Integer custId,String eventType,String msgTypeCd)throws BizException;
	
	
	/**
	 * 获取消息配置BO列表
	 * @param custId
	 * @return
	 * @throws BizException
	 */
	public List<BizMessageConfBO> getConfList(Integer custId)throws BizException;

}
