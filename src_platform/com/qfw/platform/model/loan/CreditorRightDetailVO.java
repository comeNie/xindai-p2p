package com.qfw.platform.model.loan;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 投标记录
 * @author Think
 *
 */
public class CreditorRightDetailVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer custId;// 客户编号
	private String userCode;//用户名
	private String custName;// 投资人
	private Integer crCount;//投资份数
	private BigDecimal crAmt;// 投资金额
	private String tenderTypeCd;//投标方式
	private Date sysCreateTime;//投标时间

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
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

	public BigDecimal getCrAmt() {
		return crAmt;
	}

	public void setCrAmt(BigDecimal crAmt) {
		this.crAmt = crAmt;
	}

	public String getTenderTypeCd() {
		return tenderTypeCd;
	}

	public void setTenderTypeCd(String tenderTypeCd) {
		this.tenderTypeCd = tenderTypeCd;
	}

	public Date getSysCreateTime() {
		return sysCreateTime;
	}

	public void setSysCreateTime(Date sysCreateTime) {
		this.sysCreateTime = sysCreateTime;
	}

	public Integer getCrCount() {
		return crCount;
	}

	public void setCrCount(Integer crCount) {
		this.crCount = crCount;
	}
	
}