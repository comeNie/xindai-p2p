package com.qfw.dao.credit.report;

import java.util.List;

import com.qfw.common.dao.IBaseDAO;
import com.qfw.model.bo.BizCreditReportBO;

public interface ICreditReportDAO extends IBaseDAO{

	/**
	 * 获取额度主表信息列表
	 * @param vo
	 * @return
	 */
	public List<BizCreditReportBO> queryListByParams(String custID);
	
}
