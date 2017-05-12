package com.qfw.bean.merge;

/**
 * 借款申请信息Bean
 * 
 * @author qswei
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.qfw.bizservice.bussparams.IProductInfoBS;
import com.qfw.bizservice.credit.ICreditLimitApplyBS;
import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.bizservice.loan.ILoanApplyBS;
import com.qfw.bizservice.merge.IMergeApplyBS;
import com.qfw.common.bizservice.IParamBS;
import com.qfw.common.bizservice.permission.IRoleBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.permission.SysRole;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.CollectionUtil;
import com.qfw.common.util.DateUtils;
import com.qfw.common.util.NumberUtils;
import com.qfw.common.util.StringUtils;
import com.qfw.common.util.web.ViewOper;
import com.qfw.common.util.web.WorkFlowAndPartyUserBackingBean;
import com.qfw.jbpm.model.JbpmConst;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizCollateralInfoBO;
import com.qfw.model.bo.BizContactsBO;
import com.qfw.model.bo.BizCreditLimitApplyBO;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.bo.BizDisclosureInfoBO;
import com.qfw.model.bo.BizGuarantorInfoBO;
import com.qfw.model.vo.bussparams.ProductInfoVO;
import com.qfw.model.vo.common.FileInfo;
import com.qfw.model.vo.credit.BizCreditLimitApplyVO;
import com.qfw.model.vo.custinfo.CustInfoVO;
import com.qfw.model.vo.loan.LoanApplyVO;

@ViewScoped
@ManagedBean(name="mergeApplyBean")
public class MergeApplyManageBean extends WorkFlowAndPartyUserBackingBean{
    
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
	@ManagedProperty(value = "#{custInfoBS}")
	private ICustInfoBS custInfoBS;
	@ManagedProperty(value = "#{roleBS}")
	private IRoleBS roleBS;
	
    private BizCreditLimitApplyVO creditLimitApplyVO = new BizCreditLimitApplyVO();
    private LoanApplyVO loanApplyVO = new LoanApplyVO();
    private ProductInfoVO productInfoVO = new ProductInfoVO();
    
    private Logger log = LogFactory.getInstance().getPlatformLogger();
    
    private String taskid; 
	private String workItemId;
	private boolean finalApp = false ;		// 是否最终放行
	
	private List<FileInfo> files = new ArrayList<FileInfo>();
	private List<FileInfo> files1 = new ArrayList<FileInfo>();
	private List<FileInfo> files2 = new ArrayList<FileInfo>();
    
	private SysRole nextRole;// 下一审核角色
	private SysRole curRole;// 当前审核角色
	
	public Boolean canBeSubmit = true;//是否能提交。
	
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
    		// 初始化参数信息
    		String minTenderAmt = paramBS.getParam(AppConst.PARM_MIN_TENDER_AMT);
    		String advanceRate =  paramBS.getParam(AppConst.PARAMETER_CODE_TQHKWYJBL);
    		String delayRate =  paramBS.getParam(AppConst.PARAMETER_CODE_ZQSFBL);
    		String overdueRate =  paramBS.getParam(AppConst.PARAMETER_CODE_YQSFBL);
    		if(advanceRate==null || "".equals(advanceRate)
    				|| delayRate==null || "".equals(delayRate) 
    				|| overdueRate==null || "".equals(overdueRate)){
    			this.alert("借款申请信息参数获取失败");
        		return ;
    		}
        	
        	taskid = ViewOper.getParameter("taskid");
    		workItemId = ViewOper.getParameter("workItemId");
    		if(null!=taskid&&null!=workItemId){
    			try {
    				
    				loanApplyVO.setWorkItemId(workItemId);
    				loanApplyVO = this.loanApplyBS.findVOByParams(loanApplyVO);
    				
    				productInfoVO = this.productInfoBS.findProductInfoById(loanApplyVO.getProductId());
    				setAuditOpinionList(this.loanApplyBS.getAuditOpinion(workItemId));
    				
    				// 1.借款申请
    				// 2.前台发起的借款申请：这时没有额度申请信息
    				BizCreditLimitApplyBO bo  = this.creditLimitApplyBS.getCreditLimitApplyByWorkItemId(workItemId);
    				if(null==bo){// 前台申请 
    					this.creditLimitApplyBS.initCreditLimitApplyVOByCustId(loanApplyVO.getCustId(), creditLimitApplyVO);
    					loanApplyVO.setTemderCountAmt(new BigDecimal(minTenderAmt));
    					loanApplyVO.setTenderTtlCount(
    							loanApplyVO.getApplyAmt().divide(loanApplyVO.getTemderCountAmt(),BigDecimal.ROUND_HALF_UP).intValue());
    				}else{
    					creditLimitApplyVO = this.creditLimitApplyBS.getCreditLimitApplyVOByWorkItemId(workItemId);
    				}
    				returnFlag = this.mergeApplyBS.checkReturn(workItemId,  AppConst.WORKITEM_ROLE_RISK_TASK);
    				
    			} catch (BizException e) {
    				 
    			}
    			
    		}else{
    			String custId = ViewOper.getParameter("custId");
    			Object custIdSession = ViewOper.getSessionTmpAttr("custId"); 
    			Object productId =ViewOper.getSessionTmpAttr("productId");
    			if(null!=custIdSession&&(custIdSession instanceof String)){
    				custId = (String)custIdSession;
    			}
    			
    			if(null!=custId&&custId.length()>0){
    				
    				// 初始化额度信息
        			this.creditLimitApplyBS.initCreditLimitApplyVOByCustId(custId, creditLimitApplyVO);
        			
        			if(null!=creditLimitApplyVO.getCreditLimitApply().getWorkItemId()
    						&& !"0".equals(creditLimitApplyVO.getCreditLimitApply().getWorkItemId())){
    					canBeSubmit = false;
    					this.alert("该客户存在审批中的额度，不允许再发起额度申请！");
    					return;
    				}
    				
    				CustInfoVO cust = this.custInfoBS.findCustInfoById(Integer.valueOf(custId));
    				if(null == cust){
    					this.alert("客户id："+custId+"获取失败");
    				}else{
    					loanApplyVO.setCustId(cust.getCustId().toString());
    				}
    				if(null!=productId&&(productId instanceof String)){
    					productInfoVO = this.productInfoBS.findProductInfoById(Integer.valueOf((String)productId));
    				}
    			}
    		}
    		
    		loanApplyVO.setAdvanceRate(new BigDecimal(advanceRate));
    		loanApplyVO.setDelayRate(new BigDecimal(delayRate));
    		loanApplyVO.setOverdueRate(new BigDecimal(overdueRate));
    		if(null == loanApplyVO.getTemderCountAmt()){
    			loanApplyVO.setTemderCountAmt(new BigDecimal(minTenderAmt));
    		}
    		if(loanApplyVO.getApplyAmt() == null){
    			loanApplyVO.setApplyAmt(new BigDecimal(0));
    		}
    		loanApplyVO.setTenderTtlCount(
					loanApplyVO.getApplyAmt().divide(loanApplyVO.getTemderCountAmt(),BigDecimal.ROUND_HALF_UP).intValue());
    		this.loanApplyBS.initLoanApplyAuth(loanApplyVO);
    		
    		
    		//重新发起的，当前角色也是客户经理岗
			curRole = roleBS.findSysRole(null,
					AppConst.WORKITEM_ROLE_ACCOUNT);// 当前角色
			nextRole = roleBS.findSysRole(null,
					AppConst.WORKITEM_ROLE_RISK);// 获取风控岗
			getFilterUser().setRoleIds(
					String.valueOf(nextRole.getRoleId()));
			creditLimitApplyVO.getCreditLimitApply().setLimitApply(AppConst.DEFALT_CREDITLIMIT);
			creditLimitApplyVO.getCreditLimitApply().setApplyTerm(AppConst.DEFALT_TIME_LIMIT);
			creditLimitApplyVO.getCust().setScore(AppConst.CUST_SCPRE);
		} catch (Exception e) {
			this.alert("借款申请异常："+e.getMessage());
		}
		return;
    }
    
    /**
	 * 新增担保品列表
	 */
	public void addCollateral(){
		BizCollateralInfoBO collateralInfo = creditLimitApplyVO.getCollateralInfo();
		String mess = this.creditLimitApplyBS.verCollateralInfo(collateralInfo);
		if(null!=mess&&mess.length()>0){
			this.alert(mess);
			return ;
		}
		if(CollectionUtil.isNotEmpty(files)){
			int size = files.size();
			FileInfo fileInfo = null;
			for (int i = 0; i < size; i++) {
				fileInfo = files.get(i);
				if(i==0){
					collateralInfo.setCollateralName1(fileInfo.getName());
					collateralInfo.setCollateralAtt1(fileInfo.getPath());
				}else if(i==1){
					collateralInfo.setCollateralName2(fileInfo.getName());
					collateralInfo.setCollateralAtt2(fileInfo.getPath());
				}else if(i==2){
					collateralInfo.setCollateralName3(fileInfo.getName());
					collateralInfo.setCollateralAtt3(fileInfo.getPath());
				}else if(i==3){
					collateralInfo.setCollateralName4(fileInfo.getName());
					collateralInfo.setCollateralAtt4(fileInfo.getPath());
				}
			}
			files.clear();
		}
		creditLimitApplyVO.getCollateralInfos().add(collateralInfo);
		creditLimitApplyVO.setCollateralInfo(new BizCollateralInfoBO());
	}
	
	public void addDisclosure(){
		BizDisclosureInfoBO disclosureInfo = new BizDisclosureInfoBO();
		if(CollectionUtil.isNotEmpty(files2)){
			int size = files2.size();
			FileInfo fileInfo = null;
			for (int i = 0; i < size; i++) {
				fileInfo = files2.get(i);
				if(i==0){
					disclosureInfo.setCollateralName1(fileInfo.getName());
					disclosureInfo.setCollateralAtt1(fileInfo.getPath());
				}else if(i==1){
					disclosureInfo.setCollateralName2(fileInfo.getName());
					disclosureInfo.setCollateralAtt2(fileInfo.getPath());
				}else if(i==2){
					disclosureInfo.setCollateralName3(fileInfo.getName());
					disclosureInfo.setCollateralAtt3(fileInfo.getPath());
				}else if(i==3){
					disclosureInfo.setCollateralName4(fileInfo.getName());
					disclosureInfo.setCollateralAtt4(fileInfo.getPath());
				}
			}
			files2.clear();
		}
		creditLimitApplyVO.setDisclosureInfo(disclosureInfo);
	}
	/**
	 * 新增保证人列表
	 */
	public void addGuarantor(){
		
		BizGuarantorInfoBO guarantorInfo = creditLimitApplyVO.getGuarantorInfo();
		String mess = this.creditLimitApplyBS.verGuarantorInfo(guarantorInfo);
		if(null!=mess&&mess.length()>0){
			this.alert(mess);
			return ;
		}
		if(CollectionUtil.isNotEmpty(files1)){
			int size = files1.size();
			FileInfo fileInfo = null;
			for (int i = 0; i < size; i++) {
				fileInfo = files1.get(i);
				if(i==0){
					guarantorInfo.setGuarantorName1(fileInfo.getName());
					guarantorInfo.setGuarantorAtt1(fileInfo.getPath());
				}else if(i==1){
					guarantorInfo.setGuarantorName2(fileInfo.getName());
					guarantorInfo.setGuarantorAtt2(fileInfo.getPath());
				}else if(i==2){
					guarantorInfo.setGuarantorName3(fileInfo.getName());
					guarantorInfo.setGuarantorAtt3(fileInfo.getPath());
				}else if(i==3){
					guarantorInfo.setGuarantorName4(fileInfo.getName());
					guarantorInfo.setGuarantorAtt4(fileInfo.getPath());
				}
			}
			files1.clear();
		}
		creditLimitApplyVO.getGuarantorInfos().add(guarantorInfo);
		creditLimitApplyVO.setGuarantorInfo(new BizGuarantorInfoBO());
	}

	public void addContact(){
		BizContactsBO contact = creditLimitApplyVO.getContact();
		String message = this.creditLimitApplyBS.verContactsInfo(contact);
		if(null != message && message.length()>0){
			alert(message);
		}else{
			creditLimitApplyVO.getContacts().add(contact);
			creditLimitApplyVO.setContact(new BizContactsBO());
		}
	}
	/**
	 * 删除关联信息
	 */
	public void deleteContact(){
		String index = ViewOper.getParameter("index");
		if(StringUtils.isNotEmpty(index)){
			BizContactsBO contact = creditLimitApplyVO.getContacts().remove(Integer.valueOf(index).intValue());
			if(contact!=null && !NumberUtils.isBlank(contact.getId())){
				creditLimitApplyVO.getRemoveContacts().add(contact);
			}
		}
	}
	/**
	 * 删除担保品信息
	 */
	public void deleteCollateral(){
		String index = ViewOper.getParameter("index");
		if(StringUtils.isNotEmpty(index)){
			BizCollateralInfoBO collateralInfo = creditLimitApplyVO.getCollateralInfos().remove(Integer.valueOf(index).intValue());
			if(collateralInfo!=null && !NumberUtils.isBlank(collateralInfo.getId())){
				creditLimitApplyVO.getRemoveCollateralInfos().add(collateralInfo);
			}
		}
	}
	/**
	 * 删除保证人信息
	 */
	public void deleteGuarantor(){
		String index = ViewOper.getParameter("index");
		if(StringUtils.isNotEmpty(index)){
			BizGuarantorInfoBO guarantorInfo = creditLimitApplyVO.getGuarantorInfos().remove(Integer.valueOf(index).intValue());
			if(guarantorInfo!=null && !NumberUtils.isBlank(guarantorInfo.getId())){
				creditLimitApplyVO.getRemoveGuarantorInfos().add(guarantorInfo);
			}
		}
	}
	
	/**
	 * 修改担保品信息
	 */
	public void updateCollateral() {
		String index = this.collaIndex;
		BizCollateralInfoBO collateralInfo = creditLimitApplyVO
				.getCollateralInfo();
		String mess = this.creditLimitApplyBS.verCollateralInfo(collateralInfo);
		if(null!=mess&&mess.length()>0){
			this.alert(mess);
			return ;
		}
		if (CollectionUtil.isNotEmpty(files)) {
			int size = files.size();
			FileInfo fileInfo = null;
			for (int i = 0; i < size; i++) {
				fileInfo = files.get(i);
				if (i == 0) {
					collateralInfo.setCollateralName1(fileInfo.getName());
					collateralInfo.setCollateralAtt1(fileInfo.getPath());
				} else if (i == 1) {
					collateralInfo.setCollateralName2(fileInfo.getName());
					collateralInfo.setCollateralAtt2(fileInfo.getPath());
				} else if (i == 2) {
					collateralInfo.setCollateralName3(fileInfo.getName());
					collateralInfo.setCollateralAtt3(fileInfo.getPath());
				} else if (i == 3) {
					collateralInfo.setCollateralName4(fileInfo.getName());
					collateralInfo.setCollateralAtt4(fileInfo.getPath());
				}
			}
			files.clear();
		}
		creditLimitApplyVO.getCollateralInfos().remove(Integer.valueOf(index).intValue());
		creditLimitApplyVO.getCollateralInfos().add(Integer.valueOf(index).intValue(),collateralInfo);
		creditLimitApplyVO.setCollateralInfo(new BizCollateralInfoBO());
	}
	
	/**
	 * 修改担保品信息前缀
	 */
	public void updateCollateralPrefix() {
		files.clear();
		this.collaType = "2";
		String index = ViewOper.getParameter("index");
		this.collaIndex = index;
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
	
	public void updateDisclosurePrefix() {
		files2.clear();
		String index = ViewOper.getParameter("index");
		this.collaIndex = index;
		if (StringUtils.isNotEmpty(index)) {
			BizDisclosureInfoBO disclosureInfo = creditLimitApplyVO.getDisclosureInfo();
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
	
	/**
	 * 新增担保品信息前缀
	 */
	public void addCollateralPrefix() {
		this.collaType = "0";
		creditLimitApplyVO.setCollateralInfo(new BizCollateralInfoBO());
		files.clear();
	}
	
	/**
	 * 修改担保品信息
	 */
	public void updateGuarantor() {
		String index = this.guaranIndex;
		
		BizGuarantorInfoBO guarantorInfo = creditLimitApplyVO
				.getGuarantorInfo();
		
		String mess = this.creditLimitApplyBS.verGuarantorInfo(guarantorInfo);
		if(null!=mess&&mess.length()>0){
			this.alert(mess);
			return ;
		}
		
		if (CollectionUtil.isNotEmpty(files1)) {
			int size = files1.size();
			FileInfo fileInfo = null;
			for (int i = 0; i < size; i++) {
				fileInfo = files1.get(i);
				if (i == 0) {
					guarantorInfo.setGuarantorName1(fileInfo.getName());
					guarantorInfo.setGuarantorAtt1(fileInfo.getPath());
				} else if (i == 1) {
					guarantorInfo.setGuarantorName2(fileInfo.getName());
					guarantorInfo.setGuarantorAtt2(fileInfo.getPath());
				} else if (i == 2) {
					guarantorInfo.setGuarantorName3(fileInfo.getName());
					guarantorInfo.setGuarantorAtt3(fileInfo.getPath());
				} else if (i == 3) {
					guarantorInfo.setGuarantorName4(fileInfo.getName());
					guarantorInfo.setGuarantorAtt4(fileInfo.getPath());
				}
			}
			files1.clear();
		}
		creditLimitApplyVO.getGuarantorInfos().remove(Integer.valueOf(index).intValue());
		creditLimitApplyVO.getGuarantorInfos().add(Integer.valueOf(index).intValue(),guarantorInfo);
		creditLimitApplyVO.setGuarantorInfo(new BizGuarantorInfoBO());
	}
	
	/**
	 * 修改保证人信息前缀
	 */
	public void updateGuarantorPrefix() {
		files1.clear();
		this.guaranType = "2";
		String index = ViewOper.getParameter("index");
		this.guaranIndex = index;
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
	
	/**
	 * 新增保证人信息前缀
	 */
	public void addGuarantorPrefix() {
		this.guaranType = "0";
		creditLimitApplyVO.setGuarantorInfo(new BizGuarantorInfoBO());
		files1.clear();
	}
	
	/**
	 * 执行方法跳转
	 * @return
	 */
	public String forWordSubmit(){
		if(null!=workItemId&&workItemId.length()>0){
			return submitAgain();
		}else{
			try {
				return addApplyInfo();
			} catch (Exception e) {
				return null;
			}
		}
	}
    
    /**
     * 保存申请基本信息
     * @return
     * @throws BizException 
     */
    public String addApplyInfo() throws BizException{
		
		try {
			SysUser partyUser = getSelectPartyUser();
			if(null!=partyUser&&null!=partyUser.getUserId()&&partyUser.getUserId()!=0){
				BizCustomerBO cust = this.custInfoBS.findCustByUserId(partyUser.getUserId());
				loanApplyVO.setTrusteeCustId(cust.getId());
			}
			loanApplyVO.setProductId(productInfoVO.getId());
			String mess = this.validateVO();
			if(null==getSelectUser()||null==getSelectUser().getUserId()){
				mess = "请选择下一阶段处理人";
			}
			
			if(mess.length()<=0){
				this.mergeApplyBS.saveMergeApply(creditLimitApplyVO, loanApplyVO, getSelectUser(),curRole);
				this.alertInfo("申请成功");
				executeJS("alert('申请成功');closeParMainDialog();");
			}else{
				this.alert(mess);
			}
		} catch (BizException e) {
			alert("申请基本信息保存失败，原因："+e.getMessage());
		}
		return null;
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
     * 退回到发起人后的重新提交
     * @return
     */
    public String submitAgain(){
    	try {
    		String mess = "";
//    		if(null==getSelectUser()||null==getSelectUser().getUserId()){
//				mess = "请选择下一阶段处理人";
//			}
    		SysUser partyUser = getSelectPartyUser();
			if (null != partyUser && null != partyUser.getUserId()
					&& partyUser.getUserId() != 0) {
				BizCustomerBO cust = this.custInfoBS.findCustByUserId(partyUser.getUserId());
				loanApplyVO.setTrusteeCustId(cust.getId());
			}
			   if(loanApplyVO.getTenderTerm()==0)
	            {
	            	alert("请输入大于0的投标天数！");
	            	return null;
	            }
			mess = this.validateVO();
			if(null== mess||mess.length()<=0){
				mess = this.creditLimitApplyBS.validateCust(creditLimitApplyVO.getCust());
			}
    		if(mess.length()<=0){
    			this.mergeApplyBS.tranTakeAndCompleteTask(taskid, "提交", creditLimitApplyVO, loanApplyVO,getSelectUser(),curRole,
        				JbpmConst.AUDIT_STATUS_CD_YES,AppConst.WORKITEM_NODE_INIT,null);
    			this.alertInfo("提交成功");
    			return "myTasks";
    		}else{
    			this.alert(mess);
    		}
		} catch (Exception e) {
			 
			alert("申请提交异常："+ e.getMessage());
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
     * 撤销
     * @return
     */
    public String revoke(){
    	try {
    		this.mergeApplyBS.tranTakeAndCompleteTask(taskid, "撤销", creditLimitApplyVO, loanApplyVO,null,curRole,
    				JbpmConst.AUDIT_STATUS_CD_REVOKE,AppConst.WORKITEM_NODE_INIT,getHtml());
    		this.alertInfo("撤销成功");
			return "myTasks";
		} catch (Exception e) {
			this.alertInfo("申请撤销异常："+ e.getMessage());
		}
    	return null;
    }
    
    /**
     * 数据校验
     * @return
     */
    private String validateVO(){
    	StringBuffer mess = new StringBuffer();
    	if(null == creditLimitApplyVO || null == creditLimitApplyVO.getCreditLimitApply() 
    			|| null == loanApplyVO || null == productInfoVO|| null == productInfoVO.getId()){
    		mess.append("借款申请信息为空!<br/>");
    	}
    	if(null == creditLimitApplyVO.getCreditLimitApply().getLimitApply() ){
    		mess.append("额度金额不能为空!<br/>");
    	}
    	if(null == creditLimitApplyVO.getCreditLimitApply().getApplyTerm() ){
    		mess.append("期限不能为空!<br/>");
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
    	if(creditLimitApplyVO.getCreditLimitApply().getLimitApply().compareTo(loanApplyVO.getApplyAmt())<0 ){
    		mess.append("额度金额不能小于借款申请金额!<br/>");
    	}
    	if(null == loanApplyVO.getLoanTerm()||loanApplyVO.getLoanTerm()<=0 || loanApplyVO.getLoanTerm() >36){
    		mess.append("还款期限必须大于0小于36<br/>");
    	}
    	if(null == loanApplyVO.getTenderTerm() || loanApplyVO.getTenderTerm() <= 0){
    		mess.append("筹标期限必须大于0!<br/>");
    	}
    	if(null == loanApplyVO.getExpectLoanRate() ){
    		mess.append("年利率不能为空<br/>");
    	}
    	if(loanApplyVO.getExpectLoanRate().compareTo(productInfoVO.getLeastRateYear())<0){
    		mess.append("年利率不能小于产品最小年化利率<br/>");
    	}
    	if(loanApplyVO.getExpectLoanRate().compareTo(productInfoVO.getMostRateYear())>0){
    		mess.append("年利率不能大于产品最大年化利率<br/>");
    	}
    	if(null!=loanApplyVO.getPaymentWayCd() && loanApplyVO.getPaymentWayCd().length()>0){
    		if((null==loanApplyVO.getTrusteeCustId()||loanApplyVO.getTrusteeCustId()==0)
    				&& loanApplyVO.getPaymentWayCd().equals(AppConst.PAYMENT_WAY_CD_TRUSTEE)){
    			mess.append("支付方式为受托支付时，第三方用户必须有值<br/>");
    		}
    	}
    	return mess.toString();
    }
    
    public void handleFileUpload(FileUploadEvent event) {
		if (files != null && files.size() > 3) {// 最多只能上传4个文件
			FacesMessage msg = new FacesMessage("最多只能上传4个文件", "最多只能上传4个文件");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		uploadFile(event, files);
	}
    
    public void handleFileUpload2(FileUploadEvent event) {
    	if (files2 != null && files2.size() > 2) {// 最多只能上传4个文件
    		FacesMessage msg = new FacesMessage("最多只能上传3个文件", "最多只能上传3个文件");
    		FacesContext.getCurrentInstance().addMessage(null, msg);
    		return;
    	}
    	uploadFile(event, files2);
    }

	public void deleteFile() {
		String fileType = ViewOper.getParameter("fileType");
		String index = ViewOper.getParameter("index");
		if (StringUtils.isEmpty(index)) {
			this.alert("未找到文件！");
			return;
		}
		int ind = Integer.valueOf(index).intValue();
		String realPath = ViewOper.getRequest().getRealPath("");
		File file = null;
		FileInfo fileInfo = null;
		if ("collateral".equals(fileType)) {// 担保品
			if (CollectionUtil.isNotEmpty(files) && files.size() > ind) {
				fileInfo = files.remove(ind);
			}
		} else if("disclosure".equals(fileType)){
			if (CollectionUtil.isNotEmpty(files2) && files2.size() > ind) {
				fileInfo = files2.remove(ind);
			}
		}
		else {// 保证人
			if (CollectionUtil.isNotEmpty(files1) && files1.size() > ind) {
				fileInfo = files1.remove(ind);
			}
		}
		if (fileInfo != null) {
			file = new File(realPath + fileInfo.getPath());
			if (file.exists()) {
				file.delete();
			}
			alertInfo("文件" + fileInfo.getName() + "删除成功！");
		}
	}

	public void handleFileUpload1(FileUploadEvent event) {
		if (files1 != null && files1.size() > 3) {// 最多只能上传4个文件
			FacesMessage msg = new FacesMessage("最多只能上传4个文件", "最多只能上传4个文件");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		uploadFile(event, files1);
	}

	private void uploadFile(FileUploadEvent event, List files) {

		UploadedFile uploadedFile = event.getFile();
		String dir = AppConst.PARM_FILE_UPLOAD_DIR;

//		dir += DateUtils.getDateString("yyyyMMdd", new Date()) + "/";
		
		String fileName = uploadedFile.getFileName();

//		String realPath = ViewOper.getRequest().getRealPath(dir);
		String realPath = paramBS.getParam(AppConst.PARM_FILE_UPLOAD_REALPATH);
		
		File uploadDir = new File(realPath);

		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}

		String ext = fileName.substring(fileName.indexOf("."));// 得到如“.JSP”
		String uuid = StringUtils.getUuid();
		String newfileName = uuid + ext;
		File newFile = new File(uploadDir, newfileName);
		if (newFile.exists()) {
			FacesMessage msg = new FacesMessage(event.getFile().getFileName()
					+ " 文件上传失败！", event.getFile().getFileName() + " 文件上传失败！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			byte[] b = new byte[4 * 1024];
			FileOutputStream os = null;
			InputStream is = null;
			try {
				os = new FileOutputStream(newFile);
				is = uploadedFile.getInputstream();
				while (is.read(b) != -1) {
					os.write(b);
				}
				os.flush();
				FileInfo file = new FileInfo("附件" + (files.size() + 1),
						uploadedFile.getFileName(), dir + newfileName);
				//System.out.println(file.getPath());
				files.add(file);
				FacesMessage msg = new FacesMessage("文件" + file.getName()
						+ "上传成功");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} catch (Exception e) {
				log.error("文件上传失败", e);
				FacesMessage msg = new FacesMessage(event.getFile()
						.getFileName() + " 文件上传失败！", event.getFile()
						.getFileName() + " 文件上传失败！");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} finally {
				try {
					if (is != null) {
						is.close();
					}
					if (os != null) {
						os.close();
					}
				} catch (Exception e) {
					log.error("文件上传失败流关闭失败", e);
				}
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

	public ICustInfoBS getCustInfoBS() {
		return custInfoBS;
	}

	public void setCustInfoBS(ICustInfoBS custInfoBS) {
		this.custInfoBS = custInfoBS;
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
