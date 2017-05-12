package com.qfw.batch.job;

import java.util.Date;

import com.qfw.batch.bizservice.schedule.common.impl.BatchDependJob;
import com.qfw.bizservice.batch.IBatchBS;
import com.qfw.common.util.ApplicationContextUtil;
/**
 * 债权转让发布失效批处理服务
 * @author kindion
 *
 */
public class InvalidCreditorRightTranBatchJob extends BatchDependJob{

	@Override
	public boolean isCanBeRun() throws Exception {
		return true;
	}

	@Override
	public String runJob() throws Exception {
		Date batchDate = new Date();
		//System.out.println("债权转让发布失效批处理开始执行,当前日期为:"+batchDate);
		IBatchBS batchBS = (IBatchBS)ApplicationContextUtil.getBean("batchBS");
		batchBS.batchInvalidCreditorRightTran(batchDate);
		//System.out.println("债权转让发布失效批处理结束执行");
		return "债权转让发布失效批处理执行成功";
	}
	
}
