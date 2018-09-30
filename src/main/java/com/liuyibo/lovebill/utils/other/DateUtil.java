package com.liuyibo.lovebill.utils.other;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 说明：日期相关的工具类
 *
 * @author zxc DateUtil.java 2018-8-06
 */
public  class DateUtil {
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

	/**
	 * 获取当前月份,格式 "YYYYMM"
	 *
	 * @return
	 */
	public static String getCurMonth() {
		Calendar calendar = Calendar.getInstance();
		StringBuffer month = new StringBuffer();
		int y = calendar.get(Calendar.YEAR);
		int m = calendar.get(Calendar.MONTH) + 1;

		month.append(y);
		if (m < 10) {
			month.append("0");
		}
		month.append(m);

		return month.toString();
	}

	/**
	 * 获取当前日期,格式 "YYYYMMDD"
	 *
	 * @return
	 */
	public static String getToday() {
		Calendar calendar = Calendar.getInstance();
		StringBuffer today = new StringBuffer();
		int y = calendar.get(Calendar.YEAR);
		int m = calendar.get(Calendar.MONTH) + 1;
		int d = calendar.get(Calendar.DAY_OF_MONTH);

		today.append(y);
		if (m < 10) {
			today.append("0");
		}
		today.append(m);
		if (d < 10) {
			today.append("0");
		}
		today.append(d);

		return today.toString();
	}

	/**
	 * 获取当前时间,格式"hh:mm:ss"
	 *
	 * @return
	 */
	public static String getCurrentTime() {
		StringBuffer result = new StringBuffer();
		Calendar calendar = Calendar.getInstance();
		int h = calendar.get(Calendar.HOUR_OF_DAY);
		int m = calendar.get(Calendar.MINUTE);
		int s = calendar.get(Calendar.SECOND);
		if (h < 10) {
			result.append("0");
		}
		result.append(h);
		result.append(":");
		if (m < 10) {
			result.append("0");
		}
		result.append(m);
		result.append(":");
		if (s < 10) {
			result.append("0");
		}
		result.append(s);
		return result.toString();
	}

	/**
	 * 根据模式返回相应的系统时间字符串
	 *
	 * @paramdate
	 * @paramdeltaDay
	 * @return
	 */
	public static String getCurrTime(String pattern) {
		Calendar c = Calendar.getInstance();
		Date d = c.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(d);
	}

	/**
	 * 获取输入日期的前 n天 (n为负整数)或后n天(n为正整数)。 getBeforeOrNextDay("20120720",1) 返回
	 * 20120721 getBeforeOrNextDay("20120720",-1) 返回 20120719
	 *
	 * @param day
	 * @param n
	 * @return
	 */
	public static String getBeforeOrNextDay(String day, int n) {
		if (day == null || "".equals(day) || day.length() != 8) {
			throw new RuntimeException("由于缺少必要的参数，获取输入日期的前 n天 或后n天出错：day=" + day + ";n=" + n);
		}
		try {
			String sYear = day.substring(0, 4);
			int year = Integer.parseInt(sYear);
			String sMonth = day.substring(4, 6);
			int month = Integer.parseInt(sMonth);
			String sDay = day.substring(6, 8);
			int dday = Integer.parseInt(sDay);
			Calendar cal = Calendar.getInstance();
			cal.set(year, month - 1, dday);
			cal.add(Calendar.DATE, n);
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			return df.format(cal.getTime());
		} catch (Exception e) {
			throw new RuntimeException("进行日期运算时输入得参数不符合系统规格." + e);
		}
	}

	/**
	 * 获取输入月份的前 n月 (n为负整数)或后n月(n为正整数)。
	 *
	 * @param month yyyyMM
	 * @param n
	 * @return
	 */
	public static String getBeforeOrNextMonth(String month, int n) {
		if (month == null || "".equals(month) || month.length() != 6) {
			throw new RuntimeException("由于缺少必要的参数，系统无法进行制定的月份换算.");
		}
		try {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			cal.setTime(sdf.parse(month));
			cal.add(Calendar.MARCH, n);
			return sdf.format(cal.getTime());
		} catch (Exception e) {
			throw new RuntimeException("进行月份运算时输入得参数不符合系统规格." + e);
		}
	}

	/**
	 * 返回星期: 星期天:0 星期陆:6
	 *
	 * @param date 20120723
	 * @return
	 */
	public static int getWeekday(String date) {
		if (date == null || date.length() != 8) {
			throw new RuntimeException("由于缺少必要的参数，返回星期时出错：date=" + date);
		}
		String sYear = date.substring(0, 4);
		int year = Integer.parseInt(sYear);
		String sMonth = date.substring(4, 6);
		int mon = Integer.parseInt(sMonth);
		String sDay = date.substring(6, 8);
		int dday = Integer.parseInt(sDay);
		Calendar cal = Calendar.getInstance();
		cal.set(year, mon - 1, dday);
		int weekday = cal.get(Calendar.DAY_OF_WEEK) - 1;
		return weekday;
	}



	private static Date getDate(String date) {
		if (date == null || date.length() != 8) {
			throw new RuntimeException("由于缺少必要的参数，系统获取农历时出错.date=" + date);
		}
		String sYear = date.substring(0, 4);
		int year = Integer.parseInt(sYear);
		String sMonth = date.substring(4, 6);
		int mon = Integer.parseInt(sMonth);
		String sDay = date.substring(6, 8);
		int dday = Integer.parseInt(sDay);
		Calendar cal = Calendar.getInstance();
		cal.set(year, mon - 1, dday);
		return cal.getTime();
	}

	/**
	 * 得到2个日期之间间隔的天数
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int differ(String date1, String date2) {
		try {
			Date date11 = dateFormatter.parse(date1);
			Date date22 = dateFormatter.parse(date2);
			int day = differ(date11, date22);
			return day;
		} catch (Exception e) {
			throw new RuntimeException("日期格式错误！");
		}
	}

	private static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");

	private static int differ(Date date1, Date date2) {
		//long day = date1.getTime() / (24*60*60*1000) - date2.getTime() / (24*60*60*1000);
		long day = date2.getTime() / 86400000 - date1.getTime() / 86400000; //用立即数，减少乘法计算的开销
		return (int) day;
	}

	/**
	 * 功能：取同期 date为要转换的日期
	 */
	public static String getSameDate(String date) {
		if (date == null || "".equals(date)) {
			throw new RuntimeException("由于缺少必要的参数，系统无法进行制定的日期换算.");
		}
		try {
			String sYear = date.substring(0, 4);
			int year = Integer.parseInt(sYear);
			return String.valueOf(year - 1) + date.substring(4, date.length());
		} catch (Exception e) {
			throw new RuntimeException("进行日期运算时输入得参数不符合系统规格." + e);
		}
	}

	/**
	 * 获取输入 周的开始日期
	 *
	 * @param week
	 *            like 200412
	 * @return 8位日期 like 20040330
	 */
	public static String getWeekBeginDate(String week) {
		if (week == null || "".equals(week) || week.length() < 5) {
			throw new RuntimeException("由于缺少必要的参数，系统无法进行制定的周换算.");
		}
		try {
			Calendar cal = Calendar.getInstance();
			cal.setFirstDayOfWeek(Calendar.MONDAY);   // 设置一个星期的第一天为星期1，默认是星期日
			cal.set(Calendar.YEAR, Integer.parseInt(week.substring(0, 4)));
			cal.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(week.substring(4,
					week.length())));
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			return df.format(cal.getTime());
		} catch (Exception e) {
			throw new RuntimeException("进行周运算时输入得参数不符合系统规格." + e);
		}
	}
	/**
	 * 获取输入 周的结束日期
	 *
	 * @param week
	 *            like 200412
	 * @return 8位日期 like 20040330
	 */
	public static String getWeekEndDate(String week) {
		if (week == null || "".equals(week) || week.length() < 5) {
			throw new RuntimeException("由于缺少必要的参数，系统无法进行制定的周换算.");
		}
		try {
			Calendar cal = Calendar.getInstance();
			cal.setFirstDayOfWeek(Calendar.MONDAY);   // 设置一个星期的第一天为星期1，默认是星期日
			cal.set(Calendar.YEAR, Integer.parseInt(week.substring(0, 4)));
			cal.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(week.substring(4,
					week.length())));
			cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			return df.format(cal.getTime());
		} catch (Exception e) {
			throw new RuntimeException("进行周运算时输入得参数不符合系统规格." + e);
		}
	}


	/**
	 * 获取输入日期所在周的开始日期
	 * @author kuangzhy
	 * @paramweek
	 *            like 200412
	 * @return 8位日期 like 20040330
	 */
	public static String getWeekBeginDateWithDate(String date1) {
		if (date1 == null || "".equals(date1) || date1.length() != 8) {
			throw new RuntimeException("由于缺少必要的参数，系统无法进行制定的周换算.");
		}
		try {
			String year = date1.substring(0,4);
			String month = date1.substring(4,6);
			String day = date1.substring(6,8);
			date1 = year+"-"+month+"-"+day;
			Date dateNow = null;
			Calendar cal = Calendar.getInstance();
			cal.setFirstDayOfWeek(Calendar.MONDAY);   // 设置一个星期的第一天为星期1，默认是星期日
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			dateNow = format.parse(date1);
			cal.setTime(dateNow);
			cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			return df.format(cal.getTime());
		} catch (Exception e) {
			throw new RuntimeException("进行周运算时输入得参数不符合系统规格." + e);
		}
	}

	/**
	 * 获取输入日期所在周的开始日期
	 * @author kuangzhy
	 * @paramweek
	 *            like 200412
	 * @return 8位日期 like 20040330
	 */
	public static String getWeekEndDateWithDate(String date1) {
		if (date1 == null || "".equals(date1) || date1.length() != 8) {
			throw new RuntimeException("由于缺少必要的参数，系统无法进行制定的周换算.");
		}
		try {
			String year = date1.substring(0,4);
			String month = date1.substring(4,6);
			String day = date1.substring(6,8);
			date1 = year+"-"+month+"-"+day;
			Date dateNow = null;
			Calendar cal = Calendar.getInstance();
			cal.setFirstDayOfWeek(Calendar.MONDAY);   // 设置一个星期的第一天为星期1，默认是星期日
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			dateNow = format.parse(date1);
			cal.setTime(dateNow);
			cal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			return df.format(cal.getTime());
		} catch (Exception e) {
			throw new RuntimeException("进行周运算时输入得参数不符合系统规格." + e);
		}
	}

	/**
	 * 获取自然月的第一天日期 "YYYYMMDD"
	 * @param date1:"201201" 或者 "20120102"
	 * @return
	 */
	public static String getFirstDayOfMonth(String date1) {
		if (date1 == null || "".equals(date1) || date1.length() < 6
				|| date1.length() == 7 || date1.length() > 8) {
			throw new RuntimeException("传入输入日期有误！");
		}
		try {
			String month1 = date1.substring(0, 6);
			return month1+"01";
		} catch (Exception e) {
			throw new RuntimeException("进行日期运算时输入得参数不符合系统规格." + e);
		}
	}

	/**
	 * 获取自然月的最后一天日期 "YYYYMMDD"
	 *
	 * @param date1
	 *            :"201201" 或者 "20120102"
	 * @return
	 */
	public static String getLastDayOfMonth(String date1) {
		try {
			if(date1.length()==6) {date1=date1+"01";}
			Date date =formatter.parse(date1);
			Calendar instance = Calendar.getInstance();
			instance.setTime(date);
			int   lastDay   =   instance.getActualMaximum(Calendar.DAY_OF_MONTH);
			return date1.substring(0,6)+lastDay;
		} catch (ParseException e) {
			throw new RuntimeException("传入日期出错." + e);
		}
	}

	/**
	 * 获取当前月份 返回6位 格式 "yyyyMM"
	 *
	 * @return
	 */
	public static String getNowMonth() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMM");
		Date today = new Date();
		return sf.format(today);

	}

	/**
	 * @param获取当前系统时间 返回 yyyyMMddHHmmss
	 * @return
	 */
	public static String getCurrentDayTime() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		return df.format(date);
	}

	/**
	 * 获取输入日期的下一天 返回 8位 like 20050101
	 *
	 * @paramtoday
	 * @return
	 */
	public static String getNextDay(String day) {
		return getNextDay(day, 1);
	}

	/**
	 * 获取输入日期的下 n 天 返回 8位 like 20050101
	 *
	 * @param day
	 * @param n
	 * @return
	 */
	public static String getNextDay(String day, int n) {
		if (day == null || "".equals(day) || day.length() != 8) {
			throw new RuntimeException("由于缺少必要的参数，系统无法进行制定的日期换算.");
		}
		try {
			String sYear = day.substring(0, 4);
			int year = Integer.parseInt(sYear);
			String sMonth = day.substring(4, 6);
			int month = Integer.parseInt(sMonth);
			String sDay = day.substring(6, 8);
			int dday = Integer.parseInt(sDay);
			Calendar cal = Calendar.getInstance();
			cal.set(year, month - 1, dday);
			cal.add(Calendar.DATE, n);
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			return df.format(cal.getTime());
		} catch (Exception e) {
			throw new RuntimeException("进行日期运算时输入得参数不符合系统规格." + e);
		}
	}

	/**
	 * 获取输入 日期的前n年同期，如去年同期，如：2009-->2008，200904-->200804,20090410-->20080410
	 *
	 * @parammonth
	 *            like 200404
	 * @param n
	 * @return
	 */
	public static String getPreYearSamePeriod(String date,int n)
	{
		int year=Integer.parseInt(date.substring(0,4));
		int month=1,day=0;
		if(date.length()==4) {
			month=1;
			day=1;
		}
		if(date.length()==6) {
			month=Integer.parseInt(date.substring(4));
			day=1;
		}
		if(date.length()==8) {
			month=Integer.parseInt(date.substring(4,6));
			day=Integer.parseInt(date.substring(6));;
		}
		month--;
		GregorianCalendar aGregorianCalendar = new GregorianCalendar(year,month,day);
		aGregorianCalendar.set(Calendar.YEAR, aGregorianCalendar.get(Calendar.YEAR) - n);
		SimpleDateFormat aSimpleDateFormat = new SimpleDateFormat("yyyyMMdd");

		String currentYearAndMonth = aSimpleDateFormat
				.format(aGregorianCalendar.getTime());
		return currentYearAndMonth.substring(0,date.length());


	}

	/**
	 * 获取输入 月份的下 n 月份 返回 6位 like 201312
	 * @author kuangzhy
	 * @param month
	 *            like 200404
	 * @param n
	 * @return
	 */
	public static String getNextMonth(String month, int n) {
		if (month == null || "".equals(month) || month.length() != 6) {
			throw new RuntimeException("由于缺少必要的参数，系统无法进行制定的月份换算.");
		}
		try {
			String sYear = month.substring(0, 4);
			int year = Integer.parseInt(sYear);
			String sMonth = month.substring(4, 6);
			int mon = Integer.parseInt(sMonth);
			Calendar cal = Calendar.getInstance();
			cal.set(year, mon - 1, 1);
			cal.add(Calendar.MARCH, n);
			SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
			return df.format(cal.getTime());
		} catch (Exception e) {
			throw new RuntimeException("进行月份运算时输入得参数不符合系统规格." + e);
		}
	}

	/**
	 * @author kuangzhy
	 * @param date1 date2
	 * 计算两个月份之间月数
	 */
	public static int getMonthBetween(String date1,String date2){
		if (date1 == null || date1.length() != 6 ||date2 == null || date2.length() != 6 ) {
			throw new RuntimeException("由于缺少必要的参数，输入的月份出错时出错！");
		}
		String year1 = date1.substring(0,4);
		String month1 = date1.substring(4,6);
		int yearInt1 = Integer.parseInt(year1);
		int monthInt1 = Integer.parseInt(month1);
		if(monthInt1>12||monthInt1<1){
			throw new RuntimeException("输入的月份1不合法！");
		}
		String year2 = date2.substring(0,4);
		String month2 = date2.substring(4,6);
		int yearInt2 = Integer.parseInt(year2);
		int monthInt2 = Integer.parseInt(month2);
		if(monthInt2>12||monthInt2<1){
			throw new RuntimeException("输入的月份2不合法！");
		}
		System.out.println(yearInt1+"####1212221sssssss"+yearInt2);
		int months = 0;
		if(yearInt1==yearInt2){
			months=monthInt2-monthInt1;
		}else{
			months=(monthInt2-monthInt1)+(yearInt2-yearInt1)*12;
		}
		return months;
	}
	public static void main(String[] args) {
		try {


			String date1="20180806";
			System.out.println(getLastDayOfMonth(date1));
			System.out.println(getCurrentTime());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
