package com.qfw.bizservice.loan;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import com.qfw.common.exception.BizException;
import com.qfw.common.model.permission.SysRole;
import com.qfw.common.model.permission.SysUser;
import com.qfw.manager.model.MyCreditorVO;
import com.qfw.model.bo.BizArrearsDetailBO;

/**
 * 借款核心服务
 * @author kindion
 *
 */
public interface ILoanBS {
	
	/**
	 * 借款生效服务
	 * @param loanApproveId 
	 * @throws BizException
	 */
	public void effectLoan(Integer loanApproveId) throws BizException;
	
	/**
	 * 借款未生效
	 * @param loanApproveId
	 * @throws BizException
	 */
	public void invalidLoan(Integer loanApproveId) throws BizException;

	/**
	 * 借款延期处理
	 * @param bizArrearsDetailBO
	 * @param 跑批日期
	 * @return 产生罚息
	 * @throws BizException
	 */
	public BigDecimal delayLoan(BizArrearsDetailBO bizArrearsDetailBO,Date batchDate) throws BizException;
	
	/**
	 * 借款逾期处理
	 * @param bizArrearsDetailBO
	 * @param 跑批日期
	 * @return 产生罚息
	 * @throws BizException
	 */
	public BigDecimal overdueLoan(BizArrearsDetailBO bizArrearsDetailBO,Date batchDate) throws BizException;

	/**
	 * 发起放款流程
	 * @param loanApproveId 发布表id
	 * @param userId 用户id
	 * @throws BizException
	 */
	public void startLoan(Integer loanApproveId, Integer userId) throws BizException;

	/**
	 * 通过流程id获取借款申请数据
	 * @param workItemId
	 * @return
	 * @throws BizException
	 */
	public Map findLoanApproveByWorkItemId(String workItemId) throws BizException;

	/**
	 * 放款审批服务
	 * @param loanApproveId 接口发布id
	 * @param isAgree 审批是否同意
	 * @param taskId 任务id
	 * @param curAuditUser 当前审批人
	 * @param comment 审批意见
	 * @param html 审批流程结束保存html信息
	 * @throws BizException
	 */
	public void completeLoan(Integer loanApproveId, boolean isAgree, String taskId,
			SysUser curAuditUser,SysRole auditRole,String comment,String html) throws BizException;

	public MyCreditorVO getProtocolInfo(Integer loanApproveId) throws BizException;
	
}