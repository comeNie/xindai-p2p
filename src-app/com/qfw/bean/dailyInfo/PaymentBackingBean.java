package com.qfw.bean.dailyInfo;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;

import com.qfw.bizservice.transaction.ITradeBS;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.vo.transaction.LazyTradeDataModel;
import com.qfw.model.vo.transaction.TradeResponseVO;
import com.qfw.model.vo.transaction.TradeVO;

/**
 * 交易查询backingbean
 *
 * @author kyc
 */
@ViewScoped
@ManagedBean(name="paymentBean")
public class PaymentBackingBean extends BaseBackingBean {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value = "#{tradeBS}")
	private ITradeBS tradeBS;
	private LazyDataModel<TradeResponseVO> tradeModel;
	private TradeVO tradeVO = new TradeVO();
	private TradeResponseVO tradeResVO;
	private String type = "";
	
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	
	@PostConstruct
    public void init(){
		search();
    }
	
	public void search(){
		if(tradeVO != null){ 
			try {
				tradeResVO = new TradeResponseVO();
				tradeModel = new LazyTradeDataModel(tradeVO, tradeBS);
			} catch (Exception e) {
				log.error("日常查询搜索异常：", e);
				alert("日常查询搜索异常："+e.getMessage());
			}
		}
	
	}
	
	public String reset () {
		tradeVO = new TradeVO();
		return null;
	}
	
	public void operate(){
		String operateFlag = ViewOper.getParameter("operateFlag");
		if("view".equalsIgnoreCase(operateFlag)){
			ViewOper.getSession().setAttribute("deptId", tradeResVO.getId());
			// 充值
			// 提现
		}
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
