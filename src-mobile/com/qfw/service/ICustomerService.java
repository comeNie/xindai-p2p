package com.qfw.service;

import com.qfw.common.exception.BizException;
import com.qfw.model.CustomerRequest;
import com.qfw.model.CustomerResponse;

/**
 * 客户管理service
 */
public interface ICustomerService {
	
	/**
	 * 添加客户
	 * @param customerRequest
	 * @return
	 * @throws BizException
	 */
	public CustomerResponse customerAdd(CustomerRequest customerRequest) throws BizException;
	
	/**
	 * 客户列表
	 * @param customerRequest
	 * @return
	 * @throws BizException
	 */
	public CustomerResponse customerList(CustomerRequest customerRequest) throws BizException;
	
	/**
	 * 客户交易流水
	 * @param customerRequest
	 * @return
	 * @throws BizException
	 */
	public CustomerResponse customerTradeList(CustomerRequest customerRequest) throws BizException;
	
	/**
	 * 客户登录
	 * @param customerRequest
	 * @return
	 * @throws BizException
	 */
	public CustomerResponse login(CustomerRequest customerRequest) throws BizException;
	
	/**
	 * 客户安全退出
	 * @param customerRequest
	 * @return
	 * @throws BizException
	 */
	public CustomerResponse logout(CustomerRequest customerRequest) throws BizException;
	
	/**
	 * 客户修改密码
	 * @param customerRequest
	 * @return
	 * @throws BizException
	 */
	public CustomerResponse modifyPwd(CustomerRequest customerRequest) throws BizException;
	
}
