package com.qfw.bean.payout;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.bizservice.custinfo.account.ICustAccountBS;
import com.qfw.bizservice.payout.IWithdrawalsPayoutBS;
import com.qfw.common.util.web.ViewOper;
import com.qfw.common.util.web.WorkFlowBackingBean;
import com.qfw.jbpm.model.JbpmConst;
import com.qfw.model.bo.BizAccountBO;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.vo.custinfo.account.AccountRequestVO;
import com.qfw.model.vo.payout.WithdrawalsVO;

@ViewScoped
@ManagedBean(name="submitWithdrawalsBean")
public class SubmitWithdrawalsBackingBean extends WorkFlowBackingBean {

	private static final long serialVersionUID = -3666900489014364511L;

	@ManagedProperty(value = "#{withdrawalsPayoutBS}")
	private IWithdrawalsPayoutBS payoutBS;
	@ManagedProperty(value = "#{custAccountBS}")
	private ICustAccountBS custAccountBS;
	@ManagedProperty(value = "#{custInfoBS}")
	private ICustInfoBS custInfoBS;
	
	private WithdrawalsVO infoVO = new WithdrawalsVO();
	private String comment;
	private String taskid; 
	private String workItemId;
	
	private String accountId = new String();
	private  List<SelectItem> accountList = new ArrayList<SelectItem>();
	private List<BizAccountBO> accountBOList = new ArrayList<BizAccountBO>();
	
	public String his ;
	
	@PostConstruct
    public void init(){
		his = ViewOper.getParameter("his");
		taskid = ViewOper.getParameter("taskid");
		workItemId = ViewOper.getParameter("workItemId");
		if(null!=taskid&&null!=workItemId){
			infoVO.setWorkItemId(workItemId);
	    	infoVO = this.payoutBS.findVOInfoByVO(infoVO);
	    	setAuditOpinionList(this.payoutBS.getAuditOpinion(workItemId));
		}else{
			try {
				BizCustomerBO cust  = custInfoBS.findCustByUserId(ViewOper.getUser().getUserId());
				AccountRequestVO requestVO = new AccountRequestVO();
				requestVO.setCustId(cust.getId());
				accountBOList = this.custAccountBS.findInfoByParams(requestVO);
				if(null!=accountBOList && accountBOList.size()>0){
					for (BizAccountBO bo : accountBOList) {
						accountList.add(new SelectItem(bo.getId().toString(),bo.getAccount()));
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		
    }
	
	 /**
     * 保存提现申请基本信息
     * @return
     */
    public String addWithdraWals(){
		
		try {
			for (BizAccountBO bo : accountBOList) {
				if(bo.getId().toString().equals(accountId)){
					infoVO.setCustId(bo.getCustId());
					infoVO.setOutAccount(bo.getAccount());
				}
			}
			String mess = validateVO();
			if(mess.length()<=0){
				this.payoutBS.saveWithdrawalsApply(infoVO);
				mess = "提交成功";
			}
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,mess, null);
    		FacesContext.getCurrentInstance().addMessage(null, message);
		} catch (Exception e) {
			alert("保存提现申请异常："+ e);
		}
		return null;
	}
    
    /**
     * 提交成功
     */
    public String submit(){
    	try {
			this.payoutBS.tranTakeAndCompleteTask(taskid, "提交", infoVO,
					ViewOper.getUser(), JbpmConst.AUDIT_STATUS_CD_YES, false,null);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
    				"提交成功", null);
    		FacesContext.getCurrentInstance().addMessage(null, message);
		} catch (Exception e) {
			alert("提现申请异常："+ e);
		}
    	return null;
    }
    
    /**
     * 撤销
     */
    public String reject(){
    	try {
			this.payoutBS.tranTakeAndCompleteTask(taskid, "撤销", infoVO,
					ViewOper.getUser(), JbpmConst.AUDIT_STATUS_CD_REVOKE, true, getHtml());
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
    				"撤销成功", null);
    		FacesContext.getCurrentInstance().addMessage(null, message);
		} catch (Exception e) {
			alert("提现申请拒绝异常："+ e);
		}
    	return null;
    }
    
    /**
     * 数据校验
     * @return
     */
    private String validateVO(){
    	StringBuffer mess = new StringBuffer();
    	if(null == infoVO){
    		mess.append("提现申请信息为空!<br/>");
    	}
    	if(null == infoVO.getOutAccount() || infoVO.getOutAccount().length()<=0){
    		mess.append("请选择申请账号<br/>");
    	}
    	if(null == infoVO.getDealAmt()|| infoVO.getDealAmt().compareTo(BigDecimal.ZERO)<=0){
    		mess.append("申请金额必须大于0<br/>");
    	}
    	return mess.toString();
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


	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getWorkItemId() {
		return workItemId;
	}

	public void setWorkItemId(String workItemId) {
		this.workItemId = workItemId;
	}

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public ICustAccountBS getCustAccountBS() {
		return custAccountBS;
	}

	public void setCustAccountBS(ICustAccountBS custAccountBS) {
		this.custAccountBS = custAccountBS;
	}

	public ICustInfoBS getCustInfoBS() {
		return custInfoBS;
	}

	public void setCustInfoBS(ICustInfoBS custInfoBS) {
		this.custInfoBS = custInfoBS;
	}

	public List<SelectItem> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<SelectItem> accountList) {
		this.accountList = accountList;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public List<BizAccountBO> getAccountBOList() {
		return accountBOList;
	}

	public void setAccountBOList(List<BizAccountBO> accountBOList) {
		this.accountBOList = accountBOList;
	}

	public String getHis() {
		return his;
	}

	public void setHis(String his) {
		this.his = his;
	}
	
}
