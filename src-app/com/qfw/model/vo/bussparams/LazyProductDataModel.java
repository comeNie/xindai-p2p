package com.qfw.model.vo.bussparams;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.qfw.bizservice.bussparams.IProductInfoBS;
import com.qfw.common.util.StringUtils;
import com.qfw.model.bo.BizProductBO;

public class LazyProductDataModel extends LazyDataModel<BizProductBO>{
	
	private static final long serialVersionUID = 1L;
	private List<BizProductBO> productInfoList;	
	private IProductInfoBS productInfoBS;
	private SearchProductInfoVO searchProductInfoVO;
	
	public LazyProductDataModel(SearchProductInfoVO searchProductInfoVO, IProductInfoBS productInfoBS){
		this.searchProductInfoVO = searchProductInfoVO;
		this.productInfoBS = productInfoBS;
	}

	@Override
	public List<BizProductBO> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		try {
			productInfoList = this.productInfoBS.findProductInfoPagesByVO(searchProductInfoVO, first, pageSize);
			setRowCount(this.productInfoBS.findProductInfoCountByVO(searchProductInfoVO));
		} catch (Exception e) {
			 
		}
		return productInfoList;
	}
	
	@Override
	public BizProductBO getRowData(String rowKey) {
		if(StringUtils.isEmpty(rowKey)){
			return null;
		}
		for(BizProductBO productInfoVO : productInfoList){
			if(String.valueOf(productInfoVO.getId()).equals(rowKey))
				return productInfoVO;
		}
		BizProductBO productInfoVO = new ProductInfoVO();
		try {
			productInfoVO = productInfoBS.findProductInfoById(Integer.valueOf(rowKey));
		} catch (Exception e) {
		}
		return productInfoVO;
	}
	
	@Override
	public Object getRowKey(BizProductBO productInfoVO){
		return productInfoVO.getId();
	}

	public List<BizProductBO> getProductInfoList() {
		return productInfoList;
	}

	public void setProductInfoList(List<BizProductBO> productInfoList) {
		this.productInfoList = productInfoList;
	}

	public IProductInfoBS getProductInfoBS() {
		return productInfoBS;
	}

	public void setProductInfoBS(IProductInfoBS productInfoBS) {
		this.productInfoBS = productInfoBS;
	}
	
}
