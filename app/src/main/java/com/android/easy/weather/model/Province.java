package com.android.easy.weather.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by admin on 2016/12/22.
 * 用于存省信息
 */
@RealmClass
public class Province implements RealmModel {
    @SerializedName("name")
    private String provinceName;    //  省名字
    @PrimaryKey
    @SerializedName("id")
    private int provinceCode;       //  省代码

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }
}
