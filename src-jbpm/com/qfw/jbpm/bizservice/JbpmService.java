package com.qfw.jbpm.bizservice;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.history.HistoryComment;
import org.jbpm.api.history.HistoryProcessInstance;
import org.jbpm.api.history.HistoryTask;
import org.jbpm.api.model.ActivityCoordinates;
import org.jbpm.api.task.Task;


public interface JbpmService {

	/**
	 * 流程定义部署
	 * @param resourceXml	流程定义xml文件路径
	 * @param resourcePng	流程定义png图片路径
	 */
	public abstract void executeTaskDeployProcessDefinition(String resourceXml,
			String resourcePng);

	/**
	 * 删除流程实例
	 */
	public abstract void deleteProcessInstance(String deploymentId);

	/**
	 * 查询流程实例列表
	 * @return 
	 */
	public abstract List<ProcessDefinition> queryProcessInstance();
	/**
	 * 获取流程定义中的资源(查看流程使用.流程图)
	 */
	public abstract InputStream getResource(String deploymentId,
			String resourceName);

	/**
	 * 启动流程实例
	 */

	public abstract String executeTaskStartProcessInstance(
			String processDefinitionKey, Map<String, Object> map);

	/**
	 * 我的待办任务
	 */
	public abstract List<Task> getMyNotFinshedTasks(String userId);

	/**
	 * 获取用户所有的代办任务
	 * @param userId
	 * @return
	 */
	public List<Task> getAllMyNotFinshedTasks(String userId);
 
	/**
	 * 办理任务
	 * @param taskId
	 */
	public abstract void executeTask(String taskId);

	/**
	 * 办理指定连线任务
	 * 
	 * @param taskId
	 * @param outcome
	 */
	public abstract void executeTaskWithOutcome(String taskId, String outcome);

 
	/**
	 * 查询我的已办任务
	 */
	public abstract List<HistoryTask> getMyFinshedTask(String userId);

	/**
	 * 流程监控 根据流程实例id获取流程当前运行节点的详细信息
	 */
	public abstract ActivityCoordinates getProcessInstanceExecuteNode(
			String processInstanceId);

	/**
	 * 根据流程实例id获取流程相关变量
	 */

	public abstract Map<String,Object> getProcessInstanceVariable(
			String processInstanceId);
 
	/**
	 * 获取任务变量
	 * @param taskId
	 * @return
	 */
	public abstract Map<String, Object> getTaskVariable(String taskId);

	/**
	 * 获取执行变量
	 * @param taskId
	 * @return
	 */
	public abstract Map<String, Object> getExecutionVariable(String executionId);
	/**
	 * 根据流程实例id及任务名称获取指定任务
	 * @param userId			处理人
	 * @param processInstanceId 流程实例id
	 * @param activityName		任务名称
	 * @return
	 */
	public abstract Task getTaskByProcessIdAndActivityName(String userId,
			String processInstanceId,String activityName);

	/**
	 * 根据TaskId获取Task
	 * 
	 * @param taskId
	 * @return
	 */
	public abstract Task getTaskById(String taskId);

	/**
	 * 根据流程实例ID获取流程
	 */
	public abstract org.jbpm.api.ProcessInstance getProcessInstanceById(
			String piId);

	/**
	 * 通过执行实例id获取流程处理id
	 * @param executionId
	 * @return
	 */
	public abstract ProcessInstance getProcessInstanceByExecutionIdId (String executionId);
	/**
	 * 根据流程实例ID获取历史任务列表
	 * 
	 * @param piId
	 * @return
	 */
	public abstract List<HistoryTask>  getHistoryTaskListByProcessInstanceId(String piId);
	/**
	 * 根据流程实例获取对应流程图片
	 * @param processInstanceId
	 * @return
	 */
	public abstract  InputStream findProcessInstancePic(String processInstanceId);
	
	/**
	 * 添加任务意见
	 * @param taskId
	 * @param comment
	 */
	public abstract void addTaskComment(String taskId,String comment);

	/**
	 * 获取任务审批意见
	 * @param taskId
	 * @return
	 */
	public abstract List<HistoryComment> getTaskComments(String taskId);
	
	
	/**
	 * 根据流程id获取历史流程定义
	 * @param piId
	 * @return
	 */
	public HistoryProcessInstance getHistoryProcessInstanceById(String piId);

	/**
	 * 完成任务并动态传入参数
	 * @param taskId
	 * @param paraMap
	 */
	public abstract void executeTask(String taskId, Map<String, Object> paraMap,String to);
	
	/**
	 * 完成任务并动态传入参数
	 * @param taskId
	 * @param paraMap
	 */
	public abstract void executeTask(String taskId, Map<String, Object> paraMap);

	/**
	 * 根据流程定义key,获取我的待办任务
	 * @param userId               办理人名称
	 * @param processDefinitionKey 流程定义key
	 * @return
	 */
	public abstract List<Task> getMyNotFinshedTasksByProcessDefinitionKey(
			String userId, String processDefinitionKey);
	/**
	 * 根据里路程实例ID，获取我的代办任务
	 * @param userId
	 * @param processDefinitionId 流程定义id
	 * @return
	 */
	public abstract List<Task> getMyNotFinshedAssigneeTasksByProcessDefinitionId(String userId,String processDefinitionId);
	
	/**
	 * 通过任务实例id，获取任务
	 * @param userId 用户id
	 * @param processInstanceId 任务实例
	 * @return
	 */
	public abstract List<Task> getMyNotFinshedAssigneeTasksByProcessInstanceId(String userId,String processInstanceId);
	/**
	 * 根据活动名称,获取我的待办任务
	 * @param userId         办理人名称
	 * @param activityName   活动名称
	 * @return
	 */
	public abstract List<Task> getMyNotFinshedTasksByActivityName(
			String userId, String activityName);
	
	/**
	 * 根据流程定义key获取最新版本的流程id
	 * @param processDefinitionKey
	 * @return
	 */
	public abstract String getProcessDefinitionIdByprocessDefinitionKey(
			String processDefinitionKey) ;
	 /**
	  * 根据流程定义id获取对应的流程实例列表
	  * @param ProcessDefinitionId
	  * @return
	  */
	public abstract  List<HistoryProcessInstance> getHistoryProcessInstancesByProcessDefinitionId(String ProcessDefinitionId);

	/**
	 * 根据流程实例id及任务处理人获取历史任务列表
	 * @param processInstanceId	流程实例id
	 * @param userId			处理者
	 * @return
	 */
	public abstract List<HistoryTask> getHistoryTaskListByProcessInstanceIdAndOwner(
			String processInstanceId, String userId);
	
	/**
	 * 根据任务id  用户名 获取  某个可选任务 并完成
	 * @param path  选择的路径
	 */
	public abstract void takeAndCompleteTask(
			String taskId, String userId, String path);
	
	/**
	 * 获取任务
	 * @param taskId
	 * @param userId
	 */
	public abstract void takeTask(String taskId, String userId);

	/**
	 * 根据历史任务id获取历史任务
	 * @param taskId
	 * @return
	 */
	public abstract HistoryTask getHistoryTaskById(String taskId);
	
	
	/**
	 * 为流程定义注入变量
	 * @param piId
	 * @param params
	 */
	public void executeTaskInjectPrams2PD(String piId,Map<String,String> params);
	
}