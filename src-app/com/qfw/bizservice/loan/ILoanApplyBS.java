package com.qfw.bizservice.loan;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.permission.SysRole;
import com.qfw.common.model.permission.SysUser;
import com.qfw.jbpm.model.bo.Jbpm4AuditOpinion;
import com.qfw.model.bo.BizCreditReportBO;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.bo.BizLoanApplyBO;
import com.qfw.model.bo.BizLoanApproveBO;
import com.qfw.model.bo.BizLoanBO;
import com.qfw.model.vo.custinfo.CustInfoVO;
import com.qfw.model.vo.loan.LoanApplyVO;

public interface ILoanApplyBS extends IBaseService{
	
	/**
	 * 保存借款申请信息(借款申请自身需要调用工作流)
	 * @param loanApplyVO
	 * @param auditUser
	 * @throws BizException
	 */
	public void saveLoanApplyForFlow(LoanApplyVO loanApplyVO,SysUser auditUser, SysRole auditRole)throws BizException;
	
	/**
	 * 保存借款申请信息(自身不需要调用工作流)
	 * @param loanApplyVO
	 * @param cust
	 * @param workItemId
	 * @throws BizException
	 */
	public void saveLoanApply(LoanApplyVO loanApplyVO,CustInfoVO cust,String workItemId) throws BizException;
	
	/**
	 * 工作流流转
	 * @param taskId
	 * @param to
	 * @param loanApplyVO
	 * @param auditUser
	 * @param auditStatus
	 * @param appNode	
	 * @throws BizException
	 */
	public void tranTakeAndCompleteTask(String taskId, String to,LoanApplyVO loanApplyVO, SysUser auditUser, SysRole auditRole,
			String auditStatus, String appNode, String html) throws BizException;

	/**
	 * 更新借款申请信息（工作流）
	 * @param loanApplyVO
	 * @param auditUser
	 * @param auditStatus
	 * @param appNode
	 * @throws BizException
	 */
	public void updateLoanApplyByTask(LoanApplyVO loanApplyVO, SysUser auditUser, BizCustomerBO cust,String auditStatus, String appNode) throws BizException;
	
	/**
	 * 查看借款申请信息
	 */
	public BizLoanApplyBO getLoanApply(int custId) throws BizException;
	
	/**
	 * 获取借款申请信息：工作流
	 * @param loanApplyVO
	 * @return
	 */
	public LoanApplyVO findVOByParams(LoanApplyVO loanApplyVO);
	
	/**
	 * 获取审批意见信息
	 * @param workItemId
	 * @return
	 */
	public List<Jbpm4AuditOpinion> getAuditOpinion(String workItemId);
	
	/**
	 * 获取借款发布信息
	 */
	public LoanApplyVO findLoanApply(int loanId)throws BizException;
	
	/**
	 * 查看借款发布信息
	 */
	public BizLoanApproveBO getLoanApprove(int loanId) throws BizException;
	
	/**
	 * 查看借据信息 
	 */
	public BizLoanBO getLoan(int loanId) throws BizException;
	
	/**
	 * 通过客户ID，初始化认证信息
	 * @param loanApplyVO
	 * @throws BizException
	 */
	public void initLoanApplyAuth(LoanApplyVO loanApplyVO) throws BizException;
	
	/**
	 * 前台提交借款申请
	 * @param vo
	 */
	public void submitLoanApply(LoanApplyVO loanApplyVO) throws BizException;

	/**
	 * 保存前台提交借款申请
	 * @param loanApplyVO
	 * @param cust
	 * @param workItemId
	 * @throws BizException
	 */
	public void savePreLoanApply(LoanApplyVO loanApplyVO, CustInfoVO cust,
			String workItemId) throws BizException;
	
	/**
	 * 初始化信用报告信息
	 * @param reportVO
	 * @param custId
	 */
	public BizCreditReportBO initCreditReportVO(String custId);

	/**
	 * 检查是否流程被退回
	 * @param workItemId
	 * @param taskName
	 * @return
	 */
	public boolean checkReturn(String workItemId,String taskName);
	
	/**
	 * 借款申请提交客户经理处理
	 * @param loanIntentionId
	 * @param auditUser
	 * @throws BizException
	 */
	public void submitCustManage(Integer loanIntentionId,SysUser auditUser) throws BizException;

	/**
	 * 接口意向置失效
	 * @param loanIntentionId 借款意向id
	 * @param refuseReason 拒绝遇见
	 * @throws BizException 
	 */
	public void setloanIntentionDisValid(Integer loanIntentionId,String refuseReason) throws BizException;

}
