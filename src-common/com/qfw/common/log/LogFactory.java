package com.qfw.common.log;

import org.apache.log4j.Logger;

public class LogFactory {

	private static LogFactory instance;
	
	private LogFactory() {
		super();
	}
	
	public static LogFactory getInstance(){
		if(instance == null){
			instance = new LogFactory();
		}
		return instance;
	}

	public Logger getBusinessLogger(){
		return Logger.getLogger("business");
	}
	
	public Logger getPlatformLogger(){
		return Logger.getLogger("platform");
	}
}
