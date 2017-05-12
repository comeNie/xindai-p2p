package com.qfw.test;
import java.io.IOException; 
import java.io.UnsupportedEncodingException; 
import java.security.KeyManagementException; 
import java.security.NoSuchAlgorithmException; 
import java.security.cert.CertificateException; 
import java.security.cert.X509Certificate; 
import java.util.ArrayList; 
import java.util.Date;
import java.util.HashMap; 
import java.util.LinkedList;
import java.util.List; 
import java.util.Map; 
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.net.ssl.SSLContext; 
import javax.net.ssl.TrustManager; 
import javax.net.ssl.X509TrustManager; 

import org.apache.http.HttpEntity; 
import org.apache.http.HttpResponse; 
import org.apache.http.NameValuePair; 
import org.apache.http.ParseException; 
import org.apache.http.client.ClientProtocolException; 
import org.apache.http.client.HttpClient; 
import org.apache.http.client.entity.UrlEncodedFormEntity; 
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost; 
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.conn.scheme.Scheme; 
import org.apache.http.conn.ssl.SSLSocketFactory; 
import org.apache.http.impl.client.DefaultHttpClient; 
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicNameValuePair; 
import org.apache.http.util.EntityUtils; 

import com.qfw.common.util.DateUtils;
import com.qfw.common.util.MD5Utils;
 
/**
 * @see =====================================================================================================
 * @see 在开发HTTPS应用时，时常会遇到两种情况
 * @see 1、要么测试服务器没有有效的SSL证书,客户端连接时就会抛异常
 * @see?   javax.net.ssl.SSLPeerUnverifiedException: peer not authenticated
 * @see 2、要么测试服务器有SSL证书,但可能由于各种不知名的原因,它还是会抛一个堆烂码七糟的异常
 * @see =====================================================================================================
 * @see 由于我们这里使用的是HttpComponents-Client-4.1.2创建的连接，所以，我们就要告诉它使用一个不同的TrustManager
 * @see TrustManager是一个用于检查给定的证书是否有效的类
 * @see SSL使用的模式是X.509....对于该模式,Java有一个特定的TrustManager,称为X509TrustManager
 * @see 所以我们自己创建一个X509TrustManager实例
 * @see 而在X509TrustManager实例中，若证书无效，那么TrustManager在它的checkXXX()方法中将抛出CertificateException
 * @see 既然我们要接受所有的证书,那么X509TrustManager里面的方法体中不抛出异常就行了
 * @see 然后创建一个SSLContext并使用X509TrustManager实例来初始化之
 * @see 接着通过SSLContext创建SSLSocketFactory，最后将SSLSocketFactory注册给HttpClient就可以了
 * @see =====================================================================================================
 * @create Jul 30, 2012 1:11:52 PM
 * @author 玄玉(http://blog.csdn/net/jadyer)
 */ 
public class HttpClientUtil { 
    public static void main(String[] args)throws Exception{ 
    	
    	//HeadMethod
    	
    	/*Map<String, String> param = new HashMap<String, String>();
		String merNo = "19464";
		String billNo = "Td0100d0101";
		String amount = "10.00";
		String returnURL = "http://qiangfanwan.f3322.org:8080/p2p/paymentReturn.do";
		String md5key = "R[Qjw^zN";
		String md5 = MD5Utils.getMD5Str(merNo+"&"+billNo+"&"+amount+"&"+returnURL+"&"+md5key);
		param.put("MerNo", merNo);
		param.put("BillNo", billNo);
		param.put("Amount", amount);
		param.put("ReturnURL", returnURL);
		param.put("AdviceURL", "http://qiangfanwan.f3322.org:8080/p2p/paymentReturn.do");
		param.put("SignInfo", md5.toUpperCase());
		param.put("orderTime", DateUtils.getDateString("yyyyMMddHHmmss", new Date()));
		param.put("defaultBankNumber", "CCB");
		param.put("Remark", "10");
		param.put("products", "可乐2L");
		String s = sendSSLPostRequest("https://pay.ecpss.cn/sslpayment", param);*/
		//System.out.println(s);
    	 CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
    	try {
    		
     	   httpclient.start();
     	   final HttpGet[] requests = new HttpGet[] {
     	     new HttpGet("http://www.apache.org/"),
     	     new HttpGet("https://www.verisign.com/"),
     	     new HttpGet("http://www.google.com/"),
     	     new HttpGet("http://www.baidu.com/") };
     	   final CountDownLatch latch = new CountDownLatch(requests.length);
     	 /* httpclient.execute("http://120.24.97.127:8889/century-webapps/api/sys/user/synUser", new FutureCallback<HttpResponse>() {
      	     //无论完成还是失败都调用countDown()
      	     @Override
      	     public void completed(final HttpResponse response) {
      	      latch.countDown();
      	      System.out.println(request.getRequestLine() + "->"
      	        + response.getStatusLine());
      	     }
      	     @Override
      	     public void failed(final Exception ex) {
      	      latch.countDown();
      	      System.out.println(request.getRequestLine() + "->" + ex);
      	     }
      	     @Override
      	     public void cancelled() {
      	      latch.countDown();
      	      System.out.println(request.getRequestLine()
      	        + " cancelled");
      	     }
      	    })*/
     	   for (final HttpGet request : requests) {
     	    httpclient.execute(request, new FutureCallback<HttpResponse>() {
     	     //无论完成还是失败都调用countDown()
     	     @Override
     	     public void completed(final HttpResponse response) {
     	      latch.countDown();
     	      System.out.println(request.getRequestLine() + "->"
     	        + response.getStatusLine());
     	     }
     	     @Override
     	     public void failed(final Exception ex) {
     	      latch.countDown();
     	      System.out.println(request.getRequestLine() + "->" + ex);
     	     }
     	     @Override
     	     public void cancelled() {
     	      latch.countDown();
     	      System.out.println(request.getRequestLine()
     	        + " cancelled");
     	     }
     	    });
     	   }
     	   latch.await();
     	   System.out.println("Shutting down");
     	  } finally {
     	   httpclient.close();
     	  }
     	  System.out.println("Done");
    } 
     
    /**
     * 向HTTPS地址发送POST请求
     * @param reqURL 请求地址
     * @param params 请求参数
     * @return 响应内容
     */ 
    @SuppressWarnings("finally") 
    public static String sendSSLPostRequest(String reqURL, Map<String, String> params){ 
        long responseLength = 0;                         //响应长度 
        String responseContent = null;                   //响应内容 
        HttpClient httpClient = new DefaultHttpClient(); //创建默认的httpClient实例?
        X509TrustManager xtm = new X509TrustManager(){   //创建TrustManager?
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {} 
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {} 
            public X509Certificate[] getAcceptedIssuers() { return null; } 
        }; 
        try { 
            //TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext 
            SSLContext ctx = SSLContext.getInstance("SSL"); 
             
            //使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用?
            ctx.init(null, new TrustManager[]{xtm}, null); 
             
            //创建SSLSocketFactory?
            SSLSocketFactory socketFactory = new SSLSocketFactory(ctx); 
             
            //通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上?
            httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", socketFactory,443)); 
             
            HttpPost httpPost = new HttpPost(reqURL);                        //创建HttpPost
            List<NameValuePair> formParams = new ArrayList<NameValuePair>(); //构建POST请求的表单参数
            for(Map.Entry<String,String> entry : params.entrySet()){ 
                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue())); 
            } 
            httpPost.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8")); 
             
            HttpResponse response = httpClient.execute(httpPost); //执行POST请求
            HttpEntity entity = response.getEntity();             //获取响应实体
             
            if (null != entity) { 
                responseLength = entity.getContentLength(); 
                responseContent = EntityUtils.toString(entity, "UTF-8"); 
                //EntityUtils.consume(entity); //Consume response content
                //EntityUtils.
            } 
            //System.out.println("请求地址: " + httpPost.getURI()); 
            //System.out.println("响应状态: " + response.getStatusLine()); 
            //System.out.println("响应长度: " + responseLength); 
            //System.out.println("响应内容: " + responseContent); 
        } catch (KeyManagementException e) { 
            e.printStackTrace(); 
        } catch (NoSuchAlgorithmException e) { 
            e.printStackTrace(); 
        } catch (UnsupportedEncodingException e) { 
            e.printStackTrace(); 
        } catch (ClientProtocolException e) { 
            e.printStackTrace(); 
        } catch (ParseException e) { 
            e.printStackTrace(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } finally { 
            httpClient.getConnectionManager().shutdown(); //关闭连接,释放资源
            return responseContent; 
        } 
    } 


  /*  public void test2() throws InterruptedException, ExecutionException,
            IOException {
        CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
        // Start the client
        httpclient.start();
 
        // Execute 100 request in async
        final HttpGet request = new HttpGet(
                "http://xxxx");
        request.setHeader("Connection", "close");
        List<Future<HttpResponse>> respList = new LinkedList<Future<HttpResponse>>();
        for (int i = 0; i < 50; i++) {
            respList.add(httpclient.execute(request, null));
        }
 
        // Print response code
        for (Future<HttpResponse> response : respList) {
            response.get().getStatusLine();
            // System.out.println(response.get().getStatusLine());
        }
 
        httpclient.close();
        
        CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();// 默认的配置
        try {
         httpclient.start();
         HttpGet request = new HttpGet("http://www.apache.org/");
         Future<HttpResponse> future = httpclient.execute(request, null);
         HttpResponse response = future.get();// 获取结果
         System.out.println("Response: " + response.getStatusLine());
         System.out.println("Shutting down");
        } finally {
         httpclient.close();
        }
        System.out.println("Done");
       }
    }
    public void  AsyncClientHttpExchangeFutureCallback() {
    	  RequestConfig requestConfig = RequestConfig.custom()
    	    .setSocketTimeout(3000).setConnectTimeout(3000).build();
    	  CloseableHttpAsyncClient httpclient = HttpAsyncClients.custom()
    	    .setDefaultRequestConfig(requestConfig).build();
    	  try {
    	   httpclient.start();
    	   final HttpGet[] requests = new HttpGet[] {
    	     new HttpGet("http://www.apache.org/"),
    	     new HttpGet("https://www.verisign.com/"),
    	     new HttpGet("http://www.google.com/"),
    	     new HttpGet("http://www.baidu.com/") };
    	   final CountDownLatch latch = new CountDownLatch(requests.length);
    	   for (final HttpGet request : requests) {
    	    httpclient.execute(request, new FutureCallback<HttpResponse>() {
    	     //无论完成还是失败都调用countDown()
    	     @Override
    	     public void completed(final HttpResponse response) {
    	      latch.countDown();
    	      System.out.println(request.getRequestLine() + "->"
    	        + response.getStatusLine());
    	     }
    	     @Override
    	     public void failed(final Exception ex) {
    	      latch.countDown();
    	      System.out.println(request.getRequestLine() + "->" + ex);
    	     }
    	     @Override
    	     public void cancelled() {
    	      latch.countDown();
    	      System.out.println(request.getRequestLine()
    	        + " cancelled");
    	     }
    	    });
    	   }
    	   latch.await();
    	   System.out.println("Shutting down");
    	  } finally {
    	   httpclient.close();
    	  }
    	  System.out.println("Done");
    	 }*/
} 