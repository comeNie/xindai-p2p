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
 * BizContacts entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "biz_contacts")
public class BizContactsBO implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer custId;
	private String contactsName;
	private String contactsTypeCd;
	private String certificateTypeCd;
	private String certificateNum;
	private String telephone;
	private String mobileTelephone;
	private String address;
	private String jobStatusCd;
	private String workItemId;
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;

	// Constructors

	/** default constructor */
	public BizContactsBO() {
	}

	/** minimal constructor */
	public BizContactsBO(Integer custId, String workItemId,
			Integer sysCreateUser, Date sysCreateTime,
			Integer sysUpdateUser, Date sysUpdateTime) {
		this.custId = custId;
		this.workItemId = workItemId;
		this.sysCreateUser = sysCreateUser;
		this.sysCreateTime = sysCreateTime;
		this.sysUpdateUser = sysUpdateUser;
		this.sysUpdateTime = sysUpdateTime;
	}

	/** full constructor */
	public BizContactsBO(Integer custId, String contactsName,
			String contactsTypeCd, String certificateTypeCd,
			String certificateNum, String telephone, String mobileTelephone,
			String address, String jobStatusCd, String workItemId,
			Integer sysCreateUser, Date sysCreateTime,
			Integer sysUpdateUser, Date sysUpdateTime) {
		this.custId = custId;
		this.contactsName = contactsName;
		this.contactsTypeCd = contactsTypeCd;
		this.certificateTypeCd = certificateTypeCd;
		this.certificateNum = certificateNum;
		this.telephone = telephone;
		this.mobileTelephone = mobileTelephone;
		this.address = address;
		this.jobStatusCd = jobStatusCd;
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

	@Column(name = "CUST_ID", nullable = false)
	public Integer getCustId() {
		return this.custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	@Column(name = "CONTACTS_NAME")
	public String getContactsName() {
		return this.contactsName;
	}

	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}

	@Column(name = "CONTACTS_TYPE_CD")
	public String getContactsTypeCd() {
		return this.contactsTypeCd;
	}

	public void setContactsTypeCd(String contactsTypeCd) {
		this.contactsTypeCd = contactsTypeCd;
	}

	@Column(name = "CERTIFICATE_TYPE_CD")
	public String getCertificateTypeCd() {
		return this.certificateTypeCd;
	}

	public void setCertificateTypeCd(String certificateTypeCd) {
		this.certificateTypeCd = certificateTypeCd;
	}

	@Column(name = "CERTIFICATE_NUM")
	public String getCertificateNum() {
		return this.certificateNum;
	}

	public void setCertificateNum(String certificateNum) {
		this.certificateNum = certificateNum;
	}

	@Column(name = "TELEPHONE")
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "MOBILE_TELEPHONE")
	public String getMobileTelephone() {
		return this.mobileTelephone;
	}

	public void setMobileTelephone(String mobileTelephone) {
		this.mobileTelephone = mobileTelephone;
	}

	@Column(name = "ADDRESS")
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "JOB_STATUS_CD")
	public String getJobStatusCd() {
		return this.jobStatusCd;
	}

	public void setJobStatusCd(String jobStatusCd) {
		this.jobStatusCd = jobStatusCd;
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