package com.qfw.bean.postLoan;

/**
 * 贷后管理Bean
 * @author weiqs
 */
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;

import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.bizservice.custinfo.account.ICustAccountBS;
import com.qfw.bizservice.postloan.IPostLoanManageBS;
import com.qfw.bizservice.repay.IRepayBS;
import com.qfw.common.bizservice.IMsgTemplateBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.DateUtils;
import com.qfw.common.util.StringUtils;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizAccountBO;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.vo.postLoan.LazyPostLoanDataModel;
import com.qfw.model.vo.postLoan.PostLoanManageVO;
import com.qfw.model.vo.postLoan.PostLoanSearchVO;

@ViewScoped
@ManagedBean(name="postLoanManageBean")
public class PostLoanManageBean extends BaseBackingBean{
    
	private static final long serialVersionUID = -2631989147972388606L;
	
	@ManagedProperty(value = "#{postLoanManageBS}")
	private IPostLoanManageBS postLoanManageBS;
	@ManagedProperty(value = "#{repayBS}")
	private IRepayBS repayBS;
	
	@ManagedProperty(value = "#{msgTemplateBS}")
	private IMsgTemplateBS msgTemplateBS;
	private PostLoanSearchVO searchVO = new PostLoanSearchVO();
	private LazyDataModel<PostLoanManageVO> dataModel;
	private PostLoanManageVO  postLoanManageVO = new PostLoanManageVO();
	@ManagedProperty(value = "#{custAccountBS}")
	private ICustAccountBS custAccountBS; 
	@ManagedProperty(value = "#{custInfoBS}")
	private ICustInfoBS custInfoBS;
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	private String loanStatusCd ;
	
	@PostConstruct
    public void init(){
		if(loanStatusCd==null){
			loanStatusCd = ViewOper.getParameter("loanStatusCd");
		}
		if(null == loanStatusCd ||loanStatusCd.length()<=0){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
    				"贷后管理查询失败", null);
    		FacesContext.getCurrentInstance().addMessage(null, message);
    		return ;
		}else{
			searchVO.setLoanStatusCd(loanStatusCd);
			searchVO.setRepayStatusCd(loanStatusCd);
		}
		if (searchVO != null) {
			try {
				postLoanManageVO = new PostLoanManageVO();
				dataModel = new LazyPostLoanDataModel(searchVO, postLoanManageBS);
			} catch (Exception e) {
				log.error("贷后查询异常：", e);
				alert("贷后查询搜异常："+e.getMessage());
			}
		}
	}
	
	public void search() {
		init();
	}
	
	public String reset () {
		searchVO = new PostLoanSearchVO();
		return null;
	}
	
	public void operate(){
		String operateFlag = ViewOper.getParameter("operateFlag");
		if("advance".equalsIgnoreCase(operateFlag)||"advance".equals(operateFlag)){
			//平台垫付
			String mess = "";
			try {
				this.repayBS.repayForFull(AppConst.REPAY_WAY_CD_DF, postLoanManageVO.getLoanId(), postLoanManageVO.getPlanId());
				mess = "垫付操作成功";
			} catch (Exception e) {
				log.warn("平台垫付失败",e);
				mess = "垫付失败";
			}
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,mess, null);
    		FacesContext.getCurrentInstance().addMessage(null, message);
    		return;
		}
		Map dataMap = new HashMap();
		BizAccountBO accout = null;
		BizCustomerBO cust = null;
		if(postLoanManageVO != null){
			try {
				accout = custAccountBS.findCustAccount(postLoanManageVO.getCustId());
				cust = custInfoBS.findCustById(postLoanManageVO.getCustId());
			} catch (BizException e) {
				this.alert(e.getMessage());
				return;
			}
			if(accout == null){
				this.alert("获取账号为空");
				return;
			}
			if(cust == null){
				this.alert("获取客户信息为空");
				return;
			}
			dataMap.put("accno", accout.getAccount());
			dataMap.put("amt", postLoanManageVO.getPrincipalAmt());
			dataMap.put("date", DateUtils.getYmd(postLoanManageVO.getRepayplanDate()));
		}
        if("smsNotice".equalsIgnoreCase(operateFlag)){//短信催收
			
			try {
				if(StringUtils.isEmpty(cust.getMobileTelephone())){
					this.alert("短信提醒失败，手机号码不能为空");
					return;
				}
				//平台账号${accno}有一笔${amt}元还款，还款日：${date}已逾期，请及时还款，详咨XXXXXXXXXXXX
				msgTemplateBS.sendMsg(AppConst.SMS_OVERDUE_NOTICE, dataMap, null, cust.getMobileTelephone());
			} catch (BizException e) {
				this.alert("短信提醒失败");
				return;
			}
			this.alertInfo("短信提醒成功");
		}else if("emailNotice".equals(operateFlag)){//邮件催收
			try {
				if(StringUtils.isEmpty(cust.getEmail())){
					this.alert("邮件提醒失败，邮箱地址不能为空");
					return;
				}
				//平台账号${accno}有一笔${amt}元还款，还款日：${date}已逾期，请及时还款，详咨XXXXXXXXXXXX
				msgTemplateBS.sendMsg(AppConst.EMAIL_OVERDUE_NOTICE, dataMap, null, cust.getEmail());
			} catch (BizException e) {
				this.alert("邮件提醒失败");
				return;
			}
			this.alertInfo("邮件提醒成功");
		}else if("znxNotice".equals(operateFlag)){
//			ViewOper.getSession().setAttribute("userid", selectUser.getUserId());
		}
		else if("addPostLoan".equals(operateFlag)){// 生成贷后任务
			ViewOper.getSession().setAttribute("planId", postLoanManageVO.getPlanId());
		}
	}

	
	public IPostLoanManageBS getPostLoanManageBS() {
		return postLoanManageBS;
	}

	public void setPostLoanManageBS(IPostLoanManageBS postLoanManageBS) {
		this.postLoanManageBS = postLoanManageBS;
	}

	public PostLoanSearchVO getSearchVO() {
		return searchVO;
	}

	public void setSearchVO(PostLoanSearchVO searchVO) {
		this.searchVO = searchVO;
	}

	public LazyDataModel<PostLoanManageVO> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<PostLoanManageVO> dataModel) {
		this.dataModel = dataModel;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public PostLoanManageVO getPostLoanManageVO() {
		return postLoanManageVO;
	}

	public void setPostLoanManageVO(PostLoanManageVO postLoanManageVO) {
		this.postLoanManageVO = postLoanManageVO;
	}

	public String getLoanStatusCd() {
		return loanStatusCd;
	}

	public void setLoanStatusCd(String loanStatusCd) {
		this.loanStatusCd = loanStatusCd;
	}

	public IRepayBS getRepayBS() {
		return repayBS;
	}

	public void setRepayBS(IRepayBS repayBS) {
		this.repayBS = repayBS;
	}

	public IMsgTemplateBS getMsgTemplateBS() {
		return msgTemplateBS;
	}

	public void setMsgTemplateBS(IMsgTemplateBS msgTemplateBS) {
		this.msgTemplateBS = msgTemplateBS;
	}

	public ICustAccountBS getCustAccountBS() {
		return custAccountBS;
	}

	public void setCustAccountBS(ICustAccountBS custAccountBS) {
		this.custAccountBS = custAccountBS;
	}

	public ICustInfoBS getCustInfoBS() {
		return custInfoBS;
	}

	public void setCustInfoBS(ICustInfoBS custInfoBS) {
		this.custInfoBS = custInfoBS;
	}
	
	
}
