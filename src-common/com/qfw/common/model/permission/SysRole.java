package com.qfw.common.model.permission;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import com.qfw.common.model.BaseBO;

/**
 * SysRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYS_ROLE")
public class SysRole extends BaseBO implements java.io.Serializable {

	// Fields

	private Integer roleId;
	private String roleCode;
	private String roleName;
	private Date sysCreateTime ;
	private Date sysUpdateTime;
	private String sysUpdateUser;
	private String remark;
	private Integer deptId;
	private String roleDesc;
	//private Set<SysUser> sysUsers = new HashSet<SysUser>(0);
	//private Set<SysFunction> sysFunctions = new HashSet<SysFunction>(0);

	// Constructors

	/** default constructor */
	public SysRole() {
	}

	/** full constructor */
	/*public SysRole(SysDept sysDept, String roleCode, String roleName,
			Set<SysUser> sysUsers, Set<SysFunction> sysFunctions) {
		this.sysDept = sysDept;
		this.roleCode = roleCode;
		this.roleName = roleName;
		this.sysUsers = sysUsers;
		this.sysFunctions = sysFunctions;
	}*/

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "role_seq")
	//@SequenceGenerator(name = "role_seq", sequenceName = "seq_sys_role",allocationSize = 1,initialValue = 10000)
	@GenericGenerator(name = "role_seq", strategy = "identity")  
	@Column(name = "ROLE_ID")
	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	/*@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DEPT_ID")
	public SysDept getSysDept() {
		return this.sysDept;
	}

	public void setSysDept(SysDept sysDept) {
		this.sysDept = sysDept;
	}*/

	@Column(name = "ROLE_CODE", length = 32)
	public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	@Column(name = "ROLE_NAME", length = 32)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "SYS_CREATE_TIME", nullable = false, updatable = false)
	public Date getSysCreateTime() {
		return sysCreateTime;
	}

	public void setSysCreateTime(Date sysCreateTime) {
		this.sysCreateTime = sysCreateTime;
	}

	@Column(name = "SYS_UPDATE_TIME")
//	@Version
	public Date getSysUpdateTime() {
		return sysUpdateTime;
	}

	public void setSysUpdateTime(Date sysUpdateTime) {
		this.sysUpdateTime = sysUpdateTime;
	}

	@Column(name = "SYS_UPDATE_USER")
	public String getSysUpdateUser() {
		return sysUpdateUser;
	}

	public void setSysUpdateUser(String sysUpdateUser) {
		this.sysUpdateUser = sysUpdateUser;
	}

	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "DEPT_ID")
	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	@Override
	@Transient
	public String getRowKey() {
		return String.valueOf(roleId);
	}
	
	@Column(name = "ROLE_DESC", length = 100)
	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
}