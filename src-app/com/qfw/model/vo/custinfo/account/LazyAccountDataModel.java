package com.qfw.model.vo.custinfo.account;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.qfw.bizservice.custinfo.account.ICustAccountBS;
import com.qfw.common.log.LogFactory;

@SuppressWarnings("serial")
public class LazyAccountDataModel  extends LazyDataModel<PMInfoVO>{

	private PMSearchVO searchVO;
	private List<PMInfoVO> infoVOs;
	private ICustAccountBS accountBS;
	Logger log = LogFactory.getInstance().getBusinessLogger();
	
	public LazyAccountDataModel(PMSearchVO searchVO,ICustAccountBS accountBS){
		this.accountBS = accountBS;
		this.searchVO = searchVO;
	}
	
	@Override
	public List<PMInfoVO> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		try {
			infoVOs = this.accountBS.findPMInfoPagesByParams(searchVO, first, pageSize);
			setRowCount(this.accountBS.getCountByVO(searchVO));
		} catch (Exception e) {
			log.error("获取pm币翻页信息异常：", e);
		}
		return infoVOs;
	}

}
