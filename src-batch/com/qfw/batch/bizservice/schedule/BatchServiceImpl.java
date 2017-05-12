package com.qfw.batch.bizservice.schedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.qfw.batch.bizservice.IBatchJobBS;
import com.qfw.batch.bizservice.schedule.common.IBatchJob;
import com.qfw.batch.bizservice.util.BeanContextUtil;
import com.qfw.batch.model.JobStatus;
import com.qfw.batch.model.bo.BatchInstanceDependOn;
import com.qfw.batch.model.bo.BatchJob;
import com.qfw.batch.model.bo.BatchJobDependOn;
import com.qfw.batch.model.bo.BatchJobInstance;
import com.sun.jmx.snmp.tasks.ThreadService;

public class BatchServiceImpl {
	
	private static Logger logger = Logger.getLogger(BatchServiceImpl.class);
	/** 命令行列表 */
	public static ArrayList<String> taskList = new ArrayList<String>();
	/** 定时任务列表 */
	public static HashMap<Integer,String> QuartzjobMap = new HashMap<Integer,String>();
	/** 入驻线程中任务的列表 */
	//public static HashMap threadJobMap = new HashMap();
	
	/** 正在运行的任务的列表 */
	public static Map<Integer,IBatchJob> threadRunJobMap = Collections.synchronizedMap(new HashMap<Integer,IBatchJob>());
	public static ThreadService threadService = new ThreadService(30);
	/**
	 * 很重要 启动任务，将新任务加入到任务队列中，通过调用startScan方法扫描任务
	 * 
	 * @param String
	 *            任务别名
	 * @param long
	 *            任务ID
	 * @param String[]
	 *            传入参数 [20060606] [0]
	 * @author Cao Si Bin
	 * @version v1.0 Cao Si Bin 2006-07-19
	 */
	public void startBatch(Integer jobID, String[] args) {
		if (logger.isInfoEnabled()) {
			logger.info("### startBatch ###----> 任务ID为[" + jobID + "] 启动状态为[" + args[1] + "] 批次号为[ " + args[0] + "]");
		}
		try {
			// 保存 启动状态
			//this.setStartStatus(args[1]);
			// 查找上次任务进行BO
			int batchNo = Integer.parseInt(args[0]);
			// 如果是重新启动的话
			if (args[1].equals("1")) {
				
				IBatchJobBS batchJobBS = (IBatchJobBS) BeanContextUtil.getBean("batchJobBS");
				BatchJobInstance jobInstance = batchJobBS.findBatchJobInstance(jobID);//重启时直接用实例id启动
				// 判断这个ID的任务是否可以运行
				/*if (!canBeRun(jobInstance)) {
					return;
				}*/
				
				if (String.valueOf(JobStatus.CREATED).equals(jobInstance.getStatus()) || String.valueOf(JobStatus.RUNNING).equals(jobInstance.getStatus()) || String.valueOf(JobStatus.WAITFILE).equals(jobInstance.getStatus())) {
					if (logger.isInfoEnabled()) {
						logger.info("### startBatch ###----> 任务名称[" + jobInstance.getName() + "] 已经创建或正在运行 ");
					}
					return;
				}
				this.updatejobStatus(jobInstance);
			} else {
				IBatchJobBS batchJobBS = (IBatchJobBS) BeanContextUtil.getBean("batchJobBS");
				BatchJobInstance jobInstance = batchJobBS.findBatchJobInstance(jobID, batchNo);//启动时按批任务id启动
				if (jobInstance != null) {
					if (String.valueOf(JobStatus.CREATED).equals(jobInstance.getStatus()) || String.valueOf(JobStatus.RUNNING).equals(jobInstance.getStatus()) || String.valueOf(JobStatus.WAITFILE).equals(jobInstance.getStatus())) {
						if (logger.isInfoEnabled()) {
							logger.info("### startBatch ###----> 任务名称[" + jobInstance.getName() + "] 已经创建或正在运行 ");
						}
						return;
					}
					this.updatejobStatus(jobInstance);
				} else {
					this.createJobfromBatchJob(batchNo, jobID);
				}
			}
			startScan();
		} catch (Exception e) {
			 
		}
	}
	public void stopBatch(Integer jobInstanceId){
		IBatchJobBS batchJobBS = (IBatchJobBS) BeanContextUtil.getBean("batchJobBS");
		BatchJobInstance jobInstance = batchJobBS.findBatchJobInstance(jobInstanceId);//停止时直接用实例id启动
		if(jobInstance != null){
			if("1".equals(jobInstance.getIsSubJob())){
				if(String.valueOf(JobStatus.CREATED).equals(jobInstance.getStatus())
						||String.valueOf(JobStatus.CREATED).equals(jobInstance.getStatus())){//任务等待状态
					jobInstance.setStatus(String.valueOf(JobStatus.STOPPED.toString()));
					batchJobBS.updateObject(jobInstance);
					batchJobBS.scanJobStatus(jobInstanceId);
				}else{
					//任务已经启动
				}
			}else{
				jobInstance.setStatus(String.valueOf(JobStatus.STOPPED.toString()));
				batchJobBS.updateObject(jobInstance);
				batchJobBS.scanJobStatus(jobInstanceId);
			}
			
		}
		
	}
	/**
	 * 判断任务实例是否可以执行
	 * @param jobInstance
	 * @return
	 */
	private boolean canBeRun(BatchJobInstance jobInstance) {
		if (String.valueOf(JobStatus.CREATED).equals(jobInstance.getStatus()) || String.valueOf(JobStatus.RUNNING).equals(jobInstance.getStatus()) || String.valueOf(JobStatus.WAITFILE).equals(jobInstance.getStatus())){
			if (logger.isInfoEnabled()) {
				logger.info("### canBeRun ###----> 任务ID为[" + jobInstance.getJobId()+ "] 任务名称为[" + jobInstance.getName() + "] 正在运行或已经被创建还没有运行，不能更改此任务状态");
			}
			return false;
		} else {
			return true;
		}
	}
	/**
	 * 更新任务实例状态
	 * @param jobInstance
	 */
	private void updatejobStatus(BatchJobInstance jobInstance){
		IBatchJobBS batchJobBS = (IBatchJobBS) BeanContextUtil.getBean("batchJobBS");
		if("1".equals(jobInstance.getIsSubJob())){//判断任务是否子节点
			BatchJobInstance group = batchJobBS.findBatchJobInstance(jobInstance.getParentJobId());
			if(group != null && !String.valueOf(JobStatus.CREATED).equals(jobInstance.getStatus()) 
					&& !String.valueOf(JobStatus.RUNNING).equals(jobInstance.getStatus()) 
					&& !String.valueOf(JobStatus.WAITFILE).equals(jobInstance.getStatus())){
				jobInstance.setStatus(String.valueOf(JobStatus.CREATED));
				group.setStartTime("");
				group.setEndTime("");
				group.setProgress("0");
				//group.setTotalprogress("0");
				batchJobBS.updateObject(group);
			}
		}else{
			List<BatchJobInstance> jobChildren = (List<BatchJobInstance>) batchJobBS.findAllBatchJobInstanceByParentId(jobInstance.getJobInstanceId());
			for (BatchJobInstance batchJobInstance : jobChildren) {
				batchJobInstance.setStatus(String.valueOf(JobStatus.CREATED));
				batchJobInstance.setStartTime("");
				batchJobInstance.setEndTime("");
				batchJobInstance.setProgress("0");
				//batchJobInstance.setTotalprogress("0");
				batchJobBS.updateObject(batchJobInstance);
			}
		}
		jobInstance.setStatus(String.valueOf(JobStatus.CREATED));
		jobInstance.setStartTime("");
		jobInstance.setEndTime("");
		jobInstance.setProgress("0");
		//jobInstance.setTotalprogress("0");
		batchJobBS.updateObject(jobInstance);
	}
	private void createJobfromBatchJob(Integer batchNo, Integer jobId) {
		IBatchJobBS batchJobBS = (IBatchJobBS) BeanContextUtil.getBean("batchJobBS");
		BatchJob batchJob = batchJobBS.findBatchJobByJobId(jobId);
		if(batchJob != null && batchJob.getParentJobId().intValue() == 0){
			//查看依赖任务是否创建
			List<BatchJobDependOn> jobDepends = batchJobBS.findBatchJobDepend(jobId);
			if(jobDepends != null && !jobDepends.isEmpty()){
				BatchJobInstance dependJobInstance = null;
				for (BatchJobDependOn batchJobDependOn : jobDepends) {
					dependJobInstance = batchJobBS.findBatchJobInstance(batchJobDependOn.getDependJobId(), batchNo);//启动时按批任务id启动
					if(dependJobInstance == null){
						logger.info("### 创建任务失败 ###----> 组的前置任务没有被创建，无法创建该任务 ");
						return;
					}
				}
			}
			//创建任务实例
			BatchJobInstance groupInst = createJobInfoUnit(batchJob, batchNo, null);
			if(groupInst != null){
				 List<BatchJob> jobChildren = batchJobBS.findAllBatchJobChildren(jobId);
				 if(jobChildren != null && !jobChildren.isEmpty()){
					 for (BatchJob jobChild : jobChildren) {
						 createJobInfoUnit(jobChild, batchNo, groupInst);
					 }
					 groupInst.setTotalprogress(String.valueOf(jobChildren.size()));
					 batchJobBS.updateObject(groupInst);
				 }
			}
		}
	}
	private BatchJobInstance createJobInfoUnit(BatchJob batchJob,Integer batchNo,BatchJobInstance parentJobInstance){
		BatchJobInstance jobInst = new BatchJobInstance();
		if(Integer.valueOf(0).equals(batchJob.getParentJobId())){
			jobInst.setIsSubJob("0");
			jobInst.setParentJobId(0);//组实例id
			jobInst.setJobGroup(jobInst.getName());
		}else{
			jobInst.setIsSubJob("1");
			if(parentJobInstance == null){//子节点必须要有父亲实例
				//System.out.println("子节点必须要有父亲实例");
				return null;
			}else{
				jobInst.setParentJobId(parentJobInstance.getJobInstanceId());//组实例id
				jobInst.setJobGroup(parentJobInstance.getName());//任务组名
				jobInst.setTotalprogress("1");
			}
		}
		
		IBatchJobBS batchJobBS = (IBatchJobBS) BeanContextUtil.getBean("batchJobBS");
		
		jobInst.setBatchNo(batchNo);
		jobInst.setBatchSeqNo(0);
		jobInst.setJobId(batchJob.getJobId());
		jobInst.setName(batchJob.getJobName());
		jobInst.setAliasName(batchJob.getJobAliasName());
		jobInst.setStatus(String.valueOf(JobStatus.CREATED));
		jobInst.setProgress("0");
		batchJobBS.saveObject(jobInst);
		// 创建依赖任务是否创建
		List<BatchJobDependOn> jobDepends = batchJobBS.findBatchJobDepend(batchJob.getJobId());
		if (jobDepends != null && !jobDepends.isEmpty()) {
			BatchJobInstance dependJobInstance = null;
			for (BatchJobDependOn batchJobDependOn : jobDepends) {
				dependJobInstance = batchJobBS.findBatchJobInstance(
						batchJobDependOn.getDependJobId(), batchNo);// 启动时按批任务id启动
				if (dependJobInstance == null) {
					logger.info("### 创建任务失败 ###----> 组的前置任务没有被创建，无法创建该任务 ");
					return null;
				} else {
					BatchInstanceDependOn batchInstDepd = new BatchInstanceDependOn();
					batchInstDepd.setDependJobInstanceId(dependJobInstance.getJobInstanceId());
					batchInstDepd.setJobInstanceId(jobInst.getJobInstanceId());
					batchJobBS.saveObject(batchInstDepd);
				}
			}
		}
				
		return jobInst;
	}
	/**
	 * 扫描数据库任务实例
	 */
	public static synchronized void startScan() {
		
		IBatchJobBS batchJobBS = (IBatchJobBS) BeanContextUtil.getBean("batchJobBS");
		List<BatchJobInstance> jobInstances = batchJobBS.findCreateBatchJobInstances();
		if(jobInstances != null && !jobInstances.isEmpty()){
			for (BatchJobInstance batchJobInstance : jobInstances) {
				boolean canBeRuniflag = true;
				//boolean dependIsSucc = true;
				List<BatchJobInstance> jobInstDepends = batchJobBS.findBatchJobInstancesDepend(batchJobInstance.getJobInstanceId());
				if(jobInstDepends != null && !jobInstDepends.isEmpty()){
					for (BatchJobInstance jobInstDepend : jobInstDepends) {
						/*
						 * 依赖任务还处于未执行或在执行中的任务
						 */
						if(String.valueOf(JobStatus.CREATED).equals(jobInstDepend.getStatus()) || String.valueOf(JobStatus.RUNNING).equals(jobInstDepend.getStatus())){
							canBeRuniflag = false;
							break;
						}
					}
				}
				
				if(!canBeRuniflag){
					continue;
				}
				// 判断是否能运行标记
				
				if("1".equals(batchJobInstance.getIsSubJob())){//子节点
					BatchJobInstance jobInst = batchJobBS.findBatchJobInstance(batchJobInstance.getParentJobId());
					if(jobInst == null || !String.valueOf(JobStatus.RUNNING).equals(jobInst.getStatus())){
						if (logger.isInfoEnabled()) {
							logger.info(" ### startScan ###----> 该任务ID为[" + batchJobInstance.getJobInstanceId() + "] 任务名称为[" + batchJobInstance.getName() + "] 的组,组ID为[" + jobInst.getJobId() + "] 组名称为[" + jobInst.getName() + "]还没有运行，任务不能运行");
						}
						continue;
					}
				}
				
				BatchJob job = batchJobBS.findBatchJobByJobId(batchJobInstance.getJobId());
				String javaClass = job.getJavaclass();
				try {
					Class jobClass = Class.forName(javaClass);
					IBatchJob batch = (IBatchJob) jobClass.newInstance();
					batch.setJobInstanceId(batchJobInstance.getJobInstanceId());
					
					synchronized (BatchServiceImpl.threadRunJobMap) {
						if (!BatchServiceImpl.threadRunJobMap.containsKey(batchJobInstance.getJobInstanceId())) {
							BatchServiceImpl.threadRunJobMap.put(batchJobInstance.getJobInstanceId(), batch);
						} else {
							continue;
						}
					}
					/*
					 * 任务添加到线程池中
					 */
					threadService.submitTask(batch);
				} catch (Exception e) {
					logger.error(e);
				}
			}
		}
	}
}
