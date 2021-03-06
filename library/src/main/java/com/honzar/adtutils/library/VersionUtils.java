package com.honzar.adtutils.library;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

/**
 * Created by Honza Rychnovský on 09.05.17.
 * AppsDevTeam
 * honzar@appsdevteam.com
 */

public class VersionUtils extends Utils {


    //
    // SDK VERSIONS
    //

    /**
     * Returns true if is device Jelly Bean or higher SDK version
     *
     * @return true/false
     */
    public static boolean isThisDeviceJellyBeanOrHigher()
    {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    /**
     * Returns true if is device KiKat or higher SDK version
     *
     * @return true/false
     */
    public static boolean isThisDeviceKitKatOrHigher()
    {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * Returns true if is device Lollipop or higher SDK version
     *
     * @return true/false
     */
    public static boolean isThisDeviceLollipopOrHigher()
    {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * Returns true if is device Marshmallow or higher SDK version
     *
     * @return true/false
     */
    public static boolean isThisDeviceMarshmallowOrHigher()
    {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
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
     * Checks if this device is Oreo or higher
     *
     * @return true/false
     */
    public static boolean isThisDeviceOreoOrHigher()
    {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

    /**
     * Checks if this device is Oreo or higher
     *
     * @return true/false
     */
    public static boolean isThisDevicePieOrHigher()
    {
        return Build.VERSION.SDK_INT >= 28;
    }

    /**
     * Checks if this device is Android 10 or higher
     *
     * @return true/false
     */
    public static boolean isThisDeviceAndroid10OrHigher()
    {
        return Build.VERSION.SDK_INT >= 29;
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
            return StringUtils.getStringFirstUpper(model);
        } else {
            return StringUtils.getStringFirstUpper(manufacturer) + " " + model;
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
        } catch (PackageManager.NameNotFoundException e) {
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
        } catch (PackageManager.NameNotFoundException e) {
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
        catch (PackageManager.NameNotFoundException e) {
            return "err";
        }
    }

    /**
     * Returns human readable network type, e.g. WIFI or MOBILE-2G, ...
     *
     * @return network type string or "none" in case of error
     */
    public static String getNetworkTypeName(Context context)
    {
        String type = "none";
        if (checkNull(context))
            return type;

        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

            if (activeNetworkInfo == null)
                return type;

            type = activeNetworkInfo.getTypeName();
            String subTypeName = activeNetworkInfo.getSubtypeName();

            return type + (subTypeName != null && !subTypeName.isEmpty() ? "-" + subTypeName : "");

        } catch (Exception e) {
            return type;
        }
    }
}
