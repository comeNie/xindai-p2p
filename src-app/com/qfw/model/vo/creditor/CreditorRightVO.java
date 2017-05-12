package com.qfw.model.vo.creditor;

import java.math.BigDecimal;

/**
 * 债权投资参数VO
 * @author kindion
 *
 */
public class CreditorRightVO {

	private Integer custId;//投资人会员ID
	private Integer loanApproveId;//发布表信息ID
	private Integer creditorRightTranId;//债权转让发布表ID,债权转让投资使用
	private BigDecimal crAmt;//投资金额
	private Integer turnoverCount;//投资份数
	private String tenderTypeCd;//投资类型,自动投标0\手动投标1
	
	public Integer getCustId() {
		return custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	public Integer getLoanApproveId() {
		return loanApproveId;
	}

	public void setLoanApproveId(Integer loanApproveId) {
		this.loanApproveId = loanApproveId;
	}

	public BigDecimal getCrAmt() {
		return crAmt;
	}

	public void setCrAmt(BigDecimal crAmt) {
		this.crAmt = crAmt;
	}

	public Integer getTurnoverCount() {
		return turnoverCount;
	}

	public void setTurnoverCount(Integer turnoverCount) {
		this.turnoverCount = turnoverCount;
	}

	public String getTenderTypeCd() {
		return tenderTypeCd;
	}

	public void setTenderTypeCd(String tenderTypeCd) {
		this.tenderTypeCd = tenderTypeCd;
	}

	public Integer getCreditorRightTranId() {
		return creditorRightTranId;
	}

	public void setCreditorRightTranId(Integer creditorRightTranId) {
		this.creditorRightTranId = creditorRightTranId;
	}
	
}
