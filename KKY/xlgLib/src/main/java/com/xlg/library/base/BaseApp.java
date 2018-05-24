package com.xlg.library.base;

import android.app.Application;
import android.content.Context;

/**
 * @Author: Jason
 * @Time: 2018/4/19 10:54
 * @Description:
 */
public class BaseApp extends Application {

    private static Context mAppContext;

    public BaseApp() {
    }

    public void onCreate() {
        super.onCreate();

        mAppContext = this;
    }

    public static Context getAppContext() {
        return mAppContext;
    }
}
