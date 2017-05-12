package com.qfw.batch.bizservice.util;

import java.util.HashMap;
import java.util.List;

import com.qfw.batch.bizservice.IBatchJobBS;
import com.qfw.batch.bizservice.schedule.BatchServiceImpl;
import com.qfw.batch.bizservice.schedule.common.SchedulerJob;
import com.qfw.batch.model.bo.BatchJob;
import com.qfw.batch.model.bo.BatchJobInstance;

public class JobUtil {

	/**
	 * 批量框架初始化方法
	 * 
	 * @throws Exception
	 * @author Cao Si Bin
	 * @version v1.0 Cao Si Bin 2006-07-14
	 */
	public static void init() throws Exception {
		// 获得所用任务信息列表

		IBatchJobBS batchJobBS = (IBatchJobBS) BeanContextUtil.getBean("batchJobBS");
		List<BatchJob> jobList = batchJobBS.findAllBatchJobGroup();
		// 判读任务列表不为NULL
		if (jobList == null) {
			//System.out.println("任务队列为空!");
			return;
		}
		// 判读自启动Map等于NULL
		if (BatchServiceImpl.QuartzjobMap == null) {
			// 实例化自启动Map
			BatchServiceImpl.QuartzjobMap = new HashMap<Integer,String>();
		}
		// 清空自启动Map
		BatchServiceImpl.QuartzjobMap.clear();
		// 循环提取任务信息
		for (BatchJob job : jobList) {
			Integer jobID = job.getJobId();
			// 加入定时队列中
			String schedulerDate = job.getSedulerdate();
			// 判读自启动时间不为NULL 并且 自启动时间不为 “”
			if (schedulerDate != null && schedulerDate.trim().length() > 0) {
				// 为自启动Map设置值
				BatchServiceImpl.QuartzjobMap.put(jobID, schedulerDate);
				//System.out.println(" 任务id =  " + jobID + "任务名称 = "+job.getJobAliasName()+" 启动时间  = "+ schedulerDate);
			}
		}
	}
	
	public static void startJob(BatchJob batchJob){
		if(batchJob != null){
			String commandline="";
			commandline +="start "+batchJob.getJobId();
			commandline +=" "+batchJob.getJobName();
			commandline +=" "+SchedulerJob.getBatchno();
			commandline +=" 0";
			//System.out.println("Quartz run = "+commandline);
			synchronized(BatchServiceImpl.taskList)	{
				 BatchServiceImpl.taskList.add(commandline);
				 BatchServiceImpl.taskList.notifyAll();
			}
		}
	}
	
	public static void reStartJob(BatchJobInstance jobInstance){
		if(jobInstance!=null){
			String command = "start "+jobInstance.getJobInstanceId()+" "+jobInstance.getJobId()
					+" "+jobInstance.getBatchNo()+" 1";
			synchronized(BatchServiceImpl.taskList)	{
				 BatchServiceImpl.taskList.add(command);
				 BatchServiceImpl.taskList.notifyAll();
			}
		}
	}
	
	public static void stopJob(BatchJobInstance jobInstance){
		if(jobInstance!=null){
			String command = "stop "+jobInstance.getJobInstanceId()+" "+jobInstance.getJobId()
					+" "+jobInstance.getBatchNo()+" 1";
			synchronized(BatchServiceImpl.taskList)	{
				 BatchServiceImpl.taskList.add(command);
				 BatchServiceImpl.taskList.notifyAll();
			}
		}
	}

}
