package com.qfw.bizservice.income;

import java.util.Date;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.model.bo.BizRepayDetailBO;

/**
 * 收益核心服务
 * @author kindion
 *
 */
public interface IIncomeBS extends IBaseService{
	/**
	 * 批处理调用-还款收益分配
	 * @param bizRepayDetailBO
	 * @param batchDate
	 * @throws BizException
	 */
	public void incomeForRepay(BizRepayDetailBO bizRepayDetailBO,Date batchDate) throws BizException;
	
}
