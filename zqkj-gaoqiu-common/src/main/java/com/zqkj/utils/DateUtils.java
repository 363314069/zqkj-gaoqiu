package com.zqkj.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 日期工具类
 * @author yinfu
 */
public class DateUtils {
	private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat cntimeFormat = new SimpleDateFormat("yyyy年M月d日");
	private static final SimpleDateFormat datetimesimFormat2 = new SimpleDateFormat("yyyyMMdd");
	private static final SimpleDateFormat datetimesimFormat3 = new SimpleDateFormat("yyyyMMddHH");
	private static final SimpleDateFormat datetimesimFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
	private static final SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
    /**
	 * 获得当前日期时间
	 * <p>
	 * 日期时间格式yyyyMMddHHmmss
	 * 
	 * @return
	 */
	public static String currentDate2() {
		return datetimesimFormat.format(now());
	}
	
	/**
	 * 获得当前日期时间
	 * <p>
	 * 日期时间格式yyyyMMdd
	 * 
	 * @return
	 */
	public static String currentDate3() {
		return datetimesimFormat2.format(now());
	}
	
	/**
	 * 获得当前日期时间
	 * <p>
	 * 日期时间格式yyyyMMdd
	 * 
	 * @return
	 */
	public static String currentDate4() {
		return datetimesimFormat3.format(now());
	}
	
	/**
	 * 获得当前日期时间
	 * <p>
	 * 日期时间格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String currentDatetime() {
		return datetimeFormat.format(now());
	}

	/**
	 * 自定义时间格式
	 * @param fromat
	 * @return
	 */
	public static String currentDatetime(String fromat) {
	   SimpleDateFormat sdf = new SimpleDateFormat(fromat);
		return sdf.format(now());
	}
	/**
	 * 格式化日期时间
	 * <p>
	 * 日期时间格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String formatDatetime(Date date) {
		return datetimeFormat.format(date);
	}

	/**
	 * 格式化日期时间
	 * 
	 * @param date
	 * @param pattern
	 *            格式化模式，详见{@link SimpleDateFormat}构造器
	 *            <code>SimpleDateFormat(String pattern)</code>
	 * @return
	 */
	public static String formatDatetime(Date date, String pattern) {
		SimpleDateFormat customFormat = (SimpleDateFormat) datetimeFormat
				.clone();
		customFormat.applyPattern(pattern);
		return customFormat.format(date);
	}

	/**
	 * 获得当前日期
	 * <p>
	 * 日期格式yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String currentDate() {
		return dateFormat.format(now());
	}
	
	/**
	 * 获得当前日期
	 * 日期格式yyyy年MM月dd日
	 */
	public static String currentCnDate() {
		return cntimeFormat.format(now());
	}
	
	/**
	 * 格式化日期
	 * <p>
	 * 日期格式yyyy年MM月dd日
	 */
	public static String formatCnDate(Date date) {
		return cntimeFormat.format(date);
	}

	/**
	 * 格式化日期
	 * <p>
	 * 日期格式yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String formatDate(Date date) {
		return dateFormat.format(date);
	}

	/**
	 * 获得当前时间
	 * <p>
	 * 时间格式HH:mm:ss
	 * 
	 * @return
	 */
	public static String currentTime() {
		return timeFormat.format(now());
	}

	/**
	 * 格式化时间
	 * <p>
	 * 时间格式HH:mm:ss
	 * 
	 * @return
	 */
	public static String formatTime(Date date) {
		return timeFormat.format(date);
	}

	/**
	 * 获得当前时间的<code>java.util.Date</code>对象
	 * 
	 * @return
	 */
	public static Date now() {
		return new Date();
	}

	public static Calendar calendar() {
		Calendar cal = GregorianCalendar.getInstance(Locale.CHINESE);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		return cal;
	}

	/**
	 * 获得当前时间的毫秒数
	 * <p>
	 * 详见{@link System#currentTimeMillis()}
	 * 
	 * @return
	 */
	public static long millis() {
		return System.currentTimeMillis();
	}

	/**
	 * 
	 * 获得当前Chinese月份
	 * 
	 * @return
	 */
	public static int month() {
		return calendar().get(Calendar.MONTH) + 1;
	}

	/**
	 * 获得月份中的第几天
	 * 
	 * @return
	 */
	public static int dayOfMonth() {
		return calendar().get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 今天是星期的第几天
	 * 
	 * @return
	 */
	public static int dayOfWeek() {
		return calendar().get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 今天是年中的第几天
	 * 
	 * @return
	 */
	public static int dayOfYear() {
		return calendar().get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 判断原日期是否在目标日期之前
	 * 
	 * @param src
	 * @param dst
	 * @return
	 */
	public static boolean isBefore(Date src, Date dst) {
		return src.before(dst);
	}

	/**
	 * 判断原日期是否在目标日期之后
	 * 
	 * @param src
	 * @param dst
	 * @return
	 */
	public static boolean isAfter(Date src, Date dst) {
		return src.after(dst);
	}

	/**
	 * 判断两日期是否相同
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isEqual(Date date1, Date date2) {
		return date1.compareTo(date2) == 0;
	}

	/**
	 * 判断某个日期是否在某个日期范围
	 * 
	 * @param beginDate
	 *            日期范围开始
	 * @param endDate
	 *            日期范围结束
	 * @param src
	 *            需要判断的日期
	 * @return
	 */
	public static boolean between(Date beginDate, Date endDate, Date src) {
		return beginDate.before(src) && endDate.after(src);
	}

	/**
	 * 获得当前月的最后一天
	 * <p>
	 * HH:mm:ss为0，毫秒为999
	 * 
	 * @return
	 */
	public static Date lastDayOfMonth() {
		Calendar cal = calendar();
		cal.set(Calendar.DAY_OF_MONTH, 0); // M月置零
		cal.set(Calendar.HOUR_OF_DAY, 0);// H置零
		cal.set(Calendar.MINUTE, 0);// m置零
		cal.set(Calendar.SECOND, 0);// s置零
		cal.set(Calendar.MILLISECOND, 0);// S置零
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);// 月份+1
		cal.set(Calendar.MILLISECOND, -1);// 毫秒-1
		return cal.getTime();
	}

	/**
	 * 获得当前月的第一天
	 * <p>
	 * HH:mm:ss SS为零
	 * 
	 * @return
	 */
	public static Date firstDayOfMonth() {
		Calendar cal = calendar();
		cal.set(Calendar.DAY_OF_MONTH, 1); // M月置1
		cal.set(Calendar.HOUR_OF_DAY, 0);// H置零
		cal.set(Calendar.MINUTE, 0);// m置零
		cal.set(Calendar.SECOND, 0);// s置零
		cal.set(Calendar.MILLISECOND, 0);// S置零
		return cal.getTime();
	}

	private static Date weekDay(int week) {
		Calendar cal = calendar();
		cal.set(Calendar.DAY_OF_WEEK, week);
		return cal.getTime();
	}

	/**
	 * 获得周五日期
	 * <p>
	 * 注：日历工厂方法{@link #calendar()}设置类每个星期的第一天为Monday，US等每星期第一天为sunday
	 * 
	 * @return
	 */
	public static Date friday() {
		return weekDay(Calendar.FRIDAY);
	}

	/**
	 * 获得周六日期
	 * <p>
	 * 注：日历工厂方法{@link #calendar()}设置类每个星期的第一天为Monday，US等每星期第一天为sunday
	 * 
	 * @return
	 */
	public static Date saturday() {
		return weekDay(Calendar.SATURDAY);
	}

	/**
	 * 获得周日日期
	 * <p>
	 * 注：日历工厂方法{@link #calendar()}设置类每个星期的第一天为Monday，US等每星期第一天为sunday
	 * 
	 * @return
	 */
	public static Date sunday() {
		return weekDay(Calendar.SUNDAY);
	}

	/**
	 * 将字符串日期时间转换成java.util.Date类型
	 * <p>
	 * 日期时间格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @param datetime
	 * @return
	 */
	public static Date parseDatetime(String datetime) throws ParseException {
		return datetimeFormat.parse(datetime);
	}

	/**
	 * 字符串转Date
	 * @param datetime
	 * @param fromat
	 * @return
	 */
	public static Date parseDatetimeFromat(String datetime,String fromat){
		 SimpleDateFormat sdf = new SimpleDateFormat(fromat);
		 
		 Date date=null;
		try {
			date = sdf.parse(datetime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		 
		return date;
	}
	/**
	 * 将字符串日期转换成java.util.Date类型
	 * <p>
	 * 日期时间格式yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String date) throws ParseException {
		return dateFormat.parse(date);
	}

	/**
	 * 将字符串日期转换成java.util.Date类型
	 * <p>
	 * 时间格式 HH:mm:ss
	 * 
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static Date parseTime(String time) throws ParseException {
		return timeFormat.parse(time);
	}

	/**
	 * 根据自定义pattern将字符串日期转换成java.util.Date类型
	 * 
	 * @param datetime
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDatetime(String datetime, String pattern)
			throws ParseException {
		SimpleDateFormat format = (SimpleDateFormat) datetimeFormat.clone();
		format.applyPattern(pattern);
		return format.parse(datetime);
	}
	
	/**
	 * 时间加分钟
	 * @param timeStr
	 * @param addnumber
	 * @return
	 */
   public static String addtime(String timeStr,String addnumber){
        String str=null;
        try {
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = df.parse(timeStr);
            //时间累计
            Calendar gc =new GregorianCalendar();
            gc.setTime(date);
            gc.add(GregorianCalendar.MINUTE,Integer.parseInt(addnumber));
            str=  df.format(gc.getTime());
        }catch (Exception e){
        }
        return str;
    }
   
	   
   
   /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     * 
     * @param nowTime 当前时间
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     * @author jqlin
     */
	public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
	       if (nowTime.getTime() == startTime.getTime()
	               || nowTime.getTime() == endTime.getTime()) {
	           return true;
	       }


	       Calendar date = Calendar.getInstance();
	       date.setTime(nowTime);


	       Calendar begin = Calendar.getInstance();
	       begin.setTime(startTime);


	       Calendar end = Calendar.getInstance();
	       end.setTime(endTime);


	       if (date.after(begin) && date.before(end)) {
	           return true;
	       } else {
	           return false;
	       }
	   }
	
	
	/**
	 * 获取当前时间点离一天结束剩余秒数
	 * ocalDateTime是java 8的新特性，提供对时间日期的描述和操作，其提供的对象是不可改变并且线程安全的。
	 * @param currentDate
	 * @return
	 */
    public static Integer getRemainSecondsOneDay() {
    	Date currentDate = new Date();
        LocalDateTime midnight = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault()).plusDays(1).withHour(0).withMinute(0)
                .withSecond(0).withNano(0);
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault());
        long seconds = ChronoUnit.SECONDS.between(currentDateTime, midnight);
        return (int) seconds;
    }
    
    /**
     * 获取当前时间点离一天结束剩余秒数
     * ocalDateTime是java 8的新特性，提供对时间日期的描述和操作，其提供的对象是不可改变并且线程安全的。
     * @param currentDate
     * @return
     */
    public static Integer getRemainSecondsOneDay(Date currentDate) {
    	LocalDateTime midnight = LocalDateTime.ofInstant(currentDate.toInstant(),
    			ZoneId.systemDefault()).plusDays(1).withHour(0).withMinute(0)
    			.withSecond(0).withNano(0);
    	LocalDateTime currentDateTime = LocalDateTime.ofInstant(currentDate.toInstant(),
    			ZoneId.systemDefault());
    	long seconds = ChronoUnit.SECONDS.between(currentDateTime, midnight);
    	return (int) seconds;
    }
	

	
	 
	 
}
