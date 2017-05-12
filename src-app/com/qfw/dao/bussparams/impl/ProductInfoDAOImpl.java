package com.qfw.dao.bussparams.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.qfw.common.dao.ICommonQuery;
import com.qfw.common.dao.impl.BaseDAOImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.util.StringUtils;
import com.qfw.dao.bussparams.IProductInfoDAO;
import com.qfw.model.vo.bussparams.ProductInfoVO;
import com.qfw.model.vo.bussparams.SearchProductInfoVO;

@Repository("productInfoDAO")
public class ProductInfoDAOImpl extends BaseDAOImpl implements IProductInfoDAO {
	
	@Autowired
	private ICommonQuery commonQuery;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductInfoVO> findProductInfo(SearchProductInfoVO searchProductInfoVO) throws BizException {
		StringBuffer sb = new StringBuffer();
		sb.append("select s.id as id,s.PRODUCT_NUM as product_num,s.PRODUCT_NAME as product_name,");
		sb.append(" s.PRODUCT_DESC as product_desc,s.LEAST_RATE_YEAR as least_rate_year,");
		sb.append(" s.MOST_RATE_YEAR as most_rate_year,s.PRODUCT_IMG_URL as product_img_url");
		sb.append(" from BIZ_PRODUCT s");
		sb.append(" where 1=1");
		if (StringUtils.isNotEmpty(searchProductInfoVO.getProductNum())) {
			sb.append(" and s.PRODUCT_NUM like '%").append(searchProductInfoVO.getProductNum()).append("%'");
		}
		if (StringUtils.isNotEmpty(searchProductInfoVO.getProductNum())) {
			sb.append(" and s.PRODUCT_NAME like '%").append(searchProductInfoVO.getProductName()).append("%'");
		}
		sb.append(" order by s.PRODUCT_NUM");
		List<ProductInfoVO> productInfoList = this.commonQuery.findObjects(sb.toString(),ProductInfoVO.class);
		return productInfoList;
	}
	
}
