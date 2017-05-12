package com.qfw.manager.action;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qfw.bizservice.bankcard.IBankCardBS;
import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.bizservice.custinfo.account.ICustAccountBS;
import com.qfw.bizservice.custinfo.account.ITransferAccountsBS;
import com.qfw.bizservice.payout.IRechargePayoutBS;
import com.qfw.bizservice.payout.IWithdrawalsPayoutBS;
import com.qfw.common.bizservice.ICodeDictBS;
import com.qfw.common.bizservice.IMsgTemplateBS;
import com.qfw.common.bizservice.IParamBS;
import com.qfw.common.bizservice.PromotionService.PromotionService;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.BussConst;
import com.qfw.common.model.SysBankCode;
import com.qfw.common.util.CollectionUtil;
import com.qfw.common.util.DateUtils;
import com.qfw.common.util.MD5Utils;
import com.qfw.common.util.MobileUtil;
import com.qfw.common.util.NumberUtils;
import com.qfw.common.util.StringUtils;
import com.qfw.manager.model.UserIndexVO;
import com.qfw.manager.service.IUserFundService;
import com.qfw.manager.service.IUserIndexService;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizAccountBO;
import com.qfw.model.bo.BizCardBO;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.bo.BizPaymentInfoBO;
import com.qfw.model.vo.bankcard.BankCardVO;
import com.qfw.model.vo.custinfo.account.AccountRequestVO;
import com.qfw.model.vo.payout.RechargeVO;
import com.qfw.model.vo.payout.WithdrawalsVO;
import com.qfw.platform.cache.CacheManager;
import com.qfw.platform.model.json.Pager;
import com.qfw.platform.model.vo.LoginInfo;

/**
 * 会员中心-资金管理
 * @author Teddy
 */
@Controller
@RequestMapping("/userFund")
public class UserFundAction extends BaseAction {
	@Resource(name = "userFundService")
	private IUserFundService userFundService;
	@Resource(name = "bankCardBS")
	private IBankCardBS bankCardBS;
	@Resource(name = "rechargePayoutBS")
	private IRechargePayoutBS rechargePayoutBS;
	@Resource(name = "custAccountBS")
	private ICustAccountBS custAccountBS;
	@Resource(name = "userIndexService")
	private IUserIndexService userIndexService;
	@Resource(name = "paramBS")
	private IParamBS paramBSService;
	@Resource(name = "promotionService")
	private PromotionService promotionService;
	
	@Resource(name = "cacheManager")
	private CacheManager cacheManager;
	
	@Resource(name = "codeDictBS")
	private ICodeDictBS codeDictBS;
	
	@Resource(name = "withdrawalsPayoutBS")
	private IWithdrawalsPayoutBS withdrawalsPayoutBS;
	
	@Resource(name = "transferAccountsBS")
	private ITransferAccountsBS transferAccountsBS;
	
	@Resource(name = "custInfoBS")
	private ICustInfoBS custInfoBS;
	
	@Resource(name = "msgTemplateBS")
	private IMsgTemplateBS msgTemplateBS;
	
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	
	/**
	 * 我要充值
	 */
	@RequestMapping(value = "/myRecharge")
	public String myRecharge(HttpServletRequest request, HttpServletResponse response) {
		try {
			UserIndexVO userIndexVO = new UserIndexVO();
			if (request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION) != null ) {
				LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION) ;
				String fee = paramBSService.getParam("CZSXF");
				String exceed = paramBSService.getParam("EXCEED_WITHDRAW_RATE");
				if(StringUtils.isNotEmpty(exceed)){
					String exceedRate = (new BigDecimal(exceed).multiply(new BigDecimal(100))).setScale(1).toString();
					setAttribute(request, "exceedRate", exceedRate);
				}
				if(StringUtils.isEmpty(fee)){
					fee="0.00";
				}
				
				userIndexVO = userIndexService.getUserIndexVO(loginInfo.getCustId());// 我的账户
				// 绑定数据
				setAttribute(request, "userIndexVO", userIndexVO);
				setAttribute(request, "fee", fee);
				setAttribute(request, "tabIndex", "tab1");
				//setAttribute(request,"myVid","userinfohead3");
				String myVid = request.getParameter("myVid");
				setAttribute(request,"myVid",myVid);
			}
			setCommonData(request);// 设置公共数据
		} catch (BizException e) {
			 
		}
		if(MobileUtil.JudgeIsMoblie(request)){
			return getResultPath(MOBILE_MY_RECHARGE);
			
			
		}
		return getResultPath(MY_RECHARGE);
	}

	/**
	 * 我要提现 - 页面
	 */
	@RequestMapping(value = "/myDrawal")
	public String myDrawal(HttpServletRequest request, HttpServletResponse response) {
		// 设置公共数据
		try {
			setCommonData(request);
			//List myBankCardList = new ArrayList();
			
			String errMes = "";
			String sucMes = "";
			String isCashPassword ="false";
			try{
				errMes = request.getParameter("errMes");
				sucMes = request.getParameter("sucMes");
				
				if(StringUtils.isNotEmpty(errMes)){
					errMes = URLDecoder.decode(errMes, "UTF-8");
					setAttribute(request, "errMes", errMes);
				}
				
				if(StringUtils.isNotEmpty(sucMes)){
					sucMes = URLDecoder.decode(sucMes, "UTF-8");
					setAttribute(request, "sucMes", sucMes);
				}
			}catch(Exception ex){ex.printStackTrace();}
			
			UserIndexVO userIndexVO = new UserIndexVO();
			if (request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION) != null ) {
				LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION) ;
				userIndexVO = this.userIndexService.getUserIndexVO(loginInfo.getCustId());
				//myBankCardList = this.userFundService.findBankCardList(loginInfo.getCustId());
				String cashPassword = loginInfo.getUserSecurity().getCashPassword();
				if(StringUtils.isNotEmpty(cashPassword)){
					isCashPassword ="true";
				}
			}
			String drawalRate = paramBSService.getParam("TXSXF");// 提现费率 
			//BigDecimal drawalRate = new BigDecimal(0.01);
			
			//setAttribute(request, "myBankCardList", myBankCardList);
			setAttribute(request, "userIndexVO", userIndexVO);
			setAttribute(request, "drawalRate", drawalRate);
			//setAttribute(request,"myVid","userinfohead3");
			String myVid = request.getParameter("myVid");
			setAttribute(request,"myVid",myVid);
			setAttribute(request, "isCashPassword", isCashPassword);
		} catch (Exception e) {
			 
		}
		// 绑定数据
		return getResultPath(MY_DRAWAL);
	}

	/**
	 * 交易流水
	 */
	@RequestMapping(value = "/myPayment")
	public String myPayment(HttpServletRequest request, HttpServletResponse response) {
		setCommonData(request);// 设置公共数据	
		//setAttribute(request,"myVid","userinfohead3");
		String myVid = request.getParameter("myVid");
		setAttribute(request,"myVid",myVid);
		boolean isMobile = MobileUtil.JudgeIsMoblie(request);
		if(isMobile){
			return getResultPath(MOBILE_MY_PAYMENT);
		}
		return getResultPath(MY_PAYMENT);
	}
	
	/**
	 *  ajax 查询交易流水
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxQueryMyPayment")
	public Pager ajaxQuerymyPayment(
			@RequestParam("tradeType") String tradeType,
			@RequestParam("startTime") String startTime,
			@RequestParam("endTime") String endTime,
			@RequestParam("requestPage") int requestPage,
			@RequestParam("pageSize") int pageSize, HttpSession session) {
		Pager pager = null;
		try {
			if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null ) {
				LoginInfo loginInfo = (LoginInfo) session.getAttribute(AppConst.LOGIN_INFO_SESSION) ;
				//System.out.println(loginInfo.getUserId());
				return this.userFundService.findTradeList(tradeType,startTime,endTime,requestPage, pageSize, loginInfo.getUserCode());
			}
		} catch (Exception e) {
			 
		}
		return pager;
	}

	/**
	 * 我的银行卡
	 */
	@RequestMapping(value = "/myBankCard")
	public String myBankCard(HttpServletRequest request, HttpServletResponse response) {
		setCommonData(request);// 设置公共数据
		if (request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION) != null ) {
			LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION) ;
			
			
			 String Mobile = loginInfo.getUserSecurity().getMobile();
			 String idCard = loginInfo.getUserSecurity().getIdCard();
			 String realName = loginInfo.getUserSecurity().getRealName();
		
			//未身份认证或者未手机认证
			if(StringUtils.isEmpty(Mobile) ||StringUtils.isEmpty(idCard) ||StringUtils.isEmpty(realName)||StringUtils.isEmpty(realName)){
				String errMes = "身份认证、手机认证及设置交易密码的用户，才能添加银行卡";
				try {
					errMes = URLEncoder.encode(errMes, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					 
				}
				return "redirect:/userSecurity/index.do?errMes="+errMes;						
			}
				setAttribute(request, "certificateNum", loginInfo.getUserSecurity().getShowIdCard());	
				setAttribute(request, "custName", realName);	
				//setAttribute(request, "bankCards", this.userFundService.findBankCardList(loginInfo.getCustId()));
				String myVid = request.getParameter("myVid");
				setAttribute(request,"myVid",myVid);
		}
		return getResultPath(MY_BANKCARD);
	}
	
	
	
	/**
	 * 添加银行卡 - 页面
	 */
	@RequestMapping(value = "/addBankCard")
	public String addBankCard(HttpServletRequest request, HttpServletResponse response) {
		// 设置公共数据
		setCommonData(request);
		if (request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION) != null ) {
			LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION) ;
			setAttribute(request, "certificateNum", loginInfo.getCertificateNum());	
			setAttribute(request, "custName", loginInfo.getCustName());	
		}
		
		// 绑定数据
		return getResultPathBySecurity(ADD_BANKCARD,request);
	}
	
	/**
	 * 删除银行卡
	 */
	@RequestMapping(value = "/deleteCard")
	public String deleteCard(HttpServletRequest request, HttpServletResponse response) {
		try {
			setCommonData(request);// 设置公共数据
			if (request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION) != null ) {
				Integer cardId = Integer.valueOf(request.getParameter("cardId"));
				this.bankCardBS.deleteCard(cardId);
			}
			setAttribute(request, "sucMes", "删除成功");	
			// 绑定数据
		} catch (Exception e) {
			 
			setAttribute(request, "errMes", "删除失败");	
		}
		return getResultPath(MY_DRAWAL);
	}
	
	/**
	 * 添加银行卡 - 验证卡号是否存在
	 */
	@RequestMapping(value = "/validateForm")
	@ResponseBody
	public boolean validateForm(@ModelAttribute BizCardBO cardBO,HttpServletRequest request, HttpServletResponse response) {
		try {
			if (request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION) != null ) {
				if(bankCardBS.validate(cardBO.getAccountNum())){
					return true;
				};
			}
			// 绑定数据
		} catch (Exception e) {
			 
		}
		return false;
	}
	
	/**
	 * 添加银行卡页面 - 提交
	 */
	@RequestMapping(value = "/addBankCardSubmit")
	public String addBankCardSubmit(@ModelAttribute BizCardBO cardBO,HttpServletRequest request, HttpServletResponse response) {
		String errMes = "";
		try {
			
			setCommonData(request);// 设置公共数据
			LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION) ;
			
			if (loginInfo != null ) {
				
				BankCardVO bankCardVO = new BankCardVO();
				bankCardVO.setAccountNum(cardBO.getAccountNum());
				List<BizCardBO> list = this.bankCardBS.findByParams(bankCardVO);
						
				if (CollectionUtil.isNotEmpty(list)) {// 校验卡号是否已经存在
					errMes = "该卡号已经存在";
					setAttribute(request, "errMes", errMes);
					return getResultPath(ADD_BANKCARD);
				}
				
				cardBO.setCustId(loginInfo.getCustId());
				cardBO.setSysCreateTime(new Date());
				cardBO.setSysUpdateTime(new Date());
				cardBO.setSysUpdateUser(loginInfo.getUserId());
				cardBO.setSysCreateUser(loginInfo.getUserId());
				cardBO.setAccountName(loginInfo.getCustName());
				cardBO.setBankType("");
				//添加银行卡并发放奖金
				promotionService.addBankCard(loginInfo.getUserId(),cardBO);
				
			}
			// 绑定数据
		} catch (Exception e) {
			 
			errMes = "添加银行卡失败！";
			setAttribute(request, "errMes", errMes);
			return getResultPath(ADD_BANKCARD);
		}
		
		setAttribute(request, "sucMes", "添加成功");
		return getResultPath(ADD_BANKCARD);
	}
	
	
	
	/**
	 *  ajax 增加银行卡
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxAddBankCard")
	public Map<String, String> ajaxAddBankCard(
			HttpServletRequest request,
			HttpSession session) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		String accountNum = request.getParameter("accountNum");
		String accountName = request.getParameter("accountName");
		String areaCity = request.getParameter("areaCity");
		String areaProvince = request.getParameter("areaProvince");
		String bankName = request.getParameter("bankName");
		String bankType = request.getParameter("bankType");
		LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION);
		try {
			if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null ) {
				
				bankName = new String(request.getParameter("bankName")
						.getBytes("iso8859-1"), "utf-8");
								
				areaProvince = new String(request.getParameter("areaProvince")
						.getBytes("iso8859-1"), "utf-8");
				areaCity = new String(request.getParameter("areaCity")
						.getBytes("iso8859-1"), "utf-8");
				
				BankCardVO bankCardVO = new BankCardVO();
				bankCardVO.setAccountNum(accountNum);
				List list = this.bankCardBS.findByParams(bankCardVO);
				if (CollectionUtil.isNotEmpty(list)) {// 校验卡号是否已经存在
					jsonMap.put("reMes", "该卡号已经存在");
					jsonMap.put("status", "-1");
				}else{
					BizCardBO cardBO = new BizCardBO();
					cardBO.setAccountName(accountName);
					cardBO.setAccountNum(accountNum);
					cardBO.setAreaCity(areaCity);
					cardBO.setAreaProvince(areaProvince);
					cardBO.setBankName(bankName);
					
					cardBO.setCustId(loginInfo.getCustId());
					cardBO.setSysCreateTime(new Date());
					cardBO.setSysUpdateTime(new Date());
					cardBO.setSysUpdateUser(loginInfo.getUserId());
					cardBO.setSysCreateUser(loginInfo.getUserId());
					cardBO.setAccountName(loginInfo.getCustName());
					cardBO.setBankType(bankType);
					//添加银行卡并发放奖金
					promotionService.addBankCard(loginInfo.getUserId(),cardBO);
					jsonMap.put("reMes", "银行卡添加成功");
					jsonMap.put("status", "1");
				}	
			}
		} catch (Exception e) {
			 
			jsonMap.put("reMes", "银行卡添加失败，请重试");
			jsonMap.put("status", "-1");
		}
		return jsonMap;
	}
	
	
	/**
	 *  ajax 删除银行卡
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxDelBankCard")
	public Map<String, String> ajaxDelBankCard(
			@RequestParam("cardId") int cardId,
			HttpServletRequest request,
			HttpSession session) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			if (request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION) != null ) {
				
				this.bankCardBS.deleteCard(cardId);
				jsonMap.put("reMes", "银行卡删除成功");
				jsonMap.put("status", "1");
			}
		} catch (Exception e) {
			 
			jsonMap.put("reMes", "银行卡删除失败，请重试");
			jsonMap.put("status", "-1");
		}
		return jsonMap;
	}
	
	/**
	 * 我要提现 - 提交
	 */
	@RequestMapping(value = "/myDrawalSubmit")
	public String myDrawalSubmit(@ModelAttribute WithdrawalsVO withdrawalsVO, HttpServletRequest request, HttpServletResponse response) {
		if(null == withdrawalsVO){
	    	setAttribute(request, "errMes", "提现申请信息为空!");
			return getResultPath(MY_DRAWAL);
    	}
		
		String errMes = null;
		try {
			 setCommonData(request);// 设置公共数据
			 LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION) ;
			 UserIndexVO userIndexVO = new UserIndexVO();
			if (loginInfo != null ) {
				String realName = loginInfo.getUserSecurity().getRealName();
				if(StringUtils.isEmpty(realName)){
					setAttribute(request, "errMes", "实名认证后，才能发起提现");
					return getResultPath(MY_DRAWAL);
				}
				if(!MD5Utils.getMD5Str(withdrawalsVO.getTradePwd()).equals(loginInfo.getUserSecurity().getCashPassword())){
					setAttribute(request, "errMes", "提现密码错误!");
					userIndexVO = this.userIndexService.getUserIndexVO(loginInfo.getCustId());
					String drawalRate = paramBSService.getParam("TXSXF");// 提现费率 
					setAttribute(request, "userIndexVO", userIndexVO);
					setAttribute(request, "drawalRate", drawalRate);
					setAttribute(request,"myVid","userinfohead3");
					return getResultPath(MY_DRAWAL);
				} 
				
				withdrawalsVO.setCustId(loginInfo.getCustId());
				withdrawalsVO.setUserCode(loginInfo.getUserCode());
				withdrawalsVO.setCustName(loginInfo.getCustName());
				
				String drawalRate = paramBSService.getParam("TXSXF");// 提现费率
//				BigDecimal feeAmt = withdrawalsVO.getDealAmt().multiply(new BigDecimal(drawalRate)).setScale(2,BigDecimal.ROUND_HALF_UP);
				BigDecimal feeAmt = new BigDecimal(drawalRate);
				withdrawalsVO.setFeeAmt(feeAmt);
		    	
		    	if(null == withdrawalsVO.getOutAccount() || withdrawalsVO.getOutAccount().length()<=0){
		    		setAttribute(request, "errMes", "请选择银行卡");
		    		return getResultPath(MY_DRAWAL);
		    	}
		    	if(!BussConst.YES_FLAG.equals(paramBSService.getParam("WITHDRAW_SWITCH"))) {
		    		setAttribute(request, "errMes", "由于银行与第三方平台节假日转账延时原因，节假日暂不支持提现。");
		    		return getResultPath(MY_DRAWAL);
		    	}
		    	if(null == withdrawalsVO.getDealAmt() || withdrawalsVO.getDealAmt().compareTo(BigDecimal.ZERO) <= 0
		    			|| withdrawalsVO.getDealAmt().compareTo(new BigDecimal(paramBSService.getParam("PER_WITHDRAW_AMT"))) > 0 ) {
		    		setAttribute(request, "errMes", "申请金额必须大于0,小于"+paramBSService.getParam("PER_WITHDRAW_AMT"));
		    		return getResultPath(MY_DRAWAL);
		    	}
		    	
//				this.userFundService.submitDrawal(withdrawalsVO);
		    	String status = withdrawalsPayoutBS.submitDrawal(withdrawalsVO);
		    	if(AppConst.WD_STATUS_S.equals(status)){
		    		try {
		    			Map<String,String> dataMap = new HashMap<String,String>();
		    			//您在${date}为账号XXXX充值${amt}元已到账，详咨XXXXXXXXXXXX
		    			BizAccountBO acc = transferAccountsBS.getAccountBO(withdrawalsVO.getCustId());
		    			BizCustomerBO cust = custInfoBS.findCustById(withdrawalsVO.getCustId());
		    			dataMap.put("DATE", DateUtils.getYmdhms(new Date()));//
		    			dataMap.put("AMT", NumberUtils.format2(withdrawalsVO.getDealAmt()));
		    			dataMap.put("CUST_NAME", cust.getCustName());
		    			dataMap.put("BALANCE", NumberUtils.format2(acc.getUsableBalAmt()));
		    			msgTemplateBS.sendMsg(AppConst.EVENTTYPE_DRAWAL, cust, dataMap);
		    		} catch (Exception e) {
		    			log.error("提现短信发送失败，========"+e.getMessage());
		    		}
		    		return getReturnUrlSucc("提现成功");
		    	}else
		    	if(AppConst.WD_STATUS_P.equals(status)){
		    		return getReturnUrlSucc("提现处理中，请稍后查看");
		    	}else if(AppConst.WD_STATUS_F.equals(status)){
		    		return getReturnUrlError("提现处理中，请稍后查看");
		    	}
		    	
			}
			// 绑定数据
		} catch (Exception e) {
			 
			errMes = e.getMessage().replace('\'', '‘');
			if(errMes.length() > 100){
				return getReturnUrlError(errMes.substring(1, 99));
			}else{
				return getReturnUrlError(errMes);
			}
			
		}
		
		return getReturnUrlSucc("提交申请成功");
	}

	private String getReturnUrlError(String errMes){
		String errStr = null;
		try {
			errStr = URLEncoder.encode(errMes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			 
		}
		
		return "redirect:/userFund/myDrawal.do?errMes="+errStr;
	 }
	
	private String getReturnUrlSucc(String sucMes){
		String sucStr = null;
		try {
			sucStr = URLEncoder.encode(sucMes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			 
		}
		return "redirect:/userFund/myDrawal.do?sucMes="+sucStr;
	 }
	
	/**
	 *  ajax 查询我的银行卡列表
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxQueryMyBankCard")
	public Pager ajaxQueryMyBankCard(@RequestParam("requestPage") int requestPage,
			@RequestParam("pageSize") int pageSize, HttpSession session) {
		Pager pager = null;
		try {
			if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null ) {
				LoginInfo loginInfo = (LoginInfo) session.getAttribute(AppConst.LOGIN_INFO_SESSION);
				return this.userFundService.findBankCardList(requestPage, pageSize, loginInfo.getCustId());
			}
		} catch (Exception e) {
			 
		}
		return pager;
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajaxQueryWithdrawFee")
	public String getWithdrawFee(@RequestParam("dealAmt") String dealAmt,HttpSession session) {
		BigDecimal fee = new BigDecimal(paramBSService.getParam("TXSXF"));
		BigDecimal amt = BigDecimal.ZERO;
		BigDecimal exceedAmt = BigDecimal.ZERO;
		if(StringUtils.isNotEmpty(dealAmt)){
			amt = new BigDecimal(dealAmt);
		}
		try {
			if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null ) {
				LoginInfo loginInfo = (LoginInfo) session.getAttribute(AppConst.LOGIN_INFO_SESSION);
				BizAccountBO bizAccountBO = custAccountBS.findCustAccount(loginInfo.getCustId());
				if(bizAccountBO.getUsableBalAmt().subtract(bizAccountBO.getRechargeAmt())
						.compareTo(amt) < 0){//
					BigDecimal useable = bizAccountBO.getUsableBalAmt().subtract(bizAccountBO.getRechargeAmt());
					if(useable.compareTo(BigDecimal.ZERO) < 0){
						exceedAmt =amt;
					}else{
						exceedAmt =amt.subtract(useable);
					}
						
					if(exceedAmt.compareTo(BigDecimal.ZERO) > 0){//未投资金额按不同费率收费
						BigDecimal exceedFee = exceedAmt.multiply(new BigDecimal(paramBSService.getParam("EXCEED_WITHDRAW_RATE")));
						fee = fee.add(exceedFee);
					}
				}
			}
		} catch (Exception e) {
			 
		}
		return fee.toString();
	}
		
	@RequestMapping(value = "/paymentCard")
	public String paymentCard(@RequestParam("cardCd") String cardCd,@RequestParam("password") String password,
			 HttpSession session,HttpServletRequest request){
		UserIndexVO userIndexVO = new UserIndexVO();
		
		setCommonData(request);
		setAttribute(request, "tabIndex", "tab2");
		try {
			LoginInfo loginInfo = (LoginInfo) session.getAttribute(AppConst.LOGIN_INFO_SESSION) ;
			
			if(null == loginInfo){
				setAttribute(request, "errMes", "登陆后，才能充值！");
				return getResultPath(LOGIN_INDEX);
			}
			
			AccountRequestVO accountVO = new AccountRequestVO();
			accountVO.setCustId(loginInfo.getCustId());
			accountVO.setAccountType(AppConst.ACCOUNT_TYPE_CUST);
			List<BizAccountBO> accountList = custAccountBS.findInfoByParams(accountVO);
			
			if(null!= accountList && accountList.size()>0){
				RechargeVO vo = new RechargeVO();
				vo.setAccountID(accountList.get(0).getId());
				vo.setAccount(accountList.get(0).getAccount());
				vo.setCardCD(cardCd);
				vo.setPassword(password);
				vo.setRechargeType(AppConst.PAYOUTTYPE_CARD);
				this.rechargePayoutBS.rechargeCard(vo,loginInfo.getCustId());
				
				userIndexVO = userIndexService.getUserIndexVO(loginInfo.getCustId());// 我的账户
				setAttribute(request, "userIndexVO", userIndexVO);
			}else{
				setAttribute(request, "errMes", "此客户无帐号");
				return getResultPath(MY_RECHARGE);
			}
		} catch (Exception e) {
			 
			setAttribute(request, "errMes", e.getMessage());
			return getResultPath(MY_RECHARGE);
		}
		setAttribute(request, "sucMes", "充值成功！");
		return getResultPath(MY_RECHARGE);
	}
	
	@RequestMapping(value = "/payment")
	public String payment(@RequestParam("bank") String bank,@RequestParam("amount") String amount,HttpServletRequest request){
		setCommonData(request);
//		UserIndexVO userIndexVO = new UserIndexVO();
		try {
			setAttribute(request, "tabIndex", "tab1");
			LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION);
			BizPaymentInfoBO paymentInfoBO = rechargePayoutBS.createPaymentInfo(bank, amount,loginInfo.getCustId(),
					request);
			String dateStr = DateUtils.getDateString("yyyyMMddHHmmss",paymentInfoBO.getOrderTime());
			
			//汇潮支付
			/*setAttribute(request, "MerNo", paymentInfoBO.getMerNo());
			setAttribute(request, "BillNo", paymentInfoBO.getBillNo());
			setAttribute(request, "Amount", amount);
			setAttribute(request, "ReturnURL", paymentInfoBO.getReturnUrl());
			setAttribute(request, "AdviceURL", paymentInfoBO.getAdviceUrl());
			setAttribute(request, "SignInfo", paymentInfoBO.getSignInfo());
			setAttribute(request, "orderTime", DateUtils.getDateString("yyyyMMddHHmmss", paymentInfoBO.getOrderTime()));
			setAttribute(request, "defaultBankNumber", bank);
			setAttribute(request, "Remark", paymentInfoBO.getRemark());
			setAttribute(request, "products", paymentInfoBO.getProducts());*/
			
			if(loginInfo != null){
				//add by yangjj for 通联支付
				if(AppConst.PAY_SERVICE_TL.equals(paramBSService.getParam("PAY_SERVICE"))){//加一个支付开关
					String payerIDCard = "";
					String pan = "";
					//若直连telpshx渠道，payerTelephone、payerName、payerIDCard、pan四个字段不可为空
					//其中payerIDCard、pan需使用公钥加密（PKCS1格式）后进行Base64编码
					/*if(null!=payerIDCard&&!"".equals(payerIDCard)){
						try{
							payerIDCard = SecurityUtil.encryptByPublicKey("/opt/conf/TLCert.cer", payerIDCard);
						}catch(Exception e){
						}
					}
					if(null!=pan&&!"".equals(pan)){
						try{
							pan = SecurityUtil.encryptByPublicKey("/opt/conf/TLCert.cer", pan);
						}catch(Exception e){
//							 
						}
					}*/
					setAttribute(request, "signMsg",paymentInfoBO.getSignInfo());
					setAttribute(request, "version", AppConst.TL_VERSION);
					setAttribute(request, "language", AppConst.TL_LANGUAGE);
					setAttribute(request, "inputCharset", AppConst.TL_INPUT_CHARSET);
					setAttribute(request, "merchantId", paramBSService.getParam("PAY_MER_CODE"));
					setAttribute(request, "pickupUrl", paymentInfoBO.getReturnUrl());
					setAttribute(request, "receiveUrl", paymentInfoBO.getAdviceUrl());
					setAttribute(request, "payType", AppConst.TL_PAYTYPE);
					setAttribute(request, "signType", paramBSService.getParam("SIGN_TYPE"));
					setAttribute(request, "orderNo", paymentInfoBO.getBillNo());
					setAttribute(request, "orderAmount", new BigDecimal(paymentInfoBO.getAmount()).multiply(new BigDecimal(100)).toString());
					setAttribute(request, "orderDatetime",dateStr );
					setAttribute(request, "orderCurrency", AppConst.TL_ORDER_CURRENCY);
					if(MobileUtil.JudgeIsMoblie(request)){
						setAttribute(request, "ext1", "1");
					}
					
					//订单过期时间 TODO 确认时间作用域
//					setAttribute(request, "orderExpireDatetime", AppConst.TL_ORDER_EXPIREDATETIME);
//					setAttribute(request, "payerTelephone", AppConst.PAYMENT_VERSION);
//					setAttribute(request, "payerEmail", );
//					setAttribute(request, "payerName",);
//					setAttribute(request, "payerIDCard", payerIDCard);
//					setAttribute(request, "pid", );
					/*setAttribute(request, "productName", "");
					setAttribute(request, "productId", "");
					setAttribute(request, "productNum", "");
					setAttribute(request, "productPrice", "");
					setAttribute(request, "productDesc", "");
					setAttribute(request, "ext1", "");
					setAttribute(request, "ext2", "");
					setAttribute(request, "extTL", "");
					setAttribute(request, "issuerId", "");
					setAttribute(request, "pan", "");
					setAttribute(request, "tradeNature", "");*/
					if(!MobileUtil.JudgeIsMoblie(request)){//pc
						setAttribute(request, "requestUrl", AppConst.GATEWAY_URL);
						return getResultPath(MY_TLPAYSUBMIT);
				    }else{//手机
				    	setAttribute(request, "requestUrl", AppConst.MOBILEPAYMENT_URL);
				    	return getResultPath(MY_TLPAYSUBMIT);
				    }
					
				}else if(AppConst.PAY_SERVICE_GFB.equals(paramBSService.getParam("PAY_SERVICE"))){
					//add by yjj for 国付宝 20150713 start
					
					setAttribute(request, "version", AppConst.PAYMENT_VERSION);
					setAttribute(request, "charset", "2");
					setAttribute(request, "language", AppConst.PAYMENT_LANGUAGE);
					setAttribute(request, "signType", "1");//报文加密方式：MD5
					setAttribute(request, "tranCode",AppConst.PAYMENT_TRANCODE);
					setAttribute(request, "merchantID", AppConst.PAYMENT_MERCHANTID);
					setAttribute(request, "merOrderNum",paymentInfoBO.getBillNo());//订单号
					
					setAttribute(request, "tranAmt", amount);//交易金额 
					setAttribute(request, "feeAmt", AppConst.PAYMENT_FEEAMT);
					setAttribute(request, "currencyType", AppConst.PAYMENT_CURRENCYTYPE);
					setAttribute(request, "frontMerUrl", paymentInfoBO.getReturnUrl());//商户前台通知地址
					setAttribute(request, "backgroundMerUrl", paymentInfoBO.getAdviceUrl());//商户后台通知地址
					
					setAttribute(request, "tranDateTime", dateStr);//交易时间
					setAttribute(request, "virCardNoIn", AppConst.PAYMENT_VIRCARDNOIN);
					setAttribute(request, "tranIP", AppConst.PAYMENT_TRANIP);//用户浏览器IP
					setAttribute(request, "isRepeatSubmit", AppConst.PAYMENT_ISREPEATSUBMIT);
					setAttribute(request, "goodsName", paymentInfoBO.getProducts());//商品名称
					setAttribute(request, "goodsDetail", paymentInfoBO.getRemark());//商品详情
					setAttribute(request, "buyerName","");//买方姓名
					setAttribute(request, "buyerContact", "");//买方联系方式
					setAttribute(request, "merRemark1", "");//商户备用信息字段
					
					setAttribute(request, "merRemark2", "");//商户备用信息字段
					setAttribute(request, "signValue", paymentInfoBO.getSignInfo());//密文串
					setAttribute(request, "gopayServerTime", paymentInfoBO.getServerTime());//服务器时间
					setAttribute(request, "bankCode", bank);//银行代码
					setAttribute(request, "userType", "1");//用户类型
				}else{
					log.error("支付服务方参数获取错误");
					throw new BizException("充值失败");
				}
				
//				userIndexVO = userIndexService.getUserIndexVO(loginInfo.getCustId());// 我的账户
//				setAttribute(request, "userIndexVO", userIndexVO);
				return getResultPath(MY_PAYSUBMIT);
			}else{
				setAttribute(request, "reMessage", "请先登录......");
				return getResultPath(MY_RECHARGE);
			}
			
		} catch (BizException e) {
			setAttribute(request, "reMessage", e.getMessage());
		}
		
		return null;
	}
	
	
	
	/**
	 *  ajax 获取省区信息
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxQueryPro")
	public Map<String, Object> ajaxQueryPro(
			HttpServletRequest request,
			HttpSession session) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			List<Map<String,Object>> list = userFundService.getProvinceList();
			jsonMap.put("list", list);
			jsonMap.put("status", "1");
		} catch (Exception e) {
			 
			jsonMap.put("reMes", "获取省区信息失败，请重试");
			jsonMap.put("status", "-1");
		}
		return jsonMap;
	}
	
	
	/**
	 *  ajax 获取城市信息
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxQueryCity")
	public Map<String, Object> ajaxQueryCity(
			@RequestParam("proId") String proId,
			HttpServletRequest request,
			HttpSession session) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			List<Map<String,Object>> list = userFundService.getCityListByProId(proId);
			jsonMap.put("list", list);
			jsonMap.put("status", "1");
		} catch (Exception e) {
			 
			jsonMap.put("reMes", "获取城市信息失败，请重试");
			jsonMap.put("status", "0");
		}
		return jsonMap;
	}
	
	
	
	/**
	 *  ajax 获取银行信息
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxQueryBank")
	public Map<String, Object> ajaxQueryBank(
			HttpServletRequest request,
			HttpSession session) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		List<Map> list = new ArrayList<Map>();
		try {
			//List<Map<String,Object>> list = bankCardBS.getBankList();           //XXXService.取得银行列表方法;
						//new ArrayList<Map<String,Object>>();
//			List<SysCodeDict> syslist = cacheManager.getCodeList("bankName");
			List<SysBankCode> bankList = codeDictBS.findBankCodes();
			for(SysBankCode bank : bankList){
			  // System.out.println("银行列表=="+sysCodeDict.getDisplayValue());
			   Map<String,Object> bankItem = new HashMap<String,Object>();
			   bankItem.put("id", bank.getCodeValue());	
			   bankItem.put("name", bank.getBankName());
			   list.add(bankItem);
			}
			jsonMap.put("list", list);
			jsonMap.put("status", "1");
		} catch (Exception e) {
			 
			jsonMap.put("reMes", "获取银行信息失败，请重试");
			jsonMap.put("status", "-1");
		}
		return jsonMap;
	}
	
	
	
	
	/**
	 *  ajax 获取提现记录
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxQueryTxjr")
	public Pager ajaxQueryTxjr(@RequestParam("requestPage") int requestPage,
			@RequestParam("pageSize") int pageSize, HttpSession session) {
		Pager pager = new Pager();
		try {
			if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null ) {
				LoginInfo loginInfo = (LoginInfo) session.getAttribute(AppConst.LOGIN_INFO_SESSION);
				pager = this.userFundService.findWithdrawalsList(requestPage, pageSize, loginInfo.getCustId());
				
				List<Map<String, Object>> list = pager.getList();
				if (CollectionUtil.isNotEmpty(list)) {
					Map changeColMap = new HashMap();
					changeColMap.put("tx_status", "withdrawalsStatusCd");
					list = cacheManager.changeListDisplayName(list,
							changeColMap);
					pager.setList(list);
				}
				
				
			}
		} catch (Exception e) {
			 
		}
		return pager;
	}
	
	@RequestMapping(value = "/returnBack")
	public String paymentReturnBack(HttpServletRequest request){
		try {
			UserIndexVO userIndexVO = new UserIndexVO();
			setCommonData(request);// 设置公共数据
			if(AppConst.PAY_SERVICE_TL.equals(paramBSService.getParam("PAY_SERVICE"))){
				try {
					request.setCharacterEncoding("UTF-8");
				} catch (UnsupportedEncodingException e1) {
				}
				if (request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION) != null ) {
					LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION) ;
					String fee = paramBSService.getParam("CZSXF");
					if(StringUtils.isEmpty(fee)){
						fee="0.00";
					} 
					userIndexVO = userIndexService.getUserIndexVO(loginInfo.getCustId());// 我的账户
					// 绑定数据
					setAttribute(request, "userIndexVO", userIndexVO);
					setAttribute(request, "fee", fee);
					setAttribute(request, "tabIndex", "tab1");

//					String sucCode = request.getParameter("Succeed");
					/*String payResult = request.getParameter("payResult");
					String errorCode = request.getParameter("errorCode");
					if(log.isDebugEnabled()){
						log.debug("充值返回代码========"+payResult);
						log.debug("充值错误代码========"+errorCode);
					}
					if("1".equals(payResult)){
						setAttribute(request, "sucMes", "充值成功");
					}else{
						setAttribute(request, "errMes", "充值失败");
					}*/
//					String myVid = request.getParameter("myVid");
//					setAttribute(request,"myVid",myVid);
					setAttribute(request, "sucMes", "充值完成");
				}

			}else if(AppConst.PAY_SERVICE_GFB.equals(paramBSService.getParam("PAY_SERVICE"))){
				try {
					request.setCharacterEncoding("GBK");
				} catch (UnsupportedEncodingException e1) {
				}
				if (request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION) != null ) {
					LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION) ;
					String fee = paramBSService.getParam("CZSXF");
					if(StringUtils.isEmpty(fee)){
						fee="0.00";
					} 
					userIndexVO = userIndexService.getUserIndexVO(loginInfo.getCustId());// 我的账户
					// 绑定数据
					setAttribute(request, "userIndexVO", userIndexVO);
					setAttribute(request, "fee", fee);
					setAttribute(request, "tabIndex", "tab1");

//					String sucCode = request.getParameter("Succeed");
					String sucCode = request.getParameter("respCode");
					String msgExt = request.getParameter("msgExt");
					if(log.isDebugEnabled()){
						log.debug("充值返回代码========"+sucCode);
						log.debug("充值返回信息========"+msgExt);
					}
					if(AppConst.PAY_SUCCESS.equals(sucCode)){
						setAttribute(request, "sucMes", "充值成功");
					}else{
						setAttribute(request, "errMes", "充值失败");
					}
//					String myVid = request.getParameter("myVid");
//					setAttribute(request,"myVid",myVid);
				}
			}
			
		} catch (BizException e) {
			 
		}
		if("1".equals(request.getParameter("ext1"))){
//			return getResultPath("/fund/mobileMyRecharge");
			return getResultPath("/fund/mobileRechargeSuccess");
		}
		return getResultPath("/fund/myRecharge");
	}
	
}