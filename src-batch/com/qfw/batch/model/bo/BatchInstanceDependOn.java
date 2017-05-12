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
 * BatchInstanceDependOn entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BATCH_INSTANCE_DEPEND_ON")
public class BatchInstanceDependOn implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer jobInstanceId;
	private Integer dependJobInstanceId;

	// Constructors

	/** default constructor */
	public BatchInstanceDependOn() {
	}

	/** minimal constructor */
	public BatchInstanceDependOn(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public BatchInstanceDependOn(Integer id, Integer jobInstanceId,
			Integer dependJobInstanceId) {
		this.id = id;
		this.jobInstanceId = jobInstanceId;
		this.dependJobInstanceId = dependJobInstanceId;
	}

	// Property accessors
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator="BATCH_INSTANCE_DEPEND_ON_SEQ")  
	//@SequenceGenerator(name="BATCH_INSTANCE_DEPEND_ON_SEQ", sequenceName="SEQ_BATCH_INSTANCE_DEPEND_ON",allocationSize = 1)
	@GenericGenerator(name = "BATCH_INSTANCE_DEPEND_ON_SEQ", strategy = "identity")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "JOB_INSTANCE_ID")
	public Integer getJobInstanceId() {
		return this.jobInstanceId;
	}

	public void setJobInstanceId(Integer jobInstanceId) {
		this.jobInstanceId = jobInstanceId;
	}

	@Column(name = "DEPEND_JOB_INSTANCE_ID")
	public Integer getDependJobInstanceId() {
		return this.dependJobInstanceId;
	}

	public void setDependJobInstanceId(Integer dependJobInstanceId) {
		this.dependJobInstanceId = dependJobInstanceId;
	}

}