package com.qfw.model.vo.payout;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.qfw.bizservice.payout.IRechargePayoutBS;
import com.qfw.bizservice.payout.IWithdrawalsPayoutBS;
import com.qfw.common.log.LogFactory;
import com.qfw.model.bo.BizRechargeCardBO;
import com.qfw.model.bo.BizWithdrawalsBO;

public class LazyWithDataModel extends LazyDataModel<WithdrawalsResponseVO>{

	private static final long serialVersionUID = 8841394803634079924L;
	
	private IWithdrawalsPayoutBS payoutBS;
	private WithdrawalsVO draVO ;
	private List<WithdrawalsResponseVO> draBOList;
	
	Logger log = LogFactory.getInstance().getBusinessLogger();
	
	public LazyWithDataModel(WithdrawalsVO draVO , IWithdrawalsPayoutBS payoutBS){
		this.draVO = draVO;
		this.payoutBS = payoutBS;
	}
	
	@Override
	public List<WithdrawalsResponseVO> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		try {
			draBOList = this.payoutBS.findWithBOPagesByVO(draVO, first, pageSize);
			setRowCount(this.payoutBS.findWithCountByVO(draVO));
		} catch (Exception e) {
			log.error("获取理财卡翻页信息异常：", e);
		}
		return draBOList;
	}
	
	public WithdrawalsResponseVO getRowData(String rowKey){
		if(StringUtils.isEmpty(rowKey)){
			return null;
		}
		for( WithdrawalsResponseVO bo : draBOList){
			if(String.valueOf(bo.getId()).equals(rowKey)){
				return bo;
			}
		}
		WithdrawalsResponseVO bo = new WithdrawalsResponseVO();
		try {
			bo = this.payoutBS.findWithBOById(Integer.valueOf(rowKey));
		} catch (Exception e) {
			log.error("通过ID获取理财卡异常：", e);
		}
		return bo;
	}
	
	@Override
	public Object getRowKey(WithdrawalsResponseVO bo) {
		return bo.getId();
	}

}
