package com.qfw.bean.loan;

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
import com.qfw.model.AppConst;
import com.qfw.model.vo.loan.LazyRepayDataModel;
import com.qfw.model.vo.loan.LoanManageVO;
import com.qfw.model.vo.loan.LoanSearchVO;

@ViewScoped
@ManagedBean(name="repayManageBean")
public class RepayManageBackingBean extends BaseBackingBean {

	private static final long serialVersionUID = 1L;
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	
	@ManagedProperty(value = "#{loanManageBS}")
	private ILoanManageBS loanManageBS;
	private LoanSearchVO searchVO = new LoanSearchVO();
	private LoanManageVO loanVO;
	private LoanManageVO selectLoan;
	private LazyDataModel<LoanManageVO> myRepayList;
	
	@PostConstruct
	public void init(){
		searchVO.setLoanStatusCd(AppConst.APP_STATUS_CD_HK);
		this.search();
	}
	
	public void search() {
		super.search();
		if (searchVO != null) {
			try {
				loanVO = new LoanManageVO();
				myRepayList = new LazyRepayDataModel(searchVO, loanManageBS);
			} catch (Exception e) {
				log.error("还款管理查询异常：", e);
				alert("还款管理查询异常："+e.getMessage());
			}
			
		}
	}
	
	public void operate(){
		String operateFlag = ViewOper.getParameter("operateFlag");
		if("repay".equalsIgnoreCase(operateFlag)){// 还款
			ViewOper.getSession().setAttribute("loanId", loanVO.getLoanId());
			ViewOper.getSession().setAttribute("custId", loanVO.getCustId());
		} else if ("prepay".equalsIgnoreCase(operateFlag)) {// 提前还款
			ViewOper.getSession().setAttribute("loanId", loanVO.getLoanId());
		}
	}
	
	public String reset() {
		searchVO = new LoanSearchVO();
		return null;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
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

	public LoanManageVO getSelectLoan() {
		return selectLoan;
	}

	public void setSelectLoan(LoanManageVO selectLoan) {
		this.selectLoan = selectLoan;
	}

	public LazyDataModel<LoanManageVO> getMyRepayList() {
		return myRepayList;
	}

	public void setMyRepayList(LazyDataModel<LoanManageVO> myRepayList) {
		this.myRepayList = myRepayList;
	}

}
