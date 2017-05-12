package com.qfw.model.vo.custinfo.account;

import java.math.BigDecimal;
import java.util.Date;

public class PMInfoVO {

	private Integer accountDetailId;  // 明细id
	private String userCode;  // 登录编号
	private String userName; // 登录名
	private String custName;  // 真实姓名
	private String eventTypeCd;	//交易类型
	private BigDecimal pmAmt;	//PM币
	private Date txDate;	//交易日期
	
	public Integer getAccountDetailId() {
		return accountDetailId;
	}
	public void setAccountDetailId(Integer accountDetailId) {
		this.accountDetailId = accountDetailId;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public BigDecimal getPmAmt() {
		return pmAmt;
	}
	public void setPmAmt(BigDecimal pmAmt) {
		this.pmAmt = pmAmt;
	}
	public Date getTxDate() {
		return txDate;
	}
	public void setTxDate(Date txDate) {
		this.txDate = txDate;
	}
	public String getEventTypeCd() {
		return eventTypeCd;
	}
	public void setEventTypeCd(String eventTypeCd) {
		this.eventTypeCd = eventTypeCd;
	}
}
