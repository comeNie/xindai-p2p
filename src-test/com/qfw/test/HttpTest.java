package com.qfw.test;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.qfw.common.gateway.impl.HttpEndPointBS;
import com.qfw.common.util.DateUtils;
import com.qfw.common.util.MD5Utils;

public class HttpTest {
	public static void main(String[] args) {
		
		Map<String, String> param = new HashMap<String, String>();
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
		
		
		try {
			HttpEndPointBS.send("https://pay.ecpss.cn/sslpayment", param, null);
			
			
			List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
			Set<Map.Entry<String, String>> params = param.entrySet();
			for (Map.Entry<String, String> entry : params) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
				// p.setParameter(entry.getKey(), entry.getValue());
			}
			
			//Scheme http = new Scheme("http", PlainSocketFactory.getSocketFactory(), 80);
			
			TrustManager easyTrustManager = new X509TrustManager() {
				@Override
				public void checkClientTrusted(X509Certificate[] chain,
				String authType) throws CertificateException {
				// 哦，这很简单！
				}
				@Override
				public void checkServerTrusted(X509Certificate[] chain,
				String authType) throws CertificateException {
				//哦，这很简单！
				}
				@Override
				public X509Certificate[] getAcceptedIssuers() {
				return null;
				}
				};
				
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, new TrustManager[] { easyTrustManager }, null);
			SSLSocketFactory sf = new SSLSocketFactory(sslcontext);
			sf.setHostnameVerifier(SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
			Scheme https = new Scheme("https", sf, 443);
			SchemeRegistry sr = new SchemeRegistry();
			//sr.register(http);
			sr.register(https);
			
			DefaultHttpClient client = new DefaultHttpClient();
			
			client.getConnectionManager().getSchemeRegistry().register(https);
			
			UrlEncodedFormEntity entity1 = new UrlEncodedFormEntity(nvps, "UTF-8");
			
			
			HttpPost httppost = new HttpPost("https://pay.ecpss.cn/sslpayment");
			
			httppost.setEntity(entity1);
			
			//client.execute(httppost);
			HttpResponse response = client.execute(httppost);
			//System.out.println(response.getStatusLine().getStatusCode());
			
			HttpEntity entity = response.getEntity();
			String result = IOUtils.toString(entity.getContent(), entity
					.getContentEncoding() == null ? "UTF-8" : entity
					.getContentEncoding().getValue());
			
			//System.out.println(result);
			client.getConnectionManager().shutdown();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
