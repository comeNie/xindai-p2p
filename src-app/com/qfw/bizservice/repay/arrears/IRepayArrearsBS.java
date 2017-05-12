package com.qfw.bizservice.repay.arrears;

import java.util.List;

import com.qfw.common.exception.BizException;
import com.qfw.model.bo.BizArrearsDetailBO;
import com.qfw.model.bo.BizRepayPlanDetailBO;

/**
 * 欠款明细查询
 * @author kangyc
 *
 */
public interface IRepayArrearsBS {
	
	/**
	 * 获取欠款明细列表
	 * @param loanId 借款id
	 * @return bizRepayPlanDetailBO
	 */
	public List<BizRepayPlanDetailBO> queryArrearsDetail(Integer loanId) throws BizException;
	
}
