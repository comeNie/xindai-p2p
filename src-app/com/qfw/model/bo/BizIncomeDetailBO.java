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

import com.qfw.common.model.BaseBO;

/**
 * BizIncomeDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "biz_income_detail")
public class BizIncomeDetailBO extends BizBaseBO implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer incomeRelId;
	private String incomeTypeCd;
	private Integer incomeSourRelId;
	private String incomeSourTypeCd;
	private BigDecimal tenderScale;
	private Date incomeStartDate;
	private Date incomeEndDate;
	private BigDecimal incomeTenderAmt;
	private BigDecimal incomeBaseAmt;
	private BigDecimal incomeRate;
	private Integer incomeDay;
	private BigDecimal incomeAmt;
	private BigDecimal incomeTakeAmt;
	private Date incomeDate;
	private String workItemId;
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;

	// Constructors

	/** default constructor */
	public BizIncomeDetailBO() {
	}

	/** full constructor */
	public BizIncomeDetailBO(Integer incomeRelId, String incomeTypeCd,
			BigDecimal incomeBaseAmt, BigDecimal incomeRate, Integer incomeDay,
			BigDecimal incomeAmt, Date incomeDate, String workItemId,
			Integer sysCreateUser, Date sysCreateTime,
			Integer sysUpdateUser, Date sysUpdateTime) {
		this.incomeRelId = incomeRelId;
		this.incomeTypeCd = incomeTypeCd;
		this.incomeBaseAmt = incomeBaseAmt;
		this.incomeRate = incomeRate;
		this.incomeDay = incomeDay;
		this.incomeAmt = incomeAmt;
		this.incomeDate = incomeDate;
		this.workItemId = workItemId;
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

	@Column(name = "INCOME_REL_ID")
	public Integer getIncomeRelId() {
		return this.incomeRelId;
	}

	public void setIncomeRelId(Integer incomeRelId) {
		this.incomeRelId = incomeRelId;
	}

	@Column(name = "INCOME_TYPE_CD")
	public String getIncomeTypeCd() {
		return this.incomeTypeCd;
	}

	public void setIncomeTypeCd(String incomeTypeCd) {
		this.incomeTypeCd = incomeTypeCd;
	}

	@Column(name = "INCOME_BASE_AMT")
	public BigDecimal getIncomeBaseAmt() {
		return this.incomeBaseAmt;
	}

	public void setIncomeBaseAmt(BigDecimal incomeBaseAmt) {
		this.incomeBaseAmt = incomeBaseAmt;
	}

	@Column(name = "INCOME_RATE")
	public BigDecimal getIncomeRate() {
		return this.incomeRate;
	}

	public void setIncomeRate(BigDecimal incomeRate) {
		this.incomeRate = incomeRate;
	}

	@Column(name = "INCOME_DAY")
	public Integer getIncomeDay() {
		return this.incomeDay;
	}

	public void setIncomeDay(Integer incomeDay) {
		this.incomeDay = incomeDay;
	}

	@Column(name = "INCOME_AMT")
	public BigDecimal getIncomeAmt() {
		return this.incomeAmt;
	}

	public void setIncomeAmt(BigDecimal incomeAmt) {
		this.incomeAmt = incomeAmt;
	}
	
	@Column(name = "INCOME_TAKE_AMT")
	public BigDecimal getIncomeTakeAmt() {
		return incomeTakeAmt;
	}

	public void setIncomeTakeAmt(BigDecimal incomeTakeAmt) {
		this.incomeTakeAmt = incomeTakeAmt;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "INCOME_DATE")
	public Date getIncomeDate() {
		return this.incomeDate;
	}

	public void setIncomeDate(Date incomeDate) {
		this.incomeDate = incomeDate;
	}

	@Column(name = "INCOME_SOUR_REL_ID")
	public Integer getIncomeSourRelId() {
		return incomeSourRelId;
	}

	public void setIncomeSourRelId(Integer incomeSourRelId) {
		this.incomeSourRelId = incomeSourRelId;
	}

	@Column(name = "INCOME_SOUR_TYPE_CD")
	public String getIncomeSourTypeCd() {
		return incomeSourTypeCd;
	}

	public void setIncomeSourTypeCd(String incomeSourTypeCd) {
		this.incomeSourTypeCd = incomeSourTypeCd;
	}

	@Column(name = "TENDER_SCALE")
	public BigDecimal getTenderScale() {
		return tenderScale;
	}

	public void setTenderScale(BigDecimal tenderScale) {
		this.tenderScale = tenderScale;
	}

	@Column(name = "INCOME_START_DATE")
	public Date getIncomeStartDate() {
		return incomeStartDate;
	}

	public void setIncomeStartDate(Date incomeStartDate) {
		this.incomeStartDate = incomeStartDate;
	}

	@Column(name = "INCOME_END_DATE")
	public Date getIncomeEndDate() {
		return incomeEndDate;
	}

	public void setIncomeEndDate(Date incomeEndDate) {
		this.incomeEndDate = incomeEndDate;
	}

	@Column(name = "INCOME_TENDER_AMT")
	public BigDecimal getIncomeTenderAmt() {
		return incomeTenderAmt;
	}

	public void setIncomeTenderAmt(BigDecimal incomeTenderAmt) {
		this.incomeTenderAmt = incomeTenderAmt;
	}

	@Column(name = "WORK_ITEM_ID")
	public String getWorkItemId() {
		return this.workItemId;
	}

	public void setWorkItemId(String workItemId) {
		this.workItemId = workItemId;
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