package com.qfw.bean.loan;

/**
 * 借款申请信息Bean
 * 
 * @author qswei
 */

import java.math.BigDecimal;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.jbpm.api.task.Task;

import com.qfw.bizservice.bussparams.IProductInfoBS;
import com.qfw.bizservice.credit.ICreditBS;
import com.qfw.bizservice.credit.ICreditLimitApplyBS;
import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.bizservice.loan.ILoanApplyBS;
import com.qfw.common.bizservice.IParamBS;
import com.qfw.common.bizservice.permission.IRoleBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.permission.SysRole;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.web.ViewOper;
import com.qfw.common.util.web.WorkFlowAndPartyUserBackingBean;
import com.qfw.jbpm.bizservice.JbpmService;
import com.qfw.jbpm.model.JbpmConst;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizCreditReportBO;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.vo.bussparams.ProductInfoVO;
import com.qfw.model.vo.credit.BizCreditLimitApplyVO;
import com.qfw.model.vo.credit.RequestCreditVO;
import com.qfw.model.vo.custinfo.CustInfoVO;
import com.qfw.model.vo.custinfo.basicInfo.CustBasicInfoVO;
import com.qfw.model.vo.custinfo.custAuth.CustAuthInfoVO;
import com.qfw.model.vo.loan.LoanApplyVO;

@ViewScoped
@ManagedBean(name = "loanApplyBean")
public class LoanApplyBackingBean extends WorkFlowAndPartyUserBackingBean {

	private static final long serialVersionUID = -2293601672836954353L;

	@ManagedProperty(value = "#{loanApplyBS}")
	private ILoanApplyBS loanApplyBS;
	@ManagedProperty(value = "#{creditLimitApplyBS}")
	private ICreditLimitApplyBS creditLimitApplyBS;
	@ManagedProperty(value = "#{productInfoBS}")
	private IProductInfoBS productInfoBS;
	@ManagedProperty(value = "#{paramBS}")
	private IParamBS paramBS;
	@ManagedProperty(value = "#{creditBS}")
	private ICreditBS creditBS;
	@ManagedProperty(value = "#{custInfoBS}")
	private ICustInfoBS custInfoBS;
	@ManagedProperty(value = "#{roleBS}")
	private IRoleBS roleBS;
	@ManagedProperty(value = "#{jbpmService}")
	private JbpmService jbpmService;

	private LoanApplyVO loanApplyVO = new LoanApplyVO();
	private CustBasicInfoVO custBasicVO = new CustBasicInfoVO();
	private CustAuthInfoVO custAuthVO = new CustAuthInfoVO();
	private ProductInfoVO productInfoVO = new ProductInfoVO();
	private BizCreditLimitApplyVO creditLimitApplyVO = new BizCreditLimitApplyVO();

	private BizCreditReportBO creditReportVO = new BizCreditReportBO();
	
	private String taskid;
	private String workItemId;

	private String showMessage = "";

	private SysRole nextRole;// 下一审核角色
	private SysRole curRole;// 当前审核角色
	
	public Boolean canBeSubmit = true;//是否能提交。
	public boolean returnFlag = false;
	
	public String his ;

	@PostConstruct
	public void init() {
		try {
			his = ViewOper.getParameter("his");
			// 初始化参数信息
			String advanceRate = paramBS
					.getParam(AppConst.PARAMETER_CODE_TQHKWYJBL);
			String delayRate = paramBS.getParam(AppConst.PARAMETER_CODE_ZQSFBL);
			String overdueRate = paramBS
					.getParam(AppConst.PARAMETER_CODE_YQSFBL);
			if (advanceRate == null || "".equals(advanceRate)
					|| delayRate == null || "".equals(delayRate)
					|| overdueRate == null || "".equals(overdueRate)) {
				this.alertInfo("借款申请信息参数获取失败");
				return;
			}
			String minTenderAmt = paramBS.getParam(AppConst.PARM_MIN_TENDER_AMT);

			taskid = ViewOper.getParameter("taskid");
			workItemId = ViewOper.getParameter("workItemId");

			if (null != taskid && null != workItemId) {
				// creditLimitApplyVO =
				// this.creditLimitApplyBS.getCreditLimitApplyVOByWorkItemId(workItemId);
				Task task = jbpmService.getTaskById(taskid);
				loanApplyVO.setWorkItemId(workItemId);
				loanApplyVO = this.loanApplyBS.findVOByParams(loanApplyVO);
				productInfoVO = this.productInfoBS
						.findProductInfoById(loanApplyVO.getProductId());
				// 初始化标签页信息
				this.creditLimitApplyBS.initCreditLimitApplyVOByCustId(
						loanApplyVO.getCustId(), creditLimitApplyVO);
				setAuditOpinionList(this.loanApplyBS
						.getAuditOpinion(workItemId));
				
				returnFlag = this.loanApplyBS.checkReturn(workItemId,  AppConst.WORKITEM_ROLE_RISK_TASK);
			} else {
				Object custId = ViewOper.getSessionTmpAttr("custId");
				// Object businessType =
				// ViewOper.getSessionTmpAttr("businessType");
				Object productId = ViewOper.getSessionTmpAttr("productId");

				if (null != custId && (custId instanceof String)) {
					CustInfoVO cust = this.custInfoBS.findCustInfoById(Integer
							.valueOf((String) custId));
					if (null == cust) {
						this.alert("客户id：" + custId+ "获取失败");
					} else {
						loanApplyVO.setCustId(cust.getCustId().toString());
					}
					if (null != productId && (productId instanceof String)) {
						productInfoVO = this.productInfoBS
								.findProductInfoById(Integer
										.valueOf((String) productId));
					}
					// 初始化标签页信息
					this.creditLimitApplyBS.initCreditLimitApplyVOByCustId(
							loanApplyVO.getCustId(), creditLimitApplyVO);
				}
				loanApplyVO.setTemderCountAmt(new BigDecimal(minTenderAmt));
			}

			loanApplyVO.setAdvanceRate(new BigDecimal(advanceRate));
			loanApplyVO.setDelayRate(new BigDecimal(delayRate));
			loanApplyVO.setOverdueRate(new BigDecimal(overdueRate));

			// 初始化认证信息
			this.loanApplyBS.initLoanApplyAuth(loanApplyVO);

			// 初始化提示信息
			try {
				RequestCreditVO creditVO = new RequestCreditVO();
				creditVO.setRelId(loanApplyVO.getCustId());
				Map<String, BigDecimal> map = this.creditBS.surplusCreditAmt(
						creditVO, null);
				if (null == map || null == (map.get(loanApplyVO.getCustId()))) {
					showMessage = "客户额度信息获取失败!";
				} else {
					showMessage = "剩余额度：" + map.get(loanApplyVO.getCustId());
				}
			} catch (Exception e) {
			}

			// 重新发起的，当前角色也是客户经理岗
			curRole = roleBS.findSysRole(null, AppConst.WORKITEM_ROLE_ACCOUNT);// 当前角色
			nextRole = roleBS.findSysRole(null, AppConst.WORKITEM_ROLE_RISK);// 获取风控岗
			getFilterUser().setRoleIds(String.valueOf(nextRole.getRoleId()));
			if(null!=loanApplyVO.getCustId()){
				creditReportVO = this.loanApplyBS.initCreditReportVO(loanApplyVO.getCustId());
			}
		} catch (Exception e) {
			this.alert( "借款申请异常：" + e.getMessage());
		}

	}
	
	/**
	 * 执行方法跳转
	 * @return
	 */
	public String forWordSubmit(){
		if(null!=workItemId&&workItemId.length()>0){
			return submit();
		}else{
			return addLoanApply();
		}
	}

	/**
	 * 保存借款申请基本信息
	 * 
	 * @return
	 * @throws BizException
	 */
	public String addLoanApply() {
		try {
			SysUser partyUser = getSelectPartyUser();
			if (null != partyUser && null != partyUser.getUserId()
					&& partyUser.getUserId() != 0) {
				BizCustomerBO cust = this.custInfoBS.findCustByUserId(partyUser.getUserId());
				loanApplyVO.setTrusteeCustId(cust.getId());
			}
			loanApplyVO.setProductId(productInfoVO.getId());
			String mess = this.validateVO();
			if (mess.length() <= 0) {
				this.loanApplyBS.saveLoanApplyForFlow(loanApplyVO,
						getSelectUser(), curRole);
				this.alertInfo("借款申请成功");
				executeJS("alert('借款申请成功');closeParMainDialog();");
			}else{
				this.alert(mess);
				return null;
			}
		} catch (Exception e) {
			alert("借款申请基本信息保存失败，原因：" + e.getMessage());
		}
		return null;
	}

	/**
	 * 提交(重新提交)
	 */
	public String submit() {
		try {
			SysUser partyUser = getSelectPartyUser();
			if (null != partyUser && null != partyUser.getUserId()
					&& partyUser.getUserId() != 0) {
				BizCustomerBO cust = this.custInfoBS.findCustByUserId(partyUser.getUserId());
				loanApplyVO.setTrusteeCustId(cust.getId());
			}
			this.loanApplyBS.tranTakeAndCompleteTask(taskid, "提交", loanApplyVO,
					getSelectUser(), curRole, JbpmConst.AUDIT_STATUS_CD_YES,
					AppConst.WORKITEM_NODE_INIT,null);
			
			this.alertInfo("提交成功");
			return "myTasks";
		} catch (Exception e) {
			alert("借款申请提交异常：" + e);
		}
		return null;
	}

	/**
	 * 撤销
	 */
	public String reject() {
		try {

			this.loanApplyBS.tranTakeAndCompleteTask(taskid, "撤销", loanApplyVO,
					null, curRole, JbpmConst.AUDIT_STATUS_CD_REVOKE,
					AppConst.WORKITEM_NODE_INIT,getHtml());
			
			this.alertInfo("撤销成功");
			return "myTasks";
		} catch (Exception e) {
			alert("借款申请撤销异常：" + e);
		}
		return null;
	}

	/**
	 * 数据校验
	 * 
	 * @return
	 */
	private String validateVO() {
		StringBuffer mess = new StringBuffer();
		if (null == loanApplyVO || null == productInfoVO
				|| null == productInfoVO.getId()) {
			mess.append("借款申请信息为空!<br/>");
		}
		if (null == loanApplyVO.getCustId()
				|| loanApplyVO.getCustId().length() <= 0) {
			mess.append("找不到客户信息<br/>");
		}
		if (null == loanApplyVO.getLoanName()
				|| loanApplyVO.getLoanName().length() <= 0) {
			mess.append("标题不能为空<br/>");
		}
		if (null == loanApplyVO.getLoanTypeCd()
				|| loanApplyVO.getLoanTypeCd().length() <= 0) {
			mess.append("标的类型不能为空<br/>");
		}
		if (null == loanApplyVO.getTemderCountAmt()
				|| (loanApplyVO.getTemderCountAmt()
						.remainder(new BigDecimal(50)))
						.compareTo(BigDecimal.ZERO) > 0) {
			mess.append("每份金额必须为50的倍数!<br/>");
		}
		if (null == loanApplyVO.getApplyAmt()
				|| loanApplyVO.getApplyAmt().compareTo(BigDecimal.ZERO) <= 0) {
			mess.append("申请金额必须大于0<br/>");
		}
		if ((loanApplyVO.getApplyAmt().remainder(loanApplyVO
				.getTemderCountAmt())).compareTo(BigDecimal.ZERO) > 0) {
			mess.append("申请金额必须是每份金额的倍数<br/>");
		}
		if (null == loanApplyVO.getLoanTerm() || loanApplyVO.getLoanTerm() <= 0) {
			mess.append("还款期限必须大于0<br/>");
		}
		if (null == loanApplyVO.getExpectLoanRate()) {
			mess.append("年利率不能为空<br/>");
		}
		if (loanApplyVO.getExpectLoanRate().compareTo(
				productInfoVO.getLeastRateYear()) < 0) {
			mess.append("年利率不能小于产品最小年化利率<br/>");
		}
		if (loanApplyVO.getExpectLoanRate().compareTo(
				productInfoVO.getMostRateYear()) > 0) {
			mess.append("年利率不能大于产品最大年化利率<br/>");
		}
		if (null != loanApplyVO.getPaymentWayCd()
				&& loanApplyVO.getPaymentWayCd().length() > 0) {
			if ((null == loanApplyVO.getTrusteeCustId()|| loanApplyVO.getTrusteeCustId() == 0)
					&&loanApplyVO.getPaymentWayCd().equals(AppConst.PAYMENT_WAY_CD_TRUSTEE)) {
				mess.append("支付方式为受托支付时，第三方用户必须有值<br/>");
			}
		}
		return mess.toString();
	}

	/**
	 * 查询借款申请基本信息
	 * 
	 * @return
	 */
	// public void getLoanApplyInfo() throws BizException{
	// try {
	// int tempValue = 1001;
	// //查看借款申请信息
	// BizLoanApplyBO loanApply = this.loanApplyBS.getLoanApply(tempValue);
	// //获取客户基本信息
	// BizCustomerBO custInfo =
	// this.creditLimitApplyBS.getCustomerInfo(tempValue);
	// loanApplyVO.setApplyAmt(loanApply.getApplyAmt());
	// loanApplyVO.setApplyDate(loanApply.getApplyDate());
	// loanApplyVO.setApplyStatusCd(loanApply.getApplyStatusCd());
	// loanApplyVO.setCustId(loanApply.getCustId());
	// loanApplyVO.setExpectLoanRate(loanApply.getExpectLoanRate());
	// loanApplyVO.setLoanName(loanApply.getLoanName());
	// loanApplyVO.setLoanPurpose(loanApply.getLoanPurpose());
	// loanApplyVO.setLoanTerm(loanApply.getLoanTerm());
	// loanApplyVO.setLoanTypeCd(loanApply.getLoanTypeCd());
	// loanApplyVO.setRemark(loanApply.getRemark());
	// loanApplyVO.setWorkItemId(loanApply.getWorkItemId());
	// loanApplyVO.setTermUnitCd(loanApply.getTermUnitCd());
	// loanApplyVO.setTenderTerm(loanApply.getTenderTerm());
	// loanApplyVO.setRepayTypeCd(loanApply.getRepayTypeCd());
	// } catch (BizException e) {
	// alert("查看借款申请信息失败："+ e);
	// }
	// }

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

	public String getShowMessage() {
		return showMessage;
	}

	public void setShowMessage(String showMessage) {
		this.showMessage = showMessage;
	}

	public ICreditBS getCreditBS() {
		return creditBS;
	}

	public void setCreditBS(ICreditBS creditBS) {
		this.creditBS = creditBS;
	}

	public BizCreditLimitApplyVO getCreditLimitApplyVO() {
		return creditLimitApplyVO;
	}

	public void setCreditLimitApplyVO(BizCreditLimitApplyVO creditLimitApplyVO) {
		this.creditLimitApplyVO = creditLimitApplyVO;
	}

	public ICustInfoBS getCustInfoBS() {
		return custInfoBS;
	}

	public void setCustInfoBS(ICustInfoBS custInfoBS) {
		this.custInfoBS = custInfoBS;
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

	public Boolean getCanBeSubmit() {
		return canBeSubmit;
	}

	public void setCanBeSubmit(Boolean canBeSubmit) {
		this.canBeSubmit = canBeSubmit;
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

	public JbpmService getJbpmService() {
		return jbpmService;
	}

	public void setJbpmService(JbpmService jbpmService) {
		this.jbpmService = jbpmService;
	}

	public String getHis() {
		return his;
	}

	public void setHis(String his) {
		this.his = his;
	}

}
