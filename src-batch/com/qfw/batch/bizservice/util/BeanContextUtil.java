package com.qfw.batch.bizservice.util;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
public class BeanContextUtil implements ApplicationContextAware{
	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context = context;		
	}

	public static ApplicationContext getContext() {
		return context;
	}
	public static Object getBean(String name){
		return context.getBean(name);
	}
	
}
