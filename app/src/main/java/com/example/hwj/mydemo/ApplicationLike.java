package com.example.hwj.mydemo;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.example.hwj.mydemo.dagger.NetworkModule;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.interfaces.BetaPatchListener;
import com.tencent.tinker.loader.app.DefaultApplicationLike;

import java.util.Locale;

/**
 * 自定义ApplicationLike类.
 * <p>
 * 注意：这个类是Application的代理类
 */
public class ApplicationLike extends DefaultApplicationLike {

    public static final String TAG = "CrashReport";

    public AppComponent appComponent;
    private Application application;

    public ApplicationLike (Application application, int tinkerFlags,
                            boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime,
                            long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime,
                applicationStartMillisTime, tinkerResultIntent);
        this.application = application;
    }

    @Override
    public void onCreate () {
        initBugly();
        initDagger();
    }

    private void initDagger () {
//        appComponent = DaggerAppComponent.builder()
//                .networkModule(new NetworkModule())
//                .build();
//        appComponent.inject(application);
    }

    private void initBugly () {
        // 设置是否开启热更新能力，默认为true
        Beta.enableHotfix = true;
        // 设置是否自动下载补丁，默认为true
        Beta.canAutoDownloadPatch = true;
        // 设置是否自动合成补丁，默认为true
        Beta.canAutoPatch = true;
        // 设置是否提示用户重启，默认为false
        Beta.canNotifyUserRestart = false;
        // 补丁回调接口
        Beta.betaPatchListener = new BetaPatchListener() {
            @Override
            public void onPatchReceived (String patchFile) {
                Log.d(TAG, "补丁下载地址-->" + patchFile);
            }

            @Override
            public void onDownloadReceived (long savedLength, long totalLength) {
                Log.d(TAG, String.format(Locale.getDefault(), "%s %d%%",
                        Beta.strNotificationDownloading,
                        (int) (totalLength == 0 ? 0 : savedLength * 100 / totalLength)));
            }

            @Override
            public void onDownloadSuccess (String msg) {
                Log.d(TAG, "补丁下载成功-->" + msg);
            }

            @Override
            public void onDownloadFailure (String msg) {
                Log.d(TAG, "补丁下载失败");

            }

            @Override
            public void onApplySuccess (String msg) {
                Log.d(TAG, "补丁应用成功-->" + msg);
            }

            @Override
            public void onApplyFailure (String msg) {
                Log.d(TAG, "补丁应用成功-->" + msg);
            }

            @Override
            public void onPatchRollback () {

            }
        };

        // 设置开发设备，默认为false，上传补丁如果下发范围指定为“开发设备”，需要调用此接口来标识开发设备
        Bugly.setIsDevelopmentDevice(getApplication(), true);
        //这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
        //在测试阶段设置第三个参数成true，发布时设置为false。
        Bugly.init(getApplication(), "17af477045", true);
    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached (Context base) {
        super.onBaseContextAttached(base);
        MultiDex.install(base);
        Beta.installTinker(this);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallback (
            Application.ActivityLifecycleCallbacks callbacks) {
        getApplication().registerActivityLifecycleCallbacks(callbacks);
    }

}
