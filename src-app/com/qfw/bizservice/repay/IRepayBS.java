package com.qfw.bizservice.repay;

import java.util.List;

import com.qfw.common.exception.BizException;
import com.qfw.model.bo.BizRepayPlanDetailBO;
import com.qfw.model.vo.repay.RepayInfoVO;
import com.qfw.model.vo.repay.RepayPlanDetailVO;

/**
 * 还款服务
 * @author kindion
 *
 */
public interface IRepayBS {
	
	/**
	 * 生成还款计划服务
	 * @param repayPlanDetailVO 还款计划参数VO
	 * @return bizRepayPlanDetailBO
	 */
	public List<BizRepayPlanDetailBO> genRepayPlanDetail(RepayPlanDetailVO repayPlanDetailVO) throws BizException;
	
	/**
	 * 保存还款计划服务
	 * @param repayPlanDetailVO
	 * @return RepayPlanDetailVO.bizRepayPlanDetailBOs  还款计划
	 * @return RepayPlanDetailVO.ttlPrincipalInterestAmt 总本息
	 * @return RepayPlanDetailVO.ttlInterestAmt 总利息
	 * @throws BizException
	 */
	public RepayPlanDetailVO saveRepayPlanDetail(RepayPlanDetailVO repayPlanDetailVO) throws BizException;
	
	/**
	 * 全额还款处理服务
	 * @param repayWayCD 正常还款1\提前还款2\平台垫付3
	 * @param loanId 借款ID
	 * @param repayPlanId 还款计划ID
	 * @throws BizException
	 */
	public void repayForFull(String repayWayCD ,Integer loanId,Integer repayPlanId) throws BizException;
	
	/**
	 * 全额代还服务
	 * @param repayWayCD 正常还款1\提前还款2\平台垫付3
	 * @param loanId 借款ID
	 * @param repayPlanId 还款计划ID
	 * @throws BizException
	 */
	public void repayForFullDh(String repayWayCD ,Integer loanId,Integer repayPlanId,Integer custId) throws BizException;
	
	/**
	 * 还款金额试算
	 * @param repayWayCD
	 * @param loanId
	 * @param repayPlanId
	 * @return
	 * @throws BizException
	 */
	public RepayInfoVO repayForTrial(String repayWayCD ,Integer loanId,Integer repayPlanId) throws BizException;
	
}
