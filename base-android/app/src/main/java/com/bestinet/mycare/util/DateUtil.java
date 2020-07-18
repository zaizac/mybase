package com.bestinet.mycare.util;

import android.annotation.SuppressLint;
import android.util.Log;

import com.bestinet.mycare.constant.BaseConstant;
import com.bestinet.mycare.constant.ExceptionConstant;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateUtil {

    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat normalDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static SimpleDateFormat dateFormat24Hour = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public static String getTotalTime(Date startDate, Date endDate) {

        String totalHours = BaseConstant.EMPTY_STRING;

        try {
            //milliseconds
            long different = endDate.getTime() - startDate.getTime();

            System.out.println("startDate : " + startDate);
            System.out.println("endDate : " + endDate);
            System.out.println("different : " + different);

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            //long elapsedDays = different / daysInMilli;
            //different = different % daysInMilli;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;

            totalHours = elapsedHours + "h " + elapsedMinutes + "m";// + elapsedSeconds;

        } catch (Exception e) {
            Log.d(ExceptionConstant.CATCH_ERROR, e.getLocalizedMessage());
        }

        return totalHours;
    }

    public static Double getTotalHour(Date startDate, Date endDate) {

        Double totalHours = 0.0;

        try {
            //milliseconds
            long different = endDate.getTime() - startDate.getTime();

            System.out.println("startDate : " + startDate);
            System.out.println("endDate : " + endDate);
            System.out.println("different : " + different);

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            //long elapsedDays = different / daysInMilli;
            //different = different % daysInMilli;

            double elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;

            totalHours = elapsedHours;

        } catch (Exception e) {
            Log.d(ExceptionConstant.CATCH_ERROR, e.getLocalizedMessage());
        }

        return totalHours;
    }

    public static Date parseDate(String strDate) {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date1 = null;
        try {
            date1 = dateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    public static String getLastSevenDayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -6);

        return normalDateFormat.format(calendar.getTime());
    }

    public static Date convertStringToDate(String dateString) {
        Date dateConvert = null;

        if (!BaseUtil.isObjNull(dateString)) {
            try {
                dateConvert = normalDateFormat.parse(dateString);
            } catch (ParseException e) {
                Log.d(ExceptionConstant.CATCH_ERROR, e.getLocalizedMessage());
            }
        }

        return dateConvert;
    }

    public static Integer getCurrentDay() {
        Calendar calendar = Calendar.getInstance();

        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static Integer getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();

        return calendar.get(Calendar.MONTH);
    }

    public static Integer getCurrentYear() {
        Calendar calendar = Calendar.getInstance();

        return calendar.get(Calendar.YEAR);
    }

    public static String getCurrentDate() {
        return normalDateFormat.format(new Date());
    }

    public static String getCurrentDateTime() {
        return dateFormat24Hour.format(new Date());
    }

    public static String getPreviousDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return normalDateFormat.format(cal.getTime());
    }

    public static String getCurrentTime() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");

        return currentTime.format(new Date());
    }

    public static String getDayOfWeek(String date) {
        Date inputDate = null;
        try {
            inputDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException e) {
            Log.d(ExceptionConstant.CATCH_ERROR, e.getLocalizedMessage());
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(inputDate);
        String dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US);

        return dayOfWeek;
    }

    public static int getMonthNoByName(String monthName) {
        try {
            @SuppressLint("SimpleDateFormat") Date date = new SimpleDateFormat("MMM").parse(monthName);//put your month name here
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            return cal.get(Calendar.MONTH);
        } catch (Exception e) {
            Log.d(ExceptionConstant.CATCH_ERROR, e.getLocalizedMessage());
        }

        return 0;
    }

    public static String getMonthNameByNo(int month) {
        return new DateFormatSymbols().getShortMonths()[month - 1];
    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public static String convertUnixtoDate(long unixTimeStamp) {
        // convert seconds to milliseconds
        Date date = new Date(unixTimeStamp);
// the format of your date
        SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd h:m:s ZZZZ yyyy", Locale.getDefault());
// give a timezone reference for formatting (see comment at the bottom)
//        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-8"));

        return sdf.format(date);
    }

}
