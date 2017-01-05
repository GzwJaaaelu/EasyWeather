package com.android.easy.weather.util;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Created by admin on 2016/12/22
 * 用来暂时Logger的设置的类
 */
public class LoggerUtil {

    //  还原设置
    public static void resetSettings() {
        Logger.resetSettings();
    }

    //  隐藏线程信息
    public static void hidenTheadInfo() {
        Logger.init().hideThreadInfo();
    }

    //  只是打印信息
    public static void onlyShowMessage() {
        Logger.init().methodCount(0).hideThreadInfo();
    }

    //  设置自定义的Tag
    public static void setLoggerTag(String tag) {
        Logger.init(tag);
    }

    //  不打印Log
    public static void noLog() {
        Logger.init().logLevel(LogLevel.NONE);
    }

    //  打印异常
    public static void printException(Exception e) {
        resetSettings();
        Logger.e(e, "message");
    }

    //  打印Json
    public static void printJson(String json) {
        resetSettings();
        Logger.json(json);
    }

    //  打印一般信息
    public static void printGeneralLog(Object info) {
        resetSettings();
        Logger.d(info);
    }

    //  只打印异常
    public static void printExceptionOnly(Exception e) {
        onlyShowMessage();
        Logger.e(e, "message");
    }

    //  只打印Json
    public static void printJsonOnly(String json) {
        onlyShowMessage();
        Logger.json(json);
    }

    //  只打印信息
    public static void printGeneralLogOnly(Object info) {
        onlyShowMessage();
        Logger.d(info);
    }

    //  自定义打印信息
    public static void printCustomInfo(int priority, String tag, String message, Throwable throwable) {
        resetSettings();
        Logger.log(priority, tag, message, throwable);
    }

}