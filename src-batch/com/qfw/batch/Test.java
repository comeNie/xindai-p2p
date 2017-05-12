package com.qfw.batch;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.qfw.common.util.CollectionUtil;


public class Test {

	public static void main(String[] args) throws InterruptedException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		Map map = new HashMap();
		map.put("a", "1");
		map.put("b", "2");
		String mapStr = map.toString();
		//System.out.println(mapStr);
		Map map1 = CollectionUtil.string2Map(mapStr);
		//System.out.println(map1);
		/*//System.out.println(mapStr.indexOf("{"));
		//System.out.println(mapStr.lastIndexOf("}"));
		//System.out.println(mapStr.substring(1, 9));
		//System.out.println(mapStr.length());*/
		/*Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				int i=0;
				while(true){
					try {
						Thread.sleep(1000L);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						 
					}
					////System.out.println(++i);
				}
				
			}
		});
		t.start();
		//Thread.sleep(1000L);
		//t.stop();
		try{
			t.interrupt();
			//System.out.println(t.interrupted());
			//System.out.println(t.interrupted());
		}catch(Exception e){
			 
			t.stop();
		}*/
		
		Class objClass = Class.forName("java.lang.String");
		Object obj =  objClass.newInstance();
		if(obj instanceof String){
			((String) obj).valueOf("aaaddd");
			//System.out.println(obj);
		}
		
	}

}
