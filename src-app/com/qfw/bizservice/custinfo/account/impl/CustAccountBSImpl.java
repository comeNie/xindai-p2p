package com.qfw.bizservice.custinfo.account.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qfw.bizservice.custinfo.account.ICustAccountBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.CollectionUtil;
import com.qfw.common.util.web.ViewOper;
import com.qfw.dao.custinfo.account.ICustAccountDAO;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizAccountBO;
import com.qfw.model.bo.BizAccountDetailBO;
import com.qfw.model.vo.custinfo.account.AccountRequestVO;
import com.qfw.model.vo.custinfo.account.AccountResponseVO;
import com.qfw.model.vo.custinfo.account.PMInfoVO;
import com.qfw.model.vo.custinfo.account.PMSearchVO;

@Service("custAccountBS")
public class CustAccountBSImpl extends BaseServiceImpl implements ICustAccountBS{

	@Autowired
	private ICustAccountDAO custAccountDAO;
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	

	@Override
	public AccountResponseVO findAccountDetailInfoByid(Integer id) {
		return this.custAccountDAO.findDetailById(id);
	}
	
	@Override
	public List<AccountResponseVO> findDetailInfoByParams(AccountRequestVO vo, int first, int pageSize)
			throws BizException {
		try {
			if(null == vo ){
				throw new BizException("账号查询参数不能为空!");
			}
			return this.custAccountDAO.findDetailPagesByParams(vo, first, pageSize);
		} catch (Exception e) {
			log.error("根据参数查询账号信息异常："+e);
			throw new BizException(e);
		}
	}
	
	@Override
	public int findCountDeatilByParams(AccountRequestVO vo) throws BizException {
		return this.custAccountDAO.findCountDetailByParams(vo);
	}

	@Override
	public BizAccountBO findAccountInfoByid(Integer id) {
		return (BizAccountBO)this.custAccountDAO.findById(BizAccountBO.class, id);
	}
	/**
	 * 获取会员账号
	 * @param custId
	 * @return
	 */
	public BizAccountBO findCustAccount(Integer custId) throws BizException{
		try{
			BizAccountBO accountBO = null;
			String sql = "from BizAccountBO where accountTypeCd = ? and custId = ?";
			List<BizAccountBO> accounts = getHibernateTemplate().find(sql, AppConst.ACCOUNT_TYPE_CUST,custId);
			if(CollectionUtil.isNotEmpty(accounts)){
				accountBO = accounts.get(0);
			}
			return accountBO;
		}catch(Exception e){
			log.error("获取客户账号失败,客户id"+custId, e);
			throw new BizException("获取客户账号失败");
		}
		
	}
	@Override
	public BizAccountBO findInfoByAccount(AccountRequestVO vo) throws BizException{
		try {
			List<BizAccountBO> boList = this.findInfoByParams(vo);
			if(null == boList || boList.size()!=1){
				throw new BizException("账号信息查询失败:查询结果不唯一!");
			}
			return boList.get(0);
		} catch (Exception e) {
			log.error("账号信息查询异常："+e);
			throw new BizException(e);
		}
	}

	@Override
	public List<BizAccountBO> findInfoByParams(AccountRequestVO vo) throws BizException{
		try {
			if(null == vo ){
				throw new BizException("账号查询参数不能为空!");
			}
			return this.custAccountDAO.findInfoByParams(vo);
		} catch (Exception e) {
			log.error("根据参数查询账号信息异常："+e);
			throw new BizException(e);
		}
	}
	
	@Override
	public List<BizAccountBO> findInfoPagesByParams(AccountRequestVO vo, int first, int pageSize) throws BizException{
		try {
			if(null == vo ){
				throw new BizException("账号查询参数不能为空!");
			}
			return this.custAccountDAO.findInfoPagesByParams(vo, first, pageSize);
		} catch (Exception e) {
			log.error("根据参数查询账号信息异常："+e);
			throw new BizException(e);
		}
	}
	
	@Override
	public int findCountInfoByParams(AccountRequestVO vo) throws BizException{
		return this.custAccountDAO.findCountInfoByParams(vo);
	}
	
	@Override
	public BigDecimal queryUsableAmt(AccountRequestVO vo, BizAccountBO bo)
			throws BizException {
		
		if(null == bo ){
			BigDecimal result = BigDecimal.ZERO;
			List<BizAccountBO> boList = this.findInfoByParams(vo);
			if(null == boList || boList.size() <=0){
				return result;
			}else{
				for (BizAccountBO temp : boList) {
					result = result.add(temp.getAccountBalAmt().subtract(temp.getFreezeBalAmt()));
				}
				return result;
			}
		}else{
			return bo.getAccountBalAmt().subtract(bo.getFreezeBalAmt());
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public BigDecimal updateAccountAmt(AccountRequestVO vo) throws BizException {
		
		List<BizAccountBO> boList = this.findInfoByParams(vo);
		if(null == boList || boList.size() <=0){
			throw new BizException("账号金额更新失败：不支持多笔账号同时更新!");
		}
		BizAccountBO bo = boList.get(0);
		
		if(null == vo.getDealType() || vo.getDealType().length() <=0){
			throw new BizException("账号更新时，金额交易类型不能为空!");
		}
		if(null == vo.getDealAmt()){
			throw new BizException("账号更新时，交易金额不能为空!");
		}
		
		if(AppConst.ACCOUNT_AMT.equals(vo.getDealType())){
			bo.setAccountBalAmt(bo.getAccountBalAmt().add(vo.getDealAmt()));
		}else if(AppConst.ACCOUNT_AMT_FREEZE.equals(vo.getDealType())){
			bo.setFreezeBalAmt(bo.getFreezeBalAmt().add(vo.getDealAmt()));
		}else if(AppConst.ACCOUNT_AMT_UNFREEZE.equals(vo.getDealType())){//释放冻结
			if((bo.getFreezeBalAmt().subtract(vo.getDealAmt())).compareTo(BigDecimal.ZERO) < 0){
				bo.setFreezeBalAmt(BigDecimal.ZERO);
			}else{
				bo.setFreezeBalAmt(bo.getFreezeBalAmt().subtract(vo.getDealAmt()));
			}
		}
		
		BigDecimal amt = bo.getAccountBalAmt().subtract(bo.getFreezeBalAmt());
		if(amt.compareTo(BigDecimal.ZERO)<0)
			amt = BigDecimal.ZERO;
		bo.setUsableBalAmt(amt);
		
		this.custAccountDAO.update(bo);
		BizAccountDetailBO detailBO = this.tranFromDetail(bo, vo);
		this.custAccountDAO.save(detailBO);
		
		return this.queryUsableAmt(vo, bo);
	}
	
	@Override
	public BigDecimal updateAccountAmt(BizAccountBO bo,AccountRequestVO vo) throws BizException {
		
		if(null == vo.getDealType() || vo.getDealType().length() <=0){
			throw new BizException("账号更新时，金额交易类型不能为空!");
		}
		if(null == vo.getDealAmt()){
			throw new BizException("账号更新时，交易金额不能为空!");
		}
		
		if(AppConst.ACCOUNT_AMT.equals(vo.getDealType())){
			bo.setAccountBalAmt(bo.getAccountBalAmt().add(vo.getDealAmt()));
		}else if(AppConst.ACCOUNT_AMT_FREEZE.equals(vo.getDealType())){
			bo.setFreezeBalAmt(bo.getFreezeBalAmt().add(vo.getDealAmt()));
		}
		
		BigDecimal amt = bo.getAccountBalAmt().subtract(bo.getFreezeBalAmt());
		if(amt.compareTo(BigDecimal.ZERO)<0)
			amt = BigDecimal.ZERO;
		bo.setUsableBalAmt(amt);
		
		this.custAccountDAO.update(bo);
		BizAccountDetailBO detailBO = this.tranFromDetail(bo, vo);
		this.custAccountDAO.save(detailBO);
		
		return this.queryUsableAmt(vo, bo);
	}
	
	/**
	 * 封装明细信息
	 * @param accountBO
	 * @return
	 */
	private BizAccountDetailBO tranFromDetail(BizAccountBO accountBO,AccountRequestVO vo){
		BizAccountDetailBO detailBO = new BizAccountDetailBO();
		detailBO.setAccount(accountBO.getAccount());
		detailBO.setAccountBalAmt(accountBO.getAccountBalAmt());
		detailBO.setCustId(accountBO.getCustId());
		detailBO.setFreezeBalAmt(accountBO.getFreezeBalAmt());
		detailBO.setPmAmt(accountBO.getPmAmt());
		detailBO.setTxAmt(vo.getDealAmt());
		detailBO.setTxDate(new Date());
		detailBO.setTxNo(vo.getTxNo());
		detailBO.setEventTypeCd(vo.getEventTypeCd());
		detailBO.setSysCreateTime(accountBO.getSysCreateTime());
		detailBO.setSysCreateUser(accountBO.getSysCreateUser());
		detailBO.setSysUpdateTime(accountBO.getSysUpdateTime());
		detailBO.setSysUpdateUser(accountBO.getSysUpdateUser());
		return detailBO;
	}

	/**
	 * 添加账户信息
	 * @param user
	 * @param deptIds
	 */
	public void addCustAccount(BizAccountBO  account) throws BizException{
		account.setSysCreateTime(new Timestamp(new Date().getTime()));
		account.setSysCreateUser(ViewOper.getUser().getUserId());
		account.setSysUpdateTime(new Timestamp(new Date().getTime()));
		account.setSysUpdateUser(ViewOper.getUser().getUserId());
		account.setAccountBalAmt(new BigDecimal(0.00));
		account.setFreezeBalAmt(new BigDecimal(0.00));
		account.setUsableBalAmt(new BigDecimal(0.00));
		account.setPmAmt(new BigDecimal(0.00));
		account.setWorkItemId("0");
		account.setAccountTypeCd("0");
		getHibernateTemplate().saveOrUpdate(account);
	}
	
	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	@Override
	public List<PMInfoVO> findPMInfoPagesByParams(PMSearchVO vo, int first,
			int pageSize) throws BizException {
		try {
			if(null == vo ){
				throw new BizException("pm币查询参数不能为空!");
			}
			return this.custAccountDAO.findPMInfoPagesByParams(vo, first, pageSize);
		} catch (Exception e) {
			log.error("根据参数查询pm币信息异常："+e);
			throw new BizException(e);
		}
	}
	
	@Override
	public int getCountByVO(PMSearchVO searchVO) {
		int num = 0;
		try {
			num = this.custAccountDAO.getCountByVO(searchVO);
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.error(e.getMessage());
			}
		}
		return num;
	}

}
