package com.qfw.dao.loan;

import java.util.List;

import com.qfw.common.dao.IBaseDAO;
import com.qfw.common.exception.BizException;
import com.qfw.model.vo.loan.LoanApproveVO;
import com.qfw.model.vo.loan.LoanManageVO;
import com.qfw.model.vo.loan.LoanSearchVO;

public interface ILoanManageDAO extends IBaseDAO {

	/**
	 * 根据借款发布id查询信息
	 * @param loanApproveId
	 * @return
	 */
	public LoanManageVO findInfoByID(Integer loanApproveId) ;
	
	/**
	 * 分页查询list
	 * @return
	 * @throws BizException
	 */
	public List<LoanManageVO> findInfoPagesByVO (LoanSearchVO searchVO, int first, int pageSize) throws BizException;

	/**
	 * 分页查询(投资列表)
	 * @author 535
	 * @return
	 * @throws BizException
	 */
	public List<LoanApproveVO> findListPagesByVO (LoanSearchVO searchVO, int first, int pageSize) throws BizException;
	/**
	 * 获取数据总数
	 * @param searchVO
	 * @return
	 * @throws BizException
	 */
	public int getCountByVO(LoanSearchVO searchVO);
	
	/**
	 * 获取数据总数
	 * @param searchVO
	 * @return
	 * @throws BizException
	 */
	public int getApproveCountByVO(LoanSearchVO searchVO);
	
}
