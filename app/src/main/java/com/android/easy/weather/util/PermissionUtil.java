package com.android.easy.weather.util;

import android.Manifest;

import com.android.easy.weather.WeatherApplication;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import java.util.List;

/**
 * Created by admin on 2016/12/23.
 * Android6.0权限获取
 */

public class PermissionUtil {
    private static volatile PermissionUtil singleton;
    private OnPermissionChangeListener mPermissionListener;
    public static final int LOCATION_PERMISSION = 0;

    public static PermissionUtil getInstance() {
        if (singleton == null) {
            synchronized (PermissionUtil.class) {
                if (singleton == null) {
                    singleton = new PermissionUtil();
                }
            }
        }
        return singleton;
    }

    /**
     * 获取定位权限
     */
    public void getLocationPermission() {
        Acp.getInstance(WeatherApplication.getContext()).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION)
                        .build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                        mPermissionListener.onGranted(LOCATION_PERMISSION);
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        mPermissionListener.onDenied(LOCATION_PERMISSION);
                    }
                });

    }

    /**
     * 获取权限后的回调
     */
    public interface OnPermissionChangeListener {
        void onGranted(int permissionType);
        void onDenied(int permissionType);
    }

    /**
     * set方法
     * @param mPermissionListener
     */
    public void setPermissionListener(OnPermissionChangeListener mPermissionListener) {
        this.mPermissionListener = mPermissionListener;
    }
}
