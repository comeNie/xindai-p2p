package com.qfw.bizservice.bussparams.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qfw.bizservice.bussparams.IProductInfoBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.StringUtils;
import com.qfw.dao.bussparams.IProductInfoDAO;
import com.qfw.model.bo.BizProductBO;
import com.qfw.model.vo.bussparams.ProductInfoVO;
import com.qfw.model.vo.bussparams.SearchProductInfoVO;

@Service("productInfoBS")
public class ProductInfoBSImpl extends BaseServiceImpl implements IProductInfoBS {
	
	@Autowired
	private IProductInfoDAO productInfoDAO;
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public List<ProductInfoVO> findProductInfo (SearchProductInfoVO searchProductInfoVO) throws BizException {
		return this.productInfoDAO.findProductInfo(searchProductInfoVO);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BizProductBO> findProductInfoPagesByVO(
			SearchProductInfoVO searchProductInfoVO, int first, int pageSize) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select s.ID,s.PRODUCT_NUM,s.PRODUCT_NAME,s.MOST_RATE_YEAR,s.LEAST_RATE_YEAR,");
			sb.append(" s.PRODUCT_DESC,s.PRODUCT_IMG_URL");
			sb.append(" from BIZ_PRODUCT s");
			sb.append(" where 1=1");
			if (StringUtils.isNotEmpty(searchProductInfoVO.getProductNum())) {
				sb.append(" and s.PRODUCT_NUM like '%").append(searchProductInfoVO.getProductNum()).append("%'");
			}
			if (StringUtils.isNotEmpty(searchProductInfoVO.getProductName())) {
				sb.append(" and s.PRODUCT_NAME like '%").append(searchProductInfoVO.getProductName()).append("%'");
			}
			return commonQuery.findBySQLByPages(sb.toString(), first, pageSize, BizProductBO.class);
		} catch (Exception e) {
			 
		}
		return null;
	}

	@Override
	public int findProductInfoCountByVO(SearchProductInfoVO searchProductInfoVO) {
		StringBuilder sb = new StringBuilder();
		sb.append("select count(1)");
		sb.append(" from BIZ_PRODUCT s");
		sb.append(" where 1=1");
		if (StringUtils.isNotEmpty(searchProductInfoVO.getProductNum())) {
			sb.append(" and s.PRODUCT_NUM like '%").append(searchProductInfoVO.getProductNum()).append("%'");
		}
		if (StringUtils.isNotEmpty(searchProductInfoVO.getProductNum())) {
			sb.append(" and s.PRODUCT_NAME like '%").append(searchProductInfoVO.getProductName()).append("%'");
		}
		return this.commonQuery.findCountBySQL(sb, null);
	}

	@Override
	public ProductInfoVO findProductInfoById(Integer id) throws BizException {
		BizProductBO bo = (BizProductBO)this.productInfoDAO.findById(BizProductBO.class,id);
		ProductInfoVO vo = new ProductInfoVO(bo);
		return vo;
	}
	
	@SuppressWarnings("unchecked")
	public List<BizProductBO> checkProductNum(String productName,Integer id) throws BizException{
		if(productName != null && !"".equals(productName)){
			StringBuilder sb = new StringBuilder("from BizProductBO where ");
			sb.append(" productName = '").append(productName).append("'");
			if(id!=null && !id.equals(Integer.valueOf(0))){
				sb.append(" and id != '").append(id).append("'");
			}
			List<BizProductBO> list = baseDAO.findByHQL(sb.toString());
			return list;
		}else{
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<BizProductBO> checkProductName(String productName,Integer id)throws BizException{
		if(productName != null && !"".equals(productName)){
			StringBuilder sb = new StringBuilder("from BizProductBO where ");
			sb.append(" productName = '").append(productName).append("'");
			if(id!=null && !id.equals(Integer.valueOf(0))){
				sb.append(" and id != '").append(id).append("'");
			}
			List<BizProductBO> list = baseDAO.findByHQL(sb.toString());
			return list;
		}else{
			return null;
		}
	}
	
	@Override
	public BizProductBO getBizProductBOById(Integer id) throws BizException{
		return (BizProductBO) productInfoDAO.findById(BizProductBO.class, id);
	}

	public IProductInfoDAO getProductInfoDAO() {
		return productInfoDAO;
	}

	public void setProductInfoDAO(IProductInfoDAO productInfoDAO) {
		this.productInfoDAO = productInfoDAO;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}
	
}
