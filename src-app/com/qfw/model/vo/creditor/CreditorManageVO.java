package com.qfw.model.vo.creditor;

import java.math.BigDecimal;
import java.util.Date;

public class CreditorManageVO {

	private Integer creditorId; // 债权id
	private String account;		// 账户
	private String loanName; // 借款标题
	private String userName; // 登录名
	private String userCode;//登录编号
	private String custName; // 会员名称
	private BigDecimal crAmt; // 债权金额
	private BigDecimal tranBalAmt; // 转让金额（剩余转让金额）
	private BigDecimal rate;		// 利率
	private Integer term;    // 剩余期限
	private String crStatusCd; // 状态
	
	private BigDecimal loanAmt; //标的总额
	private String repayTypeCd; //还款方式
	private Integer loanApproveId; //借款发布ID
	
	private Integer tranId; //债权发布ID
	private Integer crId; //债权ID
	private Integer surplusPeriod; //剩余期数
	private BigDecimal loanRate; //年利率
	private Integer tranTtlCount; //转让份数
	private Integer tranOutCount; //转出份数
	private BigDecimal tranTtlAmt; //转出债权总价值
	private BigDecimal tranOutAmt;  //转出总成交金额
	private BigDecimal takeAmt; //接手奖金 
	private BigDecimal takeBalAmt;  //债权剩余奖金
	private String crtStatusCd;//状态
	private Date sysCreateTime;
	
	private Integer totalPeriod;		//总期数
	private Date startDate;			// 开始时间
	private Date endDate;			// 结束时间
	private Integer loanTerm;		//借款期限
	
	private String loanCustName;		// 借款人名称
	private BigDecimal  completeness; // 完成进度
	private Date surplusDate;		// 剩余转让时间
	
	private BigDecimal totalProfitAmt;	//总收益金额
	private BigDecimal interest;		//利息收益
	
	public Integer getCreditorId() {
		return creditorId;
	}
	public void setCreditorId(Integer creditorId) {
		this.creditorId = creditorId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getLoanName() {
		return loanName;
	}
	public void setLoanName(String loanName) {
		this.loanName = loanName;
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
	public BigDecimal getTranBalAmt() {
		return tranBalAmt;
	}
	public void setTranBalAmt(BigDecimal tranBalAmt) {
		this.tranBalAmt = tranBalAmt;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public Integer getTerm() {
		return term;
	}
	public void setTerm(Integer term) {
		this.term = term;
	}
	public String getCrStatusCd() {
		return crStatusCd;
	}
	public void setCrStatusCd(String crStatusCd) {
		this.crStatusCd = crStatusCd;
	}
	public BigDecimal getLoanAmt() {
		return loanAmt;
	}
	public void setLoanAmt(BigDecimal loanAmt) {
		this.loanAmt = loanAmt;
	}
	public String getRepayTypeCd() {
		return repayTypeCd;
	}
	public void setRepayTypeCd(String repayTypeCd) {
		this.repayTypeCd = repayTypeCd;
	}
	public Integer getLoanApproveId() {
		return loanApproveId;
	}
	public void setLoanApproveId(Integer loanApproveId) {
		this.loanApproveId = loanApproveId;
	}
	public Integer getCrId() {
		return crId;
	}
	public void setCrId(Integer crId) {
		this.crId = crId;
	}
	public Integer getSurplusPeriod() {
		return surplusPeriod;
	}
	public void setSurplusPeriod(Integer surplusPeriod) {
		this.surplusPeriod = surplusPeriod;
	}
	public BigDecimal getLoanRate() {
		return loanRate;
	}
	public void setLoanRate(BigDecimal loanRate) {
		this.loanRate = loanRate;
	}
	public Integer getTranTtlCount() {
		return tranTtlCount;
	}
	public void setTranTtlCount(Integer tranTtlCount) {
		this.tranTtlCount = tranTtlCount;
	}
	public Integer getTranOutCount() {
		return tranOutCount;
	}
	public void setTranOutCount(Integer tranOutCount) {
		this.tranOutCount = tranOutCount;
	}
	public BigDecimal getTranTtlAmt() {
		return tranTtlAmt;
	}
	public void setTranTtlAmt(BigDecimal tranTtlAmt) {
		this.tranTtlAmt = tranTtlAmt;
	}
	public BigDecimal getTranOutAmt() {
		return tranOutAmt;
	}
	public void setTranOutAmt(BigDecimal tranOutAmt) {
		this.tranOutAmt = tranOutAmt;
	}
	public BigDecimal getTakeAmt() {
		return takeAmt;
	}
	public void setTakeAmt(BigDecimal takeAmt) {
		this.takeAmt = takeAmt;
	}
	public BigDecimal getTakeBalAmt() {
		return takeBalAmt;
	}
	public void setTakeBalAmt(BigDecimal takeBalAmt) {
		this.takeBalAmt = takeBalAmt;
	}
	public String getCrtStatusCd() {
		return crtStatusCd;
	}
	public void setCrtStatusCd(String crtStatusCd) {
		this.crtStatusCd = crtStatusCd;
	}
	public Integer getTranId() {
		return tranId;
	}
	public void setTranId(Integer tranId) {
		this.tranId = tranId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getTotalPeriod() {
		return totalPeriod;
	}
	public void setTotalPeriod(Integer totalPeriod) {
		this.totalPeriod = totalPeriod;
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
	public Integer getLoanTerm() {
		return loanTerm;
	}
	public void setLoanTerm(Integer loanTerm) {
		this.loanTerm = loanTerm;
	}
	public String getLoanCustName() {
		return loanCustName;
	}
	public void setLoanCustName(String loanCustName) {
		this.loanCustName = loanCustName;
	}
	public BigDecimal getCompleteness() {
		return completeness;
	}
	public void setCompleteness(BigDecimal completeness) {
		this.completeness = completeness;
	}
	public Date getSurplusDate() {
		return surplusDate;
	}
	public void setSurplusDate(Date surplusDate) {
		this.surplusDate = surplusDate;
	}
	public BigDecimal getTotalProfitAmt() {
		return totalProfitAmt;
	}
	public void setTotalProfitAmt(BigDecimal totalProfitAmt) {
		this.totalProfitAmt = totalProfitAmt;
	}
	public BigDecimal getInterest() {
		return interest;
	}
	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public Date getSysCreateTime() {
		return sysCreateTime;
	}
	public void setSysCreateTime(Date sysCreateTime) {
		this.sysCreateTime = sysCreateTime;
	}
	
}
