package com.qfw.dao.loan.loanApprove;

import java.util.List;

import com.qfw.common.dao.IBaseDAO;
import com.qfw.model.bo.BizLoanApproveBO;
import com.qfw.model.vo.loan.loanApprove.LoanApproveSearchVO;

public interface ILoanApproveDAO extends IBaseDAO {

	/**
	 * 根据参数查询借款发布信息
	 * @param searchVO
	 * @return
	 */
	public List<BizLoanApproveBO> findBOListBySearchVO(LoanApproveSearchVO searchVO);
}
