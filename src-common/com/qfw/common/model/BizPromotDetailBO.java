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
 * BizPromotDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="biz_promot_detail")

public class BizPromotDetailBO  implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
     private Integer custId;
     private String userCode;
     private Integer byCustId;
     private String byUserCode;
     private String byUserName;
     private String promotTypeCd;
     private Date txDate;
     private BigDecimal promotAmt;
     private Integer sysCreateUser;
     private Date sysCreateTime;
     private Integer sysUpdateUser;
     private Date sysUpdateTime;
     private String txRemark;
     

    // Constructors

    /** default constructor */
    public BizPromotDetailBO() {
    }

	/** minimal constructor */
    public BizPromotDetailBO(Integer custId, String userCode, Integer byCustId, String byUserCode, String byUserName, String promotTypeCd, Date txDate, BigDecimal promotAmt) {
        this.custId = custId;
        this.userCode = userCode;
        this.byCustId = byCustId;
        this.byUserCode = byUserCode;
        this.byUserName = byUserName;
        this.promotTypeCd = promotTypeCd;
        this.txDate = txDate;
        this.promotAmt = promotAmt;
    }
    
    /** full constructor */
    public BizPromotDetailBO(Integer custId, String userCode, Integer byCustId, String byUserCode, String byUserName, String promotTypeCd, Date txDate, BigDecimal promotAmt, Integer sysCreateUser, Timestamp sysCreateTime, Integer sysUpdateUser, Timestamp sysUpdateTime) {
        this.custId = custId;
        this.userCode = userCode;
        this.byCustId = byCustId;
        this.byUserCode = byUserCode;
        this.byUserName = byUserName;
        this.promotTypeCd = promotTypeCd;
        this.txDate = txDate;
        this.promotAmt = promotAmt;
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
    
    @Column(name="BY_CUST_ID", nullable=false)

    public Integer getByCustId() {
        return this.byCustId;
    }
    
    public void setByCustId(Integer byCustId) {
        this.byCustId = byCustId;
    }
    
    @Column(name="BY_USER_CODE", nullable=false, length=50)

    public String getByUserCode() {
        return this.byUserCode;
    }
    
    public void setByUserCode(String byUserCode) {
        this.byUserCode = byUserCode;
    }
    
    @Column(name="BY_USER_NAME", nullable=false, length=250)

    public String getByUserName() {
        return this.byUserName;
    }
    
    public void setByUserName(String byUserName) {
        this.byUserName = byUserName;
    }
    
    @Column(name="PROMOT_TYPE_CD", nullable=false, length=2)

    public String getPromotTypeCd() {
        return this.promotTypeCd;
    }
    
    public void setPromotTypeCd(String promotTypeCd) {
        this.promotTypeCd = promotTypeCd;
    }
    
    @Column(name="TX_DATE", nullable=false, length=19)

    public Date getTxDate() {
        return this.txDate;
    }
    
    public void setTxDate(Date txDate) {
        this.txDate = txDate;
    }
    
    @Column(name="PROMOT_AMT", nullable=false, precision=16)

    public BigDecimal getPromotAmt() {
        return this.promotAmt;
    }
    
    public void setPromotAmt(BigDecimal promotAmt) {
        this.promotAmt = promotAmt;
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
    
    public void setSysCreateTime(Date sysCreateTime) {
        this.sysCreateTime = sysCreateTime;
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
    
    @Column(name="TX_REMARK", length=250)
	public String getTxRemark() {
		return txRemark;
	}

	public void setTxRemark(String txRemark) {
		this.txRemark = txRemark;
	}
}