package com.qfw.common.bizservice.permission.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qfw.bizservice.credit.report.ICreditReportBS;
import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.bizservice.custinfo.account.ICustAccountBS;
import com.qfw.bizservice.custinfo.recommendation.IRecommendationBS;
import com.qfw.common.bizservice.PromotionService.PromotionService;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.bizservice.permission.IUserBS;
import com.qfw.common.bizservice.permission.IUserInfoSyncBS;
import com.qfw.common.dao.permission.IUserDAO;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.BussConst;
import com.qfw.common.model.permission.SysRole;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.model.permission.vo.SysUserVO;
import com.qfw.common.util.CollectionUtil;
import com.qfw.common.util.DateUtils;
import com.qfw.common.util.MD5Utils;
import com.qfw.common.util.RamdomUtil;
import com.qfw.common.util.StringUtils;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizAccountBO;
import com.qfw.model.bo.BizCreditLimitBO;
import com.qfw.model.bo.BizCreditReportBO;
import com.qfw.model.bo.BizCreditUseBO;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.platform.model.vo.LoginInfo;
import com.qfw.platform.model.vo.UserInfo;


public class UserBSImpl extends BaseServiceImpl implements IUserBS {

	@Autowired
	private ICustInfoBS custInfoBS;
	@Autowired
	private ICustAccountBS custAccountBS;
	@Autowired
	private ICreditReportBS creditReportBS;
	
	@Autowired
	private PromotionService promotionService;
	
	@Autowired
	private IRecommendationBS recommendationBS;
	
	@Resource(name = "userInfoSync")
	private IUserInfoSyncBS userInfoSyncBS;
	
	private IUserDAO userDAO;
	private Logger log = LogFactory.getInstance().getBusinessLogger();

	public List<SysUser> findUsersByVO(SysUserVO userVO) {
		StringBuilder sb = new StringBuilder("from SysUser");
		sb.append(VO2Condition(userVO));
		return userDAO.findByHQL(sb.toString());
	}

	public int findUsersCountByVO(SysUserVO userVO) {
		return commonQuery.findCountByWapSQL(wrapSelectUserSQL(userVO), null);

		/*
		 * StringBuilder sb = new
		 * StringBuilder("select count(userId) from SysUser");
		 * sb.append(VO2Condition(userVO)); return userDAO.count(sb.toString());
		 */
	}

	private String VO2Condition(SysUserVO userVO) {
		StringBuilder sb = new StringBuilder(" where 1=1");
		if (userVO != null) {
			if (StringUtils.isNotEmpty(userVO.getUserName())) {
				sb.append(" and userName like '%").append(userVO.getUserName())
						.append("%'");
			}
			if (StringUtils.isNotEmpty(userVO.getUserCode())) {
				sb.append(" and userCode like '%").append(userVO.getUserCode())
						.append("%'");
			}
			if (StringUtils.isNotEmpty(userVO.getIsAdmin())) {
				sb.append(" and is_admin = '").append(userVO.getIsAdmin()).append("'");
			}
		}
		return sb.toString();
	}

	public SysUser findUserById(Integer id) {
		return (SysUser) userDAO.findById(SysUser.class, id);
	}

	public IUserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(IUserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public void merge(SysUser user) {
		this.userDAO.merge(user);
	}

	@Override
	public List<SysUser> findUsersPagesByVO(SysUserVO userVO, int first,
			int pageSize) {

		return commonQuery.findBySQLByPages(wrapSelectUserSQL(userVO), first,
				pageSize, SysUserVO.class);

		/*
		 * StringBuilder sb = new StringBuilder("from SysUser where 1=1");
		 * if(userVO!=null){ if(StringUtils.isNotEmpty(userVO.getUserName())){
		 * sb
		 * .append(" and userName like '%").append(userVO.getUserName()).append
		 * ("%'"); } if(StringUtils.isNotEmpty(userVO.getUserCode())){
		 * sb.append(
		 * " and userCode like '%").append(userVO.getUserCode()).append("%'"); }
		 * } return userDAO.findByHQLByPages(sb.toString(), first, pageSize);
		 */
	}

	private String wrapSelectUserSQL(SysUserVO userVO) {
		StringBuilder sb = new StringBuilder("");
		StringBuilder where = new StringBuilder(
				" WHERE 1=1 ");
		if (StringUtils.isNotEmpty(userVO.getIsAdmin())) {
			where.append(" and is_admin = '").append(userVO.getIsAdmin()).append("'");
		}
		
		if (StringUtils.isNotEmpty(userVO.getUserName())) {
			where.append(" AND U.USER_NAME LIKE '%")
					.append(userVO.getUserName()).append("%'");
		}
		if (StringUtils.isNotEmpty(userVO.getUserCode())) {
			where.append(" AND U.USER_CODE LIKE '%")
					.append(userVO.getUserCode()).append("%'");
		}
		if (StringUtils.isNotEmpty(userVO.getRoleIds())) {
			where.append(" AND EXISTS (SELECT 1 FROM SYS_USER_ROLE UR WHERE UR.USER_ID = U.USER_ID AND UR.ROLE_ID IN ("
					+ userVO.getRoleIds() + "))");
		}
		if (StringUtils.isNotEmpty(userVO.getDeptIds())) {
			// where.append(" AND U.USER_ID = UD.USER_ID AND D.DEPT_ID = UD.DEPT_ID");
			where.append(" AND UD.DEPT_ID IN (").append(userVO.getDeptIds())
					.append(")");
			// sb.append("SELECT U.*,GROUP_CONCAT(D.DEPT_ID) AS DEPT_IDS,GROUP_CONCAT(D.DEPT_NAME) AS DEPT_NAMES FROM SYS_USER U,SYS_USER_DEPT UD,SYS_DEPT D ");
			// sb.append(where);
		} else {
			// sb.append("SELECT U.* FROM SYS_USER U ").append(where);
		}
		
		sb.append(" SELECT U.*,GROUP_CONCAT(D.DEPT_ID) AS DEPT_IDS,GROUP_CONCAT(D.DEPT_NAME) AS DEPT_NAMES FROM SYS_USER U LEFT JOIN SYS_USER_DEPT UD ON U.USER_ID = UD.USER_ID LEFT JOIN SYS_DEPT D ON D.DEPT_ID = UD.DEPT_ID");
		sb.append(where);
		sb.append(" GROUP BY U.USER_ID");
		return sb.toString();
	}

	@Override
	public int findUserCount(String userCode) {
//		StringBuilder sb = new StringBuilder(
//				"select count(userId) from SysUser where lower(userCode) = '"
//						+ userCode.toLowerCase() + "'");
		StringBuilder sb = new StringBuilder(
				"select count(u.userId) from SysUser u,BizCustomerBO c where lower(u.userCode) = '"
						+ userCode.toLowerCase() + "'"
								+ "and u.userId = c.userId ");
		return userDAO.count(sb.toString());
	}
	
	public int getIsAdminCount(String userCode) {
		StringBuilder sb = new StringBuilder(
				"select count(u.userId) from SysUser u where lower(u.userCode) = '"
						+ userCode.toLowerCase() + "'"
								+ "and isAdmin = '1' ");
		return userDAO.count(sb.toString());
	}
	public List<SysUser> findExistUser(String cardId,String tel) {
		try {
			String queryString = "from SysUser where cardid = ? or tel = ?";
			List<SysUser> list = getHibernateTemplate().find(queryString, new Object[]{cardId,tel} );
			if (list != null && !list.isEmpty()) {
				return list;
			}
			return null;
		} catch (Exception e) {
			 
		}
		return null;
	}

	public SysUser findUser(String userCode) {
		try {
			String queryString = "from SysUser where lower(userCode) = ?";
			List list = getHibernateTemplate().find(queryString, userCode.toLowerCase());
			if (list != null && !list.isEmpty()) {
				return (SysUser) list.get(0);
			}
			return null;
		} catch (Exception e) {
			 
		}
		return null;

	}

	@Override
	public List<SysRole> getUserRoles(String userID) {
		String sql = "select role_id,role_code,role_name from sys_role where role_id in (select role_id from sys_user_role where user_id = '"
				+ userID + "')";
		// this.getCommonQuery().findObjects(sql, SysRole.class);
		return this.getCommonQuery().findObjects(sql, SysRole.class);
	}

	@Override
	public List findObjectsByPages(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters,
			LazyDataModel lazyDataMode) {
		StringBuilder hqlString = new StringBuilder(
				"from SysUser user where 1=1 ");
		if (filters != null && !filters.isEmpty()) {
			Set<String> keys = filters.keySet();
			for (String key : keys) {
				if ("isAllowFriend".equals(key)) {
					hqlString.append(" and ").append(key).append(" in (")
							.append(filters.get(key)).append(")");
				} else {
					hqlString.append(" and ").append(key).append(" like '%")
							.append(filters.get(key)).append("%'");
				}

			}
		}
		int count = this.count("select count(user) " + hqlString.toString());
		if (StringUtils.isNotEmpty(sortField)) {
			if (!SortOrder.UNSORTED.equals(sortOrder)) {
				hqlString.append(" order by ").append(sortField);
				if (SortOrder.DESCENDING.equals(sortOrder)) {
					hqlString.append(" desc");
				}
			}
		}
		lazyDataMode.setRowCount(count);

		return userDAO.findByHQLByPages(hqlString.toString(), first, pageSize);
	}

	public List findObjectsByPages(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters,
			LazyDataModel lazyDataMode, String condition) {
		StringBuilder hqlString = new StringBuilder(
				"from SysUser user where 1=1" + condition);
		if (filters != null && !filters.isEmpty()) {
			Set<String> keys = filters.keySet();
			for (String key : keys) {
				hqlString.append(" and ").append(key).append(" like '%")
						.append(filters.get(key)).append("%'");
			}
		}
		int count = this.count("select count(user) " + hqlString.toString());
		if (StringUtils.isNotEmpty(sortField)) {
			if (!SortOrder.UNSORTED.equals(sortOrder)) {
				hqlString.append(" order by ").append(sortField);
				if (SortOrder.DESCENDING.equals(sortOrder)) {
					hqlString.append(" desc");
				}
			}
		}
		lazyDataMode.setRowCount(count);

		return userDAO.findByHQLByPages(hqlString.toString(), first, pageSize);
	}

	public boolean findUsersExist() {
		return false;
	}

	/**
	 * 保存系统用户机构表
	 */
	public void saveDept(SysUser user) throws BizException {
		try {
			String sql = "INSERT INTO SYS_USER_DEPT(USER_ID,DEPT_ID,SYS_CREATE_TIME) VALUES ("
					+ user.getUserId() + ",'1',sysdate())";
			this.getJdbcTemplate().execute(sql.toString());
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.error(e.getMessage());
			}
			throw new BizException(e.getMessage());
		}

	}

	/**
	 * 保存系统用户注册信息
	 */
	public void saveUser(SysUser user) {
		this.save(user);
	}

	/**
	 * 用户注册
	 */
	public void tranRegister(SysUser user) {
		this.saveUser(user);
		baseDAO.flush();
		saveDefaultDeptAndRole(user);
	}

	/**
	 * 保存系统用户机构表和用户角色表
	 */
	private void saveDefaultDeptAndRole(SysUser user) {
		String sql = "INSERT INTO SYS_USER_DEPT(USER_ID,DEPT_ID) VALUES ("
				+ user.getUserId() + ",'" + BussConst.BUYER_DEPT + "')";
		this.getJdbcTemplate().execute(sql.toString());
		sql = "INSERT INTO SYS_USER_ROLE(USER_ID,ROLE_ID) VALUES ("
				+ user.getUserId() + ",'" + BussConst.BUYER_ROLE + "')";
		this.getJdbcTemplate().execute(sql.toString());
	}

	public void updateUserOfLoginOutTime(SysUser user) {
		String sql = "UPDATE SYS_USER SET LOGOUT_TIME = sysdate() WHERE USER_CODE = '"
				+ user.getUserCode() + "'";
		this.getJdbcTemplate().execute(sql.toString());
	}

	public SysUser findUserByUserId(Integer userId) {
		try {
			String queryString = "from SysUser where userId = ?";
			List list = getHibernateTemplate().find(queryString, userId);
			if (list != null && !list.isEmpty()) {
				return (SysUser) list.get(0);
			}
			return null;
		} catch (Exception e) {
			 
		}
		return null;

	}
	
	public SysUser findUserByCustId(Integer custId) {
		try {
			String queryString = "select s from SysUser s,BizCustomerBO c where s.userId = c.userId and c.id = ?";
			List list = getHibernateTemplate().find(queryString, custId);
			if (list != null && !list.isEmpty()) {
				return (SysUser) list.get(0);
			}
			return null;
		} catch (Exception e) {
			 
		}
		return null;

	}

	@Override
	public void deleteUserByUserId(Integer userId) {
		String sql = "DELETE FROM SYS_USER_DEPT WHERE USER_ID = '" + userId
				+ "'";
		getJdbcTemplate().execute(sql);
		sql = "DELETE FROM SYS_USER_ROLE WHERE USER_ID = '" + userId + "'";
		getJdbcTemplate().execute(sql);
		sql = "DELETE FROM SYS_USER WHERE USER_ID = '" + userId + "'";
		getJdbcTemplate().execute(sql);
		
		// 删除会员信息表
		/*sql = "DELETE FROM biz_customer WHERE USER_ID = '" + userId + "'";
		getJdbcTemplate().execute(sql);*/ //delete by yangjj
	}

	@Override
	public void saveUserRole(Integer userId, Integer roleId)
			throws BizException {
		SysUser sysUser = (SysUser) ViewOper.getSession().getAttribute("user");
		StringBuilder sb = new StringBuilder(
				" INSERT INTO SYS_USER_ROLE(USER_ID, ROLE_ID, SYS_CREATE_TIME, SYS_UPDATE_TIME, SYS_UPDATE_USER)");
		sb.append(" VALUES(").append(userId).append(",");
		sb.append(roleId).append(",");
		sb.append("sysdate(),sysdate(),'");
		sb.append(sysUser.getUserCode()).append("')");
		this.getJdbcTemplate().execute(sb.toString());
	}

	@Override
	public void saveUserDept(Integer userId, Integer deptId)
			throws BizException {
		SysUser sysUser = (SysUser) ViewOper.getSession().getAttribute("user");
		StringBuilder sb = new StringBuilder(
				" INSERT INTO SYS_USER_DEPT(USER_ID, DEPT_ID, SYS_CREATE_TIME, SYS_UPDATE_TIME, SYS_UPDATE_USER)");
		sb.append(" VALUES(").append(userId).append(",");
		sb.append(deptId).append(",");
		sb.append("sysdate(),sysdate(),'");
		sb.append(sysUser.getUserCode()).append("')");
		this.getJdbcTemplate().execute(sb.toString());
	}

	/**
	 * 添加用户
	 * 
	 * @param user
	 * @param deptIds
	 * @throws BizException
	 * @throws NumberFormatException
	 */
	public void addUser(SysUser user, String[] deptIds, String[] roleIds)
			throws BizException {
		getHibernateTemplate().saveOrUpdate(user);
		this.saveUserLinkInfo(user); 
		if (deptIds != null && deptIds.length > 0) {
			for (String deptId : deptIds) {
				saveUserDept(user.getUserId(), Integer.valueOf(deptId));
			}
		}
		if (roleIds != null && roleIds.length > 0) {
			for (String roleId : roleIds) {
				saveUserRole(user.getUserId(), Integer.valueOf(roleId));
			}
		}
	}

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 * @param deptIds
	 */
	public void updateUser(SysUser user, String[] deptIds, String[] roleIds)
			throws BizException {
		getHibernateTemplate().update(user);
		this.saveUserLinkInfo(user); 
		String sql = "DELETE FROM SYS_USER_DEPT WHERE USER_ID = '"
				+ user.getUserId() + "'";
		getJdbcTemplate().execute(sql);// 删除用户机构信息
		sql = "DELETE FROM SYS_USER_ROLE WHERE USER_ID = '" + user.getUserId()
				+ "'";
		getJdbcTemplate().execute(sql);// 删除角色关联信息

		if (deptIds != null && deptIds.length > 0) {
			for (String deptId : deptIds) {
				saveUserDept(user.getUserId(), Integer.valueOf(deptId));
			}
		}
		if (roleIds != null && roleIds.length > 0) {
			for (String roleId : roleIds) {
				saveUserRole(user.getUserId(), Integer.valueOf(roleId));
			}
		}

		// SELECT * FROM SYS_USER_ROLE UR WHERE UR.USER_ID = '1' AND NOT EXISTS
		// (SELECT 1 FROM SYS_ROLE R,SYS_DEPT D WHERE UR.ROLE_ID = R.ROLE_ID AND
		// R.DEPT_ID = D.DEPT_ID AND R.DEPT_ID NOT IN ('3','10001'))

	}

	@Override
	public int findUserCountByTel(String tel) {
//		StringBuilder sb = new StringBuilder(
//				"select count(userId) from SysUser where tel = '" + tel + "'");
		StringBuilder sb = new StringBuilder(
				"select count(u.userId) from SysUser u,BizCustomerBO c where u.tel = '" + tel + "'"
						+ "and u.userId = c.userId");
		return userDAO.count(sb.toString());
	}

	@Override
	public int findUserCountByEmail(String email) {
//		StringBuilder sb = new StringBuilder(
//				"select count(userId) from SysUser where lower(email) = '" + email.toLowerCase()
//						+ "'");
		StringBuilder sb = new StringBuilder(
				"select count(u.userId) from SysUser u,BizCustomerBO c where lower(u.email) = '" 
		+ email.toLowerCase()+ "'"
				+ "and u.userId = c.userId");
		//System.out.println("sb.toString() enami =="+sb.toString());
		return userDAO.count(sb.toString());
	}

	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void saveRegisterUser(String mobile, String email,String custCode, String password,
			String refereeName) throws BizException {
		Integer refCustId = null;
		BizCustomerBO refCust = null;
		List<BizCustomerBO> customer = custInfoBS.findCustomer(mobile);
		if(customer != null && customer.size() > 0){
			throw new BizException("重复的手机号码");
		}
		if(StringUtils.isNotEmpty(refereeName)){
			refCust = custInfoBS.findCustByRefereeCode(refereeName);
			
		}else{
			refCust = custInfoBS.findCustByRefereeCode(AppConst.DEFALT_REFEREECODE);
		}
		if(refCust == null){
			throw new BizException("获取不到推荐人信息");
		}
		refCustId = refCust.getId();
		try {
			String passwordMd5 = MD5Utils.getMD5Str(password);
			SysUser user = new SysUser();
			user.setUserCode(custCode);
			user.setPassword(passwordMd5);
			user.setEmail(email);
			user.setTel(mobile);
			user.setUserReferee(refereeName);
			getHibernateTemplate().save(user);
			getHibernateTemplate().flush();
			
			BizCustomerBO cust = new BizCustomerBO();
			cust.setMobileTelephone(mobile);
			cust.setUserId(user.getUserId());
			cust.setEmail(email);
//			cust.setRefereeName(refereeName);
			cust.setRefereeId(refCustId);
			//生成推荐码
			cust.setRefereeCode(RamdomUtil.getRefereeCode());
			getHibernateTemplate().save(cust);
			getHibernateTemplate().flush();
			
			BizAccountBO accountBO = new BizAccountBO();
			accountBO.setCustId(cust.getId());
			accountBO.setAccount(user.getUserCode());
			accountBO.setAccountTypeCd(AppConst.ACCOUNT_TYPE_CUST);
			accountBO.setAccountBalAmt(BigDecimal.ZERO);
			accountBO.setFreezeBalAmt(BigDecimal.ZERO);
			accountBO.setUsableBalAmt(BigDecimal.ZERO);
			accountBO.setPmAmt(BigDecimal.ZERO);
			accountBO.setSysCreateTime(new Date());
			accountBO.setSysCreateUser(cust.getId());
			accountBO.setSysUpdateUser(cust.getId());
			accountBO.setSysUpdateTime(new Date());
			
			getHibernateTemplate().save(accountBO);
			
			BizCreditReportBO  reportBO = new BizCreditReportBO();
			reportBO.setCustId(cust.getId());
			reportBO.setCreditAmt(new BigDecimal(0));//信用额度
//			reportBO.setRemainAmt(new BigDecimal(0));//剩余额度
			reportBO.setRemainAmt(AppConst.DEFALT_CREDITLIMIT);//剩余额度
			reportBO.setApplyLoanNum(new Integer(0));//申请借款笔数
			reportBO.setApproveNum(new Integer(0));//成功借款笔数
			reportBO.setPayOffNum(new Integer(0));//还清笔数
			reportBO.setLoanTolAmt(new BigDecimal(0));//还款总金额
			reportBO.setOverdueAmt(new BigDecimal(0));//逾期总额
			reportBO.setOverdueNum(new Integer(0));//逾期次数
			reportBO.setPrincipaInterestAmt(new BigDecimal(0));//待还本息
			reportBO.setSerOverdueNum(new Integer(0));//严重逾期
			reportBO.setLoanBal(new BigDecimal(0));//借款余额
			getHibernateTemplate().save(reportBO);
			//推荐奖励
//			recommendationBS.recommendReward(refCustId);
		} catch (Exception e) {
			log.error("保存用户信息失败：========="+e.getMessage());
			throw new BizException("保存用户信息失败："+e.getMessage());
		}
		
	}

	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void saveUserLinkInfo(SysUser user) throws BizException {
		BizCustomerBO cust = this.custInfoBS.findCustByUserId(user.getUserId());
		boolean isNull = false;
		if(null == cust){
			cust = new BizCustomerBO();
			cust.setUserId(user.getUserId());
			cust.setSysCreateTime(new Date());
			
			isNull = true;
		}
		cust.setCustName(user.getUserName());
		cust.setSex(user.getSex());
		cust.setQq(user.getQq());
		cust.setMobileTelephone(user.getTel());
		cust.setEmail(user.getEmail());
		cust.setCertificateNum(user.getCardid());
		cust.setSysUpdateTime(new Date());
		if(StringUtils.isEmpty(cust.getRefereeCode())){
			cust.setRefereeCode(RamdomUtil.getRefereeCode());
		}
		
		getHibernateTemplate().saveOrUpdate(cust);
		
		BizAccountBO accountBO = this.custAccountBS.findCustAccount(cust.getId());
		if(null == accountBO){
			accountBO = new BizAccountBO();
			accountBO.setCustId(cust.getId());
			accountBO.setAccountBalAmt(BigDecimal.ZERO);
			accountBO.setFreezeBalAmt(BigDecimal.ZERO);
			accountBO.setUsableBalAmt(BigDecimal.ZERO);
			accountBO.setAccount(user.getUserCode());
			accountBO.setAccountTypeCd(AppConst.ACCOUNT_TYPE_CUST);
			accountBO.setPmAmt(new BigDecimal(0));
			accountBO.setSysCreateTime(new Date());
			accountBO.setSysUpdateTime(new Date());
			accountBO.setWorkItemId("0");
			getHibernateTemplate().save(accountBO);
		}
		
		BizCreditReportBO  reportBO = this.creditReportBS.findByCustInfo(cust.getId().toString());
		if(null==reportBO.getId()){
			getHibernateTemplate().save(reportBO);
		}
		/*try {
			//同步用户信息
			UserInfo userInfo = new UserInfo();
			
			userInfo.setMobile(user.getTel());
			userInfo.setLoginName(user.getUserCode());
//			userInfo.setRefereeName(registerInfo.getRecommender());
			userInfo.setBirthday(DateUtils.getDateString("yyyy-MM-dd", user.getBirthDate()));
			userInfo.setIdCard(user.getCardid());
			userInfo.setMobile(user.getTel());
			userInfo.setMail(user.getEmail());
			userInfo.setSex("0".equals(cust.getSex()) ? "F" : "M");
			if(isNull){
				userInfo.setPassword(user.getPassword());
				userInfo.setOperate(AppConst.USERINFOSYNC_OPERATE_REG);
			}else{
				userInfo.setOperate(AppConst.USERINFOSYNC_OPERATE_MOD);
			}
			this.userInfoSyncBS.userInfoSync(userInfo);
		} catch (Exception e) {
			log.error("用户信息同步失败");
		}*/
		
	}

	@Override
	public int getUserCountByIdCard(String IdCard) throws BizException {
		StringBuilder sb = new StringBuilder(
				"select count(userId) from SysUser where CARDID = '" + IdCard
						+ "'");
		return userDAO.count(sb.toString()) ;
		
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void updateUserOfIdCard(String name,String idCard,LoginInfo loginInfo)  throws BizException {
		StringBuilder sql = new StringBuilder("UPDATE SYS_USER SET user_name = '"+name+"',cardid='"+idCard+"' "
				+ " WHERE USER_CODE = '" + loginInfo.getUserCode()+ "'");
		this.getJdbcTemplate().execute(sql.toString());
		
		StringBuilder sqlstr = new StringBuilder("UPDATE biz_customer SET CERTIFICATE_TYPE_CD='0',CUST_NAME = '"+name+"',CERTIFICATE_NUM='"+idCard+"' "
				+ "WHERE id = '" + loginInfo.getCustId()+ "'");
		this.getJdbcTemplate().execute(sqlstr.toString());
	}
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void updateUserOfIdCard(String name,String idCard,String birthDate,LoginInfo loginInfo)  throws BizException {
		StringBuilder sql = new StringBuilder("UPDATE SYS_USER SET user_name = '"+name+"',cardid='"+idCard+"',BIRTH_DATE = '"+birthDate+"'"
				+ " WHERE USER_CODE = '" + loginInfo.getUserCode()+ "'");
		this.getJdbcTemplate().execute(sql.toString());
		
		StringBuilder sqlstr = new StringBuilder("UPDATE biz_customer SET CERTIFICATE_TYPE_CD='0',CUST_NAME = '"+name+"',CERTIFICATE_NUM='"+idCard+"',BIRTH_DATE = '"+birthDate+"'" 
				+ " WHERE id = '" + loginInfo.getCustId()+ "'");
		this.getJdbcTemplate().execute(sqlstr.toString());
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void updateIdCardByMobile(String name,String idCard,String mobile)  throws BizException {
		StringBuilder sql = new StringBuilder("UPDATE SYS_USER SET user_name = '"+name+"',cardid='"+idCard+"' WHERE TEL = '" + mobile+ "'  AND IS_ADMIN IS NULL");
		this.getJdbcTemplate().execute(sql.toString());
		
		StringBuilder sqlstr = new StringBuilder("UPDATE biz_customer SET CERTIFICATE_TYPE_CD='0',CUST_NAME = '"+name+"',CERTIFICATE_NUM='"+idCard+"' WHERE MOBILE_TELEPHONE = '" + mobile+ "'");
		this.getJdbcTemplate().execute(sqlstr.toString());
	}

	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void updateUserOfStatus(String status, String userCode)
			throws BizException {
		StringBuilder sql = new StringBuilder("UPDATE SYS_USER SET STATUS = '"+status+"' WHERE lower(USER_CODE) = '" + userCode.toLowerCase() + "'");
		this.getJdbcTemplate().execute(sql.toString());
		
	}

	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void updateUserOfPassword(String Password, Integer userId)
			throws BizException {
		StringBuilder sql = new StringBuilder("UPDATE SYS_USER SET PASSWORD = '"+Password+"' WHERE USER_ID = " + userId);
		this.getJdbcTemplate().execute(sql.toString());
	}
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void updatePasswordByMobile(String password, String mobile)
			throws BizException {
		StringBuilder sql = new StringBuilder("UPDATE SYS_USER SET PASSWORD = '"+password+"' WHERE TEL = '"+password+"' "
				+ "AND IS_ADMIN IS NULL");
		this.getJdbcTemplate().execute(sql.toString());
	}

	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void updateUserOfPhone(String Phone, Integer userId)
			throws BizException {
		StringBuilder sql = new StringBuilder("UPDATE SYS_USER SET tel = '"+Phone+"' WHERE USER_ID = " + userId );
		this.getJdbcTemplate().execute(sql.toString());
		
		sql = new StringBuilder("UPDATE biz_customer SET MOBILE_TELEPHONE = '"+Phone+"' WHERE USER_ID = " + userId );
		this.getJdbcTemplate().execute(sql.toString());
		
	}

	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void updateUserOfCashPassword(String cashPasswrod, Integer userId)
			throws BizException {
		StringBuilder sql = new StringBuilder("UPDATE SYS_USER SET trade_password = '"+cashPasswrod+"' WHERE USER_ID = " + userId );
		this.getJdbcTemplate().execute(sql.toString());
	}

	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void updateUserOfEmail(String email, String status,
			String userCode) throws BizException {
		StringBuilder sql = new StringBuilder("UPDATE SYS_USER SET email = '"+email+"',status='"+status+"' WHERE lower(USER_CODE) = '" + userCode.toLowerCase() + "'" );
		this.getJdbcTemplate().execute(sql.toString());
		
		sql = new StringBuilder("SELECT USER_ID FROM SYS_USER WHERE lower(USER_CODE) = ?");
		List<SysUser> list = commonQuery.findObjects(sql.toString(), new Object[]{userCode.toLowerCase()},SysUser.class);
		
		
		if(null == list){
			throw new BizException("找不到该客户");
		}
		
		sql = new StringBuilder("UPDATE biz_customer SET EMAIL = '"+email+"' WHERE USER_ID = " + list.get(0).getUserId());
		
		this.getJdbcTemplate().execute(sql.toString());
		
	}

	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void updateUserOfpassanswer(String passanswer,Integer userId) throws BizException {
		StringBuilder sql = new StringBuilder("UPDATE SYS_USER SET PASSANSWER = ?  WHERE USER_ID = ?" );
		this.getJdbcTemplate().update(sql.toString(),new Object[]{passanswer,userId});
	}

	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void updateUserOfPromotLevel(String promotLevel, Integer userId)
			throws BizException {
		StringBuilder sql = new StringBuilder("UPDATE SYS_USER SET PASSQUESTION = ?,INTRODUCTION=INTRODUCTION+1 WHERE USER_ID = ?" );
		this.getJdbcTemplate().update(sql.toString(),new Object[]{promotLevel,userId});
		
	}

	@Override
	public SysUser findUserByTel(String tel) {
		StringBuilder sb = new StringBuilder(
				"from SysUser where tel = ?");
		List list = userDAO.findByHQL(sb.toString(), new Object[]{tel}) ;
		SysUser sysUser = null;
		if(CollectionUtil.isNotEmpty(list)){
			sysUser = (SysUser) list.get(0);
		}
		return sysUser;
	}

	@Override
	public String getMd5Password(String userCode, String password)
			throws BizException {
		return MD5Utils.getMD5Str(password);
		
	}

	@Override
	public void updatePwdALL() throws BizException {

		SysUser sysUser = new SysUser();
		  String Md5password = "";
		  List list = this.findByHQL("From SysUser where userCode is not null");
		  for(int i=0;i<list.size();i++){
			  sysUser = (SysUser) list.get(i);
			  try {
				Md5password = this.getMd5Password(sysUser.getUserCode(), "123456");
				//System.out.println("userCode=="+sysUser.getUserCode()+"Md5password=="+Md5password);
				this.updateUserOfPassword(Md5password, sysUser.getUserId());
			} catch (BizException e) {
				// TODO Auto-generated catch block
				 
			}
	 }
	}

	@Override
	public SysUser findUserByAll(String userCode) {
		try {
			String queryString = "from SysUser where lower(userCode) = ? or lower(email) = ? or tel = ?";
			List list = getHibernateTemplate().find(queryString, new Object[]{userCode.toLowerCase(),userCode.toLowerCase(),userCode} );
			if (list != null && !list.isEmpty()) {
				return (SysUser) list.get(0);
			}
			return null;
		} catch (Exception e) {
			 
		}
		return null;
	}

	public IUserInfoSyncBS getUserInfoSyncBS() {
		return userInfoSyncBS;
	}

	public void setUserInfoSyncBS(IUserInfoSyncBS userInfoSyncBS) {
		this.userInfoSyncBS = userInfoSyncBS;
	}
}
