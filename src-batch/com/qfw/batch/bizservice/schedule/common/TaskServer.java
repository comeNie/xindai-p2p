package com.qfw.batch.bizservice.schedule.common;


public interface TaskServer extends com.sun.jmx.snmp.tasks.TaskServer {
	
	/**
	 * 获取活动线程数
	 * @return
	 */
	public int getAliveCount();
}
