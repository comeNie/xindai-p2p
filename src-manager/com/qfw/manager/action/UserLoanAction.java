package com.qfw.manager.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.bizservice.custinfo.account.ITransferAccountsBS;
import com.qfw.bizservice.loan.ILoanManageBS;
import com.qfw.bizservice.repay.IRepayBS;
import com.qfw.common.bizservice.IMsgTemplateBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.util.CollectionUtil;
import com.qfw.common.util.DateUtils;
import com.qfw.common.util.MobileUtil;
import com.qfw.common.util.NumberUtils;
import com.qfw.manager.service.IUserLoanService;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizAccountBO;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.vo.repay.RepayInfoVO;
import com.qfw.platform.cache.CacheManager;
import com.qfw.platform.model.json.Message;
import com.qfw.platform.model.json.Pager;
import com.qfw.platform.model.vo.LoginInfo;

/**
 * 会员中心-借款管理
 * @author Teddy
 */
@Controller
@RequestMapping("/userLoan")
public class UserLoanAction extends BaseAction {

	@Resource(name = "loanManageBS")
	private ILoanManageBS loanManageBS;// 我的借款
	@Resource(name = "userLoanService")
	private IUserLoanService userLoanService;
	@Resource(name = "repayBS")
	private IRepayBS repayBS;
	
	@Resource(name = "transferAccountsBS")
	private ITransferAccountsBS transferAccountsBS;
	
	@Resource(name = "cacheManager")
	private CacheManager cacheManager;
	@Autowired
	
	@Resource(name = "custInfoBS")
	private ICustInfoBS custInfoBS;
	
	@Resource(name = "msgTemplateBS")
	private IMsgTemplateBS msgTemplateBS;
	
	// 我的借款
	@RequestMapping(value = "/myLoan")
	public String myLoan(HttpServletRequest request, HttpServletResponse response) {
		// 设置公共数据
		setCommonData(request);
		//setAttribute(request,"myVid","userinfohead4");
		String myVid = request.getParameter("myVid");
		setAttribute(request,"myVid",myVid);
		// 绑定数据
		boolean isMobile = MobileUtil.JudgeIsMoblie(request);
		if(isMobile){
			return getResultPath(MOBILE_MY_LOAN);
		}
		return getResultPath(MY_LOAN);
	}
	
	// 我的借款
		@RequestMapping(value = "/myLoanInt")
		public String myLoanInt(HttpServletRequest request, HttpServletResponse response) {
			// 设置公共数据
			setCommonData(request);
			//setAttribute(request,"myVid","userinfohead4");
			String myVid = request.getParameter("myVid");
			setAttribute(request,"myVid",myVid);
			// 绑定数据
			boolean isMobile = MobileUtil.JudgeIsMoblie(request);
			if(isMobile){
				return getResultPath(MOBILE_MY_LOAN_INT);
			}
			return getResultPath(MY_LOAN_INT);
		}
	
		/**
		 *  ajax 我的借款意向
		 */
		@ResponseBody
		@RequestMapping(value = "/ajaxQueryMyLoanInt")
		public Pager ajaxQueryMyLoanInt(@RequestParam("requestPage") int requestPage,
				@RequestParam("pageSize") int pageSize, HttpSession session) {
			Pager pager = null;
			// TODO 前台查询条件
			try {
				if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null ) {
					LoginInfo loginInfo = (LoginInfo) session.getAttribute(AppConst.LOGIN_INFO_SESSION) ;
					pager = loanManageBS.findLoanInt(requestPage, pageSize, loginInfo.getCustId());
					List<Map<String,Object>> list = pager.getList();
					if(CollectionUtil.isNotEmpty(list)){
						Map changeColMap = new HashMap();
						changeColMap.put("PROCESS_STATUS_CD", "processStatusCd");
						list = cacheManager.changeListDisplayName(list, changeColMap);
						pager.setList(list);
					}
					
					return pager;
				}else{
					
				}
			} catch (Exception e) {
				 
			}
			return pager;
		}
		
	// 还款管理
	@RequestMapping(value = "/myRepay")
	public String myRepay(HttpServletRequest request, HttpServletResponse response) {
		// 设置公共数据
		setCommonData(request);
		//setAttribute(request,"myVid","userinfohead4");
		String myVid = request.getParameter("myVid");
		setAttribute(request,"myVid",myVid);
		// 绑定数据
		boolean isMobile = MobileUtil.JudgeIsMoblie(request);
		if(isMobile){
			return getResultPath(MOBILE_MY_REPAY);
		}
		return getResultPath(MY_REPAY);
	}

	
	// 还款管理
		@RequestMapping(value = "/myRepayDh")
		public String myRepayDh(HttpServletRequest request, HttpServletResponse response) {
			// 设置公共数据
			setCommonData(request);
			//setAttribute(request,"myVid","userinfohead4");
			String myVid = request.getParameter("myVid");
			setAttribute(request,"myVid",myVid);
			// 绑定数据
			return getResultPath(MY_REPAY_DH);
		}
	
	/**
	 * ajax 查找还款管理列表
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxQueryMyRepay")
	public Pager loanAjaxQuery(@RequestParam("requestPage") int requestPage,
			@RequestParam("pageSize") int pageSize, HttpSession session) {
		Pager pager = null;
		try {
			if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null ) {
				LoginInfo loginInfo = (LoginInfo) session.getAttribute(AppConst.LOGIN_INFO_SESSION) ;
				pager = loanManageBS.findLoaning(requestPage, pageSize, loginInfo.getCustId());
				
				List<Map<String,Object>> list = pager.getList();
				if(CollectionUtil.isNotEmpty(list)){
					Map changeColMap = new HashMap();
					changeColMap.put("REPAY_TYPE_CD", "repayTypeCd");
					changeColMap.put("LOAN_STATUS_CD", "loanStatusCd");
					list = cacheManager.changeListDisplayName(list, changeColMap);
					pager.setList(list);
				}
				
				
				return pager;
			}
		} catch (Exception e) {
			 
		}
		return pager;
	}

	/**
	 * ajax 查找还款管理列表
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxQueryMyRepayDh")
	public Pager ajaxQueryMyRepayDh(@RequestParam("requestPage") int requestPage,
			@RequestParam("pageSize") int pageSize, HttpSession session) {
		Pager pager = null;
		try {
			if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null ) {
				LoginInfo loginInfo = (LoginInfo) session.getAttribute(AppConst.LOGIN_INFO_SESSION) ;
				pager = loanManageBS.findLoaningDhByCustId(requestPage, pageSize, loginInfo.getCustId());
				
				List<Map<String,Object>> list = pager.getList();
				if(CollectionUtil.isNotEmpty(list)){
					Map changeColMap = new HashMap();
					changeColMap.put("REPAY_TYPE_CD", "repayTypeCd");
					changeColMap.put("LOAN_STATUS_CD", "loanStatusCd");
					list = cacheManager.changeListDisplayName(list, changeColMap);
					pager.setList(list);
				}
				return pager;
			}
		} catch (Exception e) {
			 
		}
		return pager;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/ajaxQueryMyRepayed")
	public Pager loanAjaxQueryed(@RequestParam("requestPage") int requestPage,
			@RequestParam("pageSize") int pageSize, HttpSession session) {
		Pager pager = null;
		try {
			if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null ) {
				LoginInfo loginInfo = (LoginInfo) session.getAttribute(AppConst.LOGIN_INFO_SESSION) ;
				pager = loanManageBS.findLoaned(requestPage, pageSize, loginInfo.getCustId());
				List<Map<String,Object>> list = pager.getList();
				if(CollectionUtil.isNotEmpty(list)){
					Map changeColMap = new HashMap();
					changeColMap.put("REPAY_TYPE_CD", "repayTypeCd");
					list = cacheManager.changeListDisplayName(list, changeColMap);
					pager.setList(list);
				}
				return pager;
			}
		} catch (Exception e) {
			 
		}
		return pager;
	}

	/**
	 *  ajax 申请中的借款
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxQueryApplyLoan")
	public Pager ajaxQueryApplyLoan(@RequestParam("requestPage") int requestPage,
			@RequestParam("pageSize") int pageSize, HttpSession session) {
		Pager pager = null;
		try {
			if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null) {
				LoginInfo loginInfo = (LoginInfo) session.getAttribute(AppConst.LOGIN_INFO_SESSION) ;
				pager = loanManageBS.findApplyLoan(requestPage, pageSize, loginInfo.getCustId());
				List<Map<String,Object>> list = pager.getList();
				if(CollectionUtil.isNotEmpty(list)){
					Map changeColMap = new HashMap();
					changeColMap.put("REPAY_TYPE_CD", "repayTypeCd");
					changeColMap.put("LOAN_TYPE_CD", "loanTypeCd");
					changeColMap.put("APPLY_STATUS_CD", "applyStatusCd");
					list = cacheManager.changeListDisplayName(list, changeColMap);
					pager.setList(list);
				}
				return pager;
			}
		} catch (Exception e) {
			 
		}
		return pager;
	}
	
	/**
	 *  ajax 筹标中的借款
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxQueryApproveLoan")
	public Pager ajaxQueryApproveLoan(@RequestParam("requestPage") int requestPage,
			@RequestParam("pageSize") int pageSize, HttpSession session) {
		Pager pager = null;
		// TODO 前台查询条件
		try {
			if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null ) {
				LoginInfo loginInfo = (LoginInfo) session.getAttribute(AppConst.LOGIN_INFO_SESSION) ;
				pager = loanManageBS.findApproveLoan(requestPage, pageSize, loginInfo.getCustId());
				List<Map<String,Object>> list = pager.getList();
				if(CollectionUtil.isNotEmpty(list)){
					Map changeColMap = new HashMap();
					changeColMap.put("REPAY_TYPE_CD", "repayTypeCd");
					changeColMap.put("APPROVE_STATUS_CD", "approveStatusCd");
					list = cacheManager.changeListDisplayName(list, changeColMap);
					pager.setList(list);
				}
				return pager;
			}
		} catch (Exception e) {
			 
		}
		return pager;
	}
	
	/**
	 * 还款界面（展示还款计划）
	 */
	@RequestMapping(value = "/myRepayDetail")
	public String myRepayDetail(HttpServletRequest request, HttpServletResponse response) {
		setCommonData(request);
		String loanId = request.getParameter("loanId");
		setAttribute(request, "loanId", loanId);
		//System.out.println("loanId === "+loanId);
		return getResultPath(MY_REPAY_DETAIL);
	}
	
	/**
	 * 还款界面（展示还款计划）
	 */
	@RequestMapping(value = "/myRepayDetailDh")
	public String myRepayDetailDh(HttpServletRequest request, HttpServletResponse response) {
		setCommonData(request);
		String loanId = request.getParameter("loanId");
		setAttribute(request, "loanId", loanId);
		//System.out.println("loanId === "+loanId);
		return getResultPath(MY_REPAY_DETAIL_DH);
	}
	
	/**
	 * 确认还款
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxRepay")
	public Message ajaxRepay(@RequestParam("loanId") int loanId,
			@RequestParam("repayPlanId") int repayPlanId, HttpSession session) {
		
		Message message = new Message();
		message.setStatus(1);
		message.setMessage( "还款成功！");
		try {
			LoginInfo loginInfo = (LoginInfo) session.getAttribute(AppConst.LOGIN_INFO_SESSION) ;
			BizAccountBO bizAccountBO= transferAccountsBS.getAccountBO(loginInfo.getCustId());
			RepayInfoVO repayInfoVO= this.repayBS.repayForTrial(AppConst.REPAY_WAY_CD_ZC, loanId, repayPlanId);
			if(bizAccountBO.getUsableBalAmt().compareTo(repayInfoVO.getTtlRepayedAmt()) < 0 ){
				 message.setStatus(-1);
				 message.setMessage("账户余额不足,还款金额=["+repayInfoVO.getTtlRepayedAmt()+"]");
			 }else{
			   this.repayBS.repayForFull(AppConst.REPAY_WAY_CD_ZC, loanId, repayPlanId);
			 }
			//发送短信
			try {
				Map<String,String> dataMap = new HashMap<String, String>();
				BizCustomerBO cust = custInfoBS.findCustById(loginInfo.getCustId());
				BizAccountBO account = transferAccountsBS.getAccountBO(loginInfo.getCustId());
				dataMap.put("CUST_NAME", cust.getCustName());
				dataMap.put("BALANCE", NumberUtils.format2(account.getUsableBalAmt()));
				dataMap.put("AMT", NumberUtils.format2(repayInfoVO.getTtlRepayedAmt()));
				msgTemplateBS.sendMsg(AppConst.EVENTTYPE_REAPY, cust, dataMap);
			} catch (Exception e) {
				
			}
		} catch (BizException e) {
			 
			message.setStatus(-1);
			message.setMessage("还款失败");
			if(e.getMessage().length() <= 60){
				message.setStatus(-1);
				message.setMessage(e.getMessage());
			}
			
		}
		return message;
	}
	
	/**
	 * 确认还款（代还）
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxRepayDh")
	public Message ajaxRepayDh(@RequestParam("loanId") int loanId,
			@RequestParam("repayPlanId") int repayPlanId, HttpSession session) {
		Message message = new Message();
		LoginInfo loginInfo = (LoginInfo) session.getAttribute(AppConst.LOGIN_INFO_SESSION) ;
	   if(null != loginInfo){
		   message.setStatus(1);
		   message.setMessage( "还款成功！");
		   try {
				this.repayBS.repayForFullDh(AppConst.REPAY_WAY_CD_ZC, loanId, repayPlanId,loginInfo.getCustId());
		   } catch (BizException e) {
				 
				message.setStatus(-1);
				message.setMessage("还款失败");
				if(e.getMessage().length() <= 60){
					message.setStatus(-1);
					message.setMessage(e.getMessage());
				}
				
			}
	   }else{
		   message.setStatus(-3);
		   message.setMessage("登陆超时！");
	   }
		return message;
	}
	
	/**
	 * 确认提前还款
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxPreRepay")
	public Message ajaxPreRepay(@RequestParam("loanId") int loanId, HttpSession session) {
		Message message = new Message();
		message.setStatus(1);
		message.setMessage( "还款成功！");
		try {
			
			LoginInfo loginInfo = (LoginInfo) session.getAttribute(AppConst.LOGIN_INFO_SESSION) ;
			 if(null != loginInfo){
				 BizAccountBO bizAccountBO= transferAccountsBS.getAccountBO(loginInfo.getCustId());
				 RepayInfoVO repayInfoVO= this.repayBS.repayForTrial(AppConst.REPAY_WAY_CD_TQ, loanId, null);
				 if(bizAccountBO.getUsableBalAmt().compareTo(repayInfoVO.getTtlRepayedAmt()) < 0 ){
					 message.setStatus(-1);
					 message.setMessage("账户余额不足,还款金额=["+repayInfoVO.getTtlRepayedAmt()+"]");
				 }else{
					 this.repayBS.repayForFull(AppConst.REPAY_WAY_CD_TQ, loanId, null);
				 }
			 }else{
				 message.setStatus(-1);
				 message.setMessage("未登陆,无法还款！");
			 }
		} catch (BizException e) {
			 
			message.setStatus(-1);
			message.setMessage("还款失败");
			if(e.getMessage().length() <= 60){
				message.setStatus(-1);
				message.setMessage(e.getMessage());
			}
		}
		return message;
	}
	
	
	/**
	 * 提前还款（代还）
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxPreRepayDh")
	public Message ajaxPreRepayDh(@RequestParam("loanId") int loanId, HttpSession session) {
		Message message = new Message();
		
		LoginInfo loginInfo = (LoginInfo) session.getAttribute(AppConst.LOGIN_INFO_SESSION) ;
		if(null != loginInfo){
			message.setStatus(1);
			message.setMessage( "还款成功！");
			try {
				this.repayBS.repayForFullDh(AppConst.REPAY_WAY_CD_TQ, loanId, null,loginInfo.getCustId());
			} catch (BizException e) {
				 
				message.setStatus(-1);
				message.setMessage("还款失败");
				if(e.getMessage().length() <= 60){
					message.setStatus(-1);
					message.setMessage(e.getMessage());
				}
			}
		}else{
			message.setStatus(-3);
			message.setMessage("登陆超时！");
		}
		return message;
	}
	
	/**
	 * 获取提前还款信息
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxQueryPreRepayInfo")
	public Map<String, String> ajaxQueryPreRepayInfo(@RequestParam("loanId") int loanId, HttpSession session) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			
			
			RepayInfoVO vo = this.repayBS.repayForTrial(AppConst.REPAY_WAY_CD_TQ, loanId, null);
			
			jsonMap.put("repayedPrincipalAmt", vo.getRepayedPrincipalAmt().toString());
			jsonMap.put("repayAmtNormal", vo.getRepayAmtNormal().toString());
			jsonMap.put("repayAmtPre", vo.getRepayAmtPre().toString());
			jsonMap.put("repayedInterestAmt", vo.getRepayedInterestAmt().toString());
			jsonMap.put("repayedPenaltyAmt", vo.getRepayedPenaltyAmt().toString());
			jsonMap.put("loanChargeAmt", vo.getLoanChargeAmt().toString());
			jsonMap.put("delayChargeAmt", vo.getDelayChargeAmt().toString());
			jsonMap.put("overdueChargeAmt", vo.getOverdueChargeAmt().toString());
			jsonMap.put("prepaymentChargeAmt", vo.getPrepaymentChargeAmt().toString());
			jsonMap.put("ttlRepayedAmt", vo.getTtlRepayedAmt().toString());
			
			
			
			jsonMap.put("reMes", "获取提前还款信息成功");
			jsonMap.put("status", "1");
		} catch (Exception e) {
			 
			jsonMap.put("reMes", "获取提前还款信息失败");
			jsonMap.put("status", "-1");;
			
		}
		return jsonMap;
	}
	
	
	
	/**
	 * 获取还款信息
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxQueryRepayInfo")
	public Map<String, String> ajaxQueryRepayInfo(
			@RequestParam("loanId") int loanId,
			@RequestParam("repayPlanId") int repayPlanId,
			HttpSession session) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			
			
			RepayInfoVO vo = this.repayBS.repayForTrial(AppConst.REPAY_WAY_CD_ZC, loanId, repayPlanId);
			
			jsonMap.put("repayedPrincipalAmt", vo.getRepayedPrincipalAmt().toString());
			jsonMap.put("repayedInterestAmt", vo.getRepayedInterestAmt().toString());
			jsonMap.put("repayedPenaltyAmt", vo.getRepayedPenaltyAmt().toString());
			jsonMap.put("loanChargeAmt", vo.getLoanChargeAmt().toString());
			jsonMap.put("delayChargeAmt", vo.getDelayChargeAmt().toString());
			jsonMap.put("overdueChargeAmt", vo.getOverdueChargeAmt().toString());
			jsonMap.put("prepaymentChargeAmt", vo.getPrepaymentChargeAmt().toString());
			jsonMap.put("ttlRepayedAmt", vo.getTtlRepayedAmt().toString());
			jsonMap.put("reMes", "获取还款信息成功");
			jsonMap.put("status", "1");
		} catch (BizException be) {
			jsonMap.put("reMes", be.getMessage());
			jsonMap.put("status", "-1");;
			
		} catch (Exception e) {
			 
			jsonMap.put("reMes", "获取还款信息失败");
			jsonMap.put("status", "-1");;
			
		}
		return jsonMap;
	}
	
	
	/**
	 *  ajax 查询还款计划
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxQueryMyRepayDetail")
	public Pager ajaxQueryMyRepayDetail(@RequestParam("loanId") int loanId, @RequestParam("requestPage") int requestPage,
			@RequestParam("pageSize") int pageSize, HttpSession session) {
		Pager pager = null;
		//System.out.println("loanId==="+loanId);
		try {
			if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null ) {
				pager = userLoanService.findLoanRepayPlan(requestPage, pageSize, loanId);
				
				List<Map<String,Object>> list = pager.getList();
				if(CollectionUtil.isNotEmpty(list)){
					Map changeColMap = new HashMap();
					changeColMap.put("repay_status_cd", "repayStatusCd");
					list = cacheManager.changeListDisplayName(list, changeColMap);
					pager.setList(list);
				}
				return pager;
			}
		} catch (Exception e) {
			 
		}
		return pager;
	}
	
	/**
	 *  ajax 查询还款计划
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxQueryMyRepayDetailDh")
	public Pager ajaxQueryMyRepayDetailDh(@RequestParam("loanId") int loanId, @RequestParam("requestPage") int requestPage,
			@RequestParam("pageSize") int pageSize, HttpSession session) {
		Pager pager = null;
		//System.out.println("loanId==="+loanId);
		try {
			if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null ) {
				pager = userLoanService.findLoanRepayPlan(requestPage, pageSize, loanId);
				
				List<Map<String,Object>> list = pager.getList();
				if(CollectionUtil.isNotEmpty(list)){
					Map changeColMap = new HashMap();
					changeColMap.put("repay_status_cd", "repayStatusCd");
					list = cacheManager.changeListDisplayName(list, changeColMap);
					pager.setList(list);
				}
				return pager;
			}
		} catch (Exception e) {
			 
		}
		return pager;
	}
	
	/**
	 * 提前还款界面
	 */
	@RequestMapping(value = "/myPreRepay")
	public String myPreRepay(HttpServletRequest request, HttpServletResponse response) {
		String loanApproveId = request.getParameter("loanApproveId");
		setAttribute(request, "loanApproveId", loanApproveId);
		setCommonData(request);
		return getResultPath(MY_PRE_REPAY);
	}

	// 我的授信
	@RequestMapping(value = "/myCreditLimit")
	public String myCreditLimit(HttpServletRequest request, HttpServletResponse response) {
		// 设置公共数据
		setCommonData(request);
		setAttribute(request, "test", 500);
		//setAttribute(request,"myVid","userinfohead4");
		String myVid = request.getParameter("myVid");
		setAttribute(request,"myVid",myVid);
		// 绑定数据
		return getResultPath(MY_CREDIT_LIMIT);
	}

}