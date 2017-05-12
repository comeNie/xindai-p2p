package com.qfw.bean.dailyInfo;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;

import com.qfw.bizservice.custinfo.account.ICustAccountBS;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.model.vo.custinfo.account.LazyAccountDataModel;
import com.qfw.model.vo.custinfo.account.PMInfoVO;
import com.qfw.model.vo.custinfo.account.PMSearchVO;

/**
 * pm币查询
 *
 * @author kyc
 */
@ViewScoped
@ManagedBean(name="pmInfoBean")
public class PMInfoBackingBean extends BaseBackingBean {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value = "#{custAccountBS}")
	private ICustAccountBS accountBS;
	private LazyDataModel<PMInfoVO> pmModels;
	private PMSearchVO searchVO = new PMSearchVO();
	private PMInfoVO pminfoVO;
	
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	
	public void search(){
		super.search();
		if(searchVO != null){ 
			try {
				pminfoVO = new PMInfoVO();
				pmModels = new LazyAccountDataModel(searchVO, accountBS);
			} catch (Exception e) {
				log.error("日常查询搜索异常：", e);
				alert("日常查询搜索异常："+e.getMessage());
			}
		}
	}
	
	public void operate(){
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public ICustAccountBS getAccountBS() {
		return accountBS;
	}

	public void setAccountBS(ICustAccountBS accountBS) {
		this.accountBS = accountBS;
	}

	public LazyDataModel<PMInfoVO> getPmModels() {
		return pmModels;
	}

	public void setPmModels(LazyDataModel<PMInfoVO> pmModels) {
		this.pmModels = pmModels;
	}

	public PMSearchVO getSearchVO() {
		return searchVO;
	}

	public void setSearchVO(PMSearchVO searchVO) {
		this.searchVO = searchVO;
	}

	public PMInfoVO getPminfoVO() {
		return pminfoVO;
	}

	public void setPminfoVO(PMInfoVO pminfoVO) {
		this.pminfoVO = pminfoVO;
	}

}
