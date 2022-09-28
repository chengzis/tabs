package com.example.video_0927.app;

import android.app.Application;
import android.content.Context;

import com.example.video_0927.util.SharedPreferencesUtil;

public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        //初始化SharedPreferences
        SharedPreferencesUtil.init(context);

    }

    public static Context getContext(){
        return context;
    }
}
