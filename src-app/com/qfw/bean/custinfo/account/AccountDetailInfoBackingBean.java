package com.qfw.bean.custinfo.account;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;

import com.qfw.bizservice.custinfo.account.ICustAccountBS;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.vo.custinfo.account.AccountRequestVO;
import com.qfw.model.vo.custinfo.account.AccountResponseVO;
import com.qfw.model.vo.custinfo.account.LazyAccountDetailDataModel;

/**
 * 账号明细表
 *
 * @author kyc
 */
@ViewScoped
@ManagedBean(name="accountDetailInfoBean")
public class AccountDetailInfoBackingBean extends BaseBackingBean {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value = "#{custAccountBS}")
	private ICustAccountBS accountBS;
	private LazyDataModel<AccountResponseVO> detailModels;
	private AccountRequestVO accountVO = new AccountRequestVO();
	private AccountResponseVO detailBO;
	
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	
	@PostConstruct
	public void init() {
		Object custId = ViewOper.getSessionTmpAttr("userId");
		Object account = ViewOper.getSessionTmpAttr("accout");
		if(custId!=null&&account!=null){
			try {
				accountVO = new AccountRequestVO();
				accountVO.setCustId((Integer)custId);
				accountVO.setAccount((String)account);
				detailModels =  new LazyAccountDetailDataModel(accountVO, accountBS);
			} catch (Exception e) {
				log.error("账户明细信息获取异常：", e);
			}
		}
	}
	
	public void search(){
		super.search();
		if(accountVO != null){ 
			try {
				detailBO = new AccountResponseVO();
				detailModels = new LazyAccountDetailDataModel(accountVO, accountBS);
			} catch (Exception e) {
				log.error("日常查询搜索异常：", e);
				alert("日常查询搜索异常："+e.getMessage());
			}
		}
	}
	
	public void operate(){
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public ICustAccountBS getAccountBS() {
		return accountBS;
	}

	public void setAccountBS(ICustAccountBS accountBS) {
		this.accountBS = accountBS;
	}

	public AccountRequestVO getAccountVO() {
		return accountVO;
	}

	public void setAccountVO(AccountRequestVO accountVO) {
		this.accountVO = accountVO;
	}

	public LazyDataModel<AccountResponseVO> getDetailModels() {
		return detailModels;
	}

	public void setDetailModels(LazyDataModel<AccountResponseVO> detailModels) {
		this.detailModels = detailModels;
	}

	public AccountResponseVO getDetailBO() {
		return detailBO;
	}

	public void setDetailBO(AccountResponseVO detailBO) {
		this.detailBO = detailBO;
	}

}
