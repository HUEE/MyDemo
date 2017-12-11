package com.example.hwj.mydemo.utils;

import android.os.Build;
import android.util.Log;

import com.example.hwj.mydemo.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Logger {
    /**
     * 自定义Log日志打印
     */

    //是否是调试模式,调试模式自动关闭日志打印
    private static boolean DEBUG = BuildConfig.DEBUG;

    public static void v (String c, String s) {
        if (isEnable()) {
            if (s == null)
                s = "\"NULL\"";
            Log.v(c, s);
        }
    }


    public static void d (String c, String s) {
        if (isEnable()) {
            if (s == null)
                s = "\"NULL\"";
            Log.d(c, s);
        }
    }

    public static void i (String c, String s) {
        if (isEnable()) {
            if (s == null)
                s = "\"NULL\"";
            Log.i(c, s);
        }
    }

    public static void w (String c, String s) {
        if (isEnable()) {
            if (s == null)
                s = "\"NULL\"";
            Log.w(c, s);
        }
    }

    public static void e (String c, String s) {
        if (isEnable()) {
            if (s == null)
                s = "\"NULL\"";
            Log.e(c, s);
        }
    }

    public static void d (String logMsg) {
        if (isEnable()) {
            log(getCurrentClassName(), getCurrentMethodName() + "(): " + logMsg);
        }
    }

    public static void json (String tag, Object source) {
        if (isEnable()) {
            Object o = getJsonObjFromStr(source);
            if (o != null) {
                try {
                    if (o instanceof JSONObject) {
                        format(tag, ((JSONObject) o).toString(2));
                    } else if (o instanceof JSONArray) {
                        format(tag, ((JSONArray) o).toString(2));
                    } else {
                        format(tag, source);
                    }
                } catch (JSONException e) {
                    format(tag, source);
                }
            } else {
                format(tag, source);
            }
        }
    }

    private static void log (String tag, String msg) {
        Log.d(tag, msg);
    }

    private static String getSplitter (int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append("-");
        }
        return builder.toString();
    }

    private static void format (String tag, Object source) {
        tag = " " + tag + " ";
        log(" ", " ");
        log(" ", getSplitter(50) + tag + getSplitter(50));
        log(" ", "" + source);
        log(" ", getSplitter(100 + tag.length()));
        log(" ", " ");
    }

    private static String getCurrentMethodName () {
        return Thread.currentThread().getStackTrace()[4].getMethodName();
    }

    private static String getCurrentClassName () {
        String className = Thread.currentThread().getStackTrace()[4].getClassName();
        String[] temp = className.split("[\\.]");
        className = temp[temp.length - 1];
        return className;
    }

    private static Object getJsonObjFromStr (Object test) {
        Object o = null;
        try {
            o = new JSONObject(test.toString());
        } catch (JSONException ex) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    o = new JSONArray(test);
                }
            } catch (JSONException ex1) {
                return null;
            }
        }
        return o;
    }

    public static boolean isEnable () {
        return DEBUG;
    }

    public static void setEnable (boolean flag) {
        Logger.DEBUG = flag;
    }


}
