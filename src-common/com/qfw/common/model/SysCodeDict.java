package com.qfw.common.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

/**
 * SysCodeDict entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYS_CODE_DICT")
public class SysCodeDict implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	// Fields

	private Integer codeId;
	private String codeType;
	private String codeTypeName;
	private String codeValue;
	private String displayValue;
	private BigDecimal codeSort;
	private String remark;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;

	// Constructors

	/** default constructor */
	public SysCodeDict() {
	}

	// Property accessors
	@Id
	@Column(name = "CODE_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY,generator="code_dict_seq")  
	@GenericGenerator(name = "code_dict_seq", strategy = "identity")
	public Integer getCodeId() {
		return this.codeId;
	}

	public void setCodeId(Integer codeId) {
		this.codeId = codeId;
	}

	@Column(name = "CODE_TYPE", length = 32)
	public String getCodeType() {
		return this.codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	
	@Column(name = "CODE_TYPE_NAME")
	public String getCodeTypeName() {
		return codeTypeName;
	}

	public void setCodeTypeName(String codeTypeName) {
		this.codeTypeName = codeTypeName;
	}

	@Column(name = "CODE_VALUE", length = 15)
	public String getCodeValue() {
		return this.codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	@Column(name = "DISPLAY_VALUE", length = 20)
	public String getDisplayValue() {
		return this.displayValue;
	}

	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}
	
	@Column(name = "REMARK", length = 100)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
}