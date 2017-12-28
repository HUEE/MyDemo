/**
 * @project: 58bangbang
 * @file: SharedPreferencesUtil.java
 * @date: 2014年9月17日 上午10:26:00
 * @copyright: 2014  58.com Inc.  All rights reserved.
 */
package com.example.hwj.mydemo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

/**
 * @author 黄宏宇
 * @date: 2014年9月17日 上午10:26:00
 */
public final class SharedPreferencesUtil {

    //设备标识
    public static final String DEVICE_ID = "device_id";

    /* 本地缓存版本号，用于缓存控制 */
    private static final String SHARED_PREFERENCES_INFO = "bangbang.shareInfo";
    private static final String SHARED_PREFERENCES_HEADER = "bangbang.header";

    /*首页弹框广告时间戳*/
    public static final String ADVERT_NEXT_TIME = "advertNextTime";

    private static Context myContext;

    private static SharedPreferences mPreferences;

    private static Editor mEditor;

    private static SharedPreferencesUtil mSharedInstance = new SharedPreferencesUtil();

    private SharedPreferencesUtil () {

    }

    /**
     * 单例模式获得对象实例
     */
    public static SharedPreferencesUtil getInstance (Context context) {
        myContext = context.getApplicationContext();
        if (mPreferences == null && myContext != null) {
            mPreferences = myContext.getSharedPreferences(
                    SHARED_PREFERENCES_INFO, Context.MODE_PRIVATE);
            mEditor = mPreferences.edit();
        }
        return mSharedInstance;
    }

    /**
     * 是否有键
     */
    public boolean isContainKey (String key) {
        return mPreferences.contains(key);
    }

    /**
     * 删除指定键的值item
     */
    public boolean clearItem (String key) {
        mEditor.remove(key);
        return mEditor.commit();
    }

    /**
     * 获得所有保存对象
     */
    @SuppressWarnings("unchecked")
    public HashMap<String, ?> getAll () {
        if (mPreferences.getAll() instanceof HashMap) {
            return (HashMap<String, ?>) mPreferences.getAll();
        }
        return null;
    }

    /**
     * 给指定键设置String值
     */
    public boolean setString (String key, String value) {
        if (mPreferences.contains(key)) {
            mEditor.remove(key);
        }
        mEditor.putString(key, value);
        return mEditor.commit();
    }

    /**
     * 获得指定键的String类型值
     *
     * @param key 键
     */
    public String getString (String key) {
        return mPreferences.getString(key, "");
    }

    /**
     * 获得指定键的String类型值，带有默认值的
     *
     * @param key      键
     * @param defValue 默认值
     */
    public String getString (String key, String defValue) {
        return mPreferences.getString(key, defValue);
    }

    /**
     * 给指定键设置int值
     */
    public boolean setInt (String key, int value) {
        if (mPreferences.contains(key)) {
            mEditor.remove(key);
        }
        mEditor.putInt(key, value);
        return mEditor.commit();
    }

    /**
     * 获得int类型数据
     */
    public int getInt (String key) {
        return mPreferences.getInt(key, -1);
    }

    /**
     * 获得int类型数据，带有默认值的
     */
    public int getInt (String key, int defValue) {
        return mPreferences.getInt(key, defValue);
    }

    /**
     * 设置float类型数据
     */
    public boolean setFloat (String key, float value) {
        if (mPreferences.contains(key)) {
            mEditor.remove(key);
        }
        mEditor.putFloat(key, value);
        return mEditor.commit();
    }

    /**
     * 获得float类型数据
     */
    public float getFloat (String key) {
        return mPreferences.getFloat(key, 0);
    }

    /**
     * 获得float类型数据，带有默认值的
     */
    public float getFloat (String key, float defValue) {
        return mPreferences.getFloat(key, defValue);
    }

    /**
     * 设置long类型数据，带有默认值的
     */
    public boolean setBoolean (String key, boolean value) {
        if (mPreferences.contains(key)) {
            mEditor.remove(key);
        }
        mEditor.putBoolean(key, value);
        return mEditor.commit();
    }

    /**
     * 获得boolean类型数据
     */
    public boolean getBoolean (String key, boolean defValue) {
        return mPreferences.getBoolean(key, defValue);
    }

    /**
     * 设置long类型数据
     */
    public boolean setLong (String key, long value) {
        if (mPreferences.contains(key)) {
            mEditor.remove(key);
        }
        mEditor.putLong(key, value);
        return mEditor.commit();
    }

    /**
     * 获得long类型数据
     */
    public long getLong (String key) {
        return mPreferences.getLong(key, 0);
    }

    /**
     * 获得long类型数据，带有默认值的
     */
    public long getLong (String key, long defValue) {
        return mPreferences.getLong(key, defValue);
    }

    /**
     * 程序第一次运行之后设置
     *
     * @param context 上下文
     * @author 黄宏宇
     */
    public static void setAppIsFirstInit (String userId, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("user",
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putBoolean(userId + "-FIRST_INIT", false);
        editor.commit();
    }

    /**
     * 程序是否第一次运行
     *
     * @param userId
     * @param context 上下文
     * @return boolean
     * @author 黄宏宇
     */
    public static boolean appIsFirstInit (String userId, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("user",
                Context.MODE_PRIVATE);
        return preferences.getBoolean(userId + "-FIRST_INIT", true);
    }

    /**
     * 单例模式获得记录加载头像的对象实例
     */
    public static SharedPreferencesUtil getHeaderInstance (Context context) {
        myContext = context.getApplicationContext();
        if (mPreferences == null && myContext != null) {
            mPreferences = myContext.getSharedPreferences(
                    SHARED_PREFERENCES_HEADER, Context.MODE_PRIVATE);
            mEditor = mPreferences.edit();
        }
        return mSharedInstance;
    }
}