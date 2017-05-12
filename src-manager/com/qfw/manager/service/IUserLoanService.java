package com.qfw.manager.service;

import java.util.List;

import com.qfw.common.exception.BizException;
import com.qfw.platform.model.json.Pager;

public interface IUserLoanService {

	/**
	 * 获取还款计划列表
	 */
	public Pager findLoanRepayPlan(int requestPage, int pageSize, Integer loanApproveId) throws BizException;

	public List findLoanRepayPlan(Integer loanId) throws BizException;

	public List findCreditorByLoanId(Integer loanId) throws BizException;
	
}
