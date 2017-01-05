package com.android.easy.weather.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.android.easy.weather.WeatherApplication;

/**
 * Created by admin on 2016/12/22
 * 用来显示Toast提示用户.
 */

public class ToastUtil {
    private static Context context;
    private static Toast toast;
    private static View view;

    private ToastUtil() {
    }

    @SuppressLint("ShowToast")
    private static void getToast(Context context) {
        if (toast == null) {
            toast = new Toast(context);
        }
        if (view == null) {
            view = Toast.makeText(context, "", Toast.LENGTH_SHORT).getView();
        }
        toast.setView(view);
    }

    //  调用下面的方法就可以显示信息了

    public static void showShortToast(CharSequence msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

    public static void showShortToast(int resId) {
        showToast(resId, Toast.LENGTH_SHORT);
    }

    public static void showLongToast(CharSequence msg) {
        showToast(msg, Toast.LENGTH_LONG);
    }

    public static void showLongToast(int resId) {
        showToast(resId, Toast.LENGTH_LONG);
    }

    private static void showToast(CharSequence msg,
                                  int duration) {
        context = WeatherApplication.getContext();
        try {
            getToast(context);
            toast.setText(msg);
            toast.setDuration(duration);
            toast.show();
        } catch (Exception e) {
            LoggerUtil.printException(e);
        }
    }

    private static void showToast(int resId, int duration) {
        context = WeatherApplication.getContext();
        try {
            if (resId == 0) {
                return;
            }
            getToast(context);
            toast.setText(resId);
            toast.setDuration(duration);
            toast.show();
        } catch (Exception e) {
            LoggerUtil.printException(e);
        }
    }

}
