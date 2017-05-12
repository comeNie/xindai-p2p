package com.qfw.bizservice.transaction;

import java.math.BigDecimal;
import java.util.List;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.model.vo.transaction.TradeResponseVO;
import com.qfw.model.vo.transaction.TradeVO;

/**
 * 交易记录bs
 *
 * @author kyc
 */
public interface ITradeBS extends IBaseService {

	/**
	 * 根据id查询交易记录bo
	 * @param id
	 * @return
	 * @throws BizException
	 */
	public TradeResponseVO findBOById(Integer id ) throws BizException;
	
	/**
	 * 分页查询list
	 * @return
	 * @throws BizException
	 */
	public List<TradeResponseVO> findBOListByVO (TradeVO tradeVO, int first, int pageSize) throws BizException;

	/**
	 * 获取数据总数
	 * @param tradeVO
	 * @return
	 * @throws BizException
	 */
	public int getCountByVO(TradeVO tradeVO);

	public BigDecimal sumByVO(TradeVO tradeVO);
}
