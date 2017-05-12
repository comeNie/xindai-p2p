package com.qfw.batch.bizservice.schedule.common.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.qfw.batch.bizservice.IBatchJobBS;
import com.qfw.batch.bizservice.schedule.BatchServiceImpl;
import com.qfw.batch.bizservice.schedule.common.IBatchJob;
import com.qfw.batch.bizservice.util.BeanContextUtil;
import com.qfw.batch.bizservice.util.DateUtil;
import com.qfw.batch.model.JobStatus;
import com.qfw.batch.model.bo.BatchJobInstance;

/**
 *
 */
public class DailyBatchJob implements IBatchJob {

	private Logger logger = Logger.getLogger(DailyBatchJob.class);
	private Integer jobInstanceId;
	@Override
	public void cancel() {
		IBatchJobBS batchJobBS = (IBatchJobBS) BeanContextUtil.getBean("batchJobBS");
		List<BatchJobInstance> childrenJob = batchJobBS.findAllBatchJobInstanceByParentId(jobInstanceId);
		for (BatchJobInstance childJob : childrenJob) {
			
		}

	}

	@Override
	public void run() {
		IBatchJobBS batchJobBS = (IBatchJobBS) BeanContextUtil.getBean("batchJobBS");
		BatchJobInstance jobInst = batchJobBS.findBatchJobInstance(jobInstanceId);
		
		jobInst.setStartTime(DateUtil.getYmdhms(new Date()));//设置批开始启动时间
		logger.info("### Run ###----> 应用准备好，任务ID为[" + jobInst.getJobId() + "] 名称为[" +  jobInst.getName() + "] 的任务启动");
		jobInst.setStatus(String.valueOf(JobStatus.RUNNING));
		batchJobBS.updateObject(jobInst);//先更新任务为正在跑批状态
		logger.info("### Run ###----> 当前ID将运行，任务ID为[" + jobInst.getJobInstanceId() + "] 名称为[" + jobInst.getName() + "]");
		logger.info("正在运行的任务有："+BatchServiceImpl.threadRunJobMap.size());
		jobInst = batchJobBS.findBatchJobInstance(jobInstanceId);
		jobInst.setMemo("0");//设置任务返回结果
		jobInst.setProgress("0");
		batchJobBS.updateObject(jobInst);
		BatchServiceImpl.threadRunJobMap.remove(jobInstanceId);
		/*try {
			Thread.sleep(1000L);//睡眠1秒钟
			List<BatchJobInstance> childrenJob = batchJobBS.findAllBatchJobInstanceByParentId(jobInstanceId);
			int progressCount = 0;
			for (BatchJobInstance childJob : childrenJob) {
				String status = childJob.getStatus();
			}
		} catch (Exception e) {
			
			jobInst = batchJobBS.findBatchJobInstance(jobInstanceId);
			jobInst.setMemo(e.getMessage());//设置任务返回结果
			jobInst.setEndTime(DateUtil.getYmdhms(new Date()));//设置任务结束时间
			jobInst.setStatus(String.valueOf(JobStatus.FAILURE));//批处理执行成功
			batchJobBS.updateObject(jobInst);
			
			logger.error("任务执行失败",e);
			logger.info("### Run ###---->应用出错抛出异常，任务ID为[" + jobInst.getJobId() + "] 名称为[" + jobInst.getName() + "] 的任务失败");
			
		}*/
	}

	@Override
	public void setJobInstanceId(Integer id) {
		this.jobInstanceId = id;
	}
	
}
