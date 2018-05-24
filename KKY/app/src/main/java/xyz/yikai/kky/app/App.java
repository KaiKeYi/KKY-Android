package xyz.yikai.kky.app;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.xlg.library.base.BaseApp;
import com.xlg.library.helper.ThreadHelper;

import xyz.yikai.kky.helper.CrashHelper;
import xyz.yikai.kky.helper.ImageDisplayOptions;

/**
 * @Author: Jason
 * @Time: 2018/4/20 09:39
 * @Description:Application
 */
public class App extends BaseApp {

    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;

        ThreadHelper.excuteThread(new Runnable() {

            @Override
            public void run() {

                // 初始化崩溃统计
                CrashHelper.init();

                //初始化图片加载器
                initImageLoader();
            }
        });
    }

    public static App getInstance() {
        return app;
    }

    void initImageLoader() {
        ImageDisplayOptions.changeMetrics(this);
        ImageLoaderConfiguration config = ImageDisplayOptions
                .imageConfig(getApplicationContext());
        ImageLoader.getInstance().init(config);
    }
}
