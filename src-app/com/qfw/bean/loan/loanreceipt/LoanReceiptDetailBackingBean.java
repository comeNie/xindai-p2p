package com.qfw.bean.loan.loanreceipt;

/**
 * 借据明细信息Bean
 * 
 * @author 
 */

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.qfw.bizservice.loan.ILoanApplyBS;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.bo.BizLoanBO;
import com.qfw.model.vo.loan.LoanApplyVO;

@ViewScoped
@ManagedBean(name = "loanReceiptDetailBean")
public class LoanReceiptDetailBackingBean extends BaseBackingBean {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value = "#{loanApplyBS}")
	private ILoanApplyBS loanApplyBS;

	private LoanApplyVO loanApplyVO = new LoanApplyVO();
	private BizLoanBO loanBO = new BizLoanBO();
	
	@PostConstruct
	public void init() {
		try {
			
			Object loanId = ViewOper.getSessionTmpAttr("loanId");
			if(null != loanId && (loanId instanceof Integer)){
				loanBO = this.loanApplyBS.getLoan((Integer) loanId);
				loanApplyVO = this.loanApplyBS.findLoanApply((Integer) loanId);
			}
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "借款申请查看异常：" + e.getMessage(),
					null);
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	public ILoanApplyBS getLoanApplyBS() {
		return loanApplyBS;
	}

	public void setLoanApplyBS(ILoanApplyBS loanApplyBS) {
		this.loanApplyBS = loanApplyBS;
	}

	public LoanApplyVO getLoanApplyVO() {
		return loanApplyVO;
	}

	public void setLoanApplyVO(LoanApplyVO loanApplyVO) {
		this.loanApplyVO = loanApplyVO;
	}

	public BizLoanBO getLoanBO() {
		return loanBO;
	}

	public void setLoanBO(BizLoanBO loanBO) {
		this.loanBO = loanBO;
	}
	
}
