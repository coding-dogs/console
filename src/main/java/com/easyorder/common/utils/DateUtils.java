package com.easyorder.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.util.StringUtils;

import com.easyorder.common.exceptions.InternalServerException;

/**
 * @Project : spring-boot-sample
 * @Program Name : com.ljt.springboot.common.utils.DateUtils.java
 * @Description : 日期工具类
 * @Author : wangchao
 * @Creation Date : 2016-3-8 上午11:16:53
 * @ModificationHistory Who When What ---------- ------------- -----------------------------------
 *                      wangchao 2016-3-8 create
 */
public abstract class DateUtils {

  /**
   * 日期格式
   */
  public static final String DATE_FORMAT = "DATE_FORMAT";
  public static final String DATE = "yyyy-MM-dd";
  public static final String DATE_HH_MM = "yyyy-MM-dd HH:mm";
  public static final String DATE_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
  public static final String DATE_HH_MM_SS_S = "yyyy-MM-dd HH:mm:ss S";
  public static final String DATE_HH_MM_SS_Z1 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
  public static final String DATE_HH_MM_SS_Z2 = "yyyy-MM-dd'T'HH:mm:ssZ";
  public static final String DATE_HH_MM_SS_Z3 = "yyyy-MM-dd'T'HH:mm:ssz";
  public static final String DATE_HH_MM_SS_Z4 = "yyyy-MM-dd'T'HH:mm:ss";
  public static final String DATE_HH_MM_SS_A = "MM/dd/yyyy HH:mm:ss a";
  public static final String DATE_HHMMSS = "yyyyMMddHHmmss";

  private static List<DateFormat> formats = new ArrayList<>();

  private DateUtils() {}

  static {
    /** alternative formats */
    formats.add(new SimpleDateFormat(DATE_HH_MM_SS));
    formats.add(new SimpleDateFormat(DATE_HH_MM));
    formats.add(new SimpleDateFormat(DATE));
    /** ISO formats */
    formats.add(new SimpleDateFormat(DATE_HH_MM_SS_Z1));
    formats.add(new SimpleDateFormat(DATE_HH_MM_SS_Z2));
    formats.add(new SimpleDateFormat(DATE_HH_MM_SS_Z3));
    formats.add(DateFormat.getDateTimeInstance());
    /** XPDL examples format */
    formats.add(new SimpleDateFormat(DATE_HH_MM_SS_A, Locale.US));
    formats.add(new SimpleDateFormat(DATE_HHMMSS));
    /** Only date, no time */
    formats.add(DateFormat.getDateInstance());
  }

  /**
   * 字符串转化成日期
   */
  public static synchronized Date parse(String dateString) throws ParseException {
    if (!StringUtils.hasLength(dateString)) {
      return null;
    }

    for (DateFormat format : formats) {
      return format.parse(dateString);
    }
    return null;
  }

  /**
   * 根据指定格式将字符串转化成日期
   */
  public static Date parseByPattern(String dateString, String pattern) throws ParseException {
    if (!StringUtils.hasLength(dateString)) {
      return null;
    }

    DateFormat format = new SimpleDateFormat(pattern);
    return format.parse(dateString);
  }

  /**
   * 日期类型转字符串 yyyy-MM-dd
   */
  public static String format(Date date) {
    if (date == null) {
      return null;
    }

    SimpleDateFormat format = new SimpleDateFormat(DATE);
    return format.format(date);
  }

  /**
   * 日期类型转字符串
   */
  public static String format(Date date, String pattern) {
    if (date == null) {
      return null;
    }

    return DateFormatUtils.format(date, pattern);
  }

  public static Date getCurrDateNoTime() {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.HOUR, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    cal.set(Calendar.AM_PM, Calendar.AM);
    return cal.getTime();
  }

  /**
   * 将某个时间范围按天进行切分，未满一天的按一天算
   */
  public static List<Date> splitByDay(Date startDate, Date endDate) throws ParseException {
    List<Date> dayList = new ArrayList<>();
    String startDateStr = DateFormatUtils.format(startDate, DATE);
    Date startDate1 = DateUtils.parse(startDateStr);

    Calendar startCal = Calendar.getInstance();
    startCal.setTime(startDate1);
    Calendar endCal = Calendar.getInstance();
    endCal.setTime(endDate);
    Calendar tempCal = Calendar.getInstance();
    tempCal.setTime(startDate1);
    tempCal.add(Calendar.DAY_OF_MONTH, 1);

    while (tempCal.before(endCal)) {
      dayList.add(startCal.getTime());
      startCal.add(Calendar.DAY_OF_MONTH, 1);
      tempCal.add(Calendar.DAY_OF_MONTH, 1);
    }
    dayList.add(startCal.getTime());
    return dayList;
  }

  /**
   * 判断两个时间是否在同一天内
   */
  public static boolean isSameDay(Date date1, Date date2) {
    String date1Str = DateFormatUtils.format(date1, DATE);
    String date2Str = DateFormatUtils.format(date2, DATE);
    if (date1Str.equals(date2Str)) {
      return true;
    }
    return false;
  }

  /**
   * 判断两个时间是否在同一个月内
   */
  public static boolean isSameMonth(Date date1, Date date2) {
    Calendar cal1 = Calendar.getInstance();
    cal1.setTime(date1);
    Calendar cal2 = Calendar.getInstance();
    cal2.setTime(date2);
    if (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) {
      return true;
    }
    return false;
  }

  /**
   * 判断两个时间是否在同一季度里
   */
  public static boolean isSameQuarter(Date date1, Date date2) {
    Calendar cal1 = Calendar.getInstance();
    cal1.setTime(date1);
    Calendar cal2 = Calendar.getInstance();
    cal2.setTime(date2);
    int month1 = cal1.get(Calendar.MONTH);
    int month2 = cal2.get(Calendar.MONTH);
    if (((month1 >= Calendar.JANUARY && month1 <= Calendar.MARCH)
        && (month2 >= Calendar.JANUARY && month2 <= Calendar.MARCH))
        || ((month1 >= Calendar.APRIL && month1 <= Calendar.JUNE)
            && (month2 >= Calendar.APRIL && month2 <= Calendar.JUNE))
        || ((month1 >= Calendar.JULY && month1 <= Calendar.SEPTEMBER)
            && (month2 >= Calendar.JULY && month2 <= Calendar.SEPTEMBER))
        || ((month1 >= Calendar.OCTOBER && month1 <= Calendar.DECEMBER)
            && (month2 >= Calendar.OCTOBER && month2 <= Calendar.DECEMBER))) {
      return true;
    }
    return false;
  }

  /**
   * 得到两个时间的差额
   */
  public static long betDate(Date date, Date otherDate) {
    return date.getTime() - otherDate.getTime();
  }

  /**
   * 获取当前日期
   */
  public static long getCurrentTime() {
    return Calendar.getInstance().getTimeInMillis();
  }

  /**
   * 获取当前日期
   */
  public static Date getCurrentDate() {
    return new Date();
  }

  /**
   * 获取当前日期
   */
  public static Calendar getCurrentCalendar() {
    return Calendar.getInstance();
  }

  public static String calendarToString(Calendar calendar, String template) {
    String stringCalendar = template;
    stringCalendar = stringCalendar.replace("{year}", String.valueOf(calendar.get(Calendar.YEAR)));
    stringCalendar =
        stringCalendar.replace("{month}", String.valueOf(calendar.get(Calendar.MONTH)));
    stringCalendar = stringCalendar.replace("{date}", String.valueOf(calendar.get(Calendar.DATE)));
    stringCalendar =
        stringCalendar.replace("{hour}", String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));
    stringCalendar =
        stringCalendar.replace("{minute}", String.valueOf(calendar.get(Calendar.MINUTE)));
    stringCalendar =
        stringCalendar.replace("{second}", String.valueOf(calendar.get(Calendar.SECOND)));
    stringCalendar =
        stringCalendar.replace("{millisecond}", String.valueOf(calendar.get(Calendar.MILLISECOND)));
    return stringCalendar;
  }

  /**
   * 比较时间差 1小时内的显示：** 分钟前，例如：25 分钟前 1小时前（１天内的）：今天 **时：**分，例如： 08：17 1天前的（当前年）：*月*号 **时：**分，例如：05-09
   * 23：58 当前年之前的：年－月－日 **时：**分，例如：2009-09-26 16：33
   **/
  public static String timeCompare(Date inow, Date idate) {
    Calendar now = Calendar.getInstance();
    now.setTime(inow);
    Calendar date = Calendar.getInstance();
    date.setTime(idate);

    int nowY = now.get(Calendar.YEAR);
    int dateY = date.get(Calendar.YEAR);

    int nowM = now.get(Calendar.MONTH);
    int dateM = date.get(Calendar.MONTH);

    int nowD = now.get(Calendar.DAY_OF_MONTH);
    int dateD = date.get(Calendar.DAY_OF_MONTH);

    long l = now.getTimeInMillis() - date.getTimeInMillis();
    long m = (long) nowM - dateM;
    long day = (long) nowD - dateD;

    int dateHour = date.get(Calendar.HOUR_OF_DAY);
    int dateMinutes = date.get(Calendar.MINUTE);

    long hour = l / (60 * 60 * 1000) - day * 24;
    long min = (l / (60 * 1000)) - day * 24 * 60 - hour * 60;
    long y = (long) nowY - dateY;
    String ret = "";
    if (y > 0)// 大于一年的
      ret += (dateY + 1900) + "-";
    if (day > 0 || y > 0 || m > 0) {// 大于一天的
      if (dateM + 1 < 10)
        ret += "0";
      ret += (dateM + 1) + "-";
      if (dateD < 10)
        ret += "0";
      ret += dateD + " ";
    }
    if (hour > 0 || day > 0 || y > 0 || m > 0) {// 大于一小时
      if (dateHour < 10)
        ret += "0";
      ret += dateHour + ":";
      if (dateMinutes < 10)
        ret += "0";
      ret += Integer.toString(dateMinutes);
    }
    if (y == 0 && (day * 24 + hour) == 0 && min != 0)
      ret = min + " 分前";
    if (y == 0 && (day * 24 + hour) == 0 && min == 0)
      ret = "1  分前";
    return ret;
  }

  public static long getFormatedTime(long time) {
    return getFormatedTime(new Date(time));
  }

  public static long getFormatedTime(Date date) {
    if (date == null) {
      return 0;
    }
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    return Long.parseLong(dateFormat.format(date));
  }

  /**
   * @description 给定时间差解析成中文表示(只计算过去的某个时间和当前时间的差)
   * @param deltaMill 时差毫秒表示
   */
  public static String subtractParse(long deltaMillis, String format) {
    if (deltaMillis <= 0) {
      return "";
    } else {
      StringBuilder res = new StringBuilder();
      long day = deltaMillis / (24 * 60 * 60 * 1000);
      long hour = deltaMillis / (60 * 60 * 1000) - day * 24;
      long min = (deltaMillis / (60 * 1000)) - day * 24 * 60 - hour * 60;
      long sec = deltaMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60;
      if (day > 0 && format.contains("d")) {
        res.append(day + "天");
      }
      if (hour > 0 && format.contains("H")) {
        res.append(hour + "小时");
      }
      if (min > 0 && format.contains("m")) {
        res.append(min + "分钟");
      }
      if (sec > 0 && format.contains("s")) {
        res.append(sec + "秒");
      }
      return res.toString();
    }
  }

  /**
   * @description 解析过去某个时间和当前时间的差的中文表示
   *              <p>
   *              如"2小时32分"
   *              </p>
   * @param oldTime 过去的某个时间
   * @param format 格式化方式 。 如dHms表示 xx天xx小时xx分钟xx秒
   */
  public static String subtractParse(Date oldTime, String format) {
    Date current = getCurrentDate();
    if (oldTime.compareTo(current) <= 0) {
      return subtractParse(betDate(current, oldTime), format);
    }
    return "";
  }

  /**
   * @Description : 根据时区显示时间
   * @Method_Name : parseByTimeZone
   * @param date
   * @param timezone
   * @return : String
   * @Creation Date : 2014-8-4 下午2:38:40
   * @Author : mango_zhs@163.com 周松
   */
  public static String parseByTimeZone(Date date, String timezone) {
    if (date == null) {
      return null;
    }
    SimpleDateFormat sdf = new SimpleDateFormat(DATE);
    sdf.setTimeZone(TimeZone.getTimeZone(timezone));
    return sdf.format(date);
  }

  /**
   * @Description : 根据时区、格式显示时间
   * @Method_Name : parseByTimeZone
   * @param date
   * @param timezone
   * @param pattern
   * @return : String
   * @Creation Date : 2014-8-4 下午2:38:56
   * @Author : mango_zhs@163.com 周松
   */
  public static String parseByTimeZone(Date date, String timezone, String pattern) {
    if (date == null) {
      return null;
    }
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    sdf.setTimeZone(TimeZone.getTimeZone(timezone));
    return sdf.format(date);

  }

  /**
   * @Description : 获取当前月份
   * @return : String
   * @Creation Date : 2016年3月18日 上午11:40:01
   * @Author : wangliyi
   */
  public static String getCurrentMonth() {
    Calendar calendar = Calendar.getInstance(Locale.getDefault());
    int month = calendar.get(Calendar.MONTH) + 1;
    return (month >= 10) ? "" + Integer.toString(month) : "0" + month;
  }

  /**
   * @Description : 获取当前年份
   * @return : String
   * @Creation Date : 2016年3月18日 上午11:40:23
   * @Author : wangliyi
   */
  public static String getCurrentYear() {
    Calendar calendar = Calendar.getInstance(Locale.getDefault());
    return String.valueOf(calendar.get(Calendar.YEAR));
  }

  /**
   * 获取之后一天的日期
   * 
   * @return : Date
   * @Creation Date : 2016年6月8日 下午3:07:07
   * @Author : zhiqiang zhang
   */
  public static Date getNextDay(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_MONTH, +1);// +1今天的时间加一天
    date = calendar.getTime();
    return date;
  }

  /**
   * 获取之后一天的日期
   * 
   * @return : Date
   * @Creation Date : 2016年6月8日 下午3:07:07
   * @Author : zhiqiang zhang
   */
  public static Date getNextDayNoTime(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_MONTH, +1);// +1今天的时间加一天
    calendar.set(Calendar.HOUR, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    calendar.set(Calendar.AM_PM, Calendar.AM);
    date = calendar.getTime();
    return date;
  }

  /**
   * @Description : 计算两个日期之间相差的天数
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @return : 相差天数
   * @Creation Date : 2016年8月3日 下午4:54:22
   * @Author : chichangchao
   */
  public static int getDaysBetween(Date startDate, Date endDate) throws ParseException {
    if (startDate == null || endDate == null)
      return 0;
    SimpleDateFormat sdf = new SimpleDateFormat(DATE);
    startDate = sdf.parse(sdf.format(startDate));
    endDate = sdf.parse(sdf.format(endDate));

    Calendar cal = Calendar.getInstance();
    cal.setTime(startDate);
    long startTime = cal.getTimeInMillis();
    cal.setTime(endDate);
    long endTime = cal.getTimeInMillis();

    long betweenDays = (endTime - startTime) / (1000 * 3600 * 24);
    return Integer.parseInt(String.valueOf(betweenDays));
  }

  /**
   * @Description : 获取两个时间中间缺失的月份个数
   * @return : int 月份个数
   * @Creation Date : 2016年11月25日 上午11:27:46
   * @Author : zhiqiang zhang
   */
  public static int getMonthsBetween(Date startDate, Date endDate) throws ParseException {
    if (startDate == null || endDate == null)
      return 0;
    Calendar c = Calendar.getInstance();

    c.setTime(startDate);
    int startYear = c.get(Calendar.YEAR);
    int startMonth = c.get(Calendar.MONTH);

    c.setTime(endDate);
    int endYear = c.get(Calendar.YEAR);
    int endMonth = c.get(Calendar.MONTH);

    return 12 * (endYear - startYear) + endMonth - startMonth - 1;
  }

  /**
   * @Description : 获取传入时间的月份
   * @return : String 月份
   * @Creation Date : 2016年11月25日 上午11:29:02
   * @Author : zhiqiang zhang
   */
  public static String getMonth(Date date) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    int mouth = c.get(Calendar.MONTH);
    return Integer.toString(mouth);
  }

  /**
   * @Description : 获取向前n个月的今天
   * @return : Calendar
   * @Creation Date : 2016年12月28日 下午3:54:23
   * @Author : zhiqiang zhang
   */
  public static Date getDateOfLastMonth(Date dateStr, int n) {
    Calendar c = Calendar.getInstance();
    c.setTime(dateStr);
    Calendar lastDate = (Calendar) c.clone();
    lastDate.add(Calendar.MONTH, -n);
    return lastDate.getTime();
  }

  /**
   * @Description : 获取向前n天的日期
   * @return : Calendar
   * @Creation Date : 2016年12月28日 下午3:54:23
   * @Author : wangshouming
   */
  public static Date getDateOfSomeDayBefore(Date dateStr, int n) {
    Calendar c = Calendar.getInstance();
    c.setTime(dateStr);
    Calendar lastDate = (Calendar) c.clone();
    lastDate.add(Calendar.DATE, -n);
    return lastDate.getTime();
  }

  /**
   * 
   * @Description : 根据类型获取一个此类型为单位的之前的日期
   * @Return : String
   * @Creation Date : 2017年3月5日 上午1:33:55
   * @Author : wangshouming
   */
  public static String getSomeDayBeforeByType(String type) {
    if ("today".equals(type)) {
      return format(getCurrentDate());
    } else if ("yesterday".equals(type)) {
      return format(getDateOfSomeDayBefore(getCurrentDate(), 1));
    } else if ("week".equals(type)) {
      return format(getDateOfSomeDayBefore(getCurrentDate(), 7));
    } else if ("month".equals(type)) {
      return format(getDateOfLastMonth(getCurrentDate(), 1));
    } else {
      return null;
    }
  }

  /**
   * 得到当前日期字符串 格式（yyyy-MM-dd）
   */
  public static String getDate() {
    return getDate("yyyy-MM-dd");
  }

  /**
   * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
   */
  public static String getDate(String pattern) {
    return DateFormatUtils.format(new Date(), pattern);
  }

  /**
   * 
   * @Description : 判断start是否大于end，取指定范围的时间
   * @param start 开始时间
   * @param end 结束时间
   * @param field 时间单位，从Calendar取常量
   * @param amount 间隔时间
   * @return : boolean
   * @Creation Date : 2017年2月17日 下午3:03:46
   * @Author : zhiqiang zhang
   */
  public static boolean isDateBetweenMoreThen(Date start, Date end, int field, int amount) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(start);
    calendar.add(field, amount);
    return end.after(calendar.getTime());
  }

  /**
   * @description : 判断两个时间段是否重叠（每个时间段的两个时间不能相同，否则返回是覆盖的）
   * @param ruleStart 标准时间段开始时间
   * @param ruleEnd 标准时间段结束时间
   * @param srcStart 校验时间段开始时间
   * @param srcEnd 校验时间段开始时间
   * @return : boolean
   * @creation Date : 2017年3月14日 下午1:33:18
   * @author : chichangchao
   */
  public static boolean isCover(Date ruleStart, Date ruleEnd, Date srcStart, Date srcEnd) {
    if (ruleStart == null || ruleEnd == null || srcStart == null || srcEnd == null) {
      return false;
    }
    if (!ruleStart.before(ruleEnd) || !srcStart.before(srcEnd)) {
      return false;
    }
    if (Objects.equals(ruleStart, srcEnd) || Objects.equals(ruleEnd, srcStart)) {
      return false;
    }
    Date[] temp = {ruleStart, ruleEnd, srcStart, srcEnd};
    Arrays.sort(temp);
    int ruleStartIndex = Arrays.binarySearch(temp, ruleStart);
    int ruleEndIndex = Arrays.binarySearch(temp, ruleEnd);
    if ((ruleStartIndex == 0 || ruleStartIndex == temp.length - 2)
        && ruleEndIndex - ruleStartIndex == 1) {
      return false;
    }
    return true;
  }
  /**
   * 
   *  @Description	: 前端页面使用的方法，获取时间的格式化，没有则返回指定默认值
   *  @param          date 时间
   *  @param        str 指定默认值
   *  @return         : String
   *  @Creation Date  : 2017年3月17日 下午4:32:02 
   *  @Author         : lcy
   */
  public static String getTimeFormat(Date date,String str){
    if (BeanUtils.isNotEmpty(date)) {
      SimpleDateFormat sd = new SimpleDateFormat(DATE_HH_MM_SS);
      return sd.format(date);
    }
    return str;
  }
  
  /**
   *  @Description	: 获取一年内的12个月(含本月)
   *  @return         : List<String> 
   *  @Creation Date  : 2017年3月30日 下午4:19:23 
   *  @Author         : qiudequan
   */
  public static List<String> getMonthsWithinYear() { 
    List<String> months = new ArrayList<>();
    Calendar instance = Calendar.getInstance();
    int count = 12;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年"
        + "MM月");
    for (int i = 0; i < count; i++) {
      if(i != 0) {
        instance.add(Calendar.MONTH, -1);
      }
      Date date = instance.getTime();
      String format = sdf.format(date);
      months.add(format);
    }
    return months;
  }
  
  /**
   * 
   *  @Description	: 根据年和月 获取传入年份月份的月初和月末
   *  @param dateTime  格式要求   yyyy年MM月
   *  @return         : Map<String,Date>  key---->beg  end   value-----> 月初  月末
   *  @Creation Date  : 2017年3月30日 下午5:17:58 
   *  @Author         : lcy
   */
  public Map<String, Date> getMonthBegAndEnd(String dateTime){
    Map<String, Date> map = new HashMap<>();
    int year = Integer.parseInt(dateTime.substring(0, 4));
    int month = Integer.parseInt( dateTime.substring(5, 7)) - 1;
    SimpleDateFormat format = new SimpleDateFormat(DATE);
    SimpleDateFormat formats = new SimpleDateFormat(DATE_HH_MM_SS);
    Calendar cale = Calendar.getInstance();
    cale.set(Calendar.YEAR, year);
    cale.set(Calendar.MONTH, month);
    cale.set(Calendar.DAY_OF_MONTH, 1);
    cale.set(Calendar.DAY_OF_MONTH, cale.getActualMaximum(Calendar.DAY_OF_MONTH));
    String endTime = format.format(cale.getTime());
    endTime += " 23:59:59";
    try {
      Date begTimes = format.parse(format.format(cale.getTime()));
      Date endTimes = formats.parse(endTime);
      map.put("beg", begTimes);
      map.put("end", endTimes);
      return map;
    } catch (ParseException e) {
      throw new InternalServerException(e);
    }
  }
}
