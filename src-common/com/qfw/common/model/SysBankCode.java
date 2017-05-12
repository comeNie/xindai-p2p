package com.qfw.common.model;

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
 * SysCodeDict entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYS_BANK_CODE")
public class SysBankCode implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	// Fields

	private Integer Id;
	private String bankCode;
	private String bankName;
	private String codeValue;
	private BigDecimal codeSort;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;
	private int enable;

	// Constructors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}
	@Column(name = "BANK_CODE")
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	@Column(name = "BANK_NAME")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/** default constructor */
	public SysBankCode() {
	}
	
	@Column(name = "CODE_VALUE", length = 15)
	public String getCodeValue() {
		return this.codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	
	@Column(name = "CODE_SORT")
	public BigDecimal getCodeSort() {
		return codeSort;
	}

	public void setCodeSort(BigDecimal codeSort) {
		this.codeSort = codeSort;
	}

	@Column(name = "SYS_CREATE_TIME", length = 19)
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

	@Column(name = "SYS_UPDATE_TIME")
	@Version
	public Date getSysUpdateTime() {
		return this.sysUpdateTime;
	}

	public void setSysUpdateTime(Date sysUpdateTime) {
		this.sysUpdateTime = sysUpdateTime;
	}

	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}
}