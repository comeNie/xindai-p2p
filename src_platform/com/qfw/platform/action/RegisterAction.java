package com.qfw.platform.action;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.bizservice.transaction.chanel.IAuthenticationBS;
import com.qfw.common.bizservice.permission.IUserBS;
import com.qfw.common.bizservice.permission.IUserInfoSyncBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.DateUtils;
import com.qfw.common.util.DateUtils.DateType;
import com.qfw.common.util.MobileUtil;
import com.qfw.manager.model.SecurityErrorMsg;
import com.qfw.manager.model.UserSecurity;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.platform.cache.CustomGenericManageableCaptchaService;
import com.qfw.platform.cache.JCaptchaEngine;
import com.qfw.platform.cache.MessageManager;
import com.qfw.platform.cache.SmsMessageManager;
import com.qfw.platform.model.register.Msg;
import com.qfw.platform.model.register.RegisterInfo;
import com.qfw.platform.model.register.SendMessage;
import com.qfw.platform.model.vo.LoginInfo;
import com.qfw.platform.model.vo.UserInfo;
import com.qfw.platform.utils.AuthUtil;

/**
 * 会员注册
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/register")
public class RegisterAction extends BaseAction {
	
	private static Logger log = LogFactory.getInstance().getBusinessLogger();
	
	@Resource
	private CustomGenericManageableCaptchaService captchaService;

	@Resource
	private MessageManager messageManager;

	@Resource(name = "userBS")
	private IUserBS userBS;

	@Resource(name = "custInfoBS")
	private ICustInfoBS custInfoBS;
	
	@Resource(name = "authenticationBS")
	private IAuthenticationBS authenticationBS;
	@Resource(name = "userInfoSync")
	private IUserInfoSyncBS userInfoSyncBS;

	/**
	 * 生成所有的静态页面
	 */
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		String userCode = (String) request.getParameter("userCode");
		String recommender = (String) request.getParameter("recommender");
		if(StringUtils.isNotEmpty(userCode)){
			String userCodeStr = null;
			try {
				userCodeStr  = new String(userCode.getBytes("iso8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				 
			}
			setAttribute(request, "userCode", userCodeStr);
		}
		setAttribute(request, "recommender", recommender);
		setCommonData(request);
		
		if(MobileUtil.JudgeIsMoblie(request)){
			return "/platform/mobileRegisterIndex";
		}
		return "/platform/registerIndex";
	}
	
	@RequestMapping(value = "/mobileRegister")
	public String register(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("username") String userName,
			@RequestParam("password") String password,
			@RequestParam("confirmPassword") String confirmPassword,
			@RequestParam("mobile") String mobile,
			@RequestParam("authCode") String code,
			@RequestParam("recommender") String recommender,
//			@RequestParam("registerType") int type,
			@RequestParam("j_captcha") String j_captcha)
			{
		String tel = request.getParameter("mobile");
		String email = request.getParameter("email");
		String errMes = "";
		String authCode = SmsMessageManager.getInstance().getAuthCode(mobile);
		setCommonData(request);
		setAttribute(request, "mobile", mobile);
		setAttribute(request, "username", userName);
		setAttribute(request, "recommender", recommender);
		
		int type = SendMessage.MESSAGE_TYPE_PHONE;
		Msg msg = checkEmpty(userName, password, confirmPassword, type, tel,
				email, recommender, j_captcha);
		if (Msg.SUCCESS_STATUS == msg.getStatus()) {
			// 注册成功处理
			msg = checkAll(request, userName, password, confirmPassword, type,
					tel, email, recommender, j_captcha);
			if (StringUtils.isEmpty(authCode)) {
				errMes = "验证码失效.";
				setAttribute(request, "errMes", errMes);
				return "/platform/mobileRegisterIndex";
			} else {
				if (code.equals(authCode)) {
					if(StringUtils.isNotEmpty(recommender)){//推荐码不为空
						BizCustomerBO cust = null;
						try {
							cust = custInfoBS.findCustByRefereeCode(recommender);
						} catch (BizException e) {
							setAttribute(request, "errMes", "无此推荐码.");
							return "/platform/mobileRegisterIndex";
						}
						
						if (null == cust) {
							setAttribute(request, "errMes", "无此推荐码.");
							return "/platform/mobileRegisterIndex";
						}
					}
					// 保存注册信息
					try {
						userBS.saveRegisterUser(mobile, "",userName, password,recommender);
						/*//同步用户信息
						UserInfo userInfo = new UserInfo();
						userInfo.setMobile(mobile);
						userInfo.setLoginName(userName);
						userInfo.setPassword(password);
						userInfo.setRefereeName(recommender);
						userInfo.setOperate(AppConst.USERINFOSYNC_OPERATE_REG);
						this.userInfoSyncBS.userInfoSync(userInfo);*/
						
					} catch (Exception e) {
						setAttribute(request, "errMes", "注册失败");
						return "/platform/mobileRegisterIndex";
					}
					// 模拟登陆
					mockLogin(request, response, userName);
					// 验证码处理
					captchaService.removeCaptcha(request.getSession().getId());
					return "/platform/mobileRegisterAuth_Success";
				} else {
					errMes = "验证码不正确.";
					setAttribute(request, "errMes", errMes);
					return "/platform/mobileRegisterIndex";
				}
			}
				
		}else{
			setAttribute(request, "errMes", msg.getMessage());
			return "/platform/mobileRegisterIndex";
		}
			
	}

	@RequestMapping(value = "/beforeRegister")
	public String beforeRegister(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("username") String userName,
			@RequestParam("password") String password,
			@RequestParam("confirmPassword") String confirmPassword,
			@RequestParam("recommender") String recommender,
			@RequestParam("registerType") int type,
			@RequestParam("j_captcha") String j_captcha) {
		String tel = request.getParameter("mobile");
		String email = request.getParameter("email");
		
		setCommonData(request);
		
		Msg msg = checkEmpty(userName, password, confirmPassword, type, tel,
				email, recommender, j_captcha);
		if (Msg.SUCCESS_STATUS == msg.getStatus()) {
			// 注册成功处理
			msg = checkAll(request, userName, password, confirmPassword, type,
					tel, email, recommender, j_captcha);
			
			if(StringUtils.isNotEmpty(recommender)){
//				SysUser user = userBS.findUser(recommender);
				BizCustomerBO cust = null;
				try {
					cust = custInfoBS.findCustByRefereeCode(recommender);
				
				//大小写问题
					if (null == cust) {
						setAttribute(request, "errMes", "无此推荐人。");
						return "redirect:/register/index.do";
					}
				} catch (BizException e) {
					// TODO Auto-generated catch block
				}
				
			}
			
			
			if (Msg.SUCCESS_STATUS == msg.getStatus()) {
				if (type == SendMessage.MESSAGE_TYPE_EMAIL) {
					// 邮箱地址验证
					messageManager.addEmailMessage(email, userName, password);
				} else if (type == SendMessage.MESSAGE_TYPE_PHONE) {
					// 手机号码验证
					if(log.isDebugEnabled()){
						log.debug("发送短信处理开始===========");
					}
					messageManager.addSmsMessage(tel, userName,
							confirmPassword, recommender);
					if(log.isDebugEnabled()){
						log.debug("发送短信处理结束===========");
					}
				}

				try {
					if (SendMessage.MESSAGE_TYPE_EMAIL == type) {
						userBS.saveRegisterUser(tel, null, userName,
								confirmPassword, recommender);
					}
				} catch (BizException e) {
					 
				}
				// 验证码处理
				captchaService.removeCaptcha(request.getSession().getId());
			}else{
				setAttribute(request, "errMes", msg.getMessage());
				return "redirect:/register/index.do";
			}
			
			
			if (SendMessage.MESSAGE_TYPE_EMAIL == type) {
				setAttribute(request, "email", email);
				return "/platform/registerAuth_Email";
			} else if (SendMessage.MESSAGE_TYPE_PHONE == type) {
				setAttribute(request, "userName", userName);
				setAttribute(request, "mobile", tel);
//				if(MobileUtil.JudgeIsMoblie(request)){
//					return "/platform/mobileRegisterAuth_Phone";	
//				}
				return "/platform/registerAuth_Phone";
			}
		}
		
		setAttribute(request, "errMes", msg.getMessage());
		return "redirect:/register/index.do";
	}

	/**
	 * 验证短信验证码
	 * 
	 * @return
	 */
	@RequestMapping(value = "/authSmsMessage")
	public String authSmsMessage(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("userName") String userName,
			@RequestParam("mobile") String mobile,
			@RequestParam("authCode") String code) {
		String authCode = SmsMessageManager.getInstance().getAuthCode(mobile);
		String errMsg = "";
		setCommonData(request);
		
		if (StringUtils.isEmpty(authCode)) {
			errMsg = "验证码失效.";
			setAttribute(request, "mobile", mobile);
			setAttribute(request, "errMsg", errMsg);
			return "/platform/registerAuth_Phone";
		} else {
			if (code.equals(authCode)) {

				// 保存注册信息
				RegisterInfo registerInfo = SmsMessageManager.getInstance()
						.getRegisterInfoByTel(mobile);
				if (null != registerInfo) {
					try {
						userBS.saveRegisterUser(registerInfo.getTel(), "",
								registerInfo.getUsername(), registerInfo.getPassword(),
								registerInfo.getRecommender());
						//同步用户信息
						/*UserInfo userInfo = new UserInfo();
						userInfo.setMobile(registerInfo.getTel());
						userInfo.setLoginName(registerInfo.getUsername());
						userInfo.setPassword(registerInfo.getPassword());
						userInfo.setRefereeName(registerInfo.getRecommender());
						userInfo.setOperate(AppConst.USERINFOSYNC_OPERATE_REG);
						this.userInfoSyncBS.userInfoSync(userInfo);*/
						
					} catch (Exception e) {
						setAttribute(request, "errorMsg", "注册失败");
						return null;
					}
					// 模拟登陆
					mockLogin(request, response, userName);
					boolean isMobile = MobileUtil.JudgeIsMoblie(request);
					if(isMobile){
						return "/platform/mobileRegisterAuth_Success";
					}
					return "/platform/registerAuth_Success";
				} else {
					errMsg = "验证码失效.";
					setAttribute(request, "mobile", mobile);
					setAttribute(request, "errMsg", errMsg);
					return "/platform/registerAuth_Phone";
				}
			} else {
				errMsg = "验证码失效.";
				setAttribute(request, "mobile", mobile);
				setAttribute(request, "errMsg", errMsg);
				return "/platform/registerAuth_Phone";
			}
		}
	}

	/**
	 * 注册认证
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/authEmail")
	public String authEmail(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("username") String userName,
			@RequestParam("auth") String auth) {
		String errorMsg = "";
		  
		setCommonData(request);
		
		if (StringUtils.isEmpty(userName)) {
			errorMsg = "用户名为空.";
			setAttribute(request, "errorMsg", errorMsg);
			return "/platform/registerAuth";
		}  
		
		if (StringUtils.isEmpty(auth)) {
			errorMsg = "授权码为空.";
			setAttribute(request, "errorMsg", errorMsg);
			return "/platform/registerAuth";
		} 
		//验证auth
		String configUserName = AuthUtil.getFieldByAuth(auth,0);
		String mode = AuthUtil.getFieldByAuth(auth,1);
		String email = AuthUtil.getFieldByAuth(auth,2);
		String key = AuthUtil.getFieldByAuth(auth,3);
		String dateStr = AuthUtil.getFieldByAuth(auth,4);
		//判断验证链接地址是否失效
		double hourCount = DateUtils.DateMinu(DateUtils.getDateFormateByStr(dateStr), new Date(), DateType.HOUR) ;
		if(hourCount > AppConst.EMAIL_AUTH_HOUR){
			errorMsg = "邮箱验证链接已失效.";
			setAttribute(request, "errorMsg", errorMsg);
			return "/platform/registerAuth";
		}
		
		if(!configUserName.equals(userName) && !mode.equals(AppConst.BINGING_EMAIL) && email.equals("") &&  !key.equals(AuthUtil.AUTH_KEY) ){
			errorMsg = "授权码错误.";
			setAttribute(request, "errorMsg", errorMsg);
			return "/platform/registerAuth";
		}
		
		// 验证用户名
		SysUser sysUser = checkUserByUserName(userName);
		
		if (null == sysUser) {
			errorMsg = "用户不存在.";
			setAttribute(request, "errorMsg", errorMsg);
			return "/platform/registerAuth";
	     } 
		
		try {
			userBS.updateUserOfEmail(email, AppConst.USER_STATUS_YES, userName);
			
		} catch (BizException e) {
			 
			errorMsg = "EMAIL更新失败";
			setAttribute(request, "errorMsg", errorMsg);
			return "/platform/registerAuth";
			
		}
		
		// 模拟登陆
		mockLogin(request, response, userName);
		 
	    return "/platform/registerAuth_Success";
	}

	
	/**
	 * 绑定或者解绑邮箱
	 * @param request
	 * @param realname
	 * @param idcard
	 * @return
	 */
	@RequestMapping(value = "/bingingEmail")
	public String bingingEmail(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("username") String userName,
			@RequestParam("auth") String auth) {
		
		String errorMsg = "";
		  
		setCommonData(request);
		int type = SecurityErrorMsg.SECURITY_TYPE_EMAIL;
		
		if (StringUtils.isEmpty(userName)) {
			errorMsg = "用户名为空.";
			setAttribute(request, "errorMsg", errorMsg);
			return "/platform/registerAuth";
		}  
		
		if (StringUtils.isEmpty(auth)) {
			errorMsg = "授权码为空.";
			setAttribute(request, "errorMsg", errorMsg);
			return "/platform/registerAuth";
		} 
		// 验证auth
		String configUserName = AuthUtil.getFieldByAuth(auth,0);
		String mode = AuthUtil.getFieldByAuth(auth,1);
		String email = AuthUtil.getFieldByAuth(auth,2);
		String key = AuthUtil.getFieldByAuth(auth,3);
		String dateStr = AuthUtil.getFieldByAuth(auth,4);
		//判断验证链接地址是否失效
		double hourCount = DateUtils.DateMinu(DateUtils.getDateByFull(dateStr), new Date(), DateType.HOUR) ;
		if(hourCount > AppConst.EMAIL_BING_HOUR){
			errorMsg = "邮箱验证链接已失效.";
			setAttribute(request, "errorMsg", errorMsg);
			return "/platform/registerAuth";
		}
		
		if(!configUserName.equals(userName) && !mode.equals(AppConst.BINGING_EMAIL) && email.equals("") &&  !key.equals(AuthUtil.AUTH_KEY) ){
			errorMsg = "授权码错误.";
			setAttribute(request, "errorMsg", errorMsg);
			return "/platform/registerAuth";
		}
		
		LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION);
		try {
			if(mode.equals(AppConst.BINGING_EMAIL)){
				userBS.updateUserOfEmail(email, AppConst.USER_STATUS_YES, userName);
				//更新session
				if(null != loginInfo ){
						UserSecurity userSecurity = loginInfo.getUserSecurity();
						userSecurity.setEmail(email);
						loginInfo.setUserSecurity(userSecurity);
						request.getSession().setAttribute(AppConst.LOGIN_INFO_SESSION, loginInfo);
				   }
			}else{
				userBS.updateUserOfEmail("", AppConst.USER_STATUS_YES, userName);
				//更新session
				if(null != loginInfo ){
						UserSecurity userSecurity = loginInfo.getUserSecurity();
						userSecurity.setEmail("");
						loginInfo.setUserSecurity(userSecurity);
						request.getSession().setAttribute(AppConst.LOGIN_INFO_SESSION, loginInfo);
				   }
			}
			
		} catch (BizException e) {
			 
			if(mode.equals(AppConst.BINGING_EMAIL)){
				errorMsg = "email绑定失败";
				setAttribute(request, "errorMsg", errorMsg);
				return "/platform/registerAuth";
			}else{
				errorMsg = "email解绑失败";
				setAttribute(request, "errorMsg", errorMsg);
				return "/platform/registerAuth";
			}
		}
		 
	    return "/platform/bingAuth_Success";
	}

	/**
	 * 模拟登陆
	 */
	private void mockLogin(HttpServletRequest request,
			HttpServletResponse response, String userName) {
		SysUser sysUser = userBS.findUser(userName);
		if (null != sysUser) {
			LoginInfo loginInfo = new LoginInfo();
			loginInfo.setUserId(sysUser.getUserId());
			loginInfo.setUserCode(sysUser.getUserCode());
			try {
				BizCustomerBO customerBO = custInfoBS.findCustByUserId(sysUser
						.getUserId());
				
				UserSecurity userSecurity = new UserSecurity();
				userSecurity.setCashPassword(sysUser.getTradePassword());
				userSecurity.setEmail(sysUser.getEmail());
				userSecurity.setIdCard(sysUser.getCardid());
				userSecurity.setMobile(sysUser.getTel());
				userSecurity.setPassword(sysUser.getPassword());
				userSecurity.setNickName(sysUser.getUserCode());
				userSecurity.setRealName(sysUser.getUserName());
				userSecurity.setBirthDate(DateUtils.getYmd(customerBO.getBirthDate()));
				
				loginInfo.setCustId(customerBO.getId());
				loginInfo.setCertificateNum(customerBO.getCertificateNum());
				loginInfo.setCustName(customerBO.getCustName());
				loginInfo.setUserSecurity(userSecurity);
				
				request.getSession().setAttribute(AppConst.LOGIN_INFO_SESSION,
						loginInfo);
				//Cookie loginUsernameCookie = new Cookie(
				//		SysUser.LOGIN_MEMBER_USERNAME_COOKIE_NAME,
				//		URLEncoder.encode(userName.toLowerCase(), "UTF-8"));
				//loginUsernameCookie.setPath(CommonUtil.getBasePath() + "/");
				//response.addCookie(loginUsernameCookie);
			} catch (Exception e) {
				 
			}
		}
	}

	/**
	 * 实名验证(需要登录后才能做实名认证)
	 * 
	 * @return
	 */
	@RequestMapping(value = "/authRealName")
	public String authRealName(HttpServletRequest request,
			@RequestParam("realname") String realname,
			@RequestParam("idcard") String idcard) {
			
			Msg msg = checkIdCard(request.getSession(), realname, idcard);
			if (Msg.SUCCESS_STATUS == msg.getStatus()) {
				//更新实名认证
				LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION);
				try {
					String birthDate = DateUtils.getYmd(DateUtils.getDateByFormat("yyyyMMdd",(idcard.substring(6, 14))));
					userBS.updateUserOfIdCard(realname,idcard,birthDate,loginInfo);
					
					UserSecurity userSecurity = loginInfo.getUserSecurity();
					userSecurity.setNickName(loginInfo.getUserCode());
					userSecurity.setRealName(realname);
					userSecurity.setIdCard(idcard);
					userSecurity.setBirthDate(birthDate);
					loginInfo.setCustName(realname);
					loginInfo.setCertificateNum(idcard);
					loginInfo.setUserSecurity(userSecurity);
					request.getSession().setAttribute(AppConst.LOGIN_INFO_SESSION, loginInfo);
					//同步用户信息
					/*UserInfo userInfo = new UserInfo();
					userInfo.setBirthday(birthDate);
					userInfo.setCustName(realname);
					userInfo.setIdCard(idcard);
					userInfo.setMobile(userSecurity.getMobile());
					userInfo.setOperate(AppConst.USERINFO_SYN_AUTH);
					
					this.userInfoSyncBS.userInfoSync(userInfo);*/
					
				} catch (BizException e) {
//					 
					setCommonData(request);
					setAttribute(request, "errorMsg", "更新实名认证失败");
					boolean isMobile = MobileUtil.JudgeIsMoblie(request);
					if(isMobile){
						return "/platform/mobileRegisterAuth_Success";
					}
					return "/platform/registerAuth_Success";
				}
			} else {
				setCommonData(request);
				setAttribute(request, "errorMsg", msg.getMessage());
				boolean isMobile = MobileUtil.JudgeIsMoblie(request);
				if(isMobile){
					return "/platform/mobileRegisterAuth_Success";
				}
				return "/platform/registerAuth_Success";
			}
		 
		setCommonData(request);
		boolean isMobile = MobileUtil.JudgeIsMoblie(request);
		if(isMobile){
			return "redirect:/userIndex/mobileIndex.do";
		}
		return "redirect:/userIndex/index.do";
		
	}

	/**
	 * 重新发送验证码
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxSendAuthMessage")
	public Map<String, String> ajaxSendAuthMessage(HttpServletRequest request,
			@RequestParam("mobile") String mobile) {
		if (!StringUtils.isEmpty(mobile)) {
			SendMessage sendMessage = new SendMessage();
			sendMessage.setAddress(mobile);
			sendMessage.setType(SendMessage.MESSAGE_TYPE_PHONE);
			String content = AuthUtil.getAuthMessage();
			sendMessage.setCreateTime(System.currentTimeMillis());
			sendMessage.setContent(content);
			sendMessage.setEvenType(AppConst.EVENTTYPE_REGISTER);
			messageManager.addMessage(sendMessage);
		}
		return getSuccessJson("短信发送成功.");
	}

	/**
	 * 检查用户名
	 * 
	 * @param userName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxCheckUserName", method = RequestMethod.GET)
	public Map<String, String> ajaxCheckUserName(HttpServletRequest request,
			HttpServletResponse response) {
		String userName = null;
		try {
			userName = new String(request.getParameter("username").getBytes("iso8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			 
			return getErrorJson("用户名不合法.");
		}
		
		if (StringUtils.isEmpty(userName))
			return getErrorJson("用户名不能为空.");

		if (!checkUserName(userName)) {
			return getErrorJson("对不起，该用户名已经被注册.");
		}
		return getSuccessJson("恭喜您,该用户名可以注册.");
	}
	
	/**
	 * 检查推广人
	 * @param userName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxRecommender")
	public Map<String, String> ajaxRecommender(HttpServletRequest request,
			HttpServletResponse response) {
		/*String recommender;
		try {
			recommender = new String(request.getParameter("recommender").getBytes("iso8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			 
			return getErrorJson("输入不合法");
		}*/
		String recommender = request.getParameter("recommender");
		
		if (StringUtils.isEmpty(recommender)){
		  return getSuccessJson("");
		}
		BizCustomerBO cust = null;
		try {
			cust = custInfoBS.findCustByRefereeCode(recommender);
		} catch (BizException e) {
			// TODO Auto-generated catch block
			 
		}
		if (cust == null) {
//		  return  getErrorJson("无此人");
		  return  getErrorJson("无此推荐码");
		}
		
		return getSuccessJson("");
	}
	/**
	 * 检查手机号码
	 * 
	 * @param tel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxCheckTel")
	public Map<String, String> ajaxCheckTel(@RequestParam("mobile") String tel) {
		if (StringUtils.isEmpty(tel))
			return getErrorJson("手机号码不能为空.");

		if (!checkTel(tel)) {
			return getErrorJson("对不起，该手机号码已经被注册.");
		}
		return getSuccessJson("恭喜您,该手机号码可以注册.");
	}
	

	/**
	 * 检查Email
	 * 
	 * @param email
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxCheckEmail")
	public Map<String, String> ajaxCheckEmail(
			@RequestParam("email") String email) {
		if (StringUtils.isEmpty(email))
			return getErrorJson("邮箱地址不能为空.");

		if (!checkEmail(email)) {
			return getErrorJson("对不起，该邮箱地址已经被注册.");
		}
		return getSuccessJson("恭喜您,该邮箱地址可以注册.");
	}

	/**
	 * 检查身份证
	 * 
	 * @param email
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxCheckIdCard")
	public Map<String, String> ajaxCheckIdCard(HttpServletRequest request,
			@RequestParam("idcard") String idcard) {
		Msg msg = null;
		try {
			String tmpStr = request.getParameter("realname");
			if (!StringUtils.isEmpty(tmpStr)) {
				String realname = new String(tmpStr.getBytes("ISO-8859-1"),
						"UTF-8");
				 msg = checkIdCard(request.getSession(), realname, idcard);
				
				if (Msg.SUCCESS_STATUS == msg.getStatus()) {
					return getSuccessJson("恭喜您,实名认证信息正确.");
				}
				
				
			}
		} catch (UnsupportedEncodingException e) {
			 
		}
		return getErrorJson(msg.getMessage());
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
	 * 验证用户名
	 * @param userName
	 * @return
	 */
	private boolean checkUserName(String userName) {
		if (StringUtils.isEmpty(userName))
			return false;

		int count = userBS.findUserCount(userName);
		if (count > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 验证手机号码是否存在
	 * 
	 * @param tel
	 * @return
	 */
	private boolean checkTel(String tel) {
		if (StringUtils.isEmpty(tel))
			return false;

		int count = userBS.findUserCountByTel(tel);
		if (count > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 检测邮件地址是否存在
	 * 
	 * @param email
	 * @return
	 */
	private boolean checkEmail(String email) {
		if (StringUtils.isEmpty(email))
			return false;

		int count = userBS.findUserCountByEmail(email);
		if (count > 0) {
			return false;
		} else {
			return true;
		}
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
				|| captchaService
						.validateResponseForID(captchaID, challengeVal) == false) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 检查两次输入的密码是否一致
	 * 
	 * @return
	 */
	private boolean checkPassword(String password, String configPassword) {
		if (StringUtils.isEmpty(password)
				|| StringUtils.isEmpty(configPassword))
			return false;
		return password.equals(configPassword);
	}

	/**
	 * 检查身份证
	 * 
	 * @return
	 */
	private Msg checkIdCard(HttpSession session, String realname, String idcard) {
		LoginInfo loginInfo = (LoginInfo) session
				.getAttribute(AppConst.LOGIN_INFO_SESSION);

		if (null == loginInfo)
			return new Msg(Msg.ERROR_STATUS, "您还未登录.");

		if (StringUtils.isEmpty(realname))
			return new Msg(Msg.ERROR_STATUS, "姓名不能为空.");

		if (StringUtils.isEmpty(idcard))
			return new Msg(Msg.ERROR_STATUS, "身份证号不能为空.");
		
		boolean success = false;
		//判断身份证是否已经认证过了
		int count = 0;
		 
		
		try {
//			count = userBS.getUserCountByIdCard(idcard);
			count = custInfoBS.getCustCountByIdCard(idcard);
			if(count > 0){
				return new Msg(Msg.ERROR_STATUS, "此身份证已认证过了！");
			}
			
//			CreditDetailVO creditDetailVO = authenticationBS.personalAuth(
//					realname, idcard);
			success = authenticationBS.personalValidator(realname, idcard);
//			if (null != creditDetailVO) {
//				String result = creditDetailVO.getResult();
				
			if (success) {
				return Msg.SUCCESS_MSG;
			}
		} catch (BizException e) {
//			 
			return new Msg(Msg.ERROR_STATUS, "姓名或者身份证号输入错误.");
		}
		return new Msg(Msg.ERROR_STATUS, "姓名或者身份证号输入错误.");
	}

	/**
	 * 检查空值
	 * 
	 * @return
	 */
	private Msg checkEmpty(String userName, String password,
			String configPassword, int type, String tel, String email,
			String recommender, String challengeResponse) {

		if (StringUtils.isEmpty(userName)) {
			return new Msg(Msg.ERROR_STATUS, "用户名为空.");
		}

		if (StringUtils.isEmpty(password)) {
			return new Msg(Msg.ERROR_STATUS, "密码为空.");
		}

		if (StringUtils.isEmpty(configPassword)) {
			return new Msg(Msg.ERROR_STATUS, "确认密码为空.");
		}

//		if (StringUtils.isEmpty(recommender)) {
//			return new Msg(Msg.ERROR_STATUS, "推荐人为空.");
//		}

		if (type == SendMessage.MESSAGE_TYPE_PHONE) {
			if (StringUtils.isEmpty(tel)) {
				return new Msg(Msg.ERROR_STATUS, "手机号码为空.");
			}
		} else if (type == SendMessage.MESSAGE_TYPE_EMAIL) {
			if (StringUtils.isEmpty(email)) {
				return new Msg(Msg.ERROR_STATUS, "Email为空.");
			}
		}

		if (StringUtils.isEmpty(challengeResponse)) {
			return new Msg(Msg.ERROR_STATUS, "验证码为空.");
		}

		return Msg.SUCCESS_MSG;
	}

	/**
	 * 检查所有的输入项
	 * 
	 * @return
	 */
	private Msg checkAll(HttpServletRequest request, String userName,
			String password, String configPassword, int type, String tel,
			String email, String recommender, String challengeResponse) {
		Msg msg = checkEmpty(userName, password, configPassword, type, tel,
				email, recommender, challengeResponse);

		if (Msg.SUCCESS_STATUS == msg.getStatus()) {
			if (!checkUserName(userName)) {
				return new Msg(Msg.ERROR_STATUS, "用户名不合法.");
			}
			
			if (!checkPassword(password, configPassword)) {
				return new Msg(Msg.ERROR_STATUS, "密码和确认密码不一致.");
			}

			if (type == SendMessage.MESSAGE_TYPE_PHONE) {
				if (!checkTel(tel)) {
					return new Msg(Msg.ERROR_STATUS, "手机号码不合法.");
				}
			} else if (type == SendMessage.MESSAGE_TYPE_EMAIL) {
				if (!checkEmail(email)) {
					return new Msg(Msg.ERROR_STATUS, "Email不合法.");
				}
			}

			if (!checkCaptcha(request, challengeResponse)) {
				return new Msg(Msg.ERROR_STATUS, "验证码不合法.");
			}
		}
		return msg;
	}

	/**
	 * 检查用户
	 * 
	 * @return
	 */
	private SysUser checkUserByUserName(String username) {
		if (StringUtils.isEmpty(username))
			return null;
		SysUser sysUser = userBS.findUser(username);
		return sysUser;
	}
	
	@RequestMapping(value = "/gotoRegister")
	public String gotoRegister(HttpServletRequest request,HttpServletResponse response)
			throws BizException {
		request.getSession().removeAttribute(AppConst.LOGIN_INFO_SESSION);
		String recommender = (String) request.getParameter("recommender");
		setAttribute(request, "recommender", recommender);
		setCommonData(request);
		
		if(MobileUtil.JudgeIsMoblie(request)){
			return "/platform/mobileRegisterIndex";
		}
		return "/platform/registerIndex";
	}

	public CustomGenericManageableCaptchaService getCaptchaService() {
		return captchaService;
	}

	public void setCaptchaService(
			CustomGenericManageableCaptchaService captchaService) {
		this.captchaService = captchaService;
	}

	public MessageManager getMessageManager() {
		return messageManager;
	}

	public void setMessageManager(MessageManager messageManager) {
		this.messageManager = messageManager;
	}

	public IUserBS getUserBS() {
		return userBS;
	}

	public void setUserBS(IUserBS userBS) {
		this.userBS = userBS;
	}

	public ICustInfoBS getCustInfoBS() {
		return custInfoBS;
	}

	public void setCustInfoBS(ICustInfoBS custInfoBS) {
		this.custInfoBS = custInfoBS;
	}

	public IAuthenticationBS getAuthenticationBS() {
		return authenticationBS;
	}

	public void setAuthenticationBS(IAuthenticationBS authenticationBS) {
		this.authenticationBS = authenticationBS;
	}

	public IUserInfoSyncBS getUserInfoSyncBS() {
		return userInfoSyncBS;
	}

	public void setUserInfoSyncBS(IUserInfoSyncBS userInfoSyncBS) {
		this.userInfoSyncBS = userInfoSyncBS;
	}
}