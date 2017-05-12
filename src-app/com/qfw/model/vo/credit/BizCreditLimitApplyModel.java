package com.qfw.model.vo.credit;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.qfw.bizservice.credit.ICreditLimitApplyBS;
import com.qfw.common.model.vo.PageList;
import com.qfw.common.util.ApplicationContextUtil;
import com.qfw.jbpm.bizservice.IFlowBS;
import com.qfw.jbpm.model.vo.FilterTaskVO;

public class BizCreditLimitApplyModel extends LazyDataModel<BizCreditLimitApplyVO> {
	

	
	private List<BizCreditLimitApplyVO> creditLimitApply;
	
	private BizCreditLimitApplyVO creditLimitApplyVO;
	
	
	public BizCreditLimitApplyModel(BizCreditLimitApplyVO creditLimitApplyVO){
		this.creditLimitApplyVO = creditLimitApplyVO;
	}
	 
	@Override
	public List<BizCreditLimitApplyVO> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		ICreditLimitApplyBS creditLimitApplyBS = (ICreditLimitApplyBS) ApplicationContextUtil.getBean("creditLimitApplyBS");
		PageList<BizCreditLimitApplyVO> pl = creditLimitApplyBS.getCreditLimitApply((BizCreditLimitApplyVO) creditLimitApply, first, pageSize);
		creditLimitApply = pl.getData();
		setRowCount(pl.getCount());
		return creditLimitApply;
	}

	public List<BizCreditLimitApplyVO> getCreditLimitApply() {
		return creditLimitApply;
	}

	public void setCreditLimitApply(List<BizCreditLimitApplyVO> creditLimitApply) {
		this.creditLimitApply = creditLimitApply;
	}

	public BizCreditLimitApplyVO getCreditLimitApplyVO() {
		return creditLimitApplyVO;
	}

	public void setCreditLimitApplyVO(BizCreditLimitApplyVO creditLimitApplyVO) {
		this.creditLimitApplyVO = creditLimitApplyVO;
	}

	

}
