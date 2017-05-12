package com.qfw.dao.postloan.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.qfw.batch.bizservice.util.DateUtil;
import com.qfw.common.dao.ICommonQuery;
import com.qfw.common.dao.impl.BaseDAOImpl;
import com.qfw.common.exception.BizException;
import com.qfw.dao.postloan.IPostLoanManageDAO;
import com.qfw.model.AppConst;
import com.qfw.model.vo.postLoan.PostLoanManageVO;
import com.qfw.model.vo.postLoan.PostLoanSearchVO;

/**
 * 贷后管理dao
 *
 * @author weiqs
 */
@Repository("postLoanManageDAO")
public class PostLoanManageDAOImpl extends BaseDAOImpl implements IPostLoanManageDAO {
	
	@Autowired
	private ICommonQuery commonQuery;
	
	@Override
	public PostLoanManageVO findInfoByID(Integer planId) {
		StringBuffer sql = new StringBuffer();
		PostLoanSearchVO searchVO = new PostLoanSearchVO();
		searchVO.setPlanId(planId);
		sql.append(this.assembleSQL(searchVO));
		 List<PostLoanManageVO> list = this.commonQuery.findObjects(sql.toString(),PostLoanManageVO.class);
		 if(null==list||list.size()<=0){
			 return null;
		 }else{
			 return list.get(0);
		 }
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PostLoanManageVO> findInfoPagesByVO(PostLoanSearchVO searchVO,
			int first, int pageSize) throws BizException {
		StringBuffer sql = new StringBuffer();
		sql.append(this.assembleSQL(searchVO));
		return this.commonQuery.findBySQLByPages(sql.toString(), first, pageSize,PostLoanManageVO.class);
	}

	@Override
	public int getCountByVO(PostLoanSearchVO searchVO) {
		return commonQuery.findCountByWapSQL(this.assembleSQL(searchVO), null);
	}
	
	/**
	 * 组装查询sql
	 * @param searchVO
	 * @return
	 */
	private String assembleSQL(PostLoanSearchVO searchVO){
		StringBuffer str = new StringBuffer();
		str.append(" select * from (");
		str.append(" SELECT plan.id as plan_id ,loan.id as loan_id, loan.LOAN_NAME AS loan_name, cust.ID AS cust_id, cust.CUST_NAME AS cust_name, loan.TOTAL_PERIOD AS total_period,");
		str.append(" plan.PERIOD AS period, plan.REPAYPLAN_DATE AS REPAYPLAN_DATE, plan.PRINCIPAL_AMT AS principal_amt, plan.PRINCIPA_INTEREST_AMT AS principa_interest_amt,");
		str.append(" ( CASE WHEN arrears.ARREARS_FLAG_CD = '2' THEN arrears.UNPAID_INTEREST_AMT ELSE 0 END ) AS deplay_interest_amt,");
		str.append(" ( CASE WHEN arrears.ARREARS_FLAG_CD = '3' THEN arrears.UNPAID_PRINCIPAL_AMT ELSE 0 END ) AS overdate_interest_amt,");
//		str.append(" "	管理费	客户经理	机构	",");
		str.append(" repayDetail.REPAY_STATUS_CD  AS loan_status_cd ");
		str.append(" FROM biz_customer cust, sys_user sysuser,biz_loan loan, biz_loan_approve app, biz_loan_apply apply, biz_repay_detail repayDetail,");
		str.append(" biz_repay_plan_detail plan LEFT JOIN biz_arrears_detail arrears ON plan.ID = arrears.REPAY_PLAN_DETAIL_ID");
		str.append(" AND plan.LOAN_ID = arrears.LOAN_ID LEFT JOIN biz_repay_detail repay ON repay.REPAY_PLAN_DETAIL_ID = plan.ID");
		str.append(" WHERE loan.CUST_ID = cust.ID AND cust.USER_ID = sysuser.USER_ID AND loan.LOAN_APPROVE_ID = app.ID");
		str.append(" AND app.LOAN_APPLY_ID = apply.ID AND loan.id = plan.LOAN_ID");
		str.append(" and repayDetail.REPAY_PLAN_DETAIL_ID = plan.id");
		if(null != searchVO.getPlanId()){
			str.append(" and plan.id = ").append(searchVO.getPlanId());
		}
//		if(null != searchVO.getCustId()&& searchVO.getCustId().length()>0){
//			str.append(" and sysuser.USER_ID = '").append(searchVO.getCustId()).append("'");
//		}
		if(null != searchVO.getSeachName() && searchVO.getSeachName().length()>0 ){
			str.append(" and cust.CUST_NAME like '%").append(searchVO.getSeachName()).append("%'");
		}
		if(null != searchVO.getLoanName() && searchVO.getLoanName().length()>0 ){
			str.append(" and loan.LOAN_NAME like '%").append(searchVO.getLoanName()).append("%'");
		}
		if(null != searchVO.getStartDate()  ){
			str.append(" and date_format(plan.REPAYPLAN_DATE,'%y%m%d') >= date_format('").append(DateUtil.getYmdhms(searchVO.getStartDate())).append("','%y%m%d')");
		}
		if(null != searchVO.getEndDate()  ){
			str.append(" and date_format(plan.REPAYPLAN_DATE,'%y%m%d') <= date_format('").append(DateUtil.getYmdhms(searchVO.getEndDate())).append("','%y%m%d')");
		}
		
		if(null == searchVO.getLoanStatusCd()){
			
		}else if("-1".equals(searchVO.getLoanStatusCd())){
			// 30天应还
			str.append(" and loan.LOAN_STATUS_CD != '1'");// 未结清
			str.append(" and date_format(plan.REPAYPLAN_DATE,'%y%m%d') between  date_format(now(),'%y%m%d') "
					+ " and date_format(date_add(now(), interval 30 day),'%y%m%d')");
		}else if( "3".equals(searchVO.getLoanStatusCd())){
			// 逾期贷款，根据还款计划还款日期判断
			str.append(" and date_format(plan.REPAYPLAN_DATE,'%y%m%d') < date_format(now(),'%y%m%d')");
			str.append(" and repayDetail.REPAY_STATUS_CD = '").append(AppConst.REPAY_STATUS_CD_WH).append("'");
		}else if( "4".equals(searchVO.getLoanStatusCd())){
			//平台垫付
			str.append(" and repayDetail.REPAY_STATUS_CD = '").append(AppConst.REPAY_STATUS_CD_PTDF).append("'");
		}else if( "1".equals(searchVO.getLoanStatusCd())){
			//已还贷款
			str.append(" and repayDetail.REPAY_STATUS_CD = '").append(AppConst.REPAY_STATUS_CD_YH).append("'");
		}else{
			str.append(" and loan.LOAN_STATUS_CD = '").append(searchVO.getLoanStatusCd()).append("'");
		}
		
		str.append(" order by plan.REPAYPLAN_DATE,plan.id) temp where 1=1");
		return str.toString();
		//select * from ( SELECT plan.id as plan_id ,loan.id as loan_id, loan.LOAN_NAME AS loan_name, cust.ID AS cust_id, cust.CUST_NAME AS cust_name, loan.TOTAL_PERIOD AS total_period, plan.PERIOD AS period, plan.REPAYPLAN_DATE AS REPAYPLAN_DATE, plan.PRINCIPAL_AMT AS principal_amt, plan.PRINCIPA_INTEREST_AMT AS principa_interest_amt, ( CASE WHEN arrears.ARREARS_FLAG_CD = '2' THEN arrears.UNPAID_INTEREST_AMT ELSE 0 END ) AS deplay_interest_amt, ( CASE WHEN arrears.ARREARS_FLAG_CD = '3' THEN arrears.UNPAID_PRINCIPAL_AMT ELSE 0 END ) AS overdate_interest_amt, repayDetail.REPAY_STATUS_CD  AS loan_status_cd  FROM biz_customer cust, sys_user sysuser,biz_loan loan, biz_loan_approve app, biz_loan_apply apply, biz_repay_detail repayDetail, biz_repay_plan_detail plan LEFT JOIN biz_arrears_detail arrears ON plan.ID = arrears.REPAY_PLAN_DETAIL_ID AND plan.LOAN_ID = arrears.LOAN_ID LEFT JOIN biz_repay_detail repay ON repay.REPAY_PLAN_DETAIL_ID = plan.ID WHERE loan.CUST_ID = cust.ID AND cust.USER_ID = sysuser.USER_ID AND loan.LOAN_APPROVE_ID = app.ID AND app.LOAN_APPLY_ID = apply.ID AND loan.id = plan.LOAN_ID and repayDetail.REPAY_PLAN_DETAIL_ID = plan.id and sysuser.USER_ID = '64' and repayDetail.REPAY_STATUS_CD = '1' order by plan.REPAYPLAN_DATE,plan.id) temp where 1=1
	}

}
