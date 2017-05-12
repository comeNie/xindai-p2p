package com.qfw.dao.creditor.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.qfw.common.dao.ICommonQuery;
import com.qfw.common.dao.impl.BaseDAOImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.DateUtils;
import com.qfw.common.util.StringUtils;
import com.qfw.dao.creditor.ICreditorCountManageDAO;
import com.qfw.model.vo.creditor.CreditorCountVO;
import com.qfw.model.vo.creditor.CreditorSearchVO;

/**
 * 投资统计
 *
 */
@Repository("creditorCountManageDAO")
public class CreditorCountManageDAOImpl extends BaseDAOImpl implements ICreditorCountManageDAO {
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	@Autowired
	private ICommonQuery commonQuery;
	
	@Override
	public List<CreditorCountVO> findInfoPagesByVO(CreditorSearchVO searchVO,
			int first, int pageSize) throws BizException {
		return this.commonQuery.findBySQLByPages(assembleSQL(searchVO), first, pageSize,CreditorCountVO.class);
	}
	
	@Override
	public int getCountByVO(CreditorSearchVO searchVO) {
		StringBuilder sql = new StringBuilder(" select count(1) from ( ");
		sql.append(assembleSQL(searchVO));
		sql.append(" ) t where 1=1");
		return this.commonQuery.findCountBySQL(sql, null);
	}
	
	private String assembleSQL(CreditorSearchVO searchVO){
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(count.CR_AMT) as INVEST_AMT,");
		sql.append(" cus.CUST_NAME,");
		sql.append(" DATE_FORMAT(count.date,'%Y-%m') as DATE ");
		sql.append(" from biz_creditor_count count,biz_customer cus");
		sql.append(" where 1=1 and count.CUST_ID = cus.ID");
		if(StringUtils.isNotEmpty(searchVO.getCustName())){
			sql.append(" and cus.CUST_NAME like '%"+searchVO.getCustName()+"%'");
		}
		
		if(searchVO.getDate() != null){
			String strDate = DateUtils.getDateString("yyyy-MM", searchVO.getDate());
			sql.append(" and DATE_FORMAT(count.date,'%Y-%m') = '"+strDate+"'");
		}
		
		sql.append(" group by count.CUST_ID,DATE_FORMAT(count.date,'%Y-%m')");
		
		return sql.toString();
	}
}
