package com.qfw.batch.job;

import java.util.Date;

import com.qfw.batch.bizservice.schedule.common.impl.BatchDependJob;
import com.qfw.bizservice.payout.IWithdrawalsPayoutBS;
import com.qfw.common.util.ApplicationContextUtil;

/**
 * 提现查询
 * @author 
 *
 */
public class WithdrawBatchJob extends BatchDependJob {
	
	
	@Override
	public boolean isCanBeRun() throws Exception {
		return true;
	}

	@Override
	public String runJob() throws Exception {
//		Date batchDate = new Date();
		
		IWithdrawalsPayoutBS withdrawalsPayoutBS = (IWithdrawalsPayoutBS)ApplicationContextUtil.getBean("withdrawalsPayoutBS");
		withdrawalsPayoutBS.withdrawalOrderHandle();
		return "提现处理跑批成功";
	}

	
}
