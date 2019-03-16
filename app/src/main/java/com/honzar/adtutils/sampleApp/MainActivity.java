package com.honzar.adtutils.sampleApp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.honzar.adtutils.library.DateTimeUtils;
import com.honzar.adtutils.library.IntentUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        JsonObject o = new JsonObject();
//        o.addProperty("test", true);
//        Boolean b = JsonUtils.getBoolean(o, "test");
//
//        LogUtils.d(com.honzar.adtutils.sampleApp.BuildConfig.DEBUG, MainActivity.class.getName(), "test: " + b);
//
//        IntentUtils.openBrowserApp(MainActivity.this, "http://www.appsdevteam.com/?ref=mujsvetzdraviandroidapp");
//
//        LogUtils.d(com.honzar.adtutils.sampleApp.BuildConfig.DEBUG, MainActivity.class.getName(), "test navigation bar height: " + ViewUtils.getSystemNavigationBarHeight(MainActivity.this));
//        LogUtils.d(com.honzar.adtutils.sampleApp.BuildConfig.DEBUG, MainActivity.class.getName(), "test status bar height: " + ViewUtils.getSystemStatusBarHeight(MainActivity.this));
//
//        LogUtils.d(true, "test", DateTimeUtils.getFormattedDateAndTimeInWords(MainActivity.this, new Date()));
//
//        ViewUtils.keepScreenOn(MainActivity.this, true);
//
//        LogUtils.d(true, "TEST DATE RANGE", DateTimeUtils.getFormattedDateAndTimeInterval(MainActivity.this, 	new Date(1509959820000L), new Date(1510136220000L)));
//        LogUtils.d(true, "TEST DATE RANGE", DateTimeUtils.getFormattedDateAndTimeInterval(MainActivity.this, 	new Date(1509963420000L), new Date(1509992820000L)));
//
//        LogUtils.d(true, "TEST TIME DURATION", DateTimeUtils.getFormattedTimeDuration(MainActivity.this, 1510515505193L, true));
//        LogUtils.d(true, "TEST TIME DURATION", DateTimeUtils.getFormattedTimeDuration(MainActivity.this, 1510515505193L, false));
//        LogUtils.d(true, "TEST TIME DURATION", DateTimeUtils.getFormattedTimeDuration(MainActivity.this, 13243243, true));
//        LogUtils.d(true, "TEST TIME DURATION", DateTimeUtils.getFormattedTimeDuration(MainActivity.this, 13243243, false));
//
//        LogUtils.d(true, "TEST MD5 hash input", "123456789qwerty");
//        LogUtils.d(true, "TEST MD5 hash output", HashingUtils.md5("123456789qwerty"));
//
//        //ViewUtils.setStatusBarColorIfCan(MainActivity.this, R.color.colorAppWhite);
//        //ViewUtils.setNavigationBarColorIfCan(MainActivity.this, R.color.colorAppWhite);
//
//        LogUtils.d(true, "Network type", VersionUtils.getNetworkTypeName(MainActivity.this));
//
//        //IntentUtils.openFilePickerComponent(MainActivity.this,"video/*", "TEST TITLE", 101);

//        file = new File(getExternalFilesDir(null), "image.mp4");
//        try {
//            file.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        IntentUtils.openCameraAppForPhoto(MainActivity.this, 2209, false, file, true);
//    }
//    File file;


        //IntentUtils.openAppPlayStoreSubscriptions(MainActivity.this);

//        Toast.makeText(this, DateTimeUtils.getFormattedTimeDuration(this, System.currentTimeMillis(), false), Toast.LENGTH_SHORT).show();

        IntentUtils.shareApp(this, "choooooser", "subjectttt", "bodyyyyy", "www.link.com");

     }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 2209) {
//
//            if (resultCode == RESULT_OK) {
//                String filePath = file.getAbsolutePath();
//            }
//        }
//    }

}
