package com.qfw.jbpm.bizservice.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.permission.SysRole;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.model.vo.PageList;
import com.qfw.common.util.CollectionUtil;
import com.qfw.common.util.StringUtils;
import com.qfw.jbpm.bizservice.IFlowBS;
import com.qfw.jbpm.bizservice.JbpmService;
import com.qfw.jbpm.model.JbpmConst;
import com.qfw.jbpm.model.bo.Jbpm4AuditOpinion;
import com.qfw.jbpm.model.bo.Jbpm4Expand;
import com.qfw.jbpm.model.vo.FilterTaskVO;
import com.qfw.jbpm.model.vo.TaskVO;

@Service("flowBS")
public class FlowBSImpl extends BaseServiceImpl implements IFlowBS {

	private Logger log = Logger.getLogger(FlowBSImpl.class);
	
	@Autowired
	private JbpmService jbpmService;
	/**
	 * 查询执行中的任务已办语句
	 */
//	private final String FIND_HIST_TASK_SQL_0 = "SELECT TA.DBID_ AS TASK_ID,TA.EXECUTION_ID_ AS EXECUTION_ID,(SELECT DICT.DISPLAY_VALUE FROM JBPM4_DEPLOYPROP DP, SYS_CODE_DICT DICT WHERE DP.KEY_ = 'pdid' AND DP.STRINGVAL_ = EX.PROCDEFID_ AND DP.OBJNAME_ = DICT.CODE_VALUE AND DICT.CODE_TYPE = 'FOLWNAME') AS TX_NAME, TA.CREATE_ AS PROCESS_DATE, TA.NAME_ AS CUR_TASK_NAME, (SELECT U.USER_NAME FROM SYS_USER U WHERE U.USER_ID = TA.ASSIGNEE_) AS CUR_PROCESS_USER,TA.FORM_ AS URL_PATH,ED.APPLY_USER_NAME,ED.SYS_CREATE_TIME AS APPLY_TIME,ED.REMARK FROM JBPM4_EXECUTION EX, JBPM4_TASK TA LEFT JOIN JBPM4_EXPAND ED ON TA.EXECUTION_ID_ = ED.WORK_ITEM_ID WHERE EX.ID_ = TA.EXECUTION_ID_ AND EXISTS (SELECT 1 FROM JBPM4_HIST_TASK HTA  WHERE HTA.EXECUTION_ = EX.ID_ AND HTA.ASSIGNEE_ = ?) ";
	private final String FIND_HIST_TASK_SQL_0 = "SELECT TA.DBID_ AS TASK_ID,TA.EXECUTION_ID_ AS EXECUTION_ID,(SELECT DICT.DISPLAY_VALUE FROM JBPM4_DEPLOYPROP DP, SYS_CODE_DICT DICT WHERE DP.KEY_ = 'pdid' AND DP.STRINGVAL_ = EX.PROCDEFID_ AND DP.OBJNAME_ = DICT.CODE_VALUE AND DICT.CODE_TYPE = 'FOLWNAME') AS TX_NAME, TA.CREATE_ AS PROCESS_DATE, TA.NAME_ AS CUR_TASK_NAME, (SELECT U.USER_NAME FROM SYS_USER U WHERE U.USER_ID = TA.ASSIGNEE_) AS CUR_PROCESS_USER,TA.FORM_ AS URL_PATH,ED.APPLY_USER_NAME,ED.SYS_CREATE_TIME AS APPLY_TIME,ED.REMARK FROM JBPM4_EXECUTION EX, JBPM4_TASK TA LEFT JOIN JBPM4_EXPAND ED ON TA.EXECUTION_ID_ = ED.WORK_ITEM_ID WHERE EX.ID_ = TA.EXECUTION_ID_ AND EXISTS (SELECT 1 FROM JBPM4_HIST_TASK HTA  WHERE HTA.EXECUTION_ = EX.ID_ AND HTA.ASSIGNEE_ = ? AND STATE_ = 'completed') ";
	/**
	 * 查询执行完成的任务已办语句
	 */
	private final String FIND_HIST_TASK_SQL_1 ="SELECT 0 AS TASK_ID, HPI.ID_ AS EXECUTION_ID,(SELECT DICT.DISPLAY_VALUE FROM JBPM4_DEPLOYPROP DP, SYS_CODE_DICT DICT WHERE DP.KEY_ = 'pdid' AND DP.STRINGVAL_ = HPI.PROCDEFID_ AND DP.OBJNAME_ = DICT.CODE_VALUE AND DICT.CODE_TYPE = 'FOLWNAME') AS TX_NAME, HPI.END_ AS PROCESS_DATE, '流程结束' AS CUR_TASK_NAME, '' AS CUR_PROCESS_USER,  '' AS URL_PATH,ED.APPLY_USER_NAME,ED.SYS_CREATE_TIME AS APPLY_TIME,ED.REMARK  FROM JBPM4_HIST_PROCINST HPI LEFT JOIN JBPM4_EXPAND ED ON HPI.ID_ = ED.WORK_ITEM_ID  WHERE HPI.ID_ IN (SELECT DISTINCT HTA.EXECUTION_ FROM JBPM4_HIST_TASK HTA WHERE HTA.EXECUTION_ = HPI.ID_ AND HTA.ASSIGNEE_ = ?)  AND HPI.ID_ NOT IN (SELECT EX.ID_ FROM JBPM4_EXECUTION EX WHERE HPI.ID_ = EX.ID_)";
	/**
	 * 查询代办任务语句
	 */
	private final String FIND_TASK_SQL = "SELECT TA.DBID_ AS TASK_ID,  TA.EXECUTION_ID_ AS EXECUTION_ID,  (SELECT DICT.DISPLAY_VALUE FROM JBPM4_DEPLOYPROP DP, SYS_CODE_DICT DICT  WHERE DP.KEY_ = 'pdid'  AND DP.STRINGVAL_ = EX.PROCDEFID_  AND DP.OBJNAME_ = DICT.CODE_VALUE  AND DICT.CODE_TYPE = 'FOLWNAME') AS TX_NAME, TA.CREATE_ AS PROCESS_DATE, TA.NAME_ AS CUR_TASK_NAME, (SELECT U.USER_NAME FROM SYS_USER U WHERE U.USER_ID = TA.ASSIGNEE_) AS CUR_PROCESS_USER, TA.FORM_ AS URL_PATH,ED.APPLY_USER_NAME,ED.SYS_CREATE_TIME AS APPLY_TIME,ED.REMARK FROM JBPM4_EXECUTION EX, JBPM4_TASK TA LEFT JOIN JBPM4_EXPAND ED ON TA.EXECUTION_ID_ = ED.WORK_ITEM_ID WHERE EX.ID_ = TA.EXECUTION_ID_ AND (TA.ASSIGNEE_ = ? OR EXISTS (SELECT 1 FROM JBPM4_PARTICIPATION PA WHERE PA.TASK_ = TA.DBID_ AND PA.TYPE_ = 'candidate' AND PA.GROUPID_ IN (SELECT UR.ROLE_ID FROM SYS_USER_ROLE UR WHERE UR.USER_ID = ?)))";
	/**
	 * 查询用户已办任务
	 * 
	 * @param userId
	 * @param first
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageList<TaskVO> findMyHistoryTask(FilterTaskVO filterVO, int first, int pageSize) {
		Object[] args = wrapMyHistoryTaskSQL(filterVO);
		log.debug("用户"+filterVO.getUserId()+"查询已办任务执行SQL："+args[0]);
		
		PageList<TaskVO> pl = new PageList<TaskVO>();
		int count = getCommonQuery().findCountByWapSQL((String)args[0], (Object[])args[1]) ;
		pl.setCount(count);
		List<TaskVO> tasks = getCommonQuery().findBySQLByPages((String)args[0], first,
				pageSize, (Object[])args[1],TaskVO.class);
		pl.setData(tasks);
		
		return pl;
	}
	
	/**
	 * 查询用户待办任务
	 * 
	 * @param userId
	 * @param first
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageList<TaskVO> findMyTask(FilterTaskVO filterVO, int first, int pageSize) {
		
		Object[] args = wrapMyTaskSQL(filterVO);
		log.debug("用户"+filterVO.getUserId()+"查询待办任务执行SQL："+args[0]);
		
		PageList<TaskVO> pl = new PageList<TaskVO>();
		int count = getCommonQuery().findCountByWapSQL((String)args[0], (Object[])args[1]) ;
		pl.setCount(count);
		List<TaskVO> tasks = getCommonQuery().findBySQLByPages((String)args[0], first,
				pageSize, (Object[])args[1],TaskVO.class);
		pl.setData(tasks);
		
		return pl;
	}
	
	/**
	 * 获取任务并执行下一任务
	 */
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void takeAndCompleteTask(String taskId,String to,String comment,Map<String, Object> paraMap) throws BizException{
		try{
			
			SysUser curAuditUser = (SysUser) paraMap.remove(JbpmConst.CUR_AUDIT_USER);
			if(curAuditUser == null){
				throw new BizException("当前审批人不能为空");
			}
			
			SysUser auditUser = (SysUser) paraMap.remove(JbpmConst.NEXT_AUDIT_USER);//获取下一审批人
			
			if(auditUser != null){
				paraMap.put(JbpmConst.NEXT_AUDIT_USER, String.valueOf(auditUser.getUserId()));
			}
			
			SysRole auditRole = (SysRole) paraMap.remove(JbpmConst.AUDIT_ROLE);
			if(auditRole!=null){
				paraMap.put(JbpmConst.AUDIT_ROLE, String.valueOf(auditRole.getRoleId()));
			}
			
			Task task = jbpmService.getTaskById(taskId);
			String exId = task.getExecutionId();
			if(StringUtils.isNotEmpty(exId)){
				Date now = new Date();
				Jbpm4AuditOpinion jao = new Jbpm4AuditOpinion();
				jao.setApprovalUserId(curAuditUser.getUserId());
				jao.setApprovalUserName(curAuditUser.getUserName());
				jao.setComment(comment);
				jao.setWorkItemId(exId);
				jao.setSysCreateTime(now);
				jao.setSysCreateUser(curAuditUser.getUserId());
				jao.setSysUpdateUser(curAuditUser.getUserId());
				jao.setAuditStatusCd(String.valueOf(paraMap.get(JbpmConst.AUDIT_STATUS_CD)));
				if(auditRole!=null){
					jao.setApprovalRoleId(auditRole.getRoleId());
					jao.setApprovalRoleName(auditRole.getRoleName());
				}
				save(jao);
				if(paraMap.get(JbpmConst.FLOW_REMARK)!=null || paraMap.get(JbpmConst.FLOW_HTML)!=null){
					List<Jbpm4Expand> exps= findByHQL("from Jbpm4Expand where workItemId = '"+exId+"'");
					if(CollectionUtil.isNotEmpty(exps)){
						for (Jbpm4Expand je : exps) {
							if(paraMap.get(JbpmConst.FLOW_REMARK)!=null){
								je.setRemark((String) paraMap.get(JbpmConst.FLOW_REMARK));
							}
							if(paraMap.get(JbpmConst.FLOW_HTML)!=null){
								je.setHtml((String) paraMap.remove(JbpmConst.FLOW_HTML));
							}
							update(je);
						}
					}
					
				}
			}else{
				throw new BizException("获取流程实例id为空");
			}
			jbpmService.takeTask(taskId, String.valueOf(curAuditUser.getUserId()));
	    	jbpmService.executeTask(taskId, paraMap, to);
	    	
		}catch(BizException e){
			throw e;
		}catch(Exception e){
			throw new BizException(e.getMessage());
		}
		
	}
	/**
	 * 获取任务并执行下一任务,不带审批意见
	 * @param taskId 任务id
	 * @param userId 用户id
	 * @param to 跳转流程
	 * @param paraMap 参数map
	 */
	public void takeAndCompleteTask(String taskId,String to,Map<String, Object> paraMap) throws BizException{

		try{
			
			SysUser curAuditUser = (SysUser) paraMap.remove(JbpmConst.CUR_AUDIT_USER);
			if(curAuditUser == null){
				throw new BizException("当前审批人不能为空");
			}
			
			SysUser auditUser = (SysUser) paraMap.remove(JbpmConst.NEXT_AUDIT_USER);//获取下一审批人
			
			if(auditUser != null){
				paraMap.put(JbpmConst.NEXT_AUDIT_USER, String.valueOf(auditUser.getUserId()));
			}
			
			SysRole auditRole = (SysRole) paraMap.remove(JbpmConst.AUDIT_ROLE);
			if(auditRole!=null){
				paraMap.put(JbpmConst.AUDIT_ROLE, String.valueOf(auditRole.getRoleId()));
			}
			
			if(paraMap.get(JbpmConst.FLOW_REMARK)!=null || paraMap.get(JbpmConst.FLOW_HTML)!=null){
				Task task = jbpmService.getTaskById(taskId);
				String exId = task.getExecutionId();
				List<Jbpm4Expand> exps= findByHQL("from Jbpm4Expand where workItemId = '"+exId+"'");
				if(CollectionUtil.isNotEmpty(exps)){
					for (Jbpm4Expand je : exps) {
						if(paraMap.get(JbpmConst.FLOW_REMARK)!=null){
							je.setRemark((String) paraMap.get(JbpmConst.FLOW_REMARK));
						}
						if(paraMap.get(JbpmConst.FLOW_HTML)!=null){
							je.setHtml((String) paraMap.remove(JbpmConst.FLOW_HTML));
						}
						update(je);
					}
				}
				
			}
			
			jbpmService.takeTask(taskId, String.valueOf(curAuditUser.getUserId()));
	    	jbpmService.executeTask(taskId, paraMap, to);
	    	
		}catch(BizException e){
			throw e;
		}catch(Exception e){
			throw new BizException(e.getMessage());
		}
		
	
	}
	
	/**
	 * 执行下一任务，带审批意见。
	 * @param taskId
	 * @param to
	 * @param comment
	 * @param paraMap
	 * @throws BizException
	 */
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void completeTask(String taskId,String to,String comment,Map<String, Object> paraMap) throws BizException{
		try{
			
			SysUser curAuditUser = (SysUser) paraMap.remove(JbpmConst.CUR_AUDIT_USER);
			if(curAuditUser == null){
				throw new BizException("当前审批人不能为空");
			}
			
			SysUser auditUser = (SysUser) paraMap.remove(JbpmConst.NEXT_AUDIT_USER);//获取下一审批人
			
			if(auditUser != null){
				paraMap.put(JbpmConst.NEXT_AUDIT_USER, String.valueOf(auditUser.getUserId()));
			}
			
			SysRole auditRole = (SysRole) paraMap.remove(JbpmConst.AUDIT_ROLE);
			if(auditRole!=null){
				paraMap.put(JbpmConst.AUDIT_ROLE, String.valueOf(auditRole.getRoleId()));
			}
			
			/*if(comment != null && !comment.isEmpty()){
				jbpmService.addTaskComment(taskId, comment);
			}*/
			
			Task task = jbpmService.getTaskById(taskId);
			String exId = task.getExecutionId();
			if(StringUtils.isNotEmpty(exId)){
				Date now = new Date();
				Jbpm4AuditOpinion jao = new Jbpm4AuditOpinion();
				jao.setApprovalUserId(curAuditUser.getUserId());
				jao.setApprovalUserName(curAuditUser.getUserName());
				jao.setComment(comment);
				jao.setWorkItemId(exId);
				jao.setSysCreateTime(now);
				jao.setSysCreateUser(curAuditUser.getUserId());
				jao.setSysUpdateUser(curAuditUser.getUserId());
				jao.setAuditStatusCd(String.valueOf(paraMap.get(JbpmConst.AUDIT_STATUS_CD)));
				if(auditRole!=null){
					jao.setApprovalRoleId(auditRole.getRoleId());
					jao.setApprovalRoleName(auditRole.getRoleName());
				}
				save(jao);
				if(paraMap.get(JbpmConst.FLOW_REMARK)!=null || paraMap.get(JbpmConst.FLOW_HTML)!=null){
					List<Jbpm4Expand> exps= findByHQL("from Jbpm4Expand where workItemId = '"+exId+"'");
					if(CollectionUtil.isNotEmpty(exps)){
						for (Jbpm4Expand je : exps) {
							if(paraMap.get(JbpmConst.FLOW_REMARK)!=null){
								je.setRemark((String) paraMap.get(JbpmConst.FLOW_REMARK));
							}
							if(paraMap.get(JbpmConst.FLOW_HTML)!=null){
								je.setHtml((String) paraMap.remove(JbpmConst.FLOW_HTML));
							}
							update(je);
						}
					}
					
				}
				
			}else{
				throw new BizException("获取流程实例id为空");
			}
	    	jbpmService.executeTask(taskId, paraMap, to);
	    	
		}catch(BizException e){
			throw e;
		}catch(Exception e){
			throw new BizException(e.getMessage());
		}
		
	}
	
	/**
	 * 执行下一任务，不带审批意见。
	 * @param taskId
	 * @param to
	 * @param paraMap
	 * @throws BizException
	 */
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void completeTask(String taskId,String to,Map<String, Object> paraMap) throws BizException{
		try{
			
			SysUser curAuditUser = (SysUser) paraMap.remove(JbpmConst.CUR_AUDIT_USER);
			if(curAuditUser == null){
				throw new BizException("当前审批人不能为空");
			}
			
			SysUser auditUser = (SysUser) paraMap.remove(JbpmConst.NEXT_AUDIT_USER);//获取下一审批人
			
			if(auditUser != null){
				paraMap.put(JbpmConst.NEXT_AUDIT_USER, String.valueOf(auditUser.getUserId()));
			}
			
			SysRole auditRole = (SysRole) paraMap.remove(JbpmConst.AUDIT_ROLE);
			if(auditRole!=null){
				paraMap.put(JbpmConst.AUDIT_ROLE, String.valueOf(auditRole.getRoleId()));
			}
			
			if(paraMap.get(JbpmConst.FLOW_REMARK)!=null || paraMap.get(JbpmConst.FLOW_HTML)!=null){
				Task task = jbpmService.getTaskById(taskId);
				String exId = task.getExecutionId();
				List<Jbpm4Expand> exps= findByHQL("from Jbpm4Expand where workItemId = '"+exId+"'");
				if(CollectionUtil.isNotEmpty(exps)){
					for (Jbpm4Expand je : exps) {
						if(paraMap.get(JbpmConst.FLOW_REMARK)!=null){
							je.setRemark((String) paraMap.get(JbpmConst.FLOW_REMARK));
						}
						if(paraMap.get(JbpmConst.FLOW_HTML)!=null){
							je.setHtml((String) paraMap.remove(JbpmConst.FLOW_HTML));
						}
						update(je);
					}
				}
				
			}
			
	    	jbpmService.executeTask(taskId, paraMap, to);
	    	
		}catch(BizException e){
			throw e;
		}catch(Exception e){
			throw new BizException(e.getMessage());
		}
		
	}
	
	/**
	 * 启动流程
	 * @param processDefinitionKey 流程名称
	 * @param userId 用户
	 * @param map 传入参数
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public String startProcessInstance(String processDefinitionKey,String to,Map<String, Object> map)throws BizException{
		//启动任务实例
		
		SysUser applyUser = (SysUser) map.remove(JbpmConst.APPLY_USER);
		
		if(applyUser == null){
			
		}else{
			map.put(JbpmConst.APPLY_USER, String.valueOf(applyUser.getUserId()));
		}
		
		SysUser auditUser = (SysUser) map.remove(JbpmConst.NEXT_AUDIT_USER);
		if(auditUser!=null){
			map.put(JbpmConst.NEXT_AUDIT_USER, String.valueOf(auditUser.getUserId()));
		}
		
		SysRole auditRole = (SysRole) map.remove(JbpmConst.AUDIT_ROLE);
		if(auditRole!=null){
			map.put(JbpmConst.AUDIT_ROLE, String.valueOf(auditRole.getRoleId()));
		}
		
		String processInstanceId = jbpmService.executeTaskStartProcessInstance(processDefinitionKey, map);
		if(StringUtils.isNotEmpty(processInstanceId)){
			Date now = new Date();
			Jbpm4Expand je = new Jbpm4Expand();
			je.setWorkItemId(processInstanceId);
			je.setApplyUserId(applyUser.getUserId());
			je.setApplyUserName(applyUser.getUserName());
			je.setSysCreateTime(now);
			je.setSysUpdateUser(applyUser.getUserId());
			je.setSysCreateUser(applyUser.getUserId());
			je.setRemark((String) map.get(JbpmConst.FLOW_REMARK));
			save(je);
		}else{
			throw new BizException("获取流程实例id为空");
		}
		return processInstanceId;
	}
	
	/**
	 * 启动流程，并执行下一节点流程
	 * @param processDefinitionKey 流程名称
	 * @param userId 用户
	 * @param map 传入参数
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public String startProcessInstanceAndCompleteTask(String processDefinitionKey,String to,Map<String, Object> map)throws BizException{
		//启动任务实例
		//String userId = String.valueOf(map.get(JbpmConst.APPLY_USER));
		
		SysUser applyUser = (SysUser) map.remove(JbpmConst.APPLY_USER);
		
		/*if(StringUtils.isNotEmpty(userId)){
			user = (SysUser) find(SysUser.class, Integer.valueOf(userId));
		}*/
		
		if(applyUser == null){
			log.error("获取不到任务执行用户");
			throw new BizException("获取不到任务执行用户");
		}else{
			map.put(JbpmConst.APPLY_USER, String.valueOf(applyUser.getUserId()));
		}
		
		SysUser auditUser = (SysUser) map.remove(JbpmConst.NEXT_AUDIT_USER);
		if(auditUser!=null){
			map.put(JbpmConst.NEXT_AUDIT_USER, String.valueOf(auditUser.getUserId()));
		}
		
		SysRole auditRole = (SysRole) map.remove(JbpmConst.AUDIT_ROLE);
		if(auditRole!=null){
			map.put(JbpmConst.AUDIT_ROLE, String.valueOf(auditRole.getRoleId()));
		}
		
		String processInstanceId = jbpmService.executeTaskStartProcessInstance(processDefinitionKey, map);
		if(StringUtils.isNotEmpty(processInstanceId)){
			Date now = new Date();
			Jbpm4Expand je = new Jbpm4Expand();
			je.setWorkItemId(processInstanceId);
			je.setApplyUserId(applyUser.getUserId());
			je.setApplyUserName(applyUser.getUserName());
			je.setSysCreateTime(now);
			je.setSysUpdateUser(applyUser.getUserId());
			je.setSysCreateUser(applyUser.getUserId());
			je.setRemark((String) map.get(JbpmConst.FLOW_REMARK));
			save(je);
		}else{
			throw new BizException("获取流程实例id为空");
		}
    	//执行代办任务
		List<Task> tasks = jbpmService.getMyNotFinshedAssigneeTasksByProcessInstanceId(String.valueOf(applyUser.getUserId()),processInstanceId);
    	if(tasks != null && !tasks.isEmpty()){
    		for (Task task : tasks) {
    			//执行任务
    			//jbpmService.executeTask(task.getId(), map);
    			jbpmService.executeTask(task.getId(), map, to);
			}
    		
    	}
		return processInstanceId;
	}


	/**
	 * 获取审批意见信息
	 * @param processInstanceId
	 * @return
	 */
	public List<Jbpm4AuditOpinion> getAuditOpinion(String processInstanceId){
		return this.getHibernateTemplate().find("from Jbpm4AuditOpinion where workItemId = ?",processInstanceId);
	}
	/**
	 * 组织待办任务语句
	 * @param filterVO
	 * @return
	 */
	private Object[] wrapMyTaskSQL(FilterTaskVO filterVO){
		
		StringBuilder sb = new StringBuilder(FIND_TASK_SQL);
		List<Object> args = new ArrayList<Object>();
		args.add(filterVO.getUserId());
		args.add(filterVO.getUserId());
		
		if(StringUtils.isNotEmpty(filterVO.getProcdefId())){
			sb.append(" AND EXISTS (SELECT 1 FROM JBPM4_DEPLOYPROP DP1 WHERE DP1.KEY_ = 'pdid' AND DP1.STRINGVAL_ = EX.PROCDEFID_ AND DP1.OBJNAME_ = ?)");
			args.add(filterVO.getProcdefId());
		}
		if(filterVO.getStartDate() != null){
			sb.append(" AND TA.CREATE_ >= ?");
			args.add(filterVO.getStartDate());
		}
		if(filterVO.getEndDate() != null){
			sb.append(" AND TA.CREATE_ <= ?");
			args.add(filterVO.getEndDate());
		}
		sb.append(" order by ta.CREATE_ desc ");
		return new Object[]{sb.toString(),args.toArray()};
	}
	/**
	 * 组装已办任务
	 * @param filterVO
	 * @return
	 */
	private Object[] wrapMyHistoryTaskSQL(FilterTaskVO filterVO){
		StringBuilder sb0 = new StringBuilder(FIND_HIST_TASK_SQL_0);
		StringBuilder sb1 = new StringBuilder(FIND_HIST_TASK_SQL_1);
		List<Object> args0 = new ArrayList<Object>();
		List<Object> args1 = new ArrayList<Object>();
		args0.add(filterVO.getUserId());
		args1.add(filterVO.getUserId());
		
		if(StringUtils.isNotEmpty(filterVO.getProcdefId())){
			sb0.append(" AND EXISTS (SELECT 1 FROM JBPM4_DEPLOYPROP DP1 WHERE DP1.KEY_ = 'pdid' AND DP1.STRINGVAL_ = EX.PROCDEFID_ AND DP1.OBJNAME_ = ?)");
			args0.add(filterVO.getProcdefId());
			sb1.append("  AND EXISTS (SELECT 1 FROM JBPM4_DEPLOYPROP DP1 WHERE DP1.KEY_ = 'pdid' AND DP1.STRINGVAL_ = HPI.PROCDEFID_ AND DP1.OBJNAME_ = ?)");
			args1.add(filterVO.getProcdefId());
		}
		if(filterVO.getStartDate() != null){
			sb0.append(" AND TA.CREATE_ >= ?");
			args0.add(filterVO.getStartDate());
			sb1.append(" AND HPI.END_ >= ?");
			args1.add(filterVO.getStartDate());
		}
		if(filterVO.getEndDate() != null){
			sb0.append(" AND TA.CREATE_ <= ?");
			args0.add(filterVO.getEndDate());
			sb1.append(" AND HPI.END_ <= ?");
			args1.add(filterVO.getEndDate());
		}
		args0.addAll(args1);
		StringBuffer result = new StringBuffer("select * from (");
		result.append(sb0.append(" UNION ALL ").append(sb1).toString());
		result.append(") tem where 1=1 order by tem.PROCESS_DATE desc ");
		return new Object[]{result.toString(),args0.toArray()};
	}
	
	public boolean isReturned(String workItemId,String taskName){
		String sql = "SELECT COUNT(1) FROM JBPM4_HIST_ACTINST J WHERE J.DBVERSION_ = '1' AND J.EXECUTION_ = ? AND J.ACTIVITY_NAME_ = ?";
		return getJdbcTemplate().queryForInt(sql, workItemId,taskName) > 0;
	}

	public JbpmService getJbpmService() {
		return jbpmService;
	}

	public void setJbpmService(JbpmService jbpmService) {
		this.jbpmService = jbpmService;
	}
	
	
}
