package com.example.hwj.mydemo;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * Created by weilu on 2016/1/26.
 */
public class MyApplication extends DaggerApplication {
    /** general variables */
    public static MyApplication me;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector () {
        return DaggerAppComponent.builder().create(this);
    }
}