package com.qfw.batch.model.bo;
// default package

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * BatchErrorDetailBO entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="batch_error_detail")
public class BatchErrorDetailBO  implements java.io.Serializable {


    // Fields    

     private Long id;
     private Date batchDate;
     private String errJobName;
     private String errDesc;
     private String exception;


    // Constructors

    /** default constructor */
    public BatchErrorDetailBO() {
    }

    
    /** full constructor */
    public BatchErrorDetailBO(Date batchDate, String errJobName, String errDesc, String exception) {
        this.batchDate = batchDate;
        this.errJobName = errJobName;
        this.errDesc = errDesc;
        this.exception = exception;
    }

   
    // Property accessors
    @Id @GeneratedValue
    
    @Column(name="ID", unique=true, nullable=false)

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="BATCH_DATE", length=10)

    public Date getBatchDate() {
        return this.batchDate;
    }
    
    public void setBatchDate(Date batchDate) {
        this.batchDate = batchDate;
    }
    
    @Column(name="ERR_JOB_NAME", length=100)

    public String getErrJobName() {
        return this.errJobName;
    }
    
    public void setErrJobName(String errJobName) {
        this.errJobName = errJobName;
    }
    
    @Column(name="ERR_DESC", length=500)

    public String getErrDesc() {
        return this.errDesc;
    }
    
    public void setErrDesc(String errDesc) {
        this.errDesc = errDesc;
    }
    
    @Column(name="EXCEPTION", length=500)

    public String getException() {
        return this.exception;
    }
    
    public void setException(String exception) {
        this.exception = exception;
    }
   








}