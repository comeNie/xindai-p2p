package com.qfw.platform.model.transfer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.qfw.common.util.StringUtils;

public class TransferSearchVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer transferId; // 借款id
	private Integer crId; // 债权Id
	private Integer loanApproveId; //发布Id
	private String loanType; //标的类型
	private String loanRate;// 年化利率
	private String creditRate;// 信用等级
	private String remainTerm; // 剩余期限
	public Integer getTransferId() {
		return transferId;
	}
	public void setTransferId(Integer transferId) {
		this.transferId = transferId;
	}
	public Integer getCrId() {
		return crId;
	}
	public void setCrId(Integer crId) {
		this.crId = crId;
	}
	public Integer getLoanApproveId() {
		return loanApproveId;
	}
	public void setLoanApproveId(Integer loanApproveId) {
		this.loanApproveId = loanApproveId;
	}
	public String getLoanType() {
		return loanType;
	}
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	public String getLoanRate() {
		return loanRate;
	}
	public void setLoanRate(String loanRate) {
		this.loanRate = loanRate;
	}
	public String getCreditRate() {
		return creditRate;
	}
	public void setCreditRate(String creditRate) {
		this.creditRate = creditRate;
	}
	
	public String getRemainTerm() {
		return remainTerm;
	}
	public void setRemainTerm(String remainTerm) {
		this.remainTerm = remainTerm;
	}
	

	
	
	/**
	 * 获取格式化后借款期限查询
	 * @param columnName
	 * @return
	 */
	public String getRemainTermFormat(String columnName) {
		if (StringUtils.isEmpty(columnName))
			return null;

		if (StringUtils.isNotEmpty(getRemainTerm())) {
			String tmpStr = LOAN_TERM_MAP.get(getRemainTerm());
			return String.format(tmpStr, columnName,columnName);
		}
		return null;
	}
	/**
	 * 获取格式化后年化利率查询
	 * @param columnName
	 * @return
	 */
	public String getLoanRateFormat(String columnName) {
		if (StringUtils.isEmpty(columnName))
			return null;

		if (StringUtils.isNotEmpty(getLoanRate())) {
			String tmpStr = LOAN_RATE_MAP.get(getLoanRate());
			return String.format(tmpStr, columnName,columnName);
		}
		return null;
	}


	/**
	 * 期限
	 */
	private static final Map<String, String> LOAN_TERM_MAP = new HashMap<String, String>();
	static{
		LOAN_TERM_MAP.put("0", "%s >=1 and %s<=3");
		LOAN_TERM_MAP.put("1", "%s >=3 and %s<=6");
		LOAN_TERM_MAP.put("2", "%s >=6 and %s<=12");
		LOAN_TERM_MAP.put("3", "%s > 12");
	}
	
	/**
	 * 年化利率
	 */
	private static final Map<String, String> LOAN_RATE_MAP = new HashMap<String, String>();
	static{
		LOAN_RATE_MAP.put("0", "%s <0.12");
		LOAN_RATE_MAP.put("1", "%s >=0.12 and %s<=0.14");
		LOAN_RATE_MAP.put("2", "%s >=0.14 and %s<=0.17");
		LOAN_RATE_MAP.put("3", "%s >0.17");
	}
	
	
}
