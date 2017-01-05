package com.android.easy.weather.model;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by admin on 2017/1/3.
 * 用来存选择过的城市列表
 */
@RealmClass
public class SelectCity implements RealmModel {
    private String cityName;
    @PrimaryKey
    private String weatherId;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }
}
