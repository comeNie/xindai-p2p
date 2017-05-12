package com.qfw.bizservice.loan.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfw.bizservice.loan.ILoanApplyManageBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.vo.PageList;
import com.qfw.dao.loan.ILoanApplyManageDAO;
import com.qfw.model.vo.loan.loanApply.LoanApplyManageVO;
import com.qfw.model.vo.loan.loanApply.LoanApplySearchVO;

/**
 * 借款申请管理bs
 *
 * @author kyc
 */
@Service("loanApplyManageBS")
public class LoanApplyManageBSImpl extends BaseServiceImpl implements ILoanApplyManageBS {

	@Autowired
	private ILoanApplyManageDAO loanApplyManageDAO;
	
	private Logger log = LogFactory.getInstance().getBusinessLogger();

	@Override
	public LoanApplyManageVO findInfoById(Integer id) {
		return this.loanApplyManageDAO.findInfoByID(id);
	}

	@Override
	public List<LoanApplyManageVO> findInfoPagesByVO(LoanApplySearchVO searchVO,
			int first, int pageSize) throws BizException {
		try {
			 List<LoanApplyManageVO> result= this.loanApplyManageDAO.findInfoPagesByVO(searchVO, first, pageSize);
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

	/**
	 * 查询借款意向列表
	 * @param filter
	 * @param first
	 * @param pageSize
	 * @return
	 * @throws BizException
	 */
	public PageList findInfoPagesByMap(Map filter,int first, int pageSize) throws BizException{
		return loanApplyManageDAO.findInfoPagesByMap(filter, first, pageSize);
	}
	@Override
	public int getCountByVO(LoanApplySearchVO searchVO) {
		int num = 0;
		try {
			num = this.loanApplyManageDAO.getCountByVO(searchVO);
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.error(e.getMessage());
			}
		}
		return num;
	}
	
}
