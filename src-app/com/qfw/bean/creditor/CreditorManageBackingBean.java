package com.qfw.bean.creditor;

/**
 * 债权管理Bean
 * 
 * @author kangyc
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
@ManagedBean(name="creditorManageBean")
public class CreditorManageBackingBean extends BaseBackingBean{
    
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
				creditorVO = new CreditorManageVO();
				dataModel = new LazyCreditorDataModel(searchVO, creditorManageBS);
			} catch (Exception e) {
				log.error("日常查询搜索异常：", e);
				alert("日常查询搜索异常："+e.getMessage());
			}
		}
    }
	
	public void search() {
		init();
	}
	
	public void operate(){
		String operateFlag = ViewOper.getParameter("operateFlag");
		if("view".equalsIgnoreCase(operateFlag)){// 借款详情
			ViewOper.getSession().setAttribute("loanId", creditorVO.getCreditorId());
			ViewOper.getSession().setAttribute("loanApproveId", creditorVO.getLoanApproveId());
		} else if ("repayPlan".equalsIgnoreCase(operateFlag)) {// 还款计划
			ViewOper.getSession().setAttribute("loanId", creditorVO.getCreditorId());
		} else if ("debtor".equalsIgnoreCase(operateFlag)) {// 债权人信息
			ViewOper.getSession().setAttribute("loanId", creditorVO.getCreditorId());
		} else if ("repay".equalsIgnoreCase(operateFlag)) {// 还款信息
			ViewOper.getSession().setAttribute("loanId", creditorVO.getCreditorId());
		} else if("reditorRights".equalsIgnoreCase(operateFlag)){ //债权转让
			ViewOper.getSession().setAttribute("loanId", creditorVO.getCreditorId());
			ViewOper.getSession().setAttribute("loanApproveId", creditorVO.getLoanApproveId());
		} else if("incomeView".equalsIgnoreCase(operateFlag)){ //收益详情
			ViewOper.getSession().setAttribute("creditorId", creditorVO.getCreditorId());
		} else if("tranView".equalsIgnoreCase(operateFlag)){ //债权转让详情
			ViewOper.getSession().setAttribute("loanId", creditorVO.getCreditorId());
			ViewOper.getSession().setAttribute("loanApproveId", creditorVO.getLoanApproveId());
			ViewOper.getSession().setAttribute("tranId", creditorVO.getTranId());
		} 
	}
	
	public String reset () {
		searchVO = new CreditorSearchVO();
		return null;
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
