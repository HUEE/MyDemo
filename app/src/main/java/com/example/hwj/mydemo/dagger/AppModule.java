package com.example.hwj.mydemo.dagger;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hwj on 17-12-12.
 * 全局单例
 */
@Module
public class AppModule {
//    private final MyApplication context;
//
//    public AppModule (MyApplication application) {
//        this.context = application;
//    }
//
//    @Singleton
//    @Provides
//    public MyApplication providerContext () {
//        return context;
//    }
//
//    @Singleton
//    @Provides
//    public Context providerApplicationContext () {
//        return context;
//    }

    @Singleton
    @Provides
    @Named("default")
    public SharedPreferences providerSharePreference (Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
