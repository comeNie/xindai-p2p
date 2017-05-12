package com.qfw.model.vo.custinfo;

public class SearchCustInfoVO {
	private String account; // 账户
	private Integer custId;  // 客户ID
	private String custName; // 客户名称
	private String phone;	 // 手机号码
	private String isAdmin;	 // 是否内部用户
	private String day;
	private String isVip;
	/**
	 * 推广状态
	 */
	private String refereeStatus;
	
	private Boolean isrefereeApply;
	
	public String getAccount() {
		return account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	
	public Integer getCustId() {
		return custId;
	}
	
	public void setCustId(Integer custId) {
		this.custId = custId;
	}
	
	public String getCustName() {
		return custName;
	}
	
	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getIsAdmin() {
		return isAdmin;
	}
	
	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getIsVip() {
		return isVip;
	}

	public void setIsVip(String isVip) {
		this.isVip = isVip;
	}

	public String getRefereeStatus() {
		return refereeStatus;
	}

	public void setRefereeStatus(String refereeStatus) {
		this.refereeStatus = refereeStatus;
	}

	public Boolean getIsrefereeApply() {
		return isrefereeApply;
	}

	public void setIsrefereeApply(Boolean isrefereeApply) {
		this.isrefereeApply = isrefereeApply;
	}

}
