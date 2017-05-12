package com.qfw.platform.service;

import com.qfw.common.exception.BizException;
import com.qfw.platform.model.loan.CreditorTranDetailVO;

/**
 * 债权转让service
 * @author Teddy
 *
 */
public interface ICreditorTranService {

	/**
	 * 标的详情
	 * @throws BizException
	 */
	public CreditorTranDetailVO getCreditorTranDetail(Integer creditorTranId) throws BizException;
	
}
