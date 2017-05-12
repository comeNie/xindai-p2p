package com.qfw.bean.payout;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.bizservice.custinfo.account.ICustAccountBS;
import com.qfw.bizservice.payout.IRechargePayoutBS;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.MessagesController;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizAccountBO;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.vo.custinfo.account.AccountRequestVO;
import com.qfw.model.vo.payout.RechargeVO;

/**
 * 充值backingbean
 *
 * @author kyc
 */
@ViewScoped
@ManagedBean(name="addRechargeViewBean")
public class AddRechargeBackingBean extends BaseBackingBean{

	private static final long serialVersionUID = 3695180615731534312L;

	@ManagedProperty(value = "#{rechargePayoutBS}")
    private IRechargePayoutBS rechargePayoutBS;
	@ManagedProperty(value = "#{custAccountBS}")
	private ICustAccountBS custAccountBS;
	@ManagedProperty(value = "#{custInfoBS}")
	private ICustInfoBS custInfoBS;
	
	private RechargeVO vo = new RechargeVO();
	
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	
	private  List<SelectItem> accountList = new ArrayList<SelectItem>();
	
	@PostConstruct
    public void init(){
		vo = new RechargeVO();
		try {
			BizCustomerBO cust  = custInfoBS.findCustByUserId(ViewOper.getUser().getUserId());
			AccountRequestVO requestVO = new AccountRequestVO();
			requestVO.setCustId(cust.getId());
			List<BizAccountBO> accountBOList = this.custAccountBS.findInfoByParams(requestVO);
			if(null!=accountBOList && accountBOList.size()>0){
				for (BizAccountBO bo : accountBOList) {
					accountList.add(new SelectItem(bo.getId(),bo.getAccount()));
					vo.setAccount(bo.getAccount());
				}
			}
		} catch (Exception e) {
			
		}
		
    }
	
	/**
	 * 线上支付
	 */
	public void rechargeONline(){
		alert("当前不支持线上支付!");
	}
	
	/**
	 * 理财卡充值
	 */
	public String rechargeCard(){
		try {
			if(null==vo.getAccountID()||vo.getAccountID()==0){
				MessagesController.addWarn("充值失败","充值账号获取失败");
				return null;
			}
			vo.setRechargeType(AppConst.PAYOUTTYPE_CARD);
			this.rechargePayoutBS.transRechargePayou(vo);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"充值成功！", null);
			FacesContext.getCurrentInstance().addMessage(null, message);
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"充值失败", null);
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		return null;
	}

	public IRechargePayoutBS getRechargePayoutBS() {
		return rechargePayoutBS;
	}

	public void setRechargePayoutBS(IRechargePayoutBS rechargePayoutBS) {
		this.rechargePayoutBS = rechargePayoutBS;
	}

	public RechargeVO getVo() {
		return vo;
	}

	public void setVo(RechargeVO vo) {
		this.vo = vo;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public List<SelectItem> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<SelectItem> accountList) {
		this.accountList = accountList;
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
