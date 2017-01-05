package com.android.easy.weather;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;

public class WeatherApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        Realm.init(this);
    }

    /**
     * 获取App的上下文
     * @return
     */
    public static Context getContext() {
        return mContext;
    }



}