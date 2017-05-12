package com.qfw.batch.bizservice.schedule;

import com.qfw.batch.bizservice.schedule.common.ThreadDeamon;
import com.qfw.batch.bizservice.util.JobUtil;
import com.qfw.batch.bizservice.util.QuartzUtil;
import com.qfw.batch.listener.BatchWorkerListener;



/**
 * 
 * @author Jie
 *
 */
public class BatchDaemoUCC {
	
	private static boolean isRun = false;

	/**
	 * 启动批框架
	 */
	public static void mainStart() {
		try {
			JobUtil.init();
			QuartzUtil.startScheduler();
			if(!isRun){
				BatchWorkerListener workerListener = new BatchWorkerListener();
				workerListener.start();
				ThreadDeamon td = new ThreadDeamon();
				td.start();
				isRun = true;
			}
		} catch (Exception e) {
			 
		}
	}

}