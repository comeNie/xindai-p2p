package com.qfw.manager.action;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qfw.bizservice.creditor.IAutoTenderConfigBS;
import com.qfw.bizservice.creditor.ICreditorRightBS;
import com.qfw.bizservice.loan.ILoanBS;
import com.qfw.common.bizservice.IParamBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.util.CollectionUtil;
import com.qfw.common.util.MobileUtil;
import com.qfw.manager.model.MyCreditorTranVO;
import com.qfw.manager.model.MyCreditorVO;
import com.qfw.manager.service.IUserCreditorServcie;
import com.qfw.manager.service.IUserLoanService;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizAutoTenderConfigBO;
import com.qfw.model.vo.creditor.CreditorRightTranVO;
import com.qfw.platform.cache.CacheManager;
import com.qfw.platform.model.json.Pager;
import com.qfw.platform.model.vo.LoginInfo;

/**
 * 会员中心-理财管理
 * 
 * @author Teddy
 */
@Controller
@RequestMapping("/userFinancial")
public class UserFinancialAction extends BaseAction {

	@Resource(name = "userCreditorServcie")
	private IUserCreditorServcie userCreditorServcie;// 
	@Resource(name = "creditorRightBS")
	private ICreditorRightBS creditorRightBS;// 

	@Resource(name = "cacheManager")
	private CacheManager cacheManager;
	@Resource(name = "paramBS")
	private IParamBS paramBSService;

	@Resource(name = "autoTenderConfigBS")
	private IAutoTenderConfigBS autoTenderConfigBS;
	
	@Resource(name = "loanBS")
	private ILoanBS loanBS;
	
	@Resource(name = "userLoanService")
	private IUserLoanService userLoanService;

	/**
	 * 我的投资
	 */
	@RequestMapping(value = "/myCreditor")
	public String myCreditor(HttpServletRequest request,
			HttpServletResponse response) {
		// 设置公共数据
		setCommonData(request);
		// 绑定数据
		MyCreditorVO myCreditorVO = new MyCreditorVO();// TODO
		setAttribute(request, "myCreditorVO", myCreditorVO);
		//setAttribute(request,"myVid","userinfohead2");
		String myVid = request.getParameter("myVid");
		setAttribute(request,"myVid",myVid);
		boolean isMobile = MobileUtil.JudgeIsMoblie(request);
		if(isMobile){
			return getResultPath(MOBILE_MY_CREDITOR);
		}
		return getResultPath(MY_CREDITOR);
	}

	/**
	 * 投资协议
	 */
	@RequestMapping(value = "/creditorProtocol")
	public String creditorProtocol(@RequestParam("loanApproveId") Integer loanApproveId,
			HttpServletRequest request,HttpSession session) throws BizException {
		// 设置公共数据
		setCommonData(request);
		try {
			if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null) {
				LoginInfo loginInfo = (LoginInfo) session.getAttribute(AppConst.LOGIN_INFO_SESSION);
				
				MyCreditorVO vo = loanBS.getProtocolInfo(loanApproveId);
				if(vo == null){
					setAttribute(request, "errMes", "获取不到协议");
				}
				Calendar c = Calendar.getInstance();
				c.setTime(vo.getCreatDate());
				//签约日期
				setAttribute(request,"contractDate", c.get(Calendar.YEAR)+"年"+(c.get(Calendar.MONTH)+1)+"月"+c.get(Calendar.DAY_OF_MONTH)+"日");
				c.setTime(vo.getStartLoanDate());
				setAttribute(request,"repayDay", String.valueOf(c.get(Calendar.DAY_OF_MONTH)));//还款日
				vo.setCreditorCustName(loginInfo.getCustName());
				setAttribute(request, "myCreditorVO", vo);
				
			}
		} catch (BizException be) {
			setAttribute(request, "errMes", be.getMessage());
		} catch (Exception e) {
			setAttribute(request, "errMes", "获取协议失败");
		}
		
		return getResultPath(CREDITOR_PROTOCOL);
	}
	/**
	 * 查看协议
	 * @param loanApproveId
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxQueryProtocolInfo")
	public List ajaxQueryProtocolInfo(
			@RequestParam("loanId") Integer loanId,
			HttpSession session) {
		try {
			List list = userLoanService.findLoanRepayPlan(loanId);
			return list;
		} catch (Exception e) {
		
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajaxQueryCreditor")
	public List ajaxQueryCreditor(
			@RequestParam("loanId") Integer loanId,
			HttpSession session) {
		try {
			List list = userLoanService.findCreditorByLoanId(loanId);
			return list;
		} catch (Exception e) {
			
		}
		return null;
	}


	/**
	 * ajax 回收中的债权
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxQueryCreditorRecovering")
	public Pager ajaxQueryCreditorRecovering(@RequestParam("requestPage")
	int requestPage, @RequestParam("pageSize")
	int pageSize, HttpSession session) {
		Pager pager = null;
		try {
			if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null) {
				LoginInfo loginInfo = (LoginInfo) session
						.getAttribute(AppConst.LOGIN_INFO_SESSION);
				pager = this.userCreditorServcie.findUserCreditorRecovering(
						requestPage, pageSize, loginInfo.getCustId());
				List<Map<String, Object>> list = pager.getList();
				if (CollectionUtil.isNotEmpty(list)) {
					Map changeColMap = new HashMap();
					changeColMap.put("repay_type_cd", "repayTypeCd");
					changeColMap.put("cr_status_cd", "crStatusCd");
					list = cacheManager.changeListDisplayName(list,
							changeColMap);
					pager.setList(list);
				}

			}
		} catch (Exception e) {
			 
		}
		return pager;
	}

	/**
	 * ajax 已结清的债权
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxQueryCreditorRecovered")
	public Pager ajaxQueryCreditorRecovered(@RequestParam("requestPage")
	int requestPage, @RequestParam("pageSize")
	int pageSize, HttpSession session) {
		Pager pager = null;
		try {
			if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null) {
				LoginInfo loginInfo = (LoginInfo) session
						.getAttribute(AppConst.LOGIN_INFO_SESSION);
				pager = this.userCreditorServcie.findUserCreditorRecovered(
						requestPage, pageSize, loginInfo.getCustId());
				List<Map<String, Object>> list = pager.getList();
				if (CollectionUtil.isNotEmpty(list)) {
					Map changeColMap = new HashMap();
					changeColMap.put("repay_type_cd", "repayTypeCd");
					changeColMap.put("cr_status_cd", "crStatusCd");
					list = cacheManager.changeListDisplayName(list,
							changeColMap);
					pager.setList(list);
				}
			}
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return pager;
	}

	/**
	 * ajax 投标中的债权
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxQueryCreditorTendering")
	public Pager ajaxQueryCreditorTendering(@RequestParam("requestPage")
	int requestPage, @RequestParam("pageSize")
	int pageSize, HttpSession session) {
		Pager pager = null;
		try {
			if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null) {
				LoginInfo loginInfo = (LoginInfo) session
						.getAttribute(AppConst.LOGIN_INFO_SESSION);
				pager = this.userCreditorServcie.findUserCreditorTendering(
						requestPage, pageSize, loginInfo.getCustId());
				List<Map<String, Object>> list = pager.getList();
				if (CollectionUtil.isNotEmpty(list)) {
					Map changeColMap = new HashMap();
					changeColMap.put("repay_type_cd", "repayTypeCd");
					changeColMap.put("cr_status_cd", "crStatusCd");
					list = cacheManager.changeListDisplayName(list,
							changeColMap);
					pager.setList(list);
				}
			}
		} catch (Exception e) {
			 
		}
		return pager;
	}

	/**
	 * ajax 债权转让列表
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxQueryCreditorTran")
	public Pager ajaxQueryCreditorTran(@RequestParam("requestPage")
	int requestPage, @RequestParam("pageSize")
	int pageSize, HttpSession session) {
		Pager pager = null;
		try {
			if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null) {
				LoginInfo loginInfo = (LoginInfo) session
						.getAttribute(AppConst.LOGIN_INFO_SESSION);
				pager = this.userCreditorServcie.findUserCreditorTran(
						requestPage, pageSize, loginInfo.getCustId());

				List<Map<String, Object>> list = pager.getList();
				if (CollectionUtil.isNotEmpty(list)) {
					Map changeColMap = new HashMap();
					changeColMap.put("crt_status_cd", "crtStatusCd");
					list = cacheManager.changeListDisplayName(list,
							changeColMap);
					pager.setList(list);
				}
				return pager;
			}
		} catch (Exception e) {
			 
		}
		return pager;
	}

	/**
	 * 债权转让详细页面
	 */
	@RequestMapping(value = "/creditorTranDetail")
	public String creditorTranDetail(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		// 设置公共数据
		setCommonData(request);
		String creditorIdStr = request.getParameter("creditorId");
		if( creditorIdStr==null ){
			creditorIdStr = request.getAttribute("creditorId")==null?"":request.getAttribute("creditorId").toString();
		}
		Integer creditorId = Integer.valueOf(creditorIdStr);
		MyCreditorTranVO myCreditorTranVO = new MyCreditorTranVO();
		String errMsg = "";

		try {
			if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null) {

				Pager pager = this.userCreditorServcie.findObjects(creditorId);
				if (null == pager.getList()) {

				} else {

					if (pager.getList().size() > 0) {
						errMsg = "该笔债权已经在转让中了";
						//System.out.println("errMsg=="+errMsg);
						setAttribute(request, "errMes", errMsg);
						// 绑定数据
						return myCreditor(request, response);// "redirect:/userFinancial/myCreditor.do";
					}
				}

				myCreditorTranVO = this.userCreditorServcie
						.getMyCreditorTranVO(creditorId);

				// 债权状态不是有效的,不能转让
				if (!myCreditorTranVO.getCrStatusCd().equals("2")
						& !myCreditorTranVO.getCrStatusCd().equals("3")) {

					errMsg = "该笔债权状态未生效或者无效状态,不能转让";
					//System.out.println("errMsg=="+errMsg);
					setAttribute(request, "errMes", errMsg);
					// 绑定数据
					return myCreditor(request, response);// "redirect:/userFinancial/myCreditor.do";
				}

				// 到期日的前一个星期,不能债权转让
				if (myCreditorTranVO.getRemainDay() < AppConst.NoTranDay) {

					errMsg = "该笔债权快到期了，不能转让";
					//System.out.println("errMsg=="+errMsg);
					setAttribute(request, "errMes", errMsg);
					// 绑定数据
					return myCreditor(request, response);// "redirect:/userFinancial/myCreditor.do";
				}

				myCreditorTranVO.setCanTranCount(new Integer(myCreditorTranVO
						.getUnretrieveAmt().divide(
								myCreditorTranVO.getCrAmt().divide(
										new BigDecimal(myCreditorTranVO
												.getTurnoverCount()), 2,
										BigDecimal.ROUND_HALF_EVEN), 2,
								BigDecimal.ROUND_HALF_EVEN).intValue()));

				String tranRate = paramBSService.getParam("ZQZRGLFL");// new
				// BigDecimal(0.01);//
				// 提现费率
				// TODO
				// 要从数据中去查询
				setAttribute(request, "tranRate", tranRate);
			} else {
				errMsg = "未登录,请登录后操作";
				//System.out.println("errMsg=="+errMsg);
				setAttribute(request, "errMes", errMsg);
				return "redirect:/login.do";
			}
		} catch (BizException e) {
			 
			//System.out.println("errMsg=="+errMsg);
			setAttribute(request, "errMes", e.getMessage());
			return getResultPath(CREDITOR_TRAN);
		}
		setAttribute(request, "myCreditorTranVO", myCreditorTranVO);

		// 绑定数据
		return getResultPath(CREDITOR_TRAN);
	}

	/**
	 * 债权转让操作
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/creditorTran")
	public String creditorTran(@ModelAttribute
	CreditorRightTranVO creditorRightTranVO, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {

		if (creditorRightTranVO.getTakeAmt() == null
				|| creditorRightTranVO.getCrId() == null
				|| creditorRightTranVO.getTranTtlCount() == null) {
			String errMsg = "该笔债权快到期了，不能转让";
			setAttribute(request, "errMes", errMsg);
			// 绑定数据
			return myCreditor(request, response);// "redirect:/userFinancial/myCreditor.do";
		}

		// 设置公共数据
		setCommonData(request);
		try {
			creditorRightBS.creditorTranApprove(creditorRightTranVO);
		} catch (BizException e) {
			 
			setAttribute(request, "errMes", e.getMessage());
			setAttribute(request, "creditorId",creditorRightTranVO.getCrId());
			return creditorTranDetail(request, response,session);
		}

		// 绑定数据
		return getResultPath(MY_CREDITOR_TRAN);
	}

	
	
	/**
	 *  ajax 债权转让操作
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxCreditorTran")
	public Map<String, String> ajaxCreditorTran(
			@RequestParam("crId") Integer crId,
			@RequestParam("loanAmt") BigDecimal loanAmt,
			@RequestParam("loanName") String loanName,
			@RequestParam("takeAmt") BigDecimal takeAmt,
			@RequestParam("tranFee") BigDecimal takeFee,
			@RequestParam("tranRate") BigDecimal tranRate,
			@RequestParam("tranTerm") Integer tranTerm,
			@RequestParam("tranTtlAmt") BigDecimal tranTtlAmt,
			@RequestParam("tranTtlCount") Integer tranTtlCount,
			HttpServletRequest request,
			HttpSession session) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		String reMes = "";
		try {
			if (takeAmt == null
					|| crId == null
					|| tranTtlCount == null) {
				reMes = "该笔债权快到期了，不能转让";
				jsonMap.put("reMes", reMes);
				jsonMap.put("status", "0");
				// 绑定数据
				return jsonMap;
			}
			CreditorRightTranVO vo = new CreditorRightTranVO();
			vo.setCrId(crId);
			vo.setTakeAmt(takeAmt);
			vo.setTranTerm(tranTerm);
			vo.setTranTtlAmt(tranTtlAmt);
			vo.setTranTtlCount(tranTtlCount);
			creditorRightBS.creditorTranApprove(vo);
			
			reMes = "债权转让成功";
			jsonMap.put("reMes", reMes);
			jsonMap.put("status", "1");
		} catch (Exception e) {
			 
			jsonMap.put("reMes", e.getMessage());
			jsonMap.put("status", "0");
		}
		return jsonMap;
	}
	
	
	
	/**
	 * 债权转让列表
	 */
	@RequestMapping(value = "/myCreditorTran")
	public String myCreditorTran(HttpServletRequest request,
			HttpServletResponse response) {
		// 设置公共数据
		setCommonData(request);
		String myVid = request.getParameter("myVid");
		setAttribute(request,"myVid",myVid);
		//setAttribute(request,"myVid","userinfohead2");
		// 绑定数据
		return getResultPath(MY_CREDITOR_TRAN);
	}

	/**
	 * 自动投标
	 */
	@RequestMapping(value = "/myAutoTender")
	public String myAutoTender(HttpServletRequest request,
			HttpServletResponse response) {
		// 设置公共数据
		setCommonData(request);
		LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(
				AppConst.LOGIN_INFO_SESSION);
		
		String autoFlag = request.getAttribute("autoFlag")==null?"":request.getAttribute("autoFlag").toString();
		if(  autoFlag=="" ){
			setAttribute(request, "autoFlag", "0");
			//setAttribute(request,"myVid","userinfohead2");
			String myVid = request.getParameter("myVid");
			setAttribute(request,"myVid",myVid);
		}
		
		// 绑定数据
		return getResultPath(MY_AUTO_TENDER);
	}

	/**
	 * ajax 自动投标
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxAutoTenderConfig")
	public Pager ajaxAutoTenderConfig(@RequestParam("autoFlag")
					int autoFlag, HttpSession session) {
		Pager pager = new Pager();
		List<BizAutoTenderConfigBO> list = new ArrayList<BizAutoTenderConfigBO>();
		try {
			if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null) {
				LoginInfo loginInfo = (LoginInfo) session
						.getAttribute(AppConst.LOGIN_INFO_SESSION);
				
				if (loginInfo != null) {
					BizAutoTenderConfigBO configBO = this.autoTenderConfigBS
					.findAutoTenderConfigBOByCustId(loginInfo.getCustId());
					if( autoFlag==1  ){
						if(configBO==null){
							configBO = new BizAutoTenderConfigBO();
							configBO.setStatus(0);
							pager.setTotalCount(3);
						}else{
							if(configBO.getIsAuto()==1 && configBO.getStatus()==1){
								pager.setTotalCount(4);
							}else{
								pager.setTotalCount(3);
							}	
						}
						configBO.setYearRateBe(AppConst.AUTO_TENDER_CONFIG.getYearRateBe());
						configBO.setYearRateEn(AppConst.AUTO_TENDER_CONFIG.getYearRateEn());
						configBO.setYearRateEn(AppConst.AUTO_TENDER_CONFIG.getYearRateEn());
						configBO.setLoanPeriodBe(AppConst.AUTO_TENDER_CONFIG.getLoanPeriodBe());
						configBO.setLoanPeriodEn(AppConst.AUTO_TENDER_CONFIG.getLoanPeriodEn());
						configBO.setAccBal(AppConst.AUTO_TENDER_CONFIG.getAccBal());
						configBO.setCreditRateBe(AppConst.AUTO_TENDER_CONFIG.getCreditRateBe());
						configBO.setCreditRateEn(AppConst.AUTO_TENDER_CONFIG.getCreditRateEn());
						configBO.setAccRetain(AppConst.AUTO_TENDER_CONFIG.getAccRetain());
						configBO.setEachMaxBid(AppConst.AUTO_TENDER_CONFIG.getEachMaxBid());						
						configBO.setIsAuto(autoFlag);
					}else{
						if(configBO==null){
							pager.setTotalCount(1);
						}else{
							if(configBO.getIsAuto()==0 && configBO.getStatus()==1){
								pager.setTotalCount(2);
							}else if(configBO.getIsAuto()==1 && configBO.getStatus()==1){
								pager.setTotalCount(0);
							}else{
								pager.setTotalCount(1);
							}
							if( configBO.getIsAuto()==1 ){
								configBO = null;
							}
							
						}
					}

					list.add(configBO);
				}
				pager.setList(list);
				
				return pager;
			}
		} catch (Exception e) {
			 
		}
		return pager;
	}
	
	
	/**
	 * 添加自动投标信息 - 提交
	 */
	@RequestMapping(value = "/addAutoTenderConfigSubmit")
	public String addAutoTenderConfigSubmit(@ModelAttribute
	BizAutoTenderConfigBO configBO, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			setCommonData(request);// 设置公共数据
			LoginInfo loginInfo = (LoginInfo) request.getSession()
					.getAttribute(AppConst.LOGIN_INFO_SESSION);
			if (loginInfo != null) {
				BizAutoTenderConfigBO oldConfigBO = this.autoTenderConfigBS
						.findAutoTenderConfigBOByCustId(loginInfo.getCustId());
				if (oldConfigBO== null) {

					configBO.setCustId(loginInfo.getCustId());
					configBO.setUserCode(loginInfo.getUserCode());
					configBO.setUserId(loginInfo.getUserId());
					configBO.setSysCreateTime(new Timestamp(new Date()
							.getTime()));
					configBO.setSysCreateUser(loginInfo.getUserId());
					configBO.setSysUpdateTime(new Timestamp(new Date()
							.getTime()));
					configBO.setSysUpdateUser(loginInfo.getUserId());

					this.autoTenderConfigBS.save(configBO);
				} else {
					configBO.setId(oldConfigBO.getId());
					configBO.setCustId(loginInfo.getCustId());
					configBO.setSysCreateTime(new Timestamp(new Date()
							.getTime()));
					configBO.setSysCreateUser(loginInfo.getUserId());
					configBO.setSysUpdateTime(new Timestamp(new Date()
							.getTime()));
					configBO.setSysUpdateUser(loginInfo.getUserId());
					this.autoTenderConfigBS.update(configBO);
				}
			}
			setAttribute(request, "sucMes", "自动投标设置成功");
			setAttribute(request, "autoFlag", request.getParameter("autoFlag"));
			// 绑定数据
		} catch (Exception e) {
			 
			setAttribute(request, "errMes", "自动投标设置失败，" + e.getMessage());
		}
		return myAutoTender(request, response);// getResultPath(MY_AUTO_TENDER);
	}

}