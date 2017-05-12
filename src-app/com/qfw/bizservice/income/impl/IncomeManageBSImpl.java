package com.qfw.bizservice.income.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfw.bizservice.income.IIncomeManageBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.dao.income.IIncomeDAO;
import com.qfw.model.vo.income.IncomeDetailVO;
import com.qfw.model.vo.income.IncomeSearchVO;

/**
 * 收益管理bs
 *
 * @author kyc
 */
@Service("incomeManageBS")
public class IncomeManageBSImpl extends BaseServiceImpl implements IIncomeManageBS {

	@Autowired
	private IIncomeDAO incomeDAO;
	
	private Logger log = LogFactory.getInstance().getBusinessLogger();

	@Override
	public IncomeDetailVO findInfoById(Integer id) {
		return this.incomeDAO.findInfoByID(id);
	}

	@Override
	public List<IncomeDetailVO> findInfoPagesByVO(IncomeSearchVO searchVO,
			int first, int pageSize) throws BizException {
		try {
			 List<IncomeDetailVO> result= this.incomeDAO.findInfoPagesByVO(searchVO, first, pageSize);
			 if(null!=result && result.size()>0){
				 return result;
			 }
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.error(e.getMessage());
			}
			throw new BizException(e);
		}
		return null;
	}

	@Override
	public int getCountByVO(IncomeSearchVO searchVO) {
		int num = 0;
		try {
			num = this.incomeDAO.getCountByVO(searchVO);
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.error(e.getMessage());
			}
		}
		return num;
	}

}
