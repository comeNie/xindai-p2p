package com.qfw.bean.creditor;

/**
 * 债权转让发布Bean
 * 
 * @author weiqs
 */

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;

import com.qfw.bizservice.creditor.ICreditorManageBS;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.vo.creditor.CreditorManageVO;
import com.qfw.model.vo.creditor.CreditorSearchVO;
import com.qfw.model.vo.creditor.LazyCreditorDataModel;

@ViewScoped
@ManagedBean(name="creditorTranManageBean")
public class CreditorTranManageBackingBean extends BaseBackingBean{
    
	private static final long serialVersionUID = 2859782162400705672L;
	
	@ManagedProperty(value = "#{creditorManageBS}")
	private ICreditorManageBS creditorManageBS;
	private CreditorSearchVO searchVO = new CreditorSearchVO();
	private LazyDataModel<CreditorManageVO> dataModel;
	private CreditorManageVO creditorVO;
	
	
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	
	@PostConstruct
    public void init() {
		if (searchVO != null) {
			try {
				searchVO.setCreditorRightTran("crTran");
				dataModel = new LazyCreditorDataModel(searchVO, creditorManageBS);
			} catch (Exception e) {
				log.error("债权转让发布列表查询异常：", e);
				alert("债权转让发布列表查询异常："+e.getMessage());
			}
		}
    }
	
	public void search() {
		init();
	}
	
	public String reset () {
		searchVO = new CreditorSearchVO();
		return null;
	}
	
	public void operate(){
		String operateFlag = ViewOper.getParameter("operateFlag");
		if("view".equalsIgnoreCase(operateFlag)){// 借款详情
			ViewOper.getSession().setAttribute("tranId", creditorVO.getTranId());
		} else if("tranView".equalsIgnoreCase(operateFlag)){ //债权转让详情
			ViewOper.getSession().setAttribute("loanId", creditorVO.getCreditorId());
			ViewOper.getSession().setAttribute("loanApproveId", creditorVO.getLoanApproveId());
			ViewOper.getSession().setAttribute("tranId", creditorVO.getTranId());
		} 
	}
	
	public ICreditorManageBS getCreditorManageBS() {
		return creditorManageBS;
	}

	public void setCreditorManageBS(ICreditorManageBS creditorManageBS) {
		this.creditorManageBS = creditorManageBS;
	}

	public CreditorSearchVO getSearchVO() {
		return searchVO;
	}

	public void setSearchVO(CreditorSearchVO searchVO) {
		this.searchVO = searchVO;
	}

	public LazyDataModel<CreditorManageVO> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<CreditorManageVO> dataModel) {
		this.dataModel = dataModel;
	}

	public CreditorManageVO getCreditorVO() {
		return creditorVO;
	}

	public void setCreditorVO(CreditorManageVO creditorVO) {
		this.creditorVO = creditorVO;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}
	
}
