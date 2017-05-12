package com.qfw.dao.custinfo.custAuth;

import java.util.List;

import com.qfw.common.dao.IBaseDAO;
import com.qfw.model.bo.BizAuthBO;
import com.qfw.model.vo.custinfo.custAuth.CustAuthSearchVO;

public interface ICustAuthDAO  extends IBaseDAO{
	
	/**
	 * 根据客户id查询工作信息
	 * @param searchVO
	 * @return
	 */
	public List<BizAuthBO> findBOByParams(CustAuthSearchVO searchVO);
	
}
