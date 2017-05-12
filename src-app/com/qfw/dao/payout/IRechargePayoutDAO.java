package com.qfw.dao.payout;

import java.util.List;

import com.qfw.common.dao.IBaseDAO;
import com.qfw.common.exception.BizException;
import com.qfw.model.bo.BizRechargeCardBO;
import com.qfw.model.vo.payout.RechargeVO;

/**
 * 理财卡dao
 *
 * @author kyc
 */
public interface IRechargePayoutDAO  extends IBaseDAO{

	/**
	 * 根据参数获取bo
	 * @param vo
	 * @param statusList 
	 * @return
	 * @throws BizException
	 */
	public List<BizRechargeCardBO> queryBOByParams(RechargeVO vo,List<String> statusList) throws BizException;
}
