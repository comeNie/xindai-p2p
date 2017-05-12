package com.qfw.model.vo.custinfo.account;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.qfw.bizservice.custinfo.account.ICustAccountBS;
import com.qfw.common.log.LogFactory;

@SuppressWarnings("serial")
public class LazyAccountDetailDataModel  extends LazyDataModel<AccountResponseVO>{

	private AccountRequestVO requestVO;
	private List<AccountResponseVO> infoVOs;
	private ICustAccountBS accountBS;
	Logger log = LogFactory.getInstance().getBusinessLogger();
	
	public LazyAccountDetailDataModel(AccountRequestVO requestVO,ICustAccountBS accountBS){
		this.accountBS = accountBS;
		this.requestVO = requestVO;
	}
	
	@Override
	public List<AccountResponseVO> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		try {
			infoVOs = this.accountBS.findDetailInfoByParams(requestVO, first, pageSize);
			setRowCount(this.accountBS.findCountDeatilByParams(requestVO));
		} catch (Exception e) {
			log.error("获取pm币翻页信息异常：", e);
		}
		return infoVOs;
	}

	@Override
	public AccountResponseVO getRowData(String rowKey){
		for (AccountResponseVO vo : infoVOs) {
			if(String.valueOf(vo.getDetailId()).equals(rowKey)){
				return vo;
			}
		}
		return this.accountBS.findAccountDetailInfoByid(Integer.valueOf(rowKey));
	}
	
	@Override
	public Object getRowKey(AccountResponseVO vo){
		return vo.getDetailId();
	}
	
}
