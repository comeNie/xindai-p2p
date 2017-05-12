package com.qfw.common.model.permission;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import com.qfw.common.model.BaseBO;

/**
 * SysFunction entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYS_FUNCTION")
public class SysFunction extends BaseBO implements java.io.Serializable {

	// Fields

	private Integer funId;
	private Integer parentFunId;
	private String funCode;
	private String isLast;
	private String funName;
	private String funPath;
	private String funType;
	private String funLevel;
	private String funStatus;
	private String funIcon;
	private Integer sort;
	private Date sysCreateTime = new Date();
	private Date sysUpdateTime;
	private String sysUpdateUser;
	private String remark;
	//private Set<SysRole> sysRoles = new HashSet<SysRole>(0);

	// Constructors

	/** default constructor */
	public SysFunction() {
	}

	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY,generator="function_seq")  
	//@SequenceGenerator(name="function_seq", sequenceName="seq_sys_function",allocationSize = 1,initialValue = 10000)
	@GenericGenerator(name = "function_seq", strategy = "identity")   
	@Column(name = "FUN_ID")
	public Integer getFunId() {
		return this.funId;
	}

	public void setFunId(Integer funId) {
		this.funId = funId;
	}

	@Column(name = "PARENT_FUN_ID")
	public Integer getParentFunId() {
		return this.parentFunId;
	}

	public void setParentFunId(Integer parentFunId) {
		this.parentFunId = parentFunId;
	}

	@Column(name = "FUN_CODE")
	public String getFunCode() {
		return funCode;
	}


	public void setFunCode(String funCode) {
		this.funCode = funCode;
	}


	@Column(name = "IS_LAST")
	public String getIsLast() {
		return this.isLast;
	}

	public void setIsLast(String isLast) {
		this.isLast = isLast;
	}

	@Column(name = "FUN_NAME", length = 32)
	public String getFunName() {
		return this.funName;
	}

	public void setFunName(String funName) {
		this.funName = funName;
	}

	@Column(name = "FUN_PATH", length = 500)
	public String getFunPath() {
		return this.funPath;
	}

	public void setFunPath(String funPath) {
		this.funPath = funPath;
	}

	@Column(name = "FUN_TYPE", length = 2)
	public String getFunType() {
		return this.funType;
	}

	public void setFunType(String funType) {
		this.funType = funType;
	}

	@Column(name = "FUN_LEVEL")
	public String getFunLevel() {
		return funLevel;
	}

	public void setFunLevel(String funLevel) {
		this.funLevel = funLevel;
	}

	@Column(name = "FUN_STATUS", length = 1)
	public String getFunStatus() {
		return this.funStatus;
	}

	public void setFunStatus(String funStatus) {
		this.funStatus = funStatus;
	}
	
	@Column(name = "FUN_ICON")
	public String getFunIcon() {
		return funIcon;
	}

	public void setFunIcon(String funIcon) {
		this.funIcon = funIcon;
	}

	/*@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysFunctions")
	public Set<SysRole> getSysRoles() {
		return this.sysRoles;
	}

	public void setSysRoles(Set<SysRole> sysRoles) {
		this.sysRoles = sysRoles;
	}*/
	
	@Column(name = "SORT")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Column(name="SYS_CREATE_TIME", nullable=false, updatable=false)   
    public Date getSysCreateTime() {
		return sysCreateTime;
	}

	public void setSysCreateTime(Date sysCreateTime) {
		this.sysCreateTime = sysCreateTime;
	}

    @Column(name="SYS_UPDATE_TIME")
    @Version
	public Date getSysUpdateTime() {
		return sysUpdateTime;
	}
	public void setSysUpdateTime(Date sysUpdateTime) {
		this.sysUpdateTime = sysUpdateTime;
	}
	
	@Column(name="SYS_UPDATE_USER")   
	public String getSysUpdateUser() {
		return sysUpdateUser;
	}
	public void setSysUpdateUser(String sysUpdateUser) {
		this.sysUpdateUser = sysUpdateUser;
	}
	
	@Column(name="REMARK") 
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Override
	@Transient
	public String getRowKey() {
		return String.valueOf(this.getFunId());
	}
	

}