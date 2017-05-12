package com.qfw.model.vo.payout;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付界面vo
 *
 * @author kyc
 */
public class RechargeVO {

	private String userID;				// 用户id 
	private Integer accountID;			// 账号id
	private String account;				// 账号
	private String rechargeType;		// 充值类型
	private BigDecimal dealAmt;			// 充值金额
	private String password;			// 密码
	private String cardCD;				// 卡号
	private Date txDate;				// 交易日期
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public Integer getAccountID() {
		return accountID;
	}
	public void setAccountID(Integer accountID) {
		this.accountID = accountID;
	}
	public String getRechargeType() {
		return rechargeType;
	}
	public void setRechargeType(String rechargeType) {
		this.rechargeType = rechargeType;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public BigDecimal getDealAmt() {
		return dealAmt;
	}
	public void setDealAmt(BigDecimal dealAmt) {
		this.dealAmt = dealAmt;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCardCD() {
		return cardCD;
	}
	public void setCardCD(String cardCD) {
		this.cardCD = cardCD;
	}
	public Date getTxDate() {
		return txDate;
	}
	public void setTxDate(Date txDate) {
		this.txDate = txDate;
	}
	
}
