package com.honzar.adtutils.library;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;

/**
 * Created by Honza Rychnovský on 20.4.2017.
 * AppsDevTeam
 * honzar@appsdevteam.com
 */

public class AppPropertyUtils {
    private static int versionCode = Integer.MIN_VALUE;

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


    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }
}
