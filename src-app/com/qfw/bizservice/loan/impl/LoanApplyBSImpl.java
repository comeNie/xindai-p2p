package com.qfw.bizservice.loan.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
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

import com.qfw.bizservice.bussparams.IProductInfoBS;
import com.qfw.bizservice.credit.ICreditBS;
import com.qfw.bizservice.credit.report.ICreditReportBS;
import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.bizservice.custinfo.account.ICustAccountBS;
import com.qfw.bizservice.loan.ILoanApplyBS;
import com.qfw.common.bizservice.IMsgTemplateBS;
import com.qfw.common.bizservice.IParamBS;
import com.qfw.common.bizservice.ISeqBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.bizservice.permission.IRoleBS;
import com.qfw.common.bizservice.permission.IUserBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.permission.SysRole;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.CollectionUtil;
import com.qfw.common.util.NumberUtils;
import com.qfw.common.util.web.ViewOper;
import com.qfw.dao.loan.ILoanApplyDAO;
import com.qfw.dao.loan.loanApprove.ILoanApproveDAO;
import com.qfw.jbpm.bizservice.IFlowBS;
import com.qfw.jbpm.model.JbpmConst;
import com.qfw.jbpm.model.bo.Jbpm4AuditOpinion;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizAuthBO;
import com.qfw.model.bo.BizCreditReportBO;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.bo.BizLoanApplyBO;
import com.qfw.model.bo.BizLoanApproveBO;
import com.qfw.model.bo.BizLoanBO;
import com.qfw.model.bo.BizLoanIntentionBO;
import com.qfw.model.bo.BizProductBO;
import com.qfw.model.vo.bussparams.ProductInfoVO;
import com.qfw.model.vo.bussparams.SearchProductInfoVO;
import com.qfw.model.vo.credit.RequestCreditVO;
import com.qfw.model.vo.custinfo.CustInfoVO;
import com.qfw.model.vo.loan.LoanApplyVO;

@SuppressWarnings("unchecked")
@Service("loanApplyBS")
public class LoanApplyBSImpl extends BaseServiceImpl implements ILoanApplyBS {

	private Logger log = Logger.getLogger(LoanApplyBSImpl.class);

	@Autowired
	@Qualifier("flowBS")
	private IFlowBS flowBS;
	@Autowired
	@Qualifier("roleBS")
	private IRoleBS roleBS;
	@Autowired
	private ILoanApplyDAO loanApplyDAO;
	@Autowired
	private ILoanApproveDAO approveDAO;
	@Autowired
	private IUserBS userBS;
	@Autowired
	private ICreditBS creditBS;
	@Autowired
	public ISeqBS seqBS;
	@Autowired
	private IProductInfoBS productInfoBS;
	@Autowired
	private ICustInfoBS custInfoBS;
	@Autowired
	private ICustAccountBS custAccountBS;
	@Autowired
	private IParamBS paramBS;
	@Autowired
	private ICreditReportBS creditReportBS;
	@Autowired
	private IMsgTemplateBS msgTemplateBS;
	
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
	
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void saveLoanApplyForFlow(LoanApplyVO loanApplyVO, SysUser auditUser, SysRole auditRole)
			throws BizException {
		try {
			log.info("借款申请工作流开始");
			if(null == loanApplyVO){
				throw new BizException("借款申请参数不能为空");
			}
			CustInfoVO cust = null;
			if(null == loanApplyVO.getCustId()||loanApplyVO.getCustId().length()<=0){
//				user = ViewOper.getUser();
				throw new BizException("客户id不能为空");
			}else{
				cust = custInfoBS.findCustInfoById(Integer.valueOf(loanApplyVO.getCustId()));
			}
			if(null == cust){
				throw new BizException("客户信息获取失败");
			}
			String valiMess = validateVO(loanApplyVO);
			if(valiMess.length()>0){
				log.warn("客户："+cust.getCustName()+" 借款申请失败："+valiMess);
				throw new BizException("借款申请失败："+valiMess);
			}
			
			// 额度验证
			RequestCreditVO creditSearchVO = new RequestCreditVO();
			creditSearchVO.setRelId(cust.getCustId().toString());
			Map<String, BigDecimal> creditMap = this.creditBS.surplusCreditAmt(creditSearchVO, null);
			if(null == creditMap || null==(creditMap.get(cust.getCustId().toString()))){
				log.warn("客户："+cust.getCustName()+" 借款申请失败，额度可用余额校验失败：额度信息获取不到!");
				throw new BizException("额度可用余额校验失败：额度信息获取不到!");
			}else if(creditMap.get(cust.getCustId().toString()).compareTo(loanApplyVO.getApplyAmt())<0){
				log.warn("客户："+cust.getCustName()+" 借款申请失败，额度可用余额校验失败：可用余额不足!");
				throw new BizException("额度可用余额校验失败：可用余额不足!");
			}
			
			//借款申请信息保存成功后调用风控岗审批工作流
			Map map = new HashMap();
			map.put(JbpmConst.APPLY_USER, ViewOper.getUser());
			map.put(JbpmConst.NEXT_AUDIT_USER, auditUser);
			map.put(JbpmConst.FLOW_REMARK, cust.getCustName()+"发起一笔"+loanApplyVO.getApplyAmt()
					+"借款申请("+ViewOper.getUser().getUserName()+"操作)");
			String workItemId = flowBS.startProcessInstanceAndCompleteTask("loan_apply", "提交", map);
			log.warn("客户："+cust.getCustName()+" 借款申请,工作流信息保存成功! workItemId="+workItemId);
			this.saveLoanApply(loanApplyVO, cust, workItemId);
		
		} catch (Exception e) {
			log.error("借款申请基本信息保存失败，原因："+e);
			throw new BizException("借款申请基本信息保存失败，原因："+e.getMessage());
		}
	}

	@Override
	public void saveLoanApply(LoanApplyVO loanApplyVO, CustInfoVO cust,
			String workItemId) throws BizException {
		
		if(null == loanApplyVO){
			throw new BizException("借款申请参数不能为空");
		}
		if(null == cust){
			throw new BizException("客户信息不能为空");
		}
		if(null == workItemId || workItemId.length()<=0){
			throw new BizException("工作流id不能为空");
		}
		
		String valiMess = validateVO(loanApplyVO);
		if(valiMess.length()>0){
			log.error("客户："+loanApplyVO.getCustName()+" 借款申请失败："+valiMess);
			throw new BizException("借款申请失败："+valiMess);
		}
		log.info("客户："+loanApplyVO.getCustName()+" 借款申请，申请金额："+loanApplyVO.getApplyAmt()+"保存信息开始!");
		SysUser optUser = ViewOper.getUser();
		
		// 初始化申请vo，填写信息
		loanApplyVO.setWorkItemId(workItemId);
		loanApplyVO.setApplyDate(new Date());
		loanApplyVO.setCustId(cust.getCustId().toString());
		loanApplyVO.setTermUnitCd(AppConst.TERM_UNIT_CD_MONTH);
		
		// 保存借款申请信息
		BizLoanApplyBO applyBO = new BizLoanApplyBO();
		this.loanApplyVOToBO(applyBO,loanApplyVO);
		applyBO.setApplyStatusCd(AppConst.APPROVAL_STATUS_PENDING);
		applyBO.setSysCreateUser(optUser.getUserId());
		applyBO.setSysUpdateUser(optUser.getUserId());
		applyBO.setSysCreateTime(new Date());
		applyBO.setSysUpdateTime(new Date());
		this.loanApplyDAO.save(applyBO);
		
		String 	txNO = seqBS.getResultNum(AppConst.TXNO);//获取交易编号
		log.info("客户："+loanApplyVO.getCustId()+"借款金额："+loanApplyVO.getApplyAmt()+"申请信息保存时，更新额度开始。交易编号："+txNO);
	
		RequestCreditVO creditVO = new RequestCreditVO();
		creditVO.setPassAmt(loanApplyVO.getApplyAmt());
		creditVO.setRelId(loanApplyVO.getCustId().toString());
		creditVO.setUnPassAmt(BigDecimal.ZERO);
		creditVO.setEventTypeCd(Integer.valueOf(AppConst.EVENTTYPE_LOAN));
		creditVO.setTxNO(txNO);
		this.creditBS.tranTransactionCredit(creditVO);
		log.info("客户："+loanApplyVO.getCustId()+"借款金额："+loanApplyVO.getApplyAmt()+"申请信息保存时，更新额度结束。交易编号："+txNO);
		log.error("客户："+loanApplyVO.getCustName()+" 借款申请保存申请信息成功!");
		// 更新申请次数
		this.creditReportBS.updateApplyNum(applyBO.getCustId(), 1, null, loanApplyVO.getApplyAmt());
		log.error("客户："+loanApplyVO.getCustName()+" 借款申请更新信用报告成功!");
		//更新会员认证情况开始
		Map<String,BizAuthBO> authMap = loanApplyVO.getAuthMap();
		if(authMap != null){
			Collection<BizAuthBO> authBOs = authMap.values();
			for (BizAuthBO bizAuthBO : authBOs) {
				bizAuthBO.setRelId(loanApplyVO.getCustId());
				bizAuthBO.setRelTypeCd(AppConst.AUTH_REL_TYPE_CD_LOAN);
				bizAuthBO.setSysUpdateUser(optUser.getUserId());
				bizAuthBO.setWorkItemId(workItemId);
				if(NumberUtils.isBlank(bizAuthBO.getId())){
					bizAuthBO.setSysCreateUser(optUser.getUserId());
					bizAuthBO.setSysCreateTime(new Date());
					save(bizAuthBO);
				}else{
					update(bizAuthBO);
				}
			}
		}
		//更新会员认证情况结束
		log.info("客户："+loanApplyVO.getCustName()+" 借款申请，申请金额："+loanApplyVO.getApplyAmt()+"保存信息结束!");
	}
	
	@Override
	public void savePreLoanApply(LoanApplyVO loanApplyVO, CustInfoVO cust,
			String workItemId) throws BizException {
		
		if(null == loanApplyVO){
			throw new BizException("借款申请参数不能为空");
		}
		if(null == cust){
			throw new BizException("客户信息不能为空");
		}
		if(null == workItemId || workItemId.length()<=0){
			throw new BizException("工作流id不能为空");
		}
		
		
		SysUser optUser = ViewOper.getUser();
		
		// 初始化申请vo，填写信息
		loanApplyVO.setWorkItemId(workItemId);
		loanApplyVO.setApplyDate(new Date());
		loanApplyVO.setCustId(cust.getCustId().toString());
		//loanApplyVO.setTermUnitCd(AppConst.TERM_UNIT_CD_DATE);
		
		// 保存借款申请信息
		BizLoanApplyBO applyBO =  new BizLoanApplyBO();
		this.loanApplyVOToBO(applyBO,loanApplyVO);
		applyBO.setApplyStatusCd(AppConst.APPROVAL_STATUS_PENDING);
		applyBO.setSysCreateUser(optUser.getUserId());
		applyBO.setSysUpdateUser(optUser.getUserId());
		applyBO.setSysCreateTime(new Date());
		applyBO.setSysUpdateTime(new Date());
		this.loanApplyDAO.save(applyBO);
		
		
	}
	
	/**
     * 数据校验
     * @return
     */
    private String validateVO(LoanApplyVO loanApplyVO){
    	StringBuffer mess = new StringBuffer();
    	if(null == loanApplyVO){
    		mess.append("借款申请信息为空!<br/>");
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
    	if(null == loanApplyVO.getLoanTerm()||loanApplyVO.getLoanTerm()<=0){
    		mess.append("还款期限必须大于0<br/>");
    	}
    	if(null == loanApplyVO.getExpectLoanRate() ){
    		mess.append("年利率不能为空<br/>");
    	}
    	if(null == loanApplyVO.getProductId()){
    		mess.append("所属产品信息不能为空<br/>");
    	}
    	try {
    		ProductInfoVO productInfoVO = productInfoBS.findProductInfoById(loanApplyVO.getProductId());
    		if(loanApplyVO.getExpectLoanRate().compareTo(productInfoVO.getLeastRateYear())<0){
        		mess.append("年利率不能小于产品最小年化利率<br/>");
        	}
        	if(loanApplyVO.getExpectLoanRate().compareTo(productInfoVO.getMostRateYear())>0){
        		mess.append("年利率不能大于产品最大年化利率<br/>");
        	}
		} catch (Exception e) {
			mess.append("产品最小年化利率获取失败!<br/>");
		}
    	if(null!=loanApplyVO.getPaymentWayCd() && loanApplyVO.getPaymentWayCd().length()>0){
    		if((null == loanApplyVO.getTrusteeCustId()|| loanApplyVO.getTrusteeCustId() == 0)
    				&&loanApplyVO.getPaymentWayCd().equals(AppConst.PAYMENT_WAY_CD_TRUSTEE)){
    			mess.append("支付方式为受托支付时，第三方用户必须有值<br/>");
    		}
    	}
    	return mess.toString();
    }
	
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void tranTakeAndCompleteTask(String taskId, String to,LoanApplyVO loanApplyVO, SysUser auditUser, SysRole auditRole,
			String auditStatus, String appNode, String html) throws BizException {
	try {
			log.info("客户："+loanApplyVO.getCustId()+"借款金额："+loanApplyVO.getApplyAmt()+"申请信息更新开始");
			//数据校验
			String valiMess = validateVO(loanApplyVO);
			if(valiMess.length()>0){
				throw new BizException("借款申请失败："+valiMess);
			}
			BizCustomerBO cust = custInfoBS.findCustById(Integer.valueOf(loanApplyVO.getCustId()));
			// 工作流
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put(JbpmConst.AUDIT_ROLE, auditRole);
			paraMap.put(JbpmConst.CUR_AUDIT_USER, ViewOper.getUser());
			paraMap.put(JbpmConst.NEXT_AUDIT_USER, auditUser);		// 下一阶段审批人
			paraMap.put(JbpmConst.AUDIT_STATUS_CD, auditStatus);
			if((AppConst.WORKITEM_NODE_TWO.equals(appNode)&&"通过".equals(to))
					||(AppConst.WORKITEM_NODE_INIT.equals(appNode)&&"撤销".equals(to))){
				// 流程结束，成功或失败
				paraMap.put(JbpmConst.FLOW_HTML, html);
			}
			if("提交".equals(to)){
				paraMap.put(JbpmConst.FLOW_REMARK, cust.getCustName()+"发起一笔"+loanApplyVO.getApplyAmt()
						+"借款申请("+ViewOper.getUser().getUserName()+"操作)");
				flowBS.completeTask(taskId, to, paraMap);//重新提交时不带审批意见
			}else{
				flowBS.completeTask(taskId,to, loanApplyVO.getComment(), paraMap);
			}
			log.info("客户："+loanApplyVO.getCustId()+"借款金额："+loanApplyVO.getApplyAmt()+"申请信息更新，工作流更新结束。");
			// 更新借款申请
			this.updateLoanApplyByTask(loanApplyVO, auditUser,cust, auditStatus, appNode);
		} catch (Exception e) {
			log.error("借款申请流程流转异常：",e);
			throw new BizException(e);
		}
	}
	
	@Override
	public void updateLoanApplyByTask(LoanApplyVO loanApplyVO, SysUser auditUser, BizCustomerBO cust,String auditStatus, String appNode) throws BizException{
		List<BizLoanApplyBO> boList =  this.loanApplyDAO.findByParams(loanApplyVO);
		if(null == boList || boList.size()!=1){
			throw new BizException("获取不到具体的借款申请信息");
		}
		BigDecimal diffRemainAmt = BigDecimal.ZERO;
		log.info("客户："+loanApplyVO.getCustId()+"借款金额："+loanApplyVO.getApplyAmt()+"申请信息更新，更新申请信息开始。");
		BizLoanApplyBO bo = boList.get(0);
		String workItemID = bo.getWorkItemId();
		diffRemainAmt = bo.getApplyAmt().subtract(loanApplyVO.getApplyAmt());// 差值：更新前金额-更新后金额
		this.loanApplyVOToBO(bo, loanApplyVO);
		if(auditStatus.equals(JbpmConst.AUDIT_STATUS_CD_YES)&&AppConst.WORKITEM_NODE_TWO.equals(appNode)){
			bo.setApplyStatusCd(AppConst.APPROVAL_STATUS_SUCCESS);
			workItemID = AppConst.WORKITEMID_NORMAL;
		}else if(auditStatus.equals(JbpmConst.AUDIT_STATUS_CD_REVOKE)){
			bo.setApplyStatusCd(AppConst.APPROVAL_STATUS_FAILURE);
			workItemID = AppConst.WORKITEMID_NORMAL;
		}
		bo.setWorkItemId(workItemID);
		this.loanApplyDAO.update(bo);
		log.info("客户："+loanApplyVO.getCustId()+"借款金额："+loanApplyVO.getApplyAmt()+"申请信息更新，更新申请信息结束。");
		//更新会员认证情况开始
		Map<String,BizAuthBO> authMap = loanApplyVO.getAuthMap();
		Collection<BizAuthBO> authBOs = authMap.values();
		for (BizAuthBO bizAuthBO : authBOs) {
			bizAuthBO.setRelId(loanApplyVO.getCustId());
			bizAuthBO.setRelTypeCd(AppConst.AUTH_REL_TYPE_CD_LOAN);
			bizAuthBO.setSysUpdateUser(ViewOper.getUser().getUserId());
			bizAuthBO.setWorkItemId(bo.getWorkItemId());
			if(NumberUtils.isBlank(bizAuthBO.getId())){
				bizAuthBO.setSysCreateUser(bo.getSysCreateUser());
				bizAuthBO.setSysCreateTime(new Date());
				save(bizAuthBO);
			}else{
				update(bizAuthBO);
			}
		}
		//更新会员认证情况结束
		log.info("客户："+loanApplyVO.getCustId()+"借款金额："+loanApplyVO.getApplyAmt()+"申请信息更新，更新会员信息结束。");
		if(auditStatus.equals(JbpmConst.AUDIT_STATUS_CD_YES)&&diffRemainAmt.compareTo(BigDecimal.ZERO)!=0){
			log.info("客户："+loanApplyVO.getCustId()+"借款金额："+loanApplyVO.getApplyAmt()+"申请信息,更新申请金额。差值："+diffRemainAmt);
			// 同意申请，且借款金额变更，再提交到下一阶段，更新剩余额度
			// 更新信用报告信息
			// 如果差值为负，代表本次操作，更新的借款金额比原先的大，需要扣减掉剩余额度
			this.creditReportBS.updateApplyAmtForRev(loanApplyVO.getCustId(),diffRemainAmt);
			String 	txNO = seqBS.getResultNum(AppConst.TXNO);//获取交易编号
			log.info("客户："+loanApplyVO.getCustId()+"借款金额："+loanApplyVO.getApplyAmt()+"申请信息，更新申请金额，联动更新额度开始。交易编号："+txNO);
			//额度占用
			RequestCreditVO creditVO = new RequestCreditVO();
			creditVO.setRelId(loanApplyVO.getCustId().toString());
			creditVO.setEventTypeCd(Integer.valueOf(AppConst.EVENTTYPE_LOAN));
			creditVO.setTxNO(txNO);
			//如果差值为负，代表本次操作，更新的借款金额比原先的大，需要重新占用额度，需要传入正值
			if(diffRemainAmt.compareTo(BigDecimal.ZERO)<0){
				creditVO.setPassAmt(BigDecimal.ZERO.subtract(diffRemainAmt));
				creditVO.setUnPassAmt(BigDecimal.ZERO);
			}else{
				// 差值为正，撤销部分金额，同时撤销金额需要传入正数值
				creditVO.setPassAmt(BigDecimal.ZERO);
				creditVO.setUnPassAmt(diffRemainAmt);
			}
			this.creditBS.tranTransactionCredit(creditVO);
			log.info("客户："+loanApplyVO.getCustId()+"借款金额："+loanApplyVO.getApplyAmt()+"申请信息，更新申请金额，联动更新额度结束。交易编号："+txNO);
		}
		
		// 借款发布信息
		// 最后一个阶段，且为同意操作
		if(auditStatus.equals(JbpmConst.AUDIT_STATUS_CD_YES)&&AppConst.WORKITEM_NODE_TWO.equals(appNode)){
			log.info("客户："+loanApplyVO.getCustId()+"借款金额："+loanApplyVO.getApplyAmt()+"申请信息，审批通过。");
			BizProductBO product = this.productInfoBS.findProductInfoById(loanApplyVO.getProductId());
			// 新增借款发布信息
			BigDecimal base = loanApplyVO.getTemderCountAmt();
			String 	txNO = seqBS.getResultNum(AppConst.TXNO);//获取交易编号
			Integer count = loanApplyVO.getApplyAmt().divide(base,0,BigDecimal.ROUND_DOWN).intValue();
			loanApplyVO.setTemderCountAmt(base);
			loanApplyVO.setTenderTtlCount(count);
			BizLoanApproveBO app = this.installLoanApprove(bo, loanApplyVO,product.getProductName());
			app.setLoanNum(txNO);
			this.loanApplyDAO.save(app);
			//短信提醒
			/*delete by yangjj
			 * try {
				Map dataMap = new HashMap();
				//【平台在线】尊敬的客户，您已成功实施借款发标，发标金额为XXXX ，感谢您选择平台在线
				//dataMap.put("date", DateUtils.getYmdhms(new Date()));//对应模板的${date}
				dataMap.put("amt", app.getLoanAmt());//对应模板的${amt}
				msgTemplateBS.sendMsg(AppConst.TRADE_TYPE_JKSQ, cust, dataMap);
			} catch (BizException e) {
				// TODO Auto-generated catch block
				 
			}*/
			//短信提醒
			// 更新信用报告信息
			//this.creditReportBS.updateApplyNum(loanApplyVO.getCustId(), null, 1, loanApplyVO.getApplyAmt());
		}else if(auditStatus.equals(JbpmConst.AUDIT_STATUS_CD_REVOKE)){
			String 	txNO = seqBS.getResultNum(AppConst.TXNO);//获取交易编号
			log.info("客户："+loanApplyVO.getCustId()+"借款金额："+loanApplyVO.getApplyAmt()+"申请信息，审批不通过，撤销额度开始。交易编号:"+txNO);
			//额度测试
			RequestCreditVO creditVO = new RequestCreditVO();
			creditVO.setPassAmt(BigDecimal.ZERO);
			creditVO.setRelId(loanApplyVO.getCustId().toString());
			creditVO.setUnPassAmt(loanApplyVO.getApplyAmt());
			creditVO.setEventTypeCd(Integer.valueOf(AppConst.EVENTTYPE_LOAN));
			creditVO.setTxNO(txNO);
			this.creditBS.tranTransactionCredit(creditVO);
			log.info("客户："+loanApplyVO.getCustId()+"借款金额："+loanApplyVO.getApplyAmt()+"申请信息，审批不通过，撤销额度结束。交易编号:"+txNO);
			log.info("客户："+loanApplyVO.getCustId()+"借款金额："+loanApplyVO.getApplyAmt()+"申请信息，审批不通过。");
			// 撤销
			// 更新信用报告信息
			this.creditReportBS.updateApplyAmtForRev(loanApplyVO.getCustId(),loanApplyVO.getApplyAmt());
			log.info("客户："+loanApplyVO.getCustId()+"借款金额："+loanApplyVO.getApplyAmt()+"申请信息，审批不通过，更新信用报告结束。");
		}
	}
	
	/**
	 * 查看额度申请信息
	 */
	public BizLoanApplyBO getLoanApply(int custId) throws BizException{
		try{
			String queryString = "from BizLoanApplyBO where custId = ?";
			List list = getHibernateTemplate().find(queryString, custId);
			if(list != null &&  !list.isEmpty()){
				return  (BizLoanApplyBO) list.get(0);
			} 
			return null;
		}catch(Exception e){
			 
		}
		return null;
		
	}
	
	@Override
	public LoanApplyVO findVOByParams(LoanApplyVO loanApplyVO) {
		List<BizLoanApplyBO> boList =  this.loanApplyDAO.findByParams(loanApplyVO);
		if(null!= boList&& boList.size()==1){
			BizLoanApplyBO bo = boList.get(0);
			loanApplyVO = this.loanApplyBoToVo(bo, null);
		}
		return loanApplyVO;
	}
	
	/**
	 * 组装借款发布信息
	 * @param bo
	 * @param loanApplyVO
	 * @throws BizException
	 */
	private BizLoanApproveBO installLoanApprove(BizLoanApplyBO bo ,LoanApplyVO loanApplyVO,String productName) throws BizException{
		BizLoanApproveBO app = new BizLoanApproveBO();
		app.setApproveStatusCd(AppConst.NO_FLAG);
		app.setCustId(Integer.valueOf(bo.getCustId()));
		app.setLoanAmt(bo.getApplyAmt());
		app.setLoanApplyId(bo.getId().toString());
		app.setLoanName(bo.getLoanName());
		app.setLoanNum("");
		app.setLoanPurpose(bo.getLoanPurpose());
		app.setLoanRate(bo.getExpectLoanRate());
		app.setLoanTerm(bo.getLoanTerm());
		app.setTermUnitCd(bo.getTermUnitCd());
		app.setRemark(bo.getRemark());
		app.setRepayTypeCd(bo.getRepayTypeCd());
		app.setTenderApproveTime(new Date());
		// 计算竞标结束时间
		Calendar ca = Calendar.getInstance();
		ca.setTime(app.getTenderApproveTime() );  
		ca.set(Calendar.DATE,ca.get(Calendar.DATE)+bo.getTenderTerm());  
		app.setTenderDueTime(ca.getTime());
		app.setTenderBalAmt(bo.getApplyAmt());
		app.setTenderUseAmt(BigDecimal.ZERO);
		app.setTenderLimitAmt(loanApplyVO.getTemderCountAmt());
		app.setTenderTerm(bo.getTenderTerm());
		app.setTenderTtlCount(loanApplyVO.getTenderTtlCount());
		app.setTenderBalCount(loanApplyVO.getTenderTtlCount());
		app.setTenderUseCount(0);
		app.setWorkItemId(bo.getWorkItemId());
		app.setSysCreateTime(new Date());
		app.setSysUpdateTime(new Date());
		app.setSysCreateUser(ViewOper.getUser().getUserId());
		app.setSysUpdateUser(ViewOper.getUser().getUserId());
		app.setPaymentWayCd(loanApplyVO.getPaymentWayCd());
		app.setTrusteeCustId(loanApplyVO.getTrusteeCustId());
		app.setProductId(loanApplyVO.getProductId().toString());
		app.setLoanTypeCd(loanApplyVO.getLoanTypeCd());
		app.setProductName(productName);
		app.setRecommendPercent(loanApplyVO.getRecommendPercent());
		return app;
	}
	
	/**
	 * 申请vo转bo
	 * @param loanApplyVO
	 * @return
	 */
	private BizLoanApplyBO loanApplyVOToBO(BizLoanApplyBO loanApplyBO ,LoanApplyVO loanApplyVO){
		loanApplyBO.setApplyAmt(loanApplyVO.getApplyAmt());
		loanApplyBO.setApplyDate(loanApplyVO.getApplyDate());
		loanApplyBO.setApplyStatusCd(loanApplyVO.getApplyStatusCd());
		loanApplyBO.setCustId(loanApplyVO.getCustId());
		loanApplyBO.setExpectLoanRate(loanApplyVO.getExpectLoanRate());
		loanApplyBO.setLoanName(loanApplyVO.getLoanName());
		loanApplyBO.setLoanPurpose(loanApplyVO.getLoanPurpose());
		loanApplyBO.setLoanTerm(loanApplyVO.getLoanTerm());
		loanApplyBO.setLoanTypeCd(loanApplyVO.getLoanTypeCd());
		loanApplyBO.setWorkItemId(loanApplyVO.getWorkItemId());
		loanApplyBO.setTermUnitCd(loanApplyVO.getTermUnitCd());
		loanApplyBO.setTenderTerm(loanApplyVO.getTenderTerm());
		loanApplyBO.setRepayTypeCd(loanApplyVO.getRepayTypeCd());
		loanApplyBO.setRemark(loanApplyVO.getRemark());
		loanApplyBO.setProductId(loanApplyVO.getProductId().toString());
		loanApplyBO.setTemderCountAmt(loanApplyVO.getTemderCountAmt());
		loanApplyBO.setPaymentWayCd(loanApplyVO.getPaymentWayCd());
		loanApplyBO.setTrusteeCustId(loanApplyVO.getTrusteeCustId());
		loanApplyBO.setEmail(loanApplyVO.getEmail());
		loanApplyBO.setAddr(loanApplyVO.getAddr());
		loanApplyBO.setJobStatusCd(loanApplyVO.getJobStatusCd());
		loanApplyBO.setRecommendPercent(loanApplyVO.getRecommendPercent());
		return loanApplyBO;
	}
	
	/**
	 * 申请vo转bo
	 * @param loanApplyVO
	 * @return
	 */
	private BizLoanIntentionBO loanApplyVOToIntentionBO(LoanApplyVO loanApplyVO){
		BizLoanIntentionBO loanApplyBO = new BizLoanIntentionBO();
		loanApplyBO.setApplyAmt(loanApplyVO.getApplyAmt());
		loanApplyBO.setApplyDate(loanApplyVO.getApplyDate());
		loanApplyBO.setEmail(loanApplyVO.getEmail());
		loanApplyBO.setCustId(loanApplyVO.getCustId());
		loanApplyBO.setCustName(loanApplyVO.getCustName());
		loanApplyBO.setExpectLoanRate(loanApplyVO.getExpectLoanRate());
		loanApplyBO.setLoanName(loanApplyVO.getLoanName());
		loanApplyBO.setLoanPurpose(loanApplyVO.getLoanPurpose());
		loanApplyBO.setLoanTerm(loanApplyVO.getLoanTerm());
		loanApplyBO.setLoanTypeCd(loanApplyVO.getLoanTypeCd());
		loanApplyBO.setWorkItemId(loanApplyVO.getWorkItemId());
		loanApplyBO.setTermUnitCd(loanApplyVO.getTermUnitCd());
		loanApplyBO.setTenderTerm(loanApplyVO.getTenderTerm());
		loanApplyBO.setRepayTypeCd(loanApplyVO.getRepayTypeCd());
		loanApplyBO.setRemark(loanApplyVO.getRemark());
		loanApplyBO.setProductId(loanApplyVO.getProductId().toString());
		loanApplyBO.setTel(loanApplyVO.getTel());
		loanApplyBO.setSex(loanApplyBO.getSex());
		loanApplyBO.setAddr(loanApplyVO.getAddr());
		loanApplyBO.setJobStatusCd(loanApplyVO.getJobStatusCd());
		return loanApplyBO;
	}
	
	/**
	 * 申请bo转vo
	 * @param bo
	 */
	private LoanApplyVO loanApplyBoToVo(BizLoanApplyBO bo, BizLoanApproveBO appBO){
		LoanApplyVO vo = new LoanApplyVO();
		vo.setApplyAmt(bo.getApplyAmt());
		vo.setApplyDate(bo.getApplyDate());
		vo.setApplyStatusCd(bo.getApplyStatusCd());
		vo.setComment("");
		vo.setCustId(bo.getCustId());
		vo.setExpectLoanRate(bo.getExpectLoanRate());
		vo.setId(bo.getId());
		vo.setLoanName(bo.getLoanName());
		vo.setLoanPurpose(bo.getLoanPurpose());
		vo.setLoanTerm(bo.getLoanTerm());
		vo.setLoanTypeCd(bo.getLoanTypeCd());
		vo.setRemark(bo.getRemark());
		vo.setRepayTypeCd(bo.getRepayTypeCd());
		vo.setTenderTerm(bo.getTenderTerm());
		vo.setTemderCountAmt(bo.getTemderCountAmt());
		if(bo.getTemderCountAmt()!=null && bo.getTemderCountAmt() !=null && bo.getTemderCountAmt().compareTo(BigDecimal.ZERO) != 0){
			vo.setTenderTtlCount(bo.getApplyAmt().divide(bo.getTemderCountAmt()).setScale(0).intValue());
		}else{
			vo.setTenderTtlCount(0);
		}
		
		vo.setAdvanceRate(BigDecimal.ZERO);
		vo.setDelayRate(BigDecimal.ZERO);
		vo.setOverdueRate(BigDecimal.ZERO);
		vo.setTermUnitCd(bo.getTermUnitCd());
		vo.setWorkItemId(bo.getWorkItemId());
		vo.setProductId(Integer.valueOf(bo.getProductId()));
		vo.setPaymentWayCd(bo.getPaymentWayCd());
		vo.setTrusteeCustId(bo.getTrusteeCustId());
		vo.setRecommendPercent(bo.getRecommendPercent());
		return vo;
		
	}
	
	/**
	 * 获取借款发布信息
	 * @throws BizException 
	 */
	public LoanApplyVO findLoanApply(int loanId) throws BizException{
		try{
			LoanApplyVO loanApp = new LoanApplyVO();
			BizLoanApproveBO loanApprove = this.getLoanApprove(loanId);
			
			//获取借款申请信息
			loanApp.setId(Integer.valueOf(loanApprove.getLoanApplyId()));
			loanApp = this.findVOByParams(loanApp);
			
			loanApp.setLoanName(loanApprove.getLoanName());
			loanApp.setApplyAmt(loanApprove.getLoanAmt());
			loanApp.setExpectLoanRate(loanApprove.getLoanRate());
			loanApp.setRepayTypeCd(loanApprove.getRepayTypeCd());
			loanApp.setLoanTerm(loanApprove.getLoanTerm());
			loanApp.setTemderCountAmt(loanApprove.getTenderLimitAmt()); //每份金额
			loanApp.setOverCountAmt(loanApprove.getTenderBalAmt()); //剩余金额
			loanApp.setTenderTtlCount(loanApprove.getTenderTtlCount()); //总份数
			loanApp.setOverTtlCount(loanApprove.getTenderBalCount());  //剩余份数
			loanApp.setBuyAmt(loanApprove.getTenderLimitAmt());	//购买金额  购买份数*最小投资金额   购买份数 * 每份金额 = 购买金额
			
			CustInfoVO custVO = custInfoBS.findCustInfoById(Integer.valueOf(loanApp.getCustId()));
			//BizAccountBO accountBO = custAccountBS.findAccountInfoByid(ViewOper.getUser().getUserId());
			BigDecimal  accountBaiAmtTemp = custVO.getAccountBalAmt();
			BigDecimal  canBuyCount = accountBaiAmtTemp.divideToIntegralValue(loanApprove.getTenderLimitAmt());
			loanApp.setCanBuyTtlCount(canBuyCount.intValue());	//可购买份数 账户余额 /最小投资每份金额
			loanApp.setBuyTtlCount(1);	//购买份数  默认为1
			
			return loanApp;
		}catch(Exception e){
			 
			log.error("获取借款发布信息异常",e);
		}
		return null;
	}
	
	/**
	 * 查看借款发布信息
	 */
	public BizLoanApproveBO getLoanApprove(int loanId) throws BizException{
		try{
			String queryString = "from BizLoanApproveBO where id = ?";
			List list = getHibernateTemplate().find(queryString, loanId);
			if(list != null &&  !list.isEmpty()){
				return  (BizLoanApproveBO) list.get(0);
			} 
			return null;
		}catch(Exception e){
			 
			log.error("查看借款发布信息异常",e);
		}
		return null;
	}
	
	@Override
	public BizLoanBO getLoan(int loanId) throws BizException{
		try{
			String queryString = "from BizLoanBO where id = ?";
			List list = getHibernateTemplate().find(queryString, loanId);
			if(list != null &&  !list.isEmpty()){
				return  (BizLoanBO) list.get(0);
			} 
			return null;
		}catch(Exception e){
			 
			log.error("查看借据信息异常：",e);
		}
		return null;
	}
	
	@Override
	public void initLoanApplyAuth(LoanApplyVO loanApplyVO) throws BizException{
		List<BizAuthBO> auths = getHibernateTemplate().find("from BizAuthBO where relTypeCd = '1' and relId = ?", loanApplyVO.getCustId());
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
			authMap = loanApplyVO.getAuthMap();
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
		loanApplyVO.setAuthMap(authMap);
	}
	
	/**
	 * 前台提交借款申请
	 * @param vo
	 */
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void submitLoanApply(LoanApplyVO loanApplyVO) throws BizException{
		try{
			//System.out.println("loanApplyVO.getProductId()===" + loanApplyVO.getProductId());
			
			String mess = validateVOForView(loanApplyVO);
			if(null != mess && mess.length()>0){
				throw new BizException(mess);
			}
			String minTenderAmt = paramBS.getParam(AppConst.PARM_MIN_TENDER_AMT);
			loanApplyVO.setTemderCountAmt(new BigDecimal(minTenderAmt));
			loanApplyVO.setExpectLoanRate(loanApplyVO.getExpectLoanRate().divide(new BigDecimal("100")));
			
			RequestCreditVO vo = new RequestCreditVO();
			vo.setRelTypeCd(AppConst.CREDITLIMIT_RELTYPE_CUST);
			vo.setRelId(String.valueOf(loanApplyVO.getCustId()));
			String workItemId = null;
			BizCustomerBO cust = custInfoBS.findCustById(Integer.valueOf(loanApplyVO.getCustId()));//获取客户信息
			SysUser applyUser = userBS.findUserById(cust.getUserId());//获取用户信息
			  if(creditBS.checkCreditlimit(vo)){//存在额度
				/*delete by yangjj 不校验额度
				 * if(!creditBS.checkCreditlimit(loanApplyVO.getCustId(), loanApplyVO.getApplyAmt())){
					throw new BizException("额度不够");
				}*/
				Map map = new HashMap();
				
				SysUser auditUser = userBS.findUserById(cust.getCustManagerId());
				map.put(JbpmConst.NEXT_AUDIT_USER, auditUser);//下一审核人，客户经理
				map.put(JbpmConst.APPLY_USER, applyUser);
				map.put(JbpmConst.FLOW_REMARK, "客户"+loanApplyVO.getCustName()+"申请一笔借款金额"+loanApplyVO.getApplyAmt()+"元");
				workItemId = flowBS.startProcessInstance("pre_loan_apply", null, map);//额度存在启动接口申请流程
				// mod by yangjj
//				workItemId = flowBS.startProcessInstance("pre_limits_loan_apply", null, map);//额度存在启动接口申请流程
				
				loanApplyVO.setWorkItemId(workItemId);
				loanApplyVO.setApplyDate(new Date());
				loanApplyVO.setCustId(cust.getId().toString());
				//loanApplyVO.setTermUnitCd(AppConst.TERM_UNIT_CD_DATE);
				
				// 保存借款申请信息
				BizLoanApplyBO applyBO = new BizLoanApplyBO();
				this.loanApplyVOToBO(applyBO,loanApplyVO);
				
				//更新借款期限信息
				if(AppConst.TERM_UNIT_CD_DATE.equals(applyBO.getTermUnitCd())){
					int num= applyBO.getLoanTerm()/30;
					applyBO.setLoanTerm(num+1);;
				}else if(AppConst.TERM_UNIT_CD_YEAR.equals(applyBO.getTermUnitCd())){
					applyBO.setLoanTerm(applyBO.getLoanTerm()*12);;
				}
				applyBO.setTermUnitCd(AppConst.TERM_UNIT_CD_MONTH);
				// 更新借款期限信息结束
				applyBO.setApplyStatusCd(AppConst.APPROVAL_STATUS_PENDING);
				applyBO.setSysCreateUser(applyUser.getUserId());
				applyBO.setSysUpdateUser(applyUser.getUserId());
				applyBO.setSysCreateTime(new Date());
				applyBO.setSysUpdateTime(new Date());
				this.loanApplyDAO.save(applyBO);
				
				String 	txNO = seqBS.getResultNum(AppConst.TXNO);//获取交易编号
				log.info("客户："+loanApplyVO.getCustId()+"借款金额："+loanApplyVO.getApplyAmt()+"前台申请时，额度已存在，更新额度开始。交易编号："+txNO);
				//额度占用
				RequestCreditVO creditVO = new RequestCreditVO();
				creditVO.setPassAmt(loanApplyVO.getApplyAmt());
				creditVO.setRelId(loanApplyVO.getCustId().toString());
				creditVO.setUnPassAmt(BigDecimal.ZERO);
				creditVO.setEventTypeCd(Integer.valueOf(AppConst.EVENTTYPE_LOAN));
				creditVO.setTxNO(txNO);
				this.creditBS.tranTransactionCredit(creditVO);
				log.info("客户："+loanApplyVO.getCustId()+"借款金额："+loanApplyVO.getApplyAmt()+"前台申请时，额度已存在，更新额度开始。交易编号："+txNO);
				// 更新申请次数
				this.creditReportBS.updateApplyNum(cust.getId().toString(), 1, null, loanApplyVO.getApplyAmt());
			}else{
				BizLoanIntentionBO loanIntentionBO = this.loanApplyVOToIntentionBO(loanApplyVO);
				
				//更新借款期限信息
				if(AppConst.TERM_UNIT_CD_DATE.equals(loanIntentionBO.getTermUnitCd())){
					int num= loanIntentionBO.getLoanTerm()/30;
					loanIntentionBO.setLoanTerm(num+1);;
				}else if(AppConst.TERM_UNIT_CD_YEAR.equals(loanIntentionBO.getTermUnitCd())){
					loanIntentionBO.setLoanTerm(loanIntentionBO.getLoanTerm()*12);;
				}
				loanIntentionBO.setTermUnitCd(AppConst.TERM_UNIT_CD_MONTH);
				// 更新借款期限信息结束
				
				loanIntentionBO.setSysCreateUser(applyUser.getUserId());
				loanIntentionBO.setSysUpdateUser(applyUser.getUserId());
				loanIntentionBO.setSysCreateTime(new Date());
				loanIntentionBO.setSysUpdateTime(new Date());
				loanIntentionBO.setProcessStatusCd("0");
				loanIntentionBO.setApplyDate(new Date());
				this.loanApplyDAO.save(loanIntentionBO);
				
				// 同步客户信息
				/*
				cust.setCustName(loanIntentionBO.getCustName());
				cust.setMobileTelephone(loanIntentionBO.getTel());
				applyUser.setUserName(loanIntentionBO.getCustName());
				applyUser.setTel(loanIntentionBO.getTel());
				this.update(cust);
				this.update(applyUser);
				*/
				/*Map map = new HashMap();
				map.put(JbpmConst.FLOW_REMARK, "客户"+loanApplyVO.getCustName()+"申请一笔借款金额"+loanApplyVO.getApplyAmt()+"元");
				SysRole role = roleBS.findSysRole(null, AppConst.WORKITEM_ROLE_CUST);
				if(role != null){
					map.put(JbpmConst.CUST_SERVICE_ROLE_ID, String.valueOf(role.getRoleId()));
				}
				if(cust!=null){
					map.put(JbpmConst.APPLY_USER, applyUser);
				}
				workItemId = flowBS.startProcessInstance("pre_limits_loan_apply", null, map);*///额度不存在启动二合一流程
			}
			/*CustInfoVO custVO = new CustInfoVO();
			custVO.setCustId(cust.getId());
			saveLoanApply(loanApplyVO, custVO, workItemId);*/
		}catch(BizException e){
			 
			throw e;
		}catch(Exception e){
			log.error("前台借款申请提交错误",e);
			 
			throw new BizException("借款申请失败，请联系管理员");
		}
		
	}
	
	/**
     * 前台数据校验
     * @return
     */
    private String validateVOForView(LoanApplyVO loanApplyVO){
    	StringBuffer mess = new StringBuffer();
    	if(null == loanApplyVO){
    		mess.append("借款申请信息为空!<br/>");
    	}
    	if(null == loanApplyVO.getCustId() || loanApplyVO.getCustId().length()<=0){
    		mess.append("找不到客户信息<br/>");
    	}
    	if(null == loanApplyVO.getCustName() || loanApplyVO.getCustName().length()<=0){
    		mess.append("客户名称不能为空<br/>");
    	}
    	if(null == loanApplyVO.getExpectLoanRate()){
    		mess.append("期望年利率不能为空<br/>");
    	}
    	if(null == loanApplyVO.getProductId() || loanApplyVO.getProductId()==0){
    		mess.append("产品不能为空<br/>");
    	}
    	if(null == loanApplyVO.getLoanTerm()||loanApplyVO.getLoanTerm()<=0){
    		mess.append("贷款期限必须大于0<br/>");
    	}
    	if(null == loanApplyVO.getApplyAmt() || loanApplyVO.getApplyAmt().compareTo(BigDecimal.ZERO)<=0){
    		mess.append("贷款金额必须大于0<br/>");
    	}
    	if(null == loanApplyVO.getTel() || loanApplyVO.getTel().length()<=0){
    		mess.append("联系电话不能为空<br/>");
    	}
    	return mess.toString();
    }
	
	/**
	 * 借款申请提交客户经理处理
	 * @param loanIntentionId
	 * @param auditUser
	 * @throws BizException
	 */
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void submitCustManage(Integer loanIntentionId,SysUser auditUser) throws BizException{
		try {
			BizLoanIntentionBO loanIntentionBO = getHibernateTemplate().get(
					BizLoanIntentionBO.class, loanIntentionId);
			if (loanIntentionBO != null) {
				BizLoanApplyBO loanApplyBO = new BizLoanApplyBO();
				loanApplyBO.setApplyAmt(loanIntentionBO.getApplyAmt());
				loanApplyBO.setApplyDate(loanIntentionBO.getApplyDate());
				loanApplyBO.setEmail(loanIntentionBO.getEmail());
				loanApplyBO.setCustId(loanIntentionBO.getCustId());
				loanApplyBO.setCustName(loanIntentionBO.getCustName());
				loanApplyBO.setExpectLoanRate(loanIntentionBO
						.getExpectLoanRate());
				loanApplyBO.setLoanName(loanIntentionBO.getLoanName());
				loanApplyBO.setLoanPurpose(loanIntentionBO.getLoanPurpose());
				loanApplyBO.setLoanTerm(loanIntentionBO.getLoanTerm());
				loanApplyBO.setLoanTypeCd(loanIntentionBO.getLoanTypeCd());
				loanApplyBO.setTermUnitCd(loanIntentionBO.getTermUnitCd());
				loanApplyBO.setTenderTerm(loanIntentionBO.getTenderTerm());
				loanApplyBO.setRepayTypeCd(loanIntentionBO.getRepayTypeCd());
				loanApplyBO.setRemark(loanIntentionBO.getRemark());
				loanApplyBO.setProductId(loanIntentionBO.getProductId()
						.toString());
				loanApplyBO.setTel(loanIntentionBO.getTel());
				loanApplyBO.setSex(loanIntentionBO.getSex());
				loanApplyBO.setAddr(loanIntentionBO.getAddr());
				loanApplyBO.setJobStatusCd(loanIntentionBO.getJobStatusCd());
				loanApplyBO.setApplyStatusCd(AppConst.APPROVAL_STATUS_PENDING);
				loanApplyBO.setSysCreateUser(ViewOper.getUser().getUserId());
				loanApplyBO.setSysUpdateUser(ViewOper.getUser().getUserId());
				loanApplyBO.setSysCreateTime(new Date());
				loanApplyBO.setSysUpdateTime(new Date());
				String minTenderAmt = paramBS.getParam(AppConst.PARM_MIN_TENDER_AMT);
				loanApplyBO.setTemderCountAmt(null==minTenderAmt?AppConst.MIN_TENDER_AMT
						:new BigDecimal(minTenderAmt));
				/****/
				Map map = new HashMap();

				BizCustomerBO cust = custInfoBS.findCustById(Integer.valueOf(loanIntentionBO.getCustId()));// 获取客户信息
				
				SysUser applyUser = userBS.findUserById(cust.getUserId());// 获取用户信息

				cust.setCustManagerId(auditUser.getUserId());
				cust.setCustManagerName(auditUser.getUserName());
				getHibernateTemplate().update(cust);
				
				map.put(JbpmConst.NEXT_AUDIT_USER, auditUser);// 下一审核人，客户经理
				map.put(JbpmConst.APPLY_USER, applyUser);
				map.put(JbpmConst.FLOW_REMARK,"客户" + loanIntentionBO.getCustName() + "申请一笔借款金额"+ loanIntentionBO.getApplyAmt() + "元");
				String workItemId = flowBS.startProcessInstance("pre_limits_loan_apply", null, map);// 额度存在启动接口申请流程
				loanApplyBO.setWorkItemId(workItemId);
				getHibernateTemplate().save(loanApplyBO);
				
				loanIntentionBO.setProcessStatusCd(AppConst.YES_FLAG);//已处理
				getHibernateTemplate().save(loanIntentionBO);
				
				// 更新申请次数
				// 前台借款申请在填写额度信息后占用
//				this.creditReportBS.updateApplyNum(cust.getId().toString(), 1, null, loanIntentionBO.getApplyAmt());
			}
		} catch (Exception e) {
			log.error("借款申请分配客户经理错误",e);
			throw new BizException("借款申请分配客户经理错误");
		}
		
	}
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void setloanIntentionDisValid(Integer loanIntentionId,String refuseReason) throws BizException{
		try{
			BizLoanIntentionBO loanIntentionBO = getHibernateTemplate().get(
					BizLoanIntentionBO.class, loanIntentionId);
			loanIntentionBO.setProcessStatusCd("2");//失效
			loanIntentionBO.setRefuseReason(refuseReason);
			getHibernateTemplate().save(loanIntentionBO);
		}catch(Exception e){
			log.error(e);
			throw new BizException("设置失效状态失败");
		}
		
	}
	public IFlowBS getFlowBS() {
		return flowBS;
	}


	public void setFlowBS(IFlowBS flowBS) {
		this.flowBS = flowBS;
	}

	@Override
	public BizCreditReportBO initCreditReportVO(String custId) {
		return creditReportBS.findByCustInfo(custId);
	}

}
