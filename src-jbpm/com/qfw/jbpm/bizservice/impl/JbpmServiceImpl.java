package com.qfw.jbpm.bizservice.impl;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jbpm.api.Execution;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.history.HistoryComment;
import org.jbpm.api.history.HistoryProcessInstance;
import org.jbpm.api.history.HistoryTask;
import org.jbpm.api.model.ActivityCoordinates;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfw.jbpm.bizservice.JbpmService;

@Service("jbpmService")
public class JbpmServiceImpl implements JbpmService {
	/**
	 * 流程引擎
	 */
	@Autowired
	private ProcessEngine processEngine;

	private Logger logger = org.slf4j.LoggerFactory
			.getLogger(JbpmServiceImpl.class);

	/**
	 * 流程定义部署
	 */
	@Override
	public void executeTaskDeployProcessDefinition(String resourceXml,
			String resourcePng) {
		String deploymentId = processEngine.getRepositoryService()
				.createDeployment().addResourceFromClasspath(resourceXml)
				.addResourceFromClasspath(resourcePng).deploy();
		logger.info("部署流程定义成功,ID:" + deploymentId);
	}

	@Override
	public void deleteProcessInstance(String deploymentId) {
		// 级联删除，删除流程相关文件 建议使用
		processEngine.getRepositoryService().deleteDeploymentCascade(
				deploymentId);
	}

	@Override
	public List<ProcessDefinition> queryProcessInstance() {
		// 查询流程定义使用RepositoryService ，仓库服务
		List<ProcessDefinition> list = processEngine.getRepositoryService().createProcessDefinitionQuery().list();
		/*for (ProcessDefinition pd : list) {
			logger.info("流程定义ID：" + pd.getId() + ",流程定义名称：" + pd.getName()
					+ ",流程定义key：" + pd.getKey() + ",流程定义版本 :" + pd.getVersion()
					+ ",流程部署ID：" + pd.getDeploymentId());
		}*/
		return list;
	}

	@Override
	public InputStream getResource(String deploymentId, String resourceName) {
		try {
			InputStream is = processEngine.getRepositoryService()
					.getResourceAsStream(deploymentId, resourceName);

			// 将流程图写入到磁盘中
			OutputStream os = new FileOutputStream("e:/process.png");
			for (int b = 0; (b = is.read()) != -1;) {
				os.write(b);
			}
			is.close();
			os.close();

			return is;
		} catch (Exception e) {
			 
		}
		return null;
	}

	/**
	 * 根据流程实例获取对应流程图片
	 * 
	 * @param processInstanceId
	 * @return
	 */
	@Override
	public InputStream findProcessInstancePic(String processInstanceId) {
		ExecutionService executionService = processEngine.getExecutionService();
		RepositoryService repositoryService = processEngine
				.getRepositoryService();
		ProcessInstance processInstance = executionService
				.findProcessInstanceById(processInstanceId);

		String processDefinitionId = processInstance.getProcessDefinitionId();

		ProcessDefinition processDefinition = repositoryService
				.createProcessDefinitionQuery()
				.processDefinitionId(processDefinitionId).uniqueResult();
		return repositoryService.getResourceAsStream(
				processDefinition.getDeploymentId(),
				processDefinition.getImageResourceName());
	}

	@Override
	public String executeTaskStartProcessInstance(String processDefinitionKey,
			Map<String, Object> map) {
		ExecutionService executionService = processEngine.getExecutionService();
		ProcessInstance pi = executionService.startProcessInstanceByKey(
				processDefinitionKey, map);
		String id = pi.getId();
		Collection<Execution> col = (Collection<Execution>) pi.getExecutions();
		for (Execution execution : col) {
			//System.out.println("execution id:"+execution.getId());
		}
		logger.info("流程ID ： " + pi.getId());
		logger.info("流程状态: " + pi.getState());
		return id;
	}

	@Override
	public List<Task> getMyNotFinshedTasks(String userId) {
		List<Task> tasks = processEngine.getTaskService().findPersonalTasks(userId);

		return tasks;
	}
	public List<Task> getMyGroupNotFinshedTasks(String group) {
		List<Task> tasks = processEngine.getTaskService().findGroupTasks(group);
		return tasks;
	}
	
	@Override
	public void executeTask(String taskId) {
		processEngine.getTaskService().completeTask(taskId);
	}

	@Override
	public void executeTask(String taskId, Map<String, Object> paraMap,
			String to) {
		processEngine.getTaskService().completeTask(taskId, to, paraMap);
	}
	
	public void executeTask(String taskId, Map<String, Object> paraMap){
		processEngine.getTaskService().completeTask(taskId,paraMap);
	}

	@Override
	public void executeTaskWithOutcome(String taskId, String outcome) {
		processEngine.getTaskService().completeTask(taskId, outcome);

	}

	/**
	 * 获取我的已完成的任务
	 */
	@Override
	public List<HistoryTask> getMyFinshedTask(String userId) {
		HistoryService historyService = processEngine.getHistoryService();
		List<HistoryTask> list = historyService.createHistoryTaskQuery()
				.state(HistoryTask.STATE_COMPLETED).assignee(userId).list();

		/*
		 * List<HistoryActivityInstance> list =
		 * processEngine.getHistoryService()
		 * .createHistoryActivityInstanceQuery().processInstanceId(piId)
		 * .orderAsc
		 * (HistoryActivityInstanceQuery.PROPERTY_STARTTIME).executionId(piId)
		 * .list();
		 */

		return list;
	}

	@Override
	public ActivityCoordinates getProcessInstanceExecuteNode(
			String processInstanceId) {
		try {
			RepositoryService repositoryService = processEngine
					.getRepositoryService();
			ExecutionService executionService = processEngine
					.getExecutionService();
			ProcessInstance processInstance = executionService
					.findProcessInstanceById(processInstanceId);

			// 根据ID获取流程实例
			Set<String> activityNames = processInstance
					.findActiveActivityNames();
			ActivityCoordinates ac = repositoryService.getActivityCoordinates(
					processInstance.getProcessDefinitionId(), activityNames
							.iterator().next());
			logger.info("x:" + ac.getX() + ",y:" + ac.getY() + ",heigt :"
					+ ac.getHeight() + ",width :" + ac.getWidth());
			return ac;
		} catch (Exception e) {
			 
		}
		return null;
	}

	/**
	 * 根据流程id获取流程相关变量
	 * 
	 * @param processInstanceId
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getProcessInstanceVariable(String processInstanceId) {
		Map<String, Object> variableMap = null;
		// 流程历史变量
		Set<String> variableNames = processEngine.getHistoryService()
				.getVariableNames(processInstanceId);
		variableMap = (Map<String, Object>) processEngine.getHistoryService().getVariables(
				processInstanceId, variableNames);
		return variableMap;
	}
	/**
	 * 获取任务变量
	 * @param taskId
	 * @return
	 */
	public Map<String, Object> getTaskVariable(String taskId){
		Map<String, Object> variableMap = null;
		// 流程历史变量
		Set<String> variableNames = processEngine.getTaskService().getVariableNames(taskId);
		variableMap = (Map<String, Object>) processEngine.getTaskService().getVariables(
				taskId, variableNames);
		return variableMap;
	}

	/**
	 * 获取执行变量
	 * @param taskId
	 * @return
	 */
	public Map<String, Object> getExecutionVariable(String executionId){
		Map<String, Object> variableMap = null;
		// 流程历史变量
		Set<String> variableNames = processEngine.getExecutionService().getVariableNames(executionId);
		variableMap = (Map<String, Object>) processEngine.getExecutionService().getVariables(executionId, variableNames);
		return variableMap;
	}
	
	@Override
	public Task getTaskByProcessIdAndActivityName(String userId,
			String processInstanceId, String activityName) {
		// "资源申请"
		Task task = null;
		List<Task> tasks = processEngine.getTaskService().createTaskQuery()
				.activityName(activityName)
				.processInstanceId(processInstanceId).assignee(userId).list();
		if (tasks != null & tasks.size() > 0) {
			task = tasks.get(tasks.size() - 1);
			logger.info("任务ID:" + task.getId() + ",任务名称：" + task.getName()
					+ ",办理人 :" + task.getAssignee() + ",任务创建时间 :"
					+ task.getCreateTime() + ",所属流程ID：" + task.getExecutionId());
		} else {
			logger.error("没有找到我的最近任务");
		}
		return task;
	}

	/**
	 * 根据TaskId获取Task
	 * 
	 * @return
	 */
	@Override
	public Task getTaskById(String taskId) {
		Task task = null;
		task = processEngine.getTaskService().getTask(taskId);
		return task;
	}

	/**
	 * 根据流程实例ID获取流程
	 */
	@Override
	public ProcessInstance getProcessInstanceById(String piId) {
		ProcessInstance pi = processEngine.getExecutionService()
				.findProcessInstanceById(piId);
		return pi;
	}

	/**
	 * 通过执行实例id获取流程处理id
	 * @param executionId
	 * @return
	 */
	public ProcessInstance getProcessInstanceByExecutionIdId (String executionId) {
		return (ProcessInstance)processEngine.getExecutionService().findExecutionById(executionId).getProcessInstance();
	}
	
	/**
	 * 根据流程实例id获取历史流程定义
	 * 
	 * @param piId
	 * @return
	 */
	@Override
	public HistoryProcessInstance getHistoryProcessInstanceById(String piId) {
		HistoryProcessInstance pi = processEngine.getHistoryService()
				.createHistoryProcessInstanceQuery().processInstanceId(piId)
				.uniqueResult();
		return pi;
	}

	/**
	 * 根据流程实例ID获取历史任务列表
	 * 
	 * @param piId
	 * @return
	 */
	@Override
	public List<HistoryTask> getHistoryTaskListByProcessInstanceId(String piId) {
		// 带处理人名称的
		List<HistoryTask> tasks = processEngine.getHistoryService()
				.createHistoryTaskQuery().executionId(piId)
				.state(HistoryTask.STATE_COMPLETED).list();

		// 带任务名称的
		/*
		 * List<HistoryActivityInstance> list =
		 * processEngine.getHistoryService()
		 * .createHistoryActivityInstanceQuery().processInstanceId(piId)
		 * .orderAsc
		 * (HistoryActivityInstanceQuery.PROPERTY_STARTTIME).executionId(piId)
		 * .list();
		 */

		return tasks;
	}

	/**
	 * 添加任务意见
	 */
	@Override
	public void addTaskComment(String taskId, String comment) {
		processEngine.getTaskService().addTaskComment(taskId, comment);
	}

	/**
	 * 获取任务意见
	 */
	@Override
	public List<HistoryComment> getTaskComments(String taskId) {
		return processEngine.getTaskService().getTaskComments(taskId);
	}

	/**
	 * 根据流程定义key,获取我的待办任务
	 * 
	 * @param userId
	 * @param processDefinitionKey
	 * @return
	 */
	@Override
	public List<Task> getMyNotFinshedTasksByProcessDefinitionKey(String userId,
			String processDefinitionKey) {
		String processDefinitionId = getProcessDefinitionIdByprocessDefinitionKey(processDefinitionKey);
		List<Task> tasks = new ArrayList<Task>();
		if (processDefinitionId != null) {
			tasks = processEngine.getTaskService().createTaskQuery()
					.assignee(userId).processDefinitionId(processDefinitionId)
					.list();
			List<Task> temp = processEngine.getTaskService().createTaskQuery()
					.candidate(userId).processDefinitionId(processDefinitionId)
					.list();
			for (Task t : temp) {
				tasks.add(t);
			}
		}
		return tasks;
	}
	
	/**
	 * 获取用户所有的代办任务
	 * @param userId
	 * @return
	 */
	public List<Task> getAllMyNotFinshedTasks(String userId){
		List<Task> tasks = new ArrayList<Task>();
		tasks = processEngine.getTaskService().createTaskQuery()
				.assignee(userId).list();
		List<Task> temp = processEngine.getTaskService().createTaskQuery()
				.candidate(userId).list();
		tasks.addAll(temp);
		return tasks;
	}
	
	public List<Task> getMyNotFinshedAssigneeTasksByProcessDefinitionId(String userId,String processDefinitionId) {
		List<Task> tasks = new ArrayList<Task>();
		if (processDefinitionId != null) {
			tasks = processEngine.getTaskService().createTaskQuery().assignee(userId).processDefinitionId(processDefinitionId).list();
			List<Task> temp = processEngine.getTaskService().createTaskQuery().candidate(userId).processDefinitionId(processDefinitionId)
					.list();
			for (Task t : temp) {
				tasks.add(t);
			}
		}
		return tasks;
	}

	/**
	 * 通过任务实例id，获取任务
	 * @param userId 用户id
	 * @param processInstanceId 任务实例
	 * @return
	 */
	public List<Task> getMyNotFinshedAssigneeTasksByProcessInstanceId(String userId,String processInstanceId) {
		List<Task> tasks = new ArrayList<Task>();
		if (processInstanceId != null) {
			tasks = processEngine.getTaskService().createTaskQuery().assignee(userId).processInstanceId(processInstanceId).list();
			/*List<Task> temp = processEngine.getTaskService().createTaskQuery()
					.candidate(userId).processDefinitionId(processDefinitionId)
					.list();
			for (Task t : temp) {
				tasks.add(t);
			}*/
		}
		return tasks;
	}
	/**
	 * 根据流程定义key获取最新版本的流程id
	 * 
	 * @param processDefinitionKey
	 * @return
	 */
	@Override
	public String getProcessDefinitionIdByprocessDefinitionKey(
			String processDefinitionKey) {
		String deploymentId = null;
		List<ProcessDefinition> list = processEngine.getRepositoryService()
				.createProcessDefinitionQuery()
				.processDefinitionKey(processDefinitionKey)
				.orderDesc(ProcessDefinitionQuery.PROPERTY_VERSION).list();
		if (list != null && list.size() > 0) {
			deploymentId = list.get(0).getId();
		} else {
			logger.error("流程定义key:" + processDefinitionKey + ",对应的流程实例不存在。");
		}

		return deploymentId;
	}

	@Override
	public List<Task> getMyNotFinshedTasksByActivityName(String userId,
			String activityName) {
		List<Task> tasks = processEngine.getTaskService().createTaskQuery()
				.assignee(userId).activityName(activityName).list();
		return tasks;
	}

	/**
	 * 根据流程定义id获取对应的流程实例列表
	 * 
	 * @param ProcessDefinitionId
	 * @return
	 */
	@Override
	public List<HistoryProcessInstance> getHistoryProcessInstancesByProcessDefinitionId(
			String ProcessDefinitionId) {
		List<HistoryProcessInstance> list = new ArrayList<HistoryProcessInstance>();
		if (ProcessDefinitionId != null) {
			list = processEngine.getHistoryService()
					.createHistoryProcessInstanceQuery()
					.processDefinitionId(ProcessDefinitionId).list();
		}
		return list;
	}

	/**
	 * 根据流程实例id及任务处理人获取历史任务列表
	 * 
	 * @param processInstanceId
	 *            流程实例id
	 * @param userId
	 *            处理者
	 * @return
	 */
	@Override
	public List<HistoryTask> getHistoryTaskListByProcessInstanceIdAndOwner(
			String processInstanceId, String userId) {
		List<HistoryTask> tasks = processEngine.getHistoryService()
				.createHistoryTaskQuery().executionId(processInstanceId)
				.assignee(userId).state(HistoryTask.STATE_COMPLETED).list();
		return tasks;
	}

	@Override
	public void takeAndCompleteTask(String taskId, String userId, String path) {
		processEngine.getTaskService().takeTask(taskId, userId);
		processEngine.getTaskService().completeTask(taskId, path);
	}
	
	/**
	 * 获取任务
	 * @param taskId
	 * @param userId
	 */
	public void takeTask(String taskId, String userId){
		processEngine.getTaskService().takeTask(taskId, userId);
	}

	@Override
	public HistoryTask getHistoryTaskById(String taskId) {
		HistoryTask historyTask = processEngine.getHistoryService()
				.createHistoryTaskQuery().taskId(taskId).uniqueResult();
		return historyTask;
	}
	
	@Override
	public void executeTaskInjectPrams2PD(String piId,Map<String,String> params){
		if(params!=null&&params.size()>0){
			 Set<String> keySet = params.keySet();
			 String value;
			 for (String key : keySet) {
				value = params.get(key);
				if(value!=null&&!value.trim().equals("")){
					processEngine.getExecutionService().setVariable(piId, key, value);
				}
			}
		}
		Set<String> variableNames = processEngine.getExecutionService().getVariableNames(piId);
		Map<String, Object> variables = processEngine.getExecutionService().getVariables(piId, variableNames);
		logger.info("-----");
	}

	public ProcessEngine getProcessEngine() {
		return processEngine;
	}

	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}
	
}
