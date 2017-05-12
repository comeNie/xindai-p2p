package com.qfw.batch.bizservice.impl;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.qfw.batch.bizservice.IBatchJobBS;
import com.qfw.batch.bizservice.schedule.BatchServiceImpl;
import com.qfw.batch.bizservice.schedule.common.IBatchJob;
import com.qfw.batch.bizservice.util.DateUtil;
import com.qfw.batch.dao.IBatchJobDAO;
import com.qfw.batch.model.JobStatus;
import com.qfw.batch.model.bo.BatchJob;
import com.qfw.batch.model.bo.BatchJobDependOn;
import com.qfw.batch.model.bo.BatchJobInstance;
import com.qfw.batch.model.bo.BatchJobParam;

public class BatchJobBSImpl implements IBatchJobBS {
	
	private IBatchJobDAO batchJobDAO;
	
	/**
	 * 获取所有的批任务组
	 * @return
	 */
	public List<BatchJob> findAllBatchJobGroup() {
		return batchJobDAO.findBatchJobByParentId(0);
	}

	/**
	 * 通过批任务id获取子任务
	 * @return
	 */
	public List<BatchJob> findAllBatchJobChildren(Integer parentJobId) {
		return batchJobDAO.findBatchJobByParentId(parentJobId);
	}
	
	/**
	 * 通过批次号获取所有执行任务实例
	 * @param batchNo
	 * @return
	 */
	public List<BatchJobInstance> findBatchJobInstances(Integer batchNo){
		return batchJobDAO.findBatchJobInstance(batchNo);
	}
	
	/**
	 * 通过组任务实例id查询子任务实例
	 * @param parentJobId
	 * @return
	 */
	public List<BatchJobInstance> findAllBatchJobInstanceByParentId(Integer parentJobId){
		return batchJobDAO.findBatchJobInstanceByParentId(parentJobId);
	}

	/**
	 * 获取所有任务树
	 * @return
	 */
	public TreeNode getJobTree() {
		TreeNode jobRoot = new DefaultTreeNode("root", null);
		TreeNode jobNode = null;
		List<BatchJob> groups = findAllBatchJobGroup();
		if(groups != null && !groups.isEmpty()){
			for (BatchJob batchJob : groups) {
				jobNode = new DefaultTreeNode(batchJob, jobRoot);
				List<BatchJob> children = findAllBatchJobChildren(batchJob.getJobId());
				for (BatchJob batchJob2 : children) {
					new DefaultTreeNode(batchJob2, jobNode);
				}
			}
		}
		return jobRoot;
	}
	
	/**
	 * 获取实例任务树
	 * @param batchNo
	 * @return
	 */
	public TreeNode getInstanceTree(Integer batchNo){
		TreeNode root = new DefaultTreeNode("root", null);
		
		List<BatchJobInstance> tempJobInstances = new ArrayList<BatchJobInstance>();//存放临时任务实例
		List<BatchJobInstance> batchJobInstances = findBatchJobInstances(batchNo);
		
		if(batchJobInstances !=null && !batchJobInstances.isEmpty()){
			//处理实例组开始
			for (BatchJobInstance batchJobInstance : batchJobInstances) {
				if(Integer.valueOf(0).equals(batchJobInstance.getParentJobId())){
					tempJobInstances.add(batchJobInstance);
					batchJobInstance.setStatus(JobStatus.changeToGB(Integer.valueOf(batchJobInstance.getStatus())));
					new DefaultTreeNode(batchJobInstance, root);
				}
			}
			batchJobInstances.removeAll(tempJobInstances);
			//处理实例组结束
			//循环处理子任务开始
			List<TreeNode> children = root.getChildren();
			if(children != null && !children.isEmpty()){
				for (TreeNode treeNode : children) {
					BatchJobInstance data = (BatchJobInstance) treeNode.getData();
					for (BatchJobInstance batchJobInstance : batchJobInstances) {
						if(data.getJobInstanceId().equals(batchJobInstance.getParentJobId())){
							tempJobInstances.add(batchJobInstance);
							batchJobInstance.setStatus(JobStatus.changeToGB(Integer.valueOf(batchJobInstance.getStatus())));
							new DefaultTreeNode(batchJobInstance, treeNode);
						}
					}
					batchJobInstances.removeAll(tempJobInstances);
				}
			}
			//循环处理子任务结束
		}
		
		
		return root;
	
	}

	@Override
	public BatchJobInstance findBatchJobInstance(Integer jobInstanceId) {
		return (BatchJobInstance) batchJobDAO.findById(BatchJobInstance.class, jobInstanceId);
	}
	
	/**
	 * 通过任务id获取依赖关系
	 * @param jobId
	 * @return
	 */
	public List<BatchJobDependOn> findBatchJobDepend(Integer jobId){
		return batchJobDAO.findBatchJobDepend(jobId);
	}
	/**
	 * 通过任务实例id查找依赖任务实例
	 * @param jobInstanceId
	 * @return
	 */
	public List<BatchJobInstance> findBatchJobInstancesDepend(Integer jobInstanceId){
		return batchJobDAO.findBatchJobInstancesDepend(jobInstanceId);
	}
	/**
	 * 通过任务id获取参数配置信息
	 * @param jobId
	 * @return
	 */
	public List<BatchJobParam> findBatchJobParams(Integer jobId){
		return batchJobDAO.findBatchJobParams(jobId);
	}
	/**
	 * 通过jobId获取参数信息
	 * @param jobId
	 * @return
	 */
	public Map<String, Object> getBatchJobParams(Integer jobId){
		Map<String, Object> map = new HashMap<String, Object>();
		List<BatchJobParam> jobParams = findBatchJobParams(jobId);
		if(jobParams != null && !jobParams.isEmpty()){
			for (BatchJobParam batchJobParam : jobParams) {
				String paramType = batchJobParam.getParamType();
				String key = batchJobParam.getParamKey();
				Object value = batchJobParam.getParamValue();
				try {
					if(paramType != null && paramType.trim().length() > 0){
						Class objClass = Class.forName(paramType);
						Object obj = objClass.newInstance();
						if(obj instanceof String){
							
						}else if(obj instanceof Integer){
							value = Integer.valueOf((String) value);
						}else if(obj instanceof Long){
							value = Long.valueOf((String) value);
						}else if(obj instanceof Date){
							value = DateUtil.getDateByFull((String) value);
						}else if(obj instanceof BigDecimal){
							value = new BigDecimal((String)value);
						}else{
							Method method = objClass.getDeclaredMethod("valueOf", String.class);
							value = method.invoke(obj, (String)value);
						}
					}
					map.put(key, value);
				} catch (Exception e) {
					 
				}
			}
		}
		return map;
	}
	/**
	 * 查找已经创建的任务实例
	 * @return
	 */
	public List<BatchJobInstance> findCreateBatchJobInstances(){
		return batchJobDAO.findCreateBatchJobInstances();
	}
	/**
	 * 通过任务id及批次号 获取批任务
	 * @param jobId
	 * @param batchNo
	 * @return
	 */
	public BatchJobInstance findBatchJobInstance(Integer jobId,Integer batchNo){
		return batchJobDAO.findBatchJobInstance(jobId, batchNo);
	}
	
	public void saveObject(Object obj) {
		batchJobDAO.save(obj);
	}
	public void updateObject(Object obj){
		batchJobDAO.update(obj);
	}
	
	@Override
	public BatchJob findBatchJobByJobId(Integer jobId) {
		return (BatchJob) batchJobDAO.findById(BatchJob.class, jobId);
	}
	/**
	 * 扫描任务状态
	 * @param jobInstanceId
	 */
	public synchronized void scanJobStatus(Integer jobInstanceId){
		BatchJobInstance instance = findBatchJobInstance(jobInstanceId);
		if("1".equals(instance.getIsSubJob())){//如果是子任务
			List<BatchJobInstance> jobChilren = findAllBatchJobInstanceByParentId(instance.getParentJobId());
			if(jobChilren != null && !jobChilren.isEmpty()){
				int processingCount = 0;
				//int processedCount = 0;
				int processedSuccCount = 0;
				int processedSuccCycleCount = 0;
				for (BatchJobInstance batchJobInstance : jobChilren) {
					Integer status = Integer.valueOf(batchJobInstance.getStatus());
					if(JobStatus.CREATED.equals(status)||JobStatus.RUNNING.equals(status)||JobStatus.WAITFILE.equals(status)){
						processingCount++;
					}else if(JobStatus.SUCCESS.equals(status)){
						processedSuccCount++;
						//processedCount++;
					}else if(JobStatus.SUCCESS_CYCLE.equals(status)){
						processedSuccCycleCount++;
					}else if(JobStatus.FAILURE.equals(status)||JobStatus.TEMINATED.equals(status)||JobStatus.STOPPED.equals(status)||JobStatus.NOSUCCESS.equals(status)){
						//processedCount++;
					}
				}
				BatchJobInstance parentInstance = findBatchJobInstance(instance.getParentJobId());
				parentInstance.setProgress(String.valueOf(processedSuccCount+processedSuccCycleCount));
				if(processingCount == 0){
					parentInstance.setEndTime(DateUtil.getYmdhms(new Date()));
					if(processedSuccCycleCount > 0){
						parentInstance.setStatus(String.valueOf(JobStatus.SUCCESS_CYCLE));
					}else{
						parentInstance.setStatus(String.valueOf(JobStatus.SUCCESS));
					}
				}
				updateObject(parentInstance);
			}
		}else{
			if(JobStatus.STOPPED.equals(Integer.valueOf(instance.getStatus()))){//父任务停止运行
				List<BatchJobInstance> jobChilren = findAllBatchJobInstanceByParentId(instance.getJobInstanceId());
				//int processedCount = 0;
				int processedSuccCount = 0;
				for (BatchJobInstance batchJobInstance : jobChilren) {
					Integer status = Integer.valueOf(batchJobInstance.getStatus());
					if(JobStatus.CREATED.equals(status)||JobStatus.WAITFILE.equals(status)){
						batchJobInstance.setStatus(instance.getStatus());
						updateObject(batchJobInstance);
					}else if(JobStatus.SUCCESS.equals(status)||JobStatus.SUCCESS_CYCLE.equals(status)){
						processedSuccCount++;
					}else if(JobStatus.RUNNING.equals(status)){//任务正在执行
						IBatchJob batch = BatchServiceImpl.threadRunJobMap.get(batchJobInstance.getJobInstanceId());
						BatchServiceImpl.threadService.removeTask(batch);
					}
				}
			}
		}
	}
	public IBatchJobDAO getBatchJobDAO() {
		return batchJobDAO;
	}

	public void setBatchJobDAO(IBatchJobDAO batchJobDAO) {
		this.batchJobDAO = batchJobDAO;
	}
}
