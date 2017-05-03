package com.honzar.adtutils.library;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.os.Build;

/**
 * Created by Honza Rychnovský on 20.4.2017.
 * AppsDevTeam
 * honzar@appsdevteam.com
 */

public class AppPropertyUtils {

    private static int versionCode = Integer.MIN_VALUE;

    public static boolean isThisDeviceLollipopOrHigher()
    {
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean isThisDeviceMarschmallowOrHigher()
    {
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.M;
    }

    public static boolean isThisDeviceNougatAndHigher()
    {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    public static int getDeviceSDKVersion()
    {
        return Build.VERSION.SDK_INT;
    }

    public static boolean isTablet(Context context)
    {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static String getReadableDeviceName()
    {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return getFirstWordCapitalized(model);
        } else {
            return getFirstWordCapitalized(manufacturer) + " " + model;
        }
    }

    public static int getAppVersionBuild(Context context)
    {
        if (versionCode != Integer.MIN_VALUE)
            return versionCode;
        else {
            try {
                return versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            }
            catch (PackageManager.NameNotFoundException e) {
                return versionCode = 1;
            }
        }
    }

    public static String getAppVersionName(Context context)
    {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static int getAppVersionCode(Context context)
    {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        }
        catch (PackageManager.NameNotFoundException e) {
            return 1;
        }
    }

    /**
     * Funkce vrati verzi aplikace bez postfixu typu beta1, RC3 "-debug" apod. - tzn. jen napr. 2.0.0
     * @param context
     * @return
     */
    public static String getAppVersionNameWithoutPostfix(Context context) {
        try {
            String ret = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;

            int ind = ret.indexOf(' ');
            if (ind >= 0)
                ret = ret.substring(0, ind);

            ind = ret.indexOf('-');
            if (ind >= 0)
                ret = ret.substring(0, ind);

            return ret;
        }
        catch (PackageManager.NameNotFoundException e) {
            return "err";
        }
    }

    public static String getModelName() {
        try {
            String manufacturer = Build.MANUFACTURER;
            String model = Build.MODEL;
            if (model.startsWith(manufacturer)) {
                return capitalize(model);
            } else {
                return capitalize(manufacturer) + " " + model;
            }
        }
        catch (Exception ex) {
            return "err";
        }
    }

    public static int getAppVersionCode(Context context) {
        if (versionCode != Integer.MIN_VALUE)
            return versionCode;
        else {
            try {
                return versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            }
            catch (NameNotFoundException e) {
                return versionCode = -1;
            }
        }
    }

    public static String getAppVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        }
        catch (NameNotFoundException e) {
            return "err";
        }
    }

    /**
     * Funkce vrati verzi aplikace bez postfixu typu beta1, RC3 "-debug" apod. - tzn. jen napr. 2.0.0
     * @param context
     * @return
     */
    public static String getAppVersionNameWithoutPostfix(Context context) {
        try {
            String ret = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;

            int ind = ret.indexOf(' ');
            if (ind >= 0)
                ret = ret.substring(0, ind);

            ind = ret.indexOf('-');
            if (ind >= 0)
                ret = ret.substring(0, ind);

            return ret;
        }
        catch (NameNotFoundException e) {
            return "err";
        }
    }

    public static String getModelName()
    {
        try {
            String manufacturer = Build.MANUFACTURER;
            String model = Build.MODEL;
            if (model.startsWith(manufacturer)) {
                return StringUtils.capitalize(model);
            } else {
                return StringUtils.capitalize(manufacturer) + " " + model;
            }
        }
        catch (Exception ex) {
            return "";
        }
    }

}
