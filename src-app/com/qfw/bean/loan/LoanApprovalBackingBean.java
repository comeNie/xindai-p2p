package com.qfw.bean.loan;

/**
 * 放款审批信息Bean
 * 
 * @author qswei
 */

import java.math.BigDecimal;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.qfw.bizservice.bussparams.IProductInfoBS;
import com.qfw.bizservice.loan.ILoanBS;
import com.qfw.common.bizservice.IParamBS;
import com.qfw.common.bizservice.permission.IRoleBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.permission.SysRole;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.web.ViewOper;
import com.qfw.common.util.web.WorkFlowAndPartyUserBackingBean;
import com.qfw.model.AppConst;
import com.qfw.model.vo.bussparams.ProductInfoVO;

@ViewScoped
@ManagedBean(name = "loanApprovalBean")
public class LoanApprovalBackingBean extends WorkFlowAndPartyUserBackingBean {

	private static final long serialVersionUID = -2293601672836954353L;

	@ManagedProperty(value = "#{loanBS}")
	private ILoanBS loanBS;
	@ManagedProperty(value = "#{productInfoBS}")
	private IProductInfoBS productInfoBS;
	@ManagedProperty(value = "#{roleBS}")
	private IRoleBS roleBS;
	@ManagedProperty(value = "#{paramBS}")
	private IParamBS paramBS;
	
	private Map data;
	private ProductInfoVO productInfoVO = new ProductInfoVO();
	private String taskid;
	private String workItemId;
	private SysRole curRole;// 当前审核角色
	private SysUser selectPartyUser = new SysUser();
	@PostConstruct
	public void init() {
		try {
			if(null == taskid){
				taskid = ViewOper.getParameter("taskid");
			}
			if(null == workItemId){
				workItemId = ViewOper.getParameter("workItemId");
			}
			if(null==taskid||null==workItemId){
				return ;
			}
			data = loanBS.findLoanApproveByWorkItemId(workItemId);
			productInfoVO = this.productInfoBS.findProductInfoById(Integer.valueOf((String)data.get("PRODUCT_ID")));
			if (null != data.get("TRUSTEE_CUST_ID") &&  (Integer)data.get("TRUSTEE_CUST_ID") != 0) {
				selectPartyUser = getUserBS().findUserById( (Integer)data.get("TRUSTEE_CUST_ID"));
			}
			
			// 初始化参数信息
			String advanceRate = paramBS.getParam(AppConst.PARAMETER_CODE_TQHKWYJBL);
			String delayRate = paramBS.getParam(AppConst.PARAMETER_CODE_ZQSFBL);
			String overdueRate = paramBS.getParam(AppConst.PARAMETER_CODE_YQSFBL);
			
			if(null!=data){
				//违约利率
				data.put("ADVANCE_RATE", null==overdueRate?BigDecimal.ZERO:new BigDecimal(advanceRate));
				//展期加罚率
				data.put("DELAY_RATE", null==overdueRate?BigDecimal.ZERO:new BigDecimal(delayRate));
				//逾期加罚率
				data.put("OVERDUE_RATE", null==overdueRate?BigDecimal.ZERO:new BigDecimal(overdueRate));
			}
		} catch (Exception e) {
			this.alert( "放款申请异常：" + e.getMessage());
		}

	}

	public String disagree(){
		try {
			curRole = roleBS.findSysRole(null,AppConst.WORKITEM_ROLE_FINANICAL);
			loanBS.completeLoan((Integer)data.get("ID"), false, taskid,ViewOper.getUser(),curRole, String.valueOf(data.get("COMMENT")),getHtml());
		} catch (BizException e) {
			this.alert(e.getMessage());
			return null;
		}
		return "myTasks";
	}
	
	public String agree(){
		try {
			curRole = roleBS.findSysRole(null,AppConst.WORKITEM_ROLE_FINANICAL);
			loanBS.completeLoan((Integer)data.get("ID"), true, taskid,ViewOper.getUser(), curRole,String.valueOf(data.get("COMMENT")),getHtml());
		} catch (BizException e) {
			this.alert(e.getMessage());
			return null;
		}
		return "myTasks";
	}
	
	public ILoanBS getLoanBS() {
		return loanBS;
	}

	public void setLoanBS(ILoanBS loanBS) {
		this.loanBS = loanBS;
	}

	public Map getData() {
		return data;
	}

	public void setData(Map data) {
		this.data = data;
	}

	public ProductInfoVO getProductInfoVO() {
		return productInfoVO;
	}

	public void setProductInfoVO(ProductInfoVO productInfoVO) {
		this.productInfoVO = productInfoVO;
	}

	public IProductInfoBS getProductInfoBS() {
		return productInfoBS;
	}

	public void setProductInfoBS(IProductInfoBS productInfoBS) {
		this.productInfoBS = productInfoBS;
	}

	public IRoleBS getRoleBS() {
		return roleBS;
	}

	public void setRoleBS(IRoleBS roleBS) {
		this.roleBS = roleBS;
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

	public SysRole getCurRole() {
		return curRole;
	}

	public void setCurRole(SysRole curRole) {
		this.curRole = curRole;
	}

	public SysUser getSelectPartyUser() {
		return selectPartyUser;
	}

	public void setSelectPartyUser(SysUser selectPartyUser) {
		this.selectPartyUser = selectPartyUser;
	}

	public IParamBS getParamBS() {
		return paramBS;
	}

	public void setParamBS(IParamBS paramBS) {
		this.paramBS = paramBS;
	}
	
	
}
