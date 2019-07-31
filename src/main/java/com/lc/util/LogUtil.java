package com.lc.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author MWEI E-mail: 574185505@qq.com
 * @version 2019年7月31日 上午9:32:24 Class Explain:输出日志文件
 */
public class LogUtil {
	/**
	 * @param path 
	 * path:保存日志文件路径 
	 * @param content
	 * content：日志内容     
	 */
	public static void writeFile(String path, String content) {
		File writefile;
		try {
			// 通过这个对象来判断是否向文本文件中追加内容
			// boolean addStr = append;
			writefile = new File(path);
			// 如果文本文件不存在则创建它
			if (!writefile.exists()) {
				writefile.createNewFile();
				writefile = new File(path); // 重新实例化
			}
			FileOutputStream fw = new FileOutputStream(writefile, true);
			Writer out = new OutputStreamWriter(fw, "utf-8");
			out.write(content);
			String newline = System.getProperty("line.separator");
			//写入换行  
			out.write(newline);
			out.close();
			fw.flush();
			fw.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	// 获取当前时间
	public static String getCurrentYYYYMMDDHHMMSS() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		Date currTime = new Date();
		String thisTime = new String(formatter.format(currTime));
		return thisTime;
	}

	public static void main(String[] args) {
		String logpath="E:/admin.log";
		String content = LogUtil.getCurrentYYYYMMDDHHMMSS() + " ****************************************进件处理开始****************************************";
		writeFile(logpath, content);
		content = LogUtil.getCurrentYYYYMMDDHHMMSS() + " 生成.spl的路劲为:" ;
		writeFile(logpath, content);
		writeFile(logpath,"dftyuiop[");
		writeFile(logpath,"dftyuiop[");
		
	}
}
