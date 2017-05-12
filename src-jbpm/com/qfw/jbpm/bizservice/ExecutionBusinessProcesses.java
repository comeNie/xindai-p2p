package com.qfw.jbpm.bizservice;

import java.lang.reflect.Method;

import com.qfw.common.exception.BizException;
import com.qfw.common.util.ApplicationContextUtil;

public class ExecutionBusinessProcesses {
	
	/**
	 * 通过Sring beanId 执行业务处理流程。
	 * @param beanId
	 * @param methodName
	 * @param executionId
	 * @return
	 * @throws Exception
	 */
	public Object execution(String beanId,String methodName,String executionId) throws Exception{
		if(beanId!=null && !beanId.isEmpty() && methodName!=null && !methodName.isEmpty()){
			Object obj = ApplicationContextUtil.getBean(beanId);
			if(obj != null){
				try {
					Method method = obj.getClass().getDeclaredMethod(methodName, new Class[]{String.class});
					return method.invoke(obj, executionId);
				} catch (Exception e) {
					throw e;
				}
			}else{
				throw new BizException(methodName+"not exists!");
			}
		}else{
			throw new BizException("beanId,methodName is null");
		}
	}

}
