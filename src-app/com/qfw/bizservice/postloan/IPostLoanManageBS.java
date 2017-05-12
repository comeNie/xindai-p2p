package com.qfw.bizservice.postloan;

import java.util.List;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.model.vo.postLoan.PostLoanManageVO;
import com.qfw.model.vo.postLoan.PostLoanSearchVO;
import com.qfw.model.vo.postLoan.PostLoanTaskInfoVO;

/**
 * 贷后管理bs
 *
 * @author weiqs
 */
public interface IPostLoanManageBS extends IBaseService {

	/**
	 * 根据id查询交易记录bo
	 * @param id
	 * @return
	 * @throws BizException
	 */
	public PostLoanManageVO findInfoById(Integer id ) ;
	
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
	
	/**
	 * 生成贷后任务
	 * @param infoVO
	 */
	public void savePostLoan(PostLoanTaskInfoVO infoVO) throws BizException;
	
}
