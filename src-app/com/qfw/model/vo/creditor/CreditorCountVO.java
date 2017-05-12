package com.qfw.model.vo.creditor;

import java.math.BigDecimal;

public class CreditorCountVO {
	private Integer id;
	
	private String custName; // 会员名称
	
	private BigDecimal investAmt; // 投资总额
	
	private String date;			// 时间

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public BigDecimal getInvestAmt() {
		return investAmt;
	}

	public void setInvestAmt(BigDecimal investAmt) {
		this.investAmt = investAmt;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
		
}
