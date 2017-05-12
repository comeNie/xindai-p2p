package com.qfw.model.vo.loan;


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.qfw.model.bo.BizAuthBO;


/**
 * 借款申请VO
 * @author WEIQS
 *
 */

public class LoanApplyVO  {

	private Integer id;
	private String custId;
	private String custName;
	private String sex;
	private String jobStatusCd;
	private String tel;
	private String addr;
	private String email;
	private String loanTypeCd;
	private String loanName;
	private BigDecimal applyAmt;
	private Integer loanTerm;
	private String termUnitCd;
	private String loanPurpose;
	private Integer tenderTerm;
	private BigDecimal expectLoanRate;
	private String repayTypeCd;
	private Date applyDate;
	private String applyStatusCd;
	private String remark;
	private String workItemId;
	private Integer sysCreateUser;
	private Date sysCreateTime;
	private Integer sysUpdateUser;
	private Date sysUpdateTime;
	private Integer productId;
	
	private Integer tenderTtlCount;  // 总份数
	private Integer overTtlCount; //剩余份数
	private BigDecimal temderCountAmt;  // 每份金额
	private BigDecimal overCountAmt; //剩余金额
	private BigDecimal overdueRate;		// 逾期利率
	private BigDecimal delayRate;		// 延期利率
	private BigDecimal advanceRate;		// 提前还款违约利率
	private BigDecimal buyAmt;  // 购买金额
	private Integer buyTtlCount; //购买份数
	private Integer canBuyTtlCount;  //可购买份数
	
	private String paymentWayCd;//支付方式
	private Integer trusteeCustId;//受托支付会员ID
	
	private String comment;		// 审批意见
	
	private BigDecimal recommendPercent;//推荐提成比率
	
	private Map<String,BizAuthBO> authMap = new HashMap<String, BizAuthBO>();//认证信息
	
	public Integer getCanBuyTtlCount() {
		return canBuyTtlCount;
	}
	public void setCanBuyTtlCount(Integer canBuyTtlCount) {
		this.canBuyTtlCount = canBuyTtlCount;
	}
	public Integer getBuyTtlCount() {
		return buyTtlCount;
	}
	public void setBuyTtlCount(Integer buyTtlCount) {
		this.buyTtlCount = buyTtlCount;
	}
	public BigDecimal getBuyAmt() {
		return buyAmt;
	}
	public void setBuyAmt(BigDecimal buyAmt) {
		this.buyAmt = buyAmt;
	}
	public Integer getOverTtlCount() {
		return overTtlCount;
	}
	public void setOverTtlCount(Integer overTtlCount) {
		this.overTtlCount = overTtlCount;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getLoanTypeCd() {
		return loanTypeCd;
	}
	public void setLoanTypeCd(String loanTypeCd) {
		this.loanTypeCd = loanTypeCd;
	}
	public String getLoanName() {
		return loanName;
	}
	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	public BigDecimal getApplyAmt() {
		return applyAmt;
	}
	public void setApplyAmt(BigDecimal applyAmt) {
		this.applyAmt = applyAmt;
	}
	public Integer getLoanTerm() {
		return loanTerm;
	}
	public void setLoanTerm(Integer loanTerm) {
		this.loanTerm = loanTerm;
	}
	public String getTermUnitCd() {
		return termUnitCd;
	}
	public void setTermUnitCd(String termUnitCd) {
		this.termUnitCd = termUnitCd;
	}
	public String getLoanPurpose() {
		return loanPurpose;
	}
	public void setLoanPurpose(String loanPurpose) {
		this.loanPurpose = loanPurpose;
	}
	public Integer getTenderTerm() {
		return tenderTerm;
	}
	public void setTenderTerm(Integer tenderTerm) {
		this.tenderTerm = tenderTerm;
	}
	public BigDecimal getExpectLoanRate() {
		return expectLoanRate;
	}
	public void setExpectLoanRate(BigDecimal expectLoanRate) {
		this.expectLoanRate = expectLoanRate;
	}
	public String getRepayTypeCd() {
		return repayTypeCd;
	}
	public void setRepayTypeCd(String repayTypeCd) {
		this.repayTypeCd = repayTypeCd;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public String getApplyStatusCd() {
		return applyStatusCd;
	}
	public void setApplyStatusCd(String applyStatusCd) {
		this.applyStatusCd = applyStatusCd;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getWorkItemId() {
		return workItemId;
	}
	public void setWorkItemId(String workItemId) {
		this.workItemId = workItemId;
	}
	public Integer getSysCreateUser() {
		return sysCreateUser;
	}
	public void setSysCreateUser(Integer sysCreateUser) {
		this.sysCreateUser = sysCreateUser;
	}
	public Date getSysCreateTime() {
		return sysCreateTime;
	}
	public void setSysCreateTime(Date sysCreateTime) {
		this.sysCreateTime = sysCreateTime;
	}
	public Integer getSysUpdateUser() {
		return sysUpdateUser;
	}
	public void setSysUpdateUser(Integer sysUpdateUser) {
		this.sysUpdateUser = sysUpdateUser;
	}
	public Date getSysUpdateTime() {
		return sysUpdateTime;
	}
	public void setSysUpdateTime(Date sysUpdateTime) {
		this.sysUpdateTime = sysUpdateTime;
	}
	public Integer getTenderTtlCount() {
		return tenderTtlCount;
	}
	public void setTenderTtlCount(Integer tenderTtlCount) {
		this.tenderTtlCount = tenderTtlCount;
	}
	public BigDecimal getTemderCountAmt() {
		return temderCountAmt;
	}
	public void setTemderCountAmt(BigDecimal temderCountAmt) {
		this.temderCountAmt = temderCountAmt;
	}
	public BigDecimal getOverdueRate() {
		return overdueRate;
	}
	public void setOverdueRate(BigDecimal overdueRate) {
		this.overdueRate = overdueRate;
	}
	public BigDecimal getDelayRate() {
		return delayRate;
	}
	public void setDelayRate(BigDecimal delayRate) {
		this.delayRate = delayRate;
	}
	public BigDecimal getAdvanceRate() {
		return advanceRate;
	}
	public void setAdvanceRate(BigDecimal advanceRate) {
		this.advanceRate = advanceRate;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public BigDecimal getOverCountAmt() {
		return overCountAmt;
	}
	public void setOverCountAmt(BigDecimal overCountAmt) {
		this.overCountAmt = overCountAmt;
	}
	public Map<String, BizAuthBO> getAuthMap() {
		return authMap;
	}
	public void setAuthMap(Map<String, BizAuthBO> authMap) {
		this.authMap = authMap;
	}
	public String getPaymentWayCd() {
		return paymentWayCd;
	}
	public void setPaymentWayCd(String paymentWayCd) {
		this.paymentWayCd = paymentWayCd;
	}
	public Integer getTrusteeCustId() {
		return trusteeCustId;
	}
	public void setTrusteeCustId(Integer trusteeCustId) {
		this.trusteeCustId = trusteeCustId;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getJobStatusCd() {
		return jobStatusCd;
	}
	public void setJobStatusCd(String jobStatusCd) {
		this.jobStatusCd = jobStatusCd;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public BigDecimal getRecommendPercent() {
		return recommendPercent;
	}
	public void setRecommendPercent(BigDecimal recommendPercent) {
		this.recommendPercent = recommendPercent;
	}
	
}