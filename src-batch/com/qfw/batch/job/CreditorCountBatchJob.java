package com.qfw.batch.job;

import java.util.Date;

import com.qfw.batch.bizservice.schedule.common.impl.BatchDependJob;
import com.qfw.bizservice.batch.IBatchBS;
import com.qfw.common.util.ApplicationContextUtil;
/**
 * 推荐投资统计批处理服务
 *
 */
public class CreditorCountBatchJob extends BatchDependJob{

	@Override
	public boolean isCanBeRun() throws Exception {
		return true;
	}

	@Override
	public String runJob() throws Exception {
		Date batchDate = new Date();
		IBatchBS batchBS = (IBatchBS)ApplicationContextUtil.getBean("batchBS");
		batchBS.batchCreditorCount(batchDate);
		return "推荐投资统计批处理服务执行成功";
	}
	
}
