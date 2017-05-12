package com.qfw.dao.loan.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.qfw.common.dao.ICommonQuery;
import com.qfw.common.dao.impl.BaseDAOImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.vo.PageList;
import com.qfw.common.util.StringUtils;
import com.qfw.dao.loan.ILoanApplyManageDAO;
import com.qfw.model.vo.loan.loanApply.LoanApplyManageVO;
import com.qfw.model.vo.loan.loanApply.LoanApplySearchVO;

/**
 * 借款申请管理dao
 *
 * @author kyc
 */
@SuppressWarnings("unchecked")
@Repository("loanApplyManageDAO")
public class LoanApplyManageDAOImpl extends BaseDAOImpl implements ILoanApplyManageDAO {
	
	@Autowired
	private ICommonQuery commonQuery;
	
	@Override
	public LoanApplyManageVO findInfoByID(Integer loanApplyId) {
		StringBuffer sql = new StringBuffer();
		LoanApplySearchVO searchVO = new LoanApplySearchVO();
		searchVO.setLoanApplyId(loanApplyId);
		sql.append(this.assembleSQL(searchVO));
		 List<LoanApplyManageVO> list = this.commonQuery.findBySQL(sql.toString());
		 if(null==list||list.size()<=0){
			 return null;
		 }else{
			 return list.get(0);
		 }
	}
	
	@Override
	public List<LoanApplyManageVO> findInfoPagesByVO(LoanApplySearchVO searchVO,
			int first, int pageSize) throws BizException {
		StringBuffer sql = new StringBuffer();
		sql.append(this.assembleSQL(searchVO));
		return this.commonQuery.findBySQLByPages(sql.toString(), first, pageSize,LoanApplyManageVO.class);
	}
	
	public PageList findInfoPagesByMap(Map filter,int first, int pageSize) throws BizException {
		/*StringBuffer sql = new StringBuffer();
		sql.append(this.assembleSQL(searchVO));*/
		try{
			PageList pageList = new PageList();
			StringBuilder sql = new StringBuilder("SELECT LI.*,P.PRODUCT_NAME FROM BIZ_LOAN_INTENTION LI LEFT JOIN BIZ_PRODUCT P ON LI.PRODUCT_ID = P.ID WHERE 1=1 ");
			if(StringUtils.isNotEmpty((String)filter.get("custName"))){
				sql.append(" AND LI.CUST_NAME LIKE '%").append(filter.get("custName")).append("%'");
			}
			if(StringUtils.isNotEmpty((String)filter.get("processStatus"))){
				sql.append(" AND LI.PROCESS_STATUS_CD ='").append(filter.get("processStatus")).append("'");
			}
			int count = commonQuery.findCountByWapSQL(sql.toString());
			pageList.setCount(count);
			if(count > 0){
				sql.append(" ORDER BY ID DESC");
				List data = this.commonQuery.findBySQLByPages(sql.toString(), first, pageSize);
				pageList.setData(data);
			}
			return pageList;
		}catch(Exception e){
			throw new BizException("查询融资列表错误");
		}
		
		
	}

	@Override
	public int getCountByVO(LoanApplySearchVO searchVO) {
		return commonQuery.findCountByWapSQL(this.assembleSQL(searchVO), null);
	}
	
	/**
	 * 借款申请列表组装查询sql
	 * @param searchVO
	 * @return
	 */
	private String assembleSQL(LoanApplySearchVO searchVO){
		StringBuffer str = new StringBuffer();
		str.append(" select * from (");
		str.append(" SELECT apply.id as loan_apply_id, apply.LOAN_NAME as loan_name , sysuser.USER_NAME as  user_name ,sysuser.USER_CODE as USER_CODE, ");
		str.append(" cust.CUST_NAME as cust_name , product.PRODUCT_NAME as product_name ,cust.CREDIT_RATE as credit_rate, ");
		str.append(" apply.APPLY_AMT as apply_amt ,apply.EXPECT_LOAN_RATE as expect_loan_rate,apply.LOAN_TERM as  loan_term ,");
		str.append(" date_add(apply.APPLY_DATE, interval apply.TENDER_TERM day) as tender_data, apply.APPLY_DATE as apply_date,");
		str.append(" apply.REPAY_TYPE_CD as repay_type_cd,apply.APPLY_STATUS_CD as  apply_status_cd");
		str.append(" FROM biz_loan_apply apply, biz_product product, biz_customer cust, sys_user sysuser");
		str.append(" where apply.CUST_ID = cust.ID and apply.PRODUCT_ID = product.ID and cust.USER_ID = sysuser.USER_ID");
		if(null != searchVO.getCustId()&& searchVO.getCustId().length()>0){
			str.append(" and apply.CUST_ID = '").append(searchVO.getCustId()).append("'");
		}
		if(null != searchVO.getLoanApplyId()){
			str.append(" and apply.id = '").append(searchVO.getLoanApplyId()).append("'");
		}
		if(null != searchVO.getLoanStatusCd()&&searchVO.getLoanStatusCd().length()>0){
			str.append(" and apply.APPLY_STATUS_CD = '").append(searchVO.getLoanStatusCd()).append("'");
		}
		if(null != searchVO.getLoanName()&&searchVO.getLoanName().length()>0){
			str.append(" and apply.LOAN_NAME like '%").append(searchVO.getLoanName()).append("%'");
		}
		if(null != searchVO.getUserCode()&&searchVO.getUserCode().length()>0){
			str.append(" and sysuser.USER_CODE like '%").append(searchVO.getUserCode()).append("%'");
		}
		str.append(" order by apply.id desc ) temp where 1=1");
		return str.toString();
	}

}
