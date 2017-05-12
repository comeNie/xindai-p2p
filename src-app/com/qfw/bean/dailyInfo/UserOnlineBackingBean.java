package com.qfw.bean.dailyInfo;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;

import com.qfw.bizservice.dailyinfo.IUserOnlineBS;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.model.bo.BizUserOnlineBO;
import com.qfw.model.vo.transaction.LazyUserOnlineDataModel;

@ViewScoped
@ManagedBean(name="userOnlineBackingBean")
public class UserOnlineBackingBean extends BaseBackingBean{

	private static final long serialVersionUID = 1L;
	
	private BizUserOnlineBO searchVO = new BizUserOnlineBO();
	private BizUserOnlineBO userOnlineBO;
	private LazyDataModel<BizUserOnlineBO> userOnlineModel;
	@ManagedProperty(value = "#{userOnlineBS}")
	private IUserOnlineBS userOnlineBS;
	
	private Logger log = LogFactory.getInstance().getPlatformLogger();
	
	@PostConstruct
    public void init(){
		this.search();
	} 
	
	public void search() {
		try {
			super.search();
			userOnlineModel = new LazyUserOnlineDataModel(searchVO, userOnlineBS);
		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	public BizUserOnlineBO getSearchVO() {
		return searchVO;
	}

	public void setSearchVO(BizUserOnlineBO searchVO) {
		this.searchVO = searchVO;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public BizUserOnlineBO getUserOnlineBO() {
		return userOnlineBO;
	}

	public void setUserOnlineBO(BizUserOnlineBO userOnlineBO) {
		this.userOnlineBO = userOnlineBO;
	}

	public LazyDataModel<BizUserOnlineBO> getUserOnlineModel() {
		return userOnlineModel;
	}

	public void setUserOnlineModel(LazyDataModel<BizUserOnlineBO> userOnlineModel) {
		this.userOnlineModel = userOnlineModel;
	}

	public IUserOnlineBS getUserOnlineBS() {
		return userOnlineBS;
	}

	public void setUserOnlineBS(IUserOnlineBS userOnlineBS) {
		this.userOnlineBS = userOnlineBS;
	}
	
}
