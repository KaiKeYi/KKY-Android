package xyz.yikai.kky.web;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.xlg.library.cache.SharePreferences;
import com.xlg.library.utils.LogUtil;
import com.xlg.library.utils.ToastUtil;
import com.xlg.library.utils.Utils;
import com.xlg.library.utils.WindowUtil;
import com.xlg.library.web.HTTPSTrustManager;

import java.util.HashMap;
import java.util.Map;

import xyz.yikai.kky.BuildConfig;

/**
 * @Author: Jason
 * @Time: 2018/4/24 11:43
 * @Description:网络请求II级【1、请求失败错误信息提示；2、请求成功data数据回传；3、日志打印】
 */
public class CommonRequest {

    private int outTime = 15*1000; //超时15s

    private static RequestQueue mRequestQueue;
    private IRequestResultListener mResultListener;

    public static CommonRequest getCommonRequest(Context context) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context);
        }
        return new CommonRequest();
    }

    /**
     * get请求
     */
    public void requestGet(final String url, final ActionType...tag) {

        if (url.toString().contains("https")) {
            HTTPSTrustManager.allowAllSSL();
        }
        StringRequest stringRequestG = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseResponse(response, tag);

                        LogUtil.i("tag:"+tag+"参数:"+url+"返回结果:"+response);

                        String logStr = "时间："+ Utils.getCurrentTime()+"\n接口地址："+url+"\n返回结果："+response+"\n\n\n";
                        setLog(logStr);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error(error, tag);

                LogUtil.i("tag:"+tag+"参数:"+url+"返回结果:"+error);

                String logStr = "时间："+Utils.getCurrentTime()+"\n接口地址："+url+"\n返回结果："+error+"\n\n\n";
                setLog(logStr);
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();

                headers.put("Charset", "UTF-8");
                headers.put("Accept", "application/json");
                headers.put("Content-Type",
                        "application/x-www-form-urlencoded");
                headers.put("Protocol", "HTTP/1.1");

                return headers;
            }
        };

        if (tag != null) {
            stringRequestG.setTag(tag);
        }
        // 设置默认请求时间
        stringRequestG
                .setRetryPolicy(new DefaultRetryPolicy(outTime, 1, 1.0f));
        mRequestQueue.add(stringRequestG);
    }

    /**
     * post请求
     */
    public void requestPost(final String url, final Map<String, String> map,
                            final ActionType...tag) {

        if (url.startsWith("https")) {
            HTTPSTrustManager.allowAllSSL();
        }
        StringRequest stringRequestP = new StringRequest(Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        parseResponse(response, tag);

                        LogUtil.i("tag:"+tag+"参数:"+url+map+"返回结果:"+response);

                        String logStr = "时间："+Utils.getCurrentTime()+"\n接口地址："+url+map+"\n返回结果："+response+"\n\n\n";
                        setLog(logStr);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error(error, tag);

                LogUtil.i("tag:"+tag+"参数:"+url+map+"返回结果:"+error);

                String logStr = "时间："+Utils.getCurrentTime()+"\n接口地址："+url+map+"\n返回结果："+error+"\n\n\n";
                setLog(logStr);
            }
        }) {

            @Override
            protected Map<String, String> getPostParams()
                    throws AuthFailureError {
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();

                headers.put("Charset", "UTF-8");
                headers.put("Accept", "application/json");
                headers.put("Content-Type",
                        "application/x-www-form-urlencoded");

                return headers;
            }
        };
        if (tag != null) {
            stringRequestP.setTag(tag);
        }
        // 设置默认请求时间
        stringRequestP
                .setRetryPolicy(new DefaultRetryPolicy(outTime, 1, 1.0f));
        mRequestQueue.add(stringRequestP);
    }

    /**
     * 设置调试日志
     */
    public void setLog(String logStr) {
        SharePreferences.setLogInfo(logStr+SharePreferences.getLogInfo());
        if(BuildConfig.DEBUG){ //日志开启
            WindowUtil.setLogTv(logStr+WindowUtil.getLogTv());
        }
    }

    /**
     * 解析返回数据
     */
    protected void parseResponse(String response, ActionType...tag) {

        try {
            Gson gson = new Gson();
            StatusItem item = gson.fromJson(response, StatusItem.class);
            if (null == item) {
                mResultListener.onFail("解析网络数据出错", tag);
                ToastUtil.getInstance().showToast("解析网络数据出错"); //错误信息toast
                return;
            }
            if (!TextUtils.equals(String.valueOf(item.code), RespondCode.SUCCESS)) {
                mResultListener.onFail(item.msg,tag);
                ToastUtil.getInstance().showToast(item.msg); //错误信息toast
            } else {
                mResultListener.onSuccess(response, tag);
            }
        } catch (Exception e) {

            if (null != e) {
                e.printStackTrace();
                LogUtil.i(e.toString());
            }
        }
    }

    /**
     * 设置监听
     */
    public void setRequestListener(IRequestResultListener resultListener) {
        this.mResultListener = resultListener;
    }

    /**
     * Response.ErrorListener的异常处理
     */
    private void error(VolleyError error, ActionType...tag) {

        String returnCode = RespondCode.NET_FAIL;
        if (error == null) {

            returnCode = RespondCode.NET_FAIL; //网络请求异常

        } else if (error instanceof TimeoutError) {

            returnCode = RespondCode.TIMEOUT; //超时

        } else if (error instanceof AuthFailureError) {

            returnCode = RespondCode.AuthFailureError; //HTTP身份验证错误

        } else if (error instanceof NoConnectionError) {

            returnCode = RespondCode.NoConnectionError; //客户端没有网络连接

        } else if (error instanceof NetworkError) {

            returnCode = RespondCode.NetworkError; //服务器无响应,Socket关闭，服务器宕机，DNS错误都会产生这个错误

        } else if (error instanceof ServerError) {
            NetworkResponse networkResponse = error.networkResponse;
            if (null != networkResponse) {
                int errorCode = networkResponse.statusCode;
                returnCode = String.valueOf(errorCode);
            }
        }

        if (mResultListener != null) {
            mResultListener.onFail(RespondCode.getErrorNotice(returnCode), tag);
            ToastUtil.getInstance().showToast(RespondCode.getErrorNotice(returnCode)); //错误信息toast
        }
    }

    /**
     * 停止0~N个请求
     */
    public void stopRequestTask(ActionType...tags) {

        for (int i = 0; i < tags.length; i++) {

            cancleRequest(tags[i]);
        }
        mRequestQueue.stop();
    }

    /**
     * 取消指定的请求
     */
    public void cancleRequest(ActionType...tag) {
        if (tag != null && tag.length > 0)
            mRequestQueue.cancelAll(tag[0]);
    }
}
