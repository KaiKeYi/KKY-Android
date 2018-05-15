package xyz.yikai.kky.config;

import com.xlg.library.cache.SharePreferences;

/**
 * @Author: Jason
 * @Time: 2018/4/24 13:53
 * @Description:
 */
public class CacheConfig extends SharePreferences {

    /**
     * token
     */
    public static final String TOKEN = "TOKEN";

    /**
     * 是否是测试环境
     */
    public static final String IS_TEST = "IS_TEST";


    public static void setIsTest(boolean isTest) {
        putObject(IS_TEST, isTest);
    }

    public static boolean isTest() {
        return getBoolean(IS_TEST);
    }

    public static void setToken(String token) {
        sharePreferences().edit().putString(TOKEN, token).commit();
    }

    public static String getToken() {
        return getString(TOKEN);
    }
}
