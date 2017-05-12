package com.qfw.common.bizservice.PmService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qfw.common.bizservice.IParamBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.dao.PmDao.PmDao;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.BizPmDetailBO;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizAccountBO;
import com.qfw.model.bo.BizAccountDetailBO;

@Service("pmService")
public class PmService extends BaseServiceImpl {
	
	@Autowired
	private PmDao pmDao;
	
	@Autowired
	private IParamBS iParam;
	
	/**
	 * pm币变动调用此方法
	 * @param pmType pm类型
	 * @param account_id 账户表主键
	 * @param pmValue 增加的PM币，如减少（-9）
	 * @throws BizException
	 */
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void insertPmTrate(String pmType,Integer account_id,BigDecimal pmValue) throws BizException{
		//pm币类型
		if(null == pmType || "".equals(pmType) ){
			throw new BizException("pm币类型不能为空！");
		}
		//更新PM币
		BizAccountBO bizAccountBO= (BizAccountBO) pmDao.findById(BizAccountBO.class, account_id);
		BigDecimal pmAmt = bizAccountBO.getPmAmt().add(pmValue) ;
		bizAccountBO.setPmAmt(pmAmt);
		updateOperator(bizAccountBO);
		pmDao.update(bizAccountBO);
		
		//插入PM交易流水
		BizPmDetailBO bizPmDetailBO = new BizPmDetailBO(); 
		bizPmDetailBO.setCustId(bizAccountBO.getCustId());
		bizPmDetailBO.setUserCode(bizAccountBO.getAccount());
		bizPmDetailBO.setPmAmt(pmValue);
		bizPmDetailBO.setTxDate(new Date());
		bizPmDetailBO.setPmTypeCd(pmType);
		bizPmDetailBO.setSysCreateUser(0);//自动化操作
		bizPmDetailBO.setSysUpdateUser(0);
		bizPmDetailBO.setSysCreateTime(new Date());
		bizPmDetailBO.setSysUpdateTime(new Date());
		pmDao.save(bizPmDetailBO);
		
	}
	
	
	
	/**
	 * 通过推广获得的pm币
	 * @param promLevel 推广等级
	 * @return
	 * @throws BizException 
	 */
	public BigDecimal getPmValue(String promLevel) throws BizException{
		 
		  if(null == promLevel || "".equals(promLevel) ){
			  throw new BizException("会员推荐等级不能为空！");
		  }
		  //根据推广等级获取Pm值
		  String pmvalue = iParam.getParam(promLevel+"_PM");
		  if(null == pmvalue && "".equals(pmvalue) ){
			 throw new BizException("会员推荐等级不能为空！");
		  }
		  return new BigDecimal(pmvalue);
	}
	
	
	/**
	 * 通过投资获得的pm币
	 * @param buyAmt 购买金额  
	 * @return
	 * @throws BizException 
	 */
	public BigDecimal getPmValue(BigDecimal buyAmt) throws BizException{

		if(buyAmt == null ){
			return new BigDecimal(0); 
		}
		//获取投资者pm转化比率
		String buy_pm_rate = iParam.getParam("BUY_PM_RATE");
		BigDecimal pm_rate = new BigDecimal(buy_pm_rate);
		BigDecimal pm =buyAmt.multiply(pm_rate).setScale(2, BigDecimal.ROUND_HALF_UP);
		return pm; 
	}
	

	public PmDao getPmDao() {
		return pmDao;
	}

	public void setPmDao(PmDao pmDao) {
		this.pmDao = pmDao;
	}

	public IParamBS getiParam() {
		return iParam;
	}

	public void setiParam(IParamBS iParam) {
		this.iParam = iParam;
	}
	
	
	
}
