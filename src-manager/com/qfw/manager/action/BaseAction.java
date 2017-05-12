package com.qfw.manager.action;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.qfw.common.bizservice.permission.IUserBS;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.StringUtils;
import com.qfw.model.AppConst;
import com.qfw.platform.model.vo.LoginInfo;
import com.qfw.platform.service.IHtmlService;

/**
 * 管理中心请求基类
 * 
 * @author Administrator
 * 
 */
public class BaseAction {

	public static final String LIST = "list";
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	public static final String MESSAGE = "message";
	public static final String CONTENT = "content";
	public static final String LOGIN_INDEX = "loginIndex";
	// 我的平台 start
	public static final String INDEX = "index/index";// 我的首页
	public static final String MOBILE_USER_INDEX = "index/mobileUserIndex";//移动端用户中心
	public static final String MY_FRIEND = "index/myFriend";// 好友管理
	public static final String MY_SECURITY = "index/mySecurity";// 安全信息
	public static final String MOBILE_MY_SECURITY = "index/mobileMySecurity";// 移动端 安全信息
	public static final String MY_SERVICE = "index/myService";// 会员服务
	// 我的平台 end
	// 资金管理 start
	public static final String MY_RECHARGE = "fund/myRecharge";// 我要充值
	public static final String MOBILE_MY_RECHARGE = "fund/mobileMyRecharge";// 移动端我要充值
	public static final String MY_DRAWAL = "fund/myDrawal";// 我要提现
	public static final String MY_PAYMENT = "fund/myPayment";// 交易流水
	public static final String MOBILE_MY_PAYMENT = "fund/mobileMyPayment";// 移动端交易流水
	public static final String MY_BANKCARD = "fund/myBankcard";// 我的银行卡
	public static final String ADD_BANKCARD = "fund/addBankcard";// 我的银行卡
	public static final String MY_PAYSUBMIT = "fund/paySubmit";// 付款
	public static final String MY_TLPAYSUBMIT = "fund/tlPaySubmit";// 通联付款
	// 资金管理 end
	// 理财管理 start
	public static final String MY_CREDITOR = "financial/myCreditor";// 我的投资
	public static final String MOBILE_MY_CREDITOR="financial/mobileMyCreditor";//移动端 我的投资
	public static final String CREDITOR_PROTOCOL = "financial/creditorProtocol";// 投资协议
	public static final String MY_CREDITOR_TRAN = "financial/myCreditorTran";// 债权转让列表
	public static final String CREDITOR_TRAN = "financial/creditorTran";// 债权转让
	public static final String MY_AUTO_TENDER = "financial/myAutoTender";// 自动投标
	// 理财管理 end
	// 借款管理 start
	public static final String MY_LOAN = "loan/myLoan";// 我的借款
	public static final String MOBILE_MY_LOAN = "loan/mobileMyLoan";// 移动端 我的借款
	public static final String MY_LOAN_INT = "loan/myLoanInt";// 我的借款意向
	public static final String MOBILE_MY_LOAN_INT = "loan/mobileMyLoanInt";// 移动端  我的借款意向
	public static final String MY_REPAY = "loan/myRepay";// 还款管理
	public static final String MOBILE_MY_REPAY = "loan/mobileMyRepay";// 移动端 还款管理
	public static final String MY_REPAY_DH = "loan/myRepayDh";// 还款管理
	public static final String MY_REPAY_DETAIL = "loan/myRepayDetail";// 还款
	public static final String MY_REPAY_DETAIL_DH = "loan/myRepayDetailDh";// 还款
	public static final String MY_PRE_REPAY = "loan/myPreRepay";// 提前还款
	public static final String MY_CREDIT_LIMIT = "loan/myCreditLimit";// 我的授信
	// 借款管理 end
	// 消息设置 start 
	public static final String NOTE_SET = "message/noteSet";// 通知设置
	public static final String MESSAGE_MANAGE = "message/messageManage";// 我要推广
	public static final String RECOMMEND_PROTOCOL = "message/recommendProtocol";
	public static final String RECOMMEN_INFO = "message/recommendInfo";
	public static final String MOBILE_PROMOTE = "message/mobilePromote";//移动端我的推广
	public static final String RECOMMENDED_DETAIL = "message/recommendedDetail";// 推荐明细
	//帮助设置
	public static final String HELP = "help/help";
	public static final String MCJS = "help/mcjs"; //名词解释
	public static final String PTJS = "help/ptjs";  //平台介绍
	public static final String WYJK = "help/wyjk";  //我要借款
	public static final String WYLC = "help/wylc";  //我要理财
	public static final String LXHFY = "help/lxhfy"; //利息和费用
	public static final String ZXZH = "help/zxzh";   //在线账户
	
	//关于我们start 

	public static final String ABOUT_US = "aboutus/aboutUs";// 公司简介
	public static final String SJCT_VIP = "aboutus/sjctVip";// 世纪创投vip
	public static final String MOBILE_ABOUT_US = "aboutus/mobileAboutUs";// 了解我们
	public static final String MOBILE_NOTICE = "aboutus/mobileNotice";// 移动端公告
	public static final String MOBILE_RECHARGE_SUCCESS = "aboutus/mobileRechargeSuccess";// 充值成功
	public static final String ABOUT_ZZ = "aboutus/aboutZz"; // 公司资质
	public static final String ABOUT_US_ANNOUNCEMENT = "aboutus/aboutUsAnnouncement";// 网站公告 
	public static final String ABOUT_US_ANNOUNCEMENT_DETAIL_1 = "aboutus/aboutUsAnnouncementDetail_1";// 网站公告详情
	public static final String ABOUT_US_ANNOUNCEMENT_DETAIL_2 = "aboutus/aboutUsAnnouncementDetail_2";// 网站公告详情
	public static final String ABOUT_US_ANNOUNCEMENT_DETAIL_3 = "aboutus/aboutUsAnnouncementDetail_3";// 网站公告详情
	public static final String ABOUT_US_ANNOUNCEMENT_DETAIL_4 = "aboutus/aboutUsAnnouncementDetail_4";// 网站公告详情
	public static final String ABOUT_US_ANNOUNCEMENT_DETAIL_5 = "aboutus/aboutUsAnnouncementDetail_5";// 网站公告详情
	public static final String ABOUT_US_ANNOUNCEMENT_DETAIL_6 = "aboutus/aboutUsAnnouncementDetail_6";// 网站公告详情
	public static final String ABOUT_US_ANNOUNCEMENT_DETAIL_7 = "aboutus/aboutUsAnnouncementDetail_7";// 网站公告详情
	public static final String ABOUT_US_ANNOUNCEMENT_DETAIL_8 = "aboutus/aboutUsAnnouncementDetail_8";// 网站公告详情
	public static final String ABOUT_US_ANNOUNCEMENT_DETAIL_9 = "aboutus/aboutUsAnnouncementDetail_9";// 网站公告详情
	public static final String ABOUT_US_ANNOUNCEMENT_DETAIL_10 = "aboutus/aboutUsAnnouncementDetail_10";// 网站公告详情
	public static final String ABOUT_US_ANNOUNCEMENT_DETAIL_11 = "aboutus/aboutUsAnnouncementDetail_11";// 网站公告详情
	public static final String ABOUT_US_ANNOUNCEMENT_DETAIL_12 = "aboutus/aboutUsAnnouncementDetail_12";// 网站公告详情
	public static final String ABOUT_US_ANNOUNCEMENT_DETAIL_14 = "aboutus/aboutUsAnnouncementDetail_14";// 网站公告详情
	public static final String ABOUT_US_MANAGER = "aboutus/aboutUsManager";// 网站公告详情
	public static final String ABOUT_US_INFO = "aboutus/aboutUsInfo";// 信息披露
	public static final String ABOUT_US_INFO_DETAIL_1 = "aboutus/aboutUsInfoDetail_1";// 信息披露详情
	public static final String ABOUT_US_INFO_DETAIL_2 = "aboutus/aboutUsInfoDetail_2";// 信息披露详情
	public static final String ABOUT_US_INFO_DETAIL_3 = "aboutus/aboutUsInfoDetail_3";// 信息披露详情
	public static final String ABOUT_US_INFO_DETAIL_4 = "aboutus/aboutUsInfoDetail_4";// 信息披露详情
	public static final String ABOUT_US_INFO_DETAIL_5 = "aboutus/aboutUsInfoDetail_5";// 信息披露详情
	public static final String ABOUT_US_INFO_DETAIL_6 = "aboutus/aboutUsInfoDetail_6";// 信息披露详情
	public static final String ABOUT_US_INFO_DETAIL_7 = "aboutus/aboutUsInfoDetail_7";// 信息披露详情
	public static final String ABOUT_US_INFO_DETAIL_8 = "aboutus/aboutUsInfoDetail_8";// 信息披露详情
	public static final String ABOUT_US_INFO_DETAIL_9 = "aboutus/aboutUsInfoDetail_9";// 信息披露详情
	public static final String ABOUT_US_PARTNER = "aboutus/aboutUsPartner";// 合作伙伴
	public static final String ABOUT_US_NETBUILD = "aboutus/aboutUsNetBuild";// 网点建设
	public static final String ABOUT_US_NETBUILD_DETAIL_1 = "aboutus/aboutUsNetBuildDetail_1";// 网点建设披露详情
	public static final String ABOUT_US_NETBUILD_DETAIL_2 = "aboutus/aboutUsNetBuildDetail_2";// 网点建设披露详情
	public static final String ABOUT_US_NETBUILD_DETAIL_3 = "aboutus/aboutUsNetBuildDetail_3";// 网点建设披露详情
	public static final String ABOUT_US_CAREERS = "aboutus/aboutUsCareers";// 招贤纳士
	public static final String ABOUT_US_CONTACT = "aboutus/aboutUsContact";// 联系我们 
	public static final String ABOUT_US_NEWS = "aboutus/aboutUsNews";// 网站动态 
	public static final String ABOUT_US_NEWS_DETAIL_01 = "aboutus/aboutUsNewsDetail_01";// 网站动态明细 1
	//关于我们end

	// 消息设置 end
	// 经纪人模式 start
	public static final String MY_RECOMMEND = "agent/myRecommend";// 我的授信
	public static final String MY_AGENT = "agent/myAgent";// 我的经纪人
	public static final String MY_INCOME = "agent/myIncome";// 我的收益
	// 经济人模式 end

	@Resource(name = "htmlService")
	private IHtmlService htmlService;

	@Resource(name = "userBS")
	private IUserBS userBS;

	/**
	 * 获取Attribute
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public Object getAttribute(HttpServletRequest request, String name) {
		return request.getAttribute(name);
	}

	/**
	 * 设置Attribute
	 * 
	 * @param request
	 * @param name
	 * @param value
	 */
	public void setAttribute(HttpServletRequest request, String name,
			Object value) {
		request.setAttribute(name, value);
	}

	/**
	 * 获取返回的模板地址
	 * 
	 * @param ftlFileName
	 * @return
	 */
	public String getResultPath(String ftlFileName) {
		return "/user/" + ftlFileName;
	}

	/**
	 * 设置公共数据
	 * 
	 * @param request
	 */
	public void setCommonData(HttpServletRequest request) {
		Map<String, Object> dataMap = htmlService.getCommonData();
		if (null == dataMap || dataMap.isEmpty())
			return;

		for (final String key : dataMap.keySet()) {
			request.setAttribute(key, dataMap.get(key));
		}
	}

	/**
	 * 获取Session
	 * 
	 * @param name
	 * @return
	 */
	public Object getSession(String name) {
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession();
		return session.getAttribute(name);
	}

	/**
	 * 获取登陆的用户
	 * 
	 * @return
	 */
	public SysUser getLoginSysUser() {
		LoginInfo loginInfo = (LoginInfo) getSession(AppConst.LOGIN_INFO_SESSION);
		if (null == loginInfo)
			return null;
		SysUser sysUser = userBS.findUserById(loginInfo.getUserId());
		return sysUser;
	}

	/**
	 * 获取成功反馈的json
	 * 
	 * @return
	 */
	public Map<String, String> getSuccessJson(String successMsg) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		if (StringUtils.isEmpty(successMsg)) {
			jsonMap.put("ok", "");
		} else {
			jsonMap.put("ok", successMsg);
		}
		return jsonMap;
	}

	/**
	 * 获取错误的json
	 * 
	 * @return
	 */
	public Map<String, String> getErrorJson(String errMsg) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		if (StringUtils.isEmpty(errMsg)) {
			jsonMap.put("error", "");
		} else {
			jsonMap.put("error", errMsg);
		}
		return jsonMap;
	}
	
	/**
	 * 获取返回的模板地址,如果没有安全登记，则返回“安全信息”的模板地址
	 * @param ftlFileName
	 * @param request
	 * @return
	 */
	public String getResultPathBySecurity(String ftlFileName,HttpServletRequest request){
		String resultPath =  "/user/" + ftlFileName;
		if (request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION) != null ) {
			LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION) ;
			String certificateNum = loginInfo.getCertificateNum()==null?"":loginInfo.getCertificateNum();
			String custName = loginInfo.getCustName()==null?"":loginInfo.getCustName();
			if( "".equals(certificateNum) || "".equals(custName)){
				String errMes = "";
				try{
					errMes = "您未进行身份认证，请先进行身份认证！";
					errMes = URLEncoder.encode(errMes, "UTF-8");
				}catch(Exception ex){
					ex.printStackTrace();
				}
				resultPath =  "redirect:/userSecurity/index.do?errMes=" + errMes;
				
			}
		}
		return resultPath;
	}
}
