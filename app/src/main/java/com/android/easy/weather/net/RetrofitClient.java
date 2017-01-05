package com.android.easy.weather.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 2016/12/22
 * 用来创建Retrofit网络请求相关实例
 */

public class RetrofitClient {
    public static final String BASE_URL = "http://guolin.tech/api/";
    public static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .setLenient()
            .create();

    private static volatile RetrofitClient singleton;
    private final Retrofit mRetrofit;

    /**
     * 私有化构造器 不能通过new创建对象
     */
    private RetrofitClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new LogInterceptor())
                .connectTimeout(5000, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
    }

    /**
     * 通过单例模式获得该类的实例(双重检查)
     * @return  该类的唯一实例
     */
    public static RetrofitClient getInstance() {
        if (singleton == null) {
            synchronized (RetrofitClient.class) {
                if (singleton == null) {
                    singleton = new RetrofitClient();
                }
            }
        }
        return singleton;
    }

    /**
     * 获取ApiManager
     * @return  ApiManager
     */
    public ApiManager getApiManager() {
        return mRetrofit.create(ApiManager.class);
    }

}