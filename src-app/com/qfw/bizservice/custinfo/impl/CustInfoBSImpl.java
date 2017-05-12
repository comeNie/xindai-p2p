package com.qfw.bizservice.custinfo.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.bizservice.custinfo.account.ICustAccountBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.bizservice.permission.IUserBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.CollectionUtil;
import com.qfw.common.util.web.ViewOper;
import com.qfw.dao.custinfo.ICustInfoDAO;
import com.qfw.model.bo.BizAccountBO;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.vo.custinfo.CustInfoVO;
import com.qfw.model.vo.custinfo.SearchCustInfoVO;
import com.qfw.model.vo.custinfo.basicInfo.CustBasicInfoVO;
import com.qfw.platform.model.json.Pager;

@Service("custInfoBS")
public class CustInfoBSImpl extends BaseServiceImpl implements ICustInfoBS {
	
	@Autowired
	private ICustInfoDAO custInfoDAO;
	@Autowired
	private ICustAccountBS custAccountBS;
	@Autowired
	private IUserBS userBS;
	
	private Logger log = LogFactory.getInstance().getBusinessLogger();

	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public List<CustInfoVO> findCustInfo(SearchCustInfoVO searchCustInfoVO) throws BizException {
		
		return this.custInfoDAO.findCustInfo(searchCustInfoVO);
	}

	@Override
	public List<CustInfoVO> findCustInfoPagesByVO(SearchCustInfoVO searchCustInfoVO, int first, int pageSize) {
		try {
			List<CustInfoVO> custInfoVOs = this.custInfoDAO.findCustInfoPagesByVO(searchCustInfoVO, first, pageSize);
			if (custInfoVOs != null && !custInfoVOs.isEmpty()) {
				return custInfoVOs;
			}
		} catch (BizException e) {
			if (log.isDebugEnabled()) {
				log.error(e.getMessage());
			}
		}
		return null;
	}
	@Override
	public Pager findMyRecommend(int requestPage, int pageSize, Integer custId) throws BizException{
		return custInfoDAO.findMyRecommend(requestPage, pageSize, custId);
	}

	@Override
	public int findCustInfoCountByVO(SearchCustInfoVO searchCustInfoVO) {
		int count = 0;
		try {
			count = this.custInfoDAO.findCustInfoCountByVO(searchCustInfoVO);
		} catch (BizException e) {
			if (log.isDebugEnabled()) {
				log.error(e.getMessage());
			}
		}
		return count;
	}
	
	public int countReferee(Integer custId) {
		int count = 0;
		try {
			count = this.custInfoDAO.countReferee(custId);
		} catch (BizException e) {
			if (log.isDebugEnabled()) {
				log.error(e.getMessage());
			}
		}
		return count;
	}
	
	/**
	 * 根据客户编号查询客户相关信息
	 * @param custId
	 * @return
	 * @throws BizException
	 */
	public CustInfoVO findCustInfoById (Integer custId) {
		try {
			List<CustInfoVO> custInfoVOs = this.custInfoDAO.findCustInfoById(custId);
			if (custInfoVOs != null && !custInfoVOs.isEmpty()) {
				return custInfoVOs.get(0);
			}
		} catch (BizException e) {
			if (log.isDebugEnabled()) {
				log.error(e.getMessage());
			}
		}
		return null;
		
	}

	@Override
	public CustBasicInfoVO findCustBasicInfo(Integer custId) {
		return null;
	}
	
	
	/**
	 * 添加用户信息
	 * @param user
	 * @param deptIds
	 */
	public void addCust(BizCustomerBO  cust) throws BizException{
		cust.setSysCreateTime(new Timestamp(new Date().getTime()));
		cust.setSysCreateUser(ViewOper.getUser().getUserId());
		cust.setSysUpdateTime(new Timestamp(new Date().getTime()));
		cust.setSysUpdateUser(ViewOper.getUser().getUserId());
		getHibernateTemplate().saveOrUpdate(cust);
	}

	/**
	 * 更新用户信息
	 * @param user
	 * @param deptIds
	 */
	public void updateCust(BizCustomerBO  cust) throws BizException{
		cust.setSysCreateTime(new Timestamp(new Date().getTime()));
		cust.setSysCreateUser(ViewOper.getUser().getUserId());
		cust.setSysUpdateTime(new Timestamp(new Date().getTime()));
		cust.setSysUpdateUser(ViewOper.getUser().getUserId());
		getHibernateTemplate().update(cust);
		
		if(null!=cust.getUserId()&&cust.getUserId()>0){
			SysUser user =  this.userBS.findUserByUserId(cust.getUserId());	
			user.setTel(cust.getMobileTelephone());
			user.setSex(cust.getSex());
			user.setCardid(cust.getCertificateNum());
			getHibernateTemplate().update(user);
		}
		
	}
	
	/**
	 * 根据用户ID 查找对应的客户ID
	 * @param userId
	 * @return
	 * @throws BizException 
	 */
	public CustInfoVO findCust(Integer userId) throws BizException{
		List<CustInfoVO> custInfoVOs = this.custInfoDAO.findCustInfoByUserId(userId);
		if (custInfoVOs != null && !custInfoVOs.isEmpty()) {
			return custInfoVOs.get(0);
		}
		return null;
		
	}
	
	public BizCustomerBO findCustById(Integer id){
		BizCustomerBO cust = new BizCustomerBO();
		cust = (BizCustomerBO) custInfoDAO.findById(BizCustomerBO.class, id);
		return cust;
	}
	
	/**
	 * 通过用户id查询客户信息
	 * @param userId
	 * @return
	 * @throws BizException
	 */
	public BizCustomerBO findCustByUserId(Integer userId) throws BizException{
		try{
			List list = getHibernateTemplate().find("from BizCustomerBO where userId = ?",userId);
			if(CollectionUtil.isNotEmpty(list)){
				return (BizCustomerBO) list.get(0);
			}
		}catch(Exception e){
			throw new BizException("客户信息查询失败");
		}
		return null;
	}
	
	public BizCustomerBO findCustByRefereeCode(String refereeCode) throws BizException{
		try{
			List list = getHibernateTemplate().find("from BizCustomerBO where refereeCode = ?",refereeCode);
			if(CollectionUtil.isNotEmpty(list)){
				return (BizCustomerBO) list.get(0);
			}
		}catch(Exception e){
			throw new BizException("客户信息查询失败");
		}
		return null;
	}
	
	public List<BizCustomerBO> findCustomer(String mobile) throws BizException {
		try{
			List<BizCustomerBO> list = getHibernateTemplate().find("from BizCustomerBO where mobileTelephone = ?",mobile);
			return list;
		}catch(Exception e){
			throw new BizException("客户信息查询失败");
		}
	}
	
	public ICustInfoDAO getCustInfoDAO() {
		return custInfoDAO;
	}

	public void setCustInfoDAO(ICustInfoDAO custInfoDAO) {
		this.custInfoDAO = custInfoDAO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void saveCust(BizCustomerBO cust) throws BizException {
		try {
			this.addCust(cust);
			BizAccountBO accountBO = new BizAccountBO();
	    	accountBO.setAccount(cust.getId().toString());
	    	accountBO.setCustId(cust.getId());
	    	custAccountBS.addCustAccount(accountBO);
		} catch (Exception e) {
			log.error("客户信息新增失败", e);
			throw new BizException(e);
		}
	}
	
	public int getCustCountByIdCard(String IdCard) throws BizException {
		StringBuilder sb = new StringBuilder(
				"select count(id) from BizCustomerBO where certificateNum = '" + IdCard
						+ "' and certificateTypeCd = '0'" );
		return custInfoDAO.count(sb.toString()) ;
		
	}
	@Override
	public CustInfoVO getPlatCustInfo() throws BizException {
		CustInfoVO custInfoVO = null;
		List<CustInfoVO> list = custInfoDAO.sumAccount();
		if(list != null && list.size() > 0){
			custInfoVO = list.get(0);
		}else{
			custInfoVO = new CustInfoVO();
			custInfoVO.setAccountBalAmt(BigDecimal.ZERO);
			custInfoVO.setUsableBalAmt(BigDecimal.ZERO);
		}
		custInfoVO.setCountCust(new Long(custInfoDAO.countCust()));
		custInfoVO.setCountVip(new Long(custInfoDAO.countVip()));
		custInfoVO.setRecommend(new Long(custInfoDAO.countRecommend()));
		return custInfoVO;
	}
}
