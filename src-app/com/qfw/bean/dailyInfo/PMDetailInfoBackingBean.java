package com.qfw.bean.dailyInfo;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;

import com.qfw.bizservice.custinfo.account.ICustAccountBS;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.vo.custinfo.account.LazyAccountDataModel;
import com.qfw.model.vo.custinfo.account.PMInfoVO;
import com.qfw.model.vo.custinfo.account.PMSearchVO;

/**
 * pm币查询
 *
 * @author kyc
 */
@ViewScoped
@ManagedBean(name="pmDetailInfoBean")
public class PMDetailInfoBackingBean extends BaseBackingBean {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value = "#{custAccountBS}")
	private ICustAccountBS accountBS;
	
	private LazyDataModel<PMInfoVO> detailModels;
	private PMSearchVO searchVO = new PMSearchVO();
	private PMInfoVO detailBO;
	
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	
	@PostConstruct
	public void init() {
		Object custId = ViewOper.getSessionTmpAttr("userId");
		Object account = ViewOper.getSessionTmpAttr("accout");
		if(custId!=null&&account!=null){
			try {
				searchVO = new PMSearchVO();
				searchVO.setCustId((Integer)custId);
				searchVO.setAccount((String)account);
				detailModels =  new LazyAccountDataModel(searchVO, accountBS);
			} catch (Exception e) {
				log.error("pm币明细信息获取异常：", e);
			}
		}
	}
	
	
	public void search(){
		super.search();
		if(searchVO != null){ 
			try {
				detailBO = new PMInfoVO();
				detailModels = new LazyAccountDataModel(searchVO, accountBS);
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

	public LazyDataModel<PMInfoVO> getDetailModels() {
		return detailModels;
	}

	public void setDetailModels(LazyDataModel<PMInfoVO> detailModels) {
		this.detailModels = detailModels;
	}

	public PMSearchVO getSearchVO() {
		return searchVO;
	}

	public void setSearchVO(PMSearchVO searchVO) {
		this.searchVO = searchVO;
	}

	public PMInfoVO getDetailBO() {
		return detailBO;
	}

	public void setDetailBO(PMInfoVO detailBO) {
		this.detailBO = detailBO;
	}

}
