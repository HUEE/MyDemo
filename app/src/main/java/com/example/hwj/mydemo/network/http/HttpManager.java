package com.example.hwj.mydemo.network.http;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hwj on 17-12-11.
 */
@Singleton
public class HttpManager {
    public static final String BASE_URL = "https://api.douban.com/v2/movie/";
    Retrofit.Builder retrofit;

    @Inject
    public HttpManager (OkHttpClient okHttpClient, HttpLoggingInterceptor logging) {
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL);
    }
}
