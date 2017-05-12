package com.qfw.bizservice.credit.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qfw.bizservice.credit.ICreditLimitApplyBS;
import com.qfw.bizservice.credit.report.ICreditReportBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.bizservice.permission.IRoleBS;
import com.qfw.common.bizservice.permission.IUserBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.permission.SysRole;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.model.vo.PageList;
import com.qfw.common.util.CollectionUtil;
import com.qfw.common.util.DateUtils;
import com.qfw.common.util.NumberUtils;
import com.qfw.common.util.web.ViewOper;
import com.qfw.dao.loan.ILoanApplyDAO;
import com.qfw.jbpm.bizservice.IFlowBS;
import com.qfw.jbpm.model.JbpmConst;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizAuthBO;
import com.qfw.model.bo.BizCollateralInfoBO;
import com.qfw.model.bo.BizContactsBO;
import com.qfw.model.bo.BizCreditLimitApplyBO;
import com.qfw.model.bo.BizCreditLimitBO;
import com.qfw.model.bo.BizCreditUseBO;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.bo.BizDisclosureInfoBO;
import com.qfw.model.bo.BizGuaranteeInfoBO;
import com.qfw.model.bo.BizGuarantorInfoBO;
import com.qfw.model.bo.BizJobBO;
import com.qfw.model.vo.credit.BizCreditLimitApplyVO;

@Service("creditLimitApplyBS")
public class CreditLimitApplyBSImpl extends BaseServiceImpl implements ICreditLimitApplyBS {

	private Logger log = Logger.getLogger(CreditLimitApplyBSImpl.class);
	/**
	 * 查询额度申请流程信息
	 */
	private final String FIND_CREIDT_LIMIT_SQL = "";
	
	@Autowired
	@Qualifier("flowBS")
	private IFlowBS flowBS;
	@Autowired
	@Qualifier("roleBS")
	private IRoleBS roleBS;
	@Autowired
	private ILoanApplyDAO loanApplyDAO;
	@Autowired
	private IUserBS userBS;
	@Autowired
	private ICreditReportBS creditReportBS;
	/*@Autowired
	@Qualifier("baseService")
	private IBaseService baseService;*/
	
	/**
	 * 通过客户ID，初始化额度申请vo信息
	 * @param custId
	 * @param creditLimitApplyVO
	 * @throws BizException
	 */
	public void initCreditLimitApplyVOByCustId(String custId,BizCreditLimitApplyVO creditLimitApplyVO) throws BizException{
		BizCustomerBO cust = (BizCustomerBO) find(BizCustomerBO.class,custId);
		if (null == cust) {
			throw new BizException("客户id：" + custId+ "获取失败");
		}
		creditLimitApplyVO.setCust(cust);
		
		BizCreditLimitApplyBO claBO = getCreditLimitApplyByCustId(cust.getId());
		if(claBO != null){
			creditLimitApplyVO.setCreditLimitApply(claBO);
		}
		
		List<BizJobBO> jobs = findByHQL("from BizJobBO where custId = '" + custId + "'");
		if (CollectionUtil.isNotEmpty(jobs)) {
			creditLimitApplyVO.setJob(jobs.get(0));
		}
		List<BizContactsBO> contacts = findByHQL("from BizContactsBO where custId = '" + custId + "' order by id");
		if (CollectionUtil.isNotEmpty(contacts)) {
			creditLimitApplyVO.setContacts(contacts);
			/*int size = contacts.size();
			for (int i = 0; i < size; i++) {
				if(i==0){
					creditLimitApplyVO.setContact1(contacts.get(i));
				}else if(i==1){
					creditLimitApplyVO.setContact2(contacts.get(i));
				}else if(i==2){
					creditLimitApplyVO.setContact3(contacts.get(i));
				}else if(i==3){
					creditLimitApplyVO.setContact4(contacts.get(i));
				}
			}*/
		}
		List<BizAuthBO> auths = getHibernateTemplate().find("from BizAuthBO where relTypeCd = '0' and relId = ?", custId);
		Map<String, BizAuthBO> authMap = new HashMap<String, BizAuthBO>();
		if(CollectionUtil.isNotEmpty(auths)){
			authMap = new HashMap<String, BizAuthBO>();
			for (BizAuthBO bizAuthBO : auths) {
				authMap.put(bizAuthBO.getAuthItemCd(), bizAuthBO);
			}
		}else{
			//认证项目 身份认证0\工作认证1\居住地认证2\信用报告3\收入认证4\房产5\购车6\结婚7\学历8\技术9\手机10\
			//微博11\现场12\抵押认证13\担保认证14\户口本15\社保缴费账单16\银行信用报告17\个人所得税18\购房合同19\
			//汽车行驶证20\房屋产权证21\车位产权证22
			authMap = creditLimitApplyVO.getAuthMap();
			authMap.put("0", new BizAuthBO("0"));
			authMap.put("15", new BizAuthBO("15"));
			authMap.put("8", new BizAuthBO("8"));
			authMap.put("4", new BizAuthBO("4"));
			authMap.put("16", new BizAuthBO("16"));
			authMap.put("17", new BizAuthBO("17"));
			authMap.put("18", new BizAuthBO("18"));
			authMap.put("19", new BizAuthBO("19"));
			authMap.put("20", new BizAuthBO("20"));
			authMap.put("21", new BizAuthBO("21"));
			authMap.put("22", new BizAuthBO("22"));
		}
		if(claBO!=null){
			List<BizCollateralInfoBO> collateralInfos = findCollateralInfosByCL(claBO.getId());
			 if(CollectionUtil.isNotEmpty(collateralInfos)){
				 creditLimitApplyVO.setCollateralInfos(collateralInfos);
			 }
			 List<BizGuarantorInfoBO> guarantorInfos = findGuarantorInfoByCL(claBO.getId());
			 if(CollectionUtil.isNotEmpty(guarantorInfos)){
				 creditLimitApplyVO.setGuarantorInfos(guarantorInfos);
			 }
		}
		
		creditLimitApplyVO.setDisclosureInfo(findDisclosureInfo(cust.getId()));
		 
		creditLimitApplyVO.setAuthMap(authMap);
	}
	/**
	 * 保存额度申请相关信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void saveCreditLimitApply(BizCreditLimitApplyVO creditLimitApplyVO,SysUser auditUser) throws BizException {
		try{
			if(null == creditLimitApplyVO){
				throw new BizException("额度申请参数不能为空");
			}
			SysUser user = ViewOper.getUser();
			
			Map map = new HashMap();
			map.put(JbpmConst.APPLY_USER, user);
			map.put(JbpmConst.NEXT_AUDIT_USER, auditUser);
			map.put(JbpmConst.FLOW_REMARK, creditLimitApplyVO.getCust().getCustName()+"发起一笔"+ NumberUtils.format2(creditLimitApplyVO.getCreditLimitApply().getLimitApply())+"元额度申请");
			String workItemId = flowBS.startProcessInstanceAndCompleteTask("limits_apply", "提交", map);
			saveCreditLimitApply(creditLimitApplyVO, workItemId);
			
		}catch(Exception e){
			 
			log.error("额度申请基本信息保存失败，原因："+e);
			throw new BizException("额度申请基本信息保存失败，原因："+e.getMessage());

		}
		
	}
	
	/**
	 * 
	 * @param creditLimitApplyVO
	 * @param workItemId
	 */
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void saveCreditLimitApply(BizCreditLimitApplyVO creditLimitApplyVO,String workItemId) throws BizException{
		
		SysUser optUser = ViewOper.getUser();
		Date now = new Date();
		
		//更新客户信息开始
		BizCustomerBO cust = creditLimitApplyVO.getCust();
		String mess = this.validateCust(cust);
		if(null !=mess && mess.length()>0){
			throw new BizException(mess);
		}
		/*Integer score = cust.getScore();
		cust = (BizCustomerBO) find(BizCustomerBO.class, String.valueOf(cust.getId()));
		cust.setScore(score);*/
		if(null == cust.getCustManagerId()||cust.getCustManagerId()==0){
			cust.setCustManagerId(ViewOper.getUser().getUserId());
			cust.setCustManagerName(ViewOper.getUser().getUserName());
		}
		cust.setCreditRate(this.tranformCreditRate(cust.getScore()));
		cust.setSysUpdateUser(optUser.getUserId());
		update(cust);
		updateUserByCust(cust);
		//更新客户信息结束
		
		BizCreditLimitApplyBO creditLimitApplyBO = creditLimitApplyVO.getCreditLimitApply();
		//创建额度信息开始
		creditLimitApplyBO.setApplyStartdate(now);
		
		creditLimitApplyBO.setApplyEnddate(DateUtils.addMonth(now, creditLimitApplyBO.getApplyTerm()));
		
		creditLimitApplyBO.setCustId(creditLimitApplyVO.getCust().getId());
		creditLimitApplyBO.setWorkItemId(creditLimitApplyVO.getWorkItemId());
		
		creditLimitApplyBO.setSysUpdateUser(ViewOper.getUser().getUserId());
		creditLimitApplyBO.setSysCreateUser(ViewOper.getUser().getUserId());
		creditLimitApplyBO.setWorkItemId(workItemId);
		//creditLimitApplyBO.setSysUpdateTime(now);
		if(NumberUtils.isBlank(creditLimitApplyBO.getId())){
			creditLimitApplyBO.setSysCreateTime(now);
			save(creditLimitApplyBO);
		}else{
			update(creditLimitApplyBO);
		}
		
		Integer creditLimitId = creditLimitApplyBO.getId();//额度id
		//创建额度结束
		
		//更新联系人信息开始
		
		List<BizContactsBO> contacts = creditLimitApplyVO.getContacts();
		if(CollectionUtil.isNotEmpty(contacts)){
			for (BizContactsBO bizContactsBO : contacts) {
				bizContactsBO.setWorkItemId("0");//联系人信息不走流程
				bizContactsBO.setCustId(cust.getId());
				bizContactsBO.setSysUpdateUser(optUser.getUserId());
				if(NumberUtils.isBlank(bizContactsBO.getId())){
					bizContactsBO.setSysCreateUser(optUser.getUserId());
					bizContactsBO.setSysCreateTime(now);
					save(bizContactsBO);
				}else{
					update(bizContactsBO);
				}
			}
		}
		//删除移除的联系信息人
		List<BizContactsBO> removeContacts = creditLimitApplyVO.getRemoveContacts();
		if(CollectionUtil.isNotEmpty(removeContacts)){
			for (BizContactsBO bizContactsBO : removeContacts) {
				delete(bizContactsBO);
			}
		}
		//更新联系人信息结束
		
		//更新工作信息开始
		BizJobBO job = creditLimitApplyVO.getJob();
		job.setCustId(cust.getId());
		job.setWorkItemId("0");
		job.setSysUpdateUser(optUser.getUserId());
		if(NumberUtils.isBlank(job.getId())){
			job.setSysCreateUser(optUser.getUserId());
			job.setSysCreateTime(now);
			save(job);
		}else{
			update(job);
		}
		//更新工作信息结束
		//更新担保品信息开始
		List<BizCollateralInfoBO> collateralInfos = creditLimitApplyVO.getCollateralInfos();
		if(CollectionUtil.isNotEmpty(collateralInfos)){
			for (BizCollateralInfoBO bizCollateralInfoBO : collateralInfos) {
				
				bizCollateralInfoBO.setCreditLimitId(creditLimitId);//额度id
				bizCollateralInfoBO.setCustId(cust.getId());//会员id
				bizCollateralInfoBO.setSysUpdateUser(optUser.getUserId());
				
				//getHibernateTemplate().saveOrUpdate(bizCollateralInfoBO);
				
				if(NumberUtils.isBlank(bizCollateralInfoBO.getId())){//ID为空
					bizCollateralInfoBO.setSysCreateUser(optUser.getUserId());
					bizCollateralInfoBO.setSysCreateTime(now);
					save(bizCollateralInfoBO);
				}else{
					update(bizCollateralInfoBO);
				}
				
			}
		}
		//删除担保品信息
		List<BizCollateralInfoBO> removeCollateralInfos = creditLimitApplyVO.getRemoveCollateralInfos();
		if(CollectionUtil.isNotEmpty(removeCollateralInfos)){
			for (BizCollateralInfoBO bizCollateralInfoBO : removeCollateralInfos) {
				delete(bizCollateralInfoBO);
			}
		}
		//更新担保品信息结束
		//更新保证人信息开始
		List<BizGuarantorInfoBO> guarantorInfos = creditLimitApplyVO.getGuarantorInfos();
		if(CollectionUtil.isNotEmpty(guarantorInfos)){
			for (BizGuarantorInfoBO bizGuarantorInfoBO : guarantorInfos) {
				bizGuarantorInfoBO.setCreditLimitId(creditLimitId);
				bizGuarantorInfoBO.setSysUpdateUser(optUser.getUserId());
				bizGuarantorInfoBO.setCustId(cust.getId());
				if(NumberUtils.isBlank(bizGuarantorInfoBO.getId())){//ID为空
					bizGuarantorInfoBO.setSysCreateUser(optUser.getUserId());
					bizGuarantorInfoBO.setSysCreateTime(now);
					save(bizGuarantorInfoBO);
				}else{
					update(bizGuarantorInfoBO);
				}
			}
		}
		List<BizGuarantorInfoBO> removeGuarantorInfos = creditLimitApplyVO.getRemoveGuarantorInfos();
		if(CollectionUtil.isNotEmpty(removeGuarantorInfos)){
			for (BizGuarantorInfoBO bizGuarantorInfoBO : removeGuarantorInfos) {
				delete(bizGuarantorInfoBO);
			}
		}
		//更新保证人信息结束
		
		//add by yangjj start
		BizDisclosureInfoBO  disclosureInfo = creditLimitApplyVO.getDisclosureInfo();
		if(disclosureInfo != null){
			if(disclosureInfo.getId() != null){
				update(disclosureInfo);
			}else{
				disclosureInfo.setCustId(cust.getId());
				disclosureInfo.setSysCreateUser(optUser.getUserId());
				disclosureInfo.setSysCreateTime(now);
				save(disclosureInfo);
			}
		}
		//add by yangjj end
		
		//更新会员认证情况开始
		Map<String,BizAuthBO> authMap = creditLimitApplyVO.getAuthMap();
		Collection<BizAuthBO> authBOs = authMap.values();
		for (BizAuthBO bizAuthBO : authBOs) {
			bizAuthBO.setRelId(String.valueOf(cust.getId()));
			bizAuthBO.setRelTypeCd(AppConst.AUTH_REL_TYPE_CD_CUST);
			bizAuthBO.setSysUpdateUser(optUser.getUserId());
			bizAuthBO.setWorkItemId(workItemId);
			if(NumberUtils.isBlank(bizAuthBO.getId())){
				bizAuthBO.setSysCreateUser(optUser.getUserId());
				bizAuthBO.setSysCreateTime(now);
				save(bizAuthBO);
			}else{
				update(bizAuthBO);
			}
		}
		//更新会员认证情况结束
		
		// 更新额度信息
		List<BizCreditLimitBO> cls = getHibernateTemplate().find("from BizCreditLimitBO where relId = ? and relTypeCd = ?",String.valueOf(creditLimitApplyBO.getCustId()),AppConst.CREDITLIMIT_RELTYPE_CUST);
		BizCreditLimitBO limitBO = null;
		BigDecimal diffAmt = BigDecimal.ZERO;
		if(CollectionUtil.isNotEmpty(cls)){
//			diffAmt = creditLimitApplyBO.getLimitApply().subtract(cls.get(0).getClAmt());
//			limitBO = cls.get(0);
//			limitBO.setClAmt(creditLimitApplyBO.getLimitApply());
//			limitBO.setStartDate(creditLimitApplyBO.getApplyStartdate());
//			limitBO.setEndDate(creditLimitApplyBO.getApplyEnddate());
//			limitBO.setSysUpdateUser(ViewOper.getUser().getUserId());
//			update(limitBO);
		}else{
			// 新增额度信息
			limitBO = new BizCreditLimitBO();
			limitBO.setRelId(creditLimitApplyBO.getCustId().toString());
			limitBO.setRelTypeCd(AppConst.CREDITLIMIT_RELTYPE_CUST);
			limitBO.setClAmt(creditLimitApplyBO.getLimitApply());
			limitBO.setFreezeAmt(BigDecimal.ZERO);
			limitBO.setCreditStateCd(AppConst.CREDITLIMIT_STATUE_UNUSABLE);
			limitBO.setEndDate(creditLimitApplyBO.getApplyEnddate());
			limitBO.setOpstateCd(AppConst.CREDITLIMIT_STATUE_USABLE);
			limitBO.setStartDate(creditLimitApplyBO.getApplyStartdate());
			limitBO.setWorkItemId(AppConst.WORKITEMID_NORMAL);
			limitBO.setSysCreateUser(ViewOper.getUser().getUserId());
			limitBO.setSysUpdateUser(ViewOper.getUser().getUserId());
			limitBO.setSysCreateTime(new Date());
			this.save(limitBO);
			BizCreditUseBO cubo = new BizCreditUseBO();
			cubo.setClId(limitBO.getId());
			cubo.setPreRestoreAmt(BigDecimal.ZERO);
			cubo.setPreTieupAmt(BigDecimal.ZERO);
			cubo.setTieupAmt(BigDecimal.ZERO);
			cubo.setSysCreateUser(ViewOper.getUser().getUserId());
			cubo.setSysUpdateUser(ViewOper.getUser().getUserId());
			cubo.setSysCreateTime(new Date());
			this.save(cubo);
			
			diffAmt = creditLimitApplyBO.getLimitApply();
			
			// 更新信用报告
			this.creditReportBS.updateCreditAmt(creditLimitApplyVO.getCust().getId().toString(),
					creditLimitApplyVO.getCreditLimitApply().getLimitApply(),diffAmt
					);
		}
	}
	
	/**
	 * 校验客户信息
	 * @param cust
	 * @return
	 */
	public String validateCust(BizCustomerBO cust){
		StringBuffer mess = new StringBuffer();
		if(null == cust){
			mess.append("客户信息不能为空<br/>");
		}
		if(null == cust.getAge()||cust.getAge() == 0){
			mess.append("年龄不能为空<br/>");
		}
		if(null == cust.getCustName()||cust.getCustName().length()<=0){
			mess.append("客户名称不能为空<br/>");
		}
		if(null == cust.getTelephone()||cust.getTelephone().length()<=0){
			mess.append("固定电话不能为空<br/>");
		}
		if(null == cust.getMobileTelephone()||cust.getMobileTelephone().length()<=0){
			mess.append("移动电话不能为空<br/>");
		}
		if(null == cust.getSex()||cust.getSex().length()<=0){
			mess.append("性别不能为空<br/>");
		}
		if(null == cust.getCertificateNum()){
			mess.append("身份证 不能为空<br/>");
		}
		String Ai = ""; 
		if (cust.getCertificateNum().length() == 18) {   
	        Ai = cust.getCertificateNum().substring(0, 17);   
	    } 
	    if (isNumeric(Ai) == false || cust.getCertificateNum().length() != 18) {   
	    	mess.append("身份证18位号码都应为数字 ; 18位号码除最后一位外，都应为数字。<br/>");   
	    }
	    if(null == cust.getMaritalStatusCd()||cust.getMaritalStatusCd().length()<=0){
	    	mess.append("婚姻状况不能为空<br/>");
	    }
	    if(null == cust.getEducationCd()||cust.getEducationCd().length()<=0){
	    	mess.append("学历不能为空<br/>");
	    }
	    if(null == cust.getProvinceCd()||cust.getProvinceCd().length()<=0
	    		|| null == cust.getCityCd()||cust.getCityCd().length()<=0
	    		|| null == cust.getNationalityCd() || cust.getNationalityCd().length()<=0
	    		|| null == cust.getStreetAddress() || cust.getStreetAddress().length()<=0){
	    	mess.append("户籍地址需要全部填写详细<br/>");
	    }
	    if(null == cust.getLiveProvinceCd()||cust.getLiveProvinceCd().length()<=0
	    		|| null == cust.getLiveCityCd()||cust.getLiveCityCd().length()<=0
	    		|| null == cust.getLiveNationalityCd() || cust.getLiveNationalityCd().length()<=0
	    		|| null == cust.getLiveStreetAddress() || cust.getLiveStreetAddress().length()<=0){
	    	mess.append("现居住地址需要全部填写详细<br/>");
	    }
	    if(null == cust.getScore()){
	    	mess.append("评分不能为空<br/>");
	    }
		return mess.toString();
	}
			
	/**
     * 数据校验
     * @return
     */
    /*private String validateVO(BizCreditLimitApplyVO creditLimitApplyVO){
    	StringBuffer mess = new StringBuffer();
    	if(null == creditLimitApplyVO.getCustId()){
    		mess.append("找不到客户信息<br/>");
    	}
    	if(null == creditLimitApplyVO.getCustTypeCd() || creditLimitApplyVO.getCustTypeCd().length()<=0){
    		mess.append("客户类型不能为空<br/>");
    	}
    	if(null == creditLimitApplyVO.getLimitApple() || creditLimitApplyVO.getLimitApple().compareTo(BigDecimal.ZERO)<=0){
    		mess.append("申请金额必须大于0<br/>");
    	}
    	if(null == creditLimitApplyVO.getTenderType()||creditLimitApplyVO.getTenderType().length()<=0){
    		mess.append("标的类型不能为空<br/>");
    	}
    	return mess.toString();
    }*/

	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void tranTakeAndCompleteTask(String taskId, String to, BizCreditLimitApplyVO creditLimitApplyVO, SysUser auditUser,
			SysRole auditRole, String auditStatus, boolean isLast, String html) throws BizException {
	try {
			// 是否重新提交
			boolean isSubmitAgain = false;
			// 工作流
			Map<String, Object> paraMap = new HashMap<String, Object>();
			
			paraMap.put(JbpmConst.AUDIT_ROLE, auditRole);
			paraMap.put(JbpmConst.CUR_AUDIT_USER, ViewOper.getUser());
			paraMap.put(JbpmConst.AUDIT_STATUS_CD, auditStatus);
			paraMap.put(JbpmConst.NEXT_AUDIT_USER,auditUser);
			
			if(isLast&&("通过".equals(to)||"撤销".equals(to))){
				// 流程结束，成功或失败
				paraMap.put(JbpmConst.FLOW_HTML, html);
			}
			if("提交".equals(to)){
				paraMap.put(JbpmConst.FLOW_REMARK, creditLimitApplyVO.getCust().getCustName()+"发起一笔"+ NumberUtils.format2(creditLimitApplyVO.getCreditLimitApply().getLimitApply())+"元额度申请");
				flowBS.completeTask(taskId, to, paraMap);//重新提交时不带审批意见
				isSubmitAgain = true;
			}else{
				flowBS.completeTask(taskId,to, creditLimitApplyVO.getComment(), paraMap);
			}
			this.updateApplyForTake(creditLimitApplyVO, auditUser, auditStatus, isLast,isSubmitAgain);
		} catch (Exception e) {
			log.error("提现申请审批异常：",e);
			throw new BizException(e);
		}
	}
	
	@Override
	public void updateApplyForTake(BizCreditLimitApplyVO creditLimitApplyVO,
			SysUser auditUser, String auditStatus, boolean isLast,boolean isSubmitAgain)
			throws BizException {
		/*if(!isLast){
			return;
		}*/
		BizCreditLimitApplyBO creditLimitApplyTemp = creditLimitApplyVO.getCreditLimitApply();
		BizCreditLimitApplyBO bo = this.getCreditLimitApply(creditLimitApplyTemp.getId());
		
		if(null == bo || !bo.getWorkItemId().equals(creditLimitApplyTemp.getWorkItemId())){
			throw new BizException("获取不到额度申请信息!");
		}

		bo.setLimitApply(creditLimitApplyTemp.getLimitApply());
		bo.setApplyTerm(creditLimitApplyTemp.getApplyTerm());
		bo.setTenderType(creditLimitApplyTemp.getTenderType());
		bo.setApplyStartdate(new Date());
		bo.setApplyEnddate(DateUtils.addMonth(bo.getApplyStartdate(), bo.getApplyTerm()));
		bo.setWorkItemId(creditLimitApplyTemp.getWorkItemId());
		if(auditStatus.equals(JbpmConst.AUDIT_STATUS_CD_YES)&&isLast){
			bo.setApplyStatusCd(AppConst.APPROVAL_STATUS_SUCCESS);
			bo.setWorkItemId(AppConst.WORKITEMID_NORMAL);
		}else if(auditStatus.equals(JbpmConst.AUDIT_STATUS_CD_REVOKE)){
			bo.setApplyStatusCd(AppConst.APPROVAL_STATUS_FAILURE);
			bo.setWorkItemId(AppConst.WORKITEMID_NORMAL);
		}
		super.update(bo);
		
		creditLimitApplyVO.setCreditLimitApply(bo);
		
		//更新会员认证情况开始
		Map<String,BizAuthBO> authMap = creditLimitApplyVO.getAuthMap();
		Collection<BizAuthBO> authBOs = authMap.values();
		for (BizAuthBO bizAuthBO : authBOs) {
			bizAuthBO.setRelId(String.valueOf(bo.getCustId()));
			bizAuthBO.setRelTypeCd(AppConst.AUTH_REL_TYPE_CD_CUST);
			bizAuthBO.setSysUpdateUser(ViewOper.getUser().getUserId());
			bizAuthBO.setWorkItemId(bo.getWorkItemId());
			if(NumberUtils.isBlank(bizAuthBO.getId())){
				bizAuthBO.setSysCreateUser(bo.getSysCreateUser());
				bizAuthBO.setSysCreateTime(bo.getSysCreateTime());
				save(bizAuthBO);
			}else{
				update(bizAuthBO);
			}
		}
		//更新会员认证情况结束
		
		//重新提交
		if(isSubmitAgain){
			this.saveCreditlimtTabInfo(creditLimitApplyVO);
		}
		
		// 如果是最终审核，且同意，联动保存额度信息
		if(auditStatus.equals(JbpmConst.AUDIT_STATUS_CD_YES)&&isLast){
			List<BizCreditLimitBO> cls = getHibernateTemplate().find("from BizCreditLimitBO where relId = ? and relTypeCd = ?",String.valueOf(bo.getCustId()),AppConst.CREDITLIMIT_RELTYPE_CUST);
			BizCreditLimitBO limitBO = null;
			BigDecimal diffAmt = BigDecimal.ZERO;
			if(CollectionUtil.isNotEmpty(cls)){
				diffAmt = bo.getLimitApply().subtract(cls.get(0).getClAmt());
				limitBO = cls.get(0);
//				limitBO.setClAmt(bo.getLimitApply());
				limitBO.setCreditStateCd(AppConst.CREDITLIMIT_STATUE_USABLE);
//				limitBO.setStartDate(bo.getApplyStartdate());
//				limitBO.setEndDate(bo.getApplyEnddate());
				limitBO.setSysUpdateUser(ViewOper.getUser().getUserId());
				update(limitBO);
			}else{
				// 新增额度信息
				limitBO = new BizCreditLimitBO();
				limitBO.setRelId(bo.getCustId().toString());
				limitBO.setRelTypeCd(AppConst.CREDITLIMIT_RELTYPE_CUST);
				limitBO.setClAmt(bo.getLimitApply());
				limitBO.setFreezeAmt(BigDecimal.ZERO);
				limitBO.setCreditStateCd(AppConst.CREDITLIMIT_STATUE_USABLE);
				limitBO.setEndDate(bo.getApplyEnddate());
				limitBO.setOpstateCd(AppConst.CREDITLIMIT_STATUE_USABLE);
				limitBO.setStartDate(bo.getApplyStartdate());
				limitBO.setWorkItemId(AppConst.WORKITEMID_NORMAL);
				limitBO.setSysCreateUser(ViewOper.getUser().getUserId());
				limitBO.setSysUpdateUser(ViewOper.getUser().getUserId());
				limitBO.setSysCreateTime(new Date());
				this.save(limitBO);
				BizCreditUseBO cubo = new BizCreditUseBO();
				cubo.setClId(limitBO.getId());
				cubo.setPreRestoreAmt(BigDecimal.ZERO);
				cubo.setPreTieupAmt(BigDecimal.ZERO);
				cubo.setTieupAmt(BigDecimal.ZERO);
				cubo.setSysCreateUser(ViewOper.getUser().getUserId());
				cubo.setSysUpdateUser(ViewOper.getUser().getUserId());
				cubo.setSysCreateTime(new Date());
				this.save(cubo);
				
				diffAmt = bo.getLimitApply();
			}
			// 更新信用报告
			this.creditReportBS.updateCreditAmt(creditLimitApplyVO.getCust().getId().toString(),
					creditLimitApplyVO.getCreditLimitApply().getLimitApply(),diffAmt
					);
		}else if(auditStatus.equals(JbpmConst.AUDIT_STATUS_CD_REVOKE)){
			List<BizCreditLimitBO> cls = getHibernateTemplate().find("from BizCreditLimitBO where relId = ? and relTypeCd = ? and CREDIT_STATE_CD = ?",
					String.valueOf(bo.getCustId()),AppConst.CREDITLIMIT_RELTYPE_CUST,AppConst.CREDITLIMIT_STATUE_UNUSABLE);
			// 第一次额度申请，并撤销申请
			if(CollectionUtil.isNotEmpty(cls)){
				List<BizCreditUseBO> useBO = getHibernateTemplate().find("from BizCreditUseBO where clId = ? ",cls.get(0).getId());
				if(CollectionUtil.isNotEmpty(useBO)){
					delete(useBO.get(0));
				}
				delete(cls.get(0));
				// 更新信用报告
				this.creditReportBS.updateCreditAmt(creditLimitApplyVO.getCust().getId().toString(),
						BigDecimal.ZERO,BigDecimal.ZERO.subtract(cls.get(0).getClAmt())
						);
				//删除额度申请信息
				delete(bo);
			}
		}
	}
	
	@Override
	/**
	 * 删除额度申请信息
	 */
	public void delCreditLimitApply(int creditLimitApplyID)
			throws BizException {
		String sql = "";
		getJdbcTemplate().execute(sql);
	}

	@Override
	/**
	 * 查看额度申请信息
	 */
	public BizCreditLimitApplyBO getCreditLimitApply(
			int id) throws BizException {
		try{
			String queryString = "from BizCreditLimitApplyBO where id = ?";
			List list = getHibernateTemplate().find(queryString, id);
			if(list != null &&  !list.isEmpty()){
				return  (BizCreditLimitApplyBO) list.get(0);
			} 
			return null;
		}catch(Exception e){
			 
		}
		return null;
		
	}
	/**
	 * 通过工作流信息查找额度申请信息
	 * @param workItemId
	 * @return
	 * @throws BizException
	 */
	public BizCreditLimitApplyVO getCreditLimitApplyVOByWorkItemId(
			String workItemId) throws BizException{
		BizCreditLimitApplyVO claVO = new BizCreditLimitApplyVO(); 
		BizCreditLimitApplyBO claBO = getCreditLimitApplyByWorkItemId(workItemId);
		if(claBO == null){
			throw new BizException("额度申请不存在或者已经审批通过");
		}
		claVO.setCreditLimitApply(claBO);
		String custId = String.valueOf(claBO.getCustId());
		BizCustomerBO cust = (BizCustomerBO) find(BizCustomerBO.class, custId);
		if(cust != null){
			claVO.setCust(cust);
			initCreditLimitApplyVOByCustId(custId, claVO);
		}
		 List<BizCollateralInfoBO> collateralInfos = findCollateralInfosByCL(claBO.getId());
		 if(CollectionUtil.isNotEmpty(collateralInfos)){
			 claVO.setCollateralInfos(collateralInfos);
		 }
		 List<BizGuarantorInfoBO> guarantorInfos = findGuarantorInfoByCL(claBO.getId());
		 if(CollectionUtil.isNotEmpty(guarantorInfos)){
			 claVO.setGuarantorInfos(guarantorInfos);
		 }
		return claVO;
	}
	
	/**
	 * 通过额度id查找担保品信息
	 */
	public List<BizCollateralInfoBO> findCollateralInfosByCL(Integer creditLimitId){
		String sql = "from BizCollateralInfoBO where creditLimitId = ?";
		return getHibernateTemplate().find(sql, creditLimitId);
	}
	
	public BizDisclosureInfoBO findDisclosureInfo(Integer custId){
		String sql = "from BizDisclosureInfoBO where custId = ?";
		List list = getHibernateTemplate().find(sql, custId);
		if(list != null &&  !list.isEmpty()){
			return  (BizDisclosureInfoBO) list.get(0);
		} 
		return null;
	}
	
	/**
	 * 通过额度id查找保证人信息
	 * @param creditLimitId
	 * @return
	 */
	public List<BizGuarantorInfoBO> findGuarantorInfoByCL(Integer creditLimitId){
		String sql = "from BizGuarantorInfoBO where creditLimitId = ?";
		return getHibernateTemplate().find(sql, creditLimitId);
	}
	/**
	 * 查看额度申请信息
	 */
	@Override
	public BizCreditLimitApplyBO getCreditLimitApplyByWorkItemId(
			String workItemId) throws BizException {
		try{
			String queryString = "from BizCreditLimitApplyBO where workItemId = ?";
			List list = getHibernateTemplate().find(queryString, workItemId);
			if(list != null &&  !list.isEmpty()){
				return  (BizCreditLimitApplyBO) list.get(0);
			} 
			return null;
		}catch(Exception e){
			 
		}
		return null;
		
	}
	
	public BizCreditLimitApplyBO getCreditLimitApplyByCustId(
			Integer custId) throws BizException {
		try{
			String queryString = "from BizCreditLimitApplyBO where custId = ?";
			List list = getHibernateTemplate().find(queryString, custId);
			if(list != null &&  !list.isEmpty()){
				return  (BizCreditLimitApplyBO) list.get(0);
			} 
			return null;
		}catch(Exception e){
			 
		}
		return null;
		
	}
	
	/**
	 * 查看客户基本信息
	 */
	public BizCustomerBO getCustomerInfo(int custID) throws BizException{
		try{
			String queryString = "from BizCustomerBO where userId = ?";
			List list = getHibernateTemplate().find(queryString, custID);
			if(list != null &&  !list.isEmpty()){
				return  (BizCustomerBO) list.get(0);
			} 
			return null;
		}catch(Exception e){
			 
		}
		return null;
	}
	
	/**
	 * 查看会员联系人
	 */
	public BizContactsBO getContactsInfo(int custID) throws BizException{
		try{
			String queryString = "from BizContactsBO where custId = ?";
			List list = getHibernateTemplate().find(queryString, custID);
			if(list != null &&  !list.isEmpty()){
				return  (BizContactsBO) list.get(0);
			} 
			return null;
		}catch(Exception e){
			 
		}
		return null;
		
	} 
	
	/**
	 * 查看会员工作信息
	 */
	public BizJobBO getJobInfo(int custID) throws BizException{
		try{
			String queryString = "from BizJobBO where custId = ?";
			List list = getHibernateTemplate().find(queryString, custID);
			if(list != null &&  !list.isEmpty()){
				return  (BizJobBO) list.get(0);
			} 
			return null;
		}catch(Exception e){
			 
		}
		return null;
		
	} 
	/**
	 * 查看担保基本信息
	 */
	public BizGuaranteeInfoBO getGuaranteeInfo(int guaranteeID) throws BizException{
		try{
			String queryString = "from BizGuaranteeInfoBO where guaranteeId = ?";
			List list = getHibernateTemplate().find(queryString, guaranteeID);
			if(list != null &&  !list.isEmpty()){
				return  (BizGuaranteeInfoBO) list.get(0);
			} 
			return null;
		}catch(Exception e){
			 
		}
		return null;
	}
	
	public BizCollateralInfoBO getCollateralInfo(Integer custId) throws BizException{
		try{
			String queryString = "from BizCollateralInfoBO where custId = ?";
			List list = getHibernateTemplate().find(queryString, custId);
			if(list != null &&  !list.isEmpty()){
				return  (BizCollateralInfoBO) list.get(0);
			} 
			return null;
		}catch(Exception e){
			 
		}
		return null;
	}
	@Override
	public BizDisclosureInfoBO getDisclosureInfo(Integer custId) throws BizException{
		try{
			String queryString = "from BizDisclosureInfoBO where custId = ?";
			List list = getHibernateTemplate().find(queryString, custId);
			if(list != null &&  !list.isEmpty()){
				return  (BizDisclosureInfoBO) list.get(0);
			} 
			return null;
		}catch(Exception e){
			 
		}
		return null;
	}
	
	/**
	 * 查看额度申请列表信息
	 * 
	 */
	public List getCreditLimitApplyList() throws BizException{
		
		try{
			String queryString = "";
			List list = getJdbcTemplate().queryForList(queryString);
			if(list != null &&  !list.isEmpty()){
				return  list;
			} 
			return null;
		}catch(Exception e){
			 
		}
		return null;
	}
	
	/**
	 * 更新额度申请基本信息
	 */
	public void updateCreditLimitApply(int creditLimitApplyID) throws BizException{
		String querySQL = "";
		this.getJdbcTemplate().execute(querySQL);
	}
	
	
	/**
	 * 查询额度申请流程列表
	 * 
	 * @param userId
	 * @param first
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageList<BizCreditLimitApplyVO> getCreditLimitApply(BizCreditLimitApplyVO creditLimitApplyVO, int first, int pageSize) {
		
		Object[] args = null;//wrapMyTaskSQL(creditLimitApplyVO);
		//log.debug("用户"+creditLimitApplyVO.getUserId()+"查询额度申请流程列表SQL："+args[0]);
		
		PageList<BizCreditLimitApplyVO> pl = new PageList<BizCreditLimitApplyVO>();
		int count = getCommonQuery().findCountByWapSQL((String)args[0], (Object[])args[1]) ;
		pl.setCount(count);
		List<BizCreditLimitApplyVO> limit = getCommonQuery().findBySQLByPages((String)args[0], first,
				pageSize, (Object[])args[1],BizCreditLimitApplyVO.class);
		pl.setData(limit);
		return pl;
	}

	public IFlowBS getFlowBS() {
		return flowBS;
	}


	public void setFlowBS(IFlowBS flowBS) {
		this.flowBS = flowBS;
	}

	public IRoleBS getRoleBS() {
		return roleBS;
	}

	public void setRoleBS(IRoleBS roleBS) {
		this.roleBS = roleBS;
	}
	
	@Override
	public void saveCreditlimtTabInfo(BizCreditLimitApplyVO creditLimitApplyVO)throws BizException {
		
		SysUser optUser = ViewOper.getUser();
		Date now = new Date();
		Integer creditLimitId = creditLimitApplyVO.getCreditLimitApply().getId();//额度id
		
		//更新客户信息开始
		BizCustomerBO cust = creditLimitApplyVO.getCust();
		//TODO
//		String mess = this.validateCust(cust);
//		if(null !=mess && mess.length()>0){
//			throw new BizException(mess);
//		}
		cust.setSysUpdateUser(optUser.getUserId()); 
		if(cust.getId()!=null){
			update(cust);
			updateUserByCust(cust);
		}else{
			save(cust);
			//updateUserByCust(cust);
		}
		//更新客户信息结束
		
		//更新联系人信息开始
		
		List<BizContactsBO> contacts = creditLimitApplyVO.getContacts();
		if(CollectionUtil.isNotEmpty(contacts)){
			for (BizContactsBO bizContactsBO : contacts) {
				bizContactsBO.setWorkItemId("0");//联系人信息不走流程
				bizContactsBO.setCustId(cust.getId());
				bizContactsBO.setSysUpdateUser(optUser.getUserId());
				if(NumberUtils.isBlank(bizContactsBO.getId())){
					bizContactsBO.setSysCreateUser(optUser.getUserId());
					bizContactsBO.setSysCreateTime(now);
					save(bizContactsBO);
				}else{
					update(bizContactsBO);
				}
			}
		}
		//删除移除的联系信息人
		List<BizContactsBO> removeContacts = creditLimitApplyVO.getRemoveContacts();
		if(CollectionUtil.isNotEmpty(removeContacts)){
			for (BizContactsBO bizContactsBO : removeContacts) {
				delete(bizContactsBO);
			}
		}
		//更新联系人信息结束
		
		//更新工作信息开始
		BizJobBO job = creditLimitApplyVO.getJob();
		job.setCustId(cust.getId());
		job.setWorkItemId("0");
		job.setSysUpdateUser(optUser.getUserId());
		if(NumberUtils.isBlank(job.getId())){
			job.setSysCreateUser(optUser.getUserId());
			job.setSysCreateTime(now);
			save(job);
		}else{
			update(job);
		}
		//更新工作信息结束
		//更新担保品信息开始
		List<BizCollateralInfoBO> collateralInfos = creditLimitApplyVO.getCollateralInfos();
		if(CollectionUtil.isNotEmpty(collateralInfos)){
			for (BizCollateralInfoBO bizCollateralInfoBO : collateralInfos) {
				
				bizCollateralInfoBO.setCreditLimitId(creditLimitId);//额度id
				bizCollateralInfoBO.setCustId(cust.getId());//会员id
				bizCollateralInfoBO.setSysUpdateUser(optUser.getUserId());
				
				//getHibernateTemplate().saveOrUpdate(bizCollateralInfoBO);
				
				if(NumberUtils.isBlank(bizCollateralInfoBO.getId())){//ID为空
					bizCollateralInfoBO.setSysCreateUser(optUser.getUserId());
					bizCollateralInfoBO.setSysCreateTime(now);
					save(bizCollateralInfoBO);
				}else{
					update(bizCollateralInfoBO);
				}
				
			}
		}
		//删除担保品信息
		List<BizCollateralInfoBO> removeCollateralInfos = creditLimitApplyVO.getRemoveCollateralInfos();
		if(CollectionUtil.isNotEmpty(removeCollateralInfos)){
			for (BizCollateralInfoBO bizCollateralInfoBO : removeCollateralInfos) {
				delete(bizCollateralInfoBO);
			}
		}
		//更新担保品信息结束
		//更新保证人信息开始
		List<BizGuarantorInfoBO> guarantorInfos = creditLimitApplyVO.getGuarantorInfos();
		if(CollectionUtil.isNotEmpty(guarantorInfos)){
			for (BizGuarantorInfoBO bizGuarantorInfoBO : guarantorInfos) {
				bizGuarantorInfoBO.setCreditLimitId(creditLimitId);
				bizGuarantorInfoBO.setSysUpdateUser(optUser.getUserId());
				bizGuarantorInfoBO.setCustId(cust.getId());
				if(NumberUtils.isBlank(bizGuarantorInfoBO.getId())){//ID为空
					bizGuarantorInfoBO.setSysCreateUser(optUser.getUserId());
					bizGuarantorInfoBO.setSysCreateTime(now);
					save(bizGuarantorInfoBO);
				}else{
					update(bizGuarantorInfoBO);
				}
			}
		}
		List<BizGuarantorInfoBO> removeGuarantorInfos = creditLimitApplyVO.getRemoveGuarantorInfos();
		if(CollectionUtil.isNotEmpty(removeGuarantorInfos)){
			for (BizGuarantorInfoBO bizGuarantorInfoBO : removeGuarantorInfos) {
				delete(bizGuarantorInfoBO);
			}
		}
		//更新保证人信息结束
	}
	
	/**
	 * 联动更新用户信息
	 * @param cust
	 */
	private void updateUserByCust(BizCustomerBO cust){
		SysUser user = this.userBS.findUserById(cust.getUserId());
//		user.setEmail(cust.getEmail());			//邮箱
		user.setUserName(cust.getCustName());	//用户名
		user.setTel(cust.getMobileTelephone()); //电话
		user.setSex(cust.getSex());				//性别
		user.setCardid(cust.getCertificateNum());//身份证
		user.setQq(cust.getQq());				// qq
		update(user);
	}
	
	@Override
	public String verContactsInfo(BizContactsBO contact) {
		StringBuffer result = new StringBuffer();
		
        if(null == contact ){
        	return result.toString();
        }
        
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");  
        String Ai = ""; 
        		
		Matcher m = p.matcher(contact.getMobileTelephone()); 
	    if( !m.matches()){
	    	result.append("用户:").append(contact.getContactsName()).append("请输入正确电话号码！<br/>");
	    }
		
	    if(AppConst.CERTIFICATE_TYPE_CD_SF.equals(contact.getCertificateTypeCd())){
	    	if (contact.getCertificateNum().length() == 18) {   
		        Ai = contact.getCertificateNum().substring(0, 17);   
		    } 
		    if (isNumeric(Ai) == false || contact.getCertificateNum().length() != 18) {   
		    	result.append("用户：").append(contact.getContactsName()).append("身份证18位号码都应为数字 ; 18位号码除最后一位外，都应为数字。<br/>");   
		    } 
	    }
        return result.toString();
	}
	
	/**  
     * 功能：判断字符串是否为数字  
     * @param str  
     * @return  
     */  
    private static boolean isNumeric(String str) {   
        Pattern pattern = Pattern.compile("[0-9]*");   
        Matcher isNum = pattern.matcher(str);   
        if (isNum.matches()) {   
            return true;   
        } else {   
            return false;   
        }   
    }
	@Override
	public String verCollateralInfo(BizCollateralInfoBO collateralInfoBO) {
		StringBuffer result = new StringBuffer();
		
        if(null == collateralInfoBO ){
        	return result.toString();
        }
        if(null == collateralInfoBO.getCollateralName()||collateralInfoBO.getCollateralName().length()<=0){
        	result.append("担保品名称不能为空<br/>");
        }
        if(null == collateralInfoBO.getCollateralType()||collateralInfoBO.getCollateralType().length()<=0 ){
        	result.append("担保品类型不能为空<br/>");
        }
        if(null == collateralInfoBO.getCollateralWorth()){
        	result.append("担保品价值不能为空<br/>");
        }
        if(null == collateralInfoBO.getCollateralExplain()||collateralInfoBO.getCollateralExplain().length()<=0 ){
        	result.append("担保品说明不能为空<br/>");
        }
        if(null == collateralInfoBO.getGuarantorName()||collateralInfoBO.getGuarantorName().length()<=0 ){
        	result.append("抵质押人姓名不能为空<br/>");
        }
        if(null == collateralInfoBO.getGuarantTypeCd()||collateralInfoBO.getGuarantTypeCd().length()<=0 ){
        	result.append("保证人类型不能为空<br/>");
        }
        if(null == collateralInfoBO.getGuarantorPaType()||collateralInfoBO.getGuarantorPaType().length()<=0 ){
        	result.append("抵质押人证件类型不能为空<br/>");
        }
        if(AppConst.CERTIFICATE_TYPE_CD_SF.equals(collateralInfoBO.getGuarantorPaType())){
        	// 身份证
            String Ai = ""; 
            		
    		if (collateralInfoBO.getGuarantorPaNo().length() == 18) {   
    	        Ai = collateralInfoBO.getGuarantorPaNo().substring(0, 17);   
    	    } 
    	    if (isNumeric(Ai) == false || collateralInfoBO.getGuarantorPaNo().length() != 18) {   
    	    	result.append("担保品：").append(collateralInfoBO.getCollateralName()).append("身份证18位号码都应为数字 ; 18位号码除最后一位外，都应为数字。<br/>");   
    	    }  
        }
        return result.toString();
	}
	@Override
	public String verGuarantorInfo(BizGuarantorInfoBO guarantorInfoBO) {
		StringBuffer result = new StringBuffer();
		
        if(null == guarantorInfoBO ){
        	return result.toString();
        }
        if(null == guarantorInfoBO.getGuarantorName()||guarantorInfoBO.getGuarantorName().length()<=0){
        	result.append("保证人名称不能为空<br/>");
        }
        if(null == guarantorInfoBO.getGuarantTypeCd()||guarantorInfoBO.getGuarantTypeCd().length()<=0 ){
        	result.append("担保类型不能为空<br/>");
        }
        if(null == guarantorInfoBO.getGuarantorPaType()||guarantorInfoBO.getGuarantorPaType().length()<=0 ){
        	result.append("保证人证件类型不能为空<br/>");
        }
        if(null == guarantorInfoBO.getGuarantorAmt()){
        	result.append("担保金额不能为空<br/>");
        }
        if(null == guarantorInfoBO.getGuarantorExplain()||guarantorInfoBO.getGuarantorExplain().length()<=0 ){
        	result.append("担保说明不能为空<br/>");
        }
        if(AppConst.CERTIFICATE_TYPE_CD_SF.equals(guarantorInfoBO.getGuarantorPaType())){
        	// 身份证
            String Ai = ""; 
            		
    		if (guarantorInfoBO.getGuarantorPaNo().length() == 18) {   
    	        Ai = guarantorInfoBO.getGuarantorPaNo().substring(0, 17);   
    	    } 
    	    if (isNumeric(Ai) == false || guarantorInfoBO.getGuarantorPaNo().length() != 18) {   
    	    	result.append("保证人：").append(guarantorInfoBO.getGuarantorName()).append("身份证18位号码都应为数字 ; 18位号码除最后一位外，都应为数字。<br/>");   
    	    }  
        }
        return result.toString();
	}
	
	/**
	 * 计算信用等级
	 * @param score
	 * @return
	 */
	private String tranformCreditRate(Integer score){
		if(score<50){
			return AppConst.CREDITRATE_E;
		}else if (score<70){
			return AppConst.CREDITRATE_D;
		}else if (score<80){
			return AppConst.CREDITRATE_C;
		}else if (score<90){
			return AppConst.CREDITRATE_B;
		}else if (score<100){
			return AppConst.CREDITRATE_A;
		}else{
			return AppConst.CREDITRATE_AA;
		}
	}

}
