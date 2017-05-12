package com.qfw.dao.credit;

import java.util.List;

import com.qfw.common.dao.IBaseDAO;
import com.qfw.model.bo.BizCreditUseBO;

public interface ICreditUseDAO extends IBaseDAO{

	/**
	 * 根据额度id查询额度动用表
	 * @param clId
	 * @return
	 */
	public List<BizCreditUseBO> findByClId(String clId);
	
}
