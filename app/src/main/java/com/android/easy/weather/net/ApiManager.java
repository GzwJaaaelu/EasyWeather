package com.android.easy.weather.net;


import com.android.easy.weather.model.City;
import com.android.easy.weather.model.County;
import com.android.easy.weather.model.Province;
import com.android.easy.weather.model.Weather;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by admin on 2016/12/22.
 */

public interface ApiManager {

    /**
     * 获取中国省列表的接口
     *
     * @return
     */
    @GET("china")
    Call<List<Province>> getChinaProvince();

    /**
     * 获取中国某省下面的市列表
     *
     * @return
     */
    @GET("china/{provinceId}")
    Call<List<City>> getChinaCity(@Path("provinceId") int provinceId);

    /**
     * 获取中国某市下面的县列表
     *
     * @return
     */
    @GET("china/{provinceId}/{cityId}")
    Call<List<County>> getChinaCounty(@Path("provinceId") int provinceId, @Path("cityId") int cityId);

    /**
     * 获取某个城市的天气信息
     *
     * @return
     */
    @GET("weather")
    Call<Weather> getWeatherInfo(@Query("cityid") String cityid, @Query("key") String key);

    /**
     * 获取每日一图作为背景
     * 暂时没用到
     * @return
     */
    @GET("bing_pic")
    Call<String> getBingPic();

}
