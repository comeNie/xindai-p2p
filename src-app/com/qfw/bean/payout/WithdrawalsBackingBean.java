package com.qfw.bean.payout;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;

import com.qfw.bizservice.payout.IWithdrawalsPayoutBS;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.model.vo.payout.LazyWithDataModel;
import com.qfw.model.vo.payout.WithdrawalsResponseVO;
import com.qfw.model.vo.payout.WithdrawalsVO;

/**
 * 充值查询backingbean
 *
 * @author kyc
 */
@ViewScoped
@ManagedBean(name="withdrawalsViewBean")
public class WithdrawalsBackingBean extends BaseBackingBean{

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value = "#{withdrawalsPayoutBS}")
	private IWithdrawalsPayoutBS payoutBS;
	private LazyDataModel<WithdrawalsResponseVO> tradeModel;
	private WithdrawalsVO draVO = new WithdrawalsVO();
	private WithdrawalsResponseVO draResVO;
	
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	
	@PostConstruct
	public void init() {
		if(draVO != null){ 
			try {
				draResVO = new WithdrawalsResponseVO();
				tradeModel = new LazyWithDataModel(draVO, payoutBS);
			} catch (Exception e) {
				log.error("日常查询搜索异常：", e);
				alert("日常查询搜索异常："+e.getMessage());
			}
		}
	}
	
	public void search(){
		super.search();
		init();
	}
	
	public void operate(){
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public IWithdrawalsPayoutBS getPayoutBS() {
		return payoutBS;
	}

	public void setPayoutBS(IWithdrawalsPayoutBS payoutBS) {
		this.payoutBS = payoutBS;
	}

	public WithdrawalsVO getDraVO() {
		return draVO;
	}

	public void setDraVO(WithdrawalsVO draVO) {
		this.draVO = draVO;
	}

	public LazyDataModel<WithdrawalsResponseVO> getTradeModel() {
		return tradeModel;
	}

	public void setTradeModel(LazyDataModel<WithdrawalsResponseVO> tradeModel) {
		this.tradeModel = tradeModel;
	}

	public WithdrawalsResponseVO getDraResVO() {
		return draResVO;
	}

	public void setDraResVO(WithdrawalsResponseVO draResVO) {
		this.draResVO = draResVO;
	}

}
