package com.qfw.bizservice.payout;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.model.bo.BizAccountBO;
import com.qfw.model.bo.BizWithdrawalApplyBO;
import com.qfw.model.vo.payout.WithdrawalsVO;
import com.qfw.model.vo.transaction.TranxCon;

/**
 *
 */
public interface IWithdrawalApplyBS extends IBaseService {
	
	public void saveWithdrawalApply(TranxCon tranxCon,WithdrawalsVO withdrawalsVO) throws BizException;
	
	public void updateStatusOf(String status,String reqSn) throws BizException;
	
	public void updateQueryTime() throws BizException;
	
	public void drawalTransfer(WithdrawalsVO withdrawalsVO,BizAccountBO custAcc) throws BizException;
	
	public void withdrawalFailHandle(BizWithdrawalApplyBO bo) throws BizException;
	
}
