package com.qfw.model.vo.custinfo.custAuth;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 客户认证信息
 *
 * @author kyc
 */
public class CustAuthInfoVO {

	private Integer custId;  // 客户ID
	private String account;  // 账户
	private BigDecimal accountBalAmt; // 账户余额
	private BigDecimal usableBalAmt; // 可用余额
	private String custName; // 客户名称
	private String certificateTypeCd; // 证件类型
	private String certificateNum; // 证件号码
	private Date sysCreateTime; // 注册日期
	private BigDecimal clAmt;  // 额度金额
	private BigDecimal balClAmt; // 剩余额度
	private BigDecimal pmAmt; // PM币 
	
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
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCertificateTypeCd() {
		return certificateTypeCd;
	}
	public void setCertificateTypeCd(String certificateTypeCd) {
		this.certificateTypeCd = certificateTypeCd;
	}
	public String getCertificateNum() {
		return certificateNum;
	}
	public void setCertificateNum(String certificateNum) {
		this.certificateNum = certificateNum;
	}
	public Date getSysCreateTime() {
		return sysCreateTime;
	}
	public void setSysCreateTime(Date sysCreateTime) {
		this.sysCreateTime = sysCreateTime;
	}
	public BigDecimal getClAmt() {
		return clAmt;
	}
	public void setClAmt(BigDecimal clAmt) {
		this.clAmt = clAmt;
	}
	public BigDecimal getBalClAmt() {
		return balClAmt;
	}
	public void setBalClAmt(BigDecimal balClAmt) {
		this.balClAmt = balClAmt;
	}
	public BigDecimal getPmAmt() {
		return pmAmt;
	}
	public void setPmAmt(BigDecimal pmAmt) {
		this.pmAmt = pmAmt;
	}
	
	
}