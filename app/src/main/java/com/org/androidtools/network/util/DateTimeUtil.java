package com.org.androidtools.network.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yp on 2018-05-18.
 */

public class DateTimeUtil {
    /**
     * 根据默认的样式以及默认的环境，获取当前的时间
     * @return 当前的时间
     */
    public static String getCurrentTime(){
        return getCurrentDateTimeByFormat(getTimeFormat());
    }

    /**
     * 根据给定的格式化器，获取当前的日期时间
     * @param fromat 给定的格式化器
     * @return 当前的日期时间
     */
    public static String getCurrentDateTimeByFormat(DateFormat fromat){
        return fromat.format(new Date());
    }

    /**
     * 根据默认的样式以及默认的环境，获取一个时间格式化器
     * @return 时间格式化器
     */
    public static DateFormat getTimeFormat(){
        return DateFormat.getTimeInstance(DateFormat.DEFAULT, Locale.getDefault());
    }
    /**
     * 获取一个自定义格式的日期时间格式化器
     * @param customFormat 给定的自定义格式，例如："yyyy-MM-dd hh:mm:ss"
     * @return 日期时间格式化器
     */
    public static DateFormat getDateTimeFormatByCustom(String customFormat){
        return new SimpleDateFormat(customFormat, Locale.getDefault());
    }
    /**
     * 获取一个默认格式（yyyy-MM-dd）的日期格式化器
     * @return 日期格式化器
     */
    public static DateFormat getDateFormatByDefult(){
        return getDateTimeFormatByCustom("yyyy-MM-dd");
    }

    /**
     * 获取给定的日期中的年份
     * @param date 给定的日期
     * @return 年份
     */
    public static int getYear(Date date){
        return Integer.valueOf(getYearFormat().format(date));
    }
    /* **************************************获取其它具体的Format********************************* */
    /**
     * 获取年份格式化器
     * @return 年份格式化器
     */
    public static DateFormat getYearFormat(){
        return getDateTimeFormatByCustom("yyyy");
    }
}
