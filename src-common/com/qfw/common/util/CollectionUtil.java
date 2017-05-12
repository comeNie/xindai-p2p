package com.qfw.common.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CollectionUtil {
	/**
	 * 判断集合是否为空
	 * @param c
	 * @return
	 */
	public static boolean isEmpty(Collection c){
		if(c ==null || c.isEmpty()){
			return true;
		}
		return false;
	}
	
	public static boolean isNotEmpty(Collection c){
		return !isEmpty(c);
	}
	/**
	 * 字符串转成Map对象,key不带空格
	 * @param s
	 * @return
	 */
	public static Map string2Map(String s){
		Map map = new HashMap();
		if(s != null && !s.isEmpty()){
			String trimstr = s.trim();
			String mapDataStr = null;
			int mapDataBeginIndex = trimstr.indexOf("{");
			int mapDataEndIndex = trimstr.lastIndexOf("}");
			if(mapDataBeginIndex == 0){//s字符串以“{”开头，必须要以“}”结尾
				if(mapDataEndIndex != trimstr.length()-1){//不以“}”结尾
					return map;
				}else{
					mapDataStr = trimstr.substring(mapDataBeginIndex+1, mapDataEndIndex);
				}
			}else{
				mapDataStr = trimstr;
			}
			String[] keyValues = mapDataStr.split(",");
			for (String string : keyValues) {
				String[] keyValue = string.split("=");
				map.put(keyValue[0].trim(), keyValue[1]);
			}
		}
		return map;
	}

}
