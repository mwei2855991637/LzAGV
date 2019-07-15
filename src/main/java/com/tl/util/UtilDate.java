package com.tl.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 处理了一下系统的java.util.Date,重点在于日期的格式化
 * @author	lenovo
 * @date	To019-06-18
 */
public class UtilDate {
	private static final String FormatAll = "yyyy-MM-dd HH:mm:ss";
	private static final String FormatSimple = "yyyy-MM-dd";

	public static Long getDateTime() {
		return getSystemTime().getTime();
	}

	/**
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getAllDate() {
		return dateToLongString(getSystemTime());
	}

	/**
	 * @return yyyy-MM-dd
	 */
	public static String getSimpleDate() {
		return dateToShortString(getSystemTime());
	}

	public static String dateToLongString(Date date) {
		return formatDateToStr(FormatAll, date);
	}

	public static String dateToShortString(Date date) {
		return formatDateToStr(FormatSimple, date);
	}

	public static Date longStringToDate(String str) {
		return parseStrToDate(FormatAll, str);
	}

	public static Date shortStringToDate(String str) {
		return parseStrToDate(FormatSimple, str);
	}

	/**
	 * java.util.Date -> Format String
	 * 
	 * @param dataFormat
	 * @param date
	 * @return
	 */
	private static String formatDateToStr(String dateFormat, Date date) {
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
	private static Date parseStrToDate(String dateFormat, String dateVal) {
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

	/**
	* 禁止被初始化
	*/
	private UtilDate() {

	}
}
