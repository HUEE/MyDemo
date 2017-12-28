package com.example.hwj.mydemo.dagger;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.hwj.mydemo.MyApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by weilu on 2016/1/26.
 */
@Module
public class StorageModule {

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences (MyApplication application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

}
