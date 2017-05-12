package com.qfw.jbpm.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.jpdl.internal.model.JpdlProcessDefinition;

import com.qfw.common.log.LogFactory;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.jbpm.bizservice.JbpmService;

@ViewScoped
@ManagedBean(name="flowManageBean")
public class FlowManageBean extends BaseBackingBean{
    //private TreeNodeImpl rootNode;
    
    @ManagedProperty(value = "#{jbpmService}")
    private JbpmService jbpmService;
    
    private List<ProcessDefinition> processDefinitions;
    
    private JpdlProcessDefinition selectPrpDef;
        
    private Logger log = LogFactory.getInstance().getPlatformLogger();
	
    @PostConstruct
    public void init(){
    	try {
    		processDefinitions = jbpmService.queryProcessInstance();
    		
		} catch (Exception e) {
			log.error("流程定义查询失败：", e);
			alert("流程定义查询失败："+e.getMessage());
		}
    }
	
	public void search(){
		init();
	}
	
	public void operate(){
		String operateFlag = ViewOper.getParameter("operateFlag");
		if("view".equalsIgnoreCase(operateFlag) || "edit".equalsIgnoreCase(operateFlag)){
			
		}else if("delete".equalsIgnoreCase(operateFlag)){
			if(selectPrpDef != null){
				jbpmService.deleteProcessInstance(selectPrpDef.getDeploymentId());
				search();
			}
		}
	}

	public JbpmService getJbpmService() {
		return jbpmService;
	}

	public void setJbpmService(JbpmService jbpmService) {
		this.jbpmService = jbpmService;
	}

	public List<ProcessDefinition> getProcessDefinitions() {
		return processDefinitions;
	}

	public void setProcessDefinitions(List<ProcessDefinition> processDefinitions) {
		this.processDefinitions = processDefinitions;
	}

	public JpdlProcessDefinition getSelectPrpDef() {
		return selectPrpDef;
	}

	public void setSelectPrpDef(JpdlProcessDefinition selectPrpDef) {
		this.selectPrpDef = selectPrpDef;
	}

	
}
