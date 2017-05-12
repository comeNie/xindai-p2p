package com.qfw.model.bo;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * BizFinancialProduct entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "biz_financial_product")
public class BizFinancialProductBO implements java.io.Serializable {

	// Fields

	private Integer id;
	private BigDecimal unitAmt;
	private BigDecimal leastInvestAmt;
	private String creditGradeCd;
	private String financialProductCd;
	private String title;
	private String financialProductDesc;
	private Integer appyyUserId;
	private String creditIndexCd;
	private BigDecimal financialProductAmt;
	private BigDecimal financialProductIntRate;
	private Date financialProductDueDate;
	private String fpStatusCd;
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;

	// Constructors

	/** default constructor */
	public BizFinancialProductBO() {
	}

	/** full constructor */
	public BizFinancialProductBO(BigDecimal unitAmt, BigDecimal leastInvestAmt,
			String creditGradeCd, String financialProductCd, String title,
			String financialProductDesc, Integer appyyUserId,
			String creditIndexCd, BigDecimal financialProductAmt,
			BigDecimal financialProductIntRate, Date financialProductDueDate,
			String fpStatusCd, Integer sysCreateUser, Date sysCreateTime,
			Integer sysUpdateUser, Date sysUpdateTime) {
		this.unitAmt = unitAmt;
		this.leastInvestAmt = leastInvestAmt;
		this.creditGradeCd = creditGradeCd;
		this.financialProductCd = financialProductCd;
		this.title = title;
		this.financialProductDesc = financialProductDesc;
		this.appyyUserId = appyyUserId;
		this.creditIndexCd = creditIndexCd;
		this.financialProductAmt = financialProductAmt;
		this.financialProductIntRate = financialProductIntRate;
		this.financialProductDueDate = financialProductDueDate;
		this.fpStatusCd = fpStatusCd;
		this.sysCreateUser = sysCreateUser;
		this.sysCreateTime = sysCreateTime;
		this.sysUpdateUser = sysUpdateUser;
		this.sysUpdateTime = sysUpdateTime;
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

	@Column(name = "UNIT_AMT")
	public BigDecimal getUnitAmt() {
		return this.unitAmt;
	}

	public void setUnitAmt(BigDecimal unitAmt) {
		this.unitAmt = unitAmt;
	}

	@Column(name = "LEAST_INVEST_AMT")
	public BigDecimal getLeastInvestAmt() {
		return this.leastInvestAmt;
	}

	public void setLeastInvestAmt(BigDecimal leastInvestAmt) {
		this.leastInvestAmt = leastInvestAmt;
	}

	@Column(name = "CREDIT_GRADE_CD")
	public String getCreditGradeCd() {
		return this.creditGradeCd;
	}

	public void setCreditGradeCd(String creditGradeCd) {
		this.creditGradeCd = creditGradeCd;
	}

	@Column(name = "FINANCIAL_PRODUCT_CD")
	public String getFinancialProductCd() {
		return this.financialProductCd;
	}

	public void setFinancialProductCd(String financialProductCd) {
		this.financialProductCd = financialProductCd;
	}

	@Column(name = "TITLE")
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "FINANCIAL_PRODUCT_DESC")
	public String getFinancialProductDesc() {
		return this.financialProductDesc;
	}

	public void setFinancialProductDesc(String financialProductDesc) {
		this.financialProductDesc = financialProductDesc;
	}

	@Column(name = "APPYY_USER_ID")
	public Integer getAppyyUserId() {
		return this.appyyUserId;
	}

	public void setAppyyUserId(Integer appyyUserId) {
		this.appyyUserId = appyyUserId;
	}

	@Column(name = "CREDIT_INDEX_CD")
	public String getCreditIndexCd() {
		return this.creditIndexCd;
	}

	public void setCreditIndexCd(String creditIndexCd) {
		this.creditIndexCd = creditIndexCd;
	}

	@Column(name = "FINANCIAL_PRODUCT_AMT")
	public BigDecimal getFinancialProductAmt() {
		return this.financialProductAmt;
	}

	public void setFinancialProductAmt(BigDecimal financialProductAmt) {
		this.financialProductAmt = financialProductAmt;
	}

	@Column(name = "FINANCIAL_PRODUCT_INT_RATE")
	public BigDecimal getFinancialProductIntRate() {
		return this.financialProductIntRate;
	}

	public void setFinancialProductIntRate(BigDecimal financialProductIntRate) {
		this.financialProductIntRate = financialProductIntRate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "FINANCIAL_PRODUCT_DUE_DATE")
	public Date getFinancialProductDueDate() {
		return this.financialProductDueDate;
	}

	public void setFinancialProductDueDate(Date financialProductDueDate) {
		this.financialProductDueDate = financialProductDueDate;
	}

	@Column(name = "FP_STATUS_CD")
	public String getFpStatusCd() {
		return this.fpStatusCd;
	}

	public void setFpStatusCd(String fpStatusCd) {
		this.fpStatusCd = fpStatusCd;
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

}