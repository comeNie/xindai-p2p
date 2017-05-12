package com.qfw.bean.loan;

/**
 * 借款申请信息Bean
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

import com.qfw.bizservice.bussparams.IProductInfoBS;
import com.qfw.bizservice.credit.ICreditLimitApplyBS;
import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.bizservice.loan.ILoanApplyBS;
import com.qfw.common.bizservice.IParamBS;
import com.qfw.common.bizservice.permission.IUserBS;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizCreditReportBO;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.bo.BizLoanApproveBO;
import com.qfw.model.vo.bussparams.ProductInfoVO;
import com.qfw.model.vo.credit.BizCreditLimitApplyVO;
import com.qfw.model.vo.loan.LoanApplyVO;

@ViewScoped
@ManagedBean(name = "loanInfoManageBean")
public class LoanInfoManageBackingBean extends BaseBackingBean {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value = "#{loanApplyBS}")
	private ILoanApplyBS loanApplyBS;
	@ManagedProperty(value = "#{creditLimitApplyBS}")
	private ICreditLimitApplyBS creditLimitApplyBS;
	@ManagedProperty(value = "#{productInfoBS}")
	private IProductInfoBS productInfoBS;
	@ManagedProperty(value = "#{paramBS}")
	private IParamBS paramBS;
	@ManagedProperty(value="#{userBS}")
	private IUserBS userBS;
	@ManagedProperty(value = "#{custInfoBS}")
	private ICustInfoBS custInfoBS;
	
	private LoanApplyVO loanApplyVO = new LoanApplyVO();
	private ProductInfoVO productInfoVO = new ProductInfoVO();
	private BizCreditLimitApplyVO creditLimitApplyVO = new BizCreditLimitApplyVO();
	private SysUser selectPartyUser = new SysUser();
	
	private BizCreditReportBO creditReportVO = new BizCreditReportBO();
	
	@PostConstruct
	public void init() {
		try {
			Object loanApplyId = ViewOper.getSessionTmpAttr("loanApplyId");
			Object loanApproveId = ViewOper.getSessionTmpAttr("loanApproveId");
			Integer applyId = 0;
			if (null != loanApplyId && (loanApplyId instanceof Integer)) {
				applyId =(Integer) loanApplyId;
			}else if(null != loanApproveId && (loanApproveId instanceof Integer)){
				BizLoanApproveBO approvebo = this.loanApplyBS.getLoanApprove((Integer) loanApproveId);
				if(null != approvebo){
					applyId = Integer.valueOf(approvebo.getLoanApplyId());
				}
			}else{
				return ;
			}
			
			loanApplyVO.setId(applyId);
			loanApplyVO = this.loanApplyBS.findVOByParams(loanApplyVO);
			productInfoVO = this.productInfoBS
					.findProductInfoById(loanApplyVO.getProductId());

			String advanceRate = paramBS
					.getParam(AppConst.PARAMETER_CODE_TQHKWYJBL);
			String delayRate = paramBS
					.getParam(AppConst.PARAMETER_CODE_ZQSFBL);
			String overdueRate = paramBS
					.getParam(AppConst.PARAMETER_CODE_YQSFBL);
			if (advanceRate == null || "".equals(advanceRate)
					|| delayRate == null || "".equals(delayRate)
					|| overdueRate == null || "".equals(overdueRate)) {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_WARN, "借款申请信息参数获取失败", null);
				FacesContext.getCurrentInstance().addMessage(null, message);
				return;
			}
			loanApplyVO.setAdvanceRate(new BigDecimal(advanceRate));
			loanApplyVO.setDelayRate(new BigDecimal(delayRate));
			loanApplyVO.setOverdueRate(new BigDecimal(overdueRate));
			// 初始化认证信息
			this.loanApplyBS.initLoanApplyAuth(loanApplyVO);

			// 初始化标签页信息
			this.creditLimitApplyBS.initCreditLimitApplyVOByCustId(
					loanApplyVO.getCustId(), creditLimitApplyVO);
		
			if(null!=loanApplyVO.getTrusteeCustId()&&loanApplyVO.getTrusteeCustId()!=0){
				BizCustomerBO cust = this.custInfoBS.findCustById(loanApplyVO.getTrusteeCustId());
				selectPartyUser = userBS.findUserByUserId(cust.getUserId());
			}
			
			if(null!=loanApplyVO.getCustId()){
				creditReportVO = this.loanApplyBS.initCreditReportVO(loanApplyVO.getCustId());
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

	public ICreditLimitApplyBS getCreditLimitApplyBS() {
		return creditLimitApplyBS;
	}

	public void setCreditLimitApplyBS(ICreditLimitApplyBS creditLimitApplyBS) {
		this.creditLimitApplyBS = creditLimitApplyBS;
	}

	public IProductInfoBS getProductInfoBS() {
		return productInfoBS;
	}

	public void setProductInfoBS(IProductInfoBS productInfoBS) {
		this.productInfoBS = productInfoBS;
	}

	public ProductInfoVO getProductInfoVO() {
		return productInfoVO;
	}

	public void setProductInfoVO(ProductInfoVO productInfoVO) {
		this.productInfoVO = productInfoVO;
	}

	public IParamBS getParamBS() {
		return paramBS;
	}

	public void setParamBS(IParamBS paramBS) {
		this.paramBS = paramBS;
	}

	public BizCreditLimitApplyVO getCreditLimitApplyVO() {
		return creditLimitApplyVO;
	}

	public void setCreditLimitApplyVO(BizCreditLimitApplyVO creditLimitApplyVO) {
		this.creditLimitApplyVO = creditLimitApplyVO;
	}
	
	public SysUser getSelectPartyUser() {
		return selectPartyUser;
	}

	public void setSelectPartyUser(SysUser selectPartyUser) {
		this.selectPartyUser = selectPartyUser;
	}

	public IUserBS getUserBS() {
		return userBS;
	}

	public void setUserBS(IUserBS userBS) {
		this.userBS = userBS;
	}

	public BizCreditReportBO getCreditReportVO() {
		return creditReportVO;
	}

	public void setCreditReportVO(BizCreditReportBO creditReportVO) {
		this.creditReportVO = creditReportVO;
	}

	public ICustInfoBS getCustInfoBS() {
		return custInfoBS;
	}

	public void setCustInfoBS(ICustInfoBS custInfoBS) {
		this.custInfoBS = custInfoBS;
	}
	
}
