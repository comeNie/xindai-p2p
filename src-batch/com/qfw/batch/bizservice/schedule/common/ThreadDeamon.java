package com.qfw.batch.bizservice.schedule.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qfw.batch.bizservice.schedule.BatchServiceImpl;



/**
 * 守护线程类
 * @author Cao Si Bin
 * @version v1.0 Cao Si Bin 2006-07-14
 */
public class  ThreadDeamon extends Thread
{
	/** 日志对象 */
	private static Log logger = LogFactory.getLog(ThreadDeamon.class);
	/** 标记批量框架当前运行状态 */
	public static boolean RUNSWITCH = false;

	/**
	 * 线程的run方法
	 * @author Cao Si Bin
	 * @version v1.0 Cao Si Bin 2006-07-14
	 */
	public void run()
	{
		//获得Runtime实例
		Runtime runc = Runtime.getRuntime();
		//循环
		while (true)
		{
			try
			{
				//设置框架运行标志为true
				
				RUNSWITCH = true;
				//System.out.println(" 总耗用内存数 = "+ runc.totalMemory() + " 可用内存数 = " + runc.freeMemory());
				if( logger.isDebugEnabled() )
				{
					logger.debug( " 总耗用内存数 = " + runc.totalMemory() + " 可用内存数 = " + runc.freeMemory() );
				}
				//判断 线程池激活线程 ！＝ 线程池的最大线程数量
				BatchServiceImpl.startScan();
				//线程睡眠3分钟
				Thread.sleep(30000);
				
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				if(logger.isInfoEnabled())
				{
					logger.info("-----------ThreadDeamon error" + ex.getMessage());
					logger.info("-----------ThreadDeamon error" + ex.getStackTrace()[0]);
				}
			}
		}
	}
}
