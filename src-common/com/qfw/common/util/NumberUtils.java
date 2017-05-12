package com.qfw.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

public class NumberUtils {
	
	public static boolean isBlank(Integer num){
		return num == null || num.intValue() == 0;
	}
	/**
	 * 保留二位小数金额
	 * @return
	 */
	public static DecimalFormat getDecimalFormat2(){
		DecimalFormat myformat = new DecimalFormat();
		myformat.applyPattern("##,###.00");
		return myformat;
	}
	/**
	 * 格式化两位金额，按","分开。
	 * @param num
	 * @return
	 */
	public static String format2(Object num){
		return getDecimalFormat2().format(num);
	}
	public static BigDecimal parseNum(String numSttr){
		try {
			Number num = getDecimalFormat2().parse(numSttr);
			return new BigDecimal(num.doubleValue());
		} catch (ParseException e) {
			 
		}
		return null;
	}
	public static void main(String[] args) throws ParseException {
		BigDecimal d = new BigDecimal("0.00");
		//System.out.println(getDecimalFormat2().format(d));
		BigDecimal b = parseNum(getDecimalFormat2().format(d));
		//System.out.println(b);
	}

}
