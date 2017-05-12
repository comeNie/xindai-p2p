package com.qfw.model.vo.transaction;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.qfw.bizservice.transaction.ITradeBS;
import com.qfw.common.log.LogFactory;

public class LazyTradeDataModel extends LazyDataModel<TradeResponseVO> {

	private static final long serialVersionUID = -5904821066777782729L;
	
	private TradeVO tradeVO;
	private List<TradeResponseVO> tradeBOList;
	private ITradeBS tradeBS;
	
	Logger log = LogFactory.getInstance().getBusinessLogger();
	
	public LazyTradeDataModel(TradeVO tradeVO,ITradeBS tradeBS){
		this.tradeVO = tradeVO;
		this.tradeBS = tradeBS;
		
	}
	
	@Override
	public List<TradeResponseVO> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		try {
			tradeBOList = this.tradeBS.findBOListByVO(tradeVO, first, pageSize);
			setRowCount(this.tradeBS.getCountByVO(tradeVO));
		} catch (Exception e) {
			log.error("获取充值翻页信息异常：", e);
		}
		return tradeBOList;
	}
	
	@Override
	public TradeResponseVO getRowData(String rowKey) {
		for(TradeResponseVO bo : tradeBOList){
			if(String.valueOf(bo.getId()).equals(rowKey))
				return bo;
		}
		TradeResponseVO bo = new TradeResponseVO();
		try {
			bo = tradeBS.findBOById(Integer.valueOf(rowKey));
		} catch (Exception e) {
			log.error("通过ID获取交易记录异常：", e);
		}
		return bo;
	}
	
	@Override
	public Object getRowKey(TradeResponseVO bo) {
		return bo.getId();
	}

}
