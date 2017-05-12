package com.qfw.batch.test;

import com.qfw.batch.bizservice.schedule.common.impl.BatchCycleJob;

public class TestCycleJob extends BatchCycleJob {

	
	@Override
	public boolean isCanBeRun() throws Exception {
		// TODO Auto-generated method stub
		double ran = Math.random();
		//System.out.println("获取随机数"+ran);
		return false;//ran > 0.5;
	}

	@Override
	public String runJob() throws Exception {
		// TODO Auto-generated method stub
		//System.out.println("开始执行");
		int i = 1;
		while(i<=5){
			Thread.sleep(1000L);
			//System.out.println("执行"+i+"分钟");
			i++;
		}
		//System.out.println("结束执行");
		return "执行成功";
	}

}
