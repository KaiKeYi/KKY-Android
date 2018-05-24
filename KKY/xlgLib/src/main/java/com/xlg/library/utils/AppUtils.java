package com.xlg.library.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * @Author: Jason
 * @Time: 2018/4/19 11:25
 * @Description:
 */
public class AppUtils {

    public AppUtils() {
    }

    private static Boolean isDebug = null;

    public static boolean isDebug() {
        return isDebug == null ? false : isDebug.booleanValue();
    }

    /**
     * Sync lib debug with app's debug value. Should be called in module Application
     * AppUtils.syncIsDebug(getApplicationContext());
     * @param context
     */
    public static void syncIsDebug(Context context) {
        if (isDebug == null) {
            isDebug = context.getApplicationInfo() != null &&
                    (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        }
    }

    public static String getVersionName(Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = null;

        try {
            pi = pm.getPackageInfo(context.getPackageName(), 0);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return pi == null?null:pi.versionName;
    }

    public static int getVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = null;

        try {
            pi = pm.getPackageInfo(context.getPackageName(), 0);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return pi == null?-1:pi.versionCode;
    }
}
