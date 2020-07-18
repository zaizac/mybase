/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.sdk.util;


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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baseboot.idm.sdk.constants.BaseConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
public class DateUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

	private static final String LOG_EXCEPTION = "Exception: {}";


	private DateUtil() {
		throw new IllegalStateException("Utility class");
	}


	/**
	 * To get current server date (format yyyy-MM-dd)
	 */
	public static String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstants.DT_YYYY_MM_DD_SLASH);
		Date uDate = new Date();
		return sdf.format(uDate);
	}


	/**
	 * @param Date
	 *             startDate, Date endDate
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


	public static String getStr(Object obj) {
		if (obj != null) {
			return obj.toString().trim();
		} else {
			return "";
		}
	}


	public static int getDateDiffJodaTimeInYears(String startDate, String endDate) {
		return getDateDiffJodaTimeInYears(startDate, endDate, BaseConstants.DT_DD_MM_YYYY_SLASH);
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
			LOGGER.error(LOG_EXCEPTION, e.getMessage());
		}
		return diff;
	}


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
			LOGGER.error(LOG_EXCEPTION, e.getMessage());
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
		return getDateDiff(startDate, new Date(), BaseConstants.YEAR);
	}


	public static int getDateDiffJodaTimeInYears(Date startDate, Date endDate) {
		return getDateDiff(startDate, endDate, BaseConstants.YEAR);
	}


	public static int getDateDiffJodaTimeInMonths(String startDate, String endDate) {
		return getDateDiffJodaTimeInMonths(startDate, endDate, BaseConstants.DT_DD_MM_YYYY_SLASH);
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
			LOGGER.error(LOG_EXCEPTION, e.getMessage());
		}
		return diff;
	}


	public static Date addYearMonthDaysToDate(int year, int month, int day) {
		return addYearMonthDaysToDate(null, year, month, day);
	}


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


	public static Date minusYearMonthDaysToDate(int year, int month, int day) {
		return minusYearMonthDaysToDate(null, year, month, day);
	}


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


	public static Date addHourMinutesSecondToDate(int hours, int minutes, int seconds) {
		return addHourMinutesSecondToDate(null, hours, minutes, seconds);
	}


	public static Date addHourMinutesSecondToDate(Date date, int hours, int minutes, int seconds) {
		if (BaseUtil.isObjNull(date)) {
			date = new Date();
		}
		DateTime dt = new DateTime(date).plusHours(hours).plusMinutes(minutes).plusSeconds(seconds);
		return dt.toDate();
	}


	public static Date minusHourMinutesSecondToDate(int hours, int minutes, int seconds) {
		return minusHourMinutesSecondToDate(null, hours, minutes, seconds);
	}


	public static Date minusHourMinutesSecondToDate(Date date, int hours, int minutes, int seconds) {
		if (BaseUtil.isObjNull(date)) {
			date = new Date();
		}
		DateTime dt = new DateTime(date).minusHours(hours).minusMinutes(minutes).minusSeconds(seconds);
		return dt.toDate();
	}


	public static int getDateDiffJodaTimeInMonths(Date date) {
		return getDateDiffJodaTimeInMonths(new Date(), date);
	}


	public static int getDateDiffJodaTimeInMonths(Date startDate, Date endDate) {
		return getDateDiff(startDate, endDate, BaseConstants.MONTH);
	}


	public static int getDateDiffJodaTimeInDays(String startDate, String endDate) {
		int diff = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(BaseConstants.DT_DD_MM_YYYY_SLASH);
			Date d1 = sdf.parse(startDate);
			Date d2 = sdf.parse(endDate);
			diff = getDateDiffJodaTimeInDays(d1, d2);
		} catch (Exception e) {
			LOGGER.error(LOG_EXCEPTION, e.getMessage());
		}
		return diff;
	}


	public static int getDateDiffJodaTimeInDays(Date startDate, Date endDate) {
		return getDateDiff(startDate, endDate, BaseConstants.DAY);
	}


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
			LOGGER.error(LOG_EXCEPTION, e.getMessage());
		}
		return diff;
	}


	public static String getFormatDateWithSlash(Date date) {
		return getFormatDate(date, BaseConstants.DT_DD_MM_YYYY_SLASH);
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
		return new java.sql.Timestamp(now.getTime());
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
			LOGGER.error(LOG_EXCEPTION, e.getMessage());
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
			LOGGER.error(LOG_EXCEPTION, ex.getMessage());
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
			LOGGER.error(LOG_EXCEPTION, ex.getMessage());
			return null;
		}
	}


	public static String getFormatBigEndianTimeWithDash(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstants.DT_YYYY_MM_DD_DASH_TIME_S);
		if (date != null) {
			return sdf.format(date);
		} else {
			return "";
		}
	}


	public static String getFormatBigEndianWithDash(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstants.DT_YYYY_MM_DD_DASH);
		if (date != null) {
			return sdf.format(date);
		} else {
			return "";
		}
	}


	public static String getFormatBigEndian(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstants.SIMPLE_DATE_FORMAT);
		if (date != null) {
			return sdf.format(date);
		} else {
			return "";
		}
	}


	public static String getFormatBigEndianWithSlash(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstants.DT_DD_MM_YYYY_SLASH);
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

		if (getStr(formatStrDate).equals("")) {
			formatStrDate = "yyyy-MM-dd";
		}
		SimpleDateFormat sdfStrDate = new SimpleDateFormat(formatStrDate);
		java.util.Date date = new Date();
		try {
			date = sdfStrDate.parse(strDate);
			return date;
		} catch (Exception e) {
			LOGGER.error(LOG_EXCEPTION, e.getMessage());
			return null;
		}
	}


	public static String getCurrentYear() {
		return BaseUtil.getStr(new DateTime().getYear());
	}


	public static Timestamp convertDate2SqlTimeStamp(Date date) {
		return new Timestamp(date.getTime());
	}


	public static Date convertTimestampToDate(Timestamp timestamp) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timestamp.getTime());
		return calendar.getTime();
	}


	public static boolean checkBday45y1d(String bday) {
		return checkBday45y1d(bday, getFormatDate(new Date(), BaseConstants.DT_YYYY_MM_DD_DASH),
				BaseConstants.DT_YYYY_MM_DD_DASH);
	}


	public static boolean checkBday45y1d(String bday, String compDate) {
		return checkBday45y1d(bday, compDate, BaseConstants.DT_YYYY_MM_DD_DASH);
	}


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
			LOGGER.error(LOG_EXCEPTION, e.getMessage());
		}

		boolean checkDay = false;
		if (month != 0 || day != 0) {
			checkDay = true;
		}
		return checkDay;
	}

}