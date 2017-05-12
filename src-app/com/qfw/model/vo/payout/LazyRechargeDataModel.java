package com.qfw.model.vo.payout;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.qfw.bizservice.payout.IRechargePayoutBS;

@SuppressWarnings("serial")
public class LazyRechargeDataModel extends LazyDataModel<RechargeModelVO>{

	private RechargeVO rechargeVO;
	private List<RechargeModelVO> modelVOs;
	private IRechargePayoutBS payoutBS;

	public LazyRechargeDataModel(RechargeVO rechargeVO,IRechargePayoutBS payoutBS){
		this.rechargeVO = rechargeVO;
		this.payoutBS = payoutBS;
	}
	
	@Override
	public List<RechargeModelVO> load(int arg0, int arg1, String arg2,
			SortOrder arg3, Map<String, String> arg4) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public RechargeModelVO getRowData(String rowKey){
		return null;
	}
	
}
