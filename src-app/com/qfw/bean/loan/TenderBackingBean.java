package com.qfw.bean.loan;

/**
 * 投标Bean
 * 
 * @author qswei
 */


import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.qfw.bizservice.creditor.ICreditorRightBS;
import com.qfw.bizservice.loan.ILoanApplyBS;
import com.qfw.bizservice.loan.ILoanBS;
import com.qfw.common.util.web.ViewOper;
import com.qfw.common.util.web.WorkFlowBackingBean;
import com.qfw.model.bo.BizLoanApproveBO;
import com.qfw.model.vo.creditor.CreditorRightVO;
import com.qfw.model.vo.loan.LoanApplyVO;

@ViewScoped
@ManagedBean(name="tenderBBean")
public class TenderBackingBean extends WorkFlowBackingBean{
    
	private BizLoanApproveBO loanApp = new BizLoanApproveBO();
	private LoanApplyVO loanApplyVO = new LoanApplyVO();
	private CreditorRightVO creditorRightVO = new CreditorRightVO();
	
	@ManagedProperty(value = "#{loanApplyBS}")
	private ILoanApplyBS loanApplyBS;
	
	@ManagedProperty(value = "#{creditorRightBS}")
	private ICreditorRightBS creditorRightBS;
	
	@ManagedProperty(value = "#{loanBS}")
	private ILoanBS loanBS;
	
	
	private Integer loanId;
	private Integer custId;
	private Integer loanApproveId;
	
	@PostConstruct
    public void init(){
		try {
			Object loanIdTemp = ViewOper.getSessionTmpAttr("loanId");
			Object custIdTemp = ViewOper.getSessionTmpAttr("custId");
			Object loanApproveIdTemp = ViewOper.getSessionTmpAttr("loanApproveId");
			loanId = (Integer) loanIdTemp;
			custId = (Integer) custIdTemp;
			loanApproveId = (Integer)loanApproveIdTemp;
			loanApplyVO = this.loanApplyBS.findLoanApply(loanApproveId);
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"借款申请异常："+e.getMessage(), null);
    		FacesContext.getCurrentInstance().addMessage(null, message);
		}
		
    }
	
	/**
	 * 投标
	 */
    public void tender(){
    	try{
    		
    		BigDecimal overCountAmt = loanApplyVO.getOverCountAmt();
    		BigDecimal buyAmt = loanApplyVO.getBuyAmt();
    		int overCountAmtTemp =  overCountAmt.intValue();
    		int buyAmtTemp = buyAmt.intValue();
    		if(buyAmtTemp > overCountAmtTemp){
    			//MessagesController.addInfo("剩余金额小于购买金额！", "请重新输入购买份数");
    			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"购买金额不能大于剩余金额", null);
        		FacesContext.getCurrentInstance().addMessage(null, message);
    			return;           
    		}
    		int buyCount = loanApplyVO.getBuyTtlCount(); //购买份数
    		int canBuyCount = loanApplyVO.getCanBuyTtlCount(); //可购买份数
    		if(buyCount > canBuyCount){
    			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"购买份数不能多于可购买份数！", null);
        		FacesContext.getCurrentInstance().addMessage(null, message);
    			return;
    		}
    		creditorRightVO.setCustId(custId); //投资人会员ID
    		creditorRightVO.setLoanApproveId(loanApproveId); //发布表信息ID
    		creditorRightVO.setCrAmt(loanApplyVO.getBuyAmt());//投资金额 
    		creditorRightVO.setTurnoverCount(loanApplyVO.getBuyTtlCount());//投资份数
    		creditorRightVO.setTenderTypeCd("1"); //投资类型,自动投标0\手动投标1
    		Boolean  isFullTender = this.creditorRightBS.submitTrender(creditorRightVO);
    		if(isFullTender){
    			//调用放款服务
    			this.loanBS.effectLoan(loanApproveId);
    		} 
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"恭喜投标成功！", null);
    		FacesContext.getCurrentInstance().addMessage(null, message);
    	}catch(Exception e){
    		       
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"投标失败！"+e.getMessage(), null);
    		FacesContext.getCurrentInstance().addMessage(null, message);
    	}
    }
    
	public ILoanBS getLoanBS() {
		return loanBS;
	}

	public void setLoanBS(ILoanBS loanBS) {
		this.loanBS = loanBS;
	}

	public CreditorRightVO getCreditorRightVO() {
		return creditorRightVO;
	}

	public void setCreditorRightVO(CreditorRightVO creditorRightVO) {
		this.creditorRightVO = creditorRightVO;
	}

	public ICreditorRightBS getCreditorRightBS() {
		return creditorRightBS;
	}

	public void setCreditorRightBS(ICreditorRightBS creditorRightBS) {
		this.creditorRightBS = creditorRightBS;
	}

	public BizLoanApproveBO getLoanApp() {
		return loanApp;
	}



	public void setLoanApp(BizLoanApproveBO loanApp) {
		this.loanApp = loanApp;
	}



	public LoanApplyVO getLoanApplyVO() {
		return loanApplyVO;
	}



	public void setLoanApplyVO(LoanApplyVO loanApplyVO) {
		this.loanApplyVO = loanApplyVO;
	}



	public ILoanApplyBS getLoanApplyBS() {
		return loanApplyBS;
	}



	public void setLoanApplyBS(ILoanApplyBS loanApplyBS) {
		this.loanApplyBS = loanApplyBS;
	}

	public Integer getLoanId() {
		return loanId;
	}

	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}

	public Integer getCustId() {
		return custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	public Integer getLoanApproveId() {
		return loanApproveId;
	}

	public void setLoanApproveId(Integer loanApproveId) {
		this.loanApproveId = loanApproveId;
	}
	

}
