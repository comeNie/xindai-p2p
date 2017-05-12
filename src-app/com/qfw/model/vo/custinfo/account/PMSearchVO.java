package com.qfw.model.vo.custinfo.account;

import java.util.Date;

/**
 * pm币信息查询vo
 *
 * @author kyc
 */
public class PMSearchVO {
	
	private Integer accountDetailId;  // 明细id
	private String userCode;  // 登录编号
	private String userName; // 登录名
	private String custName;  // 真实姓名
	private String eventTypeCd;	//交易类型
	private Date startDate;	//开始时间
	private Date endDate;	// 结束时间
	
	private String account;	// 账号
	private Integer custId;  // 客户id
	
	public Integer getAccountDetailId() {
		return accountDetailId;
	}
	public void setAccountDetailId(Integer accountDetailId) {
		this.accountDetailId = accountDetailId;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getEventTypeCd() {
		return eventTypeCd;
	}
	public void setEventTypeCd(String eventTypeCd) {
		this.eventTypeCd = eventTypeCd;
	}
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
	
}

