package com.qfw.batch.bizservice;

import java.util.List;
import java.util.Map;

import org.primefaces.model.TreeNode;

import com.qfw.batch.model.bo.BatchJob;
import com.qfw.batch.model.bo.BatchJobDependOn;
import com.qfw.batch.model.bo.BatchJobInstance;
import com.qfw.batch.model.bo.BatchJobParam;


public interface IBatchJobBS {
	/**
	 * 获取所有的批任务组
	 * @return
	 */
	public List<BatchJob> findAllBatchJobGroup();
	/**
	 * 通过批任务id获取子任务
	 * @return
	 */
	public List<BatchJob> findAllBatchJobChildren(Integer jobId);
	/**
	 * 通过批次号获取所有执行任务实例
	 * @param batchNo
	 * @return
	 */
	public List<BatchJobInstance> findBatchJobInstances(Integer batchNo);
	/**
	 * 通过jobId获取BatchJob任务。
	 * @param jobId
	 * @return
	 */
	public BatchJob findBatchJobByJobId(Integer jobId);
	/**
	 * 通过jobInstanceId获取BatchJobInstance执行实例
	 * @param jobInstanceId
	 * @return
	 */
	public BatchJobInstance findBatchJobInstance(Integer jobInstanceId);
	/**
	 * 通过组任务实例id查询子任务实例
	 * @param parentJobId
	 * @return
	 */
	public List<BatchJobInstance> findAllBatchJobInstanceByParentId(Integer parentJobId);
	
	/**
	 * 通过任务id及批次号 获取批任务
	 * @param jobId
	 * @param batchNo
	 * @return
	 */
	public BatchJobInstance findBatchJobInstance(Integer jobId,Integer batchNo);
	
	/**
	 * 通过任务id获取依赖关系
	 * @param jobId
	 * @return
	 */
	public List<BatchJobDependOn> findBatchJobDepend(Integer jobId);
	
	/**
	 * 通过任务id获取参数配置信息
	 * @param jobId
	 * @return
	 */
	public List<BatchJobParam> findBatchJobParams(Integer jobId);
	/**
	 * 通过jobId获取参数信息
	 * @param jobId
	 * @return
	 */
	public Map<String, Object> getBatchJobParams(Integer jobId);
	/**
	 * 查找已经创建的任务实例
	 * @return
	 */
	public List<BatchJobInstance> findCreateBatchJobInstances();
	/**
	 * 通过任务实例id查找依赖任务实例
	 * @param jobInstanceId
	 * @return
	 */
	public List<BatchJobInstance> findBatchJobInstancesDepend(Integer jobInstanceId);
	/**
	 * 获取所有任务树
	 * @return
	 */
	public TreeNode getJobTree();
	
	/**
	 * 扫描任务状态
	 * @param jobInstanceId
	 */
	public void scanJobStatus(Integer jobInstanceId);
	
	/**
	 * 获取实例任务树
	 * @param batchNo
	 * @return
	 */
	public TreeNode getInstanceTree(Integer batchNo);
	
	/**
	 * 保存对象
	 * @param obj
	 */
	public void saveObject(Object obj);
	/**
	 * 更新对象
	 * @param obj
	 */
	public void updateObject(Object obj);
}
