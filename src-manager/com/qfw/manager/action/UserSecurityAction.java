package com.qfw.manager.action;

import java.net.URLDecoder;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.bizservice.transaction.chanel.IAuthenticationBS;
import com.qfw.common.bizservice.permission.IUserBS;
import com.qfw.common.bizservice.permission.IUserInfoSyncBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.DateUtils;
import com.qfw.common.util.MD5Utils;
import com.qfw.common.util.MobileUtil;
import com.qfw.manager.model.SecurityErrorMsg;
import com.qfw.manager.model.UserSecurity;
import com.qfw.model.AppConst;
import com.qfw.model.vo.transaction.CreditDetailVO;
import com.qfw.platform.cache.MessageManager;
import com.qfw.platform.cache.SmsMessageManager;
import com.qfw.platform.model.json.Message;
import com.qfw.platform.model.register.Msg;
import com.qfw.platform.model.register.RegisterInfo;
import com.qfw.platform.model.register.SendMessage;
import com.qfw.platform.model.vo.LoginInfo;
import com.qfw.platform.model.vo.UserInfo;
import com.qfw.platform.utils.AuthUtil;
import com.qfw.common.util.StringUtils;
import com.qfw.common.util.DateUtils.DateType;
/**
 * 安全信息
 * @author Administrator
 */
@Controller
@RequestMapping("/userSecurity")
public class UserSecurityAction extends BaseAction {

	@Resource(name = "authenticationBS")
	private IAuthenticationBS authenticationBS;
	
	@Resource
	private MessageManager messageManager;
	
	@Resource(name = "userBS")
	private IUserBS userBS;
	
	@Resource(name = "custInfoBS")
	private ICustInfoBS custInfoBS;
	
	@Resource(name = "userInfoSync")
	private IUserInfoSyncBS userInfoSyncBS;
	
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request) {
		
		String errMes = "";
		try{
			errMes = request.getParameter("errMes");
			if(StringUtils.isNotEmpty(errMes)){
				errMes = URLDecoder.decode(errMes, "UTF-8");
				setAttribute(request, "errMes", errMes);
			}
		}catch(Exception ex){ex.printStackTrace();}
		
		UserSecurity userSecurity = initData();
		setAttribute(request, "userSecurity", userSecurity);
		String myVid = request.getParameter("myVid");
		setAttribute(request,"myVid",myVid);
		//setAttribute(request,"myVid","userinfohead1");
		// 设置公共数据
		setCommonData(request);
		// 绑定数据
		boolean isMobile = MobileUtil.JudgeIsMoblie(request);
		if(isMobile){
			return getResultPath(MOBILE_MY_SECURITY);
		}
		return getResultPath(MY_SECURITY);
	}
	
	/**
	 * 实名认证
	 * @param request
	 * @param realname
	 * @param idcard
	 * @return
	 */
	@RequestMapping(value = "/realNameAuth")
	public String realNameAuth(HttpServletRequest request,
			@RequestParam("realname") String realname,
			@RequestParam("idcard") String idcard) {
		int type = SecurityErrorMsg.SECURITY_TYPE_AUTH;
		String errorMsgStr = null;
		if (StringUtils.isEmpty(realname)) {
			errorMsgStr = "真实姓名不能为空.";
			return feedBack(request, type, errorMsgStr);
		}

		if (StringUtils.isEmpty(idcard)) {
			errorMsgStr = "身份证号不能为空.";
			return feedBack(request, type, errorMsgStr);
		}
		
		Msg msg = checkIdCard(realname, idcard);
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
				 
				setCommonData(request);
				errorMsgStr = "更新实名认证失败";
				return feedBack(request, type, errorMsgStr);
			}
		} else {
			errorMsgStr =msg.getMessage() ;
			return feedBack(request, type, errorMsgStr);
		}
		return feedBack(request, type, errorMsgStr);

	}
	
	
	/**
	 * 发送绑定或者解绑邮件
	 * @param request
	 * @param realname
	 * @param idcard
	 * @return
	 */
	@RequestMapping(value = "/operEmail")
	public String operEmail(HttpServletRequest request,
			@RequestParam("email") String email,
			@RequestParam("emailMode") String emailMode) {
		
		
		int type = SecurityErrorMsg.SECURITY_TYPE_EMAIL;
		String errMes = null;
		
		 
		LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION);
				
		if (StringUtils.isEmpty(email)) {
			errMes = "邮箱不能为空.";
			return feedBack(request, type, errMes);
		}

		if (StringUtils.isEmpty(emailMode)) {
			errMes = "非法操作.";
			return feedBack(request, type, errMes);
		}
		
		 
		if(checkEmail(email) && emailMode.equals(AppConst.BINGING_EMAIL)){
			errMes = "邮箱已认证过了，无法再认证!";
			return feedBack(request, type, errMes);
		}
		// 邮箱地址验证
		messageManager.addSendEmailMessage(email, loginInfo.getUserCode(), emailMode);
		
		setCommonData(request);
		if(emailMode.equals(AppConst.BINGING_EMAIL)){
			setAttribute(request, "sucMes", "绑定邮箱验证信息已发送到您的邮箱，请到邮箱确认！");
		}else{
			setAttribute(request, "sucMes", "解绑邮箱验证信息已发送到您的邮箱，请到邮箱确认！");
		}
		return feedBack(request, type, errMes);

	}
	
	
	
	
	/**
	 * 修改电话号码
	 * @return
	 */
	@RequestMapping(value = "/modifyPhone")
	public String modifyPhone(HttpServletRequest request,
			@RequestParam("authCode1") String authCode1,
			@RequestParam("mobile2") String mobile2,
			@RequestParam("authCode2") String authCode2) {
		
		//System.out.println("authCode1=="+authCode1);
		int type1 = SecurityErrorMsg.SECURITY_TYPE_PHONE_MODIFY1;
		int type2 = SecurityErrorMsg.SECURITY_TYPE_PHONE_MODIFY2;
		LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION);
		String mobile1 = loginInfo.getUserSecurity().getMobile() ;
		String errorMsgStr = null;
		if (StringUtils.isEmpty(mobile1)) {
			errorMsgStr = "原手机号码不能为空.";
			//System.out.println("错误信息===="+errorMsgStr);
			return feedBack(request, type1, errorMsgStr);
		}

		if (StringUtils.isEmpty(authCode1)) {
			errorMsgStr = "原手机验证码不能为空.";
			//System.out.println("错误信息===="+errorMsgStr);
			return feedBack(request, type1, errorMsgStr);
		}
		
		if (StringUtils.isEmpty(mobile2)) {
			errorMsgStr = "新手机号码不能为空.";
			//System.out.println("错误信息===="+errorMsgStr);
			setAttribute(request, "authCode1", authCode1);
			return feedBack(request, type2, errorMsgStr);
		}

		if (StringUtils.isEmpty(authCode2)) {
			errorMsgStr = "新手机验证码不能为空.";
			//System.out.println("错误信息===="+errorMsgStr);
			setAttribute(request, "authCode1", authCode1);
			return feedBack(request, type2, errorMsgStr);
		}
		
		String authCodeForSys1 = SmsMessageManager.getInstance().getAuthCode(mobile1);
		//System.out.println("authCodeForSys1=="+authCodeForSys1);
		String authCodeForSys2 = SmsMessageManager.getInstance().getAuthCode(mobile2);
		
		if (mobile2.equals(mobile1)) {
			errorMsgStr = "新手机号码不能与旧手机号码一样.";
			//System.out.println("新手机号码不能与旧手机号码一样");
			setAttribute(request, "authCode1", authCode1);
			return feedBack(request, type2, errorMsgStr);
		}
		
		
		if (StringUtils.isEmpty(authCodeForSys1)) {
			errorMsgStr = "原手机验证码已失效.";
			//System.out.println("原手机验证码已失效");
			return feedBack(request, type1, errorMsgStr);
		}
		
		if (StringUtils.isEmpty(authCodeForSys2)) {
			errorMsgStr = "新手机验证码已失效.";
			//System.out.println("新手机验证码已失效");
			setAttribute(request, "authCode1", authCode1);
			return feedBack(request, type2, errorMsgStr);
		}
		
		if (!authCode1.equals(authCodeForSys1)) {
				errorMsgStr = "原手机验证码错误.";
				//System.out.println("原手机验证码错误");
				return feedBack(request, type1, errorMsgStr);
			}
		
		if(!authCode2.equals(authCodeForSys2)){
			errorMsgStr = "新手机验证码错误.";
			//System.out.println("新手机验证码错误");
			setAttribute(request, "authCode1", authCode1);
			return feedBack(request, type2, errorMsgStr);
		}
		
		  if(checkTel(mobile2)){
			  errorMsgStr = "新手机已经认证过,无法修改手机!";
			  //System.out.println("新手机已经认证过,无法修改手机!");
			  setAttribute(request, "authCode1", authCode1);
			  return feedBack(request, type2, errorMsgStr);
		  }
		
		
		try {
			userBS.updateUserOfPhone(mobile2, loginInfo.getUserId());
			UserSecurity userSecurity = loginInfo.getUserSecurity();
			userSecurity.setMobile(mobile2);
			loginInfo.setUserSecurity(userSecurity);
			request.getSession().setAttribute(AppConst.LOGIN_INFO_SESSION, loginInfo);
			
		} catch (BizException e) {
			 
			setCommonData(request);
			errorMsgStr = "修改手机失败";
			return feedBack(request, type1, errorMsgStr);
		}
		setCommonData(request);
		setAttribute(request, "sucMes", "手机修改成功");
		return feedBack(request, type2, errorMsgStr);

	}
	
	/**
	 * 绑定电话号码
	 * @return
	 */
	@RequestMapping(value = "/bindPhone")
	public String bindPhone(HttpServletRequest request,
			@RequestParam("mobile3") String mobile3,
			@RequestParam("authCode3") String authCode3) {
		
		int type = SecurityErrorMsg.SECURITY_TYPE_PHONE;
		String errorMsgStr = null;
		if (StringUtils.isEmpty(mobile3)) {
			errorMsgStr = "手机号码不能为空.";
			//System.out.println("错误信息===="+errorMsgStr);
			return feedBack(request, type, errorMsgStr);
		}

		if (StringUtils.isEmpty(authCode3)) {
			errorMsgStr = "手机验证码不能为空.";
			//System.out.println("错误信息===="+errorMsgStr);
			return feedBack(request, type, errorMsgStr);
		}
		
		 
		
		String authCodeForSys = SmsMessageManager.getInstance().getAuthCode(mobile3);
	 
		
		if (StringUtils.isEmpty(authCodeForSys)) {
			errorMsgStr = "手机号码验证码已失效.";
			//System.out.println("手机验证码已失效");
			return feedBack(request, type, errorMsgStr);
		}
		 
		if(!authCode3.equals(authCodeForSys)){
			errorMsgStr = "手机验证码错误.";
			//System.out.println("手机验证码错误");
			return feedBack(request, type, errorMsgStr);
		}
		
		if(checkTel(mobile3)){
			  errorMsgStr = "新手机已经认证过,无法绑定手机!";
			  //System.out.println("新手机已经认证过,无法绑定手机!");
			  return feedBack(request, type, errorMsgStr);
		  }
		
		LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION);
		try {
			userBS.updateUserOfPhone(mobile3, loginInfo.getUserId());
			UserSecurity userSecurity = loginInfo.getUserSecurity();
			userSecurity.setMobile(mobile3);
			loginInfo.setUserSecurity(userSecurity);
			request.getSession().setAttribute(AppConst.LOGIN_INFO_SESSION, loginInfo);
			
		} catch (BizException e) {
			 
			setCommonData(request);
			errorMsgStr = "绑定手机失败";
			return feedBack(request, type, errorMsgStr);
		}
		
		setAttribute(request, "sucMes", "手机绑定成功");
		return feedBack(request, type, errorMsgStr);

	}
	 
	/**
	 * 验证短信验证码
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkAuthCode")
	public Message checkAuthCode(HttpServletRequest request,
			@RequestParam("authCode1") String authCode) {
		
		LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION);
		
		String mobile = loginInfo.getUserSecurity().getMobile();
		//System.out.println("mobile=="+mobile);
		//System.out.println("authCode=="+authCode);
		
		Message message = new Message();
		
		String authCodeForSys = SmsMessageManager.getInstance().getAuthCode(mobile);
		setCommonData(request);
		if (StringUtils.isEmpty(authCode)) {
			message.setStatus(-1);
			message.setMessage("验证码失效");
			return message;
		} 
		if (authCode.equals(authCodeForSys)) {
				message.setStatus(0);
				message.setMessage("验证成功");
				return message;
			} else {
				message.setStatus(-1);
				message.setMessage("验证码错误");
				return message;
			}
		
	}
	
	 
	/**
	 * 修改登陆密码
	 * @return
	 */
	@RequestMapping(value = "/modifyLoginPasswd")
	public String modifyLoginPasswd(HttpServletRequest request,
			@RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword,
			@RequestParam("newPassword2") String newPassword2) {
		int type = SecurityErrorMsg.SECURITY_TYPE_PASSWORD;
		
		LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION);
		String errorMsgStr = null;
		if(StringUtils.isEmpty(oldPassword)){
			errorMsgStr = "原密码不能为空！";
			return feedBack(request, type, errorMsgStr);
		}
		
		if(StringUtils.isEmpty(newPassword)){
			errorMsgStr = "新密码不能为空！";
			return feedBack(request, type, errorMsgStr);
		}
		
		if(StringUtils.isEmpty(newPassword2)){
			errorMsgStr = "确认新密码不能为空！";
			return feedBack(request, type, errorMsgStr);
		}
		
		if(oldPassword.equals(newPassword) ||oldPassword.equals(newPassword2)){
			errorMsgStr = "旧密码不能与新密码一致！";
			return feedBack(request, type, errorMsgStr);
		}
		
		Msg tmpMsg = checkOldPassword(oldPassword);
		if (Msg.SUCCESS_STATUS == tmpMsg.getStatus()) {
			if (newPassword.equals(newPassword2)) {
		      String passwordMd5 = MD5Utils.getMD5Str(newPassword);
				try {
					userBS.updateUserOfPassword(passwordMd5, loginInfo.getUserId());
					
					//同步用户信息
					/*UserInfo userInfo = new UserInfo();
					userInfo.setMobile(loginInfo.getUserSecurity().getMobile());
					userInfo.setPassword(newPassword);
					userInfo.setOperate(AppConst.USERINFO_SYN_CHG);
					this.userInfoSyncBS.userInfoSync(userInfo);*/
				} catch (BizException e) {
					 
					errorMsgStr = "密码修改失败";
					return feedBack(request, type, errorMsgStr);
					
				}
			}else{
				errorMsgStr = "新密码和确认新密码不一致！";
				return feedBack(request, type, errorMsgStr);
			}
		}else{
			errorMsgStr = tmpMsg.getMessage();
			return feedBack(request, type, errorMsgStr);
		}
		
		
		UserSecurity userSecurity = initData();
		setAttribute(request, "userSecurity", userSecurity);
		if (StringUtils.isNotEmpty(errorMsgStr)) {
			SecurityErrorMsg errorMsg = new SecurityErrorMsg();
			errorMsg.setType(type);
			errorMsg.setMsg(errorMsgStr);
			setAttribute(request, "errorMsg", errorMsg);
		}
		String sucMes = "密码修改成功";
		setAttribute(request, "sucMes", sucMes);
		// 设置公共数据
		setCommonData(request);
		// 绑定数据
		return getResultPath(MY_SECURITY);
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
		////System.out.println("mobile=="+mobile);
		if (!StringUtils.isEmpty(mobile)) {
			SendMessage sendMessage = new SendMessage();
			sendMessage.setAddress(mobile);
			sendMessage.setType(SendMessage.MESSAGE_TYPE_PHONE);
			String content = AuthUtil.getAuthMessage();
			sendMessage.setCreateTime(System.currentTimeMillis());
			sendMessage.setContent(content);
			sendMessage.setEvenType(AppConst.EVENTTYPE_TRADEPWD);
			
			messageManager.addMessage(sendMessage);
		}
		return getSuccessJson("短信重新发送成功.");
	}
	
	/**
	 * 重新发送验证码
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxSendAuthMessageForUser")
	public Map<String, String> ajaxSendAuthMessageForUser(HttpServletRequest request) {
		
		LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION);
		String mobile = loginInfo.getUserSecurity().getMobile();
		//System.out.println("mobile=="+mobile);
		if (!StringUtils.isEmpty(mobile)) {
			SendMessage sendMessage = new SendMessage();
			sendMessage.setAddress(mobile);
			sendMessage.setType(SendMessage.MESSAGE_TYPE_PHONE);
			String content = AuthUtil.getAuthMessage();
			sendMessage.setCreateTime(System.currentTimeMillis());
			sendMessage.setContent(content);
			sendMessage.setEvenType(AppConst.EVENTTYPE_TRADEPWD);
			messageManager.addMessage(sendMessage);
		}
		return getSuccessJson("短信重新发送成功.");
	}
	
	/**
	 * 绑定交易密码
	 * @return
	 */
	@RequestMapping(value = "/bindCashPassword")
	public String bindCashPassword(HttpServletRequest request,
			@RequestParam("authCode4") String authCode4,
			@RequestParam("cashPassword3") String cashPassword3,
			@RequestParam("confirmPassword3") String confirmPassword3) {
		
		LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION);
		UserSecurity userSecurity = loginInfo.getUserSecurity();
		String mobile4 = userSecurity.getMobile();
		int type = SecurityErrorMsg.SECURITY_TYPE_WITHDRAW;
		String errorMsgStr = null;
		if (StringUtils.isEmpty(mobile4)) {
			errorMsgStr = "手机号码不能为空.";
			//System.out.println("错误信息===="+errorMsgStr);
			return feedBack(request, type, errorMsgStr);
		}

		if (StringUtils.isEmpty(authCode4)) {
			errorMsgStr = "手机验证码不能为空.";
			//System.out.println("错误信息===="+errorMsgStr);
			return feedBack(request, type, errorMsgStr);
		}
		
		if (!cashPassword3.equals(confirmPassword3)) {
			errorMsgStr = "两次输入的密码不一致.";
			//System.out.println("错误信息===="+errorMsgStr);
			return feedBack(request, type, errorMsgStr);
		}
		String authCodeForSys = SmsMessageManager.getInstance().getAuthCode(mobile4);
		
		if (StringUtils.isEmpty(authCodeForSys)) {
			errorMsgStr = "手机验证码已失效.";
			//System.out.println("手机验证码已失效");
			return feedBack(request, type, errorMsgStr);
		}
		 
		if(!authCode4.equals(authCodeForSys)){
			errorMsgStr = "手机验证码错误.";
			//System.out.println("手机验证码错误");
			return feedBack(request, type, errorMsgStr);
		}
		String passwordMd5 = MD5Utils.getMD5Str(cashPassword3);
		
		if(passwordMd5.equals(userSecurity.getPassword())){
			errorMsgStr = "交易密码不能与登陆密码相同.";
			//System.out.println("交易密码不能与登陆密码相同");
			return feedBack(request, type, errorMsgStr);
		}
		
		try {
			
			userBS.updateUserOfCashPassword(passwordMd5, loginInfo.getUserId());
			
			userSecurity.setCashPassword(passwordMd5);
			loginInfo.setUserSecurity(userSecurity);
			request.getSession().setAttribute(AppConst.LOGIN_INFO_SESSION, loginInfo);
			
		} catch (BizException e) {
			 
			setCommonData(request);
			errorMsgStr = "交易密码设置失败";
			return feedBack(request, type, errorMsgStr);
		}
		
		setAttribute(request, "sucMes", "交易密码设置成功");
		return feedBack(request, type, errorMsgStr);

	}
	
	
	/**
	 * 修改交易密码
	 * @return
	 */
	@RequestMapping(value = "/modifyCashPassword")
	public String modifyCashPassword(HttpServletRequest request,
			@RequestParam("oldCashPassword") String oldCashPassword,
			@RequestParam("newCashPassword") String newCashPassword,
			@RequestParam("newCashPassword1") String newCashPassword1) {
		
		LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION);
		UserSecurity userSecurity = loginInfo.getUserSecurity();
		
		int type = SecurityErrorMsg.SECURITY_TYPE_WITHDRAW_MODIFY;
		String errorMsgStr = null;
		if (StringUtils.isEmpty(oldCashPassword)) {
			errorMsgStr = "原密码不能为空.";
			//System.out.println("错误信息===="+errorMsgStr);
			return feedBack(request, type, errorMsgStr);
		}

		if (StringUtils.isEmpty(newCashPassword)) {
			errorMsgStr = "新密码不能为空.";
			//System.out.println("错误信息===="+errorMsgStr);
			return feedBack(request, type, errorMsgStr);
		}
		
		if (!newCashPassword.equals(newCashPassword1)) {
			errorMsgStr = "两次输入的密码不一致.";
			//System.out.println("错误信息===="+errorMsgStr);
			return feedBack(request, type, errorMsgStr);
		}
		
		String passwordMd5 = MD5Utils.getMD5Str(newCashPassword);
		
		if (passwordMd5.equals(userSecurity.getCashPassword())) {
			errorMsgStr = "修改后的密码不能与原密码一样.";
			//System.out.println("错误信息===="+errorMsgStr);
			return feedBack(request, type, errorMsgStr);
		}
		 
		String oldPasswordMd5 = MD5Utils.getMD5Str(oldCashPassword);
		if (!oldPasswordMd5.equals(userSecurity.getCashPassword())) {
			errorMsgStr = "原密码输入错误.";
			//System.out.println("错误信息===="+errorMsgStr);
			return feedBack(request, type, errorMsgStr);
		}

		
		
		if(passwordMd5.equals(userSecurity.getPassword())){
			errorMsgStr = "交易密码不能与登陆密码相同.";
			//System.out.println("交易密码不能与登陆密码相同");
			return feedBack(request, type, errorMsgStr);
		}
		
		try {
			userBS.updateUserOfCashPassword(passwordMd5, loginInfo.getUserId());
			userSecurity.setCashPassword(passwordMd5);
			loginInfo.setUserSecurity(userSecurity);
			request.getSession().setAttribute(AppConst.LOGIN_INFO_SESSION, loginInfo);
			
		} catch (BizException e) {
			 
			setCommonData(request);
			errorMsgStr = "交易密码设置失败";
			return feedBack(request, type, errorMsgStr);
		}
		
		setAttribute(request, "sucMes", "交易密码设置成功");
		return feedBack(request, type, errorMsgStr);

	}
	
	
	/**
	 * 找回交易密码
	 * @return
	 */
	@RequestMapping(value = "/findCashPwd")
	public String findCashPwd(HttpServletRequest request,
			@RequestParam("authCode5") String authCode5,
			@RequestParam("certNo") String certNo,
			@RequestParam("newCashPassword4") String newCashPassword4,
			@RequestParam("confirmPassword4") String confirmPassword4) {
		
		LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION);
		UserSecurity userSecurity = loginInfo.getUserSecurity();
		
		int type = SecurityErrorMsg.SECURITY_TYPE_WITHDRAW_FIND;
		String errorMsgStr = null;
		
		String mobile5 = userSecurity.getMobile();
		 
		if (StringUtils.isEmpty(certNo)) {
			errorMsgStr = "身份证不能为空.";
			//System.out.println("错误信息===="+errorMsgStr);
			return feedBack(request, type, errorMsgStr);
		}
		
		if (!certNo.equals(userSecurity.getIdCard())) {
			errorMsgStr = "输入的身份证错误.";
			//System.out.println("错误信息===="+errorMsgStr);
			return feedBack(request, type, errorMsgStr);
		}
		
		if (StringUtils.isEmpty(mobile5)) {
			errorMsgStr = "手机号码不能为空.";
			//System.out.println("错误信息===="+errorMsgStr);
			return feedBack(request, type, errorMsgStr);
		}

		if (StringUtils.isEmpty(authCode5)) {
			errorMsgStr = "码验证码不能为空.";
			//System.out.println("错误信息===="+errorMsgStr);
			return feedBack(request, type, errorMsgStr);
		}
		
		String authCodeForSys = SmsMessageManager.getInstance().getAuthCode(mobile5);
		
		if (StringUtils.isEmpty(authCodeForSys)) {
			errorMsgStr = "手机验证码已失效.";
			//System.out.println("手机验证码已失效");
			return feedBack(request, type, errorMsgStr);
		}
		 
		if(!authCode5.equals(authCodeForSys)){
			errorMsgStr = "手机验证码错误.";
			//System.out.println("手机验证码错误");
			return feedBack(request, type, errorMsgStr);
		}
		
		if (StringUtils.isEmpty(newCashPassword4)) {
			errorMsgStr = "交易密码不能为空.";
			//System.out.println("错误信息===="+errorMsgStr);
			return feedBack(request, type, errorMsgStr);
		}
		
		if (!newCashPassword4.equals(confirmPassword4)) {
			errorMsgStr = "两次输入的密码不一致.";
			//System.out.println("错误信息===="+errorMsgStr);
			return feedBack(request, type, errorMsgStr);
		}
		
		String  passwordMd5 = MD5Utils.getMD5Str(newCashPassword4);
		
		if (passwordMd5.equals(userSecurity.getCashPassword())) {
			errorMsgStr = "修改后的密码不能与原密码一样.";
			//System.out.println("错误信息===="+errorMsgStr);
			return feedBack(request, type, errorMsgStr);
		}
		 
		 
		if(passwordMd5.equals(userSecurity.getPassword())){
			errorMsgStr = "交易密码不能与登陆密码相同.";
			//System.out.println("交易密码不能与登陆密码相同");
			return feedBack(request, type, errorMsgStr);
		}
		
		try {
			userBS.updateUserOfCashPassword(passwordMd5, loginInfo.getUserId());
			userSecurity.setCashPassword(passwordMd5);
			loginInfo.setUserSecurity(userSecurity);
			request.getSession().setAttribute(AppConst.LOGIN_INFO_SESSION, loginInfo);
			
		} catch (BizException e) {
			 
			setCommonData(request);
			errorMsgStr = "交易密码设置失败";
			return feedBack(request, type, errorMsgStr);
		}
		
		setAttribute(request, "sucMes", "交易密码设置成功");
		return feedBack(request, type, errorMsgStr);

	}
	/**
	 * 返回安全信息界面
	 * 
	 * @return
	 */
	private String feedBack(HttpServletRequest request, int type,
			String errorMsgStr) {
		UserSecurity userSecurity = initData();
		setAttribute(request, "userSecurity", userSecurity);
		if (StringUtils.isNotEmpty(errorMsgStr)) {
			SecurityErrorMsg errorMsg = new SecurityErrorMsg();
			errorMsg.setType(type);
			errorMsg.setMsg(errorMsgStr);
			setAttribute(request, "errorMsg", errorMsg);
		}
		// 设置公共数据
		setCommonData(request);
		// 绑定数据
		return getResultPath(MY_SECURITY);
	}


	/**
	 * 检查身份证
	 * 
	 * @return
	 */
	private Msg checkIdCard(String realname, String idcard) {
		if (StringUtils.isEmpty(realname))
			return new Msg(Msg.ERROR_STATUS, "真实姓名不能为空.");

		if (StringUtils.isEmpty(idcard))
			return new Msg(Msg.ERROR_STATUS, "身份证号不能为空.");

		try {
			
			//判断身份证是否已经认证过了
			int count = 0;
			boolean success = false; 
			
//			count = userBS.getUserCountByIdCard(idcard);
			count = custInfoBS.getCustCountByIdCard(idcard);
			//System.out.println("count ===="+count);
			if(count > 0){
				return new Msg(Msg.ERROR_STATUS, "此身份证已认证过了！");
			}
			
			/*CreditDetailVO creditDetailVO = authenticationBS.personalAuth(
					realname, idcard);
			if (null != creditDetailVO) {
				String result = creditDetailVO.getResult();
				if (!StringUtils.isEmpty(result) && "1".equals(result)) {
					return Msg.SUCCESS_MSG;
				}
			}*/
			success = authenticationBS.personalValidator(realname, idcard);
			if(success){
				return Msg.SUCCESS_MSG;
			}
		} catch (BizException e) {
			 
			return new Msg(Msg.ERROR_STATUS, "真实姓名或者身份证号输入错误.");
		}
		return new Msg(Msg.ERROR_STATUS, "真实姓名或者身份证号输入错误.");
	}
	
	/**
	 * 验证手机号码是否存在
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
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 验证原密码是否正确
	 * @return
	 */
	private Msg checkOldPassword(String oldPassword){
		if(StringUtils.isEmpty(oldPassword))
			return new Msg(Msg.ERROR_STATUS, "原密码不能为空.");
		
		SysUser sysUser = getLoginSysUser();
		if (null != sysUser) {
			String tmpPassword = MD5Utils.getMD5Str(oldPassword);
			if (tmpPassword.equals(sysUser.getPassword())) {
				return Msg.SUCCESS_MSG;
			}else{
				return new Msg(Msg.ERROR_STATUS, "原密码输入错误.");
			}
		}else{
			return new Msg(Msg.ERROR_STATUS, "未登录.");
		}
	}

	/**
	 * 初始化数据
	 */
	private UserSecurity initData() {
		LoginInfo loginInfo = (LoginInfo) getSession(AppConst.LOGIN_INFO_SESSION);
		if (null != loginInfo) {
			return loginInfo.getUserSecurity();
		}
		return null;
	}

	public IAuthenticationBS getAuthenticationBS() {
		return authenticationBS;
	}

	public void setAuthenticationBS(IAuthenticationBS authenticationBS) {
		this.authenticationBS = authenticationBS;
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

	public IUserInfoSyncBS getUserInfoSyncBS() {
		return userInfoSyncBS;
	}

	public void setUserInfoSyncBS(IUserInfoSyncBS userInfoSyncBS) {
		this.userInfoSyncBS = userInfoSyncBS;
	}

}
