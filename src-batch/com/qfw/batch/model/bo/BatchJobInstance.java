package com.qfw.batch.model.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * BatchJobInstance entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BATCH_JOB_INSTANCE")
public class BatchJobInstance implements java.io.Serializable {

	// Fields

	private Integer jobInstanceId;
	private Integer batchNo;
	private Integer batchSeqNo;
	private Integer jobId;
	private Integer parentJobId;
	private String name;
	private String aliasName;
	private String jobGroup;
	private String startTime;
	private String endTime;
	private String status;
	private String progress;
	private String totalprogress;
	private String comments;
	private String isSubJob;
	private String param;
	private String memo;

	// Constructors

	/** default constructor */
	public BatchJobInstance() {
	}

	/** minimal constructor */
	public BatchJobInstance(Integer jobInstanceId) {
		this.jobInstanceId = jobInstanceId;
	}

	/** full constructor */
	public BatchJobInstance(Integer jobInstanceId, Integer batchNo,
			Integer batchSeqNo, Integer jobId, Integer parentJobId, String name,
			String aliasName, String jobGroup, String startTime,
			String endTime, String status, String progress,
			String totalprogress, String comments, String isSubJob,
			String param, String memo) {
		this.jobInstanceId = jobInstanceId;
		this.batchNo = batchNo;
		this.batchSeqNo = batchSeqNo;
		this.jobId = jobId;
		this.parentJobId = parentJobId;
		this.name = name;
		this.aliasName = aliasName;
		this.jobGroup = jobGroup;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
		this.progress = progress;
		this.totalprogress = totalprogress;
		this.comments = comments;
		this.isSubJob = isSubJob;
		this.param = param;
		this.memo = memo;
	}

	// Property accessors
	@Id
	@Column(name = "JOB_INSTANCE_ID")
	@GeneratedValue(generator="BATCH_JOB_INSTANCE_SEQ")
	//@SequenceGenerator(name="BATCH_JOB_INSTANCE_SEQ", sequenceName="SEQ_BATCH_JOB_INSTANCE",allocationSize = 1)
	@GenericGenerator(name = "BATCH_JOB_INSTANCE_SEQ", strategy = "identity")
	public Integer getJobInstanceId() {
		return this.jobInstanceId;
	}

	public void setJobInstanceId(Integer jobInstanceId) {
		this.jobInstanceId = jobInstanceId;
	}

	@Column(name = "BATCH_NO")
	public Integer getBatchNo() {
		return this.batchNo;
	}

	public void setBatchNo(Integer batchNo) {
		this.batchNo = batchNo;
	}

	@Column(name = "BATCH_SEQ_NO")
	public Integer getBatchSeqNo() {
		return this.batchSeqNo;
	}

	public void setBatchSeqNo(Integer batchSeqNo) {
		this.batchSeqNo = batchSeqNo;
	}

	@Column(name = "JOB_ID")
	public Integer getJobId() {
		return this.jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	@Column(name = "PARENT_JOB_ID")
	public Integer getParentJobId() {
		return this.parentJobId;
	}

	public void setParentJobId(Integer parentJobId) {
		this.parentJobId = parentJobId;
	}

	@Column(name = "NAME")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "ALIAS_NAME")
	public String getAliasName() {
		return this.aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	@Column(name = "JOB_GROUP")
	public String getJobGroup() {
		return this.jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	@Column(name = "START_TIME")
	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	@Column(name = "END_TIME")
	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Column(name = "STATUS")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "PROGRESS")
	public String getProgress() {
		return this.progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	@Column(name = "TOTALPROGRESS")
	public String getTotalprogress() {
		return this.totalprogress;
	}

	public void setTotalprogress(String totalprogress) {
		this.totalprogress = totalprogress;
	}

	@Column(name = "COMMENTS")
	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Column(name = "IS_SUB_JOB")
	public String getIsSubJob() {
		return this.isSubJob;
	}

	public void setIsSubJob(String isSubJob) {
		this.isSubJob = isSubJob;
	}

	@Column(name = "PARAM")
	public String getParam() {
		return this.param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	@Column(name = "MEMO")
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}