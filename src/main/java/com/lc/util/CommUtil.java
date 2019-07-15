package com.lc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommUtil {
	// ******************************************** 日期格式化 ****************************************************
	private static final String FormatAll = "yyyy-MM-dd HH:mm:ss";
	private static final String FormatSimple = "yyyy-MM-dd";

	public static Long getDateTime() {
		return getSystemTime().getTime();
	}

	/**
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getAllDate() {
		return date2LongString(getSystemTime());
	}

	/**
	 * @return yyyy-MM-dd
	 */
	public static String getSimpleDate() {
		return date2ShortString(getSystemTime());
	}

	public static String date2LongString(Date date) {
		return formatDate2Str(FormatAll, date);
	}

	public static String date2ShortString(Date date) {
		return formatDate2Str(FormatSimple, date);
	}

	public static Date longString2Date(String str) {
		return parseStr2Date(FormatAll, str);
	}

	public static Date shortString2Date(String str) {
		return parseStr2Date(FormatSimple, str);
	}

	/**
	 * java.util.Date -> Format String
	 * 
	 * @param dataFormat
	 * @param date
	 * @return
	 */
	private static String formatDate2Str(String dateFormat, Date date) {
		try {
			return new SimpleDateFormat(dateFormat).format(date);
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * Format String -> java.util.Date
	 * 
	 * @param dateFormat
	 * @param dateVal
	 * @return
	 */
	private static Date parseStr2Date(String dateFormat, String dateVal) {
		try {
			return new SimpleDateFormat(dateFormat).parse(dateVal);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @return now system time
	 */
	private static Date getSystemTime() {
		return new Date();
	}

	// ******************************************** json <-> object ***************************************************
	/**
	 * obj -> json String
	 * 
	 * @param object
	 * @returnw
	 */
	public static <T> String objToJson(T object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * jsonString -> T (use Generics)
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static <T> T jsonToObj(String jsonStr, Class<T> clazz) {
		try {
			return mapper.readValue(jsonStr, clazz);
		} catch (Exception e) {
			System.err.println("try to turn to " + clazz + "\njsonString : " + jsonStr);
			e.printStackTrace();
			return null;
		}
	}

	// 常用的json转object的函数
	public static HashMap<?, ?> jsonToMap(String jsonStr) {
		return jsonToObj(jsonStr, HashMap.class);
	}

	public static String[] jsonToStrOne(String jsonStr) {
		return jsonToObj(jsonStr, String[].class);
	}

	public static String[][] jsonToStrTwo(String jsonStr) {
		return jsonToObj(jsonStr, String[][].class);
	}

	private static final ObjectMapper mapper = getMapper();

	private static ObjectMapper getMapper() {
		// 获取 mapper， 设置属性，空值不被格式化
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		return mapper;
	}

	// ******************************************** http 请求 ****************************************************
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
		String postBody = requestBodyStr instanceof String ? (String) requestBodyStr : objToJson(requestBodyStr);
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

	// *************************************** 处理配置文件中的数据 ****************************************************
	/**
	 * read properties file
	 */
	// private static ResourceBundle resource = ResourceBundle.getBundle("config");

	/**
	 * 获取配置文件中，key 对应的值
	 * 
	 * @param str
	 * @return
	 */
//	public static String getPropertyByKey(String str) {
//		return resource.getString(str);
//	}

	/**
	 * 根据配置文件中的 debug = true/false 追加key 前缀，处理发布和本地测试中不同的属性值
	 * 
	 * @return
	 */
//	public static String getPropertyPreSuffix() {
//		return Boolean.valueOf(resource.getString("debug")) ? "dev." : "run.";
//	}

	// ******************************************** cookie操作****************************************************

	public static boolean setCookie(HttpServletResponse response, String cookieName, String cookieValue) {
		return setCookie(response, cookieName, cookieValue, "/", 60 * 50 * 24 * 30);
	}

	public static boolean setCookie(HttpServletResponse response, String cookieName, String cookieValue, String path, Integer maxAge) {
		Cookie cookie = new Cookie(cookieName, cookieValue);// 创建新cookie
		cookie.setMaxAge(maxAge);// 设置存在时间为30天，单位：秒
		// cookie.setPath("/");// 设置作用域
		cookie.setPath(path);
		response.addCookie(cookie);// 将cookie添加到response的cookie数组中返回给客户端
		return true;
	}

	// 使用springMVC的注解 @CookieValue，直接读取对应的cookie
	/*	@RequestMapping("test")
		@ResponseBody
		public String test(@CookieValue String sizeInPage) {
			return sizeInPage;
		}*/
	public static String readCookie(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();// 根据请求数据，找到cookie数组
		if (cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(cookieName)) {
					return cookie.getValue();
				}
			}
		}
		return "";
	}
}
