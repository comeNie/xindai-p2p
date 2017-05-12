package com.qfw.model.bo;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * BizLoanApprove entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "biz_loan_approve")
public class BizLoanApproveBO extends BizBaseBO implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer custId;
	private String loanApplyId;
	private String loanNum;
	private String loanName;
	private BigDecimal loanAmt;
	private Integer loanTerm;
	private String termUnitCd;
	private BigDecimal tenderBalAmt;
	private BigDecimal tenderUseAmt;
	private BigDecimal tenderLimitAmt;
	private String loanPurpose;
	private Integer tenderTerm;
	private Date tenderApproveTime;
	private Date tenderDueTime;
	private BigDecimal loanRate;
	private String repayTypeCd;
	private Integer tenderTtlCount;
	private Integer tenderUseCount;
	private Integer tenderBalCount;
	private String approveStatusCd;
	private String paymentWayCd;//支付方式
	private Integer trusteeCustId;//受托支付会员ID
	private String remark;
	private String workItemId;
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;
	
	private String productId;
	private String productName;
	private String loanTypeCd;
	/**
	 * 推荐提成比率
	 */
	private BigDecimal recommendPercent;
	
	private BizLoanBO bizLoanBO;
	
	@OneToOne(fetch=FetchType.LAZY,targetEntity=BizLoanBO.class,mappedBy="bizLoanApproveBO")
	public BizLoanBO getBizLoanBO() {
		return bizLoanBO;
	}

	// Constructors

	/** default constructor */
	public BizLoanApproveBO() {
	}

	/** minimal constructor */
	public BizLoanApproveBO(Integer custId, String loanApplyId, String loanNum,
			String loanName, BigDecimal loanAmt, Integer loanTerm,
			String termUnitCd, BigDecimal tenderBalAmt,
			BigDecimal tenderUseAmt, BigDecimal tenderLimitAmt,
			Integer tenderTerm, Date tenderApproveTime,
			Date tenderDueTime, BigDecimal loanRate, String repayTypeCd,
			Integer tenderTtlCount, Integer tenderUseCount,
			Integer tenderBalCount, String approveStatusCd, String workItemId,String paymentWayCd,Integer trusteeCustId,
			Integer sysCreateUser, Date sysCreateTime,
			Integer sysUpdateUser, Date sysUpdateTime,
			String productId,String productName,String loanTypeCd) {
		this.custId = custId;
		this.loanApplyId = loanApplyId;
		this.loanNum = loanNum;
		this.loanName = loanName;
		this.loanAmt = loanAmt;
		this.loanTerm = loanTerm;
		this.termUnitCd = termUnitCd;
		this.tenderBalAmt = tenderBalAmt;
		this.tenderUseAmt = tenderUseAmt;
		this.tenderLimitAmt = tenderLimitAmt;
		this.tenderTerm = tenderTerm;
		this.tenderApproveTime = tenderApproveTime;
		this.tenderDueTime = tenderDueTime;
		this.loanRate = loanRate;
		this.repayTypeCd = repayTypeCd;
		this.tenderTtlCount = tenderTtlCount;
		this.tenderUseCount = tenderUseCount;
		this.tenderBalCount = tenderBalCount;
		this.approveStatusCd = approveStatusCd;
		this.paymentWayCd = paymentWayCd;
		this.trusteeCustId = trusteeCustId;
		this.workItemId = workItemId;
		this.sysCreateUser = sysCreateUser;
		this.sysCreateTime = sysCreateTime;
		this.sysUpdateUser = sysUpdateUser;
		this.sysUpdateTime = sysUpdateTime;
		this.productId = productId;
		this.productName = productName;
		this.loanTypeCd = loanTypeCd;
	}

	/** full constructor */
	public BizLoanApproveBO(Integer custId, String loanApplyId, String loanNum,
			String loanName, BigDecimal loanAmt, Integer loanTerm,
			String termUnitCd, BigDecimal tenderBalAmt,
			BigDecimal tenderUseAmt, BigDecimal tenderLimitAmt,
			String loanPurpose, Integer tenderTerm,
			Date tenderApproveTime, Date tenderDueTime,
			BigDecimal loanRate, String repayTypeCd, Integer tenderTtlCount,
			Integer tenderUseCount, Integer tenderBalCount,
			String approveStatusCd, String remark, String workItemId,String paymentWayCd,Integer trusteeCustId,
			Integer sysCreateUser, Date sysCreateTime,
			Integer sysUpdateUser, Date sysUpdateTime,
			String productId,String productName,String loanTypeCd) {
		this.custId = custId;
		this.loanApplyId = loanApplyId;
		this.loanNum = loanNum;
		this.loanName = loanName;
		this.loanAmt = loanAmt;
		this.loanTerm = loanTerm;
		this.termUnitCd = termUnitCd;
		this.tenderBalAmt = tenderBalAmt;
		this.tenderUseAmt = tenderUseAmt;
		this.tenderLimitAmt = tenderLimitAmt;
		this.loanPurpose = loanPurpose;
		this.tenderTerm = tenderTerm;
		this.tenderApproveTime = tenderApproveTime;
		this.tenderDueTime = tenderDueTime;
		this.loanRate = loanRate;
		this.repayTypeCd = repayTypeCd;
		this.tenderTtlCount = tenderTtlCount;
		this.tenderUseCount = tenderUseCount;
		this.tenderBalCount = tenderBalCount;
		this.approveStatusCd = approveStatusCd;
		this.paymentWayCd = paymentWayCd;
		this.trusteeCustId = trusteeCustId;
		this.remark = remark;
		this.workItemId = workItemId;
		this.sysCreateUser = sysCreateUser;
		this.sysCreateTime = sysCreateTime;
		this.sysUpdateUser = sysUpdateUser;
		this.sysUpdateTime = sysUpdateTime;
		this.productId = productId;
		this.productName = productName;
		this.loanTypeCd = loanTypeCd;
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

	@Column(name = "CUST_ID")
	public Integer getCustId() {
		return this.custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	@Column(name = "LOAN_APPLY_ID")
	public String getLoanApplyId() {
		return this.loanApplyId;
	}

	public void setLoanApplyId(String loanApplyId) {
		this.loanApplyId = loanApplyId;
	}

	@Column(name = "LOAN_NUM")
	public String getLoanNum() {
		return this.loanNum;
	}

	public void setLoanNum(String loanNum) {
		this.loanNum = loanNum;
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

	@Column(name = "LOAN_TERM")
	public Integer getLoanTerm() {
		return this.loanTerm;
	}

	public void setLoanTerm(Integer loanTerm) {
		this.loanTerm = loanTerm;
	}

	@Column(name = "TERM_UNIT_CD")
	public String getTermUnitCd() {
		return this.termUnitCd;
	}

	public void setTermUnitCd(String termUnitCd) {
		this.termUnitCd = termUnitCd;
	}

	@Column(name = "TENDER_BAL_AMT")
	public BigDecimal getTenderBalAmt() {
		return this.tenderBalAmt;
	}

	public void setTenderBalAmt(BigDecimal tenderBalAmt) {
		this.tenderBalAmt = tenderBalAmt;
	}

	@Column(name = "TENDER_USE_AMT")
	public BigDecimal getTenderUseAmt() {
		return this.tenderUseAmt;
	}

	public void setTenderUseAmt(BigDecimal tenderUseAmt) {
		this.tenderUseAmt = tenderUseAmt;
	}

	@Column(name = "TENDER_LIMIT_AMT")
	public BigDecimal getTenderLimitAmt() {
		return this.tenderLimitAmt;
	}

	public void setTenderLimitAmt(BigDecimal tenderLimitAmt) {
		this.tenderLimitAmt = tenderLimitAmt;
	}

	@Column(name = "LOAN_PURPOSE")
	public String getLoanPurpose() {
		return this.loanPurpose;
	}

	public void setLoanPurpose(String loanPurpose) {
		this.loanPurpose = loanPurpose;
	}

	@Column(name = "TENDER_TERM")
	public Integer getTenderTerm() {
		return this.tenderTerm;
	}

	public void setTenderTerm(Integer tenderTerm) {
		this.tenderTerm = tenderTerm;
	}

	@Column(name = "TENDER_APPROVE_TIME")
	public Date getTenderApproveTime() {
		return this.tenderApproveTime;
	}

	public void setTenderApproveTime(Date tenderApproveTime) {
		this.tenderApproveTime = tenderApproveTime;
	}

	@Column(name = "TENDER_DUE_TIME")
	public Date getTenderDueTime() {
		return this.tenderDueTime;
	}

	public void setTenderDueTime(Date tenderDueTime) {
		this.tenderDueTime = tenderDueTime;
	}

	@Column(name = "LOAN_RATE")
	public BigDecimal getLoanRate() {
		return this.loanRate;
	}

	public void setLoanRate(BigDecimal loanRate) {
		this.loanRate = loanRate;
	}

	@Column(name = "REPAY_TYPE_CD")
	public String getRepayTypeCd() {
		return this.repayTypeCd;
	}

	public void setRepayTypeCd(String repayTypeCd) {
		this.repayTypeCd = repayTypeCd;
	}

	@Column(name = "TENDER_TTL_COUNT")
	public Integer getTenderTtlCount() {
		return this.tenderTtlCount;
	}

	public void setTenderTtlCount(Integer tenderTtlCount) {
		this.tenderTtlCount = tenderTtlCount;
	}

	@Column(name = "TENDER_USE_COUNT")
	public Integer getTenderUseCount() {
		return this.tenderUseCount;
	}

	public void setTenderUseCount(Integer tenderUseCount) {
		this.tenderUseCount = tenderUseCount;
	}

	@Column(name = "TENDER_BAL_COUNT")
	public Integer getTenderBalCount() {
		return this.tenderBalCount;
	}

	public void setTenderBalCount(Integer tenderBalCount) {
		this.tenderBalCount = tenderBalCount;
	}

	@Column(name = "APPROVE_STATUS_CD")
	public String getApproveStatusCd() {
		return this.approveStatusCd;
	}

	public void setApproveStatusCd(String approveStatusCd) {
		this.approveStatusCd = approveStatusCd;
	}

	@Column(name = "PAYMENT_WAY_CD")
	public String getPaymentWayCd() {
		return paymentWayCd;
	}

	public void setPaymentWayCd(String paymentWayCd) {
		this.paymentWayCd = paymentWayCd;
	}

	@Column(name = "TRUSTEE_CUST_ID")
	public Integer getTrusteeCustId() {
		return trusteeCustId;
	}

	public void setTrusteeCustId(Integer trusteeCustId) {
		this.trusteeCustId = trusteeCustId;
	}
	
	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public void setBizLoanBO(BizLoanBO bizLoanBO) {
		this.bizLoanBO = bizLoanBO;
	}

	@Column(name = "PRODUCT_ID")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	@Column(name = "PRODUCT_NAME")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Column(name = "LOAN_TYPE_CD")
	public String getLoanTypeCd() {
		return loanTypeCd;
	}

	public void setLoanTypeCd(String loanTypeCd) {
		this.loanTypeCd = loanTypeCd;
	}
	@Column(name = "RECOMMEND_PERCENT")
	public BigDecimal getRecommendPercent() {
		return recommendPercent;
	}

	public void setRecommendPercent(BigDecimal recommendPercent) {
		this.recommendPercent = recommendPercent;
	}
	
}