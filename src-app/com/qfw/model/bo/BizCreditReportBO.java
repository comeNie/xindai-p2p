package com.qfw.model.bo;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BizCreditLimit entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "biz_credit_report")
public class BizCreditReportBO implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer custId;			//会员ID
	private BigDecimal creditAmt;	//信用额度
	private BigDecimal remainAmt;	//剩余额度
	private Integer applyLoanNum;	//申请借款笔数
	private Integer approveNum;		//成功借款笔数
	private Integer payOffNum;		//还清笔数
	private BigDecimal loanTolAmt;	//还款总金额
	private BigDecimal overdueAmt;	//逾期总额
	private Integer overdueNum;	//逾期次数
	private Integer serOverdueNum;	//严重逾期
	private BigDecimal principaInterestAmt;//待还本息
	private BigDecimal loanBal;//贷款余额
	
	// Constructors

	/** default constructor */
	public BizCreditReportBO() {
	}

	/** full constructor */
	public BizCreditReportBO(Integer custId,BigDecimal creditAmt,BigDecimal remainAmt,	
			Integer applyLoanNum,Integer approveNum,Integer payOffNum,BigDecimal loanTolAmt,
			BigDecimal overdueAmt,Integer overdueNum,Integer serOverdueNum,BigDecimal principaInterestAmt,BigDecimal loanBal) {
		this.custId = custId;
		this.creditAmt = creditAmt;
		this.remainAmt = remainAmt;
		this.applyLoanNum = applyLoanNum;
		this.approveNum = approveNum;
		this.payOffNum = payOffNum;
		this.loanTolAmt = loanTolAmt;
		this.overdueAmt = overdueAmt;
		this.overdueNum = overdueNum;
		this.serOverdueNum = serOverdueNum;
		this.principaInterestAmt = principaInterestAmt;
		this.loanBal = loanBal;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "CUST_ID")
	public Integer getCustId() {
		return custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	@Column(name = "credit_amt")
	public BigDecimal getCreditAmt() {
		return creditAmt;
	}

	public void setCreditAmt(BigDecimal creditAmt) {
		this.creditAmt = creditAmt;
	}

	@Column(name = "remain_amt")
	public BigDecimal getRemainAmt() {
		return remainAmt;
	}

	public void setRemainAmt(BigDecimal remainAmt) {
		this.remainAmt = remainAmt;
	}

	@Column(name = "apply_loan_num")
	public Integer getApplyLoanNum() {
		return applyLoanNum;
	}

	public void setApplyLoanNum(Integer applyLoanNum) {
		this.applyLoanNum = applyLoanNum;
	}

	@Column(name = "approve_num")
	public Integer getApproveNum() {
		return approveNum;
	}

	public void setApproveNum(Integer approveNum) {
		this.approveNum = approveNum;
	}

	@Column(name = "Pay_off_num")
	public Integer getPayOffNum() {
		return payOffNum;
	}

	public void setPayOffNum(Integer payOffNum) {
		this.payOffNum = payOffNum;
	}

	@Column(name = "loan_tol_amt")
	public BigDecimal getLoanTolAmt() {
		return loanTolAmt;
	}

	public void setLoanTolAmt(BigDecimal loanTolAmt) {
		this.loanTolAmt = loanTolAmt;
	}

	@Column(name = "overdue_amt")
	public BigDecimal getOverdueAmt() {
		return overdueAmt;
	}

	public void setOverdueAmt(BigDecimal overdueAmt) {
		this.overdueAmt = overdueAmt;
	}

	@Column(name = "overdue_num")
	public Integer getOverdueNum() {
		return overdueNum;
	}

	public void setOverdueNum(Integer overdueNum) {
		this.overdueNum = overdueNum;
	}

	@Column(name = "Ser_overdue_num")
	public Integer getSerOverdueNum() {
		return serOverdueNum;
	}

	public void setSerOverdueNum(Integer serOverdueNum) {
		this.serOverdueNum = serOverdueNum;
	}

	@Column(name = "principa_interest_amt")
	public BigDecimal getPrincipaInterestAmt() {
		return principaInterestAmt;
	}
    
	public void setPrincipaInterestAmt(BigDecimal principaInterestAmt) {
		this.principaInterestAmt = principaInterestAmt;
	}
     
	@Column(name = "loan_bal")
	public BigDecimal getLoanBal() {
		return loanBal;
	}

	public void setLoanBal(BigDecimal loanBal) {
		this.loanBal = loanBal;
	}

}