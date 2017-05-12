package com.qfw.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfw.common.dao.ICommonQuery;
import com.qfw.common.exception.BizException;
import com.qfw.platform.model.loan.CreditorTranDetailVO;
import com.qfw.platform.service.ICreditorTranService;

@Service("creditorTranService")
public class CreditorTranServiceImpl implements ICreditorTranService {
	
	@Autowired
	private ICommonQuery commonQuery;

	@Override
	public CreditorTranDetailVO getCreditorTranDetail(Integer creditorTranId) throws BizException {
		CreditorTranDetailVO creditorTranDetailVO = new CreditorTranDetailVO();
		StringBuilder buffer = new StringBuilder(
				" b.loan_name," + 
				" b.LOAN_CUST_ID," + 
				" b.SETTLE_DATE," + 
				" b.REPAY_TYPE_CD," + 
				" b.loan_rate * 100  as loan_rate," + 
				" c.credit_rate," + 
				" b.SURPLUS_PERIOD," + 
				" a.TRAN_TTL_COUNT," + 
				" a.TRAN_TTL_AMT ," + 
				" a.TRAN_TTL_COUNT - a.TRAN_OUT_COUNT  as tran_in_count," + 
				" b.CR_AMT/b.TURNOVER_COUNT as cr_amt," + 
				" round((a.TRAN_OUT_AMT/a.TRAN_TTL_AMT)*100)  as completeness," + 
				" a.TAKE_AMT " +
				" from BIZ_CREDITOR_RIGHT_TRAN a," + 
				" BIZ_CREDITOR_RIGHT b," + 
				" BIZ_CUSTOMER c" + 
				" where a.CR_ID = b.id" + 
				" and b.LOAN_CUST_ID = b.id" + 
				" and a.id = '" + creditorTranId + "'"); 
		List list = commonQuery.findBySQLByPages(buffer.toString(), 1, 1, CreditorTranDetailVO.class);
		if (list != null && list.size() > 0) {
			creditorTranDetailVO = (CreditorTranDetailVO)list.get(0);
		}
		return creditorTranDetailVO;
	}

}
