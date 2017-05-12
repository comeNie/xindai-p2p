package com.qfw.bizservice.charge;

import java.math.BigDecimal;

import com.qfw.common.exception.BizException;
import com.qfw.model.bo.BizCostBO;
import com.qfw.model.vo.charge.ChargeVO;

/**
 * 费用收取核心模块
 * @author kindion
 *
 */
public interface IChargeBS {
	
	/**
	 * 费用生成服务
	 * @param chargeVO 
	 * @return bizCostBO
	 * @throws BizException
	 */
	public BizCostBO genCharge(ChargeVO chargeVO) throws BizException;
	
	/**
	 * 费用试算服务
	 * @param chargeVO 
	 * @return bizCostBO
	 * @throws BizException
	 */
	public BizCostBO trialCharge(ChargeVO chargeVO) throws BizException;
	
	/**
	 * 费用扣收服务
	 * @param bizCostBO
	 * @return
	 * @throws BizException
	 */
	public BigDecimal deductCharge(String txNO,BizCostBO bizCost) throws BizException;
	
	/**
	 * 费用收取服务
	 * @param chargeVO 
	 * @return
	 * @throws BizException
	 */
	public BigDecimal genAndDeductCharge(ChargeVO chargeVO) throws BizException; 
	
}
