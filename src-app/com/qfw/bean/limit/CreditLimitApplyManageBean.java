package com.qfw.bean.limit;

/**
 * 额度申请信息Bean
 * 
 * @author qswei
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
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
import org.jbpm.api.task.Task;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.UploadedFile;

import com.qfw.bizservice.credit.ICreditLimitApplyBS;
import com.qfw.bizservice.loan.ILoanApplyBS;
import com.qfw.common.bizservice.IBaseService;
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
import com.qfw.common.util.web.WorkFlowBackingBean;
import com.qfw.jbpm.bizservice.JbpmService;
import com.qfw.jbpm.model.JbpmConst;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizCollateralInfoBO;
import com.qfw.model.bo.BizContactsBO;
import com.qfw.model.bo.BizCreditLimitApplyBO;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.bo.BizGuaranteeInfoBO;
import com.qfw.model.bo.BizGuarantorInfoBO;
import com.qfw.model.bo.BizJobBO;
import com.qfw.model.vo.common.FileInfo;
import com.qfw.model.vo.credit.BizCreditLimitApplyModel;
import com.qfw.model.vo.credit.BizCreditLimitApplyVO;

@ViewScoped
@ManagedBean(name = "creditLimitApplyManageBean")
public class CreditLimitApplyManageBean extends WorkFlowBackingBean {

	@ManagedProperty(value = "#{creditLimitApplyBS}")
	private ICreditLimitApplyBS creditLimitApplyBS;
	@ManagedProperty(value = "#{loanApplyBS}")
	private ILoanApplyBS loanApplyBS;
	@ManagedProperty(value = "#{baseService}")
	private IBaseService baseService;
	@ManagedProperty(value = "#{paramBS}")
	private IParamBS paramBS;
	@ManagedProperty(value = "#{jbpmService}")
	private JbpmService jbpmService;
	@ManagedProperty(value = "#{roleBS}")
	private IRoleBS roleBS;

	// @ManagedProperty(value="#{userBS}")
	// private IUserBS userBS;
	private BizCreditLimitApplyBO creditLimitApply = new BizCreditLimitApplyBO();
	private BizGuaranteeInfoBO bizGuaranteeInfo = new BizGuaranteeInfoBO();
	private List<BizCreditLimitApplyVO> creditLimitApplyVOList;
	private BizCreditLimitApplyVO creditLimitApplyVO = new BizCreditLimitApplyVO();
	private Logger log = LogFactory.getInstance().getPlatformLogger();
	private LazyDataModel<BizCreditLimitApplyVO> creditLimitApplyModel;

	private String taskid;
	private String workItemId;
	private boolean finalApp = false; // 是否最终放行

	private List<FileInfo> files = new ArrayList<FileInfo>();
	private List<FileInfo> files1 = new ArrayList<FileInfo>();

	private SysRole nextRole;// 下一审核角色
	private SysRole curRole;// 当前审核角色
	
	public Boolean canBeSubmit = true;//是否能提交。
	
	public String collaType = "0";//担保信息操作类型
	public String guaranType = "0";// 保证人信息操作类型
	public String collaIndex ;
	public String guaranIndex;
	
	public String his ;

	// private Bizf
	@PostConstruct
	public void init() {
		his = ViewOper.getParameter("his");
		taskid = ViewOper.getParameter("taskid");
		workItemId = ViewOper.getParameter("workItemId");
		if (null != taskid && null != workItemId) {

			// 查看额度基本信息
			try {
				Task task = jbpmService.getTaskById(taskid);
				if (AppConst.WORKITEM_ROLE_ACCOUNT_TASK.equals(task.getName())) {// 获取当前任务名称:
																					// 客户经理岗
					curRole = roleBS.findSysRole(null,
							AppConst.WORKITEM_ROLE_ACCOUNT);// 当前角色

					nextRole = roleBS.findSysRole(null,
							AppConst.WORKITEM_ROLE_RISK);// 获取风控岗

					getFilterUser().setRoleIds(
							String.valueOf(nextRole.getRoleId()));
				} else if (AppConst.WORKITEM_ROLE_RISK_TASK.equals(task.getName())) {// 风控岗审核

					curRole = roleBS.findSysRole(null,
							AppConst.WORKITEM_ROLE_RISK);// 当前角色

					nextRole = roleBS.findSysRole(null,
							AppConst.WORKITEM_ROLE_COMP);// 获取合规岗角色

					getFilterUser().setRoleIds(
							String.valueOf(nextRole.getRoleId()));
				} else if (AppConst.WORKITEM_ROLE_COMP_TASK.equals(task
						.getName())) {// 获取当前任务名称

					curRole = roleBS.findSysRole(null,
							AppConst.WORKITEM_ROLE_COMP);// 获取合规岗角色
					finalApp = true;
				}

				creditLimitApplyVO = this.creditLimitApplyBS
						.getCreditLimitApplyVOByWorkItemId(workItemId);
				setAuditOpinionList(this.loanApplyBS
						.getAuditOpinion(workItemId));
			} catch (BizException e) {
				this.alert(e.getMessage());
			}

		} else {

			String custId = (String) ViewOper.getSessionTmpAttr("custId");

			if (StringUtils.isNotEmpty(custId)) {
				try {
					creditLimitApplyBS.initCreditLimitApplyVOByCustId(custId,
							creditLimitApplyVO);
					if(null!=creditLimitApplyVO.getCreditLimitApply().getWorkItemId()
							&& !"0".equals(creditLimitApplyVO.getCreditLimitApply().getWorkItemId())){
						canBeSubmit = false;
						this.alert("该客户存在审批中的额度，不允许再发起额度申请！");
						return;
					}
					curRole = roleBS.findSysRole(null,
							AppConst.WORKITEM_ROLE_ACCOUNT);// 当前角色
					nextRole = roleBS.findSysRole(null,
							AppConst.WORKITEM_ROLE_RISK);// 获取风控岗

					getFilterUser().setRoleIds(
							String.valueOf(nextRole.getRoleId()));
				} catch (BizException e) {
					this.alert(e.getMessage());
					return;
				}

			}
		}

	}

	/**
	 * 新增担保品列表
	 */
	public void addCollateral() {
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
		creditLimitApplyVO.getCollateralInfos().add(collateralInfo);
		creditLimitApplyVO.setCollateralInfo(new BizCollateralInfoBO());
	}

	/**
	 * 新增保证人列表
	 */
	public void addGuarantor() {

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
		creditLimitApplyVO.getGuarantorInfos().add(guarantorInfo);
		creditLimitApplyVO.setGuarantorInfo(new BizGuarantorInfoBO());
	}

	public void addContact() {
		BizContactsBO contact = creditLimitApplyVO.getContact();
		String message = this.creditLimitApplyBS.verContactsInfo(contact);
		if(null != message && message.length()>0){
			this.alert(message);
			return ;
		}else{
			creditLimitApplyVO.getContacts().add(contact);
			creditLimitApplyVO.setContact(new BizContactsBO());
		}
	}

	/**
	 * 删除关联信息
	 */
	public void deleteContact() {
		String index = ViewOper.getParameter("index");
		if (StringUtils.isNotEmpty(index)) {
			BizContactsBO contact = creditLimitApplyVO.getContacts().remove(
					Integer.valueOf(index).intValue());
			if (contact != null && !NumberUtils.isBlank(contact.getId())) {
				creditLimitApplyVO.getRemoveContacts().add(contact);
			}
		}
	}

	/**
	 * 删除担保品信息
	 */
	public void deleteCollateral() {
		String index = ViewOper.getParameter("index");
		if (StringUtils.isNotEmpty(index)) {
			BizCollateralInfoBO collateralInfo = creditLimitApplyVO
					.getCollateralInfos().remove(
							Integer.valueOf(index).intValue());
			if (collateralInfo != null
					&& !NumberUtils.isBlank(collateralInfo.getId())) {
				creditLimitApplyVO.getRemoveCollateralInfos().add(
						collateralInfo);
			}
		}
	}
	
	/**
	 * 删除保证人信息
	 */
	public void deleteGuarantor() {
		String index = ViewOper.getParameter("index");
		if (StringUtils.isNotEmpty(index)) {
			BizGuarantorInfoBO guarantorInfo = creditLimitApplyVO
					.getGuarantorInfos().remove(
							Integer.valueOf(index).intValue());
			if (guarantorInfo != null
					&& !NumberUtils.isBlank(guarantorInfo.getId())) {
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
	 * 修改保证人信息
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
	 * 保存额度申请基本信息
	 * 
	 * @return
	 * @throws BizException
	 */
	public String addCreditLimit() throws BizException {

		try {
			if (getSelectUser() == null
					|| NumberUtils.isBlank(getSelectUser().getUserId())) {
				alert("请选择审批人");
				return null;
			}
			this.creditLimitApplyBS.saveCreditLimitApply(creditLimitApplyVO,
					getSelectUser());
			this.alertInfo("额度申请成功");
			executeJS("alert('额度申请成功');closeParMainDialog();");
		} catch (BizException e) {
			alert("额度申请基本信息保存失败，原因：" + e.getMessage());
		}
		return null;
	}

	/**
	 * 提交成功(审批流程中的提交)
	 */
	public String submit() {
		try {

			this.creditLimitApplyBS.tranTakeAndCompleteTask(taskid, "通过",
					creditLimitApplyVO, getSelectUser(), curRole,
					JbpmConst.AUDIT_STATUS_CD_YES, finalApp,getHtml());
			this.alertInfo("额度申请提交成功");
			return "myTasks";
		} catch (Exception e) {
			 
			alert("额度申请提交异常：" + e);
		}
		return null;
	}

	/**
	 * 退回到发起人后的重新提交
	 * 
	 * @return
	 */
	public String submitAgain() {
		try {
			this.creditLimitApplyBS.tranTakeAndCompleteTask(taskid, "提交",
					creditLimitApplyVO, getSelectUser(), curRole,
					JbpmConst.AUDIT_STATUS_CD_YES, finalApp,null);
			this.alertInfo("提交成功");
			return "myTasks";
		} catch (Exception e) {
			 
			alert("额度申请提交异常：" + e);
		}
		return null;
	}

	/**
	 * 拒绝
	 */
	public String reject() {
		try {
			this.creditLimitApplyBS.tranTakeAndCompleteTask(taskid, "退回",
					creditLimitApplyVO, null, curRole,
					JbpmConst.AUDIT_STATUS_CD_NO, finalApp,null);
			this.alertInfo("退回成功");
			return "myTasks";
		} catch (Exception e) {
			alert("额度申请拒绝异常：" + e);
		}
		return null;
	}

	/**
	 * 撤销
	 * 
	 * @return
	 */
	public String revoke() {
		try {
			this.creditLimitApplyBS.tranTakeAndCompleteTask(taskid, "撤销",
					creditLimitApplyVO, null, curRole,
					JbpmConst.AUDIT_STATUS_CD_REVOKE, true,getHtml());
			this.alertInfo("撤销成功");
			return "myTasks";
		} catch (Exception e) {
			alert("额度申请撤销异常：" + e);
		}
		return null;
	}

	/**
	 * 更新额度申请信息
	 */
	public void updateCreditLimitApple() {
		try {
			String creditLimitApplyID = String
					.valueOf(creditLimitApply.getId());
			this.creditLimitApplyBS.updateCreditLimitApply(Integer
					.valueOf(creditLimitApplyID));
			alert("更新额度相关信息成功");
		} catch (BizException e) {
			alert("更新额度申请信息异常：" + e);
		}
	}

	/**
	 * 查询额度申请，会员，担保信息
	 * 
	 * @return
	 */
	public void getCreditLimitApplyInfo() throws BizException {
		try {
			int tempValue = 1001;
			// 查看额度基本信息
			BizCreditLimitApplyBO creditLimitApply = this.creditLimitApplyBS
					.getCreditLimitApply(tempValue);
			// 查看担保基本信息
			BizGuaranteeInfoBO guaranteeInfo = this.creditLimitApplyBS
					.getGuaranteeInfo(tempValue);
			// 查看客户基本信息
			BizCustomerBO custInfo = this.creditLimitApplyBS
					.getCustomerInfo(tempValue);
			// 查看会员联系人信息
			BizContactsBO contactsInfo = this.creditLimitApplyBS
					.getContactsInfo(tempValue);
			// 查看会员工作信息
			BizJobBO jobInfo = this.creditLimitApplyBS.getJobInfo(tempValue);

			/*
			 * creditLimitApplyVO.setCustId(custInfo.getId());
			 * creditLimitApplyVO.setCustTypeCd(custInfo.getCustTypeCd());
			 * creditLimitApplyVO
			 * .setLimitApple(creditLimitApply.getLimitApply());
			 * creditLimitApplyVO
			 * .setTenderType(creditLimitApply.getTenderType());
			 * creditLimitApplyVO.setCustName(custInfo.getCustName());
			 * creditLimitApplyVO.setCertificateTypeCd(custInfo
			 * .getCertificateTypeCd());
			 * creditLimitApplyVO.setAge(Integer.valueOf(custInfo.getAge()));
			 * creditLimitApplyVO.setSex(custInfo.getSex()); creditLimitApplyVO
			 * .setMaritalStatusCd(custInfo.getMaritalStatusCd());
			 * creditLimitApplyVO.setEducation(custInfo.getEducationCd());
			 * creditLimitApplyVO
			 * .setMobileTelephone(custInfo.getMobileTelephone());
			 * creditLimitApplyVO
			 * .setContactsName(contactsInfo.getContactsName());
			 * creditLimitApplyVO.setContactsTypeCd(contactsInfo
			 * .getContactsTypeCd());
			 * creditLimitApplyVO.setConMobileTelephone(contactsInfo
			 * .getMobileTelephone());
			 * creditLimitApplyVO.setJobCompanyName(jobInfo
			 * .getJobCompanyName());
			 * creditLimitApplyVO.setCompanyTypeCd(jobInfo.getCompanyTypeCd());
			 * creditLimitApplyVO.setCompanyPhone(jobInfo.getCompanyPhone());
			 * creditLimitApplyVO.setJobIncomeCd(jobInfo.getJobIncomeCd());
			 * creditLimitApplyVO.setJobPosition(jobInfo.getJobPosition());
			 * creditLimitApplyVO.setJobYearCd(jobInfo.getJobYearCd());
			 * creditLimitApplyVO.setJobDeptName(jobInfo.getJobDeptName());
			 * creditLimitApplyVO
			 * .setCompanyAddress(jobInfo.getCompanyAddress());
			 * creditLimitApplyVO.setGuaranteeName(guaranteeInfo
			 * .getGuaranteeName());
			 * creditLimitApplyVO.setGuaranteeType(guaranteeInfo
			 * .getGuaranteeType());
			 * creditLimitApplyVO.setGuaranteeWorth(guaranteeInfo
			 * .getGuaranteeWorth().toString());
			 * creditLimitApplyVO.setGuaranteeExplain(guaranteeInfo
			 * .getGuaranteeExplain()); creditLimitApplyVO
			 * .setGuaranteeAmt(guaranteeInfo.getGuaranteeAtt1());
			 * creditLimitApplyVO.setGuarantorName(guaranteeInfo
			 * .getGuarantorName());
			 * creditLimitApplyVO.setGuarantorType(guaranteeInfo
			 * .getGuarantorType());
			 * creditLimitApplyVO.setGuarantorAmt(guaranteeInfo
			 * .getGuarantorAmt());
			 * creditLimitApplyVO.setGuarantorExplain(guaranteeInfo
			 * .getGuarantorExplain());
			 * creditLimitApplyVO.setGuarantorAtt1(guaranteeInfo
			 * .getGuarantorAtt1());
			 * creditLimitApplyVO.setGuarantorAtt2(guaranteeInfo
			 * .getGuarantorAtt2());
			 * creditLimitApplyVO.setGuarantorAtt3(guaranteeInfo
			 * .getGuarantorAtt3());
			 */
		} catch (BizException e) {
			alert("查看相关信息失败：" + e);
		}
	}

	/**
	 * 删除额度申请基本信息
	 */
	public void delCreditLimitApple() {
		try {
			String creditLimitAppleyD = String
					.valueOf(creditLimitApply.getId());
			this.creditLimitApplyBS.delCreditLimitApply(Integer
					.valueOf(creditLimitAppleyD));
			alert("删除额度相关信息成功");
		} catch (BizException e) {
			alert("删除额度申请信息异常：" + e);
		}
	}

	/**
	 * 查看额度申请信息列表
	 * 
	 * @return
	 */
	public void getCreditLimitApplyList() throws BizException {

		try {
			SysUser user = ViewOper.getUser();
			// creditLimitApplyVO.setCreditLimitID(user.getUserId());
			creditLimitApplyModel = new BizCreditLimitApplyModel(
					creditLimitApplyVO);
		} catch (Exception e) {
			log.error("额度申请查询失败：", e);
			alert("额度申请查询失败：" + e.getMessage());
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		if (files != null && files.size() > 3) {// 最多只能上传4个文件
			FacesMessage msg = new FacesMessage("最多只能上传4个文件", "最多只能上传4个文件");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		uploadFile(event, files);
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
		} else {// 保证人
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
		String dir = paramBS.getParam(AppConst.PARM_FILE_UPLOAD_DIR);

		dir += DateUtils.getDateString("yyyyMMdd", new Date()) + "/";
		String fileName = uploadedFile.getFileName();

		String realPath = ViewOper.getRequest().getRealPath(dir);

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

	public LazyDataModel<BizCreditLimitApplyVO> getCreditLimitApplyModel() {
		return creditLimitApplyModel;
	}

	public void setCreditLimitApplyModel(
			LazyDataModel<BizCreditLimitApplyVO> creditLimitApplyModel) {
		this.creditLimitApplyModel = creditLimitApplyModel;
	}

	public ICreditLimitApplyBS getCreditLimitApplyBS() {
		return creditLimitApplyBS;
	}

	public void setCreditLimitApplyBS(ICreditLimitApplyBS creditLimitApplyBS) {
		this.creditLimitApplyBS = creditLimitApplyBS;
	}

	public BizGuaranteeInfoBO getBizGuaranteeInfo() {
		return bizGuaranteeInfo;
	}

	public void setBizGuaranteeInfo(BizGuaranteeInfoBO bizGuaranteeInfo) {
		this.bizGuaranteeInfo = bizGuaranteeInfo;
	}

	public List<BizCreditLimitApplyVO> getCreditLimitApplyVOList() {
		return creditLimitApplyVOList;
	}

	public void setCreditLimitApplyVOList(
			List<BizCreditLimitApplyVO> creditLimitApplyVOList) {
		this.creditLimitApplyVOList = creditLimitApplyVOList;
	}

	public BizCreditLimitApplyBO getCreditLimitApply() {
		return creditLimitApply;
	}

	public void setCreditLimitApply(BizCreditLimitApplyBO creditLimitApply) {
		this.creditLimitApply = creditLimitApply;
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

	public IBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}

	public List<FileInfo> getFiles() {
		return files;
	}

	public List<FileInfo> getFiles1() {
		return files1;
	}

	public IParamBS getParamBS() {
		return paramBS;
	}

	public void setParamBS(IParamBS paramBS) {
		this.paramBS = paramBS;
	}

	public void setFiles(List<FileInfo> files) {
		this.files = files;
	}

	public void setFiles1(List<FileInfo> files1) {
		this.files1 = files1;
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

	public String getHis() {
		return his;
	}

	public void setHis(String his) {
		this.his = his;
	}

}
