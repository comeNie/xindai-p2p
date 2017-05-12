package com.qfw.dao.loan.loanApprove.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qfw.common.dao.impl.BaseDAOImpl;
import com.qfw.dao.loan.loanApprove.ILoanApproveDAO;
import com.qfw.model.bo.BizLoanApproveBO;
import com.qfw.model.vo.loan.loanApprove.LoanApproveSearchVO;

@Repository("loanApproveDAO")
public class LoanApproveDAOImpl extends BaseDAOImpl implements ILoanApproveDAO {

	@Override
	public List<BizLoanApproveBO> findBOListBySearchVO(
			LoanApproveSearchVO searchVO) {
		StringBuffer str = new StringBuffer();
		str.append(" from BizLoanApproveBO bo where 1=1");
		if(null!=searchVO.getCustId()){
			str.append(" and bo.custId = '").append(searchVO.getCustId()).append("'");
		}
		if(null!=searchVO.getLoanApplyId()&&searchVO.getLoanApplyId().length()>0){
			str.append(" and bo.loanApplyId = '").append(searchVO.getLoanApplyId()).append("'");
		}
		if(null!=searchVO.getWorkItemId()){
			str.append(" and bo.workItemId = '").append(searchVO.getWorkItemId()).append("'");
		}
		return findByHQL(str.toString());
	}

}
