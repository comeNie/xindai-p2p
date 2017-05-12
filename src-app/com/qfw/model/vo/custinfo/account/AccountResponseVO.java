package com.qfw.model.vo.custinfo.account;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户账号信息vo
 *
 * @author kyc
 */
public class AccountResponseVO {

	private Integer detailId;		// 明细id
	private Integer accountId;		// 账户id
	private String custId; 			// 会员id
	private String account;			// 账户
	private Date txDate;			// 交易日期
	private BigDecimal accountBalAmt;	// 账户余额
	private BigDecimal tradeAmt;		// 交易金额
	private String txNo;				// 交易编号
	private String tradeTypeCd;	//交易类型
	private String comment;
	
	private String userCode;  // 登录编号
	private String userName; // 登录名
	private String custName;  // 真实姓名
	
	public Integer getDetailId() {
		return detailId;
	}
	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Date getTxDate() {
		return txDate;
	}
	public void setTxDate(Date txDate) {
		this.txDate = txDate;
	}
	public BigDecimal getAccountBalAmt() {
		return accountBalAmt;
	}
	public void setAccountBalAmt(BigDecimal accountBalAmt) {
		this.accountBalAmt = accountBalAmt;
	}
	public BigDecimal getTradeAmt() {
		return tradeAmt;
	}
	public void setTradeAmt(BigDecimal tradeAmt) {
		this.tradeAmt = tradeAmt;
	}
	public String getTxNo() {
		return txNo;
	}
	public void setTxNo(String txNo) {
		this.txNo = txNo;
	}
	public String getTradeTypeCd() {
		return tradeTypeCd;
	}
	public void setTradeTypeCd(String tradeTypeCd) {
		this.tradeTypeCd = tradeTypeCd;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
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
	
}
