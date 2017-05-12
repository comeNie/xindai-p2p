package com.qfw.manager.model;

import java.io.Serializable;
import java.math.BigDecimal;


public class FinAccountVo implements Serializable {
  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer custId;
	private Integer buyCount;
	private BigDecimal crAmt;
	private BigDecimal totalProfitAmt;
	public Integer getCustId() {
		return custId;
	}
	public void setCustId(Integer custId) {
		this.custId = custId;
	}
	public Integer getBuyCount() {
		return buyCount;
	}
	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}
	public BigDecimal getCrAmt() {
		return crAmt;
	}
	public void setCrAmt(BigDecimal crAmt) {
		this.crAmt = crAmt;
	}
	public BigDecimal getTotalProfitAmt() {
		return totalProfitAmt;
	}
	public void setTotalProfitAmt(BigDecimal totalProfitAmt) {
		this.totalProfitAmt = totalProfitAmt;
	}
	 
	
	 
  
}
