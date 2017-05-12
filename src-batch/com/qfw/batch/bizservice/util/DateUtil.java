package com.qfw.batch.bizservice.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 日期工具类
 * @author LSJ
 *
 */
public class DateUtil {
	
	//年月日 时分秒
	public static SimpleDateFormat getYmdhmsFormatter(){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}	
	
	
	/**
	 * 获取时间的年月日时分秒
	 */ 
	public static String getYmdhms(Date value){
		if(value!=null){
			return getYmdhmsFormatter().format(value);
		}
		return "";
	}
	
	/**
	 * 将字符串转换成时间类型
	 * @param s
	 * @return
	 */
	public static Date getDateByFull(String s){
		try {
			return getYmdhmsFormatter().parse(s);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
