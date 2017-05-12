package com.qfw.service.impl;

import org.springframework.stereotype.Service;

import com.qfw.common.exception.BizException;
import com.qfw.model.CustomerRequest;
import com.qfw.model.CustomerResponse;
import com.qfw.service.ICustomerService;

@Service("customerService")
public class CustomerServiceImpl implements ICustomerService {

	@Override
	public CustomerResponse customerAdd(CustomerRequest customerRequest)
			throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerResponse customerList(CustomerRequest customerRequest)
			throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerResponse customerTradeList(CustomerRequest customerRequest)
			throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerResponse login(CustomerRequest customerRequest)
			throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerResponse logout(CustomerRequest customerRequest)
			throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerResponse modifyPwd(CustomerRequest customerRequest)
			throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

}
