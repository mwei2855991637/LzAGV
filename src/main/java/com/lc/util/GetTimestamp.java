package com.lc.util;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author Aimelony
 * @Date 2019/5/29 15:53
 * @File GetTimestamp
 * @Describe 得到时间戳
 **/
public class GetTimestamp {
    /**
     * 得到当前时间精确毫秒
     *
     * @return
     */
    public static String getTimestamp() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new Date());
        return format;
    }
    public static String getDateTimestamp() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(new Date());
        return format;
    }

    public static void main(String[] args) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssS");
        String format = simpleDateFormat.format(new Date());
        System.out.println(format);
    }
}
