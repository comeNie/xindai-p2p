package com.qfw.batch.bizservice.schedule.common;

import com.sun.jmx.snmp.tasks.Task;

public interface IBatchJob extends Task {

	/**
	 * 设置任务执行id
	 */
	public void setJobInstanceId(Integer id);
	
}
