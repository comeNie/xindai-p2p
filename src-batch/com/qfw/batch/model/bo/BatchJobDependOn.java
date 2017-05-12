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
 * BatchJobDependOn entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BATCH_JOB_DEPEND_ON")
public class BatchJobDependOn implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer jobId;
	private Integer dependJobId;

	// Constructors

	/** default constructor */
	public BatchJobDependOn() {
	}

	/** minimal constructor */
	public BatchJobDependOn(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public BatchJobDependOn(Integer id, Integer jobId, Integer dependJobId) {
		this.id = id;
		this.jobId = jobId;
		this.dependJobId = dependJobId;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@GeneratedValue(generator="BATCH_JOB_DEPEND_ON_SEQ")
	//@SequenceGenerator(name="BATCH_JOB_DEPEND_ON_SEQ", sequenceName="SEQ_BATCH_JOB_DEPEND_ON",allocationSize = 1)
	@GenericGenerator(name = "BATCH_JOB_DEPEND_ON_SEQ", strategy = "identity")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "JOB_ID")
	public Integer getJobId() {
		return this.jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	@Column(name = "DEPEND_JOB_ID")
	public Integer getDependJobId() {
		return this.dependJobId;
	}

	public void setDependJobId(Integer dependJobId) {
		this.dependJobId = dependJobId;
	}

}