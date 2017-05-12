package com.qfw.model.vo.payout;

import java.math.BigDecimal;
import java.util.Date;

public class WithdrawalsResponseVO {

	private Integer id;
	private Integer custId;			
	private BigDecimal txAmt;
	private Date txDate;
	private String ctrStateCd;
	private String outAccount;			// 转出账户
	private String inputAccount;		// 转入账户
	private BigDecimal feeAmt;		// 提现费用
	private String tradeNum;			// 交易编号
	
	private String userCode;  // 登录编号
	private String userName; // 登录名
	private String custName;  // 真实姓名
	
	private String phone;		//	手机
	private String bank;		//  提现银行
	private Date applyDate;		// 	申请日期
	
	private String accountName;
	private String bankName;
	private String areaProvince;
	private String areaCity;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public BigDecimal getTxAmt() {
		return txAmt;
	}
	public void setTxAmt(BigDecimal txAmt) {
		this.txAmt = txAmt;
	}
	public Date getTxDate() {
		return txDate;
	}
	public void setTxDate(Date txDate) {
		this.txDate = txDate;
	}
	public String getCtrStateCd() {
		return ctrStateCd;
	}
	public void setCtrStateCd(String ctrStateCd) {
		this.ctrStateCd = ctrStateCd;
	}
	public String getOutAccount() {
		return outAccount;
	}
	public void setOutAccount(String outAccount) {
		this.outAccount = outAccount;
	}
	public String getInputAccount() {
		return inputAccount;
	}
	public void setInputAccount(String inputAccount) {
		this.inputAccount = inputAccount;
	}
	public BigDecimal getFeeAmt() {
		return feeAmt;
	}
	public void setFeeAmt(BigDecimal feeAmt) {
		this.feeAmt = feeAmt;
	}
	public String getTradeNum() {
		return tradeNum;
	}
	public void setTradeNum(String tradeNum) {
		this.tradeNum = tradeNum;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public Integer getCustId() {
		return custId;
	}
	public void setCustId(Integer custId) {
		this.custId = custId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getAreaProvince() {
		return areaProvince;
	}
	public void setAreaProvince(String areaProvince) {
		this.areaProvince = areaProvince;
	}
	public String getAreaCity() {
		return areaCity;
	}
	public void setAreaCity(String areaCity) {
		this.areaCity = areaCity;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
}
