package com.qfw.manager.action;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.bizservice.friend.IFriendManageBS;
import com.qfw.bizservice.payout.IWithdrawalApplyBS;
import com.qfw.bizservice.payout.IWithdrawalsPayoutBS;
import com.qfw.common.bizservice.permission.IUserBS;
import com.qfw.common.util.MobileUtil;
import com.qfw.manager.model.FinAccountVo;
import com.qfw.manager.model.UserIndexVO;
import com.qfw.manager.service.IUserIndexService;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.bo.BizFriendManageBO;
import com.qfw.platform.model.json.Pager;
import com.qfw.platform.model.loan.CreditReportDetailVO;
import com.qfw.platform.model.vo.LoginInfo;
import com.qfw.platform.service.ILoanService;

/**
 * 会员中心-我的平台
 * @author Teddy
 */
@Controller
@RequestMapping("/userIndex")
public class UserIndexAction extends BaseAction {

	@Resource(name = "userIndexService")
	private IUserIndexService userIndexService;// 用户中心首页BS
	@Resource(name = "loanService")
	private ILoanService loanService;
	
	@Resource(name = "friendBS")
	private IFriendManageBS friendBS;
	
	@Resource(name = "custInfoBS")
	private ICustInfoBS custInfoBS;
	
	@Resource(name = "userBS")
	private IUserBS userBS;
	
	@Resource(name = "withdrawalsPayoutBS")
	private IWithdrawalsPayoutBS withdrawalsPayoutBS;
	/**
	 * 我的首页
	 */
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		// 设置公共数据
		try {
			setCommonData(request);
			UserIndexVO userIndexVO = new UserIndexVO();// 
			FinAccountVo finAccount = new FinAccountVo();
			CreditReportDetailVO creditReportDetailVO = new CreditReportDetailVO();// 借款账户信息
			  String secLevel = "" ;//安全等级
			  int progress = 0;//进度
			  String isIdCard = "false";   //默认图标为暗色
			  String isMobile = "false";
			  String isEmail = "false";
			  String isCashPassword = "false";
			LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION) ;
			if (loginInfo != null ) {
				String idCard = loginInfo.getUserSecurity().getIdCard();
				String mobile = loginInfo.getUserSecurity().getMobile();
				String email = loginInfo.getUserSecurity().getEmail();
				String cashPassword = loginInfo.getUserSecurity().getCashPassword();
//				//System.out.println("idCard=="+idCard);
//				//System.out.println("email=="+email);
//				//System.out.println("cashPassword=="+cashPassword);
//				//System.out.println("mobile=="+mobile);
				int count = 0;
				//
				if(StringUtils.isNotEmpty(idCard)){
					count++;
					isIdCard = "true";
				}
				//
				if(StringUtils.isNotEmpty(mobile)){
					count++;
					isMobile = "true";
				}
				
				if(StringUtils.isNotEmpty(email)){
					count++;
					isEmail = "true";
				}
				//
				if(StringUtils.isNotEmpty(cashPassword)){
					count++;
					isCashPassword ="true";
				}
				progress = count*100/4;
				if(progress <= 50){
					secLevel = "低";
				}
				if(progress>50 && progress<=85){
					secLevel = "中";
				}
				if(progress > 85){
					secLevel = "高";
				}
				setAttribute(request, "isIdCard",isIdCard);
				setAttribute(request, "isMobile", isMobile);
				setAttribute(request, "isEmail", isEmail);
				setAttribute(request, "isCashPassword", isCashPassword);
				setAttribute(request, "secLevel", secLevel);
				setAttribute(request, "progress", progress);
//				try {
//					withdrawalsPayoutBS.withdrawalOrderHandle(loginInfo.getCustId());
//				} catch (Exception e) {
//					// TODO: handle exception
//				}
				
				userIndexVO = this.userIndexService.getUserIndexVO(loginInfo.getCustId());// 我的账户
				creditReportDetailVO = this.loanService.getCreditReport(loginInfo.getCustId());// 借款账户信息
				finAccount = this.loanService.getFinAccount(loginInfo.getCustId());// 理财账户列表
				//add by yangjj
				BizCustomerBO cust = custInfoBS.findCustById(loginInfo.getCustId());
				if(cust != null){
					userIndexVO.setIsVip(cust.getIsVip());
				}
//				int isAdmin = userBS.getIsAdminCount(loginInfo.getUserCode());
				// 绑定数据
				setAttribute(request, "userIndexVO", userIndexVO);
				setAttribute(request, "fundAccountVo", finAccount);
				setAttribute(request, "creditReportDetailVO", creditReportDetailVO);
				//setAttribute(request,"myVid","userinfohead1");
				/*String myVid = request.getParameter("myVid");
				setAttribute(request,"myVid",myVid);
				if(isAdmin > 0){
					setAttribute(request,"isViewReferee",true);
				}*/
				
			}else{
				setCommonData(request);
				
				return getResultPath(INDEX);
			}
		} catch (Exception e) {
			 
		}
		boolean isMobile = MobileUtil.JudgeIsMoblie(request);
		if(isMobile){
			return getResultPath(MOBILE_USER_INDEX);
		}
		return getResultPath(INDEX);
		
	}
	
	/**
	 * 好友管理
	 */
	@RequestMapping(value = "/myFriend")
	public String myFriend(HttpServletRequest request, HttpServletResponse response) {
		// 设置公共数据
		setCommonData(request);
		//setAttribute(request,"myVid","userinfohead1");
		String myVid = request.getParameter("myVid");
		setAttribute(request,"myVid",myVid);
		// 绑定数据
		return getResultPath(MY_FRIEND);
	}

	/**
	 * 安全信息
	 */
	@RequestMapping(value = "/mySecurity")
	public String mySecurity(HttpServletRequest request, HttpServletResponse response) {
		// 设置公共数据
		setCommonData(request);
		setAttribute(request, "test", 500);
		// 绑定数据
		return getResultPath(MY_SECURITY);
	}

	/**
	 *  ajax 查询好友列表
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxQuerymyFriend")
	public Pager ajaxQuerymyFriend(
			@RequestParam("friendName") String friendName,
			@RequestParam("requestPage") int requestPage,
			@RequestParam("pageSize") int pageSize, HttpSession session) {
		Pager pager = null;
		try {
			if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null ) {
				LoginInfo loginInfo = (LoginInfo) session.getAttribute(AppConst.LOGIN_INFO_SESSION) ;
				
				return this.friendBS.findInfoPages(loginInfo.getUserId(),friendName,requestPage, pageSize);
			}
		} catch (Exception e) {
			 
		}
		return pager;
	}
	
	/**
	 *  ajax 查询陌生人列表
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxQuerymyStranger")
	public Pager ajaxQuerymyStranger(
			@RequestParam("friendName") String friendName,
			@RequestParam("requestPage") int requestPage,
			@RequestParam("pageSize") int pageSize, HttpSession session) {
		Pager pager = null;
		try {
			if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null ) {
				LoginInfo loginInfo = (LoginInfo) session.getAttribute(AppConst.LOGIN_INFO_SESSION) ;
				
				return this.friendBS.findStrangerPages(loginInfo.getUserId(),friendName,requestPage, pageSize);
			}
		} catch (Exception e) {
			 
		}
		return pager;
	}
	
	
	/**
	 *  ajax 关注好友
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxAddFriend")
	public Map<String, String> ajaxAddFriend(
			@RequestParam("friendId") int friendId, HttpSession session) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null ) {
				LoginInfo loginInfo = (LoginInfo) session.getAttribute(AppConst.LOGIN_INFO_SESSION) ;
				BizFriendManageBO bo = new BizFriendManageBO();
				
				bo.setUserId(loginInfo.getUserId());
				bo.setFriendId(friendId);				
				bo.setUserId(loginInfo.getUserId());
				bo.setSysCreateTime(new Timestamp(new Date()
						.getTime()));
				bo.setSysCreateUser(loginInfo.getUserId());
				bo.setSysUpdateTime(new Timestamp(new Date()
						.getTime()));
				bo.setSysUpdateUser(loginInfo.getUserId());
				
				this.friendBS.save(bo);
				
				jsonMap.put("reMes", "关注成功");
				jsonMap.put("status", "1");
			}
		} catch (Exception e) {
			 
			jsonMap.put("reMes", "关注失败，请重试");
			jsonMap.put("status", "0");
		}
		return jsonMap;
	}
	
	/**
	 *  ajax 取消关注好友
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxCancelFriend")
	public Map<String, String> ajaxCancelFriend(
			@RequestParam("friendId") int friendId, HttpSession session) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null ) {
				LoginInfo loginInfo = (LoginInfo) session.getAttribute(AppConst.LOGIN_INFO_SESSION) ;
				BizFriendManageBO bo = (BizFriendManageBO)friendBS.find(BizFriendManageBO.class, friendId);
				
				this.friendBS.delete(bo);
				
				jsonMap.put("reMes", "取消关注成功");
				jsonMap.put("status", "1");
			}
		} catch (Exception e) {
			 
			jsonMap.put("reMes", "取消关注失败，请重试");
			jsonMap.put("status", "0");
		}
		return jsonMap;
	}
	
	/**
	 * 会员服务
	 */
	@RequestMapping(value = "/myService")
	public String myService(HttpServletRequest request, HttpServletResponse response) {
		// 设置公共数据
		setCommonData(request);
		setAttribute(request, "test", 500);
		// 绑定数据
		return getResultPath(MY_SERVICE);
	}

	public ICustInfoBS getCustInfoBS() {
		return custInfoBS;
	}

	public void setCustInfoBS(ICustInfoBS custInfoBS) {
		this.custInfoBS = custInfoBS;
	}

	public IUserBS getUserBS() {
		return userBS;
	}

	public void setUserBS(IUserBS userBS) {
		this.userBS = userBS;
	}
	 
	
}