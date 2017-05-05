package com.honzar.adtutils.library;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by Honza Rychnovský on 4.5.2017.
 * AppsDevTeam
 * honzar@appsdevteam.com
 */

public class NumberUtils {


    /**
     * Removes unnecessary decimal zeros from float value
     *
     * @param value
     *
     * @return string of float value without unnecessary decimal zeros
     */
    public static String removeUnnecessaryDecimalZeros(float value)
    {
        DecimalFormat format = new DecimalFormat("#.#");
        return format.format(value);
    }

    /**
     * Rounds double to required number of decimal places
     *
     * @param value
     * @param places
     *
     * @return rounded double or 0 in case of failure
     */
    public static double roundDouble(double value, int places)
    {
        if (places < 0) {
            return 0d;
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * Rounds float to required number of decimal places
     *
     * @param value
     * @param places
     *
     * @return rounded float or 0 in case of failure
     */
    public static float roundDouble(float value, int places)
    {
        if (places < 0) {
            return 0f;
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

    /**
     * Converts decimal to hex format of number
     *
     * @param number
     *
     * @return hex format of number
     */
    public static String decimalToHex(int number)
    {
        return Integer.toString(number, 16);
    }
}
