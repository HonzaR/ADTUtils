package com.honzar.adtutils.library;

import android.content.Context;
import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by Honza Rychnovský on 15.06.17.
 * AppsDevTeam
 * honzar@appsdevteam.com
 */

public class DateTimeUtils extends Utils {

    private static SimpleDateFormat simpleISO8601DateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());
    private static SimpleDateFormat simpleISO8601DateWithMillisFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault());


    // DECODE & ENCODE

    /**
     * Returns Date object parsed from date string or current date in case of error.
     *
     * @param dateStr
     *
     * @return Date object parsed from date string or current date in case of error
     */
    public static Date decodeDateFromIsoString(String dateStr)
    {
        if (checkNull(dateStr)) {
            return new Date();
        }

        try {
            return simpleISO8601DateFormatter.parse(dateStr);
        } catch (ParseException ex) {
            return new Date();
        }
    }

    /**
     * Returns Date with millis object parsed from date string or current date in case of error.
     *
     * @param dateStr
     *
     * @return Date object with millis parsed from date string or current date in case of error
     */
    public static Date decodeDateWithMillisFromIsoString(String dateStr)
    {
        if (checkNull(dateStr)) {
            return new Date();
        }

        try {
            return simpleISO8601DateWithMillisFormatter.parse(dateStr);
        } catch (ParseException ex) {
            return new Date();
        }
    }

    /**
     * Returns ISO 8601 string formatted from time
     *
     * @param millis
     *
     * @return ISO 8601 String or current date ISO String in case of error
     */
    public static String encodeMillisToIsoString(long millis)
    {
        if (millis < 0) {
            return simpleISO8601DateFormatter.format(System.currentTimeMillis());
        }

        try {
            return simpleISO8601DateFormatter.format(millis);
        } catch(RuntimeException rte) {
            return simpleISO8601DateFormatter.format(System.currentTimeMillis());
        }
    }

    /**
     * Returns ISO 8601 string formatted from time
     *
     * @param date
     *
     * @return ISO 8601 String or current date ISO String in case of error
     */
    public static String encodeDateToIsoString(Date date)
    {
        if (date == null) {
            return simpleISO8601DateFormatter.format(System.currentTimeMillis());
        }

        return encodeMillisToIsoString(date.getTime());
    }

    // DATE AND TIME

    /**
     * Returns formatted date and time according to system locale
     *
     * @param context
     * @param date
     *
     * @return formatted date and time or empty string in case of failure
     */
    public static String getFormattedDateAndTime(Context context, Date date)
    {
        if (checkNull(context) || checkNull(date)) {
            return "";
        }

        return DateUtils.formatDateTime(context, date.getTime(), DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_24HOUR);
    }

    /**
     * Returns formatted date and time according to system locale
     *
     * @param context
     * @param millis
     *
     * @return formatted date and time or empty string in case of failure
     */
    public static String getFormattedDateAndTime(Context context, long millis)
    {
        if (checkNull(context) || millis < 0) {
            return "";
        }

        return DateUtils.formatDateTime(context, millis, DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_24HOUR);
    }

    /**
     * Returns formatted date and time with date in words and according to system locale
     *
     * @param context
     * @param millis
     *
     * @return formatted date and time or empty string in case of failure
     */
    public static String getFormattedDateAndTimeInWords(Context context, long millis)
    {
        if (checkNull(context) || millis < 0) {
            return "";
        }

        return DateUtils.formatDateTime(context, millis, DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_24HOUR);
    }

    /**
     * Returns formatted date and time with date in words and according to system locale
     *
     * @param context
     * @param date
     *
     * @return formatted date and time or empty string in case of failure
     */
    public static String getFormattedDateAndTimeInWords(Context context, Date date)
    {
        if (checkNull(context) || checkNull(date)) {
            return "";
        }

        return DateUtils.formatDateTime(context, date.getTime(), DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_24HOUR);
    }

    /**
     * Returns formatted time according to system locale
     *
     * @param context
     * @param date
     *
     * @return formatted time or empty string in case of failure
     */
    public static String getFormattedJustTime(Context context, Date date)
    {
        if (checkNull(context) || checkNull(date)) {
            return "";
        }

        return DateUtils.formatDateTime(context, date.getTime(), DateUtils.FORMAT_SHOW_TIME);
    }

    /**
     * Returns formatted time according to system locale
     *
     * @param context
     * @param millis
     *
     * @return formatted time or empty string in case of failure
     */
    public static String getFormattedJustTime(Context context, long millis)
    {
        if (checkNull(context) || millis < 0) {
            return "";
        }

        return DateUtils.formatDateTime(context, millis, DateUtils.FORMAT_SHOW_TIME);
    }

    /**
     * Returns formatted date according to system locale
     *
     * @param context
     * @param date
     *
     * @return formatted date or empty string in case of failure
     */
    public static String getFormattedJustDate(Context context, Date date)
    {
        if (checkNull(context) || checkNull(date)) {
            return "";
        }

        return DateUtils.formatDateTime(context, date.getTime(), DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_NUMERIC_DATE);
    }

    /**
     * Returns formatted date according to system locale
     *
     * @param context
     * @param millis
     *
     * @return formatted date or empty string in case of failure
     */
    public static String getFormattedJustDate(Context context, long millis)
    {
        if (checkNull(context) || millis < 0) {
            return "";
        }

        return DateUtils.formatDateTime(context, millis, DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_NUMERIC_DATE);
    }

    /**
     * Returns formatted date (just day and month) according to system locale
     *
     * @param context
     * @param date
     *
     * @return formatted date or empty string in case of failure
     */
    public static String getFormattedJustDateDayAndMonth(Context context, Date date)
    {
        if (checkNull(context) || checkNull(date)) {
            return "";
        }

        return DateUtils.formatDateTime(context, date.getTime(), DateUtils.FORMAT_SHOW_DATE);
    }

    /**
     * Returns formatted date (just day and month) according to system locale
     *
     * @param context
     * @param millis
     *
     * @return formatted date or empty string in case of failure
     */
    public static String getFormattedJustDateDayAndMonth(Context context, long millis)
    {
        if (checkNull(context) || millis < 0) {
            return "";
        }

        return DateUtils.formatDateTime(context, millis, DateUtils.FORMAT_SHOW_DATE);
    }

    /**
     * Returns formatted date in words according to system locale
     *
     * @param context
     * @param date
     *
     * @return formatted date or empty string in case of failure
     */
    public static String getFormattedJustDateInWords(Context context, Date date)
    {
        if (checkNull(context) || checkNull(date)) {
            return "";
        }

        return DateUtils.formatDateTime(context, date.getTime(), DateUtils.FORMAT_SHOW_YEAR);
    }

    /**
     * Returns formatted date in words according to system locale
     *
     * @param context
     * @param millis
     *
     * @return formatted date or empty string in case of failure
     */
    public static String getFormattedJustDateInWords(Context context, long millis)
    {
        if (checkNull(context) || millis < 0) {
            return "";
        }

        return DateUtils.formatDateTime(context, millis, DateUtils.FORMAT_SHOW_YEAR);
    }

    /**
     * Returns formatted date in words according to system locale
     *
     * @param context
     * @param dateStart
     * @param dateEnd
     *
     * @return formatted date and time interval or empty string in case of failure
     */
    public static String getFormattedDateAndTimeInterval(Context context, Date dateStart, Date dateEnd)
    {
        if (checkNull(context) || checkNull(dateStart) || checkNull(dateEnd)) {
            return "";
        }

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(dateStart);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long startDateInMillis = calendar.getTimeInMillis();

        calendar.setTime(dateEnd);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long endDateInMillis = calendar.getTimeInMillis();

        String resultDate = "";
        if (startDateInMillis == endDateInMillis) {
            resultDate += getFormattedJustDate(context, dateStart);
            resultDate += " ";
            resultDate += getFormattedJustTime(context, dateStart);
            resultDate += " - ";
            resultDate += getFormattedJustTime(context, dateEnd);
        } else {
            resultDate += getFormattedDateAndTime(context, dateStart);
            resultDate += " - ";
            resultDate += getFormattedDateAndTime(context, dateEnd);
        }

        return resultDate;
    }

    /**
     * Returns formatted date in words according to system locale
     *
     * @param context
     * @param millisStart
     * @param millisEnd
     *
     * @return formatted date and time interval or empty string in case of failure
     */
    public static String getFormattedDateAndTimeInterval(Context context, long millisStart, long millisEnd)
    {
        return getFormattedDateAndTimeInterval(context, millisStart, millisEnd);
    }

    /**
     * Returns formatted time duration
     *
     * @param context
     * @param milliseconds
     *
     * @return formatted time duration or empty string in case of failure
     */
    public static String getFormattedTimeDuration(Context context, long milliseconds, boolean withMilliseconds)
    {
        if (checkNull(context) || milliseconds < 0) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        final long hr = TimeUnit.MILLISECONDS.toHours(milliseconds);
        final long min = TimeUnit.MILLISECONDS.toMinutes(milliseconds) % 60;
        final long sec = TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60;
        final long millis = TimeUnit.MILLISECONDS.toMillis(milliseconds) % 1000;

        if (hr > 0) {
            builder.append(hr);
            builder.append(":");
        }
        if (min > 0) {
            builder.append(min);
            builder.append(":");
        }
        if (sec > 0) {
            builder.append(sec);
            builder.append(withMilliseconds ? "." : "");
        }
        if (withMilliseconds && millis > 0) {
            builder.append(millis);
        }

        return builder.toString();
    }

    /**
     * Returns formatted time duration
     *
     * @param context
     * @param time
     *
     * @return formatted time duration or empty string in case of failure
     */
    public static String getFormattedTimeDuration(Context context, Date time, boolean withMilliseconds)
    {
        if (checkNull(context) || checkNull(time)) {
            return "";
        }

        return getFormattedTimeDuration(context, time.getTime(), withMilliseconds);
    }
}
