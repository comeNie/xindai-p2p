package com.qfw.bean.loan;

/**
 * 借款申请列表Bean
 * 
 * @author weiqs
 */

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;

import com.qfw.bizservice.loan.ILoanApplyManageBS;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.vo.loan.LazyLoanApplyDataModel;
import com.qfw.model.vo.loan.loanApply.LoanApplyManageVO;
import com.qfw.model.vo.loan.loanApply.LoanApplySearchVO;

@ViewScoped
@ManagedBean(name="loanApplyManageBackingBean")
public class LoanApplyManageBackingBean extends BaseBackingBean{
    
	private static final long serialVersionUID = -2631989147972388606L;
	
	@ManagedProperty(value = "#{loanApplyManageBS}")
	private ILoanApplyManageBS loanManageBS;
	private LoanApplySearchVO searchVO = new LoanApplySearchVO();
	private LazyDataModel<LoanApplyManageVO> dataModel;
	private LoanApplyManageVO loanVO = new LoanApplyManageVO();;
	
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	
	@PostConstruct
    public void init(){
		search();
    }
	
	public void search() {
		super.search();
		if (searchVO != null) {
			try {        
				loanVO = new LoanApplyManageVO();
				dataModel = new LazyLoanApplyDataModel(searchVO, loanManageBS);
			} catch (Exception e) {
				log.error("借款申请列表异常：", e);
				alert("借款申请列表异常："+e.getMessage());
			}
			
		}
	}
	
	public String reset () {
		searchVO = new LoanApplySearchVO();
		return null;
	}

	public void operate(){
		String operateFlag = ViewOper.getParameter("operateFlag");
		if("view".equalsIgnoreCase(operateFlag)){// 查看
			ViewOper.getSession().setAttribute("loanApplyId", loanVO.getLoanApplyId());
		} 
	}
	public ILoanApplyManageBS getLoanManageBS() {
		return loanManageBS;
	}

	public void setLoanManageBS(ILoanApplyManageBS loanManageBS) {
		this.loanManageBS = loanManageBS;
	}

	public LoanApplySearchVO getSearchVO() {
		return searchVO;
	}

	public void setSearchVO(LoanApplySearchVO searchVO) {
		this.searchVO = searchVO;
	}

	public LoanApplyManageVO getLoanVO() {
		return loanVO;
	}

	public void setLoanVO(LoanApplyManageVO loanVO) {
		this.loanVO = loanVO;
	}

	public LazyDataModel<LoanApplyManageVO> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LoanApplyManageVO> dataModel) {
		this.dataModel = dataModel;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}
	
}
