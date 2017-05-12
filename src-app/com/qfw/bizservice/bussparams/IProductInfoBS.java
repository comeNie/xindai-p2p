package com.qfw.bizservice.bussparams;

import java.util.List;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.model.bo.BizProductBO;
import com.qfw.model.vo.bussparams.ProductInfoVO;
import com.qfw.model.vo.bussparams.SearchProductInfoVO;

public interface IProductInfoBS extends IBaseService {

	/**
	 * 查询产品相关信息
	 * @param searchProductInfoVO
	 * @return
	 * @throws BizException
	 */
	public List<ProductInfoVO> findProductInfo (SearchProductInfoVO searchProductInfoVO) throws BizException;
	
	/**
	 * 分页查询产品相关信息
	 * @param searchProductInfoVO
	 * @param first
	 * @param pageSize
	 * @return
	 * @throws BizException
	 */
	public List<BizProductBO> findProductInfoPagesByVO (SearchProductInfoVO searchProductInfoVO, int first, int pageSize);
	
	/**
	 * 查询产品信息笔数
	 * @param searchProductInfoVO
	 * @return
	 * @throws BizException
	 */
	public int findProductInfoCountByVO (SearchProductInfoVO searchProductInfoVO);	
	
	/**
	 * 校验产品编号是否存在
	 * @param ProductNum
	 * @return
	 * @throws BizException
	 */
	public List<BizProductBO> checkProductNum(String productName,Integer id) throws BizException;
	
	
	/**
	 * 校验产品名称是否存在
	 * @param ProductName
	 * @return
	 * @throws BizException
	 */
	public List<BizProductBO> checkProductName(String productName,Integer id)throws BizException;
	
	/**
	 * 根据ID获取查询产品信息
	 * @param id
	 * @return
	 * @throws BizException
	 */
	public BizProductBO getBizProductBOById(Integer id) throws BizException;
	
	public ProductInfoVO findProductInfoById(Integer id) throws BizException;
}
