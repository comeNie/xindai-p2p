package com.qfw.dao.bussparams;

import java.util.List;

import com.qfw.common.dao.IBaseDAO;
import com.qfw.common.exception.BizException;
import com.qfw.model.vo.bussparams.ProductInfoVO;
import com.qfw.model.vo.bussparams.SearchProductInfoVO;

public interface IProductInfoDAO extends IBaseDAO{
	
	/**
	 * 查询产品相关信息
	 * @param searchProductInfoVO
	 * @return
	 * @throws BizException
	 */
	public List<ProductInfoVO> findProductInfo(SearchProductInfoVO searchProductInfoVO) throws BizException;
 
}
