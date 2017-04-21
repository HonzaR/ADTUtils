package com.honzar.adtutils.library;

import android.util.Log;

/**
 * Created by Honza Rychnovský on 20.4.2017.
 * AppsDevTeam
 * honzar@appsdevteam.com
 */

public class LogUtils {

    private static boolean loggingEnabled;

    static {
        loggingEnabled = BuildConfig.DEBUG;
    }

    /**
     * Wraps {@link android.util.Log#i}.
     *
     * @param tag
     * @param string
     */
    public static void i(String tag, String string)
    {
        if (loggingEnabled) {
            Log.i(tag, string);
        }
    }

    /**
     * Wraps {@link android.util.Log#e}.
     *
     * @param tag
     * @param string
     */
    public static void e(String tag, String string)
    {
        if (loggingEnabled) {
            Log.e(tag, string);
        }
    }

    /**
     * Wraps {@link android.util.Log#e}.
     *
     * @param tag
     * @param string
     */
    public static void e(String tag, String string, Throwable tr)
    {
        if (loggingEnabled) {
            Log.e(tag, string, tr);
        }
    }

    /**
     * Wraps {@link android.util.Log#d}.
     *
     * @param tag
     * @param string
     */
    public static void d(String tag, String string)
    {
        if (loggingEnabled) {
            Log.d(tag, string);
        }
    }

    /**
     * Wraps {@link android.util.Log#w}.
     *
     * @param tag
     * @param string
     */
    public static void w(String tag, String string)
    {
        if (loggingEnabled) {
            Log.w(tag, string);
        }
    }


    /**
     * Wraps {@link android.util.Log#d}.
     *
     * @param tag
     * @param value
     */
    public static void d(String tag, int value)
    {
        d(tag, String.valueOf(value));
    }
}
