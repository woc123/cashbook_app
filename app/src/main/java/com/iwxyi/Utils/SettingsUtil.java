package com.iwxyi.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsUtil {

    /**
     * 读取设置
     * @param ct 上下文
     * @param key 关键词
     * @return 设置项的值
     */
    public static String getVal(Context ct, String key) {
        if (ct == null) {
            return "";
        }
        SharedPreferences sp = ct.getSharedPreferences("config", 0);
        return sp.getString(key, "");
    }

    /**
     * 保存设置
     * @param ct 上下文
     * @param key 关键词
     * @param val 值（字符串）
     */
    public static void setVal(Context ct, String key, String val) {
        SharedPreferences sp = ct.getSharedPreferences("config", 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, val);
        edit.commit();
    }

    /**
     * 保存设置
     * @param ct 上下文
     * @param key 关键词
     * @param val 值（整数）
     */
    public static void setVal(Context ct, String key, int val) {
        SharedPreferences sp = ct.getSharedPreferences("config", 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(key, val);
        edit.commit();
    }

    /**
     * 保存设置
     * @param ct 上下文
     * @param key 关键词
     * @param val 值（长整数型）
     */
    public static void setVal(Context ct, String key, long val) {
        SharedPreferences sp = ct.getSharedPreferences("config", 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putLong(key, val);
        edit.commit();
    }

    /**
     * 读取设置
     * @param ct 上下文
     * @param key 关键词
     * @return 整数值
     */
    public static int getInt(Context ct, String key) {
        if (ct == null) {
            return 0;
        }
        SharedPreferences sp = ct.getSharedPreferences("config", 0);
        return sp.getInt(key, 0);
    }

    /**
     * 读取设置
     * @param ct 上下文
     * @param key 关键词
     * @return 字符串值
     */
    public static long getLong(Context ct, String key) {
        if (ct == null) {
            return 0;
        }
        SharedPreferences sp = ct.getSharedPreferences("config", 0);
        return sp.getLong(key, 0);
    }
}
