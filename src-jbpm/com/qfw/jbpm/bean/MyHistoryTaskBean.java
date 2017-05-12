package com.qfw.jbpm.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;

import com.qfw.common.log.LogFactory;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.jbpm.model.vo.FilterTaskVO;
import com.qfw.jbpm.model.vo.HistoryTaskDataModel;
import com.qfw.jbpm.model.vo.TaskVO;

@ViewScoped
@ManagedBean(name="myHistoryTaskBean")
public class MyHistoryTaskBean extends BaseBackingBean{
    
    private LazyDataModel<TaskVO> taskModel;
    
    private FilterTaskVO filterVO = new FilterTaskVO();
    
    private Logger log = LogFactory.getInstance().getPlatformLogger();
	
    @PostConstruct
    public void init(){
    	try {
    		SysUser user = ViewOper.getUser();
    		filterVO.setUserId(user.getUserId().toString());
    		taskModel = new HistoryTaskDataModel(filterVO);
		} catch (Exception e) {
			log.error("已办任务查询失败：", e);
			alert("已办任务查询失败："+e.getMessage());
		}
    }
	
	public void operate(){
		
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
