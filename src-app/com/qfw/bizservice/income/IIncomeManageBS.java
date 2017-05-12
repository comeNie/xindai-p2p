package com.qfw.bizservice.income;

import java.util.List;

import com.qfw.common.exception.BizException;
import com.qfw.model.vo.income.IncomeDetailVO;
import com.qfw.model.vo.income.IncomeSearchVO;

/**
 * 收益服务
 */
public interface IIncomeManageBS {

	/**
	 * 根据id查询收益vo
	 * @param id
	 * @return
	 * @throws BizException
	 */
	public IncomeDetailVO findInfoById(Integer id ) ;
	
	/**
	 * 分页查询list
	 * @return
	 * @throws BizException
	 */
	public List<IncomeDetailVO> findInfoPagesByVO (IncomeSearchVO searchVO, int first, int pageSize) throws BizException;

	/**
	 * 获取数据总数
	 * @param searchVO
	 * @return
	 * @throws BizException
	 */
	public int getCountByVO(IncomeSearchVO searchVO);
	
}
