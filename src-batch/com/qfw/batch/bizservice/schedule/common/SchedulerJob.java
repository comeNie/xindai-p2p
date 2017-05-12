package com.qfw.batch.bizservice.schedule.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.qfw.batch.bizservice.schedule.BatchServiceImpl;
import com.qfw.batch.model.bo.BatchJob;

public class SchedulerJob implements Job {
	public static String getBatchno(){
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		return fmt.format(new Date());
	}
	/**
	 * 很重要
	 * QuartzUtil调用startScheduler() 方法后, Quartz自己的trigger将会调用此方法，来自动执行任务
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		
		Integer jobId = dataMap.getInt("jobId");
		String argues = dataMap.getString("argues");
		String jobName = dataMap.getString("jobName");
		if(jobId==null) return;
		String commandline="";
		commandline +="start "+jobId;
		if(jobName!=null)
			commandline +=" "+jobName;
		commandline +=" "+getBatchno();
		if(argues!=null)
			commandline +=" "+argues;
		//System.out.println("Quartz run = "+commandline);
		synchronized(BatchServiceImpl.taskList)	{
			 BatchServiceImpl.taskList.add(commandline);
			 BatchServiceImpl.taskList.notifyAll();
		}		
	}
	
}
