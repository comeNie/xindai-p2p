package com.qfw.bean.loan;

/**
 * 借款申请信息Bean
 * 
 * @author qswei
 */

import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.jbpm.api.task.Task;

import com.qfw.bizservice.bussparams.IProductInfoBS;
import com.qfw.bizservice.credit.ICreditLimitApplyBS;
import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.bizservice.loan.ILoanApplyBS;
import com.qfw.common.bizservice.IParamBS;
import com.qfw.common.bizservice.permission.IRoleBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.permission.SysRole;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.web.ViewOper;
import com.qfw.common.util.web.WorkFlowBackingBean;
import com.qfw.jbpm.bizservice.JbpmService;
import com.qfw.jbpm.model.JbpmConst;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizCreditReportBO;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.bo.BizLoanApplyBO;
import com.qfw.model.vo.bussparams.ProductInfoVO;
import com.qfw.model.vo.credit.BizCreditLimitApplyVO;
import com.qfw.model.vo.custinfo.basicInfo.CustBasicInfoVO;
import com.qfw.model.vo.custinfo.custAuth.CustAuthInfoVO;
import com.qfw.model.vo.loan.LoanApplyVO;

@ViewScoped
@ManagedBean(name = "approvalLoanBean")
public class ApprovalLoanBackingBean extends WorkFlowBackingBean {

	private static final long serialVersionUID = -2293601672836954353L;

	@ManagedProperty(value = "#{loanApplyBS}")
	private ILoanApplyBS loanApplyBS;
	@ManagedProperty(value = "#{creditLimitApplyBS}")
	private ICreditLimitApplyBS creditLimitApplyBS;
	@ManagedProperty(value = "#{jbpmService}")
	private JbpmService jbpmService;
	@ManagedProperty(value = "#{productInfoBS}")
	private IProductInfoBS productInfoBS;
	@ManagedProperty(value = "#{paramBS}")
	private IParamBS paramBS;
	@ManagedProperty(value = "#{roleBS}")
	private IRoleBS roleBS;
	@ManagedProperty(value = "#{custInfoBS}")
	private ICustInfoBS custInfoBS;

	private LoanApplyVO loanApplyVO = new LoanApplyVO();
	private CustBasicInfoVO custBasicVO = new CustBasicInfoVO();
	private CustAuthInfoVO custAuthVO = new CustAuthInfoVO();
	private ProductInfoVO productInfoVO = new ProductInfoVO();
	private BizCreditLimitApplyVO creditLimitApplyVO = new BizCreditLimitApplyVO();
	private SysUser selectPartyUser = new SysUser();
	
	private BizCreditReportBO creditReportVO = new BizCreditReportBO();

	private String taskid;
	private String workItemId;

	private boolean finalApp = false; // 是否最终放行

	private SysRole nextRole;// 下一审核角色
	private SysRole curRole;// 当前审核角色

	public boolean returnFlag = false;
	
	public String his ;
	
	@PostConstruct
	public void init() {
		try {
			his = ViewOper.getParameter("his");
			if (taskid == null) {
				taskid = ViewOper.getParameter("taskid");
			}
			if (taskid == null) {
				return;
			}
			if (workItemId == null) {
				workItemId = ViewOper.getParameter("workItemId");
			}
			if (workItemId == null) {
				return;
			}
			Task task = jbpmService.getTaskById(taskid);
			String taskName = "";
			if (AppConst.WORKITEM_ROLE_ACCOUNT_TASK.equals(task.getName())) {// 获取当前任务名称:
																				// 客户经理岗
				curRole = roleBS.findSysRole(null,
						AppConst.WORKITEM_ROLE_ACCOUNT);// 当前角色
				nextRole = roleBS
						.findSysRole(null, AppConst.WORKITEM_ROLE_RISK);// 获取风控岗
				getFilterUser()
						.setRoleIds(String.valueOf(nextRole.getRoleId()));
				taskName = AppConst.WORKITEM_ROLE_RISK_TASK;
			} else if (AppConst.WORKITEM_ROLE_RISK_TASK.equals(task.getName())) {// 风控岗审核
				curRole = roleBS.findSysRole(null, AppConst.WORKITEM_ROLE_RISK);// 当前角色
				nextRole = roleBS
						.findSysRole(null, AppConst.WORKITEM_ROLE_COMP);// 获取合规岗角色
				getFilterUser()
						.setRoleIds(String.valueOf(nextRole.getRoleId()));
				taskName = AppConst.WORKITEM_ROLE_COMP_TASK;
			} else if (AppConst.WORKITEM_ROLE_COMP_TASK.equals(task.getName())) {// 获取当前任务名称
				curRole = roleBS.findSysRole(null, AppConst.WORKITEM_ROLE_COMP);// 获取合规岗角色
				finalApp = true;
			}

			setAuditOpinionList(this.loanApplyBS.getAuditOpinion(workItemId));

			loanApplyVO.setWorkItemId(workItemId);
			loanApplyVO = this.loanApplyBS.findVOByParams(loanApplyVO);
			productInfoVO = this.productInfoBS.findProductInfoById(loanApplyVO
					.getProductId());

			if (null != loanApplyVO.getTrusteeCustId()
					&& loanApplyVO.getTrusteeCustId() != 0) {
				BizCustomerBO cust = this.custInfoBS.findCustById(loanApplyVO.getTrusteeCustId());
				selectPartyUser = getUserBS().findUserByUserId(cust.getUserId());
			}

			String advanceRate = paramBS
					.getParam(AppConst.PARAMETER_CODE_TQHKWYJBL);
			String delayRate = paramBS.getParam(AppConst.PARAMETER_CODE_ZQSFBL);
			String overdueRate = paramBS
					.getParam(AppConst.PARAMETER_CODE_YQSFBL);
			if (advanceRate == null || "".equals(advanceRate)
					|| delayRate == null || "".equals(delayRate)
					|| overdueRate == null || "".equals(overdueRate)) {
				this.alert("借款申请信息参数获取失败");
				return;
			}
			loanApplyVO.setAdvanceRate(new BigDecimal(advanceRate));
			loanApplyVO.setDelayRate(new BigDecimal(delayRate));
			loanApplyVO.setOverdueRate(new BigDecimal(overdueRate));
			// 初始化认证信息
			this.loanApplyBS.initLoanApplyAuth(loanApplyVO);

			// 初始化标签页信息
			this.creditLimitApplyBS.initCreditLimitApplyVOByCustId(
					loanApplyVO.getCustId(), creditLimitApplyVO);
			creditReportVO = this.loanApplyBS.initCreditReportVO(loanApplyVO.getCustId());
			
			if(!finalApp){
				returnFlag = this.loanApplyBS.checkReturn(workItemId, taskName);
			}
		} catch (Exception e) {
			this.alert("借款申请异常：" + e.getMessage());
		}

	}
	
	/**
	 * 提交成功
	 */
	public String submit() {
		try {
			String appNode = AppConst.WORKITEM_NODE_ONE;
			if (finalApp) {
				appNode = AppConst.WORKITEM_NODE_TWO;
			}
			this.loanApplyBS.tranTakeAndCompleteTask(taskid, "通过", loanApplyVO,
					getSelectUser(), curRole, JbpmConst.AUDIT_STATUS_CD_YES,
					appNode,getHtml());
			
			this.alertInfo("借款申请提交成功");
			return "myTasks";
		} catch (Exception e) {
			alert("借款申请提交异常：" + e);
		}
		return null;
	}

	/**
	 * 拒绝
	 */
	public String reject() {
		try {
			String appNode = AppConst.WORKITEM_NODE_ONE;
			if (finalApp) {
				appNode = AppConst.WORKITEM_NODE_TWO;
			}
			this.loanApplyBS.tranTakeAndCompleteTask(taskid, "退回", loanApplyVO,
					null, curRole, JbpmConst.AUDIT_STATUS_CD_NO, appNode,null);
			
			this.alertInfo("退回成功");
			return "myTasks";
		} catch (Exception e) {
			alert("借款申请拒绝异常：" + e);
		}
		return null;
	}

	/**
	 * 查询借款申请基本信息
	 * 
	 * @return
	 */
	public void getLoanApplyInfo() throws BizException {
		try {
			int tempValue = 1001;
			// 查看借款申请信息
			BizLoanApplyBO loanApply = this.loanApplyBS.getLoanApply(tempValue);
			// 获取客户基本信息
			BizCustomerBO custInfo = this.creditLimitApplyBS
					.getCustomerInfo(tempValue);
			loanApplyVO.setApplyAmt(loanApply.getApplyAmt());
			loanApplyVO.setApplyDate(loanApply.getApplyDate());
			loanApplyVO.setApplyStatusCd(loanApply.getApplyStatusCd());
			loanApplyVO.setCustId(loanApply.getCustId());
			loanApplyVO.setExpectLoanRate(loanApply.getExpectLoanRate());
			loanApplyVO.setLoanName(loanApply.getLoanName());
			loanApplyVO.setLoanPurpose(loanApply.getLoanPurpose());
			loanApplyVO.setLoanTerm(loanApply.getLoanTerm());
			loanApplyVO.setLoanTypeCd(loanApply.getLoanTypeCd());
			loanApplyVO.setRemark(loanApply.getRemark());
			loanApplyVO.setWorkItemId(loanApply.getWorkItemId());
			loanApplyVO.setTermUnitCd(loanApply.getTermUnitCd());
			loanApplyVO.setTenderTerm(loanApply.getTenderTerm());
			loanApplyVO.setRepayTypeCd(loanApply.getRepayTypeCd());
		} catch (BizException e) {
			alert("查看借款申请信息失败：" + e);
		}
	}

	public ILoanApplyBS getLoanApplyBS() {
		return loanApplyBS;
	}

	public void setLoanApplyBS(ILoanApplyBS loanApplyBS) {
		this.loanApplyBS = loanApplyBS;
	}

	public LoanApplyVO getLoanApplyVO() {
		return loanApplyVO;
	}

	public void setLoanApplyVO(LoanApplyVO loanApplyVO) {
		this.loanApplyVO = loanApplyVO;
	}

	public ICreditLimitApplyBS getCreditLimitApplyBS() {
		return creditLimitApplyBS;
	}

	public void setCreditLimitApplyBS(ICreditLimitApplyBS creditLimitApplyBS) {
		this.creditLimitApplyBS = creditLimitApplyBS;
	}

	public CustBasicInfoVO getCustBasicVO() {
		return custBasicVO;
	}

	public void setCustBasicVO(CustBasicInfoVO custBasicVO) {
		this.custBasicVO = custBasicVO;
	}

	public CustAuthInfoVO getCustAuthVO() {
		return custAuthVO;
	}

	public void setCustAuthVO(CustAuthInfoVO custAuthVO) {
		this.custAuthVO = custAuthVO;
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

	public JbpmService getJbpmService() {
		return jbpmService;
	}

	public void setJbpmService(JbpmService jbpmService) {
		this.jbpmService = jbpmService;
	}

	public boolean isFinalApp() {
		return finalApp;
	}

	public void setFinalApp(boolean finalApp) {
		this.finalApp = finalApp;
	}

	public IProductInfoBS getProductInfoBS() {
		return productInfoBS;
	}

	public void setProductInfoBS(IProductInfoBS productInfoBS) {
		this.productInfoBS = productInfoBS;
	}

	public ProductInfoVO getProductInfoVO() {
		return productInfoVO;
	}

	public void setProductInfoVO(ProductInfoVO productInfoVO) {
		this.productInfoVO = productInfoVO;
	}

	public IParamBS getParamBS() {
		return paramBS;
	}

	public void setParamBS(IParamBS paramBS) {
		this.paramBS = paramBS;
	}

	public BizCreditLimitApplyVO getCreditLimitApplyVO() {
		return creditLimitApplyVO;
	}

	public void setCreditLimitApplyVO(BizCreditLimitApplyVO creditLimitApplyVO) {
		this.creditLimitApplyVO = creditLimitApplyVO;
	}

	public IRoleBS getRoleBS() {
		return roleBS;
	}

	public void setRoleBS(IRoleBS roleBS) {
		this.roleBS = roleBS;
	}

	public SysRole getNextRole() {
		return nextRole;
	}

	public void setNextRole(SysRole nextRole) {
		this.nextRole = nextRole;
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

	public BizCreditReportBO getCreditReportVO() {
		return creditReportVO;
	}

	public void setCreditReportVO(BizCreditReportBO creditReportVO) {
		this.creditReportVO = creditReportVO;
	}

	public boolean isReturnFlag() {
		return returnFlag;
	}

	public void setReturnFlag(boolean returnFlag) {
		this.returnFlag = returnFlag;
	}

	public ICustInfoBS getCustInfoBS() {
		return custInfoBS;
	}

	public void setCustInfoBS(ICustInfoBS custInfoBS) {
		this.custInfoBS = custInfoBS;
	}

	public String getHis() {
		return his;
	}

	public void setHis(String his) {
		this.his = his;
	}

}
