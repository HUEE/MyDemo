package com.example.hwj.mydemo.dagger;

import com.example.hwj.mydemo.Main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * 作者：weilu on 2017/6/9 11:16
 */
@Module
public abstract class BuildersModule {

//    @Binds
//    @IntoMap
//    @ActivityKey(MainActivity.class)
//    abstract AndroidInjector.Factory<? extends Activity> bindMainActivityInjectorFactory(MainComponent.Builder builder);
//
//    @Binds
//    @IntoMap
//    @ActivityKey(SecondActivity.class)
//    abstract AndroidInjector.Factory<? extends Activity> bindSecondActivityInjectorFactory(UserComponent.Builder builder);


    //    @ActivityScope
//    @ContributesAndroidInjector(modules = UserModule.class)
//    abstract SecondActivity contributeSecondActivityInjector();
//
//    @ActivityScope
    @ContributesAndroidInjector
    abstract MainActivity contributeActivityInjector ();
}
