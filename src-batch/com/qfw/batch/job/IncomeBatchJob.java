package com.qfw.batch.job;

import java.util.Date;

import com.qfw.batch.bizservice.schedule.common.impl.BatchDependJob;
import com.qfw.bizservice.batch.IBatchBS;
import com.qfw.common.util.ApplicationContextUtil;
/**
 * 收益批处理服务
 * @author kindion
 *
 */
public class IncomeBatchJob extends BatchDependJob{

	@Override
	public boolean isCanBeRun() throws Exception {
		return true;
	}

	@Override
	public String runJob() throws Exception {
		Date batchDate = new Date();
		//System.out.println("收益分配批处理服务开始执行,当前日期为:"+batchDate);
		IBatchBS batchBS = (IBatchBS)ApplicationContextUtil.getBean("batchBS");
		batchBS.batchIncomeForRepay(batchDate);
		//System.out.println("收益分配批处理服务结束执行");
		return "收益分配批处理服务执行成功";
	}
	
}
