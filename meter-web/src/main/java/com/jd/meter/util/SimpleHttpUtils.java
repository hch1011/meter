package com.jd.meter.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jd.meter.exception.MeterExceptionFactory;

public class SimpleHttpUtils {
	private static final Logger logger = LoggerFactory.getLogger(SimpleHttpUtils.class);
	public static class SimpleHttpResponse {
		private int status;
		private String body;
		private Exception e;

		public SimpleHttpResponse() {
		}

		public String toString(){
			if(e == null){
				return "status=" + status + "\nbody=" + body;
			}

			return "status=" + status +"\nexception="+ e.getMessage() + "\nbody=" + body;
		}

		public SimpleHttpResponse(int status, String body) {
			this.status = status;
			this.body = body;
		}

		public String getBody() {
			return body;
		}

		public void setBody(String body) {
			this.body = body;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public Exception getE() {
			return e;
		}

		public void setE(Exception e) {
			this.e = e;
		}
	}

	/**
	 * 通过HttpClient发送get请求，并返回response的文本内容和状态码status code
	 *
	 * @param url
	 * @param header
	 * @return
	 */
	public static SimpleHttpResponse doGet(String url, Map<String, String> param, Map<String, String> header) {
		HttpClient httpClient =HttpClients.createDefault();// new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(rebuildUrl(url, param));

		// header
		if (header != null && header.size() > 0) {
			for (String name : header.keySet()) {
				httpGet.addHeader(name, header.get(name));
			}
		}

		SimpleHttpResponse result = new SimpleHttpResponse();
		try {
			HttpResponse response = httpClient.execute(httpGet);
			String output = EntityUtils.toString(response.getEntity(),"UTF-8");
			result.setBody(output);
			result.setStatus(response.getStatusLine().getStatusCode());
		} catch (Exception e) {
			result.setStatus(-1);
			result.setBody(e.getMessage());
			result.setE(e);
			logger.error("http doGet error, url:"+url, e);
		} finally {
			HttpClientUtils.closeQuietly(httpClient);
		}
		return result;
	}
	
	/**
	 * 获取url
	 * @param picUrl
	 * @return
	 */
	public static byte[] getByteByUrl(String picUrl) {
		HttpClient httpClient =HttpClients.createDefault();// new DefaultHttpClient();
		try {
			HttpGet httpGet = new HttpGet(picUrl);
			HttpResponse response = httpClient.execute(httpGet);
			return EntityUtils.toByteArray(response.getEntity());
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
			throw MeterExceptionFactory.applicationException("获取内容错误"+e.getMessage(), e);
		} finally {
			HttpClientUtils.closeQuietly(httpClient);
		}
	}

	/**
	 * 保存url内容到文件
	 * @param picUrl
	 * @param filePath：c:\\163.html
	 * @return
	 */
	public static boolean saveToFile(String picUrl, String filePath) {
		try { 
			FileUtils.copyURLToFile(new URL(picUrl) , new File(filePath), 10 * 1000,  30 * 1000);
		} catch (MalformedURLException e1) {
			logger.info(e1.getMessage(),e1);
			throw MeterExceptionFactory.applicationException("URL格式不对："+e1.getMessage(), e1);
		} catch (IOException e2) {
			logger.info(e2.getMessage(),e2);
			throw MeterExceptionFactory.applicationException("获取内容错误："+e2.getMessage(), e2);
		} 
		return true;
	}
	
	public static SimpleHttpResponse doDelete(String url, Map<String, String> param, Map<String, String> header) {
		logger.info("http client get started, url: " + url);
		HttpClient httpClient =HttpClients.createDefault();
		HttpDelete httpMethod = new HttpDelete(rebuildUrl(url, param));

		// header
		if (header != null & header.size() > 0) {
			for (String name : header.keySet()) {
				httpMethod.addHeader(name, header.get(name));
			}
		}

		SimpleHttpResponse result = new SimpleHttpResponse();
		try {
			HttpResponse response = httpClient.execute(httpMethod);
			String output = EntityUtils.toString(response.getEntity());
			result.setBody(output);
			result.setStatus(response.getStatusLine().getStatusCode());
		} catch (Exception e) {
			result.setStatus(-1);
			result.setBody(e.getMessage());
			result.setE(e);
			logger.error("http doGet error, url:"+url, e);
		} finally {
			HttpClientUtils.closeQuietly(httpClient);
		}
		return result;
	}

	/**
	 * 通过HttpClient发送post请求，并返回response的文本内容
	 *
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */

	public static SimpleHttpResponse doPost(String url, Map<String, String> params, Map<String, String> header) {
		logger.debug("http client post started");
		SimpleHttpResponse result = new SimpleHttpResponse();
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);

		try {
			// header
			if (header != null & header.size() > 0) {
				for (String name : header.keySet()) {
					httpPost.addHeader(name, header.get(name));
				}
			}

			// params
			if (params != null && params.size() > 0) {
				List<NameValuePair> formParams = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> e : params.entrySet()) {
					formParams.add(new BasicNameValuePair(e.getKey(), e.getValue()));
				}

				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams,"UTF-8");
				httpPost.setEntity(entity);
			}

			// execute
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			String output = EntityUtils.toString(entity);

			result.setStatus(response.getStatusLine().getStatusCode());
			result.setBody(output);
		} catch (IOException e) {
			result.setStatus(-1);
			result.setBody(e.getMessage());
			result.setE(e);
			logger.error("http doGet error, url:"+url, e);
		} finally {
			HttpClientUtils.closeQuietly(httpClient);
		}
		return result;
	}


	public static String rebuildUrl(String url, Map<String, String> params) {
		if (params == null || params.size() == 0) {
			return url;
		}

		StringBuffer sb = new StringBuffer(url);
		if (url.indexOf("?") < 0) {
			sb.append("?");
		}else if(url.lastIndexOf("?") < url.length()-1){
			sb.append("&");
		}

		String value = null;
		for (String key : params.keySet()) {
			value = params.get(key);
			if (StringUtils.isNotBlank(value)) {
				sb.append(key).append("=").append(value).append("&");
			}
		}
		url = sb.deleteCharAt(sb.length() - 1).toString();
		return url;
	}
	
	public static void main(String[] args) {
		System.out.println("main start");
		SimpleHttpUtils.saveToFile("http://www.163.com/", "c:\\163.html");
		System.out.println("main end");
	}
}
