package com.qfw.dao.loan;

import java.util.List;
import java.util.Map;

import com.qfw.common.dao.IBaseDAO;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.vo.PageList;
import com.qfw.model.vo.loan.loanApply.LoanApplyManageVO;
import com.qfw.model.vo.loan.loanApply.LoanApplySearchVO;

public interface ILoanApplyManageDAO extends IBaseDAO {

	/**
	 * 根据借款申请id查询信息
	 * @param loanApplyId
	 * @return
	 */
	public LoanApplyManageVO findInfoByID(Integer loanApplyId) ;
	
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
	 * 查询接口意向列表
	 * @param filter
	 * @param first
	 * @param pageSize
	 * @return
	 * @throws BizException
	 */
	public PageList findInfoPagesByMap(Map filter,int first, int pageSize) throws BizException;
}
