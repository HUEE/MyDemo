package com.example.hwj.mydemo.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 保存一些工具类
 *
 * @author huwenjie
 * @time 2016/4/15
 */
public class DeviceTools {

    /**
     * @param context Context
     * @param path    文件的绝对路径
     * @return boolean
     * @description 判断文件是否存在
     */
    public static boolean isFileExist(Context context, String path) {
        File file = new File(path);
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * @description 获取手机IMEI
     */
    public static String getIMEI(Context context) {
        String imei = "";

        if (context != null) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (!TextUtils.isEmpty(tm.getDeviceId())) {
                imei = tm.getDeviceId();
            }
        }

        return imei;
    }

    /**
     * @description 安装私有文件夹下的apk
     */
    public static void installApk(Context context, String file) {
        if (isFileExist(context, file)) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            try {
                intent.putExtra(
                        Intent.EXTRA_UID,
                        context.getPackageManager().getPackageInfo(
                                context.getPackageName(),
                                PackageManager.GET_SIGNATURES).applicationInfo.uid);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.parse("file://" + file),
                        "application/vnd.android.package-archive");
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @description 获取手机品牌
     */
    public static String getBrand() {
        return android.os.Build.BRAND.toLowerCase();
    }

    /**
     * @description 获取手机型号
     */
    public static String getModel() {
        return android.os.Build.MODEL.toLowerCase();
    }

    /**
     * @param time 从数据库中获取的时间字符串
     * @return
     */
    public static String getFormatDatetime(String time) {
        try {
            Date date = new Date(time);
            SimpleDateFormat dateformat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            return dateformat.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 是否在后台运行
     *
     * @param context
     * @return
     */
    public static boolean isBackground(Context context) {
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> mRunningTasks = mActivityManager.getRunningTasks(1);
        for (ActivityManager.RunningTaskInfo amTask : mRunningTasks) {
            if ("com.wuba.wbmarketing".equals(amTask.topActivity.getPackageName())) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        if (context != null) {
            int result = 0;
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = context.getResources().getDimensionPixelSize(resourceId);
            }
            return result;
        }
        return 0;
    }

    /**
     * 获取屏幕高度
     */
    public static int getHeight(Context context){
        DisplayMetrics dm2 = context.getResources().getDisplayMetrics();
        return dm2.heightPixels;
    }
    /**
     * 获取屏幕宽度
     */
    public static int getWidth(Context context){
        DisplayMetrics dm2 = context.getResources().getDisplayMetrics();
        return dm2.widthPixels;
    }

}
