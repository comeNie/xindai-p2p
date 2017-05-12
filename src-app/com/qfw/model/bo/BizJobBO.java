package com.qfw.model.bo;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * BizJob entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "biz_job")
public class BizJobBO implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer custId;
	private String jobStatusCd;
	private String jobCompanyName;
	private String jobDeptName;
	private String companyTypeCd;
	private String companyIndustryCd;
	private String companyScaleCd;
	private String jobPosition;
	private String jobEmalil;
	private String companyPhone;
	private String companyAddress;
	private Integer jobYearCd;
	private String jobIncomeCd;
	private String jobYearIncomeCd;
	private String workItemId;
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;

	// Constructors

	/** default constructor */
	public BizJobBO() {
	}

	/** minimal constructor */
	public BizJobBO(Integer custId, String jobDeptName, String workItemId,
			Integer sysCreateUser, Date sysCreateTime,
			Integer sysUpdateUser, Date sysUpdateTime) {
		this.custId = custId;
		this.jobDeptName = jobDeptName;
		this.workItemId = workItemId;
		this.sysCreateUser = sysCreateUser;
		this.sysCreateTime = sysCreateTime;
		this.sysUpdateUser = sysUpdateUser;
		this.sysUpdateTime = sysUpdateTime;
	}

	/** full constructor */
	public BizJobBO(Integer custId, String jobStatusCd, String jobCompanyName,
			String jobDeptName, String companyTypeCd, String companyIndustryCd,
			String companyScaleCd, String jobPosition, String jobEmalil,
			String companyPhone, String companyAddress, Integer jobYearCd,
			String jobIncomeCd, String jobYearIncomeCd, String workItemId,
			Integer sysCreateUser, Date sysCreateTime,
			Integer sysUpdateUser, Date sysUpdateTime) {
		this.custId = custId;
		this.jobStatusCd = jobStatusCd;
		this.jobCompanyName = jobCompanyName;
		this.jobDeptName = jobDeptName;
		this.companyTypeCd = companyTypeCd;
		this.companyIndustryCd = companyIndustryCd;
		this.companyScaleCd = companyScaleCd;
		this.jobPosition = jobPosition;
		this.jobEmalil = jobEmalil;
		this.companyPhone = companyPhone;
		this.companyAddress = companyAddress;
		this.jobYearCd = jobYearCd;
		this.jobIncomeCd = jobIncomeCd;
		this.jobYearIncomeCd = jobYearIncomeCd;
		this.workItemId = workItemId;
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
	public Integer getCustId() {
		return this.custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	@Column(name = "JOB_STATUS_CD")
	public String getJobStatusCd() {
		return this.jobStatusCd;
	}

	public void setJobStatusCd(String jobStatusCd) {
		this.jobStatusCd = jobStatusCd;
	}

	@Column(name = "JOB_COMPANY_NAME")
	public String getJobCompanyName() {
		return this.jobCompanyName;
	}

	public void setJobCompanyName(String jobCompanyName) {
		this.jobCompanyName = jobCompanyName;
	}

	@Column(name = "JOB_DEPT_NAME")
	public String getJobDeptName() {
		return this.jobDeptName;
	}

	public void setJobDeptName(String jobDeptName) {
		this.jobDeptName = jobDeptName;
	}

	@Column(name = "COMPANY_TYPE_CD")
	public String getCompanyTypeCd() {
		return this.companyTypeCd;
	}

	public void setCompanyTypeCd(String companyTypeCd) {
		this.companyTypeCd = companyTypeCd;
	}

	@Column(name = "COMPANY_INDUSTRY_CD")
	public String getCompanyIndustryCd() {
		return this.companyIndustryCd;
	}

	public void setCompanyIndustryCd(String companyIndustryCd) {
		this.companyIndustryCd = companyIndustryCd;
	}

	@Column(name = "COMPANY_SCALE_CD")
	public String getCompanyScaleCd() {
		return this.companyScaleCd;
	}

	public void setCompanyScaleCd(String companyScaleCd) {
		this.companyScaleCd = companyScaleCd;
	}

	@Column(name = "JOB_POSITION")
	public String getJobPosition() {
		return this.jobPosition;
	}

	public void setJobPosition(String jobPosition) {
		this.jobPosition = jobPosition;
	}

	@Column(name = "JOB_EMALIL")
	public String getJobEmalil() {
		return this.jobEmalil;
	}

	public void setJobEmalil(String jobEmalil) {
		this.jobEmalil = jobEmalil;
	}

	@Column(name = "COMPANY_PHONE")
	public String getCompanyPhone() {
		return this.companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	@Column(name = "COMPANY_ADDRESS")
	public String getCompanyAddress() {
		return this.companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	@Column(name = "JOB_YEAR_CD")
	public Integer getJobYearCd() {
		return this.jobYearCd;
	}

	public void setJobYearCd(Integer jobYearCd) {
		this.jobYearCd = jobYearCd;
	}

	@Column(name = "JOB_INCOME_CD")
	public String getJobIncomeCd() {
		return this.jobIncomeCd;
	}

	public void setJobIncomeCd(String jobIncomeCd) {
		this.jobIncomeCd = jobIncomeCd;
	}

	@Column(name = "JOB_YEAR_INCOME_CD")
	public String getJobYearIncomeCd() {
		return this.jobYearIncomeCd;
	}

	public void setJobYearIncomeCd(String jobYearIncomeCd) {
		this.jobYearIncomeCd = jobYearIncomeCd;
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

}