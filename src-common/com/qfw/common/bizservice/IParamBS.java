package com.qfw.common.bizservice;

import java.util.Map;


public interface IParamBS extends IBaseService {

	/**
	 * 获取参数值
	 * @param parameterCode
	 * @return
	 */
	public String getParam(String parameterCode);
	/**
	 * 获取参数缓存
	 * @return
	 */
	public Map<String, String> getParams() ;
	
	/**
	 * 刷新缓存图片
	 */
	public void refreshParam();
}
