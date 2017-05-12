package com.qfw.model.vo.custinfo;

import java.math.BigDecimal;
import java.util.Date;


public class CustInfoVO {

	private Integer custId;  // 客户ID
	private String account;  // 账户
	private BigDecimal accountBalAmt; // 账户余额
	private BigDecimal usableBalAmt; // 可用余额
	private String custName; // 客户名称
	private String certificateTypeCd; // 证件类型
	private String certificateNum; // 证件号码
	private Date sysCreateTime; // 注册日期
	private BigDecimal clAmt;  // 额度金额
	private BigDecimal balClAmt; // 剩余额度
	private BigDecimal pmAmt; // PM币 
	
	private String phone;			// 电话
	private String userCode;		// 登录名
	private String custManager;		// 客户经理
	
	private Integer refereeId;//推荐人id
	private String birthDateStr;
	private Date birthDate;//生日
	private  String isVip;//是否vip列
	private Long countCust;
	private Long countVip;//是否vip下拉框
	private Long recommend;//是否有推荐码
	private String age;//年龄
	private String sex;//性别
	private String address;//地址
	private String  education;//学历
	private String  marital;//婚姻状况
	private String provincial;//省
	private String city;//市
	private String area;//区
	
	
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
	public BigDecimal getAccountBalAmt() {
		return accountBalAmt;
	}
	public void setAccountBalAmt(BigDecimal accountBalAmt) {
		this.accountBalAmt = accountBalAmt;
	}
	public BigDecimal getUsableBalAmt() {
		return usableBalAmt;
	}
	public void setUsableBalAmt(BigDecimal usableBalAmt) {
		this.usableBalAmt = usableBalAmt;
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
	public BigDecimal getClAmt() {
		return clAmt;
	}
	public void setClAmt(BigDecimal clAmt) {
		this.clAmt = clAmt;
	}
	public BigDecimal getBalClAmt() {
		return balClAmt;
	}
	public void setBalClAmt(BigDecimal balClAmt) {
		this.balClAmt = balClAmt;
	}
	public BigDecimal getPmAmt() {
		return pmAmt;
	}
	public void setPmAmt(BigDecimal pmAmt) {
		this.pmAmt = pmAmt;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getCustManager() {
		return custManager;
	}
	public void setCustManager(String custManager) {
		this.custManager = custManager;
	}
	public Integer getRefereeId() {
		return refereeId;
	}
	public void setRefereeId(Integer refereeId) {
		this.refereeId = refereeId;
	}
	
	public Long getCountCust() {
		return countCust;
	}
	public void setCountCust(Long countCust) {
		this.countCust = countCust;
	}
	public Long getCountVip() {
		return countVip;
	}
	public void setCountVip(Long countVip) {
		this.countVip = countVip;
	}
	public Long getRecommend() {
		return recommend;
	}
	public void setRecommend(Long recommend) {
		this.recommend = recommend;
	}
	public String getIsVip() {
		return isVip;
	}
	public void setIsVip(String isVip) {
		this.isVip = isVip;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getMarital() {
		return marital;
	}
	public void setMarital(String marital) {
		this.marital = marital;
	}
	public String getProvincial() {
		return provincial;
	}
	public void setProvincial(String provincial) {
		this.provincial = provincial;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getBirthDateStr() {
		return birthDateStr;
	}
	public void setBirthDateStr(String birthDateStr) {
		this.birthDateStr = birthDateStr;
	}
	

}