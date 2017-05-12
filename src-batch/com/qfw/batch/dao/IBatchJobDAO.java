package com.qfw.batch.dao;

import java.util.List;

import com.qfw.batch.model.bo.BatchJob;
import com.qfw.batch.model.bo.BatchJobDependOn;
import com.qfw.batch.model.bo.BatchJobInstance;
import com.qfw.batch.model.bo.BatchJobParam;
import com.qfw.common.dao.IBaseDAO;

public interface IBatchJobDAO extends IBaseDAO{

	/**
	 * 通过父batchid获取batchJob任务
	 * @param parentJobId
	 * @return
	 */
	public List<BatchJob> findBatchJobByParentId(Integer parentJobId);
	/**
	 * 通过组任务实例id查询子任务实例
	 * @param parentJobId
	 * @return
	 */
	public List<BatchJobInstance> findBatchJobInstanceByParentId(Integer parentJobId);
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
	 * 通过批次号获取所有执行任务实例
	 * @param batchNo
	 * @return
	 */
	public List<BatchJobInstance> findBatchJobInstance(Integer batchNo);
	/**
	 * 通过任务id获取参数配置信息
	 * @param jobId
	 * @return
	 */
	public List<BatchJobParam> findBatchJobParams(Integer jobId);
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
}
