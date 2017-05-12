package com.qfw.bean.payout;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;

import com.qfw.bizservice.transaction.ITradeBS;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.model.AppConst;
import com.qfw.model.vo.transaction.LazyTradeDataModel;
import com.qfw.model.vo.transaction.TradeResponseVO;
import com.qfw.model.vo.transaction.TradeVO;

/**
 * 充值查询backingbean
 *
 * @author kyc
 */
@ViewScoped
@ManagedBean(name="rechargeViewBean")
public class RechargeBackingBean extends BaseBackingBean{

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value = "#{tradeBS}")
	private ITradeBS tradeBS;
	private LazyDataModel<TradeResponseVO> tradeModel;
	private TradeVO tradeVO = new TradeVO();
	private TradeResponseVO tradeResVO;
	
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	
	@PostConstruct
	public void init() {
		if(tradeVO != null){ 
			try {
				tradeResVO = new TradeResponseVO();
//				tradeVO.setTradeTypeCd(AppConst.TRADE_TYPE_RECHARGE);
				tradeVO.setTradeTypeCd(AppConst.TRADE_TYPE_RECHARGE_BANK);
				tradeModel = new LazyTradeDataModel(tradeVO, tradeBS);
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

	public ITradeBS getTradeBS() {
		return tradeBS;
	}

	public void setTradeBS(ITradeBS tradeBS) {
		this.tradeBS = tradeBS;
	}

	public TradeVO getTradeVO() {
		return tradeVO;
	}

	public void setTradeVO(TradeVO tradeVO) {
		this.tradeVO = tradeVO;
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

	public LazyDataModel<TradeResponseVO> getTradeModel() {
		return tradeModel;
	}

	public void setTradeModel(LazyDataModel<TradeResponseVO> tradeModel) {
		this.tradeModel = tradeModel;
	}

	public TradeResponseVO getTradeResVO() {
		return tradeResVO;
	}

	public void setTradeResVO(TradeResponseVO tradeResVO) {
		this.tradeResVO = tradeResVO;
	}

}
