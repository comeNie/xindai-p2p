package com.qfw.model.vo.custinfo.account;

import java.math.BigDecimal;

/**
 * 用户账号信息查询vo
 *
 * @author kyc
 */
public class AccountRequestVO {

	private Integer id;
	private Integer custId;
	private String account;
	private String workItemId;
	private BigDecimal dealAmt;		//交易金额
	private String dealType;		//金额操作类型
	private String txNo;			// 交易编号
	private String eventTypeCd;		// 交易类型
	private String accountType;		//帐号类型
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCustId() {
		return custId;
	}
	public void setCustId(Integer custId) {
		this.custId = custId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getWorkItemId() {
		return workItemId;
	}
	public void setWorkItemId(String workItemId) {
		this.workItemId = workItemId;
	}
	public BigDecimal getDealAmt() {
		return dealAmt;
	}
	public void setDealAmt(BigDecimal dealAmt) {
		this.dealAmt = dealAmt;
	}
	public String getDealType() {
		return dealType;
	}
	public void setDealType(String dealType) {
		this.dealType = dealType;
	}
	public String getTxNo() {
		return txNo;
	}
	public void setTxNo(String txNo) {
		this.txNo = txNo;
	}
	public String getEventTypeCd() {
		return eventTypeCd;
	}
	public void setEventTypeCd(String eventTypeCd) {
		this.eventTypeCd = eventTypeCd;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
}
