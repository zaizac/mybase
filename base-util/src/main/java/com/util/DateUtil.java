/**
 * Copyright 2019. 
 */
package com.util;


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
import org.joda.time.Hours;
import org.joda.time.Months;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.Years;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.util.constants.BaseConstants;


/**
 * The Class DateUtil.
 *
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public class DateUtil {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);


	/**
	 * Instantiates a new date util.
	 */
	private DateUtil() {
		throw new IllegalStateException("Utility class");
	}


	/**
	 * To get current server date (format yyyy-MM-dd).
	 *
	 * @return the current date
	 */
	public static String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstants.DT_YYYY_MM_DD_SLASH);
		Date uDate = new Date();
		return sdf.format(uDate);
	}


	/**
	 * Compare dates.
	 *
	 * @param startDate
	 *             the start date
	 * @param endDate
	 *             the end date
	 * @return int. If startDate is less than endDate returns -1, Else if
	 *         startDate is greater than endDate returns 1 Else both are equal
	 *         returns 0
	 */
	public static int compareDates(Date startDate, Date endDate) {
		if (startDate.before(endDate)) {
			return -1;
		} else if (startDate.after(endDate)) {
			return 1;
		} else {
			return 0;
		}
	}


	/**
	 * Gets the str.
	 *
	 * @param obj
	 *             the obj
	 * @return the str
	 */
	public static String getStr(Object obj) {
		if (obj != null) {
			return obj.toString().trim();
		} else {
			return "";
		}
	}


	/**
	 * Gets the date diff joda time in years.
	 *
	 * @param startDate
	 *             the start date
	 * @param endDate
	 *             the end date
	 * @return the date diff joda time in years
	 */
	public static int getDateDiffJodaTimeInYears(String startDate, String endDate) {
		return getDateDiffJodaTimeInYears(startDate, endDate, BaseConstants.DT_DD_MM_YYYY_SLASH);
	}


	/**
	 * Gets the date diff joda time in years.
	 *
	 * @param startDate
	 *             the start date
	 * @param endDate
	 *             the end date
	 * @param pattern
	 *             the pattern
	 * @return the date diff joda time in years
	 */
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
			LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
		}
		return diff;
	}


	/**
	 * Gets the date diff joda time in hours.
	 *
	 * @param startDate
	 *             the start date
	 * @param endDate
	 *             the end date
	 * @param pattern
	 *             the pattern
	 * @return the date diff joda time in hours
	 */
	public static int getDateDiffJodaTimeInHours(String startDate, String endDate, String pattern) {
		int diff = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			Date d1 = sdf.parse(startDate);
			Date d2 = sdf.parse(endDate);
			DateTime dt1 = new DateTime(d1);
			DateTime dt2 = new DateTime(d2);
			diff = Hours.hoursBetween(dt1, dt2).getHours();
		} catch (Exception e) {
			LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
		}
		return diff;
	}


	/**
	 * Gets the period year month day.
	 *
	 * @param date
	 *             the date
	 * @return the period year month day
	 */
	public static String getPeriodYearMonthDay(Date date) {
		DateTime endDt = new DateTime(new Date());
		DateTime startDt = new DateTime(date);
		Period p = new Period(startDt, endDt);
		String period = p.getYears() + "y" + p.getMonths() + "m" + p.getDays() + "d";
		return period.replace("-", "");
	}


	/**
	 * *********************** available FOR JDK 8 ****************************
	 * public static String getPeriodInYearMonthDay(LocalDate startDate,
	 * LocalDate endDate) { java.time.Period p =
	 * java.time.Period.between(startDate, endDate);
	 *
	 * String period = p.getYears() + "y" + p.getMonths() + "m" + p.getDays() +
	 * "d"; return period.replace("-", ""); } // example to call public static
	 * void main(String args[]) { LocalDate birthDate = LocalDate.of(1960,
	 * Month.JANUARY, 10); LocalDate today = LocalDate.now();
	 *
	 * System.out.println("My DOB JDK 8: " + getPeriodInYearMonthDay(birthDate,
	 * today));
	 *
	 * Calendar bDay = new GregorianCalendar(1960, 0, 10);
	 * System.out.println("My DOB JDK 7: " +
	 * getPeriodYearMonthDay(bDay.getTime())); }
	 */

	/**
	 * This method below applies to JDK 8 and above by using
	 * java.util.LocalDate Example: LocalDate today = LocalDate.now();
	 * LocalDate birthday = LocalDate.of(1960, Month.JANUARY, 1);
	 *
	 * @param startDate
	 *             the start date
	 * @return String example 3y4m7d
	 */

	public static int getDateDiffJodaTimeInYears(Date startDate) {
		return getDateDiff(startDate, new Date(), BaseConstants.YEAR);
	}


	/**
	 * Gets the date diff joda time in years.
	 *
	 * @param startDate
	 *             the start date
	 * @param endDate
	 *             the end date
	 * @return the date diff joda time in years
	 */
	public static int getDateDiffJodaTimeInYears(Date startDate, Date endDate) {
		return getDateDiff(startDate, endDate, BaseConstants.YEAR);
	}


	/**
	 * Gets the date diff joda time in months.
	 *
	 * @param startDate
	 *             the start date
	 * @param endDate
	 *             the end date
	 * @return the date diff joda time in months
	 */
	public static int getDateDiffJodaTimeInMonths(String startDate, String endDate) {
		return getDateDiffJodaTimeInMonths(startDate, endDate, BaseConstants.DT_DD_MM_YYYY_SLASH);
	}


	/**
	 * Gets the date diff joda time in months.
	 *
	 * @param startDate
	 *             the start date
	 * @param endDate
	 *             the end date
	 * @param pattern
	 *             the pattern
	 * @return the date diff joda time in months
	 */
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
			LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
		}
		return diff;
	}


	/**
	 * Adds the year month days to date.
	 *
	 * @param year
	 *             the year
	 * @param month
	 *             the month
	 * @param day
	 *             the day
	 * @return the date
	 */
	public static Date addYearMonthDaysToDate(int year, int month, int day) {
		return addYearMonthDaysToDate(null, year, month, day);
	}


	/**
	 * Adds the year month days to date.
	 *
	 * @param date
	 *             the date
	 * @param year
	 *             the year
	 * @param month
	 *             the month
	 * @param day
	 *             the day
	 * @return the date
	 */
	public static Date addYearMonthDaysToDate(Date date, int year, int month, int day) {
		LOGGER.debug("addYearMonthDaysToDate >>> Date: {} - Year: {} - Month: {} - Day: {}", date, year, month, day);
		if (BaseUtil.isObjNull(date)) {
			date = new Date();
			LOGGER.debug("addYearMonthDaysToDate >>> Date is null, get current date: {}", date);
		}
		DateTime dt = new DateTime(date).plusYears(year).plusMonths(month).plusDays(day);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("addYearMonthDaysToDate >>> New Date: {}", dt);
		}
		return dt.toDate();
	}


	/**
	 * Minus year month days to date.
	 *
	 * @param year
	 *             the year
	 * @param month
	 *             the month
	 * @param day
	 *             the day
	 * @return the date
	 */
	public static Date minusYearMonthDaysToDate(int year, int month, int day) {
		return minusYearMonthDaysToDate(null, year, month, day);
	}


	/**
	 * Minus year month days to date.
	 *
	 * @param date
	 *             the date
	 * @param year
	 *             the year
	 * @param month
	 *             the month
	 * @param day
	 *             the day
	 * @return the date
	 */
	public static Date minusYearMonthDaysToDate(Date date, int year, int month, int day) {
		LOGGER.debug("minusYearMonthDaysToDate >>> Date: {} - Year: {} - Month: {} - Day: {}", date, year, month,
				day);
		if (BaseUtil.isObjNull(date)) {
			date = new Date();
			LOGGER.debug("minusYearMonthDaysToDate >>> Date is null, get current date: {}", date);
		}
		DateTime dt = new DateTime(date).minusYears(year).minusMonths(month).minusDays(day);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("minusYearMonthDaysToDate >>> New Date: {}", dt);
		}
		return dt.toDate();
	}


	/**
	 * Adds the hour minutes second to date.
	 *
	 * @param hours
	 *             the hours
	 * @param minutes
	 *             the minutes
	 * @param seconds
	 *             the seconds
	 * @return the date
	 */
	public static Date addHourMinutesSecondToDate(int hours, int minutes, int seconds) {
		return addHourMinutesSecondToDate(null, hours, minutes, seconds);
	}


	/**
	 * Adds the hour minutes second to date.
	 *
	 * @param date
	 *             the date
	 * @param hours
	 *             the hours
	 * @param minutes
	 *             the minutes
	 * @param seconds
	 *             the seconds
	 * @return the date
	 */
	public static Date addHourMinutesSecondToDate(Date date, int hours, int minutes, int seconds) {
		if (BaseUtil.isObjNull(date)) {
			date = new Date();
		}
		DateTime dt = new DateTime(date).plusHours(hours).plusMinutes(minutes).plusSeconds(seconds);
		return dt.toDate();
	}


	/**
	 * Minus hour minutes second to date.
	 *
	 * @param hours
	 *             the hours
	 * @param minutes
	 *             the minutes
	 * @param seconds
	 *             the seconds
	 * @return the date
	 */
	public static Date minusHourMinutesSecondToDate(int hours, int minutes, int seconds) {
		return minusHourMinutesSecondToDate(null, hours, minutes, seconds);
	}


	/**
	 * Minus hour minutes second to date.
	 *
	 * @param date
	 *             the date
	 * @param hours
	 *             the hours
	 * @param minutes
	 *             the minutes
	 * @param seconds
	 *             the seconds
	 * @return the date
	 */
	public static Date minusHourMinutesSecondToDate(Date date, int hours, int minutes, int seconds) {
		if (BaseUtil.isObjNull(date)) {
			date = new Date();
		}
		DateTime dt = new DateTime(date).minusHours(hours).minusMinutes(minutes).minusSeconds(seconds);
		return dt.toDate();
	}


	/**
	 * Gets the date diff joda time in months.
	 *
	 * @param date
	 *             the date
	 * @return the date diff joda time in months
	 */
	public static int getDateDiffJodaTimeInMonths(Date date) {
		return getDateDiffJodaTimeInMonths(new Date(), date);
	}


	/**
	 * Gets the date diff joda time in months.
	 *
	 * @param startDate
	 *             the start date
	 * @param endDate
	 *             the end date
	 * @return the date diff joda time in months
	 */
	public static int getDateDiffJodaTimeInMonths(Date startDate, Date endDate) {
		return getDateDiff(startDate, endDate, BaseConstants.MONTH);
	}


	/**
	 * Gets the date diff joda time in days.
	 *
	 * @param startDate
	 *             the start date
	 * @param endDate
	 *             the end date
	 * @return the date diff joda time in days
	 */
	public static int getDateDiffJodaTimeInDays(String startDate, String endDate) {
		int diff = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(BaseConstants.DT_DD_MM_YYYY_SLASH);
			Date d1 = sdf.parse(startDate);
			Date d2 = sdf.parse(endDate);
			diff = getDateDiffJodaTimeInDays(d1, d2);
		} catch (Exception e) {
			LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
		}
		return diff;
	}


	/**
	 * Gets the date diff joda time in days.
	 *
	 * @param startDate
	 *             the start date
	 * @param endDate
	 *             the end date
	 * @return the date diff joda time in days
	 */
	public static int getDateDiffJodaTimeInDays(Date startDate, Date endDate) {
		return getDateDiff(startDate, endDate, BaseConstants.DAY);
	}


	/**
	 * Gets the date diff.
	 *
	 * @param startDate
	 *             the start date
	 * @param endDate
	 *             the end date
	 * @param type
	 *             the type
	 * @return the date diff
	 */
	private static int getDateDiff(Date startDate, Date endDate, String type) {
		int diff = 0;
		try {
			DateTime dt1 = new DateTime(startDate);
			DateTime dt2 = new DateTime(endDate);
			if (type.equalsIgnoreCase(BaseConstants.DAY)) {
				diff = Days.daysBetween(dt1, dt2).getDays();
			} else if (type.equalsIgnoreCase(BaseConstants.MONTH)) {
				diff = Months.monthsBetween(dt1, dt2).getMonths();
			} else if (type.equalsIgnoreCase(BaseConstants.YEAR)) {
				diff = Years.yearsBetween(dt1, dt2).getYears();
			}
		} catch (Exception e) {
			LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
		}
		return diff;
	}


	/**
	 * Gets the date diff joda time in days hours minutes.
	 *
	 * @param startDate
	 *             the start date
	 * @param endDate
	 *             the end date
	 * @return the date diff joda time in days hours minutes
	 */
	public static String getDateDiffJodaTimeInDaysHoursMinutes(Date startDate, Date endDate) {
		Period period = new Period(startDate.getTime(), endDate.getTime());
		PeriodFormatter formatter = new PeriodFormatterBuilder().appendDays().appendSuffix(" days\n").appendHours()
				.appendSuffix(" hours\n").appendMinutes().appendSuffix(" minutes\n").printZeroNever().toFormatter();
		return formatter.print(period);
	}


	/**
	 * Gets the format date with slash.
	 *
	 * @param date
	 *             the date
	 * @return the format date with slash
	 */
	public static String getFormatDateWithSlash(Date date) {
		return getFormatDate(date, BaseConstants.DT_DD_MM_YYYY_SLASH);
	}


	/**
	 * Gets the format date.
	 *
	 * @param date
	 *             the date
	 * @param pattern
	 *             the pattern
	 * @return the format date
	 * @throws ParseException
	 *              the parse exception
	 */
	public static Date getFormatDate(String date, String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		if (date != null) {
			return sdf.parse(date);
		} else {
			return null;
		}
	}


	/**
	 * Format timestamp.
	 *
	 * @param date
	 *             the date
	 * @param pattern
	 *             the pattern
	 * @return the string
	 */
	public static String formatTimestamp(Timestamp date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		if (date != null) {
			return sdf.format(date);
		} else {
			return null;
		}
	}


	/**
	 * Gets the format date.
	 *
	 * @param date
	 *             the date
	 * @param pattern
	 *             the pattern
	 * @return the format date
	 */
	public static String getFormatDate(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		if (date != null) {
			return sdf.format(date);
		} else {
			return "";
		}
	}


	/**
	 * Gets the SQL timestamp.
	 *
	 * @return the SQL timestamp
	 */
	public static Timestamp getSQLTimestamp() {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		return new java.sql.Timestamp(now.getTime());
	}


	/**
	 * Adds the day to timestamp.
	 *
	 * @param date
	 *             the date
	 * @param addDay
	 *             the add day
	 * @return the java.util. date
	 */
	public static java.util.Date addDayToTimestamp(Date date, Integer addDay) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, addDay);
		return cal.getTime();
	}


	/**
	 * Gets the XML gregorian calendar.
	 *
	 * @return the XML gregorian calendar
	 */
	public static XMLGregorianCalendar getXMLGregorianCalendar() {
		try {
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar());
		} catch (DatatypeConfigurationException e) {
			LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
			return null;
		}
	}


	/**
	 * To calendar.
	 *
	 * @param date
	 *             the date
	 * @return the calendar
	 */
	public static Calendar toCalendar(final Date date) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}


	/**
	 * To calendar.
	 *
	 * @param millis
	 *             the millis
	 * @return the calendar
	 */
	public static Calendar toCalendar(final long millis) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar;
	}


	/**
	 * To xml gregorian calendar.
	 *
	 * @param date
	 *             the date
	 * @return the XML gregorian calendar
	 */
	public static XMLGregorianCalendar toXmlGregorianCalendar(final Date date) {
		return toXmlGregorianCalendar(date.getTime());
	}


	/**
	 * To xml gregorian calendar.
	 *
	 * @param date
	 *             the date
	 * @return the XML gregorian calendar
	 */
	public static XMLGregorianCalendar toXmlGregorianCalendar(final long date) {
		try {
			final GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTimeInMillis(date);
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
		} catch (final DatatypeConfigurationException ex) {
			LOGGER.error(BaseConstants.LOG_EXCEPTION, ex.getMessage());
			return null;
		}
	}


	/**
	 * Gets the XML gregorian calendar.
	 *
	 * @param timestamp
	 *             the timestamp
	 * @return the XML gregorian calendar
	 */
	public static XMLGregorianCalendar getXMLGregorianCalendar(Timestamp timestamp) {
		try {
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTimeZone(TimeZone.getTimeZone("GMT+02:00"));
			gc.setTimeInMillis(timestamp.getTime());
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
		} catch (Exception ex) {
			LOGGER.error(BaseConstants.LOG_EXCEPTION, ex.getMessage());
			return null;
		}
	}


	/**
	 * Gets the format big endian time with dash.
	 *
	 * @param date
	 *             the date
	 * @return the format big endian time with dash
	 */
	public static String getFormatBigEndianTimeWithDash(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstants.DT_YYYY_MM_DD_DASH_TIME_S);
		if (date != null) {
			return sdf.format(date);
		} else {
			return "";
		}
	}


	/**
	 * Gets the format big endian with dash.
	 *
	 * @param date
	 *             the date
	 * @return the format big endian with dash
	 */
	public static String getFormatBigEndianWithDash(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstants.DT_YYYY_MM_DD_DASH);
		if (date != null) {
			return sdf.format(date);
		} else {
			return "";
		}
	}


	/**
	 * Gets the format big endian.
	 *
	 * @param date
	 *             the date
	 * @return the format big endian
	 */
	public static String getFormatBigEndian(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstants.SIMPLE_DATE_FORMAT);
		if (date != null) {
			return sdf.format(date);
		} else {
			return "";
		}
	}


	/**
	 * Gets the format big endian with slash.
	 *
	 * @param date
	 *             the date
	 * @return the format big endian with slash
	 */
	public static String getFormatBigEndianWithSlash(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstants.DT_DD_MM_YYYY_SLASH);
		if (date != null) {
			return sdf.format(date);
		} else {
			return "";
		}
	}


	/**
	 * Convert str date to date.
	 *
	 * @param strDate
	 *             the str date
	 * @return the java.util. date
	 */
	public static java.util.Date convertStrDateToDate(String strDate) {
		return convertStrDateToDate(strDate, "");
	}


	/**
	 * Convert str date to date.
	 *
	 * @param strDate
	 *             the str date
	 * @param formatStrDate
	 *             the format str date
	 * @return the java.util. date
	 */
	public static java.util.Date convertStrDateToDate(String strDate, String formatStrDate) {

		if (getStr(formatStrDate).equals("")) {
			formatStrDate = BaseConstants.DT_YYYY_MM_DD_DASH;
		}
		SimpleDateFormat sdfStrDate = new SimpleDateFormat(formatStrDate);
		java.util.Date date = new Date();
		try {
			date = sdfStrDate.parse(strDate);
			return date;
		} catch (Exception e) {
			LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
			return null;
		}
	}


	/**
	 * Gets the current year.
	 *
	 * @return the current year
	 */
	public static String getCurrentYear() {
		return BaseUtil.getStr(new DateTime().getYear());
	}


	/**
	 * Convert date 2 sql time stamp.
	 *
	 * @param date
	 *             the date
	 * @return the timestamp
	 */
	public static Timestamp convertDate2SqlTimeStamp(Date date) {
		return new Timestamp(date.getTime());
	}


	/**
	 * Convert timestamp to date.
	 *
	 * @param timestamp
	 *             the timestamp
	 * @return the date
	 */
	public static Date convertTimestampToDate(Timestamp timestamp) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timestamp.getTime());
		return calendar.getTime();
	}


	/**
	 * Check bday 45 y 1 d.
	 *
	 * @param bday
	 *             the bday
	 * @return true, if successful
	 */
	public static boolean checkBday45y1d(String bday) {
		return checkBday45y1d(bday, getFormatDate(new Date(), BaseConstants.DT_YYYY_MM_DD_DASH),
				BaseConstants.DT_YYYY_MM_DD_DASH);
	}


	/**
	 * Check bday 45 y 1 d.
	 *
	 * @param bday
	 *             the bday
	 * @param compDate
	 *             the comp date
	 * @return true, if successful
	 */
	public static boolean checkBday45y1d(String bday, String compDate) {
		return checkBday45y1d(bday, compDate, BaseConstants.DT_YYYY_MM_DD_DASH);
	}


	/**
	 * Check bday 45 y 1 d.
	 *
	 * @param bday
	 *             the bday
	 * @param compDate
	 *             the comp date
	 * @param pattern
	 *             the pattern
	 * @return true, if successful
	 */
	public static boolean checkBday45y1d(String bday, String compDate, String pattern) {
		Integer month = 0;
		Integer day = 0;
		try {
			DateTime dt1 = new DateTime(getFormatDate(bday, pattern));
			DateTime dt2 = new DateTime(getFormatDate(compDate, pattern));
			Period p = new Period(dt1, dt2, PeriodType.yearMonthDayTime());
			LOGGER.debug("Year: {} - Month: {} - Day: {}", p.getYears(), p.getMonths(), p.getDays());
			month = p.getMonths();
			day = p.getDays();
		} catch (Exception e) {
			LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
		}

		boolean checkBday45y1d = false;
		if (month != 0 || day != 0) {
			checkBday45y1d = true;
		}
		return checkBday45y1d;
	}


	/**
	 * Convert date.
	 *
	 * @param date
	 *             the date
	 * @param pattern
	 *             the pattern
	 * @return the string
	 */
	public static String convertDate(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String convertDate = "";
		if (date != null) {
			convertDate = sdf.format(date);
		}
		return convertDate;
	}


	/**
	 * Gets the age from dob.
	 *
	 * @param date
	 *             the date
	 * @return the age from dob
	 */
	public static Integer getAgeFromDob(Date date) {
		Calendar dob = Calendar.getInstance();
		dob.setTime(date);
		Calendar today = Calendar.getInstance();
		int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
		if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
			age--;
		}

		return age;
	}


	/**
	 * Gets the dob from age.
	 *
	 * @param age
	 *             the age
	 * @return the dob from age
	 */
	public static Date getDobFromAge(Integer age) {
		Calendar today = Calendar.getInstance();
		int curYear = today.get(Calendar.YEAR);
		int difYear = curYear - age;
		today.set(Calendar.YEAR, difYear);
		return today.getTime();
	}


	// checks date with current date whether expired
	// Returns true if expired
	/**
	 * Check date expire.
	 *
	 * @param expiryDate
	 *             the expiry date
	 * @return true, if successful
	 */
	// return false if not expired
	public static boolean checkDateExpire(Date expiryDate) {
		boolean valid = true;
		Integer val = compareDates(expiryDate, new Date());
		if (val == -1) {
			valid = true;
		} else {
			valid = false;
		}
		return valid;
	}


	/**
	 * To calculate minutes between first and last login attempt
	 *
	 * Return: minutes.
	 *
	 * @param currenttime
	 *             the currenttime
	 * @param pretimestp
	 *             the pretimestp
	 * @return the long
	 */
	public static long convTimestampBetweenFirstAndLastLoginAttempt(Timestamp currenttime, Timestamp pretimestp) {
		long curTime = (currenttime.getTime() / 1000);
		long preTime = (pretimestp.getTime() / 1000);
		return ((curTime - preTime) / 60);
	}
}