package com.qfw.bean.custinfo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.qfw.bizservice.bussparams.IProductInfoBS;
import com.qfw.bizservice.credit.ICreditBS;
import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.vo.bussparams.ProductInfoVO;
import com.qfw.model.vo.bussparams.SearchProductInfoVO;
import com.qfw.model.vo.credit.RequestCreditVO;
import com.qfw.model.vo.custinfo.CustInfoVO;
import com.qfw.model.vo.custinfo.LazyCustDataModel;
import com.qfw.model.vo.custinfo.SearchCustInfoVO;

@ViewScoped
@ManagedBean(name = "custInfoManageBean")
public class CustInfoManageBean extends BaseBackingBean {

	private static final long serialVersionUID = 1L;

	private String custIdHidden;

	private String businessType;

	private String productId = "";

	private List<SelectItem> productItems = new ArrayList<SelectItem>();

	private SearchCustInfoVO searchCustInfoVO = new SearchCustInfoVO();

	private List<CustInfoVO> custInfoList;

	private LazyCustDataModel dataModel;

	private CustInfoVO selectCust;

	@ManagedProperty(value = "#{custInfoBS}")
	private ICustInfoBS custInfoBS;
	@ManagedProperty(value = "#{productInfoBS}")
	private IProductInfoBS productInfoBS;
	@ManagedProperty(value = "#{creditBS}")
	private ICreditBS creditBS;

	private Logger log = LogFactory.getInstance().getBusinessLogger();

	private BizCustomerBO cust = new BizCustomerBO();

	@PostConstruct
	public void init() {
		if (searchCustInfoVO != null) {
			try {
				selectCust = new CustInfoVO();
				dataModel = new LazyCustDataModel(searchCustInfoVO, custInfoBS);
			} catch (Exception e) {
				log.error("日常查询搜索异常：", e);
				alert("日常查询搜索异常：" + e.getMessage());
			}
		}

		Object custIdtemp = ViewOper.getSessionTmpAttr("custIdSession");
		if (null != custIdtemp && custIdtemp instanceof Integer) {
			custIdHidden = String.valueOf((Integer) custIdtemp);
		}

		try {
			List<ProductInfoVO> productInfoVOList = this.productInfoBS
					.findProductInfo(new SearchProductInfoVO());
			if (null != productInfoVOList && productInfoVOList.size() > 0) {
				for (ProductInfoVO productInfoVO : productInfoVOList) {
					productItems.add(new SelectItem(productInfoVO.getId(),
							productInfoVO.getProductName()));
				}
			}
		} catch (Exception e) {
			 
		}

	}

	public void searchCustInfo() {
		init();
	}

	public String reset() {
		searchCustInfoVO = new SearchCustInfoVO();
		return null;
	}

	public void operate() {
		String operateFlag = ViewOper.getParameter("operateFlag");
		if ("custInfo".equalsIgnoreCase(operateFlag)) {
			ViewOper.getSession()
					.setAttribute("userId", selectCust.getCustId());
		} else if ("pmDetail".equalsIgnoreCase(operateFlag)) {
			ViewOper.getSession()
					.setAttribute("userId", selectCust.getCustId());
			ViewOper.getSession().setAttribute("accout",
					selectCust.getAccount());
		} else if ("accDetail".equalsIgnoreCase(operateFlag)) {
			ViewOper.getSession()
					.setAttribute("userId", selectCust.getCustId());
			ViewOper.getSession().setAttribute("accout",
					selectCust.getAccount());
		} else if ("addBusiness".equalsIgnoreCase(operateFlag)) {
			ViewOper.getSession().setAttribute("custIdSession",
					selectCust.getCustId());
		}
	}

	public String chooseBusinessType() {
		if (null == custIdHidden || custIdHidden.length() <= 0) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"客户信息为空", null);
			FacesContext.getCurrentInstance().addMessage(null, message);
			return null;
		}
		ViewOper.getSession().setAttribute("businessType", businessType);
		ViewOper.getSession().setAttribute("custId", custIdHidden);
		if ("1".equalsIgnoreCase(businessType)) {// 额度申请
			return "creditLimitApply";
		} else if ("2".equalsIgnoreCase(businessType)) {// 业务申请
			try {
				RequestCreditVO requestVO = new RequestCreditVO();
				requestVO.setRelId(custIdHidden);
				Map<String, BigDecimal> map = this.creditBS.surplusCreditAmt(
						requestVO, null);
				if (null == map
						|| null == map.get(custIdHidden)
						|| map.get(custIdHidden).compareTo(BigDecimal.ZERO) <= 0) {
					FacesMessage message = new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"业务发起失败：额度不存在或可使用额度为0!", null);
					FacesContext.getCurrentInstance().addMessage(null, message);
					businessType = "";
					return null;
				}
				ViewOper.getSession().setAttribute("productId", productId);
				return "borrower";
			} catch (Exception e) {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"业务发起失败：" + e.getMessage(), null);
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
			return null;
		} else if ("3".equalsIgnoreCase(businessType)) {// 二合一
			ViewOper.getSession().setAttribute("productId", productId);
			return "mergeApply";
		}
		return null;

	}

	public LazyCustDataModel getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyCustDataModel dataModel) {
		this.dataModel = dataModel;
	}

	public SearchCustInfoVO getSearchCustInfoVO() {
		return searchCustInfoVO;
	}

	public void setSearchCustInfoVO(SearchCustInfoVO searchCustInfoVO) {
		this.searchCustInfoVO = searchCustInfoVO;
	}

	public List<CustInfoVO> getCustInfoList() {
		return custInfoList;
	}

	public void setCustInfoList(List<CustInfoVO> custInfoList) {
		this.custInfoList = custInfoList;
	}

	public ICustInfoBS getCustInfoBS() {
		return custInfoBS;
	}

	public void setCustInfoBS(ICustInfoBS custInfoBS) {
		this.custInfoBS = custInfoBS;
	}

	public CustInfoVO getSelectCust() {
		return selectCust;
	}

	public void setSelectCust(CustInfoVO selectCust) {
		this.selectCust = selectCust;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getCustIdHidden() {
		return custIdHidden;
	}

	public void setCustIdHidden(String custIdHidden) {
		this.custIdHidden = custIdHidden;
	}

	public BizCustomerBO getCust() {
		return cust;
	}

	public void setCust(BizCustomerBO cust) {
		this.cust = cust;
	}

	public List<SelectItem> getProductItems() {
		return productItems;
	}

	public void setProductItems(List<SelectItem> productItems) {
		this.productItems = productItems;
	}

	public IProductInfoBS getProductInfoBS() {
		return productInfoBS;
	}

	public void setProductInfoBS(IProductInfoBS productInfoBS) {
		this.productInfoBS = productInfoBS;
	}

	public ICreditBS getCreditBS() {
		return creditBS;
	}

	public void setCreditBS(ICreditBS creditBS) {
		this.creditBS = creditBS;
	}

}
