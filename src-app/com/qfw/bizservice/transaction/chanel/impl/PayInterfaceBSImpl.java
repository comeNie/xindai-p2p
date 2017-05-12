package com.qfw.bizservice.transaction.chanel.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.qfw.bizservice.transaction.chanel.IPayInterfaceBS;
import com.qfw.common.bizservice.IParamBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.gateway.impl.HttpEndPointBS;
import com.qfw.common.gateway.impl.MessageServiceBSImpl;
import com.qfw.common.util.MD5Utils;
import com.qfw.common.util.StringUtils;
import com.qfw.model.template.RuleUtil;
import com.qfw.model.vo.transaction.OrderVO;
import com.qfw.model.vo.transaction.QueryOrderVO;

@Service("payInterfaceBS")
public class PayInterfaceBSImpl implements IPayInterfaceBS {

	private Logger log = Logger.getLogger(PayInterfaceBSImpl.class);
		
	@Autowired
	@Qualifier("messageServiceBS")
	private MessageServiceBSImpl messageServiceBS;
	
	@Autowired
	@Qualifier("paramBS")
	private IParamBS paramBS;
	
	@Override
	public OrderVO queryOrder(QueryOrderVO queryOrderVO) throws BizException {
		if(queryOrderVO != null){
			queryOrderVO.setMerCode(paramBS.getParam("PAY_MER_CODE"));
			queryOrderVO.setTx(paramBS.getParam("PAY_QUERY_ORDER_TX"));
			queryOrderVO.setSign(MD5Utils.getMD5Str(queryOrderVO.getMerCode()+paramBS.getParam("PAY_MD5_KEY")).toUpperCase());
			String xml = messageServiceBS.generateXml(queryOrderVO, "queryOrder.xml");
			log.debug("订单查询请求报文："+xml);
			String s = StringUtils.getBASE64(xml);
			Map param = new HashMap();
			param.put("requestDomain", s);
			String result = HttpEndPointBS.send(paramBS.getParam("PAY_QUERY_ORDER_URL"), param, null);
			log.debug("订单查询响应报文："+result);
			if(StringUtils.isNotEmpty(result)){
				OrderVO order = (OrderVO) RuleUtil.orderVOStream.fromXML(result);
				return order;
			}
		}
		return null;
	}

}
