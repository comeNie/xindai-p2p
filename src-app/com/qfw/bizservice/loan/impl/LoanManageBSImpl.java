package com.qfw.bizservice.loan.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfw.bizservice.loan.ILoanManageBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.dao.loan.ILoanManageDAO;
import com.qfw.model.vo.loan.LoanApproveVO;
import com.qfw.model.vo.loan.LoanManageVO;
import com.qfw.model.vo.loan.LoanSearchVO;
import com.qfw.platform.model.json.Pager;

/**
 * 借款管理bs
 *
 * @author kyc
 */
@Service("loanManageBS")
public class LoanManageBSImpl extends BaseServiceImpl implements ILoanManageBS {

	@Autowired
	private ILoanManageDAO loanManageDAO;
	
	private Logger log = LogFactory.getInstance().getBusinessLogger();

	@Override
	public LoanManageVO findInfoById(Integer id) {
		return this.loanManageDAO.findInfoByID(id);
	}

	@Override
	public List<LoanManageVO> findInfoPagesByVO(LoanSearchVO searchVO,
			int first, int pageSize) throws BizException {
		try {
			 List<LoanManageVO> result= this.loanManageDAO.findInfoPagesByVO(searchVO, first, pageSize);
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
	public int getCountByVO(LoanSearchVO searchVO) {
		int num = 0;
		try {
			num = this.loanManageDAO.getCountByVO(searchVO);
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.error(e.getMessage());
			}
		}
		return num;
	}
	
	public Pager findLoaning(int requestPage,int pageSize,Integer custId){
		String sql = "SELECT L.ID,L.LOAN_NAME,L.LOAN_AMT,L.LOAN_RATE*100 as LOAN_RATE,L.SURPLUS_PERIOD,L.TOTAL_PERIOD,L.REPAY_TYPE_CD,L.LOAN_STATUS_CD,L.loan_approve_id FROM BIZ_LOAN L WHERE L.CUST_ID = ? AND L.LOAN_STATUS_CD IN ('0','2','3','4','5')";
		////System.out.println("custId1===="+sql+"custId=="+ custId);
		return findPagerByCust(sql,requestPage,pageSize,custId);
	}
	
	public Pager findLoaned(int requestPage,int pageSize,Integer custId){
		String sql = "SELECT L.LOAN_APPROVE_ID, \n" +
						"	L.LOAN_NAME, \n" +
						"	L.LOAN_AMT, \n" +
						"	L.LOAN_TERM, \n" +
						"	L.LOAN_RATE * 100 AS LOAN_RATE, \n" +
						"	L.REPAY_TYPE_CD, \n" +
						"	L.TTL_REPAYED_AMT, \n" +
						"	'结清' as LOAN_STATUS_CD \n" +
						"FROM BIZ_LOAN L " +
						"WHERE L.CUST_ID = ? " +
						"AND L.LOAN_STATUS_CD = '1' \n" +
						"union all " +
						"SELECT " +
						"	A.ID as LOAN_APPROVE_ID, \n" +
						"	A.LOAN_NAME, " +
						"	A.LOAN_AMT, " +
						"	A.LOAN_TERM, " +
						"	A.LOAN_RATE * 100 AS LOAN_RATE, \n" +
						"	A.REPAY_TYPE_CD, \n" +
						"	0.00 as TTL_REPAYED_AMT, \n" +
						"	'流标' as LOAN_STATUS_CD \n" +
						"FROM BIZ_LOAN_APPROVE A \n" +
						"WHERE A.APPROVE_STATUS_CD = '2' \n" +
						"AND A.CUST_ID = ?";
		////System.out.println("custId2===="+sql+"custId=="+ custId);
		return findPagerByCustII(sql,requestPage,pageSize,custId);
	}
	
	public Pager findApplyLoan(int requestPage,int pageSize,Integer custId){
		String sql = "SELECT A.ID, A.LOAN_NAME, A.PRODUCT_ID,A.LOAN_TYPE_CD,A.LOAN_TERM, A.REPAY_TYPE_CD,A.EXPECT_LOAN_RATE*100 as EXPECT_LOAN_RATE,A.APPLY_AMT, A.APPLY_DATE, A.APPLY_STATUS_CD FROM BIZ_LOAN_APPLY A WHERE A.APPLY_STATUS_CD  IN ('0','2') AND A.CUST_ID = ?";
		////System.out.println("custId3===="+sql+"custId=="+ custId);
		return findPagerByCust(sql,requestPage,pageSize,custId);
	}

	public Pager findApproveLoan(int requestPage,int pageSize,Integer custId){
		String sql = "SELECT A.ID,A.LOAN_NAME,A.LOAN_TERM,A.LOAN_AMT ,A.LOAN_RATE*100 AS LOAN_RATE,A.REPAY_TYPE_CD,DATE_FORMAT(A.TENDER_APPROVE_TIME,'%Y-%m-%d') AS TENDER_APPROVE_TIME,DATE_FORMAT(A.TENDER_DUE_TIME,'%Y-%m-%d') AS TENDER_DUE_TIME,ROUND(A.TENDER_USE_AMT*100/LOAN_AMT) AS PROGRESS,A.APPROVE_STATUS_CD FROM BIZ_LOAN_APPROVE A WHERE A.APPROVE_STATUS_CD  = 0 AND A.CUST_ID = ?";
		////System.out.println("custId4===="+sql +"custId=="+ custId);
		return findPagerByCust(sql,requestPage,pageSize,custId);
	}
	
	public Pager findPagerByCustII(String sql,int requestPage,int pageSize,Integer custId){
		Pager pager = new Pager();
		int count = getCommonQuery().findCountByWapSQL(sql,new Object[]{custId,custId});
		pager.setTotalCount(count);
		pager.setPageSize(pageSize);
		if(count > 0){
			List list = getCommonQuery().findBySQLByPages(sql, (requestPage-1)*pageSize, pageSize,new Object[]{custId,custId});
			pager.setList(list);
		}
		return pager;
	}
	
	
	public Pager findPagerByCust(String sql,int requestPage,int pageSize,Integer custId){
		Pager pager = new Pager();
		int count = getCommonQuery().findCountByWapSQL(sql,new Object[]{custId});
		pager.setTotalCount(count);
		pager.setPageSize(pageSize);
		if(count > 0){
			List list = getCommonQuery().findBySQLByPages(sql, (requestPage-1)*pageSize, pageSize,new Object[]{custId});
			pager.setList(list);
		}
		return pager;
	}

	@Override
	public List<LoanApproveVO> findListPagesByVO(LoanSearchVO searchVO,
			int first, int pageSize) throws BizException {
		try {
			 List<LoanApproveVO> result= this.loanManageDAO.findListPagesByVO(searchVO, first, pageSize);
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
	public int getApproveCountByVO(LoanSearchVO searchVO) {
		int num = 0;
		try {
			num = this.loanManageDAO.getApproveCountByVO(searchVO);
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.error(e.getMessage());
			}
		}
		return num;
	}

	@Override
	public Pager findLoanInt(int requestPage, int pageSize, Integer custId) {
		Pager pager = new Pager();
		String sql = "select a.cust_id, a.cust_name, a.apply_amt,a.loan_term, a.expect_loan_rate*100 as expect_loan_rate,ifnull(a.refuse_reason,'') as refuse_reason,a.PROCESS_STATUS_CD from biz_loan_intention a where a.cust_id = ?";
		////System.out.println("sql==="+sql);
		int count = getCommonQuery().findCountByWapSQL(sql,new Object[]{custId});
		pager.setTotalCount(count);
		pager.setPageSize(pageSize);
		if(count > 0){
			List list = getCommonQuery().findBySQLByPages(sql, (requestPage-1)*pageSize, pageSize,new Object[]{custId});
			pager.setList(list);
		}
		return pager;
		 
	}
	
	@Override
	public int findLoanIntCount(Integer custId,String statusCd) {
 
		String sql = "select * from biz_loan_intention a where a.cust_id = ? and a.process_status_cd = ?";
 
		int count = getCommonQuery().findCountByWapSQL(sql,new Object[]{custId,statusCd});
		 
		return count;
		 
	}
	
	
	@Override
	public Pager findLoaningDhByCustId(int requestPage, int pageSize,
			Integer custId) {
		String sql = "SELECT L.ID,L.LOAN_NAME,L.LOAN_AMT,L.LOAN_RATE*100 as LOAN_RATE,"
				+ "L.SURPLUS_PERIOD,L.TOTAL_PERIOD,L.REPAY_TYPE_CD,L.LOAN_STATUS_CD,L.loan_approve_id "
				+ "FROM BIZ_LOAN L WHERE L.TRUSTEE_CUST_ID = ? AND L.LOAN_STATUS_CD IN ('0','2','3','4','5')";
		//System.out.println("custid ="+custId +";sql =-=="+sql );
		return findPagerByCust(sql,requestPage,pageSize,custId);
	}

}
