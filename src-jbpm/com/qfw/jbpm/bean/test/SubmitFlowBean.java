package com.qfw.jbpm.bean.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.jbpm.api.RepositoryService;
import org.jbpm.api.history.HistoryComment;
import org.jbpm.api.history.HistoryTask;
import org.jbpm.api.task.Task;
import org.primefaces.model.UploadedFile;

import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.jbpm.bizservice.JbpmService;

@RequestScoped
@ManagedBean(name="submitFlowBean")
public class SubmitFlowBean extends BaseBackingBean {

    private UploadedFile file;
    
    @ManagedProperty(value = "#{repositoryService}")
    private RepositoryService repositoryService;
    
    @ManagedProperty(value = "#{jbpmService}")
    private JbpmService jbpmService;
    
    private Date date;
    
    private int day;
    
    private String reason;
    
    private String taskid;
    
    
    private List<String> comments;

    @PostConstruct
    public void init(){
    	if(taskid == null){
    		taskid = ViewOper.getParameter("taskid");
    	}
    	if(taskid == null){
    		return;
    	}
    	comments = new ArrayList<String>();
    	if(taskid!=null && !taskid.isEmpty()){
    		Task task = jbpmService.getTaskById(taskid);
        	
        	Map<String,Object> map = jbpmService.getExecutionVariable(task.getExecutionId());
        	if(map.get("date")!=null){
        		date = (Date) map.get("date");
        	}
        	if(map.get("reason")!=null){
        		reason = (String) map.get("reason");
        	}
        	if(map.get("day")!=null){
        		day = (Integer) map.get("day");
        	}
        	
        	List<HistoryTask> hisTasks = jbpmService.getHistoryTaskListByProcessInstanceId(task.getExecutionId());
        	
        	for (HistoryTask historyTask : hisTasks) {
        		
        		List<HistoryComment> coms = jbpmService.getTaskComments(historyTask.getId());
            	if(coms!= null && !coms.isEmpty()){
            		String comment = coms.get(0).getMessage();
            		if(comment!=null && !comment.isEmpty()){
            			comments.add(comment);
            		}
            	}
			}
        	
    	}
    	
    }
    
    public void submit(){
    	SysUser user = ViewOper.getUser();
    	Map map = new HashMap();
    	map.put("date", date);
    	map.put("reason", reason);
    	map.put("userId", String.valueOf(user.getUserId()));
    	map.put("roleId", "1");
    	map.put("day", day);
    	
    	
    	if(taskid == null || taskid.isEmpty()){
    		String processInstanceId = jbpmService.executeTaskStartProcessInstance("secondLevel", map);
        	List<Task> tasks = jbpmService.getMyNotFinshedAssigneeTasksByProcessInstanceId(String.valueOf(user.getUserId()),processInstanceId);
        	if(tasks != null && !tasks.isEmpty()){
        		for (Task task : tasks) {
        			jbpmService.executeTask(task.getId(), map);
    			}
        		
        	}
    	}else{
    		jbpmService.executeTask(taskid, map);
    	}
    	
    	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"提交成功", null);
		FacesContext.getCurrentInstance().addMessage(null, message);
    }

	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	public JbpmService getJbpmService() {
		return jbpmService;
	}

	public void setJbpmService(JbpmService jbpmService) {
		this.jbpmService = jbpmService;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public List<String> getComments() {
		return comments;
	}

	public void setComments(List<String> comments) {
		this.comments = comments;
	}

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

    
}