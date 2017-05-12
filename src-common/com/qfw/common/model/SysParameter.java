package com.qfw.common.model;

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
 * SysParameter entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYS_PARAMETER")
public class SysParameter implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 1L;
	private Integer parameterId;
	private String parameterCode;
	private String parameterValue;
	private String remark;
	private String parameterTypeCd;
	private String parameterTypeName;
	private Integer parameterSort;
	private Integer sysCreateUser;
	private String parameterDisplayName;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;

	// Constructors

	/** default constructor */
	public SysParameter() {
	}

	/** full constructor */
	public SysParameter(String parameterCode, String parameterValue, String remark,
			String parameterTypeCd, String parameterTypeName, Integer parameterSort,
			String parameterDisplayName, Integer sysCreateUser, Date sysCreateTime,
			Integer sysUpdateUser, Date sysUpdateTime) {
		this.parameterCode = parameterCode;
		this.parameterValue = parameterValue;
		this.remark = remark;
		this.parameterTypeCd = parameterTypeCd;
		this.parameterTypeName = parameterTypeName;
		this.parameterSort = parameterSort;
		this.parameterDisplayName = parameterDisplayName;
		this.sysCreateUser = sysCreateUser;
		this.sysCreateTime = sysCreateTime;
		this.sysUpdateUser = sysUpdateUser;
		this.sysUpdateTime = sysUpdateTime;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY,generator="parameter_seq")  
	@GenericGenerator(name = "parameter_seq", strategy = "identity")
	@Column(name = "PARAMETER_ID")
	public Integer getParameterId() {
		return this.parameterId;
	}

	public void setParameterId(Integer parameterId) {
		this.parameterId = parameterId;
	}

	@Column(name = "PARAMETER_CODE", length = 20)
	public String getParameterCode() {
		return this.parameterCode;
	}

	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}

	@Column(name = "PARAMETER_VALUE", length = 50)
	public String getParameterValue() {
		return this.parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	
	@Column(name = "REMARK", length = 100)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "PARAMETER_TYPE_CD")
	public String getParameterTypeCd() {
		return parameterTypeCd;
	}

	public void setParameterTypeCd(String parameterTypeCd) {
		this.parameterTypeCd = parameterTypeCd;
	}
	
	@Column(name = "PARAMETER_TYPE_NAME", length = 50)
	public String getParameterTypeName() {
		return parameterTypeName;
	}

	public void setParameterTypeName(String parameterTypeName) {
		this.parameterTypeName = parameterTypeName;
	}
	
	@Column(name = "PARAMETER_SORT")
	public Integer getParameterSort() {
		return parameterSort;
	}

	public void setParameterSort(Integer parameterSort) {
		this.parameterSort = parameterSort;
	}
	
	@Column(name = "PARAMETER_DISPLAY_NAME", length = 100)
	public String getParameterDisplayName() {
		return parameterDisplayName;
	}

	public void setParameterDisplayName(String parameterDisplayName) {
		this.parameterDisplayName = parameterDisplayName;
	}

	@Column(name = "SYS_CREATE_USER", nullable = true)
	public Integer getSysCreateUser() {
		return this.sysCreateUser;
	}

	public void setSysCreateUser(Integer sysCreateUser) {
		this.sysCreateUser = sysCreateUser;
	}

	@Column(name = "SYS_CREATE_TIME", nullable = true, length = 19)
	public Date getSysCreateTime() {
		return this.sysCreateTime;
	}

	public void setSysCreateTime(Date sysCreateTime) {
		this.sysCreateTime = sysCreateTime;
	}

	@Column(name = "SYS_UPDATE_USER", nullable = true)
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