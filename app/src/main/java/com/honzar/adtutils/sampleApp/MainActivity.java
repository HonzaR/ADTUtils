package com.honzar.adtutils.sampleApp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.honzar.adtutils.library.*;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JsonObject o = new JsonObject();
        o.addProperty("test", true);
        Boolean b = JsonUtils.getBoolean(o, "test");

        LogUtils.d(com.honzar.adtutils.sampleApp.BuildConfig.DEBUG, MainActivity.class.getName(), "test: " + b);

        IntentUtils.openBrowserApp(MainActivity.this, "http://www.appsdevteam.com/?ref=mujsvetzdraviandroidapp");

        LogUtils.d(com.honzar.adtutils.sampleApp.BuildConfig.DEBUG, MainActivity.class.getName(), "test navigation bar height: " + ViewUtils.getSystemNavigationBarHeight(MainActivity.this));
        LogUtils.d(com.honzar.adtutils.sampleApp.BuildConfig.DEBUG, MainActivity.class.getName(), "test status bar height: " + ViewUtils.getSystemStatusBarHeight(MainActivity.this));

        LogUtils.d(true, "test", DateTimeUtils.getFormattedDateAndTimeInWordsWithSystemLocale(MainActivity.this, new Date()));

        ViewUtils.keepScreenOn(MainActivity.this, true);

        LogUtils.d(true, "TEST", ""+DeviceUtils.checkGooglePlayServicesAvailable(MainActivity.this));
        LogUtils.d(true, "TEST", DeviceUtils.checkGooglePlayServicesResultCode(MainActivity.this));

        LogUtils.d(true, "TEST DATE RANGE", DateTimeUtils.getFormattedDateAndTimeIntervalWithSystemLocale(MainActivity.this, 	new Date(1509959820000L), new Date(1510136220000L)));
        LogUtils.d(true, "TEST DATE RANGE", DateTimeUtils.getFormattedDateAndTimeIntervalWithSystemLocale(MainActivity.this, 	new Date(1509963420000L), new Date(1509992820000L)));

        LogUtils.d(true, "TEST TIME DURATION", DateTimeUtils.getFormattedTimeDuration(MainActivity.this, 1510515505193L, true));
        LogUtils.d(true, "TEST TIME DURATION", DateTimeUtils.getFormattedTimeDuration(MainActivity.this, 1510515505193L, false));
        LogUtils.d(true, "TEST TIME DURATION", DateTimeUtils.getFormattedTimeDuration(MainActivity.this, 13243243, true));
        LogUtils.d(true, "TEST TIME DURATION", DateTimeUtils.getFormattedTimeDuration(MainActivity.this, 13243243, false));

        LogUtils.d(true, "TEST MD5 hash input", "123456789qwerty");
        LogUtils.d(true, "TEST MD5 hash output", HashingUtils.md5("123456789qwerty"));

        ViewUtils.setStatusBarColorIfCan(MainActivity.this, R.color.colorAppWhite);
        ViewUtils.setNavigationBarColorIfCan(MainActivity.this, R.color.colorAppWhite);
    }
}
