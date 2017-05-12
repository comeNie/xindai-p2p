package com.qfw.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * SysParameter entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYS_REQ")
public class SysSeq implements java.io.Serializable {

	// Fields
	
	private Integer id;
	private String seqName;
	private Integer seqValue;
	private String prefix;				//前缀
	private String postfix;				//后缀
	private Integer size;				//总长度
	
	// Constructors
	
	/** default constructor */
	public SysSeq() {
		
	}
	
	/** minimal constructor */
	public SysSeq(String seqName, Integer seqValue) {
		this.seqName = seqName;
		this.seqValue = seqValue;
	}
	
	// Property accessors
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY,generator="sysseq_seq")  
	//@SequenceGenerator(name="sysseq_seq", sequenceName="seq_sys_seq",allocationSize = 1,initialValue = 10000)
	@GenericGenerator(name = "sysseq_seq", strategy = "identity")
	@Column(name = "ID")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "SEQ_NAME", length = 50)
	public String getSeqName() {
		return seqName;
	}

	public void setSeqName(String seqName) {
		this.seqName = seqName;
	}

	@Column(name = "SEQ_VALUE")
	public Integer getSeqValue() {
		return seqValue;
	}

	public void setSeqValue(Integer seqValue) {
		this.seqValue = seqValue;
	}

	@Column(name = "PREFIX", length = 5)
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Column(name = "POSTFIX", length = 5)
	public String getPostfix() {
		return postfix;
	}

	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}

	@Column(name = "SIZE")
	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}
	
}
