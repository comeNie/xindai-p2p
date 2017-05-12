package com.qfw.model.bo;

import static javax.persistence.GenerationType.IDENTITY;

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
@Table(name = "biz_card")
public class BizCardBO extends BizBaseBO implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer custId;
	private String bankType;
	private String accountNum;
	private String accountName;
	private String accountBank;
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;
	
	private String bankName; 	
	private String areaCity;
	private String areaProvince;
	

	// Constructors

	/** default constructor */
	public BizCardBO() {
	}

	/** full constructor */
	public BizCardBO(Integer custId, String bankType,
			String accountNum, String accountName, String accountBank,
			Integer sysCreateUser, Timestamp sysCreateTime,
			Integer sysUpdateUser, Timestamp sysUpdateTime) {
		this.custId = custId;
		this.bankType = bankType;
		this.accountNum = accountNum;
		this.accountName = accountName;
		this.accountBank = accountBank;
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

	@Column(name = "BANK_TYPE", nullable = false, length = 10)
	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	@Column(name = "ACCOUNT_NUM", nullable = false, length = 20)
	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	@Column(name = "ACCOUNT_NAME", nullable = false, length = 20)
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	@Column(name = "ACCOUNT_BANK", nullable = true, length = 50)
	public String getAccountBank() {
		return accountBank;
	}

	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
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

	@Column(name = "BANK_NAME", length = 32)
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name = "AREA_CITY", length = 50)
	public String getAreaCity() {
		return areaCity;
	}

	public void setAreaCity(String areaCity) {
		this.areaCity = areaCity;
	}

	@Column(name = "AREA_PROVINCE", length = 50)
	public String getAreaProvince() {
		return areaProvince;
	}

	public void setAreaProvince(String areaProvince) {
		this.areaProvince = areaProvince;
	}

}