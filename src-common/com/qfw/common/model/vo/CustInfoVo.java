package com.qfw.common.model.vo;

public class CustInfoVo {
	private Integer userId;
	private String userCode;
	private String userReferee;
	private Integer custId;
	private String userName;
	private Integer accountId;
	private String passquestion;//推荐等级
	private String passanswer;//推荐人等级
	
	private Integer refUserId;//推荐人客户号
	private Integer refCustId;//推荐人客户号
	private Integer refAccountId;//推荐人账号
	private String refUserCode;//推荐人登陆号
	private String refUserName;//推荐人名称
	private String refPassquestion;//推荐人的推荐等级
	private String refIntroduction;//推荐个数
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserReferee() {
		return userReferee;
	}
	public void setUserReferee(String userReferee) {
		this.userReferee = userReferee;
	}
	public Integer getCustId() {
		return custId;
	}
	public void setCustId(Integer custId) {
		this.custId = custId;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public String getPassquestion() {
		return passquestion;
	}
	public void setPassquestion(String passquestion) {
		this.passquestion = passquestion;
	}
	public Integer getRefCustId() {
		return refCustId;
	}
	public void setRefCustId(Integer refCustId) {
		this.refCustId = refCustId;
	}
	public Integer getRefAccountId() {
		return refAccountId;
	}
	public void setRefAccountId(Integer refAccountId) {
		this.refAccountId = refAccountId;
	}
	public String getRefUserCode() {
		return refUserCode;
	}
	public void setRefUserCode(String refUserCode) {
		this.refUserCode = refUserCode;
	}
	public String getRefPassquestion() {
		return refPassquestion;
	}
	public void setRefPassquestion(String refPassquestion) {
		this.refPassquestion = refPassquestion;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRefUserName() {
		return refUserName;
	}
	public void setRefUserName(String refUserName) {
		this.refUserName = refUserName;
	}
	public String getPassanswer() {
		return passanswer;
	}
	public void setPassanswer(String passanswer) {
		this.passanswer = passanswer;
	}
	public String getRefIntroduction() {
		return refIntroduction;
	}
	public void setRefIntroduction(String refIntroduction) {
		this.refIntroduction = refIntroduction;
	}
	public Integer getRefUserId() {
		return refUserId;
	}
	public void setRefUserId(Integer refUserId) {
		this.refUserId = refUserId;
	}
  
}
