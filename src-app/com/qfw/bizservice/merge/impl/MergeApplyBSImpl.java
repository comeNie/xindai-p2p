package com.qfw.bizservice.merge.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qfw.bizservice.credit.ICreditBS;
import com.qfw.bizservice.credit.ICreditLimitApplyBS;
import com.qfw.bizservice.credit.report.ICreditReportBS;
import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.bizservice.loan.ILoanApplyBS;
import com.qfw.bizservice.merge.IMergeApplyBS;
import com.qfw.common.bizservice.ISeqBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.bizservice.permission.IRoleBS;
import com.qfw.common.bizservice.permission.IUserBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.permission.SysRole;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.CollectionUtil;
import com.qfw.common.util.web.ViewOper;
import com.qfw.jbpm.bizservice.IFlowBS;
import com.qfw.jbpm.model.JbpmConst;
import com.qfw.jbpm.model.bo.Jbpm4AuditOpinion;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizCreditLimitApplyBO;
import com.qfw.model.bo.BizCreditLimitBO;
import com.qfw.model.bo.BizCreditUseBO;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.bo.BizLoanApplyBO;
import com.qfw.model.vo.credit.BizCreditLimitApplyVO;
import com.qfw.model.vo.credit.RequestCreditVO;
import com.qfw.model.vo.custinfo.CustInfoVO;
import com.qfw.model.vo.loan.LoanApplyVO;

@Service("mergeApplyBS")
public class MergeApplyBSImpl extends BaseServiceImpl implements IMergeApplyBS {

	private Logger log = Logger.getLogger(MergeApplyBSImpl.class);

	@Autowired
	@Qualifier("flowBS")
	private IFlowBS flowBS;
	@Autowired
	@Qualifier("roleBS")
	private IRoleBS roleBS;
	@Autowired
	private ILoanApplyBS loanApplyBS;
	@Autowired
	private ICreditLimitApplyBS creditLimitApplyBS;
	@Autowired
	private IUserBS userBS;
	@Autowired
	private ICreditBS creditBS;
	@Autowired
	private ICustInfoBS custInfoBS;
	@Autowired
	private ICreditReportBS creditReportBS;
	@Autowired
	public ISeqBS seqBS;

	@Override
	public boolean checkReturn(String workItemId,String taskName){
		return this.flowBS.isReturned(workItemId, taskName);
	}
	
	@Override
	public List<Jbpm4AuditOpinion> getAuditOpinion(String workItemId) {
		try {
			List<Jbpm4AuditOpinion> result = this.flowBS.getAuditOpinion(workItemId);
			return result;
		} catch (Exception e) {
			log.error("获取审批意见异常：",e);
		}
		return new ArrayList<Jbpm4AuditOpinion>();
	}
	
	/**
	 * 保存申请相关信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void saveMergeApply(BizCreditLimitApplyVO limitApplyVO,LoanApplyVO loanApplyVO,SysUser auditUser,SysRole auditRole) throws BizException {
		try{
			if(null == loanApplyVO|| null == limitApplyVO){
				throw new BizException("借款申请参数不能为空");
			}
			String name = "";
			CustInfoVO cust = null;
			if(null == loanApplyVO.getCustId()||loanApplyVO.getCustId().length()<=0){
//				user = ViewOper.getUser();
				throw new BizException("客户信息不能为空");
			}else{
				cust = custInfoBS.findCustInfoById(Integer.valueOf(loanApplyVO.getCustId()));
			}
			if(null == cust || null == cust.getCustName() || cust.getCustName().length()<=0){
				name = limitApplyVO.getCust().getCustName();
			}else{
				name = cust.getCustName();
			}
			
			String valiMess = validateVO(limitApplyVO,loanApplyVO);
			if(valiMess.length()>0){
				throw new BizException("借款申请失败："+valiMess);
			}

			//申请信息保存成功后调用风控岗审批工作流
			Map map = new HashMap();
			
			map.put(JbpmConst.APPLY_USER, ViewOper.getUser());
			map.put(JbpmConst.NEXT_AUDIT_USER, auditUser);
			map.put(JbpmConst.FLOW_REMARK, name+"借款申请，申请借款"+ loanApplyVO.getApplyAmt());
			String workItemId = flowBS.startProcessInstanceAndCompleteTask("limits_loan_apply", "提交", map);

			// 保存额度申请信息
			this.creditLimitApplyBS.saveCreditLimitApply(limitApplyVO, workItemId);
			
			// 第一次产生额度的时候需要进行借款金额的占用
			//占用额度
			List<BizCreditLimitBO> cls = getHibernateTemplate().find("from BizCreditLimitBO where relId = ? and relTypeCd = ? and CREDIT_STATE_CD = ?",
					String.valueOf(loanApplyVO.getCustId()),AppConst.CREDITLIMIT_RELTYPE_CUST,AppConst.CREDITLIMIT_STATUE_UNUSABLE);
			if(CollectionUtil.isNotEmpty(cls)){
				List<BizCreditUseBO> useBO = getHibernateTemplate().find("from BizCreditUseBO where clId = ? ",cls.get(0).getId());
				if(CollectionUtil.isNotEmpty(useBO)){
					BizCreditUseBO bo = useBO.get(0);
					bo.setPreTieupAmt(loanApplyVO.getApplyAmt());
					update(bo);
				}
			}
			
			// 保存借款申请信息
			this.loanApplyBS.saveLoanApply(loanApplyVO, cust, workItemId);
			
		}catch(Exception e){
			 
			log.error("借款申请基本信息保存失败，原因："+e);
			throw new BizException("借款申请基本信息保存失败，原因："+e.getMessage());
		}
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void tranTakeAndCompleteTask(String taskId, String to,BizCreditLimitApplyVO limitApplyVO, LoanApplyVO loanApplyVO,
			SysUser auditUser, SysRole auditRole, String auditStatus,String appNode,String html) throws BizException {
		try {
			
			boolean islast = false;
			boolean isSubmitAgain = false;
			if(AppConst.WORKITEM_NODE_TWO.equals(appNode)){
				islast = true;
			}
			BizCustomerBO cust = limitApplyVO.getCust();
			
			// 工作流
			Map<String, Object> paraMap = new HashMap<String, Object>();

			paraMap.put(JbpmConst.AUDIT_ROLE, auditRole);
			paraMap.put(JbpmConst.CUR_AUDIT_USER, ViewOper.getUser());
			paraMap.put(JbpmConst.NEXT_AUDIT_USER, auditUser);
			paraMap.put(JbpmConst.AUDIT_STATUS_CD, auditStatus);
			if((AppConst.WORKITEM_NODE_TWO.equals(appNode)&&"通过".equals(to))
					||(AppConst.WORKITEM_NODE_INIT.equals(appNode)&&"撤销".equals(to))){
				// 流程结束，成功或失败
				paraMap.put(JbpmConst.FLOW_HTML, html);
			}
			if("提交".equals(to)){
				paraMap.put(JbpmConst.FLOW_REMARK, cust.getCustName()+"借款申请，申请借款"+ loanApplyVO.getApplyAmt());
				flowBS.completeTask(taskId, to, paraMap);//重新提交时不带审批意见
				isSubmitAgain = true;
			}else{
				flowBS.completeTask(taskId,to, loanApplyVO.getComment(), paraMap);
			}
			
			
			if(null == limitApplyVO.getCreditLimitApply().getId()||limitApplyVO.getCreditLimitApply().getId()==0){
				// 保存额度申请信息
				this.creditLimitApplyBS.saveCreditLimitApply(limitApplyVO, loanApplyVO.getWorkItemId());
				
				List<BizCreditLimitBO> cls = getHibernateTemplate().find("from BizCreditLimitBO where relId = ? and relTypeCd = ? and CREDIT_STATE_CD = ?",
						String.valueOf(loanApplyVO.getCustId()),AppConst.CREDITLIMIT_RELTYPE_CUST,AppConst.CREDITLIMIT_STATUE_UNUSABLE);
				if(CollectionUtil.isNotEmpty(cls)){
					List<BizCreditUseBO> useBO = getHibernateTemplate().find("from BizCreditUseBO where clId = ? ",cls.get(0).getId());
					if(CollectionUtil.isNotEmpty(useBO)){
						BizCreditUseBO bo = useBO.get(0);
						bo.setPreTieupAmt(loanApplyVO.getApplyAmt());
						update(bo);
					}
				}else{
					throw new BizException("额度保存失败");
				}
				
				// 更新信用报告
				this.creditReportBS.updateApplyNum(loanApplyVO.getCustId(), 1, null, loanApplyVO.getApplyAmt());
			}else{
				// 更新额度申请信息
				limitApplyVO.getCreditLimitApply().setWorkItemId(loanApplyVO.getWorkItemId());
				this.creditLimitApplyBS.updateApplyForTake(limitApplyVO, auditUser, auditStatus, islast,isSubmitAgain);
			}
			
			// 更新借款申请
			this.loanApplyBS.updateLoanApplyByTask(loanApplyVO, auditUser,cust,auditStatus, appNode);
						
		} catch (Exception e) {
			log.error("借款申请审批异常：",e);
			throw new BizException(e.getMessage());
		}
	}
	
	/**
	 * 查看额度申请信息
	 */
	public BizLoanApplyBO getLoanApply(int custId) throws BizException{
		try{
			String queryString = "from BizCreditLimitApplyBO where creditLimitId = ?";
			List list = getHibernateTemplate().find(queryString, custId);
			if(list != null &&  !list.isEmpty()){
				return  (BizLoanApplyBO) list.get(0);
			} 
			return null;
		}catch(Exception e){
			 
		}
		return null;
		
	}
	
	/**
     * 数据校验
     * @return
     */
    private String validateVO(BizCreditLimitApplyVO limitApplyVO,LoanApplyVO loanApplyVO){
    	StringBuffer mess = new StringBuffer();
    	if(null == loanApplyVO){
    		mess.append("借款申请信息为空!<br/>");
    	}
    	if(null == limitApplyVO|| null == limitApplyVO.getCreditLimitApply() ){
    		mess.append("额度申请信息为空!<br/>");
    	}
    	if(null == limitApplyVO.getCreditLimitApply().getLimitApply() ){
    		mess.append("额度金额不能为空!<br/>");
    	}
    	if(null == loanApplyVO.getCustId() || loanApplyVO.getCustId().length()<=0){
    		mess.append("找不到客户信息<br/>");
    	}
    	if(null == loanApplyVO.getLoanName() || loanApplyVO.getLoanName().length()<=0){
    		mess.append("标题不能为空<br/>");
    	}
    	if(null == loanApplyVO.getLoanTypeCd() || loanApplyVO.getLoanTypeCd().length()<=0){
    		mess.append("标的类型不能为空<br/>");
    	}
    	if(null == loanApplyVO.getTemderCountAmt() 
    			|| (loanApplyVO.getTemderCountAmt().remainder(new BigDecimal(50))).compareTo(BigDecimal.ZERO)>0){
    		mess.append("每份金额必须为50的倍数!<br/>");
    	}
    	if(null == loanApplyVO.getApplyAmt() || loanApplyVO.getApplyAmt().compareTo(BigDecimal.ZERO)<=0){
    		mess.append("申请金额必须大于0<br/>");
    	}
    	if((loanApplyVO.getApplyAmt().remainder(loanApplyVO.getTemderCountAmt())).compareTo(BigDecimal.ZERO)>0){
    		mess.append("申请金额必须是每份金额的倍数<br/>");
    	}
    	if(limitApplyVO.getCreditLimitApply().getLimitApply().compareTo(loanApplyVO.getApplyAmt())<0 ){
    		mess.append("额度金额不能小于借款申请金额!<br/>");
    	}
    	if(null == loanApplyVO.getLoanTerm()||loanApplyVO.getLoanTerm()<=0){
    		mess.append("还款期限必须大于0<br/>");
    	}
    	if(null == loanApplyVO.getProductId()){
    		mess.append("所属产品信息不能为空<br/>");
    	}
    	if(null!=loanApplyVO.getPaymentWayCd() && loanApplyVO.getPaymentWayCd().length()>0){
    		if((null==loanApplyVO.getTrusteeCustId()||loanApplyVO.getTrusteeCustId()==0)
    				&& loanApplyVO.getPaymentWayCd().equals(AppConst.PAYMENT_WAY_CD_TRUSTEE)){
    			mess.append("支付方式为受托支付时，第三方用户必须有值<br/>");
    		}
    	}
    	return mess.toString();
    }
	
	public IFlowBS getFlowBS() {
		return flowBS;
	}


	public void setFlowBS(IFlowBS flowBS) {
		this.flowBS = flowBS;
	}

}
