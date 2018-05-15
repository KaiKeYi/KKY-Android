package com.xlg.library.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.xlg.library.R;
import com.xlg.library.base.BaseApp;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

    /**
     * 需要加粗 并且 使用字体包的
     *
     * @param textView
     */
    public static void setTypeFace(TextView textView) {
        Typeface type = Typeface.createFromAsset(BaseApp.getAppContext().getAssets(), "font/avanti_bold.ttf");
        textView.setTypeface(type);
    }

    /**
     * 判断网络状态
     */
    public static boolean isNetworkAvailable(Context context) {
        if (context == null)
            return false;
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 判断是否是wifi
     */
    public static boolean isWIFI(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    /**
     * 获取当前网络连接的类型信息
     *
     * @param context
     * @return 1 WiFi 0 普通网络 -1 无网络
     */
    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }


    /**
     * 是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {

        if (null == str) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 关闭软键盘
     *
     * @param context
     * @param view
     */
    public static void closeSoftPan(Context context, View view) {

        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); // 强制隐藏键盘

    }

    /**
     * 打开软键盘
     *
     * @param context
     * @param view
     */
    public static void showSoftPan(Context context, View view) {

        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 屏幕宽度
     *
     * @return
     */
    public static int getWindowWidth() {

        Activity appContext = getCurrentActivity();
        DisplayMetrics dm = new DisplayMetrics();
        appContext.getWindowManager().getDefaultDisplay()
                .getMetrics(dm);
        appContext = null;
        return dm.widthPixels;
    }

    /**
     * 屏幕高度
     *
     * @return
     */
    public static int getWindowHeight() {

        Activity appContext = getCurrentActivity();
        DisplayMetrics dm = new DisplayMetrics();
        appContext.getWindowManager().getDefaultDisplay()
                .getMetrics(dm);
        appContext = null;
        return dm.heightPixels;

    }

    /**
     * 跳转
     *
     * @param _class
     */
    public static void skipActivity(Class<? extends Activity> _class) {

        Activity appContext = getCurrentActivity();
        Intent skipIntent = new Intent();
        skipIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        skipIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        skipIntent.setClass(appContext, _class);
        appContext.startActivity(skipIntent);
        appContext.overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        appContext = null;
    }

    /**
     * 跳转
     *
     * @param _class
     */
    public static void skipActivity(Intent skipIntent,
                                    Class<? extends Activity> _class) {

        Activity appContext = getCurrentActivity();
        skipIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        skipIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        skipIntent.setClass(appContext, _class);
        appContext.startActivity(skipIntent);
        appContext.overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        appContext = null;
        skipIntent = null;
    }

    /**
     * 跳转
     *
     * @param _class
     */
    public static void skipActivity(Class<? extends Activity> _class, int animIn, int animOut) {

        Activity appContext = getCurrentActivity();
        Intent skipIntent = new Intent();
        skipIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        skipIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        skipIntent.setClass(appContext, _class);
        appContext.startActivity(skipIntent);
        appContext.overridePendingTransition(animIn, animOut);
        appContext = null;
    }


    /**
     * 附带参数跳转
     *
     * @param _class
     */
    public static void skipBundleActivity(Map<String, Serializable> params,
                                          Class<? extends Activity> _class, int animIn, int animOut) {

        Activity appContext = getCurrentActivity();
        Intent skipIntent = new Intent();
        Iterator<String> iterator = params.keySet().iterator();

        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            Serializable value = params.get(key);
            skipIntent.putExtra(key, value);
        }

        skipIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        skipIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        skipIntent.setClass(appContext, _class);
        appContext.startActivity(skipIntent);
        appContext.overridePendingTransition(animIn, animOut);
        appContext = null;
    }

    /**
     * 跳转
     *
     * @param _class
     */
    public static void skipActivityForResult(Class<? extends Activity> _class, int requestCode, int animIn, int animOut) {

        Activity appContext = getCurrentActivity();
        Intent skipIntent = new Intent();
        skipIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        skipIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        skipIntent.setClass(appContext, _class);
        appContext.startActivityForResult(skipIntent, requestCode);
        appContext.overridePendingTransition(animIn, animOut);
        appContext = null;
    }

    /**
     * 跳转
     *
     * @param _class
     */
    public static void skipActivity(Intent skipIntent,
                                    Class<? extends Activity> _class, int animIn, int animOut) {

        Activity appContext = getCurrentActivity();
        skipIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        skipIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        skipIntent.setClass(appContext, _class);
        appContext.startActivity(skipIntent);
        appContext.overridePendingTransition(animIn, animOut);
        appContext = null;
    }

    /**
     * 跳转
     *
     * @param _class
     */
    public static void skipActivityNoAnim(Class<? extends Activity> _class) {

        Activity appContext = getCurrentActivity();
        Intent skipIntent = new Intent();
        skipIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        skipIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        skipIntent.setClass(appContext, _class);
        appContext.startActivity(skipIntent);
    }

    /**
     * 打开系统的浏览器
     *
     * @param activity
     * @param mUrl
     */
    public static void openSystemWebView(Activity activity, String mUrl) {

        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(mUrl);
        intent.setData(content_url);
        activity.startActivity(intent);
        activity = null;
    }

    /**
     * md5
     *
     * @param str
     * @return
     */
    public static String getMD5(String str) {

        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        char[] charArray = str.toCharArray();

        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * 跳转到main
     *
     * @param context
     * @param clazz
     */
    public static void skipLauncher(Context context,
                                    Class<? extends Activity> clazz) {

        try {

            Intent intent = new Intent();
            String launch = clazz.getName();

            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            String packageNames = info.packageName;

            intent.setComponent(new ComponentName(packageNames, launch));
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            context.startActivity(intent);

        } catch (NameNotFoundException e) {

            e.printStackTrace();
        }
    }


    public static int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                BaseApp.getAppContext().getResources().getDisplayMetrics());
    }

    /**
     * 获取渠道号
     */
    public static String getChannelID() {
        String channels = "";
        try {
            Context ctx = BaseApp.getAppContext();
            ApplicationInfo appInfo = ctx.getPackageManager().getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
            int chan = appInfo.metaData.getInt("RENTALCAR_CHANNLE");
            if (chan == 0) {
                channels = "0000";
            } else {
                channels = chan + "";
            }
        } catch (PackageManager.NameNotFoundException e) {
        }

        if (TextUtils.isEmpty(channels)) {
            channels = "0000";
        }
        return channels;

    }

    public static Activity getCurrentActivity() {

        try {

            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);

            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            Map activities = (Map) activitiesField.get(activityThread);

            for (Object activityRecord : activities.values()) {

                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);

                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    Activity activity = (Activity) activityField.get(activityRecord);
                    return activity;
                }
            }

        } catch (Exception e) {
            LogUtil.e(e.toString());
        }

        return null;
    }

    public static void finishOatherActivity() {

        try {

            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);

            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            Map activities = (Map) activitiesField.get(activityThread);

            for (Object activityRecord : activities.values()) {

                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);

                if (pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    Activity activity = (Activity) activityField.get(activityRecord);
                    activity.finish();
                }
            }

        } catch (Exception e) {
            LogUtil.e(e.toString());
        }
    }

    /**
     * 获取版本号
     * @param context
     * @return
     */
    public static String getApplicationVersion(Context context) {
        String version = "";
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }
}
