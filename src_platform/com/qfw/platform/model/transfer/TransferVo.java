package com.qfw.platform.model.transfer;

import java.io.Serializable;
import java.math.BigDecimal;

public class TransferVo implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 转让申请id
	 */
	private Integer creditorId;

	/**
	 * 发布编号
	 */
	private Integer loanApproveId;

	/**
	 * 债权id
	 */
	private Integer crId;

	/**
	 * 借款人
	 */
	private Integer loanCustId;
	
	/**
	 * 投资人
	 */
	private Integer custId;
	
	
	/**
	 * 借款标题
	 */
	private String loanName;

	/**
	 * 信用等级
	 */
	private String creditRate;

	/**
	 * 借款年利率
	 */
	private BigDecimal loanRate;

	/**
	 * 还款方式
	 */
	private String repayTypeCd;

	/**
	 * 转让总份数
	 */
	private Integer tranTtlCount;

	/**
	 * 转让总金额
	 */
	private BigDecimal  tranTtlAmt ;

	/**
	 * 剩余期数
	 */
	private Integer surplusPeriod;

	/**
	 * 剩余份数
	 */
	private Integer remainCount;
	
	/**
	 * 每份金额
	 */
	private BigDecimal  limitAmt ;

	/**
	 * 接手奖金
	 */
	private BigDecimal takeAmt;
	
	/**
	 * 担保方式
	 */
	private String loanTypeCd;

	/**
	 * 进度
	 */
	private BigDecimal completeness;
	
	/**
	 * 结清日期
	 */
	private String settleDate;
	

	/**
	 * 债权转让状态
	 */
	private String crtStatusCd;
	
	private String repayTypeCdStr;
	

	public Integer getCreditorId() {
		return creditorId;
	}

	public void setCreditorId(Integer creditorId) {
		this.creditorId = creditorId;
	}

	public Integer getCrId() {
		return crId;
	}

	public void setCrId(Integer crId) {
		this.crId = crId;
	}

	public Integer getLoanCustId() {
		return loanCustId;
	}

	public void setLoanCustId(Integer loanCustId) {
		this.loanCustId = loanCustId;
	}

	public String getLoanName() {
		return loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	public String getCreditRate() {
		return creditRate;
	}

	public void setCreditRate(String creditRate) {
		this.creditRate = creditRate;
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

	public Integer getTranTtlCount() {
		return tranTtlCount;
	}

	public void setTranTtlCount(Integer tranTtlCount) {
		this.tranTtlCount = tranTtlCount;
	}

	public BigDecimal getTranTtlAmt() {
		return tranTtlAmt;
	}

	public void setTranTtlAmt(BigDecimal tranTtlAmt) {
		this.tranTtlAmt = tranTtlAmt;
	}

	public Integer getSurplusPeriod() {
		return surplusPeriod;
	}

	public void setSurplusPeriod(Integer surplusPeriod) {
		this.surplusPeriod = surplusPeriod;
	}

	public Integer getRemainCount() {
		return remainCount;
	}

	public void setRemainCount(Integer remainCount) {
		this.remainCount = remainCount;
	}

	public BigDecimal getLimitAmt() {
		return limitAmt;
	}

	public void setLimitAmt(BigDecimal limitAmt) {
		this.limitAmt = limitAmt;
	}

	public BigDecimal getTakeAmt() {
		return takeAmt;
	}

	public void setTakeAmt(BigDecimal takeAmt) {
		this.takeAmt = takeAmt;
	}

	public BigDecimal getCompleteness() {
		return completeness;
	}

	public void setCompleteness(BigDecimal completeness) {
		this.completeness = completeness;
	}

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public String getLoanTypeCd() {
		return loanTypeCd;
	}

	public void setLoanTypeCd(String loanTypeCd) {
		this.loanTypeCd = loanTypeCd;
	}

	public String getCrtStatusCd() {
		return crtStatusCd;
	}

	public void setCrtStatusCd(String crtStatusCd) {
		this.crtStatusCd = crtStatusCd;
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

	public String getRepayTypeCdStr() {
		return repayTypeCdStr;
	}

	public void setRepayTypeCdStr(String repayTypeCdStr) {
		this.repayTypeCdStr = repayTypeCdStr;
	}
}