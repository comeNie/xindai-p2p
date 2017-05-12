package com.qfw.model.vo.repay;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.qfw.common.exception.BizException;
import com.qfw.model.bo.BizLoanBO;
import com.qfw.model.bo.BizRepayPlanDetailBO;

public class RepayPlanDetailVO {
	
	private Integer loanId;//借款ID
	private Integer loanApproveId;//借款发布ID
	private Integer custId;//会员ID
	
	private BigDecimal loanAmt;// 贷款金额
	private String repayTypeCD;// 还款类型
	private Date loanDate;// 贷款日期
	private Date loanDueDate;// 贷款到期日期
	private Integer repayDateNum;// 约定还款日
	private Integer repayCycle;// 还款周期
	private String cycleUnitCD;// 周期单位
	private BigDecimal loanRate;// 贷款利率
	private Integer loanTerm;//融资期限

	private BizLoanBO bizLoanBO; //借款BO
	
	//返回值
	private List<BizRepayPlanDetailBO> bizRepayPlanDetailBOs;//还款计划
	private BigDecimal ttlPrincipalInterestAmt;//总本息
	private BigDecimal ttlInterestAmt;//总利息
	private Date firstRepayDate ; //首期还款日
	
	public RepayPlanDetailVO(){
		super();
	}
	
	public RepayPlanDetailVO(BizLoanBO bizLoanBO,Integer repayDateNum) throws BizException{
		super();
		if(bizLoanBO==null) throw new BizException("参数[bizLoanBO]不能为空");
		this.bizLoanBO = bizLoanBO;
		this.loanId =bizLoanBO.getId();
		this.custId =bizLoanBO.getCustId();
		
		this.loanAmt=bizLoanBO.getLoanAmt();
		this.repayTypeCD=bizLoanBO.getRepayTypeCd();
		this.loanDate=bizLoanBO.getLoanDate();
		this.loanDueDate=bizLoanBO.getLoanDueDate();
		this.repayDateNum=repayDateNum;
		this.repayCycle=bizLoanBO.getRepayCycle();
		this.cycleUnitCD=bizLoanBO.getCycleUnitCd();
		this.loanRate=bizLoanBO.getLoanRate();
		this.loanTerm = bizLoanBO.getLoanTerm();
		
	}
	
	public Integer getRepayDateNum() {
		return repayDateNum;
	}

	public void setRepayDateNum(Integer repayDateNum) {
		this.repayDateNum = repayDateNum;
	}

	public Date getLoanDueDate() {
		return loanDueDate;
	}

	public void setLoanDueDate(Date loanDueDate) {
		this.loanDueDate = loanDueDate;
	}
	
	public BigDecimal getLoanAmt() {
		return loanAmt;
	}

	public void setLoanAmt(BigDecimal loanAmt) {
		this.loanAmt = loanAmt;
	}

	public String getRepayTypeCD() {
		return repayTypeCD;
	}

	public void setRepayTypeCD(String repayTypeCD) {
		this.repayTypeCD = repayTypeCD;
	}

	public Date getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}


	public Integer getRepayCycle() {
		return repayCycle;
	}

	public void setRepayCycle(Integer repayCycle) {
		this.repayCycle = repayCycle;
	}

	public String getCycleUnitCD() {
		return cycleUnitCD;
	}

	public void setCycleUnitCD(String cycleUnitCD) {
		this.cycleUnitCD = cycleUnitCD;
	}


	public BigDecimal getLoanRate() {
		return loanRate;
	}

	public void setLoanRate(BigDecimal loanRate) {
		this.loanRate = loanRate;
	}

	public Integer getLoanId() {
		return loanId;
	}

	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}

	public Integer getLoanApproveId() {
		return loanApproveId;
	}

	public void setLoanApproveId(Integer loanApproveId) {
		this.loanApproveId = loanApproveId;
	}

	public Integer getCustId() {
		return custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}
	
	public BizLoanBO getBizLoanBO() {
		return bizLoanBO;
	}

	public void setBizLoanBO(BizLoanBO bizLoanBO) {
		if(bizLoanBO==null) return ;
		this.bizLoanBO = bizLoanBO;
		this.loanId =bizLoanBO.getId();
		this.custId =bizLoanBO.getCustId();
		
		this.loanAmt=bizLoanBO.getLoanAmt();
		this.repayTypeCD=bizLoanBO.getRepayTypeCd();
		this.loanDate=bizLoanBO.getLoanDate();
		this.loanDueDate=bizLoanBO.getLoanDueDate();
		this.repayCycle=bizLoanBO.getRepayCycle();
		this.cycleUnitCD=bizLoanBO.getCycleUnitCd();
		this.loanRate=bizLoanBO.getLoanRate();
	}

	public List<BizRepayPlanDetailBO> getBizRepayPlanDetailBOs() {
		return bizRepayPlanDetailBOs;
	}

	public void setBizRepayPlanDetailBOs(
			List<BizRepayPlanDetailBO> bizRepayPlanDetailBOs) {
		this.bizRepayPlanDetailBOs = bizRepayPlanDetailBOs;
	}

	public BigDecimal getTtlPrincipalInterestAmt() {
		return ttlPrincipalInterestAmt;
	}

	public void setTtlPrincipalInterestAmt(BigDecimal ttlPrincipalInterestAmt) {
		this.ttlPrincipalInterestAmt = ttlPrincipalInterestAmt;
	}

	public BigDecimal getTtlInterestAmt() {
		return ttlInterestAmt;
	}

	public void setTtlInterestAmt(BigDecimal ttlInterestAmt) {
		this.ttlInterestAmt = ttlInterestAmt;
	}

	public Date getFirstRepayDate() {
		return firstRepayDate;
	}

	public void setFirstRepayDate(Date firstRepayDate) {
		this.firstRepayDate = firstRepayDate;
	}

	public Integer getLoanTerm() {
		return loanTerm;
	}

	public void setLoanTerm(Integer loanTerm) {
		this.loanTerm = loanTerm;
	}
	
	
}
