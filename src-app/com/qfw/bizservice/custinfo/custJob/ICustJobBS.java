package com.qfw.bizservice.custinfo.custJob;

import java.util.List;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.model.bo.BizJobBO;
import com.qfw.model.vo.custinfo.custJob.CustJobSearchVO;

public interface ICustJobBS extends IBaseService {
	
	/**
	 * 根据客户id查询工作信息
	 * @param searchVO
	 * @return
	 */
	public List<BizJobBO> findBOByParams(CustJobSearchVO searchVO);
	
}
