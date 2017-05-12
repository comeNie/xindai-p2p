package com.qfw.bizservice.custinfo.custAuth;

import java.util.List;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.model.bo.BizAuthBO;
import com.qfw.model.vo.custinfo.custAuth.CustAuthSearchVO;

public interface ICustAuthBS extends IBaseService{
	
	/**
	 * 根据客户id查询工作信息
	 * @param custId
	 * @return
	 */
	public BizAuthBO findBOByCustID(Integer custId);
	
	/**
	 * 根据查询参数获取list
	 * @param searchVO
	 * @return
	 */
	public List<BizAuthBO> findBOByParams(CustAuthSearchVO searchVO);
	
}
