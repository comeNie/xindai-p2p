package com.qfw.dao.repay;

import java.util.List;

import com.qfw.common.dao.IBaseDAO;
import com.qfw.common.exception.BizException;
import com.qfw.model.bo.BizRepayPlanDetailBO;

public interface IRepayDAO extends IBaseDAO {

	/**
	 * 查询还款计划
	 * @param loanId 借款ID
	 * @return
	 * @throws BizException
	 */
	public List<BizRepayPlanDetailBO> queryRepayPlanDetail(Integer loanId) throws BizException;
	
	
}
