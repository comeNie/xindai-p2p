package com.qfw.model.bo;

import static javax.persistence.GenerationType.IDENTITY;





import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.qfw.common.model.BussConst;
import com.qfw.common.util.StringUtils;


/**
 * BizCustomer entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "biz_customer")
public class BizCustomerBO implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userId;
	private String custName;
	private String custTypeCd;
	private String certificateTypeCd;
	private String certificateNum;
	private Integer age;
	private String sex;
	private String domicile;
	private String educationCd;
	private String maritalStatusCd;
	private String hasChildrenCd;
	private String countyCd;
	private String provinceCd;
	private String cityCd;
	private String nationalityCd;
	private String streetAddress;
	
	private String liveCountyCd;
	private String liveProvinceCd;
	private String liveCityCd;
	private String liveNationalityCd;
	private String liveStreetAddress;
	private String zipNum;
	private String telephone;
	private String mobileTelephone;
	private String fax;
	private String qq;
	private String website;
	private Integer score;
	private String email;
	private String refereeName;//推荐人
	//private String 
	private Integer custManagerId;//客户经理id
	private String custManagerName;//客户经理名称
	private Integer deptId;//所属机构id
	private String deptName;//所属部门
	private String workItemId;
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;
	private String creditRate;
	private String refereeCode;//推荐码
	private Integer refereeId;//推荐人id
	
	private Date birthDate;//生日
	private String isVip;
	
	private String refereeStatus;
	private Date refereeDate;
	// Constructors

	/** default constructor */
	public BizCustomerBO() {
		
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

	@Column(name = "USER_ID")
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "CUST_NAME", length = 20)
	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	@Column(name = "CUST_TYPE_CD", length = 2)
	public String getCustTypeCd() {
		return this.custTypeCd;
	}

	public void setCustTypeCd(String custTypeCd) {
		this.custTypeCd = custTypeCd;
	}

	@Column(name = "CERTIFICATE_TYPE_CD", length = 2)
	public String getCertificateTypeCd() {
		return this.certificateTypeCd;
	}

	public void setCertificateTypeCd(String certificateTypeCd) {
		this.certificateTypeCd = certificateTypeCd;
	}

	@Column(name = "CERTIFICATE_NUM", length = 20)
	public String getCertificateNum() {
		return this.certificateNum;
	}

	public void setCertificateNum(String certificateNum) {
		this.certificateNum = certificateNum;
	}

	@Column(name = "MARITAL_STATUS_CD", length = 2)
	public String getMaritalStatusCd() {
		return this.maritalStatusCd;
	}

	public void setMaritalStatusCd(String maritalStatusCd) {
		this.maritalStatusCd = maritalStatusCd;
	}

	@Column(name = "HAS_CHILDREN_CD", length = 2)
	public String getHasChildrenCd() {
		return this.hasChildrenCd;
	}

	public void setHasChildrenCd(String hasChildrenCd) {
		this.hasChildrenCd = hasChildrenCd;
	}

	@Column(name = "COUNTY_CD", length = 10)
	public String getCountyCd() {
		return this.countyCd;
	}

	public void setCountyCd(String countyCd) {
		this.countyCd = countyCd;
	}

	@Column(name = "PROVINCE_CD", length = 10)
	public String getProvinceCd() {
		return this.provinceCd;
	}

	public void setProvinceCd(String provinceCd) {
		this.provinceCd = provinceCd;
	}

	@Column(name = "CITY_CD", length = 10)
	public String getCityCd() {
		return this.cityCd;
	}

	public void setCityCd(String cityCd) {
		this.cityCd = cityCd;
	}

	@Column(name = "NATIONALITY_CD", length = 10)
	public String getNationalityCd() {
		return this.nationalityCd;
	}

	public void setNationalityCd(String nationalityCd) {
		this.nationalityCd = nationalityCd;
	}

	@Column(name = "STREET_ADDRESS", length = 200)
	public String getStreetAddress() {
		return this.streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	@Column(name = "LIVE_COUNTY_CD", length = 10)
	public String getLiveCountyCd() {
		return liveCountyCd;
	}

	public void setLiveCountyCd(String liveCountyCd) {
		this.liveCountyCd = liveCountyCd;
	}
	@Column(name = "LIVE_PROVINCE_CD", length = 10)
	public String getLiveProvinceCd() {
		return liveProvinceCd;
	}

	public void setLiveProvinceCd(String liveProvinceCd) {
		this.liveProvinceCd = liveProvinceCd;
	}

	@Column(name = "LIVE_CITY_CD", length = 10)
	public String getLiveCityCd() {
		return liveCityCd;
	}

	public void setLiveCityCd(String liveCityCd) {
		this.liveCityCd = liveCityCd;
	}

	@Column(name = "LIVE_NATIONALITY_CD", length = 10)
	public String getLiveNationalityCd() {
		return liveNationalityCd;
	}

	public void setLiveNationalityCd(String liveNationalityCd) {
		this.liveNationalityCd = liveNationalityCd;
	}
	
	@Column(name = "LIVE_STREET_ADDRESS", length = 200)
	public String getLiveStreetAddress() {
		return liveStreetAddress;
	}

	public void setLiveStreetAddress(String liveStreetAddress) {
		this.liveStreetAddress = liveStreetAddress;
	}

	@Column(name = "ZIP_NUM", length = 6)
	public String getZipNum() {
		return this.zipNum;
	}

	public void setZipNum(String zipNum) {
		this.zipNum = zipNum;
	}

	@Column(name = "TELEPHONE", length = 20)
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "MOBILE_TELEPHONE", length = 20)
	public String getMobileTelephone() {
		return this.mobileTelephone;
	}

	public void setMobileTelephone(String mobileTelephone) {
		this.mobileTelephone = mobileTelephone;
	}

	@Column(name = "FAX", length = 20)
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "QQ", length = 20)
	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@Column(name = "WEBSITE", length = 200)
	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
	
	@Column(name = "SCORE")
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	
	
	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name = "REFEREE_NAME")
	public String getRefereeName() {
		return refereeName;
	}

	public void setRefereeName(String refereeName) {
		this.refereeName = refereeName;
	}

	@Column(name = "CUST_MANAGER_ID")
	public Integer getCustManagerId() {
		return custManagerId;
	}

	public void setCustManagerId(Integer custManagerId) {
		this.custManagerId = custManagerId;
	}

	@Column(name = "CUST_MANAGER_NAME")
	public String getCustManagerName() {
		return custManagerName;
	}

	public void setCustManagerName(String custManagerName) {
		this.custManagerName = custManagerName;
	}

	@Column(name = "DEPT_ID")
	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	@Column(name = "DEPT_NAME")
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
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
	@Column(name = "AGE")
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	@Column(name = "SEX", length = 2)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	@Column(name = "DOMICILE")
	public String getDomicile() {
		return domicile;
	}

	public void setDomicile(String domicile) {
		this.domicile = domicile;
	}
	@Column(name = "EDUCATION_CD")
	public String getEducationCd() {
		return educationCd;
	}

	public void setEducationCd(String educationCd) {
		this.educationCd = educationCd;
	}

	@Column(name = "CREDIT_RATE")
	public String getCreditRate() {
		return creditRate;
	}

	public void setCreditRate(String creditRate) {
		this.creditRate = creditRate;
	}
	@Column(name = "REFEREE_CODE")
	public String getRefereeCode() {
		return refereeCode;
	}

	public void setRefereeCode(String refereeCode) {
		this.refereeCode = refereeCode;
	}
	@Column(name = "REFEREE_ID")
	public Integer getRefereeId() {
		return refereeId;
	}

	public void setRefereeId(Integer refereeId) {
		this.refereeId = refereeId;
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "BIRTH_DATE", length = 10)
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	@Column(name = "IS_VIP")
	public String getIsVip() {
		if(StringUtils.isEmpty(isVip)){
			isVip = BussConst.NO_FLAG;
		}
		return isVip;
	}

	public void setIsVip(String isVip) {
		this.isVip = isVip;
	}
	@Column(name = "REFEREE_STATUS")
	public String getRefereeStatus() {
		return refereeStatus;
	}

	public void setRefereeStatus(String refereeStatus) {
		this.refereeStatus = refereeStatus;
	}
	@Column(name = "REFEREE_DATE")
	public Date getRefereeDate() {
		return refereeDate;
	}

	public void setRefereeDate(Date refereeDate) {
		this.refereeDate = refereeDate;
	}

}