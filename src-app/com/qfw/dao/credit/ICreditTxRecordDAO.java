package com.qfw.dao.credit;

import java.util.List;

import com.qfw.common.dao.IBaseDAO;
import com.qfw.model.bo.BizCreditTxRecordBO;

public interface ICreditTxRecordDAO extends IBaseDAO{

	/**
	 * 根据txno查询额度交易信息
	 * @param txNo
	 * @return
	 */
	public List<BizCreditTxRecordBO> queryListByTxNo(String txNo,String state);
}
