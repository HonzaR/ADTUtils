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
    }
}
