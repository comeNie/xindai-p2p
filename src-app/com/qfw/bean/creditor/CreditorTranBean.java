package com.qfw.bean.creditor;

import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.qfw.bizservice.creditor.ICreditorManageBS;
import com.qfw.bizservice.creditor.ICreditorRightBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.util.StringUtils;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizCreditorRightBO;
import com.qfw.model.bo.BizCreditorRightTranBO;
import com.qfw.model.vo.creditor.CreditorRightTranVO;


@RequestScoped
@ManagedBean(name="creditorTranBean")
public class CreditorTranBean extends BaseBackingBean{
	
	private CreditorRightTranVO crt = new CreditorRightTranVO();//债权转让信息表
	private BizCreditorRightBO bcr;
	
	
	@ManagedProperty(value = "#{creditorRightBS}")
	private ICreditorRightBS creditorRightBS;
	
	@ManagedProperty(value = "#{creditorManageBS}")
	private ICreditorManageBS creditorManageBS;
	
	
	@PostConstruct
	public void init(){
		String crId = (String) ViewOper.getSessionTmpAttr("");
		if(StringUtils.isNotEmpty(crId)){
			bcr = (BizCreditorRightBO) creditorManageBS.find(BizCreditorRightBO.class, crId);
			if(bcr == null){
				this.alert("找不到可转让的债权信息");
			}
		}else{
			this.alert("找不到可转让的债权信息");
		}
	}
	
	public String submit(){
		try {
			creditorRightBS.creditorTranApprove(crt);
		} catch (Exception e) {
			this.alert(e.getMessage());
		}
		this.alertInfo("转让成功");
		return null;
	}

	public CreditorRightTranVO getCrt() {
		return crt;
	}

	public void setCrt(CreditorRightTranVO crt) {
		this.crt = crt;
	}

	public ICreditorRightBS getCreditorRightBS() {
		return creditorRightBS;
	}

	public void setCreditorRightBS(ICreditorRightBS creditorRightBS) {
		this.creditorRightBS = creditorRightBS;
	}

	public BizCreditorRightBO getBcr() {
		return bcr;
	}

	public void setBcr(BizCreditorRightBO bcr) {
		this.bcr = bcr;
	}

	public ICreditorManageBS getCreditorManageBS() {
		return creditorManageBS;
	}

	public void setCreditorManageBS(ICreditorManageBS creditorManageBS) {
		this.creditorManageBS = creditorManageBS;
	}
	public BigDecimal getMinTenderAmt(){
		return AppConst.MIN_TENDER_AMT;
	}
	

}
