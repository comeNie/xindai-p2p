package com.qfw.model.vo.payout;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.qfw.bizservice.payout.IRechargePayoutBS;
import com.qfw.common.log.LogFactory;

@SuppressWarnings("serial")
public class LazyRechargeCardModel extends LazyDataModel<RechargeCardResponseVO>{

	private RechargeCardVO cardVO;
	private IRechargePayoutBS payoutBS;
	private List<RechargeCardResponseVO> boList;
	
	Logger log = LogFactory.getInstance().getBusinessLogger();
	
	public LazyRechargeCardModel(RechargeCardVO cardVO,IRechargePayoutBS payoutBS){
		this.cardVO = cardVO;
		this.payoutBS = payoutBS;
	}

	@Override
	public List<RechargeCardResponseVO> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		try {
			boList = this.payoutBS.findCardBOPagesByVO(cardVO, first, pageSize);
			setRowCount(this.payoutBS.findCardCountByVO(cardVO));
		} catch (Exception e) {
			log.error("获取理财卡翻页信息异常：", e);
		}
		return boList;
	}
	
	public RechargeCardResponseVO getRowData(String rowKey){
		if(StringUtils.isEmpty(rowKey)){
			return null;
		}
		for( RechargeCardResponseVO bo : boList){
			if(String.valueOf(bo.getId()).equals(rowKey)){
				return bo;
			}
		}
		RechargeCardResponseVO bo = new RechargeCardResponseVO();
		try {
			bo = this.payoutBS.findCardInfoByID(Integer.valueOf(rowKey));
		} catch (Exception e) {
			log.error("通过ID获取理财卡异常：", e);
		}
		return bo;
	}
	
	@Override
	public Object getRowKey(RechargeCardResponseVO bo) {
		return bo.getId();
	}
}

