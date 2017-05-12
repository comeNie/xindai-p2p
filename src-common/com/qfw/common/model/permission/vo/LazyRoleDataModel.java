package com.qfw.common.model.permission.vo;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.qfw.common.bizservice.permission.IRoleBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.permission.SysRole;

@SuppressWarnings("serial")
public class LazyRoleDataModel extends LazyDataModel<SysRole>{

	private SysRoleVO sysRoleVO;	
	private IRoleBS roleBS;
	private List<SysRole> roles;
	private Logger log = LogFactory.getInstance().getPlatformLogger();
	
	public LazyRoleDataModel(SysRoleVO sysRoleVO, IRoleBS roleBS) {
		this.sysRoleVO = sysRoleVO;
		this.roleBS = roleBS;
	}
	
	@Override
	public List<SysRole> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		try {
			roles = roleBS.findRolesPagesByVO(sysRoleVO, first, pageSize);
			setRowCount(roleBS.findRolesCountByVO(sysRoleVO));
		} catch (BizException e) {
			log.error("获取角色翻页信息异常：", e);
		}
		return roles;
	}
	
	@Override
	public SysRole getRowData(String rowKey) {
		for(SysRole role : roles){
			if(String.valueOf(role.getRoleId()).equals(rowKey))
				return role;
		}
		SysRole sysRole = new SysRole();
		try {
			sysRole = roleBS.findRoleById(Integer.valueOf(rowKey));
		} catch (BizException e) {
			log.error("通过ID获取角色异常：", e);
		}
		return sysRole;
	}
	
	@Override
	public Object getRowKey(SysRole role) {
		return role.getRoleId();
	}

	public SysRoleVO getSysRoleVO() {
		return sysRoleVO;
	}

	public void setSysRoleVO(SysRoleVO sysRoleVO) {
		this.sysRoleVO = sysRoleVO;
	}

	public IRoleBS getRoleBS() {
		return roleBS;
	}

	public void setRoleBS(IRoleBS roleBS) {
		this.roleBS = roleBS;
	}

	public List<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}
	
}
