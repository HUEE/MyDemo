package com.example.hwj.mydemo.utils.tools;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hwj on 16-8-18.
 * SharedPreferences数据存取相关操作
 */

public class SPreferences {

    private static final String SP_NAME = "sp_name";
    private SharedPreferences sp;
    private static SPreferences instance;

    public SPreferences(Context context) {
        sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    private static synchronized void syncInit(Context context) {
        instance = new SPreferences(context);
    }

    public static SPreferences getInstance(Context context) {
        if (instance == null) {
            syncInit(context);
        }
        return instance;
    }


    public int getInt(String key, int def) {
        try {
            if (sp != null)
                def = sp.getInt(key, def);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public void putInt(String key, int val) {
        try {
            if (sp != null) {
                SharedPreferences.Editor e = sp.edit();
                e.putInt(key, val);
                e.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getLong(String key, long def) {
        try {
            if (sp != null)
                def = sp.getLong(key, def);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public void putLong(String key, long val) {
        try {
            if (sp != null) {
                SharedPreferences.Editor e = sp.edit();
                e.putLong(key, val);
                e.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getString(String key, String def) {
        try {
            if (sp != null)
                def = sp.getString(key, def);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public void putString(String key, String val) {
        try {
            if (sp != null) {
                SharedPreferences.Editor e = sp.edit();
                e.putString(key, val);
                e.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getBoolean(String key, boolean def) {
        try {
            if (sp != null)
                def = sp.getBoolean(key, def);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public void putBoolean(String key, boolean val) {
        try {
            if (sp != null) {
                SharedPreferences.Editor e = sp.edit();
                e.putBoolean(key, val);
                e.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void remove(String key) {
        try {
            if (sp != null) {
                SharedPreferences.Editor e = sp.edit();
                e.remove(key);
                e.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
