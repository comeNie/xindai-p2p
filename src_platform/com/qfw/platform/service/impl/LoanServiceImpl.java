package com.qfw.platform.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfw.common.dao.ICommonQuery;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.manager.model.FinAccountVo;
import com.qfw.platform.model.loan.AuthDetailVO;
import com.qfw.platform.model.loan.CreditReportDetailVO;
import com.qfw.platform.model.loan.CreditorRightDetailVO;
import com.qfw.platform.model.loan.CustomerDetailVO;
import com.qfw.platform.model.loan.LoanDetailVO;
import com.qfw.platform.model.loan.LoanRepayInfoVo;
import com.qfw.platform.model.loan.RepayPlanDtlVO;
import com.qfw.platform.model.vo.CustAccountVO;
import com.qfw.platform.service.ILoanService;

@Service("loanService")
public class LoanServiceImpl implements ILoanService {
	
	@Autowired
	private ICommonQuery commonQuery;
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	
	@Override
	public LoanDetailVO getLoanDetail(Integer loanId) throws BizException {
		LoanDetailVO loanDetailVO = new LoanDetailVO();
		StringBuilder buffer = new StringBuilder(
				" select a.loan_name," + 
				" a.id as loan_Approve_Id," +
			    " a.product_name," +
				" a.loan_amt," +
				" a.LOAN_TERM," + 
				" a.LOAN_TYPE_CD," + 
				" a.REPAY_TYPE_CD," + 
				" a.TENDER_BAL_COUNT," + 
				" a.TENDER_BAL_AMT," + 
				" case when datediff(date_format(a.TENDER_DUE_TIME,'%Y%m%d'),date_format(sysdate(),'%Y%m%d')) < 0 " +
				" then 0 " +
				" else datediff(date_format(a.TENDER_DUE_TIME,'%Y%m%d'),date_format(sysdate(),'%Y%m%d'))  " +
				" end as remain_day," + 
				" a.loan_rate * 100 AS loan_rate," + 
				" a.APPROVE_STATUS_CD," + 
//				" ROUND((a.TENDER_USE_AMT/LOAN_AMT)*100) AS completeness," +
				" truncate((a.TENDER_USE_AMT/a.LOAN_AMT)*100,1) AS completeness," + 
				" b.credit_rate," + 
				" a.cust_id," + 
				" a.tender_limit_amt," + 
				" c.user_code," + 
				" a.remark," + 
				"a.APPROVE_STATUS_CD,"+
				"bl.LOAN_STATUS_CD,"+
				" a.ID FROM " + 
				" BIZ_CUSTOMER b," + 
				" SYS_USER c, " + 
				"biz_loan_approve a left join biz_loan bl"+
				" on bl.LOAN_APPROVE_ID = a.id"+
				" WHERE a.CUST_ID = b.id" + 
				"  AND b.USER_ID = c.USER_ID " +
				"  AND a.id = '" + loanId + "'");
		List list = commonQuery.findBySQLByPages(buffer.toString(), 0, 1, LoanDetailVO.class);
		if (list != null && list.size() > 0) {
			loanDetailVO = (LoanDetailVO)list.get(0);
		}
		return loanDetailVO;
	}

	@Override
	public CustomerDetailVO getCustomerDetail(Integer customerId) throws BizException {
		CustomerDetailVO customerDetailVO = new CustomerDetailVO();
		StringBuilder buffer = new StringBuilder(
				" SELECT a.AGE," + 
				" a.SEX," + 
				" a.EDUCATION_CD," + 
				" a.MARITAL_STATUS_CD," + 
				" b.JOB_COMPANY_NAME," + 
				" b.JOB_DEPT_NAME," + 
				" b.COMPANY_INDUSTRY_CD," + 
				" b.JOB_POSITION," + 
				" b.JOB_YEAR_CD," + 
				" b.company_type_cd," + 
				" b.JOB_INCOME_CD" + 
				" FROM BIZ_CUSTOMER a," + 
				" BIZ_JOB b," + 
				" sys_user c" + 
				" WHERE a.id = b.cust_id AND a.id = '" + customerId +"'");
		List list = commonQuery.findBySQLByPages(buffer.toString(), 0, 1, CustomerDetailVO.class);
		if (list != null && list.size() > 0) {
			customerDetailVO = (CustomerDetailVO)list.get(0);
		}
		return customerDetailVO;
	}

	@Override
	public CreditReportDetailVO getCreditReport(Integer customerId) throws BizException {
		CreditReportDetailVO creditReportDetailVO = new CreditReportDetailVO();
		StringBuilder buffer = new StringBuilder(
				" SELECT credit_amt," + 
				" remain_amt," + 
				" apply_loan_num," + 
				" 			approve_num," + 
				" 			Pay_off_num," + 
				" 			loan_tol_amt," + 
				" 			loan_bal," + 
				" 			overdue_amt," + 
				" 			overdue_num," + 
				" 			Ser_overdue_num" + 
				" FROM BIZ_CREDIT_Report" + 
				" WHERE cust_id = '" + customerId + "'");
		//log.info("buffer===="+buffer);
		List list = commonQuery.findBySQLByPages(buffer.toString(), 0, 1, CreditReportDetailVO.class);
		if (list != null && list.size() > 0) {
			creditReportDetailVO = (CreditReportDetailVO)list.get(0);
		}
		return creditReportDetailVO;
	}

	@Override
	public List getAuthDetail(Integer customerId) throws BizException {
		StringBuilder buffer = new StringBuilder(
				" select a.auth_item_cd," + 
				"  a.AUTH_STATUS_CD," + 
				"  a.sys_create_time" + 
				" from BIZ_AUTH a" + 
				" where a.auth_status_cd = '2' " + 
				"  and a.rel_Type_Cd= '1'" + 
				"  and a.rel_id = '" + customerId + "'");
		List list = commonQuery.findBySQLByPages(buffer.toString(), 0, 10000, AuthDetailVO.class);// TODO 是否考虑重新写一个方法来代替
		return list;
		
	}

	@Override
	public List getCreditorRight(Integer loanId,boolean isTran) throws BizException {
		StringBuilder buffer = new StringBuilder(
				" select a.cust_id," + 
			    " c.user_code," + 
				" b.cust_name," + 
			    " a.TURNOVER_COUNT as cr_Count," + 
				" a.CR_AMT," + 
				" a.TENDER_TYPE_CD," + 
				" a.SYS_CREATE_TIME" + 
				" from BIZ_CREDITOR_RIGHT a," + 
				" 	   BIZ_CUSTOMER b," + 
				" sys_user c" + 
				" where a.cust_id = b.id " + 
				" and b.user_id = c.user_id ");
		
		if(isTran){
			buffer.append(" and a.cr_type_cd = '1' ");
		}
	
		buffer.append(" and LOAN_APPROVE_ID= '"+ loanId +"'");
		
		List list = commonQuery.findBySQLByPages(buffer.toString(), 0, 10000, CreditorRightDetailVO.class);// TODO 是否考虑重新写一个方法来代替
		return list;
	}

	@Override
	public List getRepayPlanDetail(Integer loanId) throws BizException {
		StringBuilder buffer = new StringBuilder(
				" select a.PERIOD," + 
				"  a.REPAYPLAN_DATE," + 
				" a.PRINCIPAL_AMT," + 
				" a.INTEREST_AMT," + 
				"  b.REPAY_STATUS_CD" + 
				" from BIZ_REPAY_PLAN_DETAIL a" + 
				" left join BIZ_REPAY_DETAIL b " + 
				" on a.ID = b.repay_plan_detail_id" + 
				" WHERE a.LOAN_APPROVE_ID ='" + loanId + "'");
		
		List list = commonQuery.findBySQLByPages(buffer.toString(), 0, 10000, RepayPlanDtlVO.class);// TODO 是否考虑重新写一个方法来代替
		return list;
	}

	@Override
	public CustAccountVO getCustAccount(Integer cust_id) throws BizException {
		
		CustAccountVO custAccountVO = new CustAccountVO();
	
		StringBuilder buffer = new StringBuilder(
				" select a.cust_id," + 
				"  		 a.account_bal_amt + 0  as acc_Bal," + 
				"        a.usable_bal_amt + 0 as avai_Bal" + 
				" from biz_account a" + 
				" where a.cust_id ='" + cust_id + "'");
		
		List list = commonQuery.findBySQLByPages(buffer.toString(), 0, 1, CustAccountVO.class);
		if (list != null && list.size() > 0) {
			custAccountVO = (CustAccountVO)list.get(0);
		}
		return custAccountVO;
	}

	@Override
	public FinAccountVo getFinAccount(Integer customerId) throws BizException {
		
		FinAccountVo finAccountVo = new  FinAccountVo();
		
		StringBuilder buffer = new StringBuilder(
				"select cust_id, " + 
                "	    buy_Count, " + 
				"		cr_Amt," + 
				"	    TOTAL_PROFIT_AMT " + 
				"from (SELECT a.cust_id, " + 
				"			  count(*) as buy_Count, " + 
				"			  sum(a.cr_amt) as cr_amt, " +
				"			  sum(a.TOTAL_PROFIT_AMT) as TOTAL_PROFIT_AMT" + 
				"		FROM biz_creditor_right a " + 
				"		where a.cr_status_cd <> '5' " + 
				"		group by a.cust_id " + 
				"     ) aa " + 
				"where  aa.cust_id = '"+customerId+"' ");
		
		//log.info("buffer===="+buffer);
		List list = commonQuery.findBySQLByPages(buffer.toString(), 0, 1, FinAccountVo.class);
		if (list != null && list.size() > 0) {
			finAccountVo = (FinAccountVo)list.get(0);
		}
		return finAccountVo;
	}

	@Override
	public LoanRepayInfoVo getLoanRepayInfo(Integer loanApproveId)
			throws BizException {
		LoanRepayInfoVo loanRepayInfo = new LoanRepayInfoVo();
		StringBuilder buffer = new StringBuilder(
				"SELECT b.LOAN_APPROVE_ID," +
						"			 count(*) as rest_Periods," +
						"			 min(b.REPAY_PLAN_DATE) as next_repay_date," +
						"	     sum(b.UNPAID_INTEREST_AMT+b.UNPAID_PRINCIPAL_AMT) as sum_Repay_Amt " +
						" FROM biz_arrears_detail b" +
						" where b.LOAN_APPROVE_ID = ?" +
						"  and b.ARREARS_STATUS_CD <> '1'" +
						" group by b.LOAN_APPROVE_ID"
				);
		List list = commonQuery.findBySQLByPages(buffer.toString(), 0, 1, new Object[]{loanApproveId},LoanRepayInfoVo.class);
		if (list != null && list.size() > 0) {
			loanRepayInfo = (LoanRepayInfoVo)list.get(0);
		}
		return loanRepayInfo;
	}

	 

}
