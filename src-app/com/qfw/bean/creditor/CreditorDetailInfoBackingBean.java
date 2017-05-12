package com.qfw.bean.creditor;

/**
 * 显示债券转让详细信息Bean
 * 
 * @author 
 */
import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.qfw.bizservice.creditor.ICreditorManageBS;
import com.qfw.bizservice.creditor.ICreditorRightBS;
import com.qfw.bizservice.loan.ILoanApplyBS;
import com.qfw.common.bizservice.IParamBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.util.StringUtils;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizCreditorRightBO;
import com.qfw.model.bo.BizCreditorRightTranBO;
import com.qfw.model.vo.loan.LoanApplyVO;

@ViewScoped
@ManagedBean(name="creditorDetailInfoBean")
public class CreditorDetailInfoBackingBean extends BaseBackingBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{loanApplyBS}")
	private ILoanApplyBS loanApplyBS;
	
	@ManagedProperty(value = "#{creditorManageBS}")
	private ICreditorManageBS creditorManageBS;
	
	@ManagedProperty(value = "#{paramBS}")
	private IParamBS paramBS;
	
	
	private LoanApplyVO loanApplyVO = new LoanApplyVO();
	
	private int loanApproveId;
	
	private BizCreditorRightTranBO tranBO = new BizCreditorRightTranBO();//债权转让信息表
	private BizCreditorRightBO bcr;
	
	private Integer loanApproveIdTemp;
	
	private String crId;
	
	private Integer tranId;
	
	private int ttlUseCont = 0;//债权转让发布的总份数
	
	private BigDecimal ttlUseAmt = BigDecimal.ZERO;//发布表总转让金额
	
	@ManagedProperty(value = "#{creditorRightBS}")
	private ICreditorRightBS creditorRightBS;
	

	@PostConstruct
    public void init(){
		try {
			loanApproveIdTemp = (Integer)ViewOper.getSessionTmpAttr("loanApproveId"); //债权ID
			crId = String.valueOf(ViewOper.getSessionTmpAttr("loanId"));
			tranId = (Integer) ViewOper.getSessionTmpAttr("tranId");
			initData();
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"债权转让信息异常："+e.getMessage(), null);
    		FacesContext.getCurrentInstance().addMessage(null, message);
		}
		
    }
	
	private void initData() throws BizException{
		
		ttlUseCont = 0;
		ttlUseAmt = BigDecimal.ZERO;
		loanApplyVO = this.loanApplyBS.findLoanApply(loanApproveIdTemp);
		
		if(StringUtils.isNotEmpty(crId)){
			bcr = (BizCreditorRightBO) creditorManageBS.find(BizCreditorRightBO.class, crId);
			tranBO = this.creditorRightBS.findCreditorRightTransById(tranId);
		}else{
			this.alert("找不到债权信息");
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

	public ICreditorManageBS getCreditorManageBS() {
		return creditorManageBS;
	}

	public void setCreditorManageBS(ICreditorManageBS creditorManageBS) {
		this.creditorManageBS = creditorManageBS;
	}

	public int getLoanApproveId() {
		return loanApproveId;
	}

	public void setLoanApproveId(int loanApproveId) {
		this.loanApproveId = loanApproveId;
	}

	public String getFeeRate(){
		return paramBS.getParam(AppConst.PARAMETER_CODE_ZQZRGLFL);
	}

	public IParamBS getParamBS() {
		return paramBS;
	}

	public void setParamBS(IParamBS paramBS) {
		this.paramBS = paramBS;
	}

	public BizCreditorRightBO getBcr() {
		return bcr;
	}

	public void setBcr(BizCreditorRightBO bcr) {
		this.bcr = bcr;
	}

	public ICreditorRightBS getCreditorRightBS() {
		return creditorRightBS;
	}

	public void setCreditorRightBS(ICreditorRightBS creditorRightBS) {
		this.creditorRightBS = creditorRightBS;
	}

	public int getTtlUseCont() {
		return ttlUseCont;
	}

	public void setTtlUseCont(int ttlUseCont) {
		this.ttlUseCont = ttlUseCont;
	}

	public BigDecimal getTtlUseAmt() {
		return ttlUseAmt;
	}

	public void setTtlUseAmt(BigDecimal ttlUseAmt) {
		this.ttlUseAmt = ttlUseAmt;
	}

	public Integer getTranId() {
		return tranId;
	}

	public void setTranId(Integer tranId) {
		this.tranId = tranId;
	}

	public BizCreditorRightTranBO getTranBO() {
		return tranBO;
	}

	public void setTranBO(BizCreditorRightTranBO tranBO) {
		this.tranBO = tranBO;
	}

}
