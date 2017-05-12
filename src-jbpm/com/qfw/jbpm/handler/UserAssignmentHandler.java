package com.qfw.jbpm.handler;

import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;

import com.qfw.common.bizservice.permission.IRoleBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.permission.SysRole;
import com.qfw.common.util.ApplicationContextUtil;
import com.qfw.common.util.StringUtils;
import com.qfw.jbpm.model.JbpmConst;

public class UserAssignmentHandler implements AssignmentHandler {

	String var;
	@Override
	public void assign(Assignable assignable, OpenExecution arg1) throws Exception {
		String varValue = String.valueOf(arg1.getVariable(var));
		if(StringUtils.isEmpty(varValue)){
			varValue = String.valueOf(arg1.getVariable(JbpmConst.NEXT_AUDIT_USER));
			arg1.setVariable(var, varValue);
		}
		assignable.setAssignee(varValue);
	}

}
