package com.qfw.platform.service;

import java.util.List;

import com.qfw.common.exception.BizException;
import com.qfw.manager.model.FinAccountVo;
import com.qfw.platform.model.loan.CreditReportDetailVO;
import com.qfw.platform.model.loan.CustomerDetailVO;
import com.qfw.platform.model.loan.LoanDetailVO;
import com.qfw.platform.model.loan.LoanRepayInfoVo;
import com.qfw.platform.model.vo.CustAccountVO;

/**
 * 投资service
 * @author Teddy
 *
 */
public interface ILoanService {

	/**
	 * 标的详情
	 * @throws BizException
	 */
	public LoanDetailVO getLoanDetail(Integer loanId) throws BizException;
	
	/**
	 * 获得还款信息
	 * @throws BizException
	 */
	public LoanRepayInfoVo getLoanRepayInfo(Integer loanApproveId) throws BizException;
	
	/**
	 * 个人信息
	 * @throws BizException
	 */
	public CustomerDetailVO getCustomerDetail(Integer customerId) throws BizException;
	
	/**
	 * 信用报告
	 * @throws BizException
	 */
	public CreditReportDetailVO getCreditReport(Integer customerId) throws BizException;
	
	/**
	 * 理财账户
	 * @throws BizException
	 */
	public FinAccountVo getFinAccount(Integer customerId) throws BizException;
	
	/**
	 * 认证信息
	 * @throws BizExcption
	 */
	public List getAuthDetail(Integer customerId) throws BizException;
	
	/**
	 * 投标记录
	 * @param loanId 借款发布号
	 * @param isTran是否债券转让
	 * @throws BizException
	 */
	public List getCreditorRight(Integer loanId,boolean isTran) throws BizException;

	
	/**
	 * 还款计划
	 * @throws BizException
	 */
	public List getRepayPlanDetail(Integer loanId) throws BizException;
	
	
	/**
	 * 获取账户余额
	 * @throws BizException
	 */
	public CustAccountVO getCustAccount(Integer cust_id) throws BizException;
}
