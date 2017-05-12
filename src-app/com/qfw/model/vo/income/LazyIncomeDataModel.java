package com.qfw.model.vo.income;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.qfw.bizservice.income.IIncomeManageBS;
import com.qfw.common.log.LogFactory;

@SuppressWarnings("serial")
public class LazyIncomeDataModel extends LazyDataModel<IncomeDetailVO> {

	private IncomeSearchVO searchVO;
	private IIncomeManageBS incomeManageBS;
	private List<IncomeDetailVO> incomeVOList ;
	
	Logger log = LogFactory.getInstance().getPlatformLogger();
	
	public LazyIncomeDataModel(IncomeSearchVO searchVO, IIncomeManageBS incomeManageBS){
		this.searchVO = searchVO;
		this.incomeManageBS = incomeManageBS;
	}
	
	@Override
	public List<IncomeDetailVO> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		try {
			incomeVOList = incomeManageBS.findInfoPagesByVO(searchVO, first, pageSize);
			setRowCount(this.incomeManageBS.getCountByVO(searchVO));
		} catch (Exception e) {
			if(log.isInfoEnabled()){
				log.error("获取借款信息异常：", e);
			}
		}
		return incomeVOList;
	}
	
	@Override
	public IncomeDetailVO getRowData(String rowKey) {
		if(StringUtils.isEmpty(rowKey)|| (null == incomeVOList || incomeVOList.size()<=0)){
			return null;
		}
		for(IncomeDetailVO incomeDetailVO : incomeVOList){
			if(String.valueOf(incomeDetailVO.getId()).equals(rowKey))
				return incomeDetailVO;
		}
		IncomeDetailVO vo = new IncomeDetailVO();
		try {
			vo = incomeManageBS.findInfoById(Integer.valueOf(rowKey));
		} catch (Exception e) {
			log.error("通过ID获取异常：", e);
		}
		return vo;
	}
	
	@Override
	public Object getRowKey(IncomeDetailVO infoVO) {
		return infoVO.getId();
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public IncomeSearchVO getSearchVO() {
		return searchVO;
	}

	public void setSearchVO(IncomeSearchVO searchVO) {
		this.searchVO = searchVO;
	}

	public IIncomeManageBS getIncomeManageBS() {
		return incomeManageBS;
	}

	public void setIncomeManageBS(IIncomeManageBS incomeManageBS) {
		this.incomeManageBS = incomeManageBS;
	}

	public List<IncomeDetailVO> getIncomeVOList() {
		return incomeVOList;
	}

	public void setIncomeVOList(List<IncomeDetailVO> incomeVOList) {
		this.incomeVOList = incomeVOList;
	}

}
