package com.qfw.bean.creditor;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.qfw.bizservice.creditor.ICreditorManageBS;
import com.qfw.bizservice.creditor.ICreditorRightBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.util.web.WorkFlowBackingBean;
import com.qfw.model.bo.BizCreditorRightBO;
import com.qfw.model.vo.creditor.CreditorRightVO;

@ViewScoped
@ManagedBean(name="tenderBean")
public class TenderBean extends WorkFlowBackingBean {
	
	private BizCreditorRightBO crbo = new BizCreditorRightBO();
	@ManagedProperty(value = "#{creditorManageBS}")
	private ICreditorManageBS creditorManageBS;

	@ManagedProperty(value = "#{creditorRightBS}")
	private ICreditorRightBS creditorRightBS ;
	
	public String submit(){
		try {
			CreditorRightVO rightVO = new CreditorRightVO();
			rightVO.setCrAmt(crbo.getCrAmt());
			rightVO.setCustId(crbo.getCustId());
			rightVO.setLoanApproveId(crbo.getLoanApproveId());
			rightVO.setTenderTypeCd(crbo.getTenderTypeCd());
			rightVO.setTurnoverCount(crbo.getTurnoverCount());
			creditorRightBS.submitTrender(rightVO);
		} catch (BizException e) {
			this.alert(e.getMessage());
		}
		return null;
	}
	
	
	
	public ICreditorManageBS getCreditorManageBS() {
		return creditorManageBS;
	}

	public void setCreditorManageBS(ICreditorManageBS creditorManageBS) {
		this.creditorManageBS = creditorManageBS;
	}

	public BizCreditorRightBO getCrbo() {
		return crbo;
	}

	public void setCrbo(BizCreditorRightBO crbo) {
		this.crbo = crbo;
	}
}
