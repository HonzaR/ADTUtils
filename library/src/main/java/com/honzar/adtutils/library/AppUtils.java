package com.honzar.adtutils.library;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by Honza Rychnovský on 26.02.19.
 * AppsDevTeam
 * honzar@appsdevteam.com
 */
public class AppUtils extends Utils {

    /**
     * Checks if app is running in foreground or background (i.e. is minimized or killed)
     *
     * @param context
     *
     * @return true/false
     */
    public static boolean isAppRunningInForeground(Context context)
    {
        if (context == null) {
            return false;
        }

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        if (activityManager == null)
            return false;

        List<ActivityManager.RunningAppProcessInfo> services = activityManager.getRunningAppProcesses();
        boolean isActivityFound = false;

        if (services.get(0).importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
            isActivityFound = true;
        }

        return isActivityFound;
    }
}
