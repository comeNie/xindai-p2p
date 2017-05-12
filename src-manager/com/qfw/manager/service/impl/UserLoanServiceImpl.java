package com.qfw.manager.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.exception.BizException;
import com.qfw.manager.service.IUserLoanService;
import com.qfw.platform.model.json.Pager;

@Service("userLoanService")
public class UserLoanServiceImpl extends BaseServiceImpl implements IUserLoanService {

	@Override
	public Pager findLoanRepayPlan(int requestPage, int pageSize, Integer loanApproveId) throws BizException {
		StringBuffer str = new StringBuffer();
		Pager pager = new Pager();
		str.append(" select a.id, a.repayplan_date,a.principal_amt,a.interest_amt, a.principa_interest_amt, b.repayed_Principal_Amt + b.repayed_Interest_Amt as repayed_amt, ");
		str.append(" b.repayed_penalty_amt, b.repay_status_cd ");
		str.append(" from biz_repay_plan_detail a , biz_repay_detail b");
		str.append("  where b.REPAY_PLAN_DETAIL_ID = a.ID");
		str.append("    and a.LOAN_ID = ?");
		pager = findPagerByLoanApproveId(str.toString(), requestPage, pageSize, loanApproveId);
		return pager;
	}
	@Override
	public List findLoanRepayPlan(Integer loanId) throws BizException {
		StringBuffer str = new StringBuffer();
		str.append(" select a.id, a.repayplan_date,a.principal_amt,a.interest_amt, a.principa_interest_amt, ");
		str.append(" a.LOAN_RATE*100 as LOAN_RATE");
		str.append(" from biz_repay_plan_detail a");
		str.append(" where 1=1");
		str.append(" and a.LOAN_ID = ").append(loanId);
		str.append(" order by a.repayplan_date");
		List list = getCommonQuery().findBySQL(str.toString());
		return list;
	}
	@Override
	public List findCreditorByLoanId(Integer loanId) throws BizException {
		StringBuffer str = new StringBuffer();
		str.append(" select cus.CUST_NAME as custName ,cre.CR_AMT as crAmt,ln.LOAN_TERM as loanTerm,ln.LOAN_RATE*100 as loanRate,");
		str.append(" date_format(ln.LOAN_DATE,'%Y-%m-%d') as loanDate,");//开始日期
		str.append(" date_format(ln.LOAN_DUE_DATE,'%Y-%m-%d') as loanDueDAte,");//到期日期
		str.append(" ln.TTL_PRINCIPAL_INTEREST_AMT*(cre.CR_AMT/cre.LOAN_AMT) as ttlPrincipalInterest");
		str.append(" from BIZ_CREDITOR_RIGHT cre,BIZ_CUSTOMER cus,BIZ_LOAN ln");
		str.append(" where cre.CUST_ID = cus.ID and ln.ID = cre.LOAN_ID");
		str.append(" and cre.LOAN_ID = ").append(loanId);
		List list = getCommonQuery().findBySQL(str.toString());
		return list;
	}

	private Pager findPagerByLoanApproveId(String sql,int requestPage,int pageSize,Integer loanApproveId){
		Pager pager = new Pager();
		int count = getCommonQuery().findCountByWapSQL(sql,new Object[]{loanApproveId});
		pager.setTotalCount(count);
		pager.setPageSize(pageSize);
		if(count > 0){
			List list = getCommonQuery().findBySQLByPages(sql, (requestPage-1)*pageSize, pageSize,new Object[]{loanApproveId});
			pager.setList(list);
		}
		return pager;
	}
	
}
