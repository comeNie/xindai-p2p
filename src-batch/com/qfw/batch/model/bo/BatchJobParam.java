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
 * BatchJobParam entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BATCH_JOB_PARAM")
public class BatchJobParam implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer jobId;
	private String paramKey;
	private String paramValue;
	private String paramType;
	private String memo;

	// Constructors

	/** default constructor */
	public BatchJobParam() {
	}

	/** minimal constructor */
	public BatchJobParam(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public BatchJobParam(Integer id, Integer jobId, String paramKey,
			String paramValue, String paramType, String memo) {
		this.id = id;
		this.jobId = jobId;
		this.paramKey = paramKey;
		this.paramValue = paramValue;
		this.paramType = paramType;
		this.memo = memo;
	}

	// Property accessors
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator="BATCH_JOB_PARAM_SEQ")
	//@SequenceGenerator(name="BATCH_JOB_PARAM_SEQ", sequenceName="SEQ_BATCH_JOB_PARAM",allocationSize = 1)
	@GenericGenerator(name = "BATCH_JOB_PARAM_SEQ", strategy = "identity")
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

	@Column(name = "PARAM_KEY")
	public String getParamKey() {
		return this.paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	@Column(name = "PARAM_VALUE")
	public String getParamValue() {
		return this.paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	@Column(name = "PARAM_TYPE")
	public String getParamType() {
		return this.paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	@Column(name = "MEMO")
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}