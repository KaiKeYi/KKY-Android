package xyz.yikai.kky.app;

import com.xlg.library.base.BaseApp;

/**
 * @Author: Jason
 * @Time: 2018/4/20 09:39
 * @Description:
 */
public class App extends BaseApp {

    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;
    }

    public static App getInstance() {
        return app;
    }

}
