package com.qfw.test;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import com.qfw.common.exception.BizException;
import com.qfw.common.gateway.impl.HttpEndPointBS;
import com.qfw.common.util.StringUtils;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import com.sun.org.apache.xml.internal.security.utils.Base64;
public class Test {
	public static void main(String[] args) throws IOException, TemplateException {
		 
//		 CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom()
	    	    .setSocketTimeout(3000).setConnectTimeout(10000).build();
	    	  CloseableHttpAsyncClient httpclient = HttpAsyncClients.custom()
	    	    .setDefaultRequestConfig(requestConfig).build();
    	try {
     	   httpclient.start();
     	  final CountDownLatch latch = new CountDownLatch(1);
     	   HttpPost httpPost = new HttpPost("http://120.24.97.127:8889/century-webapps/api/sys/user/synUser");
     	   List<NameValuePair> formParams = new ArrayList<NameValuePair>(); //构建POST请求的表单参数
     	   Map<String,String> params = new HashMap<String, String>();

	  		params.put("operate", "1");
	  		params.put("loginName", "userinfo_test1");
	  		params.put("mobile", "15159597675");
	  		params.put("mail", "test@test.com");
	  		params.put("refereeName", "");
	  		params.put("idCard", "31011120150729451X");
	  		params.put("age", "20");
	  		params.put("birthday", "2015-07-29");
	  		params.put("custName", "yang");
	  		params.put("cultural", "0");
	  		params.put("telephone", "1111111");
	  		params.put("sex", "");
	  		params.put("marital", "M");
	  		params.put("haveChild", "0");
	  		params.put("nativeAddr", "其他");
			params.put("city", "其他");
  			params.put("password",Base64.encode("111111".getBytes()));
            for(Map.Entry<String,String> entry : params.entrySet()){ 
                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue())); 
            } 
            httpPost.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8")); 
     	   httpclient.execute(httpPost, new FutureCallback<HttpResponse>() {
      	     @Override
      	     public void completed(final HttpResponse response) {
      	    	latch.countDown();
      	    	HttpEntity entity = response.getEntity(); 
      	    	String responseContent = "";
      	    	if(entity != null){
      	    		try {
						responseContent = EntityUtils.toString(entity, "UTF-8");
					} catch (ParseException e) {
					} catch (IOException e) {
					} 
      	    	}
      	      System.out.println(response);
      	      System.out.println(responseContent);
      	     }
      	     @Override
      	     public void failed(final Exception ex) {
      	    	latch.countDown();
      	    	 System.out.println(ex);
      	     }
      	     @Override
      	     public void cancelled() {
      	    	latch.countDown();
      	    	 System.out.println("1111111111");
      	     }
      	    });
     	  try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     	  } finally {
     		  httpclient.close();
     	  }
     	  System.out.println("Done");
	}
}
