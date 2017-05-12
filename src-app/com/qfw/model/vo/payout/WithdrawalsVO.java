package com.qfw.model.vo.payout;

import java.math.BigDecimal;
import java.util.Date;

public class WithdrawalsVO {

	private Integer id;
	private Integer custId;				//客户id
	private String outAccount;			// 转出账户
	private String intputAccount;		// 转入账户
	private BigDecimal accountBalAmt; // 账户余额
	private BigDecimal usableBalAmt; // 可用金额
	private BigDecimal dealAmt;		//提现金额
	private BigDecimal feeAmt;		// 提现费用
	private String tradeNum;			// 交易编号
	private String tradeDate;			// 交易日期
	private String status;				// 状态
	private String workItemId;
	private String tradePwd;
	private String bankType;
	private String bankName;
	private Date txDate;
	
	private String comment;			// 审批意见
	
	private String userCode;  // 登录编号
	private String userName; // 登录名
	private String custName;  // 真实姓名
	private Date startDate;	//开始时间
	private Date endDate;	// 结束时间
	
	public String getOutAccount() {
		return outAccount;
	}
	public void setOutAccount(String outAccount) {
		this.outAccount = outAccount;
	}
	public String getIntputAccount() {
		return intputAccount;
	}
	public void setIntputAccount(String intputAccount) {
		this.intputAccount = intputAccount;
	}
	public BigDecimal getAccountBalAmt() {
		return accountBalAmt;
	}
	public void setAccountBalAmt(BigDecimal accountBalAmt) {
		this.accountBalAmt = accountBalAmt;
	}
	public BigDecimal getUsableBalAmt() {
		return usableBalAmt;
	}
	public void setUsableBalAmt(BigDecimal usableBalAmt) {
		this.usableBalAmt = usableBalAmt;
	}
	public BigDecimal getDealAmt() {
		return dealAmt;
	}
	public void setDealAmt(BigDecimal dealAmt) {
		this.dealAmt = dealAmt;
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
	public String getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getWorkItemId() {
		return workItemId;
	}
	public void setWorkItemId(String workItemId) {
		this.workItemId = workItemId;
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
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
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
	public String getTradePwd() {
		return tradePwd;
	}
	public void setTradePwd(String tradePwd) {
		this.tradePwd = tradePwd;
	}
	public String getBankType() {
		return bankType;
	}
	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	public Date getTxDate() {
		return txDate;
	}
	public void setTxDate(Date txDate) {
		this.txDate = txDate;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
}
