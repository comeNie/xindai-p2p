package com.qfw.bean.dailyInfo;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;

import com.qfw.bizservice.payout.IRechargePayoutBS;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.MessagesController;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.AppConst;
import com.qfw.model.vo.payout.LazyRechargeCardModel;
import com.qfw.model.vo.payout.RechargeCardResponseVO;
import com.qfw.model.vo.payout.RechargeCardVO;

/**
 * 充值卡BackingBean
 *
 * @author kyc
 */
@ViewScoped
@ManagedBean(name="rechargeCardDailyBean")
public class RechargeCardDailyBackingBean extends BaseBackingBean{

	private static final long serialVersionUID = -2500537072924347181L;
	
	@ManagedProperty(value = "#{rechargePayoutBS}")
	private IRechargePayoutBS payoutBS;
	private LazyDataModel<RechargeCardResponseVO> cardModel;
	private RechargeCardVO vo = new RechargeCardVO() ;
	private RechargeCardResponseVO cardVO;

    private Logger log = LogFactory.getInstance().getBusinessLogger();
    
    @PostConstruct
    public void init() {
    	search();
    }
    
	public void search() {
		super.search();
		if(vo != null) {
			try {
				cardVO = new RechargeCardResponseVO();
				vo.setStatus(AppConst.RECHARGE_STATUS_USE);
				cardModel = new LazyRechargeCardModel(vo, payoutBS);
			} catch (Exception e) {
				log.error("理财卡搜索异常：", e);
				alert("理财卡搜索异常："+e.getMessage());
			}
		}
	}
	
	public void operate(){}
	
	public void change() {
		String operateFlag = ViewOper.getParameter("operateFlag");
		ViewOper.getSession().setAttribute("id", cardVO.getId());
		String id = String.valueOf(cardVO.getId());
		if ("no".equalsIgnoreCase(operateFlag)) {
			String sql = "update biz_recharge_card set status='3' where id='" + id + "'";
			try {
				payoutBS.getJdbcTemplate().update(sql);
				MessagesController.addInfo("充值卡状态已置为失效！", "充值卡状态更新成功！");
			} catch (Exception e) {
				log.error("卡号为：" + cardVO.getCardCd() + "的充值卡状态更新异常：", e);
				alert("卡号为：" + cardVO.getCardCd() + "的充值卡状态更新异常：" + e);
			}
		} else if ("yes".equalsIgnoreCase(operateFlag)) {
			String sql = "update biz_recharge_card set status='0' where id='" + id + "'";
			try {
				payoutBS.getJdbcTemplate().update(sql);
				MessagesController.addInfo("充值卡状态已置为生效！", "充值卡状态更新成功！");
			} catch (Exception e) {
				log.error("卡号为：" + cardVO.getCardCd() + "的充值卡状态更新异常：", e);
				alert("卡号为：" + cardVO.getCardCd() + "的充值卡状态更新异常：" + e);
			}
		}
	}

	public IRechargePayoutBS getPayoutBS() {
		return payoutBS;
	}

	public void setPayoutBS(IRechargePayoutBS payoutBS) {
		this.payoutBS = payoutBS;
	}

	public RechargeCardVO getVo() {
		return vo;
	}

	public void setVo(RechargeCardVO vo) {
		this.vo = vo;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public RechargeCardResponseVO getCardVO() {
		return cardVO;
	}

	public void setCardVO(RechargeCardResponseVO cardVO) {
		this.cardVO = cardVO;
	}

	public void setCardModel(LazyDataModel<RechargeCardResponseVO> cardModel) {
		this.cardModel = cardModel;
	}

	public LazyDataModel<RechargeCardResponseVO> getCardModel() {
		return cardModel;
	}

}
