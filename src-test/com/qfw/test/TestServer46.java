package com.qfw.test;


import java.io.File;




import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.junit.Test;

import com.qfw.bizservice.transaction.chanel.impl.QueryValidatorServices;
import com.qfw.common.util.DesAndBase64Utils;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public class TestServer46 {
	
	private static String param = "张三,140421199003086816";
	private static String param1 = "张三,140421199003086816;李四,140421197005063456";

	@Test
	public void testWs() {
		TestServer46.wstester(param);
	}
	
	
	public static void wstester(String param){
		long start = System.currentTimeMillis();
		try {
			System.out.println("======start===thread========");

			JaxWsProxyFactoryBean svr = new JaxWsProxyFactoryBean();
			svr.setServiceClass(QueryValidatorServices.class);
			svr.setAddress("http://211.147.7.46/zcxy/services/QueryValidatorServices?wsdl"); //这个地址有可能调整，请根据实际需要配置
			
			QueryValidatorServices service = (QueryValidatorServices) svr
					.create();
			String userName = "jssjtztest";// 用户名
			String password = "jssjtz@20150714";// 密码
			System.setProperty("javax.net.ssl.trustStore", "");
			String resultXML = "";
			String datasource = "1A020202";// 数据类型1A020202=身份证信息查询

			String encodeUserName = "", encodePassword = "", encodeParam = "", encodeDatasource = "";

			encodeUserName = DesAndBase64Utils.encode(DesAndBase64Utils.ENCODE_KEY,
					userName.getBytes("UTF-8")).toString();
			encodePassword = DesAndBase64Utils.encode(DesAndBase64Utils.ENCODE_KEY,
					password.getBytes("UTF-8")).toString();
			encodeDatasource = DesAndBase64Utils.encode(DesAndBase64Utils.ENCODE_KEY,
					datasource.getBytes("UTF-8")).toString();
			encodeParam = DesAndBase64Utils.encode(DesAndBase64Utils.ENCODE_KEY,
					param.getBytes("GBK")).toString();

			int queryCount = 1;
			
			for (int i = 0; i < queryCount; i++) {
			//单条
				resultXML = service.querySingle(encodeUserName, encodePassword,
						encodeDatasource, encodeParam);
						
		
			FileUtils.writeStringToFile(
					new File("G:\\ztzx\\ztzximg\\"
							+ StringUtils.substringAfter(param, ",") + ".xml"),
					new String(DesAndBase64Utils.decode(DesAndBase64Utils.ENCODE_KEY,
							Base64.decode(resultXML.getBytes())), "GBK"));
			}
			
//			for (int i = 0; i < queryCount; i++) {
//			//多条查询
//			encodeParam = QueryThread.encode(QueryThread.ENCODE_KEY,
//					param1.getBytes("GBK")).toString();			
//			
//			resultXML = service.queryBatch(encodeUserName, encodePassword,
//						encodeDatasource, encodeParam);			
//			
//			FileUtils.writeStringToFile(
//					new File("G:\\ztzx\\ztzximg\\"
//							+ CommUtils.crtRandomUUID() + ".xml"),
//					new String(QueryThread.decode(QueryThread.ENCODE_KEY,
//							Base64.decode(resultXML.getBytes())), "GBK"));
//			
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();

		System.out.println("thread-time:" + (end - start));
	}
}
