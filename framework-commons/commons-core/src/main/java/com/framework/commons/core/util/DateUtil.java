package com.framework.commons.core.util;

import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Slf4j
public class DateUtil {
    /**
     * 日期FORMAT格式  yyyy-MM-dd HH:mm:ss
     */
    public static final String DATA_FORMAT_TYPE_1 = "yyyy-MM-dd HH:mm:ss";
    /**
     * 日期FORMAT格式  yyyy-MM-dd
     */
    public static final String DATA_FORMAT_TYPE_2 = "yyyy-MM-dd";
    /**
     * 分秒FORMAT格式  :00:00
     */
    public static final String DATA_FORMAT_TYPE_MINUTE_SECOND = ":00:00";

    // 相隔的分钟数
    public static long getDistinceMinute(Date beforedate,
                                         Date afterdate) {
        long dayCount = 0;
        dayCount = (afterdate.getTime() - beforedate.getTime())
                / (60 * 1000);
        return dayCount;
    }

    /**
     * 将timestamp转换成date
     *
     * @param tt
     * @return
     */
    public static Date timestampToDate(Timestamp tt) {
        return new Date(tt.getTime());
    }

    /**
     * 将date转换成timestamp
     *
     * @param tt
     * @return
     */
    public static Timestamp dateToTimestamp(Date tt) {
        return new Timestamp(tt.getTime());
    }

    /**
     * 将日期转化成指定格式的字符串
     *
     * @param date 日期
     * @return String
     */
    public static String getDateTimeStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * getDateStr get a string with format YYYY-MM-DD from a Date object
     *
     * @param date date
     * @return String
     */
    public static String getDateStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * getDateStr get a string with format YYYY/MM/DD from a Date object
     *
     * @param date date
     * @return String
     */
    public static String getDateStr2(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        return format.format(date);
    }

    public static String getYear(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return format.format(date);
    }

    public static String getMonth(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM");
        return format.format(date);
    }

    public static String getDateStrC(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(date);
    }

    public static String getDateStrCompact(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String str = format.format(date);
        return str;
    }

    public static String getDateTimeStrC(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        return format.format(date);
    }

    public static String getCurDateStr(String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date());
    }

    /**
     * Parses text in 'YYYY-MM-DD' format to produce a date.
     *
     * @param s the text
     * @return Date
     * @throws ParseException
     */
    public static Date parseDate(String s) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parseDateC(String s) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        try {
            return format.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Parses text in 'YYYY-MM-DD' format to produce a date.
     *
     * @param s the text
     * @return Date
     * @throws ParseException
     */
    public static Date parseDateTime(String s) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return format.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parseDateTimeC(String s) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        try {
            return format.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parseDateTimeOfString(String s) {
        String ymd = s.substring(0, 10);
        String hour = s.substring(11, 13);
        String minute = s.substring(16, 18);
        String second = s.substring(21, 23);
        String strDate = ymd + " " + hour + ":" + minute + ":" + second;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            return format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static java.sql.Date getNextMonthFirstDate(java.sql.Date date) {
        Calendar scalendar = new GregorianCalendar();
        scalendar.setTime(date);
        scalendar.add(Calendar.MONTH, 1);
        scalendar.set(Calendar.DATE, 1);
        return new java.sql.Date(scalendar.getTime().getTime());
    }

    public static java.sql.Date getFrontDateByDayCount(java.sql.Date date,
                                                       int dayCount) {
        Calendar scalendar = new GregorianCalendar();
        scalendar.setTime(date);
        scalendar.add(Calendar.DATE, -dayCount);
        return new java.sql.Date(scalendar.getTime().getTime());
    }

    /**
     * Get first day of the month.
     *
     * @param year  the year
     * @param month the month
     * @return Date first day of the month.
     * @throws ParseException
     */
    public static Date getFirstDay(String year, String month) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(year + "-" + month + "-1");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getFirstDay(int year, int month) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(year + "-" + month + "-1");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getLastDay(String year, String month) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(year + "-" + month + "-1");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar scalendar = new GregorianCalendar();
        scalendar.setTime(date);
        scalendar.add(Calendar.MONTH, 1);
        scalendar.add(Calendar.DATE, -1);
        date = scalendar.getTime();
        return date;
    }

    public static Date getLastDay(int year, int month) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(year + "-" + month + "-1");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar scalendar = new GregorianCalendar();
        scalendar.setTime(date);
        scalendar.add(Calendar.MONTH, 1);
        scalendar.add(Calendar.DATE, -1);
        date = scalendar.getTime();
        return date;
    }

    /**
     * getToday get todat string with format YYYY-MM-DD from a Date object
     *
     * @return String
     */

    public static String getTodayStr() {
        Calendar calendar = Calendar.getInstance();
        return getDateStr(calendar.getTime());
    }

    public static Date getToday() {
        return new Date(System.currentTimeMillis());
    }

    public static String getTodayAndTime() {
        return new Timestamp(System.currentTimeMillis()).toString();
    }

    public static String getTodayC() {
        Calendar calendar = Calendar.getInstance();
        return getDateStrC(calendar.getTime());
    }

    public static int getThisYearMonth() {
        Calendar today = Calendar.getInstance();
        return (today.get(Calendar.YEAR) + 1900) * 100 + today.get(Calendar.MONTH) + 1;
    }

    public static int getYearMonth(Date date) {
        Calendar today = Calendar.getInstance();
        today.setTime(date);
        return (today.get(Calendar.YEAR) + 1900) * 100 + today.get(Calendar.MONTH) + 1;
    }

    // 获取相隔月数   
    public static long getDistinceMonth(String beforedate, String afterdate) {
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
        long monthCount = 0;
        try {
            Calendar d1 = Calendar.getInstance();
            d1.setTime(d.parse(beforedate));

            Calendar d2 = Calendar.getInstance();
            d2.setTime(d.parse(afterdate));
            monthCount = (d2.get(Calendar.YEAR) - d1.get(Calendar.YEAR)) * 12 + d2.get(Calendar.MONTH)
                    - d1.get(Calendar.MONTH);
        } catch (ParseException e) {
            log.info("Date parse error!");
            // throw e;   
        }
        return monthCount;
    }

    // 获取相隔天数   
    public static long getDistinceDay(String beforedate, String afterdate) {
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
        long dayCount = 0;
        try {
            Date d1 = d.parse(beforedate);
            Date d2 = d.parse(afterdate);

            dayCount = (d2.getTime() - d1.getTime()) / (24 * 60 * 60 * 1000);
        } catch (ParseException e) {
            log.info("Date parse error!");
            // throw e;   
        }
        return dayCount;
    }

    // 获取相隔天数   
    public static long getDistinceDay(Date beforedate, Date afterdate) {
        long dayCount = 0;

        try {
            dayCount = (afterdate.getTime() - beforedate.getTime())
                    / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            // log.info("Date parse error!");   
            // // throw e;   
        }
        return dayCount;
    }

    public static long getDistinceDay(java.sql.Date beforedate,
                                      java.sql.Date afterdate) {
        long dayCount = 0;

        try {
            dayCount = (afterdate.getTime() - beforedate.getTime())
                    / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            // log.info("Date parse error!");   
            // // throw e;   
        }
        return dayCount;
    }


    // 获取相隔天数   
    public static long getDistinceDay(String beforedate) {
        return getDistinceDay(beforedate, getTodayStr());
    }

    // 获取相隔时间数   
    public static long getDistinceTime(String beforeDateTime,
                                       String afterDateTime) {
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        long timeCount = 0;
        try {
            Date d1 = d.parse(beforeDateTime);
            Date d2 = d.parse(afterDateTime);

            timeCount = (d2.getTime() - d1.getTime()) / (60 * 60 * 1000);
        } catch (ParseException e) {
            log.info("Date parse error!");
        }
        return timeCount;
    }

    // 获取相隔时间数   
    public static long getDistinceTime(String beforeDateTime) {
        return getDistinceTime(beforeDateTime, new Timestamp(System.currentTimeMillis()).toLocaleString());
    }


    // 获取相隔分钟数   
    public static long getDistinceMinute(String beforeDateTime,
                                         String afterDateTime) {
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timeCount = 0;
        try {
            Date d1 = d.parse(beforeDateTime);
            Date d2 = d.parse(afterDateTime);

            timeCount = (d2.getTime() - d1.getTime()) / (60 * 1000);
        } catch (ParseException e) {
            log.info("Date parse error!");
        }
        return timeCount;
    }

    /**
     * @return 输入日期大于等于当前日期, 返回true, 否则返回 false;
     * @throws ParseException
     */
    public static boolean inputDateIsGreaterThanSysDate(String sysDate, String inputDate) {

        boolean checkFlg = false;

        Date sys = parseDate(sysDate);
        Date inp = parseDate(inputDate);

        if (inp.getTime() - sys.getTime() >= 86400000) {
            checkFlg = true;
        } else {
            checkFlg = false;
        }
        return checkFlg;
    }

    /**
     * 毫秒转日期字符串
     *
     * @param str
     * @return
     */
    public static String getDateTimeByMillisecond(String str) {

        Date date = new Date(Long.valueOf(str));

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        String time = format.format(date);

        return time;
    }

    /**
     * 获取精确到秒的时间戳
     *
     * @return
     */
    public static int getSecondTimestamp(Date date) {
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime());
        int length = timestamp.length();
        if (length > 3) {
            return Integer.valueOf(timestamp.substring(0, length - 3));
        } else {
            return 0;
        }
    }

    /**
     * 在指定日期上增加或减少分钟 *
     */
    public static Date addMinute(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.MINUTE, num);
        return startDT.getTime();
    }

    /**
     * 在指定日期上增加或减少小时 *
     */
    public static Date addHour(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.HOUR_OF_DAY, num);
        return startDT.getTime();
    }

    /**
     * 在指定日期上增加或减少天数 *
     */
    public static Date addDay(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DAY_OF_MONTH, num);
        return startDT.getTime();
    }

    /**
     * 在指定日期上增加或减少月数 *
     */
    public static Date addMonth(Date date, int month) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.MONTH, month);
        return startDT.getTime();
    }

    /**
     * 在指定日期上增加或减少年数 *
     */
    public static Date addYear(Date date, int year) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.YEAR, year);
        return startDT.getTime();
    }

    /**
     * 在指定日期上增加或减少秒数 *
     */
    public static Date addSeconds(Date date, int seconds) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.SECOND, seconds);
        return startDT.getTime();
    }

    /**
     * 获取指定日期是星期几
     *
     * @param date
     * @return 1-7分别对应星期一到星期日
     */
    public static int getWeekOfDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week = cal.get(Calendar.DAY_OF_WEEK);
        //星期天特殊处理
        if (week == 1) {
            return 7;
        }
        return week - 1;
    }

    /**
     * 获取当前时间 yyyy-MM-dd
     *
     * @return
     */
    public static Date getNowDateForYmd() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(DATA_FORMAT_TYPE_2);
        try {
            Date nowTime = format.parse(format.format(date));
            return nowTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析日期 yyyy-MM-dd
     *
     * @param date 日期字符串
     * @return
     */
    public static Timestamp parse(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Date) {
            return new Timestamp(((Date) object).getTime());
        }
        String date = StrUtil.asString(object);
        try {
            if (date.length() == 10) {
                return new Timestamp(parseDate(date).getTime());
            } else if (date.length() == 8) {
                DateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
                Date d = yyyyMMdd.parse(date);
                return new Timestamp(d.getTime());
            } else if (date.length() == 9) {
                if (date.matches("\\d{4}-\\d-\\d{2}")) {//yyyy-M-dd
                    DateFormat yyyyMdd = new SimpleDateFormat("yyyy-M-dd");
                    Date d = yyyyMdd.parse(date);
                    return new Timestamp(d.getTime());
                } else if (date.matches("\\d{4}-\\d{2}-\\d")) {//yyyy-MM-d
                    DateFormat yyyyMdd = new SimpleDateFormat("yyyy-MM-d");
                    Date d = yyyyMdd.parse(date);
                    return new Timestamp(d.getTime());
                }
            } else if (date.length() == 16) {//yyyy-MM-dd HH:mm
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date d = format.parse(date);
                return new Timestamp(d.getTime());
            } else if (date.length() == 19) {//yyyy-MM-dd HH:mm:ss
                return new Timestamp(parseDateTime(date).getTime());
            } else if (date.length() >= 20 && date.length() <= 23) {//yyyy-MM-dd HH:mm:ss.SSS
                if (date.matches("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}\\.\\d{1,3}")) {
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                    Date d = format.parse(date);
                    return new Timestamp(d.getTime());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
