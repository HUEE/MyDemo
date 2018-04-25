package com.example.hwj.mydemo.dagger;

import android.content.Context;

import com.example.hwj.mydemo.MyApplication;
//import com.example.hwj.mydemo.network.http.ParamsInterceptor;
import com.example.hwj.mydemo.utils.RequestInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hwj on 17-12-12.
 */
@Singleton
@Module
public class HttpModule {

    public static final int CONNECT_TIMEOUT_IN_MS = 30000;
    public static final String BASE_URL = "https://api.douban.com/v2/movie/";

    @Provides
    @Singleton
    Interceptor requestInterceptor (RequestInterceptor interceptor) {
        return interceptor;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient (RequestInterceptor requestInterceptor, MyApplication context) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new okhttp3.OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT_IN_MS, TimeUnit.MILLISECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(requestInterceptor)
                //请求参数拦截
//                .addInterceptor(new ParamsInterceptor(context))
                .build();
    }

    @Singleton
    @Provides
    Retrofit retrofit (OkHttpClient okHttpClient) {
        return new Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

}
