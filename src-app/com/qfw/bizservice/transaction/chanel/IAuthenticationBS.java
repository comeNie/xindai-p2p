package com.qfw.bizservice.transaction.chanel;

import com.qfw.common.exception.BizException;
import com.qfw.model.vo.transaction.CreditDetailVO;

/**
 * 认证接口服务
 * @author Jie
 *
 */
public interface IAuthenticationBS {


	/**
	 * 个人认证接口
	 * @param name 姓名
	 * @param documentNo 身份证号码
	 * @return
	 * @throws BizException
	 */
	public CreditDetailVO personalAuth(String name,String documentNo) throws BizException;
	
	public boolean personalValidator(String name, String documentNo) throws BizException;

}
