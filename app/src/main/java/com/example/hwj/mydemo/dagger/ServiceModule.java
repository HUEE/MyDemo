package com.example.hwj.mydemo.dagger;

import com.example.hwj.mydemo.network.http.HttpService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by hwj on 17-12-12.
 */
@Module
public class ServiceModule {

    @Singleton
    @Provides
    public HttpService providerUserService (Retrofit retrofit) {
        return retrofit.create(HttpService.class);
    }

//    @Singleton
//    @Provides
//    public HttpService providerLocalService (Retrofit retrofit) {
//        return retrofit.create(HttpService.class);
//    }
}
