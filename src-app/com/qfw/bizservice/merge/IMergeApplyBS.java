package com.qfw.bizservice.merge;

import java.util.List;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.permission.SysRole;
import com.qfw.common.model.permission.SysUser;
import com.qfw.jbpm.model.bo.Jbpm4AuditOpinion;
import com.qfw.model.vo.credit.BizCreditLimitApplyVO;
import com.qfw.model.vo.loan.LoanApplyVO;

public interface IMergeApplyBS extends IBaseService {

	/**
	 * 保存申请信息
	 * 
	 * @param limitApplyVO
	 * @param loanApplyVO
	 * @param auditUser
	 * @throws BizException
	 */
	public void saveMergeApply(BizCreditLimitApplyVO limitApplyVO,LoanApplyVO loanApplyVO, SysUser auditUser, SysRole auditRole)
			throws BizException;

	/**
	 * 工作流流转
	 * 
	 * @param taskId
	 * @param to
	 * @param loanApplyVO
	 * @param auditUser
	 * @param auditStatus
	 * @param appNode
	 * @throws BizException
	 */
	public void tranTakeAndCompleteTask(String taskId, String to,BizCreditLimitApplyVO limitApplyVO, LoanApplyVO loanApplyVO,
			SysUser auditUser, SysRole auditRole, String auditStatus,String appNode, String html) throws BizException;

	/**
	 * 获取审批意见信息
	 * 
	 * @param workItemId
	 * @return
	 */
	public List<Jbpm4AuditOpinion> getAuditOpinion(String workItemId);

	/**
	 * 检查是否流程被退回
	 * 
	 * @param workItemId
	 * @param taskName
	 * @return
	 */
	public boolean checkReturn(String workItemId, String taskName);
}
