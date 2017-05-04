package com.honzar.adtutils.library;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.os.Build;

/**
 * Created by Honza Rychnovský on 20.4.2017.
 * AppsDevTeam
 * honzar@appsdevteam.com
 */

public class AppPropertyUtils {


    // CHECK DEVICE SDK VERSION

    /**
     * Returns true if is device Jelly Bean or higher SDK version
     *
     * @return true/false
     */
    public static boolean isThisDeviceJellyBeanOrHigher()
    {
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN;
    }

    /**
     * Returns true if is device KiKat or higher SDK version
     *
     * @return true/false
     */
    public static boolean isThisDeviceKitKatOrHigher()
    {
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT;
    }

    /**
     * Returns true if is device Lollipop or higher SDK version
     *
     * @return true/false
     */
    public static boolean isThisDeviceLollipopOrHigher()
    {
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * Returns true if is device Marshmallow or higher SDK version
     *
     * @return true/false
     */
    public static boolean isThisDeviceMarshmallowOrHigher()
    {
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.M;
    }

    /**
     * Returns true if is device Nougat or higher SDK version
     *
     * @return true/false
     */
    public static boolean isThisDeviceNougatAndHigher()
    {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    /**
     * Returns this device SDK version
     *
     * @return SDK version
     */
    public static int getDeviceSDKVersion()
    {
        return Build.VERSION.SDK_INT;
    }


    // DEVICE TYPE

    /**
     * Returns true if this device is tablet
     *
     * @return true/false
     */
    public static boolean isTablet(Context context)
    {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * Returns true if this device is smartphone
     *
     * @return true/false
     */
    public static boolean isSmartphone(Context context)
    {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) < Configuration.SCREENLAYOUT_SIZE_LARGE;
    }


    // MODEL / NAME / CODE

    /**
     * Returns readable device name, consisting of manufacturer (if any) and model
     *
     * @return readable device name
     */
    public static String getReadableDeviceName()
    {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return StringUtils.getFirstLetterCapitalized(model);
        } else {
            return StringUtils.getFirstLetterCapitalized(manufacturer) + " " + model;
        }
    }

    /**
     * Returns version name, which is defined in app gradle file
     *
     * @return version name or empty string in case of failure
     */
    public static String getAppVersionName(Context context)
    {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            return "";
        }
    }

    /**
     * Returns version code, which is defined in app gradle file
     *
     * @return version core or 0 in case of failure
     */
    public static int getAppVersionCode(Context context)
    {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            return 0;
        }
    }

    /**
     * Returns version code without postfix like beta1, RC3 "-debug" and so on, so it returns e.g. 2.0.0
     *
     * @return version code without postfix
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

}
