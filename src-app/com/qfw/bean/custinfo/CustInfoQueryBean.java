package com.qfw.bean.custinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.bizservice.loan.ILoanApplyBS;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.CollectionUtil;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.bo.BizAuthBO;
import com.qfw.model.bo.BizContactsBO;
import com.qfw.model.bo.BizCreditReportBO;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.bo.BizJobBO;

@ViewScoped
@ManagedBean(name="custInfoQueryBean")
public class CustInfoQueryBean extends BaseBackingBean{

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value = "#{custInfoBS}")
	private ICustInfoBS custInfoBS;
	@ManagedProperty(value = "#{loanApplyBS}")
	private ILoanApplyBS loanApplyBS;
	
	private List<BizContactsBO> contacts = new ArrayList<BizContactsBO>();//联系人信息列表
	private Map<String,BizAuthBO> authMap = new HashMap<String, BizAuthBO>();//认证信息
	private BizJobBO job = new BizJobBO();//工作信息BO
	private BizCustomerBO cust = new BizCustomerBO();//客户BO
	
	private BizCreditReportBO creditReportVO = new BizCreditReportBO();
	
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	
	@PostConstruct
	public void init() {
		Object custId = ViewOper.getSessionTmpAttr("userId");
		if(null != custId ){
			try {
				this.initInfo(String.valueOf((Integer)custId));
				if(null!=custId){
					creditReportVO = this.loanApplyBS.initCreditReportVO(String.valueOf((Integer)custId));
				}
			} catch (Exception e) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
						"客户详情信息获取异常", null);
				FacesContext.getCurrentInstance().addMessage(null, message);
				log.error("客户详情信息获取异常：", e);
			}
		}else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"客户详情信息获取异常", null);
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	
	private void initInfo(String custId){
		cust = (BizCustomerBO) this.custInfoBS.find(BizCustomerBO.class,custId);
		if (null == cust) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"客户id：" + custId+ "获取失败", null);
			FacesContext.getCurrentInstance().addMessage(null, message);
			return ;
		}
		List<BizJobBO> jobs = this.custInfoBS.findByHQL("from BizJobBO where custId = '" + custId + "'");
		if (CollectionUtil.isNotEmpty(jobs)) {
			job = jobs.get(0);
		}
		List<BizContactsBO> contacts = this.custInfoBS.findByHQL("from BizContactsBO where custId = '" + custId + "' order by id");
		if (CollectionUtil.isNotEmpty(contacts)) {
			this.contacts = contacts;
			/*int size = contacts.size();
			for (int i = 0; i < size; i++) {
				if(i==0){
					creditLimitApplyVO.setContact1(contacts.get(i));
				}else if(i==1){
					creditLimitApplyVO.setContact2(contacts.get(i));
				}else if(i==2){
					creditLimitApplyVO.setContact3(contacts.get(i));
				}else if(i==3){
					creditLimitApplyVO.setContact4(contacts.get(i));
				}
			}*/
		}
		List<BizAuthBO> auths = this.custInfoBS.findByHQL("from BizAuthBO where relTypeCd = '0' and relId = '"+custId+"'");
		if(CollectionUtil.isNotEmpty(auths)){
			authMap = new HashMap<String, BizAuthBO>();
			for (BizAuthBO bizAuthBO : auths) {
				authMap.put(bizAuthBO.getAuthItemCd(), bizAuthBO);
			}
		}
	}
	
	public ICustInfoBS getCustInfoBS() {
		return custInfoBS;
	}

	public void setCustInfoBS(ICustInfoBS custInfoBS) {
		this.custInfoBS = custInfoBS;
	}

	public BizCustomerBO getCust() {
		return cust;
	}

	public void setCust(BizCustomerBO cust) {
		this.cust = cust;
	}

	public List<BizContactsBO> getContacts() {
		return contacts;
	}

	public void setContacts(List<BizContactsBO> contacts) {
		this.contacts = contacts;
	}

	public Map<String, BizAuthBO> getAuthMap() {
		return authMap;
	}

	public void setAuthMap(Map<String, BizAuthBO> authMap) {
		this.authMap = authMap;
	}

	public BizJobBO getJob() {
		return job;
	}

	public void setJob(BizJobBO job) {
		this.job = job;
	}

	public ILoanApplyBS getLoanApplyBS() {
		return loanApplyBS;
	}

	public void setLoanApplyBS(ILoanApplyBS loanApplyBS) {
		this.loanApplyBS = loanApplyBS;
	}

	public BizCreditReportBO getCreditReportVO() {
		return creditReportVO;
	}

	public void setCreditReportVO(BizCreditReportBO creditReportVO) {
		this.creditReportVO = creditReportVO;
	}
	
}
