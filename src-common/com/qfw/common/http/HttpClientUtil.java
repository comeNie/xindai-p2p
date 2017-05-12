package com.qfw.common.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import jodd.http.HttpBrowser;
import jodd.http.HttpRequest;

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
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.qfw.common.log.LogFactory;
import com.qfw.platform.model.vo.UserInfo;

public class HttpClientUtil {
	private static Logger log = LogFactory.getInstance().getBusinessLogger();
	
	public static void UserInfoSyncHttp(String reqURL, UserInfo userInfo) throws IOException{
		
		HttpBrowser browser = new HttpBrowser();
		HttpRequest request = HttpRequest.post(reqURL);
		request.header("Accept-Charset", "utf-8");
		request.header("Content-Type", "text/html;charset=UTF-8");
		request.query("age", userInfo.getAge());
		request.query("birthday", userInfo.getBirthday());
		request.query("city", userInfo.getCity());
		request.query("cultural", userInfo.getCultural());
		request.query("custName", userInfo.getCustName());
		request.query("haveChild", userInfo.getHaveChild());
		request.query("idCard", userInfo.getIdCard());
		request.query("loginName", userInfo.getLoginName());
		request.query("mail", userInfo.getMail());
		request.query("marital", userInfo.getMarital());
		request.query("mobile", userInfo.getMobile());
		request.query("nativeAddr", userInfo.getNativeAddr());
		request.query("operate", userInfo.getOperate());
		request.query("password", userInfo.getPassword());
		request.query("sex", userInfo.getSex());
		browser.sendRequest(request);
		String page = browser.getPage();
		if(log.isDebugEnabled()){
			log.debug("数据同步返回结果：==="+page);
		}
	}
	
	public static void UserInfoSync(String reqURL, Map<String, String> params) throws IOException{
//		 CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
		 RequestConfig requestConfig = RequestConfig.custom()
		    	    .setSocketTimeout(3000).setConnectTimeout(10000).build();
		    	  CloseableHttpAsyncClient httpclient = HttpAsyncClients.custom()
		    	    .setDefaultRequestConfig(requestConfig).build();
    	try {
     	   httpclient.start();
     	   HttpPost httpPost = new HttpPost(reqURL);
     	   List<NameValuePair> formparams = new ArrayList<NameValuePair>(); 
     	   Set<Entry<String, String>> set = params.entrySet();
     	   Iterator<Entry<String, String>> it = set.iterator();
     	   while(it.hasNext()){
     		  Entry<String, String> entry = it.next();
     		  formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
     	   } 
     	   UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
           httpPost.setEntity(uefEntity);  
     	   final CountDownLatch latch = new CountDownLatch(1);
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
      	      	if(log.isDebugEnabled()){
      	      	latch.countDown();
      	      		log.debug(response.getStatusLine());
					log.debug(responseContent);
      	      	}
      	     }
      	     @Override
      	     public void failed(final Exception ex) {
      	    	latch.countDown();
      	    	 log.error(ex);
      	     }
      	     @Override
      	     public void cancelled() {
      	    	if(log.isDebugEnabled()){
      	      		log.debug("cancelled");
      	      	}
      	     }
      	    });
     	  try {
			latch.await();
		} catch (InterruptedException e) {
		}
     	  } finally {
     		  httpclient.close();
     	  }
			if(log.isDebugEnabled()){
	      		log.debug("Done");
	      	}
	}
}
