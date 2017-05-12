package com.qfw.model.vo.creditor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 债权转让参数VO
 * @author kindion
 */
public class CreditorRightTranVO {
	private Integer crTranId;//债权转让ID,需要审批的时候需赋值,其他情况无需赋值
	private Integer crId; // 原债权ID
	private Integer tranTtlCount;// 转让份数
	private BigDecimal perTenderAmt; //每份金额
	private BigDecimal tranTtlAmt;//转让金额
	private BigDecimal takeAmt;// 接手金额
	private Integer tranTerm;	//转让期限(天)
	private Date tranDate; // 转让开始时间

	public Integer getCrId() {
		return crId;
	}

	public void setCrId(Integer crId) {
		this.crId = crId;
	}

	public Integer getTranTtlCount() {
		return tranTtlCount;
	}

	public void setTranTtlCount(Integer tranTtlCount) {
		this.tranTtlCount = tranTtlCount;
	}

	public BigDecimal getTakeAmt() {
		return takeAmt;
	}

	public void setTakeAmt(BigDecimal takeAmt) {
		this.takeAmt = takeAmt;
	}

	public Integer getCrTranId() {
		return crTranId;
	}

	public void setCrTranId(Integer crTranId) {
		this.crTranId = crTranId;
	}

	public BigDecimal getTranTtlAmt() {
		return tranTtlAmt;
	}

	public void setTranTtlAmt(BigDecimal tranTtlAmt) {
		this.tranTtlAmt = tranTtlAmt;
	}

	public BigDecimal getPerTenderAmt() {
		return perTenderAmt;
	}

	public void setPerTenderAmt(BigDecimal perTenderAmt) {
		this.perTenderAmt = perTenderAmt;
	}

	public Integer getTranTerm() {
		return tranTerm;
	}

	public void setTranTerm(Integer tranTerm) {
		this.tranTerm = tranTerm;
	}

	public Date getTranDate() {
		return tranDate;
	}

	public void setTranDate(Date tranDate) {
		this.tranDate = tranDate;
	}
	
}
