package com.qfw.bean.custinfo;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.vo.custinfo.CustInfoVO;
import com.qfw.model.vo.custinfo.LazyCustDataModel;
import com.qfw.model.vo.custinfo.SearchCustInfoVO;

@ViewScoped
@ManagedBean(name = "refereeManageBean")
public class RefereeManageBean extends BaseBackingBean {

	private static final long serialVersionUID = 1L;

	private SearchCustInfoVO searchCustInfoVO = new SearchCustInfoVO();

	private List<CustInfoVO> custInfoList;

	private LazyCustDataModel dataModel;

	private CustInfoVO selectCust;

	@ManagedProperty(value = "#{custInfoBS}")
	private ICustInfoBS custInfoBS;

	private Logger log = LogFactory.getInstance().getBusinessLogger();

	private BizCustomerBO cust = new BizCustomerBO();

	@PostConstruct
	public void init() {
		if (searchCustInfoVO != null) {
			try {
				selectCust = new CustInfoVO();
//				searchCustInfoVO.setRefereeStatus(AppConst.REFEREE_STATUS_APPLY);
				searchCustInfoVO.setIsrefereeApply(true);
				dataModel = new LazyCustDataModel(searchCustInfoVO, custInfoBS);
			} catch (Exception e) {
				log.error("日常查询搜索异常：", e);
				alert("日常查询搜索异常：" + e.getMessage());
			}
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
		} else if ("agree".equalsIgnoreCase(operateFlag)) {
			BizCustomerBO bo = custInfoBS.findCustById(selectCust.getCustId());
			if(bo != null){
				bo.setRefereeStatus(AppConst.REFEREE_STATUS_AGREE);
				bo.setRefereeDate(new Date());
			}
			custInfoBS.update(bo);
			init();
		} else if ("disAgree".equalsIgnoreCase(operateFlag)) {
			BizCustomerBO bo = custInfoBS.findCustById(selectCust.getCustId());
			if(bo != null){
			   if(AppConst.REFEREE_STATUS_APPLY.equals(bo.getRefereeStatus())){
				   bo.setRefereeStatus(AppConst.REFEREE_STATUS_NOTAGREE_AGAIN);
			   }else if(AppConst.REFEREE_STATUS_APPLY_AGAIN.equals(bo.getRefereeStatus())){
				   bo.setRefereeStatus(AppConst.REFEREE_STATUS_NOTAGREE_TWICE);
			   }else if(AppConst.REFEREE_STATUS_APPLY_THIRD.equals(bo.getRefereeStatus())){
				   bo.setRefereeStatus(AppConst.REFEREE_STATUS_NOT_AGREE);
			   }
			}
			custInfoBS.update(bo);
			init();
		} 
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


	public BizCustomerBO getCust() {
		return cust;
	}

	public void setCust(BizCustomerBO cust) {
		this.cust = cust;
	}

}
