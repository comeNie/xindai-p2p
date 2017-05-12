package com.qfw.manager.model;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.qfw.model.AppConst;

/**
 *
 */
public class MyCreditorVO {
	
	private String creditorCustName;
	
	private String loanCustName;
	
	private String contractNum;
	
	private String certificateNum;
	
	private Date creatDate;
	
	private BigDecimal loanAmt;
	
	private BigDecimal loanRate;
	
	private Integer loanTerm;
	
	private String loanDate;//债权开始日
	
	private String loanDueDate;//债权到期日
	
	private BigDecimal ttlPrincipalInterestAmt;
	
	private String loanPurpose;//贷款用途
	
	private String repayTypeCd;//
	
	private String repayTypeName;//还款方式
	
	private Date startLoanDate;
	
	private Integer id;
	
	/**
	 * 带*的身份证号
	 */
	private String showIdCard;

	public String getCreditorCustName() {
		return creditorCustName;
	}

	public void setCreditorCustName(String creditorCustName) {
		this.creditorCustName = creditorCustName;
	}

	public String getLoanCustName() {
		return loanCustName;
	}

	public void setLoanCustName(String loanCustName) {
		this.loanCustName = loanCustName;
	}

	public String getContractNum() {
		return contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}

	public Date getCreatDate() {
		return creatDate;
	}

	public void setCreatDate(Date creatDate) {
		this.creatDate = creatDate;
	}

	public BigDecimal getLoanAmt() {
		return loanAmt;
	}

	public void setLoanAmt(BigDecimal loanAmt) {
		this.loanAmt = loanAmt;
	}

	public BigDecimal getLoanRate() {
		return loanRate;
	}

	public void setLoanRate(BigDecimal loanRate) {
		this.loanRate = loanRate;
	}


	public String getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}

	public String getLoanDueDate() {
		return loanDueDate;
	}

	public void setLoanDueDate(String loanDueDate) {
		this.loanDueDate = loanDueDate;
	}

	public BigDecimal getTtlPrincipalInterestAmt() {
		return ttlPrincipalInterestAmt;
	}

	public void setTtlPrincipalInterestAmt(BigDecimal ttlPrincipalInterestAmt) {
		this.ttlPrincipalInterestAmt = ttlPrincipalInterestAmt;
	}


	public String getRepayTypeCd() {
		return repayTypeCd;
	}

	public void setRepayTypeCd(String repayTypeCd) {
		this.repayTypeCd = repayTypeCd;
	}

	public String getRepayTypeName() {
		if(AppConst.REPAY_TYPE_CD_DEBX.equals(repayTypeCd)){
			repayTypeName = "等额本息";
		}else if(AppConst.REPAY_TYPE_CD_DEBJ.equals(repayTypeCd)){
			repayTypeName = "等额本金";
		}else if(AppConst.REPAY_TYPE_CD_LSBQ.equals(repayTypeCd)){
			repayTypeName = "利随本清";
		}else if(AppConst.REPAY_TYPE_CD_ZQFX.equals(repayTypeCd)){
			repayTypeName = "按固定周期付息,到期还款";
		}
		return repayTypeName;
	}

	public void setRepayTypeName(String repayTypeName) {
		this.repayTypeName = repayTypeName;
	}

	public String getShowIdCard() {
		if (StringUtils.isNotEmpty(certificateNum)) {
			int idCardLength = certificateNum.length();
			if(idCardLength != 18){
				return showIdCard;
			}
			StringBuffer buffer = new StringBuffer();
			buffer.append(certificateNum.substring(0, 10));
			for (int i = 10; i < idCardLength-10; i++) {
				buffer.append("*");
			}
			return buffer.toString();
		}
		return showIdCard;
	}

	public void setShowIdCard(String showIdCard) {
		this.showIdCard = showIdCard;
	}

	public String getCertificateNum() {
		return certificateNum;
	}

	public void setCertificateNum(String certificateNum) {
		this.certificateNum = certificateNum;
	}

	public Date getStartLoanDate() {
		return startLoanDate;
	}

	public void setStartLoanDate(Date startLoanDate) {
		this.startLoanDate = startLoanDate;
	}

	public Integer getLoanTerm() {
		return loanTerm;
	}

	public void setLoanTerm(Integer loanTerm) {
		this.loanTerm = loanTerm;
	}

	public String getLoanPurpose() {
		return loanPurpose;
	}

	public void setLoanPurpose(String loanPurpose) {
		this.loanPurpose = loanPurpose;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
}
