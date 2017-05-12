package com.qfw.bizservice.loan;

import java.util.List;
import java.util.Map;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.vo.PageList;
import com.qfw.model.vo.loan.loanApply.LoanApplyManageVO;
import com.qfw.model.vo.loan.loanApply.LoanApplySearchVO;

/**
 * 借款申请管理bs
 *
 * @author kyc
 */
public interface ILoanApplyManageBS extends IBaseService {

	/**
	 * 根据id查询交易记录bo
	 * @param id
	 * @return
	 * @throws BizException
	 */
	public LoanApplyManageVO findInfoById(Integer id ) ;
	
	/**
	 * 分页查询list
	 * @return
	 * @throws BizException
	 */
	public List<LoanApplyManageVO> findInfoPagesByVO (LoanApplySearchVO searchVO, int first, int pageSize) throws BizException;

	/**
	 * 获取数据总数
	 * @param searchVO
	 * @return
	 * @throws BizException
	 */
	public int getCountByVO(LoanApplySearchVO searchVO);
	/**
	 * 查询借款意向列表
	 * @param filter
	 * @param first
	 * @param pageSize
	 * @return
	 * @throws BizException
	 */
	public PageList findInfoPagesByMap(Map filter,int first, int pageSize) throws BizException;
	
}
