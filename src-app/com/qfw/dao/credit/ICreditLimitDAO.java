package com.qfw.dao.credit;

import java.util.List;

import com.qfw.common.dao.IBaseDAO;
import com.qfw.model.bo.BizCreditLimitBO;
import com.qfw.model.vo.credit.CreditQueryVO;

public interface ICreditLimitDAO extends IBaseDAO{

	/**
	 * 获取额度主表信息列表
	 * @param vo
	 * @return
	 */
	public List<BizCreditLimitBO> queryListByParams(CreditQueryVO vo);
	
}
