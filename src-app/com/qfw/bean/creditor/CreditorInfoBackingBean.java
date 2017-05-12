package com.qfw.bean.creditor;

/**
 * 显示债券转让信息Bean
 * 
 * @author qswei
 */



import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
import com.qfw.common.util.CollectionUtil;
import com.qfw.common.util.StringUtils;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizCreditorRightBO;
import com.qfw.model.bo.BizCreditorRightTranBO;
import com.qfw.model.vo.creditor.CreditorRightTranVO;
import com.qfw.model.vo.loan.LoanApplyVO;

@ViewScoped
@ManagedBean(name="creditorInfoBean")
public class CreditorInfoBackingBean extends BaseBackingBean{
    
	
	
	@ManagedProperty(value = "#{loanApplyBS}")
	private ILoanApplyBS loanApplyBS;
	
	@ManagedProperty(value = "#{creditorManageBS}")
	private ICreditorManageBS creditorManageBS;
	
	@ManagedProperty(value = "#{paramBS}")
	private IParamBS paramBS;
	
	
	private LoanApplyVO loanApplyVO = new LoanApplyVO();
	
	private int loanApproveId;
	
	//private CreditorManageVO creditorVO= new CreditorManageVO();
	
	private CreditorRightTranVO crt = new CreditorRightTranVO();//债权转让信息表
	private BizCreditorRightBO bcr;
	
	private Integer loanApproveIdTemp;
	
	private String crId;
	
	private int ttlUseCont = 0;//债权转让发布的总份数
	
	private BigDecimal ttlUseAmt = BigDecimal.ZERO;//发布表总转让金额
	
	@ManagedProperty(value = "#{creditorRightBS}")
	private ICreditorRightBS creditorRightBS;
	
	

	@PostConstruct
    public void init(){
		try {
			loanApproveIdTemp = (Integer)ViewOper.getSessionTmpAttr("loanApproveId"); //债权ID
			crId = String.valueOf(ViewOper.getSessionTmpAttr("loanId"));
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
			if(bcr == null){
				this.alert("找不到可转让的债权信息");
			}else{
				crt.setCrId(bcr.getId());
				crt.setTranDate(new Date());
				List<BizCreditorRightTranBO> crts = creditorRightBS.findCreditorRightTransByCrId(bcr.getId());
				if(CollectionUtil.isNotEmpty(crts)){
					for (BizCreditorRightTranBO bizCreditorRightTranBO : crts) {
						ttlUseCont += bizCreditorRightTranBO.getTranTtlCount();
						ttlUseAmt = ttlUseAmt.add( bizCreditorRightTranBO.getTranTtlAmt());
					}
				}
			}
		}else{
			this.alert("找不到可转让的债权信息");
		}
	}
	
	/**
	 * 债权转让发布提交
	 */
    public void submitCreditorRight(){
    	
    	try {
    		if (null == crt.getTranTerm() || crt.getTranTerm() <= 0) {
    			this.alert("转让期限大于0<br/>");
    			return ;
    		}
			creditorRightBS.creditorTranApprove(crt);
			initData();
		} catch (Exception e) {
			 
			this.alert("转让失败"+e.getMessage());
			return;
		}
		this.alertInfo("转让成功");
		/*
    	try{
    		//根据债权发布ID查找相应信息
    		/*creditorVO = this.creditorManageBS.findInfoById(loanApproveId);
    		creditorRightTranBO.setCrId(creditorVO.getCreditorId()); //债权ID
    		creditorRightTranBO.setSurplusPeriod(creditorVO.getTerm()); //剩余期数
    		creditorRightTranBO.setLoanRate(loanApplyVO.getExpectLoanRate()); //年利率
    		creditorRightTranBO.setTranOutCount(10);  //转出份数
    	  //creditorRightTranBO.setTranTtlCount();  //剩余总份数 原来总份数-转出份数
    	  //creditorRightTranBO.setTranTtlAmt(tranTtlAmt); //债权总额  总份数 * 债权金额
    	  //creditorRightTranBO.setTranOutAmt();  //转出债权总额  转出份数 * 转让金额
    		creditorRightTranBO.setTakeAmt(takeAmt); //接手奖金
    		creditorRightTranBO.setCrtStatusCd("0"); //状态
    		this.creditorManageBS.addCreditorRightTran(creditorRightTranBO)
    		
    	}catch(Exception e){
    		       
    		alert("债权转让失败！");
    	}
    	;*/
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

	public CreditorRightTranVO getCrt() {
		return crt;
	}

	public void setCrt(CreditorRightTranVO crt) {
		this.crt = crt;
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

	
}
