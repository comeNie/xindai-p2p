package com.qfw.common.model;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * BizPmDetailBO entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="biz_pm_detail")
public class BizPmDetailBO  implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
     private Integer custId;
     private String userCode;
     private Date txDate;
     private String pmTypeCd;
     private BigDecimal pmAmt;
     private Integer sysCreateUser;
     private Date sysCreateTime;
     private Integer sysUpdateUser;
     private Date sysUpdateTime;


    // Constructors

    /** default constructor */
    public BizPmDetailBO() {
    }

	/** minimal constructor */
    public BizPmDetailBO(Integer custId, String userCode, Date txDate, String pmTypeCd, BigDecimal pmAmt) {
        this.custId = custId;
        this.userCode = userCode;
        this.txDate = txDate;
        this.pmTypeCd = pmTypeCd;
        this.pmAmt = pmAmt;
    }
    
    /** full constructor */
    public BizPmDetailBO(Integer custId, String userCode, Date txDate, String pmTypeCd, BigDecimal pmAmt, Integer sysCreateUser, Date sysCreateTime, Integer sysUpdateUser, Date sysUpdateTime) {
        this.custId = custId;
        this.userCode = userCode;
        this.txDate = txDate;
        this.pmTypeCd = pmTypeCd;
        this.pmAmt = pmAmt;
        this.sysCreateUser = sysCreateUser;
        this.sysCreateTime = sysCreateTime;
        this.sysUpdateUser = sysUpdateUser;
        this.sysUpdateTime = sysUpdateTime;
    }

   
    // Property accessors
    @Id @GeneratedValue
    
    @Column(name="ID", unique=true, nullable=false)

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    @Column(name="CUST_ID", nullable=false)

    public Integer getCustId() {
        return this.custId;
    }
    
    public void setCustId(Integer custId) {
        this.custId = custId;
    }
    
    @Column(name="USER_CODE", nullable=false, length=50)

    public String getUserCode() {
        return this.userCode;
    }
    
    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
    
    @Column(name="TX_DATE", nullable=false, length=19)

    public Date getTxDate() {
        return this.txDate;
    }
    
    public void setTxDate(Date txDate) {
        this.txDate = txDate;
    }
    
    @Column(name="PM_TYPE_CD", nullable=false, length=2)

    public String getPmTypeCd() {
        return this.pmTypeCd;
    }
    
    public void setPmTypeCd(String pmTypeCd) {
        this.pmTypeCd = pmTypeCd;
    }
    
    @Column(name="PM_AMT", nullable=false, precision=16)

    public BigDecimal getPmAmt() {
        return this.pmAmt;
    }
    
    public void setPmAmt(BigDecimal pmAmt) {
        this.pmAmt = pmAmt;
    }
    
    @Column(name="SYS_CREATE_USER")

    public Integer getSysCreateUser() {
        return this.sysCreateUser;
    }
    
    public void setSysCreateUser(Integer sysCreateUser) {
        this.sysCreateUser = sysCreateUser;
    }
    
    @Column(name="SYS_CREATE_TIME", length=19)

    public Date getSysCreateTime() {
        return this.sysCreateTime;
    }
    
    public void setSysCreateTime(Date date) {
        this.sysCreateTime = date;
    }
    
    @Column(name="SYS_UPDATE_USER")

    public Integer getSysUpdateUser() {
        return this.sysUpdateUser;
    }
    
    public void setSysUpdateUser(Integer sysUpdateUser) {
        this.sysUpdateUser = sysUpdateUser;
    }
    
    @Column(name="SYS_UPDATE_TIME", length=19)

    public Date getSysUpdateTime() {
        return this.sysUpdateTime;
    }
    
    public void setSysUpdateTime(Date sysUpdateTime) {
        this.sysUpdateTime = sysUpdateTime;
    }
   








}