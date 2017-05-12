package com.qfw.bean.custinfo;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.BussConst;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.vo.custinfo.CustInfoVO;
import com.qfw.model.vo.custinfo.LazyCustDataModel;
import com.qfw.model.vo.custinfo.SearchCustInfoVO;

@ViewScoped
@ManagedBean(name="birthBean")
public class BirthBean extends BaseBackingBean{
    
	private LazyCustDataModel dataModel;
	
	private SearchCustInfoVO searchCustInfoVO = new SearchCustInfoVO();
    
	@ManagedProperty(value = "#{custInfoBS}")
	private ICustInfoBS custInfoBS;
	
	private CustInfoVO selectCust;
    
    private Logger log = LogFactory.getInstance().getPlatformLogger();
	
	@PostConstruct
	public void init() {
		if (searchCustInfoVO != null) {
			try {
				selectCust = new CustInfoVO();
				searchCustInfoVO.setDay("7");
				searchCustInfoVO.setIsVip(BussConst.YES_FLAG);
				dataModel = new LazyCustDataModel(searchCustInfoVO, custInfoBS);
			} catch (Exception e) {
				log.error("日常查询搜索异常：", e);
				alert("日常查询搜索异常：" + e.getMessage());
			}
		}
	}
	
	public void search(){
		super.search();
		//init();
	}
	
	public String operate(){
		String url = ViewOper.getParameter("url");
		//System.out.println(url);
		return url;
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



}
