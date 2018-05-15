package xyz.yikai.kky.helper;

import xyz.yikai.kky.config.CacheConfig;

/**
 * @Author: Jason
 * @Time: 2018/4/24 20:33
 * @Description:用户工具类
 */
public class UserHelper {

    /** 是否登录 */
    public static boolean isLogin(){

        return CacheConfig.getToken() != null;
    }
}
