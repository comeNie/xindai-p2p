package com.qfw.jbpm.handler;

import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;

import com.qfw.common.bizservice.permission.IRoleBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.permission.SysRole;
import com.qfw.common.util.ApplicationContextUtil;

public class RoleAssignmentHandler implements AssignmentHandler {

	String roleCode;
	Integer deptId;//所属机构
	@Override
	public void assign(Assignable assignable, OpenExecution arg1) throws Exception {
		//Object obj = ApplicationContextUtil.getBean(beanId);
		IRoleBS roleBS = (IRoleBS) ApplicationContextUtil.getBean("roleBS");
		SysRole role = roleBS.findSysRole(deptId, roleCode);
		if(role == null){
			throw new BizException("获取用户岗位为空");
		}
		assignable.addCandidateGroup(String.valueOf(role.getRoleId()));

	}

}
