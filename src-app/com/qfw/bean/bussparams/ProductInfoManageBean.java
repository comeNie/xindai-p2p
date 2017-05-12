package com.qfw.bean.bussparams;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;

import com.qfw.bizservice.bussparams.IProductInfoBS;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.bo.BizProductBO;
import com.qfw.model.vo.bussparams.LazyProductDataModel;
import com.qfw.model.vo.bussparams.SearchProductInfoVO;

@ViewScoped
@ManagedBean(name="productInfoManageBean")
public class ProductInfoManageBean extends BaseBackingBean{

	private static final long serialVersionUID = 1L;

	private SearchProductInfoVO searchProductInfoVO = new SearchProductInfoVO();
	
	private LazyDataModel<BizProductBO> productInfoList;
	
	@ManagedProperty(value = "#{productInfoBS}")
	private IProductInfoBS productInfoBS;
	
	private BizProductBO selectProduct;
	
	private Logger log = LogFactory.getInstance().getPlatformLogger();
	
	@PostConstruct
    public void init(){
		searchProductInfo();
	} 
	
	public void searchProductInfo () {
		try {
			super.search();
			productInfoList = new LazyProductDataModel(searchProductInfoVO, productInfoBS);
		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	public void operate(){
		String operateFlag = ViewOper.getParameter("operateFlag");
		if("view".equalsIgnoreCase(operateFlag) || "edit".equalsIgnoreCase(operateFlag)){
			ViewOper.getSession().setAttribute("id", selectProduct.getId());
		}else if("delete".equalsIgnoreCase(operateFlag)){
			String productId = String.valueOf(selectProduct.getId());
			String sql = "delete from BIZ_PRODUCT where id = '"+productId+"'";
			try {
				productInfoBS.getJdbcTemplate().execute(sql);
				productInfoList.setRowCount(productInfoList.getRowCount()-1);
			} catch (Exception e) {
				log.error("删除产品"+selectProduct.getProductName()+"异常：", e);
				alert("删除产品异常："+ e);
			}
		}
	}
	
	public IProductInfoBS getProductInfoBS() {
		return productInfoBS;
	}

	public void setProductInfoBS(IProductInfoBS productInfoBS) {
		this.productInfoBS = productInfoBS;
	}

	public SearchProductInfoVO getSearchProductInfoVO() {
		return searchProductInfoVO;
	}

	public void setSearchProductInfoVO(SearchProductInfoVO searchProductInfoVO) {
		this.searchProductInfoVO = searchProductInfoVO;
	}

	public BizProductBO getSelectProduct() {
		return selectProduct;
	}

	public void setSelectProduct(BizProductBO selectProduct) {
		this.selectProduct = selectProduct;
	}

	public LazyDataModel<BizProductBO> getProductInfoList() {
		return productInfoList;
	}

	public void setProductInfoList(LazyDataModel<BizProductBO> productInfoList) {
		this.productInfoList = productInfoList;
	}
	
}
