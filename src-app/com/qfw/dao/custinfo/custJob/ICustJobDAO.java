package com.qfw.dao.custinfo.custJob;

import java.util.List;

import com.qfw.common.dao.IBaseDAO;
import com.qfw.model.bo.BizJobBO;
import com.qfw.model.vo.custinfo.custJob.CustJobSearchVO;

public interface ICustJobDAO extends IBaseDAO {
	
	/**
	 * 根据客户id查询工作信息
	 * @param searchVO
	 * @return
	 */
	public List<BizJobBO> findBOByParams(CustJobSearchVO searchVO);
	
}
