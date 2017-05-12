package com.qfw.batch.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;
import org.primefaces.model.TreeNode;

import com.qfw.batch.bizservice.IBatchJobBS;
import com.qfw.batch.bizservice.util.JobUtil;
import com.qfw.batch.bizservice.util.QuartzUtil;
import com.qfw.batch.model.bo.BatchJobInstance;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;

@ViewScoped
@ManagedBean(name = "batchInstanceManageBean")
public class BatchInstanceManageBean extends BaseBackingBean {
	private TreeNode jobInstanceRoot = null;
	@ManagedProperty(value = "#{batchJobBS}")
	private IBatchJobBS batchJobBS;
	private String batchNo = QuartzUtil.getBatchno();
	private BatchJobInstance selectJobInstance;
	@PostConstruct
    public void init(){
		if(jobInstanceRoot == null){
			jobInstanceRoot = batchJobBS.getInstanceTree(Integer.valueOf(batchNo));
		}
	}
	
	public void search(){
		try{
			jobInstanceRoot = batchJobBS.getInstanceTree(Integer.valueOf(batchNo));
		}catch(Exception e){
			this.alert("请输入YYYYMMDD格式的日期作为批次号！");
		}
	}
	
	public void refresh(){
		try{
			TreeNode newJobInstanceRoot = batchJobBS.getInstanceTree(Integer.valueOf(batchNo));
			if(this.jobInstanceRoot.isExpanded()){
				newJobInstanceRoot.setExpanded(true);
				List<TreeNode> oldInstTree = this.jobInstanceRoot.getChildren();
				List<TreeNode> newInstTree = newJobInstanceRoot.getChildren();
				for (TreeNode oldTreeNode : oldInstTree) {
					if(oldTreeNode.isExpanded()){
						for (TreeNode newTreeNode : newInstTree) {
							if(((BatchJobInstance)oldTreeNode.getData()).getJobInstanceId().equals(((BatchJobInstance)newTreeNode.getData()).getJobInstanceId())){
								newTreeNode.setExpanded(true);
								break;
							}
						}
					}
					
				}
			}
			this.jobInstanceRoot = newJobInstanceRoot;
		}catch(Exception e){
			this.alert("请输入YYYYMMDD格式的日期作为批次号！");
		}
	}
	/**
	 * 重新启动批任务
	 */
	public void reStart(){
		String jobInstanceId = ViewOper.getParameter("jobInstanceId");
		if(jobInstanceId!=null){
			selectJobInstance = batchJobBS.findBatchJobInstance(Integer.valueOf(jobInstanceId));
			if(selectJobInstance != null){
				JobUtil.reStartJob(selectJobInstance);
				RequestContext.getCurrentInstance().execute("alert('"+selectJobInstance.getAliasName()+"重启成功"+"')");
			}
		}else{
			RequestContext.getCurrentInstance().execute("alert('重启失败')");
		}
		
	}
	/**
	 * 停止批任务
	 */
	public void stop(){
		String jobInstanceId = ViewOper.getParameter("jobInstanceId");
		if(jobInstanceId!=null){
			selectJobInstance = batchJobBS.findBatchJobInstance(Integer.valueOf(jobInstanceId));
			if(selectJobInstance != null){
				JobUtil.stopJob(selectJobInstance);
				RequestContext.getCurrentInstance().execute("alert('"+selectJobInstance.getAliasName()+"停止成功"+"')");
			}
		}else{
			RequestContext.getCurrentInstance().execute("alert('停止失败')");
		}
		
	}
	public IBatchJobBS getBatchJobBS() {
		return batchJobBS;
	}
	public void setBatchJobBS(IBatchJobBS batchJobBS) {
		this.batchJobBS = batchJobBS;
	}

	public TreeNode getJobInstanceRoot() {
		return jobInstanceRoot;
	}

	public void setJobInstanceRoot(TreeNode jobInstanceRoot) {
		this.jobInstanceRoot = jobInstanceRoot;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public BatchJobInstance getSelectJobInstance() {
		return selectJobInstance;
	}

	public void setSelectJobInstance(BatchJobInstance selectJobInstance) {
		this.selectJobInstance = selectJobInstance;
	}

	
}
