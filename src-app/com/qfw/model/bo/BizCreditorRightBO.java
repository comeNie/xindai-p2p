package com.qfw.model.bo;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;
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
 * BizCreditorRight entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "biz_creditor_right")
public class BizCreditorRightBO extends BizBaseBO implements java.io.Serializable {

	// Fields

	private Integer id;
	private String crTypeCd;
	private Integer custId;
	private Integer loanCustId;
	private Integer loanApproveId;
	private Integer loanId;
	private String loanName;
	private BigDecimal loanAmt;
	private BigDecimal crAmt;
	private BigDecimal perTenderAmt;
	private Integer turnoverCount;
	private Integer surplusCount;
	private BigDecimal retrieveAmt;
	private BigDecimal unretrieveAmt;
	private String tenderTypeCd;
	private BigDecimal loanRate;
	private Integer totalPeriod;
	private Integer surplusPeriod;
	private Date nextGatherDate;
	private String crStatusCd;
	private BigDecimal totalProfitAmt;
	private Date settleDate;
	private String repayTypeCd;
	private Integer crId;
	private Date crTranDate;
	private String workItemId;
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;
	
	private String isVip;

	// Constructors

	/** default constructor */
	public BizCreditorRightBO() {
	}

	/** minimal constructor */
	public BizCreditorRightBO(String crTypeCd, Integer custId,
			Integer loanCustId, Integer loanApproveId, Integer loanId,
			String loanName, BigDecimal loanAmt, BigDecimal crAmt,BigDecimal perTenderAmt,
			Integer turnoverCount, Integer surplusCount,
			BigDecimal retrieveAmt, BigDecimal unretrieveAmt,
			String tenderTypeCd, BigDecimal loanRate, Integer totalPeriod,
			Integer surplusPeriod, Date nextGatherDate, String crStatusCd,
			BigDecimal totalProfitAmt, Date settleDate, String repayTypeCd,
			String workItemId, Integer sysCreateUser, Date sysCreateTime,
			Integer sysUpdateUser, Date sysUpdateTime) {
		this.crTypeCd = crTypeCd;
		this.custId = custId;
		this.loanCustId = loanCustId;
		this.loanApproveId = loanApproveId;
		this.loanId = loanId;
		this.loanName = loanName;
		this.loanAmt = loanAmt;
		this.crAmt = crAmt;
		this.perTenderAmt = perTenderAmt;
		this.turnoverCount = turnoverCount;
		this.surplusCount = surplusCount;
		this.retrieveAmt = retrieveAmt;
		this.unretrieveAmt = unretrieveAmt;
		this.tenderTypeCd = tenderTypeCd;
		this.loanRate = loanRate;
		this.totalPeriod = totalPeriod;
		this.surplusPeriod = surplusPeriod;
		this.nextGatherDate = nextGatherDate;
		this.crStatusCd = crStatusCd;
		this.totalProfitAmt = totalProfitAmt;
		this.settleDate = settleDate;
		this.repayTypeCd = repayTypeCd;
		this.workItemId = workItemId;
		this.sysCreateUser = sysCreateUser;
		this.sysCreateTime = sysCreateTime;
		this.sysUpdateUser = sysUpdateUser;
		this.sysUpdateTime = sysUpdateTime;
	}

	/** full constructor */
	public BizCreditorRightBO(String crTypeCd, Integer custId,
			Integer loanCustId, Integer loanApproveId, Integer loanId,
			String loanName, BigDecimal loanAmt, BigDecimal crAmt,BigDecimal perTenderAmt,
			Integer turnoverCount, Integer surplusCount,
			BigDecimal retrieveAmt, BigDecimal unretrieveAmt,
			String tenderTypeCd, BigDecimal loanRate, Integer totalPeriod,
			Integer surplusPeriod, Date nextGatherDate, String crStatusCd,
			BigDecimal totalProfitAmt, Date settleDate, String repayTypeCd,
			Integer crId, String workItemId, Integer sysCreateUser,
			Date sysCreateTime, Integer sysUpdateUser,
			Date sysUpdateTime) {
		this.crTypeCd = crTypeCd;
		this.custId = custId;
		this.loanCustId = loanCustId;
		this.loanApproveId = loanApproveId;
		this.loanId = loanId;
		this.loanName = loanName;
		this.loanAmt = loanAmt;
		this.crAmt = crAmt;
		this.perTenderAmt = perTenderAmt;
		this.turnoverCount = turnoverCount;
		this.surplusCount = surplusCount;
		this.retrieveAmt = retrieveAmt;
		this.unretrieveAmt = unretrieveAmt;
		this.tenderTypeCd = tenderTypeCd;
		this.loanRate = loanRate;
		this.totalPeriod = totalPeriod;
		this.surplusPeriod = surplusPeriod;
		this.nextGatherDate = nextGatherDate;
		this.crStatusCd = crStatusCd;
		this.totalProfitAmt = totalProfitAmt;
		this.settleDate = settleDate;
		this.repayTypeCd = repayTypeCd;
		this.crId = crId;
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

	@Column(name = "CR_TYPE_CD")
	public String getCrTypeCd() {
		return this.crTypeCd;
	}

	public void setCrTypeCd(String crTypeCd) {
		this.crTypeCd = crTypeCd;
	}

	@Column(name = "CUST_ID")
	public Integer getCustId() {
		return this.custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	@Column(name = "LOAN_CUST_ID")
	public Integer getLoanCustId() {
		return this.loanCustId;
	}

	public void setLoanCustId(Integer loanCustId) {
		this.loanCustId = loanCustId;
	}

	@Column(name = "LOAN_APPROVE_ID")
	public Integer getLoanApproveId() {
		return this.loanApproveId;
	}

	public void setLoanApproveId(Integer loanApproveId) {
		this.loanApproveId = loanApproveId;
	}

	@Column(name = "LOAN_ID")
	public Integer getLoanId() {
		return this.loanId;
	}

	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}

	@Column(name = "LOAN_NAME")
	public String getLoanName() {
		return this.loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	@Column(name = "LOAN_AMT")
	public BigDecimal getLoanAmt() {
		return this.loanAmt;
	}

	public void setLoanAmt(BigDecimal loanAmt) {
		this.loanAmt = loanAmt;
	}

	@Column(name = "CR_AMT")
	public BigDecimal getCrAmt() {
		return this.crAmt;
	}

	public void setCrAmt(BigDecimal crAmt) {
		this.crAmt = crAmt;
	}

	@Column(name = "TURNOVER_COUNT")
	public Integer getTurnoverCount() {
		return this.turnoverCount;
	}

	public void setTurnoverCount(Integer turnoverCount) {
		this.turnoverCount = turnoverCount;
	}
	
	@Column(name = "PER_TENDER_AMT")
	public BigDecimal getPerTenderAmt() {
		return perTenderAmt;
	}

	public void setPerTenderAmt(BigDecimal perTenderAmt) {
		this.perTenderAmt = perTenderAmt;
	}

	@Column(name = "SURPLUS_COUNT")
	public Integer getSurplusCount() {
		return this.surplusCount;
	}

	public void setSurplusCount(Integer surplusCount) {
		this.surplusCount = surplusCount;
	}

	@Column(name = "RETRIEVE_AMT")
	public BigDecimal getRetrieveAmt() {
		return this.retrieveAmt;
	}

	public void setRetrieveAmt(BigDecimal retrieveAmt) {
		this.retrieveAmt = retrieveAmt;
	}

	@Column(name = "UNRETRIEVE_AMT")
	public BigDecimal getUnretrieveAmt() {
		return this.unretrieveAmt;
	}

	public void setUnretrieveAmt(BigDecimal unretrieveAmt) {
		this.unretrieveAmt = unretrieveAmt;
	}

	@Column(name = "TENDER_TYPE_CD")
	public String getTenderTypeCd() {
		return this.tenderTypeCd;
	}

	public void setTenderTypeCd(String tenderTypeCd) {
		this.tenderTypeCd = tenderTypeCd;
	}

	@Column(name = "LOAN_RATE")
	public BigDecimal getLoanRate() {
		return this.loanRate;
	}

	public void setLoanRate(BigDecimal loanRate) {
		this.loanRate = loanRate;
	}

	@Column(name = "TOTAL_PERIOD")
	public Integer getTotalPeriod() {
		return this.totalPeriod;
	}

	public void setTotalPeriod(Integer totalPeriod) {
		this.totalPeriod = totalPeriod;
	}

	@Column(name = "SURPLUS_PERIOD")
	public Integer getSurplusPeriod() {
		return this.surplusPeriod;
	}

	public void setSurplusPeriod(Integer surplusPeriod) {
		this.surplusPeriod = surplusPeriod;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "NEXT_GATHER_DATE")
	public Date getNextGatherDate() {
		return this.nextGatherDate;
	}

	public void setNextGatherDate(Date nextGatherDate) {
		this.nextGatherDate = nextGatherDate;
	}

	@Column(name = "CR_STATUS_CD")
	public String getCrStatusCd() {
		return this.crStatusCd;
	}

	public void setCrStatusCd(String crStatusCd) {
		this.crStatusCd = crStatusCd;
	}

	@Column(name = "TOTAL_PROFIT_AMT")
	public BigDecimal getTotalProfitAmt() {
		return this.totalProfitAmt;
	}

	public void setTotalProfitAmt(BigDecimal totalProfitAmt) {
		this.totalProfitAmt = totalProfitAmt;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "SETTLE_DATE")
	public Date getSettleDate() {
		return this.settleDate;
	}

	public void setSettleDate(Date settleDate) {
		this.settleDate = settleDate;
	}

	@Column(name = "REPAY_TYPE_CD")
	public String getRepayTypeCd() {
		return this.repayTypeCd;
	}

	public void setRepayTypeCd(String repayTypeCd) {
		this.repayTypeCd = repayTypeCd;
	}

	@Column(name = "CR_ID")
	public Integer getCrId() {
		return this.crId;
	}

	public void setCrId(Integer crId) {
		this.crId = crId;
	}
	
	@Column(name = "CR_TRAN_DATE")
	public Date getCrTranDate() {
		return crTranDate;
	}

	public void setCrTranDate(Date crTranDate) {
		this.crTranDate = crTranDate;
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
	@Column(name = "IS_VIP")
	public String getIsVip() {
		return isVip;
	}

	public void setIsVip(String isVip) {
		this.isVip = isVip;
	}

}