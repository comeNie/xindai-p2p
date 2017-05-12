package com.qfw.dao.loan.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.qfw.common.dao.ICommonQuery;
import com.qfw.common.dao.impl.BaseDAOImpl;
import com.qfw.common.exception.BizException;
import com.qfw.dao.loan.ILoanManageDAO;
import com.qfw.model.vo.loan.LoanApproveVO;
import com.qfw.model.vo.loan.LoanManageVO;
import com.qfw.model.vo.loan.LoanSearchVO;

/**
 * 鍊熸绠＄悊dao
 *
 * @author kyc
 */
@Repository("loanManageDAO")
public class LoanManageDAOImpl extends BaseDAOImpl implements ILoanManageDAO {
	
	@Autowired
	private ICommonQuery commonQuery;
	
	@Override
	public LoanManageVO findInfoByID(Integer loanApproveId) {
		StringBuffer sql = new StringBuffer();
		LoanSearchVO searchVO = new LoanSearchVO();
		searchVO.setLoanApproveId(loanApproveId);
		sql.append(this.assembleSQL(searchVO));
		 List<LoanManageVO> list = this.commonQuery.findBySQL(sql.toString());
		 if(null==list||list.size()<=0){
			 return null;
		 }else{
			 return list.get(0);
		 }
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<LoanManageVO> findInfoPagesByVO(LoanSearchVO searchVO,
			int first, int pageSize) throws BizException {
		StringBuffer sql = new StringBuffer();
		if("0".equals(searchVO.getLoanStatusCd())){
			sql.append(this.loanApplySQL(searchVO));
		}else{
			sql.append(this.assembleSQL(searchVO));
		}
		
		return this.commonQuery.findBySQLByPages(sql.toString(), first, pageSize,LoanManageVO.class);
	}

	@Override
	public int getCountByVO(LoanSearchVO searchVO) {
		/*StringBuilder sql = new StringBuilder(" select count(1) from ( ");
		sql.append(this.assembleSQL(searchVO));
		sql.append(" ) t where 1=1");*/
		if("0".equals(searchVO.getLoanStatusCd())){
			return commonQuery.findCountByWapSQL(this.loanApplySQL(searchVO), null);
		}else {
			return commonQuery.findCountByWapSQL(this.assembleSQL(searchVO), null);
		}
		
		//return this.commonQuery.findCountBySQL(sql, null);
	}
	
	
	
	/**
	 * 缁勮鏌ヨsql
	 * @param searchVO
	 * @return
	 */
	private String assembleSQL(LoanSearchVO searchVO){
		StringBuffer str = new StringBuffer();
		str.append(" select * from (");
		str.append(" select apply.id as LOAN_APPLY_ID,app.id as loan_approve_id,loan.id as loan_id , app.loan_name ,app.loan_amt, ");
		str.append("  app.loan_rate , app.repay_type_cd , app.cust_id, ");
		str.append(" ( case when loan.id is null then '0' else loan.TOTAL_PERIOD end ) total_period,");
		str.append(" ( case when loan.id is null then '0' else loan.SURPLUS_PERIOD end ) surplus_period, ");
		str.append(" ( case when loan.id is null then '0' else loan.REPAYED_PERIOD end ) repayed_period, ");
//		str.append(" ( case when apply.APPLY_STATUS_CD ='0' then '0' when loan.id is null and apply.APPLY_STATUS_CD ='1' then '1' ");
//		str.append("  when loan.id is not null and loan.LOAN_BAL_AMT>0  then '3' when loan.id is not null and loan.LOAN_BAL_AMT>0 then '4' "); 
//		str.append(" else '' end )  LOAN_STATUS_CD ");
		str.append(" app.APPROVE_STATUS_CD as LOAN_STATUS_CD");
		str.append(" ,sysuser.USER_NAME as  user_name ,sysuser.USER_CODE as USER_CODE, app.LOAN_TERM  as loan_term, cust.CUST_NAME as cust_name ,");
		str.append(" product.PRODUCT_NAME as product_name ,cust.credit_Rate as credit_rate , (case when loan.ID>0 then round((loan.LOAN_AMT / app.LOAN_AMT) *100,0) else 0 end ) as completeness");
		str.append(" from biz_loan_approve app   left join biz_loan loan on loan.loan_approve_id = app.id  ");
		str.append(" ,biz_loan_apply apply 	, biz_customer cust , sys_user sysuser ,biz_product product ");
		str.append(" where app.LOAN_APPLY_ID = apply.id and app.CUST_ID = cust.ID and cust.USER_ID = sysuser.USER_ID ");
		str.append(" and apply.PRODUCT_ID = product.ID");
		if(null != searchVO.getCustId()&& searchVO.getCustId().length()>0){
			str.append(" and app.CUST_ID = '").append(searchVO.getCustId()).append("'");
		}
		if(null != searchVO.getLoanApproveId()){
			str.append(" and app.id = ").append(searchVO.getLoanApproveId());
		}
		
		if(StringUtils.isNotEmpty(searchVO.getLoanType())){
			// TODO 鏍囩殑绫诲瀷
		}
		if (StringUtils.isNotEmpty(searchVO.getLoanTermFormat("app.LOAN_TERM"))) {
			str.append(" and ");
			str.append(searchVO.getLoanTermFormat("app.LOAN_TERM"));
 		}
		if(StringUtils.isNotEmpty(searchVO.getLoanRateFormat("app.loan_rate"))){
			str.append(" and ");
			str.append(searchVO.getLoanRateFormat("app.loan_rate"));
		}
		if(StringUtils.isNotEmpty(searchVO.getCreditRate())){
			str.append(" and cust.credit_Rate ='"+searchVO.getCreditRate()+"'");
		}
		if(StringUtils.isNotEmpty(searchVO.getLoanName())){
			str.append(" and app.loan_name  like '%"+searchVO.getLoanName()+"%' ");
		}
		if(StringUtils.isNotEmpty(searchVO.getUserCode())){
			str.append(" and sysuser.USER_CODE like '%"+searchVO.getUserCode()+"%'");
		}
		str.append(" order by app.id desc) temp where 1=1");
		if(null != searchVO.getLoanStatusCd() && searchVO.getLoanStatusCd().length() >0 ){
			str.append(" and temp.LOAN_STATUS_CD = '").append(searchVO.getLoanStatusCd()).append("'");
		}
		return str.toString();
	}
	
	/**
	 * 鍊熸鐢宠鍒楄〃缁勮鏌ヨsql
	 * @param searchVO
	 * @return
	 */
	private String loanApplySQL(LoanSearchVO searchVO){
		StringBuffer str = new StringBuffer();
		str.append(" select l.LOAN_TYPE_CD,l.LOAN_NAME,l.APPLY_AMT, ");
		str.append(" l.LOAN_TERM,l.TERM_UNIT_CD,l.LOAN_PURPOSE,l.TENDER_TERM,");
		str.append(" l.EXPECT_LOAN_RATE,l.REPAY_TYPE_CD,l.APPLY_DATE,");
		str.append(" l.APPLY_STATUS_CD,l.id as loan_apply_id from biz_loan_apply l ");
//		str.append(" ( case when apply.APPLY_STATUS_CD ='0' then '0' when loan.id is null and apply.APPLY_STATUS_CD ='1' then '1' ");
//		str.append("  when loan.id is not null and loan.LOAN_BAL_AMT>0  then '3' when loan.id is not null and loan.LOAN_BAL_AMT>0 then '4' "); 
//		str.append(" else '' end )  LOAN_STATUS_CD ");
//		str.append(" from biz_loan_approve app   left join biz_loan loan on loan.loan_approve_id = app.id  ");
//		str.append(" ,biz_loan_apply apply ");
//		str.append(" where app.LOAN_APPLY_ID = apply.id ");
//		if(null != searchVO.getCustId()&& searchVO.getCustId().length()>0){
//			str.append(" and app.CUST_ID = '").append(searchVO.getCustId()).append("'");
//		}
//		if(null != searchVO.getLoanId()){
//			str.append(" and app.id = ").append(searchVO.getLoanId());
//		}
//		str.append(") temp where 1=1");
//		if(null != searchVO.getLoanStatusCd() && searchVO.getLoanStatusCd().length() >0 ){
//			str.append(" and temp.LOAN_STATUS_CD = '").append(searchVO.getLoanStatusCd()).append("'");
//		}
		return str.toString();
	}

	@Override
	public List<LoanApproveVO> findListPagesByVO(LoanSearchVO searchVO,
			int first, int pageSize) throws BizException {
		StringBuffer sql = new StringBuffer();
		
		sql.append(this.invSQL(searchVO));
		
		return this.commonQuery.findBySQLByPages(sql.toString(), first, pageSize,LoanApproveVO.class);
	
	}
	
	
	/**
	 * 鎶曡祫鍒楄〃鏌ヨ
	 * @param searchVO
	 * @return
	 */
	private String invSQL(LoanSearchVO searchVO){
		StringBuffer str = new StringBuffer();
		str.append(" select a.loan_name,");
		str.append("b.credit_rate,");
		str.append("a.loan_rate * 100 as loan_rate,");
		str.append("a.REPAY_TYPE_CD,");
		str.append("a.TENDER_LIMIT_AMT,");
		str.append("a.loan_amt,");
		str.append("a.loan_Term,");
		str.append("a.term_unit_cd,");
		str.append("round((a.TENDER_USE_AMT/a.LOAN_AMT)*100) as completeness_round,");
//		str.append("round((a.TENDER_USE_AMT/LOAN_AMT)*100,1) as completeness,");
		str.append("truncate((a.TENDER_USE_AMT/a.LOAN_AMT)*100,1) as completeness,");
		str.append("a.loan_type_cd,");
		str.append("a.APPROVE_STATUS_CD,");
		str.append("bl.LOAN_STATUS_CD,");
		str.append("a.ID+0 as loan_Approve_Id");
		str.append(",a.REMARK");
		str.append(" from BIZ_CUSTOMER b,biz_loan_approve a");
		str.append(" left join biz_loan bl");
		str.append(" on bl.LOAN_APPROVE_ID = a.id");
		str.append(" where a.CUST_ID = b.id ");
		
		if(null != searchVO.getCustId()&& searchVO.getCustId().length()>0){
			str.append(" and a.CUST_ID != '").append(searchVO.getCustId()).append("'");
		}
		if(null != searchVO.getLoanApproveId()){
			str.append(" and a.id = ").append(searchVO.getLoanApproveId());
		}
		
		if(StringUtils.isNotEmpty(searchVO.getLoanType())){
			// TODO 鏍囩殑绫诲瀷
			str.append(" and a.loan_type_cd = ").append(searchVO.getLoanType());
			
		}
		if (StringUtils.isNotEmpty(searchVO.getLoanTermFormat("a.LOAN_TERM"))) {
			str.append(" and ");
			str.append(searchVO.getLoanTermFormat("a.LOAN_TERM"));
 		}
		
		if(StringUtils.isNotEmpty(searchVO.getLoanRateFormat("a.loan_rate"))){
			////System.out.println("searchVO.getLoanRateFormat(a.loan_rate)==="+searchVO.getLoanRateFormat("a.loan_rate"));
			str.append(" and ");
			str.append(searchVO.getLoanRateFormat("a.loan_rate"));
		}
		if(StringUtils.isNotEmpty(searchVO.getCreditRate())){
			str.append(" and ");
			str.append(" b.credit_Rate ='"+searchVO.getCreditRate()+"'");
		}
		
		if(null != searchVO.getLoanStatusCd() && searchVO.getLoanStatusCd().length() >0 ){
			str.append(" and a.APPROVE_STATUS_CD = '").append(searchVO.getLoanStatusCd()).append("'");
		}
		str.append(" order by a.APPROVE_STATUS_CD asc,a.id desc");
		////System.out.println("invSQL====================="+str);
		return str.toString();
		
	}
	@Override
	public int getApproveCountByVO(LoanSearchVO searchVO) {
		return commonQuery.findCountByWapSQL(this.invSQL(searchVO), null);
	}
	
	
}
