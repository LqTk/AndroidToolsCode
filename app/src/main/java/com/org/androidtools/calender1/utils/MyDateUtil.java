package com.org.androidtools.calender1.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by yp on 2018-05-10.
 */

public class MyDateUtil {
    public static final int YEAR = 0;
    public static final int MONTH_DAY = 1;
    public static final int TIME = 2;
    public static final int NOT_YEAR = 3;
    public static final int YEAR_MONTH_DAY = 4;
    public static final int NOT_SECONDS = 5;
    public static final int HOUR_SECONDS = 6;
    public static final int ALL = 7;
    public static final int YEAR_MONTH = 8;

    /**
     * @param date "yyyy-MM-dd HH:mm:ss"
     * @param type
     * @return
     */
    public static String getTiem(String date, int type) {
        if (date == null || date.isEmpty()) {
            return "";
        }

        String result = "";

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date timeDate = sdf.parse(date);
            SimpleDateFormat year = new SimpleDateFormat("yyyy");
            SimpleDateFormat monthAndDaySdf = new SimpleDateFormat("MM-dd");
            SimpleDateFormat timeSdf = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat notYearSdf = new SimpleDateFormat("MM-dd HH:mm:ss");
            SimpleDateFormat yearMonthDaySdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat notSecondsSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            SimpleDateFormat hourAndSeconds = new SimpleDateFormat("HH:mm");
            switch (type) {
                case YEAR:
                    result = year.format(timeDate);
                    break;
                case MONTH_DAY:
                    result = monthAndDaySdf.format(timeDate);
                    break;
                case TIME:
                    result = timeSdf.format(timeDate);
                    break;
                case NOT_YEAR:
                    result = notYearSdf.format(timeDate);
                    break;
                case YEAR_MONTH_DAY:
                    result = yearMonthDaySdf.format(timeDate);
                    break;
                case NOT_SECONDS:
                    result = notSecondsSdf.format(timeDate);
                    break;
                case HOUR_SECONDS:
                    result = hourAndSeconds.format(timeDate);
                    break;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param timeDate
     * @param type
     * @return
     */
    public static String getTiemByDate(Date timeDate, int type) {
        if (timeDate == null) {
            return "";
        }

        String result = "";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat monthAndDaySdf = new SimpleDateFormat("MM-dd");
        SimpleDateFormat timeSdf = new SimpleDateFormat("HH:mm:ss");
        timeSdf.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        SimpleDateFormat notYearSdf = new SimpleDateFormat("MM-dd HH:mm:ss");
        SimpleDateFormat yearMonthDaySdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat yearMonthSdf = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat notSecondsSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat hourAndSeconds = new SimpleDateFormat("HH:mm");
        switch (type) {
            case YEAR:
                result = year.format(timeDate);
                break;
            case MONTH_DAY:
                result = monthAndDaySdf.format(timeDate);
                break;
            case TIME:
                result = timeSdf.format(timeDate);
                break;
            case NOT_YEAR:
                result = notYearSdf.format(timeDate);
                break;
            case YEAR_MONTH_DAY:
                result = yearMonthDaySdf.format(timeDate);
                break;
            case NOT_SECONDS:
                result = notSecondsSdf.format(timeDate);
                break;
            case HOUR_SECONDS:
                result = hourAndSeconds.format(timeDate);
                break;
            case ALL:
                result = sdf.format(timeDate);
                break;
            case YEAR_MONTH:
                result = yearMonthSdf.format(timeDate);
                break;
        }

        return result;
    }

    /**
     * 取指定日期为星期几.
     *
     * @param strDate 指定日期
     *                指定日期格式 yyyy-MM-dd HH:mm
     * @return String 星期几
     */
    public static String getWeekNumber(String strDate) {
        return getWeekNumber(strDate, "yyyy-MM-dd HH:mm");
    }

    /**
     * 取指定日期为星期几.
     *
     * @param strDate  指定日期
     * @param inFormat 指定日期格式
     * @return String 星期几
     */
    public static String getWeekNumber(String strDate, String inFormat) {
        String week = "周日";
        Calendar calendar = new GregorianCalendar();
        DateFormat df = new SimpleDateFormat(inFormat);
        try {
            calendar.setTime(df.parse(strDate));
        } catch (Exception e) {
            return "错误";
        }
        int intTemp = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        switch (intTemp) {
            case 0:
                week = "周日";
                break;
            case 1:
                week = "周一";
                break;
            case 2:
                week = "周二";
                break;
            case 3:
                week = "周三";
                break;
            case 4:
                week = "周四";
                break;
            case 5:
                week = "周五";
                break;
            case 6:
                week = "周六";
                break;
        }
        return week;
    }

    /**
     * 字符串转毫秒数
     *
     * @return
     */
    public static long getMsByString(String str) {
        if (str == null || str.isEmpty()) {
            return 0;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = 0;
        try {
            Date timeDate = sdf.parse(str);
            time = timeDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 获取当前日期和时间字符串
     *
     * @return
     */
    public static String getNowDateAndTimeString() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 获取当前日期字符串
     *
     * @return
     */
    public static String getNowDateString() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String getHMSbySeconds(long seconds){
        if (seconds > 0) {
            int second = (int)seconds % 60;
            int totalMins = (int)seconds / 60;
            int mins = totalMins % 60;
            int hour = totalMins / 60;

            return getPrettyFormat(hour) + ":" + getPrettyFormat(mins) + ":" + getPrettyFormat(second);
        }
        return "00:00:00";
    }

    /**
     * 根据秒数得到XX时XX分XX秒
     * @param seconds
     * @return
     */
    public static String getHMSUnitBySeconds(long seconds){
        if (seconds > 0) {
            int second = (int)seconds % 60;
            int totalMins = (int)seconds / 60;
            int mins = totalMins % 60;
            int hour = totalMins / 60;

            if(hour > 0){
                return getPrettyFormat(hour) + "时" + getPrettyFormat(mins) + "分" + getPrettyFormat(second) + "秒";
            }else{
                return getPrettyFormat(mins) + "分" + getPrettyFormat(second) + "秒";
            }
        }
        return "00分00秒";
    }

    private static String getPrettyFormat(int num) {
        return num < 10 ? "0" + num : String.valueOf(num);
    }

    public static Integer getDayByDate(String str){
        int day = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date timeDate = sdf.parse(str);
            day = timeDate.getDate();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }

    /**
     * 月份增减
     */
    public static String monthChange(int change, String month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(str2Date(month));
        calendar.add(Calendar.MONTH, change);
        return getMonthStr(calendar.getTime());
    }

    /**
     * 获取月份标题
     */
    public static String getMonthStr(Date month) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        return df.format(month);
    }

    public static Date str2Date(String str) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
            return df.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
