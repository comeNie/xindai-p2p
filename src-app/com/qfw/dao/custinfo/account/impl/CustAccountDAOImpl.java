package com.qfw.dao.custinfo.account.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.qfw.batch.bizservice.util.DateUtil;
import com.qfw.common.dao.ICommonQuery;
import com.qfw.common.dao.impl.BaseDAOImpl;
import com.qfw.common.exception.BizException;
import com.qfw.dao.custinfo.account.ICustAccountDAO;
import com.qfw.model.bo.BizAccountBO;
import com.qfw.model.vo.custinfo.account.AccountRequestVO;
import com.qfw.model.vo.custinfo.account.AccountResponseVO;
import com.qfw.model.vo.custinfo.account.PMInfoVO;
import com.qfw.model.vo.custinfo.account.PMSearchVO;

@SuppressWarnings("unchecked")
@Repository("custAccountDAO")
public class CustAccountDAOImpl extends BaseDAOImpl implements ICustAccountDAO {

	@Autowired
	private ICommonQuery commonQuery;
	
	@Override
	public AccountResponseVO findDetailById(Integer id) {
		AccountRequestVO searchVO = new AccountRequestVO();
		searchVO.setId(id);
		List<AccountResponseVO> list = this.commonQuery.findObjects(this.assembleDetailSQL(searchVO), AccountResponseVO.class);
		if(null!=list&&list.size()==1){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	@Override
	public List<AccountResponseVO> findDetailPagesByParams(
			AccountRequestVO vo, int first, int pageSize) throws BizException {
		return this.commonQuery.findBySQLByPages(this.assembleDetailSQL(vo), first, pageSize,AccountResponseVO.class);
	}
	
	@Override
	public int findCountDetailByParams(AccountRequestVO vo) throws BizException {
		return commonQuery.findCountByWapSQL(this.assembleDetailSQL(vo), null);
	}
	
	/**
	 * 组装查询sql
	 * @param searchVO
	 * @return
	 */
	private String assembleDetailSQL(AccountRequestVO searchVO){
		StringBuffer str = new StringBuffer();
		str.append(" select * from (");
		str.append(" SELECT detail.ID as detail_id, detail.ACCOUNT_BAL_AMT , detail.ACCOUNT,tra.`COMMENT`,");
		str.append(" tra.TRADA_TIME as TX_DATE, tra.TRADE_AMT, detail.TX_NO, tra.TRADE_TYPE_CD,");
		str.append(" cust.CUST_NAME,sysuser.USER_CODE,sysuser.USER_NAME");
		str.append(" FROM biz_account acc, biz_account_detail detail, biz_trade tra, biz_customer cust, sys_user sysuser ");
		str.append(" where acc.ACCOUNT = detail.ACCOUNT and acc.CUST_ID = detail.CUST_ID");
		str.append(" and detail.TX_NO = tra.TRADE_NUM and acc.CUST_ID = cust.ID and cust.USER_ID = sysuser.USER_ID");
		if(null != searchVO.getId() && searchVO.getId() != 0 ){
			str.append(" and detail.id = '").append(searchVO.getId()).append("'");
		}
		if(null != searchVO.getCustId() && searchVO.getCustId() != 0 ){
			str.append(" and acc.CUST_ID = '").append(searchVO.getCustId()).append("'");
		}
		if(null != searchVO.getAccount() && searchVO.getAccount().length()>0 ){
			str.append(" and acc.ACCOUNT = '").append(searchVO.getAccount()).append("'");
		}
		str.append(" order by detail.TX_DATE desc,detail.id desc) temp where 1=1");
		return str.toString();
	}
	
	
	@Override
	public List<BizAccountBO> findInfoByParams(AccountRequestVO vo)
			throws BizException {
		try {
			StringBuffer str = new StringBuffer(" from BizAccountBO bo where 1=1");
			str.append(this.queryConditionForHQL(vo));
			return super.findByHQL(str.toString());
		} catch (Exception e) {
			throw new BizException(e);
		}
	}
	
	@Override
	public List<BizAccountBO> findInfoPagesByParams(AccountRequestVO vo,
			int first, int pageSize) throws BizException {
		try {
			StringBuffer str = new StringBuffer("select * from biz_account bo where 1=1");
			str.append( this.queryConditionForSQL(vo));
			return this.commonQuery.findBySQLByPages(str.toString(), first, pageSize,BizAccountBO.class);
		} catch (Exception e) {
			throw new BizException(e);
		}
	}
	
	@Override
	public int findCountInfoByParams(AccountRequestVO vo)  throws BizException  {
		int count =0 ;
		try {
			StringBuilder str = new StringBuilder(" select count(1) from biz_account bo where 1=1");
			str.append(this.queryConditionForSQL(vo));
			count = this.commonQuery.findCountBySQL(str, null);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return count;
	}
	
	/**
	 * 组装查询sql
	 * @param vo
	 * @return
	 * @throws BizException
	 */
	private String queryConditionForSQL(AccountRequestVO vo) throws BizException {
		StringBuffer str = new StringBuffer();
		if(null != vo.getAccount() && vo.getAccount().length() > 0){
			str.append(" and bo.account = '").append(vo.getAccount()).append("'");
		}
		if(null != vo.getCustId() && vo.getCustId() > 0){
			str.append(" and bo.CUST_ID = '").append(vo.getCustId()).append("'");
		}
		if(null != vo.getWorkItemId() && vo.getWorkItemId().length() > 0){
			str.append(" and bo.WORK_ITEM_ID = '").append(vo.getWorkItemId()).append("'");
		}
		return str.toString();
	}
	
	/**
	 * 组装查询hql
	 * @param vo
	 * @return
	 * @throws BizException
	 */
	private String queryConditionForHQL(AccountRequestVO vo) throws BizException {
		StringBuffer str = new StringBuffer();
		if(null != vo.getAccount() && vo.getAccount().length() > 0){
			str.append(" and bo.account = '").append(vo.getAccount()).append("'");
		}
		if(null != vo.getCustId() && vo.getCustId() > 0){
			str.append(" and bo.custId = '").append(vo.getCustId()).append("'");
		}
		if(null != vo.getAccountType() && vo.getAccountType().length() > 0){
			str.append(" and bo.accountTypeCd = '").append(vo.getAccountType()).append("'");
		}
		if(null != vo.getWorkItemId() && vo.getWorkItemId().length() > 0){
			str.append(" and bo.workItemId = '").append(vo.getWorkItemId()).append("'");
		}
		return str.toString();
	}
	
	/**
	 * 组装明细查询sql
	 * @param vo
	 * @return
	 * @throws BizException
	 */
	private String queryDetailCondition(AccountRequestVO vo) throws BizException {
		StringBuffer str = new StringBuffer();
		if(null != vo.getAccount() && vo.getAccount().length() > 0){
			str.append(" and bo.account = '").append(vo.getAccount()).append("'");
		}
		if(null != vo.getEventTypeCd() && vo.getEventTypeCd().length() > 0){
			str.append(" and bo.EVENT_TYPE_CD = '").append(vo.getEventTypeCd()).append("'");
		}
		if(null != vo.getWorkItemId() && vo.getWorkItemId().length() > 0){
			str.append(" and bo.WORK_ITEM_ID = '").append(vo.getWorkItemId()).append("'");
		}
		if(null != vo.getTxNo()){
			str.append(" and bo.TX_NO = '").append(vo.getTxNo()).append("'");
		}
		return str.toString();
	}

	@Override
	public List<PMInfoVO> findPMInfoPagesByParams(PMSearchVO vo, int first,
			int pageSize) throws BizException {
		try {
			StringBuffer str = new StringBuffer();
			str.append(this.assembleSQL(vo));
			return this.commonQuery.findBySQLByPages(str.toString(), first, pageSize,PMInfoVO.class);
		} catch (Exception e) {
			throw new BizException(e);
		}
	}
	
	@Override
	public int getCountByVO(PMSearchVO searchVO) {
		return commonQuery.findCountByWapSQL(this.assembleSQL(searchVO), null);
	}
	
	/**
	 * 组装查询sql
	 * @param searchVO
	 * @return
	 */
	private String assembleSQL(PMSearchVO searchVO){
		StringBuffer str = new StringBuffer();
		str.append(" select * from (");
		str.append(" SELECT acc.ID as account_detail_id, sysuser.USER_code , sysuser.USER_NAME, cust.CUST_NAME,");
		str.append(" acc.EVENT_TYPE_CD , acc.PM_AMT, acc.TX_DATE ");
		str.append(" FROM biz_account_detail acc, biz_customer cust, sys_user sysuser WHERE acc.CUST_ID = cust.ID");
		str.append(" AND cust.USER_ID = sysuser.USER_ID");
		if(null != searchVO.getAccountDetailId() && searchVO.getAccountDetailId() != 0 ){
			str.append(" and acc.id = '").append(searchVO.getAccountDetailId()).append("'");
		}
		if(null != searchVO.getUserCode() && searchVO.getUserCode().length()>0 ){
			str.append(" and sysuser.USER_CODE like '%").append(searchVO.getUserCode()).append("%'");
		}
		if(null != searchVO.getCustName() && searchVO.getCustName().length()>0 ){
			str.append(" and cust.CUST_NAME like '%").append(searchVO.getCustName()).append("%'");
		}
		if(null != searchVO.getUserName() && searchVO.getUserName().length()>0 ){
			str.append(" and sysuser.USER_NAME like '%").append(searchVO.getUserName()).append("%'");
		}
		if(null != searchVO.getCustName() && searchVO.getCustName().length()>0 ){
			str.append(" and cust.CUST_NAME like '%").append(searchVO.getCustName()).append("%'");
		}
		if(null != searchVO.getEventTypeCd() && searchVO.getEventTypeCd().length()>0){
			str.append(" and acc.EVENT_TYPE_CD = '").append(searchVO.getEventTypeCd()).append("'");
		}
		if(null != searchVO.getStartDate()  ){
			str.append(" and acc.TX_DATE >= '").append(DateUtil.getYmdhms(searchVO.getStartDate())).append("'");
		}
		if(null != searchVO.getEndDate()  ){
			str.append(" and acc.TX_DATE <= '").append(DateUtil.getYmdhms(searchVO.getEndDate())).append("'");
		}
		if(null != searchVO.getCustId() && searchVO.getCustId()!=0 ){
			str.append(" and cust.id = '").append(searchVO.getCustId()).append("'");
		}
		if(null != searchVO.getAccount() && searchVO.getAccount().length()>0 ){
			str.append(" and acc.ACCOUNT = '").append(searchVO.getAccount()).append("'");
		}
		str.append(" order by acc.TX_DATE desc) temp where 1=1");
		return str.toString();
	}

}
