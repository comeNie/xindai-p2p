package com.qfw.dao.bankcard;

import java.io.Serializable;
import java.util.List;

import com.qfw.common.dao.IBaseDAO;
import com.qfw.model.bo.BizCardBO;
import com.qfw.model.vo.bankcard.BankCardVO;

public interface IBankCardDAO extends IBaseDAO {

	/**
	 * 根据参数查询提现信息
	 * @param vo
	 * @return
	 */
	public  List<BizCardBO> findByParams(BankCardVO vo) ;

	public boolean validate(String accountNum);
	
	public void deleteById(Serializable id);
	
}
