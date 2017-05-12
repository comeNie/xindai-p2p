package com.qfw.batch.dao.impl;

import java.util.List;

import com.qfw.batch.dao.IBatchJobDAO;
import com.qfw.batch.model.JobStatus;
import com.qfw.batch.model.bo.BatchJob;
import com.qfw.batch.model.bo.BatchJobDependOn;
import com.qfw.batch.model.bo.BatchJobInstance;
import com.qfw.batch.model.bo.BatchJobParam;
import com.qfw.common.dao.impl.BaseDAOImpl;

public class BatchJobDAOImpl extends BaseDAOImpl implements IBatchJobDAO {

	
	@SuppressWarnings("unchecked")
	public List<BatchJob> findBatchJobByParentId(Integer parentJobId) {
		String hql = "from BatchJob where enable = ? and parentJobId = ? order by jobOrder,jobId";
		return this.getHibernateTemplate().find(hql, "1",parentJobId);
	}
	/**
	 * 通过组任务实例id查询子任务实例
	 * @param parentJobId
	 * @return
	 */
	public List<BatchJobInstance> findBatchJobInstanceByParentId(Integer parentJobId){
		String hql = "from BatchJobInstance where parentJobId = ? order by jobInstanceId";
		return this.getHibernateTemplate().find(hql, parentJobId);
	}
	/**
	 * 通过任务id及批次号 获取批任务
	 * @param jobId
	 * @param batchNo
	 * @return
	 */
	public BatchJobInstance findBatchJobInstance(Integer jobId,Integer batchNo){
		String hql = "from BatchJobInstance where jobId = ? and batchNo = ?";
		List<BatchJobInstance> list = this.getHibernateTemplate().find(hql, jobId,batchNo);
		if(list != null && !list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 通过任务id获取依赖关系
	 * @param jobId
	 * @return
	 */
	public List<BatchJobDependOn> findBatchJobDepend(Integer jobId){
		String hql = "from BatchJobDependOn where jobId = ?";
		return this.getHibernateTemplate().find(hql, jobId);
	}
	/**
	 * 通过批次号获取所有执行任务实例
	 * @param batchNo
	 * @return
	 */
	public List<BatchJobInstance> findBatchJobInstance(Integer batchNo){
		String hql = "from BatchJobInstance where batchNo = ? order by parentJobId,jobInstanceId";
		return this.getHibernateTemplate().find(hql, batchNo);
	}
	/**
	 * 通过任务id获取参数配置信息
	 * @param jobId
	 * @return
	 */
	public List<BatchJobParam> findBatchJobParams(Integer jobId){
		String hql = "from BatchJobParam where jobId = ?";
		return this.getHibernateTemplate().find(hql, jobId);
	}
	/**
	 * 查找已经创建的任务实例
	 * @return
	 */
	public List<BatchJobInstance> findCreateBatchJobInstances(){
		String hql = "from BatchJobInstance where status = ? order by jobInstanceId";
		return this.getHibernateTemplate().find(hql, String.valueOf(JobStatus.CREATED));
	}
	/**
	 * 通过任务实例id查找依赖任务实例
	 * @param jobInstanceId
	 * @return
	 */
	public List<BatchJobInstance> findBatchJobInstancesDepend(Integer jobInstanceId){
		String hql = "select inst from BatchJobInstance inst,BatchInstanceDependOn depd where inst.jobInstanceId = depd.dependJobInstanceId and depd.jobInstanceId = ? order by inst.jobInstanceId";
		return this.getHibernateTemplate().find(hql, jobInstanceId);
	}	
}
