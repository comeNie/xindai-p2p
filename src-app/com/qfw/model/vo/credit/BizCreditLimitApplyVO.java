package com.qfw.model.vo.credit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qfw.model.bo.BizAuthBO;
import com.qfw.model.bo.BizCollateralInfoBO;
import com.qfw.model.bo.BizContactsBO;
import com.qfw.model.bo.BizCreditLimitApplyBO;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.bo.BizDisclosureInfoBO;
import com.qfw.model.bo.BizGuarantorInfoBO;
import com.qfw.model.bo.BizJobBO;

/**
 * 额度申请VO
 */

public class BizCreditLimitApplyVO  {


	/*private Integer id;
	private Integer userId;
	private String custName;
	private String custTypeCd;
	private String certificateTypeCd;
	private String certificateNum;
	private String maritalStatusCd;
	private String hasChildrenCd;
	private String countyCd;
	private String provinceCd;
	private String cityCd;
	private String nationalityCd;
	private String streetAddress;
	private String zipNum;
	private String telephone;
	private String mobileTelephone;
	private String fax;
	private String qq;
	private String website;
	
	private Integer age;
	private String sex;
	private String education;
	private String house;
	private Integer custId;
	private String contactsName;
	private String contactsName1;
	private String contactsTypeCd;
	private String conCertificateTypeCd;
	private String conCertificateNum;
	private String conTelephone;
	private String conMobileTelephone;
	private String address;
	private String conJobStatusCd;
	private String guaranteeId; //担保信息ID
	private BigDecimal limitApple; //申请额度
	private String tenderType;  //标类型 待定
	private Timestamp applyStartDate; //申请开始时间
	private Timestamp applyEndDate; //结束时间
	private Integer creditLimitID; //额度申请ID
	private String guaranteeName; //担保品名称
	private String guaranteeType; //担保品类型
	private String guaranteeWorth; //担保品价值
	private String guaranteeExplain; //担保品说明
	private String guaranteeAttachment; //担保品附件
	private String guaranteeAtt1;
	private String guaranteeAtt2;
	private String guaranteeAtt3;
	private String guaranteeAmt; //担保品金额
	private String guarantorName; // 保证人
	private String guarantorType; //担保类型(多人分保 多人联保 单人保证 多人保证)
	private BigDecimal guarantorAmt; //保证人金额
	private String guarantorAttachment; //保证人信息附件
	private String guarantorExplain; //担保说明
	private String guarantorPaperType; //保证人证件类型
	private String guarantorPagerValue; // 保证人证件号码
	private String guarantorContractNumber; //担保合同编号（纸质合同编号）
	private String guarantorAtt1;
	private String guarantorAtt2;
	private String guarantorAtt3;
	
	private String jobStatusCd;
	private String jobCompanyName;
	private String jobDeptName;
	private String companyTypeCd;
	private String companyIndustryCd;
	private String companyScaleCd;
	private String jobPosition;
	private String jobEmalil;
	private String companyPhone;
	private String companyAddress;
	private Integer jobYearCd;
	private String jobIncomeCd;
	private String jobYearIncomeCd;*/
	
	private String comment; //审批意见
	private String applyStatusCd; //额度申请状态
	private String workItemId;
	
	private BizCreditLimitApplyBO creditLimitApply = new BizCreditLimitApplyBO();//额度bo
	private BizCustomerBO cust = new BizCustomerBO();//客户BO
	private BizJobBO job = new BizJobBO();//工作信息BO
	private List<BizCollateralInfoBO> collateralInfos = new ArrayList<BizCollateralInfoBO>();//担保品信息列表
	private List<BizGuarantorInfoBO> guarantorInfos = new ArrayList<BizGuarantorInfoBO>();//保证人信息列表
	private List<BizContactsBO> contacts = new ArrayList<BizContactsBO>();//联系人信息列表

	private List<BizCollateralInfoBO> removeCollateralInfos = new ArrayList<BizCollateralInfoBO>();//删除持久化担保品信息列表
	private List<BizGuarantorInfoBO> removeGuarantorInfos = new ArrayList<BizGuarantorInfoBO>();//删除持久化保证人信息列表
	private List<BizContactsBO> removeContacts = new ArrayList<BizContactsBO>();//删除持久化联系人信息列表
	
	private BizCollateralInfoBO collateralInfo = new BizCollateralInfoBO();//担保品信息
	private BizGuarantorInfoBO guarantorInfo = new BizGuarantorInfoBO();//保证人信息
	private BizContactsBO contact = new BizContactsBO();//联系人信息
	private Map<String,BizAuthBO> authMap = new HashMap<String, BizAuthBO>();//认证信息
	
	private BizDisclosureInfoBO disclosureInfo = new BizDisclosureInfoBO();//信息批露
	
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	

	public BizCreditLimitApplyBO getCreditLimitApply() {
		return creditLimitApply;
	}

	public void setCreditLimitApply(BizCreditLimitApplyBO creditLimitApply) {
		this.creditLimitApply = creditLimitApply;
	}

	public String getApplyStatusCd() {
		return applyStatusCd;
	}

	public void setApplyStatusCd(String applyStatusCd) {
		this.applyStatusCd = applyStatusCd;
	}

	public String getWorkItemId() {
		return workItemId;
	}

	public void setWorkItemId(String workItemId) {
		this.workItemId = workItemId;
	}

	public BizCustomerBO getCust() {
		return cust;
	}

	public void setCust(BizCustomerBO cust) {
		this.cust = cust;
	}

	public BizJobBO getJob() {
		return job;
	}

	public void setJob(BizJobBO job) {
		this.job = job;
	}

	public List<BizCollateralInfoBO> getCollateralInfos() {
		return collateralInfos;
	}

	public void setCollateralInfos(List<BizCollateralInfoBO> collateralInfos) {
		this.collateralInfos = collateralInfos;
	}

	public List<BizGuarantorInfoBO> getGuarantorInfos() {
		return guarantorInfos;
	}

	public void setGuarantorInfos(List<BizGuarantorInfoBO> guarantorInfos) {
		this.guarantorInfos = guarantorInfos;
	}

	public BizCollateralInfoBO getCollateralInfo() {
		return collateralInfo;
	}

	public void setCollateralInfo(BizCollateralInfoBO collateralInfo) {
		this.collateralInfo = collateralInfo;
	}

	public BizGuarantorInfoBO getGuarantorInfo() {
		return guarantorInfo;
	}

	public void setGuarantorInfo(BizGuarantorInfoBO guarantorInfo) {
		this.guarantorInfo = guarantorInfo;
	}

	public Map<String, BizAuthBO> getAuthMap() {
		return authMap;
	}

	public void setAuthMap(Map<String, BizAuthBO> authMap) {
		this.authMap = authMap;
	}

	public List<BizContactsBO> getContacts() {
		return contacts;
	}

	public void setContacts(List<BizContactsBO> contacts) {
		this.contacts = contacts;
	}

	public BizContactsBO getContact() {
		return contact;
	}

	public void setContact(BizContactsBO contact) {
		this.contact = contact;
	}

	public List<BizCollateralInfoBO> getRemoveCollateralInfos() {
		return removeCollateralInfos;
	}

	public void setRemoveCollateralInfos(
			List<BizCollateralInfoBO> removeCollateralInfos) {
		this.removeCollateralInfos = removeCollateralInfos;
	}

	public List<BizGuarantorInfoBO> getRemoveGuarantorInfos() {
		return removeGuarantorInfos;
	}

	public void setRemoveGuarantorInfos(
			List<BizGuarantorInfoBO> removeGuarantorInfos) {
		this.removeGuarantorInfos = removeGuarantorInfos;
	}

	public List<BizContactsBO> getRemoveContacts() {
		return removeContacts;
	}

	public void setRemoveContacts(List<BizContactsBO> removeContacts) {
		this.removeContacts = removeContacts;
	}

	public BizDisclosureInfoBO getDisclosureInfo() {
		return disclosureInfo;
	}

	public void setDisclosureInfo(BizDisclosureInfoBO disclosureInfo) {
		this.disclosureInfo = disclosureInfo;
	}

}