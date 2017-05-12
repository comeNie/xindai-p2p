package com.qfw.batch.model.bo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * BatchJob entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BATCH_JOB")
public class BatchJob implements java.io.Serializable {

	// Fields

	private Integer jobId;
	private String jobName;
	private String jobAliasName;
	private String description;
	private String javaclass;
	private Integer jobOrder;
	private String sedulerdate;
	private Integer parentJobId;
	private String enable;
	private List<BatchJob> jobChildren;

	// Constructors

	/** default constructor */
	public BatchJob() {
	}

	/** minimal constructor */
	public BatchJob(Integer jobId) {
		this.jobId = jobId;
	}

	/** full constructor */
	public BatchJob(Integer jobId, String jobName, String jobAliasName,
			String description, String javaclass, Integer jobOrder,
			String sedulerdate, Integer parentJobId, String enable) {
		this.jobId = jobId;
		this.jobName = jobName;
		this.jobAliasName = jobAliasName;
		this.description = description;
		this.javaclass = javaclass;
		this.jobOrder = jobOrder;
		this.sedulerdate = sedulerdate;
		this.parentJobId = parentJobId;
		this.enable = enable;
	}

	// Property accessors
	@Id
	@Column(name = "JOB_ID")
	@GeneratedValue(generator="BATCH_JOB_SEQ")  
	//@SequenceGenerator(name="BATCH_JOB_SEQ", sequenceName="SEQ_BATCH_JOB",allocationSize = 1)
	@GenericGenerator(name = "BATCH_JOB_SEQ", strategy = "identity")
	public Integer getJobId() {
		return this.jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	@Column(name = "JOB_NAME")
	public String getJobName() {
		return this.jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	@Column(name = "JOB_ALIAS_NAME")
	public String getJobAliasName() {
		return this.jobAliasName;
	}

	public void setJobAliasName(String jobAliasName) {
		this.jobAliasName = jobAliasName;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "JAVACLASS")
	public String getJavaclass() {
		return this.javaclass;
	}

	public void setJavaclass(String javaclass) {
		this.javaclass = javaclass;
	}

	@Column(name = "JOB_ORDER", precision = 10, scale = 0)
	public Integer getJobOrder() {
		return this.jobOrder;
	}

	public void setJobOrder(Integer jobOrder) {
		this.jobOrder = jobOrder;
	}

	@Column(name = "SEDULERDATE")
	public String getSedulerdate() {
		return this.sedulerdate;
	}

	public void setSedulerdate(String sedulerdate) {
		this.sedulerdate = sedulerdate;
	}

	@Column(name = "PARENT_JOB_ID")
	public Integer getParentJobId() {
		return this.parentJobId;
	}

	public void setParentJobId(Integer parentJobId) {
		this.parentJobId = parentJobId;
	}

	@Column(name = "ENABLE", length = 1)
	public String getEnable() {
		return this.enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	@Transient
	public List<BatchJob> getJobChildren() {
		return jobChildren;
	}

	public void setJobChildren(List<BatchJob> jobChildren) {
		this.jobChildren = jobChildren;
	}
	

}