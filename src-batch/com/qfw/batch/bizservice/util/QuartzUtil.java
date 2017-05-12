/*
 * Created on 2005-8-26
 * Project: CCLBM
 */
package com.qfw.batch.bizservice.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.qfw.batch.bizservice.IBatchJobBS;
import com.qfw.batch.bizservice.schedule.BatchServiceImpl;
import com.qfw.batch.bizservice.schedule.common.SchedulerJob;
import com.qfw.batch.model.bo.BatchJob;

/**
 * QuartzUtil类是job相关的工具类，提供schedule和unschedule的方法
 * 
 * @author jie
 * @version 1.0
 */

public class QuartzUtil {
	private String jobname;
	private String aliasname;
	private String argues;

	// 初始化 时序工厂
	public static SchedulerFactory SchedulerFy = new org.quartz.impl.StdSchedulerFactory();
	public static Scheduler sched;

	public String getAliasname() {
		return aliasname;
	}

	public void setAliasname(String aliasname) {
		this.aliasname = aliasname;
	}

	public String getArgues() {
		return argues;
	}

	public void setArgues(String argues) {
		this.argues = argues;
	}

	public String getJobname() {
		return jobname;
	}

	public void setJobname(String jobname) {
		this.jobname = jobname;
	}

	/**
	 * 获得批号,默认当前日期8位拼接
	 * 
	 * @return 日期8位拼接
	 */
	public static String getBatchno() {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		return fmt.format(new Date());
	}

	public QuartzUtil() {

	}

	/**
	 * QuartzUtil 类的构造函数，当此类被实例化时用于初始化 jobname,aliasname, argues 参数的值
	 * 
	 * @param _jobname
	 * @param _aliasname
	 * @param _argues
	 */
	public QuartzUtil(String _jobname, String _aliasname, String _argues) {
		this.jobname = _jobname;
		this.aliasname = _aliasname;
		this.argues = _argues;
	}

	/**
	 * 此方法schedule一个新的任务
	 * 
	 * @param jobName
	 *            任务名称
	 * @param jobGroup
	 *            任务组
	 * @param jobBean
	 *            包含job详细信息的bean
	 * @param trigger
	 *            触发器对象
	 * @return 成功返回ture，失败返回false
	 * @throws
	 */
	public boolean scheduleJob(String jobName, String jobGroup,
			QuartzJobBean jobBean, Trigger trigger) {
		return true;
	}

	/**
	 * 此方法unschedule一个已存在任务
	 * 
	 * @param jobName
	 *            任务名称
	 * @param jobGroup
	 *            任务组
	 * @return 成功返回ture，失败返回false
	 * @throws
	 */
	public boolean unscheduleJob(String jobName, String jobGroup) {
		return true;
	}

	
	/**
	 * 很重要 Quartz时序执行任务时的调用方法 　
	 */
	public static synchronized void startScheduler() {
		Set<Map.Entry<Integer, String>> mapping = BatchServiceImpl.QuartzjobMap.entrySet();
		try {
			// 判断如果生成的Scheduler对象不为空　并且不是　停止状态时
			if (sched != null && !sched.isShutdown()) {
				sched.shutdown();
			}
			sched = SchedulerFy.getScheduler();
			sched.start();
		} catch (Exception e) {
			 
		}
		for (Entry<Integer, String> me : mapping) {
			Integer jobId = me.getKey();
			String expression = (String) me.getValue();
			createJob(jobId, expression);
		}
	}
	
	public static void createJob(Integer jobId,String expression){
		JobDetail job = new JobDetail(String.valueOf(jobId), "group1", SchedulerJob.class);
		job.getJobDataMap().put("jobId", jobId);
		job.getJobDataMap().put("argues", "0");
		
		IBatchJobBS batchJobBS = (IBatchJobBS) BeanContextUtil.getBean("batchJobBS");
		BatchJob batchJob = (BatchJob) batchJobBS.findBatchJobByJobId(jobId);
		job.getJobDataMap().put("jobName", batchJob.getJobName());
		
		try {
			CronTrigger trig = new CronTrigger("trigger" +jobId, "group1",String.valueOf(jobId), "group1", expression);
			sched.scheduleJob(job, trig);
			// 不用删除 Quartzjobmap中的任务，直接获得Trigger
			// 设置轮训时间，如果列表中没有任务，则直接添加JobDetail到Schedule中
		} catch (Exception e) {
			//System.out.println("Job:" + jobId + " Expression Error!");
			 
		} 
	}

}
