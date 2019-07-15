package com.tl.util;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 使用了Jackson包，封装部分习惯使用方法
 * @author	lenovo
 * @date	2019-06-18
 */
public class UtilJson {
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

	/**
	* 禁止被初始化
	*/
	private UtilJson() {

	}
}
