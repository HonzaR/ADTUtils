package com.honzar.adtutils.library;

import android.content.Context;
import android.location.Location;
import android.text.format.DateUtils;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * Created by Honza Rychnovský on 5.5.2017.
 * AppsDevTeam
 * honzar@appsdevteam.com
 */

public class MeasureUtils extends Utils {


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

        return DateUtils.formatDateTime(context, date.getTime(), DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE);
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

        return DateUtils.formatDateTime(context, date.getTime(), DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE);
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


    // DISTANCE AND WEIGHT

    /**
     * Returns locations distance or -1 in case of failure
     *
     * @param locationA
     * @param locationB
     *
     * @return locations distance or -1 in case of failure
     */
    public static int getLocationsDistance(Location locationA, Location locationB)
    {
        if (checkNull(locationA) || checkNull(locationB)) {
            return -1;
        }

        return Math.round(locationA.distanceTo(locationB));
    }

    /**
     * Returns distance in printable format or empty string in case of failure
     *
     * @param distance
     *
     * @return distance in printable format or empty string in case of failure
     */
    public static String getActualDistancePrintableFormant(int distance)
    {
        if (distance < 0) {
            return "";
        }

        float distanceF;
        if (distance > 499) {
            distanceF = (float) distance / 1000f;
            return (getDecimalFormatter().format(distanceF) + " km");
        }

        return (getDecimalFormatter().format(distance) + " m");
    }

    /**
     * Returns weight in printable format or empty string in case of failure
     *
     * @param weight
     *
     * @return weight in printable format or empty string in case of failure
     */
    public static String getWeightPrintableFormatted(int weight)
    {
        if (weight < 0) {
            return "";
        }

        float weightF;
        if (weight > 999) {
            weightF = (float) weight / 1000f;
            return (getDecimalFormatter().format(weightF) + "kg");
        }

        return (getDecimalFormatter().format(weight) + "g");
    }

    // OTHERS

    /**
     * Converts kilobytes to megabytes
     *
     * @param kilobytes
     *
     * @return megabytes
     */
    public static long kBTomB(long kilobytes)
    {
        return kilobytes / 1024;
    }

    /**
     * Converts bytes to kilobytes
     *
     * @param bytes
     *
     * @return kilobytes
     */
    public static long BTokB(long bytes)
    {
        return bytes / 1024;
    }

    /**
     * Converts kilocalories to kilojoules
     *
     * @param kcal
     *
     * @return kj
     */
    public static int kcalToKj(float kcal)
    {
        return (int) NumberUtils.roundDouble(kcal * 4.184, 0);
    }


    // PRIVATE

    private static DecimalFormat decimalFormat;
    private static DecimalFormat getDecimalFormatter()
    {
        if (decimalFormat == null) {
            decimalFormat = new DecimalFormat("#.#");
        }
        return decimalFormat;
    }

}
