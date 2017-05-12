package com.qfw.jbpm.bizservice;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.vo.PageList;
import com.qfw.jbpm.model.bo.Jbpm4AuditOpinion;
import com.qfw.jbpm.model.vo.FilterTaskVO;
import com.qfw.jbpm.model.vo.TaskVO;

public interface IFlowBS extends IBaseService {
	
	
	/**
	 * 查询用户已办任务
	 * @param userId
	 * @param first
	 * @param pageSize
	 * @return
	 */
	public PageList<TaskVO> findMyHistoryTask(FilterTaskVO filterVO, int first, int pageSize);
	/**
	 * 查询用户待办任务
	 * 
	 * @param userId
	 * @param first
	 * @param pageSize
	 * @return
	 */
	public PageList<TaskVO> findMyTask(FilterTaskVO filterVO, int first, int pageSize);
	
	/**
	 * 获取任务并执行下一任务
	 * @param taskId 任务id
	 * @param userId 用户id
	 * @param to 跳转流程
	 * @param comment 审批意见
	 * @param paraMap 参数map
	 */
	public void takeAndCompleteTask(String taskId,String to,String comment,Map<String, Object> paraMap) throws BizException;
	
	
	/**
	 * 获取任务并执行下一任务,不带审批意见
	 * @param taskId 任务id
	 * @param userId 用户id
	 * @param to 跳转流程
	 * @param paraMap 参数map
	 */
	public void takeAndCompleteTask(String taskId,String to,Map<String, Object> paraMap) throws BizException;
	
	/**
	 * 执行本节点任务，指定人员的执行方式
	 * @param taskId
	 * @param to
	 * @param comment
	 * @param paraMap
	 * @throws BizException
	 */
	public void completeTask(String taskId,String to,String comment,Map<String, Object> paraMap) throws BizException;
	/**
	 * 执行下一任务，不带审批意见。
	 * @param taskId
	 * @param to
	 * @param paraMap
	 * @throws BizException
	 */
	public void completeTask(String taskId,String to,Map<String, Object> paraMap) throws BizException;
	/**
	 * 启动并执行任务
	 * @param processDefinitionKey 流程名称
	 * @param userId 用户
	 * @param map 传入参数
	 * @return
	 */
	public String startProcessInstanceAndCompleteTask(String processDefinitionKey,String to,Map<String, Object> map) throws BizException;
	
	/**
	 * 启动流程
	 * @param processDefinitionKey 流程名称
	 * @param userId 用户
	 * @param map 传入参数
	 * @return
	 */
	public String startProcessInstance(String processDefinitionKey,String to,Map<String, Object> map)throws BizException;
	
	/**
	 * 获取审批意见信息
	 * @param processInstanceId
	 * @return
	 */
	public List<Jbpm4AuditOpinion> getAuditOpinion(String processInstanceId);
	/**
	 * 判断流程是否被退回
	 * @param workItemId
	 * @param taskName
	 * @return
	 */
	public boolean isReturned(String workItemId,String taskName);

}
