package com.qfw.dao.repay.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qfw.common.dao.impl.BaseDAOImpl;
import com.qfw.common.exception.BizException;
import com.qfw.dao.repay.IRepayDAO;
import com.qfw.model.bo.BizRepayPlanDetailBO;

@Repository("repayDAO")
public class RepayDAOImpl extends BaseDAOImpl implements IRepayDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<BizRepayPlanDetailBO> queryRepayPlanDetail(Integer loanId) throws BizException {
		if(loanId==null) throw new BizException("参数loanId不能为空");
		String hqlstr = "from BizRepayPlanDetailBO bo WHERE bo.bizLoanBO.id = ?";
		return (List<BizRepayPlanDetailBO>) findByHQL(hqlstr, new Object[]{loanId});
	}



}
