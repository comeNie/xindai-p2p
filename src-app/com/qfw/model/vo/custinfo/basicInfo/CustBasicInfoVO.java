package com.qfw.model.vo.custinfo.basicInfo;

import java.util.Date;

/**
 * 客户基本信息
 *
 * @author kyc
 */
public class CustBasicInfoVO {

	private Integer custId;  // 客户ID
	private String account;  // 账户
	private String age;		// 年龄
	private String sex;		// 性别
	private String educationCd; // 学历
	private Integer jobYearCd;	// 工作年限
	private String maritalStatusCd; // 婚姻状况
	private String jobIncomeCd;		// 月收入
	private String jobPosition;		// 职位
	private String custName; // 客户名称
	private String certificateTypeCd; // 证件类型
	private String certificateNum; // 证件号码
	private Date sysCreateTime; // 注册日期
	private String mobileTelephone;	// 电话
	private String fax;				// 传真
	private String qq;				// qq
	
	
	public Integer getCustId() {
		return custId;
	}
	public void setCustId(Integer custId) {
		this.custId = custId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getEducationCd() {
		return educationCd;
	}
	public void setEducationCd(String educationCd) {
		this.educationCd = educationCd;
	}
	public Integer getJobYearCd() {
		return jobYearCd;
	}
	public void setJobYearCd(Integer jobYearCd) {
		this.jobYearCd = jobYearCd;
	}
	public String getMaritalStatusCd() {
		return maritalStatusCd;
	}
	public void setMaritalStatusCd(String maritalStatusCd) {
		this.maritalStatusCd = maritalStatusCd;
	}
	public String getJobIncomeCd() {
		return jobIncomeCd;
	}
	public void setJobIncomeCd(String jobIncomeCd) {
		this.jobIncomeCd = jobIncomeCd;
	}
	public String getJobPosition() {
		return jobPosition;
	}
	public void setJobPosition(String jobPosition) {
		this.jobPosition = jobPosition;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCertificateTypeCd() {
		return certificateTypeCd;
	}
	public void setCertificateTypeCd(String certificateTypeCd) {
		this.certificateTypeCd = certificateTypeCd;
	}
	public String getCertificateNum() {
		return certificateNum;
	}
	public void setCertificateNum(String certificateNum) {
		this.certificateNum = certificateNum;
	}
	public Date getSysCreateTime() {
		return sysCreateTime;
	}
	public void setSysCreateTime(Date sysCreateTime) {
		this.sysCreateTime = sysCreateTime;
	}
	public String getMobileTelephone() {
		return mobileTelephone;
	}
	public void setMobileTelephone(String mobileTelephone) {
		this.mobileTelephone = mobileTelephone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
}