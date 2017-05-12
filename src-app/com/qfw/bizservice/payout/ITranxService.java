package com.qfw.bizservice.payout;

import java.util.List;

import com.aipg.transquery.QTDetail;
import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.model.vo.transaction.TranxCon;

public interface ITranxService extends IBaseService{
	
	public String singleTranx(String trx_code, String busicode, boolean isTLTFront,TranxCon tranxCon) 
			throws BizException;
	
	public List<QTDetail> queryTradeNew(String url,TranxCon tranxCon,boolean isTLTFront) throws BizException;
}
