package com.qfw.manager.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.exception.BizException;
import com.qfw.manager.model.MyCreditorTranVO;
import com.qfw.manager.service.IUserCreditorServcie;
import com.qfw.platform.model.json.Pager;

@Service("userCreditorServcie")
public class UserCreditorServcieImpl extends BaseServiceImpl implements IUserCreditorServcie {

	@Override
	public Pager findUserCreditorRecovering(int requestPage, int pageSize, Integer custId) throws BizException {
		StringBuffer str = new StringBuffer();
		Pager pager = new Pager();
		str.append(" select cre.id as creditor_id ,cre.TOTAL_PROFIT_AMT as tot_profit_amt,app.loan_name,cre.loan_amt,cre.repay_type_cd , cre.loan_approve_id,");
		str.append(" cre.cr_amt, cre.loan_rate*100 as rate,arr.next_gather_date,");
		str.append(" cre.surplus_period as surplus_period , cre.cr_status_cd ,cre.unretrieve_amt,");
		str.append(" cre.total_period,app.loan_term");
		str.append(" from BIZ_CREDITOR_RIGHT cre ");
		str.append(" left join biz_loan_approve app on cre.loan_approve_id = app.id ");
		str.append(" left join (select a.LOAN_APPROVE_ID,min(a.REPAY_PLAN_DATE) as next_gather_date from biz_arrears_detail a ");
		str.append(" where a.REPAY_DATE is null group by a.LOAN_APPROVE_ID) arr on cre.loan_approve_id = arr.LOAN_APPROVE_ID ");
		str.append(" where cre.cust_id = ? ");
		str.append("   and cre.CR_STATUS_CD in ('2','3') order by cre.id desc");
		pager = findPagerByCust(str.toString(), requestPage, pageSize, custId);
		return pager;
	}

	@Override
	public Pager findUserCreditorRecovered(int requestPage, int pageSize, Integer custId) throws BizException {
		StringBuffer str = new StringBuffer();
		str.append(" select cre.id as creditor_id , cre.TOTAL_PROFIT_AMT as tot_profit_amt,app.loan_name,cre.loan_amt,cre.repay_type_cd , cre.loan_approve_id,");
		str.append(" cre.cr_amt, cre.LOAN_RATE*100 as  rate,cre.settle_date as settle_date,");
		str.append(" cre.SURPLUS_PERIOD as surplus_period , cre.cr_status_cd ,cre.unretrieve_amt,");
		str.append(" cre.TOTAL_PERIOD as total_period,app.loan_term");
		str.append(" from BIZ_CREDITOR_RIGHT cre ");
		str.append(" left join biz_loan_approve app on cre.loan_approve_id = app.id ");
		str.append(" where cre.cust_id = ? ");
		str.append("   and cre.CR_STATUS_CD in ('4','5') order by cre.id desc");
		return findPagerByCust(str.toString(), requestPage, pageSize, custId);
	}
	
	public int countCreditor(Integer custId) throws BizException{
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT count(*)");
		sql.append(" FROM BIZ_CREDITOR_RIGHT");
		sql.append(" WHERE 1 = 1");
//		sql.append(" AND CR_STATUS_CD in ('2','3','4') AND CUST_ID = ").append(custId);
		sql.append(" AND CUST_ID = ").append(custId);
		return this.commonQuery.findCountBySQL(sql, null);
	}
	 
	@Override
	public Pager findUserCreditorTendering(int requestPage, int pageSize, Integer custId) throws BizException {
		StringBuffer str = new StringBuffer();
		str.append(" select cre.id as creditor_id , app.loan_name,cre.loan_amt,cre.repay_type_cd , cre.loan_approve_id,");
		str.append(" cre.cr_amt, cre.LOAN_RATE*100 as  rate,round(app.TENDER_USE_AMT*100/app.LOAN_AMT) as progress,");
		str.append(" cre.SURPLUS_PERIOD as surplus_period , cre.cr_status_cd , cre.unretrieve_amt,");
		str.append(" cre.TOTAL_PERIOD as total_period ,app.loan_term");
		str.append(" from BIZ_CREDITOR_RIGHT cre ");
		str.append(" left join biz_loan_approve app on cre.loan_approve_id = app.id ");
		str.append(" where cre.cust_id = ? ");
		str.append("   and cre.CR_STATUS_CD = '1' order by cre.id desc ");
		return findPagerByCust(str.toString(), requestPage, pageSize, custId);
	}

	@Override
	public Pager findUserCreditorTran(int requestPage, int pageSize, Integer custId) throws BizException {
		StringBuffer str = new StringBuffer();
		str.append(" select crt.id as creditor_id,");
		str.append(" cr.loan_name,");
		str.append(" crt.LOAN_APPROVE_ID,");
		str.append(" cr.cr_amt,");
		str.append(" cr.unretrieve_amt,");
		str.append(" crt.tran_ttl_amt,");
		str.append(" cr.loan_rate * 100 as rate,");
		str.append(" round((crt.tran_out_amt/crt.tran_ttl_amt)* 100)  AS completeness,");
		str.append(" crt.crt_status_cd");
		str.append(" from biz_creditor_right_tran crt");
		str.append(" left join biz_creditor_right cr on crt.cr_id = cr.id");
		str.append("  where cr.cust_id = ? order by crt.id desc ");
		return findPagerByCust(str.toString(), requestPage, pageSize, custId);
	}
	
	public MyCreditorTranVO getMyCreditorTranVO(Integer creditorId) throws BizException {
		MyCreditorTranVO myCreditorTranVO = new MyCreditorTranVO();
		StringBuffer str = new StringBuffer();
		str.append(" select cr.id,");
		str.append(" cr.loan_name,");// 标题
		str.append(" cr.loan_amt,");// 标的总额
		str.append(" cr.cr_amt,");// 债权金额
		str.append(" cr.loan_rate*100 as loan_rate,");// 年利率
		str.append(" cr.unretrieve_amt,");// 待收本金
		str.append(" cr.CR_STATUS_CD,");// 状态
		str.append(" cr.SURPLUS_PERIOD,");//剩余期数
		str.append(" date_format(bl.LOAN_DATE,'%Y-%m-%d') as LOAN_DATE,");//开始日期
		str.append(" date_format(bl.LOAN_DUE_DATE,'%Y-%m-%d') as LOAN_DUE_DATE,");//到期日期
		str.append(" datediff(date_format(bl.LOAN_DUE_DATE,'%Y%m%d'),date_format(sysdate(),'%Y%m%d')) as remain_Day,");//剩余天数
		str.append(" cr.turnover_count");// 成交份数
		str.append(" from biz_creditor_right cr,");
		str.append(" BIZ_LOAN bl");
		str.append(" where cr.LOAN_ID = bl.ID");
		str.append(" and cr.id = " + creditorId);
		str.append(" order by cr.id desc");
		
		List list = this.getCommonQuery().findObjects(str.toString(), MyCreditorTranVO.class);
		if (list != null && list.size() > 0) {
			myCreditorTranVO = (MyCreditorTranVO)list.get(0);
		}
		return myCreditorTranVO;
	}
	public BigDecimal calculateMyCreditor(Integer custId)throws BizException {
		BigDecimal amt = BigDecimal.ZERO;
		String sql = "SELECT SUM(CR_AMT) AMT FROM BIZ_CREDITOR_RIGHT "
				+ " WHERE 1=1 AND CR_STATUS_CD IN ('2','3','4') AND CUST_ID = ?" ;
		List<?> list = this.commonQuery.findBySQL(sql,new Object[]{custId});
		if(list!=null && list.size()>0){
			amt =  (BigDecimal)((Map<?,?>)list.get(0)).get("AMT");
		}
		return amt;
	}
	
	private Pager findPagerByCust(String sql,int requestPage,int pageSize,Integer custId){
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
	public Pager findObjects(Integer creditorId) {
		StringBuffer str = new StringBuffer();
		str.append(" select crt.id as creditor_id,");
		str.append(" crt.tran_ttl_amt,");
		str.append(" round((crt.tran_out_amt/crt.tran_ttl_amt)* 100)  AS completeness,");
		str.append(" crt.crt_status_cd");
		str.append(" from biz_creditor_right_tran crt");
		str.append("  where crt.cr_id = ?");
		str.append("    and crt.CRT_STATUS_CD in ('0','1','2') ");
		
		Pager pager = new Pager();
		
		int count = getCommonQuery().findCountByWapSQL(str.toString(),new Object[]{creditorId});
		pager.setTotalCount(count);
		pager.setPageSize(10);
		if(count > 0){
			List list = getCommonQuery().findBySQLByPages(str.toString(), 0, 10,new Object[]{creditorId});
			pager.setList(list);
		}
		return pager;
	}

}
