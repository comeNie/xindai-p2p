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
 * BizLoanApply entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "biz_loan_apply")
public class BizLoanApplyBO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String custId;
	private String custName;	//申请人名称
	private String jobStatusCd; //职业身份
	private String sex;	//性别
	private String tel;	//联系电话
	private String email;	//联系邮箱
	private String addr;	//通讯地址
	private String loanTypeCd;
	private String loanName;
	private BigDecimal applyAmt;
	private Integer loanTerm;
	private String termUnitCd;
	private String loanPurpose;
	private Integer tenderTerm;
	private BigDecimal expectLoanRate;
	private String repayTypeCd;
	private Date applyDate;
	private String applyStatusCd;
	private String remark;
	private String workItemId;
	private String productId;
	private BigDecimal temderCountAmt;  // 每份金额
	private String paymentWayCd;//支付方式
	private Integer trusteeCustId;//受托支付会员ID
	/**
	 * 推荐提成比率
	 */
	private BigDecimal recommendPercent;
	
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;
	
	// Constructors

	/** default constructor */
	public BizLoanApplyBO() {
	}

	/** minimal constructor */
	public BizLoanApplyBO(String custId, String loanTypeCd, String loanName,
			BigDecimal applyAmt, Integer loanTerm, String termUnitCd,BigDecimal temderCountAmt,
			String remark, String workItemId, String productId ,Integer sysCreateUser,String paymentWayCd,Integer trusteeCustId,
			Date sysCreateTime, Integer sysUpdateUser,Date sysUpdateTime) {
		this.custId = custId;
		this.loanTypeCd = loanTypeCd;
		this.loanName = loanName;
		this.applyAmt = applyAmt;
		this.loanTerm = loanTerm;
		this.termUnitCd = termUnitCd;
		this.remark = remark;
		this.workItemId = workItemId;
		this.productId = productId;
		this.temderCountAmt = temderCountAmt;
		this.paymentWayCd = paymentWayCd;
		this.trusteeCustId = trusteeCustId;
		this.sysCreateUser = sysCreateUser;
		this.sysCreateTime = sysCreateTime;
		this.sysUpdateUser = sysUpdateUser;
		this.sysUpdateTime = sysUpdateTime;
	}

	/** full constructor */
	public BizLoanApplyBO(String custId, String loanTypeCd, String loanName,
			BigDecimal applyAmt, Integer loanTerm, String termUnitCd,
			String loanPurpose, Integer tenderTerm, BigDecimal expectLoanRate,
			String repayTypeCd, Date applyDate, String applyStatusCd,String paymentWayCd,Integer trusteeCustId,
			String remark, String workItemId, String productId,Integer sysCreateUser,
			Date sysCreateTime, Integer sysUpdateUser,
			Date sysUpdateTime) {
		this.custId = custId;
		this.loanTypeCd = loanTypeCd;
		this.loanName = loanName;
		this.applyAmt = applyAmt;
		this.loanTerm = loanTerm;
		this.termUnitCd = termUnitCd;
		this.loanPurpose = loanPurpose;
		this.tenderTerm = tenderTerm;
		this.expectLoanRate = expectLoanRate;
		this.repayTypeCd = repayTypeCd;
		this.applyDate = applyDate;
		this.applyStatusCd = applyStatusCd;
		this.remark = remark;
		this.workItemId = workItemId;
		this.productId = productId;
		this.paymentWayCd = paymentWayCd;
		this.trusteeCustId = trusteeCustId;
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

	@Column(name = "CUST_ID")
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}
	
	@Column(name = "CUST_NAME")
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	@Column(name = "JOB_STATUS_CD")
	public String getJobStatusCd() {
		return jobStatusCd;
	}

	public void setJobStatusCd(String jobStatusCd) {
		jobStatusCd = jobStatusCd;
	}
	
	@Column(name = "SEX")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Column(name = "TEL")
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "ADDR")
	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Column(name = "LOAN_TYPE_CD")
	public String getLoanTypeCd() {
		return this.loanTypeCd;
	}

	public void setLoanTypeCd(String loanTypeCd) {
		this.loanTypeCd = loanTypeCd;
	}

	@Column(name = "LOAN_NAME")
	public String getLoanName() {
		return this.loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	@Column(name = "APPLY_AMT")
	public BigDecimal getApplyAmt() {
		return this.applyAmt;
	}

	public void setApplyAmt(BigDecimal applyAmt) {
		this.applyAmt = applyAmt;
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

	@Column(name = "EXPECT_LOAN_RATE")
	public BigDecimal getExpectLoanRate() {
		return this.expectLoanRate;
	}

	public void setExpectLoanRate(BigDecimal expectLoanRate) {
		this.expectLoanRate = expectLoanRate;
	}

	@Column(name = "REPAY_TYPE_CD")
	public String getRepayTypeCd() {
		return this.repayTypeCd;
	}

	public void setRepayTypeCd(String repayTypeCd) {
		this.repayTypeCd = repayTypeCd;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "APPLY_DATE")
	public Date getApplyDate() {
		return this.applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	@Column(name = "APPLY_STATUS_CD")
	public String getApplyStatusCd() {
		return this.applyStatusCd;
	}

	public void setApplyStatusCd(String applyStatusCd) {
		this.applyStatusCd = applyStatusCd;
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

	@Column(name = "PRODUCT_ID")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Column(name = "TEMDER_COUNT_AMT")
	public BigDecimal getTemderCountAmt() {
		return temderCountAmt;
	}

	public void setTemderCountAmt(BigDecimal temderCountAmt) {
		this.temderCountAmt = temderCountAmt;
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
	@Column(name = "RECOMMEND_PERCENT")
	public BigDecimal getRecommendPercent() {
		return recommendPercent;
	}

	public void setRecommendPercent(BigDecimal recommendPercent) {
		this.recommendPercent = recommendPercent;
	}

}