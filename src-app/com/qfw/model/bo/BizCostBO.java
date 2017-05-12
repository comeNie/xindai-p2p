package com.qfw.model.bo;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * BizCost entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "biz_cost")
public class BizCostBO extends BizBaseBO implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer custId;
	private Integer relateId;
	private String relateTypeCd;
	private BigDecimal costAmt;
	private BigDecimal costRate;
	private BigDecimal costBasicAmt;
	private String currency;
	private BigDecimal exchangeRate;
	private String costTypeCd;
	private String costStatusCd;
	private Date costHappenDate;
	private Date costGatherDate;
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;

	// Constructors

	/** default constructor */
	public BizCostBO() {
	}

	/** full constructor */
	public BizCostBO(Integer custId, Integer relateId, String relateTypeCd,
			BigDecimal costAmt, BigDecimal costRate, BigDecimal costBasicAmt,
			String currency, BigDecimal exchangeRate, String costTypeCd,
			String costStatusCd, Date costHappenDate, Date costGatherDate,
			Integer sysCreateUser, Timestamp sysCreateTime,
			Integer sysUpdateUser, Timestamp sysUpdateTime) {
		this.custId = custId;
		this.relateId = relateId;
		this.relateTypeCd = relateTypeCd;
		this.costAmt = costAmt;
		this.costRate = costRate;
		this.costBasicAmt = costBasicAmt;
		this.currency = currency;
		this.exchangeRate = exchangeRate;
		this.costTypeCd = costTypeCd;
		this.costStatusCd = costStatusCd;
		this.costHappenDate = costHappenDate;
		this.costGatherDate = costGatherDate;
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

	@Column(name = "CUST_ID", nullable = false)
	public Integer getCustId() {
		return this.custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	@Column(name = "RELATE_ID", nullable = false)
	public Integer getRelateId() {
		return this.relateId;
	}

	public void setRelateId(Integer relateId) {
		this.relateId = relateId;
	}

	@Column(name = "RELATE_TYPE_CD")
	public String getRelateTypeCd() {
		return this.relateTypeCd;
	}

	public void setRelateTypeCd(String relateTypeCd) {
		this.relateTypeCd = relateTypeCd;
	}

	@Column(name = "COST_AMT")
	public BigDecimal getCostAmt() {
		return this.costAmt;
	}

	public void setCostAmt(BigDecimal costAmt) {
		this.costAmt = costAmt;
	}

	@Column(name = "COST_RATE")
	public BigDecimal getCostRate() {
		return this.costRate;
	}

	public void setCostRate(BigDecimal costRate) {
		this.costRate = costRate;
	}

	@Column(name = "COST_BASIC_AMT")
	public BigDecimal getCostBasicAmt() {
		return this.costBasicAmt;
	}

	public void setCostBasicAmt(BigDecimal costBasicAmt) {
		this.costBasicAmt = costBasicAmt;
	}

	@Column(name = "CURRENCY")
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "EXCHANGE_RATE")
	public BigDecimal getExchangeRate() {
		return this.exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	@Column(name = "COST_TYPE_CD")
	public String getCostTypeCd() {
		return this.costTypeCd;
	}

	public void setCostTypeCd(String costTypeCd) {
		this.costTypeCd = costTypeCd;
	}

	@Column(name = "COST_STATUS_CD")
	public String getCostStatusCd() {
		return this.costStatusCd;
	}

	public void setCostStatusCd(String costStatusCd) {
		this.costStatusCd = costStatusCd;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "COST_HAPPEN_DATE")
	public Date getCostHappenDate() {
		return this.costHappenDate;
	}

	public void setCostHappenDate(Date costHappenDate) {
		this.costHappenDate = costHappenDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "COST_GATHER_DATE")
	public Date getCostGatherDate() {
		return this.costGatherDate;
	}

	public void setCostGatherDate(Date costGatherDate) {
		this.costGatherDate = costGatherDate;
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

	@Column(name = "SYS_UPDATE_TIME")
	public Date getSysUpdateTime() {
		return this.sysUpdateTime;
	}

	public void setSysUpdateTime(Date sysUpdateTime) {
		this.sysUpdateTime = sysUpdateTime;
	}

}