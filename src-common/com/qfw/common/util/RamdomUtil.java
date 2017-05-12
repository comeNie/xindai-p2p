package com.qfw.common.util;

import org.apache.commons.lang.RandomStringUtils;

public class RamdomUtil {
	/**
	 * 获取随机字符串
	 * @param count
	 * @return
	 */
	public static String getRandomString(int count){
		return RandomStringUtils.randomAlphanumeric(count);
	}
	/**
	 * 生成推荐码
	 * @param count
	 * @return
	 */
	public static String getRefereeCode(){
		return RandomStringUtils.randomAlphanumeric(2)+RandomStringUtils.random(6, false, true);
	}
}
