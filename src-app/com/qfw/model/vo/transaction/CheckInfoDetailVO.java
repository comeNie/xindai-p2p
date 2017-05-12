package com.qfw.model.vo.transaction;

public class CheckInfoDetailVO {
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 身份证号
	 */
	
	private String identitycard;
	/**
	 * 比对状态
	 */
	private String compStatus;
	/**
	 * 比对结果
	 */
	private String compResult;
	/**
	 * 所属地
	 */
	private String region;
	/**
	 * 出生日期
	 */
	private String birthday;
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 唯一标识
	 */
	private String no;
	
	private ResultMessageVO message = new ResultMessageVO();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdentitycard() {
		return identitycard;
	}
	public void setIdentitycard(String identitycard) {
		this.identitycard = identitycard;
	}
	public String getCompStatus() {
		return compStatus;
	}
	public void setCompStatus(String compStatus) {
		this.compStatus = compStatus;
	}
	public String getCompResult() {
		return compResult;
	}
	public void setCompResult(String compResult) {
		this.compResult = compResult;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public ResultMessageVO getMessage() {
		return message;
	}
	public void setMessage(ResultMessageVO message) {
		this.message = message;
	}
	
	
	
}
