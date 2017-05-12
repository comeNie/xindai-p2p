package com.qfw.model.vo.loan;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.qfw.bizservice.loan.ILoanManageBS;
import com.qfw.common.log.LogFactory;

@SuppressWarnings("serial")
public class LazyLoanDataModel extends LazyDataModel<LoanManageVO> {

	private LoanSearchVO searchVO;
	private ILoanManageBS loanManageBS;
	private List<LoanManageVO> loanVOList ;
	
	Logger log = LogFactory.getInstance().getPlatformLogger();
	
	public LazyLoanDataModel(LoanSearchVO searchVO, ILoanManageBS loanManageBS){
		this.searchVO = searchVO;
		this.loanManageBS = loanManageBS;
	}
	
	@Override
	public List<LoanManageVO> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		try {
			loanVOList = loanManageBS.findInfoPagesByVO(searchVO, first, pageSize);
			setRowCount(this.loanManageBS.getCountByVO(searchVO));
		} catch (Exception e) {
			if(log.isInfoEnabled()){
				log.error("获取借款信息异常：", e);
			}
		}
		return loanVOList;
	}
	
	@Override
	public LoanManageVO getRowData(String rowKey) {
		if(StringUtils.isEmpty(rowKey)|| (null == loanVOList || loanVOList.size()<=0)){
			return null;
		}
		for(LoanManageVO loanManageVO : loanVOList){
			if(String.valueOf(loanManageVO.getLoanApproveId()).equals(rowKey))
				return loanManageVO;
		}
		LoanManageVO vo = new LoanManageVO();
		try {
			vo = loanManageBS.findInfoById(Integer.valueOf(rowKey));
		} catch (Exception e) {
			log.error("通过ID获取异常：", e);
		}
		return vo;
	}
	
	@Override
	public Object getRowKey(LoanManageVO infoVO) {
		return infoVO.getLoanApproveId();
	}

	public LoanSearchVO getSearchVO() {
		return searchVO;
	}

	public void setSearchVO(LoanSearchVO searchVO) {
		this.searchVO = searchVO;
	}

	public ILoanManageBS getLoanManageBS() {
		return loanManageBS;
	}

	public void setLoanManageBS(ILoanManageBS loanManageBS) {
		this.loanManageBS = loanManageBS;
	}

	public List<LoanManageVO> getLoanVOList() {
		return loanVOList;
	}

	public void setLoanVOList(List<LoanManageVO> loanVOList) {
		this.loanVOList = loanVOList;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

}
