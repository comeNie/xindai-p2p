package com.qfw.model.vo.creditor;

import java.util.Date;

/**
 * 债权管理vo
 *
 * @author kyc
 */
public class CreditorSearchVO {

	private Integer creditorId; // 借款id
	private String account; // 账户
	private String custName; // 客户名称
	private String term; // 期限
	private String crStatusCd; // 状态
	private String creditorRightTran;  //债权转让发布
	private Integer tranId; //债权发布ID
	private String loanType; //标的类型
	private String loanRate;// 年化利率
	private String creditRate;// 信用等级
	
	private Integer custId;	// 用户id
	
	private String userCode;//用户编号
	private String loanName;//借款标题
	
	private Date date;
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public Integer getCreditorId() {
		return creditorId;
	}
	public void setCreditorId(Integer creditorId) {
		this.creditorId = creditorId;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getCrStatusCd() {
		return crStatusCd;
	}
	public void setCrStatusCd(String crStatusCd) {
		this.crStatusCd = crStatusCd;
	}
	public String getCreditorRightTran() {
		return creditorRightTran;
	}
	public void setCreditorRightTran(String creditorRightTran) {
		this.creditorRightTran = creditorRightTran;
	}
	public Integer getTranId() {
		return tranId;
	}
	public void setTranId(Integer tranId) {
		this.tranId = tranId;
	}
	public Integer getCustId() {
		return custId;
	}
	public void setCustId(Integer custId) {
		this.custId = custId;
	}
	public String getLoanType() {
		return loanType;
	}
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	public String getLoanRate() {
		return loanRate;
	}
	public void setLoanRate(String loanRate) {
		this.loanRate = loanRate;
	}
	public String getCreditRate() {
		return creditRate;
	}
	public void setCreditRate(String creditRate) {
		this.creditRate = creditRate;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getLoanName() {
		return loanName;
	}
	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}