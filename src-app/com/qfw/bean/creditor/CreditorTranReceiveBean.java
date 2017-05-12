package com.qfw.bean.creditor;

import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.qfw.bizservice.creditor.ICreditorRightBS;
import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.bizservice.loan.ILoanApplyBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.util.NumberUtils;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizCreditorRightBO;
import com.qfw.model.bo.BizCreditorRightTranBO;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.vo.creditor.CreditorRightVO;
import com.qfw.model.vo.loan.LoanApplyVO;

@ViewScoped
@ManagedBean(name="creditorTranReceiveBean")
public class CreditorTranReceiveBean extends BaseBackingBean {

	private LoanApplyVO loanApplyVO = new LoanApplyVO();
	private BizCreditorRightTranBO crt = new BizCreditorRightTranBO();
	private CreditorRightVO crvo = new CreditorRightVO();
	
	@ManagedProperty(value = "#{creditorRightBS}")
	private ICreditorRightBS creditorRightBS;
	
	@ManagedProperty(value = "#{loanApplyBS}")
	private ILoanApplyBS loanApplyBS;
	
	@ManagedProperty(value = "#{custInfoBS}")
	private ICustInfoBS custInfoBS;
	@PostConstruct
	public void init(){
		Integer tranId = (Integer) ViewOper.getSessionTmpAttr("tranId");
		//Integer loanApproveIdTemp = (Integer) ViewOper.getSessionTmpAttr("crtId");
		if(!NumberUtils.isBlank(tranId)){
			crt = (BizCreditorRightTranBO) creditorRightBS.find(BizCreditorRightTranBO.class, tranId);
			if(crt==null){
				this.alert("债权发布信息不存在");
				return ;
			}
			crvo.setCreditorRightTranId(crt.getId());
			try {
				BizCreditorRightBO crbo = (BizCreditorRightBO) creditorRightBS.find(BizCreditorRightBO.class, crt.getCrId());
				loanApplyVO = this.loanApplyBS.findLoanApply(crbo.getLoanApproveId());
				if(loanApplyVO == null){
					this.alert("借款信息不存在");
				}
				
			} catch (BizException e) {
				this.alert("查询借款信息异常");
			}
			
		}
		
	}
	
	public void submit(){
		try {
			BizCustomerBO cust = custInfoBS.findCustByUserId(ViewOper.getUser().getUserId());
			if(cust == null){
				this.alert("该用户客户信息维护");
				return;
			}
			crvo.setCustId(cust.getId());
			BigDecimal minAmt = AppConst.MIN_TENDER_AMT;
			crvo.setCrAmt(minAmt.multiply(new BigDecimal(crvo.getTurnoverCount())));
			creditorRightBS.submitTrenderTran(crvo);
			this.alertInfo("接手成功");
		} catch (BizException e) {
			this.alert("债权接手失败"+e.getMessage());
		}
		
	}

	public LoanApplyVO getLoanApplyVO() {
		return loanApplyVO;
	}

	public void setLoanApplyVO(LoanApplyVO loanApplyVO) {
		this.loanApplyVO = loanApplyVO;
	}

	public BizCreditorRightTranBO getCrt() {
		return crt;
	}

	public void setCrt(BizCreditorRightTranBO crt) {
		this.crt = crt;
	}

	public CreditorRightVO getCrvo() {
		return crvo;
	}

	public void setCrvo(CreditorRightVO crvo) {
		this.crvo = crvo;
	}

	public ICreditorRightBS getCreditorRightBS() {
		return creditorRightBS;
	}

	public void setCreditorRightBS(ICreditorRightBS creditorRightBS) {
		this.creditorRightBS = creditorRightBS;
	}

	public ILoanApplyBS getLoanApplyBS() {
		return loanApplyBS;
	}

	public void setLoanApplyBS(ILoanApplyBS loanApplyBS) {
		this.loanApplyBS = loanApplyBS;
	}

	public ICustInfoBS getCustInfoBS() {
		return custInfoBS;
	}

	public void setCustInfoBS(ICustInfoBS custInfoBS) {
		this.custInfoBS = custInfoBS;
	}
	
	
}
