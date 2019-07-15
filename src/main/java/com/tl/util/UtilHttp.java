package com.tl.util;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * 对httpclient包中的http访问做了一定的封装
 * @author	lenovo
 * @date	2019-06-18
 */
public class UtilHttp {
	/**
	 * http 的get请求
	 * 
	 * @param url
	 * @return jsonString
	 */
	public static String doGet(String url) {
		return doHttp(url, "get", null, "utf-8");
	}

	/**
	 * http 的post 请求
	 * 
	 * @param url
	 * @param requestBodyStr
	 * @return
	 */
	public static String doPost(String url, String requestBodyStr) {
		return doHttp(url, "post", requestBodyStr, "utf-8");
	}

	public static String doPost(String url, Object requestBodyStr) {
		String postBody = requestBodyStr instanceof String ? (String) requestBodyStr : UtilJson.objToJson(requestBodyStr);
		return doHttp(url, "post", postBody, "utf-8");
	}

	public static String doHttp(String url, String postBody) {
		return postBody == null ? doGet(url) : doPost(url, postBody);
	}

	public static String doHttp(String url, Object postBody) {
		return postBody == null ? doGet(url) : doPost(url, postBody);
	}

	/**
	 * http 底层实现
	 * 
	 * @param url
	 * @param methodType
	 * @param str        requestBody
	 * @param charset
	 * @return
	 */
	private static String doHttp(String url, String methodType, String str, String charset) {
		// create Httpclient
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		HttpUriRequest request = null;
		String result = "";
		try {
			if (methodType.equalsIgnoreCase("get")) {
				request = new HttpGet(url);
			} else {
				HttpPost post = new HttpPost(url);
				post.setEntity(new StringEntity(str, charset));
				request = post;
			}
			response = httpClient.execute(request);
			result = EntityUtils.toString(response.getEntity(), charset);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (Exception e2) {
			}
		}
		return result;
	}

	/**
	 * 禁止被初始化
	 */
	private UtilHttp() {

	}
}
