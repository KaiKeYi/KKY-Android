package xyz.yikai.kky.web;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Jason
 * @Time: 2018/4/24 20:00
 * @Description:相应码
 */
public class RespondCode {

    public static final String SUCCESS = "200"; //请求成功
    public static final String PAGE_404 = "404"; //请求路径不存在
    public static final String SYSTEM_500 = "500"; //系统错误
    public static final String NetworkError = "999995"; //Socket关闭，服务器宕机，DNS错误都会产生这个错误
    public static final String NoConnectionError = "999996"; //客户端无法连接到目标服务器
    public static final String AuthFailureError = "999997"; //HTTP身份验证错误
    public static final String TIMEOUT = "999998"; //请求服务器超时
    public static final String NET_FAIL = "999999"; //网络请求异常

    private static Map<String, String> mErrorMaps;

    static {
        mErrorMaps = new HashMap<String, String>();
        mErrorMaps.put(PAGE_404, "请求路径不存在[" + PAGE_404 + "]");
        mErrorMaps.put(SYSTEM_500, "系统错误[" + SYSTEM_500 + "]");
        mErrorMaps.put(NetworkError, "服务器无法连接" );
        mErrorMaps.put(NoConnectionError, "服务器连接失败");
        mErrorMaps.put(AuthFailureError, "HTTP身份验证错误");
        mErrorMaps.put(TIMEOUT, "请求服务器超时");
        mErrorMaps.put(NET_FAIL, "网络请求异常");
    }

    public static String getErrorNotice(String code) {
        String errorMsg = mErrorMaps.get(code);
        if (errorMsg == null) {
            errorMsg = mErrorMaps.get(NoConnectionError);
        }
        return errorMsg;
    }
}
