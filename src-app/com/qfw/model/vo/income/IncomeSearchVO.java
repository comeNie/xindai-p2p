package com.qfw.model.vo.income;

public class IncomeSearchVO {

	private Integer id;				// 收益表id
	private Integer incomeRelId;	// 关联id
	private String incomeTypeCd;	// 关联类型
	
	private String userCode; //登录编号
	private String loanName; // 借款标题
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIncomeRelId() {
		return incomeRelId;
	}
	public void setIncomeRelId(Integer incomeRelId) {
		this.incomeRelId = incomeRelId;
	}
	public String getIncomeTypeCd() {
		return incomeTypeCd;
	}
	public void setIncomeTypeCd(String incomeTypeCd) {
		this.incomeTypeCd = incomeTypeCd;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getLoanName() {
		return loanName;
	}
	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	
}
