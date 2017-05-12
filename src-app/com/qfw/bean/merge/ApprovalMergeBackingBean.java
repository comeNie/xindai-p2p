package com.qfw.bean.merge;

/**
 * 借款申请信息Bean
 * 
 * @author qswei
 */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.jbpm.api.task.Task;

import com.qfw.bizservice.bussparams.IProductInfoBS;
import com.qfw.bizservice.credit.ICreditLimitApplyBS;
import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.bizservice.loan.ILoanApplyBS;
import com.qfw.bizservice.merge.IMergeApplyBS;
import com.qfw.common.bizservice.IParamBS;
import com.qfw.common.bizservice.permission.IRoleBS;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.permission.SysRole;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.StringUtils;
import com.qfw.common.util.web.ViewOper;
import com.qfw.common.util.web.WorkFlowBackingBean;
import com.qfw.jbpm.bizservice.JbpmService;
import com.qfw.jbpm.model.JbpmConst;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizCollateralInfoBO;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.bo.BizDisclosureInfoBO;
import com.qfw.model.bo.BizGuarantorInfoBO;
import com.qfw.model.vo.bussparams.ProductInfoVO;
import com.qfw.model.vo.common.FileInfo;
import com.qfw.model.vo.credit.BizCreditLimitApplyVO;
import com.qfw.model.vo.loan.LoanApplyVO;

@ViewScoped
@ManagedBean(name="approvalMergeBean")
public class ApprovalMergeBackingBean extends WorkFlowBackingBean{
    
	private static final long serialVersionUID = -6923850790865555276L;
	
	@ManagedProperty(value = "#{creditLimitApplyBS}")
    private ICreditLimitApplyBS creditLimitApplyBS;
	@ManagedProperty(value = "#{loanApplyBS}")
	private ILoanApplyBS loanApplyBS;
	@ManagedProperty(value="#{mergeApplyBS}")
	private IMergeApplyBS mergeApplyBS;
	@ManagedProperty(value = "#{productInfoBS}")
    private IProductInfoBS productInfoBS;
	@ManagedProperty(value = "#{paramBS}")
	private IParamBS paramBS;
	@ManagedProperty(value = "#{roleBS}")
	private IRoleBS roleBS;
	@ManagedProperty(value = "#{jbpmService}")
    private JbpmService jbpmService;
	@ManagedProperty(value = "#{custInfoBS}")
	private ICustInfoBS custInfoBS;
	
    private BizCreditLimitApplyVO creditLimitApplyVO = new BizCreditLimitApplyVO();
    private LoanApplyVO loanApplyVO = new LoanApplyVO();
    private ProductInfoVO productInfoVO = new ProductInfoVO();
    private SysUser selectPartyUser = new SysUser();
    
    private Logger log = LogFactory.getInstance().getPlatformLogger();
    
    private String taskid; 
	private String workItemId;
	private boolean finalApp = false ;		// 是否最终放行
	
	private SysRole nextRole;// 下一审核角色
	private SysRole curRole;// 当前审核角色
	
	private List<FileInfo> files = new ArrayList<FileInfo>();
	private List<FileInfo> files1 = new ArrayList<FileInfo>();
	private List<FileInfo> files2 = new ArrayList<FileInfo>();
	public String collaType = "0";//担保信息操作类型
	public String guaranType = "0";// 保证人信息操作类型
	public String collaIndex ;
	public String guaranIndex;
	
	public boolean returnFlag = false;
	
	public String his ;
	
    @PostConstruct
	public void init()  {
    	try {
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
	    	
	    	Task task = jbpmService.getTaskById(taskid);
	    	String taskName = "";
	    	
	    	if (AppConst.WORKITEM_ROLE_ACCOUNT_TASK.equals(task.getName())) {// 获取当前任务名称: 客户经理岗
				curRole = roleBS.findSysRole(null,AppConst.WORKITEM_ROLE_ACCOUNT);// 当前角色
				nextRole = roleBS.findSysRole(null,AppConst.WORKITEM_ROLE_RISK);// 获取风控岗
				getFilterUser().setRoleIds(String.valueOf(nextRole.getRoleId()));
				taskName = AppConst.WORKITEM_ROLE_RISK_TASK;
			} else if (AppConst.WORKITEM_ROLE_RISK_TASK.equals(task.getName())) {// 风控岗审核
				curRole = roleBS.findSysRole(null,AppConst.WORKITEM_ROLE_RISK);// 当前角色
				nextRole = roleBS.findSysRole(null,AppConst.WORKITEM_ROLE_COMP);// 获取合规岗角色
				getFilterUser().setRoleIds(String.valueOf(nextRole.getRoleId()));
				taskName = AppConst.WORKITEM_ROLE_COMP_TASK;
			} else if (AppConst.WORKITEM_ROLE_COMP_TASK.equals(task.getName())) {// 获取当前任务名称
				curRole = roleBS.findSysRole(null,AppConst.WORKITEM_ROLE_COMP);// 获取合规岗角色
				finalApp = true;
			}
	    	
    		// 初始化参数信息
    		String advanceRate =  paramBS.getParam(AppConst.PARAMETER_CODE_TQHKWYJBL);
    		String delayRate =  paramBS.getParam(AppConst.PARAMETER_CODE_ZQSFBL);
    		String overdueRate =  paramBS.getParam(AppConst.PARAMETER_CODE_YQSFBL);
    		if(advanceRate==null || "".equals(advanceRate)
    				|| delayRate==null || "".equals(delayRate) 
    				|| overdueRate==null || "".equals(overdueRate)){
    			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
        				"借款申请信息参数获取失败", null);
        		FacesContext.getCurrentInstance().addMessage(null, message);
        		return ;
    		}
        	
			creditLimitApplyVO = this.creditLimitApplyBS.getCreditLimitApplyVOByWorkItemId(workItemId);
			
			loanApplyVO.setWorkItemId(workItemId);
			loanApplyVO = this.loanApplyBS.findVOByParams(loanApplyVO);
			
			productInfoVO = this.productInfoBS.findProductInfoById(loanApplyVO.getProductId());
			setAuditOpinionList(this.loanApplyBS.getAuditOpinion(workItemId));
    		
    		loanApplyVO.setAdvanceRate(new BigDecimal(advanceRate));
    		loanApplyVO.setDelayRate(new BigDecimal(delayRate));
    		loanApplyVO.setOverdueRate(new BigDecimal(overdueRate));
    		
    		this.loanApplyBS.initLoanApplyAuth(loanApplyVO);
    		
    		
    		if(null!=loanApplyVO.getTrusteeCustId()&&loanApplyVO.getTrusteeCustId()!=0){
    			BizCustomerBO cust = this.custInfoBS.findCustById(loanApplyVO.getTrusteeCustId());
				selectPartyUser = getUserBS().findUserByUserId(cust.getUserId());
			}
    		
    		if(!finalApp){
				returnFlag = this.mergeApplyBS.checkReturn(workItemId, taskName);
			}
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"借款申请异常："+e.getMessage(), null);
    		FacesContext.getCurrentInstance().addMessage(null, message);
		}
		return;
    }
    
    /**
     * 提交成功(审批流程中的提交)
     */
    public String submit(){
    	try {
    		String appNode = AppConst.WORKITEM_NODE_ONE;
    		if(finalApp){
    			appNode = AppConst.WORKITEM_NODE_TWO;
    		}
    		this.mergeApplyBS.tranTakeAndCompleteTask(taskid, "通过", creditLimitApplyVO, loanApplyVO,getSelectUser(),curRole,
    				JbpmConst.AUDIT_STATUS_CD_YES,appNode,getHtml());
    		this.alertInfo("提交成功");
			return "myTasks";
		} catch (Exception e) {
			 
			alert("申请提交异常："+ e);
		}
    	return null;
    }
    
    /**
     * 拒绝
     */
    public String reject(){
    	try {
    		String appNode = AppConst.WORKITEM_NODE_ONE;
    		if(finalApp){
    			appNode = AppConst.WORKITEM_NODE_TWO;
    		}
    		this.mergeApplyBS.tranTakeAndCompleteTask(taskid, "退回", creditLimitApplyVO, loanApplyVO,null,curRole,
    				JbpmConst.AUDIT_STATUS_CD_NO,appNode,null);
    		this.alertInfo("退回成功");
			return "myTasks";
		} catch (Exception e) {
			alert("申请拒绝异常："+ e);
		}
    	return null;
    }
    
    /**
	 * 查看担保品信息
	 */
	public void queryCollateral() {
		files.clear();
		this.collaType = "1";
		String index = ViewOper.getParameter("index");
		if (StringUtils.isNotEmpty(index)) {
			BizCollateralInfoBO collateralInfo = creditLimitApplyVO.getCollateralInfos().get(Integer.valueOf(index).intValue());
			creditLimitApplyVO.setCollateralInfo(collateralInfo); 
			if(null!=collateralInfo.getCollateralAtt1() && collateralInfo.getCollateralAtt1().length()>0){
				FileInfo fileInfo = new FileInfo();
				fileInfo.setName(collateralInfo.getCollateralName1());
				fileInfo.setPath(collateralInfo.getCollateralAtt1());
				files.add(fileInfo);
			}
			if(null!=collateralInfo.getCollateralAtt2() && collateralInfo.getCollateralAtt2().length()>0){
				FileInfo fileInfo = new FileInfo();
				fileInfo.setName(collateralInfo.getCollateralName2());
				fileInfo.setPath(collateralInfo.getCollateralAtt2());
				files.add(fileInfo);
			}
			if(null!=collateralInfo.getCollateralAtt3() && collateralInfo.getCollateralAtt3().length()>0){
				FileInfo fileInfo = new FileInfo();
				fileInfo.setName(collateralInfo.getCollateralName3());
				fileInfo.setPath(collateralInfo.getCollateralAtt3());
				files.add(fileInfo);
			}
			if(null!=collateralInfo.getCollateralAtt4() && collateralInfo.getCollateralAtt4().length()>0){
				FileInfo fileInfo = new FileInfo();
				fileInfo.setName(collateralInfo.getCollateralName4());
				fileInfo.setPath(collateralInfo.getCollateralAtt4());
				files.add(fileInfo);
			} 
		}
	}
	public void queryDisclosure() {
		files2.clear();
		String index = ViewOper.getParameter("index");
		if (StringUtils.isNotEmpty(index)) {
			BizDisclosureInfoBO disclosureInfo = creditLimitApplyVO.getDisclosureInfo();
			if(disclosureInfo != null){
				if(null!=disclosureInfo.getCollateralAtt1() && disclosureInfo.getCollateralAtt1().length()>0){
					FileInfo fileInfo = new FileInfo();
					fileInfo.setName(disclosureInfo.getCollateralName1());
					fileInfo.setPath(disclosureInfo.getCollateralAtt1());
					files2.add(fileInfo);
				}
				if(null!=disclosureInfo.getCollateralAtt2() && disclosureInfo.getCollateralAtt2().length()>0){
					FileInfo fileInfo = new FileInfo();
					fileInfo.setName(disclosureInfo.getCollateralName2());
					fileInfo.setPath(disclosureInfo.getCollateralAtt2());
					files2.add(fileInfo);
				}
				if(null!=disclosureInfo.getCollateralAtt3() && disclosureInfo.getCollateralAtt3().length()>0){
					FileInfo fileInfo = new FileInfo();
					fileInfo.setName(disclosureInfo.getCollateralName3());
					fileInfo.setPath(disclosureInfo.getCollateralAtt3());
					files2.add(fileInfo);
				}
				if(null!=disclosureInfo.getCollateralAtt4() && disclosureInfo.getCollateralAtt4().length()>0){
					FileInfo fileInfo = new FileInfo();
					fileInfo.setName(disclosureInfo.getCollateralName4());
					fileInfo.setPath(disclosureInfo.getCollateralAtt4());
					files2.add(fileInfo);
				} 
			}
		}
	}
	
	/**
	 * 查看保证人信息
	 */
	public void queryGuarantor() {
		files1.clear();
		this.guaranType = "1";
		String index = ViewOper.getParameter("index");
		if (StringUtils.isNotEmpty(index)) {
			BizGuarantorInfoBO guarantorInfoBO = creditLimitApplyVO.getGuarantorInfos().get(Integer.valueOf(index).intValue());
			creditLimitApplyVO.setGuarantorInfo(guarantorInfoBO); 
			
			if(null!=guarantorInfoBO.getGuarantorAtt1() && guarantorInfoBO.getGuarantorAtt1().length()>0){
				FileInfo fileInfo = new FileInfo();
				fileInfo.setName(guarantorInfoBO.getGuarantorName1());
				fileInfo.setPath(guarantorInfoBO.getGuarantorAtt1());
				files1.add(fileInfo);
			}
			if(null!=guarantorInfoBO.getGuarantorAtt2() && guarantorInfoBO.getGuarantorAtt2().length()>0){
				FileInfo fileInfo = new FileInfo();
				fileInfo.setName(guarantorInfoBO.getGuarantorName2());
				fileInfo.setPath(guarantorInfoBO.getGuarantorAtt2());
				files1.add(fileInfo);
			}
			if(null!=guarantorInfoBO.getGuarantorAtt3() && guarantorInfoBO.getGuarantorAtt3().length()>0){
				FileInfo fileInfo = new FileInfo();
				fileInfo.setName(guarantorInfoBO.getGuarantorName3());
				fileInfo.setPath(guarantorInfoBO.getGuarantorAtt3());
				files1.add(fileInfo);
			}
			if(null!=guarantorInfoBO.getGuarantorAtt4() && guarantorInfoBO.getGuarantorAtt4().length()>0){
				FileInfo fileInfo = new FileInfo();
				fileInfo.setName(guarantorInfoBO.getGuarantorName4());
				fileInfo.setPath(guarantorInfoBO.getGuarantorAtt4());
				files1.add(fileInfo);
			}
		}
	}
    
	public BizCreditLimitApplyVO getCreditLimitApplyVO() {
		return creditLimitApplyVO;
	}

	public void setCreditLimitApplyVO(BizCreditLimitApplyVO creditLimitApplyVO) {
		this.creditLimitApplyVO = creditLimitApplyVO;
	}

	public ICreditLimitApplyBS getCreditLimitApplyBS() {
		return creditLimitApplyBS;
	}

	public void setCreditLimitApplyBS(ICreditLimitApplyBS creditLimitApplyBS) {
		this.creditLimitApplyBS = creditLimitApplyBS;
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

	public ILoanApplyBS getLoanApplyBS() {
		return loanApplyBS;
	}

	public void setLoanApplyBS(ILoanApplyBS loanApplyBS) {
		this.loanApplyBS = loanApplyBS;
	}

	public boolean isFinalApp() {
		return finalApp;
	}

	public void setFinalApp(boolean finalApp) {
		this.finalApp = finalApp;
	}

	public LoanApplyVO getLoanApplyVO() {
		return loanApplyVO;
	}

	public void setLoanApplyVO(LoanApplyVO loanApplyVO) {
		this.loanApplyVO = loanApplyVO;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public IMergeApplyBS getMergeApplyBS() {
		return mergeApplyBS;
	}

	public void setMergeApplyBS(IMergeApplyBS mergeApplyBS) {
		this.mergeApplyBS = mergeApplyBS;
	}

	public IProductInfoBS getProductInfoBS() {
		return productInfoBS;
	}

	public void setProductInfoBS(IProductInfoBS productInfoBS) {
		this.productInfoBS = productInfoBS;
	}

	public IParamBS getParamBS() {
		return paramBS;
	}

	public void setParamBS(IParamBS paramBS) {
		this.paramBS = paramBS;
	}

	public ProductInfoVO getProductInfoVO() {
		return productInfoVO;
	}

	public void setProductInfoVO(ProductInfoVO productInfoVO) {
		this.productInfoVO = productInfoVO;
	}

	public JbpmService getJbpmService() {
		return jbpmService;
	}

	public void setJbpmService(JbpmService jbpmService) {
		this.jbpmService = jbpmService;
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

	public List<FileInfo> getFiles() {
		return files;
	}

	public void setFiles(List<FileInfo> files) {
		this.files = files;
	}

	public List<FileInfo> getFiles1() {
		return files1;
	}

	public void setFiles1(List<FileInfo> files1) {
		this.files1 = files1;
	}

	public String getCollaType() {
		return collaType;
	}

	public void setCollaType(String collaType) {
		this.collaType = collaType;
	}

	public String getGuaranType() {
		return guaranType;
	}

	public void setGuaranType(String guaranType) {
		this.guaranType = guaranType;
	}

	public String getCollaIndex() {
		return collaIndex;
	}

	public void setCollaIndex(String collaIndex) {
		this.collaIndex = collaIndex;
	}

	public String getGuaranIndex() {
		return guaranIndex;
	}

	public void setGuaranIndex(String guaranIndex) {
		this.guaranIndex = guaranIndex;
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

	public List<FileInfo> getFiles2() {
		return files2;
	}

	public void setFiles2(List<FileInfo> files2) {
		this.files2 = files2;
	}

}
