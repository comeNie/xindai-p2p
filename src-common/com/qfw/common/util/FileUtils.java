package com.qfw.common.util;

import java.io.File;

public class FileUtils {
	/**
	 * 获取文件后缀
	 * @param fileName
	 * @return
	 */
	public static String getExt(String fileName){		
		if(validFileName(fileName)){
			int index = fileName.lastIndexOf(".");
			return fileName.substring(index+1);
		}
		return null;
	}
	/**
	 * 获取没有后缀的文件名
	 * @param fileName
	 * @return
	 */
	public static String getPrefixName(String fileName){
		if(validFileName(fileName)){
			int index = fileName.lastIndexOf(".");
			return fileName.substring(0,index);
		}
		return null;
	}
	public static boolean validFileName(String fileName){
		if(StringUtils.isEmpty(fileName))
			return false;
		int index = fileName.lastIndexOf(".");
		if(index == -1)
			return false;
		return true;
	}
	
	public static boolean deleteFile(File file){
		if(file.exists() && file.isFile()){
			return file.delete();			
		}
		return false;
	}
	
	public static boolean deleteFileByName(String fileName){
		if(StringUtils.isEmpty(fileName)){
			return false;
		}
		File file = new File(fileName);
		return deleteFile(file);
	}
	
	public static void main(String[] args) {
		String s = "abc.jpg";
		//System.out.println(getExt(s));
		//System.out.println(getPrefixName(s));
		
		float a = (float)800/1024;
		//System.out.println(a);
	}

}
