package com.honzar.adtutils.sampleApp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.honzar.adtutils.library.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean b = JsonUtils.getBoolean(new JsonObject(), "test");

        LogUtils.d(com.honzar.adtutils.sampleApp.BuildConfig.DEBUG, MainActivity.class.getName(), Byte.parseByte("fff", 2));
    }
}
