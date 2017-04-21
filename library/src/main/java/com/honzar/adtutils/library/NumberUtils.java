package com.honzar.adtutils.library;

import java.text.DecimalFormat;

/**
 * Created by Honza Rychnovský on 19.4.2017.
 * AppsDevTeam
 * honzar@appsdevteam.com
 */

public class NumberUtils {


    public static String removeUnnecessaryDecimalZeros(float number)
    {
        DecimalFormat format = new DecimalFormat("#.#");
        return format.format(number);
    }
}
