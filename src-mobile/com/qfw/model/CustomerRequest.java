package com.qfw.model;

public class CustomerRequest extends BaseRequest {

	private static final long serialVersionUID = 1L;

	private String customerPassword;// 客户密码
	private String customerNewPwd;// 客户新密码
	private String customerName;// 客户名称
	private String customerIdNum;// 身份证号码
	private String customerType;// 账号类型
	private String customerAgencyCode;// 上级代理编号
	private String bankProvince;// 银行所在省
	private String bankCity;// 银行所在市
	private String bankName;// 银行名称
	private String bankDepositName;// 开户行名称
	private String bankAccountCode;// 银行帐号
	private String confirmBankAccountCode;// 确认银行账号
	private String customerIdImageUrl1File;// 身份证正面照
	private String customerIdImageUrl2File;// 身份证正面照
	private String customerIdImageUrl3File;// 身份证正面照

	public String getCustomerPassword() {
		return customerPassword;
	}

	public void setCustomerPassword(String customerPassword) {
		this.customerPassword = customerPassword;
	}

	public String getCustomerNewPwd() {
		return customerNewPwd;
	}

	public void setCustomerNewPwd(String customerNewPwd) {
		this.customerNewPwd = customerNewPwd;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerIdNum() {
		return customerIdNum;
	}

	public void setCustomerIdNum(String customerIdNum) {
		this.customerIdNum = customerIdNum;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getCustomerAgencyCode() {
		return customerAgencyCode;
	}

	public void setCustomerAgencyCode(String customerAgencyCode) {
		this.customerAgencyCode = customerAgencyCode;
	}

	public String getBankProvince() {
		return bankProvince;
	}

	public void setBankProvince(String bankProvince) {
		this.bankProvince = bankProvince;
	}

	public String getBankCity() {
		return bankCity;
	}

	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankDepositName() {
		return bankDepositName;
	}

	public void setBankDepositName(String bankDepositName) {
		this.bankDepositName = bankDepositName;
	}

	public String getBankAccountCode() {
		return bankAccountCode;
	}

	public void setBankAccountCode(String bankAccountCode) {
		this.bankAccountCode = bankAccountCode;
	}

	public String getConfirmBankAccountCode() {
		return confirmBankAccountCode;
	}

	public void setConfirmBankAccountCode(String confirmBankAccountCode) {
		this.confirmBankAccountCode = confirmBankAccountCode;
	}

	public String getCustomerIdImageUrl1File() {
		return customerIdImageUrl1File;
	}

	public void setCustomerIdImageUrl1File(String customerIdImageUrl1File) {
		this.customerIdImageUrl1File = customerIdImageUrl1File;
	}

	public String getCustomerIdImageUrl2File() {
		return customerIdImageUrl2File;
	}

	public void setCustomerIdImageUrl2File(String customerIdImageUrl2File) {
		this.customerIdImageUrl2File = customerIdImageUrl2File;
	}

	public String getCustomerIdImageUrl3File() {
		return customerIdImageUrl3File;
	}

	public void setCustomerIdImageUrl3File(String customerIdImageUrl3File) {
		this.customerIdImageUrl3File = customerIdImageUrl3File;
	}

}
