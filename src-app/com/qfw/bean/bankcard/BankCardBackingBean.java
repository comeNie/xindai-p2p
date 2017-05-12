package com.qfw.bean.bankcard;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;

import com.qfw.bizservice.bankcard.IBankCardBS;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.model.bo.BizCardBO;
import com.qfw.model.vo.bankcard.BankCardVO;
import com.qfw.model.vo.bankcard.LazyBankCardModel;

/**
 * 银行卡BackingBean
 *
 * @author kyc
 */
@ViewScoped
@ManagedBean(name="bankCardBean")
public class BankCardBackingBean extends BaseBackingBean{

	private static final long serialVersionUID = -2500537072924347181L;
	
	@ManagedProperty(value = "#{bankCardBS}")
	private IBankCardBS cardBS;
	private LazyDataModel<BizCardBO> cardModel;
	private BankCardVO vo = new BankCardVO() ;
	private BizCardBO cardBO;

    private Logger log = LogFactory.getInstance().getBusinessLogger();
    
	public void search(){
		super.search();
		if(vo != null){ 
			try {
				cardBO = new BizCardBO();
				cardModel = new LazyBankCardModel(vo, cardBS);
			} catch (Exception e) {
				log.error("理财卡搜索异常：", e);
				alert("理财卡搜索异常："+e.getMessage());
			}
		}
	}
	
	public void operate(){}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public IBankCardBS getCardBS() {
		return cardBS;
	}

	public void setCardBS(IBankCardBS cardBS) {
		this.cardBS = cardBS;
	}

	public LazyDataModel<BizCardBO> getCardModel() {
		return cardModel;
	}

	public void setCardModel(LazyDataModel<BizCardBO> cardModel) {
		this.cardModel = cardModel;
	}

	public BankCardVO getVo() {
		return vo;
	}

	public void setVo(BankCardVO vo) {
		this.vo = vo;
	}

	public BizCardBO getCardBO() {
		return cardBO;
	}

	public void setCardBO(BizCardBO cardBO) {
		this.cardBO = cardBO;
	}

}
