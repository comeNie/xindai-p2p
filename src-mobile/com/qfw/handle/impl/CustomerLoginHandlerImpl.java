package com.qfw.handle.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.qfw.common.util.StringUtils;
import com.qfw.handle.IHandler;
import com.qfw.model.AppConst;
import com.qfw.model.CustomerRequest;
import com.qfw.model.CustomerResponse;
import com.qfw.model.HandlerResponse;
import com.qfw.service.ICustomerService;

/**
 * 客户登录
 */
@Service("customerLogin")
public class CustomerLoginHandlerImpl implements IHandler {
	
	@Resource(name = "customerService")
	private ICustomerService customerService;

	@Override
	public void doHandler(HttpServletRequest request,
			HttpServletResponse response, HandlerResponse handlerResponse) 
			throws Exception {
		String customerUserName = request.getParameter("customerUserName");// 客户账号
		String customerPassword = request.getParameter("customerPassword");// 客户密码
//		customerRequest.getMobile, email, custCode, password, refereeName // 参数
		
		if (StringUtils.isEmpty(customerUserName) || StringUtils.isEmpty(customerPassword)) { // 用户名或密码为空
			handlerResponse.setResponseCode(AppConst.USER_ERROR);
			handlerResponse.setResponseMessage("用户名或密码为空.");
			return;
		}
		
		CustomerRequest customerRequest = new CustomerRequest();
		customerRequest.setCustomerUserName(customerUserName);
		customerRequest.setCustomerPassword(customerPassword);
		
		CustomerResponse customerResponse = customerService.login(customerRequest);
		handlerResponse.setResponseCode(customerResponse.getResponseCode());
		handlerResponse.setResponseMessage(customerResponse.getResponseMessage());// 登录成功则直接进行页面跳转，无需提示
//		handlerResponse.setResponseObj(customerResponse.getCustomerBO());
		handlerResponse.setResponseObj(customerResponse);
	}
	
}
