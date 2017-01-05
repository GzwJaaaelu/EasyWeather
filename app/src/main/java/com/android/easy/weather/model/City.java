package com.android.easy.weather.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by admin on 2016/12/22.
 * 用来存市信息
 */
@RealmClass
public class City implements RealmModel {
    @SerializedName("name")
    private String cityName;    //  市名字
    @PrimaryKey
    @SerializedName("id")
    private int cityCode;       //  市代码
    private int provinceId;     //  市所属的省Id

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }
}
