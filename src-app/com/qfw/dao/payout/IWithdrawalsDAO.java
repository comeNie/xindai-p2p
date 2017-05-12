package com.qfw.dao.payout;

import java.util.List;

import com.qfw.common.dao.IBaseDAO;
import com.qfw.model.bo.BizWithdrawalApplyBO;
import com.qfw.model.bo.BizWithdrawalsBO;
import com.qfw.model.vo.payout.WithdrawalsVO;

public interface IWithdrawalsDAO extends IBaseDAO {

	/**
	 * 根据参数查询提现信息
	 * @param vo
	 * @return
	 */
	public  List<BizWithdrawalsBO> findByParams(WithdrawalsVO vo) ;
	
	
	public List<BizWithdrawalApplyBO> findApplyByParams(WithdrawalsVO vo);
}
