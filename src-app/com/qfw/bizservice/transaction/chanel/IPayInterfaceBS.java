package com.qfw.bizservice.transaction.chanel;

import com.qfw.common.exception.BizException;
import com.qfw.model.vo.transaction.OrderVO;
import com.qfw.model.vo.transaction.QueryOrderVO;

/**
 * 支付接口服务
 * @author Jie
 *
 */
public interface IPayInterfaceBS {
	/**
	 * 查询支付订单
	 * @param queryOrderVO
	 * @return
	 * @throws BizException
	 */
	public OrderVO queryOrder(QueryOrderVO queryOrderVO) throws BizException;

}
