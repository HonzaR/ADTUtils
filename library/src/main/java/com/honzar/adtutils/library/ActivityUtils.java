package com.honzar.adtutils.library;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;

/**
 * Created by Honza Rychnovský on 20.4.2017.
 * AppsDevTeam
 * honzar@appsdevteam.com
 */

public class ActivityUtils {
    
    private static final String ACTIVITY_RESULT_PARCELABLE = ActivityUtils.class.getName() + "." + "ACTIVITY_RESULT_PARCELABLE";

    public static void setResultParcelable(Activity activity, int resultCode, Parcelable data) {
        Intent intent = new Intent();
        intent.putExtra(ACTIVITY_RESULT_PARCELABLE, data);
        activity.setResult(resultCode, intent);
    }

    public static <T extends Parcelable> T getResultParcelable(Intent resultData) {
        return resultData != null ? resultData.<T>getParcelableExtra(ACTIVITY_RESULT_PARCELABLE) : null;
    }
}