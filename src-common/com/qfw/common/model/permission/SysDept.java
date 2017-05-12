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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import com.qfw.common.model.BaseBO;

/**
 * SysDept entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYS_DEPT")
public class SysDept extends BaseBO implements java.io.Serializable {

	// Fields

	private Integer deptId;
	private String deptCode;
	private String deptName;
	private Integer parentDeptId;
	private String status;
	private Integer levelNum;
	private Integer orderNum;
	private Date sysCreateTime = new Date();
	private Date sysUpdateTime;
	private String sysUpdateUser;
	private String remark;
	//private Set<SysRole> sysRoles = new HashSet<SysRole>(0);
	//private Set<SysUser> sysUsers = new HashSet<SysUser>(0);

	// Constructors

	/** default constructor */
	public SysDept() {
	}

	// Property accessors	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY,generator="dept_seq")  
	//@SequenceGenerator(name="dept_seq", sequenceName="seq_sys_dept",allocationSize = 1,initialValue = 10000)  
	@GenericGenerator(name = "dept_seq", strategy = "identity")
	@Column(name = "DEPT_ID",nullable=false)
	public Integer getDeptId() {
		return this.deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	@Column(name = "DEPT_CODE", length = 32)
	public String getDeptCode() {
		return this.deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name = "DEPT_NAME", length = 32)
	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "PARENT_DEPT_ID", length = 32)
	public Integer getParentDeptId() {
		return this.parentDeptId;
	}

	public void setParentDeptId(Integer parentDeptId) {
		this.parentDeptId = parentDeptId;
	}

	@Column(name = "STATUS", length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "LEVEL_NUM")
	public Integer getLevelNum() {
		return levelNum;
	}

	public void setLevelNum(Integer levelNum) {
		this.levelNum = levelNum;
	}

	@Column(name = "ORDER_NUM")
	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
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

	/*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysDept")
	public Set<SysRole> getSysRoles() {
		return this.sysRoles;
	}

	public void setSysRoles(Set<SysRole> sysRoles) {
		this.sysRoles = sysRoles;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysDepts")
	public Set<SysUser> getSysUsers() {
		return this.sysUsers;
	}

	public void setSysUsers(Set<SysUser> sysUsers) {
		this.sysUsers = sysUsers;
	}*/

	@Override
	@Transient
	public String getRowKey() {
		return String.valueOf(deptId);
	}

}