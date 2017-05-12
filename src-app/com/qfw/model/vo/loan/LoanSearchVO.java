package com.qfw.model.vo.loan;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.qfw.common.util.StringUtils;

/**
 * 借款管理vo
 *
 * @author kyc
 */
public class LoanSearchVO {

	private Integer loanId; // 借据id
	private Integer loanApproveId;	//借款发布id
	private String account; // 账户
	private String custId;	// 客户id
	private String custName; // 客户名称
	private String loanTerm; // 期限
	private String loanStatusCd; // 状态
	private Date startDate;		// 申请开始时间
	private Date endDate;		// 申请结束时间
	private String loanType; //标的类型
	private String loanRate;// 年化利率
	private String creditRate;// 信用等级
	
	private String loanName;//借款标题
	private String userCode;// 登录编号
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getLoanTerm() {
		return loanTerm;
	}
	public void setLoanTerm(String loanTerm) {
		this.loanTerm = loanTerm;
	}
	public String getLoanStatusCd() {
		return loanStatusCd;
	}
	public void setLoanStatusCd(String loanStatusCd) {
		this.loanStatusCd = loanStatusCd;
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
	public Integer getLoanId() {
		return loanId;
	}
	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
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
	
	public String getLoanName() {
		return loanName;
	}
	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	/**
	 * 获取格式化后借款期限查询
	 * @param columnName
	 * @return
	 */
	public String getLoanTermFormat(String columnName) {
		if (StringUtils.isEmpty(columnName))
			return null;

		if (StringUtils.isNotEmpty(getLoanTerm())) {
			String tmpStr = LOAN_TERM_MAP.get(getLoanTerm());
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
	 * 借款期限
	 */
	private static final Map<String, String> LOAN_TERM_MAP = new HashMap<String, String>();
	static{
		LOAN_TERM_MAP.put("0", "%s >=1 and %s<=3");
		LOAN_TERM_MAP.put("1", "%s >=3 and %s<=6");
		LOAN_TERM_MAP.put("2", "%s >=6 and %s<=12");
		LOAN_TERM_MAP.put("3", "%s >12");
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