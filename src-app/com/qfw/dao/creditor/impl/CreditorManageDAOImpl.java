package com.qfw.dao.creditor.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.qfw.common.dao.ICommonQuery;
import com.qfw.common.dao.impl.BaseDAOImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.dao.creditor.ICreditorManageDAO;
import com.qfw.model.AppConst;
import com.qfw.model.vo.creditor.CreditorManageVO;
import com.qfw.model.vo.creditor.CreditorSearchVO;
import com.qfw.platform.model.transfer.TransferSearchVo;
import com.qfw.platform.model.transfer.TransferVo;

/**
 * 债权管理dao
 *
 * @author kyc
 */
@Repository("creditorManageDAO")
public class CreditorManageDAOImpl extends BaseDAOImpl implements ICreditorManageDAO {
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	@Autowired
	private ICommonQuery commonQuery;
	
	@Override
	public List<CreditorManageVO> findInfoList(CreditorSearchVO searchVO){
		StringBuffer sql = new StringBuffer();
		if("crTran".equals(searchVO.getCreditorRightTran())){
			sql.append(this.crTranListSQL(searchVO));
		}else {
			sql.append(this.assembleSQL(searchVO));
		}
		return this.commonQuery.findObjects(sql.toString(), CreditorManageVO.class);
	}
	
	@Override
	public CreditorManageVO findInfoByID(Integer id) {
		StringBuffer sql = new StringBuffer();
		CreditorSearchVO searchVO = new CreditorSearchVO();
		searchVO.setCreditorId(id);
		sql.append(this.assembleSQL(searchVO));
		 List<CreditorManageVO> list = this.commonQuery.findBySQL(sql.toString());
		 if(null==list||list.size()<=0){
			 return null;
		 }else{
			 return list.get(0);
		 }
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CreditorManageVO> findInfoPagesByVO(CreditorSearchVO searchVO,
			int first, int pageSize) throws BizException {
		StringBuffer sql = new StringBuffer();
		if("crTran".equals(searchVO.getCreditorRightTran())){
			sql.append(this.crTranListSQL(searchVO));
		}else {
			sql.append(this.assembleSQL(searchVO));
		}
		
		return this.commonQuery.findBySQLByPages(sql.toString(), first, pageSize,CreditorManageVO.class);
	}

	@Override
	public int getCountByVO(CreditorSearchVO searchVO) {
		StringBuilder sql = new StringBuilder(" select count(1) from ( ");
		if("crTran".equals(searchVO.getCreditorRightTran())){
			sql.append(this.crTranListSQL(searchVO));
		}else {
			sql.append(this.assembleSQL(searchVO));
		}
		sql.append(" ) t where 1=1");
		return this.commonQuery.findCountBySQL(sql, null);
	}
	
	/**
	 * 组装查询sql
	 * @param searchVO
	 * @return
	 */
	private String assembleSQL(CreditorSearchVO searchVO){
		StringBuffer str = new StringBuffer();
		str.append(" select cre.id as creditor_id , acc.account, app.loan_name,cre.loan_amt,cre.repay_type_cd , cre.loan_approve_id,");
		str.append(" sysuser.USER_CODE as USER_CODE,cust.cust_name, cre.cr_amt, cre.LOAN_RATE as  rate,");
		str.append(" cre.SURPLUS_PERIOD as term , cre.CR_STATUS_CD ,");
		str.append(" sysuser.USER_NAME ,cre.SYS_CREATE_TIME,cre.TOTAL_PERIOD , cre.SURPLUS_PERIOD ,app.LOAN_TERM,");
		str.append(" loan.LOAN_DATE as start_date ,loan.LOAN_DUE_DATE as end_date");
		str.append(" from BIZ_CREDITOR_RIGHT cre ");
		str.append(" left join biz_loan_approve app on cre.loan_approve_id = app.id ");
		str.append(" left join biz_customer cust on cust.id = cre.cust_id ");
		str.append(" left join biz_account acc ON cre.cust_id = acc.CUST_ID");
		str.append(" left join sys_user sysuser on sysuser.user_id = cust.user_id");
		str.append(" left join biz_loan Loan on loan.id = cre.LOAN_ID");
		str.append(" where 1=1 ");
		if(null != searchVO.getCreditorId()){
			str.append(" and cre.id = ").append(searchVO.getCreditorId());
		}
		if(null != searchVO.getAccount() && searchVO.getAccount().length() >0){
			str.append(" and acc.account = '").append(searchVO.getAccount()).append("'");
		}
		if(null != searchVO.getCustName() && searchVO.getCustName().length() >0){
			str.append(" and cust.cust_name like '%").append(searchVO.getCustName()).append("%'");
		}
		if(null != searchVO.getUserCode() && searchVO.getUserCode().length() >0){
			str.append(" and sysuser.USER_CODE like '%").append(searchVO.getUserCode()).append("%'");
		}
		if(null != searchVO.getLoanName() && searchVO.getLoanName().length() >0){
			str.append(" and app.loan_name like '%").append(searchVO.getLoanName()).append("%'");
		}
		if(null != searchVO.getCustId() && searchVO.getCustId()!=0){
			str.append(" and cust.id = '").append(searchVO.getCustId()).append("'");
		}
		if(null != searchVO.getTerm() && searchVO.getTerm().length() >0){
			str.append(" and cre.SURPLUS_PERIOD = '").append(searchVO.getTerm()).append("'");
		}
		if(null != searchVO.getCrStatusCd() && searchVO.getCrStatusCd().length() >0 ){
			str.append(" and cre.CR_STATUS_CD = '").append(searchVO.getCrStatusCd()).append("'");
		}
		//过滤结清数据
//		str.append(" and cre.CR_STATUS_CD not in ('").append(AppConst.CR_STATUS_CD_END).append("','")
//			.append(AppConst.CR_STATUS_CD_CANCEL).append("') ");
		str.append(" order by cre.id desc");
		return str.toString();
	}
	
	
	/**
	 * 债权转让发布列表组装查询sql
	 * @param searchVO
	 * @return
	 */
	private String crTranListSQL(CreditorSearchVO searchVO){

		StringBuffer str = new StringBuffer();
		str.append(" select cre.id as creditor_id , acc.account, app.loan_name,cre.loan_amt,cre.repay_type_cd , cre.loan_approve_id,");
		str.append(" sysuser.USER_CODE as USER_CODE,cust.cust_name, cre.cr_amt, cre.LOAN_RATE as  rate,");
		str.append(" cre.SURPLUS_PERIOD as term , cre.CR_STATUS_CD ,");
		str.append(" tran.id as tran_id ,tran.CR_ID,tran.SURPLUS_PERIOD, ");
		str.append(" tran.LOAN_RATE,tran.TRAN_TTL_COUNT,tran.TRAN_OUT_COUNT, ");
		str.append(" tran.TRAN_TTL_AMT,tran.TRAN_OUT_AMT,tran.TAKE_AMT,tran.TAKE_BAL_AMT,tran.CRT_STATUS_CD, ");
		str.append(" loancust.cust_name as loan_cust_name, cre.TOTAL_PERIOD , sysuser.user_name,");
		str.append(" round((tran.TRAN_OUT_COUNT / tran.TRAN_TTL_COUNT) *100,0) as completeness");
		str.append(" from biz_creditor_right_tran tran , biz_creditor_right cre ");
		str.append(" left join biz_loan_approve app on cre.loan_approve_id = app.id ");
		str.append(" left join biz_customer cust on cust.id = cre.cust_id ");
		str.append(" left join biz_account acc ON cre.cust_id = acc.CUST_ID");
		str.append(" left join biz_customer loancust on loanCust.id = app.CUST_ID");
		str.append(" left join sys_user sysuser on sysuser.user_id = cust.user_id ");
		str.append(" where 1=1 and tran.CR_ID = cre.ID ");
		if(null != searchVO.getCreditorId()){
			str.append(" and cre.id = ").append(searchVO.getCreditorId());
		}
		if(null != searchVO.getAccount() && searchVO.getAccount().length() >0){
			str.append(" and acc.account = '").append(searchVO.getAccount()).append("'");
		}
		if(null != searchVO.getCustName() && searchVO.getCustName().length() >0){
			str.append(" and cust.cust_name like '%").append(searchVO.getCustName()).append("%'");
		}
		if(null != searchVO.getUserCode() && searchVO.getUserCode().length() >0){
			str.append(" and sysuser.USER_CODE like '%").append(searchVO.getUserCode()).append("%'");
		}
		if(null != searchVO.getLoanName() && searchVO.getLoanName().length() >0){
			str.append(" and app.loan_name like '%").append(searchVO.getLoanName()).append("%'");
		}
		if(null != searchVO.getTerm() && searchVO.getTerm().length() >0){
			str.append(" and cre.SURPLUS_PERIOD = '").append(searchVO.getTerm()).append("'");
		}
		if(null != searchVO.getCrStatusCd() && searchVO.getCrStatusCd().length() >0 ){
			str.append(" and cre.CR_STATUS_CD = '").append(searchVO.getCrStatusCd()).append("'");
		}
		str.append(" order by tran.id desc");
		return str.toString();
	}

	@Override
	public List<TransferVo> findListPagesByVO(TransferSearchVo searchVO,
			int first, int pageSize) throws BizException {
  
		String sql =  this.getTransSQL(searchVO);
		log.info("获取债券装让列表==="+sql);
		return this.commonQuery.findBySQLByPages(sql, first, pageSize,TransferVo.class);
	}

	
	private String getTransSQL(TransferSearchVo searchVO){
		
		StringBuffer str = new StringBuffer();
		str.append(" select a.id as creditor_Id, ");
		str.append(" 		a.cr_id , ");
		str.append(" 		b.cust_id , ");
		str.append(" 	    b.loan_name, ");
		str.append(" 	    d.id as loan_Approve_Id, ");
		str.append(" 		b.LOAN_CUST_ID, ");
		str.append(" 		b.REPAY_TYPE_CD, ");
		str.append(" 		d.loan_Type_Cd, ");
		str.append(" 		b.loan_rate * 100 as loan_rate, ");
		str.append(" 		c.credit_rate , ");
		str.append(" 		b.SURPLUS_PERIOD , ");
		str.append(" 		a.TRAN_TTL_COUNT, ");
		str.append(" 		a.TRAN_TTL_AMT, ");
		str.append(" 		a.TRAN_TTL_COUNT - a.TRAN_OUT_COUNT as remain_Count, ");
		str.append(" 		d.TENDER_LIMIT_AMT as  limit_Amt, ");
		str.append(" 		round((a.TRAN_OUT_AMT/a.TRAN_TTL_AMT)*100) as completeness, ");
		str.append(" 		date_format(b.SETTLE_DATE,'%Y-%m-%d') as SETTLE_DATE, ");
		str.append(" 		a.CRT_STATUS_CD, ");
		str.append(" 		a.TAKE_AMT ");
		str.append(" from BIZ_CREDITOR_RIGHT_TRAN a, ");
		str.append(" 	  BIZ_CREDITOR_RIGHT b, ");
		str.append("      BIZ_CUSTOMER c, ");
		str.append("      BIZ_LOAN_APPROVE d ");
		str.append(" where a.CR_ID = b.id ");
		str.append("   and b.LOAN_CUST_ID = c.id");
		str.append("   and b.LOAN_APPROVE_ID = d.id");
		str.append("   and a.CRT_STATUS_CD in ('2','3','4') ");
		 
		if(null != searchVO.getLoanApproveId()){
			str.append(" and b.LOAN_APPROVE_ID = ").append(searchVO.getLoanApproveId());
		}
		
		if(null != searchVO.getTransferId()){
			str.append(" and a.id = ").append(searchVO.getTransferId());
		}
		
		if(searchVO.getLoanType() != null & searchVO.getLoanType() != ""){
			// TODO 标的类型
			str.append(" and d.loan_type_cd = ").append(searchVO.getLoanType());
			
		} 
		
		if (searchVO.getRemainTerm() != null & searchVO.getRemainTerm()  != "") {
			str.append(" and ");
			str.append(searchVO.getRemainTermFormat("b.SURPLUS_PERIOD"));
 		}
		
		if(searchVO.getLoanRate() != null & searchVO.getLoanRate() != ""){
			
			str.append(" and ");
			str.append(searchVO.getLoanRateFormat("b.loan_rate"));
		}
		
		if(searchVO.getCreditRate() != null & searchVO.getCreditRate() != ""){ 
			str.append(" and ");
			str.append(" c.credit_Rate ='"+searchVO.getCreditRate()+"'");
		}
		 
		str.append(" order by a.SYS_CREATE_TIME desc");
		////System.out.println("str==="+str);
		return str.toString();
	}
	
	@Override
	public int getTransCountByVO(TransferSearchVo searchVO) {
		
		String str =  this.getTransSQL(searchVO);
		
		StringBuilder sql = new StringBuilder(" select count(1) from ( ");
		 sql.append(str);
		sql.append(" ) t where 1=1");
		log.info("获取记录数TransferSearchVo==="+sql);
		return this.commonQuery.findCountBySQL(sql, null);
	}

}
