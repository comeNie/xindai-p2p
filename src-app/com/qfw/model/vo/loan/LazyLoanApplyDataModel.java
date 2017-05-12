package com.qfw.model.vo.loan;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.qfw.bizservice.loan.ILoanApplyManageBS;
import com.qfw.common.log.LogFactory;
import com.qfw.model.vo.loan.loanApply.LoanApplyManageVO;
import com.qfw.model.vo.loan.loanApply.LoanApplySearchVO;

@SuppressWarnings("serial")
public class LazyLoanApplyDataModel extends LazyDataModel<LoanApplyManageVO> {

	private LoanApplySearchVO searchVO;
	private ILoanApplyManageBS loanManageBS;
	private List<LoanApplyManageVO> loanVOList ;
	
	Logger log = LogFactory.getInstance().getPlatformLogger();
	
	public LazyLoanApplyDataModel(LoanApplySearchVO searchVO, ILoanApplyManageBS loanManageBS){
		this.searchVO = searchVO;
		this.loanManageBS = loanManageBS;
	}
	
	@Override
	public List<LoanApplyManageVO> load(int first, int pageSize, String sortField,
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
	public LoanApplyManageVO getRowData(String rowKey) {
		if(StringUtils.isEmpty(rowKey)|| (null == loanVOList || loanVOList.size()<=0)){
			return null;
		}
		for(LoanApplyManageVO loanManageVO : loanVOList){
			if(String.valueOf(loanManageVO.getLoanApplyId()).equals(rowKey))
				return loanManageVO;
		}
		LoanApplyManageVO vo = new LoanApplyManageVO();
		try {
			vo = loanManageBS.findInfoById(Integer.valueOf(rowKey));
		} catch (Exception e) {
			log.error("通过ID获取异常：", e);
		}
		return vo;
	}
	
	@Override
	public Object getRowKey(LoanApplyManageVO infoVO) {
		return infoVO.getLoanApplyId();
	}

	public LoanApplySearchVO getSearchVO() {
		return searchVO;
	}

	public void setSearchVO(LoanApplySearchVO searchVO) {
		this.searchVO = searchVO;
	}

	public ILoanApplyManageBS getLoanManageBS() {
		return loanManageBS;
	}

	public void setLoanManageBS(ILoanApplyManageBS loanManageBS) {
		this.loanManageBS = loanManageBS;
	}

	public List<LoanApplyManageVO> getLoanVOList() {
		return loanVOList;
	}

	public void setLoanVOList(List<LoanApplyManageVO> loanVOList) {
		this.loanVOList = loanVOList;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

}
