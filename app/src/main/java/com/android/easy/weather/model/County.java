package com.android.easy.weather.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by admin on 2016/12/22.
 * 用来存县信息
 */
@RealmClass
public class County implements RealmModel {
    @SerializedName("name")
    private String countyName;  //  县名字
    @PrimaryKey
    @SerializedName("id")
    private int countyCode;     //  县对应的Id
    @SerializedName("weather_id")
    private String weatherId;   //  县对应的天气Id
    private int cityId;         //  县所属的市Id

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
