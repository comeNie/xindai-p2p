package com.qfw.common.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.qfw.model.AppConst;


/**
 * 日期工具类
 * @author LSJ
 *
 */
public class DateUtils {
	
	//时间种类
	public enum DateType{
		YEAR,MONTH,DAY,HOUR,MINUTE,SECOND
	}
	
	//年月日 
	public static SimpleDateFormat getYmdFormatter(){
		return new SimpleDateFormat("yyyy-MM-dd");
	}
	//年月日 时分秒
	public static SimpleDateFormat getYmdhmsFormatter(){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}	
	
	//年月日 时分
	public static SimpleDateFormat getYmdhmFormatter(){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm");
	}
	
	//webService 参数使用到的时间格式转换
	public static SimpleDateFormat getymmddhmsFormatter(){
		return new SimpleDateFormat("yyyyMMdd HH-mm-ss");
	}
	//年月日时
	public static SimpleDateFormat getYmdhFormatter(){
		return new SimpleDateFormat("yyyy-MM-dd HH");
	}
	

	/**
	 * 获得指定格式的时间格式化类
	 * @param format
	 * @return
	 * @see 
	 * @author JAVA  
	 * @date 2011-5-23 下午11:04:15 
	 */ 
	public static SimpleDateFormat getSimpleDateFormat(String format){
		try{
			if(StringUtils.isEmpty(format)){
				format = "yyyy-MM-dd HH:mm:ss";
			}
			return new SimpleDateFormat(format);
		}catch (Exception e) {
			return DateUtils.getYmdhmsFormatter();
		}
		
	}
	
	/**
	 * 根据时间格式和时间，得到字符串时间
	 * @param format：时间格式
	 * @param date：时间
	 * @return：返回字符时间
	 */
	public static String getDateString(String format,Date date){
		return new SimpleDateFormat(format).format(date);
	}
	
	/**
	 * 将当天的时间转换成YYYYmmDD格式的字符串时间
	 * @return
	 */ 
	public static String getYmdOfToday(){		
		return getYmdFormatter().format(new Date());
		
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
	
	/**
	 * @return
	 * @Creator:JAVA
	 * @Date:2011-4-22 下午03:01:08
	 * @Description：获取当前时间字符串
	 */ 
	public static String getNow(){
		return DateUtils.getYmdhms(new Date());
	}
	
	/**
	 * webService 参数 时间格式 
	 * @param d
	 * @return 时间字符串
	 */
	public static String getFormatDateByStr(Date d){
		if(d==null)
			return StringUtils.EMPTY;
		return getymmddhmsFormatter().format(d);
	}
	
	
	public static Date getDateFormateByStr(String s){
		try {
			return getymmddhmsFormatter().parse(s);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	
	/**
	 *  将字符串类型转换成YYYYmmDD的时间类型
	 * @param s
	 * @return
	 */
	public static Date getDateByYMD(String s){
		try {
			return getYmdFormatter().parse(s);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 根据格式将字符串类型转换成时间类型
	 * @param format
	 * @param s
	 * @return
	 */
	public static Date getDateByFormat(String format,String s){
		try {
			return getSimpleDateFormat(format).parse(s);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 得到当天的开始时间
	 * @return
	 */
	public static Date getTodayStart(){
		try {
			return getYmdhmsFormatter().parse(getYmdOfToday()+" 00:00:00");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * 得到当天的开始时间
	 * @return
	 */
	public static Date getTodayEnd(){
		try {
			return getYmdhmsFormatter().parse(getYmdOfToday()+" 23:59:59");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * 获取时间的年月日
	 * @return
	 */
	public static String getYmd(Date value){
		if(value==null)
			return StringUtils.EMPTY;
		return getYmdFormatter().format(value);
	}
	
	/**
	 * 获取时间的年月日时分秒
	 */ 
	public static String getYmdhms(Date value){
		if(value==null)
			return StringUtils.EMPTY;
		return getYmdhmsFormatter().format(value);
	}
	
	/**
	 *获取时间的年月日时分
	 */ 
	public static String getYmdhm(Date value){
		if(value==null)
			return StringUtils.EMPTY;
		return getYmdhmFormatter().format(value);
	}
	
	/**
	 * 得到输入时间的开始时间
	 * @param value
	 * @return
	 */
	public static Date getStartDate(Date value){
		try {
			return getYmdhmsFormatter().parse(getYmd(value)+" 00:00:00");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	

	/**
	 * 得到输入时间的结束时间
	 * @param value
	 * @return
	 */
	public static Date getEndDate(Date value){
		try {
			return getYmdhmsFormatter().parse(getYmd(value)+" 23:59:59");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	

	/**
	 * 得到输入时间的结束时间
	 * @param value
	 * @return
	 */
	public static Date getYmdhmsDate(Date value){
		try {
			return getYmdhmsFormatter().parse(getYmdhms(value));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 加多少分钟
	 * @param value
	 * @param minute
	 * @return
	 */
	public static Date addMinute(Date value,int minute){
		Calendar cal=Calendar.getInstance();
		cal.setTime(value);
		cal.add(Calendar.MINUTE,minute);
		return cal.getTime();
	}
	/**
	 * 在输入时间的基础上增加天数
	 * @param value：输入的时间
	 * @param day：要增加的天数
	 * @return
	 */
	public static Date addDay(Date value,int day){
		Calendar cal=Calendar.getInstance();
		cal.setTime(value);
		cal.add(Calendar.DAY_OF_YEAR,day);
		return cal.getTime();
	}
	
	public static Date addMonth(Date value,int month){
		Calendar cal=Calendar.getInstance();
		cal.setTime(value);
		cal.add(Calendar.MONTH,month);
		return cal.getTime();
	}
	
	public static Date addYear(Date value,int year){
		Calendar cal=Calendar.getInstance();
		cal.setTime(value);
		cal.add(Calendar.YEAR,year);
		return cal.getTime();
	}
	
	/**
	 * 在基础时间上加上小时
	 * @param value
	 * @param hour
	 * @return
	 * @see 
	 * @author linzb  
	 * @date 2011-8-9 下午03:25:43 
	 * @modifier
	 * @modity Date
	 */
	public static Date addHour(Date value,int hour){
		Calendar cal=Calendar.getInstance();
		cal.setTime(value);
		cal.add(Calendar.HOUR_OF_DAY,hour);
		return cal.getTime();
	}
	
	
	/**
	 * @param start
	 * @param end
	 * @return
	 * @Creator:JAVA
	 * @Date:2011-4-22 下午03:04:40
	 * @Description：计算俩个时间的相差天数
	 */ 
	public static long minuDay(Date start,Date end){
		Long startM=getStartDate(start).getTime();
		Long endM=getStartDate(end).getTime();
		long result = (endM-startM) / (24 * 60 * 60*1000); 
		return result;
	}
	
	
	/**
	 * @param start
	 * @param end
	 * @param dateType 时间类型
	 * @return
	 * @Creator:JAVA
	 * @Date:2011-4-22 下午03:42:18
	 * @Description：根据时间类型计算俩个时间差，默认返回天数差
	 */ 
	public static long minu(Date start,Date end,DateType dateType){
		if(DateType.HOUR.equals(dateType)){
			return DateUtils.minuDay(start, end)*24;
		}
		else if(DateType.MINUTE.equals(dateType)){
			return DateUtils.minuDay(start, end)*24*60;
		}
		else if(DateType.SECOND.equals(dateType)){
			return DateUtils.minuDay(start, end)*24*60*60; 
		}
		return DateUtils.minuDay(start, end); 
	}
	

	/**
	 *  将输入的字符类型的时间转换成时间类型的开始时间
	 * @param dateStr
	 * @return
	 */
	public static Date getStartDate(String dateStr){
		try{
			return getYmdhmsFormatter().parse(dateStr+" 00:00:00");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}		
	}
	

	/**
	 *  将输入的字符类型的时间转换成时间类型的开始时间
	 * @param dateStr
	 * @return
	 */
	public static Date getEndDate(String dateStr){
		try{
			return getYmdhmsFormatter().parse(dateStr+" 23:59:59");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}		
	}
	
	/**
	 * 得到几天前的时间
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}

	/**
	 * 得到几天后的时间
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

	
	/**
	 * 
	 * 将输入的时间转换为 此小时段的开始时间
	 * @return
	 * @see 
	 * @author Cheng  
	 * @date 2011-8-26 上午11:02:22
	 */
	public static String getYmdhStartDate(){
		return getYmdhFormatter().format(new Date())+":00:00";
	}
	
	/**
	 * 
	 * 将输入的时间转换为 此小时段的结束时间
	 * @param strDate
	 * @return
	 * @see 
	 * @author Cheng  
	 * @date 2011-8-26 上午11:13:46
	 */
	public static String getYmdhEndDate(){
		return getYmdhFormatter().format(new Date())+":59:59";
	}

	
	/**
	 * 获取一个月有多少天数
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getActualMaximum(int year,int month){
		 Calendar c= Calendar.getInstance();
		 c.set(Calendar.YEAR, year);
		 c.set(Calendar.MONTH, month-1);
		 c.set(Calendar.DATE, 1);
		 return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 获取当前月有多少天数
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getActualMaximum(){
		 Calendar c= Calendar.getInstance();
		 c.setTime(new Date());
		 return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 字串转成时间
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @return
	 */
	public static Date getDateForString(int year, int month, int day) {
		StringBuffer sb = new StringBuffer();
		sb.append(year).append("-");
		if (month < 10 && month > 0) {
			sb.append(0).append(month);
		} else {
			sb.append(month);
		}
		sb.append("-");
		if (day < 10 && day > 0) {
			sb.append(0).append(day);
		} else {
			sb.append(day);
		}
		return getDateByYMD(sb.toString());
	}
	
	/**
	 * @param start
	 * @param end
	 * @return
	 * @Creator:JAVA
	 * @Date:2011-4-22 下午03:04:40
	 * @Description：计算俩个时间的相差秒数
	 */ 
	public static double minuSecond(Date start,Date end){
		Long startM=start.getTime();
		//System.out.println("startM=="+startM);
		Long endM=end.getTime();
		//System.out.println("end=="+endM);
		//System.out.println("endM-startM=="+(endM-startM));
		double result = getTwoDecimal((endM-startM) / 1000,2); 
		return result;
	}
	
	/**
	 * @param start
	 * @param end
	 * @return
	 */ 
	public static double getTwoDecimal(double value,int i){ 
		 BigDecimal bg = new BigDecimal(value);
		 double result = bg.setScale(i, BigDecimal.ROUND_HALF_UP).doubleValue();
		 return result;
	}
	
	/**
	 * 根据时间类型计算俩个时间差，默认返回秒数
	 * @param start
	 * @param end
	 * @param dateType 时间类型
	 * @return
	 * @Creator:JAVA
	 * @Date:2011-4-22 下午03:42:18
	 * @Description：根据时间类型计算俩个时间差，默认返回秒数
	 */ 
	public static double DateMinu(Date start,Date end,DateType dateType){
		if(DateType.HOUR.equals(dateType)){
			return getTwoDecimal(DateUtils.minuSecond(start, end)/3600,2);
		}
		else if(DateType.MINUTE.equals(dateType)){
			return getTwoDecimal(DateUtils.minuSecond(start, end)/60,2);
		}
		else if(DateType.SECOND.equals(dateType)){
			return getTwoDecimal(DateUtils.minuSecond(start, end),2); 
		}
		if(DateType.DAY.equals(dateType)){
			return getTwoDecimal(DateUtils.minuSecond(start, end)/(3600*24),2); 
		}
		return getTwoDecimal(DateUtils.minuSecond(start, end),2); 
	}
	
	/**
	 *月份之差
	 */
	public static int diffInMonth(Date startDate,Date endDate){
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		int startMonth = cal.get(Calendar.MONTH) + 1;
		int startYear = cal.get(Calendar.YEAR);//获取当前的年份
 
		cal.setTime(endDate);
		int endMonth =  cal.get(Calendar.MONTH) + 1;
		int endYear = cal.get(Calendar.YEAR);
		////System.out.println(endYear);
		int n = 0;
		n = (endYear - startYear) * 12 + endMonth - startMonth;
		//System.out.println(n);
		return n;
	}
	
	/**
	 *与当前日期的月份差 
	 */
	public static int diffInMonthAndNow(Date endDate){
		Date startDate = new Date();
		return  diffInMonth(startDate,endDate);
	}
	/**
	 * 获取国付宝服务器时间 用于时间戳
	 * @return 格式YYYYMMDDHHMMSS
	 */
	public static String getGopayServerTime() {
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setCookiePolicy(CookiePolicy.RFC_2109);
		httpClient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, 10000); 
		GetMethod getMethod = new GetMethod(AppConst.GOPAY_SERVER_TIME_URL);
		getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"GBK");  
		// 执行getMethod
		int statusCode = 0;
		try {
			statusCode = httpClient.executeMethod(getMethod);			
			if (statusCode == HttpStatus.SC_OK){
				String respString = org.apache.commons.lang.StringUtils.trim((new String(getMethod.getResponseBody(),"GBK")));
				return respString;
			}			
		} catch (IOException e) {
//			 
		} finally {
			getMethod.releaseConnection();
		}
		return null;
	}
}
