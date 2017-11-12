package com.honzar.adtutils.library;

import android.content.Context;
import android.text.format.DateUtils;

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


    // DATE AND TIME

    /**
     * Returns formatted date and time according to system locale
     *
     * @param context
     * @param date
     *
     * @return formatted date and time or empty string in case of failure
     */
    public static String getFormattedDateAndTimeWithSystemLocale(Context context, Date date)
    {
        if (checkNull(context) || checkNull(date)) {
            return "";
        }

        return DateUtils.formatDateTime(context, date.getTime(), DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_24HOUR);
    }

    /**
     * Returns formatted date and time with date in words and according to system locale
     *
     * @param context
     * @param date
     *
     * @return formatted date and time or empty string in case of failure
     */
    public static String getFormattedDateAndTimeInWordsWithSystemLocale(Context context, Date date)
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
    public static String getFormattedJustTimeWithSystemLocale(Context context, Date date)
    {
        if (checkNull(context) || checkNull(date)) {
            return "";
        }

        return DateUtils.formatDateTime(context, date.getTime(), DateUtils.FORMAT_SHOW_TIME);
    }

    /**
     * Returns formatted date according to system locale
     *
     * @param context
     * @param date
     *
     * @return formatted date or empty string in case of failure
     */
    public static String getFormattedJustDateWithSystemLocale(Context context, Date date)
    {
        if (checkNull(context) || checkNull(date)) {
            return "";
        }

        return DateUtils.formatDateTime(context, date.getTime(), DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_NUMERIC_DATE);
    }

    /**
     * Returns formatted date in words according to system locale
     *
     * @param context
     * @param date
     *
     * @return formatted date or empty string in case of failure
     */
    public static String getFormattedJustDateInWordsWithSystemLocale(Context context, Date date)
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
     * @param dateStart
     * @param dateEnd
     *
     * @return formatted date and time interval or empty string in case of failure
     */
    public static String getFormattedDateAndTimeIntervalWithSystemLocale(Context context, Date dateStart, Date dateEnd)
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
            resultDate += getFormattedJustDateWithSystemLocale(context, dateStart);
            resultDate += " ";
            resultDate += getFormattedJustTimeWithSystemLocale(context, dateStart);
            resultDate += " - ";
            resultDate += getFormattedJustTimeWithSystemLocale(context, dateEnd);
        } else {
            resultDate += getFormattedDateAndTimeWithSystemLocale(context, dateStart);
            resultDate += " - ";
            resultDate += getFormattedDateAndTimeWithSystemLocale(context, dateEnd);
        }

        return resultDate;
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

        StringBuilder builder = new StringBuilder("");
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
        if (min > 0) {
            builder.append(sec);
            builder.append(withMilliseconds ? "." : "");
        }
        if (withMilliseconds && millis > 0) {
            builder.append(millis);
        }

        return builder.toString();
    }
}