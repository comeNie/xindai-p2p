package com.qfw.bizservice.transaction.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfw.bizservice.transaction.ITradeBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.dao.transaction.ITradeDAO;
import com.qfw.model.bo.BizTradeBO;
import com.qfw.model.vo.transaction.TradeResponseVO;
import com.qfw.model.vo.transaction.TradeVO;

@Service("tradeBS")
public class TradeBSImpl extends BaseServiceImpl implements ITradeBS{

	@Autowired
	private ITradeDAO tradeDAO;
	
	private Logger log = LogFactory.getInstance().getBusinessLogger();

	
	@Override
	public TradeResponseVO findBOById(Integer id) throws BizException {
		return (TradeResponseVO)tradeDAO.findById(BizTradeBO.class, id);
	}

	@Override
	public List<TradeResponseVO> findBOListByVO(TradeVO tradeVO, int first,
			int pageSize) throws BizException {
		try {
			List<TradeResponseVO> list = this.tradeDAO.findPageListByVO(tradeVO, first, pageSize);
			return list;
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.error(e.getMessage());
			}
		}
		return null;
	}

	@Override
	public int getCountByVO(TradeVO tradeVO) {
		int count = 0;
		try {
			count = this.tradeDAO.findCountByVO(tradeVO);
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.error(e.getMessage());
			}
		}
		return count;
	}
	@Override
	public BigDecimal sumByVO(TradeVO tradeVO) {
		return tradeDAO.sumByVO(tradeVO);
	}

}
