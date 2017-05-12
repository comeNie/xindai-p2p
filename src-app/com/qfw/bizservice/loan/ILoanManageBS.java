package com.qfw.bizservice.loan;

import java.util.List;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.model.bo.BizLoanBO;
import com.qfw.model.bo.BizTradeBO;
import com.qfw.model.vo.loan.LoanApproveVO;
import com.qfw.model.vo.loan.LoanManageVO;
import com.qfw.model.vo.loan.LoanSearchVO;
import com.qfw.model.vo.transaction.TradeVO;
import com.qfw.platform.model.json.Pager;

/**
 * 借款管理bs
 *
 * @author kyc
 */
public interface ILoanManageBS extends IBaseService {

	/**
	 * 根据id查询交易记录bo
	 * @param id
	 * @return
	 * @throws BizException
	 */
	public LoanManageVO findInfoById(Integer id ) ;
	
	/**
	 * 分页查询list
	 * @return
	 * @throws BizException
	 */
	public List<LoanManageVO> findInfoPagesByVO (LoanSearchVO searchVO, int first, int pageSize) throws BizException;

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
	
	/**
	 * 查询还款中的借款列表
	 * @param requestPage
	 * @param pageSize
	 * @param status
	 * @return
	 */
	public Pager findLoaning(int requestPage,int pageSize,Integer custId);
	
	/**
	 * 查询已结清的借款列表
	 * @param requestPage
	 * @param pageSize
	 * @param status
	 * @return
	 */
	public Pager findLoaned(int requestPage,int pageSize,Integer custId);
	
	/**
	 * 借款信息查询
	 * @param requestPage
	 * @param pageSize
	 * @param custId
	 * @return
	 */
	public Pager findApplyLoan(int requestPage,int pageSize,Integer custId);

	/**
	 * 接口发布信息查询
	 * @param requestPage
	 * @param pageSize
	 * @param custId
	 * @return
	 */
	public Pager findApproveLoan(int requestPage,int pageSize,Integer custId);
	
	
	
	/**
	 * 查询借款意向
	 * @param requestPage
	 * @param pageSize
	 * @param custId
	 * @return
	 */
	public Pager findLoanInt(int requestPage,int pageSize,Integer custId);
	
	
	/**
	 * 查询申请中的借款意向笔数
	 * @param requestPage
	 * @param pageSize
	 * @param custId
	 * @return
	 */
	public int findLoanIntCount(Integer custId,String statusCd);
	
	/**
	 * 分页查询(投资列表)
	 * @author 535
	 * @return
	 * @throws BizException
	 */
	public List<LoanApproveVO> findListPagesByVO (LoanSearchVO searchVO, int first, int pageSize) throws BizException;
	
	
	/**
	 * 查询可代还借款列表
	 * @param requestPage
	 * @param pageSize
	 * @param status
	 * @return
	 */
	public Pager findLoaningDhByCustId(int requestPage,int pageSize,Integer custId);
}
