package com.android.easy.weather.util;

import android.content.SharedPreferences;
import android.content.Context;

import com.android.easy.weather.WeatherApplication;

/**
 * Created by zw on 16/7/8.
 */
public class SharePreferencesHelper {
    private static final String TAG = "SharePreferencesHelper";
    public static final String CURRENT_WEATHER_ID = "current_weather_id";
    public static final String WEATHER_INFO = "weather_info";
    private static final Context CONTEXT = WeatherApplication.getContext();

    /**
     * 用Sp存String类型数据
     * @param key       键 如:USER_ACCOUNT
     * @param value     值 如:获取到的用户账号
     * @param tagName   存到那个Sp中 如:用户账号密码存到用户信息中
     */
    public static void putString(String key, String value, String tagName) {
        SharedPreferences sp = getSharePreferences(tagName);
        if (sp != null) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(key, value);
            editor.apply();
            editor.commit();
        }
    }

    private static SharedPreferences getSharePreferences(String tagName) {
        return CONTEXT.getSharedPreferences(tagName, Context.MODE_PRIVATE);
    }

    /**
     * 获取Sting
     * @param key       存的时候的键
     * @param tagName   是哪个部分
     * @return          返回获取的到值, 默认值为""
     */
    public static String getString(String key, String tagName) {
        SharedPreferences sp = getSharePreferences(tagName);
        return sp.getString(key, "");
    }

    /**
     * 清除对应部分的存过的数据
     * @param tagName   对应部分 如:USER_INFO
     */
    public static void cleanSomeSp(String tagName) {
        SharedPreferences sp = getSharePreferences(tagName);
        sp.edit().clear();
        sp.edit().commit();
    }

}
