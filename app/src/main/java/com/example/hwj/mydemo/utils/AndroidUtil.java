/**
 * @project: 58bangbang
 * @file: AndroidUtil.java
 * @date: 2014年8月25日 下午6:43:24
 * @copyright: 2014  58.com Inc.  All rights reserved.
 */
package com.example.hwj.mydemo.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings.Secure;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.crypto.Cipher;


/**
 * 跟andoird系统相关的工具类
 *
 * @author 黄宏宇
 * @date: 2014年8月25日 下午6:43:24
 */
public class AndroidUtil {


//    public static void sendSMS(String phoneNume, String content, Context context) {
//        String phone_number = phoneNume.trim();
//        String sms_content = content.trim();
//        if (phone_number.equals("")) {
//            IMCustomToast.makeText(context, String.valueOf(R.string.common_send_sms_fail), IMCustomToast.TYPE_FAIL).show();
//        } else {
//            Uri uri = Uri.parse("smsto:" + phoneNume);
//            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
//            intent.putExtra("sms_body", sms_content);
//            context.startActivity(intent);
//        }
//    }

    /**
     * 发送直接短信，不会调起本机短信程序
     *
     * @param phoneNumber 接收方号码
     * @param content     内容
     * @author 黄宏宇
     */
    public static void sendDirectSms (String phoneNumber, String content) {
        SmsManager smsManager = SmsManager.getDefault();
        List<String> divideContents = smsManager.divideMessage(content);
        for (String text : divideContents) {
            smsManager.sendTextMessage(phoneNumber, null, text, null, null);
        }
    }

    /**
     * 调用本地短信app发送短信，无内容
     *
     * @param phoneNumber 手机号码，多个用分号分隔
     * @param context     上下文
     * @author 黄宏宇
     */
    public static void sendSmsByLocalApp (String phoneNumber, Context context) {
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        try {
            context.startActivity(intent);
        } catch (Exception e) {

        }
    }

    /**
     * 调用系统拨号程序
     *
     * @param number  电话号码
     * @param context 上下文
     * @author 黄宏宇
     */
    public static void call (String number, Context context) {
        //调用拨号程序
        Intent intent = new Intent();
        intent.setAction("android.intent.action.DIAL");
        intent.setData(Uri.parse("tel:" + number));
        context.startActivity(intent);
    }

    /**
     * 获取mac地址
     *
     * @param context 上下文对象
     * @return mac地址
     * @author 黄宏宇
     */
    public static String getLocalMacAddress (Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    /**
     * 获取Ip.
     *
     * @return
     * @author liam.zhuang(庄乾六)
     */
    public static String getLocalIpAddress () {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
//            Logger.trace(ex.toString());
            ex.printStackTrace();
        }
        return "";
    }

    /**
     * @param context 上下文对象
     * @return 设备标识码IMEI
     * @author lijc getIMEI:(获取设备标识码IMEI). <br/>
     */
    public static String getIMEI (Context context) {
        return getDeviceId(context);
    }

    /**
     * sdcard是否可读写
     *
     * @return 是否可读写
     */
    public static boolean isSdcardReady () {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 由于写入日志接口使用该方法，这里加个缓存数据，避免每次读取开销
     */
    private static int mIsSdcardAvailable = -1;

    /**
     * 判断sd卡剩余空间是否足够
     *
     * @return boolean
     */
    @SuppressWarnings("deprecation")
    public static boolean isSdcardAvailable () {
        if (mIsSdcardAvailable < 0) {
            mIsSdcardAvailable = 0;
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                File sdcardDir = Environment.getExternalStorageDirectory();
                StatFs sf = new StatFs(sdcardDir.getPath());
                long availCount = sf.getAvailableBlocks();
                long blockSize = sf.getBlockSize();
                long availSize = availCount * blockSize / 1024;

                if (availSize >= 3072L) {
                    mIsSdcardAvailable = 1;
                }
            }
        }

        return (mIsSdcardAvailable == 1);
    }

    /**
     * 获取文件系统的剩余空间，单位：KB
     *
     * @param dirName 文件目录
     * @return 剩余空间
     */
    @SuppressWarnings("deprecation")
    public static long getFileSystemAvailableSize (File dirName) {
        long availableSize = -1;
        if (dirName != null && dirName.exists()) {
            StatFs sf = new StatFs(dirName.getPath());
            long blockSize = sf.getBlockSize();
            long availableBlocks = sf.getAvailableBlocks();
            availableSize = availableBlocks * blockSize / 1024;
        }
        return availableSize;
    }

    /**
     * 播放通知声音（系统默认）
     *
     * @param context
     * @author 马健
     */
    public static void notifySound (Context context) {
        NotificationManager manger = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification();
        notification.defaults = Notification.DEFAULT_SOUND;
        manger.notify(1, notification);
    }

//    /**
//     * 手机震动
//     *
//     * @param context
//     * @author 马健
//     */
//    public static void notifyVibrator(Context context) {
//        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
//        vibrator.vibrate(200);
//    }

    /**
     * 获取当前应用版本号
     *
     * @param context
     * @return 当前应用的版本号，如果获取失败则返回0
     * @author lijc getVersionCode:(这里用一句话描述这个方法的作用). <br/>
     */
    public static int getVersionCode (Context context) {
        int versionCode = 0;
        PackageManager manager;
        PackageInfo info = null;
        manager = context.getPackageManager();
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
            versionCode = info.versionCode;
        } catch (NameNotFoundException e) {
            versionCode = 0;
        }
        return versionCode;
    }

    /**
     * 获取当前应用版本名称
     *
     * @param context
     * @return 当前应用的版本名称，如果获取失败则返回“”
     * @author lijc getVersionName:(这里用一句话描述这个方法的作用). <br/>
     */
    public static String getVersionName (Context context) {
        String versionName = "";
        PackageManager manager;
        PackageInfo info = null;
        manager = context.getPackageManager();
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
            versionName = info.versionName;
        } catch (NameNotFoundException e) {
            versionName = "";
        }
        return versionName;
    }

    private static String uuid;

    /**
     * 获取安卓设备唯一标识（这里读写SharedPerference逻辑别动）
     *
     * @param context
     * @return
     * @author lijc getUUID:(这里用一句话描述这个方法的作用). <br/>
     */
    public static String getDeviceId (Context context) {
        if (uuid == null) {
            //读取上次保存的UUID
            final String id = SharedPreferencesUtil.getInstance(context).getString(SharedPreferencesUtil.DEVICE_ID);
            if (!TextUtils.isEmpty(id)) {
                uuid = id;
            } else {
                //ANDROID_ID是设备第一次启动时产生和存储的64bit的一个数，当设备被wipe后该数重置
                //它在Android <=2.1 or Android >=2.3的版本是可靠、稳定的，但在2.2的版本并不是100%可靠的
                //在主流厂商生产的设备上，有一个很经常的bug，就是每个设备都会产生相同的ANDROID_ID：9774d56d682e549c
                try {
                    final String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
                    if (!"9774d56d682e549c".equals(androidId)) {
                        uuid = androidId;
                    } else {
                        //权限： 获取DEVICE_ID需要READ_PHONE_STATE权限，但如果我们只为了获取它，没有用到其他的通话功能，那这个权限有点大才小用
                        //bug：在少数的一些手机设备上，该实现有漏洞，会返回垃圾，如:zeros或者asterisks的产品
                        final String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE))
                                .getDeviceId();
                        if (!TextUtils.isEmpty(deviceId)) {
                            uuid = deviceId;
                        } else {
                            uuid = UUID.randomUUID().toString();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    uuid = UUID.randomUUID().toString();
                }
                //保存的UUID
                SharedPreferencesUtil.getInstance(context).setString(SharedPreferencesUtil.DEVICE_ID, uuid);
            }
        }
        Log.d("caowei", "push service uuid = " + uuid);
        return uuid;
    }

    /**
     * 获取手机型号
     *
     * @param context
     * @return
     * @author lijc getModel:(这里用一句话描述这个方法的作用). <br/>
     */
    public static String getModel (Context context) {
        return android.os.Build.MODEL != null ? android.os.Build.MODEL.replace(" ", "") : "unknown";
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName (Context context) {
        String versionName = "";
        try {
            // ---get the package info---  
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
//            Logger.d("VersionInfo", "Exception");
        }
        return versionName;
    }

    /**
     * 获取android系统版本
     *
     * @param context
     * @return
     * @author lijc getSystemVersion:(这里用一句话描述这个方法的作用). <br/>
     */
    public static int getSystemVersion (Context context) {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * 获取屏幕密度，每英寸有多少个显示点
     *
     * @param context
     * @return
     * @author lijc getDensity:(这里用一句话描述这个方法的作用). <br/>
     */
    public static float getDensity (Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 判断应用是否后台运行
     *
     * @param context
     * @return
     * @author lijc isBackground:(这里用一句话描述这个方法的作用). <br/>
     */
    public static boolean isBackground (Context context) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
            for (RunningAppProcessInfo appProcess : appProcesses) {
                if (appProcess.processName.equals(context.getPackageName())) {
//                    if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
//                        return true;
//                    } else {
//                        return false;
//                    }
                    return appProcess.importance == RunningAppProcessInfo.IMPORTANCE_BACKGROUND;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取渠道号
     *
     * @param context
     * @return
     */
    public static String getChannel (Context context) {
        ApplicationInfo appinfo = context.getApplicationInfo();
        String sourceDir = appinfo.sourceDir;
        String ret = "";
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(sourceDir);
            Enumeration<?> entries = zipfile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (entryName.startsWith("META-INF/bbchannel")) {
                    ret = entryName;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String[] split = ret.split("_");
        //noinspection ConstantConditions
        if (split != null && split.length >= 2) {
            return ret.substring(split[0].length() + 1);
        } else {
            return "unknown";
        }
    }

    /**
     * 获取status bar 高度
     * zhaobo
     */

    public static int getSystemStatusBarHeight (Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }

//    /**
//     * 判断是否拥有某种权限
//     * @param permission 例如 android.permission.READ_CONTACTS
//     * @return
//     */
//    public static boolean hasPermission(String permission){
//       PackageManager pm = App.getApp().getPackageManager();
//       return (PackageManager.PERMISSION_GRANTED == pm.checkPermission(permission, "air.com.wuba.bangbang"));
//    }
//
//    public static void hideSoftKeyboard(Activity activity) {
//        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
//        if (imm.isActive()) {
//            View focusView = activity.getCurrentFocus();
//            if (focusView != null) {
//                imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
//            }
//        }
//    }

    /**
     * 获取一个字符串的base64编码形式
     *
     * @param str
     * @return
     */
    public static String getBase64EncodeStr (String str) {
        byte[] encode = Base64.encode(str.getBytes(), Base64.DEFAULT);
        String enc = new String(encode);
        enc = enc.replace("\n", "");
        enc = enc.replace(" ", "");
        return enc;
    }

    /**
     * 获取base64解码
     *
     * @param base64Str
     * @return
     */
    public static String getDecodeBase64Str (String base64Str) {

        try {
            byte[] baseByte = Base64.decode(base64Str, Base64.DEFAULT);
            return new String(baseByte);
        } catch (Exception e) {
//            Logger.e("AndroidUtil", "还原base64str错误:", base64Str);
            e.printStackTrace();
            return base64Str;
        }
    }

    /**
     * 加密<br>
     * 用公钥加密
     *
     * @param data
     * @param publickey
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey (byte[] data, String publickey) throws Exception {
        // 对公钥解密
        byte[] keyBytes = decodeHEX(publickey);
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * @param str
     * @return
     * @Description:
     */
    public static byte[] decodeHEX (String str) {
        if (str == null)
            return null;
        str = str.trim();
        int len = str.length();
        if (len == 0 || len % 2 == 1)
            return null;
        byte[] b = new byte[len / 2];
        try {
            for (int i = 0; i < str.length(); i += 2) {
                b[i / 2] = (byte) Integer.decode("0x" + str.substring(i, i + 2)).intValue();
            }
            return b;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param b
     * @return
     * @Description:
     */
    public static String encodeHEX (byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs;
    }

    public static boolean isWeixinAvilible (Context context) {
        // 获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px (Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip (Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
