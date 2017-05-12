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
 * BizCollateralInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "biz_collateral_info")
public class BizCollateralInfoBO implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer creditLimitId;
	private Integer custId;
	private String guarantTypeCd;
	private String collateralType;
	private String collateralName;
	private BigDecimal collateralWorth;
	private String collateralExplain;
	private String guarantorName;
	private String guarantorPaType;
	private String guarantorPaNo;
	private String collateralName1;
	private String collateralAtt1;
	private String collateralName2;
	private String collateralAtt2;
	private String collateralName3;
	private String collateralAtt3;
	private String collateralName4;
	private String collateralAtt4;
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;

	// Constructors

	/** default constructor */
	public BizCollateralInfoBO() {
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

	@Column(name = "GUARANT_TYPE_CD")
	public String getGuarantTypeCd() {
		return this.guarantTypeCd;
	}

	public void setGuarantTypeCd(String guarantTypeCd) {
		this.guarantTypeCd = guarantTypeCd;
	}

	@Column(name = "COLLATERAL_TYPE")
	public String getCollateralType() {
		return this.collateralType;
	}

	public void setCollateralType(String collateralType) {
		this.collateralType = collateralType;
	}

	@Column(name = "COLLATERAL_NAME")
	public String getCollateralName() {
		return this.collateralName;
	}

	public void setCollateralName(String collateralName) {
		this.collateralName = collateralName;
	}

	@Column(name = "COLLATERAL_WORTH")
	public BigDecimal getCollateralWorth() {
		return this.collateralWorth;
	}

	public void setCollateralWorth(BigDecimal collateralWorth) {
		this.collateralWorth = collateralWorth;
	}

	@Column(name = "COLLATERAL_EXPLAIN")
	public String getCollateralExplain() {
		return this.collateralExplain;
	}

	public void setCollateralExplain(String collateralExplain) {
		this.collateralExplain = collateralExplain;
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

	@Column(name = "COLLATERAL_NAME1")
	public String getCollateralName1() {
		return collateralName1;
	}

	public void setCollateralName1(String collateralName1) {
		this.collateralName1 = collateralName1;
	}

	@Column(name = "COLLATERAL_NAME2")
	public String getCollateralName2() {
		return collateralName2;
	}

	public void setCollateralName2(String collateralName2) {
		this.collateralName2 = collateralName2;
	}

	@Column(name = "COLLATERAL_NAME3")
	public String getCollateralName3() {
		return collateralName3;
	}

	public void setCollateralName3(String collateralName3) {
		this.collateralName3 = collateralName3;
	}
	@Column(name = "COLLATERAL_NAME4")
	public String getCollateralName4() {
		return collateralName4;
	}

	public void setCollateralName4(String collateralName4) {
		this.collateralName4 = collateralName4;
	}

	@Column(name = "COLLATERAL_ATT1")
	public String getCollateralAtt1() {
		return this.collateralAtt1;
	}

	public void setCollateralAtt1(String collateralAtt1) {
		this.collateralAtt1 = collateralAtt1;
	}

	@Column(name = "COLLATERAL_ATT2")
	public String getCollateralAtt2() {
		return this.collateralAtt2;
	}

	public void setCollateralAtt2(String collateralAtt2) {
		this.collateralAtt2 = collateralAtt2;
	}

	@Column(name = "COLLATERAL_ATT3")
	public String getCollateralAtt3() {
		return this.collateralAtt3;
	}

	public void setCollateralAtt3(String collateralAtt3) {
		this.collateralAtt3 = collateralAtt3;
	}

	@Column(name = "COLLATERAL_ATT4")
	public String getCollateralAtt4() {
		return this.collateralAtt4;
	}

	public void setCollateralAtt4(String collateralAtt4) {
		this.collateralAtt4 = collateralAtt4;
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