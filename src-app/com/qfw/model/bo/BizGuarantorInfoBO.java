package com.qfw.model.bo;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * BizGuarantorInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "biz_guarantor_info")
public class BizGuarantorInfoBO implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer creditLimitId;
	private Integer custId;
	private String guarantorName;
	private String guarantorPaType;
	private String guarantorPaNo;
	private BigDecimal guarantorAmt;
	private String guarantTypeCd;
	private String guarantorName1;
	private String guarantorAtt1;
	private String guarantorName2;
	private String guarantorAtt2;
	private String guarantorName3;
	private String guarantorAtt3;
	private String guarantorName4;
	private String guarantorAtt4;
	private String guarantorExplain;
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;

	// Constructors

	/** default constructor */
	public BizGuarantorInfoBO() {
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

	@Column(name = "GUARANTOR_AMT")
	public BigDecimal getGuarantorAmt() {
		return this.guarantorAmt;
	}

	public void setGuarantorAmt(BigDecimal guarantorAmt) {
		this.guarantorAmt = guarantorAmt;
	}

	@Column(name = "GUARANT_TYPE_CD")
	public String getGuarantTypeCd() {
		return this.guarantTypeCd;
	}

	public void setGuarantTypeCd(String guarantTypeCd) {
		this.guarantTypeCd = guarantTypeCd;
	}

	@Column(name = "GUARANTOR_NAME1")
	public String getGuarantorName1() {
		return guarantorName1;
	}

	public void setGuarantorName1(String guarantorName1) {
		this.guarantorName1 = guarantorName1;
	}

	@Column(name = "GUARANTOR_NAME2")
	public String getGuarantorName2() {
		return guarantorName2;
	}


	public void setGuarantorName2(String guarantorName2) {
		this.guarantorName2 = guarantorName2;
	}

	@Column(name = "GUARANTOR_NAME3",  length = 100)
	public String getGuarantorName3() {
		return guarantorName3;
	}


	public void setGuarantorName3(String guarantorName3) {
		this.guarantorName3 = guarantorName3;
	}

	@Column(name = "GUARANTOR_NAME4",  length = 100)
	public String getGuarantorName4() {
		return guarantorName4;
	}


	public void setGuarantorName4(String guarantorName4) {
		this.guarantorName4 = guarantorName4;
	}


	@Column(name = "GUARANTOR_ATT1")
	public String getGuarantorAtt1() {
		return this.guarantorAtt1;
	}

	public void setGuarantorAtt1(String guarantorAtt1) {
		this.guarantorAtt1 = guarantorAtt1;
	}

	@Column(name = "GUARANTOR_ATT2", length = 100)
	public String getGuarantorAtt2() {
		return this.guarantorAtt2;
	}

	public void setGuarantorAtt2(String guarantorAtt2) {
		this.guarantorAtt2 = guarantorAtt2;
	}

	@Column(name = "GUARANTOR_ATT3", length = 100)
	public String getGuarantorAtt3() {
		return this.guarantorAtt3;
	}

	public void setGuarantorAtt3(String guarantorAtt3) {
		this.guarantorAtt3 = guarantorAtt3;
	}

	@Column(name = "GUARANTOR_ATT4", length = 100)
	public String getGuarantorAtt4() {
		return this.guarantorAtt4;
	}

	public void setGuarantorAtt4(String guarantorAtt4) {
		this.guarantorAtt4 = guarantorAtt4;
	}

	@Column(name = "GUARANTOR_EXPLAIN", length = 100)
	public String getGuarantorExplain() {
		return this.guarantorExplain;
	}

	public void setGuarantorExplain(String guarantorExplain) {
		this.guarantorExplain = guarantorExplain;
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