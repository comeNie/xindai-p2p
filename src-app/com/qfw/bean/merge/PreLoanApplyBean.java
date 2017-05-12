package com.qfw.bean.merge;

/**
 * 借款申请信息Bean
 * 
 * @author qswei
 */
import java.math.BigDecimal;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.qfw.bizservice.bussparams.IProductInfoBS;
import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.bizservice.loan.ILoanApplyBS;
import com.qfw.bizservice.merge.IMergeApplyBS;
import com.qfw.common.bizservice.IParamBS;
import com.qfw.common.bizservice.permission.IRoleBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.permission.SysRole;
import com.qfw.common.util.NumberUtils;
import com.qfw.common.util.web.ViewOper;
import com.qfw.common.util.web.WorkFlowAndPartyUserBackingBean;
import com.qfw.model.AppConst;
import com.qfw.model.vo.bussparams.ProductInfoVO;
import com.qfw.model.vo.custinfo.CustInfoVO;
import com.qfw.model.vo.loan.LoanApplyVO;

@ViewScoped
@ManagedBean(name="preLoanApplyBean")
public class PreLoanApplyBean extends WorkFlowAndPartyUserBackingBean{
    
	private static final long serialVersionUID = -6923850790865555276L;
	
	@ManagedProperty(value = "#{loanApplyBS}")
	private ILoanApplyBS loanApplyBS;
	@ManagedProperty(value="#{mergeApplyBS}")
	private IMergeApplyBS mergeApplyBS;
	@ManagedProperty(value = "#{productInfoBS}")
    private IProductInfoBS productInfoBS;
	@ManagedProperty(value = "#{paramBS}")
	private IParamBS paramBS;
	@ManagedProperty(value = "#{custInfoBS}")
	private ICustInfoBS custInfoBS;
	@ManagedProperty(value = "#{roleBS}")
	private IRoleBS roleBS;
	
    private Logger log = LogFactory.getInstance().getPlatformLogger();
    
	private SysRole nextRole;// 下一审核角色
	
	private SysRole curRole;// 当前审核角色
	
	public Map data;
	
    @PostConstruct
	public void init()  {
    	
    	try {
    		if(data == null){
        		data = (Map) ViewOper.getSessionTmpAttr("data");
        	}
    		//重新发起的，当前角色也是客户经理岗
			curRole = roleBS.findSysRole(null,	AppConst.WORKITEM_ROLE_CUST);// 当前角色
			nextRole = roleBS.findSysRole(null,	AppConst.WORKITEM_ROLE_ACCOUNT);// 获取客户经理岗
			getFilterUser().setRoleIds(String.valueOf(nextRole.getRoleId()));
		} catch (Exception e) {
			this.alert("借款申请分配客户经理 异常："+e.getMessage());
		}
		return;
    }
    
    
    public void save(){
    	try {
    		if (getSelectUser() == null
					|| NumberUtils.isBlank(getSelectUser().getUserId())) {
				alert("请选择客户经理");
			}else{
				loanApplyBS.submitCustManage((Integer)data.get("ID"), getSelectUser());
				executeJS("alert('提交成功');closeParMainDialog();");
			}
		} catch (BizException e) {
			this.alert(e.getMessage());
		}
    }
    
	public ILoanApplyBS getLoanApplyBS() {
		return loanApplyBS;
	}

	public void setLoanApplyBS(ILoanApplyBS loanApplyBS) {
		this.loanApplyBS = loanApplyBS;
	}

	

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public IMergeApplyBS getMergeApplyBS() {
		return mergeApplyBS;
	}

	public void setMergeApplyBS(IMergeApplyBS mergeApplyBS) {
		this.mergeApplyBS = mergeApplyBS;
	}

	public IProductInfoBS getProductInfoBS() {
		return productInfoBS;
	}

	public void setProductInfoBS(IProductInfoBS productInfoBS) {
		this.productInfoBS = productInfoBS;
	}

	public IParamBS getParamBS() {
		return paramBS;
	}

	public void setParamBS(IParamBS paramBS) {
		this.paramBS = paramBS;
	}


	public ICustInfoBS getCustInfoBS() {
		return custInfoBS;
	}

	public void setCustInfoBS(ICustInfoBS custInfoBS) {
		this.custInfoBS = custInfoBS;
	}


	public IRoleBS getRoleBS() {
		return roleBS;
	}

	public void setRoleBS(IRoleBS roleBS) {
		this.roleBS = roleBS;
	}

	public SysRole getNextRole() {
		return nextRole;
	}

	public void setNextRole(SysRole nextRole) {
		this.nextRole = nextRole;
	}

	public SysRole getCurRole() {
		return curRole;
	}

	public void setCurRole(SysRole curRole) {
		this.curRole = curRole;
	}

	public Map getData() {
		return data;
	}

	public void setData(Map data) {
		this.data = data;
	}
	
}
