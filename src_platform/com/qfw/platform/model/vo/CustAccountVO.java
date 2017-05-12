package com.qfw.platform.model.vo;

import java.math.BigDecimal;

public class CustAccountVO {
	
	private Integer custId;//客户号
	private BigDecimal accBal;//账户余额
	private BigDecimal  avaiBal;//可用余额
	private Integer  buyCount;//可购买份数
	public Integer getCustId() {
		return custId;
	}
	public void setCustId(Integer custId) {
		this.custId = custId;
	}
	 
	public BigDecimal getAccBal() {
		return accBal;
	}
	public void setAccBal(BigDecimal accBal) {
		this.accBal = accBal;
	}
	public BigDecimal getAvaiBal() {
		return avaiBal;
	}
	public void setAvaiBal(BigDecimal avaiBal) {
		this.avaiBal = avaiBal;
	}
	public Integer getBuyCount() {
		return buyCount;
	}
	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}

}
