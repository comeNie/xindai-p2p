package com.qfw.dao.income.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.qfw.common.dao.ICommonQuery;
import com.qfw.common.dao.impl.BaseDAOImpl;
import com.qfw.common.exception.BizException;
import com.qfw.dao.income.IIncomeDAO;
import com.qfw.model.vo.income.IncomeDetailVO;
import com.qfw.model.vo.income.IncomeSearchVO;

@Repository("incomeDAO")
public class IncomeDAOImpl extends BaseDAOImpl implements IIncomeDAO {

	@Autowired
	private ICommonQuery commonQuery;
	
	@SuppressWarnings("unchecked")
	@Override
	public IncomeDetailVO findInfoByID(Integer incomeId) {
		IncomeSearchVO searchVO = new IncomeSearchVO();
		searchVO.setId(incomeId);
		List<IncomeDetailVO> list = this.commonQuery.findObjects(this.assembleSQL(searchVO), IncomeDetailVO.class);
		if(null!=list&&list.size()==1){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<IncomeDetailVO> findInfoPagesByVO(IncomeSearchVO searchVO,
			int first, int pageSize) throws BizException {
		return this.commonQuery.findBySQLByPages(this.assembleSQL(searchVO), first, pageSize,IncomeDetailVO.class);
	}
	
	@Override
	public int getCountByVO(IncomeSearchVO searchVO) {
		return commonQuery.findCountByWapSQL(this.assembleSQL(searchVO), null);
	}
	
	/**
	 * 组装查询sql
	 * @param searchVO
	 * @return
	 */
	private String assembleSQL(IncomeSearchVO searchVO){
		StringBuffer str = new StringBuffer();
		str.append(" SELECT income.ID, income.INCOME_REL_ID ,sysuser.USER_NAME,sysuser.user_code ,cust.CUST_NAME,");
		str.append(" cred.LOAN_NAME,cred.CR_AMT ,income.INCOME_DATE as income_date ,cred.TOTAL_PERIOD ,");
		str.append(" repayDetail.PERIOD as income_period,income.INCOME_AMT,income.INCOME_RATE");
		str.append(" FROM biz_income_detail income, biz_creditor_right cred, biz_customer cust, sys_user sysuser,");
		str.append(" biz_repay_detail repayDetail ");
		str.append(" where cust.USER_ID = sysuser.USER_ID and cust.ID = cred.CUST_ID AND cred.id = income.INCOME_REL_ID");
		str.append(" AND income.INCOME_TYPE_CD = '0' and income.INCOME_SOUR_REL_ID = repayDetail.ID ");
		str.append(" AND income.INCOME_SOUR_TYPE_CD = '0' AND cred.LOAN_ID = repayDetail.LOAN_ID ");
		if(null != searchVO.getIncomeRelId() && searchVO.getIncomeRelId() != 0 ){
			str.append(" and income.INCOME_REL_ID = '").append(searchVO.getIncomeRelId()).append("'");
		}
		if(null != searchVO.getId() && searchVO.getId() != 0 ){
			str.append(" and income.id = '").append(searchVO.getId()).append("'");
		}
		if(null != searchVO.getIncomeTypeCd() && searchVO.getIncomeTypeCd().length() >0 ){
			str.append(" and income.INCOME_TYPE_CD = '").append(searchVO.getIncomeTypeCd()).append("'");
		}
		if(null != searchVO.getUserCode() && searchVO.getUserCode().length() >0 ){
			str.append(" and sysuser.user_code like '%").append(searchVO.getUserCode()).append("%'");
		}
		if(null != searchVO.getLoanName() && searchVO.getLoanName().length() >0 ){
			str.append(" and cred.LOAN_NAME like '%").append(searchVO.getLoanName()).append("%'");
		}
		str.append(" order by income.id desc ");
		return str.toString();
	}
	
}
