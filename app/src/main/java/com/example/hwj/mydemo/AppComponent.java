package com.example.hwj.mydemo;

import com.example.hwj.mydemo.dagger.BuildersModule;
import com.example.hwj.mydemo.dagger.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by hwj on 17-12-8.
 */

@Singleton
@Component(modules = {
        NetworkModule.class,
        BuildersModule.class,
        AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class
})
interface AppComponent extends AndroidInjector<MyApplication> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<MyApplication> {
    }
}
