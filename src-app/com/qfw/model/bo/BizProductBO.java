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
 * BizProduct entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "biz_product")
public class BizProductBO implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String productName;
	private String productDesc;
	private String productNum;
	private String productImgUrl;
	private BigDecimal leastRateYear;
	private BigDecimal mostRateYear;
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;

	// Constructors

	/** default constructor */
	public BizProductBO() {
	}

	/** full constructor */
	public BizProductBO(String productName, String productDesc,
			String productNum, String productImgUrl, BigDecimal leastRateYear,
			BigDecimal mostRateYear, Integer sysCreateUser, Date sysCreateTime,
			Integer sysUpdateUser, Date sysUpdateTime) {
		this.productName = productName;
		this.productDesc = productDesc;
		this.productNum = productNum;
		this.productImgUrl = productImgUrl;
		this.leastRateYear = leastRateYear;
		this.mostRateYear = mostRateYear;
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

	@Column(name = "PRODUCT_NAME")
	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Column(name = "PRODUCT_DESC")
	public String getProductDesc() {
		return this.productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	@Column(name = "PRODUCT_NUM")
	public String getProductNum() {
		return this.productNum;
	}

	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}

	@Column(name = "PRODUCT_IMG_URL")
	public String getProductImgUrl() {
		return this.productImgUrl;
	}

	public void setProductImgUrl(String productImgUrl) {
		this.productImgUrl = productImgUrl;
	}

	@Column(name = "LEAST_RATE_YEAR")
	public BigDecimal getLeastRateYear() {
		return this.leastRateYear;
	}

	public void setLeastRateYear(BigDecimal leastRateYear) {
		this.leastRateYear = leastRateYear;
	}

	@Column(name = "MOST_RATE_YEAR")
	public BigDecimal getMostRateYear() {
		return this.mostRateYear;
	}

	public void setMostRateYear(BigDecimal mostRateYear) {
		this.mostRateYear = mostRateYear;
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