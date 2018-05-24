package xyz.yikai.kky.web;

import com.xlg.library.base.BaseApp;
import com.xlg.library.network.NetworkUtil;
import com.xlg.library.utils.ToastUtil;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import xyz.yikai.kky.config.CacheConfig;
import xyz.yikai.kky.helper.UserHelper;

/**
 * @Author: Jason
 * @Time: 2018/4/24 11:43
 * @Description:网络请求I级【1、参数再处理；2、无网提示】
 */
public class RequestWebHelper {

    private static boolean isCanMotitor = true;
    private static RequestWebHelper mRequestHeper;
    private static CommonRequest comRequest;
    public static final String netWrong = "当前网络不可用";

    public static RequestWebHelper getInstance() {
        if (null == mRequestHeper) {
            mRequestHeper = new RequestWebHelper();
            comRequest = CommonRequest.getCommonRequest(BaseApp.getAppContext());
        }
        return mRequestHeper;
    }

    /**
     * get请求
     *
     * @param url
     * @param params
     * @param tag 请求网络标签，用来区分请求类型
     * @param rListener
     */
    public void requestGet(IRequestResultListener rListener, String url, Map<String, String> params, ActionType...tag) {

        if (!NetworkUtil.isNetworkAvailable(BaseApp.getAppContext()) && isCanMotitor) {
            isCanMotitor = false;
            rListener.onFail(netWrong, tag);
            ToastUtil.getInstance().showToast(netWrong); //错误信息toast
            return;
        }
        isCanMotitor = true;
        comRequest = CommonRequest.getCommonRequest(BaseApp.getAppContext());
        comRequest.setRequestListener(rListener);
        url=sortAndCombineGetParams(url,params);
        comRequest.requestGet(url, tag);
    }

    /**
     * post请求
     *
     * @param url
     * @param params
     * @param tag 请求网络标签，用来区分请求类型
     * @param rListener
     */
    public void requestPost(IRequestResultListener rListener, String url, Map<String, String> params,
                            ActionType...tag) {

        if (!NetworkUtil.isNetworkAvailable(BaseApp.getAppContext()) && isCanMotitor) {
            isCanMotitor = false;
            rListener.onFail(netWrong, tag);
            ToastUtil.getInstance().showToast(netWrong); //错误信息toast
            return;
        }
        isCanMotitor = true;
        comRequest = CommonRequest.getCommonRequest(BaseApp.getAppContext());
        comRequest.setRequestListener(rListener);
        params=sortAndCombineParams(params);
        comRequest.requestPost(url, params, tag);
    }

    public void deleteReq(ActionType...tag) {
        if (comRequest != null)
            comRequest.cancleRequest(tag);
    }

    /**
     * 组合参数，排序
     * @param params
     * @return
     */
    private String sortAndCombineGetParams(String url,Map<String, String> params) {
        if (params==null){
            params=new HashMap<>();
        }
        params = combineParams(params);
        Map<String, String> sortParams = new TreeMap<String, String>(new MyCompart());
        sortParams.putAll(params);
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : sortParams.entrySet()) {
            String value = entry.getValue();
            if (value == null) {//判断是否为空
                sortParams.remove(entry);
            }else {
                builder.append(entry.getKey() + "=" + value + "&");
            }
        }
        return url.concat("?").concat(builder.toString().substring(0, builder.length() - 1));
    }

    /**
     * 组合参数，排序
     * @param params
     * @return
     */
    private Map<String, String> sortAndCombineParams(Map<String, String> params) {
        if (params==null){
            params=new HashMap<>();
        }
        params = combineParams(params);
        Map<String, String> sortParams = new TreeMap<String, String>(new MyCompart());
        sortParams.putAll(params);
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : sortParams.entrySet()) {
            String value = entry.getValue();
            if (value == null) {//判断是否为空
                sortParams.remove(entry);
            }else {
                builder.append(entry.getKey() + "=" + value + "&");
            }
        }
        return sortParams;
    }

    /**
     * 组合参数
     *
     * @param params
     *            security: xxxxx // 加密串 token：xxxxx // 令牌 version_no：xxxx //
     *            版本号 device_type:xxxx // 设备类型(ios或者android) device_id:xxxx //
     *            设备id timestamp:xxxx // 时间戳 system_version:xxx//系统版本
     *            device_name:xxx//设备型号
     *
     * @return
     */
    private Map<String, String> combineParams(Map<String, String> params) {
        params.put(InterfaceParam.PARAM_TOKEN, UserHelper.isLogin()? CacheConfig.getToken() :"");
//        params.put(InterfaceData.COMMON_PARAMETERS_VERSION_NO, NetworkParamsProvider.getInstance().getSystemInfo().appVersion);
//        params.put(InterfaceData.COMMON_PARAM_VERSIONCODE, UpgradeUtil.getVersionName(App.getAppContext()));
//        params.put(InterfaceData.COMMON_PARAM_BUILDNUMBER, UpgradeUtil.getVersionCode(App.getAppContext()));
//        params.put(InterfaceData.COMMON_PARAMETERS_DEVICE_TYPE, "android");
//        params.put(InterfaceData.COMMON_PARAMETERS_DEVICE_ID, NetworkParamsProvider.getInstance().getSystemInfo().imei);
//        params.put(InterfaceData.COMMON_PARAMETERS_TIMESTAMP, System.currentTimeMillis() / 1000 + "");
//        params.put(InterfaceData.COMMON_PARAMETERS_SYSTEM_VERSION, NetworkParamsProvider.getInstance().getSystemInfo().versionSystem);
//        String phoneModel = NetworkParamsProvider.getInstance().getSystemInfo().phoneModel.replace(" ","");
//        phoneModel = phoneModel.replace("+","");
//        params.put(InterfaceData.COMMON_PARAMETERS_DEVICE_NAME, phoneModel);
//        params.put(InterfaceData.COMMON_PARAMETERS_CHANNEL_ID,  Utils.getChannelID());
        return params;
    }

    class MyCompart implements Comparator<String> {
        @Override
        public int compare(String lhs, String rhs) {
            return lhs.compareTo(rhs);
        }
    }
}
