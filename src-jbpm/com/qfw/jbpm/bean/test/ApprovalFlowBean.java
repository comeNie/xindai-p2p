package com.qfw.jbpm.bean.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.task.Task;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.jbpm.bizservice.JbpmService;

@RequestScoped
@ManagedBean(name="approvalFlowBean")
public class ApprovalFlowBean extends BaseBackingBean {

    private UploadedFile file;
    
    @ManagedProperty(value = "#{repositoryService}")
    private RepositoryService repositoryService;
    
    @ManagedProperty(value = "#{jbpmService}")
    private JbpmService jbpmService;
    
    private Date date;
    
    private int day;
    
    private String reason;
    
    private String comment;
    
    private String taskid;

    @PostConstruct
    public void init(){
    	if(taskid == null){
    		taskid = ViewOper.getParameter("taskid");
    	}
    	if(taskid == null){
    		return;
    	}
    	Task task = jbpmService.getTaskById(taskid);
    	
    	Map<String,Object> map = jbpmService.getExecutionVariable(task.getExecutionId());
    	
    	if(map != null){
    		if(map.get("date")!=null){
        		date = (Date) map.get("date");
        	}
        	if(map.get("reason")!=null){
        		reason = (String) map.get("reason");
        	}
        	if(map.get("day")!=null){
        		day = (Integer) map.get("day");
        	}
    	}
    	
    }
    public void submit(){
    	Task task = jbpmService.getTaskById(taskid);
    	jbpmService.addTaskComment(taskid, comment);
    	SysUser user = ViewOper.getUser();
    	jbpmService.takeAndCompleteTask(task.getId(), user.getUserId().toString(), "结束");
    	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"提交成功", null);
		FacesContext.getCurrentInstance().addMessage(null, message);
    	
    }
    
    public void reject(){
    	Task task = jbpmService.getTaskById(taskid);
    	SysUser user = ViewOper.getUser();
    	jbpmService.addTaskComment(taskid, comment);
    	jbpmService.takeAndCompleteTask(task.getId(), user.getUserId().toString(), "驳回");
    	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"拒绝成功", null);
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
	public String getTaskid() {
		return taskid;
	}
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
    
}