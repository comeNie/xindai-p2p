package com.qfw.bean.loan.loanList;

/**
 * 投资列表Bean
 * 
 * @author kangyc
 */

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;

import com.qfw.bizservice.loan.ILoanManageBS;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.vo.loan.LazyLoanDataModel;
import com.qfw.model.vo.loan.LoanManageVO;
import com.qfw.model.vo.loan.LoanSearchVO;

@ViewScoped
@ManagedBean(name="loanListBean")
public class LoanListBackingBean extends BaseBackingBean{
    
	private static final long serialVersionUID = -2631989147972388606L;
	
	@ManagedProperty(value = "#{loanManageBS}")
	private ILoanManageBS loanManageBS;
	private LoanSearchVO searchVO = new LoanSearchVO();
	private LazyDataModel<LoanManageVO> dataModel;
	private LoanManageVO loanVO;
	
	private LoanManageVO selectLoan;
	
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	
	@PostConstruct
    public void init(){
		search();
    }
	
	public void search() {
		super.search();
		if (searchVO != null) {
			try {
				loanVO = new LoanManageVO();
				searchVO.setLoanStatusCd("1");//投标中
				dataModel = new LazyLoanDataModel(searchVO, loanManageBS);
			} catch (Exception e) {
				log.error("日常查询搜索异常：", e);
				alert("日常查询搜索异常："+e.getMessage());
			}
			
		}
	}
	
	public void operate(){
		String operateFlag = ViewOper.getParameter("operateFlag");
		if("view".equalsIgnoreCase(operateFlag)){// 借款查看
			ViewOper.getSession().setAttribute("loanId", selectLoan.getLoanId());
			ViewOper.getSession().setAttribute("custId", selectLoan.getCustId());
			ViewOper.getSession().setAttribute("loanApproveId", selectLoan.getLoanApproveId());
			//ViewOper.getSession().setAttribute("loanApplyId", selectLoan.getLoanApplyId());
		} else if ("repayPlan".equalsIgnoreCase(operateFlag)) {// 还款计划
			ViewOper.getSession().setAttribute("loanId", selectLoan.getLoanId());
		} else if ("debtor".equalsIgnoreCase(operateFlag)) {// 债权人信息
			ViewOper.getSession().setAttribute("loanId", selectLoan.getLoanId());
		} else if ("repay".equalsIgnoreCase(operateFlag)) {// 还款信息
			ViewOper.getSession().setAttribute("loanId", selectLoan.getLoanId());
		} else if ("loanReceipt".equalsIgnoreCase(operateFlag)) {// 借据详情
			ViewOper.getSession().setAttribute("loanId", selectLoan.getLoanId());
		} 
	}
	
	public String reset () {
		searchVO = new LoanSearchVO();
		return null;
	}

	public ILoanManageBS getLoanManageBS() {
		return loanManageBS;
	}

	public void setLoanManageBS(ILoanManageBS loanManageBS) {
		this.loanManageBS = loanManageBS;
	}

	public LoanSearchVO getSearchVO() {
		return searchVO;
	}

	public void setSearchVO(LoanSearchVO searchVO) {
		this.searchVO = searchVO;
	}

	public LoanManageVO getLoanVO() {
		return loanVO;
	}

	public void setLoanVO(LoanManageVO loanVO) {
		this.loanVO = loanVO;
	}

	public LazyDataModel<LoanManageVO> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LoanManageVO> dataModel) {
		this.dataModel = dataModel;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public LoanManageVO getSelectLoan() {
		return selectLoan;
	}

	public void setSelectLoan(LoanManageVO selectLoan) {
		this.selectLoan = selectLoan;
	}
	
}
