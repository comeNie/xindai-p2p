package com.qfw.bizservice.batch;

import java.util.Date;

import com.qfw.common.exception.BizException;

/**
 * 批处理服务
 * @author kindion
 *
 */
public interface IBatchBS {
	
	/**
	 * 自动到期还款服务(代付)(批处理日终调用)
	 * @param batchDate 跑批日期
	 * @throws BizException
	 */
	public void autoRepayForFullDh(Date batchDate) throws BizException;
	/**
	 * 自动到期还款服务(批处理日终调用)
	 * @param batchDate 跑批日期
	 * @throws BizException
	 */
	public void autoRepayForFull(Date batchDate) throws BizException;
	
	/**
	 * 自动逾期还款服务(代付)(批处理日终调用)
	 * @param batchDate 跑批日期
	 * @throws BizException
	 */
	public void autoRepayOverdueForFullDh(Date batchDate) throws BizException;
	/**
	 * 自动逾期还款服务(批处理日终调用)
	 * @param batchDate 跑批日期
	 * @throws BizException
	 */
	public void autoRepayOverdueForFull(Date batchDate) throws BizException;
	
	/**
	 * 批处理逾期借款
	 * @param batchDate 跑批日期 
	 * @throws BizException
	 */
	public void batchOverdueLoan(Date batchDate) throws BizException;
	
	/**
	 * 批处理延期期借款
	 * @param batchDate 跑批日期
	 * @throws BizException
	 */
	public void batchDelayLoan(Date batchDate) throws BizException;
	
	/**
	 * 批处理还款分配收益
	 * @param batchDate
	 * @throws BizException
	 */
	public void batchIncomeForRepay(Date batchDate) throws BizException;
	
	/**
	 * 借款发布失效(未满标后$DAY天,投资金额自动归还到投资者账户)
	 * @param batchDate
	 * @throws BizException
	 */
	public void batchInvalidLoan(Date batchDate) throws BizException;
	
	/**
	 * 还款提醒
	 * @param batchDate
	 * @throws BizException
	 */
	public void batchRepayForTip(Date batchDate) throws BizException;
	
	/**
	 * 债权转让发布失效
	 * @param batchDate
	 * @throws BizException
	 */
	public void batchInvalidCreditorRightTran(Date batchDate) throws BizException ;
	
	/**
	 * 每天清空登陆失败数
	 * @param batchDate
	 * @throws BizException
	 */
	public void clearUserErrorCount(Date batchDate)throws BizException ;
	
	/**
	 * 贷后任务状态更新
	 * @param batchDate
	 * @throws BizException
	 */
	public void batchPostLoanStatus(Date batchDate)throws BizException ;
	
	/**
	 * 查询订单信息
	 * @param batchDate
	 * @throws BizException
	 */
	public void queryOrder(Date batchDate) throws BizException;
	public void batchCreditorCount(Date batchDate) throws BizException;
	
}
