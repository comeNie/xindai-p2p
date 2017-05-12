package com.qfw.jbpm.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.task.Task;
import org.jbpm.jpdl.internal.model.JpdlProcessDefinition;
import org.primefaces.model.LazyDataModel;

import com.qfw.common.log.LogFactory;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.model.permission.vo.CommonDataModel;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.jbpm.bizservice.JbpmService;
import com.qfw.jbpm.model.vo.FilterTaskVO;
import com.qfw.jbpm.model.vo.TaskDataModel;
import com.qfw.jbpm.model.vo.TaskVO;

@ViewScoped
@ManagedBean(name="myTaskBean")
public class MyTaskBean extends BaseBackingBean{
    
    private LazyDataModel<TaskVO> taskModel;
    
    private FilterTaskVO filterVO = new FilterTaskVO();
    
    private Logger log = LogFactory.getInstance().getPlatformLogger();
	
    @PostConstruct
    public void init(){
    	try {
    		SysUser user = ViewOper.getUser();
    		filterVO.setUserId(user.getUserId().toString());
    		taskModel = new TaskDataModel(filterVO);
		} catch (Exception e) {
			log.error("待办任务查询失败：", e);
			alert("待办任务查询失败："+e.getMessage());
		}
    }
	
	public void search(){
		super.search();
		//init();
	}
	
	public String operate(){
		String url = ViewOper.getParameter("url");
		//System.out.println(url);
		return url;
	}

	public FilterTaskVO getFilterVO() {
		return filterVO;
	}

	public void setFilterVO(FilterTaskVO filterVO) {
		this.filterVO = filterVO;
	}

	public LazyDataModel<TaskVO> getTaskModel() {
		return taskModel;
	}

	public void setTaskModel(LazyDataModel<TaskVO> taskModel) {
		this.taskModel = taskModel;
	}

}
