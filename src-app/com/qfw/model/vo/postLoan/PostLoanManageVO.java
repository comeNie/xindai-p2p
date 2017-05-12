package com.qfw.model.vo.postLoan;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 贷后管理vo
 *
 * @author weiqs
 */

public class PostLoanManageVO {

	private Integer loanId; // 借据id
	private Integer loanApproveId;//借款发布id
	private String loanName; // 借款标题
	private Integer custId; // 用户id
	private String custName; // 会员名称
	private BigDecimal loanAmt; 	// 标的总额
	private BigDecimal loanBalAmt; // 剩余借款金额
	private BigDecimal loanRate;		// 利率
	private String repayTypeCd;		// 还款方式
	private Integer totalPeriod;	// 总期数
	private Integer surplusPeriod;	// 剩余期数
	private Integer repayedPeriod; //已还期数
	private String loanStatusCd; // 状态
	
	private String loanTypeCd; //借款类型
	private BigDecimal applyAmt; //借款金额
	private Integer loanTerm;  //借款期限
	private String termUnitCd; //借款期限单位
	private String loanPurpose;  //项目用途
	private Integer tenderTerm; //筹标期限
	private BigDecimal expectLoanRate; //期望年利率
	private Date applyDate;  //申请时间
	private String applyStatusCd;  //申请状态
	private Integer loanApplyId; //借款申请ID
	
	private Integer planId; // 还款计划id
	private Integer period;	// 当前期数
	private Date repayplanDate; // 应还日期
	private BigDecimal principalAmt; // 本期本金
	private BigDecimal principaInterestAmt;//本期利息
	private BigDecimal deplayInterestAmt; // 展期利息
	private BigDecimal overdateInterestAmt; // 逾期利息
	private String repayStatusCd; // 还款状态

	public Integer getLoanId() {
		return loanId;
	}
	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}
	public String getLoanName() {
		return loanName;
	}
	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	public Integer getCustId() {
		return custId;
	}
	public void setCustId(Integer custId) {
		this.custId = custId;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public BigDecimal getLoanBalAmt() {
		return loanBalAmt;
	}
	public void setLoanBalAmt(BigDecimal loanBalAmt) {
		this.loanBalAmt = loanBalAmt;
	}
	public String getLoanStatusCd() {
		return loanStatusCd;
	}
	public void setLoanStatusCd(String loanStatusCd) {
		this.loanStatusCd = loanStatusCd;
	}
	public BigDecimal getLoanAmt() {
		return loanAmt;
	}
	public void setLoanAmt(BigDecimal loanAmt) {
		this.loanAmt = loanAmt;
	}
	public BigDecimal getLoanRate() {
		return loanRate;
	}
	public void setLoanRate(BigDecimal loanRate) {
		this.loanRate = loanRate;
	}
	public String getRepayTypeCd() {
		return repayTypeCd;
	}
	public void setRepayTypeCd(String repayTypeCd) {
		this.repayTypeCd = repayTypeCd;
	}
	public Integer getTotalPeriod() {
		return totalPeriod;
	}
	public void setTotalPeriod(Integer totalPeriod) {
		this.totalPeriod = totalPeriod;
	}
	public Integer getSurplusPeriod() {
		return surplusPeriod;
	}
	public void setSurplusPeriod(Integer surplusPeriod) {
		this.surplusPeriod = surplusPeriod;
	}
	
	public Integer getRepayedPeriod() {
		return repayedPeriod;
	}
	public void setRepayedPeriod(Integer repayedPeriod) {
		this.repayedPeriod = repayedPeriod;
	}
	
	public Integer getLoanApplyId() {
		return loanApplyId;
	}
	public void setLoanApplyId(Integer loanApplyId) {
		this.loanApplyId = loanApplyId;
	}
	public BigDecimal getApplyAmt() {
		return applyAmt;
	}
	public void setApplyAmt(BigDecimal applyAmt) {
		this.applyAmt = applyAmt;
	}
	public Integer getLoanTerm() {
		return loanTerm;
	}
	public void setLoanTerm(Integer loanTerm) {
		this.loanTerm = loanTerm;
	}
	public String getTermUnitCd() {
		return termUnitCd;
	}
	public void setTermUnitCd(String termUnitCd) {
		this.termUnitCd = termUnitCd;
	}
	public Integer getTenderTerm() {
		return tenderTerm;
	}
	public void setTenderTerm(Integer tenderTerm) {
		this.tenderTerm = tenderTerm;
	}
	
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public String getLoanTypeCd() {
		return loanTypeCd;
	}
	public void setLoanTypeCd(String loanTypeCd) {
		this.loanTypeCd = loanTypeCd;
	}
	public String getLoanPurpose() {
		return loanPurpose;
	}
	public void setLoanPurpose(String loanPurpose) {
		this.loanPurpose = loanPurpose;
	}
	public BigDecimal getExpectLoanRate() {
		return expectLoanRate;
	}
	public void setExpectLoanRate(BigDecimal expectLoanRate) {
		this.expectLoanRate = expectLoanRate;
	}
	public String getApplyStatusCd() {
		return applyStatusCd;
	}
	public void setApplyStatusCd(String applyStatusCd) {
		this.applyStatusCd = applyStatusCd;
	}
	public Integer getLoanApproveId() {
		return loanApproveId;
	}
	public void setLoanApproveId(Integer loanApproveId) {
		this.loanApproveId = loanApproveId;
	}
	public Integer getPlanId() {
		return planId;
	}
	public void setPlanId(Integer planId) {
		this.planId = planId;
	}
	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	public Date getRepayplanDate() {
		return repayplanDate;
	}
	public void setRepayplanDate(Date repayplanDate) {
		this.repayplanDate = repayplanDate;
	}
	public BigDecimal getPrincipalAmt() {
		return principalAmt;
	}
	public void setPrincipalAmt(BigDecimal principalAmt) {
		this.principalAmt = principalAmt;
	}
	public BigDecimal getPrincipaInterestAmt() {
		return principaInterestAmt;
	}
	public void setPrincipaInterestAmt(BigDecimal principaInterestAmt) {
		this.principaInterestAmt = principaInterestAmt;
	}
	public BigDecimal getDeplayInterestAmt() {
		return deplayInterestAmt;
	}
	public void setDeplayInterestAmt(BigDecimal deplayInterestAmt) {
		this.deplayInterestAmt = deplayInterestAmt;
	}
	public BigDecimal getOverdateInterestAmt() {
		return overdateInterestAmt;
	}
	public void setOverdateInterestAmt(BigDecimal overdateInterestAmt) {
		this.overdateInterestAmt = overdateInterestAmt;
	}
	public String getRepayStatusCd() {
		return repayStatusCd;
	}
	public void setRepayStatusCd(String repayStatusCd) {
		this.repayStatusCd = repayStatusCd;
	}
	
}
