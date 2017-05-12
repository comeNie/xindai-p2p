package com.qfw.bizservice.custinfo.recommendation;

import java.math.BigDecimal;

import com.qfw.common.exception.BizException;
import com.qfw.common.model.vo.MessageVO;
import com.qfw.model.bo.BizCreditorRightBO;

public interface IRecommendationBS {

	public MessageVO recommendDeduct(Integer custId,BigDecimal recommendPercent, int count, BigDecimal crAmt,String txNO)
			throws BizException;
	
	public void recommendReward(Integer custId) throws BizException;

	public void creatCreditorCount(BizCreditorRightBO bizCreditorRightBO)
			throws Exception;

}
