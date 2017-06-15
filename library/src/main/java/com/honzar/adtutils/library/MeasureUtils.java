package com.honzar.adtutils.library;

import android.location.Location;

import java.text.DecimalFormat;

/**
 * Created by Honza Rychnovský on 5.5.2017.
 * AppsDevTeam
 * honzar@appsdevteam.com
 */

public class MeasureUtils extends Utils {


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
