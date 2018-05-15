package xyz.yikai.kky.config;

import xyz.yikai.kky.BuildConfig;

/**
 * @Author: Jason
 * @Time: 2018/4/24 11:44
 * @Description:全局配置
 */
public class Config {

    public static boolean isOffline = !CacheConfig.getBoolean(CacheConfig.IS_TEST);

    /**
     * 接口域名
     */
    public static String DOMAIN_URL;

    static {
        if (!BuildConfig.DEBUG) { //发布环境
            DOMAIN_URL = "https://yy.kaikeyi.cn/api/"; //线上服务器地址
        } else if (isOffline) { //测试线下环境
            DOMAIN_URL = "https://yy.kaikeyi.cn/api/"; //测试服务器地址
        } else { //测试线上环境
            DOMAIN_URL = "https://yy.kaikeyi.cn/api/"; //线上服务器地址
        }
    }
}
