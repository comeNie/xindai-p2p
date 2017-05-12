package com.qfw.bean.creditor;

/**
 * 投资统计管理Bean
 * 
 */

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;

import com.qfw.bizservice.creditor.ICreditorCountManageBS;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.model.vo.creditor.CreditorCountVO;
import com.qfw.model.vo.creditor.CreditorSearchVO;
import com.qfw.model.vo.creditor.LazyCreditorCountDataModel;

@ViewScoped
@ManagedBean(name="creditorCountManageBackingBean")
public class CreditorCountManageBackingBean extends BaseBackingBean{
    
	private static final long serialVersionUID = 2859782162400705672L;
	
	@ManagedProperty(value = "#{creditorCountManageBS}")
	private ICreditorCountManageBS creditorCountManageBS;
	private CreditorSearchVO searchVO = new CreditorSearchVO();
	private LazyDataModel<CreditorCountVO> dataModel;
	private CreditorCountVO creditorCountVO;
	
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	
	@PostConstruct
    public void init() {
		if (searchVO != null) {
			try {
				creditorCountVO = new CreditorCountVO();
				dataModel = new LazyCreditorCountDataModel(searchVO, creditorCountManageBS);
			} catch (Exception e) {
				log.error("日常查询搜索异常：", e);
				alert("日常查询搜索异常："+e.getMessage());
			}
		}
    }
	
	public void search() {
		init();
	}

	public ICreditorCountManageBS getCreditorCountManageBS() {
		return creditorCountManageBS;
	}

	public void setCreditorCountManageBS(
			ICreditorCountManageBS creditorCountManageBS) {
		this.creditorCountManageBS = creditorCountManageBS;
	}

	public CreditorSearchVO getSearchVO() {
		return searchVO;
	}

	public void setSearchVO(CreditorSearchVO searchVO) {
		this.searchVO = searchVO;
	}

	public LazyDataModel<CreditorCountVO> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<CreditorCountVO> dataModel) {
		this.dataModel = dataModel;
	}

	public CreditorCountVO getCreditorCountVO() {
		return creditorCountVO;
	}

	public void setCreditorCountVO(CreditorCountVO creditorCountVO) {
		this.creditorCountVO = creditorCountVO;
	}
	
	
}
