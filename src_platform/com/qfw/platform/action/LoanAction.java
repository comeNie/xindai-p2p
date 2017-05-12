package com.qfw.platform.action;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qfw.bizservice.bussparams.IProductInfoBS;
import com.qfw.bizservice.credit.ICreditBS;
import com.qfw.bizservice.credit.ICreditLimitApplyBS;
import com.qfw.bizservice.creditor.ICreditorRightBS;
import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.bizservice.custinfo.account.ITransferAccountsBS;
import com.qfw.bizservice.loan.ICmsFriendlinkBS;
import com.qfw.bizservice.loan.ILoanApplyBS;
import com.qfw.bizservice.loan.ILoanBS;
import com.qfw.bizservice.loan.ILoanManageBS;
import com.qfw.bizservice.message.IMessageBoardBS;
import com.qfw.common.bizservice.ICodeDictBS;
import com.qfw.common.bizservice.IMsgTemplateBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.DateUtils;
import com.qfw.common.util.MobileUtil;
import com.qfw.common.util.NumberUtils;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizAccountBO;
import com.qfw.model.bo.BizCreditLimitBO;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.bo.BizDisclosureInfoBO;
import com.qfw.model.bo.BizMessageBoardBO;
import com.qfw.model.bo.BizProductBO;
import com.qfw.model.vo.credit.CreditQueryVO;
import com.qfw.model.vo.credit.RequestCreditVO;
import com.qfw.model.vo.creditor.CreditorRightVO;
import com.qfw.model.vo.loan.CmsFriendLinkVO;
import com.qfw.model.vo.loan.LoanApplyVO;
import com.qfw.model.vo.loan.LoanApproveVO;
import com.qfw.model.vo.loan.LoanSearchVO;
import com.qfw.model.vo.message.MessageBoardVO;
import com.qfw.platform.cache.CacheManager;
import com.qfw.platform.cache.CustomGenericManageableCaptchaService;
import com.qfw.platform.cache.JCaptchaEngine;
import com.qfw.platform.model.json.Pager;
import com.qfw.platform.model.loan.CollateralInfoVO;
import com.qfw.platform.model.loan.CreditReportDetailVO;
import com.qfw.platform.model.loan.CreditorRightDetailVO;
import com.qfw.platform.model.loan.CustomerDetailVO;
import com.qfw.platform.model.loan.LoanDetailVO;
import com.qfw.platform.model.loan.LoanRepayInfoVo;
import com.qfw.platform.model.loan.RepayPlanDtlVO;
import com.qfw.platform.model.vo.CustAccountVO;
import com.qfw.platform.model.vo.LoginInfo;
import com.qfw.platform.service.ILoanService;

/**
 * 借款列表
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "/loan")
public class LoanAction extends BaseAction {
	
	private Logger log = LogFactory.getInstance().getPlatformLogger();
	
	@Resource
	private CustomGenericManageableCaptchaService captchaService;
	@Resource(name = "loanManageBS")
	private ILoanManageBS loanManageBS;  //借款管理

	@Resource(name = "codeDictBS")
	private ICodeDictBS codeDictBS;     //通过下拉框类型获取下拉框

	@Resource(name = "loanService")
	private ILoanService loanService;
	
	@Resource(name = "loanApplyBS")
	private ILoanApplyBS loanApplyBS;

	@Resource(name = "creditorRightBS")
	private ICreditorRightBS creditorRightBS;  //债权核心服务
	  
	@Resource(name = "loanBS")
	private ILoanBS loanBS;     //借款核心服务
	
	@Resource(name = "creditBS")
	private ICreditBS creditBS;  //额度模块核心方法

	@Resource(name = "productInfoBS")
	private IProductInfoBS productInfoBS;    //产品信息
	
	@Resource(name = "cacheManager")
	private CacheManager cacheManager;   //缓存管理器
	
	@Autowired
	private IMsgTemplateBS msgTemplateBS;  //获取短信模块
	
	@Autowired
	private ICustInfoBS custInfoBS;   //客户信息
	
	@Autowired
	private ICreditLimitApplyBS creditLimitApplyBS; 
	
	@Resource(name = "messageBoardBS")
	private IMessageBoardBS messageBoardBS;
	
	@Resource(name = "cmsFriendlinkBS")
	private ICmsFriendlinkBS cmsFriendlinkBS;
	
	@Resource(name = "transferAccountsBS")
	private ITransferAccountsBS transferAccountsBS;
	
	/**
	 * 借款列表查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		setCommonData(request);
		return getResultPath(LOAN_INDEX);
	}
	/**
	 * 移动端借款协议
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/mobileLoanInfo")
	public String mobileLoanInfo(HttpServletRequest request, HttpServletResponse response) {
		setCommonData(request);
		return getResultPath(MOBILE_LOAN_INFO);
	}

	/**
	 * 借款列表查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String loanList(HttpServletRequest request,
			HttpServletResponse response) {
		setCommonData(request);
		// 移动设备判断（投资列表）
		boolean isMobile = MobileUtil.JudgeIsMoblie(request);
		if(isMobile){
			return getResultPath(MOBILE_LOAN_LIST);
		}
		return getResultPath(LOAN_LIST);
	}

	@ResponseBody
	@RequestMapping(value = "/ajaxQuery")
	public Pager loanAjaxQuery(@RequestParam("loanType") String loanType,
			@RequestParam("term") String term,
			@RequestParam("rate") String rate,
			@RequestParam("creditLevel") String creditLevel,
			@RequestParam("requestPage") int requestPage,
			@RequestParam("pageSize") int pageSize,
			HttpServletRequest request) {
		Pager pager = new Pager();
		LoanSearchVO loanSearchVO = new LoanSearchVO(); //借款管理
		if (!"-1".equals(loanType)) {
			loanSearchVO.setLoanType(loanType);
		}

		if (!"-1".equals(term)) {
			loanSearchVO.setLoanTerm(term);
		}
	
		
		if (!"-1".equals(rate)) {
			loanSearchVO.setLoanRate(rate);
		}
		
		if (!"-1".equals(creditLevel)) {
			loanSearchVO.setCreditRate(creditLevel);
		}
		try{
		  LoginInfo loginInfo = new LoginInfo();
          loginInfo=(LoginInfo) request.getSession().getAttribute("loginInfo");
          loanSearchVO.setCustId(loginInfo.getCustId().toString());
			}catch(Exception e) {
				 
			}
		try {
			int totalCount = loanManageBS.getApproveCountByVO(loanSearchVO);
			List<LoanApproveVO> loanApproveVOList = loanManageBS
					.findListPagesByVO(loanSearchVO, (requestPage - 1)
							* pageSize, pageSize);
			changeCodeVal(loanApproveVOList);
			pager.setTotalCount(totalCount);
			pager.setList(loanApproveVOList);
			
		} catch (BizException e) {
			 
		}
		return pager;
	}
	@ResponseBody
	@RequestMapping(value = "/friendLink")
	public Pager loanFriendLink() {
		Pager pager = new Pager();
		CmsFriendLinkVO cmsFriendlinkVO = new CmsFriendLinkVO(); //友情链接管理
//		String type="1";
////		if(type!=null){
////		   cmsFriendlinkVO.setType(type);
////		}
		try {
			List<CmsFriendLinkVO> cmsFriendlinkVOList = cmsFriendlinkBS.findInfoByIDVO();
			pager.setList(cmsFriendlinkVOList);
			
		} catch (BizException e) {
			 
		}
		return pager;
	}
	
	private void changeCodeVal(List<LoanApproveVO> loanApproveVOList) {
		if (null == loanApproveVOList || loanApproveVOList.isEmpty())
			return;
		for (LoanApproveVO loanApproveVO : loanApproveVOList) {
			loanApproveVO.setRepayTypeCdStr(cacheManager
					.getDisPlayNameByCodeTypeAndVal("repayTypeCd",
							loanApproveVO.getRepayTypeCd()));
			loanApproveVO.setApproveStatusCdStr(cacheManager
					.getDisPlayNameByCodeTypeAndVal("approveStatusCd",
							loanApproveVO.getApproveStatusCd()));
		}
	}

	
	/**
	 * 借款明细
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings({"rawtypes"})
	@RequestMapping(value = "/detail")
	public String loanDetail(@RequestParam("loanApproveId") int loanApproveId,
			HttpServletRequest request, HttpServletResponse response) {
		
			String errMes = "";
			String sucMes = "";
			HttpSession session = request.getSession();
			try{
				errMes = (String) session.getAttribute("errMes");
				sucMes = (String) session.getAttribute("sucMes");
				if(StringUtils.isNotEmpty(errMes)){
					setAttribute(request, "errMes", errMes);
					session.removeAttribute( "errMes");
					
				}
				
				if(StringUtils.isNotEmpty(sucMes)){
					setAttribute(request, "sucMes", sucMes);
					session.removeAttribute( "sucMes");
				}
				//下一次提交，为重复提交
			}catch(Exception ex){ex.printStackTrace();}

			CollateralInfoVO collateralInfoVO = new CollateralInfoVO();
		    //参数
			Map<String, Object> dataMap = new HashMap<String, Object>();
			//标的详情
			LoanDetailVO loanDetailVO = new LoanDetailVO();
			//账户信息
			CustAccountVO  custAccountVO = new CustAccountVO();
			//账户信息
			CustAccountVO  custAccountVO1 = new CustAccountVO();
			//还款信息
			LoanRepayInfoVo loanRepayInfoVo = new  LoanRepayInfoVo();
			// 投标记录
			List creditorRightDetailVOList = new ArrayList();
			//客户号
			Integer customerId = null;
			//个人信息
			CustomerDetailVO customerDetailVO = new CustomerDetailVO();
			//信用报告
			CreditReportDetailVO creditReportDetailVO = new CreditReportDetailVO();
			//认证信息
			List authDetailVOList = new ArrayList();
			//还款计划
			List repayPlanDetailVOList = new ArrayList();
			//转让记录
			List tranVoList = new ArrayList();
			
			String isLogin = "true";
			 
			
			// 标的详情
			try {
				
			   loanDetailVO = loanService.getLoanDetail(loanApproveId);
			   BizDisclosureInfoBO  disclosureInfo = creditLimitApplyBS.getDisclosureInfo(loanDetailVO.getCustId());
			   if(disclosureInfo != null){
				   collateralInfoVO.setCollateralAtt1(disclosureInfo.getCollateralAtt1());
				   collateralInfoVO.setCollateralAtt2(disclosureInfo.getCollateralAtt2());
				   collateralInfoVO.setCollateralAtt3(disclosureInfo.getCollateralAtt3());
			   }
			} catch (BizException e) {
				 
			}
			dataMap.put("collateralInfoVO", collateralInfoVO);
			//获取登陆信息
			LoginInfo loginInfo =  (LoginInfo) request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION);
			
						
			//未满标，跳转到投资页面 0-投标中
			if("0".equals(loanDetailVO.getApproveStatusCd())){
				try {
					
				//未登陆，跳转到未登陆投资详情页面
				if(loginInfo != null ){
					//账户信息
					custAccountVO1 = loanService.getCustAccount(loginInfo.getCustId());
	
					custAccountVO = setMaxBuy(custAccountVO1,loanDetailVO);
					// 投标记录
					/*creditorRightDetailVOList = loanService.getCreditorRight(loanApproveId,false);*/
					
						if (null != loanDetailVO) {
							customerId = loanDetailVO.getCustId();
							// 个人信息
						    customerDetailVO = loanService.getCustomerDetail(customerId);
							// 信用报告
							creditReportDetailVO = loanService.getCreditReport(customerId);
							// 认证信息
							authDetailVOList = loanService.getAuthDetail(customerId);
						//////System.out.println("3333");
							//设置参数
							dataMap.put("customerDetailVO", customerDetailVO);
							dataMap.put("creditReportDetailVO", creditReportDetailVO);
							dataMap.put("authDetailVOList", authDetailVOList);
						}
					dataMap.put("custAccountVO", custAccountVO);
					dataMap.put("creditorRightDetailVOList", creditorRightDetailVOList);
				}else{
					isLogin = "false";
				}
				
				
				dataMap.put("isLogin", isLogin);
				dataMap.put("loanDetailVO", loanDetailVO);
				setCommonData(request);
				setAttributes(request, dataMap);
				 
				} catch (BizException e) {
					 
				}	
				// 移动设备判断（投资详情）
				if(MobileUtil.JudgeIsMoblie(request)){
					return getResultPath(MOBILE_LOAN_DETAIL);
				}
				return getResultPath(LOAN_DETAIL);
				
			 //满标，跳转到查询页面
			}else{
				try{
					//还款信息
					loanRepayInfoVo = loanService.getLoanRepayInfo(loanApproveId);
					if("1".equals(loanDetailVO.getApproveStatusCd())){
						loanRepayInfoVo.setLoanApproveId(loanApproveId);
						loanRepayInfoVo.setNextRepayDate(DateUtils.addMonth(new Date() , 1));
						loanRepayInfoVo.setRestPeriods(loanDetailVO.getLoanTerm()); 
						BigDecimal rate =loanDetailVO.getLoanRate().divide(new BigDecimal(100),8,BigDecimal.ROUND_HALF_UP);
						////System.out.println("rate=="+rate);
						BigDecimal sumAmt = loanDetailVO.getLoanAmt().multiply(rate).multiply(new BigDecimal(loanDetailVO.getLoanTerm())).divide(new BigDecimal(12),2,BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);
						loanRepayInfoVo.setSumRepayAmt(sumAmt.add(loanDetailVO.getLoanAmt()));
					}
					////System.out.println("loanRepayInfoVo=="+loanRepayInfoVo.getSumRepayAmt()+";"+loanRepayInfoVo.getNextRepayDate()+";"+loanRepayInfoVo.getRestPeriods());
					dataMap.put("loanRepayInfoVo", loanRepayInfoVo);
					
					//未登陆，跳转到未登陆投资详情页面
			   if(loginInfo != null ){
					if (null != loanDetailVO) {
						customerId = loanDetailVO.getCustId();
						// 个人信息
					    customerDetailVO = loanService.getCustomerDetail(customerId);
						// 信用报告
						creditReportDetailVO = loanService.getCreditReport(customerId);
						// 认证信息
						authDetailVOList = loanService.getAuthDetail(customerId);
						
						//设置参数
						dataMap.put("customerDetailVO", customerDetailVO);
						dataMap.put("creditReportDetailVO", creditReportDetailVO);
						dataMap.put("authDetailVOList", authDetailVOList);
						dataMap.put("repayPlanDetailVOList", repayPlanDetailVOList);
						
					}
					
					dataMap.put("custAccountVO", custAccountVO);
					dataMap.put("creditorRightDetailVOList", creditorRightDetailVOList);
					dataMap.put("tranVoList", tranVoList);
			   }else{
				   isLogin = "false";
			   }
			   
			   setCommonData(request);
			   dataMap.put("isLogin", isLogin);
			   dataMap.put("loanDetailVO", loanDetailVO);
			   setAttributes(request, dataMap);
				} catch (BizException e) {
					 
				}				
				setCommonData(request);
				if(MobileUtil.JudgeIsMoblie(request)){
					return getResultPath(MOBILE_LOAN_DETAIL_NO);
				}
				return getResultPath(LOAN_DETAIL_NO);
			}

	}
	/**
	 * 满标投资详情-信息披露
	 */
	@RequestMapping(value = "/mobileInfoDisclosure")
	public String mobileInfoDisclosure(@RequestParam("custId") Integer custId,
			HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		CollateralInfoVO collateralInfoVO = new CollateralInfoVO();
		LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION);
		if(loginInfo != null){
			BizDisclosureInfoBO  disclosureInfo = creditLimitApplyBS.getDisclosureInfo(custId);
		   if(disclosureInfo != null){
			   collateralInfoVO.setCollateralAtt1(disclosureInfo.getCollateralAtt1());
			   collateralInfoVO.setCollateralAtt2(disclosureInfo.getCollateralAtt2());
			   collateralInfoVO.setCollateralAtt3(disclosureInfo.getCollateralAtt3());
		   }
		}
		setAttribute(request, "collateralInfoVO", collateralInfoVO);
		return getResultPath(MOBILE_INFO_DISCLOSURE);
	}
	
	@RequestMapping(value = "/mobileBidRecord")
	public String mobileBidRecord(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("loanApproveId") String loanApproveId) {
		setCommonData(request);
		setAttribute(request, "loanApproveId", loanApproveId);
		return getResultPath(MOBILE_BID_RECORD);
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajaxCreditorRight")
	public Pager AjaxCreditorRight(@RequestParam("approveStatusCd") String ApproveStatusCd,
			@RequestParam("loanApproveId") int loanApproveId) {
		Pager pager = new Pager();
		
		List<CreditorRightDetailVO> creditorRightDetailVOList = null;
		try {
			creditorRightDetailVOList = loanService.getCreditorRight(loanApproveId,false);
			for (CreditorRightDetailVO creditorRightDetailVO : creditorRightDetailVOList) {
				creditorRightDetailVO.setTenderTypeCd(cacheManager     //投标方式
						.getDisPlayNameByCodeTypeAndVal("tenderWay",
								creditorRightDetailVO.getTenderTypeCd()));
				
			}
		} catch (BizException e) {
			// TODO Auto-generated catch block
			 
		}
		pager.setPageSize(1);
		pager.setTotalCount(creditorRightDetailVOList==null?0:creditorRightDetailVOList.size());
		pager.setList(creditorRightDetailVOList);
		
		return pager;
	}
	
	//还款记录 
	@ResponseBody
	@RequestMapping(value = "/ajaxRepayPlanDetail")
	public Pager AjaxRepayPlanDetail(@RequestParam("approveStatusCd") String ApproveStatusCd,
			@RequestParam("loanApproveId") int loanApproveId) {
		Pager pager = new Pager();
		
		List<RepayPlanDtlVO> repayPlanDetailVOList = null;
		try {
			repayPlanDetailVOList = loanService.getRepayPlanDetail(loanApproveId);
			for (RepayPlanDtlVO repayPlanDetailVO : repayPlanDetailVOList) {
				repayPlanDetailVO.setRepayStatusCd(cacheManager
						.getDisPlayNameByCodeTypeAndVal("repayStatusCd",
								repayPlanDetailVO.getRepayStatusCd()));
				
			}
		} catch (BizException e) {
			// TODO Auto-generated catch block
			 
		}
		pager.setPageSize(1);
		pager.setTotalCount(repayPlanDetailVOList==null?0:repayPlanDetailVOList.size());
		pager.setList(repayPlanDetailVOList);
		
		return pager;
	}

	//转让记录 
	@ResponseBody
	@RequestMapping(value = "/ajaxTranList")
	public Pager AjaxTranList(@RequestParam("approveStatusCd") String ApproveStatusCd,
			@RequestParam("loanApproveId") int loanApproveId) {
		Pager pager = new Pager();
		
		List<CreditorRightDetailVO> tranVoList = null;
		try {
			tranVoList = loanService.getCreditorRight(loanApproveId,true);
			for (CreditorRightDetailVO creditorRightDetailVO : tranVoList) {
				creditorRightDetailVO.setTenderTypeCd(cacheManager
						.getDisPlayNameByCodeTypeAndVal("tenderTypeCd",
								creditorRightDetailVO.getTenderTypeCd()));
				
			}
		} catch (BizException e) {
			// TODO Auto-generated catch block
			 
		}
		pager.setPageSize(1);
		pager.setTotalCount(tranVoList==null?0:tranVoList.size());
		pager.setList(tranVoList);
		
		return pager;
	}
	
	@RequestMapping(value = "/tender")
	public String tender(@RequestParam("loanApproveId") Integer loanApproveId,
			@RequestParam("crAmt") double crAmt,
			@RequestParam("tenderLimitAmt") double tenderLimitAmt,
			@RequestParam("usableBuyCount") int usableBuyCount,
			@RequestParam("tenderBalCount") int tenderBalCount,
			HttpServletRequest request, HttpServletResponse response) {
		
		////System.out.println("loanApproveId==="+loanApproveId+"-----tenderLimitAmt="+tenderLimitAmt);
		////System.out.println("buyCount==="+buyCount+"-----usableBuyCount="+usableBuyCount);
		////System.out.println("tenderBalCount==="+tenderBalCount+"-----tenderBalCount="+tenderBalCount);
		try{
		LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION);
		 String Mobile = loginInfo.getUserSecurity().getMobile();
		 String idCard = loginInfo.getUserSecurity().getIdCard();
		 String realName = loginInfo.getUserSecurity().getRealName();
	
		//未身份认证或者未手机认证
		if(null == Mobile || "".equals(Mobile)  || null == idCard ||"".equals(idCard) || null == realName ||"".equals(realName) ){
			String errMes = "身份认证及手机认证后的用户，才能投标";
			try {
				errMes = URLEncoder.encode(errMes, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				 
			}
			return "redirect:/userSecurity/index.do?errMes="+errMes;
		}}catch(Exception e){
			
		}
		
		CreditorRightVO creditorRightVO = new CreditorRightVO();
		String errMes= "";
		String sucMes= "投标成功";
		
		setCommonData(request);
		if(tenderLimitAmt<=0){
			errMes = "非法操作";
			setAttribute(request, "errMes", errMes);
			return loanDetail(loanApproveId,request,response); 
		}
		//获取登陆信息
		LoginInfo loginInfo =  (LoginInfo) request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION);
		BigDecimal buyCount = BigDecimal.ZERO;
		if(crAmt%tenderLimitAmt == 0){
			buyCount = new BigDecimal(crAmt/tenderLimitAmt);
		}else{
			errMes = "购买的金额必须是"+tenderLimitAmt+"的倍数！";
			setAttribute(request, "errMes", errMes);
			return loanDetail(loanApproveId,request,response); 
		}
		
		
		if(buyCount.intValue() <= 0){
			errMes = "投标失败，购买金额必须大于零！";
			setAttribute(request, "errMes", errMes);
			return loanDetail(loanApproveId,request,response); 
		}else if(buyCount.intValue() > tenderBalCount){
			errMes = "投标失败，购买金额不能多于剩余金额！" ;
			setAttribute(request, "errMes", errMes);
			return loanDetail(loanApproveId,request,response); 
		}else if(buyCount.intValue() > usableBuyCount){
			errMes = "投标失败，购买金额不能多于可购买金额！";
			setAttribute(request, "errMes", errMes);
			return loanDetail(loanApproveId,request,response); 
		}else if(loginInfo == null){
			errMes = "投标失败，未登陆无法投标";
			setAttribute(request, "errMes", errMes);
			return loanDetail(loanApproveId,request,response); 
		}else if (loanApproveId == null){
			errMes = "投标失败，无借款编号";
			setAttribute(request, "errMes", errMes);
			return loanDetail(loanApproveId,request,response); 
		}else {
		    creditorRightVO.setCustId(loginInfo.getCustId());//投资人编号
			creditorRightVO.setLoanApproveId(loanApproveId); //发布表信息ID
			creditorRightVO.setCrAmt(new BigDecimal(crAmt));//投资金额 
			creditorRightVO.setTurnoverCount(new Integer(buyCount.intValue()));//投资份数
			creditorRightVO.setTenderTypeCd("1"); //投资类型,自动投标0\手动投标1
			//是否满标标识
			Boolean isFullTender;
			try {
				isFullTender = this.creditorRightBS.submitTrender(creditorRightVO);
				//如果满标后，调用放款申请
				if(isFullTender){
					//调用放款服务
					this.loanBS.startLoan(loanApproveId, loginInfo.getUserId());
					//this.loanBS.effectLoan(loanApproveId);
				} 
				//短信提醒
				try {
					BizCustomerBO cust = custInfoBS.findCustById(loginInfo.getCustId());
					BizAccountBO acc = transferAccountsBS.getAccountBO(loginInfo.getCustId());
					LoanDetailVO loanDetailVO = loanService.getLoanDetail(loanApproveId);
					Map<String,String> dataMap = new HashMap<String,String>();
					dataMap.put("AMT", NumberUtils.format2(crAmt));
					dataMap.put("CUST_NAME", cust.getCustName());
					dataMap.put("DATE", DateUtils.getYmdhms(new Date()));
					dataMap.put("LOAN_TERM", loanDetailVO.getLoanTerm().toString());
					dataMap.put("BALANCE", NumberUtils.format2(acc.getUsableBalAmt()));
					
					msgTemplateBS.sendMsg(AppConst.EVENTTYPE_TRENDER, cust, dataMap);
				} catch (Exception e) {
					log.error("投标短信发送失败"+e.getMessage());
				}
			} catch (BizException e) {
				 
				errMes = e.getMessage();
				if(errMes.length() > 100){
					errMes = "系统繁忙,请稍后再操作!";
				}
				
				request.getSession().setAttribute("errMes",errMes);
				return "redirect:/loan/detail.do?loanApproveId="+loanApproveId;
				
			 }
		}
 
		request.getSession().setAttribute("sucMes",sucMes);
		return "redirect:/loan/detail.do?loanApproveId="+loanApproveId;
		
	}
	
	
	
	
	private CustAccountVO setMaxBuy(CustAccountVO custAccountVO ,LoanDetailVO loanDetailVO){
 
		
		BigDecimal buyCount = custAccountVO.getAvaiBal().divide(loanDetailVO.getTenderLimitAmt(), 0,BigDecimal.ROUND_HALF_DOWN) ;
		
		if(buyCount.compareTo(new BigDecimal(loanDetailVO.getTenderBalCount())) == 1){
			buyCount = new BigDecimal(loanDetailVO.getTenderBalCount());
		}
		
		custAccountVO.setBuyCount(buyCount.intValue());
		return custAccountVO;
		
	
	}
	
	
	/**
	 * 我要借款
	 * @param loanApproveId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/iWantLoan")
	public String IWantLoan(HttpServletRequest request, HttpServletResponse response) {
		 
//		SearchProductInfoVO searchProductInfoVO = new SearchProductInfoVO();
//		List<ProductInfoVO> bizProductList = new ArrayList<ProductInfoVO>();
//			  
		
//		try {
//			bizProductList = productInfoBS.findProductInfo(searchProductInfoVO);
//		} catch (BizException e) {
//			 
//		}
//		
//		setAttribute(request, "bizProductList", bizProductList);
		
		setCommonData(request);
		return getResultPath(PRODUCT_HTML);
	}
	
	/**
	 * 我要借款
	 * @param loanApproveId
	 * @param request
	 * @param response
	 * @param Go_To_Loan_Credit 
	 * @return
	 */
	@RequestMapping(value = "/goToLoan")
	public String goToLoan(HttpServletRequest request, HttpServletResponse response) {
		
		Integer productNo = null ;
		setCommonData(request);
		try {
			//产品编号
			productNo = Integer.valueOf(request.getParameter("productNo"));//这个是从下面getReturnView中返回来的信息
			setAttribute(request, "productNo", productNo);
			setAttribute(request, "productId", productNo);
			//正确提示
			String sucMes = request.getParameter("sucMes");
			if(StringUtils.isNotEmpty(sucMes)){
				sucMes = URLDecoder.decode(sucMes, "UTF-8");
				////System.out.println("sucMes111=="+sucMes);
				setAttribute(request, "sucMes", sucMes);
			}
			//错误信息
			String errMes = request.getParameter("errMes");
			if(StringUtils.isNotEmpty(errMes)){
				errMes = URLDecoder.decode(errMes, "UTF-8");
				////System.out.println("errMes2222=="+errMes);
				setAttribute(request, "errMes", errMes);
			}
			
		} catch (Exception e) {
			String errMes = "非法操作!";
			setAttribute(request, "errMes", errMes);
			return getResultPath(PRODUCT_HTML);
		}
		
		
		
		if(null == productNo || "".equals(productNo)){
			String errMes = "非法操作!";
			setAttribute(request, "errMes", errMes);
			return getResultPath(PRODUCT_HTML);
		}
		
		
		LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION);
		if(loginInfo == null){
			String errMes = "登陆后，才能申请借款!";
			setAttribute(request, "errMes", errMes);
			boolean isMobile = MobileUtil.JudgeIsMoblie(request);
			if(isMobile){
				return getResultPath(MOBILE_LOGIN_INDEX );
			}
			return getResultPath(LOGIN_INDEX);
		}
		
		 String Mobile = loginInfo.getUserSecurity().getMobile();
		 String idCard = loginInfo.getUserSecurity().getIdCard();
		 String realName = loginInfo.getUserSecurity().getRealName();
	
		//未身份认证或者未手机认证
		if(null == Mobile || "".equals(Mobile)  || null == idCard ||"".equals(idCard) || null == realName ||"".equals(realName) ){
			String errMes = "身份认证及手机认证后的用户，才能申请借款";
			try {
				errMes = URLEncoder.encode(errMes, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				 
			}
			return "redirect:/userSecurity/index.do?errMes="+errMes;
			
		}
		
		
		
		CreditQueryVO vo = new CreditQueryVO();   //额度模块请求vo的一个对象
		vo.setRelId(String.valueOf(loginInfo.getCustId()));   //(客户id)
		vo.setRelTypeCd(AppConst.CREDITLIMIT_RELTYPE_CUST);  
		BizCreditLimitBO creditLimit = null ;
		/**
		 * 期望年利率
		 */
		BizProductBO bizProductBO = new BizProductBO();// TODO
		try {
			bizProductBO = productInfoBS.findProductInfoById(productNo);
		} catch (BizException e1) {
			e1.printStackTrace();
		}
		BigDecimal leastRateYear =bizProductBO.getLeastRateYear();
		BigDecimal mostRateYear = bizProductBO.getMostRateYear();
		String productName = bizProductBO.getProductName();
	    
		setAttribute(request, "productName", productName);
		setAttribute(request,"leastRateYear",leastRateYear);
		setAttribute(request,"mostRateYear",mostRateYear);
		
		
		/*
		 * delete by yangjj 删除额度模块
		 * creditLimit  = creditBS.findObjectByCustId(vo);
		Map<String, BigDecimal> supCreditAmt =  new HashMap<String, BigDecimal>();
		if(null != creditLimit){
			
			Date endDate = creditLimit.getEndDate();
			Date startDate = new Date();
			int loanTerm = DateUtils.diffInMonth(startDate,endDate);
			setAttribute(request, "loanTerm", loanTerm);
			RequestCreditVO requestCreditVO = new RequestCreditVO();
			requestCreditVO.setRelId(creditLimit.getRelId().toString());
			try {
				supCreditAmt  = creditBS.surplusCreditAmt(requestCreditVO, null);
			} catch (BizException e) {
				// TODO Auto-generated catch block
//				 
			}
			BigDecimal supAmt  =  supCreditAmt.get(creditLimit.getRelId().toString());
			setAttribute(request, "creditLimit", creditLimit);
			setAttribute(request, "supAmt", supAmt);
			  return getResultPath(Go_To_Loan_Credit);
			}else{
			  return getResultPath(Go_To_Loan);
			}*/
		
// 移动设备判断（我要借款）
		 if(MobileUtil.JudgeIsMoblie(request)){
			 return getResultPath(MOBILE_GO_TO_LOAN);
		 }
		 return getResultPath(Go_To_Loan);
		
	}
	
	private Map<String, BigDecimal> surplusCreditAmt(
			RequestCreditVO creditSearchVO, Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@RequestMapping(value = "/applyLoan")
	public String applyLoan(@ModelAttribute LoanApplyVO loanApply,@RequestParam("j_captcha") String j_captcha,HttpServletRequest request, HttpServletResponse response) {
		/*
		 * LoanManageVO loanManageVO = loanManageBS.findInfoById(loanApproveId);
		 * if (null != loanManageVO) { setCommonData(request);
		 * setAttribute(request, "loanManageVO", loanManageVO); return
		 * getResultPath(LOAN_DETAIL); } else { return ERROR_PAGE; }
		 */
		String errMes= "";
 
		////System.out.println("还款方式:"+loanApply.getRepayTypeCd());
		if (!checkCaptcha(request, j_captcha)) {
			errMes = "对不起，您输入的验证码错误.";
			setCommonData(request);
			return getReturnView(errMes,null,loanApply.getProductId().toString());
		}
	   
			LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION);
		  if(loginInfo == null){
			 errMes = "未登陆，不能借款申请!";
			setAttribute(request, "errMes", errMes);
			setCommonData(request);
			return getResultPath(LOGIN_INDEX);
			}
		/*  
		   String Mobile = loginInfo.getUserSecurity().getMobile();
		   String idCard = loginInfo.getUserSecurity().getIdCard();
		   String realName = loginInfo.getUserSecurity().getRealName();
			*/
		   //未身份认证或者未手机认证
			/*if(null == Mobile || "".equals(Mobile)  || null == idCard ||"".equals(idCard) || null == realName ||"".equals(realName) ){
				errMes = "身份认证及手机认证后的用户，才能申请借款";
						try {
							errMes = URLEncoder.encode(errMes, "UTF-8");
						} catch (UnsupportedEncodingException e) {
							 
						}
						return "redirect:/userSecurity/index.do?errMes="+errMes;						
			 }*/
	     try {
			 int count = loanManageBS.findLoanIntCount(loginInfo.getCustId(), AppConst.LOAN_INT_STATUS);
			 if(count > 0){
				errMes = "您已经有一笔借款意向，请不要重复申请!";
				setCommonData(request);
				return getReturnView(errMes,null,loanApply.getProductId().toString());
			 }
					loanApply.setCustId(String.valueOf(loginInfo.getCustId()));
					loanApply.setTel(loginInfo.getUserSecurity().getMobile());
					loanApply.setCustName(loginInfo.getCustName());
					loanApplyBS.submitLoanApply(loanApply);
			} catch (BizException e) {
				errMes = e.getMessage();
				setCommonData(request);
				return getReturnView(errMes,null,loanApply.getProductId().toString());
			}
		
	    setCommonData(request);
		return getReturnView(errMes,"借款申请中,请耐心等待消息！",loanApply.getProductId().toString());
	}
	/**
	 * 
	 * @param isExist
	 * @return
	 */
	private String getReturnView(String errMes,String sucMes,String productNo){
		if(StringUtils.isNotEmpty(errMes) ){  //不是空，即有错误信息
			try {
				////System.out.println("errMes535=="+errMes);
				errMes = URLEncoder.encode(errMes, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				 
			}
			return "redirect:/loan/goToLoan.do?productNo="+productNo+"&errMes="+errMes;	
		}
		if(StringUtils.isNotEmpty(sucMes)){
			try {
				////System.out.println("sucMes535=="+sucMes);
				sucMes = URLEncoder.encode(sucMes, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				 
			}
			return "redirect:/loan/goToLoan.do?productNo="+productNo+"&sucMes="+sucMes;	
		}
		return "redirect:/loan/goToLoan.do?productNo="+productNo;	
    }
	
	/**
	 * 检查验证码
	 * 
	 * @param request
	 * @param captcha
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxCaptcha")
	public Map<String, String> ajaxCaptcha(HttpServletRequest request,
			@RequestParam(JCaptchaEngine.CAPTCHA_INPUT_NAME) String captcha) {
		if (StringUtils.isEmpty(captcha))
			return getErrorJson("验证码不能为空.");

		if (!checkCaptcha(request, captcha)) {
			return getErrorJson("对不起，您输入的验证码错误.");
		}
		return getSuccessJson("");
	}
	
	
	/**
	 * 检查验证码
	 * 
	 * @return
	 */
	private boolean checkCaptcha(HttpServletRequest request,
			String challengeResponse) {
		if (StringUtils.isEmpty(challengeResponse))
			return false;
		String captchaID = request.getSession().getId();
		String challengeVal = StringUtils.upperCase(challengeResponse);
		if (StringUtils.isEmpty(challengeVal)
				|| captchaService.validateResponseForID(captchaID, challengeVal) == false) {
			return false;
		} else {
			return true;
		}
	}
	
	
	/**
	 *  ajax 增加留言
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxAddMsg")
	public Map<String, String> ajaxAddMsg(
			@RequestParam("loanId") int loanId,
			HttpServletRequest request,
			HttpSession session) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			if (request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION) != null ) {
				String msgContent = new String(request.getParameter("msgContent")
						.getBytes("iso8859-1"), "utf-8");
				LoginInfo loginInfo = (LoginInfo) session
						.getAttribute(AppConst.LOGIN_INFO_SESSION);
				BizMessageBoardBO bo = new BizMessageBoardBO();
				bo.setLoanId(loanId);
				bo.setRemainUser(loginInfo.getUserId());
				bo.setContent(msgContent);
				
				bo.setSysCreateTime(new Timestamp(new Date().getTime()));
				bo.setSysCreateUser(loginInfo.getUserId());
				bo.setSysUpdateTime(new Timestamp(new Date().getTime()));
				bo.setSysUpdateUser(loginInfo.getUserId());
				messageBoardBS.save(bo);
				jsonMap.put("reMes", "留言成功！");
				jsonMap.put("status", "1");
			}
		} catch (Exception e) {
			 
			jsonMap.put("reMes", "留言失败，请重试");
			jsonMap.put("status", "0");
		}
		return jsonMap;
	}
	
	
	/**
	 *  ajax 删除留言
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxDelMsg")
	public Map<String, String> ajaxDelMsg(
			@RequestParam("msgId") int msgId,
			HttpServletRequest request,
			HttpSession session) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			if (request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION) != null ) {
				
				this.messageBoardBS.deleteMsg(msgId);
				jsonMap.put("reMes", "留言信息删除成功");
				jsonMap.put("status", "1");
			}
		} catch (Exception e) {
			 
			jsonMap.put("reMes", "留言信息删除失败，请重试");
			jsonMap.put("status", "0");
		}
		return jsonMap;
	}
	
	/**
	 *  ajax 回复留言
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxReplyMsg")
	public Map<String, String> ajaxReplyMsg(
			@RequestParam("loanId") int loanId,
			@RequestParam("linkId") int linkId,
			HttpServletRequest request,
			HttpSession session) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			if (request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION) != null ) {
				String msgContent = new String(request.getParameter("msgContent")
						.getBytes("iso8859-1"), "utf-8");
				
				LoginInfo loginInfo = (LoginInfo) session
						.getAttribute(AppConst.LOGIN_INFO_SESSION);
				BizMessageBoardBO bo = new BizMessageBoardBO();
				bo.setLoanId(loanId);
				bo.setLinkId(linkId);
				bo.setRemainUser(loginInfo.getUserId());
				bo.setContent(msgContent);
				
				bo.setSysCreateTime(new Timestamp(new Date().getTime()));
				bo.setSysCreateUser(loginInfo.getUserId());
				bo.setSysUpdateTime(new Timestamp(new Date().getTime()));
				bo.setSysUpdateUser(loginInfo.getUserId());
				messageBoardBS.save(bo);
				jsonMap.put("reMes", "留言成功！");
				jsonMap.put("status", "1");
			}
		} catch (Exception e) {
			 
			jsonMap.put("reMes", "留言失败，请重试");
			jsonMap.put("status", "0");
		}
		return jsonMap;
	}
	
	
	
	/**
	 *  ajax 查询留言列表
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxQueryMsg")
	public Pager ajaxQueryMsg(
			@RequestParam("loanId") int loanId,
			@RequestParam("requestPage") int requestPage,
			@RequestParam("pageSize") int pageSize,
			HttpSession session) {
		Pager pager = null;
		List<MessageBoardVO> list = null;
		try {
			if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null) {
				LoginInfo loginInfo = (LoginInfo) session
						.getAttribute(AppConst.LOGIN_INFO_SESSION);
				pager = new Pager();
				list = messageBoardBS.findInfoPagesByLoanId(loanId,0, pageSize);
				pager.setList(list);
				return pager;
			}
		} catch (Exception e) {
			 
		}
		return pager;
	}
	
	
	
	/**
	 *  ajax 查询留言回复列表
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxQueryReplyMsg")
	public Pager ajaxQueryReplyMsg(
			@RequestParam("linkId") int linkId,
			@RequestParam("requestPage") int requestPage,
			@RequestParam("pageSize") int pageSize,
			HttpSession session) {
		Pager pager = null;
		List<MessageBoardVO> list = null;
		try {
			if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null) {
				LoginInfo loginInfo = (LoginInfo) session
						.getAttribute(AppConst.LOGIN_INFO_SESSION);
				pager = new Pager();
				list = messageBoardBS.findInfoPagesByLinkId(linkId,0, pageSize);
				pager.setList(list);
				return pager;
			}
		} catch (Exception e) {
			 
		}
		return pager;
	}
}