package com.android.easy.weather.net;

import com.android.easy.weather.util.LoggerUtil;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by admin on 2016/12/22
 * 用来拦截网络请求并打印的相关信息
 */

public class LogInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        //  拦截并打印请求信息
        Request request = chain.request();
        Response response = chain.proceed(chain.request());
        MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        long t1 = System.nanoTime();
        LoggerUtil.printGeneralLog(String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));
        long t2 = System.nanoTime();
        LoggerUtil.printGeneralLog(String.format("Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        LoggerUtil.printGeneralLog("NetWork" + "response body:" + content);
        if (response.body() != null) {
            ResponseBody body = ResponseBody.create(mediaType, content);
            return response.newBuilder().body(body).build();
        }
        return response;
    }
}