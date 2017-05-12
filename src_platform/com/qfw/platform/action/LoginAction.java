package com.qfw.platform.action;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.bizservice.loan.ILoanManageBS;
import com.qfw.common.bizservice.IMsgTemplateBS;
import com.qfw.common.bizservice.permission.IUserBS;
import com.qfw.common.bizservice.permission.IUserInfoSyncBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.BussConst;
import com.qfw.common.model.SysMessageTemplate;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.DateUtils;
import com.qfw.common.util.MD5Utils;
import com.qfw.common.util.MobileUtil;
import com.qfw.manager.model.UserSecurity;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.platform.cache.CustomGenericManageableCaptchaService;
import com.qfw.platform.cache.JCaptchaEngine;
import com.qfw.platform.cache.MessageManager;
import com.qfw.platform.cache.SmsMessageManager;
import com.qfw.platform.model.json.Message;
import com.qfw.platform.model.vo.LoginInfo;
import com.qfw.platform.model.vo.UserInfo;

/**
 * 登陆请求
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "/loginAction")
public class LoginAction extends BaseAction {

	@Resource
	private CustomGenericManageableCaptchaService captchaService;
	@Resource(name = "userBS")
	private IUserBS userBS;
	@Resource(name = "custInfoBS")
	private ICustInfoBS custInfoBS;
	@Resource(name = "loanManageBS")
	private ILoanManageBS loanManageBS;
	@Resource
	private MessageManager messageManager;
	@Resource(name = "userInfoSync")
	private IUserInfoSyncBS userInfoSyncBS;
	
	@Resource(name = "msgTemplateBS")
	private IMsgTemplateBS msgTemplateBS;

	
	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request,
			HttpServletResponse response) {
		setCommonData(request);
		 if(MobileUtil.JudgeIsMoblie(request)){
			 return getResultPath(MOBILE_LOGIN_INDEX);
		 }
	
		return getResultPath(LOGIN_INDEX);
	}
	
	@RequestMapping(value = "/findPwd")
	public String findPwd(HttpServletRequest request,
			HttpServletResponse response) {
		setCommonData(request);
		return getResultPath(FIND_PWD);
	}
	
	@RequestMapping(value = "/findPwdByMobile")
	public String findPwdByMobile(@RequestParam("mobile") String mobile,
			@RequestParam("j_captcha") String j_captcha,HttpServletRequest request,
			HttpServletResponse response) {
		setCommonData(request);
		String errorMsg = "";
		
		if (!checkCaptcha(request, j_captcha)) {
			errorMsg = "对不起，您输入的验证码错误!" ;
			setCommonData(request);
			setAttribute(request, "mobile",mobile);
			setAttribute(request, "errMes",errorMsg);
			return getResultPath(FIND_PWD);
		}
		
		// 手机号码验证
		try {
			SysMessageTemplate mt = msgTemplateBS.getMsgTemp(AppConst.EVENTTYPE_REGISTER, BussConst.MSG_MP_TYPE_CD_SMS);
			//发送手机验证码
			messageManager.addSmsMess(mobile,AppConst.PHONE_SEND_TYPE_EDITPWD);
		} catch (BizException e) {
//			log.error("添加短信失败："+e.getMessage());
		}
		setAttribute(request, "mobile",mobile);
		// 验证码处理
		captchaService.removeCaptcha(request.getSession().getId());
		return getResultPath(FIND_PWD_BY_MOBILE);
	}
	
	
	@RequestMapping(value = "/findPwdByMobileAuth")
	public String findPwdByMobileAuth(@RequestParam("mobile") String mobile,
			@RequestParam("authCode") String authCode,HttpServletRequest request,
			HttpServletResponse response) {
		setCommonData(request);
		String errorMsg = "";
		
		if (StringUtils.isEmpty(mobile)) {
			errorMsg = "手机号码不能为空.";
			//System.out.println("错误信息===="+errorMsg);
			setAttribute(request, "mobile",mobile);
			setAttribute(request, "errMes",errorMsg);
			return getResultPath(FIND_PWD_BY_MOBILE);
		}

		if (StringUtils.isEmpty(authCode)) {
			errorMsg = "手机号码不能为空.";
			//System.out.println("错误信息===="+errorMsg);
			setAttribute(request, "errMes",errorMsg);
			setAttribute(request, "mobile",mobile);
			return getResultPath(FIND_PWD_BY_MOBILE);
		}
		 
		String authCodeForSys = SmsMessageManager.getInstance().getAuthCode(mobile);
		//System.out.println("authCodeForSys=="+authCodeForSys);
		
		if (!authCode.equals(authCodeForSys)) {
			errorMsg = "手机验证码错误.";
			//System.out.println("错误信息===="+errorMsg);
			setAttribute(request, "errMes",errorMsg);
			setAttribute(request, "mobile",mobile);
			return getResultPath(FIND_PWD_BY_MOBILE);
		}
		
		setAttribute(request, "mobile",mobile);
		setAttribute(request, "authCode",authCode);
		return getResultPath(EDIT_PWD_BY_MOBILE);
	}
	
	@RequestMapping(value = "/editPwdByMobileAuth")
	public String editPwdByMobileAuth(@RequestParam("mobile") String mobile,
			@RequestParam("authCode") String authCode,
			@RequestParam("password") String password,
			@RequestParam("configPassword") String configPassword,
			HttpServletRequest request,
			HttpServletResponse response) {
		setCommonData(request);
		String errorMsg = "";
		
		if (StringUtils.isEmpty(mobile)) {
			errorMsg = "手机号码不能为空.";
			//System.out.println("错误信息===="+errorMsg);
			setAttribute(request, "errMes",errorMsg);
			setAttribute(request, "mobile",mobile);
			return getResultPath(FIND_PWD_BY_MOBILE);
		}

		if (StringUtils.isEmpty(authCode)) {
			errorMsg = "手机号码不能为空.";
			//System.out.println("错误信息===="+errorMsg);
			setAttribute(request, "errMes",errorMsg);
			setAttribute(request, "mobile",mobile);
			return getResultPath(FIND_PWD_BY_MOBILE);
		}
		  
		String authCodeForSys = SmsMessageManager.getInstance().getAuthCode(mobile);
		//System.out.println("authCodeForSys=="+authCodeForSys);
		
		if (!authCode.equals(authCodeForSys)) {
			errorMsg = "手机验证码错误.";
			//System.out.println("错误信息===="+errorMsg);
			setAttribute(request, "errMes",errorMsg);
			setAttribute(request, "mobile",mobile);
			return getResultPath(FIND_PWD_BY_MOBILE);
		}
		
		if(!password.equals(configPassword)){
			errorMsg = "两次输入的密码不一致.";
			//System.out.println("错误信息===="+errorMsg);
			setAttribute(request, "errMes",errorMsg);
			setAttribute(request, "mobile",mobile);
			setAttribute(request, "authCode",authCode);
			return getResultPath(EDIT_PWD_BY_MOBILE);
		}
		
		SysUser sysUser = userBS.findUserByTel(mobile);
		String Md5password = "";
		try {
			Md5password = userBS.getMd5Password(sysUser.getUserCode(), password);
			userBS.updateUserOfPassword(Md5password, sysUser.getUserId());
			//同步用户信息
			/*UserInfo userInfo = new UserInfo();
			userInfo.setMobile(mobile);
			userInfo.setPassword(password);
			userInfo.setOperate(AppConst.USERINFO_SYN_CHG);
			this.userInfoSyncBS.userInfoSync(userInfo);*/
		} catch (BizException e) {
//			 
		}
		String sucMes = "密码修改成功";
		setAttribute(request, "sucMes",sucMes);
		return getResultPath(LOGIN_INDEX);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/loginIndex")
	public String loginIndex(@RequestParam("username") String userName,
			@RequestParam("password") String password,
			@RequestParam("j_captcha") String j_captcha,HttpServletRequest request,
			HttpServletResponse response) {
			
		String errorMsg = "";
		boolean isMobile = MobileUtil.JudgeIsMoblie(request);

		if (StringUtils.isEmpty(userName)) {
			errorMsg = "用户名不能为空!" ;
			setCommonData(request);
			setAttribute(request, "errMes",errorMsg);
			if(isMobile){
				return getResultPath(MOBILE_LOGIN_INDEX);
			}
			return getResultPath(LOGIN_INDEX);
		}
		
		if (StringUtils.isEmpty(password)) {
			errorMsg = "用户名不能为空!" ;
			setCommonData(request);
			setAttribute(request, "errMes",errorMsg);
			if(isMobile){
				return getResultPath(MOBILE_LOGIN_INDEX);
			}
			return getResultPath(LOGIN_INDEX);
		}
		
		if (StringUtils.isEmpty(j_captcha)) {
			errorMsg = "验证码不能为空!" ;
			setCommonData(request);
			setAttribute(request, "errMes",errorMsg);
			if(isMobile){
				return getResultPath(MOBILE_LOGIN_INDEX);
			}
			return getResultPath(LOGIN_INDEX);
		}else if(!checkCaptcha(request, j_captcha)){
			errorMsg = "验证码错误!" ;
			setCommonData(request);
			setAttribute(request, "errMes",errorMsg);
			if(isMobile){
				return getResultPath(MOBILE_LOGIN_INDEX);
			}
			return getResultPath(LOGIN_INDEX);
		}
		
		
		//System.out.println("userName=="+userName);
		SysUser sysUser = userBS.findUserByAll(userName);
		if (null != sysUser) {
			if(AppConst.USER_FREEZE_YES.equals(sysUser.getStatus())){
				errorMsg = "用户名不存在！" ;
				setCommonData(request);
				setAttribute(request, "errMes",errorMsg);
				if(isMobile){
					return getResultPath(MOBILE_LOGIN_INDEX);
				}
				return getResultPath(LOGIN_INDEX);
			}
			String newPassword = MD5Utils.getMD5Str(password);
			//System.out.println("userName=="+sysUser.getUserCode()+";password=="+ password +";md5Password=="+newPassword);
			if (!StringUtils.isEmpty(newPassword)) {
				if (!newPassword.equals(sysUser.getPassword())) {
					errorMsg = "您的用户名或密码错误！" ;
					setCommonData(request);
					setAttribute(request, "errMes",errorMsg);
					if(isMobile){
						return getResultPath(MOBILE_LOGIN_INDEX);
					}
					return getResultPath(LOGIN_INDEX);
				}
			}
		} else {
			errorMsg = "用户名不存在！" ;
			setCommonData(request);
			setAttribute(request, "errMes",errorMsg);
			if(isMobile){
				return getResultPath(MOBILE_LOGIN_INDEX);
			}
			return getResultPath(LOGIN_INDEX);
		}

		
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setUserId(sysUser.getUserId());
		UserSecurity userSecurity = new UserSecurity();
		userSecurity.setCashPassword(sysUser.getTradePassword());
		userSecurity.setEmail(sysUser.getEmail());
		userSecurity.setIdCard(sysUser.getCardid());
		userSecurity.setMobile(sysUser.getTel());
		userSecurity.setPassword(sysUser.getPassword());
		userSecurity.setNickName(sysUser.getUserCode());
		userSecurity.setRealName(sysUser.getUserName());
	    
		try {
			BizCustomerBO customerBO = this.custInfoBS.findCustByUserId(sysUser.getUserId());
			if(customerBO == null){
				errorMsg = "用户名不存在！" ;
				setCommonData(request);
				setAttribute(request, "errMes",errorMsg);
				if(isMobile){
					return getResultPath(MOBILE_LOGIN_INDEX);
				}
				return getResultPath(LOGIN_INDEX);
			}
			loginInfo.setCustId(customerBO.getId());
			loginInfo.setCertificateNum(customerBO.getCertificateNum());
			loginInfo.setCustName(customerBO.getCustName());
			loginInfo.setUserCode(sysUser.getUserCode());
			if(AppConst.YES_FLAG.equals(sysUser.getIsAdmin())){
				loginInfo.setIsAdmin("1");
			}
			userSecurity.setBirthDate(DateUtils.getYmd(customerBO.getBirthDate()));
			loginInfo.setUserSecurity(userSecurity);
		} catch (BizException e) {
			 
		}// 查找客户信息
		// 登陆成功后处理
		LoginInfo oldLoginInfo = (LoginInfo) request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION);
		if(null != oldLoginInfo){
		  request.getSession().removeAttribute(AppConst.LOGIN_INFO_SESSION);
		}
		
		request.getSession().setAttribute(AppConst.LOGIN_INFO_SESSION, loginInfo);
		setCommonData(request);
		// 验证码处理
		captchaService.removeCaptcha(request.getSession().getId());
//		return getResultPath(INDEX);
		return"redirect:/userIndex/index.do";
	}
	
	/**
	/**
	 * 异步登陆
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/ajaxLogin")
	@ResponseBody
	public Message ajaxLogin(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("username") String userName,
			@RequestParam("password") String password,
			@RequestParam("nochecklogin") boolean nochecklogin)
			throws UnsupportedEncodingException {
		Message message = new Message();
		if (StringUtils.isEmpty(userName)) {
			message.setStatus(-1);
			message.setMessage("用户名不能为空.");
			return message;
		}

		if (StringUtils.isEmpty(password)) {
			message.setStatus(-1);
			message.setMessage("密码不能为空.");
			return message;
		}

		SysUser sysUser = userBS.findUserByAll(userName);
		if (null != sysUser) {
			String newPassword = MD5Utils.getMD5Str(password);
			//System.out.println("userName=="+sysUser.getUserCode()+";password=="+ password +";md5Password=="+newPassword);
			if (!StringUtils.isEmpty(newPassword)) {
				if (!newPassword.equals(sysUser.getPassword())) {
					message.setStatus(-1);
					message.setMessage("您的用户名或密码错误！");
					return message;
				}
			}
		} else {
			message.setStatus(-1);
			message.setMessage("您的用户名或密码错误！");
			return message;
		}

		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setUserId(sysUser.getUserId());
		UserSecurity userSecurity = new UserSecurity();
		userSecurity.setCashPassword(sysUser.getTradePassword());
		userSecurity.setEmail(sysUser.getEmail());
		userSecurity.setIdCard(sysUser.getCardid());
		userSecurity.setMobile(sysUser.getTel());
		userSecurity.setPassword(sysUser.getPassword());
		userSecurity.setNickName(sysUser.getUserCode());
		userSecurity.setRealName(sysUser.getUserName());
		
		try {
			BizCustomerBO customerBO = this.custInfoBS.findCustByUserId(sysUser
					.getUserId());
			loginInfo.setCustId(customerBO.getId());
			loginInfo.setCertificateNum(customerBO.getCertificateNum());
			loginInfo.setCustName(customerBO.getCustName());
			loginInfo.setUserCode(sysUser.getUserCode());
			if(AppConst.YES_FLAG.equals(sysUser.getIsAdmin())){
				loginInfo.setIsAdmin("1");
			}
			loginInfo.setUserSecurity(userSecurity);

		} catch (BizException e) {
			 
		}// 查找客户信息
		
		
		// 登陆成功后处理
		LoginInfo oldLoginInfo = (LoginInfo) request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION);
		if(null != oldLoginInfo){
		  request.getSession().removeAttribute(AppConst.LOGIN_INFO_SESSION);
		}
		request.getSession().setAttribute(AppConst.LOGIN_INFO_SESSION,loginInfo);
		message.setStatus(0);
		message.setMessage("您好,"+sysUser.getUserCode());
		return message;
	}

	/**
	 * 检查用户名
	 * 
	 * @param userName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxIsLogin")
	public Message ajaxIsLogin(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("username") String userName) {
		Message message = new Message();
		LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION);
		if(loginInfo != null ){
			message.setStatus(0);
			message.setMessage(loginInfo.getUserCode()+"的账户");
			return message; 
		}else{
			message.setStatus(-1);
			message.setMessage("未登陆！");
			return message; 
		}
		
	}
	/**
	 * 安全退出
	 */
	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request,
			HttpServletResponse response) {
		// 清除session
		// 清除Cookie
		//Cookie loginUsernameCookie = new Cookie(
		//		SysUser.LOGIN_MEMBER_USERNAME_COOKIE_NAME, "");
		//loginUsernameCookie.setPath(CommonUtil.getBasePath() + "/");
		//loginUsernameCookie.setMaxAge(0);
		//response.addCookie(loginUsernameCookie);
		request.getSession().removeAttribute(AppConst.LOGIN_INFO_SESSION);
		setCommonData(request);
//		List<LoanManageVO> loanManageVOList;
//		try {
//			loanManageVOList = loanManageBS.findInfoPagesByVO(
//					new LoanSearchVO(), 1, 4);
//			setAttribute(request, "loanManageVOList", loanManageVOList);
//		} catch (BizException e) {
//			 
//		}
//		return getResultPath(INDEX);
		 if(MobileUtil.JudgeIsMoblie(request)){
			 return "redirect:/loginAction/login.do";
		 }
	
		return "redirect:/index.do";
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
		try {
			if (StringUtils.isEmpty(challengeVal)
					|| captchaService.validateResponseForID(captchaID, challengeVal) == false) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			 
			return false;
		}
		
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
	 * 检查手机号码
	 * 
	 * @param tel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxCheckMobile")
	public Map<String, String> ajaxCheckMobile(@RequestParam("mobile") String tel) {
		if (StringUtils.isEmpty(tel))
			return getErrorJson("手机号码不能为空.");

		if (!checkTel(tel)) {
			return getErrorJson("该手机号码未被注册，无法修改密码！");
		}
		return getSuccessJson("恭喜您,您可以通过手机修改密码.");
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
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 初始化密码
	 */
	@RequestMapping(value = "/initPassword")
	public String initPassWord(HttpServletRequest request,
			HttpServletResponse response) {
			
		 try {
			userBS.updatePwdALL();
		} catch (BizException e) {
			// TODO Auto-generated catch block
			 
		} 
		  setCommonData(request);
		  return getResultPath(LOGIN_INDEX);
	}

	public IUserInfoSyncBS getUserInfoSyncBS() {
		return userInfoSyncBS;
	}

	public void setUserInfoSyncBS(IUserInfoSyncBS userInfoSyncBS) {
		this.userInfoSyncBS = userInfoSyncBS;
	}
}