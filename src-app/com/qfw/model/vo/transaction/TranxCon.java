/**
 *项目：xmlInterf
 *通联支付网络有限公司
 * 作者：张广海
 * 日期：Jan 2, 2013
 * 功能说明：交易demo测试参数
 */
package com.qfw.model.vo.transaction;
/**
 */
public class TranxCon {

	/**
	 * XML交易参数
	 */
	private   String acctName;
	private   String acctNo;
	private   String amount;//交易金额
	private   String bankcode;//银行代码
	private   String cerPath;
	private   String merchantId;
	private   String password;
	//商户证书信息
	private   String pfxPassword;
	private   String pfxPath;
	private   String sum;//交易总金额
	private   String tel;
	private   String tltcerPath;
	private   String userName; 
	
	private String reqSn;
	private String accountProp;
	private String accountType;
	private String custUserid;
	private String summary;
	
	private int queryType;
	private int queryStatus;
	private String startDate;
	private String endDate;
	private String status;
	
	
	public String getAcctName() {
		return acctName;
	}
	public String getAcctNo() {
		return acctNo;
	}
	public String getAmount() {
		return amount;
	}
	public String getBankcode() {
		return bankcode;
	}
	public String getCerPath() {
		return cerPath;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public String getPassword() {
		return password;
	}
	public String getPfxPassword() {
		return pfxPassword;
	}
	public String getPfxPath() {
		return pfxPath;
	}
	public String getSum() {
		return sum;
	}
	public String getTel() {
		return tel;
	}
	public String getTltcerPath() {
		return tltcerPath;
	}
	public String getUserName() {
		return userName;
	}
	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}
	public void setCerPath(String cerPath) {
		this.cerPath = cerPath;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setPfxPassword(String pfxPassword) {
		this.pfxPassword = pfxPassword;
	}
	public void setPfxPath(String pfxPath) {
		this.pfxPath = pfxPath;
	}
	public void setSum(String sum) {
		this.sum = sum;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public void setTltcerPath(String tltcerPath) {
		this.tltcerPath = tltcerPath;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getReqSn() {
		return reqSn;
	}
	public void setReqSn(String reqSn) {
		this.reqSn = reqSn;
	}
	public String getAccountProp() {
		return accountProp;
	}
	public void setAccountProp(String accountProp) {
		this.accountProp = accountProp;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getCustUserid() {
		return custUserid;
	}
	public void setCustUserid(String custUserid) {
		this.custUserid = custUserid;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getQueryType() {
		return queryType;
	}
	public void setQueryType(int queryType) {
		this.queryType = queryType;
	}
	public int getQueryStatus() {
		return queryStatus;
	}
	public void setQueryStatus(int queryStatus) {
		this.queryStatus = queryStatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
 
	
	
}
