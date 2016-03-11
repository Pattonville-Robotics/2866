package com.qualcomm.ftcrobotcontroller;

import android.app.Application;
import android.content.Context;

/**
 * Created by skaggsm on 3/10/16.
 */
public class MyApplication extends Application {

    private static Context context;

    public static Context getAppContext() {
        return MyApplication.context;
    }

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }
}