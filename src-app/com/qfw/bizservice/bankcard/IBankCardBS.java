package com.qfw.bizservice.bankcard;

import java.util.List;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.model.bo.BizCardBO;
import com.qfw.model.vo.bankcard.BankCardVO;

/**
 * 银行卡bs
 *
 * @author kyc
 */
public interface IBankCardBS extends IBaseService {
	
	/**
	 * 根据参数查询提现信息
	 * @param vo
	 * @return
	 */
	public  List<BizCardBO> findByParams(BankCardVO vo) ;
	
	public void deleteCard(Integer cardId) throws BizException;

	public boolean validate(String accountNum);
	
}
