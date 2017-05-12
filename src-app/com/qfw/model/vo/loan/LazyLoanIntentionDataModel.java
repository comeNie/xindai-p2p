package com.qfw.model.vo.loan;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.qfw.bizservice.loan.ILoanApplyManageBS;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.vo.PageList;
import com.qfw.model.vo.loan.loanApply.LoanApplyManageVO;

@SuppressWarnings("serial")
public class LazyLoanIntentionDataModel extends LazyDataModel<Map> {

	private Map filter;
	private ILoanApplyManageBS loanManageBS;
	private List loanVOList ;
	
	Logger log = LogFactory.getInstance().getPlatformLogger();
	
	public LazyLoanIntentionDataModel(Map filter, ILoanApplyManageBS loanManageBS){
		this.filter = filter;
		this.loanManageBS = loanManageBS;
	}
	
	@Override
	public List load(int first, int pageSize, String sortField,	SortOrder sortOrder, Map<String, String> filters) {
		try {
			PageList pageList = loanManageBS.findInfoPagesByMap(filter, first, pageSize);
			loanVOList = pageList.getData();
			setRowCount(pageList.getCount());
		} catch (Exception e) {
			if(log.isInfoEnabled()){
				log.error("获取借款信息异常：", e);
			}
		}
		return loanVOList;
	}
	
	@Override
	public Object getRowKey(Map infoVO) {
		return infoVO.get("ID");
	}


	public ILoanApplyManageBS getLoanManageBS() {
		return loanManageBS;
	}

	public void setLoanManageBS(ILoanApplyManageBS loanManageBS) {
		this.loanManageBS = loanManageBS;
	}

	public Map getFilter() {
		return filter;
	}

	public void setFilter(Map filter) {
		this.filter = filter;
	}

	public List getLoanVOList() {
		return loanVOList;
	}

	public void setLoanVOList(List loanVOList) {
		this.loanVOList = loanVOList;
	}

	
	

}
