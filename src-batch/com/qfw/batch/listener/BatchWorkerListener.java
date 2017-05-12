package com.qfw.batch.listener;

import org.apache.log4j.Logger;

import com.qfw.batch.bizservice.schedule.BatchServiceImpl;

/**
 * batch监听器
 * @author Jie
 *
 */
public class BatchWorkerListener extends Thread {
	private Logger log = Logger.getLogger(BatchWorkerListener.class);
	public void run() {
		while (true) {
			synchronized (BatchServiceImpl.taskList) {
				while (BatchServiceImpl.taskList.isEmpty()) {
					try {
						BatchServiceImpl.taskList.wait();
					} catch (Exception e) {
						log.error(e);
					}
				}
			}

			if (BatchServiceImpl.taskList.size() == 0) {
				continue;
			}

			String command = (String) BatchServiceImpl.taskList.remove(0);

			try {
				if (log.isDebugEnabled()){
					log.debug(" BatchWorkerListener.workMapping() ----> command =  "+ command);
				}
				workMapping(command);
			} catch (Exception e) {
				log.error(e);
			}

			try {
				Thread.sleep(100);
			} catch (Exception ex) {
				log.error(ex);
			}
		}
	}

	private void workMapping(String command) throws Exception {
		BatchServiceImpl bs = new BatchServiceImpl();
		if (command.startsWith("start ")) {
			String[] strs = command.split(" ", 4);
			if (strs.length < 4) {
				//System.out.println("Error command,Please input the right command again!");
				return;
			}
			int jobID = Integer.parseInt(strs[1]);
			String jobName = strs[2];
			String[] args = strs[3].split(" ");
			//System.out.println("JobID : " + jobID + " jobName : " + jobName	+ " " + " args : " + args);
			if (log.isInfoEnabled())
				log.info("JobID:" + jobID + " jobName:" + jobName + " "	+ "args=" + args);
			bs.startBatch(jobID, args);
		} else if (command.startsWith("stop ")) {
			String[] strs = command.split(" ");
			if (strs.length < 2) {
				//System.out.println("Error command,Please input the right command again!");
				return;
			}
			bs.stopBatch(Integer.parseInt(strs[1]));
		} else {
			//System.out.println("Error command,Please input the right command again!");
		}
	}

}
