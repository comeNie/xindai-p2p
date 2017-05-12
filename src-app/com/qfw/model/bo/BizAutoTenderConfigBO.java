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

/**
 * BizCost entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "biz_auto_tender_config")
public class BizAutoTenderConfigBO extends BizBaseBO implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer custId;
	private BigDecimal yearRateBe;
	private BigDecimal yearRateEn;
	private Integer loanPeriodBe;
	private Integer loanPeriodEn;
	private String creditRateBe;
	private String creditRateEn;
	private BigDecimal accRetain;
	private BigDecimal eachMaxBid;
	private BigDecimal accBal;
	
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;
	
	private Integer status;
	private Integer isAuto;
	private String userCode;
	private Integer userId;

	// Constructors

	

	/** default constructor */
	public BizAutoTenderConfigBO() {
	}

	/** full constructor */
	public BizAutoTenderConfigBO(Integer custId, BigDecimal yearRateBe,BigDecimal yearRateEn,
			Integer loanPeriodBe, Integer loanPeriodEn,
			String creditRateBe , String creditRateEn,
			BigDecimal accRetain,BigDecimal eachMaxBid,BigDecimal accBal,
			Integer sysCreateUser, Timestamp sysCreateTime,
			Integer sysUpdateUser, Timestamp sysUpdateTime) {
		this.custId = custId;
		this.yearRateBe = yearRateBe;
		this.yearRateEn = yearRateEn;
		this.loanPeriodBe = loanPeriodBe;
		this.loanPeriodEn = loanPeriodEn;
		this.creditRateBe = creditRateBe;
		this.creditRateEn = creditRateEn;
		this.accRetain = accRetain;
		this.eachMaxBid = eachMaxBid;
		this.accBal = accBal;
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

	@Column(name = "custId", nullable = false)
	public Integer getCustId() {
		return this.custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}


	@Column(name = "yearRateBe")
	public BigDecimal getYearRateBe() {
		return yearRateBe;
	}

	public void setYearRateBe(BigDecimal yearRateBe) {
		this.yearRateBe = yearRateBe;
	}
	
	@Column(name = "yearRateEn")
	public BigDecimal getYearRateEn() {
		return yearRateEn;
	}

	public void setYearRateEn(BigDecimal yearRateEn) {
		this.yearRateEn = yearRateEn;
	}
	
	@Column(name = "loanPeriodBe")
	public Integer getLoanPeriodBe() {
		return loanPeriodBe;
	}

	public void setLoanPeriodBe(Integer loanPeriodBe) {
		this.loanPeriodBe = loanPeriodBe;
	}

	@Column(name = "loanPeriodEn")
	public Integer getLoanPeriodEn() {
		return loanPeriodEn;
	}

	public void setLoanPeriodEn(Integer loanPeriodEn) {
		this.loanPeriodEn = loanPeriodEn;
	}

	@Column(name = "creditRateBe")
	public String getCreditRateBe() {
		return creditRateBe;
	}

	public void setCreditRateBe(String creditRateBe) {
		this.creditRateBe = creditRateBe;
	}

	@Column(name = "creditRateEn")
	public String getCreditRateEn() {
		return creditRateEn;
	}

	public void setCreditRateEn(String creditRateEn) {
		this.creditRateEn = creditRateEn;
	}

	@Column(name = "accRetain")
	public BigDecimal getAccRetain() {
		return accRetain;
	}

	public void setAccRetain(BigDecimal accRetain) {
		this.accRetain = accRetain;
	}

	@Column(name = "eachMaxBid")
	public BigDecimal getEachMaxBid() {
		return eachMaxBid;
	}

	public void setEachMaxBid(BigDecimal eachMaxBid) {
		this.eachMaxBid = eachMaxBid;
	}

	@Column(name = "accBal")
	public BigDecimal getAccBal() {
		return accBal;
	}

	public void setAccBal(BigDecimal accBal) {
		this.accBal = accBal;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "SYS_CREATE_USER", nullable = true)
	public Integer getSysCreateUser() {
		return this.sysCreateUser;
	}

	public void setSysCreateUser(Integer sysCreateUser) {
		this.sysCreateUser = sysCreateUser;
	}

	@Column(name = "SYS_CREATE_TIME", nullable = false, length = 19)
	public Date getSysCreateTime() {
		return this.sysCreateTime;
	}

	public void setSysCreateTime(Date sysCreateTime) {
		this.sysCreateTime = sysCreateTime;
	}

	@Column(name = "SYS_UPDATE_USER", nullable = false)
	public Integer getSysUpdateUser() {
		return this.sysUpdateUser;
	}

	public void setSysUpdateUser(Integer sysUpdateUser) {
		this.sysUpdateUser = sysUpdateUser;
	}

	@Column(name = "SYS_UPDATE_TIME", nullable = false, length = 19)
	public Date getSysUpdateTime() {
		return this.sysUpdateTime;
	}

	public void setSysUpdateTime(Date sysUpdateTime) {
		this.sysUpdateTime = sysUpdateTime;
	}

	@Column(name = "isAuto")
	public Integer getIsAuto() {
		return isAuto;
	}

	public void setIsAuto(Integer isAuto) {
		this.isAuto = isAuto;
	}

	@Column(name = "userCode")
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	@Column(name = "userId")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	

}