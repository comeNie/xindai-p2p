package com.qfw.manager.service;

import com.qfw.common.exception.BizException;
import com.qfw.manager.model.UserIndexVO;

/**
 * 会员中心 - 首页service
 * @author Teddy
 */
public interface IUserIndexService {

	public UserIndexVO getUserIndexVO(Integer custId) throws BizException;
	
}
