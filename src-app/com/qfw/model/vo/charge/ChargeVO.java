package com.qfw.model.vo.charge;

import java.math.BigDecimal;

public class ChargeVO {

	private Integer custId;//会员ID
	private Integer relateId;//关联ID
	private String relateTypeCd;//关联类型
	private BigDecimal costBasicAmt;//计费金额
	private String costTypeCd;//费用类型
	private String txNO;//交易编号
	private Integer loanTerm;//借款期限 

	public Integer getCustId() {
		return custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	public Integer getRelateId() {
		return relateId;
	}

	public void setRelateId(Integer relateId) {
		this.relateId = relateId;
	}

	public String getRelateTypeCd() {
		return relateTypeCd;
	}

	public void setRelateTypeCd(String relateTypeCd) {
		this.relateTypeCd = relateTypeCd;
	}

	public BigDecimal getCostBasicAmt() {
		return costBasicAmt;
	}

	public void setCostBasicAmt(BigDecimal costBasicAmt) {
		this.costBasicAmt = costBasicAmt;
	}

	public String getCostTypeCd() {
		return costTypeCd;
	}

	public void setCostTypeCd(String costTypeCd) {
		this.costTypeCd = costTypeCd;
	}

	public String getTxNO() {
		return txNO;
	}

	public void setTxNO(String txNO) {
		this.txNO = txNO;
	}

	public Integer getLoanTerm() {
		return loanTerm;
	}

	public void setLoanTerm(Integer loanTerm) {
		this.loanTerm = loanTerm;
	}

}
