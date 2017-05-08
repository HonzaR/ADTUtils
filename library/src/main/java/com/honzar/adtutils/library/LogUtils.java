package com.honzar.adtutils.library;

import android.util.Log;

/**
 * Created by Honza Rychnovský on 20.4.2017.
 * AppsDevTeam
 * honzar@appsdevteam.com
 * LogUtils class provides user logging methods with different parameters.
 */

public class LogUtils extends Utils {


    // VERBOSE

    /**
     * Wraps {@link android.util.Log#v}.
     *
     * @param showLog - tells if log should be enabled
     * @param tag - log tag
     * @param string - log message
     *
     * @code LogUtils.v(your_package_name.BuildConfig.DEBUG, MainActivity.class.getName(), "Logging...");
     */
    public static void v(boolean showLog, String tag, String string)
    {
        if (showLog) {
            Log.v(tag, string);
        }
    }

    // DEBUG

    /**
     * Wraps {@link android.util.Log#d}.
     *
     * @param showLog - tells if log should be enabled
     * @param tag - log tag
     * @param string - log message
     *
     * @code LogUtils.d(your_package_name.BuildConfig.DEBUG, MainActivity.class.getName(), "Logging...");
     */
    public static void d(boolean showLog, String tag, String string)
    {
        if (showLog) {
            Log.d(tag, string);
        }
    }

    /**
     * Wraps {@link android.util.Log#d}.
     *
     * @param showLog - tells if log should be enabled
     * @param tag - log tag
     * @param value - integer value to be print
     *
     * @code LogUtils.d(your_package_name.BuildConfig.DEBUG, MainActivity.class.getName(), 12);
     */
    public static void d(boolean showLog, String tag, int value)
    {
        d(showLog, tag, String.valueOf(value));
    }

    /**
     * Wraps {@link android.util.Log#d}.
     *
     * @param showLog - tells if log should be enabled
     * @param tag - log tag
     * @param value - long value to be print
     *
     * @code LogUtils.d(your_package_name.BuildConfig.DEBUG, MainActivity.class.getName(), 12L);
     */
    public static void d(boolean showLog, String tag, long value)
    {
        d(showLog, tag, String.valueOf(value));
    }

    /**
     * Wraps {@link android.util.Log#d}.
     *
     * @param showLog - tells if log should be enabled
     * @param tag - log tag
     * @param value - float value to be print
     *
     * @code LogUtils.d(your_package_name.BuildConfig.DEBUG, MainActivity.class.getName(), 12F);
     */
    public static void d(boolean showLog, String tag, float value)
    {
        d(showLog, tag, String.valueOf(value));
    }

    /**
     * Wraps {@link android.util.Log#d}.
     *
     * @param showLog - tells if log should be enabled
     * @param tag - log tag
     * @param value - double value to be print
     *
     * @code LogUtils.d(your_package_name.BuildConfig.DEBUG, MainActivity.class.getName(), 12D);
     */
    public static void d(boolean showLog, String tag, double value)
    {
        d(showLog, tag, String.valueOf(value));
    }

    /**
     * Wraps {@link android.util.Log#d}.
     *
     * @param showLog - tells if log should be enabled
     * @param tag - log tag
     * @param value - short value to be print
     *
     * @code LogUtils.d(your_package_name.BuildConfig.DEBUG, MainActivity.class.getName(), 12);
     */
    public static void d(boolean showLog, String tag, short value)
    {
        d(showLog, tag, String.valueOf(value));
    }

    /**
     * Wraps {@link android.util.Log#d}.
     *
     * @param showLog - tells if log should be enabled
     * @param tag - log tag
     * @param value - char value to be print
     *
     * @code LogUtils.d(your_package_name.BuildConfig.DEBUG, MainActivity.class.getName(), 'c);
     */
    public static void d(boolean showLog, String tag, char value)
    {
        d(showLog, tag, String.valueOf(value));
    }

    /**
     * Wraps {@link android.util.Log#d}.
     *
     * @param showLog - tells if log should be enabled
     * @param tag - log tag
     * @param value - byte value to be print
     *
     * @code LogUtils.d(your_package_name.BuildConfig.DEBUG, MainActivity.class.getName(), Byte.parseByte(string));
     */
    public static void d(boolean showLog, String tag, byte value)
    {
        d(showLog, tag, String.valueOf(value));
    }

    // INFO

    /**
     * Wraps {@link android.util.Log#i}.
     *
     * @param showLog - tells if log should be enabled
     * @param tag - log tag
     * @param string - log message
     *
     * @code LogUtils.i(your_package_name.BuildConfig.DEBUG, MainActivity.class.getName(), "Logging...");
     */
    public static void i(boolean showLog, String tag, String string)
    {
        if (showLog) {
            Log.i(tag, string);
        }
    }

    // WARNING

    /**
     * Wraps {@link android.util.Log#w}.
     *
     * @param showLog - tells if log should be enabled
     * @param tag - log tag
     * @param string - log message
     *
     * @code LogUtils.w(your_package_name.BuildConfig.DEBUG, MainActivity.class.getName(), "Logging...");
     */
    public static void w(boolean showLog, String tag, String string)
    {
        if (showLog) {
            Log.w(tag, string);
        }
    }

    // ERROR

    /**
     * Wraps {@link android.util.Log#e}.
     *
     * @param showLog - tells if log should be enabled
     * @param tag - log tag
     * @param string - log message
     *
     * @code LogUtils.e(your_package_name.BuildConfig.DEBUG, MainActivity.class.getName(), "Logging...");
     */
    public static void e(boolean showLog, String tag, String string)
    {
        if (showLog) {
            Log.e(tag, string);
        }
    }

    /**
     * Wraps {@link android.util.Log#e}.
     *
     * @param showLog - tells if log should be enabled
     * @param tag - log tag
     * @param string - log message
     * @param tr - Throwable error to be print
     *
     * @code LogUtils.e(your_package_name.BuildConfig.DEBUG, MainActivity.class.getName(), e);
     */
    public static void e(boolean showLog, String tag, String string, Throwable tr)
    {
        if (showLog) {
            Log.e(tag, string, tr);
        }
    }
}
