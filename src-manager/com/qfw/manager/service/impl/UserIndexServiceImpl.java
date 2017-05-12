package com.qfw.manager.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfw.common.dao.ICommonQuery;
import com.qfw.common.exception.BizException;
import com.qfw.manager.model.UserIndexVO;
import com.qfw.manager.service.IUserIndexService;

@Service("userIndexService")
public class UserIndexServiceImpl implements IUserIndexService {

	@Autowired
	private ICommonQuery commonQuery;
	
	public UserIndexVO getUserIndexVO(Integer custId) throws BizException {
		
		UserIndexVO userIndexVO = new UserIndexVO();
		StringBuilder buffer = new StringBuilder(
				" select a1.cust_id," + 
				" a1.ACCOUNT_BAL_AMT," + 
				" a1.PM_AMT," + 
				" a1.FREEZE_BAL_AMT," + 
				" a1.USABLE_BAL_AMT," + 
				" a2.LOAN_BAL_AMT," + 
				" a3.UNRETRIEVE_AMT" + 
				" from BIZ_ACCOUNT a1" + 
				" left join (SELECT A.CUST_ID," + 
				" SUM(A.LOAN_BAL_AMT) AS LOAN_BAL_AMT" + 
				" FROM BIZ_LOAN A" + 
				" GROUP BY A.CUST_ID" + 
				" ) a2 on a1.cust_id = a2.cust_id" + 
				" left join " + 
				" (SELECT b.CUST_ID," + 
				" SUM(b.UNRETRIEVE_AMT) AS  UNRETRIEVE_AMT " + 
				" FROM BIZ_CREDITOR_RIGHT b" + 
				" GROUP BY b.CUST_ID" + 
				" ) a3 on a1.cust_id = a3.cust_id" +
				" where a1.cust_id = '" + custId + "'");
		List list = commonQuery.findBySQLByPages(buffer.toString(), 0, 1, UserIndexVO.class);
		if (list != null && list.size() > 0) {
			userIndexVO = (UserIndexVO)list.get(0);
			if (userIndexVO.getUnretrieveAmt() == null) {
				userIndexVO.setUnretrieveAmt(new BigDecimal(0));
			}
			if (userIndexVO.getLoanBalAmt() == null) {
				userIndexVO.setLoanBalAmt(new BigDecimal(0));
			}
			if (userIndexVO.getAccountBalAmt() == null) {
				userIndexVO.setAccountBalAmt(new BigDecimal(0));
			}
			if (userIndexVO.getFreezeBalAmt() == null) {
				userIndexVO.setFreezeBalAmt(new BigDecimal(0));
			}
			if (userIndexVO.getUsableBalAmt() == null) {
				userIndexVO.setUsableBalAmt(new BigDecimal(0));
			}
			//pm币
			if (userIndexVO.getPmAmt() == null) {
				userIndexVO.setPmAmt(new BigDecimal(0));
			}
			userIndexVO.setAccoutPureAmt(userIndexVO.getUnretrieveAmt().subtract(userIndexVO.getLoanBalAmt()).add(userIndexVO.getAccountBalAmt()));// 账户净资产 = 理财资产 - 借款负债 + 账户余额
		}
		return userIndexVO;
	}
	
}
