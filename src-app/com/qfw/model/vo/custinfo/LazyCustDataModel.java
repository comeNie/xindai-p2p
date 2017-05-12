package com.qfw.model.vo.custinfo;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.common.log.LogFactory;

@SuppressWarnings("serial")
public class LazyCustDataModel extends LazyDataModel<CustInfoVO>{

	private SearchCustInfoVO searchCustInfoVO;
	
	private ICustInfoBS custInfoBS;
	
	private List<CustInfoVO> custInfoVOs;
	
	Logger log = LogFactory.getInstance().getPlatformLogger();
	
	public LazyCustDataModel(SearchCustInfoVO searchCustInfoVO, ICustInfoBS custInfoBS){
		this.searchCustInfoVO = searchCustInfoVO;
		this.custInfoBS = custInfoBS;
	}
	
	@Override
	public List<CustInfoVO> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		try {
			custInfoVOs = custInfoBS.findCustInfoPagesByVO(searchCustInfoVO, first, pageSize);
			setRowCount(custInfoBS.findCustInfoCountByVO(searchCustInfoVO));
		} catch (Exception e) {
			log.error("获取客户翻页信息异常：", e);
		}
		return custInfoVOs;
	}
	
	@Override
	public CustInfoVO getRowData(String rowKey) {
		if(StringUtils.isEmpty(rowKey)|| (null == custInfoVOs || custInfoVOs.size()<=0)){
			return null;
		}
		for(CustInfoVO custInfoVO : custInfoVOs){
			if(String.valueOf(custInfoVO.getCustId()).equals(rowKey))
				return custInfoVO;
		}
		CustInfoVO vo = new CustInfoVO();
		try {
			vo = custInfoBS.findCustInfoById(Integer.valueOf(rowKey));
		} catch (Exception e) {
			log.error("通过ID获取异常：", e);
		}
		return vo;
	}
	
	@Override
	public Object getRowKey(CustInfoVO custInfoVO) {
		return custInfoVO.getCustId();
	}

	public SearchCustInfoVO getSearchCustInfoVO() {
		return searchCustInfoVO;
	}

	public void setSearchCustInfoVO(SearchCustInfoVO searchCustInfoVO) {
		this.searchCustInfoVO = searchCustInfoVO;
	}

	public ICustInfoBS getCustInfoBS() {
		return custInfoBS;
	}

	public void setCustInfoBS(ICustInfoBS custInfoBS) {
		this.custInfoBS = custInfoBS;
	}

	public List<CustInfoVO> getCustInfoVOs() {
		return custInfoVOs;
	}

	public void setCustInfoVOs(List<CustInfoVO> custInfoVOs) {
		this.custInfoVOs = custInfoVOs;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}
	
	
	
}
