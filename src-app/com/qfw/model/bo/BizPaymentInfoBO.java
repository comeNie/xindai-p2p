package com.qfw.model.bo;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.springframework.transaction.annotation.Transactional;

/**
 * BizPaymentInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BIZ_PAYMENT_INFO")
public class BizPaymentInfoBO implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer custId;
	private String merNo;
	private String billNo;
	private String amount;
	private String returnUrl;
	private String adviceUrl;
	private String signInfo;
	private Date orderTime;
	private String defaultBankNumber;
	private String remark;
	private String products;
	private String status;
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;
	private String serverTime;
	

	// Constructors

	/** default constructor */
	public BizPaymentInfoBO() {
	}


	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "MER_NO")
	public String getMerNo() {
		return this.merNo;
	}

	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}

	@Column(name = "BILL_NO")
	public String getBillNo() {
		return this.billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	@Column(name = "AMOUNT")
	public String getAmount() {
		return this.amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	@Column(name = "RETURN_URL")
	public String getReturnUrl() {
		return this.returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	@Column(name = "ADVICE_URL")
	public String getAdviceUrl() {
		return this.adviceUrl;
	}

	public void setAdviceUrl(String adviceUrl) {
		this.adviceUrl = adviceUrl;
	}

	@Column(name = "SIGN_INFO")
	public String getSignInfo() {
		return this.signInfo;
	}

	public void setSignInfo(String signInfo) {
		this.signInfo = signInfo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ORDER_TIME")
	public Date getOrderTime() {
		return this.orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	@Column(name = "DEFAULT_BANK_NUMBER")
	public String getDefaultBankNumber() {
		return this.defaultBankNumber;
	}

	public void setDefaultBankNumber(String defaultBankNumber) {
		this.defaultBankNumber = defaultBankNumber;
	}

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "PRODUCTS")
	public String getProducts() {
		return this.products;
	}

	public void setProducts(String products) {
		this.products = products;
	}

	@Column(name = "STATUS")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "CUST_ID")
	public Integer getCustId() {
		return custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	@Column(name = "SYS_CREATE_USER")
	public Integer getSysCreateUser() {
		return this.sysCreateUser;
	}

	public void setSysCreateUser(Integer sysCreateUser) {
		this.sysCreateUser = sysCreateUser;
	}

	@Column(name = "SYS_CREATE_TIME")
	public Date getSysCreateTime() {
		return this.sysCreateTime;
	}

	public void setSysCreateTime(Date sysCreateTime) {
		this.sysCreateTime = sysCreateTime;
	}

	@Column(name = "SYS_UPDATE_USER")
	public Integer getSysUpdateUser() {
		return this.sysUpdateUser;
	}

	public void setSysUpdateUser(Integer sysUpdateUser) {
		this.sysUpdateUser = sysUpdateUser;
	}

	@Version
	@Column(name = "SYS_UPDATE_TIME")
	public Date getSysUpdateTime() {
		return this.sysUpdateTime;
	}

	public void setSysUpdateTime(Date sysUpdateTime) {
		this.sysUpdateTime = sysUpdateTime;
	}

	@Transient
	public String getServerTime() {
		return serverTime;
	}

	public void setServerTime(String serverTime) {
		this.serverTime = serverTime;
	}

}