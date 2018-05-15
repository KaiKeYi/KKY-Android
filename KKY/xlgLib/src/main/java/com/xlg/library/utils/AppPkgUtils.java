package com.xlg.library.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * @Author: Jason
 * @Time: 2018/4/19 11:25
 * @Description:
 */
public class AppPkgUtils {

    public AppPkgUtils() {
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
