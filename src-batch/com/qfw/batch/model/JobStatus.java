/**
 * job任务状态处理类
 */
package com.qfw.batch.model;

import org.apache.commons.lang.StringUtils;

/**
 * JobStatus类包含所有任务可能的状态
 * 
 * @author Jie
 * 
 */

public class JobStatus {

	/**
	 * CREATED状态代表任务已经创建
	 */
	public static final Integer CREATED = new Integer(1);
	/**
	 * RUNNING状态代表任务正在执行
	 */
	public static final Integer RUNNING = new Integer(2);
	/**
	 * SUCCESS状态代表任务执行成功
	 */
	public static final Integer SUCCESS = new Integer(3);
	/**
	 * FAILURE状态代表任务执行失败
	 */
	public static final Integer FAILURE = new Integer(4);
	/**
	 * TEMINATED状态代表任务异常中止
	 */
	public static final Integer TEMINATED = new Integer(5);
	/**
	 * 表示人为通过命令将一个任务停止
	 */
	public static final Integer STOPPED = new Integer(6);
	/**
	 * WAITFILE表示等待轮询一个文件
	 */
	public static final Integer WAITFILE = new Integer(7);
	/**
	 * NOSUCCESS上次未完成
	 */
	public static final Integer NOSUCCESS = new Integer(8);

	/**
	 * SUCCESS_CYCLE轮询作业的成功
	 */
	public static final Integer SUCCESS_CYCLE = new Integer(9);

	/**
	 * 转换 任务状态编号为 中文表示
	 * 
	 * @param status
	 *            状态代码
	 * @return 中文表示
	 */
	public static String changeToGB(Object status) {
		String return_GB = "";
		if (status == null)
			return return_GB;
		switch (((Integer) status).intValue()) {
		case 1:
			return_GB = "任务等待";
			break;
		case 2:
			return_GB = "正在执行";
			break;
		case 3:
			return_GB = "任务完成";
			break;
		case 4:
			return_GB = "任务失败";
			break;
		case 5:
			return_GB = "异常中止";
			break;
		case 6:
			return_GB = "任务停止";
			break;
		case 7:
			return_GB = "等待文件";
			break;
		case 8:
			return_GB = "上次未完";
			break;
		case 9:
			return_GB = "轮询任务完成";
			break;
		}
		return return_GB;
	}

	/**
	 * 将 String字符串转换成 int 类型
	 * 
	 * @param str
	 *            待转换字符串
	 * @return 转换后的int类型
	 */
	public static int changeToInteger(String str) {
		int iNum = 0;
		if (StringUtils.isBlank(str)) {
			return iNum;
		} else {
			iNum = Integer.parseInt(str.trim());
		}
		return iNum;
	}
}
