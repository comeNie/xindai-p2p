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
 * 强制依赖任务
 * 依赖任务失败，该任务及跟着任务失败
 * @author Jie
 *
 */
public abstract class BatchDependJob implements IBatchJob {

	private Logger logger = Logger.getLogger(BatchJob.class);
	private Integer jobInstanceId;
	private Map paramMap = null;
	@Override
	public void cancel() {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		IBatchJobBS batchJobBS = (IBatchJobBS) BeanContextUtil.getBean("batchJobBS");
		BatchJobInstance jobInst = batchJobBS.findBatchJobInstance(jobInstanceId);
		
		/*
		 * 判断依赖任务是否执行成功
		 * 如果依赖任务失败，则改任务强制失败。
		 */
		List<BatchJobInstance> jobInstDepends = batchJobBS.findBatchJobInstancesDepend(jobInst.getJobInstanceId());
		if(jobInstDepends != null && !jobInstDepends.isEmpty()){
			for (BatchJobInstance batchJobInstance : jobInstDepends) {
				if(String.valueOf(JobStatus.STOPPED).equals(batchJobInstance.getStatus()) || String.valueOf(JobStatus.FAILURE).equals(batchJobInstance.getStatus())
				|| String.valueOf(JobStatus.TEMINATED).equals(batchJobInstance.getStatus())|| String.valueOf(JobStatus.NOSUCCESS).equals(batchJobInstance.getStatus())){
					jobInst.setStatus(batchJobInstance.getStatus());
					batchJobBS.updateObject(jobInst);
					//移除正在执行任务
					BatchServiceImpl.threadRunJobMap.remove(jobInst.getJobInstanceId());
					return;
				}
			}
		}
		/*
		 * 判断组任务是否执行失败
		 */
		BatchJobInstance parentJobInst =  batchJobBS.findBatchJobInstance(jobInst.getParentJobId());
		if(String.valueOf(JobStatus.STOPPED).equals(parentJobInst.getStatus())){
			jobInst.setStatus(parentJobInst.getStatus());
			batchJobBS.updateObject(jobInst);
			//移除正在执行任务
			BatchServiceImpl.threadRunJobMap.remove(jobInst.getJobInstanceId());
			return;
		}
		
		jobInst.setStartTime(DateUtil.getYmdhms(new Date()));//设置批开始启动时间
		
		try {
			//获取job参数
			Map<String, Object> params = batchJobBS.getBatchJobParams(jobInst.getJobId());
			this.setParamMap(params);
			if(isCanBeRun()){
				logger.info("### Run ###----> 应用准备好，任务ID为[" + jobInst.getJobId() + "] 名称为[" +  jobInst.getName() + "] 的任务启动");
				jobInst.setStatus(String.valueOf(JobStatus.RUNNING));
				batchJobBS.updateObject(jobInst);//先更新任务为正在跑批状态
				logger.info("### Run ###----> 当前ID将运行，任务ID为[" + jobInst.getJobInstanceId() + "] 名称为[" + jobInst.getName() + "]");
				logger.info("正在运行的任务有："+BatchServiceImpl.threadRunJobMap.size());
				String runStr = runJob();
				completeJob(String.valueOf(JobStatus.SUCCESS), runStr);
				logger.info("### Run ###----> return String = " + runStr);
			}else{
				jobInst.setBatchSeqNo(jobInst.getBatchSeqNo()+1);
				logger.info("### Run ###---->[第 " + (jobInst.getBatchSeqNo() + 1) + " 次]应用未准备好，任务ID为[" + jobInst.getJobId() + "] 名称为[" + jobInst.getName() + "] 的任务不能运行，稍后再试");
				batchJobBS.updateObject(jobInst);
			}
		} catch (Exception e) {
			completeJob(String.valueOf(JobStatus.FAILURE), e.getMessage());
			logger.error("任务执行失败",e);
			logger.info("### Run ###---->应用出错抛出异常，任务ID为[" + jobInst.getJobId() + "] 名称为[" + jobInst.getName() + "] 的任务失败");
			
		} finally{
			//移除正在执行任务
			BatchServiceImpl.threadRunJobMap.remove(jobInst.getJobInstanceId());
		}
	}

	@Override
	public void setJobInstanceId(Integer id) {
		this.jobInstanceId = id;
	}
	/**
	 * 判断业务应用是否满足执行所需的业务条件
	 * 返回true允许跑批，返回false不允许跑批
	 * @return
	 * @throws Exception
	 */
	public abstract boolean isCanBeRun() throws Exception;
	/**
	 * 任务启动的抽象方法，需要继承此类的派生类来实现此方法
	 * @return
	 * @throws Exception
	 */
	public abstract String runJob() throws Exception;

	/**
	 * 完成跑批
	 * @param jobStatus
	 * @param runStr
	 */
	public void completeJob(String jobStatus,String runStr){
		IBatchJobBS batchJobBS = (IBatchJobBS) BeanContextUtil.getBean("batchJobBS");
		BatchJobInstance jobInst = batchJobBS.findBatchJobInstance(jobInstanceId);
		jobInst.setMemo(runStr);//设置任务返回结果
		jobInst.setEndTime(DateUtil.getYmdhms(new Date()));//设置任务结束时间
		jobInst.setStatus(jobStatus);//批处理执行成功
		jobInst.setProgress("1");
		batchJobBS.updateObject(jobInst);
		batchJobBS.scanJobStatus(jobInstanceId);
	}
	public Map getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map paramMap) {
		this.paramMap = paramMap;
	}

	
}
