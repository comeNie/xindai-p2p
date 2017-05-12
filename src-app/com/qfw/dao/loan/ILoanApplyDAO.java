package com.qfw.dao.loan;

import java.util.List;

import com.qfw.common.dao.IBaseDAO;
import com.qfw.model.bo.BizLoanApplyBO;
import com.qfw.model.vo.loan.LoanApplyVO;

/**
 * 借款申请dao
 *
 * @author kyc
 */
public interface ILoanApplyDAO extends IBaseDAO {

	/**
	 * 根据参数查询借款信息
	 * @param vo
	 * @return
	 */
	public List<BizLoanApplyBO> findByParams(LoanApplyVO vo);
}
