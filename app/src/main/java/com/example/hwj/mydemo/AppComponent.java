package com.example.hwj.mydemo;

import com.example.hwj.mydemo.dagger.AppModule;
import com.example.hwj.mydemo.dagger.BuildersModule;
import com.example.hwj.mydemo.dagger.HttpModule;
import com.example.hwj.mydemo.dagger.ServiceModule;

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
        AppModule.class,
        HttpModule.class,
        ServiceModule.class,
        BuildersModule.class,
        AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class
})
interface AppComponent extends AndroidInjector<MyApplication> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<MyApplication> {
    }
}
