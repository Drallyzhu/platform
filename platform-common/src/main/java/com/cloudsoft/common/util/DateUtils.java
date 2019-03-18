package com.cloudsoft.common.util;


import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 日期工具类
 * 
 * @author xianyongjie
 *
 */
public class DateUtils {

	/**
	 * 常量：用于转换只有日期
	 */
	public static final String DATE_ONLY = "yyyy-MM-dd";

	/**
	 * 常量：用于转换只有时间
	 */
	public static final String TIME_ONLY = "HH:mm:ss";

	/**
	 * 常量：用于转换日期和时间
	 */
	public static final String DATE_AND_TIME = "yyyy-MM-dd HH:mm:ss";

    public static final String  fullPattern       = "yyyyMMddHHmmss";

	public static final String  shortFullPattern    = "yyMMddHHmmss";

	public static final String defaultPattern = "yyyy-MM-dd HH:mm:ss";

	public static final String PATTERN_YYYYMMDD = "yyyyMMdd";

	/**
	 * 将日期类型转换成指定格式的日期字符串
	 * 
	 * @param date
	 *            待转换的日期
	 * @param formatter
	 *            日期格式字符串
	 * @return String
	 */
	public static String formatTime(Date date, String formatter) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatter);
		return sdf.format(date);
	}
	
	/**
	 * 计算指定日期的偏移日期
	 * @param selectDate
	 * @param offsetDay
	 * @return
	 */
	public static Date calDate(Date selectDate,int offsetDay){
		
		Calendar calendar=Calendar.getInstance();   
		calendar.setTime(selectDate);		
		calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR) + offsetDay);  
		return calendar.getTime();
		
	}
	
	/**
	 * 获取当前日期的当月第一天
	 * @param      date
	 * @return     
	 * @exception  
	 */
	public static Date getFirstDay(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(GregorianCalendar.DAY_OF_MONTH, 1);  
        return cal.getTime();
	}
	
	/**
	 * 获取当前日期的当月最后一天
	 * @param      date
	 * @return     
	 * @exception  
	 */
	public static Date getLastDay(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set( Calendar.DATE, 1 );  
        cal.roll(Calendar.DATE, - 1 );
        return cal.getTime();
	}
	

	/**
	 * 获取当前日期的下一天
	 * @param      date
	 * @return     
	 * @exception  
	 */
	public static Date getNextDate(Date date){
    return com.orbitz.apache.commons.lang3.time.DateUtils.addDays(date,1);
	}
	
	/**
	 * 获取当前系统时间的昨天
	 * @return     
	 * @exception  
	 */
	public static Date getYesterDay(){
    return com.orbitz.apache.commons.lang3.time.DateUtils.addDays(new Date(),-1);
	}
	
	/**
	 * 获取当前日期的当月的所有日期
	 * @param      date
	 * @return     
	 * @exception  
	 */
	public static List<Date> getNextDateList(Date date){
		List<Date> list = new ArrayList<Date>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Date whenDate = date;
		Date endDate = getLastDay(date);
		while(whenDate.before(endDate)||whenDate.equals(endDate)){
			list.add(whenDate);
			whenDate = getNextDate(whenDate);
		}
		return list;
	}

	/**
	 * 字符串日期解析
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static Date parse(String dateStr, String pattern) {
		if (pattern == null) {
			pattern = defaultPattern;
		}
		DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
		return DateTime.parse(dateStr, formatter).toDate();
	}

	/**
	 * 日期格式化
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date, String pattern) {
		DateTime dateTime = new DateTime(date);
		if (pattern == null) {
			pattern = defaultPattern;
		}
		return dateTime.toString(DateTimeFormat.forPattern(pattern));
	}

	/**
	 * 指定时间是否在现在之前
	 * @param date
	 * @return
	 */
	public static boolean isBeforeNow(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.isBeforeNow();
	}

	/**
	 * 指定时间是否在现在之后
	 * @param date
	 * @return
	 */
	public static boolean isAfterNow(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.isAfterNow();
	}

	/***
	 *  转换成date类型格式
	 * @param dataStr
	 * @return
	 * @throws ParseException
	 */
	public static Date stringToDate(String dataStr) throws ParseException{		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return df.parse(dataStr);
	}	
	

	public static Date StringTodate3(String dataStr) throws ParseException{		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.parse(dataStr);
	}
	
	
	public static Date StringTodate4(String dataStr){		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return df.parse(dataStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static Date StringTodate7(String dataStr) throws ParseException{		
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM");	
			return df.parse(dataStr);

	}
	public static Date StringTodate5(String dataStr)throws ParseException{
		SimpleDateFormat df=new SimpleDateFormat("HH:mm");
		return df.parse(dataStr);
	}
	public static Date StringTodate6(String dataStr)throws ParseException{
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM");
		return df.parse(dataStr);
	}
	
	public static Date StringTodate8(String dataStr){		
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		try {
			return df.parse(dataStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date StringTodateYMD(String dataStr) throws Exception{		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return df.parse(dataStr);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}	
	
	public static String datetoString2(Date date){
		if(date != null){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			return df.format(date);
		}
		return "";
	}
	
	/**
	 * date类型转化为指定的字符串类型格式
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date){
		if(date != null){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return df.format(date);
		}
		return "";
	}
	
	/**
	 * date类型转化为指定的字符串类型格式
	 * @param date
	 * @return
	 */
	public static String dateToString3(Date date){
		if(date != null){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			return df.format(date);
		}
		return "";
	}
	
	/**
	 * date类型转化为指定的字符串类型格式
	 * @param date
	 * @return
	 */
	public static String dateToString4(Date date){
		if(date != null){
			SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
			return df.format(date);
		}
		return "";
	}
	public static String dateToString5(Date date){
		if(date != null){
			SimpleDateFormat df = new SimpleDateFormat("yyyy/MM");
			return df.format(date);
		}
		return "";
	}
	/**
	 * date类型转化为指定的字符串类型格式
	 * @param date
	 * @return
	 */
	public static String dateToString6(Date date){
		if(date != null){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
			return df.format(date);
		}
		return "";
	}
	
	/**
	 * date类型转化为指定的字符串类型格式
	 * @param date
	 * @return
	 */
	public static String dateToString2(Date date){
		if(date != null){
			SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
			return df.format(date);
		}
		return "";
	}
	
	public static String dateToString1(Date date){
		if(date != null){
			SimpleDateFormat df = new SimpleDateFormat("HH:mm");
			return df.format(date);
		}
		return "";
	}
	
	/**
	 * 获取当前天的开始时间
	 * @param date
	 * @return
	 */
	public static Date date2StringStar(Date date){
		return StringTodate4(dateToStringYYYYMMDD(date)+" 00:00:00");
	}
	/**
	 * 获取当前天的结束时间
	 * @param date
	 * @return
	 */
	public static Date date2StringEnd(Date date){
		return StringTodate4(dateToStringYYYYMMDD(date)+" 23:59:59");
	}
	
	/**
	 * date类型转化为指定的字符串类型格式
	 * @param date
	 * @return
	 */
	public static Date dateToDateYYYYMMDD(Date date){
		if(date != null){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try {
				return df.parse(df.format(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * date类型转化为指定的字符串类型格式
	 * @param date
	 * @return
	 */
	public static Date dateToDateHHMMDD(Date date){
		if(date != null){
			SimpleDateFormat df = new SimpleDateFormat("HH:mm:dd");
			try {
				return df.parse(df.format(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	
	public static String dateToStringYYYYMMDD(Date date){
		if(date != null){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			return df.format(date);
		}
		return "";
	}
	
	
	

	public static String dateToStringYYYY(Date date){
		if(date != null){
			SimpleDateFormat df = new SimpleDateFormat("yyyy");
			return df.format(date);
		}
		return "";
	}
	
	/**
	 * 查询2个日期是否同1天
	 * @param t
	 * @param t2
	 * @return
	 */
	public static boolean IsSameDay(Timestamp t,Timestamp t2){
		Calendar cal = Calendar.getInstance();
		cal.setTime(t);
		int y = cal.get(Calendar.YEAR);
		int d = cal.get(Calendar.DAY_OF_YEAR);
		
		cal.setTime(t2);
		int y2 = cal.get(Calendar.YEAR);
		int d2 = cal.get(Calendar.DAY_OF_YEAR);
		
		return (y==y2) && (d==d2);
	}
	
	/**获取当前时间的Timestamp值*/
	public static Timestamp NowTimestamp(){
		return new Timestamp(new Date().getTime());
	}
	
	
	/**
	 * 获取当前时间(注意:返回的是String 已经格式化好的格式)
	 * @return
	 */
	public static String getNowTime(){
		Date nowDate = new Date();
		Calendar now = Calendar.getInstance();
		now.setTime(nowDate);
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String str = formatter.format(now.getTime());
		return str;
	}
	
	/**
	 * 获取当前时间(注意:返回的是String 已经格式化好的格式)
	 * @return
	 */
	public static String getNowTimeFormat(){
		Date nowDate = new Date();
		Calendar now = Calendar.getInstance();
		now.setTime(nowDate);
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	    String str = formatter.format(now.getTime());
		return str;
	}
	/**
	 * 获取当前时间(注意:返回的是String 已经格式化好的格式)
	 * @return
	 */
	public static String getNowTimeFormat(String format){
		Date nowDate = new Date();
		Calendar now = Calendar.getInstance();
		now.setTime(nowDate);
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String str = formatter.format(now.getTime());
		return str;
	}

	public static String getNowTimeFormat(Date nowDate){
		if (nowDate==null) nowDate = new Date();
		Calendar now = Calendar.getInstance();
		now.setTime(nowDate);
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	    String str = formatter.format(now.getTime());
		return str;
	}
	
	public static long getTimeInMillis(String sDate, String eDate){
		Timestamp sd = Timestamp.valueOf(sDate);
		Timestamp ed = Timestamp.valueOf(eDate);
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(sd); 
		long timethis = calendar.getTimeInMillis();  
		
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(ed); 
		long timeend = calendar2.getTimeInMillis(); 
		long thedaymillis = timeend-timethis; 
		return thedaymillis;
	}
	
	/**
	 *   HH:mm:ss格式化的时间
	 * @param dTime
	 * @return
	 */
	public static String formatTime(String dTime) {
		String dateTime = "";
		if(dTime != null && !"".equals(dTime)) {
			Timestamp t = Timestamp.valueOf(dTime);
			SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");
			dateTime = formatter.format(t);
		}
		return dateTime;
	}
	
	/**
	 *  强制转化为时间格式
	 * @param strDate
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static Date parses(String   strDate,   String   pattern)   throws   ParseException   {   
		  return new SimpleDateFormat(pattern).parse(strDate);   
	} 
	
	/**
	 * 当前日期是第几周
	 * @return
	 */
	public static String getWeekOfYear(){
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		String week = calendar.get(Calendar.WEEK_OF_YEAR)+"";
		return week; 
	}
	
	/**
	 * 返回毫秒
	 * @param sDate
	 * @param eDate
	 * @return
	 */
	public static String getTimeInMillis(Date sDate, Date eDate){
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(sDate); 
		long timethis = calendar.getTimeInMillis();  
		
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(eDate); 
		long timeend = calendar2.getTimeInMillis(); 
		long thedaymillis = timeend-timethis; 
		return thedaymillis < 1000 ? thedaymillis + "毫秒!" : (thedaymillis/1000) + "秒钟!";
	}
	
	
	/**
	 * 获取第i月之后的时间
	 * @param ts
	 * @param i
	 * @return
	 */
	public static String getNextDate(String ts, int i){
		Calendar now = Calendar.getInstance();
		Timestamp t = Timestamp.valueOf(ts + " 00:00:00.000");
		now.setTime(t);
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    now.add(Calendar.DAY_OF_MONTH, +(i)); 
		String dt = formatter.format(now.getTime());
		return dt;
	}
	
	/**
	 * 获取第i分钟之后的时间
	 * @param ts
	 * @param i
	 * @return
	 */
	public static String getNextTime(String ts, int i){
		Calendar now = Calendar.getInstance();
		Timestamp t = Timestamp.valueOf(ts);
		now.setTime(t);
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    now.add(Calendar.MINUTE, +(i)); 
		String dt = formatter.format(now.getTime());
		return dt;
	}
	

	/***
	 * 取Unix时间戳
	 * @param dateTime
	 * @return
	 */
	public static long getUnixTime(String dateTime) {
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime);
		    date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1970-01-01 08:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
        long l = (date1.getTime() - date2.getTime())/1000;
        return l;
	}
	
	/**
	 * 计算两个日期之间的相隔的年、月、日。注意：只有计算相隔天数是准确的，相隔年和月都是 近似值，按一年365天，一月30天计算，忽略闰年和闰月的差别。
	 * 
	 * @param datepart
	 *            两位的格式字符串，yy表示年，MM表示月，dd表示日
	 * @param startdate
	 *            开始日期
	 * @param enddate
	 *            结束日期
	 * @return double 如果enddate>startdate，返回一个大于0的实数，否则返回一个小于等于0的实数
	 */
	public static double dateDiff(String datepart, Date startdate, Date enddate) {
		if (datepart == null || datepart.equals("")) {
			throw new IllegalArgumentException("DateUtil.dateDiff()方法非法参数值："
					+ datepart);
		}

		double days = (double) (enddate.getTime() - startdate.getTime())
				/ (60 * 60 * 24 * 1000);

		if (datepart.equals("yy")) {
			days = days / 365;
		} else if (datepart.equals("MM")) {
			days = days / 30;
		} else if (datepart.equals("dd")) {
			return days;
		} else {
			throw new IllegalArgumentException("DateUtil.dateDiff()方法非法参数值："
					+ datepart);
		}
		return days;
	}
	
	
	/**
    * 把日期对象加减年、月、日后得到新的日期对象
    * @param depart yy-年、MM-月、dd-日、HH-时、mm-分、ss-秒
    * @param number 加减因子
    * @param date 需要加减年、月、日的日期对象
    * @return Date 新的日期对象
    */
   public static Date addDate(String datepart, int number, Date date)
   {
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      if(datepart.equals("yy")) {
         cal.add(Calendar.YEAR, number);
      } else if(datepart.equals("MM")) {
         cal.add(Calendar.MONTH, number);
      } else if(datepart.equals("dd")) {
         cal.add(Calendar.DATE, number);
      }else if(datepart.equals("HH")) {
          cal.add(Calendar.HOUR_OF_DAY, number);
      } else if(datepart.equals("mm")) {
          cal.add(Calendar.MINUTE, number);
      } else if(datepart.equals("ss")) {
          cal.add(Calendar.SECOND, number);
      }  else {
         throw new IllegalArgumentException("DateUtil.addDate()方法非法参数值：" +
                                            datepart);
      }

      return cal.getTime();
   }
   
   
   public static Timestamp changeDate(String dateStr) {
	   Timestamp time = null;
	   try {
		   Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
		   time =  new Timestamp(date.getTime());
	   } catch (Exception e) {
			e.printStackTrace();
	   }
	   return time;
	   
   }

   /**
    * 获取当前日期是星期几
    * 
    * @param dt
    * @return 0-6代表星期日-星期六
    */
   public static int  getWeekOfDate(Date dt) {
       Calendar cal = Calendar.getInstance();
       cal.setTime(dt);
       int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
       if (w < 0)
           w = 0;
       return w;
   }
   
   /**
    * 获取当前日期
    *
    * @return 当前日期
    */
   public static Date getCurrentDate() {
       return DateTime.now().toDate();
   }
   
   /**
    * 获取当前时间 格式： yyyyMMddHHmmss
    *
    * @return 字符日期 格式：yyyyMMddHHmmss
    */
   public static String getCurrent() {
       return getCurrent(fullPattern);
   }

   /**
    * 获取当前时间 格式： 自定义
    *
    * @param pattern 时间格式
    * @return        自定义格式的当前时间
    */
   public static String getCurrent(String pattern) {
       return DateTime.now().toString(pattern);
   }
   
   /**
    * 判断一个时间点是否在一个时间段之内
    * @param begin 开始时间
    * @param end   截至时间
    * @param source 比较对象
    * @return
    */
   public static boolean isBetween(Date begin,Date end,Date source){
	   boolean beginTag = source.after(begin);
	   boolean endTag = source.before(end);
	   return beginTag && endTag;
   }
   
   /**
    * 毫秒数转日期
    * @param ms 毫秒数
    * @param format
    * @return
    * @throws ParseException
    */
   public static Date msToDate(Long ms,String format) throws ParseException{
	   SimpleDateFormat sdf=new SimpleDateFormat(format);
	   Date date = sdf.parse(sdf.format(ms));
	   return date;
   }

	/**
	 * 根据传入时分秒 获取当天具体时间
	 * @param hour
	 * @return
	 * @throws ParseException
	 */
	public static Date getTodayByHour(int hour,int minute,int second) throws ParseException {
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR_OF_DAY, hour);
		todayStart.set(Calendar.MINUTE, minute);
		todayStart.set(Calendar.SECOND, second);
		return msToDate(todayStart.getTime().getTime(),defaultPattern);
	}

	/**
	 * 获取日期的开始时间
	 * @param date
	 * @return
	 */
	public static Date beginOfDay(Date date){
		if (date == null) {
			return null;
		}
		Date beginDate = null;
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		String beginTime = s.format(date);
		beginTime += " 00:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			beginDate = sdf.parse(beginTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return beginDate;
	}

	/**
	 * 获取日期的结束时间
	 * @param date
	 * @return
	 */
	public static Date endOfDay(Date date){
		if (date == null) {
			return null;
		}
		Date endDate = null;
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		String endTime = s.format(date);
		endTime += " 23:59:59";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			endDate = sdf.parse(endTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return endDate;
	}

	/**
	 * 取得两个时间段的时间间隔
	 *
	 * @author color
	 * @param t1
	 *            时间1
	 * @param t2
	 *            时间2
	 * @return t2 与t1的间隔天数
	 * @throws ParseException
	 *             如果输入的日期格式不是0000-00-00 格式抛出异常
	 */
	public static int getBetweenDays(String t1, String t2) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		int betweenDays = 0;
		Date d1 = format.parse(t1);
		Date d2 = format.parse(t2);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		// 保证第二个时间一定大于第一个时间
		if (c1.after(c2)) {
			c1 = c2;
			c2.setTime(d1);
		}
		int betweenYears = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		betweenDays = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
		for (int i = 0; i < betweenYears; i++) {
			c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1));
			betweenDays += c1.getMaximum(Calendar.DAY_OF_YEAR);
		}
		return betweenDays;
	}

	/**
	 * 指定日期、相加月数值、格式，得到相加日期 例如：2011-06-19 2 yyyy-MM-dd 结果：2011-08-19 2011-06-19
	 * 12 yyyy-MM-dd 结果：2012-06-19
	 *
	 * @param date
	 *            指定日期
	 * @param formtStr
	 *            格式
	 * @param number
	 *            数组
	 * @param calender
	 *            指定修改日期格式数组
	 * @author leiyunshi
	 * @return
	 */
	public static String tragetDate(String date, String formtStr, int number, int calender) {
		if (date == null) {
			return null;
		}

		if (date.trim().equals("")) {
			return null;
		}
		DateFormat df = new SimpleDateFormat(formtStr);// "yyyy-MM-dd"

		Calendar cal = Calendar.getInstance();
		cal.setTime(toDate(date));
		cal.add(calender, number);
		// System.out.println(df.format(cal.getTime()));
		return df.format(cal.getTime());
	}

	/**
	 * 按照"yyyy-MM-dd"格式将字符串date转换为日期对象
	 *
	 * @param date
	 * @return
	 */
	public static Date toDate(String date) {
		if (date == null) {
			return null;
		}
		if (date.trim().equals("")) {
			return null;
		}
		SimpleDateFormat simformat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return simformat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获得间隔日期（主要是间隔N周、间隔N天）
	 *
	 * @param specifiedStrDate
	 *            指定日期
	 * @param dateForamtType
	 *            指定日期格式
	 * @param intervalNum
	 *            间隔数（周或者天）
	 * @param calenderParam
	 *            指定修改日期格式的属性
	 *            Calendar.WEEK_OF_MONTH（周）或者Calendar.DAY_OF_MONTH（天）
	 * @return
	 */
	public static String getIntervalStrDate(String specifiedStrDate, String dateForamtType, int intervalNum,
											int calenderParam) {
		if (specifiedStrDate == null) {
			return null;
		}
		if (specifiedStrDate.trim().equals("")) {
			return null;
		}

		DateFormat df = new SimpleDateFormat(dateForamtType);

		Calendar cal = Calendar.getInstance();
		cal.setTime(turnStrDateToJavaUtilDate(specifiedStrDate, dateForamtType));
		cal.add(calenderParam, intervalNum);
		return df.format(cal.getTime());
	}

	/**
	 * 按照指定格式将字符串日期转换为SQL需要的日期对象
	 *
	 * @param strDate
	 *            String，欲转换的字符串型日期
	 *
	 * @param dateFormateType
	 *            String，指定的字符串日期格式
	 * @return java.sql.Date 转换后的java.sql.Date型日期
	 */
	public static java.sql.Date turnDateToSqlDate(String strDate, String dateFormateType) {
		if (strDate == null) {
			return null;
		}
		if (strDate.trim().equals("")) {
			return null;
		}

		return new java.sql.Date(turnStrDateToJavaUtilDate(strDate, dateFormateType).getTime());
	}

	/**
	 * 按指定的字符串格式将字符串型日期转化为Date型日期
	 *
	 * @Param dateStr 字符型日期
	 * @param dateFormatType
	 *            "yyyy-MM-dd" 或者 "yyyy-MM-dd hh:mm:ss"
	 * @return Date型日期
	 * @throws Exception
	 */
	public static Date turnStrDateToJavaUtilDate(String strDate, String dateFormatType) {

		Date javaUtilDate = null;
		DateFormat df = new SimpleDateFormat(dateFormatType);
		if (strDate != null && (!"".equals(strDate)) && dateFormatType != null && (!"".equals(dateFormatType))) {

			try {

				javaUtilDate = df.parse(strDate);
			} catch (ParseException e) {

			}
		}
		return javaUtilDate;
	}

	/**
	 * 判断两个字符串型日期是否指同一天
	 *
	 * @param strDate
	 *            字符串日期
	 * @param anotherStrDate
	 *            另一个字符日期
	 *
	 * @return boolean true/false
	 */
	public static boolean isTheSameDay(String strDate, String anotherStrDate) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date firstStrDate = null;
		Date secondStrDate = null;
		try {
			firstStrDate = df.parse(strDate);
			secondStrDate = df.parse(anotherStrDate);
		} catch (ParseException e) {

		}
		if (firstStrDate.getTime() > secondStrDate.getTime()) {

			return false;
		} else if (firstStrDate.getTime() < secondStrDate.getTime()) {

			return false;
		} else if (firstStrDate.getTime() == secondStrDate.getTime()) {

			return true;
		} else {

			return false;
		}
	}


}
