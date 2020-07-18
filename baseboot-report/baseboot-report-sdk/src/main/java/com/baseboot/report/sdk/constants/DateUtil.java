/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.report.sdk.constants;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.Years;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class DateUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);


	/**
	 * To get current server date (format yyyy-MM-dd)
	 */
	public static String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat(ReportConstants.DT_YYYY_MM_DD_Slash);
		Date uDate = new Date();
		return sdf.format(uDate);
	}


	public static String getCurrentDate(String formatStrDate) {
		Calendar cal = Calendar.getInstance();
		return new SimpleDateFormat(formatStrDate).format(cal.getTime());
	}


	/**
	 * @param Date
	 *             startDate, Date endDate
	 * @return int. If startDate is less than endDate returns -1, Else if
	 *         startDate is greater than endDate returns 1 Else both are equal
	 *         returns 0
	 *
	 */

	public static String getCurrentDay() {
		return String.valueOf(new DateTime().getDayOfMonth());
	}


	public static int compareDates(Date startDate, Date endDate) {
		if (startDate.before(endDate))
			return -1;
		else if (startDate.after(endDate))
			return 1;
		else
			return 0;
	}


	public static String getStr(Object obj) {
		if (obj != null) {

			return obj.toString().trim();
		} else {
			return "";
		}
	}


	public static int getDateDiffJodaTimeInYears(String startDate, String endDate) {
		return getDateDiffJodaTimeInYears(startDate, endDate, ReportConstants.DT_DD_MM_YYYY_Slash);
	}


	public static int getDateDiffJodaTimeInYears(String startDate, String endDate, String pattern) {
		int diff = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			Date d1 = sdf.parse(startDate);
			Date d2 = sdf.parse(endDate);
			DateTime dt1 = new DateTime(d1);
			DateTime dt2 = new DateTime(d2);
			diff = Years.yearsBetween(dt1, dt2).getYears();
		} catch (Exception e) {
		}
		return diff;
	}


	public static String getPeriodYearMonthDay(Date date) {
		DateTime endDt = new DateTime(new Date());
		DateTime startDt = new DateTime(date);
		Period p = new Period(endDt, startDt);
		String period = p.getYears() + "y" + p.getMonths() + "m" + p.getDays() + "d";
		return period.replace("-", "");
	}


	public static int getDateDiffJodaTimeInYears(Date startDate) {
		return getDateDiff(startDate, new Date(), ReportConstants.YEAR);
	}


	public static int getDateDiffJodaTimeInYears(Date startDate, Date endDate) {
		return getDateDiff(startDate, endDate, ReportConstants.YEAR);
	}


	public static int getDateDiffJodaTimeInMonths(String startDate, String endDate) {
		return getDateDiffJodaTimeInMonths(startDate, endDate, ReportConstants.DT_DD_MM_YYYY_Slash);
	}


	public static int getDateDiffJodaTimeInMonths(String startDate, String endDate, String pattern) {
		int diff = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			Date d1 = sdf.parse(startDate);
			Date d2 = sdf.parse(endDate);
			DateTime dt1 = new DateTime(d1);
			DateTime dt2 = new DateTime(d2);
			diff = Months.monthsBetween(dt1, dt2).getMonths();
		} catch (Exception e) {
		}
		return diff;
	}


	public static Date addYearMonthDaysToDate(int year, int month, int day) {
		return addYearMonthDaysToDate(null, year, month, day);
	}


	public static Date addYearMonthDaysToDate(Date date, int year, int month, int day) {
		LOGGER.debug("addYearMonthDaysToDate >>> Date: " + date + " - Year: " + year + " - Month: " + month
				+ " - Day: " + day);
		if (date != null) {
			date = new Date();
			LOGGER.debug("addYearMonthDaysToDate >>> Date is null, get current date: " + date);
		}
		DateTime dt = new DateTime(date).plusYears(year).plusMonths(month).plusDays(day);
		LOGGER.debug("addYearMonthDaysToDate >>> New Date: " + dt.toString());
		return dt.toDate();
	}


	public static Date minusYearMonthDaysToDate(int year, int month, int day) {
		return minusYearMonthDaysToDate(null, year, month, day);
	}


	public static Date minusYearMonthDaysToDate(Date date, int year, int month, int day) {
		LOGGER.debug("minusYearMonthDaysToDate >>> Date: " + date + " - Year: " + year + " - Month: " + month
				+ " - Day: " + day);
		if (date != null) {
			date = new Date();
			LOGGER.debug("minusYearMonthDaysToDate >>> Date is null, get current date: " + date);
		}
		DateTime dt = new DateTime(date).minusYears(year).minusMonths(month).minusDays(day);
		LOGGER.debug("minusYearMonthDaysToDate >>> New Date: " + dt.toString());
		return dt.toDate();
	}


	public static Date addHourMinutesSecondToDate(int hours, int minutes, int seconds) {
		return addHourMinutesSecondToDate(null, hours, minutes, seconds);
	}


	public static Date addHourMinutesSecondToDate(Date date, int hours, int minutes, int seconds) {
		if (date != null) {
			date = new Date();
		}
		DateTime dt = new DateTime(date).plusHours(hours).plusMinutes(minutes).plusSeconds(seconds);
		return dt.toDate();
	}


	public static Date minusHourMinutesSecondToDate(int hours, int minutes, int seconds) {
		return minusHourMinutesSecondToDate(null, hours, minutes, seconds);
	}


	public static Date minusHourMinutesSecondToDate(Date date, int hours, int minutes, int seconds) {
		if (date != null) {
			date = new Date();
		}
		DateTime dt = new DateTime(date).minusHours(hours).minusMinutes(minutes).minusSeconds(seconds);
		return dt.toDate();
	}


	public static int getDateDiffJodaTimeInMonths(Date date) {
		return getDateDiffJodaTimeInMonths(new Date(), date);
	}


	public static int getDateDiffJodaTimeInMonths(Date startDate, Date endDate) {
		return getDateDiff(startDate, endDate, ReportConstants.MONTH);
	}


	public static int getDateDiffJodaTimeInDays(String startDate, String endDate) {
		int diff = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(ReportConstants.DT_DD_MM_YYYY_Slash);
			Date d1 = sdf.parse(startDate);
			Date d2 = sdf.parse(endDate);
			diff = getDateDiffJodaTimeInDays(d1, d2);
		} catch (Exception e) {
		}
		return diff;
	}


	public static int getDateDiffJodaTimeInDays(Date startDate, Date endDate) {
		return getDateDiff(startDate, endDate, ReportConstants.DAY);
	}


	private static int getDateDiff(Date startDate, Date endDate, String type) {
		int diff = 0;
		try {
			DateTime dt1 = new DateTime(startDate);
			DateTime dt2 = new DateTime(endDate);
			if (type.equalsIgnoreCase(ReportConstants.DAY)) {
				diff = Days.daysBetween(dt1, dt2).getDays();
			} else if (type.equalsIgnoreCase(ReportConstants.MONTH)) {
				diff = Months.monthsBetween(dt1, dt2).getMonths();
			} else if (type.equalsIgnoreCase(ReportConstants.YEAR)) {
				diff = Years.yearsBetween(dt1, dt2).getYears();
			}
		} catch (Exception e) {
		}
		return diff;
	}


	public static String getFormatDateWithSlash(Date date) {
		return getFormatDate(date, ReportConstants.DT_DD_MM_YYYY_Slash);
	}


	public static Date getFormatDate(String date, String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		if (date != null) {
			return sdf.parse(date);
		} else {
			return null;
		}
	}


	public static String getFormatDate(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		if (date != null) {
			return sdf.format(date);
		} else {
			return "";
		}
	}


	public static Timestamp getSQLTimestamp() {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

		return currentTimestamp;
	}


	public static java.util.Date addDayToTimestamp(Date date, Integer addDay) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, addDay);

		return cal.getTime();
	}


	public static XMLGregorianCalendar getXMLGregorianCalendar() {
		try {
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar());
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
			return null;
		}
	}


	public static Calendar toCalendar(final Date date) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}


	public static Calendar toCalendar(final long millis) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar;
	}


	public static XMLGregorianCalendar toXmlGregorianCalendar(final Date date) {
		return toXmlGregorianCalendar(date.getTime());
	}


	public static XMLGregorianCalendar toXmlGregorianCalendar(final long date) {
		try {
			final GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTimeInMillis(date);
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
		} catch (final DatatypeConfigurationException ex) {
			System.out.println("Unable to convert date '%s' to an XMLGregorianCalendar object");
			return null;
		}
	}


	public static XMLGregorianCalendar getXMLGregorianCalendar(Timestamp timestamp) {
		try {
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTimeZone(TimeZone.getTimeZone("GMT+02:00"));
			gc.setTimeInMillis(timestamp.getTime());
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
		} catch (Exception ex) {
			return null;
		}
	}


	public static String getFormatBigEndianTimeWithDash(Date date) {
		String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		if (date != null) {
			return sdf.format(date);
		} else {
			return "";
		}
	}


	public static String getFormatBigEndianWithDash(Date date) {
		String DATE_FORMAT = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		if (date != null) {
			return sdf.format(date);
		} else {
			return "";
		}
	}


	public static String getFormatBigEndian(Date date) {
		String DATE_FORMAT = "yyyyMMdd";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		if (date != null) {
			return sdf.format(date);
		} else {
			return "";
		}
	}


	public static String getFormatBigEndianWithSlash(Date date) {
		String DATE_FORMAT = "dd/MM/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		if (date != null) {
			return sdf.format(date);
		} else {
			return "";
		}
	}


	public static java.util.Date convertStrDateToDate(String strDate) {
		return convertStrDateToDate(strDate, "");
	}


	public static java.util.Date convertStrDateToDate(String strDate, String formatStrDate) {
		if (getStr(formatStrDate).equals(""))
			formatStrDate = "yyyy-MM-dd";
		SimpleDateFormat sdfStrDate = new SimpleDateFormat(formatStrDate);
		java.util.Date date = new Date();
		try {
			date = sdfStrDate.parse(strDate);
			return date;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}


	public static String convertDate(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		if (date != null) {
			return sdf.format(date);
		} else {
			return "";
		}
	}


	public static String getCurrentYear() {
		return String.valueOf(new DateTime().getYear());
	}


	public static Timestamp convertDate2SqlTimeStamp(Date date) {
		Timestamp sq = new Timestamp(date.getTime());
		return sq;
	}


	public static Date convertTimestampToDate(Timestamp timestamp) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timestamp.getTime());
		return calendar.getTime();
	}


	public static boolean checkBday45y1d(String bday) {
		return checkBday45y1d(bday, getFormatDate(new Date(), ReportConstants.DT_YYYY_MM_DD_Dash),
				ReportConstants.DT_YYYY_MM_DD_Dash);
	}


	public static boolean checkBday45y1d(String bday, String compDate) {
		return checkBday45y1d(bday, compDate, ReportConstants.DT_YYYY_MM_DD_Dash);
	}


	public static boolean checkBday45y1d(String bday, String compDate, String pattern) {
		Integer month = 0, day = 0;
		try {
			DateTime dt1 = new DateTime(getFormatDate(bday, pattern));
			DateTime dt2 = new DateTime(getFormatDate(compDate, pattern));
			Period p = new Period(dt1, dt2, PeriodType.yearMonthDayTime());
			System.out.println("Year: " + p.getYears());
			System.out.println("MOnth: " + p.getMonths());
			System.out.println("Day: " + p.getDays());
			month = p.getMonths();
			day = p.getDays();
		} catch (Exception e) {
		}

		if (month != 0 || day != 0) {
			return true;
		}
		return false;
	}


	public static void main(String[] args) throws ParseException {
		/*
		 * SimpleDateFormat sdf = new
		 * SimpleDateFormat(BaseConstants.DT_DD_MM_YYYY_Slash); String bdt =
		 * "26/07/1970"; DateTime dt = new DateTime(new Date()); DateTime dt1
		 * = new DateTime(); try { dt1 = new DateTime(sdf.parse(bdt)); } catch
		 * (ParseException e) { e.printStackTrace(); } Period p = new
		 * Period(dt1, dt); String period = p.getYears() + "y" + p.getMonths()
		 * + "m" + p.getDays() + "d"; System.out.println(period); int diff =
		 * Months.monthsBetween(dt, dt1).getMonths(); System.out.println(
		 * "Months :: " + diff); System.out.println(dt.getYear());
		 * System.out.println(dt.getDayOfMonth());
		 * System.out.println(dt.getMonthOfYear());
		 */
		Date expireDt = addYearMonthDaysToDate(0, 18, 6);
		Date blinkDt = addYearMonthDaysToDate(0, 19, 0);
		int expireDtDiffDays = getDateDiffJodaTimeInDays(expireDt,
				getFormatDate("28-2-2017", ReportConstants.DT_DD_MM_YYYY_Dash));
		System.out.println("expireDtDiffDays >>> " + expireDtDiffDays);
		int blnkDtDiffDays = getDateDiffJodaTimeInDays(expireDt, blinkDt);
		System.out.println("blnkDtDiffDays >>> " + blnkDtDiffDays);
		if (expireDtDiffDays >= 0 && expireDtDiffDays <= blnkDtDiffDays) {
			System.out.println("TEXT BLINK!!!");
		}

	}

}