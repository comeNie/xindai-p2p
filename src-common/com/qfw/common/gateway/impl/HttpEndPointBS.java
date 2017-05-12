package com.qfw.common.gateway.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.qfw.common.exception.BizException;

public class HttpEndPointBS {
	private static Logger log = Logger.getLogger(HttpEndPointBS.class);

	/**
	 * 最大连接数
	 */
	public final static int MAX_TOTAL_CONNECTIONS = 800;

	/**
	 * 获取连接的最大等待时间
	 */
	public final static int WAIT_TIMEOUT = 60000;

	/**
	 * 每个路由最大连接数
	 */
	public final static int MAX_ROUTE_CONNECTIONS = 400;

	/**
	 * 连接超时时间
	 */
	public final static int CONNECT_TIMEOUT = 10000;

	/**
	 * 读取超时时间
	 */
	public final static int READ_TIMEOUT = 10000;

	public static String send(String uri, Map<String, String> param,String encoding) throws BizException {

		try {
			if (encoding == null || encoding.isEmpty()) {
				encoding = HTTP.UTF_8;
			}
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(uri);

			HttpParams httpParams = new BasicHttpParams();
			ConnManagerParams.setMaxTotalConnections(httpParams,
					MAX_TOTAL_CONNECTIONS);
			// 设置获取连接的最大等待时间
			ConnManagerParams.setTimeout(httpParams, WAIT_TIMEOUT);
			// 设置每个路由最大连接数
			ConnPerRouteBean connPerRoute = new ConnPerRouteBean(
					MAX_ROUTE_CONNECTIONS);
			ConnManagerParams.setMaxConnectionsPerRoute(httpParams,
					connPerRoute);
			// 设置连接超时时间
			HttpConnectionParams.setConnectionTimeout(httpParams,
					CONNECT_TIMEOUT);
			// 设置读取超时时间
			HttpConnectionParams.setSoTimeout(httpParams, READ_TIMEOUT);
			

			httppost.setParams(httpParams);

			/*
			 * if(header!=null && !header.isEmpty()){ Set<Map.Entry<String,
			 * String>> headers = param.entrySet(); for (Map.Entry<String,
			 * String> hearder : headers) { httppost.setHeader(hearder.getKey(),
			 * hearder.getValue()); } }
			 */

			
			if (param != null && !param.isEmpty()) {

				List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
				Set<Map.Entry<String, String>> params = param.entrySet();
				for (Map.Entry<String, String> entry : params) {
					nvps.add(new BasicNameValuePair(entry.getKey(), entry
							.getValue()));
					// p.setParameter(entry.getKey(), entry.getValue());
				}

				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nvps, encoding);
				
				/*//System.out.println(entity.getContentType());
				//System.out.println(entity.getContentLength());
				//System.out.println(EntityUtils.getContentCharSet(entity));
				//System.out.println(EntityUtils.toString(entity));*/
				
				httppost.setEntity(entity);
				////System.out.println(httppost.getRequestLine());
			}
			//Scheme sch = new Scheme("https", SSLSocketFactory.getSocketFactory(), 443);
			//client.getConnectionManager().getSchemeRegistry().register(sch);
			HttpResponse response = client.execute(httppost);
			//System.out.println(response.getStatusLine().getStatusCode());
			HttpEntity entity = response.getEntity();
			String result = IOUtils.toString(entity.getContent(), entity
					.getContentEncoding() == null ? encoding : entity
					.getContentEncoding().getValue());
			client.getConnectionManager().shutdown();
			return result;
		} catch (ClientProtocolException e) {
			log.error("http发送失败", e);
			throw new BizException("http发送失败");
		} catch (UnsupportedEncodingException e) {
			log.error("http发送失败", e);
			throw new BizException("http发送失败");
		} catch (IOException e) {
			 
			log.error("http发送失败", e);
			throw new BizException("http发送失败");
		}
	}

}
