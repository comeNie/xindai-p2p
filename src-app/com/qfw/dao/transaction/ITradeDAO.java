package com.qfw.dao.transaction;

import java.math.BigDecimal;
import java.util.List;

import com.qfw.common.dao.IBaseDAO;
import com.qfw.common.exception.BizException;
import com.qfw.model.vo.transaction.TradeResponseVO;
import com.qfw.model.vo.transaction.TradeVO;

/**
 * 交易记录dao
 *
 * @author kyc
 */
public interface ITradeDAO extends IBaseDAO {

	/**
	 * 根据id查询信息
	 * @param id
	 * @return
	 */
	public TradeResponseVO findInfoByID(Integer id);
	
	/**
	 * 查询数据笔数
	 * @param vo
	 * @return
	 * @throws BizException
	 */
	public int findCountByVO(TradeVO vo) throws BizException;
	
	/**
	 * 分页查询
	 * @param tradeVO
	 * @param first
	 * @param pageSize
	 * @return
	 * @throws BizException
	 */
	public List<TradeResponseVO> findPageListByVO(TradeVO tradeVO, int first,
			int pageSize) throws BizException;

	public BigDecimal sumByVO(TradeVO vo);
}
