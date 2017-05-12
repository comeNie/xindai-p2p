package com.qfw.dao.income;

import java.util.List;

import com.qfw.common.dao.IBaseDAO;
import com.qfw.common.exception.BizException;
import com.qfw.model.vo.income.IncomeDetailVO;
import com.qfw.model.vo.income.IncomeSearchVO;

public interface IIncomeDAO extends IBaseDAO {

	/**
	 * 根据收益表id查询收益信息
	 * 
	 * @param incomeId
	 * @return
	 */
	public IncomeDetailVO findInfoByID(Integer incomeId);

	/**
	 * 查询收益列表
	 * 
	 * @param searchVO
	 * @param first
	 * @param pageSize
	 * @return
	 * @throws BizException
	 */
	public List<IncomeDetailVO> findInfoPagesByVO(IncomeSearchVO searchVO,
			int first, int pageSize) throws BizException;

	/**
	 * 获取统计笔数
	 * 
	 * @param searchVO
	 * @return
	 */
	public int getCountByVO(IncomeSearchVO searchVO);
}
