package com.qfw.batch.bean;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.qfw.batch.bizservice.IBatchJobBS;
import com.qfw.batch.bizservice.schedule.BatchDaemoUCC;
import com.qfw.batch.bizservice.util.JobUtil;
import com.qfw.batch.model.bo.BatchJob;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;

@ViewScoped
@ManagedBean(name = "batchMainManageBean")
public class BatchMainManageBean extends BaseBackingBean {
	private TreeNode jobRoot = new DefaultTreeNode("root", null);
	@ManagedProperty(value = "#{batchJobBS}")
	private IBatchJobBS batchJobBS;
	
	@PostConstruct
    public void init(){
		jobRoot = batchJobBS.getJobTree();
	}
	/**
	 * 启动框架
	 */
	public void mainStart(){
		BatchDaemoUCC.mainStart();
		/*FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"启动成功", null);
		FacesContext.getCurrentInstance().addMessage(null, message);*/
		RequestContext.getCurrentInstance().execute("alert('启动成功')");
	}
	/**
	 * 启动任务
	 */
	public void startJob(){
		String jobId = ViewOper.getParameter("job_id");
		if(jobId!=null && !jobId.isEmpty()){
			BatchJob batchJob = batchJobBS.findBatchJobByJobId(Integer.valueOf(jobId));
			if(batchJob != null){
				JobUtil.startJob(batchJob);
				RequestContext.getCurrentInstance().execute("alert('"+batchJob.getJobAliasName()+"启动成功"+"')");
			}
			
		}
		
	}
	public TreeNode getJobRoot() {
		return jobRoot;
	}

	public void setJobRoot(TreeNode jobRoot) {
		this.jobRoot = jobRoot;
	}
	public IBatchJobBS getBatchJobBS() {
		return batchJobBS;
	}
	public void setBatchJobBS(IBatchJobBS batchJobBS) {
		this.batchJobBS = batchJobBS;
	}

	
}
