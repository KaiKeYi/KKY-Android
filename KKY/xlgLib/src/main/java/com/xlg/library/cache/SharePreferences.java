package com.xlg.library.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.xlg.library.base.BaseApp;
import com.xlg.library.utils.Utils;

/**
 * @Author: Jason
 * @Time: 2018/4/24 11:58
 * @Description:缓存数据(key-value)
 */
public class SharePreferences {

    /**
     * sharePrefrences名称
     */
    public static final String SHARE_PREFERENCES = "SHARE_PREFERENCES";

    /**
     * 调试日志信息
     */
    public static final String LOG_INFO = "LOG_INFO";


    /**
     * getSharePreferences
     */
    public static SharedPreferences sharePreferences() {
        SharedPreferences preferences = BaseApp.getAppContext().getSharedPreferences(SHARE_PREFERENCES,
                Context.MODE_PRIVATE);
        return preferences;
    }

    /**
     * 获取字符串的值
     */
    public static String getString(String TAG) {
        return Utils.isEmpty(sharePreferences().getString(TAG, null))?"":sharePreferences().getString(TAG, null) ;
    }

    /**
     * 获取integer的值
     */
    public static Integer getInt(String TAG) {
        return sharePreferences().getInt(TAG, -1);
    }

    /**
     * 获取boolean的值
     */
    public static Boolean getBoolean(String TAG) {
        return sharePreferences().getBoolean(TAG, false);
    }

    /**
     * 获取float的值
     */
    public static Float getFloat(String TAG) {
        return sharePreferences().getFloat(TAG, 0);
    }

    /**
     * 获取Long的值
     */
    public static Long getLong(String TAG) {
        return sharePreferences().getLong(TAG, 0);
    }

    /**
     * 保存数据
     */
    public static void putObject(final String tag, final Object param) {

        SharedPreferences.Editor editor = sharePreferences().edit();

        if (param instanceof String) {
            String value = (String) param;
            editor.putString(tag, value);
        } else if (param instanceof Long) {
            long value = (Long) param;
            editor.putLong(tag, value);
        } else if (param instanceof Integer) {
            int value = (Integer) param;
            editor.putInt(tag, value);
        } else if (param instanceof Boolean) {
            boolean value = (Boolean) param;
            editor.putBoolean(tag, value);
        } else if (param instanceof Float
                || param instanceof Double) {
            float value = (Float) param;
            editor.putFloat(tag, value);
        } else if (null == param) {
            editor.putString(tag, null);
        } else {
            throw new RuntimeException("this sharePreferenes type " + param + " is not arrowed! ");
        }
        editor.commit();
    }

    /**
     * 清空数据
     */
    public static void clear() {
        sharePreferences().edit().clear().commit();
    }

    public static void setLogInfo(String info) {
        sharePreferences().edit().putString(LOG_INFO, info).commit();
    }

    public static String getLogInfo() { return getString(LOG_INFO); }
}
