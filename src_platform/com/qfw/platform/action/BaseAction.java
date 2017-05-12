package com.qfw.platform.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.qfw.common.util.StringUtils;
import com.qfw.platform.service.IHtmlService;

/**
 * 前台请求基类
 * 
 * @author Administrator
 * 
 */
public class BaseAction {

	public static final String INDEX = "index";
	public static final String MOBILE_INDEX = "mobileIndex"; //移动端首页
	public static final String LOGIN_INDEX = "loginIndex";
	public static final String MOBILE_LOGIN_INDEX = "mobileLoginIndex";
	public static final String FIND_PWD = "findPwd";
	public static final String FIND_PWD_BY_MOBILE = "findPwdByMobile";
	public static final String EDIT_PWD_BY_MOBILE = "editPwdByMobile";
	
	public static final String USER_INDEX = "userIndex";
	public static final String MOBILE_USER_INDEX = "mobileUserIndex";//移动端用户中心
	
	
	// 投资相关
	public static final String LOAN_INDEX = "loanIndex";
	public static final String MOBILE_LOAN_INFO = "mobileLoanInfo";//移动端借款协议
	public static final String LOAN_LIST = "loanList";
	public static final String MOBILE_LOAN_LIST = "mobileLoanList";//移动端投资列表
	public static final String LOAN_DETAIL = "loanDetail";// 未满标投资详情
	public static final String MOBILE_LOAN_DETAIL = "mobileLoanDetail";// 移动端未满标投资详情
	public static final String LOAN_DETAIL_NO = "loanDetailNo";// 满标投资详情
	public static final String MOBILE_INFO_DISCLOSURE = "mobileInfoDisclosure";// 满标投资详情-信息披露
	public static final String MOBILE_LOAN_DETAIL_NO = "mobileLoanDetailNo";// 移动端满标投资详情
	public static final String LOAN_DETAIL_NOLOGIN = "loanDetailNoLogin";// 未登陆投资详情
	public static final String MOBILE_BID_RECORD = "mobileBidRecord";// 投标记录
	
	// 债权转让相关
	public static final String Transfer_LIST = "transferList";
	public static final String Transfer_DETAIL = "transferDetail";        //未满标债权转让
	public static final String Transfer_NOLOGIN = "transferDetailNoLogin";// 未登陆债权转让
    public static final String Transfer_DETAIL_NO = "transferDetailNo";//满标债权投资转让
	
	public static final String ERROR_PAGE = "/common/error_page";

	public static final String Go_To_Loan = "GoToLoan";// 我要借款
	public static final String MOBILE_GO_TO_LOAN = "mobileGoToLoan";// 移动端我要借款
	public static final String Go_To_Loan_Credit = "GoToLoanCredit";// 我要借款
	public static final String PRODUCT_HTML = "product";// 我要借款>产品页
	
	public static final String LOGIN_HTML = "LoginHtml";// 我要登陆页面
	
    public static final String PM_CLASSROOM = "pmClassroom";//平台课堂
	
	@Resource(name = "htmlService")
	private IHtmlService htmlService;
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
	 * 获取返回的模板地址
	 * 
	 * @param ftlFileName
	 * @return
	 */
	public String getResultPath(String ftlFileName) {
		return "/platform/" + ftlFileName;    
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
	 * 设置属性值Map
	 * 
	 * @param request
	 * @param dataMap
	 */
	public void setAttributes(HttpServletRequest request,
			Map<String, Object> dataMap) {
		if (null == dataMap || dataMap.isEmpty())
			return;

		for (final String key : dataMap.keySet()) {
			setAttribute(request, key, dataMap.get(key));
		}
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
}
