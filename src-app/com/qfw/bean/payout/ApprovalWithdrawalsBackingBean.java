package com.qfw.bean.payout;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.qfw.bizservice.payout.IWithdrawalsPayoutBS;
import com.qfw.common.util.web.ViewOper;
import com.qfw.common.util.web.WorkFlowBackingBean;
import com.qfw.jbpm.bizservice.JbpmService;
import com.qfw.jbpm.model.JbpmConst;
import com.qfw.model.vo.payout.WithdrawalsVO;

@ViewScoped
@ManagedBean(name="approvalWithdrawalsBean")
public class ApprovalWithdrawalsBackingBean extends WorkFlowBackingBean {

	private static final long serialVersionUID = -3666900489014364511L;

	@ManagedProperty(value = "#{withdrawalsPayoutBS}")
	private IWithdrawalsPayoutBS payoutBS;
	@ManagedProperty(value = "#{jbpmService}")
    private JbpmService jbpmService;
	
	
	private WithdrawalsVO infoVO = new WithdrawalsVO();
	private String comment;
	private String taskid;
	private String workItemId;
	
	public String his ;
	@PostConstruct
    public void init(){
		his = ViewOper.getParameter("his");
    	if(taskid == null){
    		taskid = ViewOper.getParameter("taskid");
    	}
    	if(taskid == null){
    		return;
    	}
    	if(workItemId == null){
    		workItemId = ViewOper.getParameter("workItemId");
    	}
    	if(workItemId == null){
    		return;
    	}
    	infoVO.setWorkItemId(workItemId);
    	infoVO = this.payoutBS.findVOInfoByVO(infoVO);
    	
    	setAuditOpinionList(this.payoutBS.getAuditOpinion(workItemId));
    }
	 
    /**
     * 提交成功
     */
    public String submit(){
    	try {
			this.payoutBS.tranTakeAndCompleteTask(taskid, "通过", infoVO,
					ViewOper.getUser(), JbpmConst.AUDIT_STATUS_CD_YES, true, getHtml());
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
    				"提交成功", null);
    		FacesContext.getCurrentInstance().addMessage(null, message);
    		return "myTasks";
		} catch (Exception e) {
			 
			alert("提现申请异常："+ e);
		}
    	return null;
    }
    
    /**
     * 拒绝
     */
    public String reject(){
    	try {
			this.payoutBS.tranTakeAndCompleteTask(taskid, "退回", infoVO,
					ViewOper.getUser(), JbpmConst.AUDIT_STATUS_CD_NO, false,null);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
    				"拒绝成功", null);
    		FacesContext.getCurrentInstance().addMessage(null, message);
    		return "myTasks";
		} catch (Exception e) {
			alert("提现申请拒绝异常："+ e);
		}
    	return null;
    }

	public IWithdrawalsPayoutBS getPayoutBS() {
		return payoutBS;
	}

	public void setPayoutBS(IWithdrawalsPayoutBS payoutBS) {
		this.payoutBS = payoutBS;
	}

	public WithdrawalsVO getInfoVO() {
		return infoVO;
	}

	public void setInfoVO(WithdrawalsVO infoVO) {
		this.infoVO = infoVO;
	}

	public JbpmService getJbpmService() {
		return jbpmService;
	}

	public void setJbpmService(JbpmService jbpmService) {
		this.jbpmService = jbpmService;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public String getWorkItemId() {
		return workItemId;
	}

	public void setWorkItemId(String workItemId) {
		this.workItemId = workItemId;
	}

	public String getHis() {
		return his;
	}

	public void setHis(String his) {
		this.his = his;
	}
	
}
