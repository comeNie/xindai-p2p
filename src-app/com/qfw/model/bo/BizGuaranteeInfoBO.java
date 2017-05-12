package com.qfw.model.bo;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 担保信息BO
 */


/**
 * BizGuaranteeInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "biz_guarantee_info")
public class BizGuaranteeInfoBO implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer guaranteeId;
	private Integer creditLimitId;
	private Integer custId;
	private String guarantorConNo;
	private String guarantorType;
	private BigDecimal guarantorAmt;
	private String guarantorName;
	private String guarantorPaType;
	private String guarantorPaNo;
	private String guaranteeType;
	private String guaranteeName;
	private BigDecimal guaranteeWorth;
	private String guaranteeExplain;
	private String guaranteeAtt1;
	private String guaranteeAtt2;
	private String guaranteeAtt3;
	private String guarantorAtt1;
	private String guarantorAtt2;
	private String guarantorAtt3;
	private String guarantorExplain;
	private String sysCreateUser;
	private Timestamp sysCreateTime;
	private String sysUpdateUser;
	private Timestamp sysUpdateTime;

	// Constructors

	/** default constructor */
	public BizGuaranteeInfoBO() {
	}

	/** minimal constructor */
	public BizGuaranteeInfoBO(Integer guaranteeId, Integer creditLimitId,
			Integer custId, String guarantorConNo, String guarantorType,
			BigDecimal guarantorAmt, String guarantorName,
			String guarantorPaType, String guarantorPaNo, String guaranteeType,
			String guaranteeName, BigDecimal guaranteeWorth,
			String guaranteeAtt1, String guarantorAtt1, String sysCreateUser,
			Timestamp sysCreateTime, String sysUpdateUser,
			Timestamp sysUpdateTime) {
		this.guaranteeId = guaranteeId;
		this.creditLimitId = creditLimitId;
		this.custId = custId;
		this.guarantorConNo = guarantorConNo;
		this.guarantorType = guarantorType;
		this.guarantorAmt = guarantorAmt;
		this.guarantorName = guarantorName;
		this.guarantorPaType = guarantorPaType;
		this.guarantorPaNo = guarantorPaNo;
		this.guaranteeType = guaranteeType;
		this.guaranteeName = guaranteeName;
		this.guaranteeWorth = guaranteeWorth;
		this.guaranteeAtt1 = guaranteeAtt1;
		this.guarantorAtt1 = guarantorAtt1;
		this.sysCreateUser = sysCreateUser;
		this.sysCreateTime = sysCreateTime;
		this.sysUpdateUser = sysUpdateUser;
		this.sysUpdateTime = sysUpdateTime;
	}

	/** full constructor */
	public BizGuaranteeInfoBO(Integer guaranteeId, Integer creditLimitId,
			Integer custId, String guarantorConNo, String guarantorType,
			BigDecimal guarantorAmt, String guarantorName,
			String guarantorPaType, String guarantorPaNo, String guaranteeType,
			String guaranteeName, BigDecimal guaranteeWorth,
			String guaranteeExplain, String guaranteeAtt1,
			String guaranteeAtt2, String guaranteeAtt3, String guarantorAtt1,
			String guarantorAtt2, String guarantorAtt3,
			String guarantorExplain, String sysCreateUser,
			Timestamp sysCreateTime, String sysUpdateUser,
			Timestamp sysUpdateTime) {
		this.guaranteeId = guaranteeId;
		this.creditLimitId = creditLimitId;
		this.custId = custId;
		this.guarantorConNo = guarantorConNo;
		this.guarantorType = guarantorType;
		this.guarantorAmt = guarantorAmt;
		this.guarantorName = guarantorName;
		this.guarantorPaType = guarantorPaType;
		this.guarantorPaNo = guarantorPaNo;
		this.guaranteeType = guaranteeType;
		this.guaranteeName = guaranteeName;
		this.guaranteeWorth = guaranteeWorth;
		this.guaranteeExplain = guaranteeExplain;
		this.guaranteeAtt1 = guaranteeAtt1;
		this.guaranteeAtt2 = guaranteeAtt2;
		this.guaranteeAtt3 = guaranteeAtt3;
		this.guarantorAtt1 = guarantorAtt1;
		this.guarantorAtt2 = guarantorAtt2;
		this.guarantorAtt3 = guarantorAtt3;
		this.guarantorExplain = guarantorExplain;
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

	@Column(name = "GUARANTEE_ID")
	public Integer getGuaranteeId() {
		return this.guaranteeId;
	}

	public void setGuaranteeId(Integer guaranteeId) {
		this.guaranteeId = guaranteeId;
	}

	@Column(name = "CREDIT_LIMIT_ID")
	public Integer getCreditLimitId() {
		return this.creditLimitId;
	}

	public void setCreditLimitId(Integer creditLimitId) {
		this.creditLimitId = creditLimitId;
	}

	@Column(name = "CUST_ID")
	public Integer getCustId() {
		return this.custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	@Column(name = "GUARANTOR_CON_NO")
	public String getGuarantorConNo() {
		return this.guarantorConNo;
	}

	public void setGuarantorConNo(String guarantorConNo) {
		this.guarantorConNo = guarantorConNo;
	}

	@Column(name = "GUARANTOR_TYPE")
	public String getGuarantorType() {
		return this.guarantorType;
	}

	public void setGuarantorType(String guarantorType) {
		this.guarantorType = guarantorType;
	}

	@Column(name = "GUARANTOR_AMT")
	public BigDecimal getGuarantorAmt() {
		return this.guarantorAmt;
	}

	public void setGuarantorAmt(BigDecimal guarantorAmt) {
		this.guarantorAmt = guarantorAmt;
	}

	@Column(name = "GUARANTOR_NAME")
	public String getGuarantorName() {
		return this.guarantorName;
	}

	public void setGuarantorName(String guarantorName) {
		this.guarantorName = guarantorName;
	}

	@Column(name = "GUARANTOR_PA_TYPE")
	public String getGuarantorPaType() {
		return this.guarantorPaType;
	}

	public void setGuarantorPaType(String guarantorPaType) {
		this.guarantorPaType = guarantorPaType;
	}

	@Column(name = "GUARANTOR_PA_NO")
	public String getGuarantorPaNo() {
		return this.guarantorPaNo;
	}

	public void setGuarantorPaNo(String guarantorPaNo) {
		this.guarantorPaNo = guarantorPaNo;
	}

	@Column(name = "GUARANTEE_TYPE")
	public String getGuaranteeType() {
		return this.guaranteeType;
	}

	public void setGuaranteeType(String guaranteeType) {
		this.guaranteeType = guaranteeType;
	}

	@Column(name = "GUARANTEE_NAME")
	public String getGuaranteeName() {
		return this.guaranteeName;
	}

	public void setGuaranteeName(String guaranteeName) {
		this.guaranteeName = guaranteeName;
	}

	@Column(name = "GUARANTEE_WORTH")
	public BigDecimal getGuaranteeWorth() {
		return this.guaranteeWorth;
	}

	public void setGuaranteeWorth(BigDecimal guaranteeWorth) {
		this.guaranteeWorth = guaranteeWorth;
	}

	@Column(name = "GUARANTEE_EXPLAIN")
	public String getGuaranteeExplain() {
		return this.guaranteeExplain;
	}

	public void setGuaranteeExplain(String guaranteeExplain) {
		this.guaranteeExplain = guaranteeExplain;
	}

	@Column(name = "GUARANTEE_ATT1")
	public String getGuaranteeAtt1() {
		return this.guaranteeAtt1;
	}

	public void setGuaranteeAtt1(String guaranteeAtt1) {
		this.guaranteeAtt1 = guaranteeAtt1;
	}

	@Column(name = "GUARANTEE_ATT2")
	public String getGuaranteeAtt2() {
		return this.guaranteeAtt2;
	}

	public void setGuaranteeAtt2(String guaranteeAtt2) {
		this.guaranteeAtt2 = guaranteeAtt2;
	}

	@Column(name = "GUARANTEE_ATT3")
	public String getGuaranteeAtt3() {
		return this.guaranteeAtt3;
	}

	public void setGuaranteeAtt3(String guaranteeAtt3) {
		this.guaranteeAtt3 = guaranteeAtt3;
	}

	@Column(name = "GUARANTOR_ATT1")
	public String getGuarantorAtt1() {
		return this.guarantorAtt1;
	}

	public void setGuarantorAtt1(String guarantorAtt1) {
		this.guarantorAtt1 = guarantorAtt1;
	}

	@Column(name = "GUARANTOR_ATT2")
	public String getGuarantorAtt2() {
		return this.guarantorAtt2;
	}

	public void setGuarantorAtt2(String guarantorAtt2) {
		this.guarantorAtt2 = guarantorAtt2;
	}

	@Column(name = "GUARANTOR_ATT3")
	public String getGuarantorAtt3() {
		return this.guarantorAtt3;
	}

	public void setGuarantorAtt3(String guarantorAtt3) {
		this.guarantorAtt3 = guarantorAtt3;
	}

	@Column(name = "GUARANTOR_EXPLAIN")
	public String getGuarantorExplain() {
		return this.guarantorExplain;
	}

	public void setGuarantorExplain(String guarantorExplain) {
		this.guarantorExplain = guarantorExplain;
	}

	@Column(name = "SYS_CREATE_USER")
	public String getSysCreateUser() {
		return this.sysCreateUser;
	}

	public void setSysCreateUser(String sysCreateUser) {
		this.sysCreateUser = sysCreateUser;
	}

	@Column(name = "SYS_CREATE_TIME")
	public Timestamp getSysCreateTime() {
		return this.sysCreateTime;
	}

	public void setSysCreateTime(Timestamp sysCreateTime) {
		this.sysCreateTime = sysCreateTime;
	}

	@Column(name = "SYS_UPDATE_USER")
	public String getSysUpdateUser() {
		return this.sysUpdateUser;
	}

	public void setSysUpdateUser(String sysUpdateUser) {
		this.sysUpdateUser = sysUpdateUser;
	}

	@Column(name = "SYS_UPDATE_TIME")
	public Timestamp getSysUpdateTime() {
		return this.sysUpdateTime;
	}

	public void setSysUpdateTime(Timestamp sysUpdateTime) {
		this.sysUpdateTime = sysUpdateTime;
	}

}