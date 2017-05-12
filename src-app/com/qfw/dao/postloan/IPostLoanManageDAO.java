package com.qfw.dao.postloan;

import java.util.List;

import com.qfw.common.dao.IBaseDAO;
import com.qfw.common.exception.BizException;
import com.qfw.model.vo.postLoan.PostLoanManageVO;
import com.qfw.model.vo.postLoan.PostLoanSearchVO;

public interface IPostLoanManageDAO extends IBaseDAO {

	/**
	 * 根据id查询信息
	 * @param loanApproveId
	 * @return
	 */
	public PostLoanManageVO findInfoByID(Integer loanApproveId) ;
	
	/**
	 * 分页查询list
	 * @return
	 * @throws BizException
	 */
	public List<PostLoanManageVO> findInfoPagesByVO (PostLoanSearchVO searchVO, int first, int pageSize) throws BizException;

	/**
	 * 获取数据总数
	 * @param searchVO
	 * @return
	 * @throws BizException
	 */
	public int getCountByVO(PostLoanSearchVO searchVO);
}
