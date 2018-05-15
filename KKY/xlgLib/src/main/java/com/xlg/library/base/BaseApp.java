package com.xlg.library.base;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.xlg.library.database.DataBaseManager;
import com.xlg.library.helper.CrashHelper;
import com.xlg.library.helper.ImageDisplayOptions;
import com.xlg.library.helper.ThreadHelper;

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

        ThreadHelper.excuteThread(new Runnable() {

            @Override
            public void run() {

                // 初始化崩溃统计
                CrashHelper.init();

                //初始化图片加载器
                initImageLoader();

                //初始化数据库
                DataBaseManager.getDataBaseManager().initDB(BaseApp.getAppContext());
            }
        });
    }

    public static Context getAppContext() {
        return mAppContext;
    }

    void initImageLoader() {
        ImageDisplayOptions.changeMetrics(this);
        ImageLoaderConfiguration config = ImageDisplayOptions
                .imageConfig(getApplicationContext());
        ImageLoader.getInstance().init(config);
    }
}
