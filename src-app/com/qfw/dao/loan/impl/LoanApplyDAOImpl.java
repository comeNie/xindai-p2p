package com.qfw.dao.loan.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.qfw.common.dao.impl.BaseDAOImpl;
import com.qfw.common.log.LogFactory;
import com.qfw.dao.loan.ILoanApplyDAO;
import com.qfw.model.bo.BizLoanApplyBO;
import com.qfw.model.vo.loan.LoanApplyVO;

/**
 * 借款申请dao
 *
 * @author kyc
 */
@Repository("loanApplyDAO")
public class LoanApplyDAOImpl extends BaseDAOImpl implements ILoanApplyDAO {
	
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	
	@Override
	public List<BizLoanApplyBO> findByParams(LoanApplyVO vo) {
		List<BizLoanApplyBO> list = null;
		try {
			StringBuffer str = new StringBuffer();
			str.append(" from BizLoanApplyBO bo where 1=1");
			if(null != vo.getId() && vo.getId() !=0 ){
				str.append(" and bo.id = '").append(vo.getId()).append("'");
			}
			if(null != vo.getWorkItemId() && vo.getWorkItemId().length() >0 ){
				str.append(" and bo.workItemId = '").append(vo.getWorkItemId()).append("'");
			}
			if(null != vo.getApplyStatusCd() && vo.getApplyStatusCd().length() >0 ){
				str.append(" and bo.applyStatusCd = '").append(vo.getApplyStatusCd()).append("'");
			}
			if(null != vo.getCustId() && vo.getCustId().length() >0 ){
				str.append(" and bo.custId = '").append(vo.getCustId()).append("'");
			}
			list =  super.findByHQL(str.toString());
		} catch (Exception e) {
			log.error("提现申请查询异常："+e);
		}
		return list;
	}
}
