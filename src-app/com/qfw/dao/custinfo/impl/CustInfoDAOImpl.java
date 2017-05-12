package com.qfw.dao.custinfo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.qfw.common.dao.ICommonQuery;
import com.qfw.common.dao.impl.BaseDAOImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.BussConst;
import com.qfw.common.util.StringUtils;
import com.qfw.dao.custinfo.ICustInfoDAO;
import com.qfw.model.AppConst;
import com.qfw.model.vo.custinfo.CustInfoVO;
import com.qfw.model.vo.custinfo.SearchCustInfoVO;
import com.qfw.platform.model.json.Pager;

@Repository("custInfoDAO")
public class CustInfoDAOImpl  extends BaseDAOImpl implements ICustInfoDAO {
	
	@Autowired
	private ICommonQuery commonQuery;

	@SuppressWarnings("unchecked")
	@Override
	public List<CustInfoVO> findCustInfo(SearchCustInfoVO searchCustInfoVO) throws BizException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT cust.ID AS custid,");
		sql.append(" cust.CUST_NAME AS custname,");
		sql.append(" cust.CERTIFICATE_TYPE_CD AS certificatetypecd,");
		sql.append(" cust.CERTIFICATE_NUM AScertificatenum,");
		sql.append(" cust.SYS_CREATE_TIME AS syscreatetime,");
		sql.append(" acc.ACCOUNT AS account,");
		sql.append(" acc.ACCOUNT_BAL_AMT AS accountbalamt,");
		sql.append(" acc.USABLE_BAL_AMT AS usablebalamt,");
		sql.append(" acc.PM_AMT AS pmamt,");
		sql.append(" crelim.CL_AMT as clamt,");
		sql.append(" (IFNULL(crelim.CL_AMT,0) - IFNULL(creuse.TIEUP_AMT,0) - IFNULL(creuse.PRE_TIEUP_AMT,0) + IFNULL(creuse.PRE_RESTORE_AMT,0)) AS balclamt");
		sql.append(" FROM biz_customer cust");
		sql.append(" LEFT JOIN biz_account acc ON cust.ID = acc.CUST_ID");
		sql.append(" LEFT JOIN biz_credit_limit crelim ON crelim.REL_TYPE_CD = '0' AND cust.ID = crelim.REL_ID");
		sql.append(" LEFT JOIN biz_credit_use creuse ON crelim.ID = creuse.CL_ID");
		sql.append(" WHERE 1 = 1");
		if (StringUtils.isNotEmpty(searchCustInfoVO.getAccount())) {
			sql.append(" AND acc.ACCOUNT = '").append(searchCustInfoVO.getAccount()).append("'");
		} 
		if (null != searchCustInfoVO.getCustId() && !"".equals(searchCustInfoVO.getCustId())) {
			sql.append(" AND cust.ID = '").append(searchCustInfoVO.getCustId()).append("'");
		}
		if (StringUtils.isNotEmpty(searchCustInfoVO.getCustName())) {
			sql.append(" AND cust.CUST_NAME = '").append(searchCustInfoVO.getCustName()).append("'");
		}
		List<CustInfoVO> custInfoList = this.commonQuery.findBySQL(sql.toString());
		return custInfoList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustInfoVO> findCustInfoPagesByVO(
			SearchCustInfoVO searchCustInfoVO, int first, int pageSize)
			throws BizException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT cust.ID AS CUST_ID,");
		sql.append(" cust.CUST_NAME AS CUST_NAME,");
		sql.append(" cust.CERTIFICATE_TYPE_CD AS CERTIFICATE_TYPE_CD,");
		sql.append(" cust.CERTIFICATE_NUM AS CERTIFICATE_NUM,");
		sql.append(" cust.SYS_CREATE_TIME AS SYS_CREATE_TIME,");
		sql.append(" cust.BIRTH_DATE AS BIRTH_DATE,");
		sql.append(" acc.ACCOUNT AS ACCOUNT,");
		sql.append(" acc.ACCOUNT_BAL_AMT AS ACCOUNT_BAL_AMT,");
		sql.append(" acc.USABLE_BAL_AMT AS USABLE_BAL_AMT,");
		sql.append(" acc.PM_AMT AS PM_AMT,");
		sql.append(" cust.IS_VIP AS IS_VIP,");
//		sql.append(" crelim.CL_AMT as CL_AMT,");
//		sql.append(" (IFNULL(crelim.CL_AMT,0) - IFNULL(creuse.TIEUP_AMT,0) - IFNULL(creuse.PRE_TIEUP_AMT,0) + IFNULL(creuse.PRE_RESTORE_AMT,0)) AS BAL_CL_AMT,");
		sql.append(" cust.MOBILE_TELEPHONE as phone , sysuser.USER_CODE ,cust.CUST_MANAGER_NAME as cust_manager");
		sql.append(" FROM biz_customer cust");
		sql.append(" LEFT JOIN biz_account acc ON cust.ID = acc.CUST_ID");
//		sql.append(" LEFT JOIN biz_credit_limit crelim ON crelim.REL_TYPE_CD = '0' AND cust.ID = crelim.REL_ID");
//		sql.append(" LEFT JOIN biz_credit_use creuse ON crelim.ID = creuse.CL_ID");
		sql.append(" left join sys_user sysuser on sysuser.user_id = cust.user_id ");
		sql.append(" WHERE 1 = 1");
		if (StringUtils.isNotEmpty(searchCustInfoVO.getAccount())) {
			sql.append(" AND acc.ACCOUNT = '").append(searchCustInfoVO.getAccount()).append("'");
		} 
		if (searchCustInfoVO.getCustId() != null && 0 != searchCustInfoVO.getCustId() && !"".equals(searchCustInfoVO.getCustId())) {
			sql.append(" AND cust.ID = '").append(searchCustInfoVO.getCustId()).append("'");
		}
		if (StringUtils.isNotEmpty(searchCustInfoVO.getCustName())) {
			sql.append(" AND cust.CUST_NAME like '%").append(searchCustInfoVO.getCustName()).append("%'");
		}
		if (StringUtils.isNotEmpty(searchCustInfoVO.getPhone())) {
			sql.append(" AND cust.MOBILE_TELEPHONE = '").append(searchCustInfoVO.getPhone()).append("'");
		}
		if (StringUtils.isNotEmpty(searchCustInfoVO.getDay())) {
			sql.append(" AND DATE_FORMAT(cust.BIRTH_DATE, '%m%d') BETWEEN DATE_FORMAT(CURDATE(), '%m%d') AND DATE_FORMAT(CURDATE(), '%m%d')+ ")
			.append(searchCustInfoVO.getDay());
		}
		if (StringUtils.isNotEmpty(searchCustInfoVO.getIsVip())) {
			sql.append(" AND cust.IS_VIP = '").append(searchCustInfoVO.getIsVip()).append("'");
		}
		if (StringUtils.isNotEmpty(searchCustInfoVO.getRefereeStatus())) {
			sql.append(" AND cust.REFEREE_STATUS = '").append(searchCustInfoVO.getRefereeStatus()).append("'");
		}
		if (searchCustInfoVO.getIsrefereeApply() != null && searchCustInfoVO.getIsrefereeApply()) {
			sql.append(" AND cust.REFEREE_STATUS in(").append("'"+AppConst.REFEREE_STATUS_APPLY+"'").
				append(",'"+AppConst.REFEREE_STATUS_APPLY_AGAIN+"'").append(",'"+AppConst.REFEREE_STATUS_APPLY_THIRD+"'").append(")");
		}
		sql.append(" order by cust.id desc");
		List<CustInfoVO> custInfoList = this.commonQuery.findBySQLByPages(sql.toString(), first, pageSize,CustInfoVO.class);
		return custInfoList;
	}
	
	@Override
	public Pager findMyRecommend(int requestPage, int pageSize, Integer custId) throws BizException {
		StringBuffer str = new StringBuffer();
		Pager pager = new Pager();
		str.append(" select cus.CUST_NAME,cre.CR_AMT,bl.LOAN_TERM,DATE_FORMAT(cre.SYS_CREATE_TIME,'%Y-%m-%d') as DATE");
		str.append(" from BIZ_CREDITOR_RIGHT cre,biz_loan bl,biz_customer cus ");
		str.append(" where cre.CUST_ID = cus.ID and cre.LOAN_ID = bl.ID");
		str.append(" and cus.REFEREE_ID = ?");
		pager = findPagerByCust(str.toString(), requestPage, pageSize, custId);
		return pager;
	}
	
	private Pager findPagerByCust(String sql,int requestPage,int pageSize,Integer custId){
		Pager pager = new Pager();
		int count = commonQuery.findCountByWapSQL(sql,new Object[]{custId});
		pager.setTotalCount(count);
		pager.setPageSize(pageSize);
		if(count > 0){
			List list = commonQuery.findBySQLByPages(sql, (requestPage-1)*pageSize, pageSize,new Object[]{custId});
			pager.setList(list);
		}
		return pager;
	}

	@Override
	public int findCustInfoCountByVO(SearchCustInfoVO searchCustInfoVO)
			throws BizException {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT count(*)");
		sql.append(" FROM biz_customer cust");
		sql.append(" LEFT JOIN biz_account acc ON cust.ID = acc.CUST_ID");
		sql.append(" LEFT JOIN biz_credit_limit crelim ON crelim.REL_TYPE_CD = '0' AND cust.ID = crelim.REL_ID");
		sql.append(" LEFT JOIN biz_credit_use creuse ON crelim.ID = creuse.CL_ID");
		sql.append(" WHERE 1 = 1");
		if (StringUtils.isNotEmpty(searchCustInfoVO.getAccount())) {
			sql.append(" AND acc.ACCOUNT = '").append(searchCustInfoVO.getAccount()).append("'");
		} 
		if (searchCustInfoVO.getCustId() != null && 0 != searchCustInfoVO.getCustId() && !"".equals(searchCustInfoVO.getCustId())) {
			sql.append(" AND cust.ID = '").append(searchCustInfoVO.getCustId()).append("'");
		}
		if (StringUtils.isNotEmpty(searchCustInfoVO.getCustName())) {
			sql.append(" AND cust.CUST_NAME like '%").append(searchCustInfoVO.getCustName()).append("%'");
		}
		if (StringUtils.isNotEmpty(searchCustInfoVO.getIsVip())) {
			sql.append(" AND cust.IS_VIP = '").append(searchCustInfoVO.getIsVip()).append("'");
		}
		if (StringUtils.isNotEmpty(searchCustInfoVO.getDay())) {
			sql.append(" AND DATE_FORMAT(cust.BIRTH_DATE, '%m%d') BETWEEN DATE_FORMAT(CURDATE(), '%m%d') AND DATE_FORMAT(CURDATE(), '%m%d')+ ")
			.append(searchCustInfoVO.getDay());
		}
		if (StringUtils.isNotEmpty(searchCustInfoVO.getRefereeStatus())) {
			sql.append(" AND cust.REFEREE_STATUS = '").append(searchCustInfoVO.getRefereeStatus()).append("'");
		}
		if (searchCustInfoVO.getIsrefereeApply() != null && searchCustInfoVO.getIsrefereeApply()) {
			sql.append(" AND cust.REFEREE_STATUS in(").append("'"+AppConst.REFEREE_STATUS_APPLY+"'").
				append(",'"+AppConst.REFEREE_STATUS_APPLY_AGAIN+"'").append(",'"+AppConst.REFEREE_STATUS_APPLY_THIRD+"'").append(")");
		}
		return this.commonQuery.findCountBySQL(sql, null);
	}
	
	public int countReferee(Integer custId) throws BizException {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT count(*)");
		sql.append(" FROM biz_customer cust");
		sql.append(" WHERE 1 = 1");
		sql.append(" AND REFEREE_ID = ").append(custId);
		return this.commonQuery.findCountBySQL(sql, null);
	}
	
	/**
	 * 根据客户编号查询客户相关信息
	 * @param custId
	 * @return
	 * @throws BizException
	 */
	@SuppressWarnings("unchecked")
	public List<CustInfoVO> findCustInfoById (Integer custId) throws BizException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT cust.ID AS cust_id,");
		sql.append(" cust.CUST_NAME AS cust_name,");
		sql.append(" cust.CERTIFICATE_TYPE_CD AS certificate_type_cd,");
		sql.append(" cust.CERTIFICATE_NUM AS certificate_num,");
		sql.append(" cust.SYS_CREATE_TIME AS sys_create_time,");
		sql.append(" cust.REFEREE_ID AS referee_id,");
		sql.append(" acc.ACCOUNT AS account,");
		sql.append(" acc.ACCOUNT_BAL_AMT AS account_bal_amt,");
		sql.append(" acc.USABLE_BAL_AMT AS usable_bal_amt,");
		sql.append(" acc.PM_AMT AS pm_amt,");
		sql.append(" crelim.CL_AMT as cl_amt,");
		sql.append(" (IFNULL(crelim.CL_AMT,0) - IFNULL(creuse.TIEUP_AMT,0) - IFNULL(creuse.PRE_TIEUP_AMT,0) + IFNULL(creuse.PRE_RESTORE_AMT,0)) AS bal_cl_amt");
		sql.append(" FROM biz_customer cust");
		sql.append(" LEFT JOIN biz_account acc ON cust.ID = acc.CUST_ID");
		sql.append(" LEFT JOIN biz_credit_limit crelim ON crelim.REL_TYPE_CD = '0' AND cust.ID = crelim.REL_ID");
		sql.append(" LEFT JOIN biz_credit_use creuse ON crelim.ID = creuse.CL_ID");
		sql.append(" WHERE 1 = 1");
		if (null != custId && !"".equals(custId)) {
			sql.append(" AND cust.ID = '").append(custId).append("'");
		}
		List<CustInfoVO> custInfoList = this.commonQuery.findObjects(sql.toString(),CustInfoVO.class);
		return custInfoList;
	}

	/**
	 * 根据用户编号查询客户，客户账号相关信息
	 * @param custId
	 * @return
	 * @throws BizException
	 */
	@SuppressWarnings("unchecked")
	public List<CustInfoVO> findCustInfoByUserId (Integer userId) throws BizException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT cust.ID AS cust_id,");
		sql.append(" cust.CUST_NAME AS cust_name,");
		sql.append(" cust.CERTIFICATE_TYPE_CD AS certificate_type_cd,");
		sql.append(" cust.CERTIFICATE_NUM AS certificate_num,");
		sql.append(" cust.SYS_CREATE_TIME AS sys_create_time,");
		sql.append(" acc.ACCOUNT AS account,");
		sql.append(" acc.ACCOUNT_BAL_AMT AS account_bal_amt,");
		sql.append(" acc.USABLE_BAL_AMT AS usable_bal_amt,");
		sql.append(" acc.PM_AMT AS pm_amt ");
		sql.append(" FROM biz_customer cust");
		sql.append(" LEFT JOIN biz_account acc ON cust.ID = acc.CUST_ID");
		sql.append(" WHERE 1 = 1");
		if (null != userId && !"".equals(userId)) {
			sql.append(" AND cust.USER_ID = '").append(userId).append("'");
		}
		List<CustInfoVO> custInfoList = this.commonQuery.findObjects(sql.toString(),CustInfoVO.class);
		return custInfoList;
	}
	@Override
	public int countCust() throws BizException {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT count(*)");
		sql.append(" FROM biz_customer cust");
		sql.append(" WHERE 1 = 1");
		return this.commonQuery.findCountBySQL(sql, null);
	}
	@Override
	public int countVip() throws BizException {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT count(*)");
		sql.append(" FROM biz_customer cust");
		sql.append(" WHERE 1 = 1");
		sql.append(" AND IS_VIP = '"+BussConst.YES_FLAG+"'");
		return this.commonQuery.findCountBySQL(sql, null);
	}
	@Override
	public int countRecommend() throws BizException {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT count(*)");
		sql.append(" FROM biz_customer cust");
		sql.append(" WHERE 1 = 1");
		sql.append(" AND REFEREE_STATUS = '"+AppConst.REFEREE_STATUS_AGREE+"'");
		return this.commonQuery.findCountBySQL(sql, null);
	}
	@Override
	public List<CustInfoVO> sumAccount () throws BizException {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT sum(acc.ACCOUNT_BAL_AMT) AS account_bal_amt,");
		sql.append(" sum(acc.USABLE_BAL_AMT) AS usable_bal_amt");
		sql.append(" FROM biz_account acc");
		sql.append(" WHERE 1 = 1 and ACCOUNT_TYPE_CD = '"+AppConst.ACCOUNT_TYPE_CUST+"'");
		List<CustInfoVO> custInfoList = this.commonQuery.findObjects(sql.toString(),CustInfoVO.class);
		return custInfoList;
	}

}
